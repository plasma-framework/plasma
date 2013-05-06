package org.plasma.provisioning.rdb.oracle.sys.query;

import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.query.DataProperty;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.Expression;


import org.plasma.provisioning.rdb.oracle.sys.query.QTable;
import org.plasma.provisioning.rdb.oracle.sys.ColumnConstraint;

/**
 * A constraint definition
 * <p></p>
 * Generated Domain Specific Language (DSL) implementation class representing the domain model entity <b>ColumnConstraint</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_CONS_COLUMNS</b>.
 *
 */
public class QColumnConstraint extends DomainRoot
{


	private QColumnConstraint() {
		super(PlasmaTypeHelper.INSTANCE.getType(ColumnConstraint.class));
	}
	
	/**
	 * Constructor which instantiates a domain query path node. A path may
	 * span multiple namespaces and therefore Java inplementation packages
	 * based on the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html">Condiguration</a>.
	 * Note: while this constructor is public, it is not for application use!
	 * @param source the source path node
	 * @param sourceProperty the source property logical name
	 */
	public QColumnConstraint(PathNode source, String sourceProperty) {
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
	public QColumnConstraint(PathNode source, String sourceProperty, Expression expr) {
		super(source, sourceProperty, expr);
	}

	/**
	 * Returns a new DSL query for <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>ColumnConstraint</b> which can be used either as a query root or
	 * as the start (entry point) for a new path predicate expression.
	 * @return a new DSL query
	 */
	public static QColumnConstraint newQuery() {
		return new QColumnConstraint();
	}

	/**
	 * Returns a DSL data element for property, <b>columnName</b>.
	 * @return a DSL data element for property, <b>columnName</b>.
	 */
	public DataProperty columnName() {
		return new DataNode(this, ColumnConstraint.PROPERTY.columnName.name());
	}

	/**
	 * Returns a DSL data element for property, <b>constraintName</b>.
	 * @return a DSL data element for property, <b>constraintName</b>.
	 */
	public DataProperty constraintName() {
		return new DataNode(this, ColumnConstraint.PROPERTY.constraintName.name());
	}

	/**
	 * Returns a DSL data element for property, <b>owner</b>.
	 * @return a DSL data element for property, <b>owner</b>.
	 */
	public DataProperty owner() {
		return new DataNode(this, ColumnConstraint.PROPERTY.owner.name());
	}

	/**
	 * Returns a DSL query element for reference property, <b>table</b>.
	 * @return a DSL query element for reference property, <b>table</b>.
	 */
	public QTable table() {
		return new QTable(this, ColumnConstraint.PROPERTY.table.name());
	}
}