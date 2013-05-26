package org.plasma.provisioning.rdb.oracle.g11.sys;


import org.plasma.provisioning.rdb.oracle.g11.sys.Table;
import org.plasma.sdo.PlasmaDataObject;

/**
 * A constraint definition
 * <p></p>
 * Generated interface representing the domain model entity <b>TableColumnConstraint</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/oracle/11g/sys</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_CONS_COLUMNS</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.Table Table
 */
public interface TableColumnConstraint extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

	/** The entity or <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> logical name associated with this class. */
	public static final String TYPE_NAME_TABLE_COLUMN_CONSTRAINT = "TableColumnConstraint";
	
	/** The declared logical property names for this <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a>. */
	public static enum PROPERTY {
		
		/**
		 * Owner of the constraint definition
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>owner</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnConstraint</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_CONS_COLUMNS.OWNER</b>.
		 */
		owner,
		
		/**
		 * Name of the constraint definition
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>constraintName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnConstraint</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_CONS_COLUMNS.CONSTRAINT_NAME</b>.
		 */
		constraintName,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>columnName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnConstraint</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_CONS_COLUMNS.COLUMN_NAME</b>.
		 */
		columnName,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>table</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumnConstraint</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_CONS_COLUMNS.TABLE_NAME</b>.
		 */
		table
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
	 * Owner of the constraint definition
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner();

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the constraint definition
	 */
	public void setOwner(String value);


	/**
	 * Returns true if the <b>constraintName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getConstraintName() getConstraintName} or {@link #setConstraintName(String value) setConstraintName(...)} for a definition of property <b>constraintName</b>
	 * @return true if the <b>constraintName</b> property is set.
	 */
	public boolean isSetConstraintName();

	/**
	 * Unsets the <b>constraintName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getConstraintName() getConstraintName} or {@link #setConstraintName(String value) setConstraintName(...)} for a definition of property <b>constraintName</b>
	 */
	public void unsetConstraintName();

	/**
	 * Returns the value of the <b>constraintName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the constraint definition
	 * @return the value of the <b>constraintName</b> property.
	 */
	public String getConstraintName();

	/**
	 * Sets the value of the <b>constraintName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the constraint definition
	 */
	public void setConstraintName(String value);


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