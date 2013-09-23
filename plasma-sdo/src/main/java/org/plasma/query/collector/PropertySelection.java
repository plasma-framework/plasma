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
package org.plasma.query.collector;

import java.util.List;

import org.plasma.query.model.Where;

import commonj.sdo.Type;

/**
 * Provides access to various property mapping data collected
 * from a selection graph. 
 */
@Deprecated
public interface PropertySelection {

	/**
	 * Traverses the given predicate adding its properties
	 * and property paths to the selection.
	 * @param predicate the predicate
	 */
	public void collect(Where predicate);
	
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

	/**
	 * Adds the given given property to the 
	 * selection for the given graph root type and
	 * returns any types collected during traversal
	 * of the property path. 
	 * @param rootType the graph root type
	 * @param path the SDO XPath specifying a path from the given root type
	 * to a target or endpoint property 
	 * @return any types collected during traversal
	 * of the property path.
	 */
	public List<Type> addProperty(Type rootType, String path);
}