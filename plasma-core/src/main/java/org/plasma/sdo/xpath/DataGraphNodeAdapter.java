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

package org.plasma.sdo.xpath;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * Wraps a result or "endpoint" of a Jaxen XPATH parse, supplying source
 * traversal information, e.g. the Data Object and Property which served as the
 * source or "parent" of a particular traversal path.
 */
public abstract class DataGraphNodeAdapter {
  protected DataObject source;
  protected Property sourceProperty;

  @SuppressWarnings("unused")
  private DataGraphNodeAdapter() {
  }

  public DataGraphNodeAdapter(DataObject source, Property sourceProperty) {
    super();
    this.source = source;
    this.sourceProperty = sourceProperty;
    if (this.source == null)
      throw new IllegalArgumentException("expected non-null argument 'source'");
    if (this.sourceProperty == null)
      throw new IllegalArgumentException("expected non-null argument 'sourceProperty'");
  }

  /**
   * Returns the dereferenced value of an XPATH parse result. This method
   * generalizes how subclasses containing either data xpath result (e.g. a Data
   * Object or value) or meta-data xpath results (e.g. a Property) return or
   * dereference the results differently.
   * 
   * @return the result
   */
  public abstract Object get();

  /**
   * Sets the given value into the XPATH parse result. Abstract method to
   * generalize how subclasses containing either data xpath result (e.g. a Data
   * Object or value) or meta-data xpath results (e.g. a Property) dereference
   * the results differently.
   */
  public abstract void set(Object value);

  /**
   * Returns the data object traversal source
   * 
   * @return the source data object
   */
  public DataObject getSource() {
    return this.source;
  }

  /**
   * Returns the property traversal source
   * 
   * @return the source property
   */
  public Property getSourceProperty() {
    return this.sourceProperty;
  }
}
