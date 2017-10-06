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

package org.plasma.sdo.access.provider.common;

import java.util.Iterator;
import java.util.List;

import org.plasma.query.QueryException;
import org.plasma.query.model.Expression;
import org.plasma.query.model.GroupOperator;
import org.plasma.query.model.Literal;
import org.plasma.query.model.NullLiteral;
import org.plasma.query.model.PredicateOperator;
import org.plasma.query.model.Property;
import org.plasma.query.model.Query;
import org.plasma.query.model.QueryConstants;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.Traversal;

import commonj.sdo.Type;

public abstract class TextQueryFilterAssembler extends DefaultQueryVisitor implements
    QueryConstants, EntityConstants {
  protected Type contextType;

  protected commonj.sdo.Property contextProperty;

  protected StringBuilder filter = new StringBuilder();

  protected List<Object> params;

  public String getFilter() {
    return filter.toString();
  }

  public Object[] getParams() {
    Object[] result = new Object[params.size()];
    Iterator iter = params.iterator();
    for (int i = 0; iter.hasNext(); i++) {
      Object param = iter.next();
      if (!(param instanceof NullLiteral))
        result[i] = param;
      else
        result[i] = null;
    }
    return result;
  }

  protected boolean hasWildcard(Expression expression) {
    for (int i = 0; i < expression.getTerms().size(); i++) {
      if (expression.getTerms().get(i).getPredicateOperator() != null) {
        Literal literal = expression.getTerms().get(i + 1).getLiteral();
        if (literal.getValue().indexOf(WILDCARD) >= 0) // otherwise we can treat
                                                       // the expr like any
                                                       // other
          return true;
      }
    }
    return false;
  }

  public void start(Expression expression) {
    // THIS NEEDS REFACTOING
    if (hasWildcard(expression)) {
      int i = 0;
      while (i < expression.getTerms().size()) {
        PredicateOperator wildcardOper = expression.getTerms().get(i).getPredicateOperator();
        if (wildcardOper != null) {
          // log.info("found wildcard expression");
          Property property = expression.getTerms().get(i - 1).getProperty();
          Literal literal = expression.getTerms().get(i + 1).getLiteral();
          processWildcardExpression(property, wildcardOper, literal);
          i += 2; // skip next since we already used next term.
          continue;
        } else {
          // process everything but leave the
          // property, wildcard op, literal alone for above special case.
          if (expression.getTerms().get(i).getProperty() == null
              && expression.getTerms().get(i).getPredicateOperator() == null
              && expression.getTerms().get(i).getLiteral() == null)
            expression.getTerms().get(i).accept(this);
        }
        i++;
      }
      this.getContext().setTraversal(Traversal.ABORT);
      // abort traversal as vanilla expression
    }
    super.start(expression);
  }

  protected abstract void processWildcardExpression(Property property, PredicateOperator oper,
      Literal literal);

  protected abstract void assembleSubquery(Property property, PredicateOperator oper, Query query);

  // String.split() can cause empty tokens under some circumstances
  protected String[] filterTokens(String[] tokens) {
    int count = 0;
    for (int i = 0; i < tokens.length; i++)
      if (tokens[i].length() > 0)
        count++;
    String[] result = new String[count];
    int j = 0;
    for (int i = 0; i < tokens.length; i++)
      if (tokens[i].length() > 0) {
        result[j] = tokens[i];
        j++;
      }
    return result;
  }

  public void start(GroupOperator operator) {
    if (filter.length() > 0)
      filter.append(" ");
    switch (operator.getValue()) {
    case LP_1:
      filter.append(")");
      break;
    case LP_2:
      filter.append("))");
      break;
    case LP_3:
      filter.append(")))");
      break;
    case RP_1:
      filter.append("(");
      break;
    case RP_2:
      filter.append("((");
      break;
    case RP_3:
      filter.append("(((");
      break;
    default:
      throw new QueryException("unknown group operator, " + operator.getValue().name());
    }
    super.start(operator);
  }
}
