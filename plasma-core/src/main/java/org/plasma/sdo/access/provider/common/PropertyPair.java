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

package org.plasma.sdo.access.provider.common;

import org.plasma.sdo.PlasmaProperty;

/**
 * Associates an SDO property with a data value and optional 
 * column index or sequence.
 */
/**
 * @author scinnamond
 *
 */
public class PropertyPair {
  private PlasmaProperty prop;
  /**
   * the property which corresponds to the value. Can be null in which case the
   * original property is used
   */
  private PlasmaProperty valueProp;
  private Object value;
  /**
   * The previous value, important when key properties are modified and the old
   * key value is required to fetch and obtain a lock on the entity meing
   * modified.
   */
  private Object oldValue;
  /** the JDBC column id */
  private int column;
  /** the JDBC column id associated with the old value */
  private int oldValueColumn;
  private String propertyPath;
  /** whether the property was explicitly named in the originating query */
  private boolean queryProperty = true;

  @SuppressWarnings("unused")
  private PropertyPair() {
  }

  public PropertyPair(PlasmaProperty prop, Object value) {
    this.prop = prop;
    this.value = value;
    if (this.prop == null)
      throw new IllegalArgumentException("expected non-null 'prop' argument");
    if (this.value == null)
      throw new IllegalArgumentException("expected non-null 'value' argument");
  }

  public PropertyPair(PlasmaProperty prop, Object value, Object oldValue) {
    this(prop, value);
    this.oldValue = oldValue;
    if (this.oldValue == null)
      throw new IllegalArgumentException("expected non-null 'oldValue' argument");
  }

  public PlasmaProperty getProp() {
    return prop;
  }

  public Object getValue() {
    return value;
  }

  public Object getOldValue() {
    return oldValue;
  }

  public void setOldValue(Object oldValue) {
    this.oldValue = oldValue;
  }

  public int getColumn() {
    return column;
  }

  public PlasmaProperty getValueProp() {
    return valueProp;
  }

  public void setValueProp(PlasmaProperty valueProp) {
    this.valueProp = valueProp;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public int getOldValueColumn() {
    return oldValueColumn;
  }

  public void setOldValueColumn(int oldValueColumn) {
    this.oldValueColumn = oldValueColumn;
  }

  public String getPropertyPath() {
    return propertyPath;
  }

  public void setPropertyPath(String propertyPath) {
    this.propertyPath = propertyPath;
  }

  public String toString() {
    return this.prop.getName() + "/" + String.valueOf(this.value);
  }

  public boolean isQueryProperty() {
    return queryProperty;
  }

  public void setQueryProperty(boolean queryProperty) {
    this.queryProperty = queryProperty;
  }

}
