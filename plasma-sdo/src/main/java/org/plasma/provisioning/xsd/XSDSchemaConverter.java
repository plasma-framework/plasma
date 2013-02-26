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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.DataTypeRef;
import org.plasma.provisioning.Documentation;
import org.plasma.provisioning.DocumentationType;
import org.plasma.provisioning.Enumeration;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.Property;
import org.plasma.provisioning.SchemaConverter;
import org.plasma.xml.schema.AbstractComplexType;
import org.plasma.xml.schema.Annotated;
import org.plasma.xml.schema.Annotation;
import org.plasma.xml.schema.Any;
import org.plasma.xml.schema.Attribute;
import org.plasma.xml.schema.AttributeGroup;
import org.plasma.xml.schema.AttributeGroupRef;
import org.plasma.xml.schema.ComplexRestrictionType;
import org.plasma.xml.schema.ComplexType;
import org.plasma.xml.schema.Element;
import org.plasma.xml.schema.ExplicitGroup;
import org.plasma.xml.schema.ExtensionType;
import org.plasma.xml.schema.LocalElement;
import org.plasma.xml.schema.OpenAttrs;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SchemaConstants;
import org.plasma.xml.schema.SchemaUtil;
import org.plasma.xml.schema.SimpleExtensionType;
import org.plasma.xml.schema.SimpleType;


public class XSDSchemaConverter 
    implements SchemaConverter 
{
	private static Log log = LogFactory.getLog(
			   XSDSchemaConverter.class); 
	private ConverterSupport support;   
	private QName appNamespaceQName;
    protected String destNamespaceURI;
    protected String destNamespacePrefix;
    protected PropertyAssembler propertyAssembler;
    protected ClassAssembler classAssembler;
	
    public XSDSchemaConverter(Schema schema, 
    	String destNamespaceURI,
    	String destNamespacePrefix) {
    	this.destNamespaceURI = destNamespaceURI;
    	this.destNamespacePrefix = destNamespacePrefix;
    	this.support = new ConverterSupport(schema, 
    			destNamespaceURI, destNamespacePrefix);
        this.appNamespaceQName = this.support.findOpenAttributeQNameByValue(
        		this.support.getSchema().getTargetNamespace(), 
        		this.support.getSchema()); 
    	this.propertyAssembler = new PropertyAssembler(
    			this.support, this.appNamespaceQName);    	
    	this.classAssembler = new ClassAssembler(this.support,
    			this.destNamespaceURI,
        		this.destNamespacePrefix);
	}
	
    @SuppressWarnings("unchecked")
	public Model buildModel()
    {
        Model model = new Model();
    	model.setName(this.support.getSchema().getId());
        model.setId(UUID.randomUUID().toString());
        model.setUri(this.support.getDestNamespaceURI());
        
        for (OpenAttrs attrs : this.support.getSchema().getIncludesAndImportsAndRedefines()) {
        	if (attrs instanceof Annotation) {
        		String doc = this.support.getDocumentationContent((Annotation)attrs);
        		if (doc != null && doc.length() > 0) {
        			Documentation documentation = this.support.createDocumentation(
        	            	DocumentationType.DEFINITION, doc);
        			model.getDocumentations().add(documentation);
        		}
        	}
        }
        
        // map top-level objects (process attributes/properties on another pass)
        for (Annotated annotated : this.support.getSchema().getSimpleTypesAndComplexTypesAndGroups()) {
        	if (annotated instanceof ComplexType)
        	{
        	    ComplexType complexType = (ComplexType)annotated;	
        	    if (complexType.getName().equals(SchemaUtil.getSerializationBaseTypeName()))
        	    	continue; // skip the serialization base type
        	    this.support.getComplexTypeMap().put(complexType.getName(), complexType);
        	}
        	else if (annotated instanceof SimpleType) {
        		SimpleType simpleType = (SimpleType)annotated;	
        		if (simpleType.getName() == null || simpleType.getName().trim().length() == 0)
        			throw new IllegalStateException("expected name for top level simple type");
        		this.support.getSimpleTypeMap().put(simpleType.getName(), simpleType);
        	}
        	else if (annotated instanceof Element) {
        		Element element = (Element)annotated;
        		if (element.getName() == null || element.getName().trim().length() == 0)
        			throw new IllegalStateException("expected name for top level element");
        		this.support.getElementMap().put(element.getName(), element);
        	}
        	else if (annotated instanceof AttributeGroup) {
        		AttributeGroup attrGroup = (AttributeGroup)annotated;
        		this.support.getAttributeGroupMap().put(attrGroup.getName(), attrGroup);
        		this.support.getAttributeGroupMap().put(attrGroup.getId(), attrGroup);
        	}
        	else
        		log.warn("unknown annotated class, " 
        				+ annotated.getClass().getName());
        }

        // collect local enumerations from type hierarchies
        // starting with each top-level simple type
        EnumerationAssembler enumerationAssembler = new EnumerationAssembler(
        		this.support,
        		this.destNamespaceURI, this.destNamespacePrefix);
        for (Annotated annotated : this.support.getSchema().getSimpleTypesAndComplexTypesAndGroups()) {
        	if (annotated instanceof SimpleType) {
        		SimpleType simpleType = (SimpleType)annotated;
        		EnumerationCollector collector = 
        			new EnumerationCollector(this.support.getSchema(), 
        				this.support.getSimpleTypeMap(), enumerationAssembler);
        		simpleType.accept(collector);
        		for (Enumeration enumeration : collector.getResult())
        			model.getEnumerations().add(enumeration);
        	}
        }
        
        // create classes
        // FIXME: can we not have top level elements as well as classes?
        if (this.support.getElementMap().size() > 0) {
        	for (Element element : this.support.getElementMap().values()) {
        		ComplexType complexType = this.support.getComplexTypeMap().get(element.getName());
        		if (complexType != null) { // element has type
            	    Class cls = classAssembler.buildClass(complexType);
            	    model.getClazzs().add(cls);
            	    this.support.getClassQualifiedNameMap().put(destNamespaceURI + "#" + cls.getName(), cls);        	
            	    this.support.getClassLocalNameMap().put(cls.getAlias().getLocalName(), cls); 
            	    if (log.isDebugEnabled())
            	    	log.debug("created class, " + cls.getName());
        		}
        		else {
            	    Class cls = classAssembler.buildClass(element);
            	    model.getClazzs().add(cls);
            	    this.support.getClassQualifiedNameMap().put(destNamespaceURI + "#" + cls.getName(), cls);        	
            	    this.support.getClassLocalNameMap().put(cls.getAlias().getLocalName(), cls);        	
            	    if (log.isDebugEnabled())
            	    	log.debug("created class, " + cls.getName());
        		}
        	}
        }
        else {
        	for (ComplexType complexType : this.support.getComplexTypeMap().values()) {
        	    Class cls = classAssembler.buildClass(complexType);
        	    model.getClazzs().add(cls);
        	    this.support.getClassQualifiedNameMap().put(destNamespaceURI + "#" + cls.getName(), cls);        	
        	    this.support.getClassLocalNameMap().put(cls.getAlias().getLocalName(), cls);        	
        	    if (log.isDebugEnabled())
        	    	log.debug("created class, " + cls.getName());
        	}        	
        }
        
        // build a map of classes to their subclasses
        // so we can process properties while doing a breadth-first
        // traversal across the class lattice
        this.support.collectSubclasses();
        
        // create properties in class lattice order
        // starting w/each root
        ClassVisitor visitor = new ClassVisitor() {
			public void visit(Class target, Class source) {
	    	    if (log.isDebugEnabled())
	    	    	log.debug("creating properties for class, " + target.getName());
	        	String localName = target.getAlias().getLocalName();
	        	if (localName == null || localName.trim().length() == 0)
	        		throw new IllegalStateException("expected local name apias for class, " 
	        				+ target.getName());
	        	ComplexType complexType = support.getComplexTypeMap().get(localName);
	        	if (complexType != null) {
	        		buildProperties(target, complexType);
	        	}
	        	else {
	        		Element element = support.getElementMap().get(localName);
	        		if (element != null) 
	            		buildProperties(target, element);
	        		else
	        			throw new IllegalStateException("expected top level conplex type or element for local name, "
	        					+ localName);
	        	}
			}        	
        };
        
        // traverse each root and any subclass hierarchy
        for (Class root : this.support.getRootClasses()) {
    	    if (log.isDebugEnabled())
    	    	log.debug("traversing 'root' class, " + root.getName());
        	this.support.accept(root, visitor);
        }

        // add derived target properties
        for (Class cls : this.support.getClassQualifiedNameMap().values())
        {
        	Property[] props = new Property[cls.getProperties().size()];
        	cls.getProperties().toArray(props); // avoid concurrent mods for recursive relationships
        	for (Property prop : props) {
        		if (prop.getType() == null)
        			throw new IllegalStateException("property "
        				+ cls.getName() + "." + prop.getName() 
        				+ " has no datatype");
        		if (prop.getType() instanceof DataTypeRef) 
        			continue;
        		ClassRef classRef = (ClassRef)prop.getType();
        		String qualifiedName = classRef.getUri() 
				    + "#" + classRef.getName();
        		Class targetClass = this.support.getClassQualifiedNameMap().get(qualifiedName);
        		if (targetClass == null)
        			throw new IllegalStateException("no class definition found for qualified name '"
        					+ qualifiedName + "'");
        		Property targetProperty = null;
        		Property[] pdefs2 = new Property[targetClass.getProperties().size()];
        		targetClass.getProperties().toArray(pdefs2);
        		for (Property pdef2 : pdefs2) {
        			if (pdef2.getName().equals(prop.getOpposite())) {
        				targetProperty = pdef2;
        				break;
        			}
        		}
        		if (targetProperty == null) {
        			if (prop.getOpposite() != null) {
        			    targetProperty = this.propertyAssembler.createDerivedPropertyOpposite(cls, prop);
        			    targetClass.getProperties().add(targetProperty);
        			}
        		}
        	}
        }

        return model;
    }  
    
    private boolean hasBase(ComplexType complexType) {
    	if (complexType.getComplexContent() != null) {
			ExtensionType extension = complexType.getComplexContent().getExtension(); 
			if (extension != null) {
				QName base = extension.getBase();
	    	    if (base != null && !base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName()))
	    	       return true;	
			}
			ComplexRestrictionType restriction = complexType.getComplexContent().getRestriction(); 
			if (restriction != null) {
				QName base = restriction.getBase();
	    	    if (base != null && !base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName()))
	    	    	return true;		
			}
    	}
		return false;
    }
    
    private boolean hasBase(Element element) {
		if (element.getComplexType() != null && element.getComplexType().getComplexContent() != null) { // has a base type
			ExtensionType baseType = element.getComplexType().getComplexContent().getExtension(); 
			if (baseType != null) {
			    QName base = baseType.getBase();
    	        if (!base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName()))
    	    	    return true;
			}
		}     	
		else if (element.getSubstitutionGroup() != null) {
			QName base = element.getSubstitutionGroup();
    	    if (!base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName()))
	    	    return true;
		}
    	return false;
    }
    
    private void buildProperties(Class cls, Element element) {
    	if (element.getComplexType() != null) {
    		buildProperties(cls, element.getComplexType());
    	}
    	else {
    	    log.warn("ignoring element w/o local complex type, "
    	    		+ element.getName());	
    	}
    }    
    
    private void buildProperties(Class cls, AbstractComplexType complexType) {
    	
    	// get attributes and build datatype properties
    	List<Annotated> annotatedList = null;
    	if (complexType.getComplexContent() == null) {      		
    		// must be either complex or simple content
    		if (complexType.getSimpleContent() == null) {
    		    annotatedList = complexType.getAttributesAndAttributeGroups();  
    		}
    		else {
    			if (complexType.getSimpleContent().getExtension() != null) {
    				SimpleExtensionType extension = complexType.getSimpleContent().getExtension();
    				annotatedList = extension.getAttributesAndAttributeGroups();
    				QName base = extension.getBase();
    				if (base != null)
	    				if (base.getNamespaceURI().equals(SchemaConstants.XMLSCHEMA_NAMESPACE_URI)) {
	    				    // create a synthetic datatype property to accommodate
	    					// the value of this element or type
	    					Property property = this.propertyAssembler.buildElementContentDatatypeProperty(cls,
	    				    		base);
	            		    cls.getProperties().add(property);
	            	        Map<String, Property> classProps = this.support.getClassPropertyMap().get(cls);
	            	        if (classProps == null) {
	            	        	classProps = new HashMap<String, Property>();
	            	        	this.support.getClassPropertyMap().put(cls, classProps);
	            	        }
	        	        	classProps.put(property.getName(), property);
	    				}
	    				else
	    					log.warn("expected XML Schema namespace " 
	    				        + SchemaConstants.XMLSCHEMA_NAMESPACE_URI
	    				        + " as extension base for simple type - ignoring");
    			}
    		}
        }
    	else { //complexType.getComplexContent() != null
    		if (complexType.getComplexContent().getExtension() != null)
    		    annotatedList = complexType.getComplexContent().getExtension().getAttributesAndAttributeGroups();
    		else if (complexType.getComplexContent().getRestriction() != null)
    		    annotatedList = complexType.getComplexContent().getRestriction().getAttributesAndAttributeGroups();
    	}    	
    	
    	if (annotatedList != null)
    	    buildProperties(cls, complexType, annotatedList);
    	        
        // get sequences and build properties
        // from elements
    	ExplicitGroup sequence = null;
    	if (complexType.getComplexContent() == null) { // no base type
    		sequence = complexType.getSequence();
    	}
    	else { // complexType.getComplexContent() != null
    		if (complexType.getComplexContent().getExtension() != null)
    		    sequence = complexType.getComplexContent().getExtension().getSequence();
    		else if (complexType.getComplexContent().getRestriction() != null)
    			sequence = complexType.getComplexContent().getRestriction().getSequence();
    	} 
    	if (sequence != null)
    	    buildProperties(cls, complexType, sequence); 
    	
    	

        // get choice and build properties
        // from elements
    	ExplicitGroup choice = null;
    	if (complexType.getComplexContent() == null) { // no base type
    		choice = complexType.getChoice();
    	}
    	else { // complexType.getComplexContent() != null
    		if (complexType.getComplexContent().getExtension() != null)
    		    choice = complexType.getComplexContent().getExtension().getChoice();
    		else if (complexType.getComplexContent().getRestriction() != null)
    			choice = complexType.getComplexContent().getRestriction().getChoice();
    	}   
    	if (choice != null)
    	    buildProperties(cls, complexType, choice);  
    	
    }
    
    private void buildProperties(Class cls, AbstractComplexType complexType, ExplicitGroup explicitGroup)
    {
        for (int i = 0; i < explicitGroup.getElementsAndGroupsAndAlls().size(); i++) {
        	Object obj = explicitGroup.getElementsAndGroupsAndAlls().get(i);
        	if (obj instanceof JAXBElement) {
        		JAXBElement element = (JAXBElement)obj;
        		if (element.getValue() instanceof LocalElement) {
        		    LocalElement localElement = (LocalElement)element.getValue();
        		    Property property = this.propertyAssembler.buildProperty(cls, complexType, explicitGroup, null, localElement, i);
        		    cls.getProperties().add(property);
        	        Map<String, Property> classProps = this.support.getClassPropertyMap().get(cls);
        	        if (classProps == null) {
        	        	classProps = new HashMap<String, Property>();
        	        	this.support.getClassPropertyMap().put(cls, classProps);
        	        }
    	        	classProps.put(property.getName(), property);
        		}
        		else if (element.getValue() instanceof ExplicitGroup) {
        			ExplicitGroup childGroup = (ExplicitGroup)element.getValue();
        	        for (int j = 0; j < childGroup.getElementsAndGroupsAndAlls().size(); j++) {
        	        	Object obj2 = childGroup.getElementsAndGroupsAndAlls().get(j);
        	        	if (obj2 instanceof JAXBElement) {
        	        		JAXBElement element2 = (JAXBElement)obj2;
        	        		if (element2.getValue() instanceof LocalElement) {
        	        		    LocalElement localElement = (LocalElement)element2.getValue();
        	        		    Property property = this.propertyAssembler.buildProperty(cls, complexType, 
        	        		    		explicitGroup, childGroup, localElement, j);
        	        		    cls.getProperties().add(property);
        	        	        Map<String, Property> classProps = this.support.getClassPropertyMap().get(cls);
        	        	        if (classProps == null) {
        	        	        	classProps = new HashMap<String, Property>();
        	        	        	this.support.getClassPropertyMap().put(cls, classProps);
        	        	        }
        	    	        	classProps.put(property.getName(), property);
        	        		}
        	        		else if (element2.getValue() instanceof ExplicitGroup) {
        	        			ExplicitGroup grandChildGroup = (ExplicitGroup)element2.getValue();
        	        	        for (int k = 0; k < grandChildGroup.getElementsAndGroupsAndAlls().size(); k++) {
        	        	        	Object obj3 = grandChildGroup.getElementsAndGroupsAndAlls().get(k);
        	        	        	if (obj3 instanceof JAXBElement) {
        	        	        		JAXBElement element3 = (JAXBElement)obj3;
        	        	        		if (element3.getValue() instanceof LocalElement) {
        	        	        		    LocalElement localElement = (LocalElement)element3.getValue();
        	        	        		    Property property = this.propertyAssembler.buildProperty(cls, complexType, 
        	        	        		    		childGroup, grandChildGroup, localElement, k);
        	        	        		    cls.getProperties().add(property);
        	        	        	        Map<String, Property> classProps = this.support.getClassPropertyMap().get(cls);
        	        	        	        if (classProps == null) {
        	        	        	        	classProps = new HashMap<String, Property>();
        	        	        	        	this.support.getClassPropertyMap().put(cls, classProps);
        	        	        	        }
        	        	    	        	classProps.put(property.getName(), property);
        	        	        		}
        	        	        		else
        	                         	    log.warn("unexpected sequence/choice great-grandchild JAXBElement value class, " 
        	                              			+ element3.getValue().getClass().getName());
        	        	        	} // 
        	        	        } // for
        	    		
        	        		}
            	        	else
                         	    log.warn("unexpected sequence/choice grandchild JAXBElement value class, " 
                          			+ element2.getValue().getClass().getName());
        	        	}
        	        }
        		}
            	else {
            		log.warn("unexpected choice child JAXBElement value class, " 
            			+ element.getValue().getClass().getName());
            	}
        	}
        	else if (obj instanceof Any) {
        		log.debug("ignoring sequence/choice child class, " 
            			+ obj.getClass().getName());
        	}
        	else
        		log.warn("unexpected child class, " 
        			+ obj.getClass().getName());
        }   
    }
    
    private void buildProperties(Class cls, AbstractComplexType complexType, List<Annotated> annotatedList) {
        
    	for (Annotated annot : annotatedList) {
        	if (annot instanceof Attribute) {
        		Attribute attribute = (Attribute)annot;
        		Property property = this.propertyAssembler.buildDatatypeProperty(cls, complexType, attribute);
        		cls.getProperties().add(property);
    	        Map<String, Property> classProps = this.support.getClassPropertyMap().get(cls);
    	        if (classProps == null) {
    	        	classProps = new HashMap<String, Property>();
    	        	this.support.getClassPropertyMap().put(cls, classProps);
    	        }
	        	classProps.put(property.getName(), property);
        	}
        	else if (annot instanceof AttributeGroupRef) {
        		AttributeGroupRef ref = (AttributeGroupRef)annot;
        		AttributeGroup attrGroup = this.support.getAttributeGroupMap().get(ref.getRef().getLocalPart());
        		if (attrGroup == null)
            		throw new IllegalStateException("no top level attribute group found for, " 
                			+ ref.getRef().getLocalPart());
        		for (Annotated annot2 : attrGroup.getAttributesAndAttributeGroups()) {
        			if (annot2 instanceof Attribute) {
                		Attribute attribute = (Attribute)annot2;
           				Property property = this.propertyAssembler.buildDatatypeProperty(cls, complexType, attribute);
                		cls.getProperties().add(property);
            	        Map<String, Property> classProps = this.support.getClassPropertyMap().get(cls);
            	        if (classProps == null) {
            	        	classProps = new HashMap<String, Property>();
            	        	this.support.getClassPropertyMap().put(cls, classProps);
            	        }
        	        	classProps.put(property.getName(), property);
        			}
        			else
            		    throw new IllegalStateException("unexpected AttributeGroup child class, " 
                			+ annot2.getClass().getName());
        		}
        	}
        	else
        		throw new IllegalStateException("unexpected ComplexType child class, " 
            			+ annot.getClass().getName());
        }
    }           
}
