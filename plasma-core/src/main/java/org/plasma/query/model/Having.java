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
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

/**
 * <p>
 * Java class for Having complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Having"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.terrameta.org/plasma/query}Expression" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Having", propOrder = { "expression" })
public class Having implements org.plasma.query.Having, Predicates {

  @XmlElement(name = "Expression", required = true)
  protected List<Expression> expression;

  public Having() {
    super();
  }

  public Having(Expression expr) {
    this();
    getExpressions().add(expr);
  }

  public Having(Expression[] exprs) {
    this();
    getExpressions().add(new Expression(exprs));
  }

  public void addExpression(Expression e) {
    getExpressions().add(e);
  }

  /**
   * Gets the value of the expression property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the expression property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getExpression().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link Expression }
   * 
   * 
   */
  public List<Expression> getExpressions() {
    if (expression == null) {
      expression = new ArrayList<Expression>();
    }
    return this.expression;
  }

  public int getExpressionCount() {
    return getExpressions().size();
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
      for (int i = 0; i < this.getExpressions().size(); i++)
        ((Expression) this.getExpressions().get(i)).accept(visitor);
    visitor.end(this);
  }
}
