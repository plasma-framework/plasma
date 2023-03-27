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
 * Java class for typeDerivationControl.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="typeDerivationControl">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}derivationControl">
 *     &lt;enumeration value="extension"/>
 *     &lt;enumeration value="restriction"/>
 *     &lt;enumeration value="list"/>
 *     &lt;enumeration value="union"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "typeDerivationControl")
@XmlEnum(DerivationControl.class)
public enum TypeDerivationControl {

  @XmlEnumValue("extension")
  EXTENSION(DerivationControl.EXTENSION), @XmlEnumValue("restriction")
  RESTRICTION(DerivationControl.RESTRICTION), @XmlEnumValue("list")
  LIST(DerivationControl.LIST), @XmlEnumValue("union")
  UNION(DerivationControl.UNION);
  private final DerivationControl value;

  TypeDerivationControl(DerivationControl v) {
    value = v;
  }

  public DerivationControl value() {
    return value;
  }

  public static TypeDerivationControl fromValue(DerivationControl v) {
    for (TypeDerivationControl c : TypeDerivationControl.values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v.toString());
  }

}
