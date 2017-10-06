package org.plasma.provisioning.rdb.oracle.g11.sys.query;

import org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumnComment;
import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.sdo.helper.PlasmaTypeHelper;

/**
 * The comment for a column
 * <p>
 * </p>
 * Generated Domain Specific Language (DSL) implementation class representing
 * the domain model entity <b>ViewColumnComment</b>.
 *
 * <p>
 * </p>
 * <b>Data Store Mapping:</b> Corresponds to the physical data store entity
 * <b>ALL_COL_COMMENTS</b>.
 *
 */
public class QViewColumnComment extends DomainRoot {

  private QViewColumnComment() {
    super(PlasmaTypeHelper.INSTANCE.getType(ViewColumnComment.class));
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
  public QViewColumnComment(PathNode source, String sourceProperty) {
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
  public QViewColumnComment(PathNode source, String sourceProperty, Expression expr) {
    super(source, sourceProperty, expr);
  }

  /**
   * Returns a new DSL query for <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a> <b>ViewColumnComment</b> which can be used either as a query root
   * or as the start (entry point) for a new path predicate expression.
   * 
   * @return a new DSL query
   */
  public static QViewColumnComment newQuery() {
    return new QViewColumnComment();
  }

  /**
   * Returns a DSL data element for property, <b>columnName</b>.
   * 
   * @return a DSL data element for property, <b>columnName</b>.
   */
  public DataProperty columnName() {
    return new DataNode(this, ViewColumnComment.PROPERTY.columnName.name());
  }

  /**
   * Returns a DSL data element for property, <b>comments</b>.
   * 
   * @return a DSL data element for property, <b>comments</b>.
   */
  public DataProperty comments() {
    return new DataNode(this, ViewColumnComment.PROPERTY.comments.name());
  }

  /**
   * Returns a DSL data element for property, <b>owner</b>.
   * 
   * @return a DSL data element for property, <b>owner</b>.
   */
  public DataProperty owner() {
    return new DataNode(this, ViewColumnComment.PROPERTY.owner.name());
  }

  /**
   * Returns a DSL query element for reference property, <b>view</b>.
   * 
   * @return a DSL query element for reference property, <b>view</b>.
   */
  public QView view() {
    return new QView(this, ViewColumnComment.PROPERTY.view.name());
  }
}