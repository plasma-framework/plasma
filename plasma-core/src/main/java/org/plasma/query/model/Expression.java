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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.expr.BinaryExpr;
import org.jaxen.expr.EqualityExpr;
import org.jaxen.expr.LiteralExpr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.LogicalExpr;
import org.jaxen.expr.NameStep;
import org.jaxen.expr.NumberExpr;
import org.jaxen.expr.RelationalExpr;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

/**
 * <p>
 * Java class for Expression complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Expression">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.terrameta.org/plasma/query}Term" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Expression", propOrder = { "terms", "configurationProperty" })
@XmlRootElement(name = "Expression")
public class Expression implements org.plasma.query.Expression {

  private final static String SYNTHETIC_PARENT_ID = "synthetic";
  public final static String NOOP_EXPR_ID = "NO_OP";
  private static transient Log log = LogFactory.getLog(Expression.class);
  protected transient Expression parent;
  @XmlAttribute
  protected String id;

  @XmlElement(name = "Term")
  protected List<Term> terms;
  @XmlElement(name = "ConfigurationProperty")
  protected List<ConfigurationProperty> configurationProperty;

  public Expression getParent() {
    return this.parent;
  }

  @Override
  public org.plasma.query.Expression and(org.plasma.query.Expression other) {
    return this.cat(other, LogicalOperatorName.AND);
  }

  @Override
  public org.plasma.query.Expression or(org.plasma.query.Expression other) {
    return this.cat(other, LogicalOperatorName.OR);
  }

  @Override
  public org.plasma.query.Expression group() {
    // we may have been re-parented
    Expression root = this;
    while (root.getParent() != null) {
      root = root.getParent();
    }
    root.getTerms().add(0, new Term(new GroupOperator(GroupOperatorName.RP_1)));
    root.getTerms().add(new Term(new GroupOperator(GroupOperatorName.LP_1)));
    return this;
  }

  public boolean isGroup() {
    if (this.getTerms().size() > 0) {
      GroupOperator left = this.getTerms().get(0).getGroupOperator();
      if (left != null && left.getValue().ordinal() == GroupOperatorName.RP_1.ordinal()) {
        GroupOperator right = this.getTerms().get(this.getTerms().size() - 1).getGroupOperator();
        if (right != null && right.getValue().ordinal() == GroupOperatorName.LP_1.ordinal()) {
          return true;
        }
      }
    }
    return false;
  }

  private Expression cat(org.plasma.query.Expression other, LogicalOperatorName oper) {
    Expression result = null;
    if (this.parent == null) {
      Expression parent = findParent();
      if (parent == null) {
        if (log.isDebugEnabled())
          log.debug("creating synthetic expr parent for concatination");
        parent = new Expression();
        parent.id = SYNTHETIC_PARENT_ID;
        ((Expression) other).parent = parent;
        this.parent = parent;
        parent.getTerms().add(new Term(this));
        parent.getTerms().add(new Term(new LogicalOperator(oper)));
        parent.getTerms().add(new Term((Expression) other));
        result = this.parent;
      } else {
        if (log.isDebugEnabled())
          log.debug("using existing synthetic expr parent for concatination");
        parent.getTerms().add(new Term(new LogicalOperator(oper)));
        parent.getTerms().add(new Term((Expression) other));
        ((Expression) other).parent = parent;
        result = parent;
      }
    } else {
      if (log.isDebugEnabled())
        log.debug("using existing parent for concatination");
      this.parent.getTerms().add(new Term(new LogicalOperator(oper)));
      this.parent.getTerms().add(new Term((Expression) other));
      ((Expression) other).parent = this.parent;
      result = this.parent;
    }

    return result;
  }

  private Expression findParent() {
    ExprFinder finder = new ExprFinder(SYNTHETIC_PARENT_ID);
    this.accept(finder);
    return finder.getResult();
  }

  public Expression() {
    super();
  }

  public Expression(Term term) {
    this();
    this.getTerms().add(term);
  }

  public Expression(Term t1, Term t2, Term t3) {
    this();
    this.getTerms().add(t1);
    this.getTerms().add(t2);
    this.getTerms().add(t3);
  }

  public Expression(Expression e1, Expression e2, Expression e3) {
    this();
    this.getTerms().add(new Term(e1));
    this.getTerms().add(new Term(e2));
    this.getTerms().add(new Term(e3));
  }

  public Expression(Expression[] exprs) {
    this();
    for (int i = 0; i < exprs.length; i++)
      this.getTerms().add(new Term(exprs[i]));
  }

  public Expression(Property prop, ArithmeticOperator oper, Literal lit) {
    this();
    this.getTerms().add(new Term(prop));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(lit));
  }

  public Expression(Expression left, RelationalOperator oper, Expression right) {
    this();
    this.getTerms().add(new Term(left));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(right));
  }

  public Expression(Expression left, LogicalOperator oper, Expression right) {
    this();
    this.getTerms().add(new Term(left));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(right));
  }

  public Expression(GroupOperator right, Property prop, RelationalOperator oper, Literal lit) {
    this();
    this.getTerms().add(new Term(right));
    this.getTerms().add(new Term(prop));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(lit));
  }

  public Expression(Property prop, RelationalOperator oper, Literal lit, GroupOperator left) {
    this();
    this.getTerms().add(new Term(prop));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(lit));
    this.getTerms().add(new Term(left));
  }

  public Expression(GroupOperator right, Property prop, RelationalOperator oper, Literal lit,
      GroupOperator left) {
    this();
    this.getTerms().add(new Term(right));
    this.getTerms().add(new Term(prop));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(lit));
    this.getTerms().add(new Term(left));
  }

  public Expression(Property prop, RelationalOperator oper, Variable var) {
    this();
    this.getTerms().add(new Term(prop));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(var));
  }

  public Expression(Property prop, RelationalOperator oper, Literal lit) {
    this();
    this.getTerms().add(new Term(prop));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(lit));
  }

  public Expression(Property prop, PredicateOperator oper, Literal lit) {
    this();
    this.getTerms().add(new Term(prop));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(lit));
  }

  public Expression(Property prop, Literal min, Literal max) {
    this();
    Expression left = new Expression(prop, new RelationalOperator(
        RelationalOperatorName.GREATER_THAN_EQUALS), min);
    Expression right = new Expression(prop, new RelationalOperator(
        RelationalOperatorName.LESS_THAN_EQUALS), max);
    this.getTerms().add(new Term(left));
    this.getTerms().add(new Term(new LogicalOperator(LogicalOperatorName.AND)));
    this.getTerms().add(new Term(right));
  }

  public Expression(Property prop, RelationalOperator oper, NullLiteral lit) {
    this();
    this.getTerms().add(new Term(prop));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(lit));
  }

  public Expression(Property prop, PredicateOperator oper, Query query) {
    this();
    this.getTerms().add(new Term(prop));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(query));
  }

  public Expression(Property left, RelationalOperator oper, Property right) {
    this();
    this.getTerms().add(new Term(left));
    this.getTerms().add(new Term(oper));
    this.getTerms().add(new Term(right));
  }

  public Expression(RelationalOperator oper) {
    this();
    this.getTerms().add(new Term(oper));
  }

  public Expression(ArithmeticOperator oper) {
    this();
    this.getTerms().add(new Term(oper));
  }

  public Expression(LogicalOperatorName oper) {
    this();
    this.getTerms().add(new Term(new LogicalOperator(oper)));
  }

  public Expression(GroupOperator oper) {
    this();
    this.getTerms().add(new Term(oper));
  }

  public Expression(Property prop) {
    this();
    this.getTerms().add(new Term(prop));
  }

  public Expression(Entity entity) {
    this();
    this.getTerms().add(new Term(entity));
  }

  /**
   * Returns an expression where the given property is <b>equal to</b> to the
   * given literal object. The given object is assumed to be a string literal,
   * or can be converted to a string literal.
   * 
   * @param prop
   *          the property
   * @param object
   *          the literal
   * @return the boolean expression
   */
  public static Expression eq(String prop, Object object) {
    return Expression.eq(new Property(prop), Literal.valueOf(object));
  }

  /**
   * Returns an expression where the given property is <b>equal to</b> to the
   * given literal object. The given object is assumed to be a string literal,
   * or can be converted to a string literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the multi-element property path
   * @param object
   *          the literal
   * @return the boolean expression
   */
  public static Expression eq(String prop, String[] path, Object object) {
    return Expression.eq(new Property(prop, new Path(path)), Literal.valueOf(object));
  }

  /**
   * Returns an expression where the given property is <b>equal to</b> to the
   * given literal object. The given object is assumed to be a string literal,
   * or can be converted to a string literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the single-element property path
   * @param object
   *          the literal
   * @return the boolean expression
   */
  public static Expression eq(String prop, String path, Object object) {
    return Expression.eq(new Property(prop, new Path(path)), Literal.valueOf(object));
  }

  /**
   * Returns an expression where the given property is <b>equal to</b> to the
   * given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression eq(Property prop, Literal literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.EQUALS), literal);
  }

  /**
   * Returns an expression where the given property is <b>equal to</b> to the
   * given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression eq(Property prop, Object literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.EQUALS),
        Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>equal to</b> to the
   * given null-literal.
   * 
   * @param prop
   *          the property
   * @param object
   *          the literal
   * @return the boolean expression
   */
  public static Expression eq(Property prop, NullLiteral literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.EQUALS), literal);
  }

  /**
   * Returns an expression where the given property is <b>not equal to</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ne(String prop, Object literal) {
    return Expression.ne(new Property(prop), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>not equal to</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ne(Property prop, Object literal) {
    return Expression.ne(prop, Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>not equal to</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the single-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ne(String prop, String path, Object literal) {
    return Expression.ne(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>not equal to</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the multi-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ne(String prop, String[] path, Object literal) {
    return Expression.ne(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>not equal to</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ne(Property prop, Literal literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.NOT_EQUALS), literal);
  }

  /**
   * Returns an expression where the given property is <b>not equal to</b> to
   * the given null-literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ne(Property prop, NullLiteral literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.NOT_EQUALS), literal);
  }

  /**
   * Returns an expression where the given property is <b>greated than</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression gt(String prop, Object literal) {
    return Expression.gt(new Property(prop), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>greated than</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression gt(Property prop, Object literal) {
    return Expression.gt(prop, Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>greated than</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the single-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression gt(String prop, String path, Object literal) {
    return Expression.gt(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>greated than</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the multi-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression gt(String prop, String[] path, Object literal) {
    return Expression.gt(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>greater than</b> to
   * the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression gt(Property prop, Literal literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.GREATER_THAN),
        literal);
  }

  /**
   * Returns an expression where the given property is <b>greater than or equalt
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ge(String prop, Object literal) {
    return Expression.ge(new Property(prop), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>greater than or equal
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the single-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ge(String prop, String path, Object literal) {
    return Expression.ge(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>greater than or equal
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the multi-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ge(String prop, String[] path, Object literal) {
    return Expression.ge(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>greater than or equal
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the single-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ge(Property prop, Literal literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.GREATER_THAN_EQUALS),
        literal);
  }

  /**
   * Returns an expression where the given property is <b>greater than or equal
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the single-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression ge(Property prop, Object literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.GREATER_THAN_EQUALS),
        Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>less than</b> to the
   * given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression lt(Property prop, Object literal) {
    return Expression.lt(prop, Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>less than</b> to the
   * given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression lt(String prop, Object literal) {
    return Expression.lt(new Property(prop), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>less than</b> to the
   * given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the single-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression lt(String prop, String path, Object literal) {
    return Expression.lt(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>less than</b> to the
   * given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the multi-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression lt(String prop, String[] path, Object literal) {
    return Expression.lt(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>less than</b> to the
   * given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression lt(Property prop, Literal literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.LESS_THAN), literal);
  }

  /**
   * Returns an expression where the given property is <b>less than or equal
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression le(String prop, Object literal) {
    return Expression.le(new Property(prop), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>less than or equal
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression le(Property prop, Object literal) {
    return Expression.le(prop, Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>less than or equal
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the single-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression le(String prop, String path, Object literal) {
    return Expression.le(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>less than or equal
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the multi-element property path
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression le(String prop, String[] path, Object literal) {
    return Expression.le(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns an expression where the given property is <b>less than or equal
   * to</b> to the given literal.
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the boolean expression
   */
  public static Expression le(Property prop, Literal literal) {
    return new Expression(prop, new RelationalOperator(RelationalOperatorName.LESS_THAN_EQUALS),
        literal);
  }

  /**
   * Returns a wildcard expression "triad", where the given property is
   * <b>like</b> the given literal, and the literal may contain any number of
   * wildcards, the wildcard character being '*'
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the wildcard expression
   */
  public static Expression like(String prop, Object literal) {
    return Expression.like(new Property(prop), Literal.valueOf(literal));
  }

  /**
   * Returns a wildcard expression "triad", where the given property is
   * <b>like</b> the given literal, and the literal may contain any number of
   * wildcards, the wildcard character being '*'
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the wildcard expression
   */
  public static Expression like(Property prop, Object literal) {
    return Expression.like(prop, Literal.valueOf(literal));
  }

  /**
   * Returns a wildcard expression "triad", where the given property is
   * <b>like</b> the given literal, and the literal may contain any number of
   * wildcards, the wildcard character being '*'
   * 
   * @param prop
   *          the property
   * @param path
   *          the single-element property path
   * @param literal
   *          the literal
   * @return the wildcard expression
   */
  public static Expression like(String prop, String path, Object literal) {
    return Expression.like(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns a wildcard expression "triad", where the given property is
   * <b>like</b> the given literal, and the literal may contain any number of
   * wildcards, the wildcard character being '*'
   * 
   * @param prop
   *          the property
   * @param path
   *          the multi-element property path
   * @param literal
   *          the literal
   * @return the wildcard expression
   */
  public static Expression like(String prop, String[] path, Object literal) {
    return Expression.like(new Property(prop, new Path(path)), Literal.valueOf(literal));
  }

  /**
   * Returns a wildcard expression "triad", where the given property is
   * <b>like</b> the given literal, and the literal may contain any number of
   * wildcards, the wildcard character being '*'
   * 
   * @param prop
   *          the property
   * @param literal
   *          the literal
   * @return the wildcard expression
   */
  public static Expression like(Property prop, Literal literal) {
    return new Expression(prop, new PredicateOperator(PredicateOperatorName.LIKE), literal);
  }

  /**
   * Returns a 6 term expression, where the given property is <b>greater than or
   * equal to</b> the given 'min' literal, and is <b>less than or equal
   * to</b>the given 'max' literal.
   * 
   * @param prop
   *          the property
   * @param min
   *          the minimum literal
   * @param max
   *          the maximum literal
   * @return the expression
   */
  public static Expression between(Property prop, Literal min, Literal max) {
    return new Expression(prop, min, max);
  }

  /**
   * Returns a 6 term expression, where the given property is <b>greater than or
   * equal to</b> the given 'min' literal, and is <b>less than or equal
   * to</b>the given 'max' literal.
   * 
   * @param prop
   *          the property
   * @param min
   *          the minimum literal
   * @param max
   *          the maximum literal
   * @return the expression
   */
  public static Expression between(String prop, Object min, Object max) {
    return new Expression(new Property(prop), Literal.valueOf(min), Literal.valueOf(max));
  }

  /**
   * Returns a 6 term expression, where the given property is <b>greater than or
   * equal to</b> the given 'min' literal, and is <b>less than or equal
   * to</b>the given 'max' literal.
   * 
   * @param prop
   *          the property
   * @param min
   *          the minimum literal
   * @param max
   *          the maximum literal
   * @return the expression
   */
  public static Expression between(Property prop, Object min, Object max) {
    return new Expression(prop, Literal.valueOf(min), Literal.valueOf(max));
  }

  /**
   * Returns a 6 term expression, where the given property is <b>greater than or
   * equal to</b> the given 'min' literal, and is <b>less than or equal
   * to</b>the given 'max' literal.
   * 
   * @param prop
   *          the property
   * @param path
   *          the property path
   * @param min
   *          the minimum literal
   * @param max
   *          the maximum literal
   * @return the expression
   */
  public static Expression between(String prop, String[] path, Object min, Object max) {
    return new Expression(new Property(prop, new Path(path)), Literal.valueOf(min),
        Literal.valueOf(max));
  }

  /**
   * Returns a subquery expression, where the given property is <b>found</b>
   * within the given subquery results collection.
   * 
   * @param prop
   *          the property
   * @param subquery
   *          the subquery
   * @return the subquery expression
   */
  public static Expression in(Property prop, Query subquery) {
    return new Expression(prop, new PredicateOperator(PredicateOperatorName.IN), subquery);
  }

  /**
   * Returns a subquery expression, where the given property is <b>found</b>
   * within the given subquery results collection.
   * 
   * @param prop
   *          the property
   * @param subquery
   *          the subquery
   * @return the subquery expression
   */
  public static Expression in(String prop, Query subquery) {
    return new Expression(new Property(prop), new PredicateOperator(PredicateOperatorName.IN),
        subquery);
  }

  /**
   * Returns a subquery expression, where the given property is <b>found</b>
   * within the given subquery results collection.
   * 
   * @param prop
   *          the property
   * @param path
   *          the path
   * @param subquery
   *          the subquery
   * @return the subquery expression
   */
  public static Expression in(String prop, String[] path, Query subquery) {
    return new Expression(new Property(prop, new Path(path)), new PredicateOperator(
        PredicateOperatorName.IN), subquery);
  }

  /**
   * Returns a subquery expression, where the given property is <b>not found</b>
   * within the given subquery results collection.
   * 
   * @param prop
   *          the property
   * @param subquery
   *          the subquery
   * @return the subquery expression
   */
  public static Expression notIn(Property prop, Query subquery) {
    return new Expression(prop, new PredicateOperator(PredicateOperatorName.NOT_IN), subquery);
  }

  public static Expression exists(Property prop, Query subquery) {
    return new Expression(prop, new PredicateOperator(PredicateOperatorName.EXISTS), subquery);
  }

  /**
   * Returns a simple 1-term expression containing a boolean operator,
   * <b>AND</b>.
   * 
   * @return the simple expression
   */
  public static Expression and() {
    return new Expression(LogicalOperatorName.AND);
  }

  /**
   * Returns a simple 1-term expression containing a boolean operator,
   * <b>OR</b>.
   * 
   * @return the simple expression
   */
  public static Expression or() {
    return new Expression(LogicalOperatorName.OR);
  }

  /**
   * Returns a simple 1-term expression containing a group operator, <b>)</b>.
   * 
   * @return the simple expression
   */
  public static Expression left() {
    return new Expression(new GroupOperator(")"));
  }

  /**
   * Returns a simple 1-term expression containing a group operator, <b>(</b>.
   * 
   * @return the simple expression
   */
  public static Expression right() {
    return new Expression(new GroupOperator("("));
  }

  /**
   * Factory method supporting Jaxen XAPTH relational expressions.
   * 
   * @param expr
   *          a Jaxen XAPTH relational expression
   * @return
   */
  public static Expression valueOf(RelationalExpr expr) {
    LocationPath left = (LocationPath) expr.getLHS();
    NameStep name = (NameStep) left.getSteps().get(0);
    Literal literal = null;
    if (expr.getRHS() instanceof NumberExpr) {
      NumberExpr num = ((NumberExpr) expr.getRHS());
      literal = Literal.valueOf(num.getNumber());
    } else if (expr.getRHS() instanceof LiteralExpr) {
      LiteralExpr lit = ((LiteralExpr) expr.getRHS());
      literal = Literal.valueOf(lit.getLiteral());
    } else {
      literal = Literal.valueOf(expr.getRHS().toString());
    }
    return new Expression(new Property(name.getLocalName()), new RelationalOperator(
        expr.getOperator()), literal);
  }

  /**
   * Factory method supporting Jaxen XAPTH logical expressions.
   * 
   * @param expr
   *          a Jaxen XAPTH logical expression
   * @return
   */
  public static Expression valueOf(LogicalExpr expr) {
    return booleanExprFor(expr);
  }

  public static Expression valueOf(EqualityExpr expr) {
    return booleanExprFor(expr);
  }

  public static Expression noOp() {
    Expression noOp = new Expression();
    noOp.id = Expression.NOOP_EXPR_ID;
    return noOp;
  }

  public boolean isNoOp() {
    return this.id != null && this.id.equals(Expression.NOOP_EXPR_ID);
  }

  private static Expression booleanExprFor(BinaryExpr expr) {
    LocationPath left = (LocationPath) expr.getLHS();
    NameStep name = (NameStep) left.getSteps().get(0);
    Literal literal = null;
    if (expr.getRHS() instanceof NumberExpr) {
      NumberExpr num = ((NumberExpr) expr.getRHS());
      literal = Literal.valueOf(num.getNumber());
    } else if (expr.getRHS() instanceof LiteralExpr) {
      LiteralExpr lit = ((LiteralExpr) expr.getRHS());
      literal = Literal.valueOf(lit.getLiteral());
    } else {
      literal = Literal.valueOf(expr.getRHS().toString());
    }
    return new Expression(new Property(name.getLocalName()), RelationalOperator.valueOf(expr
        .getOperator()), literal);
  }

  /**
   * Gets the value of the terms property.
   * 
   */
  public List<Term> getTerms() {
    if (terms == null) {
      terms = new ArrayList<Term>();
    }
    return this.terms;
  }

  public List<ConfigurationProperty> getConfigurationProperty() {
    if (configurationProperty == null) {
      configurationProperty = new ArrayList<ConfigurationProperty>();
    }
    return this.configurationProperty;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
      for (int i = 0; i < this.getTerms().size(); i++)
        this.getTerms().get(i).accept(visitor);
    visitor.end(this);
  }

  class ExprFinder extends DefaultQueryVisitor {
    private String exprId;
    private Expression result;

    @SuppressWarnings("unused")
    private ExprFinder() {
    }

    public ExprFinder(String exprId) {
      this.exprId = exprId;
    }

    public void start(Expression expression) {
      if (this.exprId.equals(expression.id))
        this.result = expression;
    }

    public Expression getResult() {
      return result;
    }

  }

}
