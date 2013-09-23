package org.plasma.provisioning.rdb.mysql.v5_5.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.lang.String;
import org.plasma.provisioning.rdb.mysql.v5_5.Table;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumn;

/**
 * Represents a system column definition which is linked to its
 * system Table definition by association.
 * <p></p>
 * Generated implementation class representing the domain model entity <b>TableColumn</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>COLUMNS</b>.
 * <p></p>
 *
 */
public class TableColumnImpl extends CoreDataObject implements Serializable, TableColumn
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public TableColumnImpl() {
		super();
	}
	public TableColumnImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>charMaxLength</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getCharMaxLength() getCharMaxLength} or {@link #setCharMaxLength(int value) setCharMaxLength(...)} for a definition of property <b>charMaxLength</b>
	 * @return true if the <b>charMaxLength</b> property is set.
	 */
	public boolean isSetCharMaxLength(){
		return super.isSet(TableColumn.PROPERTY.charMaxLength.name());
	}

	/**
	 * Unsets the <b>charMaxLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getCharMaxLength() getCharMaxLength} or {@link #setCharMaxLength(int value) setCharMaxLength(...)} for a definition of property <b>charMaxLength</b>
	 */
	public void unsetCharMaxLength(){
		super.unset(TableColumn.PROPERTY.charMaxLength.name());
	}

	/**
	 * Returns the value of the <b>charMaxLength</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 * @return the value of the <b>charMaxLength</b> property.
	 */
	public int getCharMaxLength(){
		Integer result = (Integer)super.get(TableColumn.PROPERTY.charMaxLength.name());
		if (result != null)
			return result.intValue();
		else return 0;
	}

	/**
	 * Sets the value of the <b>charMaxLength</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 */
	public void setCharMaxLength(int value){
		super.set(TableColumn.PROPERTY.charMaxLength.name(), value);
	}


	/**
	 * Returns true if the <b>characterSetName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getCharacterSetName() getCharacterSetName} or {@link #setCharacterSetName(String value) setCharacterSetName(...)} for a definition of property <b>characterSetName</b>
	 * @return true if the <b>characterSetName</b> property is set.
	 */
	public boolean isSetCharacterSetName(){
		return super.isSet(TableColumn.PROPERTY.characterSetName.name());
	}

	/**
	 * Unsets the <b>characterSetName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getCharacterSetName() getCharacterSetName} or {@link #setCharacterSetName(String value) setCharacterSetName(...)} for a definition of property <b>characterSetName</b>
	 */
	public void unsetCharacterSetName(){
		super.unset(TableColumn.PROPERTY.characterSetName.name());
	}

	/**
	 * Returns the value of the <b>characterSetName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the character set: CHAR_CS or NCHAR_CS
	 * @return the value of the <b>characterSetName</b> property.
	 */
	public String getCharacterSetName(){
		return (String)super.get(TableColumn.PROPERTY.characterSetName.name());
	}

	/**
	 * Sets the value of the <b>characterSetName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the character set: CHAR_CS or NCHAR_CS
	 */
	public void setCharacterSetName(String value){
		super.set(TableColumn.PROPERTY.characterSetName.name(), value);
	}


	/**
	 * Returns true if the <b>columnComment</b> property is set.
	 * @return true if the <b>columnComment</b> property is set.
	 */
	public boolean isSetColumnComment(){
		return super.isSet(TableColumn.PROPERTY.columnComment.name());
	}

	/**
	 * Unsets the <b>columnComment</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnComment(){
		super.unset(TableColumn.PROPERTY.columnComment.name());
	}

	/**
	 * Returns the value of the <b>columnComment</b> property.
	 * @return the value of the <b>columnComment</b> property.
	 */
	public String getColumnComment(){
		return (String)super.get(TableColumn.PROPERTY.columnComment.name());
	}

	/**
	 * Sets the value of the <b>columnComment</b> property to the given value.
	 */
	public void setColumnComment(String value){
		super.set(TableColumn.PROPERTY.columnComment.name(), value);
	}


	/**
	 * Returns true if the <b>columnKey</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnKey() getColumnKey} or {@link #setColumnKey(String value) setColumnKey(...)} for a definition of property <b>columnKey</b>
	 * @return true if the <b>columnKey</b> property is set.
	 */
	public boolean isSetColumnKey(){
		return super.isSet(TableColumn.PROPERTY.columnKey.name());
	}

	/**
	 * Unsets the <b>columnKey</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnKey() getColumnKey} or {@link #setColumnKey(String value) setColumnKey(...)} for a definition of property <b>columnKey</b>
	 */
	public void unsetColumnKey(){
		super.unset(TableColumn.PROPERTY.columnKey.name());
	}

	/**
	 * Returns the value of the <b>columnKey</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Default value for the column
	 * @return the value of the <b>columnKey</b> property.
	 */
	public String getColumnKey(){
		return (String)super.get(TableColumn.PROPERTY.columnKey.name());
	}

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
	public void setColumnKey(String value){
		super.set(TableColumn.PROPERTY.columnKey.name(), value);
	}


	/**
	 * Returns true if the <b>columnName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnName() getColumnName} or {@link #setColumnName(String value) setColumnName(...)} for a definition of property <b>columnName</b>
	 * @return true if the <b>columnName</b> property is set.
	 */
	public boolean isSetColumnName(){
		return super.isSet(TableColumn.PROPERTY.columnName.name());
	}

	/**
	 * Unsets the <b>columnName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnName() getColumnName} or {@link #setColumnName(String value) setColumnName(...)} for a definition of property <b>columnName</b>
	 */
	public void unsetColumnName(){
		super.unset(TableColumn.PROPERTY.columnName.name());
	}

	/**
	 * Returns the value of the <b>columnName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Column name
	 * @return the value of the <b>columnName</b> property.
	 */
	public String getColumnName(){
		return (String)super.get(TableColumn.PROPERTY.columnName.name());
	}

	/**
	 * Sets the value of the <b>columnName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Column name
	 */
	public void setColumnName(String value){
		super.set(TableColumn.PROPERTY.columnName.name(), value);
	}


	/**
	 * Returns true if the <b>columnType</b> property is set.
	 * @return true if the <b>columnType</b> property is set.
	 */
	public boolean isSetColumnType(){
		return super.isSet(TableColumn.PROPERTY.columnType.name());
	}

	/**
	 * Unsets the <b>columnType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnType(){
		super.unset(TableColumn.PROPERTY.columnType.name());
	}

	/**
	 * Returns the value of the <b>columnType</b> property.
	 * @return the value of the <b>columnType</b> property.
	 */
	public String getColumnType(){
		return (String)super.get(TableColumn.PROPERTY.columnType.name());
	}

	/**
	 * Sets the value of the <b>columnType</b> property to the given value.
	 */
	public void setColumnType(String value){
		super.set(TableColumn.PROPERTY.columnType.name(), value);
	}


	/**
	 * Returns true if the <b>dataPrecision</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataPrecision() getDataPrecision} or {@link #setDataPrecision(int value) setDataPrecision(...)} for a definition of property <b>dataPrecision</b>
	 * @return true if the <b>dataPrecision</b> property is set.
	 */
	public boolean isSetDataPrecision(){
		return super.isSet(TableColumn.PROPERTY.dataPrecision.name());
	}

	/**
	 * Unsets the <b>dataPrecision</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataPrecision() getDataPrecision} or {@link #setDataPrecision(int value) setDataPrecision(...)} for a definition of property <b>dataPrecision</b>
	 */
	public void unsetDataPrecision(){
		super.unset(TableColumn.PROPERTY.dataPrecision.name());
	}

	/**
	 * Returns the value of the <b>dataPrecision</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Decimal precision (total number of digits) for NUMBER 
	 * datatype; binary precision for FLOAT datatype, null for 
	 * all other datatypes.
	 * @return the value of the <b>dataPrecision</b> property.
	 */
	public int getDataPrecision(){
		Integer result = (Integer)super.get(TableColumn.PROPERTY.dataPrecision.name());
		if (result != null)
			return result.intValue();
		else return 0;
	}

	/**
	 * Sets the value of the <b>dataPrecision</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Decimal precision (total number of digits) for NUMBER 
	 * datatype; binary precision for FLOAT datatype, null for 
	 * all other datatypes.
	 */
	public void setDataPrecision(int value){
		super.set(TableColumn.PROPERTY.dataPrecision.name(), value);
	}


	/**
	 * Returns true if the <b>dataScale</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataScale() getDataScale} or {@link #setDataScale(int value) setDataScale(...)} for a definition of property <b>dataScale</b>
	 * @return true if the <b>dataScale</b> property is set.
	 */
	public boolean isSetDataScale(){
		return super.isSet(TableColumn.PROPERTY.dataScale.name());
	}

	/**
	 * Unsets the <b>dataScale</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataScale() getDataScale} or {@link #setDataScale(int value) setDataScale(...)} for a definition of property <b>dataScale</b>
	 */
	public void unsetDataScale(){
		super.unset(TableColumn.PROPERTY.dataScale.name());
	}

	/**
	 * Returns the value of the <b>dataScale</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Digits to right of decimal point in a number
	 * @return the value of the <b>dataScale</b> property.
	 */
	public int getDataScale(){
		Integer result = (Integer)super.get(TableColumn.PROPERTY.dataScale.name());
		if (result != null)
			return result.intValue();
		else return 0;
	}

	/**
	 * Sets the value of the <b>dataScale</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Digits to right of decimal point in a number
	 */
	public void setDataScale(int value){
		super.set(TableColumn.PROPERTY.dataScale.name(), value);
	}


	/**
	 * Returns true if the <b>dataType</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataType() getDataType} or {@link #setDataType(String value) setDataType(...)} for a definition of property <b>dataType</b>
	 * @return true if the <b>dataType</b> property is set.
	 */
	public boolean isSetDataType(){
		return super.isSet(TableColumn.PROPERTY.dataType.name());
	}

	/**
	 * Unsets the <b>dataType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataType() getDataType} or {@link #setDataType(String value) setDataType(...)} for a definition of property <b>dataType</b>
	 */
	public void unsetDataType(){
		super.unset(TableColumn.PROPERTY.dataType.name());
	}

	/**
	 * Returns the value of the <b>dataType</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Datatype of the column
	 * @return the value of the <b>dataType</b> property.
	 */
	public String getDataType(){
		return (String)super.get(TableColumn.PROPERTY.dataType.name());
	}

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
	public void setDataType(String value){
		super.set(TableColumn.PROPERTY.dataType.name(), value);
	}


	/**
	 * Returns true if the <b>nullable</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getNullable() getNullable} or {@link #setNullable(String value) setNullable(...)} for a definition of property <b>nullable</b>
	 * @return true if the <b>nullable</b> property is set.
	 */
	public boolean isSetNullable(){
		return super.isSet(TableColumn.PROPERTY.nullable.name());
	}

	/**
	 * Unsets the <b>nullable</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getNullable() getNullable} or {@link #setNullable(String value) setNullable(...)} for a definition of property <b>nullable</b>
	 */
	public void unsetNullable(){
		super.unset(TableColumn.PROPERTY.nullable.name());
	}

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
	public String getNullable(){
		return (String)super.get(TableColumn.PROPERTY.nullable.name());
	}

	/**
	 * Sets the value of the <b>nullable</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Specifies whether a column allows NULLs. Value is N if there
	 * is a NOT NULL constraint on the column or if the column 
	 * is part of a PRIMARY KEY. The constraint should be in an 
	 * ENABLE VALIDATE state.
	 */
	public void setNullable(String value){
		super.set(TableColumn.PROPERTY.nullable.name(), value);
	}


	/**
	 * Returns true if the <b>octetMaxLength</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOctetMaxLength() getOctetMaxLength} or {@link #setOctetMaxLength(int value) setOctetMaxLength(...)} for a definition of property <b>octetMaxLength</b>
	 * @return true if the <b>octetMaxLength</b> property is set.
	 */
	public boolean isSetOctetMaxLength(){
		return super.isSet(TableColumn.PROPERTY.octetMaxLength.name());
	}

	/**
	 * Unsets the <b>octetMaxLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOctetMaxLength() getOctetMaxLength} or {@link #setOctetMaxLength(int value) setOctetMaxLength(...)} for a definition of property <b>octetMaxLength</b>
	 */
	public void unsetOctetMaxLength(){
		super.unset(TableColumn.PROPERTY.octetMaxLength.name());
	}

	/**
	 * Returns the value of the <b>octetMaxLength</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 * @return the value of the <b>octetMaxLength</b> property.
	 */
	public int getOctetMaxLength(){
		Integer result = (Integer)super.get(TableColumn.PROPERTY.octetMaxLength.name());
		if (result != null)
			return result.intValue();
		else return 0;
	}

	/**
	 * Sets the value of the <b>octetMaxLength</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 */
	public void setOctetMaxLength(int value){
		super.set(TableColumn.PROPERTY.octetMaxLength.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(TableColumn.PROPERTY.owner.name());
	}

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 */
	public void unsetOwner(){
		super.unset(TableColumn.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the table, view, or cluster
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(TableColumn.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the table, view, or cluster
	 */
	public void setOwner(String value){
		super.set(TableColumn.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>table</b> property is set.
	 * @return true if the <b>table</b> property is set.
	 */
	public boolean isSetTable(){
		return super.isSet(TableColumn.PROPERTY.table.name());
	}

	/**
	 * Unsets the <b>table</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTable(){
		super.unset(TableColumn.PROPERTY.table.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property, <b>table</b>.
	 * @return a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property <b>table</b>.
	 */
	public Table createTable(){
		return (Table)super.createDataObject(TableColumn.PROPERTY.table.name());
	}

	/**
	 * Returns the value of the <b>table</b> property.
	 * @return the value of the <b>table</b> property.
	 */
	public Table getTable(){
		return (Table)super.get(TableColumn.PROPERTY.table.name());
	}

	/**
	 * Sets the value of the <b>table</b> property to the given value.
	 */
	public void setTable(Table value){
		super.set(TableColumn.PROPERTY.table.name(), value);
	}
}