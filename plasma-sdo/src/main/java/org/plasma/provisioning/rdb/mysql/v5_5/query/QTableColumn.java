package org.plasma.provisioning.rdb.mysql.v5_5.query;

import org.plasma.provisioning.rdb.mysql.v5_5.TableColumn;
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
 * <b>COLUMNS</b>.
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
   * Returns a DSL data element for property, <b>charMaxLength</b>.
   * 
   * @return a DSL data element for property, <b>charMaxLength</b>.
   */
  public DataProperty charMaxLength() {
    return new DataNode(this, TableColumn.PROPERTY.charMaxLength.name());
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
   * Returns a DSL data element for property, <b>columnComment</b>.
   * 
   * @return a DSL data element for property, <b>columnComment</b>.
   */
  public DataProperty columnComment() {
    return new DataNode(this, TableColumn.PROPERTY.columnComment.name());
  }

  /**
   * Returns a DSL data element for property, <b>columnKey</b>.
   * 
   * @return a DSL data element for property, <b>columnKey</b>.
   */
  public DataProperty columnKey() {
    return new DataNode(this, TableColumn.PROPERTY.columnKey.name());
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
   * Returns a DSL data element for property, <b>columnType</b>.
   * 
   * @return a DSL data element for property, <b>columnType</b>.
   */
  public DataProperty columnType() {
    return new DataNode(this, TableColumn.PROPERTY.columnType.name());
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
   * Returns a DSL data element for property, <b>octetMaxLength</b>.
   * 
   * @return a DSL data element for property, <b>octetMaxLength</b>.
   */
  public DataProperty octetMaxLength() {
    return new DataNode(this, TableColumn.PROPERTY.octetMaxLength.name());
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