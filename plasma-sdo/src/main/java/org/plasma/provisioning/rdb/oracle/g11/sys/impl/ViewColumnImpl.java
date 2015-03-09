package org.plasma.provisioning.rdb.oracle.g11.sys.impl;

import java.io.Serializable;

import org.plasma.provisioning.rdb.oracle.g11.sys.View;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumn;
import org.plasma.sdo.core.CoreDataObject;

/**
 * Represents a system column definition which is linked to its
 * system Table definition by association.
 * <p></p>
 * Generated implementation class representing the domain model entity <b>ViewColumn</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_TAB_COLUMNS</b>.
 * <p></p>
 *
 */
public class ViewColumnImpl extends CoreDataObject implements Serializable, ViewColumn
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public ViewColumnImpl() {
		super();
	}
	public ViewColumnImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>characterSetName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getCharacterSetName() getCharacterSetName} or {@link #setCharacterSetName(String value) setCharacterSetName(...)} for a definition of property <b>characterSetName</b>
	 * @return true if the <b>characterSetName</b> property is set.
	 */
	public boolean isSetCharacterSetName(){
		return super.isSet(ViewColumn.PROPERTY.characterSetName.name());
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
		super.unset(ViewColumn.PROPERTY.characterSetName.name());
	}

	/**
	 * Returns the value of the <b>characterSetName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the character set: CHAR_CS or NCHAR_CS
	 * @return the value of the <b>characterSetName</b> property.
	 */
	public String getCharacterSetName(){
		return (String)super.get(ViewColumn.PROPERTY.characterSetName.name());
	}

	/**
	 * Sets the value of the <b>characterSetName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the character set: CHAR_CS or NCHAR_CS
	 */
	public void setCharacterSetName(String value){
		super.set(ViewColumn.PROPERTY.characterSetName.name(), value);
	}


	/**
	 * Returns true if the <b>columnName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnName() getColumnName} or {@link #setColumnName(String value) setColumnName(...)} for a definition of property <b>columnName</b>
	 * @return true if the <b>columnName</b> property is set.
	 */
	public boolean isSetColumnName(){
		return super.isSet(ViewColumn.PROPERTY.columnName.name());
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
		super.unset(ViewColumn.PROPERTY.columnName.name());
	}

	/**
	 * Returns the value of the <b>columnName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Column name
	 * @return the value of the <b>columnName</b> property.
	 */
	public String getColumnName(){
		return (String)super.get(ViewColumn.PROPERTY.columnName.name());
	}

	/**
	 * Sets the value of the <b>columnName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Column name
	 */
	public void setColumnName(String value){
		super.set(ViewColumn.PROPERTY.columnName.name(), value);
	}


	/**
	 * Returns true if the <b>column_id</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumn_id() getColumn_id} or {@link #setColumn_id(int value) setColumn_id(...)} for a definition of property <b>column_id</b>
	 * @return true if the <b>column_id</b> property is set.
	 */
	public boolean isSetColumn_id(){
		return super.isSet(ViewColumn.PROPERTY.column_id.name());
	}

	/**
	 * Unsets the <b>column_id</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumn_id() getColumn_id} or {@link #setColumn_id(int value) setColumn_id(...)} for a definition of property <b>column_id</b>
	 */
	public void unsetColumn_id(){
		super.unset(ViewColumn.PROPERTY.column_id.name());
	}

	/**
	 * Returns the value of the <b>column_id</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Sequence number of the column as created
	 * @return the value of the <b>column_id</b> property.
	 */
	public int getColumn_id(){
		Integer result = (Integer)super.get(ViewColumn.PROPERTY.column_id.name());
		if (result != null)
			return result.intValue();
		else return 0;
	}

	/**
	 * Sets the value of the <b>column_id</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Sequence number of the column as created
	 */
	public void setColumn_id(int value){
		super.set(ViewColumn.PROPERTY.column_id.name(), value);
	}


	/**
	 * Returns true if the <b>dataDefault</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataDefault() getDataDefault} or {@link #setDataDefault(String value) setDataDefault(...)} for a definition of property <b>dataDefault</b>
	 * @return true if the <b>dataDefault</b> property is set.
	 */
	public boolean isSetDataDefault(){
		return super.isSet(ViewColumn.PROPERTY.dataDefault.name());
	}

	/**
	 * Unsets the <b>dataDefault</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataDefault() getDataDefault} or {@link #setDataDefault(String value) setDataDefault(...)} for a definition of property <b>dataDefault</b>
	 */
	public void unsetDataDefault(){
		super.unset(ViewColumn.PROPERTY.dataDefault.name());
	}

	/**
	 * Returns the value of the <b>dataDefault</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Default value for the column
	 * @return the value of the <b>dataDefault</b> property.
	 */
	public String getDataDefault(){
		return (String)super.get(ViewColumn.PROPERTY.dataDefault.name());
	}

	/**
	 * Sets the value of the <b>dataDefault</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Default value for the column
	 */
	public void setDataDefault(String value){
		super.set(ViewColumn.PROPERTY.dataDefault.name(), value);
	}


	/**
	 * Returns true if the <b>dataLength</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataLength() getDataLength} or {@link #setDataLength(int value) setDataLength(...)} for a definition of property <b>dataLength</b>
	 * @return true if the <b>dataLength</b> property is set.
	 */
	public boolean isSetDataLength(){
		return super.isSet(ViewColumn.PROPERTY.dataLength.name());
	}

	/**
	 * Unsets the <b>dataLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataLength() getDataLength} or {@link #setDataLength(int value) setDataLength(...)} for a definition of property <b>dataLength</b>
	 */
	public void unsetDataLength(){
		super.unset(ViewColumn.PROPERTY.dataLength.name());
	}

	/**
	 * Returns the value of the <b>dataLength</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 * @return the value of the <b>dataLength</b> property.
	 */
	public int getDataLength(){
		Integer result = (Integer)super.get(ViewColumn.PROPERTY.dataLength.name());
		if (result != null)
			return result.intValue();
		else return 0;
	}

	/**
	 * Sets the value of the <b>dataLength</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Length of the column (in bytes)
	 */
	public void setDataLength(int value){
		super.set(ViewColumn.PROPERTY.dataLength.name(), value);
	}


	/**
	 * Returns true if the <b>dataPrecision</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataPrecision() getDataPrecision} or {@link #setDataPrecision(int value) setDataPrecision(...)} for a definition of property <b>dataPrecision</b>
	 * @return true if the <b>dataPrecision</b> property is set.
	 */
	public boolean isSetDataPrecision(){
		return super.isSet(ViewColumn.PROPERTY.dataPrecision.name());
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
		super.unset(ViewColumn.PROPERTY.dataPrecision.name());
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
		Integer result = (Integer)super.get(ViewColumn.PROPERTY.dataPrecision.name());
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
		super.set(ViewColumn.PROPERTY.dataPrecision.name(), value);
	}


	/**
	 * Returns true if the <b>dataScale</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataScale() getDataScale} or {@link #setDataScale(int value) setDataScale(...)} for a definition of property <b>dataScale</b>
	 * @return true if the <b>dataScale</b> property is set.
	 */
	public boolean isSetDataScale(){
		return super.isSet(ViewColumn.PROPERTY.dataScale.name());
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
		super.unset(ViewColumn.PROPERTY.dataScale.name());
	}

	/**
	 * Returns the value of the <b>dataScale</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Digits to right of decimal point in a number
	 * @return the value of the <b>dataScale</b> property.
	 */
	public int getDataScale(){
		Integer result = (Integer)super.get(ViewColumn.PROPERTY.dataScale.name());
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
		super.set(ViewColumn.PROPERTY.dataScale.name(), value);
	}


	/**
	 * Returns true if the <b>dataType</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDataType() getDataType} or {@link #setDataType(String value) setDataType(...)} for a definition of property <b>dataType</b>
	 * @return true if the <b>dataType</b> property is set.
	 */
	public boolean isSetDataType(){
		return super.isSet(ViewColumn.PROPERTY.dataType.name());
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
		super.unset(ViewColumn.PROPERTY.dataType.name());
	}

	/**
	 * Returns the value of the <b>dataType</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Datatype of the column
	 * @return the value of the <b>dataType</b> property.
	 */
	public String getDataType(){
		return (String)super.get(ViewColumn.PROPERTY.dataType.name());
	}

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
	public void setDataType(String value){
		super.set(ViewColumn.PROPERTY.dataType.name(), value);
	}


	/**
	 * Returns true if the <b>nullable</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getNullable() getNullable} or {@link #setNullable(String value) setNullable(...)} for a definition of property <b>nullable</b>
	 * @return true if the <b>nullable</b> property is set.
	 */
	public boolean isSetNullable(){
		return super.isSet(ViewColumn.PROPERTY.nullable.name());
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
		super.unset(ViewColumn.PROPERTY.nullable.name());
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
		return (String)super.get(ViewColumn.PROPERTY.nullable.name());
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
		super.set(ViewColumn.PROPERTY.nullable.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(ViewColumn.PROPERTY.owner.name());
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
		super.unset(ViewColumn.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the table, view, or cluster
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(ViewColumn.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the table, view, or cluster
	 */
	public void setOwner(String value){
		super.set(ViewColumn.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>view</b> property is set.
	 * @return true if the <b>view</b> property is set.
	 */
	public boolean isSetView(){
		return super.isSet(ViewColumn.PROPERTY.view.name());
	}

	/**
	 * Unsets the <b>view</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetView(){
		super.unset(ViewColumn.PROPERTY.view.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property, <b>view</b>.
	 * @return a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property <b>view</b>.
	 */
	public View createView(){
		return (View)super.createDataObject(ViewColumn.PROPERTY.view.name());
	}

	/**
	 * Returns the value of the <b>view</b> property.
	 * @return the value of the <b>view</b> property.
	 */
	public View getView(){
		return (View)super.get(ViewColumn.PROPERTY.view.name());
	}

	/**
	 * Sets the value of the <b>view</b> property to the given value.
	 */
	public void setView(View value){
		super.set(ViewColumn.PROPERTY.view.name(), value);
	}
}