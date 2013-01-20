package org.plasma.provisioning.xsd;

import java.util.HashMap;
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
import org.plasma.provisioning.Enumeration;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.Property;
import org.plasma.provisioning.SchemaConverter;
import org.plasma.xml.schema.AbstractComplexType;
import org.plasma.xml.schema.Annotated;
import org.plasma.xml.schema.Any;
import org.plasma.xml.schema.Attribute;
import org.plasma.xml.schema.AttributeGroup;
import org.plasma.xml.schema.AttributeGroupRef;
import org.plasma.xml.schema.ComplexType;
import org.plasma.xml.schema.Element;
import org.plasma.xml.schema.ExplicitGroup;
import org.plasma.xml.schema.LocalElement;
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
    	this.classAssembler = new ClassAssembler(this.destNamespaceURI,
        		this.destNamespacePrefix);
	}
	
    @SuppressWarnings("unchecked")
	public Model buildModel()
    {
        Model model = new Model();
    	model.setName(this.support.getSchema().getId());
        model.setId(UUID.randomUUID().toString());
        model.setUri(this.support.getDestNamespaceURI());
        
        
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
        if (this.support.getElementMap().size() > 0) {
        	for (Element element : this.support.getElementMap().values()) {
        		ComplexType complexType = this.support.getComplexTypeMap().get(element.getName());
        		if (complexType != null) { // element has type
            	    Class cls = classAssembler.buildClass(complexType);
            	    model.getClazzs().add(cls);
            	    this.support.getClassQualifiedNameMap().put(destNamespaceURI + "#" + cls.getName(), cls);        	
            	    this.support.getClassLocalNameMap().put(cls.getAlias().getLocalName(), cls);        	
        		}
        		else {
            	    Class cls = classAssembler.buildClass(element);
            	    model.getClazzs().add(cls);
            	    this.support.getClassQualifiedNameMap().put(destNamespaceURI + "#" + cls.getName(), cls);        	
            	    this.support.getClassLocalNameMap().put(cls.getAlias().getLocalName(), cls);        	
        		}
        	}
        }
        else {
        	for (ComplexType complexType : this.support.getComplexTypeMap().values()) {
        	    Class cls = classAssembler.buildClass(complexType);
        	    model.getClazzs().add(cls);
        	    this.support.getClassQualifiedNameMap().put(destNamespaceURI + "#" + cls.getName(), cls);        	
        	    this.support.getClassLocalNameMap().put(cls.getAlias().getLocalName(), cls);        	
        	}        	
        }
        
        // build both data and reference properties
        for (Class cls : this.support.getClassQualifiedNameMap().values())
        {
        	String localName = cls.getAlias().getLocalName();
        	if (localName == null || localName.trim().length() == 0)
        		throw new IllegalStateException("expected local name apias for class, " 
        				+ cls.getName());
        	ComplexType complexType = this.support.getComplexTypeMap().get(localName);
        	if (complexType != null) {
        		buildProperties(cls, complexType);
        	}
        	else {
        		Element element = this.support.getElementMap().get(localName);
        		if (element != null) 
            		buildProperties(cls, element);
        		else
        			throw new IllegalStateException("expected top level conplex type or element for local name, "
        					+ localName);
        	}
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
    	else {
    		annotatedList = complexType.getComplexContent().getExtension().getAttributesAndAttributeGroups();
    	}
    	if (annotatedList != null)
    	    buildProperties(cls, complexType, annotatedList);
    	        
        // get sequences and build properties
        // from elements
    	ExplicitGroup sequence = null;
    	if (complexType.getComplexContent() == null) { // no base type
    		sequence = complexType.getSequence();
    	}
    	else {
    		sequence = complexType.getComplexContent().getExtension().getSequence();
    	} 
    	if (sequence != null)
    	    buildProperties(cls, complexType, sequence);   	   

        // get sequences and build properties
        // from elements
    	ExplicitGroup choice = null;
    	if (complexType.getComplexContent() == null) { // no base type
    		choice = complexType.getChoice();
    	}
    	else {
    		choice = complexType.getComplexContent().getExtension().getChoice();
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
