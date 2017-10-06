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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.JaxenException;
import org.jaxen.JaxenHandler;
import org.jaxen.expr.BinaryExpr;
import org.jaxen.expr.Expr;
import org.jaxen.expr.FilterExpr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.PathExpr;
import org.jaxen.expr.Predicate;
import org.jaxen.expr.Step;
import org.jaxen.expr.UnaryExpr;
import org.jaxen.expr.XPathExpr;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.saxpath.XPathReader;
import org.jaxen.saxpath.helpers.XPathReaderFactory;
import org.plasma.sdo.xpath.DefaultXPath;
import org.plasma.sdo.xpath.XPathExprVisitor;

/**
 * Provides access to the Jaxen XPATH parse tree after a given XPATH is
 * successfully parsed.
 */
public class QueryXPath extends DefaultXPath {
  private static Log log = LogFactory.getFactory().getInstance(QueryXPath.class);

  private static final long serialVersionUID = 1L;
  private final XPathExpr queryXpath;

  /**
   * Constructor for parsing and navigation of an XPATH
   * 
   * @param xpathExpr
   *          the XPATH expression
   * @param navigator
   *          the navigator
   * @throws JaxenException
   */
  public QueryXPath(String xpathExpr, QueryPathNavigator navigator) throws JaxenException {
    super(xpathExpr, navigator);
    navigator.setXpath(this);
    try {
      XPathReader reader = XPathReaderFactory.createReader();
      JaxenHandler handler = new JaxenHandler();
      reader.setXPathHandler(handler);
      reader.parse(xpathExpr);
      this.queryXpath = handler.getXPathExpr();
    } catch (org.jaxen.saxpath.XPathSyntaxException e) {
      throw new org.jaxen.XPathSyntaxException(e);
    } catch (SAXPathException e) {
      throw new JaxenException(e);
    }
  }

  /**
   * Constructor for parsing of an XPATH without navigation.
   * 
   * @param xpathExpr
   *          the XPATH expression
   * @throws JaxenException
   */
  public QueryXPath(String xpathExpr) throws JaxenException {
    super(xpathExpr);
    try {
      XPathReader reader = XPathReaderFactory.createReader();
      JaxenHandler handler = new JaxenHandler();
      reader.setXPathHandler(handler);
      reader.parse(xpathExpr);
      this.queryXpath = handler.getXPathExpr();
    } catch (org.jaxen.saxpath.XPathSyntaxException e) {
      throw new org.jaxen.XPathSyntaxException(e);
    } catch (SAXPathException e) {
      throw new JaxenException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public List<Step> getSteps() throws JaxenException {
    LocationPath path = (LocationPath) this.queryXpath.getRootExpr();
    return path.getSteps();
  }

  @SuppressWarnings("unchecked")
  public void accept(Step step, XPathExprVisitor visitor) {
    List predicates = step.getPredicates();
    if (predicates != null)
      for (Object pred : predicates) {
        Predicate predicate = (Predicate) pred;
        Expr ex = predicate.getExpr();
        accept(visitor, ex, null, true, 0);
      }
  }

  @SuppressWarnings("unchecked")
  public void acceptBreadthFirst(Step step, XPathExprVisitor visitor) {
    List predicates = step.getPredicates();
    if (predicates != null)
      for (Object pred : predicates) {
        Predicate predicate = (Predicate) pred;
        Expr ex = predicate.getExpr();
        accept(visitor, ex, null, false, 0);
      }
  }

  private void accept(XPathExprVisitor visitor, Expr target, Expr source, boolean depthFirst,
      int level) {

    if (depthFirst)
      visitor.visit(target, source, level);

    if (target instanceof BinaryExpr) {
      BinaryExpr expr = (BinaryExpr) target;
      accept(visitor, expr.getLHS(), target, depthFirst, level + 1);
      accept(visitor, expr.getRHS(), target, depthFirst, level + 1);
    } else if (target instanceof FilterExpr) {
      FilterExpr expr = (FilterExpr) target;
      accept(visitor, expr.getExpr(), target, depthFirst, level + 1);
    } else if (target instanceof PathExpr) {
      PathExpr expr = (PathExpr) target;
      accept(visitor, expr.getFilterExpr(), target, depthFirst, level + 1);
    } else if (target instanceof UnaryExpr) {
      UnaryExpr expr = (UnaryExpr) target;
      accept(visitor, expr.getExpr(), source, depthFirst, level + 1);
    }
    // otherwise it has no child expressions

    if (!depthFirst)
      visitor.visit(target, source, level);
  }

}
