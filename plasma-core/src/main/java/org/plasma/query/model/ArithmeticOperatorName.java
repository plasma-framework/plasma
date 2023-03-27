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

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArithmeticOperatorValues.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="ArithmeticOperatorValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="minus"/>
 *     &lt;enumeration value="plus"/>
 *     &lt;enumeration value="div"/>
 *     &lt;enumeration value="mod"/>
 *     &lt;enumeration value="mult"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ArithmeticOperatorValues")
@XmlEnum
public enum ArithmeticOperatorName {

  @XmlEnumValue("minus")
  MINUS("minus"), @XmlEnumValue("plus")
  PLUS("plus"), @XmlEnumValue("div")
  DIV("div"), @XmlEnumValue("mod")
  MOD("mod"), @XmlEnumValue("mult")
  MULT("mult");
  private final String value;

  ArithmeticOperatorName(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static ArithmeticOperatorName fromValue(String v) {
    for (ArithmeticOperatorName c : ArithmeticOperatorName.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
