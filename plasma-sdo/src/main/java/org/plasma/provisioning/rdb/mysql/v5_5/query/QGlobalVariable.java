package org.plasma.provisioning.rdb.mysql.v5_5.query;

import org.plasma.provisioning.rdb.mysql.v5_5.GlobalVariable;
import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.sdo.helper.PlasmaTypeHelper;

/**
 * Generated Domain Specific Language (DSL) implementation class representing
 * the domain model entity <b>GlobalVariable</b>.
 *
 * <p>
 * </p>
 * <b>Data Store Mapping:</b> Corresponds to the physical data store entity
 * <b>GLOBAL_VARIABLES</b>.
 *
 */
public class QGlobalVariable extends DomainRoot {

  private QGlobalVariable() {
    super(PlasmaTypeHelper.INSTANCE.getType(GlobalVariable.class));
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
  public QGlobalVariable(PathNode source, String sourceProperty) {
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
  public QGlobalVariable(PathNode source, String sourceProperty, Expression expr) {
    super(source, sourceProperty, expr);
  }

  /**
   * Returns a new DSL query for <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a> <b>GlobalVariable</b> which can be used either as a query root or
   * as the start (entry point) for a new path predicate expression.
   * 
   * @return a new DSL query
   */
  public static QGlobalVariable newQuery() {
    return new QGlobalVariable();
  }

  /**
   * Returns a DSL data element for property, <b>name</b>.
   * 
   * @return a DSL data element for property, <b>name</b>.
   */
  public DataProperty name() {
    return new DataNode(this, GlobalVariable.PROPERTY.name.name());
  }

  /**
   * Returns a DSL data element for property, <b>value</b>.
   * 
   * @return a DSL data element for property, <b>value</b>.
   */
  public DataProperty value() {
    return new DataNode(this, GlobalVariable.PROPERTY.value.name());
  }
}