package org.plasma.provisioning.rdb.mysql.v5_5.query;

import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.query.DataProperty;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.Expression;


import org.plasma.provisioning.rdb.mysql.v5_5.query.QTableColumn;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QTableColumnConstraint;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QTableColumnKeyUsage;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QTableConstraint;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QView;
import org.plasma.provisioning.rdb.mysql.v5_5.Table;

/**
 * Generated Domain Specific Language (DSL) implementation class representing the domain model entity <b>Table</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>TABLES</b>.
 *
 */
public class QTable extends DomainRoot
{


	private QTable() {
		super(PlasmaTypeHelper.INSTANCE.getType(Table.class));
	}
	
	/**
	 * Constructor which instantiates a domain query path node. A path may
	 * span multiple namespaces and therefore Java inplementation packages
	 * based on the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html">Condiguration</a>.
	 * Note: while this constructor is public, it is not for application use!
	 * @param source the source path node
	 * @param sourceProperty the source property logical name
	 */
	public QTable(PathNode source, String sourceProperty) {
		super(source, sourceProperty);
	}
	
	/**
	 * Constructor which instantiates a domain query path node. A path may
	 * span multiple namespaces and therefore Java inplementation packages
	 * based on the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html">Condiguration</a>.
	 * Note: while this constructor is public, it is not for application use!
	 * @param source the source path node
	 * @param sourceProperty the source property logical name
	 * @param expr the path predicate expression
	 */
	public QTable(PathNode source, String sourceProperty, Expression expr) {
		super(source, sourceProperty, expr);
	}

	/**
	 * Returns a new DSL query for <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b> which can be used either as a query root or
	 * as the start (entry point) for a new path predicate expression.
	 * @return a new DSL query
	 */
	public static QTable newQuery() {
		return new QTable();
	}

	/**
	 * Returns a DSL data element for property, <b>autoIncrement</b>.
	 * @return a DSL data element for property, <b>autoIncrement</b>.
	 */
	public DataProperty autoIncrement() {
		return new DataNode(this, Table.PROPERTY.autoIncrement.name());
	}

	/**
	 * Returns a DSL data element for property, <b>owner</b>.
	 * @return a DSL data element for property, <b>owner</b>.
	 */
	public DataProperty owner() {
		return new DataNode(this, Table.PROPERTY.owner.name());
	}

	/**
	 * Returns a DSL query element for reference property, <b>tableColumn</b>.
	 * @return a DSL query element for reference property, <b>tableColumn</b>.
	 */
	public QTableColumn tableColumn() {
		return new QTableColumn(this, Table.PROPERTY.tableColumn.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>tableColumn</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>tableColumn</b>.
	 */
	public QTableColumn tableColumn(Expression expr) {
		return new QTableColumn(this, Table.PROPERTY.tableColumn.name(), expr);
	}

	/**
	 * Returns a DSL query element for reference property, <b>tableColumnConstraint</b>.
	 * @return a DSL query element for reference property, <b>tableColumnConstraint</b>.
	 */
	public QTableColumnConstraint tableColumnConstraint() {
		return new QTableColumnConstraint(this, Table.PROPERTY.tableColumnConstraint.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>tableColumnConstraint</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>tableColumnConstraint</b>.
	 */
	public QTableColumnConstraint tableColumnConstraint(Expression expr) {
		return new QTableColumnConstraint(this, Table.PROPERTY.tableColumnConstraint.name(), expr);
	}

	/**
	 * Returns a DSL query element for reference property, <b>tableColumnKeyUsage</b>.
	 * @return a DSL query element for reference property, <b>tableColumnKeyUsage</b>.
	 */
	public QTableColumnKeyUsage tableColumnKeyUsage() {
		return new QTableColumnKeyUsage(this, Table.PROPERTY.tableColumnKeyUsage.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>tableColumnKeyUsage</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>tableColumnKeyUsage</b>.
	 */
	public QTableColumnKeyUsage tableColumnKeyUsage(Expression expr) {
		return new QTableColumnKeyUsage(this, Table.PROPERTY.tableColumnKeyUsage.name(), expr);
	}

	/**
	 * Returns a DSL data element for property, <b>tableComment</b>.
	 * @return a DSL data element for property, <b>tableComment</b>.
	 */
	public DataProperty tableComment() {
		return new DataNode(this, Table.PROPERTY.tableComment.name());
	}

	/**
	 * Returns a DSL query element for reference property, <b>tableConstraint</b>.
	 * @return a DSL query element for reference property, <b>tableConstraint</b>.
	 */
	public QTableConstraint tableConstraint() {
		return new QTableConstraint(this, Table.PROPERTY.tableConstraint.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>tableConstraint</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>tableConstraint</b>.
	 */
	public QTableConstraint tableConstraint(Expression expr) {
		return new QTableConstraint(this, Table.PROPERTY.tableConstraint.name(), expr);
	}

	/**
	 * Returns a DSL data element for property, <b>tableName</b>.
	 * @return a DSL data element for property, <b>tableName</b>.
	 */
	public DataProperty tableName() {
		return new DataNode(this, Table.PROPERTY.tableName.name());
	}

	/**
	 * Returns a DSL data element for property, <b>tableType</b>.
	 * @return a DSL data element for property, <b>tableType</b>.
	 */
	public DataProperty tableType() {
		return new DataNode(this, Table.PROPERTY.tableType.name());
	}

	/**
	 * Returns a DSL query element for reference property, <b>view</b>.
	 * @return a DSL query element for reference property, <b>view</b>.
	 */
	public QView view() {
		return new QView(this, Table.PROPERTY.view.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>view</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>view</b>.
	 */
	public QView view(Expression expr) {
		return new QView(this, Table.PROPERTY.view.name(), expr);
	}
}