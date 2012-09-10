package org.plasma.sdo.xpath;

import java.util.List;

import org.jaxen.Context;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;

import commonj.sdo.DataObject;

/** 
 * An XPath implementation for a {@link commonj.sdo.DataGraph data graph} based on Jaxen.
 *
 * <p>This is the main entry point for matching an XPath against 
 * a {@link commonj.sdo.DataGraph data graph}.  
 */
public class DataGraphXPath extends DefaultXPath {
	private static final long serialVersionUID = 1L;
		
	private static final XPathDataObject[] EMPTY_DATA_OBJECT_ARRAY = new XPathDataObject[0];
	private static final XPathDataProperty[] EMPTY_PROPERTY_ARRAY = new XPathDataProperty[0];
	private static final DataGraphNodeAdapter[] EMPTY_NODE_ARRAY = new DataGraphNodeAdapter[0];

	public DataGraphXPath(String xpathExpr)
	    throws JaxenException {
        super(xpathExpr, new DataGraphNavigator());
    }
	
	public DataGraphXPath(String xpathExpr, Navigator navigator)
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
	public DataGraphNodeAdapter[] getResults(DataObject root) throws JaxenException, InvalidMultiplicityException
	{
		List nodes = this.selectNodes(root);
		if (nodes == null || nodes.size() == 0)
			return EMPTY_NODE_ARRAY;
		
		DataGraphNodeAdapter[] adapters = new DataGraphNodeAdapter[nodes.size()];
		nodes.toArray(adapters);
		if (adapters.length > 1)
			for (DataGraphNodeAdapter adapter : adapters)
				if (!adapter.getSourceProperty().isMany())
					throw new InvalidMultiplicityException("given XPATH '"
						+ this.toString() + "' resulted in multiple values for singular "
						+ "property, " + adapter.getSource().getType().getURI() + "#"
						+ adapter.getSource().getType().getName() + "."
						+ adapter.getSourceProperty().getName());
		return adapters;
	}
	
	/**
	 * Returns an array of Property results, an error being thrown if
	 * other than Property results are found. 
	 * @return an array of Property results or zero length array if
	 * no results are found.
	 * @throws JaxenException as required by the Jaxen API
	 * @throws InvalidEndpointException when the result or 
	 * terminating node or nodes are not Property nodes
	 */
	@SuppressWarnings("unchecked")
	public XPathDataProperty[] findProperties(DataObject root) 
	    throws JaxenException, InvalidEndpointException
	{
		List nodes = this.selectNodes(root);
		if (nodes == null || nodes.size() == 0)
			return EMPTY_PROPERTY_ARRAY;
		
		XPathDataProperty[] adapters = new XPathDataProperty[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			Object node = nodes.get(i);
			if (!(node instanceof XPathDataProperty)) {
				if (node instanceof XPathDataObject)
				    throw new InvalidEndpointException("expected Property results rather than "
						+ "Data Object results for the given XPATH '"
						+ this.toString() + "'");
				else if (node instanceof XPathDataValue)
				    throw new InvalidEndpointException("expected Property results rather than "
						+ "value results for the given XPATH '"
						+ this.toString() + "'");
				else
				    throw new InvalidEndpointException("expected Property results "
							+ "for the given XPATH '" + this.toString() + "'");
			}
			adapters[i] = (XPathDataProperty)node;
		}
		return adapters;
	}
	
	/**
	 * Returns a single Property result or null
	 * if no Property results are found. 
	 * @return an array of Property results or null if
	 * no results are found.
	 * @throws JaxenException as required by the Jaxen API
	 * @throws InvalidEndpointException when the result or 
	 * terminating node or nodes are not Property nodes
	 */
	public XPathDataProperty findProperty(DataObject root) 
	    throws JaxenException, InvalidEndpointException
	{
		Object node = this.selectSingleNode(root);
		if (node == null)
			return null;
		
		if (!(node instanceof XPathDataProperty)) {
			if (node instanceof XPathDataObject)
			    throw new InvalidEndpointException("expected Property results rather than "
					+ "Data Object results for the given XPATH '"
					+ this.toString() + "'");
			else if (node instanceof XPathDataValue)
			    throw new InvalidEndpointException("expected Property results rather than "
					+ "value results for the given XPATH '"
					+ this.toString() + "'");
			else
			    throw new InvalidEndpointException("expected Property results "
						+ "for the given XPATH '" + this.toString() + "'");
		}
        return (XPathDataProperty)node;
	}
	
	
	/**
	 * Returns an array of Data Object results, an error being thrown if
	 * other than Data Object results are found or if
	 * multiple results are found but the target Property is not a
	 * "many" Property, i.e. a multi-valued property. The target Property
	 * is the result or end point Property after resolving an XPATH. 
	 * @return an array of Data Object results.
	 * @throws IllegalXPathArgumentException if
	 * other than Data Object results are found or if
	 * multiple results are found but the target Property is not a
	 * "many" Property, i.e. a multi-valued Property. The target Property
	 * is the result or end point Property after resolving an XPATH. 
	 * @throws JaxenException as required by the Jaxen API
	 * @throws InvalidEndpointException when the result or terminating node or nodes
     * for an XPATH expression are not Data Object nodes
	 * @throws InvalidMultiplicityException when multiple results are detected for a
     * singular property
	 */
	@SuppressWarnings("unchecked")
	public XPathDataObject[] findDataObjects(DataObject root) 
	    throws JaxenException, InvalidEndpointException, InvalidMultiplicityException
	{
		List nodes = this.selectNodes(root);
		if (nodes == null || nodes.size() == 0)
			return EMPTY_DATA_OBJECT_ARRAY;
		
		XPathDataObject[] adapters = new XPathDataObject[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			Object node = nodes.get(i);
			if (!(node instanceof XPathDataObject)) {
				if (node instanceof XPathDataProperty)
			        throw new InvalidEndpointException("expected Data Object results rather than "
						+ "Property results for the given XPATH '"
						+ this.toString() + "'");
				else if (node instanceof XPathDataValue)
				    throw new InvalidEndpointException("expected Data Object results rather than "
						+ "value results for the given XPATH '"
						+ this.toString() + "'");
				else
				    throw new InvalidEndpointException("expected Property results "
							+ "for the given XPATH '" + this.toString() + "'");
			}
			adapters[i] = (XPathDataObject)node;
			if (nodes.size() > 1)
				if (!adapters[i].getSourceProperty().isMany())
					throw new InvalidMultiplicityException("given path '"
						+ this.toString() + "' resulted in multiple values for singular "
						+ "property, " +  adapters[i].getSource().getType().getURI() + "#"
						+ adapters[i].getSource().getType().getName() + "."
						+ adapters[i].getSourceProperty().getName());
		}
		return adapters;
	}
}
