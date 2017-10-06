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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.visitor.QueryVisitor;

/**
 * <p>
 * Java class for ArithmeticOperator complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ArithmeticOperator">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.terrameta.org/plasma/query>ArithmeticOperatorValues">
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArithmeticOperator", propOrder = { "value" })
@XmlRootElement(name = "ArithmeticOperator")
public class ArithmeticOperator implements org.plasma.query.Operator {

  @XmlValue
  protected ArithmeticOperatorName value;

  public ArithmeticOperator() {
    super();
  }

  public ArithmeticOperator(String content) {
    this();
    setValue(ArithmeticOperatorName.valueOf(content));
  }

  public ArithmeticOperator(ArithmeticOperatorName content) {
    this();
    setValue(content);
  }

  /**
   * Gets the value of the value property.
   * 
   * @return possible object is {@link ArithmeticOperatorName }
   * 
   */
  public ArithmeticOperatorName getValue() {
    return value;
  }

  /**
   * Sets the value of the value property.
   * 
   * @param value
   *          allowed object is {@link ArithmeticOperatorName }
   * 
   */
  public void setValue(ArithmeticOperatorName value) {
    this.value = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    visitor.end(this);
  }
}
