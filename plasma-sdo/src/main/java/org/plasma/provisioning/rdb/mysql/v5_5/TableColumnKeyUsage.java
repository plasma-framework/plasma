package org.plasma.provisioning.rdb.mysql.v5_5;


import org.plasma.provisioning.rdb.mysql.v5_5.Table;
import org.plasma.sdo.PlasmaDataObject;

/**
 * Generated interface representing the domain model entity <b>TableColumnKeyUsage</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/mysql/5_5</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>KEY_COLUMN_USAGE</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.mysql.v5_5.Table Table
 */
public interface TableColumnKeyUsage extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

	/** The entity or <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> logical name associated with this class. */
	public static final String TYPE_NAME_TABLE_COLUMN_KEY_USAGE = "TableColumnKeyUsage";
	
	/** The declared logical property names for this <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a>. */
	public static enum PROPERTY {
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>owner</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnKeyUsage</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>KEY_COLUMN_USAGE.TABLE_SCHEMA</b>.
		 */
		owner,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>name</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnKeyUsage</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>KEY_COLUMN_USAGE.CONSTRAINT_NAME</b>.
		 */
		name,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>columnName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnKeyUsage</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>KEY_COLUMN_USAGE.COLUMN_NAME</b>.
		 */
		columnName,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>referencedTableSchema</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnKeyUsage</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>KEY_COLUMN_USAGE.REFERENCED_TABLE_SCHEMA</b>.
		 */
		referencedTableSchema,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>referencedTableName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnKeyUsage</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>KEY_COLUMN_USAGE.REFERENCED_TABLE_NAME</b>.
		 */
		referencedTableName,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>referencedColumnName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnKeyUsage</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>KEY_COLUMN_USAGE.REFERENCED_COLUMN_NAME</b>.
		 */
		referencedColumnName,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>table</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnKeyUsage</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>KEY_COLUMN_USAGE.TABLE_NAME</b>.
		 */
		table
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
	 * Returns true if the <b>name</b> property is set.
	 * @return true if the <b>name</b> property is set.
	 */
	public boolean isSetName();

	/**
	 * Unsets the <b>name</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetName();

	/**
	 * Returns the value of the <b>name</b> property.
	 * @return the value of the <b>name</b> property.
	 */
	public String getName();

	/**
	 * Sets the value of the <b>name</b> property to the given value.
	 */
	public void setName(String value);


	/**
	 * Returns true if the <b>columnName</b> property is set.
	 * @return true if the <b>columnName</b> property is set.
	 */
	public boolean isSetColumnName();

	/**
	 * Unsets the <b>columnName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnName();

	/**
	 * Returns the value of the <b>columnName</b> property.
	 * @return the value of the <b>columnName</b> property.
	 */
	public String getColumnName();

	/**
	 * Sets the value of the <b>columnName</b> property to the given value.
	 */
	public void setColumnName(String value);


	/**
	 * Returns true if the <b>referencedTableSchema</b> property is set.
	 * @return true if the <b>referencedTableSchema</b> property is set.
	 */
	public boolean isSetReferencedTableSchema();

	/**
	 * Unsets the <b>referencedTableSchema</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetReferencedTableSchema();

	/**
	 * Returns the value of the <b>referencedTableSchema</b> property.
	 * @return the value of the <b>referencedTableSchema</b> property.
	 */
	public String getReferencedTableSchema();

	/**
	 * Sets the value of the <b>referencedTableSchema</b> property to the given value.
	 */
	public void setReferencedTableSchema(String value);


	/**
	 * Returns true if the <b>referencedTableName</b> property is set.
	 * @return true if the <b>referencedTableName</b> property is set.
	 */
	public boolean isSetReferencedTableName();

	/**
	 * Unsets the <b>referencedTableName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetReferencedTableName();

	/**
	 * Returns the value of the <b>referencedTableName</b> property.
	 * @return the value of the <b>referencedTableName</b> property.
	 */
	public String getReferencedTableName();

	/**
	 * Sets the value of the <b>referencedTableName</b> property to the given value.
	 */
	public void setReferencedTableName(String value);


	/**
	 * Returns true if the <b>referencedColumnName</b> property is set.
	 * @return true if the <b>referencedColumnName</b> property is set.
	 */
	public boolean isSetReferencedColumnName();

	/**
	 * Unsets the <b>referencedColumnName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetReferencedColumnName();

	/**
	 * Returns the value of the <b>referencedColumnName</b> property.
	 * @return the value of the <b>referencedColumnName</b> property.
	 */
	public String getReferencedColumnName();

	/**
	 * Sets the value of the <b>referencedColumnName</b> property to the given value.
	 */
	public void setReferencedColumnName(String value);


	/**
	 * Returns true if the <b>table</b> property is set.
	 * @return true if the <b>table</b> property is set.
	 */
	public boolean isSetTable();

	/**
	 * Unsets the <b>table</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTable();

	/**
	 * Creates and returns a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property, <b>table</b>.
	 * @return a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property <b>table</b>.
	 */
	public Table createTable();

	/**
	 * Returns the value of the <b>table</b> property.
	 * @return the value of the <b>table</b> property.
	 */
	public Table getTable();

	/**
	 * Sets the value of the <b>table</b> property to the given value.
	 */
	public void setTable(Table value);
}