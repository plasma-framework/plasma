package org.plasma.xml.schema;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassAppInfo;
import org.plasma.provisioning.DataTypeRef;
import org.plasma.provisioning.Documentation;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ModelAppInfo;
import org.plasma.provisioning.PropertyAppInfo;
import org.plasma.provisioning.ProvisioningConstants;
import org.plasma.provisioning.VisibilityType;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.provisioning.adapter.TypeAdapter;
import org.plasma.sdo.DataFlavor;
import org.plasma.sdo.DataType;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.ValueConstraint;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.helper.PlasmaXSDHelper;
import org.plasma.sdo.repository.Comment;
import org.plasma.xml.sdox.BaseDataGraphType;
import org.plasma.xml.sdox.SDOXConstants;


import commonj.sdo.Type;

/**
 * Creates an XML Schema model. 
 * @see org.plasma.query.PathNode
 * @see org.plasma.query.PathNode#getSelectClause
 * @see org.plasma.query.Select
 */
//FIXME: this class needs to work with only provisioning structures
public class SchemaModelAssembler {
	
    private static Log log = LogFactory.getLog(
    		SchemaModelAssembler.class); 
	
	private String destNamespaceURI;
	private String destNamespacePrefix;
	private Model model;
	private Schema schema;
	private boolean createNonContainmentReferenceTypes = true;
	private ModelAdapter helper;
    private Map<String, SimpleType> topLevelTypes = new HashMap<String, SimpleType>();
	
	@SuppressWarnings("unused")
	private SchemaModelAssembler() {}
    
    /**
     * Creates and XML Schema model based on the given PlasmaSDO#8482; Provisioning
     * Model. All class properties within the given model are expected to contain 
     * ordering or sequence information, such that XSD Element sequences can be
     * created with the expected ordering. 
     * 
     * @param model the PlasmaSDO#8482; Provisioning Model
     * @param destNamespaceURI the target namespace URI
     * @param destNamespacePrefix the target namespace prefix
     * @see org.plasma.provisioning.Sequence
     */
    public SchemaModelAssembler(Model model, 
    		String destNamespaceURI, String destNamespacePrefix) 
    {
		super();
		this.model = model;
		this.destNamespaceURI = destNamespaceURI;
		this.destNamespacePrefix = destNamespacePrefix;
	}
    
	public Schema getSchema() {
		construct(this.model,
			this.destNamespaceURI, 
			this.destNamespacePrefix);
		return this.schema;
	}
	
	public boolean isCreateNonContainmentReferenceTypes() {
		return createNonContainmentReferenceTypes;
	}

	public void setCreateNonContainmentReferenceTypes(
			boolean createNonContainmentReferenceTypes) {
		this.createNonContainmentReferenceTypes = createNonContainmentReferenceTypes;
	}

	private void construct(Model model, 
    		String destNamespaceURI, String destNamespacePrefix) 
    {
		if (destNamespaceURI == null || destNamespaceURI.trim().length() == 0)
			throw new IllegalArgumentException("expected 'destNamespaceURI' argument");
		if (destNamespacePrefix == null || destNamespacePrefix.trim().length() == 0)
			throw new IllegalArgumentException("expected 'destNamespacePrefix' argument");
		
		this.helper = 
			new ModelAdapter(model);
		
		this.schema = buildSchema(model); 
		
		//buildDatagraphEnvelopeType(model);
		
		ComplexType serializationBaseType = buildSerializationBaseType();
		
		TypeAdapter[] types = helper.getTypesArray();

		for (TypeAdapter adapter : types)
		{	
			if (!(adapter.getType() instanceof Class))
				continue;
			Class clss = (Class)adapter.getType();
            PlasmaType sdoType = (PlasmaType)PlasmaTypeHelper.INSTANCE.getType(
            		adapter.getUri(), adapter.getName());
                        
            ComplexType complexType = null;
            if (clss.getSuperClasses().size() == 0) {            	
            	ComplexType baseType = serializationBaseType;
        		complexType = buildComplexType(adapter, baseType);
                addDatatypeAttributesAndElements(complexType, baseType, adapter, sdoType);
            }
            else {
            	if (clss.getSuperClasses().size() > 1)
            		throw new IllegalStateException("cannot process multiple base classes for type, " 
            				+ adapter.getKey());
            	complexType = buildComplexType(adapter, clss.getSuperClasses().get(0).getName());
                addDatatypeAttributesAndElements(complexType, null, adapter, sdoType);
            }            
            
            addReferenceElements(complexType, adapter, sdoType);
		}
    }	
	
	private void addDatatypeAttributesAndElements(ComplexType complexType, 
			ComplexType baseType,
			TypeAdapter adapter, PlasmaType sdoType) {
        
		
		// add datatype attributes and elements
        for (org.plasma.provisioning.Property prop : adapter.getDeclaredPropertiesArray()) {
        	PlasmaProperty property = (PlasmaProperty)sdoType.getProperty(prop.getName());
        	if (!(prop.getType() instanceof DataTypeRef))
        		continue; // datatype prop
        	
        	// ignore properties provisioned as private 
        	//if (prop.getVisibility() != null && 
        	//	prop.getVisibility().ordinal() == VisibilityType.PRIVATE.ordinal()) {
        	//	continue;
        	//}
        	
        	//currentType = complexType;
        	//if (this.createNonContainmentReferenceTypes) {
	        //	if (property.isKey(KeyType.external) && nonContainmentReferenceType != null) {
	        //		currentType = nonContainmentReferenceType;
	        //	}
        	//}
        	// FIXME: this should be provisioned as 
        	if (property.isXMLAttribute()) { 
            	Attribute attr = buildDataAttributeModel(adapter, sdoType, prop, property, schema);
            	if (complexType.getComplexContent() == null) { // no base type
            		complexType.getAttributesAndAttributeGroups().add(attr);  
            	}
            	else {
            		complexType.getComplexContent().getExtension().getAttributesAndAttributeGroups().add(attr);
            	}
            }
        	else {
                // link the element
        		ExplicitGroup group = null;
            	if (complexType.getComplexContent() == null) { // no base type
            		group = complexType.getSequence();
                    if (group == null) {
                    	group = new ExplicitGroup();
                    	complexType.setSequence(group);
                    }
            	}
            	else {
            		group = complexType.getComplexContent().getExtension().getSequence();
                    if (group == null) {
                    	group = new ExplicitGroup();
                    	complexType.getComplexContent().getExtension().setSequence(group);
                    }
            	}                   
                
                Element element = buildDataElementModel(adapter, sdoType, prop, property, schema);
                group.getElementsAndGroupsAndAlls().add(element);
        	}
        }		
	}
	
	private void addReferenceElements(ComplexType complexType, TypeAdapter adapter, PlasmaType sdoType) {
        // add reference elements
        for (org.plasma.provisioning.Property prop : adapter.getDeclaredPropertiesArray()) {
        	PlasmaProperty property = (PlasmaProperty)sdoType.getProperty(prop.getName());
        	if (prop.getType() instanceof DataTypeRef)
        		continue; // datatype prop
        	
        	// ignore properties provisioned as private 
        	if (prop.getVisibility() != null && 
        		prop.getVisibility().ordinal() == VisibilityType.PRIVATE.ordinal()) {
        		continue;
        	}
        	
    		ExplicitGroup group = null;
        	if (complexType.getComplexContent() == null) { // no base type
        		group = complexType.getSequence();
                if (group == null) {
                	group = new ExplicitGroup();
                    complexType.setSequence(group);
                }
        	}
        	else {
        		group = complexType.getComplexContent().getExtension().getSequence();
                if (group == null) {
                	group = new ExplicitGroup();
                	complexType.getComplexContent().getExtension().setSequence(group);
                }
        	}                   
            
            Element element = buildReferenceElementModel(adapter,
            		sdoType, prop, property, schema);
            
            // link the element
            group.getElementsAndGroupsAndAlls().add(element);                    
        }
	}

	private Schema buildSchema(Model model) {
		Schema schema = new Schema();
        schema.setId(model.getName());
        schema.setTargetNamespace(this.destNamespaceURI);
        
        Annotation annotation = new Annotation();
    	schema.getIncludesAndImportsAndRedefines().add(annotation);
        if (model.getDocumentations() != null && model.getDocumentations().size() > 0) {
            org.plasma.xml.schema.Documentation docum = new org.plasma.xml.schema.Documentation();
            docum.getContent().add(model.getDocumentations().get(0).getBody().getValue());
            annotation.getAppinfosAndDocumentations().add(docum);
        }
        
        ModelAppInfo modelAppInfo = new ModelAppInfo();
        modelAppInfo.setId(model.getId());
        modelAppInfo.setName(model.getName());
        modelAppInfo.setUri(model.getUri());
        modelAppInfo.setDerivation(model.getDerivation());
        		
        Appinfo appinfo = new Appinfo();
        appinfo.getContent().add(modelAppInfo);
        annotation.getAppinfosAndDocumentations().add(appinfo);
        
        QName plasmaNamespace = new QName(ProvisioningConstants.PROVISIONING_NAMESPACE_URI, 
        		"plasma");
        schema.getOtherAttributes().put(plasmaNamespace, 
        		ProvisioningConstants.PROVISIONING_NAMESPACE_URI);
        
        QName appNamespace = new QName(schema.getTargetNamespace(), this.destNamespacePrefix, 
        		this.destNamespacePrefix); // use query name as namespace-prefix
        schema.getOtherAttributes().put(appNamespace, schema.getTargetNamespace());

        QName sdoxNamespace = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        		SDOXConstants.SDOX_NAMESPACE_PREFIX, SDOXConstants.SDOX_NAMESPACE_PREFIX);
        schema.getOtherAttributes().put(sdoxNamespace, 
        		SDOXConstants.SDOX_NAMESPACE_URI);
        
        // for xs:documentation xml:lang attrib, etc 
        QName xmlNamespace = new QName(SchemaConstants.XML_NAMESPACE_URI, 
        		"xml", "xml");
        schema.getOtherAttributes().put(xmlNamespace, 
        		SchemaConstants.XML_NAMESPACE_URI);
		return schema;
	}
	
	private ComplexType buildDatagraphEnvelopeType(Model model)
	{
		String name = model.getRootClass().getName() + "Datagraph";
		
        Element topLevelElement = new Element();
        topLevelElement.setName(name);
        QName topLevelType = new QName(schema.getTargetNamespace(), 
        		name, destNamespacePrefix); 
        topLevelElement.setType(topLevelType);
        schema.getSimpleTypesAndComplexTypesAndGroups().add(topLevelElement);
        
        // add a complex type
        ComplexType complexType = new ComplexType();
        complexType.setName(name);
        addAnnotation("SDO Data Graph root type",
        	complexType);
        schema.getSimpleTypesAndComplexTypesAndGroups().add(complexType);

        ComplexContent complexContent = new ComplexContent();
        complexType.setComplexContent(complexContent);	        
        ExtensionType extension = new ExtensionType(); 
        complexContent.setExtension(extension);
        QName base = new QName("commonj.sdo", BaseDataGraphType.class.getSimpleName(), 
        		"sdo");
        extension.setBase(base);

        Element element = new Element();
        element.setName(model.getRootClass().getName());        
        
        QName elementType = new QName(schema.getTargetNamespace(),
        		model.getRootClass().getName());       
        
        element.setType(elementType);
        element.setMinOccurs(new BigInteger("1"));
        element.setMaxOccurs("1");
 
		ExplicitGroup group = new ExplicitGroup();
    	complexType.getComplexContent().getExtension().setSequence(group);
        group.getElementsAndGroupsAndAlls().add(element);        
        
        return complexType;
	}
	
	private ComplexType buildSerializationBaseType()
	{
        
		// add a top level element referencing the below
        // complex type to keep JAXB happy
        Element topLevelElement = new Element();
        topLevelElement.setName(SchemaUtil.getSerializationBaseTypeName());
        QName topLevelType = new QName(schema.getTargetNamespace(), 
        		SchemaUtil.getSerializationBaseTypeName(), destNamespacePrefix); 
        topLevelElement.setType(topLevelType);
        schema.getSimpleTypesAndComplexTypesAndGroups().add(topLevelElement);
        
        // add a complex type
        ComplexType complexType = new ComplexType();
        complexType.setName(SchemaUtil.getSerializationBaseTypeName());
        addAnnotation("default XML serialization common base type used to "
        	+ "generalize any type within a Data Graph for use in an XML "
        	+ "document as either a containment or non-containment reference",
        	complexType);
        schema.getSimpleTypesAndComplexTypesAndGroups().add(complexType);

    	Attribute attr = new Attribute();
    	complexType.getAttributesAndAttributeGroups().add(attr);  
        attr.setName(SchemaUtil.getSerializationAttributeName());
        attr.setUse("required");
        QName attrType = new QName(SchemaConstants.XMLSCHEMA_NAMESPACE_URI,
                getSchemaDatatype(DataType.String));                    
        attr.setType(attrType);
       
        return complexType;
	}

	private ComplexType buildComplexType(TypeAdapter type)
	{
		return buildComplexType(type, (String)null);
	}
	
	private ComplexType buildComplexType(TypeAdapter type, ComplexType baseType)
	{
		return buildComplexType(type, baseType != null ? baseType.getName() : null);
	}
	
	private ComplexType buildComplexType(TypeAdapter type, String baseType)
	{
        // add a top level element referencing the below
        // complex type to keep JAXB happy
        Element topLevelElement = new Element();
        topLevelElement.setName(type.getLocalName());
        QName topLevelType = new QName(schema.getTargetNamespace(), 
        		type.getName(), destNamespacePrefix); // use query name as namespace-prefix
        topLevelElement.setType(topLevelType);
        schema.getSimpleTypesAndComplexTypesAndGroups().add(topLevelElement);
        
        // add a complex type
        ComplexType complexType = new ComplexType();
        complexType.setName(type.getLocalName());
        List<Documentation> docs = type.getDocumentation();
        for (Documentation doc : docs) {
            addAnnotation(doc, complexType);
        }
        schema.getSimpleTypesAndComplexTypesAndGroups().add(complexType);
        
        // add sdox open attributes
        addSDOXTypeAttributes(type, complexType); 
        
        if (baseType != null) {
	        ComplexContent complexContent = new ComplexContent();
	        complexType.setComplexContent(complexContent);	        
	        ExtensionType extension = new ExtensionType(); 
	        complexContent.setExtension(extension);
	        QName base = new QName(this.destNamespaceURI, baseType, 
	        		this.destNamespacePrefix);
	        extension.setBase(base);
        }

        Class clss = (Class)type.getType();
        ClassAppInfo classAppInfo = new ClassAppInfo();
        classAppInfo.setId(clss.getId());
        classAppInfo.setName(clss.getName());
        classAppInfo.setUri(clss.getUri());
        classAppInfo.setAlias(clss.getAlias());
        classAppInfo.setDerivation(clss.getDerivation());
        		
        Appinfo appinfo = new Appinfo();
        appinfo.getContent().add(classAppInfo);
        Annotation annotation = complexType.getAnnotation();
        if (annotation == null) {
            annotation = new Annotation();
            complexType.setAnnotation(annotation);
        }
        annotation.getAppinfosAndDocumentations().add(appinfo);
        
        return complexType;
	}

	private ComplexType buildNonContainmentReferenceComplexType(TypeAdapter type)
	{	
		String name = getNonContainmentReferenceComplexTypeName(type);
        // add a top level element referencing the below
        // complex type to keep JAXB happy
        Element topLevelElement = new Element();
        topLevelElement.setName(name);
        QName topLevelType = new QName(schema.getTargetNamespace(), 
        		name, destNamespacePrefix); // use query name as namespace-prefix
        topLevelElement.setType(topLevelType);
        schema.getSimpleTypesAndComplexTypesAndGroups().add(topLevelElement);
        
        // add a complex type
        ComplexType complexType = new ComplexType();
        complexType.setName(name);
        addAnnotation("non-containment reference type for,  "
        		+ type.getLocalName(),
        		complexType);
        schema.getSimpleTypesAndComplexTypesAndGroups().add(complexType);
        
        // add sdox open attributes
        addSDOXTypeAttributes(type, complexType); 
		
        return complexType;
	}
	
	private String getNonContainmentReferenceComplexTypeName(TypeAdapter type) {
		
	    return SchemaUtil.getNonContainmentReferenceName(type);	
	}
	
    private Element buildReferenceElementModel(TypeAdapter adapter, PlasmaType type, 
    		org.plasma.provisioning.Property prop, PlasmaProperty property, Schema schema) {
        Element element = new Element();
        element.setName(PlasmaXSDHelper.INSTANCE.getLocalName(property));        
        
        // build the element type based on opposite type
        PlasmaType oppositeType = (PlasmaType)property.getType();
        QName elementType = new QName(schema.getTargetNamespace(),
        		PlasmaXSDHelper.INSTANCE.getLocalName(oppositeType));
        // set every reference to the serial base type
        // use XSI type in the XML to specific subtypes
        elementType = new QName(schema.getTargetNamespace(),
        		SchemaUtil.getSerializationBaseTypeName());       
        
        element.setType(elementType);
        
        if (property.isNullable())
            element.setMinOccurs(new BigInteger("0"));
        else
            element.setMinOccurs(new BigInteger("1"));
        
        if (!property.isMany())
            element.setMaxOccurs("1");
        else
            element.setMaxOccurs("unbounded");

        if (property.getDescription() != null && property.getDescription().size() > 0) {
            addAnnotation(property.getDescription(),
            		element);
        }
        
        // add sdox open attributes
        addSDOXPropertyAttributes(property, element);

        commonj.sdo.Property oppositeProp = property.getOpposite();
        if (oppositeProp != null) {
	        QName qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
	        		SDOXConstants.LOCAL_NAME_OPPOSITE_PROPERTY, 
	        		SDOXConstants.SDOX_NAMESPACE_PREFIX);
	        element.getOtherAttributes().put(qname, 
	        		PlasmaXSDHelper.INSTANCE.getLocalName(oppositeProp));
	        qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
	        		SDOXConstants.LOCAL_NAME_PROPERTY_TYPE, 
	        		SDOXConstants.SDOX_NAMESPACE_PREFIX);
        }
        
    	return element;
    }
 
    private Element buildDataElementModel(TypeAdapter adapter, PlasmaType type, 
    		org.plasma.provisioning.Property prop, PlasmaProperty property, Schema schema) {
        Element element = new Element();
        element.setName(PlasmaXSDHelper.INSTANCE.getLocalName(property));
        if (property.getRestriction() == null) {
            QName elementType = new QName(SchemaConstants.XMLSCHEMA_NAMESPACE_URI,
                    getSchemaDatatype(DataType.valueOf(
                    		PlasmaXSDHelper.INSTANCE.getLocalName(property.getType()))));                    
            element.setType(elementType);
        }
        else {
            //LocalSimpleType simpleType = buildLocalSimpleTypeRestriction(property);
            //element.setSimpleType(simpleType);
            SimpleType simpleType = buildTopLevelSimpleTypeEnumerationRestriction(property);
            QName topLevelType = new QName(schema.getTargetNamespace(), 
            		simpleType.getName(), destNamespacePrefix); 
            element.setType(topLevelType);
        }    
        
        if (property.isNullable())
            element.setMinOccurs(new BigInteger("0"));
        else
            element.setMinOccurs(new BigInteger("1"));        
        
        if (!property.isMany())
            element.setMaxOccurs("1");
        else
            element.setMaxOccurs("unbounded");

        if (property.getDescription() != null && property.getDescription().size() > 0) {
            addAnnotation(property.getDescription(),
            		element);
        }
        
        // add sdox open attributes
        addSDOXPropertyAttributes(property, element);

    	return element;
    }
    
    private Attribute buildDataAttributeModel(TypeAdapter adapter, PlasmaType type, 
    		org.plasma.provisioning.Property prop, PlasmaProperty property, Schema schema) 
    {
    	Attribute attr = new Attribute();
        attr.setName(PlasmaXSDHelper.INSTANCE.getLocalName(property));
        
        if (property.getRestriction() == null) {
        	// value constraint below will specify XSD data type
        	if (property.getValueConstraint() == null) {
                QName attrType = new QName(SchemaConstants.XMLSCHEMA_NAMESPACE_URI,
                    getSchemaDatatype(DataType.valueOf(
                    		PlasmaXSDHelper.INSTANCE.getLocalName(
                    				property.getType()))));                    
                attr.setType(attrType);
        	}
        }
        else { // has an enum restriction
            SimpleType simpleType = buildTopLevelSimpleTypeEnumerationRestriction(property);
            QName topLevelType = new QName(schema.getTargetNamespace(), 
            		simpleType.getName(), destNamespacePrefix); 
            attr.setType(topLevelType);
        } 
        
        if (property.getValueConstraint() != null) {
        	if (property.getRestriction() == null) {
        	    ValueConstraint vc = property.getValueConstraint();
        	    LocalSimpleType simpleType = buildLocalSimpleTypeValueRestriction(property, vc);
        	    attr.setSimpleType(simpleType);
        	}
        	else
        		log.warn("both value constraint and enumeration constraint found for property, "
        			+ type.getURI() + "#" + type.getName() + "." + property.getName() 
        			+ " - ignoring value constraint");
        }
        
        if (!prop.isNullable())
            attr.setUse("required");
        else
            attr.setUse("optional");
        
        if (property.getDescription() != null)
            addAnnotation(property.getDescription(),
            		attr);
         
        // add sdox open attributes
        addSDOXPropertyAttributes(property, attr);
        
        PropertyAppInfo attribAppInfo = new PropertyAppInfo();
        attribAppInfo.setId(prop.getId());
        attribAppInfo.setName(prop.getName());
        attribAppInfo.setAlias(prop.getAlias());
        attribAppInfo.setDerivation(prop.getDerivation());
        attribAppInfo.setKey(prop.getKey());
        attribAppInfo.setConcurrent(prop.getConcurrent());
        attribAppInfo.setUniqueConstraint(prop.getUniqueConstraint());
        attribAppInfo.setValueConstraint(prop.getValueConstraint());
        attribAppInfo.setEnumerationConstraint(prop.getEnumerationConstraint());
        attribAppInfo.setValueSetConstraint(prop.getValueSetConstraint());
        attribAppInfo.setSort(prop.getSort());
        attribAppInfo.setXmlProperty(prop.getXmlProperty());
        		
        Appinfo appinfo = new Appinfo();
        appinfo.getContent().add(attribAppInfo);
        Annotation annotation = attr.getAnnotation();
        if (annotation == null) {
            annotation = new Annotation();
            attr.setAnnotation(annotation);
        }
        annotation.getAppinfosAndDocumentations().add(appinfo);
    	
        return attr;
    }

    /**
     * Adds SDO XML (sdox) attributes for SDO Type to the given superclass 
     * properties to the given OpenAttrs superclass. 
     * @param type the SDO Type
     * @param annotated
     */
    private void addSDOXTypeAttributes(TypeAdapter type, 
    		OpenAttrs openAttributes) 
    {
    	QName qname = null;
    	if (type.getPhysicalName() != null) {
	    	qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
	            SDOXConstants.LOCAL_NAME_ALIAS_NAME, 
	        	SDOXConstants.SDOX_NAMESPACE_PREFIX);
	        openAttributes.getOtherAttributes().put(qname, 
	        		type.getPhysicalName()); 
    	}
        
    	qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
                SDOXConstants.LOCAL_NAME_NAME, 
            	SDOXConstants.SDOX_NAMESPACE_PREFIX);
            openAttributes.getOtherAttributes().put(qname, 
            		type.getName());    	
    }
    
    /**
     * Adds SDO XML (sdox) attributes common to both reference and data
     * properties to the given OpenAttrs superclass. 
     * @param property the SDO property
     * @param openAttributes
     */
    private void addSDOXPropertyAttributes(PlasmaProperty property, 
    		OpenAttrs openAttributes) {
    	QName qname = null;
        // name
        qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        		SDOXConstants.LOCAL_NAME_NAME, 
        		SDOXConstants.SDOX_NAMESPACE_PREFIX);
        openAttributes.getOtherAttributes().put(qname, 
        		property.getName());
    	
    	if (!property.getType().isDataType()) {
            qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        		SDOXConstants.LOCAL_NAME_PROPERTY_TYPE, 
        		SDOXConstants.SDOX_NAMESPACE_PREFIX);
            openAttributes.getOtherAttributes().put(qname, 
            		destNamespacePrefix + ":" 
            		+ PlasmaXSDHelper.INSTANCE.getLocalName(property.getType()));
    	}
    	
    	// alias
    	if (property.getAliasNames() != null && property.getAliasNames().size() > 0) {
            qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        		SDOXConstants.LOCAL_NAME_ALIAS_NAME, 
        		SDOXConstants.SDOX_NAMESPACE_PREFIX);
            openAttributes.getOtherAttributes().put(qname, property.getAliasNames().get(0));
    	}
    	else {
    		if (!property.isMany()) {
    			log.warn("no aliases found for singular property "
    					+ property.getContainingType().getURI() + "#"
    					+ property.getContainingType().getName() + "."
    					+ property.getName());
    		}
    		
    	}
    	
        qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        		SDOXConstants.LOCAL_NAME_DATATYPE, 
        		SDOXConstants.SDOX_NAMESPACE_PREFIX);
        if (property.getType().isDataType())
        	openAttributes.getOtherAttributes().put(qname, 
        			PlasmaXSDHelper.INSTANCE.getLocalName(property.getType()));
        else
        	openAttributes.getOtherAttributes().put(qname, 
            	this.destNamespaceURI 
            	+ "#" + PlasmaXSDHelper.INSTANCE.getLocalName(property.getType()));

    	if (property.isKey()) {
    		
            qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
            		SDOXConstants.LOCAL_NAME_KEY, 
            		SDOXConstants.SDOX_NAMESPACE_PREFIX);
            openAttributes.getOtherAttributes().put(qname, 
            		"true");
            qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
            		SDOXConstants.LOCAL_NAME_KEY_TYPE, 
            		SDOXConstants.SDOX_NAMESPACE_PREFIX);
            openAttributes.getOtherAttributes().put(qname, 
            		property.getKey().getType().name().toLowerCase());
    	}
        
        // many
        qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        		SDOXConstants.LOCAL_NAME_MANY, 
        		SDOXConstants.SDOX_NAMESPACE_PREFIX);
        openAttributes.getOtherAttributes().put(qname, 
        		String.valueOf(property.isMany()));
        // readonly
        qname = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        		SDOXConstants.LOCAL_NAME_READ_ONLY, 
        		SDOXConstants.SDOX_NAMESPACE_PREFIX);
        openAttributes.getOtherAttributes().put(qname, 
        		String.valueOf(property.isReadOnly()));
    }
    
    private SimpleType buildTopLevelSimpleTypeEnumerationRestriction(PlasmaProperty property) {
    	SimpleType simpleType = topLevelTypes.get(property.getRestriction().getName());
    	if (simpleType == null) {
    		simpleType = new SimpleType();
    		org.plasma.sdo.repository.Enumeration enumeration = property.getRestriction();
            simpleType.setName(enumeration.getName());
            Restriction restriction = buildStringEnumerationRestriction(property);        
            simpleType.setRestriction(restriction);
            
            Annotation annotation = addAnnotation(property.getRestriction().getComments(),
            		simpleType);
            
        	// add the logical enum value as an XSD appinfo
            Appinfo appinfo = new Appinfo();
            appinfo.getContent().add(enumeration.getName());
            annotation.getAppinfosAndDocumentations().add(appinfo);
            
            
            
            topLevelTypes.put(property.getRestriction().getName(), simpleType);
            this.schema.getSimpleTypesAndComplexTypesAndGroups().add(simpleType);
    	}
        return simpleType;
    }

    private LocalSimpleType buildLocalSimpleTypeEnumerationRestriction(PlasmaProperty property) {
        LocalSimpleType simpleType = new LocalSimpleType();
        Restriction restriction = buildStringEnumerationRestriction(property);        
        simpleType.setRestriction(restriction);
        return simpleType;
    }

    private LocalSimpleType buildLocalSimpleTypeValueRestriction(PlasmaProperty property,
    		ValueConstraint valueConstrint) {
        LocalSimpleType simpleType = new LocalSimpleType();
        
        Restriction restriction = null;
        DataFlavor flavor = property.getDataFlavor();
        if (flavor.ordinal() == DataFlavor.string.ordinal()) {
            restriction = buildStringValueRestriction(property, valueConstrint);        
        }
        else if (flavor.ordinal() == DataFlavor.integral.ordinal()) {
            restriction = buildNumericValueRestriction(property, valueConstrint);        
        }
        else
        	throw new IllegalStateException("value constraint found for " 
        		+ "unsupported data flavor '" 
        		+ flavor.name() + "' on property "
        		+ property.getContainingType().getURI() + "#" 
				+ property.getContainingType().getName() + "." + property.getName());
    
        simpleType.setRestriction(restriction);
        return simpleType;
    }

    private Restriction buildNumericValueRestriction(PlasmaProperty property,
    		ValueConstraint valueConstrint)
    {
        Restriction restriction = new Restriction();
        QName stringType = new QName(SchemaConstants.XMLSCHEMA_NAMESPACE_URI,
                "string");                    
        restriction.setBase(stringType); 
        
        // NOTE: having primitive types here introduces ambiguity, i.e. is null intended??
        if (valueConstrint.getMaxInclusive() != null) {
        	JAXBElement<Facet> maxIncl = createNumberFacet("maxInclusive", 
        			valueConstrint.getMaxInclusive());
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(maxIncl);
        }
        else if (valueConstrint.getMinInclusive() != null) {
        	JAXBElement<Facet> minIncl = createNumberFacet("minInclusive", 
        			valueConstrint.getMinInclusive());
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(minIncl);
        }
        else if (valueConstrint.getMinExclusive() != null) {
        	JAXBElement<Facet> minExcl = createNumberFacet("minExclusive", 
        			valueConstrint.getMinExclusive());
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(minExcl);
        }
        else if (valueConstrint.getMaxExclusive() != null) {
        	JAXBElement<Facet> maxExcl = createNumberFacet("maxExclusive", 
        			valueConstrint.getMaxExclusive());
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(maxExcl);
        }
        
        if (valueConstrint.getTotalDigits() != null) {
        	JAXBElement<Facet> totDig = createNumberFacet("totalDigits", 
        			valueConstrint.getTotalDigits());
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(totDig);
        }
        if (valueConstrint.getFractionDigits() != null) {
        	JAXBElement<Facet> fracDig = createNumberFacet("fractionDigits", 
        			valueConstrint.getFractionDigits());
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(fracDig);
        }
        
        return restriction;
    }
    
    private Restriction buildStringValueRestriction(PlasmaProperty property,
    		ValueConstraint valueConstrint)
    {
        Restriction restriction = new Restriction();
        QName stringType = new QName(SchemaConstants.XMLSCHEMA_NAMESPACE_URI,
                "string");                    
        restriction.setBase(stringType); 
        if (valueConstrint.getMaxLength() != null) {
        	JAXBElement<Facet> maxLen = createNumberFacet("maxLength", 
        			valueConstrint.getMaxLength());
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(maxLen);
        }
        else if (valueConstrint.getMinLength() != null) {
        	JAXBElement<Facet> minLen = createNumberFacet("minLength", 
        			valueConstrint.getMinLength());
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(minLen);
        }
        else if (valueConstrint.getPattern() != null) {
        	Pattern pattern = new Pattern();
        	pattern.setValue(valueConstrint.getPattern());
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(pattern);
        }
        
        return restriction;
    }
    
    private JAXBElement<Facet> createNumberFacet(String name, int value)
    {
    	NumFacet facet = new NumFacet();
    	facet.setValue(String.valueOf(value));
        QName qname = new QName(SchemaConstants.XMLSCHEMA_NAMESPACE_URI, name);
        return new JAXBElement<Facet>(qname, Facet.class, null, facet);       	        	
    }
    
    private JAXBElement<Facet> createNumberFacet(String name, String value)
    {
    	NumFacet facet = new NumFacet();
    	facet.setValue(String.valueOf(value));
        QName qname = new QName(SchemaConstants.XMLSCHEMA_NAMESPACE_URI, name);
        return new JAXBElement<Facet>(qname, Facet.class, null, facet);       	        	
    }
    
    private Restriction buildStringEnumerationRestriction(PlasmaProperty property)
    {
        Restriction restriction = new Restriction();
        QName stringType = new QName(SchemaConstants.XMLSCHEMA_NAMESPACE_URI,
                "string");                    
        restriction.setBase(stringType);        

        org.plasma.sdo.repository.Enumeration propertyRestriction = property.getRestriction();
        
        for (org.plasma.sdo.repository.EnumerationLiteral literal : propertyRestriction.getOwnedLiteral()) {
            Enumeration enumeration = new Enumeration();
            enumeration.setValue(literal.getPhysicalName());
            
            Annotation annotation = addAnnotation(literal.getComments(), enumeration);
            
        	// add the logical enum value as an XSD appinfo
            Appinfo appinfo = new Appinfo();
            appinfo.getContent().add(literal.getName());
            annotation.getAppinfosAndDocumentations().add(appinfo);
            
            restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().add(enumeration);
        }
        
        return restriction;
    }
    
    private Annotation addAnnotation(Documentation doc, Annotated annotated)
    {
        Annotation annotation = new Annotation();
        annotated.setAnnotation(annotation);
        org.plasma.xml.schema.Documentation docum = new org.plasma.xml.schema.Documentation();
        // fails with below even though XML namespace declared
        // "org.xml.sax.SAXParseException: src-resolve: Cannot resolve the name 'xml:lang' to a(n) 'attribute declaration component'"
        //docum.setLang("en");
        docum.getContent().add(""+doc.getBody().getValue());
        annotation.getAppinfosAndDocumentations().add(docum);
        
     	// add appinfo
        //Appinfo appinfo = new Appinfo();
        //appinfo.getContent().add("");
        //annotation.getAppinfosAndDocumentations().add(appinfo);
        
    	return annotation;
    }
    
    private Annotation addAnnotation(List<Comment> description, Annotated annotated)
    {
        Annotation annotation = new Annotation();
        annotated.setAnnotation(annotation);
        org.plasma.xml.schema.Documentation docum = new org.plasma.xml.schema.Documentation();
        // fails with below even though XML namespace declared
        // "org.xml.sax.SAXParseException: src-resolve: Cannot resolve the name 'xml:lang' to a(n) 'attribute declaration component'"
        //docum.setLang("en");
        for (Comment comment : description)
            docum.getContent().add(""+comment.getBody());
        annotation.getAppinfosAndDocumentations().add(docum);
        
     	// add appinfo
        //Appinfo appinfo = new Appinfo();
        //appinfo.getContent().add("");
        //annotation.getAppinfosAndDocumentations().add(appinfo);
        
    	return annotation;
    }
    
    private Annotation addAnnotation(String content, Annotated annotated)
    {
        Annotation annotation = new Annotation();
        annotated.setAnnotation(annotation);
        org.plasma.xml.schema.Documentation docum = new org.plasma.xml.schema.Documentation();
        // fails with below even though XML namespace declared
        // "org.xml.sax.SAXParseException: src-resolve: Cannot resolve the name 'xml:lang' to a(n) 'attribute declaration component'"
        //docum.setLang("en");
        docum.getContent().add(content);
        annotation.getAppinfosAndDocumentations().add(docum);
    	return annotation;
    }
    
    private String getSchemaDatatype(DataType datatype) {
        switch (datatype) {
        case Boolean:   return "boolean";
        case Byte:      return "byte";
        case Bytes:     return "hexBinary";
        case Character: return "string";
        case DateTime:  return "dateTime";
        case Date:      return "date";
        case Day:       return "gDay";
        case Decimal:   return "decimal";
        case Duration:  return "duration";
        case Float:     return "float";
        case Double:    return "double";
        case Int:       return "int";
        case Integer:   return "integer";
        case Long:      return "long";
        case Month:     return "gMonth";
        case MonthDay:  return "gMonthDay";
        case Short:     return "short";
        case String:    return "string";
        case Time:      return "dateTime";
        case URI:       return "anyURI";
        case Year:      return "gYear";
        case YearMonth: return "gYearMonth";
        case YearMonthDay: return "date";
        case Object:	return "anySimpleType";
        default:
            throw new IllegalArgumentException("unknown datatype, "
                    + datatype.toString());
        }
    }
 
    private List<String> sort(List<String> list) {
    	String[] values = new String[list.size()];
    	list.toArray(values);
    	Arrays.sort(values);
    	List<String> result = new ArrayList<String>(list.size());
    	for (String s : values)
    		result.add(s);
    	return result;
    }
    
/*
SDO Type		XSD Type
Boolean			boolean
Byte			byte
Bytes			hexBinary
Character		string
DataObject		anyType
Date 			dateTime 
DateTime		dateTime
Day				gDay
Decimal			decimal
Double			double
Duration		duration
Float			float
Int				int
Integer			integer
Long			long
Month			gMonth
MonthDay		gMonthDay
Object			anySimpleType
Short			short
String			string
Strings			string
Time			time
Year			gYear
YearMonth		gYearMonth
YearMonthDay	date
URI				anyURI
    
 */
    
    
/*
XSD Simple Type	 	/ SDO Type
anySimpleType			Object
anyType					DataObject
anyURI					URI   (override with sdox:propertyType)
base64Binary			Bytes
boolean					Boolean
byte					Byte
date					YearMonthDay
dateTime				DateTime
decimal					Decimal
double					Double
duration				Duration
ENTITIES				Strings
ENTITY					String
float					Float
gDay					Day
gMonth					Month
gMonthDay				MonthDay
gYear					Year
gYearMonth				YearMonth
hexBinary				Bytes
ID						String (signifies the field is a sdo:key field)
IDREF					String   (override with sdox: propertyType)
IDREFS					Strings  (override with sdox: propertyType) 
int						Int
integer					Integer
language				String
long					Long
Name					String
NCName					String
negativeInteger			Integer
NMTOKEN					String
NMTOKENS				Strings
nonNegativeInteger		Integer
nonPositiveInteger		Integer
normalizedString		String
NOTATION				String
positiveInteger			Integer
QName					URI 
short					Short
string					String
time					Time
token					String
unsignedByte			UnsignedByte
unsignedInt				UnsignedInt
unsignedLong			UnsignedLong
unsignedShort			UnsignedShort
 
 */
}
