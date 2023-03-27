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
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for PredicateOperatorName.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="PredicateOperatorName"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="LIKE"/&gt;
 *     &lt;enumeration value="IN"/&gt;
 *     &lt;enumeration value="NOT_IN"/&gt;
 *     &lt;enumeration value="EXISTS"/&gt;
 *     &lt;enumeration value="NOT_EXISTS"/&gt;
 *     &lt;enumeration value="BETWEEN"/&gt;
 *     &lt;enumeration value="SIMILAR"/&gt;
 *     &lt;enumeration value="NULL"/&gt;
 *     &lt;enumeration value="UNIQUE"/&gt;
 *     &lt;enumeration value="MATCH"/&gt;
 *     &lt;enumeration value="DISTINCT"/&gt;
 *     &lt;enumeration value="CONTAINS"/&gt;
 *     &lt;enumeration value="APP_OTHER_NAME"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PredicateOperatorName")
@XmlEnum
public enum PredicateOperatorName {

  LIKE, IN, NOT_IN, EXISTS, NOT_EXISTS, BETWEEN, SIMILAR, NULL, UNIQUE, MATCH, DISTINCT, CONTAINS, APP_OTHER_NAME;

  public String value() {
    return name();
  }

  public static PredicateOperatorName fromValue(String v) {
    return valueOf(v);
  }

}
