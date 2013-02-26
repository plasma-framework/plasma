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
 * A base expression  
 */
public interface Expression extends Term {
	
	/**
	 * Concatenates the given expression onto this expression using
	 * a logical 'and' operator.
	 * @param expr the expression
	 * @return the original expression
	 */
	public Expression and(Expression expr);
	
	/**
	 * Concatenates the given expression onto this expression using
	 * a logical 'or' operator.
	 * @param expr the expression
	 * @return the original expression
	 */
	public Expression or(Expression expr);
	
	/**
	 * Places this expression within grouping operators 
	 * and returns the grouped expression.
	 * @return the grouped expression
	 */
	public Expression group();
}
