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

// java imports
import org.plasma.query.EmptySelectClauseException;
import org.plasma.query.InvalidPathElementException;
import org.plasma.query.QueryException;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import commonj.sdo.Type;

public class QueryValidator extends AbstractQueryValidator implements QueryConstants {
  private Type rootType;
  private Type contextType;

  @SuppressWarnings("unused")
  private QueryValidator() {
  }

  public QueryValidator(Query query, Type contextType) {
    this.rootType = contextType;
    this.contextType = contextType;
    query.accept(this);
  }

  public QueryValidator(Where where, Type contextType) {
    this.rootType = contextType;
    this.contextType = contextType;
    where.accept(this);
  }

  /**
   * Ensures class associated with From clause entity exists
   */
  public void start(Select select) {
    if (select.getProperties().size() == 0)
      throw new EmptySelectClauseException("Select clause has no properties");

    super.start(select);
  }

  /**
   * Ensures class associated with From clause entity exists
   */
  public void start(From from) {
    Type type = PlasmaTypeHelper.INSTANCE.getType(from.getEntity().getNamespaceURI(), from
        .getEntity().getName());

    try {
      String pkgName = PlasmaRuntime.getInstance().getSDONamespaceByURI(type.getURI())
          .getProvisioning().getPackageName();
      Class.forName(pkgName + "." + type.getName()); // just validate it
    } catch (ClassNotFoundException e) {
      throw new QueryException(e);
    }
    super.start(from);
  }

  public void start(Expression expression) {
    for (int i = 0; i < expression.getTerms().size(); i++) {
      PredicateOperator oper = expression.getTerms().get(i).getPredicateOperator();
      if (oper != null) {
        if (i == 0)
          throw new QueryException("did not expect WildcardOperator as first term in expression");
        Term t = expression.getTerms().get(i - 1);
        if (t.getProperty() == null)
          throw new QueryException("expected Property before WildcardOperator in expression");
        t = expression.getTerms().get(i + 1);
        if (t.getLiteral() == null)
          throw new QueryException("expected Literal after WildcardOperator in expression");
      }
    }
    super.start(expression);
  }

  // replace this with path-item
  public void start(Property property) {
    if (property.getPath() != null) {
      try {
        Path path = property.getPath();
        for (int i = 0; i < path.getPathNodes().size(); i++) {
          PathNode node = path.getPathNodes().get(i);
          AbstractPathElement elem = node.getPathElement();
          if (elem instanceof PathElement) {
            commonj.sdo.Property prop = contextType.getProperty(((PathElement) elem).getValue());

            if (prop.getType().isDataType())
              throw new InvalidPathElementException("expected reference property as path element");
            this.contextType = prop.getType();
          }
          if (node.getWhere() != null)
            new QueryValidator(node.getWhere(), this.contextType);
        }
      } catch (QueryException e) {
        throw new InvalidPathElementException(e);
      }
    }

    if (!WILDCARD.equals(property.getName())) {
      commonj.sdo.Property endpoint = contextType.getProperty(property.getName());
      if (endpoint.isMany())
        throw new QueryException("expected singular property as endpoint, not '"
            + endpoint.getName() + "'");
    }

    this.contextType = this.rootType; // reset after property traversal
    super.start(property);
  }

  public void start(Literal literal) {
    if (literal.getValue() == null)
      throw new QueryException("found null content for literal");
    super.start(literal);
  }
}
