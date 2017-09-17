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
 * Java class for BooleanOperatorValues.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="BooleanOperatorValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="equals"/>
 *     &lt;enumeration value="notEquals"/>
 *     &lt;enumeration value="greaterThan"/>
 *     &lt;enumeration value="greaterThanEquals"/>
 *     &lt;enumeration value="lessThan"/>
 *     &lt;enumeration value="lessThanEquals"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RelationalOperatorValues")
@XmlEnum
public enum RelationalOperatorName {

  @XmlEnumValue("equals")
  EQUALS("equals"), @XmlEnumValue("notEquals")
  NOT_EQUALS("notEquals"), @XmlEnumValue("greaterThan")
  GREATER_THAN("greaterThan"), @XmlEnumValue("greaterThanEquals")
  GREATER_THAN_EQUALS("greaterThanEquals"), @XmlEnumValue("lessThan")
  LESS_THAN("lessThan"), @XmlEnumValue("lessThanEquals")
  LESS_THAN_EQUALS("lessThanEquals");
  private final String value;

  RelationalOperatorName(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static RelationalOperatorName fromValue(String v) {
    for (RelationalOperatorName c : RelationalOperatorName.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
