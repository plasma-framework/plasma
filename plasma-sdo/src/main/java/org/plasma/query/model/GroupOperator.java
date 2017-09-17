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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupOperator", propOrder = { "value" })
@XmlRootElement(name = "GroupOperator")
public class GroupOperator implements org.plasma.query.Operator {

  @XmlValue
  protected GroupOperatorName value;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public GroupOperator() {
    super();
  }

  public GroupOperator(String oper) {
    super();
    value = GroupOperatorName.valueOf(oper);
  }

  public GroupOperator(GroupOperatorName oper) {
    super();
    value = oper;
  }

  /**
   * Gets the value of the value property.
   * 
   * @return possible object is {@link GroupOperatorName }
   * 
   */
  public GroupOperatorName getValue() {
    return value;
  }

  /**
   * Sets the value of the value property.
   * 
   * @param value
   *          allowed object is {@link GroupOperatorName }
   * 
   */
  public void setValue(GroupOperatorName value) {
    this.value = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    visitor.end(this);
  }

}
