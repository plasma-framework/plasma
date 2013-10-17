package org.plasma.query.collector;

import java.util.List;
import java.util.Set;

import org.plasma.query.model.Where;

import commonj.sdo.Type;


/**
 * Provides access to path predicates for a query graph with operations useful for
 * service implementors.   
 * @see org.plasma.query.model.Select 
 * @see commonj.sdo.Type
 * @see commonj.sdo.Property
 *  
 * @author Scott Cinnamond
 * @since 1.1.5
 */
public interface SelectionPredicates {
          
	
	/**
	 * Traverses the given predicate adding its properties
	 * and property paths to the selection.
	 * @param predicate the predicate
	 */
	public void collect(Where predicate);
	
	/**
	 * Returns true if the selection has path predicates. 
	 * @return true if the selection has path predicates.
	 */
	public boolean hasPredicates();
	
	/**
	 * Returns the predicate for the given property or null
	 * if the given property is not mapped. 
	 * @param property the property
	 * @return the predicate for the given property or null
	 * if the given property is not mapped.
	 */
	public Where getPredicate(commonj.sdo.Property property);
}
