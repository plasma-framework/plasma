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

import java.util.HashMap;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

import org.plasma.query.QueryException;
import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationalOperator", propOrder = { "value" })
@XmlRootElement(name = "RelationalOperator")
public class RelationalOperator implements org.plasma.query.Operator {

  private static Map<String, RelationalOperatorName> operMap = new HashMap<>();
  static {
    operMap.put("=", RelationalOperatorName.EQUALS);
    operMap.put("!=", RelationalOperatorName.NOT_EQUALS);
    operMap.put(">", RelationalOperatorName.GREATER_THAN);
    operMap.put(">=", RelationalOperatorName.GREATER_THAN_EQUALS);
    operMap.put("<", RelationalOperatorName.LESS_THAN);
    operMap.put("<=", RelationalOperatorName.LESS_THAN_EQUALS);
  }

  @XmlValue
  protected RelationalOperatorName value;

  public RelationalOperator() {
    super();
  }

  public RelationalOperator(String content) {
    this();
    setValue(RelationalOperatorName.valueOf(content));
  }

  public RelationalOperator(RelationalOperatorName content) {
    this();
    setValue(content);
  }

  public static RelationalOperator valueOf(String value) {
    RelationalOperatorName oper = operMap.get(value);
    if (oper != null)
      return new RelationalOperator(oper);
    else
      throw new QueryException("invalid operator '" + value + "'");
  }

  /**
   * Gets the value of the value property.
   * 
   * @return possible object is {@link RelationalOperatorName }
   * 
   */
  public RelationalOperatorName getValue() {
    return value;
  }

  /**
   * Sets the value of the value property.
   * 
   * @param value
   *          allowed object is {@link RelationalOperatorName }
   * 
   */
  public void setValue(RelationalOperatorName value) {
    this.value = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    visitor.end(this);
  }
}
