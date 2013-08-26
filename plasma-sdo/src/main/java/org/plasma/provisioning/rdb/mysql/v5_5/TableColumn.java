package org.plasma.provisioning.rdb.mysql.v5_5;


import org.plasma.provisioning.rdb.mysql.v5_5.Table;
import org.plasma.sdo.PlasmaDataObject;

/**
 * Represents a system column definition which is linked to its
 * system Table definition by association.
 * <p></p>
 * Generated interface representing the domain model entity <b>TableColumn</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/mysql/5_5</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>COLUMNS</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.mysql.v5_5.Table Table
 */
public interface TableColumn extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

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
		 * Corresponds to the physical data store element <b>COLUMNS.COLUMN_NAME</b>.
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
		 * Corresponds to the physical data store element <b>COLUMNS.DATA_TYPE</b>.
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
		 * Corresponds to the physical data store element <b>COLUMNS.IS_NULLABLE</b>.
		 */
		nullable,
		
		/**
		 * Length of the column (in bytes)
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>charMaxLength</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>COLUMNS.CHARACTER_MAXIMUM_LENGTH</b>.
		 */
		charMaxLength,
		
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
		 * Corresponds to the physical data store element <b>COLUMNS.NUMERIC_PRECISION</b>.
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
		 * Corresponds to the physical data store element <b>COLUMNS.NUMERIC_SCALE</b>.
		 */
		dataScale,
		
		/**
		 * Default value for the column
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>columnKey</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>COLUMNS.COLUMN_KEY</b>.
		 */
		columnKey,
		
		/**
		 * Name of the character set: CHAR_CS or NCHAR_CS
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>characterSetName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>COLUMNS.CHARACTER_SET_NAME</b>.
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
		 * Corresponds to the physical data store element <b>COLUMNS.TABLE_SCHEMA</b>.
		 */
		owner,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>columnComment</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>COLUMNS.COLUMN_COMMENT</b>.
		 */
		columnComment,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>table</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>COLUMNS.TABLE_NAME</b>.
		 */
		table,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>columnType</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>COLUMNS.COLUMN_TYPE</b>.
		 */
		columnType,
		
		/**
		 * Length of the column (in bytes)
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>octetMaxLength</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableColumn</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>COLUMNS.CHARACTER_OCTET_LENGTH</b>.
		 */
		octetMaxLength
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
	 *     <b>URI:</b>http://org.plasma/sdo/mysql/5_5</pre>
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
	 * Returns true if the <b>charMaxLength</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getCharMaxLength() getCharMaxLength} or {@link #setCharMaxLength(int value) setCharMaxLength(...)} for a definition of property <b>charMaxLength</b>
	 * @return true if the <b>charMaxLength</b> property is set.
	 */
	public boolean isSetCharMaxLength();

	/**
	 * Unsets the <b>charMaxLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getCharMaxLength() getCharMaxLength} or {@link #setCharMaxLength(int value) setCharMaxLength(...)} for a definition of property <b>charMaxLength</b>
	 */
	public void unsetCharMaxLength();

	/**
	 * Returns the value of the <b>charMaxLength</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 * @return the value of the <b>charMaxLength</b> property.
	 */
	public int getCharMaxLength();

	/**
	 * Sets the value of the <b>charMaxLength</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 */
	public void setCharMaxLength(int value);


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
	 * Returns true if the <b>columnKey</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnKey() getColumnKey} or {@link #setColumnKey(String value) setColumnKey(...)} for a definition of property <b>columnKey</b>
	 * @return true if the <b>columnKey</b> property is set.
	 */
	public boolean isSetColumnKey();

	/**
	 * Unsets the <b>columnKey</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnKey() getColumnKey} or {@link #setColumnKey(String value) setColumnKey(...)} for a definition of property <b>columnKey</b>
	 */
	public void unsetColumnKey();

	/**
	 * Returns the value of the <b>columnKey</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Default value for the column
	 * @return the value of the <b>columnKey</b> property.
	 */
	public String getColumnKey();

	/**
	 * Sets the value of the <b>columnKey</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Default value for the column
	 * <p></p>
	 * <b>Enumeration Constraints: </b><pre>
	 *     <b>name:</b> ColumnKeyType
	 *     <b>URI:</b>http://org.plasma/sdo/mysql/5_5</pre>
	 */
	public void setColumnKey(String value);


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
	 * Returns true if the <b>columnComment</b> property is set.
	 * @return true if the <b>columnComment</b> property is set.
	 */
	public boolean isSetColumnComment();

	/**
	 * Unsets the <b>columnComment</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnComment();

	/**
	 * Returns the value of the <b>columnComment</b> property.
	 * @return the value of the <b>columnComment</b> property.
	 */
	public String getColumnComment();

	/**
	 * Sets the value of the <b>columnComment</b> property to the given value.
	 */
	public void setColumnComment(String value);


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


	/**
	 * Returns true if the <b>columnType</b> property is set.
	 * @return true if the <b>columnType</b> property is set.
	 */
	public boolean isSetColumnType();

	/**
	 * Unsets the <b>columnType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnType();

	/**
	 * Returns the value of the <b>columnType</b> property.
	 * @return the value of the <b>columnType</b> property.
	 */
	public String getColumnType();

	/**
	 * Sets the value of the <b>columnType</b> property to the given value.
	 */
	public void setColumnType(String value);


	/**
	 * Returns true if the <b>octetMaxLength</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOctetMaxLength() getOctetMaxLength} or {@link #setOctetMaxLength(int value) setOctetMaxLength(...)} for a definition of property <b>octetMaxLength</b>
	 * @return true if the <b>octetMaxLength</b> property is set.
	 */
	public boolean isSetOctetMaxLength();

	/**
	 * Unsets the <b>octetMaxLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOctetMaxLength() getOctetMaxLength} or {@link #setOctetMaxLength(int value) setOctetMaxLength(...)} for a definition of property <b>octetMaxLength</b>
	 */
	public void unsetOctetMaxLength();

	/**
	 * Returns the value of the <b>octetMaxLength</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 * @return the value of the <b>octetMaxLength</b> property.
	 */
	public int getOctetMaxLength();

	/**
	 * Sets the value of the <b>octetMaxLength</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 */
	public void setOctetMaxLength(int value);
}