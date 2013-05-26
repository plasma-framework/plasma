package org.plasma.provisioning.rdb.oracle.g11.sys;


import org.plasma.provisioning.rdb.oracle.g11.sys.Constraint;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumn;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnComment;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnConstraint;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableComment;
import org.plasma.sdo.PlasmaDataObject;

/**
 * Represents a system table definition
 * <p></p>
 * Generated interface representing the domain model entity <b>Table</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/oracle/11g/sys</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_TABLES</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.Constraint Constraint
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.TableColumn TableColumn
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnComment TableColumnComment
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnConstraint TableColumnConstraint
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.TableComment TableComment
 */
public interface Table extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

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
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableColumn</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		tableColumn,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>constraint</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		constraint,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableColumnConstraint</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		tableColumnConstraint,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableColumnComment</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		tableColumnComment,
		
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
	 * Returns true if the <b>tableColumn</b> property is set.
	 * @return true if the <b>tableColumn</b> property is set.
	 */
	public boolean isSetTableColumn();

	/**
	 * Unsets the <b>tableColumn</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableColumn();

	/**
	 * Creates and returns a new instance of Type {@link TableColumn} automatically establishing a containment relationship through the object's reference property, <b>tableColumn</b>.
	 * @return a new instance of Type {@link TableColumn} automatically establishing a containment relationship through the object's reference property <b>tableColumn</b>.
	 */
	public TableColumn createTableColumn();

	/**
	 * Returns an array of <b>TableColumn</b> set for the object's multi-valued property <b>tableColumn</b>.
	 * @return an array of <b>TableColumn</b> set for the object's multi-valued property <b>tableColumn</b>.
	 */
	public TableColumn[] getTableColumn();

	/**
	 * Returns the <b>TableColumn</b> set for the object's multi-valued property <b>tableColumn</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableColumn</b> set for the object's multi-valued property <b>tableColumn</b> based on the given index.
	 */
	public TableColumn getTableColumn(int idx);

	/**
	 * Returns a count for multi-valued property <b>tableColumn</b>.
	 * @return a count for multi-valued property <b>tableColumn</b>.
	 */
	public int getTableColumnCount();

	/**
	 * Sets the given array of Type <b>TableColumn</b> for the object's multi-valued property <b>tableColumn</b>.
	 * @param value the array value
	 */
	public void setTableColumn(TableColumn[] value);

	/**
	 * Adds the given value of Type <b>TableColumn</b> for the object's multi-valued property <b>tableColumn</b>.
	 * @param value the value to add
	 */
	public void addTableColumn(TableColumn value);

	/**
	 * Removes the given value of Type <b>TableColumn</b> for the object's multi-valued property <b>tableColumn</b>.
	 * @param value the value to remove
	 */
	public void removeTableColumn(TableColumn value);


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
	 * Returns true if the <b>tableColumnConstraint</b> property is set.
	 * @return true if the <b>tableColumnConstraint</b> property is set.
	 */
	public boolean isSetTableColumnConstraint();

	/**
	 * Unsets the <b>tableColumnConstraint</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableColumnConstraint();

	/**
	 * Creates and returns a new instance of Type {@link TableColumnConstraint} automatically establishing a containment relationship through the object's reference property, <b>tableColumnConstraint</b>.
	 * @return a new instance of Type {@link TableColumnConstraint} automatically establishing a containment relationship through the object's reference property <b>tableColumnConstraint</b>.
	 */
	public TableColumnConstraint createTableColumnConstraint();

	/**
	 * Returns an array of <b>TableColumnConstraint</b> set for the object's multi-valued property <b>tableColumnConstraint</b>.
	 * @return an array of <b>TableColumnConstraint</b> set for the object's multi-valued property <b>tableColumnConstraint</b>.
	 */
	public TableColumnConstraint[] getTableColumnConstraint();

	/**
	 * Returns the <b>TableColumnConstraint</b> set for the object's multi-valued property <b>tableColumnConstraint</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableColumnConstraint</b> set for the object's multi-valued property <b>tableColumnConstraint</b> based on the given index.
	 */
	public TableColumnConstraint getTableColumnConstraint(int idx);

	/**
	 * Returns a count for multi-valued property <b>tableColumnConstraint</b>.
	 * @return a count for multi-valued property <b>tableColumnConstraint</b>.
	 */
	public int getTableColumnConstraintCount();

	/**
	 * Sets the given array of Type <b>TableColumnConstraint</b> for the object's multi-valued property <b>tableColumnConstraint</b>.
	 * @param value the array value
	 */
	public void setTableColumnConstraint(TableColumnConstraint[] value);

	/**
	 * Adds the given value of Type <b>TableColumnConstraint</b> for the object's multi-valued property <b>tableColumnConstraint</b>.
	 * @param value the value to add
	 */
	public void addTableColumnConstraint(TableColumnConstraint value);

	/**
	 * Removes the given value of Type <b>TableColumnConstraint</b> for the object's multi-valued property <b>tableColumnConstraint</b>.
	 * @param value the value to remove
	 */
	public void removeTableColumnConstraint(TableColumnConstraint value);


	/**
	 * Returns true if the <b>tableColumnComment</b> property is set.
	 * @return true if the <b>tableColumnComment</b> property is set.
	 */
	public boolean isSetTableColumnComment();

	/**
	 * Unsets the <b>tableColumnComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableColumnComment();

	/**
	 * Creates and returns a new instance of Type {@link TableColumnComment} automatically establishing a containment relationship through the object's reference property, <b>tableColumnComment</b>.
	 * @return a new instance of Type {@link TableColumnComment} automatically establishing a containment relationship through the object's reference property <b>tableColumnComment</b>.
	 */
	public TableColumnComment createTableColumnComment();

	/**
	 * Returns an array of <b>TableColumnComment</b> set for the object's multi-valued property <b>tableColumnComment</b>.
	 * @return an array of <b>TableColumnComment</b> set for the object's multi-valued property <b>tableColumnComment</b>.
	 */
	public TableColumnComment[] getTableColumnComment();

	/**
	 * Returns the <b>TableColumnComment</b> set for the object's multi-valued property <b>tableColumnComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableColumnComment</b> set for the object's multi-valued property <b>tableColumnComment</b> based on the given index.
	 */
	public TableColumnComment getTableColumnComment(int idx);

	/**
	 * Returns a count for multi-valued property <b>tableColumnComment</b>.
	 * @return a count for multi-valued property <b>tableColumnComment</b>.
	 */
	public int getTableColumnCommentCount();

	/**
	 * Sets the given array of Type <b>TableColumnComment</b> for the object's multi-valued property <b>tableColumnComment</b>.
	 * @param value the array value
	 */
	public void setTableColumnComment(TableColumnComment[] value);

	/**
	 * Adds the given value of Type <b>TableColumnComment</b> for the object's multi-valued property <b>tableColumnComment</b>.
	 * @param value the value to add
	 */
	public void addTableColumnComment(TableColumnComment value);

	/**
	 * Removes the given value of Type <b>TableColumnComment</b> for the object's multi-valued property <b>tableColumnComment</b>.
	 * @param value the value to remove
	 */
	public void removeTableColumnComment(TableColumnComment value);


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