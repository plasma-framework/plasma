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

package org.plasma.query.xpath;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.expr.BinaryExpr;
import org.jaxen.expr.EqualityExpr;
import org.jaxen.expr.Expr;
import org.jaxen.expr.LogicalExpr;
import org.jaxen.expr.RelationalExpr;
import org.plasma.query.model.Expression;
import org.plasma.query.model.RelationalOperator;
import org.plasma.query.model.Where;
import org.plasma.sdo.xpath.XPathExprVisitor;

/**
 * Receives Jaxen XPATH expression events as the expression parse tree is
 * traversed.
 */
public class QueryPredicateVisitor implements XPathExprVisitor {

  private static Log log = LogFactory.getFactory().getInstance(QueryPredicateVisitor.class);
  private Where result;
  private Map<Expr, Expression> map = new HashMap<Expr, Expression>();

  public void visit(Expr target, Expr source, int level) {
    // log.info(target.getClass().getSimpleName()
    // + " expr: " + target.toString());

    if (target instanceof BinaryExpr) {
      BinaryExpr binExpr = (BinaryExpr) target;
      Expression left = map.get(binExpr.getLHS());
      Expression right = map.get(binExpr.getRHS());
      if (left == null && right == null) {
        if (binExpr instanceof RelationalExpr) {
          RelationalExpr expr = (RelationalExpr) target;
          Expression qexpr = Expression.valueOf(expr);
          map.put(expr, qexpr);
        } else if (binExpr instanceof LogicalExpr) {
          LogicalExpr expr = (LogicalExpr) target;
          Expression qexpr = Expression.valueOf(expr);
          map.put(expr, qexpr);
        } else if (binExpr instanceof EqualityExpr) {
          EqualityExpr expr = (EqualityExpr) target;
          Expression qexpr = Expression.valueOf(expr);
          map.put(expr, qexpr);
        }
      } else {
        Expression qexpr = new Expression(left, RelationalOperator.valueOf(binExpr.getOperator()),
            right);
        map.put(binExpr, qexpr);
      }
    }
    // else
    // log.warn("could not process expression of type, "
    // + target.getClass().getSimpleName());

    if (source == null) { // root
      result = new Where();
      Expression qexpr = map.get(target);
      result.addExpression(qexpr);
    }
  }

  public Where getResult() {
    return result;
  }

}
