/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.provisioning.xsd;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.ElementNSImpl;
import org.plasma.metamodel.Alias;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.DocumentationType;
import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.EnumerationLiteral;
import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Restriction;
import org.plasma.xml.schema.SimpleType;

public class EnumerationAssembler extends AbstractAssembler {
	
	private static Log log = LogFactory.getLog(
			   EnumerationAssembler.class); 
	
	
	public EnumerationAssembler(ConverterSupport converterSupport, 
			String destNamespaceURI,
			String destNamespacePrefix) {
		super(destNamespaceURI, destNamespacePrefix, converterSupport);
	}

	public Enumeration buildEnumeration(AbstractSimpleType simpleType, AbstractSimpleType source) {
		String name = simpleType.getName();
		if (name == null)
			name = source.getName();
		return buildEnumeration(simpleType, name);
	}	
	
	public Enumeration buildEnumeration(SimpleType simpleType) {
		return buildEnumeration(simpleType, simpleType.getName());
	}
	
	public Enumeration buildEnumeration(AbstractSimpleType simpleType, String name) {
    	Enumeration enm = new Enumeration();
    	enm.setId(UUID.randomUUID().toString());
    	Alias alias = new Alias();
    	enm.setAlias(alias);
    	alias.setLocalName(name);
    	String logicalName = this.formatLocalClassName(name);
    	enm.setName(logicalName);
    	enm.setUri(this.destNamespaceURI);
    	    	
        Documentation documentation = createDocumentation(
        		DocumentationType.DEFINITION,
        		getDocumentationContent(simpleType));
		enm.getDocumentation().add(documentation);
    	Map<String, EnumerationLiteral> literalMap = new HashMap<String, EnumerationLiteral>();
		
    	Restriction restriction = simpleType.getRestriction();
    	if (restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().size() == 0)
			throw new IllegalStateException("expected collection values");
    	for (Object obj : restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives()) {
    		if (obj instanceof org.plasma.xml.schema.Enumeration) {
    			org.plasma.xml.schema.Enumeration schemaEnum = (org.plasma.xml.schema.Enumeration)obj;
    			
    			EnumerationLiteral literal = new EnumerationLiteral();
    			enm.getEnumerationLiteral().add(literal);
    			String literalName = schemaEnum.getValue();
    			literalName = support.buildLogicalEnumerationLiteralName(enm, literalName, literalMap);
    			literalMap.put(literalName, literal);    			
    			literal.setName(literalName);
        		literal.setId(UUID.randomUUID().toString());
                alias = new Alias();
                literal.setAlias(alias);       
                alias.setPhysicalName(literalName); 
                
                
                String value = findAppInfoValue(schemaEnum);
                if (value == null)
                	value = literalName;
                literal.setValue(value);
                
                buildEnumerationLiteralDocumentation(schemaEnum, literal);
    		}
        	else
        		log.warn("unexpected Restriction child class, " 
        			+ obj.getClass().getName());
    	}
    	return enm;
    }

	public void buildEnumerationLiteralDocumentation(org.plasma.xml.schema.Enumeration schemaEnum, EnumerationLiteral literal)
    {
		if (schemaEnum.getAnnotation() != null)
	    for (Object o2 : schemaEnum.getAnnotation().getAppinfosAndDocumentations()) {
	    	if (o2 instanceof org.plasma.xml.schema.Documentation) {
	    		org.plasma.xml.schema.Documentation doc = (org.plasma.xml.schema.Documentation)o2;
	    		if (doc.getContent() != null && doc.getContent().size() > 0) {
		    		for (Object content : doc.getContent()) {
		    			if (content instanceof String) {
		    				Documentation documentation = createDocumentation(
		            		DocumentationType.DEFINITION,
		            		(String)content);
    		    	        literal.getDocumentation().add(documentation);
		    			}
					    else if (content instanceof ElementNSImpl) {
						    ElementNSImpl nsElem = (ElementNSImpl) content;
		    				Documentation documentation = createDocumentation(
				            		DocumentationType.DEFINITION,
				            		serializeElement(nsElem));
		    		    	        literal.getDocumentation().add(documentation);
					    } 
		    			else {
		    				Documentation documentation = createDocumentation(
		            		DocumentationType.DEFINITION,
		            		content.toString());
    		    	        literal.getDocumentation().add(documentation);
		    			}
		    		}
	    		}
	    	}
        	else
        		log.warn("unexpected Enumeration child class, " 
        			+ o2.getClass().getName());
	    }    	
    }
}
