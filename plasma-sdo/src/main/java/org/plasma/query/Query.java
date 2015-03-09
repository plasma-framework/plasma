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
package org.plasma.query;



/**
 * The PlasmaQuery#8482; API provides a flexible mechanism to fully describe 
 * any arbitrary SDO results Data Graph, independent of any persistence 
 * framework or type of data store.  PlasmaQuery#8482; supports XPath 
 * expressions as a free-text "surface language", parsed by the API 
 * implementation and used to construct an underlying query object 
 * model representation. As an alternative to free-text, PlasmaQuery#8482; 
 * contains a query Domain Specific Language (DSL) generator and 
 * API facilitating (IDE) code-completion, 100% compile-time checking 
 * and resulting in code with an almost "fluent" English appearance 
 * based on your business model. At runtime the PlasmaQuery#8482; DSL 
 * implementation constructs an underlying query object model 
 * representation. The detailed query object model can also be 
 * manipulated directly and consists of various criteria entities
 * such as expressions, properties, operators, parameters, etc... as 
 * well as convenient factory operations which allow precise user and 
 * system control over the various elements constituting a query. 
 * The object model can also be serialized as XML for wire 
 * transport, persistence, flat file or other usage.
 */
public interface Query extends Term {
	

	/**
	 * Gets the value of the startRange property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link Integer }
	 *     
	 */
	public Integer getStartRange();

	/**
	 * Sets the value of the startRange property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Integer }
	 *     
	 */
	public void setStartRange(Integer value);

	/**
	 * Gets the value of the endRange property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link Integer }
	 *     
	 */
	public Integer getEndRange();

	/**
	 * Sets the value of the endRange property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Integer }
	 *     
	 */
	public void setEndRange(Integer value);
    
    /**
     * Appends the given property to the configuration property list.  
     * @param name the property name
     * @param value the property value
     */
    public void addConfigurationProperty(String name, String value);
    
    /**
     * Returns the configuration property value for the given name
     * @param name the property name
     * @return the property value
     */
    public String getConfigurationProperty(String name);
     
    
	/**
	 * Gets the value of the name property for the query.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getName();

	/**
	 * Sets the value of the name property for the query.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setName(String value);

	public Select getSelectClause();

	public From getFromClause();

	public Where getWhereClause();

	public Where findWhereClause();

	public void clearOrderByClause();

	public OrderBy findOrderByClause();

	public GroupBy findGroupByClause();
	
	/**
	 * Returns the underlying query model for this
	 * query.
	 * @return the underlying query model for this
	 * query
	 */
	public org.plasma.query.model.Query getModel();

}