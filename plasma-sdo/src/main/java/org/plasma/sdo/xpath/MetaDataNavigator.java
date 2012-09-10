package org.plasma.sdo.xpath;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.DefaultNavigator;
import org.jaxen.FunctionCallException;
import org.jaxen.JaxenConstants;
import org.jaxen.NamedAccessNavigator;
import org.jaxen.XPath;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.util.SingleObjectIterator;
import org.plasma.sdo.PlasmaType;

import commonj.sdo.Property;
import commonj.sdo.Type;

/** 
 * Supports navigation of SDO {@link commonj.sdo.Type} and
 * {@link commonj.sdo.Property} instances by the
 * Jaxen XPATH engine. 
 *
 * <p>
 * This class is not intended for direct usage, but is
 * used by the Jaxen engine during evaluation. Use {@link MetaDataXPath}
 * to parse an XPath against a {@link commonj.sdo.Type} graph.
 * </p>
 *
 * @see XPath
 * @see commonj.sdo.Type
 * @see commonj.sdo.Property
 */
public class MetaDataNavigator extends DefaultNavigator implements NamedAccessNavigator
{
    private static final long serialVersionUID = -1636727587303584165L;
    private static Log log = LogFactory.getFactory().getInstance(MetaDataNavigator.class);
    
    public MetaDataNavigator() {   	
    }

    public boolean isElement(Object obj)
    {
        return obj instanceof XPathType;
    }

    public boolean isComment(Object obj)
    {
        return false;
    }

    public boolean isText(Object obj)
    {
        return false;
    }

    public boolean isAttribute(Object obj)
    {
        return obj instanceof XPathProperty; 
    }

    public boolean isProcessingInstruction(Object obj)
    {
        return false;
    }

    public boolean isDocument(Object obj)
    {
        return false;
    }

    public boolean isNamespace(Object obj)
    {
        return false;
    }

    public String getElementName(Object obj)
    {
    	XPathType elem = (XPathType) obj;

        return elem.getType().getName();
    }

    public String getElementNamespaceUri(Object obj)
    {
    	XPathType elem = (XPathType) obj;
        
        String uri = elem.getType().getURI();
        if ( uri != null && uri.length() == 0 ) 
            return null;
        else
            return uri;
    }

    public String getAttributeName(Object obj)
    {
    	XPathProperty attr = (XPathProperty) obj;
        return attr.getProperty().getName();
    }

    public String getAttributeNamespaceUri(Object obj)
    {
    	XPathProperty attr = (XPathProperty) obj;

        String uri = attr.getProperty().getType().getURI();
        if ( uri != null && uri.length() == 0 ) 
            return null;
        else
            return uri;
    }

    /**
     * @param contextNode the context node
     * @see org.jaxen.DefaultNavigator#getChildAxisIterator(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
	public Iterator getChildAxisIterator(Object contextNode)
    {

        throw new IllegalArgumentException("unexpected instance, " 
        			+ contextNode.getClass().getName());
        /*
        if ( contextNode instanceof XPathProperty )
        {
        	XPathProperty contextProperty = (XPathProperty)contextNode;
        	Property property = (Property)contextProperty.getProperty();
        	
        	Object target = contextProperty.getSource().get(property);
        	if (target != null) {
        		if (target instanceof List) {
        			return ((List)target).iterator();
        		}
        		else
        			return new SingleObjectIterator(
        				new XPathDataValue(target, 
        						contextProperty.getSource(), property)); 
        	}
        	else {
        		return new SingleObjectIterator(
        				new XPathDataValue(null, 
        						contextProperty.getSource(), property));
        	}
        }
        */
    }

    /**
     * Retrieves an <code>Iterator</code> over the child elements that
     * match the supplied local name and namespace URI.
     *
     * @param contextNode      the origin context node
     * @param localName        the local name of the children to return, always present
     * @param namespacePrefix  ignored; prefixes are not used when matching in XPath
     * @param namespaceURI     the URI of the namespace of the children to return
     * @return an Iterator     that traverses the named children, or null if none
     */
    @SuppressWarnings("unchecked")
	public Iterator getChildAxisIterator(
            Object contextNode, String localName, String namespacePrefix, String namespaceURI) {

        if ( contextNode instanceof Type ) // its the root
        {
        	Type source = (Type)contextNode;
        	return getChildResult(source, localName);
        }
        else if (contextNode instanceof XPathType) {
        	XPathType contextAdapter = (XPathType)contextNode;
        	Type source = contextAdapter.getType();
        	return getChildResult(source, localName);
        }
        else
        	throw new IllegalArgumentException("unexpected instance, " 
        			+ contextNode.getClass().getName());
    }
    
    @SuppressWarnings("unchecked")
	private Iterator getChildResult(Type source, String localName) {
    	
    	String propertyName = localName;
    	
    	int indexPosition = localName.indexOf(".");
    	int index = 0;
    	if (indexPosition >= 0) {
    		propertyName = localName.substring(0, indexPosition);
    		try {
    		    index = Integer.parseInt(localName.substring(indexPosition+1));
    		}
    		catch (NumberFormatException e) {
    			throw new IllegalArgumentException(localName, e);
    		}
    	}	    		
    		
    	// Note: must use 'findProperty' rather than getProperty here to support descendant search
    	// for any data object (element in XPATH XML land) with the
    	// desired property. E.g. for "Data Graph" bookstore/catalog/book
    	// for query bookstore.get("//book") where book is not a property of bookstore
    	// but of catalog, yet we want to search for it anywhere under the root. 
    	Property sourceProp = ((PlasmaType)source).findProperty(propertyName);
    	if (sourceProp == null)
    		return JaxenConstants.EMPTY_ITERATOR;       	
    	
    	if (!sourceProp.isMany() && indexPosition >= 0)
    		throw new IllegalArgumentException("index specified for singular property, "
    			+ source.getURI() + "#" + source.getName()
    			+ "." + propertyName);

    	if (sourceProp.getType().isDataType()) { // abort traversal       		
    		return new SingleObjectIterator(
    			new XPathProperty(sourceProp, source, sourceProp)); 
    	}
    	else {
    		Type targetType = sourceProp.getType();
    		return new SingleObjectIterator(
        		new XPathType(targetType, source, sourceProp)); 
    	}    	
    }
    
    @SuppressWarnings("unchecked")
	public Iterator getNamespaceAxisIterator(Object contextNode)
    {
        return JaxenConstants.EMPTY_ITERATOR;
    }

    @SuppressWarnings("unchecked")
	public Iterator getParentAxisIterator(Object contextNode)
    {
        return JaxenConstants.EMPTY_ITERATOR;
    }

    @SuppressWarnings("unchecked")
	public Iterator getAttributeAxisIterator(Object contextNode)
    {
        if ( ! ( contextNode instanceof XPathType ) )
        {
            return JaxenConstants.EMPTY_ITERATOR;
        }

        XPathType elem = (XPathType) contextNode;

        return elem.getType().getProperties().iterator();
    }

    /**
     * Retrieves an <code>Iterator</code> over the attribute elements that
     * match the supplied name.
     *
     * @param contextNode      the origin context node
     * @param localName        the local name of the attributes to return, always present
     * @param namespacePrefix  the prefix of the namespace of the attributes to return
     * @param namespaceURI     the URI of the namespace of the attributes to return
     * @return an Iterator     that traverses the named attributes, not null
     */
    @SuppressWarnings("unchecked")
	public Iterator getAttributeAxisIterator(
            Object contextNode, String localName, String namespacePrefix, String namespaceURI) {

        if ( contextNode instanceof XPathType ) {
        	XPathType adapter = (XPathType)contextNode;
        	Property prop = adapter.getType().getProperty(localName);
        	if (prop.getType().isDataType()) {
        		return new SingleObjectIterator(
            		new XPathProperty(prop, adapter.getSource(), 
            			adapter.getSourceProperty()));         		
        	}
        	else {
           		Type targetType = prop.getType();
        		return new SingleObjectIterator(
            		new XPathType(targetType, adapter.getSource(), prop)); 
        	}
        }
        return JaxenConstants.EMPTY_ITERATOR;
    }

    /** Returns a parsed form of the given XPath string, which will be suitable
     *  for queries on Data Graph documents.
     */
    public XPath parseXPath (String xpath) throws SAXPathException
    {
        return new MetaDataXPath(xpath);
    }

    public Object getDocumentNode(Object contextNode)
    {
    	XPathType type = null;
        if ( contextNode instanceof XPathType ) // its the root
        {
        	type = (XPathType)contextNode;
        }
        
        return type;
    }

    public String getElementQName(Object obj)
    {
    	XPathType elem = (XPathType) obj;

        //String prefix = elem.getNamespacePrefix();
        return elem.getType().getURI() + "#" + elem.getType().getName();
    }

    public String getAttributeQName(Object obj)
    {
    	XPathProperty adapter = (XPathProperty)obj;
        return adapter.getProperty().getName();
    }

    public String getNamespaceStringValue(Object obj)
    {
    	return obj.toString();
    }

    public String getNamespacePrefix(Object obj)
    {
    	return obj.toString();
    }

    public String getTextStringValue(Object obj)
    {
        return obj.toString();
    }

    public String getAttributeStringValue(Object obj)
    {
    	XPathProperty adapter = (XPathProperty)obj;
        return adapter.getProperty().getName();
    }

    public String getElementStringValue(Object obj)
    {
        return obj.toString(); 
    }

    public String getProcessingInstructionTarget(Object obj)
    {
        return null;
    }

    public String getProcessingInstructionData(Object obj)
    {
        return null;
    }

    public String getCommentStringValue(Object obj)
    {
        return null;
    }

    public String translateNamespacePrefixToUri(String prefix, Object context)
    {
    	XPathType element = null;
        if ( context instanceof XPathType ) 
        {
            element = (XPathType) context;
            return element.getType().getURI();
        }

        return null;
    }

    public Object getDocument(String url) throws FunctionCallException
    {
    	throw new UnsupportedOperationException();
    }
}

