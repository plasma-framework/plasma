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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.JaxenException;
import org.jaxen.expr.NameStep;
import org.jaxen.expr.Step;
import org.plasma.query.QueryException;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;
import org.plasma.query.xpath.QueryPredicateVisitor;
import org.plasma.query.xpath.QueryXPath;
import org.plasma.sdo.xpath.XPathDetector;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Where", propOrder = { "expressions", "textContent", "importDeclaration",
    "parameters", "parameterDeclaration", "variableDeclaration", "filterId" })
@XmlRootElement(name = "Where")
public class Where implements org.plasma.query.Where, Predicates {

  private static Log log = LogFactory.getFactory().getInstance(Where.class);
  @XmlElement(name = "Expression", required = true)
  protected List<Expression> expressions;
  @XmlElement(name = "TextContent")
  protected TextContent textContent;
  @XmlElement(name = "ImportDeclaration")
  protected ImportDeclaration importDeclaration;
  @XmlElement(name = "Parameter", required = true)
  protected List<Parameter> parameters;
  @XmlElement(name = "ParameterDeclaration")
  protected ParameterDeclaration parameterDeclaration;
  @XmlElement(name = "VariableDeclaration")
  protected VariableDeclaration variableDeclaration;
  @XmlElement(name = "FilterId")
  protected FilterId filterId;

  public Where() {
    super();
  }

  public Where(Expression expr) {
    this();
    getExpressions().add(expr);
  }

  public Where(Expression[] exprs) {
    this();
    getExpressions().add(new Expression(exprs));
  }

  private class PrependPathVisitor extends DefaultQueryVisitor {
    private Path path;

    @SuppressWarnings("unused")
    private PrependPathVisitor() {
    }

    public PrependPathVisitor(Path path) {
      this.path = path;
    }

    public void start(Property property) {
      if (this.path != null) {
        property.setPath(this.path);
      }

      super.start(property);
    }

    public void start(WildcardProperty property) {
      if (this.path != null) {
        property.setPath(this.path);
      }

      super.start(property);
    }
  }

  public Where(String content) {
    this();
    if (XPathDetector.isXPath(content)) {
      try {
        Path path = null;
        PathNode currPathNode = null;
        // predicate alone is not a legal XPath, yet is what
        // we can expect.
        if (content.startsWith(XPathDetector.PREDICATE_START))
          content = "root" + content;
        else
          content = "root/" + content;

        QueryXPath xpath = new QueryXPath(content);
        List<Step> steps = xpath.getSteps();
        for (int i = 0; i < steps.size(); i++) {
          Step step = steps.get(i);
          if (!(step instanceof NameStep))
            throw new QueryException("cannot determine traversal path - "
                + "expected named path step not, " + step.getClass().getName());
          NameStep nameStep = (NameStep) step;
          if (log.isDebugEnabled())
            log.debug("step: " + nameStep.getLocalName());

          // if not "root" node
          if (i > 0) {
            if (path == null)
              path = new Path();
            if (log.isDebugEnabled())
              log.debug("adding path node: " + nameStep.getLocalName());
            currPathNode = new PathNode(nameStep.getLocalName());
            path.addPathNode(currPathNode);
          }

          Where currentWhere = null;
          if (step.getPredicates() != null && step.getPredicates().size() > 0) {
            QueryPredicateVisitor predicateVisitor = new QueryPredicateVisitor();
            xpath.acceptBreadthFirst(step, predicateVisitor);
            currentWhere = predicateVisitor.getResult();
          }

          // if last step
          if (i + 1 == steps.size()) {
            if (currentWhere != null) {
              if (path != null) {
                currentWhere.accept(new PrependPathVisitor(path));
              }
              // copy predicate expressions to THIS Where
              for (Expression expr : currentWhere.getExpressions()) {
                this.getExpressions().add(expr);
              }
              currentWhere.getExpressions().clear();
            } else
              throw new QueryException("expected predicate(s) for last path step '"
                  + nameStep.getLocalName() + "'");
          } else {
            if (currentWhere != null)
              currPathNode.setWhere(currentWhere);
          }
        }
      } catch (JaxenException e) {
        throw new QueryException(e);
      }
    } else {
      this.textContent = new TextContent();
      this.textContent.setValue(content);
    }
  }

  public Where(String content, Parameter[] params) {
    this(content);
    for (int i = 0; i < params.length; i++)
      getParameters().add(params[i]);
  }

  public Where(FilterId filterId) {
    this();
    this.filterId = filterId;
  }

  public void addExpression(Expression e) {
    getExpressions().add(e);
  }

  @Override
  public List<Expression> getExpressions() {
    if (expressions == null) {
      expressions = new ArrayList<Expression>();
    }
    return this.expressions;
  }

  public int getExpressionCount() {
    return getExpressions().size();
  }

  /**
   * Gets the value of the textContent property.
   * 
   * @return possible object is {@link TextContent }
   * 
   */
  public TextContent getTextContent() {
    return textContent;
  }

  /**
   * Sets the value of the textContent property.
   * 
   * @param value
   *          allowed object is {@link TextContent }
   * 
   */
  public void setTextContent(TextContent value) {
    this.textContent = value;
  }

  /**
   * Gets the value of the importDeclaration property.
   * 
   * @return possible object is {@link ImportDeclaration }
   * 
   */
  public ImportDeclaration getImportDeclaration() {
    return importDeclaration;
  }

  /**
   * Sets the value of the importDeclaration property.
   * 
   * @param value
   *          allowed object is {@link ImportDeclaration }
   * 
   */
  public void setImportDeclaration(ImportDeclaration value) {
    this.importDeclaration = value;
  }

  /**
   * Gets the value of the parameters property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the parameters property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getParameters().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link Parameter }
   * 
   * 
   */
  public List<Parameter> getParameters() {
    if (parameters == null) {
      parameters = new ArrayList<Parameter>();
    }
    return this.parameters;
  }

  /**
   * Gets the value of the parameterDeclaration property.
   * 
   * @return possible object is {@link ParameterDeclaration }
   * 
   */
  public ParameterDeclaration getParameterDeclaration() {
    return parameterDeclaration;
  }

  /**
   * Sets the value of the parameterDeclaration property.
   * 
   * @param value
   *          allowed object is {@link ParameterDeclaration }
   * 
   */
  public void setParameterDeclaration(ParameterDeclaration value) {
    this.parameterDeclaration = value;
  }

  /**
   * Gets the value of the variableDeclaration property.
   * 
   * @return possible object is {@link VariableDeclaration }
   * 
   */
  public VariableDeclaration getVariableDeclaration() {
    return variableDeclaration;
  }

  /**
   * Sets the value of the variableDeclaration property.
   * 
   * @param value
   *          allowed object is {@link VariableDeclaration }
   * 
   */
  public void setVariableDeclaration(VariableDeclaration value) {
    this.variableDeclaration = value;
  }

  /**
   * Gets the value of the filterId property.
   * 
   * @return possible object is {@link FilterId }
   * 
   */
  public FilterId getFilterId() {
    return filterId;
  }

  /**
   * Sets the value of the filterId property.
   * 
   * @param value
   *          allowed object is {@link FilterId }
   * 
   */
  public void setFilterId(FilterId value) {
    this.filterId = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.plasma.query.model.Where2#accept(org.plasma.query.model.QueryVisitor)
   */
  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
      for (int i = 0; i < this.getExpressions().size(); i++)
        ((Expression) this.getExpressions().get(i)).accept(visitor);
    visitor.end(this);
  }

  public Variable[] getVariables() {
    final ArrayList<Variable> list = new ArrayList<Variable>(12);
    QueryVisitor visitor = new DefaultQueryVisitor() {
      public void start(Variable variable) {
        list.add(variable);
      }
    };
    this.accept(visitor);
    Variable[] results = new Variable[list.size()];
    list.toArray(results);
    return results;
  }

}
