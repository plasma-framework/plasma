package org.plasma.provisioning.rdb.oracle.sys.query;

import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.query.DataProperty;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.Expression;


import org.plasma.provisioning.rdb.oracle.sys.query.QColumn;
import org.plasma.provisioning.rdb.oracle.sys.query.QColumnComment;
import org.plasma.provisioning.rdb.oracle.sys.query.QColumnConstraint;
import org.plasma.provisioning.rdb.oracle.sys.query.QConstraint;
import org.plasma.provisioning.rdb.oracle.sys.query.QTableComment;
import org.plasma.provisioning.rdb.oracle.sys.Table;

/**
 * Represents a system table definition
 * <p></p>
 * Generated Domain Specific Language (DSL) implementation class representing the domain model entity <b>Table</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_TABLES</b>.
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
	 * Returns a DSL query element for reference property, <b>column</b>.
	 * @return a DSL query element for reference property, <b>column</b>.
	 */
	public QColumn column() {
		return new QColumn(this, Table.PROPERTY.column.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>column</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>column</b>.
	 */
	public QColumn column(Expression expr) {
		return new QColumn(this, Table.PROPERTY.column.name(), expr);
	}

	/**
	 * Returns a DSL query element for reference property, <b>columnComment</b>.
	 * @return a DSL query element for reference property, <b>columnComment</b>.
	 */
	public QColumnComment columnComment() {
		return new QColumnComment(this, Table.PROPERTY.columnComment.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>columnComment</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>columnComment</b>.
	 */
	public QColumnComment columnComment(Expression expr) {
		return new QColumnComment(this, Table.PROPERTY.columnComment.name(), expr);
	}

	/**
	 * Returns a DSL query element for reference property, <b>columnConstraint</b>.
	 * @return a DSL query element for reference property, <b>columnConstraint</b>.
	 */
	public QColumnConstraint columnConstraint() {
		return new QColumnConstraint(this, Table.PROPERTY.columnConstraint.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>columnConstraint</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>columnConstraint</b>.
	 */
	public QColumnConstraint columnConstraint(Expression expr) {
		return new QColumnConstraint(this, Table.PROPERTY.columnConstraint.name(), expr);
	}

	/**
	 * Returns a DSL query element for reference property, <b>constraint</b>.
	 * @return a DSL query element for reference property, <b>constraint</b>.
	 */
	public QConstraint constraint() {
		return new QConstraint(this, Table.PROPERTY.constraint.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>constraint</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>constraint</b>.
	 */
	public QConstraint constraint(Expression expr) {
		return new QConstraint(this, Table.PROPERTY.constraint.name(), expr);
	}

	/**
	 * Returns a DSL data element for property, <b>owner</b>.
	 * @return a DSL data element for property, <b>owner</b>.
	 */
	public DataProperty owner() {
		return new DataNode(this, Table.PROPERTY.owner.name());
	}

	/**
	 * Returns a DSL query element for reference property, <b>tableComment</b>.
	 * @return a DSL query element for reference property, <b>tableComment</b>.
	 */
	public QTableComment tableComment() {
		return new QTableComment(this, Table.PROPERTY.tableComment.name());
	}
	
	/**
	 * Returns a DSL query element for reference property, <b>tableComment</b>, while adding the given path predicate expression. 
	 * Path predicate expressions are used to restrict
	 * the query results for a collection property within a <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaDataGraph.html">DataGraph</a>.
	 * @param expr the path predicate expression
	 * @return a DSL query element for reference property, <b>tableComment</b>.
	 */
	public QTableComment tableComment(Expression expr) {
		return new QTableComment(this, Table.PROPERTY.tableComment.name(), expr);
	}

	/**
	 * Returns a DSL data element for property, <b>tableName</b>.
	 * @return a DSL data element for property, <b>tableName</b>.
	 */
	public DataProperty tableName() {
		return new DataNode(this, Table.PROPERTY.tableName.name());
	}
}