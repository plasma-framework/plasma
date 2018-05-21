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
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.QueryException;
import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Query", propOrder = { "clauses", "startRange", "endRange",
    "configurationProperties" })
@XmlRootElement(name = "Query")
public class Query implements org.plasma.query.Query {

  @XmlElement(name = "Clause", required = true)
  protected List<Clause> clauses;
  @XmlElement(namespace = "", defaultValue = "0")
  protected Integer startRange;
  @XmlElement(namespace = "", defaultValue = "0")
  protected Integer endRange;
  @XmlElement(name = "ConfigurationProperty", required = true)
  protected List<ConfigurationProperty> configurationProperties;
  @XmlAttribute
  protected String name;

  public Query() {
    super();
  }

  public Query(Select select, From from) {
    this();
    getClauses().add(new Clause(select));
    getClauses().add(new Clause(from));
  }

  public Query(Select select, From from, GroupBy groupBy) {
    this();
    getClauses().add(new Clause(select));
    getClauses().add(new Clause(from));
    getClauses().add(new Clause(groupBy));
  }

  public Query(Select select, From from, Where where) {
    this();
    getClauses().add(new Clause(select));
    getClauses().add(new Clause(from));
    getClauses().add(new Clause(where));
  }

  public Query(Select select, From from, Where where, GroupBy groupBy) {
    this();
    getClauses().add(new Clause(select));
    getClauses().add(new Clause(from));
    getClauses().add(new Clause(where));
    getClauses().add(new Clause(groupBy));
  }

  public Query(Select select, From from, OrderBy orderBy) {
    this();
    getClauses().add(new Clause(select));
    getClauses().add(new Clause(from));
    getClauses().add(new Clause(orderBy));
  }

  public Query(Select select, From from, OrderBy orderBy, GroupBy groupBy) {
    this();
    getClauses().add(new Clause(select));
    getClauses().add(new Clause(from));
    getClauses().add(new Clause(orderBy));
    getClauses().add(new Clause(groupBy));
  }

  public Query(Select select, From from, Where where, OrderBy orderBy) {
    this();
    getClauses().add(new Clause(select));
    getClauses().add(new Clause(from));
    getClauses().add(new Clause(where));
    getClauses().add(new Clause(orderBy));
  }

  public Query(Select select, From from, Where where, OrderBy orderBy, GroupBy groupBy) {
    this();
    getClauses().add(new Clause(select));
    getClauses().add(new Clause(from));
    getClauses().add(new Clause(where));
    getClauses().add(new Clause(orderBy));
    getClauses().add(new Clause(groupBy));
  }

  public Query getModel() {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#getClauses()
   */
  public List<Clause> getClauses() {
    if (clauses == null) {
      clauses = new ArrayList<Clause>();
    }
    return this.clauses;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#getStartRange()
   */
  public Integer getStartRange() {
    if (startRange != null)
      return startRange;
    else
      return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#setStartRange(java.lang.Integer)
   */
  public void setStartRange(Integer value) {
    this.startRange = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#getEndRange()
   */
  public Integer getEndRange() {
    if (endRange != null)
      return endRange;
    else
      return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#setEndRange(java.lang.Integer)
   */
  public void setEndRange(Integer value) {
    this.endRange = value;
  }

  /**
   * Gets the value of the configurationProperties property.
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link ConfigurationProperty }
   */
  public List<ConfigurationProperty> getConfigurationProperties() {
    if (configurationProperties == null) {
      configurationProperties = new ArrayList<ConfigurationProperty>();
    }
    return this.configurationProperties;
  }

  /**
   * Appends the given property to the configuration property list.
   * 
   * @param prop
   */
  public void addConfigurationProperty(ConfigurationProperty prop) {
    getConfigurationProperties().add(prop);
  }

  /**
   * Appends the given property to the configuration property list.
   * 
   * @param name
   *          the property name
   * @param value
   *          the property value
   */
  @Override
  public void addConfigurationProperty(String name, String value) {
    ConfigurationProperty prop = new ConfigurationProperty();
    prop.setName(name);
    prop.setValue(value);
    addConfigurationProperty(prop);
  }

  /**
   * Returns the configuration property value for the given name
   * 
   * @param name
   *          the property name
   * @return the property value
   */
  @Override
  public String getConfigurationProperty(String name) {
    for (ConfigurationProperty prop : getConfigurationProperties()) {
      if (prop.getName().equals(name))
        return prop.getValue();
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#getName()
   */
  public String getName() {
    return name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#setName(java.lang.String)
   */
  public void setName(String value) {
    this.name = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
      for (int i = 0; i < this.getClauses().size(); i++)
        this.getClauses().get(i).accept(visitor);
    visitor.end(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#getSelectClause()
   */
  public Select getSelectClause() {
    for (int i = 0; i < this.getClauses().size(); i++) {
      Select select = this.getClauses().get(i).getSelect();
      if (select != null)
        return select;
    }
    throw new QueryException("could not get Select clause");
  }

  public Update getUpdateClause() {
    for (int i = 0; i < this.getClauses().size(); i++) {
      Update update = this.getClauses().get(i).getUpdate();
      if (update != null)
        return update;
    }
    throw new QueryException("could not get Update clause");
  }

  public Delete getDeleteClause() {
    for (int i = 0; i < this.getClauses().size(); i++) {
      Delete delete = this.getClauses().get(i).getDelete();
      if (delete != null)
        return delete;
    }
    throw new QueryException("could not get Delete clause");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#getFromClause()
   */
  @Override
  public From getFromClause() {
    for (int i = 0; i < this.getClauses().size(); i++) {
      From from = this.getClauses().get(i).getFrom();
      if (from != null)
        return from;
    }
    throw new QueryException("could not get From clause");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#getWhereClause()
   */
  @Override
  public Where getWhereClause() {
    Where result = findWhereClause();
    if (result == null)
      throw new QueryException("could not get Where clause");
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#findWhereClause()
   */
  @Override
  public Where findWhereClause() {
    for (int i = 0; i < this.getClauses().size(); i++) {
      Where where = this.getClauses().get(i).getWhere();
      if (where != null)
        return where;
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#clearOrderByClause()
   */
  @Override
  public void clearOrderByClause() {
    for (int i = 0; i < this.getClauses().size(); i++) {
      OrderBy orderBy = this.getClauses().get(i).getOrderBy();
      if (orderBy != null)
        this.getClauses().remove(this.getClauses().get(i));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#findOrderByClause()
   */
  @Override
  public OrderBy findOrderByClause() {
    for (int i = 0; i < this.getClauses().size(); i++) {
      OrderBy orderBy = this.getClauses().get(i).getOrderBy();
      if (orderBy != null)
        return orderBy;
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.query.model.Query2#findGroupByClause()
   */
  @Override
  public GroupBy findGroupByClause() {
    for (int i = 0; i < this.getClauses().size(); i++) {
      GroupBy groupBy = this.getClauses().get(i).getGroupBy();
      if (groupBy != null)
        return groupBy;
    }
    return null;
  }

  @Override
  public Having findHavingClause() {
    for (int i = 0; i < this.getClauses().size(); i++) {
      Having having = this.getClauses().get(i).getHaving();
      if (having != null)
        return having;
    }
    return null;
  }

  @Override
  public List<org.plasma.query.Join> findJoinClauses() {
    List<org.plasma.query.Join> result = null;
    for (int i = 0; i < this.getClauses().size(); i++) {
      Join join = this.getClauses().get(i).getJoin();
      if (join != null) {
        if (result == null)
          result = new ArrayList<>();
        result.add((org.plasma.query.Join) join);
      }
    }
    if (result != null)
      return result;
    else
      return Collections.emptyList();
  }

}
