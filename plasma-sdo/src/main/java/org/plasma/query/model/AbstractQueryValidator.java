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

import org.plasma.query.From;
import org.plasma.query.OrderBy;
import org.plasma.query.Query;
import org.plasma.query.Where;
import org.plasma.query.visitor.DefaultQueryVisitor;

/**
 */
public abstract class AbstractQueryValidator extends DefaultQueryVisitor {
  public AbstractQueryValidator() {
    super();
  }

  public void start(Query query) {
    super.start(query);
  }

  public void start(Clause clause) {
    super.start(clause);
  }

  public void start(Select select) {
    super.start(select);
  }

  public void start(Update update) {
    super.start(update);
  }

  public void start(Delete delete) {
    super.start(delete);
  }

  public void start(From from) {
    super.start(from);
  }

  public void start(Where where) {
    super.start(where);
  }

  public void start(OrderBy orderBy) {
    super.start(orderBy);
  }

  public void start(Expression expression) {
    super.start(expression);
  }

  public void start(Term term) {
    super.start(term);
  }

  public void start(Property property) {
    super.start(property);
  }

  public void start(Entity entity) {
    super.start(entity);
  }

  public void start(RelationalOperator operator) {
    super.start(operator);
  }

  public void start(LogicalOperator operator) {
    super.start(operator);
  }

  public void start(ArithmeticOperator operator) {
    super.start(operator);
  }

  public void start(PredicateOperator operator) {
    super.start(operator);
  }

  public void start(Literal literal) {
    super.start(literal);
  }

}
