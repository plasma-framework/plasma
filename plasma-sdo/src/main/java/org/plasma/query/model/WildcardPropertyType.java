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

package org.plasma.query.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for WildcardPropertyTypeValues.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="WildcardPropertyTypeValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="all"/>
 *     &lt;enumeration value="reference"/>
 *     &lt;enumeration value="data"/>
 *     &lt;enumeration value="subclass_data"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WildcardPropertyTypeValues")
@XmlEnum
public enum WildcardPropertyType {

  @XmlEnumValue("all")
  ALL("all"), @XmlEnumValue("reference")
  REFERENCE("reference"), @XmlEnumValue("data")
  DATA("data"), @XmlEnumValue("subclass_data")
  SUBCLASS___DATA("subclass_data");
  private final String value;

  WildcardPropertyType(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static WildcardPropertyType fromValue(String v) {
    for (WildcardPropertyType c : WildcardPropertyType.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
