package org.plasma.query;


/**
 * A base expression  
 */
public interface Expression {
	
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
