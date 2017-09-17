/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.query.collector;

import java.util.List;
import java.util.Set;

import org.plasma.query.model.Function;
import org.plasma.query.model.Where;

import commonj.sdo.Type;

/**
 * A selection provides access to a query graph with operations useful for
 * service implementors, providing property names for a given Type but also
 * operations which return the selected properties at a given (query) graph
 * level, and for a given source edge Type. All properties are returned as a Set
 * and implementations will typically return a HashSet.
 * 
 * @see org.plasma.query.model.Select
 * @see commonj.sdo.Type
 * @see commonj.sdo.Property
 * 
 * @author Scott Cinnamond
 * @since 1.1.4
 */
public interface Selection {

  /**
   * Returns all selected types.
   * 
   * @return all selected types.
   */
  public List<Type> getTypes();

  /**
   * Returns true if the given type is found in the type selection.
   * 
   * @param type
   *          the type
   * @return true if the given type is found in the type selection.
   */
  public boolean hasType(Type type);

  /**
   * Returns all selected types and as well as types which specialize or inherit
   * from the selected types.
   * 
   * @return all selected types and as well as types which specialize or inherit
   *         from the selected types.
   */
  public List<Type> getInheritedTypes();

  /**
   * Returns true if the given type is found in the inherited type selection.
   * 
   * @param type
   *          the type
   * @return true if the given type is found in the inherited type selection.
   */
  public boolean hasInheritedType(Type type);

  /**
   * Returns true if the given type is found in the type selection and if the
   * given property is found in the property selection for the given type.
   * 
   * @param type
   *          the type
   * @param property
   *          the property
   * @return true if the given type is found in the type selection and if the
   *         given property is found in the property selection for the given
   *         type.
   */
  public boolean hasProperty(Type type, commonj.sdo.Property property);

  /**
   * Returns true if the given type is found in the inherited type selection and
   * if the given property is found in the property selection for the given
   * type.
   * 
   * @param type
   *          the type
   * @param property
   *          the property
   * @return true if the given type is found in the inherited type selection and
   *         if the given property is found in the property selection for the
   *         given type.
   */
  public boolean hasInheritedProperty(Type type, commonj.sdo.Property property);

  /**
   * Adds the given given property to the selection for the given graph root
   * type and returns any types collected during traversal of the property path.
   * 
   * @param rootType
   *          the graph root type
   * @param path
   *          the SDO XPath specifying a path from the given root type to a
   *          target or endpoint property
   * @return any types collected during traversal of the property path.
   */
  public List<Type> addProperty(Type rootType, String path);

  /**
   * Returns the unique set of data and reference properties collected for the
   * given type irrespective of the (query) graph level, or source graph edge
   * type.
   * 
   * @param type
   *          the type
   * @return the unique set of data and reference properties collected for the
   *         given type irrespective of the (query) graph level, or source graph
   *         edge type.
   */
  public Set<commonj.sdo.Property> getProperties(Type type);

  /**
   * Returns the unique set of singular data and reference properties collected
   * for the given type irrespective of the (query) graph level, or source graph
   * edge type.
   * 
   * @param type
   *          the type
   * @return the unique set of singular data and reference properties collected
   *         for the given type irrespective of the (query) graph level, or
   *         source graph edge type.
   */
  public Set<commonj.sdo.Property> getSingularProperties(Type type);

  /**
   * Returns the unique set of inherited data and reference properties collected
   * for the given type, and any base types of the given type, irrespective of
   * the (query) graph level. of the given type
   * 
   * @param type
   *          the type
   * @return the unique set of inherited data and reference properties collected
   *         for the given type, and any base types of the given type,
   *         irrespective of the (query) graph level
   */
  public Set<commonj.sdo.Property> getInheritedProperties(Type type);

  /**
   * Returns the unique set of inherited data and reference properties collected
   * for the given type, and any base types of the given type, for the given
   * (query) graph level.
   * 
   * @param type
   *          the type
   * @param level
   *          the specific graph level where the properties were specified in
   *          the selection
   * @return the unique set of inherited data and reference properties collected
   *         for the given type, and any base types of the given type,
   *         irrespective of the (query) graph level
   */
  public Set<commonj.sdo.Property> getInheritedProperties(Type type, int level);

  /**
   * Returns the unique set of inherited data and reference properties collected
   * for the given Type for the given (query) graph edge source property.
   * 
   * @param type
   *          the type (query) graph edge source property
   * @param sourceProperty
   *          the
   * @param level
   *          the specific graph level where the properties were specified in
   *          the selection
   * @return the unique set of inherited data and reference properties collected
   *         for the given Type for the given (query) graph edge source
   *         property.
   */
  public Set<commonj.sdo.Property> getInheritedProperties(Type type,
      commonj.sdo.Property sourceProperty);

  /**
   * Returns the unique set of inherited data and reference properties collected
   * for the given Type for the given (query) graph edge source property at the
   * given traversal level or graph depth.
   * 
   * @param type
   *          the type (query) graph edge source property
   * @param sourceProperty
   *          the
   * @param level
   *          the traversal level or graph depth
   * @return the unique set of inherited data and reference properties collected
   *         for the given Type for the given (query) graph edge source
   *         property.
   */
  public Set<commonj.sdo.Property> getInheritedProperties(Type type,
      commonj.sdo.Property sourceProperty, int level);

  /**
   * Returns the unique set of data and reference properties collected for the
   * given Type for the given (query) graph level.
   * 
   * @param type
   *          the type
   * @param level
   *          the specific graph level where the properties were specified in
   *          the selection
   * @return the unique set of data and reference properties collected for the
   *         given type for the given (query) graph level.
   */
  public Set<commonj.sdo.Property> getProperties(Type type, int level);

  /**
   * Returns the unique set of data and reference properties collected for the
   * given Type for the given (query) graph edge source property.
   * 
   * @param type
   *          the type (query) graph edge source property
   * @param sourceProperty
   *          the (query) graph edge source property
   * @return the unique set of data and reference properties collected for the
   *         given Type for the given (query) graph edge source property.
   */
  public Set<commonj.sdo.Property> getProperties(Type type, commonj.sdo.Property sourceProperty);

  /**
   * Returns the unique set of data and reference properties collected for the
   * given Type for the given (query) graph edge source property at the given
   * traversal level or graph depth.
   * 
   * @param type
   *          the type (query) graph edge source property
   * @param sourceProperty
   *          the (query) graph edge source property
   * @param level
   *          the traversal level or graph depth
   * @return the unique set of data and reference properties collected for the
   *         given Type for the given (query) graph edge source property.
   */
  public Set<commonj.sdo.Property> getProperties(Type type, commonj.sdo.Property sourceProperty,
      int level);

  /**
   * Returns true if the selection has path predicates.
   * 
   * @return true if the selection has path predicates.
   */
  public boolean hasPredicates();

  /**
   * Returns the predicate for the given property or null if the given property
   * has no predicates.
   * 
   * @param property
   *          the property
   * @return the predicate for the given property or null if the given property
   *         has no predicates.
   */
  public Where getPredicate(commonj.sdo.Property property);

  /**
   * Returns the predicate, if exists, collected for the given property and for
   * the given (query) graph edge source property.
   * 
   * @param property
   *          the source property
   * @param sourceProperty
   *          the (query) graph edge source property
   * @return the predicate, if exists
   */
  public Where getPredicate(commonj.sdo.Property property, commonj.sdo.Property sourceProperty);

  /**
   * Returns the predicate , if exists, collected for the given property for the
   * given traversal level or graph depth.
   * 
   * @param type
   *          the type (query) graph edge source property
   * @param sourceProperty
   *          the
   * @param level
   *          the traversal level or graph depth
   * @return the predicate, if exists
   */
  public Where getPredicate(commonj.sdo.Property property, int level);

  /**
   * Returns the functions for the given property or empty list if the given
   * property has no functions.
   * 
   * @param property
   *          the property
   * @return the predicate for the given property or empty list if the given
   *         property has no functions.
   */
  public List<Function> getFunctions(commonj.sdo.Property property);

  /**
   * Returns the functions for the given property for the given traversal level
   * or graph depth, or empty list if the given property has no functions.
   * 
   * @param property
   *          the property
   * @param level
   *          the traversal level or graph depth
   * @return the predicate for the given property for the given traversal level
   *         or graph depth, or empty list if the given property has no
   *         functions.
   */
  public List<Function> getFunctions(commonj.sdo.Property property, int level);
}
