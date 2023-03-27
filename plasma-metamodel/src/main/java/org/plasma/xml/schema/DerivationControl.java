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

package org.plasma.xml.schema;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for derivationControl.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="derivationControl">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="substitution"/>
 *     &lt;enumeration value="extension"/>
 *     &lt;enumeration value="restriction"/>
 *     &lt;enumeration value="list"/>
 *     &lt;enumeration value="union"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "derivationControl")
@XmlEnum
public enum DerivationControl {

  @XmlEnumValue("substitution")
  SUBSTITUTION("substitution"), @XmlEnumValue("extension")
  EXTENSION("extension"), @XmlEnumValue("restriction")
  RESTRICTION("restriction"), @XmlEnumValue("list")
  LIST("list"), @XmlEnumValue("union")
  UNION("union");
  private final String value;

  DerivationControl(String v) {
    value = v;
  }

  public String value() {
    return value;
  }

  public static DerivationControl fromValue(String v) {
    for (DerivationControl c : DerivationControl.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }

}
