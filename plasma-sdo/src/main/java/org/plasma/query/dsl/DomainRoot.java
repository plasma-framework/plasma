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

package org.plasma.query.dsl;

import java.util.List;

import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.From;
import org.plasma.query.GroupBy;
import org.plasma.query.OrderBy;
import org.plasma.query.Select;
import org.plasma.query.Where;
import org.plasma.query.Wildcard;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Clause;
import org.plasma.query.model.Entity;
import org.plasma.query.Join;
import org.plasma.query.model.Path;

import commonj.sdo.Type;

/**
 * The implementation of a domain query as a root within a query graph.
 */
public class DomainRoot extends PathNode implements DomainQuery {
  private org.plasma.query.model.Query query;

  protected DomainRoot(Type type) {
    super(type);
    this.query = new org.plasma.query.model.Query();
    org.plasma.query.model.From from = new org.plasma.query.model.From();
    from.setEntity(new Entity(this.type.getName(), this.type.getURI()));
    this.query.getClauses().add(new org.plasma.query.model.Clause(from));
    this.isRoot = true;
  }

  protected DomainRoot(PathNode source, String sourceProperty) {
    super(source, sourceProperty);
  }

  protected DomainRoot(PathNode source, String sourceProperty, Expression expr) {
    super(source, sourceProperty, expr);
  }

  public org.plasma.query.model.Query getModel() {
    return this.query;
  }

  @Override
  public DomainQuery where(Expression expr) {
    org.plasma.query.model.Where where = this.query.findWhereClause();

    // we may have been re-parented
    org.plasma.query.model.Expression root = (org.plasma.query.model.Expression) expr;
    while (root.getParent() != null) {
      root = root.getParent();
    }

    if (where == null) {
      where = new org.plasma.query.model.Where();
      this.query.getClauses().add(new Clause(where));
      where.addExpression(root);
    } else {
      where.addExpression(org.plasma.query.model.Expression.and());
      where.addExpression(root);
    }
    return this;
  }

  @Override
  public DomainQuery select(DataProperty property) {
    org.plasma.query.model.Select select = null;
    for (int i = 0; i < this.query.getClauses().size(); i++) {
      select = this.query.getClauses().get(i).getSelect();
      if (select != null)
        break;
    }

    if (select == null) {
      select = new org.plasma.query.model.Select();
      this.query.getClauses().add(new Clause(select));
    }

    DataNode domainProperty = (DataNode) property;
    AbstractProperty prop = domainProperty.getModel();

    select.addProperty(prop);
    return this;
  }

  @Override
  public DomainQuery select(Wildcard property) {
    org.plasma.query.model.Select select = null;
    for (int i = 0; i < this.query.getClauses().size(); i++) {
      select = this.query.getClauses().get(i).getSelect();
      if (select != null)
        break;
    }

    if (select == null) {
      select = new org.plasma.query.model.Select();
      this.query.getClauses().add(new Clause(select));
    }

    WildcardNode domainProperty = (WildcardNode) property;
    AbstractProperty prop = domainProperty.getModel();

    select.addProperty(prop);
    return this;
  }

  @Override
  public DomainQuery orderBy(DataProperty property) {
    org.plasma.query.model.OrderBy orderBy = this.query.findOrderByClause();
    if (orderBy == null) {
      orderBy = new org.plasma.query.model.OrderBy();
      this.query.getClauses().add(new Clause(orderBy));
    }
    DataNode domainProperty = (DataNode) property;
    String[] path = domainProperty.getPath();
    AbstractProperty prop = domainProperty.getModel();
    // org.plasma.query.model.Property prop = null;
    // if (path != null && path.length > 0)
    // prop = org.plasma.query.model.Property.forName(domainProperty.getName(),
    // new Path(path));
    // else
    // prop = org.plasma.query.model.Property.forName(domainProperty.getName());
    // FIXME; why are we copying data here, just use the delegate
    // prop.setDirection(((org.plasma.query.model.Property)domainProperty.getModel()).getDirection());
    orderBy.addProperty((org.plasma.query.model.Property) prop);
    return this;
  }

  @Override
  public DomainQuery groupBy(DataProperty property) {
    org.plasma.query.model.GroupBy groupBy = this.query.findGroupByClause();
    if (groupBy == null) {
      groupBy = new org.plasma.query.model.GroupBy();
      this.query.getClauses().add(new Clause(groupBy));
    }
    DataNode domainProperty = (DataNode) property;
    String[] path = domainProperty.getPath();
    org.plasma.query.model.Property prop = null;
    if (path != null && path.length > 0)
      prop = org.plasma.query.model.Property.forName(domainProperty.getName(), new Path(path));
    else
      prop = org.plasma.query.model.Property.forName(domainProperty.getName());
    groupBy.addProperty(prop);
    return this;
  }

  @Override
  public From getFromClause() {
    return this.query.getFromClause();
  }

  @Override
  public void clearOrderByClause() {
    this.query.clearOrderByClause();
  }

  @Override
  public GroupBy findGroupByClause() {
    return this.query.findGroupByClause();
  }

  @Override
  public OrderBy findOrderByClause() {
    return this.query.findOrderByClause();
  }

  @Override
  public Where findWhereClause() {
    return this.query.findWhereClause();
  }

  @Override
  public Integer getEndRange() {
    return this.query.getEndRange();
  }

  @Override
  public String getName() {
    return this.query.getName();
  }

  @Override
  public Select getSelectClause() {
    return this.query.getSelectClause();
  }

  @Override
  public Integer getStartRange() {
    return this.query.getStartRange();
  }

  @Override
  public Where getWhereClause() {
    return this.query.getWhereClause();
  }

  public void setEndRange(Integer value) {
    this.query.setEndRange(value);
  }

  @Override
  public void setName(String value) {
    this.query.setName(value);
  }

  @Override
  public void setStartRange(Integer value) {
    this.query.setStartRange(value);
  }

  @Override
  public void addConfigurationProperty(String name, String value) {
    this.query.addConfigurationProperty(name, value);
  }

  @Override
  public String getConfigurationProperty(String name) {
    return this.query.getConfigurationProperty(name);
  }

  @Override
  public List<Join> findJoinClauses() {
    return this.query.findJoinClauses();
  }

}
