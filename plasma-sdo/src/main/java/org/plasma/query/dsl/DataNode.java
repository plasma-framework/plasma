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

import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.IntegralDataProperty;
import org.plasma.query.Query;
import org.plasma.query.RealDataProperty;
import org.plasma.query.StringDataProperty;
import org.plasma.query.TemporalDataProperty;
import org.plasma.query.Wildcard;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;
import org.plasma.query.model.SortDirection;
import org.plasma.query.model.WildcardProperty;

/**
 * A domain query node which is a data property end point within a query graph.
 */
public class DataNode extends DomainEndpoint implements StringDataProperty, IntegralDataProperty,
    RealDataProperty, TemporalDataProperty {

  public DataNode(PathNode source, String name) {
    super(source, name);
    if (this.source != null) {
      Path path = createPath();
      if (!Wildcard.WILDCARD_CHAR.equals(name)) {
        if (path != null)
          this.property = new org.plasma.query.model.Property(name, path);
        else
          this.property = new org.plasma.query.model.Property(name);
      } else {
        if (path != null)
          this.property = new WildcardProperty(path);
        else
          this.property = new WildcardProperty();
      }
    } else {
      if (!Wildcard.WILDCARD_CHAR.equals(name))
        this.property = new org.plasma.query.model.Property(name);
      else
        this.property = new WildcardProperty();
    }
  }

  AbstractProperty getModel() {
    return this.property;
  }

  @Override
  public Expression eq(Object value) {
    return org.plasma.query.model.Expression.eq((org.plasma.query.model.Property) this.property,
        value);
  }

  @Override
  public Expression like(String value) {
    return org.plasma.query.model.Expression.like((org.plasma.query.model.Property) this.property,
        value);
  }

  @Override
  public Expression between(Object min, Object max) {
    return org.plasma.query.model.Expression.between(
        (org.plasma.query.model.Property) this.property, min, max);
  }

  @Override
  public Expression ge(Object value) {
    return org.plasma.query.model.Expression.ge((org.plasma.query.model.Property) this.property,
        value);
  }

  @Override
  public Expression gt(Object value) {
    return org.plasma.query.model.Expression.gt((org.plasma.query.model.Property) this.property,
        value);
  }

  @Override
  public Expression le(Object value) {
    return org.plasma.query.model.Expression.le((org.plasma.query.model.Property) this.property,
        value);
  }

  @Override
  public Expression lt(Object value) {
    return org.plasma.query.model.Expression.lt((org.plasma.query.model.Property) this.property,
        value);
  }

  @Override
  public Expression ne(Object value) {
    return org.plasma.query.model.Expression.ne((org.plasma.query.model.Property) this.property,
        value);
  }

  @Override
  public Expression in(Query subquery) {
    return org.plasma.query.model.Expression.in((org.plasma.query.model.Property) this.property,
        subquery.getModel());
  }

  @Override
  public DataProperty min() {
    ((org.plasma.query.model.Property) this.property).min();
    return this;
  }

  @Override
  public DataProperty max() {
    ((org.plasma.query.model.Property) this.property).max();
    return this;
  }

  @Override
  public DataProperty sum() {
    ((org.plasma.query.model.Property) this.property).sum();
    return this;
  }

  @Override
  public DataProperty avg() {
    ((org.plasma.query.model.Property) this.property).avg();
    return this;
  }

  @Override
  public IntegralDataProperty abs() {
    ((org.plasma.query.model.Property) this.property).abs();
    return this;
  }

  @Override
  public RealDataProperty ceiling() {
    ((org.plasma.query.model.Property) this.property).ceiling();
    return this;
  }

  @Override
  public RealDataProperty floor() {
    ((org.plasma.query.model.Property) this.property).floor();
    return this;
  }

  @Override
  public RealDataProperty round() {
    ((org.plasma.query.model.Property) this.property).round();
    return this;
  }

  @Override
  public StringDataProperty substringBefore(String value) {
    ((org.plasma.query.model.Property) this.property).substringBefore(value);
    return this;
  }

  @Override
  public StringDataProperty substringAfter(String value) {
    ((org.plasma.query.model.Property) this.property).substringAfter(value);
    return this;
  }

  @Override
  public StringDataProperty upperCase() {
    ((org.plasma.query.model.Property) this.property).upperCase();
    return this;
  }

  @Override
  public StringDataProperty lowerCase() {
    ((org.plasma.query.model.Property) this.property).lowerCase();
    return this;
  }

  @Override
  public StringDataProperty normalizeSpace() {
    ((org.plasma.query.model.Property) this.property).normalizeSpace();
    return this;
  }

  public DataProperty asc() {
    ((org.plasma.query.model.Property) this.property).setDirection(SortDirection.ASC);
    return this;
  }

  public DataProperty desc() {
    ((org.plasma.query.model.Property) this.property).setDirection(SortDirection.DESC);
    return this;
  }

  @Override
  public Expression isNotNull() {
    return ((org.plasma.query.model.Property) this.property).isNotNull();
  }

  @Override
  public Expression isNull() {
    return ((org.plasma.query.model.Property) this.property).isNull();
  }

  @Override
  public Expression notIn(Query subquery) {
    return org.plasma.query.model.Expression.notIn((org.plasma.query.model.Property) this.property,
        subquery.getModel());
  }

  @Override
  public String getName() {
    return ((org.plasma.query.model.Property) this.property).getName();
  }

  @Override
  public boolean isDistinct() {
    return ((org.plasma.query.model.Property) this.property).isDistinct();
  }

}
