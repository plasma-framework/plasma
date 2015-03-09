package org.plasma.provisioning.rdb.mysql.v5_5.impl;

import java.io.Serializable;

import org.plasma.provisioning.rdb.mysql.v5_5.Table;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumnKeyUsage;
import org.plasma.sdo.core.CoreDataObject;

/**
 * Generated implementation class representing the domain model entity <b>TableColumnKeyUsage</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>KEY_COLUMN_USAGE</b>.
 * <p></p>
 *
 */
public class TableColumnKeyUsageImpl extends CoreDataObject implements Serializable, TableColumnKeyUsage
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public TableColumnKeyUsageImpl() {
		super();
	}
	public TableColumnKeyUsageImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>columnName</b> property is set.
	 * @return true if the <b>columnName</b> property is set.
	 */
	public boolean isSetColumnName(){
		return super.isSet(TableColumnKeyUsage.PROPERTY.columnName.name());
	}

	/**
	 * Unsets the <b>columnName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnName(){
		super.unset(TableColumnKeyUsage.PROPERTY.columnName.name());
	}

	/**
	 * Returns the value of the <b>columnName</b> property.
	 * @return the value of the <b>columnName</b> property.
	 */
	public String getColumnName(){
		return (String)super.get(TableColumnKeyUsage.PROPERTY.columnName.name());
	}

	/**
	 * Sets the value of the <b>columnName</b> property to the given value.
	 */
	public void setColumnName(String value){
		super.set(TableColumnKeyUsage.PROPERTY.columnName.name(), value);
	}


	/**
	 * Returns true if the <b>name</b> property is set.
	 * @return true if the <b>name</b> property is set.
	 */
	public boolean isSetName(){
		return super.isSet(TableColumnKeyUsage.PROPERTY.name.name());
	}

	/**
	 * Unsets the <b>name</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetName(){
		super.unset(TableColumnKeyUsage.PROPERTY.name.name());
	}

	/**
	 * Returns the value of the <b>name</b> property.
	 * @return the value of the <b>name</b> property.
	 */
	public String getName(){
		return (String)super.get(TableColumnKeyUsage.PROPERTY.name.name());
	}

	/**
	 * Sets the value of the <b>name</b> property to the given value.
	 */
	public void setName(String value){
		super.set(TableColumnKeyUsage.PROPERTY.name.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(TableColumnKeyUsage.PROPERTY.owner.name());
	}

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOwner(){
		super.unset(TableColumnKeyUsage.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(TableColumnKeyUsage.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 */
	public void setOwner(String value){
		super.set(TableColumnKeyUsage.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>referencedColumnName</b> property is set.
	 * @return true if the <b>referencedColumnName</b> property is set.
	 */
	public boolean isSetReferencedColumnName(){
		return super.isSet(TableColumnKeyUsage.PROPERTY.referencedColumnName.name());
	}

	/**
	 * Unsets the <b>referencedColumnName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetReferencedColumnName(){
		super.unset(TableColumnKeyUsage.PROPERTY.referencedColumnName.name());
	}

	/**
	 * Returns the value of the <b>referencedColumnName</b> property.
	 * @return the value of the <b>referencedColumnName</b> property.
	 */
	public String getReferencedColumnName(){
		return (String)super.get(TableColumnKeyUsage.PROPERTY.referencedColumnName.name());
	}

	/**
	 * Sets the value of the <b>referencedColumnName</b> property to the given value.
	 */
	public void setReferencedColumnName(String value){
		super.set(TableColumnKeyUsage.PROPERTY.referencedColumnName.name(), value);
	}


	/**
	 * Returns true if the <b>referencedTableName</b> property is set.
	 * @return true if the <b>referencedTableName</b> property is set.
	 */
	public boolean isSetReferencedTableName(){
		return super.isSet(TableColumnKeyUsage.PROPERTY.referencedTableName.name());
	}

	/**
	 * Unsets the <b>referencedTableName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetReferencedTableName(){
		super.unset(TableColumnKeyUsage.PROPERTY.referencedTableName.name());
	}

	/**
	 * Returns the value of the <b>referencedTableName</b> property.
	 * @return the value of the <b>referencedTableName</b> property.
	 */
	public String getReferencedTableName(){
		return (String)super.get(TableColumnKeyUsage.PROPERTY.referencedTableName.name());
	}

	/**
	 * Sets the value of the <b>referencedTableName</b> property to the given value.
	 */
	public void setReferencedTableName(String value){
		super.set(TableColumnKeyUsage.PROPERTY.referencedTableName.name(), value);
	}


	/**
	 * Returns true if the <b>referencedTableSchema</b> property is set.
	 * @return true if the <b>referencedTableSchema</b> property is set.
	 */
	public boolean isSetReferencedTableSchema(){
		return super.isSet(TableColumnKeyUsage.PROPERTY.referencedTableSchema.name());
	}

	/**
	 * Unsets the <b>referencedTableSchema</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetReferencedTableSchema(){
		super.unset(TableColumnKeyUsage.PROPERTY.referencedTableSchema.name());
	}

	/**
	 * Returns the value of the <b>referencedTableSchema</b> property.
	 * @return the value of the <b>referencedTableSchema</b> property.
	 */
	public String getReferencedTableSchema(){
		return (String)super.get(TableColumnKeyUsage.PROPERTY.referencedTableSchema.name());
	}

	/**
	 * Sets the value of the <b>referencedTableSchema</b> property to the given value.
	 */
	public void setReferencedTableSchema(String value){
		super.set(TableColumnKeyUsage.PROPERTY.referencedTableSchema.name(), value);
	}


	/**
	 * Returns true if the <b>table</b> property is set.
	 * @return true if the <b>table</b> property is set.
	 */
	public boolean isSetTable(){
		return super.isSet(TableColumnKeyUsage.PROPERTY.table.name());
	}

	/**
	 * Unsets the <b>table</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTable(){
		super.unset(TableColumnKeyUsage.PROPERTY.table.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property, <b>table</b>.
	 * @return a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property <b>table</b>.
	 */
	public Table createTable(){
		return (Table)super.createDataObject(TableColumnKeyUsage.PROPERTY.table.name());
	}

	/**
	 * Returns the value of the <b>table</b> property.
	 * @return the value of the <b>table</b> property.
	 */
	public Table getTable(){
		return (Table)super.get(TableColumnKeyUsage.PROPERTY.table.name());
	}

	/**
	 * Sets the value of the <b>table</b> property to the given value.
	 */
	public void setTable(Table value){
		super.set(TableColumnKeyUsage.PROPERTY.table.name(), value);
	}
}