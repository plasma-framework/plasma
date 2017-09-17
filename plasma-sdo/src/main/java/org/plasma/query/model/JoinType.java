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
 * Java class for JoinType.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="JoinType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="inner"/>
 *     &lt;enumeration value="left_outer"/>
 *     &lt;enumeration value="right_outer"/>
 *     &lt;enumeration value="cross"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "JoinType")
@XmlEnum
public enum JoinType {

  @XmlEnumValue("inner")
  INNER("inner"), @XmlEnumValue("left_outer")
  LEFT___OUTER("left_outer"), @XmlEnumValue("right_outer")
  RIGHT___OUTER("right_outer"), @XmlEnumValue("cross")
  CROSS("cross");
  private final String value;

  JoinType(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static JoinType fromValue(String v) {
    for (JoinType c : JoinType.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
