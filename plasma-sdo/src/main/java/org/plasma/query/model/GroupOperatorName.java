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
 * Java class for GroupOperatorValues.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="GroupOperatorValues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LP1"/>
 *     &lt;enumeration value="LP2"/>
 *     &lt;enumeration value="LP3"/>
 *     &lt;enumeration value="RP1"/>
 *     &lt;enumeration value="RP2"/>
 *     &lt;enumeration value="RP3"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GroupOperatorValues")
@XmlEnum
public enum GroupOperatorName {

  @XmlEnumValue("LP1")
  LP_1("LP1"), @XmlEnumValue("LP2")
  LP_2("LP2"), @XmlEnumValue("LP3")
  LP_3("LP3"), @XmlEnumValue("RP1")
  RP_1("RP1"), @XmlEnumValue("RP2")
  RP_2("RP2"), @XmlEnumValue("RP3")
  RP_3("RP3");
  private final String value;

  GroupOperatorName(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static GroupOperatorName fromValue(String v) {
    for (GroupOperatorName c : GroupOperatorName.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
