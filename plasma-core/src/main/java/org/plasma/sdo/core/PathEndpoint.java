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

package org.plasma.sdo.core;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * Stores the property which is the end point of a path in association with the
 * data object which was its "source" along the path.
 */
public class PathEndpoint {
  private Property property;
  private DataObject source;

  @SuppressWarnings("unused")
  private PathEndpoint() {
  }

  public PathEndpoint(Property property, DataObject source) {
    super();
    this.property = property;
    this.source = source;
  }

  public Property getProperty() {
    return property;
  }

  public DataObject getSource() {
    return source;
  }
}
