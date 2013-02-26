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
package org.plasma.query.dsl;

import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.Query;
import org.plasma.query.Wildcard;

/**
 * A domain specific query serving as the entry point for
 * assembly of a data graph. 
 */
public interface DomainQuery extends Query {
	
	/**
	 * Appends the given property to the select clause
	 * within this query and returns the query.
	 * @param property the property
	 * @return the query
	 */
	public DomainQuery select(DataProperty property);

	/**
	 * Appends the given wildcard property to the select clause
	 * within this query and returns the query.
	 * @param property the wildcard property
	 * @return the query
	 */
	public DomainQuery select(Wildcard property);
	
	/**
	 * Appends the given expression to the where clause
	 * within this query and returns the query.
	 * @param expr the expression
	 * @return the query
	 */
	public DomainQuery where(Expression expr);
	
	/**
	 * Appends the given data property to the order by clause
	 * within this query and returns the query.
	 * @param property the data property
	 * @return the query
	 */
	public DomainQuery orderBy(DataProperty property);
	
	/**
	 * Appends the given data property to the group by clause
	 * within this query and returns the query.
	 * @param property the data property
	 * @return the query
	 */
	public DomainQuery groupBy(DataProperty property);

}
