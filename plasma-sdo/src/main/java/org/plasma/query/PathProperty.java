package org.plasma.query;


/**
 * A reference (or non data type) property which constitutes part
 * of a path within a data graph
 */
public interface PathProperty extends Property {

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
	
}
