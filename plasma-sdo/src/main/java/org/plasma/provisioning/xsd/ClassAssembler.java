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

import java.util.UUID;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.Alias;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.Documentation;
import org.plasma.provisioning.DocumentationType;
import org.plasma.provisioning.NameUtils;
import org.plasma.xml.schema.ComplexRestrictionType;
import org.plasma.xml.schema.ComplexType;
import org.plasma.xml.schema.Element;
import org.plasma.xml.schema.ExtensionType;
import org.plasma.xml.schema.SchemaUtil;

public class ClassAssembler extends AbstractAssembler {
	private static Log log = LogFactory.getLog(
			ClassAssembler.class); 
	
	
	public ClassAssembler(ConverterSupport converterSupport,
			String destNamespaceURI,
			String destNamespacePrefix) {
		super(destNamespaceURI, destNamespacePrefix, converterSupport);
	}

	public Class buildClass(ComplexType complexType)
    {
    	Class clss = new Class();
        clss.setId(UUID.randomUUID().toString());
        clss.setUri(this.destNamespaceURI); // FIXME - what if we collect multiple URI's in the query
        Alias alias = new Alias();
        clss.setAlias(alias);       
        alias.setLocalName(complexType.getName()); // because XML schema "projection" names could differ
                
        String name = formatLocalClassName(
        		complexType.getName());
        clss.setName(name);        
        
        String physicalNameAlias = NameUtils.toAbbreviatedName(name);
        alias.setPhysicalName(physicalNameAlias); // Note: SDOX only allows one alias name, hence we expect PhysicalName
        
		Documentation documentation = createDocumentation(
            	DocumentationType.DEFINITION,
            	getDocumentationContent(complexType));
		clss.getDocumentations().add(documentation);
		
		if (complexType.getComplexContent() != null) { // has a base type
			ExtensionType extension = complexType.getComplexContent().getExtension(); 
			if (extension != null) {
				QName base = extension.getBase();
	    	    if (base != null && !base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName()))
	    	    {	
					ClassRef baseRef = new ClassRef();
					String formattedClassName = formatLocalClassName(base.getLocalPart());
					baseRef.setName(formattedClassName);
					baseRef.setUri(this.destNamespaceURI);
					clss.getSuperClasses().add(baseRef);
	    	    }
			}
			ComplexRestrictionType restriction = complexType.getComplexContent().getRestriction(); 
			if (restriction != null) {
				QName base = restriction.getBase();
	    	    if (base != null && !base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName()))
	    	    {	
					ClassRef baseRef = new ClassRef();
					String formattedClassName = formatLocalClassName(base.getLocalPart());
					baseRef.setName(formattedClassName);
					baseRef.setUri(this.destNamespaceURI);
					clss.getSuperClasses().add(baseRef);
	    	    }
			}
		}
    	
    	return clss;
    }

    public Class buildClass(Element element)
    {
    	Class clss = new Class();
        clss.setId(UUID.randomUUID().toString());
        clss.setUri(this.destNamespaceURI); // FIXME - what if we collect multiple URI's  
        Alias alias = new Alias();
        clss.setAlias(alias);       
        alias.setLocalName(element.getName()); // because XML schema "projection" names could differ
        
        String name = formatLocalClassName(
        		element.getName());
        clss.setName(name);        
        
        String physicalNameAlias = NameUtils.toAbbreviatedName(name);
        alias.setPhysicalName(physicalNameAlias); 
        
		Documentation documentation = createDocumentation(
            	DocumentationType.DEFINITION,
            	getDocumentationContent(element));
		clss.getDocumentations().add(documentation);
		
		if (element.getComplexType() != null && element.getComplexType().getComplexContent() != null) { // has a base type
			ExtensionType baseType = element.getComplexType().getComplexContent().getExtension(); 
			QName base = baseType.getBase();
    	    if (!base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName()))
    	    {	
				ClassRef baseRef = new ClassRef();
				String formattedClassName = formatLocalClassName(base.getLocalPart());
				baseRef.setName(formattedClassName);
				baseRef.setUri(this.destNamespaceURI);
				clss.getSuperClasses().add(baseRef);
    	    }
		}
		else if (element.getSubstitutionGroup() != null) {
			QName base = element.getSubstitutionGroup();
    	    if (!base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName()))
    	    {	
				ClassRef baseRef = new ClassRef();
				String formattedClassName = formatLocalClassName(base.getLocalPart());
				baseRef.setName(formattedClassName);
				baseRef.setUri(this.destNamespaceURI);
				clss.getSuperClasses().add(baseRef);
    	    }
		}
    	
		if (log.isDebugEnabled()) 
			log.debug("created class: " + clss.getUri() + "#"
				+ clss.getName());
		 
    	return clss;
    }    
}
