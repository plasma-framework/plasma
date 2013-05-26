package org.plasma.provisioning.rdb.oracle.g11.sys.query;

import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.query.DataProperty;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.Expression;


import org.plasma.provisioning.rdb.oracle.g11.sys.query.QTable;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnComment;

/**
 * The comment for a column
 * <p></p>
 * Generated Domain Specific Language (DSL) implementation class representing the domain model entity <b>TableColumnComment</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_COL_COMMENTS</b>.
 *
 */
public class QTableColumnComment extends DomainRoot
{


	private QTableColumnComment() {
		super(PlasmaTypeHelper.INSTANCE.getType(TableColumnComment.class));
	}
	
	/**
	 * Constructor which instantiates a domain query path node. A path may
	 * span multiple namespaces and therefore Java inplementation packages
	 * based on the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html">Condiguration</a>.
	 * Note: while this constructor is public, it is not for application use!
	 * @param source the source path node
	 * @param sourceProperty the source property logical name
	 */
	public QTableColumnComment(PathNode source, String sourceProperty) {
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
	public QTableColumnComment(PathNode source, String sourceProperty, Expression expr) {
		super(source, sourceProperty, expr);
	}

	/**
	 * Returns a new DSL query for <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnComment</b> which can be used either as a query root or
	 * as the start (entry point) for a new path predicate expression.
	 * @return a new DSL query
	 */
	public static QTableColumnComment newQuery() {
		return new QTableColumnComment();
	}

	/**
	 * Returns a DSL data element for property, <b>columnName</b>.
	 * @return a DSL data element for property, <b>columnName</b>.
	 */
	public DataProperty columnName() {
		return new DataNode(this, TableColumnComment.PROPERTY.columnName.name());
	}

	/**
	 * Returns a DSL data element for property, <b>comments</b>.
	 * @return a DSL data element for property, <b>comments</b>.
	 */
	public DataProperty comments() {
		return new DataNode(this, TableColumnComment.PROPERTY.comments.name());
	}

	/**
	 * Returns a DSL data element for property, <b>owner</b>.
	 * @return a DSL data element for property, <b>owner</b>.
	 */
	public DataProperty owner() {
		return new DataNode(this, TableColumnComment.PROPERTY.owner.name());
	}

	/**
	 * Returns a DSL query element for reference property, <b>table</b>.
	 * @return a DSL query element for reference property, <b>table</b>.
	 */
	public QTable table() {
		return new QTable(this, TableColumnComment.PROPERTY.table.name());
	}
}