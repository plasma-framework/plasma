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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Update", propOrder = { "entity", "expressions" })
@XmlRootElement(name = "Update")
public class Update {

  @XmlElement(name = "Entity", required = true)
  protected Entity entity;
  @XmlElement(name = "Expression", required = true)
  protected List<Expression> expressions;

  public Update() {
    super();
  }

  public Update(Expression expr) {
    this();
    getExpressions().add(expr);
  }

  public Update(Expression[] exprs) {
    this();
    getExpressions().add(new Expression(exprs));
  }

  /**
   * Gets the value of the entity property.
   * 
   * @return possible object is {@link Entity }
   * 
   */
  public Entity getEntity() {
    return entity;
  }

  /**
   * Sets the value of the entity property.
   * 
   * @param value
   *          allowed object is {@link Entity }
   * 
   */
  public void setEntity(Entity value) {
    this.entity = value;
  }

  public void addExpression(Expression e) {
    getExpressions().add(e);
  }

  public List<Expression> getExpressions() {
    if (expressions == null) {
      expressions = new ArrayList<Expression>();
    }
    return this.expressions;
  }

  public int getExpressionCount() {
    return getExpressions().size();
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal()) {
      for (int i = 0; i < this.getExpressions().size(); i++)
        ((Expression) this.getExpressions().get(i)).accept(visitor);
    }
    visitor.end(this);
  }
}
