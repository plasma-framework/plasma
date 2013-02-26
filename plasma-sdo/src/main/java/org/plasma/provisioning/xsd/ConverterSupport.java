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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.ElementNSImpl;
import org.plasma.provisioning.Body;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.Documentation;
import org.plasma.provisioning.DocumentationType;
import org.plasma.provisioning.Enumeration;
import org.plasma.provisioning.EnumerationLiteral;
import org.plasma.provisioning.NameUtils;
import org.plasma.provisioning.Property;
import org.plasma.sdo.DataType;
import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Annotated;
import org.plasma.xml.schema.Annotation;
import org.plasma.xml.schema.Appinfo;
import org.plasma.xml.schema.AttributeGroup;
import org.plasma.xml.schema.ComplexType;
import org.plasma.xml.schema.Element;
import org.plasma.xml.schema.LocalElement;
import org.plasma.xml.schema.OpenAttrs;
import org.plasma.xml.schema.Restriction;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SimpleType;
import org.plasma.xml.schema.XSDBuiltInType;
import org.plasma.xml.sdox.SDOXConstants;

public class ConverterSupport {
	   private static Log log = LogFactory.getLog(
			   ConverterSupport.class); 
    protected String destNamespaceURI;
    protected String destNamespacePrefix;
    protected Map<String, Class> classQualifiedNameMap = new HashMap<String, Class>();
    protected Map<String, Class> classLocalNameMap = new HashMap<String, Class>();
    protected Map<Class, Map<String, Property>> classPropertyMap = new HashMap<Class, Map<String, Property>>();
    protected Map<Class, HashSet<Class>> subclassMap = new HashMap<Class, HashSet<Class>>();
    protected Schema schema;
    /** maps top-level complex type names to complex type structures */
    protected Map<String, ComplexType> complexTypeMap = new HashMap<String, ComplexType>();
    /** maps top-level element names to element structures */
    protected Map<String, Element> elementMap = new HashMap<String, Element>();
    /** maps top-level simple type names to simple type structures */
    protected Map<String, SimpleType> simpleTypeMap = new HashMap<String, SimpleType>();
    /** maps top-level attribute group names to attribute group structures */
    protected Map<String, AttributeGroup> attributeGroupMap = new HashMap<String, AttributeGroup>();
	
	@SuppressWarnings("unused")
    private ConverterSupport() {}
    
    public ConverterSupport(Schema schema, 
    	String destNamespaceURI,
    	String destNamespacePrefix) {
		super();
		this.schema = schema;
		this.destNamespaceURI = destNamespaceURI;
		this.destNamespacePrefix = destNamespacePrefix;
        if (schema.getTargetNamespace() == null) 
        	throw new IllegalArgumentException("given schema has no target namespace");
	}
    
    public String getDestNamespaceURI() {
		return destNamespaceURI;
	}

	public String getDestNamespacePrefix() {
		return destNamespacePrefix;
	}

	public Map<String, Class> getClassQualifiedNameMap() {
		return classQualifiedNameMap;
	}

	public Map<String, Class> getClassLocalNameMap() {
		return classLocalNameMap;
	}

	public Map<Class, Map<String, Property>> getClassPropertyMap() {
		return classPropertyMap;
	}

	public Map<Class, HashSet<Class>> getSubclassMap() {
		return subclassMap;
	}

	public Schema getSchema() {
		return schema;
	}

	public Map<String, ComplexType> getComplexTypeMap() {
		return complexTypeMap;
	}

	public Map<String, Element> getElementMap() {
		return elementMap;
	}

	public Map<String, SimpleType> getSimpleTypeMap() {
		return simpleTypeMap;
	}

	public Map<String, AttributeGroup> getAttributeGroupMap() {
		return attributeGroupMap;
	}

	public String formatLocalClassName(String localName) {
    	String result = localName;
    	result = NameUtils.firstToUpperCase(result);
    	return result;
    }

    public String formatLocalPropertyName(String localName) {
    	String result = localName;
    	result = NameUtils.firstToLowerCase(result);
    	return result;
    }
    
    public boolean logicalNameConflict(Class clss, String name)
    {
        Map<String, Property> existingProps = this.classPropertyMap.get(clss);
        if (existingProps != null) 
	        return existingProps.get(name) != null;
     
        return false;
    }
    
    public String buildLogicalPropertyName(Class clss, String name)
    {
    	String logicalName = name;
        Map<String, Property> existingProps = this.getAllExistingProps(clss);
        Property existing = existingProps.get(logicalName);
        if (existing != null) {
        	int count = 1;
        	while (existing != null) {
        		int underscore = logicalName.lastIndexOf("_");
        		if (underscore > 0) {
        			logicalName = logicalName.substring(0, underscore);
        		}
        		logicalName = logicalName + "_" + String.valueOf(count);
        	    log.warn("detected name colision for property '"
	        		    + clss.getName() + "." + existing.getName() 
	        		    + "' - using synthetic name '"
	        		    + logicalName + "'");
            	count++;
            	existing = existingProps.get(logicalName);
        	}
        }
        return logicalName;    	
    }
    
    private Map<String, Property> getAllExistingProps(Class clss) {
        Map<String, Property> result = new HashMap<String, Property>();
        collect(clss, result);
        return result;
    }
    
    private void collect(Class clss, Map<String, Property> properties) {
    	Map<String, Property> baseResult = this.classPropertyMap.get(clss);
    	if (baseResult != null)
    	    properties.putAll(baseResult);
        for (ClassRef ref : clss.getSuperClasses()) {
        	Class base = this.classQualifiedNameMap.get(
        			ref.getUri() + "#" + ref.getName());
        	collect(base, properties);
        }
    }
    
    public List<Class> getRootClasses() {
    	List<Class> result = new ArrayList<Class>();
	    for (Class clss : this.classQualifiedNameMap.values()) {
	    	if (clss.getSuperClasses() == null || clss.getSuperClasses().size() == 0)
	    		result.add(clss);
	    }
	    return result;
    }
    
    public void accept(Class root, ClassVisitor visitor) {
    	traverse(root, null, visitor);
    }
 
    private void traverse(Class target, Class source, ClassVisitor visitor) {
    	visitor.visit(target, source);
    	HashSet<Class> subclasses = this.subclassMap.get(target);
    	if (subclasses != null) {
	    	Iterator<Class> iter = subclasses.iterator();
	    	while (iter.hasNext()) {
	    		traverse(iter.next(), target, visitor);
	        }
    	}
    }
    
    public void collectSubclasses() {
        for (Class clss : this.getClassQualifiedNameMap().values())
        {
            for (ClassRef ref : clss.getSuperClasses()) {
            	Class base = this.getClassQualifiedNameMap().get(
            			ref.getUri() + "#" + ref.getName());
            	HashSet<Class> subclasses = this.subclassMap.get(base);
            	if (subclasses == null) {
            		subclasses = new HashSet<Class>();
            		this.subclassMap.put(base, subclasses);
            	}
            	subclasses.add(clss);
            }
        }
    }
    
    public String buildLogicalEnumerationLiteralName(Enumeration enm, String name,
    		Map<String, EnumerationLiteral> literalMap)
    {
    	String logicalName = name;
    	EnumerationLiteral existing = literalMap.get(logicalName);
	        if (existing != null) {
	        	int count = 1;
	        	while (existing != null) {
	        		int underscore = logicalName.lastIndexOf("_");
	        		if (underscore > 0) {
	        			logicalName = logicalName.substring(0, underscore);
	        		}
	        		logicalName = logicalName + "_" + String.valueOf(count);
	        	    log.warn("detected name colision for literal '"
		        		    + enm.getName() + "." + existing.getName() 
		        		    + "' - using synthetic name '"
		        		    + logicalName + "'");
	            	count++;
	            	existing = literalMap.get(logicalName);
	        	}
        }
        return logicalName;    	
    }
    
    public boolean isEnumeration(AbstractSimpleType simpleType) {
    	Restriction restriction = simpleType.getRestriction();
    	if (restriction != null) {
    	    if (restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().size() > 0)
    	        for (Object obj : restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives()) 
    		        if (obj instanceof org.plasma.xml.schema.Enumeration)
                        return true;
    	    
    	}
    	return false;
    }
        
    public String getOpenAttributeValue(QName qname, OpenAttrs attrs) {
		return findOpenAttributeValue(qname, attrs, false);
	}
 
    public String findOpenAttributeValue(QName qname, OpenAttrs attrs) {
		return findOpenAttributeValue(qname, attrs, true);
	}

    public String findOpenAttributeValue(QName qname, OpenAttrs attrs,
			boolean supressError) {
		Iterator<QName> iter = attrs.getOtherAttributes().keySet().iterator();
		while (iter.hasNext()) {
			QName key = iter.next();
			if (key.equals(qname)) {
				String result = attrs.getOtherAttributes().get(key);
				return result;
			}
		}
		if (!supressError)
			throw new IllegalArgumentException("attribute '" + qname.toString()
					+ "' not found");
		return null;
	}

    public QName getOpenAttributeQNameByValue(String value, OpenAttrs attrs) {
		Iterator<QName> iter = attrs.getOtherAttributes().keySet().iterator();
		while (iter.hasNext()) {
			QName key = iter.next();
			String s = attrs.getOtherAttributes().get(key);
			if (s != null && s.equals(value))
				return key;
		}
		throw new IllegalArgumentException("attribute value '" + value
				+ "' not found");
	}

    public QName findOpenAttributeQNameByValue(String value, OpenAttrs attrs) {
		Iterator<QName> iter = attrs.getOtherAttributes().keySet().iterator();
		while (iter.hasNext()) {
			QName key = iter.next();
			String s = attrs.getOtherAttributes().get(key);
			if (s != null && s.equals(value))
				return key;
		}
		return null;
	}

    public String getDocumentationContent(Annotation annotation) {
		StringBuilder buf = new StringBuilder();
		for (Object annotationObj : annotation.getAppinfosAndDocumentations()) {
			buf.append(getContent(annotationObj));
		}
		return buf.toString();
	}    
    
    public String getDocumentationContent(Annotated annotated) {
		StringBuilder buf = new StringBuilder();
		if (annotated != null && annotated.getAnnotation() != null) {
			for (Object annotationObj : annotated.getAnnotation()
					.getAppinfosAndDocumentations()) {
				buf.append(getContent(annotationObj));
			}
        }
		return buf.toString();
	}    
    
    private String getContent(Object annotationObj) {
		StringBuilder buf = new StringBuilder();
		if (annotationObj instanceof org.plasma.xml.schema.Documentation) {
			org.plasma.xml.schema.Documentation doc = (org.plasma.xml.schema.Documentation) annotationObj;
			for (Object content : doc.getContent())
				if (content instanceof String) {
					buf.append(content);
				} else if (content instanceof ElementNSImpl) {
					ElementNSImpl nsElem = (ElementNSImpl) content;
					buf.append(serializeElement(nsElem));
				} else
					throw new IllegalStateException(
							"unexpected content class, "
									+ annotationObj.getClass()
											.getName());
		} else if (annotationObj instanceof Appinfo) {
			log.warn("ignoring app-info: "
					+ String.valueOf(annotationObj));
		}
		return buf.toString();
	}
	
    public String serializeElement(ElementNSImpl nsElem)
	{
		String result = "";
        TransformerFactory transFactory = TransformerFactory.newInstance();
        log.debug("transformer factory: " + transFactory.getClass().getName());
        //transFactory.setAttribute("indent-number", 2);
        Transformer idTransform = null;
        ByteArrayOutputStream stream = null;
		try {
			idTransform = transFactory.newTransformer();
			idTransform.setOutputProperty(OutputKeys.METHOD, "xml");
	        idTransform.setOutputProperty(OutputKeys.INDENT,"yes"); 
	        idTransform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes"); 
	        Source input = new DOMSource(nsElem.getOwnerDocument());
	        stream = new ByteArrayOutputStream();
	        Result output = new StreamResult(stream);
			idTransform.transform(input, output);
			stream.flush();
			result = new String(stream.toByteArray());
			return result;
		} catch (TransformerConfigurationException e1) {
			log.error(e1.getMessage(), e1);
	    } catch (TransformerException e) {
		    log.error(e.getMessage(), e);
	    } catch (IOException e) {
		    log.error(e.getMessage(), e);
		} finally {
			if (stream != null)
				try {
					stream.close();
				} catch (Throwable t) {
				}
			
		}
		return result;
	}

    public String getSDOXValue(ComplexType complexType, String localName) {
        QName nameQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        	localName, SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String value = getOpenAttributeValue(nameQName, complexType);       
    	return value;
    }
    
    public String findSDOXValue(ComplexType complexType, String localName) {
        QName nameQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        	localName, SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String value = findOpenAttributeValue(nameQName, complexType);       
    	return value;
    }
    
    public String getSDOXValue(LocalElement element, String localName) {
        QName nameQName = new QName(SDOXConstants.SDOX_NAMESPACE_URI, 
        	localName, SDOXConstants.SDOX_NAMESPACE_PREFIX);
        String value = getOpenAttributeValue(nameQName, element);       
    	return value;
    }           	
	
    public DataType mapType(XSDBuiltInType xsdType) {
	 	switch (xsdType) {
	 	case xsd_anySimpleType:              return DataType.Object;          		    
	 	case xsd_anyType:				     return DataType.Object; 	    
	 	case xsd_anyURI: 				     return DataType.URI;              
	 	case xsd_base64Binary: 		         return DataType.Bytes; 	        
	 	case xsd_boolean:				     return DataType.Boolean; 	        
	 	case xsd_byte: 				         return DataType.Byte;             
	 	case xsd_date: 				         return DataType.YearMonthDay;     
	 	case xsd_dateTime: 			         return DataType.DateTime;         
	 	case xsd_decimal:				     return DataType.Decimal; 	        
	 	case xsd_double: 				     return DataType.Double;           
	 	case xsd_duration: 			         return DataType.Duration;         
	 	case xsd_ENTITIES: 			         return DataType.Strings;          
	 	case xsd_ENTITY: 				     return DataType.String;           
	 	case xsd_float: 				     return DataType.Float; 	        
	 	case xsd_gDay: 				         return DataType.Day;              
	 	case xsd_gMonth: 				     return DataType.Month;            
	 	case xsd_gMonthDay: 			     return DataType.MonthDay;         
	 	case xsd_gYear: 				     return DataType.Year;             
	 	case xsd_gYearMonth: 			     return DataType.YearMonth;        
	 	case xsd_hexBinary: 			     return DataType.Bytes; 	        
	 	case xsd_ID: 					     return DataType.String;           
	 	case xsd_IDREF: 				     return DataType.String;           
	 	case xsd_IDREFS: 				     return DataType.Strings;          
	 	case xsd_int:					     return DataType.Int; 	            
	 	case xsd_integer:				     return DataType.Integer; 	        
	 	case xsd_language:			         return DataType.String; 	        
	 	case xsd_long: 				         return DataType.Long;             
	 	case xsd_Name: 				         return DataType.String; 	        
	 	case xsd_NCName: 				     return DataType.String;           
	 	case xsd_negativeInteger:		     return DataType.Integer; 	        
	 	case xsd_NMTOKEN:				     return DataType.String;           
	 	case xsd_NMTOKENS: 			         return DataType.Strings;          
	 	case xsd_nonNegativeInteger: 	     return DataType.Integer;          
	 	case xsd_nonPositiveInteger: 	     return DataType.Integer;          
	 	case xsd_normalizedString: 	         return DataType.String; 	        
	 	case xsd_NOTATION: 			         return DataType.String; 	        
	 	case xsd_positiveInteger:		     return DataType.Integer; 	        
	 	case xsd_QName: 				     return DataType.URI;              
	 	case xsd_short: 				     return DataType.Short; 	        
	 	case xsd_string: 				     return DataType.String;           
	 	case xsd_time: 				         return DataType.Time;             
	 	case xsd_token: 				     return DataType.String;           
	 	case xsd_unsignedByte: 		         return DataType.Short;            
	 	case xsd_unsignedInt:			     return DataType.Long;             
	 	case xsd_unsignedLong: 		         return DataType.Integer;          
	 	case xsd_unsignedShort:              return DataType.Int; 
	 	default:
	 		return DataType.String;
	 	}
	}	
    
    public String findAppInfoValue(org.plasma.xml.schema.Enumeration schemaEnum)
    {
    	String result = null;
		if (schemaEnum.getAnnotation() != null)
	    for (Object o2 : schemaEnum.getAnnotation().getAppinfosAndDocumentations()) {
	    	if (o2 instanceof Appinfo) {
	    		Appinfo appinfo = (Appinfo)o2;
	    		result = (String)appinfo.getContent().get(0);
	    		if (result != null) {
	    			result.trim();
	    			if (result.length() == 0)
	    				result = null;
	    		}
	    		break;
	    	}
	    }
    	return result;
    }
    
    public Documentation createDocumentation(DocumentationType type, 
    		String content)
    {
        Documentation documentation = new Documentation();
		documentation.setType(type);
		Body body = new Body();
		body.setValue(content);
		documentation.setBody(body); 
		return documentation;
    }
}
