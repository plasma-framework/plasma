package org.plasma.provisioning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.sdo.DataFlavor;
import org.plasma.xml.schema.Annotated;
import org.plasma.xml.schema.Appinfo;
import org.plasma.xml.schema.Attribute;
import org.plasma.xml.schema.ComplexType;
//import org.plasma.xml.schema.Documentation;
//import org.plasma.xml.schema.Enumeration;
import org.plasma.xml.schema.ExplicitGroup;
import org.plasma.xml.schema.ExtensionType;
import org.plasma.xml.schema.Facet;
import org.plasma.xml.schema.LocalElement;
import org.plasma.xml.schema.LocalSimpleType;
import org.plasma.xml.schema.NumFacet;
import org.plasma.xml.schema.OpenAttrs;
import org.plasma.xml.schema.Pattern;
import org.plasma.xml.schema.Restriction;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SchemaConstants;
import org.plasma.xml.schema.SchemaUtil;
import org.plasma.xml.schema.SimpleType;
import org.plasma.xml.schema.Element;
import org.plasma.xml.sdox.SDOXConstants;


public class SchemaProvisioningModelAssembler {
	   private static Log log = LogFactory.getLog(
			   SchemaProvisioningModelAssembler.class); 
	   
    private String destNamespaceURI;
    private String destNamespacePrefix;
    private Map<String, Class> classMap = new HashMap<String, Class>();
	private Schema schema;
	private Map<String, ComplexType> complexTypeMap = new HashMap<String, ComplexType>();
	private Map<String, SimpleType> simpleTypeMap = new HashMap<String, SimpleType>();
	
	private QName appNamespaceQName;
	
	@SuppressWarnings("unused")
	private SchemaProvisioningModelAssembler() {}
	
    public SchemaProvisioningModelAssembler(Schema schema, 
    	String destNamespaceURI,
    	String destNamespacePrefix) {
		super();
		this.schema = schema;
		this.destNamespaceURI = destNamespaceURI;
		this.destNamespacePrefix = destNamespacePrefix;
	}

	public Model getModel() {
		return buildModel(this.schema, 
			this.destNamespaceURI, this.destNamespacePrefix);
	}
	
    @SuppressWarnings("unchecked")
	private Model buildModel(Schema schema, 
    		String destNamespaceURI,
    		String destNamespacePrefix)
    {
    	Model model = new Model();
    	model.setName(schema.getId());
        model.setId(UUID.randomUUID().toString());
        model.setUri(schema.getTargetNamespace());
        //model.setName(schema.getName());
        //model.setUri(schema.getTargetNamespace());
        
        this.appNamespaceQName = getOpenAttributeQNameByValue(schema.getTargetNamespace(), 
        		schema); 
        if (this.appNamespaceQName == null)
        	throw new IllegalStateException("given schema has no target namespace");
        
        // process complex types w/o attributes/properties
        for (Annotated annotated : schema.getSimpleTypesAndComplexTypesAndGroups()) {
        	if (annotated instanceof ComplexType)
        	{
        	    ComplexType complexType = (ComplexType)annotated;	
        	    if (complexType.getName().equals(SchemaUtil.getSerializationBaseTypeName()))
        	    	continue; // skip the serialization base type
        		this.complexTypeMap.put(complexType.getName(), complexType);
        	    Class cls = buildClass(model, complexType);
        	    this.classMap.put(destNamespaceURI + "#" + cls.getName(), cls);
        	}
        	else if (annotated instanceof SimpleType) {
        		SimpleType simpleType = (SimpleType)annotated;	
        		this.simpleTypeMap.put(simpleType.getName(), simpleType);
        	}
        	else if (annotated instanceof Element) {
        		// ignore top level elements on first pass to first load entier set of types
        	}
        	else
        		log.warn("unknown annotated class, " 
        				+ annotated.getClass().getName());
        }
        // look at top level elements
        for (Annotated annotated : schema.getSimpleTypesAndComplexTypesAndGroups()) {
        	if (annotated instanceof Element) {
        		Element element = (Element)annotated;
        	    if (element.getName().equals(SchemaUtil.getSerializationBaseTypeName()))
        	    	continue; // skip the serialization base type
        		ComplexType complexType = this.complexTypeMap.get(element.getName());
        		if (complexType == null)
        			throw new IllegalStateException("found top-level Element '"
        					+ element.getName() + "' but expected top-level " 
        					+ "ComplexType with the same name (XML Schema re-use best practice)" );
        	}
        }
        
        for (Class cls : this.classMap.values())
        {
        	String localName = cls.getName();
        	if (cls.getAlias()!= null && cls.getAlias().getLocalName() != null)
        		localName = cls.getAlias().getLocalName();
        	ComplexType complexType = this.complexTypeMap.get(localName);
        	
        	List<Annotated> annotatedList = null;
        	if (complexType.getComplexContent() == null) { // no base type
        		annotatedList = complexType.getAttributesAndAttributeGroups();  
        	}
        	else {
        		annotatedList = complexType.getComplexContent().getExtension().getAttributesAndAttributeGroups();
        	}
        	
            for (Annotated annot : annotatedList) {
            	if (annot instanceof Attribute) {
            		Attribute attribute = (Attribute)annot;
            		Property property = buildDatatypeProperty(complexType, attribute);
            		cls.getProperties().add(property);
            	}
            	else
            		throw new IllegalStateException("unexpected ComplexType child class, " 
                			+ annot.getClass().getName());
            }
            
        	ExplicitGroup sequence = null;
        	if (complexType.getComplexContent() == null) { // no base type
        		sequence = complexType.getSequence();
        	}
        	else {
        		sequence = complexType.getComplexContent().getExtension().getSequence();
        	}                   
       	            
            if (sequence != null)
	            for (int i = 0; i < sequence.getElementsAndGroupsAndAlls().size(); i++) {
	            	Object obj = sequence.getElementsAndGroupsAndAlls().get(i);
	            	if (obj instanceof JAXBElement) {
	            		JAXBElement element = (JAXBElement)obj;
	            		if (element.getValue() instanceof LocalElement) {
	            		    LocalElement localElement = (LocalElement)element.getValue();
	            		    Property property = buildProperty(complexType, localElement, i);
	            		    cls.getProperties().add(property);
	            		}
	                	else
	                		throw new IllegalStateException("unexpected JAXBElement value class, " 
	                			+ element.getValue().getClass().getName());
	            	}
	            	else
	            		throw new IllegalStateException("unexpected sequence child class, " 
	            			+ obj.getClass().getName());
	            }       	
        }
        
        // add derived target properties
        for (Class cls : this.classMap.values())
        {
        	Property[] props = new Property[cls.getProperties().size()];
        	cls.getProperties().toArray(props); // avoid concurrent mods for recursive relationships
        	for (Property prop : props) {
        		if (prop.getType() instanceof DataTypeRef) 
        			continue;
        		ClassRef classRef = (ClassRef)prop.getType();
        		String qualifiedName = classRef.getUri() 
				    + "#" + classRef.getName();
        		Class targetClass = this.classMap.get(qualifiedName);
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
        			    targetProperty = createDerivedPropertyOpposite(cls, prop);
        			    targetClass.getProperties().add(targetProperty);
        			}
        		}
        	}
        }
        
        for (SimpleType simpleType : this.simpleTypeMap.values()) {
        	Enumeration enm = this.buildEnumeration(simpleType);
        	model.getEnumerations().add(enm);        
        }

        return model;
    }   
    
    private Class buildClass(Package pkg, ComplexType complexType)
    {
    	Class clss = new Class();
    	pkg.getClazzs().add(clss);
        clss.setId(UUID.randomUUID().toString());
        clss.setUri(destNamespaceURI); // FIXME - what if we collect multiple URI's in the query
        Alias alias = new Alias();
        clss.setAlias(alias);       
        alias.setLocalName(complexType.getName()); // because XML schema "projection" names could differ
        
        String name = getSDOXValue(complexType, SDOXConstants.LOCAL_NAME_NAME);
        clss.setName(name); // actual SDO type name stored as sdox name        
        String physicalNameAlias = findSDOXValue(complexType, SDOXConstants.LOCAL_NAME_ALIAS_NAME);      
        if (physicalNameAlias != null)
            alias.setPhysicalName(physicalNameAlias); // Note: SDOX only allows one alias name, hence we expect PhysicalName
        
		Documentation documentation = createDocumentation(
            	DocumentationType.DEFINITION,
            	getDocumentationContent(complexType));
		clss.getDocumentations().add(documentation);
		
		if (complexType.getComplexContent() != null) { // has a base type
			ExtensionType baseType = complexType.getComplexContent().getExtension(); 
			QName base = baseType.getBase();
    	    if (!base.getLocalPart().equals(SchemaUtil.getSerializationBaseTypeName()))
    	    {	
				ClassRef baseRef = new ClassRef();
				baseRef.setName(base.getLocalPart());
				baseRef.setUri(base.getNamespaceURI());
				clss.getSuperClasses().add(baseRef);
    	    }
		}
    	
    	return clss;
    }
    
    private Property buildProperty(ComplexType complexType, LocalElement element, int sequenceNum)
    {
        Property property = new Property();
        property.setId(UUID.randomUUID().toString());
        Alias alias = new Alias();
        property.setAlias(alias);       
        alias.setLocalName(element.getName()); // because XML schema "projection" names could differ
        QName nameQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_NAME, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String name = getOpenAttributeValue(nameQName, element);       
        property.setName(name); // actual SDO type name stored as sdox name  
        
		XmlProperty xmlProp = new XmlProperty();
		xmlProp.setNodeType(XmlNodeType.ELEMENT);
		property.setXmlProperty(xmlProp);

        Documentation documentation = createDocumentation(
            	DocumentationType.DEFINITION,
            	getDocumentationContent(element));
		property.getDocumentations().add(documentation);

        property.setVisibility(VisibilityType.PUBLIC); 
        
        if ("1".equals(String.valueOf(element.getMinOccurs())))
    	    property.setNullable(false);
    	
        QName aliasQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_ALIAS_NAME, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String physicalNameAlias = findOpenAttributeValue(aliasQName, element);       
        if (physicalNameAlias != null)
            alias.setPhysicalName(physicalNameAlias);
        else if (!"unbounded".equals(element.getMaxOccurs()))
        	log.warn("expected SDOX '" + SDOXConstants.LOCAL_NAME_ALIAS_NAME 
        			+ "' attribute for local Element '" + element.getName()
        			+ "' for ComplexType '" + complexType.getName() +"'");    	
 
        QName manyQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_MANY, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        Boolean many = new Boolean(
        		getOpenAttributeValue(manyQName, element));       
    	if (many.booleanValue())
    		property.setMany(true);
    	else
    		property.setMany(false);
    	    	               
        // is key
    	// FIXME: is there not a key-type SDOX attribute??
        QName keyQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_KEY, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        Boolean isKey = new Boolean(
        		findOpenAttributeValue(keyQName, element));              	
        if (isKey.booleanValue()) {
        	property.setVisibility(VisibilityType.PRIVATE);
			Key key = new Key();
			key.setType(KeyType.PRIMARY);
			property.setKey(key);
        }	
        
        QName typeQName = element.getType();
        // if a reference to another entity
        if (typeQName.getPrefix().equals(this.appNamespaceQName.getPrefix())) {
            QName oppositeQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                    SDOXConstants.LOCAL_NAME_OPPOSITE_PROPERTY, 
                	SDOXConstants.SDOX_NAMESPACE_PREFIX);
            String oppositeName = findOpenAttributeValue(oppositeQName, element);
            if (oppositeName != null)
                property.setOpposite(oppositeName);
            QName datatypeQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                    SDOXConstants.LOCAL_NAME_DATATYPE, 
                	SDOXConstants.SDOX_NAMESPACE_PREFIX);
            Class targetDef = this.classMap.get(getOpenAttributeValue(datatypeQName, element));
            if (targetDef != null) {
                ClassRef targetClassRef = new ClassRef();
                targetClassRef.setName(targetDef.getName());
                targetClassRef.setUri(targetDef.getUri());
                property.setType(targetClassRef);                
	            property.setContainment(true);  
            }
            else {
            	throw new IllegalStateException("could not find target class from, "
            			+ datatypeQName);
            }
        }
        else { // datatype        	.
            QName datatypeQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                    SDOXConstants.LOCAL_NAME_DATATYPE, 
                	SDOXConstants.SDOX_NAMESPACE_PREFIX);
            
            DataTypeRef dataTypeRef = new DataTypeRef();
            dataTypeRef.setName(getOpenAttributeValue(datatypeQName, element));
            dataTypeRef.setUri(PlasmaConfig.getInstance().getSDO().getDefaultNamespace().getUri());
            property.setType(dataTypeRef);        	
        }
        
        QName readonlyQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_READ_ONLY, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        Boolean isReadonly = new Boolean(
        		getOpenAttributeValue(readonlyQName, element));              	
        property.setReadOnly(isReadonly);
            
        // FIXME; add isSequence and isUnique and maxlength added to plasma specific XML annotation ??
        //Integer maxLength = (Integer)property.get(PlasmaProperty.INSTANCE_PROPERTY_INT_MAXLENGTH);           
        //if (maxLength != null && maxLength.intValue() > 0)
        //	pdef.setMaxLength(maxLength.intValue());
        if (sequenceNum > -1) {
        	Sort seq = new Sort();
        	seq.setKey(String.valueOf(sequenceNum));
        	property.setSort(seq);
        }
        
    	return property;
    }
 
    private Property createDerivedPropertyOpposite(Class clss, Property sourceProperty)
    {
    	Property targetProperty = new Property();
        targetProperty.setId(UUID.randomUUID().toString());
        targetProperty.setName(sourceProperty.getOpposite()); // actual SDO type name stored as sdox name        
        Documentation documentation = createDocumentation(
        	DocumentationType.DEFINITION,
        	"private derived opposite for, "
        		+ clss.getUri() + "#" + clss.getName() + "." + sourceProperty.getName());
		targetProperty.getDocumentations().add(documentation);
        targetProperty.setVisibility(VisibilityType.PRIVATE); 
        
        targetProperty.setNullable(true);
        targetProperty.setMany(true); 
        
        targetProperty.setDerived(true);
        targetProperty.setContainment(false);
              
        targetProperty.setOpposite(sourceProperty.getName());   
        
        ClassRef targetClassRef = new ClassRef();
        targetClassRef.setName(clss.getName());
        targetClassRef.setUri(clss.getUri());
        targetProperty.setType(targetClassRef);
        
        return targetProperty;
    }
    
    /**
     * Creates non-reference property definitions. 
     * @param complexType the Schema Complex Type
     * @param attribute the Schema Attribute
     * @return the property definition
     */
    private Property buildDatatypeProperty(ComplexType complexType, Attribute attribute)
    {
    	Property property = new Property();
        property.setId(UUID.randomUUID().toString());
        // set property names and aliases
        Alias alias = new Alias();
        property.setAlias(alias);       
        alias.setLocalName(attribute.getName()); // because XML schema "projection" names could differ
        QName nameQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_NAME, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String name = getOpenAttributeValue(nameQName, attribute);       
        property.setName(name); // actual SDO type name stored as sdox name        
        QName aliasQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_ALIAS_NAME, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String physicalNameAlias = getOpenAttributeValue(aliasQName, attribute);       
        alias.setPhysicalName(physicalNameAlias);
		
        XmlProperty xmlProp = new XmlProperty();
		xmlProp.setNodeType(XmlNodeType.ATTRIBUTE);
		property.setXmlProperty(xmlProp);
        
        Documentation documentation = new Documentation();
		documentation.setType(DocumentationType.DEFINITION);
		Body body = new Body();
		body.setValue(getDocumentationContent(attribute));
		documentation.setBody(body);
		property.getDocumentations().add(documentation);

        property.setVisibility(VisibilityType.PUBLIC); // FIXME
        
        // nullable
    	if ("required".equals(attribute.getUse()))
    		property.setNullable(false);
    	else
    		property.setNullable(true);
 
    	// multiplicity
        QName manyQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_MANY, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        Boolean many = new Boolean(
        		getOpenAttributeValue(manyQName, attribute));       
    	if (many.booleanValue())
    		property.setMany(true);
    	else
    		property.setMany(false);
    	    	
    	// datatype
        QName datatypeQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_DATATYPE, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        
        DataTypeRef dataTypeRef = new DataTypeRef();
        dataTypeRef.setName(getOpenAttributeValue(datatypeQName, attribute));
        dataTypeRef.setUri(PlasmaConfig.getInstance().getSDO().getDefaultNamespace().getUri());
        property.setType(dataTypeRef);        	
               
        // is key
        QName keyQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_KEY, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
        Boolean isKey = new Boolean(
        		findOpenAttributeValue(keyQName, attribute));              	
        if (isKey.booleanValue()) {
        	
            QName keyTypeQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                    SDOXConstants.LOCAL_NAME_KEY_TYPE, 
                	SDOXConstants.SDOX_NAMESPACE_PREFIX);
            String keyType = findOpenAttributeValue(keyTypeQName, attribute);              	
        	
        	property.setVisibility(VisibilityType.PRIVATE); // FIXME: really
			Key key = new Key();
			key.setType(KeyType.valueOf(keyType.toUpperCase()));
			property.setKey(key);
        }	
        
        QName typeQName = attribute.getType();
        if (typeQName != null) { // if local restriction will not have a simple type
	        if (typeQName.getPrefix().equals(this.appNamespaceQName.getPrefix())) {
	        	SimpleType simpleType = this.simpleTypeMap.get(typeQName.getLocalPart());
	        	
	        	EnumerationConstraint constraint = new EnumerationConstraint();
	        	EnumerationRef enumRef = new EnumerationRef();
	        	enumRef.setName(simpleType.getName());
	        	enumRef.setUri(this.destNamespaceURI);   
	        	constraint.setValue(enumRef);
	        	property.setEnumerationConstraint(constraint);
	        }
        }
        else {
        	LocalSimpleType lst = attribute.getSimpleType();
        	Restriction rest = lst.getRestriction();
        	ValueConstraint constraint = new ValueConstraint();
        	property.setValueConstraint(constraint);
        	
        	for (Object obj : rest.getMinExclusivesAndMinInclusivesAndMaxExclusives()) {
        		if (obj instanceof JAXBElement<?>) {
        			JAXBElement<Facet> facet = (JAXBElement<Facet>)obj;
        			if (facet.getName().getLocalPart().equals("maxInclusive")) {
        				NumFacet numFacet = (NumFacet)facet.getValue();
        				constraint.setMaxInclusive(Integer.valueOf(numFacet.getValue()));
        			}
        			else if (facet.getName().getLocalPart().equals("minInclusive")) {
        				NumFacet numFacet = (NumFacet)facet.getValue();
        				constraint.setMinInclusive(Integer.valueOf(numFacet.getValue()));
        			}
        			else if (facet.getName().getLocalPart().equals("minExclusive")) {
        				NumFacet numFacet = (NumFacet)facet.getValue();
        				constraint.setMinExclusive(Integer.valueOf(numFacet.getValue()));
        			}
        			else if (facet.getName().getLocalPart().equals("maxExclusive")) {
        				NumFacet numFacet = (NumFacet)facet.getValue();
        				constraint.setMaxExclusive(Integer.valueOf(numFacet.getValue()));
        			}
        			else if (facet.getName().getLocalPart().equals("totalDigits")) {
        				NumFacet numFacet = (NumFacet)facet.getValue();
        				constraint.setTotalDigits(Integer.valueOf(numFacet.getValue()));
        			}
        			else if (facet.getName().getLocalPart().equals("fractionDigits")) {
        				NumFacet numFacet = (NumFacet)facet.getValue();
        				constraint.setFractionDigits(Integer.valueOf(numFacet.getValue()));
        			}
        			else if (facet.getName().getLocalPart().equals("maxLength")) {
        				NumFacet numFacet = (NumFacet)facet.getValue();
        				constraint.setMaxLength(Integer.valueOf(numFacet.getValue()));
        			}
        			else if (facet.getName().getLocalPart().equals("minLength")) {
        				NumFacet numFacet = (NumFacet)facet.getValue();
        				constraint.setMinLength(Integer.valueOf(numFacet.getValue()));
        			}
        		}
        		else if (obj instanceof Pattern) {
        			Pattern pattern = (Pattern)obj;
        			constraint.setPattern(pattern.getValue());
        		}
        	} // for
        	
        }
    	return property;
    }
    
    @SuppressWarnings("unchecked")
	private JAXBElement<Facet> findFacet(String name, List<Object> list) {
    	for (Object obj : list) {
    		if (obj instanceof JAXBElement<?>) {
    			JAXBElement<Facet> facet = (JAXBElement<Facet>)obj;
    			if (facet.getName().getLocalPart().equals(name))
    				return facet;
    		}
    	}
    	return null;
    }
    
    private Enumeration buildEnumeration(SimpleType simpleType) {
    	Enumeration enm = new Enumeration();
    	enm.setId(UUID.randomUUID().toString());
    	enm.setName(simpleType.getName());
    	enm.setUri(this.destNamespaceURI);
    	    	
        Documentation documentation = createDocumentation(
        		DocumentationType.DEFINITION,
        		getDocumentationContent(simpleType));
		enm.getDocumentations().add(documentation);
    	
    	Restriction restriction = simpleType.getRestriction();
    	if (restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().size() == 0)
			throw new IllegalStateException("expected collection values");
    	for (Object obj : restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives()) {
    		if (obj instanceof org.plasma.xml.schema.Enumeration) {
    			org.plasma.xml.schema.Enumeration schemaEnum = (org.plasma.xml.schema.Enumeration)obj;
    			EnumerationLiteral literal = new EnumerationLiteral();
    			enm.getEnumerationLiterals().add(literal);
    			literal.setName(schemaEnum.getValue());
        		literal.setId(UUID.randomUUID().toString());
    		    for (Object o2 : schemaEnum.getAnnotation().getAppinfosAndDocumentations()) {
    		    	if (o2 instanceof Appinfo) {
    		    		Appinfo appinfo = (Appinfo)o2;
    		    		literal.setValue((String)appinfo.getContent().get(0));
    		    	}
    		    	else if (o2 instanceof org.plasma.xml.schema.Documentation) {
    		    		org.plasma.xml.schema.Documentation doc = (org.plasma.xml.schema.Documentation)o2;
    		    		if (doc.getContent() != null && doc.getContent().size() > 0) {
	    		    		documentation = createDocumentation(
	    		            		DocumentationType.DEFINITION,
	    		            		(String)doc.getContent().get(0));
		    		    	literal.getDocumentations().add(documentation);
    		    		}
    		    	}
    	        	else
    	        		throw new IllegalStateException("unexpected Enumeration child class, " 
    	        			+ obj.getClass().getName());
    		    }
                Alias alias = new Alias();
                literal.setAlias(alias);       
                alias.setPhysicalName(schemaEnum.getValue()); 
    		}
        	else
        		throw new IllegalStateException("unexpected Restriction child class, " 
        			+ obj.getClass().getName());
    	}
    	return enm;
    }
    
    private Documentation createDocumentation(DocumentationType type, 
    		String content)
    {
        Documentation documentation = new Documentation();
		documentation.setType(type);
		Body body = new Body();
		body.setValue(content);
		documentation.setBody(body); 
		return documentation;
    }
    
    private String getSDOXValue(ComplexType complexType, String localName) {
        QName nameQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        	localName, SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String value = getOpenAttributeValue(nameQName, complexType);       
    	return value;
    }
    
    private String findSDOXValue(ComplexType complexType, String localName) {
        QName nameQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        	localName, SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String value = findOpenAttributeValue(nameQName, complexType);       
    	return value;
    }
    
    private String getSDOXValue(LocalElement element, String localName) {
        QName nameQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        	localName, SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String value = getOpenAttributeValue(nameQName, element);       
    	return value;
    }       
    
    private String getOpenAttributeValue(QName qname, OpenAttrs attrs) {
    	return findOpenAttributeValue(qname, attrs, false);
    }
    
    private String findOpenAttributeValue(QName qname, OpenAttrs attrs) {
    	return findOpenAttributeValue(qname, attrs, true);
    }
    
    private String findOpenAttributeValue(QName qname, OpenAttrs attrs, boolean supressError) {
    	Iterator<QName> iter = attrs.getOtherAttributes().keySet().iterator();
    	while (iter.hasNext()) {
    		QName key = iter.next();
    		if (key.equals(qname)) {
    			String result = attrs.getOtherAttributes().get(key);
    			return result;
    		}
    	}    	
    	if (!supressError)
    	    throw new IllegalArgumentException("attribute '"
    		    + qname.toString() + "' not found");
    	return null;
    }

    private QName getOpenAttributeQNameByValue(String value, OpenAttrs attrs) {
    	Iterator<QName> iter = attrs.getOtherAttributes().keySet().iterator();
    	while (iter.hasNext()) {
    		QName key = iter.next();
    		String s = attrs.getOtherAttributes().get(key);
    		if (value.equals(s)) 
    			return key;
    	}    	
    	throw new IllegalArgumentException("attribute avalue '"
    		    + value + "' not found");
    }
    
    private String getDocumentationContent(Annotated annotated)
    {
    	StringBuilder buf = new StringBuilder();
    	if (annotated != null && annotated.getAnnotation() != null)
	    	for (Object annotationObj : annotated.getAnnotation().getAppinfosAndDocumentations()) {
	    		if (annotationObj instanceof org.plasma.xml.schema.Documentation) {
	    			org.plasma.xml.schema.Documentation doc = (org.plasma.xml.schema.Documentation)annotationObj;
	    			for (Object content : doc.getContent())
	    			if (content instanceof String)
	    			    buf.append(content);
	    			else
	    				throw new IllegalStateException("unexpected content class, "
	    						+ annotationObj.getClass().getName());
	    		}
	    		else if (annotationObj instanceof Appinfo) {
	    			log.warn("ignoring app-info: " + String.valueOf(annotationObj));
	    		}
	    	}       	
    	return buf.toString();
    }
}
