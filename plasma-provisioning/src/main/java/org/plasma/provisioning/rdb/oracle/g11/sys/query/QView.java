package org.plasma.provisioning.rdb.oracle.g11.sys.query;

import org.plasma.provisioning.rdb.oracle.g11.sys.View;
import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.sdo.helper.PlasmaTypeHelper;

/**
 * Represents a system view definition
 * <p>
 * </p>
 * Generated Domain Specific Language (DSL) implementation class representing
 * the domain model entity <b>View</b>.
 *
 * <p>
 * </p>
 * <b>Data Store Mapping:</b> Corresponds to the physical data store entity
 * <b>ALL_VIEWS</b>.
 *
 */
public class QView extends DomainRoot {

  private QView() {
    super(PlasmaTypeHelper.INSTANCE.getType(View.class));
  }

  /**
   * Constructor which instantiates a domain query path node. A path may span
   * multiple namespaces and therefore Java inplementation packages based on the
   * <a href=
   * "http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html"
   * >Condiguration</a>. Note: while this constructor is public, it is not for
   * application use!
   * 
   * @param source
   *          the source path node
   * @param sourceProperty
   *          the source property logical name
   */
  public QView(PathNode source, String sourceProperty) {
    super(source, sourceProperty);
  }

  /**
   * Constructor which instantiates a domain query path node. A path may span
   * multiple namespaces and therefore Java inplementation packages based on the
   * <a href=
   * "http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html"
   * >Condiguration</a>. Note: while this constructor is public, it is not for
   * application use!
   * 
   * @param source
   *          the source path node
   * @param sourceProperty
   *          the source property logical name
   * @param expr
   *          the path predicate expression
   */
  public QView(PathNode source, String sourceProperty, Expression expr) {
    super(source, sourceProperty, expr);
  }

  /**
   * Returns a new DSL query for <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a> <b>View</b> which can be used either as a query root or as the
   * start (entry point) for a new path predicate expression.
   * 
   * @return a new DSL query
   */
  public static QView newQuery() {
    return new QView();
  }

  /**
   * Returns a DSL data element for property, <b>editioningView</b>.
   * 
   * @return a DSL data element for property, <b>editioningView</b>.
   */
  public DataProperty editioningView() {
    return new DataNode(this, View.PROPERTY.editioningView.name());
  }

  /**
   * Returns a DSL data element for property, <b>oidText</b>.
   * 
   * @return a DSL data element for property, <b>oidText</b>.
   */
  public DataProperty oidText() {
    return new DataNode(this, View.PROPERTY.oidText.name());
  }

  /**
   * Returns a DSL data element for property, <b>oidTextLength</b>.
   * 
   * @return a DSL data element for property, <b>oidTextLength</b>.
   */
  public DataProperty oidTextLength() {
    return new DataNode(this, View.PROPERTY.oidTextLength.name());
  }

  /**
   * Returns a DSL data element for property, <b>owner</b>.
   * 
   * @return a DSL data element for property, <b>owner</b>.
   */
  public DataProperty owner() {
    return new DataNode(this, View.PROPERTY.owner.name());
  }

  /**
   * Returns a DSL data element for property, <b>readOnly</b>.
   * 
   * @return a DSL data element for property, <b>readOnly</b>.
   */
  public DataProperty readOnly() {
    return new DataNode(this, View.PROPERTY.readOnly.name());
  }

  /**
   * Returns a DSL data element for property, <b>superviewName</b>.
   * 
   * @return a DSL data element for property, <b>superviewName</b>.
   */
  public DataProperty superviewName() {
    return new DataNode(this, View.PROPERTY.superviewName.name());
  }

  /**
   * Returns a DSL data element for property, <b>text</b>.
   * 
   * @return a DSL data element for property, <b>text</b>.
   */
  public DataProperty text() {
    return new DataNode(this, View.PROPERTY.text.name());
  }

  /**
   * Returns a DSL data element for property, <b>textLength</b>.
   * 
   * @return a DSL data element for property, <b>textLength</b>.
   */
  public DataProperty textLength() {
    return new DataNode(this, View.PROPERTY.textLength.name());
  }

  /**
   * Returns a DSL data element for property, <b>typeText</b>.
   * 
   * @return a DSL data element for property, <b>typeText</b>.
   */
  public DataProperty typeText() {
    return new DataNode(this, View.PROPERTY.typeText.name());
  }

  /**
   * Returns a DSL data element for property, <b>typeTextLength</b>.
   * 
   * @return a DSL data element for property, <b>typeTextLength</b>.
   */
  public DataProperty typeTextLength() {
    return new DataNode(this, View.PROPERTY.typeTextLength.name());
  }

  /**
   * Returns a DSL query element for reference property, <b>viewColumn</b>.
   * 
   * @return a DSL query element for reference property, <b>viewColumn</b>.
   */
  public QViewColumn viewColumn() {
    return new QViewColumn(this, View.PROPERTY.viewColumn.name());
  }

  /**
   * Returns a DSL query element for reference property, <b>viewColumn</b>,
   * while adding the given path predicate expression. Path predicate
   * expressions are used to restrict the query results for a collection
   * property within a <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html"
   * >DataGraph</a>.
   * 
   * @param expr
   *          the path predicate expression
   * @return a DSL query element for reference property, <b>viewColumn</b>.
   */
  public QViewColumn viewColumn(Expression expr) {
    return new QViewColumn(this, View.PROPERTY.viewColumn.name(), expr);
  }

  /**
   * Returns a DSL query element for reference property,
   * <b>viewColumnComment</b>.
   * 
   * @return a DSL query element for reference property,
   *         <b>viewColumnComment</b>.
   */
  public QViewColumnComment viewColumnComment() {
    return new QViewColumnComment(this, View.PROPERTY.viewColumnComment.name());
  }

  /**
   * Returns a DSL query element for reference property,
   * <b>viewColumnComment</b>, while adding the given path predicate expression.
   * Path predicate expressions are used to restrict the query results for a
   * collection property within a <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html"
   * >DataGraph</a>.
   * 
   * @param expr
   *          the path predicate expression
   * @return a DSL query element for reference property,
   *         <b>viewColumnComment</b>.
   */
  public QViewColumnComment viewColumnComment(Expression expr) {
    return new QViewColumnComment(this, View.PROPERTY.viewColumnComment.name(), expr);
  }

  /**
   * Returns a DSL query element for reference property, <b>viewComment</b>.
   * 
   * @return a DSL query element for reference property, <b>viewComment</b>.
   */
  public QViewComment viewComment() {
    return new QViewComment(this, View.PROPERTY.viewComment.name());
  }

  /**
   * Returns a DSL query element for reference property, <b>viewComment</b>,
   * while adding the given path predicate expression. Path predicate
   * expressions are used to restrict the query results for a collection
   * property within a <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html"
   * >DataGraph</a>.
   * 
   * @param expr
   *          the path predicate expression
   * @return a DSL query element for reference property, <b>viewComment</b>.
   */
  public QViewComment viewComment(Expression expr) {
    return new QViewComment(this, View.PROPERTY.viewComment.name(), expr);
  }

  /**
   * Returns a DSL data element for property, <b>viewName</b>.
   * 
   * @return a DSL data element for property, <b>viewName</b>.
   */
  public DataProperty viewName() {
    return new DataNode(this, View.PROPERTY.viewName.name());
  }

  /**
   * Returns a DSL data element for property, <b>viewType</b>.
   * 
   * @return a DSL data element for property, <b>viewType</b>.
   */
  public DataProperty viewType() {
    return new DataNode(this, View.PROPERTY.viewType.name());
  }

  /**
   * Returns a DSL data element for property, <b>viewTypeOwner</b>.
   * 
   * @return a DSL data element for property, <b>viewTypeOwner</b>.
   */
  public DataProperty viewTypeOwner() {
    return new DataNode(this, View.PROPERTY.viewTypeOwner.name());
  }
}