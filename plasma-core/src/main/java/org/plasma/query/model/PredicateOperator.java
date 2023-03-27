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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PredicateOperator", propOrder = { "value" })
@XmlRootElement(name = "PredicateOperator")
public class PredicateOperator implements org.plasma.query.Operator {

  @XmlValue
  protected PredicateOperatorName value;

  /**
   * Default constructor for marshalling only !!
   */
  public PredicateOperator() {
    super();
  }

  public PredicateOperator(PredicateOperatorName operatorName) {
    super();
    value = operatorName;
  }

  /**
   * Gets the value of the value property.
   * 
   * @return possible object is {@link PredicateOperatorName }
   * 
   */
  public PredicateOperatorName getValue() {
    return value;
  }

  /**
   * Sets the value of the value property.
   * 
   * @param value
   *          allowed object is {@link PredicateOperatorName }
   * 
   */
  public void setValue(PredicateOperatorName value) {
    this.value = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    visitor.end(this);
  }

}
