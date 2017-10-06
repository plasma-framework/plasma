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

package org.plasma.sdo.xml;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;

class StreamProperty extends StreamNode {
  private PlasmaProperty property;
  private Object value;

  public StreamProperty(PlasmaType type, PlasmaProperty property, QName name, Location loc) {
    super(type, name, loc);
    this.property = property;
  }

  public Object get() {
    return value;
  }

  public void set(Object value) {
    if (value == null)
      throw new IllegalArgumentException("expected non-null value arg");
    this.value = value;
  }

  public PlasmaProperty getProperty() {
    return property;
  }
}
