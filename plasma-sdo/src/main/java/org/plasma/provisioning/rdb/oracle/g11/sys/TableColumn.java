package org.plasma.provisioning.rdb.oracle.g11.sys;


import org.plasma.sdo.PlasmaDataObject;

/**
 * Represents a system column definition which is linked to its
 * system Table definition by association.
 * <p></p>
 * Generated interface representing the domain model entity <b>TableColumn</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/oracle/11g/sys</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_TAB_COLUMNS</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.Table Table
 */
public interface TableColumn extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

	/** The entity or <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> logical name associated with this class. */
	public static final String TYPE_NAME_TABLE_COLUMN = "TableColumn";
	
	/** The declared logical property names for this <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a>. */
	public static enum PROPERTY {
		
		/**
		 * Column name
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>columnName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.COLUMN_NAME</b>.
		 */
		columnName,
		
		/**
		 * Datatype of the column
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>dataType</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.DATA_TYPE</b>.
		 */
		dataType,
		
		/**
		 * Specifies whether a column allows NULLs. Value is N if there
		 * is a NOT NULL constraint on the column or if the column 
		 * is part of a PRIMARY KEY. The constraint should be in an 
		 * ENABLE VALIDATE state.
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>nullable</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.NULLABLE</b>.
		 */
		nullable,
		
		/**
		 * Sequence number of the column as created
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>column_id</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.COLUMN_ID</b>.
		 */
		column_id,
		
		/**
		 * Length of the column (in bytes)
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>dataLength</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.DATA_LENGTH</b>.
		 */
		dataLength,
		
		/**
		 * Decimal precision (total number of digits) for NUMBER 
		 * datatype; binary precision for FLOAT datatype, null for 
		 * all other datatypes.
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>dataPrecision</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.DATA_PRECISION</b>.
		 */
		dataPrecision,
		
		/**
		 * Digits to right of decimal point in a number
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>dataScale</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.DATA_SCALE</b>.
		 */
		dataScale,
		
		/**
		 * Default value for the column
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>dataDefault</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.DATA_DEFAULT</b>.
		 */
		dataDefault,
		
		/**
		 * Name of the character set: CHAR_CS or NCHAR_CS
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>characterSetName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.CHARACTER_SET_NAME</b>.
		 */
		characterSetName,
		
		/**
		 * Owner of the table, view, or cluster
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>owner</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.OWNER</b>.
		 */
		owner,
		
		/**
		 * Reference to the owner table
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>table</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COLUMNS.TABLE_NAME</b>.
		 */
		table
	}



	/**
	 * Returns true if the <b>columnName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnName() getColumnName} or {@link #setColumnName(String value) setColumnName(...)} for a definition of property <b>columnName</b>
	 * @return true if the <b>columnName</b> property is set.
	 */
	public boolean isSetColumnName();

	/**
	 * Unsets the <b>columnName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnName() getColumnName} or {@link #setColumnName(String value) setColumnName(...)} for a definition of property <b>columnName</b>
	 */
	public void unsetColumnName();

	/**
	 * Returns the value of the <b>columnName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Column name
	 * @return the value of the <b>columnName</b> property.
	 */
	public String getColumnName();

	/**
	 * Sets the value of the <b>columnName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Column name
	 */
	public void setColumnName(String value);


	/**
	 * Returns true if the <b>dataType</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataType() getDataType} or {@link #setDataType(String value) setDataType(...)} for a definition of property <b>dataType</b>
	 * @return true if the <b>dataType</b> property is set.
	 */
	public boolean isSetDataType();

	/**
	 * Unsets the <b>dataType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataType() getDataType} or {@link #setDataType(String value) setDataType(...)} for a definition of property <b>dataType</b>
	 */
	public void unsetDataType();

	/**
	 * Returns the value of the <b>dataType</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Datatype of the column
	 * @return the value of the <b>dataType</b> property.
	 */
	public String getDataType();

	/**
	 * Sets the value of the <b>dataType</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Datatype of the column
	 * <p></p>
	 * <b>Enumeration Constraints: </b><pre>
	 *     <b>name:</b> SysDataType
	 *     <b>URI:</b>http://org.plasma/sdo/oracle/11g/sys</pre>
	 */
	public void setDataType(String value);


	/**
	 * Returns true if the <b>nullable</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getNullable() getNullable} or {@link #setNullable(String value) setNullable(...)} for a definition of property <b>nullable</b>
	 * @return true if the <b>nullable</b> property is set.
	 */
	public boolean isSetNullable();

	/**
	 * Unsets the <b>nullable</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getNullable() getNullable} or {@link #setNullable(String value) setNullable(...)} for a definition of property <b>nullable</b>
	 */
	public void unsetNullable();

	/**
	 * Returns the value of the <b>nullable</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Specifies whether a column allows NULLs. Value is N if there
	 * is a NOT NULL constraint on the column or if the column 
	 * is part of a PRIMARY KEY. The constraint should be in an 
	 * ENABLE VALIDATE state.
	 * @return the value of the <b>nullable</b> property.
	 */
	public String getNullable();

	/**
	 * Sets the value of the <b>nullable</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Specifies whether a column allows NULLs. Value is N if there
	 * is a NOT NULL constraint on the column or if the column 
	 * is part of a PRIMARY KEY. The constraint should be in an 
	 * ENABLE VALIDATE state.
	 */
	public void setNullable(String value);


	/**
	 * Returns true if the <b>column_id</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumn_id() getColumn_id} or {@link #setColumn_id(int value) setColumn_id(...)} for a definition of property <b>column_id</b>
	 * @return true if the <b>column_id</b> property is set.
	 */
	public boolean isSetColumn_id();

	/**
	 * Unsets the <b>column_id</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumn_id() getColumn_id} or {@link #setColumn_id(int value) setColumn_id(...)} for a definition of property <b>column_id</b>
	 */
	public void unsetColumn_id();

	/**
	 * Returns the value of the <b>column_id</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Sequence number of the column as created
	 * @return the value of the <b>column_id</b> property.
	 */
	public int getColumn_id();

	/**
	 * Sets the value of the <b>column_id</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Sequence number of the column as created
	 */
	public void setColumn_id(int value);


	/**
	 * Returns true if the <b>dataLength</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataLength() getDataLength} or {@link #setDataLength(int value) setDataLength(...)} for a definition of property <b>dataLength</b>
	 * @return true if the <b>dataLength</b> property is set.
	 */
	public boolean isSetDataLength();

	/**
	 * Unsets the <b>dataLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataLength() getDataLength} or {@link #setDataLength(int value) setDataLength(...)} for a definition of property <b>dataLength</b>
	 */
	public void unsetDataLength();

	/**
	 * Returns the value of the <b>dataLength</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 * @return the value of the <b>dataLength</b> property.
	 */
	public int getDataLength();

	/**
	 * Sets the value of the <b>dataLength</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 */
	public void setDataLength(int value);


	/**
	 * Returns true if the <b>dataPrecision</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataPrecision() getDataPrecision} or {@link #setDataPrecision(int value) setDataPrecision(...)} for a definition of property <b>dataPrecision</b>
	 * @return true if the <b>dataPrecision</b> property is set.
	 */
	public boolean isSetDataPrecision();

	/**
	 * Unsets the <b>dataPrecision</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataPrecision() getDataPrecision} or {@link #setDataPrecision(int value) setDataPrecision(...)} for a definition of property <b>dataPrecision</b>
	 */
	public void unsetDataPrecision();

	/**
	 * Returns the value of the <b>dataPrecision</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Decimal precision (total number of digits) for NUMBER 
	 * datatype; binary precision for FLOAT datatype, null for 
	 * all other datatypes.
	 * @return the value of the <b>dataPrecision</b> property.
	 */
	public int getDataPrecision();

	/**
	 * Sets the value of the <b>dataPrecision</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Decimal precision (total number of digits) for NUMBER 
	 * datatype; binary precision for FLOAT datatype, null for 
	 * all other datatypes.
	 */
	public void setDataPrecision(int value);


	/**
	 * Returns true if the <b>dataScale</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataScale() getDataScale} or {@link #setDataScale(int value) setDataScale(...)} for a definition of property <b>dataScale</b>
	 * @return true if the <b>dataScale</b> property is set.
	 */
	public boolean isSetDataScale();

	/**
	 * Unsets the <b>dataScale</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataScale() getDataScale} or {@link #setDataScale(int value) setDataScale(...)} for a definition of property <b>dataScale</b>
	 */
	public void unsetDataScale();

	/**
	 * Returns the value of the <b>dataScale</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Digits to right of decimal point in a number
	 * @return the value of the <b>dataScale</b> property.
	 */
	public int getDataScale();

	/**
	 * Sets the value of the <b>dataScale</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Digits to right of decimal point in a number
	 */
	public void setDataScale(int value);


	/**
	 * Returns true if the <b>dataDefault</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataDefault() getDataDefault} or {@link #setDataDefault(String value) setDataDefault(...)} for a definition of property <b>dataDefault</b>
	 * @return true if the <b>dataDefault</b> property is set.
	 */
	public boolean isSetDataDefault();

	/**
	 * Unsets the <b>dataDefault</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataDefault() getDataDefault} or {@link #setDataDefault(String value) setDataDefault(...)} for a definition of property <b>dataDefault</b>
	 */
	public void unsetDataDefault();

	/**
	 * Returns the value of the <b>dataDefault</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Default value for the column
	 * @return the value of the <b>dataDefault</b> property.
	 */
	public String getDataDefault();

	/**
	 * Sets the value of the <b>dataDefault</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Default value for the column
	 */
	public void setDataDefault(String value);


	/**
	 * Returns true if the <b>characterSetName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getCharacterSetName() getCharacterSetName} or {@link #setCharacterSetName(String value) setCharacterSetName(...)} for a definition of property <b>characterSetName</b>
	 * @return true if the <b>characterSetName</b> property is set.
	 */
	public boolean isSetCharacterSetName();

	/**
	 * Unsets the <b>characterSetName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getCharacterSetName() getCharacterSetName} or {@link #setCharacterSetName(String value) setCharacterSetName(...)} for a definition of property <b>characterSetName</b>
	 */
	public void unsetCharacterSetName();

	/**
	 * Returns the value of the <b>characterSetName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the character set: CHAR_CS or NCHAR_CS
	 * @return the value of the <b>characterSetName</b> property.
	 */
	public String getCharacterSetName();

	/**
	 * Sets the value of the <b>characterSetName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the character set: CHAR_CS or NCHAR_CS
	 */
	public void setCharacterSetName(String value);


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
	 * Owner of the table, view, or cluster
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner();

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the table, view, or cluster
	 */
	public void setOwner(String value);


	/**
	 * Returns true if the <b>table</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTable() getTable} or {@link #setTable(Table value) setTable(...)} for a definition of property <b>table</b>
	 * @return true if the <b>table</b> property is set.
	 */
	public boolean isSetTable();

	/**
	 * Unsets the <b>table</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTable() getTable} or {@link #setTable(Table value) setTable(...)} for a definition of property <b>table</b>
	 */
	public void unsetTable();

	/**
	 * Creates and returns a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property, <b>table</b>.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTable() getTable} or {@link #setTable(Table value) setTable(...)} for a definition of property <b>table</b>
	 * @return a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property <b>table</b>.
	 */
	public Table createTable();

	/**
	 * Returns the value of the <b>table</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Reference to the owner table
	 * @return the value of the <b>table</b> property.
	 */
	public Table getTable();

	/**
	 * Sets the value of the <b>table</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Reference to the owner table
	 */
	public void setTable(Table value);
}