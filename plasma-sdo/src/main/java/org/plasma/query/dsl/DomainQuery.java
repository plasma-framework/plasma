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
