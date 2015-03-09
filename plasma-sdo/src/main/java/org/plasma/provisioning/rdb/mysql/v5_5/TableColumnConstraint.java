package org.plasma.provisioning.rdb.mysql.v5_5;


import org.plasma.sdo.PlasmaDataObject;

/**
 * Generated interface representing the domain model entity <b>TableColumnConstraint</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/mysql/5_5</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>REFERENTIAL_CONSTRAINTS</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.mysql.v5_5.Table Table
 */
public interface TableColumnConstraint extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

	/** The entity or <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> logical name associated with this class. */
	public static final String TYPE_NAME_TABLE_COLUMN_CONSTRAINT = "TableColumnConstraint";
	
	/** The declared logical property names for this <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a>. */
	public static enum PROPERTY {
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>owner</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnConstraint</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>REFERENTIAL_CONSTRAINTS.CONSTRAINT_SCHEMA</b>.
		 */
		owner,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>name</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnConstraint</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>REFERENTIAL_CONSTRAINTS.CONSTRAINT_NAME</b>.
		 */
		name,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>table</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnConstraint</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>REFERENTIAL_CONSTRAINTS.TABLE_NAME</b>.
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