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

import org.plasma.query.model.Where;

/**
 * Provides access to path predicates for a query graph with operations useful
 * for service implementors.
 * 
 * @see org.plasma.query.model.Select
 * @see commonj.sdo.Type
 * @see commonj.sdo.Property
 * 
 * @author Scott Cinnamond
 * @since 1.1.5
 */
public interface SelectionPredicates {

  /**
   * Traverses the given predicate adding its properties and property paths to
   * the selection.
   * 
   * @param predicate
   *          the predicate
   */
  public void collect(Where predicate);

  /**
   * Returns true if the selection has path predicates.
   * 
   * @return true if the selection has path predicates.
   */
  public boolean hasPredicates();

  /**
   * Returns the predicate for the given property or null if the given property
   * is not mapped.
   * 
   * @param property
   *          the property
   * @return the predicate for the given property or null if the given property
   *         is not mapped.
   */
  public Where getPredicate(commonj.sdo.Property property);
}
