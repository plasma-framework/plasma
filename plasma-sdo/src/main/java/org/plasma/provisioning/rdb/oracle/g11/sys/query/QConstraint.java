package org.plasma.provisioning.rdb.oracle.g11.sys.query;

import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.query.DataProperty;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.Expression;


import org.plasma.provisioning.rdb.oracle.g11.sys.query.QTable;
import org.plasma.provisioning.rdb.oracle.g11.sys.Constraint;

/**
 * A constraint definition
 * <p></p>
 * Generated Domain Specific Language (DSL) implementation class representing the domain model entity <b>Constraint</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_CONSTRAINTS</b>.
 *
 */
public class QConstraint extends DomainRoot
{


	private QConstraint() {
		super(PlasmaTypeHelper.INSTANCE.getType(Constraint.class));
	}
	
	/**
	 * Constructor which instantiates a domain query path node. A path may
	 * span multiple namespaces and therefore Java inplementation packages
	 * based on the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html">Condiguration</a>.
	 * Note: while this constructor is public, it is not for application use!
	 * @param source the source path node
	 * @param sourceProperty the source property logical name
	 */
	public QConstraint(PathNode source, String sourceProperty) {
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
	public QConstraint(PathNode source, String sourceProperty, Expression expr) {
		super(source, sourceProperty, expr);
	}

	/**
	 * Returns a new DSL query for <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Constraint</b> which can be used either as a query root or
	 * as the start (entry point) for a new path predicate expression.
	 * @return a new DSL query
	 */
	public static QConstraint newQuery() {
		return new QConstraint();
	}

	/**
	 * Returns a DSL data element for property, <b>constraintName</b>.
	 * @return a DSL data element for property, <b>constraintName</b>.
	 */
	public DataProperty constraintName() {
		return new DataNode(this, Constraint.PROPERTY.constraintName.name());
	}

	/**
	 * Returns a DSL data element for property, <b>constraintType</b>.
	 * @return a DSL data element for property, <b>constraintType</b>.
	 */
	public DataProperty constraintType() {
		return new DataNode(this, Constraint.PROPERTY.constraintType.name());
	}

	/**
	 * Returns a DSL data element for property, <b>deleteRule</b>.
	 * @return a DSL data element for property, <b>deleteRule</b>.
	 */
	public DataProperty deleteRule() {
		return new DataNode(this, Constraint.PROPERTY.deleteRule.name());
	}

	/**
	 * Returns a DSL data element for property, <b>indexName</b>.
	 * @return a DSL data element for property, <b>indexName</b>.
	 */
	public DataProperty indexName() {
		return new DataNode(this, Constraint.PROPERTY.indexName.name());
	}

	/**
	 * Returns a DSL data element for property, <b>indexOwner</b>.
	 * @return a DSL data element for property, <b>indexOwner</b>.
	 */
	public DataProperty indexOwner() {
		return new DataNode(this, Constraint.PROPERTY.indexOwner.name());
	}

	/**
	 * Returns a DSL data element for property, <b>owner</b>.
	 * @return a DSL data element for property, <b>owner</b>.
	 */
	public DataProperty owner() {
		return new DataNode(this, Constraint.PROPERTY.owner.name());
	}

	/**
	 * Returns a DSL data element for property, <b>refConstraintName</b>.
	 * @return a DSL data element for property, <b>refConstraintName</b>.
	 */
	public DataProperty refConstraintName() {
		return new DataNode(this, Constraint.PROPERTY.refConstraintName.name());
	}

	/**
	 * Returns a DSL data element for property, <b>refOwner</b>.
	 * @return a DSL data element for property, <b>refOwner</b>.
	 */
	public DataProperty refOwner() {
		return new DataNode(this, Constraint.PROPERTY.refOwner.name());
	}

	/**
	 * Returns a DSL data element for property, <b>searchCondition</b>.
	 * @return a DSL data element for property, <b>searchCondition</b>.
	 */
	public DataProperty searchCondition() {
		return new DataNode(this, Constraint.PROPERTY.searchCondition.name());
	}

	/**
	 * Returns a DSL query element for reference property, <b>table</b>.
	 * @return a DSL query element for reference property, <b>table</b>.
	 */
	public QTable table() {
		return new QTable(this, Constraint.PROPERTY.table.name());
	}
}