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
 *  A non-reference property with a type which is a data type.  
 */
public interface DataProperty extends Property {

	/**
	 * Returns a boolean expression where
	 * this property is <b>equal to</b> to the given literal. 
	 * @param value the literal
	 * @return the boolean expression
	 */
	public Expression eq(Object value);

	/**
	 * Returns a boolean expression where
	 * this property is <b>not equal to</b> to the given literal. 
	 * @param value the literal
	 * @return the boolean expression
	 */
	public Expression ne(Object value);

	/**
	 * Returns a boolean expression where
	 * this property is <b>greater than</b> the given literal. 
	 * @param value the literal
	 * @return the boolean expression
	 */
	public Expression gt(Object value);

	/**
	 * Returns a boolean expression where
	 * this property is <b>greater than or equal to</b> the given literal. 
	 * @param value the literal
	 * @return the boolean expression
	 */
	public Expression ge(Object value);

	/**
	 * Returns a boolean expression where
	 * this property is <b>less than</b> the given literal. 
	 * @param value the literal
	 * @return the boolean expression
	 */
	public Expression lt(Object value);

	/**
	 * Returns a boolean expression where
	 * this property is <b>less than or equal to</b> the given literal. 
	 * @param value the literal
	 * @return the boolean expression
	 */
	public Expression le(Object value);

	/**
	 * Returns a 6 term expression, where
	 * this property is <b>greater than or equal to</b> the given 'min' literal, 
	 * and is <b>less than or equal to</b>the given 'max' literal.
	 * @param min the minimum literal
	 * @param max the maximum literal
	 * @return the expression
	 */
	public Expression between(Object min, Object max);

	/**
	 * Returns a wildcard expression where
	 * this property is <b>like</b> the given literal, and the literal
	 * may contain any number of wildcards, the wildcard character being '*'
	 * @param value the wildcard literal
	 * @return the wildcard expression
	 */
	public Expression like(String value);

	/**
	 * Returns a boolean expression where
	 * this property is <b>equal to</b> null. 
	 * @param value the literal
	 * @return the boolean expression
	 */
	public Expression isNull();

	/**
	 * Returns a boolean expression where
	 * this property is <b>not equal to</b> null. 
	 * @param value the literal
	 * @return the boolean expression
	 */
	public Expression isNotNull();

	/**
	 * Returns a subquery expression, where
	 * this property is <b>found</b> within the given subquery results collection.
	 * @param subquery the subquery
	 * @return the subquery expression
	 */
	public Expression in(Query subquery);

	/**
	 * Returns a subquery expression, where
	 * this property is <b>not found</b> within the given subquery results collection.
	 * @param subquery the subquery
	 * @return the subquery expression
	 */
	public Expression notIn(Query subquery);

	/**
	 * Constructs a minimum aggregate within this data property and returns 
	 * the data property for use in subsequent operations 
	 * @return the data property
	 */
	public DataProperty min();
	/**
	 * Constructs a maximum aggregate within this data property and returns 
	 * the data property for use in subsequent operations 
	 * @return the data property
	 */
	public DataProperty max();
	/**
	 * Constructs a summation aggregate within this data property and returns 
	 * the data property for use in subsequent operations 
	 * @return the data property
	 */
	public DataProperty sum();
	
	/**
	 * Constructs a average aggregate within this data property and returns 
	 * the data property for use in subsequent operations 
	 * @return the data property
	 */
	public DataProperty avg();
	
	/**
	 * Constructs a ascending ordering within this data property and returns 
	 * the data property for use in subsequent operations 
	 * @return the data property
	 */
	public DataProperty asc();
	/**
	 * Constructs a descending ordering within this data property and returns 
	 * the data property for use in subsequent operations 
	 * @return the data property
	 */
	public DataProperty desc();

	/**
	 * returns the name of this property
	 * @return
	 */
	public String getName();
	
	

	/**
	 * Gets the value of the distinct property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link Boolean }
	 *     
	 */
	public boolean isDistinct();

	/**
	 * Sets the value of the distinct property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link Boolean }
	 *     
	 */
	//public void setDistinct(Boolean value);

	/**
	 * Gets the value of the direction property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link SortDirectionValues }
	 *     
	 */
	//public SortDirectionValues getDirection();

	/**
	 * Sets the value of the direction property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link SortDirectionValues }
	 *     
	 */
	//public void setDirection(SortDirectionValues value);

	/**
	 * Gets the value of the function property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link FunctionValues }
	 *     
	 */
	//public FunctionValues getFunction();

	/**
	 * Sets the value of the function property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link FunctionValues }
	 *     
	 */
	//public void setFunction(FunctionValues value);

}