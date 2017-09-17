package org.plasma.provisioning.rdb.oracle.g11.sys.query;

import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumn;
import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.sdo.helper.PlasmaTypeHelper;

/**
 * Represents a system column definition which is linked to its system Table
 * definition by association.
 * <p>
 * </p>
 * Generated Domain Specific Language (DSL) implementation class representing
 * the domain model entity <b>TableColumn</b>.
 *
 * <p>
 * </p>
 * <b>Data Store Mapping:</b> Corresponds to the physical data store entity
 * <b>ALL_TAB_COLUMNS</b>.
 *
 */
public class QTableColumn extends DomainRoot {

  private QTableColumn() {
    super(PlasmaTypeHelper.INSTANCE.getType(TableColumn.class));
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
  public QTableColumn(PathNode source, String sourceProperty) {
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
  public QTableColumn(PathNode source, String sourceProperty, Expression expr) {
    super(source, sourceProperty, expr);
  }

  /**
   * Returns a new DSL query for <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a> <b>TableColumn</b> which can be used either as a query root or as
   * the start (entry point) for a new path predicate expression.
   * 
   * @return a new DSL query
   */
  public static QTableColumn newQuery() {
    return new QTableColumn();
  }

  /**
   * Returns a DSL data element for property, <b>characterSetName</b>.
   * 
   * @return a DSL data element for property, <b>characterSetName</b>.
   */
  public DataProperty characterSetName() {
    return new DataNode(this, TableColumn.PROPERTY.characterSetName.name());
  }

  /**
   * Returns a DSL data element for property, <b>columnName</b>.
   * 
   * @return a DSL data element for property, <b>columnName</b>.
   */
  public DataProperty columnName() {
    return new DataNode(this, TableColumn.PROPERTY.columnName.name());
  }

  /**
   * Returns a DSL data element for property, <b>column_id</b>.
   * 
   * @return a DSL data element for property, <b>column_id</b>.
   */
  public DataProperty column_id() {
    return new DataNode(this, TableColumn.PROPERTY.column_id.name());
  }

  /**
   * Returns a DSL data element for property, <b>dataDefault</b>.
   * 
   * @return a DSL data element for property, <b>dataDefault</b>.
   */
  public DataProperty dataDefault() {
    return new DataNode(this, TableColumn.PROPERTY.dataDefault.name());
  }

  /**
   * Returns a DSL data element for property, <b>dataLength</b>.
   * 
   * @return a DSL data element for property, <b>dataLength</b>.
   */
  public DataProperty dataLength() {
    return new DataNode(this, TableColumn.PROPERTY.dataLength.name());
  }

  /**
   * Returns a DSL data element for property, <b>dataPrecision</b>.
   * 
   * @return a DSL data element for property, <b>dataPrecision</b>.
   */
  public DataProperty dataPrecision() {
    return new DataNode(this, TableColumn.PROPERTY.dataPrecision.name());
  }

  /**
   * Returns a DSL data element for property, <b>dataScale</b>.
   * 
   * @return a DSL data element for property, <b>dataScale</b>.
   */
  public DataProperty dataScale() {
    return new DataNode(this, TableColumn.PROPERTY.dataScale.name());
  }

  /**
   * Returns a DSL data element for property, <b>dataType</b>.
   * 
   * @return a DSL data element for property, <b>dataType</b>.
   */
  public DataProperty dataType() {
    return new DataNode(this, TableColumn.PROPERTY.dataType.name());
  }

  /**
   * Returns a DSL data element for property, <b>nullable</b>.
   * 
   * @return a DSL data element for property, <b>nullable</b>.
   */
  public DataProperty nullable() {
    return new DataNode(this, TableColumn.PROPERTY.nullable.name());
  }

  /**
   * Returns a DSL data element for property, <b>owner</b>.
   * 
   * @return a DSL data element for property, <b>owner</b>.
   */
  public DataProperty owner() {
    return new DataNode(this, TableColumn.PROPERTY.owner.name());
  }

  /**
   * Returns a DSL query element for reference property, <b>table</b>.
   * 
   * @return a DSL query element for reference property, <b>table</b>.
   */
  public QTable table() {
    return new QTable(this, TableColumn.PROPERTY.table.name());
  }
}