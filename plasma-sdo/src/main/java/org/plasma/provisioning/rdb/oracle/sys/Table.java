package org.plasma.provisioning.rdb.oracle.sys;


import org.plasma.provisioning.rdb.oracle.sys.Column;
import org.plasma.provisioning.rdb.oracle.sys.ColumnComment;
import org.plasma.provisioning.rdb.oracle.sys.ColumnConstraint;
import org.plasma.provisioning.rdb.oracle.sys.Constraint;
import org.plasma.provisioning.rdb.oracle.sys.TableComment;
import org.plasma.sdo.PlasmaDataObject;

/**
 * Represents a system table definition
 * <p></p>
 * Generated interface representing the domain model entity <b>Table</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/oracle/sys</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_TABLES</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.oracle.sys.Column Column
 * @see org.plasma.provisioning.rdb.oracle.sys.ColumnComment ColumnComment
 * @see org.plasma.provisioning.rdb.oracle.sys.ColumnConstraint ColumnConstraint
 * @see org.plasma.provisioning.rdb.oracle.sys.Constraint Constraint
 * @see org.plasma.provisioning.rdb.oracle.sys.TableComment TableComment
 */
public interface Table extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/sys";

	/** The entity or <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> logical name associated with this class. */
	public static final String TYPE_NAME_TABLE = "Table";
	
	/** The declared logical property names for this <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a>. */
	public static enum PROPERTY {
		
		/**
		 * The schema owner
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>owner</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TABLES.OWNER</b>.
		 */
		owner,
		
		/**
		 * The table physical names
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TABLES.TABLE_NAME</b>.
		 */
		tableName,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>column</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		column,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>constraint</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		constraint,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>columnConstraint</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		columnConstraint,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>columnComment</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		columnComment,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableComment</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		tableComment
	}



	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner();

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 */
	public void unsetOwner();

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The schema owner
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner();

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The schema owner
	 */
	public void setOwner(String value);


	/**
	 * Returns true if the <b>tableName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTableName() getTableName} or {@link #setTableName(String value) setTableName(...)} for a definition of property <b>tableName</b>
	 * @return true if the <b>tableName</b> property is set.
	 */
	public boolean isSetTableName();

	/**
	 * Unsets the <b>tableName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTableName() getTableName} or {@link #setTableName(String value) setTableName(...)} for a definition of property <b>tableName</b>
	 */
	public void unsetTableName();

	/**
	 * Returns the value of the <b>tableName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The table physical names
	 * @return the value of the <b>tableName</b> property.
	 */
	public String getTableName();

	/**
	 * Sets the value of the <b>tableName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The table physical names
	 */
	public void setTableName(String value);


	/**
	 * Returns true if the <b>column</b> property is set.
	 * @return true if the <b>column</b> property is set.
	 */
	public boolean isSetColumn();

	/**
	 * Unsets the <b>column</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetColumn();

	/**
	 * Creates and returns a new instance of Type {@link Column} automatically establishing a containment relationship through the object's reference property, <b>column</b>.
	 * @return a new instance of Type {@link Column} automatically establishing a containment relationship through the object's reference property <b>column</b>.
	 */
	public Column createColumn();

	/**
	 * Returns an array of <b>Column</b> set for the object's multi-valued property <b>column</b>.
	 * @return an array of <b>Column</b> set for the object's multi-valued property <b>column</b>.
	 */
	public Column[] getColumn();

	/**
	 * Returns the <b>Column</b> set for the object's multi-valued property <b>column</b> based on the given index.
	 * @param idx the index
	 * @return the <b>Column</b> set for the object's multi-valued property <b>column</b> based on the given index.
	 */
	public Column getColumn(int idx);

	/**
	 * Returns a count for multi-valued property <b>column</b>.
	 * @return a count for multi-valued property <b>column</b>.
	 */
	public int getColumnCount();

	/**
	 * Sets the given array of Type <b>Column</b> for the object's multi-valued property <b>column</b>.
	 * @param value the array value
	 */
	public void setColumn(Column[] value);

	/**
	 * Adds the given value of Type <b>Column</b> for the object's multi-valued property <b>column</b>.
	 * @param value the value to add
	 */
	public void addColumn(Column value);

	/**
	 * Removes the given value of Type <b>Column</b> for the object's multi-valued property <b>column</b>.
	 * @param value the value to remove
	 */
	public void removeColumn(Column value);


	/**
	 * Returns true if the <b>constraint</b> property is set.
	 * @return true if the <b>constraint</b> property is set.
	 */
	public boolean isSetConstraint();

	/**
	 * Unsets the <b>constraint</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetConstraint();

	/**
	 * Creates and returns a new instance of Type {@link Constraint} automatically establishing a containment relationship through the object's reference property, <b>constraint</b>.
	 * @return a new instance of Type {@link Constraint} automatically establishing a containment relationship through the object's reference property <b>constraint</b>.
	 */
	public Constraint createConstraint();

	/**
	 * Returns an array of <b>Constraint</b> set for the object's multi-valued property <b>constraint</b>.
	 * @return an array of <b>Constraint</b> set for the object's multi-valued property <b>constraint</b>.
	 */
	public Constraint[] getConstraint();

	/**
	 * Returns the <b>Constraint</b> set for the object's multi-valued property <b>constraint</b> based on the given index.
	 * @param idx the index
	 * @return the <b>Constraint</b> set for the object's multi-valued property <b>constraint</b> based on the given index.
	 */
	public Constraint getConstraint(int idx);

	/**
	 * Returns a count for multi-valued property <b>constraint</b>.
	 * @return a count for multi-valued property <b>constraint</b>.
	 */
	public int getConstraintCount();

	/**
	 * Sets the given array of Type <b>Constraint</b> for the object's multi-valued property <b>constraint</b>.
	 * @param value the array value
	 */
	public void setConstraint(Constraint[] value);

	/**
	 * Adds the given value of Type <b>Constraint</b> for the object's multi-valued property <b>constraint</b>.
	 * @param value the value to add
	 */
	public void addConstraint(Constraint value);

	/**
	 * Removes the given value of Type <b>Constraint</b> for the object's multi-valued property <b>constraint</b>.
	 * @param value the value to remove
	 */
	public void removeConstraint(Constraint value);


	/**
	 * Returns true if the <b>columnConstraint</b> property is set.
	 * @return true if the <b>columnConstraint</b> property is set.
	 */
	public boolean isSetColumnConstraint();

	/**
	 * Unsets the <b>columnConstraint</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnConstraint();

	/**
	 * Creates and returns a new instance of Type {@link ColumnConstraint} automatically establishing a containment relationship through the object's reference property, <b>columnConstraint</b>.
	 * @return a new instance of Type {@link ColumnConstraint} automatically establishing a containment relationship through the object's reference property <b>columnConstraint</b>.
	 */
	public ColumnConstraint createColumnConstraint();

	/**
	 * Returns an array of <b>ColumnConstraint</b> set for the object's multi-valued property <b>columnConstraint</b>.
	 * @return an array of <b>ColumnConstraint</b> set for the object's multi-valued property <b>columnConstraint</b>.
	 */
	public ColumnConstraint[] getColumnConstraint();

	/**
	 * Returns the <b>ColumnConstraint</b> set for the object's multi-valued property <b>columnConstraint</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ColumnConstraint</b> set for the object's multi-valued property <b>columnConstraint</b> based on the given index.
	 */
	public ColumnConstraint getColumnConstraint(int idx);

	/**
	 * Returns a count for multi-valued property <b>columnConstraint</b>.
	 * @return a count for multi-valued property <b>columnConstraint</b>.
	 */
	public int getColumnConstraintCount();

	/**
	 * Sets the given array of Type <b>ColumnConstraint</b> for the object's multi-valued property <b>columnConstraint</b>.
	 * @param value the array value
	 */
	public void setColumnConstraint(ColumnConstraint[] value);

	/**
	 * Adds the given value of Type <b>ColumnConstraint</b> for the object's multi-valued property <b>columnConstraint</b>.
	 * @param value the value to add
	 */
	public void addColumnConstraint(ColumnConstraint value);

	/**
	 * Removes the given value of Type <b>ColumnConstraint</b> for the object's multi-valued property <b>columnConstraint</b>.
	 * @param value the value to remove
	 */
	public void removeColumnConstraint(ColumnConstraint value);


	/**
	 * Returns true if the <b>columnComment</b> property is set.
	 * @return true if the <b>columnComment</b> property is set.
	 */
	public boolean isSetColumnComment();

	/**
	 * Unsets the <b>columnComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnComment();

	/**
	 * Creates and returns a new instance of Type {@link ColumnComment} automatically establishing a containment relationship through the object's reference property, <b>columnComment</b>.
	 * @return a new instance of Type {@link ColumnComment} automatically establishing a containment relationship through the object's reference property <b>columnComment</b>.
	 */
	public ColumnComment createColumnComment();

	/**
	 * Returns an array of <b>ColumnComment</b> set for the object's multi-valued property <b>columnComment</b>.
	 * @return an array of <b>ColumnComment</b> set for the object's multi-valued property <b>columnComment</b>.
	 */
	public ColumnComment[] getColumnComment();

	/**
	 * Returns the <b>ColumnComment</b> set for the object's multi-valued property <b>columnComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ColumnComment</b> set for the object's multi-valued property <b>columnComment</b> based on the given index.
	 */
	public ColumnComment getColumnComment(int idx);

	/**
	 * Returns a count for multi-valued property <b>columnComment</b>.
	 * @return a count for multi-valued property <b>columnComment</b>.
	 */
	public int getColumnCommentCount();

	/**
	 * Sets the given array of Type <b>ColumnComment</b> for the object's multi-valued property <b>columnComment</b>.
	 * @param value the array value
	 */
	public void setColumnComment(ColumnComment[] value);

	/**
	 * Adds the given value of Type <b>ColumnComment</b> for the object's multi-valued property <b>columnComment</b>.
	 * @param value the value to add
	 */
	public void addColumnComment(ColumnComment value);

	/**
	 * Removes the given value of Type <b>ColumnComment</b> for the object's multi-valued property <b>columnComment</b>.
	 * @param value the value to remove
	 */
	public void removeColumnComment(ColumnComment value);


	/**
	 * Returns true if the <b>tableComment</b> property is set.
	 * @return true if the <b>tableComment</b> property is set.
	 */
	public boolean isSetTableComment();

	/**
	 * Unsets the <b>tableComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableComment();

	/**
	 * Creates and returns a new instance of Type {@link TableComment} automatically establishing a containment relationship through the object's reference property, <b>tableComment</b>.
	 * @return a new instance of Type {@link TableComment} automatically establishing a containment relationship through the object's reference property <b>tableComment</b>.
	 */
	public TableComment createTableComment();

	/**
	 * Returns an array of <b>TableComment</b> set for the object's multi-valued property <b>tableComment</b>.
	 * @return an array of <b>TableComment</b> set for the object's multi-valued property <b>tableComment</b>.
	 */
	public TableComment[] getTableComment();

	/**
	 * Returns the <b>TableComment</b> set for the object's multi-valued property <b>tableComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableComment</b> set for the object's multi-valued property <b>tableComment</b> based on the given index.
	 */
	public TableComment getTableComment(int idx);

	/**
	 * Returns a count for multi-valued property <b>tableComment</b>.
	 * @return a count for multi-valued property <b>tableComment</b>.
	 */
	public int getTableCommentCount();

	/**
	 * Sets the given array of Type <b>TableComment</b> for the object's multi-valued property <b>tableComment</b>.
	 * @param value the array value
	 */
	public void setTableComment(TableComment[] value);

	/**
	 * Adds the given value of Type <b>TableComment</b> for the object's multi-valued property <b>tableComment</b>.
	 * @param value the value to add
	 */
	public void addTableComment(TableComment value);

	/**
	 * Removes the given value of Type <b>TableComment</b> for the object's multi-valued property <b>tableComment</b>.
	 * @param value the value to remove
	 */
	public void removeTableComment(TableComment value);
}