package org.plasma.query.collector;

import java.util.List;

import org.plasma.query.model.Where;

import commonj.sdo.Type;

/**
 * Provides access to various property mapping data collected
 * from a selection graph. 
 */
public interface PropertySelection {

	/**
	 * Returns the predicate for the given property or null
	 * if the given property is not mapped. 
	 * @param property the property
	 * @return the predicate for the given property or null
	 * if the given property is not mapped.
	 */
	public Where getPredicate(commonj.sdo.Property property);

	/**
	 * Returns a list of only singular data and reference 
	 * property names collected for the given type.
	 * @param type the type
	 * @return a list of only singular data and reference 
	 * property names collected for the given type.
	 */
	public List<String> getSingularProperties(Type type);

	/**
	 * Returns a list of data and reference 
	 * property names collected for the given type.
	 * @param type the type
	 * @return a list of data and reference 
	 * property names collected for the given type.
	 */
	public List<String> getProperties(Type type);

	/**
	 * Returns a list of data and reference 
	 * property names collected specifically for 
	 * the given type or collected for any base type
	 * of the given type.  
	 * @param type the type
	 * @return a list of data and reference 
	 * property names collected specifically for 
	 * the given type or collected for any base type
	 * of the given type.
	 */
	public List<String> getInheritedProperties(Type type);

	/**
	 * Returns all selected types. 
	 * @return all selected types.
	 */
	public List<Type> getTypes();

	/**
	 * Returns true if the given type is found in the
	 * type selection.
	 * @param type the type
	 * @return true if the given type is found in the
	 * type selection.
	 */
	public boolean hasType(Type type);

	/**
	 * Returns all selected types and as well as types 
	 * specialize or inherit from the selected types. 
	 * @return  all selected types and as well as types 
	 * specialize or inherit from the selected types.
	 */
	public List<Type> getInheritedTypes();

	/**
	 * Returns true if the given type is found in the
	 * inherited type selection.
	 * @param type the type
	 * @return true if the given type is found in the
	 * inherited type selection.
	 */
	public boolean hasInheritedType(Type type);
	
	
	/**
	 * Returns true if the given type is found in the type selection and if the
	 * given property is found in the property selection for the
	 * given type. 
	 * @param type the type
	 * @param property the property
	 * @return true if the given type is found in the type selection and if the
	 * given property is found in the property selection for the
	 * given type. 
	 */
	public boolean hasProperty(Type type, commonj.sdo.Property property);

	/**
	 * Returns true if the given type is found in the inherited type selection and if the
	 * given property is found in the property selection for the
	 * given type. 
	 * @param type the type
	 * @param property the property
	 * @return true if the given type is found in the inherited type selection and if the
	 * given property is found in the property selection for the
	 * given type. 
	 */
	public boolean hasInheritedProperty(Type type, commonj.sdo.Property property);
}