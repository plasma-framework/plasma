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

import org.plasma.query.model.Function;
import org.plasma.query.model.FunctionName;
import org.plasma.query.model.Path;
import org.plasma.sdo.DataType;

import commonj.sdo.Property;

/**
 * Groups a specific property path with a single query scalar function
 * declaration.
 * 
 * @author Scott Cinnamond
 * @since 2.0.5
 * */
public class FunctionPath {
  private Function func;
  private Path path;
  private Property property;

  @SuppressWarnings("unused")
  private FunctionPath() {
  }

  public FunctionPath(Function func, Path path, Property property) {
    super();
    this.func = func;
    this.path = path;
    this.property = property;
    if (!this.property.getType().isDataType())
      throw new IllegalArgumentException("expected datatype property not, " + this.property);
  }

  public Function getFunc() {
    return func;
  }

  public Path getPath() {
    return path;
  }

  public Property getProperty() {
    return property;
  }

  public DataType getDataType() {
    return DataType.valueOf(this.property.getType().getName());
  }

}
