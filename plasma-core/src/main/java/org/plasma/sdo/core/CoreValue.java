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

import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaValue;

public class CoreValue implements PlasmaValue {
  private PlasmaProperty property;
  private Object value;

  @SuppressWarnings("unused")
  private CoreValue() {
  }

  public CoreValue(PlasmaProperty property, Object value) {
    super();
    this.property = property;
    this.value = value;
  }

  public PlasmaProperty getProperty() {
    return property;
  }

  public Object getValue() {
    return value;
  }
}
