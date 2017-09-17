/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaType;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * Supports navigation of an SDO {@link commonj.sdo.DataGraph data graph} by the
 * Jaxen XPATH engine.
 *
 * <p>
 * This class is not intended for direct usage, but is used by the Jaxen engine
 * during evaluation. Use {@link DataGraphXPath} to parse an XPath against a
 * {@link commonj.sdo.DataGraph data graph}.
 * </p>
 *
 * @see XPath
 * @see commonj.sdo.DataGraph
 */
public class DataGraphNavigator extends DefaultNavigator implements NamedAccessNavigator {
  private static final long serialVersionUID = -1636727587303584165L;
  private static Log log = LogFactory.getFactory().getInstance(DataGraphNavigator.class);

  public DataGraphNavigator() {
  }

  public boolean isElement(Object obj) {
    return obj instanceof XPathDataObject;
  }

  public boolean isComment(Object obj) {
    return false;
  }

  public boolean isText(Object obj) {
    // return false;
    return obj instanceof XPathDataValue;
  }

  public boolean isAttribute(Object obj) {
    return obj instanceof XPathDataProperty;
  }

  public boolean isProcessingInstruction(Object obj) {
    return false;
  }

  public boolean isDocument(Object obj) {
    return false;
  }

  public boolean isNamespace(Object obj) {
    return false;
  }

  public String getElementName(Object obj) {
    XPathDataObject elem = (XPathDataObject) obj;

    return elem.getDataObject().getType().getName();
  }

  public String getElementNamespaceUri(Object obj) {
    XPathDataObject elem = (XPathDataObject) obj;

    String uri = elem.getDataObject().getType().getURI();
    if (uri != null && uri.length() == 0)
      return null;
    else
      return uri;
  }

  public String getAttributeName(Object obj) {
    XPathDataProperty attr = (XPathDataProperty) obj;
    return attr.getProperty().getName();
  }

  public String getAttributeNamespaceUri(Object obj) {
    XPathDataProperty attr = (XPathDataProperty) obj;

    String uri = attr.getProperty().getType().getURI();
    if (uri != null && uri.length() == 0)
      return null;
    else
      return uri;
  }

  /**
   * Where the given context node is a SDO Property, gets the actual value of
   * the property wrapped in a CoreXPathValue and Iterator. This wrapper
   * approach supports subsequent navigator tests for isText() where we want any
   * Data Object value to be thought of as XML text and be returned. In
   * particular we want null values to be returned so that clients can at least
   * detect if the XPath was navigated successfully.
   * 
   * @param contextNode
   *          the context node
   * @see CoreXPathResult
   * @see org.jaxen.DefaultNavigator#getChildAxisIterator(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  public Iterator getChildAxisIterator(Object contextNode) {
    if (contextNode instanceof XPathDataProperty) {
      XPathDataProperty contextProperty = (XPathDataProperty) contextNode;
      Property property = (Property) contextProperty.getProperty();

      Object target = contextProperty.getSource().get(property);
      if (target != null) {
        if (target instanceof List) {
          return ((List) target).iterator();
        } else
          return new SingleObjectIterator(new XPathDataValue(target, contextProperty.getSource(),
              property));
      } else {
        return new SingleObjectIterator(new XPathDataValue(null, contextProperty.getSource(),
            property));
      }
    } else if (contextNode instanceof PlasmaDataObject) {
      return this.getChildResult((PlasmaDataObject) contextNode);
    } else {
      return this.getChildResult(((XPathDataObject) contextNode).getDataObject());
    }
  }

  /**
   * Retrieves an <code>Iterator</code> over the child elements that match the
   * supplied local name and namespace URI.
   *
   * @param contextNode
   *          the origin context node
   * @param localName
   *          the local name of the children to return, always present
   * @param namespacePrefix
   *          ignored; prefixes are not used when matching in XPath
   * @param namespaceURI
   *          the URI of the namespace of the children to return
   * @return an Iterator that traverses the named children, or null if none
   */
  @SuppressWarnings("unchecked")
  public Iterator getChildAxisIterator(Object contextNode, String localName,
      String namespacePrefix, String namespaceURI) {

    if (contextNode instanceof PlasmaDataObject) // its the root
    {
      PlasmaDataObject source = (PlasmaDataObject) contextNode;
      return getChildResult(source, localName);
    } else if (contextNode instanceof XPathDataObject) {
      XPathDataObject contextAdapter = (XPathDataObject) contextNode;
      PlasmaDataObject source = contextAdapter.getDataObject();
      return getChildResult(source, localName);
    } else
      throw new IllegalArgumentException("unexpected instance, " + contextNode.getClass().getName());
  }

  @SuppressWarnings("unchecked")
  private Iterator getChildResult(PlasmaDataObject source, String localName) {

    String propertyName = localName;

    int indexPosition = localName.indexOf(".");
    int index = 0;
    if (indexPosition >= 0) {
      propertyName = localName.substring(0, indexPosition);
      try {
        index = Integer.parseInt(localName.substring(indexPosition + 1));
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(localName, e);
      }
    }

    // Note: must use 'findProperty' rather than getProperty here to support
    // descendant search
    // for any data object (element in XPATH XML land) with the
    // desired property. E.g. for "Data Graph" bookstore/catalog/book
    // for query bookstore.get("//book") where book is not a property of
    // bookstore
    // but of catalog, yet we want to search for it anywhere under the root.
    Property sourceProp = ((PlasmaType) source.getType()).findProperty(propertyName);
    if (sourceProp == null)
      return JaxenConstants.EMPTY_ITERATOR;

    if (sourceProp.getType().isDataType()) { // abort traversal
      return new SingleObjectIterator(new XPathDataProperty(sourceProp, source, sourceProp));
    }

    if (!sourceProp.isMany() && indexPosition >= 0)
      throw new IllegalArgumentException("index specified for singular property, "
          + source.getType().getURI() + "#" + source.getType().getName() + "." + propertyName);

    Object target = source.get(sourceProp);
    if (target != null) {
      if (target instanceof List) {
        List targetList = (List) target;
        List<XPathDataObject> list = new ArrayList<XPathDataObject>(targetList.size());
        if (indexPosition == -1) {
          for (Object targetObject : targetList) {
            PlasmaDataObject dataObject = (PlasmaDataObject) targetObject;
            list.add(new XPathDataObject(dataObject, source, sourceProp));
          }
        } else {
          try {
            PlasmaDataObject dataObject = (PlasmaDataObject) targetList.get(index);
            list.add(new XPathDataObject(dataObject, source, sourceProp));
          } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(localName, e);
          }
        }
        return list.iterator();
      } else
        return new SingleObjectIterator(new XPathDataObject((PlasmaDataObject) target, source,
            sourceProp));
    }
    return JaxenConstants.EMPTY_ITERATOR;
  }

  @SuppressWarnings("unchecked")
  private Iterator getChildResult(PlasmaDataObject source) {
    final List<XPathDataObject> children = new ArrayList<XPathDataObject>();
    PlasmaDataGraphVisitor visitor = new PlasmaDataGraphVisitor() {
      public void visit(DataObject target, DataObject traversalSource, String sourcePropertyName,
          int level) {
        if (traversalSource == null)
          return; // skip the root
        Property sourceProperty = traversalSource.getType().getProperty(sourcePropertyName);
        children.add(new XPathDataObject((PlasmaDataObject) target,
            (PlasmaDataObject) traversalSource, sourceProperty));
      }
    };
    source.accept(visitor, 1);
    return children.iterator();
  }

  @SuppressWarnings("unchecked")
  public Iterator getNamespaceAxisIterator(Object contextNode) {
    return JaxenConstants.EMPTY_ITERATOR;
  }

  @SuppressWarnings("unchecked")
  public Iterator getParentAxisIterator(Object contextNode) {
    return JaxenConstants.EMPTY_ITERATOR;
  }

  @SuppressWarnings("unchecked")
  public Iterator getAttributeAxisIterator(Object contextNode) {
    if (!(contextNode instanceof XPathDataObject)) {
      return JaxenConstants.EMPTY_ITERATOR;
    }

    XPathDataObject elem = (XPathDataObject) contextNode;

    return elem.getDataObject().getType().getProperties().iterator();
  }

  /**
   * Retrieves an <code>Iterator</code> over the attribute elements that match
   * the supplied name.
   *
   * @param contextNode
   *          the origin context node
   * @param localName
   *          the local name of the attributes to return, always present
   * @param namespacePrefix
   *          the prefix of the namespace of the attributes to return
   * @param namespaceURI
   *          the URI of the namespace of the attributes to return
   * @return an Iterator that traverses the named attributes, not null
   */
  @SuppressWarnings("unchecked")
  public Iterator getAttributeAxisIterator(Object contextNode, String localName,
      String namespacePrefix, String namespaceURI) {

    if (contextNode instanceof XPathDataObject) {
      XPathDataObject adapter = (XPathDataObject) contextNode;

      Property prop = adapter.getDataObject().getType().getProperty(localName);
      // this.contextProperty = this.contextType.getProperty(localName);
      // Namespace namespace = (namespaceURI == null ? Namespace.NO_NAMESPACE :
      // Namespace.getNamespace(namespacePrefix, namespaceURI));
      // for propertry results sourceProperty=target
      return new SingleObjectIterator(new XPathDataProperty(prop, adapter.getDataObject(), prop));
    }
    return JaxenConstants.EMPTY_ITERATOR;
  }

  /**
   * Returns a parsed form of the given XPath string, which will be suitable for
   * queries on Data Graph documents.
   */
  public XPath parseXPath(String xpath) throws SAXPathException {
    return new DataGraphXPath(xpath);
  }

  public Object getDocumentNode(Object contextNode) {
    DataObject dataObject = null;
    if (contextNode instanceof DataObject) // its the root
    {
      dataObject = (DataObject) contextNode;
    } else if (contextNode instanceof XPathDataObject) {
      dataObject = ((XPathDataObject) contextNode).getDataObject();
    }

    return dataObject.getDataGraph().getRootObject();
  }

  public String getElementQName(Object obj) {
    XPathDataObject elem = (XPathDataObject) obj;

    // String prefix = elem.getNamespacePrefix();
    return elem.getDataObject().getType().getName();
  }

  public String getAttributeQName(Object obj) {
    XPathDataProperty adapter = (XPathDataProperty) obj;
    return adapter.getProperty().getName();
  }

  public String getNamespaceStringValue(Object obj) {
    return obj.toString();
  }

  public String getNamespacePrefix(Object obj) {
    return obj.toString();
  }

  public String getTextStringValue(Object obj) {
    return obj.toString();
  }

  public String getAttributeStringValue(Object obj) {
    XPathDataProperty adapter = (XPathDataProperty) obj;
    return adapter.getSource().getString(adapter.getProperty());
  }

  public String getElementStringValue(Object obj) {
    return obj.toString();
    /*
     * Element elem = (Element) obj;
     * 
     * StringBuffer buf = new StringBuffer();
     * 
     * List content = elem.getContent(); Iterator contentIter =
     * content.iterator(); Object each = null;
     * 
     * while ( contentIter.hasNext() ) { each = contentIter.next();
     * 
     * if ( each instanceof Text ) { buf.append( ((Text)each).getText() ); }
     * else if ( each instanceof CDATA ) { buf.append( ((CDATA)each).getText()
     * ); } else if ( each instanceof Element ) { buf.append(
     * getElementStringValue( each ) ); } }
     * 
     * return buf.toString();
     */
  }

  public String getProcessingInstructionTarget(Object obj) {
    return null;
  }

  public String getProcessingInstructionData(Object obj) {
    return null;
  }

  public String getCommentStringValue(Object obj) {
    return null;
  }

  public String translateNamespacePrefixToUri(String prefix, Object context) {
    XPathDataObject element = null;
    if (context instanceof XPathDataObject) {
      element = (XPathDataObject) context;
      return element.getDataObject().getType().getURI();
    }

    return null;
  }

  public Object getDocument(String url) throws FunctionCallException {
    throw new UnsupportedOperationException();
  }
}
