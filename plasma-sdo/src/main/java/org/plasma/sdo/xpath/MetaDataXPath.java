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
package org.plasma.sdo.xpath;

import java.util.List;

import org.jaxen.Context;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;

import commonj.sdo.DataObject;
import commonj.sdo.Type;

/** 
 * An XPath implementation for traversal of an SDO {@link commonj.sdo.Type} 
 * graph based on Jaxen. 
 *
 * <p>
 * This is the main entry point for matching an XPath against 
 * a set of {@link commonj.sdo.Type} structures linked by 
 * {@link commonj.sdo.Property properties}.
 * </p>
 * <p>
 * Predicates typically involve data as well as "meta data". 
 * E.g. /person/[@name='Sam'] where 'Sam' is data and  
 * 'person' and 'name' are meta data. This class accepts
 * only SDO meta data objects such as {@link commonj.sdo.Type} as 
 * input and therefore assumes
 * a given XPath references only meta data elements. Passing
 * an XPath expression with data elements will result in zero 
 * or null results returned. Use {@link DataGraphXPath} to process
 * mixed data and meta data XPath expressions. 
 * </p>  
 */
public class MetaDataXPath extends DefaultXPath {
	private static final long serialVersionUID = 1L;
		
	private static final XPathProperty[] EMPTY_PROPERTY_ARRAY = new XPathProperty[0];
	private static final MetaDataNodeAdapter[] EMPTY_NODE_ARRAY = new MetaDataNodeAdapter[0];

	public MetaDataXPath(String xpathExpr)
	    throws JaxenException {
        super(xpathExpr, new MetaDataNavigator());
    }
	
	public MetaDataXPath(String xpathExpr, Navigator navigator)
			throws JaxenException {
		super(xpathExpr, navigator);
	}

	public Context getNodeContext(Object node) {
		return this.getContext(node);
	}	
	
	/**
	 * Returns an array with either 1 single result or multiple results, 
	 * and error being thrown if no results are found or if
	 * multiple results are found but the target Property is not a
	 * "many" Property, i.e. a multi-valued Property. The target Property
	 * is the result or end point Property after resolving an XPATH.
	 * @return an array with either 1 single result or multiple results.
	 * @throws IllegalXPathArgumentException if no results are found or if
	 * multiple results are found but the target Property is not a
	 * "many" property, i.e. a multi-valued Property. The target Property
	 * is the result or end point Property after resolving an XPATH. 
	 * @throws JaxenException as required by the Jaxen API
	 * @throws InvalidMultiplicityException if the given XPATH resulted in 
	 * multiple values for a singular property
	 */
	@SuppressWarnings("unchecked")
	public MetaDataNodeAdapter[] getResults(Type root) throws JaxenException, InvalidMultiplicityException
	{
		List nodes = this.selectNodes(root);
		if (nodes == null || nodes.size() == 0)
			return EMPTY_NODE_ARRAY;
		
		MetaDataNodeAdapter[] adapters = new MetaDataNodeAdapter[nodes.size()];
		nodes.toArray(adapters);
		if (adapters.length > 1)
			for (MetaDataNodeAdapter adapter : adapters)
				if (!adapter.getSourceProperty().isMany())
					throw new InvalidMultiplicityException("given XPATH '"
						+ this.toString() + "' resulted in multiple values for singular "
						+ "property, " + adapter.getSource().getURI() + "#"
						+ adapter.getSource().getName() + "."
						+ adapter.getSourceProperty().getName());
		return adapters;
	}
	
	/**
	 * Returns an array of Property results, an error being thrown if
	 * other than Property results are found. 
	 * @param root the SDO Type root
	 * @return an array of Property results or zero length array if
	 * no results are found.
	 * @throws JaxenException as required by the Jaxen API
	 * @throws InvalidEndpointException when the result or 
	 * terminating node or nodes are not Property nodes
	 */
	@SuppressWarnings("unchecked")
	public XPathProperty[] findProperties(Type root) 
	    throws JaxenException, InvalidEndpointException
	{
		List nodes = this.selectNodes(root);
		if (nodes == null || nodes.size() == 0)
			return EMPTY_PROPERTY_ARRAY;
		
		XPathProperty[] adapters = new XPathProperty[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			Object node = nodes.get(i);
			if (!(node instanceof XPathProperty)) {
				    throw new InvalidEndpointException("expected Property results "
							+ "for the given XPATH '" + this.toString() + "'");
			}
			adapters[i] = (XPathProperty)node;
		}
		return adapters;
	}
	
	/**
	 * Returns a single Property result.
	 * @param root the SDO Type root
	 * @return an array of Property results or zero length array if
	 * no results are found.
	 * @throws JaxenException as required by the Jaxen API
	 * @throws InvalidEndpointException when the result or 
	 * terminating node or nodes are not Property nodes or if no
	 * result is found.
	 */
	public XPathProperty getProperty(Type root) 
	    throws JaxenException, InvalidEndpointException
	{
		Object node = this.selectSingleNode(root);
		if (node == null || !(node instanceof XPathProperty))
		    throw new InvalidEndpointException("expected Property results "
					+ "for the given XPATH '" + this.toString() + "'");
        return (XPathProperty)node;
	}	
	
	/**
	 * Returns a single Property result or null
	 * if no Property results are found. 
	 * @param root the SDO Type root
	 * @return an array of Property results or null if
	 * no results are found.
	 * @throws JaxenException as required by the Jaxen API
	 * @throws InvalidEndpointException when the result or 
	 * terminating node or nodes are not Property nodes
	 */
	public XPathProperty findProperty(Type root) 
	    throws JaxenException, InvalidEndpointException
	{
		Object node = this.selectSingleNode(root);
		if (node == null)
			return null;
		
		if (!(node instanceof XPathProperty)) {
		    throw new InvalidEndpointException("expected Property results "
					+ "for the given XPATH '" + this.toString() + "'");
		}
        return (XPathProperty)node;
	}
	
	
}
