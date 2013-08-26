package org.plasma.provisioning.rdb.mysql.v5_5;


import org.plasma.provisioning.rdb.mysql.v5_5.TableColumn;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumnConstraint;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumnKeyUsage;
import org.plasma.provisioning.rdb.mysql.v5_5.TableConstraint;
import org.plasma.sdo.PlasmaDataObject;

/**
 * Generated interface representing the domain model entity <b>Table</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/mysql/5_5</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>TABLES</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.mysql.v5_5.TableColumn TableColumn
 * @see org.plasma.provisioning.rdb.mysql.v5_5.TableColumnConstraint TableColumnConstraint
 * @see org.plasma.provisioning.rdb.mysql.v5_5.TableColumnKeyUsage TableColumnKeyUsage
 * @see org.plasma.provisioning.rdb.mysql.v5_5.TableConstraint TableConstraint
 */
public interface Table extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

	/** The entity or <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> logical name associated with this class. */
	public static final String TYPE_NAME_TABLE = "Table";
	
	/** The declared logical property names for this <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a>. */
	public static enum PROPERTY {
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>owner</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>TABLES.TABLE_SCHEMA</b>.
		 */
		owner,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>TABLES.TABLE_NAME</b>.
		 */
		tableName,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>autoIncrement</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>TABLES.AUTO_INCREMENT</b>.
		 */
		autoIncrement,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableColumnConstraint</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		tableColumnConstraint,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableConstraint</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		tableConstraint,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableColumnKeyUsage</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		tableColumnKeyUsage,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableColumn</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 */
		tableColumn,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableComment</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>Table</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>TABLES.TABLE_COMMENT</b>.
		 */
		tableComment
	}



	/**
	 * Returns true if the <b>owner</b> property is set.
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner();

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOwner();

	/**
	 * Returns the value of the <b>owner</b> property.
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner();

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 */
	public void setOwner(String value);


	/**
	 * Returns true if the <b>tableName</b> property is set.
	 * @return true if the <b>tableName</b> property is set.
	 */
	public boolean isSetTableName();

	/**
	 * Unsets the <b>tableName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTableName();

	/**
	 * Returns the value of the <b>tableName</b> property.
	 * @return the value of the <b>tableName</b> property.
	 */
	public String getTableName();

	/**
	 * Sets the value of the <b>tableName</b> property to the given value.
	 */
	public void setTableName(String value);


	/**
	 * Returns true if the <b>autoIncrement</b> property is set.
	 * @return true if the <b>autoIncrement</b> property is set.
	 */
	public boolean isSetAutoIncrement();

	/**
	 * Unsets the <b>autoIncrement</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetAutoIncrement();

	/**
	 * Returns the value of the <b>autoIncrement</b> property.
	 * @return the value of the <b>autoIncrement</b> property.
	 */
	public boolean getAutoIncrement();

	/**
	 * Sets the value of the <b>autoIncrement</b> property to the given value.
	 */
	public void setAutoIncrement(boolean value);


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
	 * Returns true if the <b>tableConstraint</b> property is set.
	 * @return true if the <b>tableConstraint</b> property is set.
	 */
	public boolean isSetTableConstraint();

	/**
	 * Unsets the <b>tableConstraint</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableConstraint();

	/**
	 * Creates and returns a new instance of Type {@link TableConstraint} automatically establishing a containment relationship through the object's reference property, <b>tableConstraint</b>.
	 * @return a new instance of Type {@link TableConstraint} automatically establishing a containment relationship through the object's reference property <b>tableConstraint</b>.
	 */
	public TableConstraint createTableConstraint();

	/**
	 * Returns an array of <b>TableConstraint</b> set for the object's multi-valued property <b>tableConstraint</b>.
	 * @return an array of <b>TableConstraint</b> set for the object's multi-valued property <b>tableConstraint</b>.
	 */
	public TableConstraint[] getTableConstraint();

	/**
	 * Returns the <b>TableConstraint</b> set for the object's multi-valued property <b>tableConstraint</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableConstraint</b> set for the object's multi-valued property <b>tableConstraint</b> based on the given index.
	 */
	public TableConstraint getTableConstraint(int idx);

	/**
	 * Returns a count for multi-valued property <b>tableConstraint</b>.
	 * @return a count for multi-valued property <b>tableConstraint</b>.
	 */
	public int getTableConstraintCount();

	/**
	 * Sets the given array of Type <b>TableConstraint</b> for the object's multi-valued property <b>tableConstraint</b>.
	 * @param value the array value
	 */
	public void setTableConstraint(TableConstraint[] value);

	/**
	 * Adds the given value of Type <b>TableConstraint</b> for the object's multi-valued property <b>tableConstraint</b>.
	 * @param value the value to add
	 */
	public void addTableConstraint(TableConstraint value);

	/**
	 * Removes the given value of Type <b>TableConstraint</b> for the object's multi-valued property <b>tableConstraint</b>.
	 * @param value the value to remove
	 */
	public void removeTableConstraint(TableConstraint value);


	/**
	 * Returns true if the <b>tableColumnKeyUsage</b> property is set.
	 * @return true if the <b>tableColumnKeyUsage</b> property is set.
	 */
	public boolean isSetTableColumnKeyUsage();

	/**
	 * Unsets the <b>tableColumnKeyUsage</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableColumnKeyUsage();

	/**
	 * Creates and returns a new instance of Type {@link TableColumnKeyUsage} automatically establishing a containment relationship through the object's reference property, <b>tableColumnKeyUsage</b>.
	 * @return a new instance of Type {@link TableColumnKeyUsage} automatically establishing a containment relationship through the object's reference property <b>tableColumnKeyUsage</b>.
	 */
	public TableColumnKeyUsage createTableColumnKeyUsage();

	/**
	 * Returns an array of <b>TableColumnKeyUsage</b> set for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 * @return an array of <b>TableColumnKeyUsage</b> set for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 */
	public TableColumnKeyUsage[] getTableColumnKeyUsage();

	/**
	 * Returns the <b>TableColumnKeyUsage</b> set for the object's multi-valued property <b>tableColumnKeyUsage</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableColumnKeyUsage</b> set for the object's multi-valued property <b>tableColumnKeyUsage</b> based on the given index.
	 */
	public TableColumnKeyUsage getTableColumnKeyUsage(int idx);

	/**
	 * Returns a count for multi-valued property <b>tableColumnKeyUsage</b>.
	 * @return a count for multi-valued property <b>tableColumnKeyUsage</b>.
	 */
	public int getTableColumnKeyUsageCount();

	/**
	 * Sets the given array of Type <b>TableColumnKeyUsage</b> for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 * @param value the array value
	 */
	public void setTableColumnKeyUsage(TableColumnKeyUsage[] value);

	/**
	 * Adds the given value of Type <b>TableColumnKeyUsage</b> for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 * @param value the value to add
	 */
	public void addTableColumnKeyUsage(TableColumnKeyUsage value);

	/**
	 * Removes the given value of Type <b>TableColumnKeyUsage</b> for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 * @param value the value to remove
	 */
	public void removeTableColumnKeyUsage(TableColumnKeyUsage value);


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
	 * Returns true if the <b>tableComment</b> property is set.
	 * @return true if the <b>tableComment</b> property is set.
	 */
	public boolean isSetTableComment();

	/**
	 * Unsets the <b>tableComment</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTableComment();

	/**
	 * Returns the value of the <b>tableComment</b> property.
	 * @return the value of the <b>tableComment</b> property.
	 */
	public String getTableComment();

	/**
	 * Sets the value of the <b>tableComment</b> property to the given value.
	 */
	public void setTableComment(String value);
}