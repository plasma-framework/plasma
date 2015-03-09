package org.plasma.provisioning.rdb.mysql.v5_5.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.plasma.provisioning.rdb.mysql.v5_5.Table;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumn;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumnConstraint;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumnKeyUsage;
import org.plasma.provisioning.rdb.mysql.v5_5.TableConstraint;
import org.plasma.provisioning.rdb.mysql.v5_5.View;
import org.plasma.sdo.core.CoreDataObject;

/**
 * Generated implementation class representing the domain model entity <b>Table</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>TABLES</b>.
 * <p></p>
 *
 */
public class TableImpl extends CoreDataObject implements Serializable, Table
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public TableImpl() {
		super();
	}
	public TableImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>autoIncrement</b> property is set.
	 * @return true if the <b>autoIncrement</b> property is set.
	 */
	public boolean isSetAutoIncrement(){
		return super.isSet(Table.PROPERTY.autoIncrement.name());
	}

	/**
	 * Unsets the <b>autoIncrement</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetAutoIncrement(){
		super.unset(Table.PROPERTY.autoIncrement.name());
	}

	/**
	 * Returns the value of the <b>autoIncrement</b> property.
	 * @return the value of the <b>autoIncrement</b> property.
	 */
	public boolean getAutoIncrement(){
		Boolean result = (Boolean)super.get(Table.PROPERTY.autoIncrement.name());
		if (result != null)
			return result.booleanValue();
		else return false;
	}

	/**
	 * Sets the value of the <b>autoIncrement</b> property to the given value.
	 */
	public void setAutoIncrement(boolean value){
		super.set(Table.PROPERTY.autoIncrement.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(Table.PROPERTY.owner.name());
	}

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOwner(){
		super.unset(Table.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(Table.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 */
	public void setOwner(String value){
		super.set(Table.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>tableColumn</b> property is set.
	 * @return true if the <b>tableColumn</b> property is set.
	 */
	public boolean isSetTableColumn(){
		return super.isSet(Table.PROPERTY.tableColumn.name());
	}

	/**
	 * Unsets the <b>tableColumn</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableColumn(){
		super.unset(Table.PROPERTY.tableColumn.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link TableColumn} automatically establishing a containment relationship through the object's reference property, <b>tableColumn</b>.
	 * @return a new instance of Type {@link TableColumn} automatically establishing a containment relationship through the object's reference property <b>tableColumn</b>.
	 */
	public TableColumn createTableColumn(){
		return (TableColumn)super.createDataObject(Table.PROPERTY.tableColumn.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>TableColumn</b> set for the object's multi-valued property <b>tableColumn</b>.
	 * @return an array of <b>TableColumn</b> set for the object's multi-valued property <b>tableColumn</b>.
	 */
	public TableColumn[] getTableColumn(){
		List<TableColumn> list = (List<TableColumn>)super.get(Table.PROPERTY.tableColumn.name());
		if (list != null) {
			TableColumn[] array = new TableColumn[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new TableColumn[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>TableColumn</b> set for the object's multi-valued property <b>tableColumn</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableColumn</b> set for the object's multi-valued property <b>tableColumn</b> based on the given index.
	 */
	public TableColumn getTableColumn(int idx){
		List<TableColumn> list = (List<TableColumn>)super.get(Table.PROPERTY.tableColumn.name());
		if (list != null) {
			return (TableColumn)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>tableColumn</b>.
	 * @return a count for multi-valued property <b>tableColumn</b>.
	 */
	public int getTableColumnCount(){
		List<TableColumn> list = (List<TableColumn>)super.get(Table.PROPERTY.tableColumn.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>TableColumn</b> for the object's multi-valued property <b>tableColumn</b>.
	 * @param value the array value
	 */
	public void setTableColumn(TableColumn[] value){
		List<TableColumn> list = (List<TableColumn>)super.get(Table.PROPERTY.tableColumn.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<TableColumn>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.tableColumn.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetTableColumn() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>TableColumn</b> for the object's multi-valued property <b>tableColumn</b>.
	 * @param value the value to add
	 */
	public void addTableColumn(TableColumn value){
		List<TableColumn> list = (List<TableColumn>)super.get(Table.PROPERTY.tableColumn.name());
		if (list == null)
			list = new ArrayList<TableColumn>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableColumn.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>TableColumn</b> for the object's multi-valued property <b>tableColumn</b>.
	 * @param value the value to remove
	 */
	public void removeTableColumn(TableColumn value){
		List<TableColumn> list = (List<TableColumn>)super.get(Table.PROPERTY.tableColumn.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableColumn.name(), list);
	}


	/**
	 * Returns true if the <b>tableColumnConstraint</b> property is set.
	 * @return true if the <b>tableColumnConstraint</b> property is set.
	 */
	public boolean isSetTableColumnConstraint(){
		return super.isSet(Table.PROPERTY.tableColumnConstraint.name());
	}

	/**
	 * Unsets the <b>tableColumnConstraint</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableColumnConstraint(){
		super.unset(Table.PROPERTY.tableColumnConstraint.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link TableColumnConstraint} automatically establishing a containment relationship through the object's reference property, <b>tableColumnConstraint</b>.
	 * @return a new instance of Type {@link TableColumnConstraint} automatically establishing a containment relationship through the object's reference property <b>tableColumnConstraint</b>.
	 */
	public TableColumnConstraint createTableColumnConstraint(){
		return (TableColumnConstraint)super.createDataObject(Table.PROPERTY.tableColumnConstraint.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>TableColumnConstraint</b> set for the object's multi-valued property <b>tableColumnConstraint</b>.
	 * @return an array of <b>TableColumnConstraint</b> set for the object's multi-valued property <b>tableColumnConstraint</b>.
	 */
	public TableColumnConstraint[] getTableColumnConstraint(){
		List<TableColumnConstraint> list = (List<TableColumnConstraint>)super.get(Table.PROPERTY.tableColumnConstraint.name());
		if (list != null) {
			TableColumnConstraint[] array = new TableColumnConstraint[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new TableColumnConstraint[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>TableColumnConstraint</b> set for the object's multi-valued property <b>tableColumnConstraint</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableColumnConstraint</b> set for the object's multi-valued property <b>tableColumnConstraint</b> based on the given index.
	 */
	public TableColumnConstraint getTableColumnConstraint(int idx){
		List<TableColumnConstraint> list = (List<TableColumnConstraint>)super.get(Table.PROPERTY.tableColumnConstraint.name());
		if (list != null) {
			return (TableColumnConstraint)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>tableColumnConstraint</b>.
	 * @return a count for multi-valued property <b>tableColumnConstraint</b>.
	 */
	public int getTableColumnConstraintCount(){
		List<TableColumnConstraint> list = (List<TableColumnConstraint>)super.get(Table.PROPERTY.tableColumnConstraint.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>TableColumnConstraint</b> for the object's multi-valued property <b>tableColumnConstraint</b>.
	 * @param value the array value
	 */
	public void setTableColumnConstraint(TableColumnConstraint[] value){
		List<TableColumnConstraint> list = (List<TableColumnConstraint>)super.get(Table.PROPERTY.tableColumnConstraint.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<TableColumnConstraint>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.tableColumnConstraint.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetTableColumnConstraint() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>TableColumnConstraint</b> for the object's multi-valued property <b>tableColumnConstraint</b>.
	 * @param value the value to add
	 */
	public void addTableColumnConstraint(TableColumnConstraint value){
		List<TableColumnConstraint> list = (List<TableColumnConstraint>)super.get(Table.PROPERTY.tableColumnConstraint.name());
		if (list == null)
			list = new ArrayList<TableColumnConstraint>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableColumnConstraint.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>TableColumnConstraint</b> for the object's multi-valued property <b>tableColumnConstraint</b>.
	 * @param value the value to remove
	 */
	public void removeTableColumnConstraint(TableColumnConstraint value){
		List<TableColumnConstraint> list = (List<TableColumnConstraint>)super.get(Table.PROPERTY.tableColumnConstraint.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableColumnConstraint.name(), list);
	}


	/**
	 * Returns true if the <b>tableColumnKeyUsage</b> property is set.
	 * @return true if the <b>tableColumnKeyUsage</b> property is set.
	 */
	public boolean isSetTableColumnKeyUsage(){
		return super.isSet(Table.PROPERTY.tableColumnKeyUsage.name());
	}

	/**
	 * Unsets the <b>tableColumnKeyUsage</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableColumnKeyUsage(){
		super.unset(Table.PROPERTY.tableColumnKeyUsage.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link TableColumnKeyUsage} automatically establishing a containment relationship through the object's reference property, <b>tableColumnKeyUsage</b>.
	 * @return a new instance of Type {@link TableColumnKeyUsage} automatically establishing a containment relationship through the object's reference property <b>tableColumnKeyUsage</b>.
	 */
	public TableColumnKeyUsage createTableColumnKeyUsage(){
		return (TableColumnKeyUsage)super.createDataObject(Table.PROPERTY.tableColumnKeyUsage.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>TableColumnKeyUsage</b> set for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 * @return an array of <b>TableColumnKeyUsage</b> set for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 */
	public TableColumnKeyUsage[] getTableColumnKeyUsage(){
		List<TableColumnKeyUsage> list = (List<TableColumnKeyUsage>)super.get(Table.PROPERTY.tableColumnKeyUsage.name());
		if (list != null) {
			TableColumnKeyUsage[] array = new TableColumnKeyUsage[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new TableColumnKeyUsage[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>TableColumnKeyUsage</b> set for the object's multi-valued property <b>tableColumnKeyUsage</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableColumnKeyUsage</b> set for the object's multi-valued property <b>tableColumnKeyUsage</b> based on the given index.
	 */
	public TableColumnKeyUsage getTableColumnKeyUsage(int idx){
		List<TableColumnKeyUsage> list = (List<TableColumnKeyUsage>)super.get(Table.PROPERTY.tableColumnKeyUsage.name());
		if (list != null) {
			return (TableColumnKeyUsage)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>tableColumnKeyUsage</b>.
	 * @return a count for multi-valued property <b>tableColumnKeyUsage</b>.
	 */
	public int getTableColumnKeyUsageCount(){
		List<TableColumnKeyUsage> list = (List<TableColumnKeyUsage>)super.get(Table.PROPERTY.tableColumnKeyUsage.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>TableColumnKeyUsage</b> for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 * @param value the array value
	 */
	public void setTableColumnKeyUsage(TableColumnKeyUsage[] value){
		List<TableColumnKeyUsage> list = (List<TableColumnKeyUsage>)super.get(Table.PROPERTY.tableColumnKeyUsage.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<TableColumnKeyUsage>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.tableColumnKeyUsage.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetTableColumnKeyUsage() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>TableColumnKeyUsage</b> for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 * @param value the value to add
	 */
	public void addTableColumnKeyUsage(TableColumnKeyUsage value){
		List<TableColumnKeyUsage> list = (List<TableColumnKeyUsage>)super.get(Table.PROPERTY.tableColumnKeyUsage.name());
		if (list == null)
			list = new ArrayList<TableColumnKeyUsage>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableColumnKeyUsage.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>TableColumnKeyUsage</b> for the object's multi-valued property <b>tableColumnKeyUsage</b>.
	 * @param value the value to remove
	 */
	public void removeTableColumnKeyUsage(TableColumnKeyUsage value){
		List<TableColumnKeyUsage> list = (List<TableColumnKeyUsage>)super.get(Table.PROPERTY.tableColumnKeyUsage.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableColumnKeyUsage.name(), list);
	}


	/**
	 * Returns true if the <b>tableComment</b> property is set.
	 * @return true if the <b>tableComment</b> property is set.
	 */
	public boolean isSetTableComment(){
		return super.isSet(Table.PROPERTY.tableComment.name());
	}

	/**
	 * Unsets the <b>tableComment</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTableComment(){
		super.unset(Table.PROPERTY.tableComment.name());
	}

	/**
	 * Returns the value of the <b>tableComment</b> property.
	 * @return the value of the <b>tableComment</b> property.
	 */
	public String getTableComment(){
		return (String)super.get(Table.PROPERTY.tableComment.name());
	}

	/**
	 * Sets the value of the <b>tableComment</b> property to the given value.
	 */
	public void setTableComment(String value){
		super.set(Table.PROPERTY.tableComment.name(), value);
	}


	/**
	 * Returns true if the <b>tableConstraint</b> property is set.
	 * @return true if the <b>tableConstraint</b> property is set.
	 */
	public boolean isSetTableConstraint(){
		return super.isSet(Table.PROPERTY.tableConstraint.name());
	}

	/**
	 * Unsets the <b>tableConstraint</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableConstraint(){
		super.unset(Table.PROPERTY.tableConstraint.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link TableConstraint} automatically establishing a containment relationship through the object's reference property, <b>tableConstraint</b>.
	 * @return a new instance of Type {@link TableConstraint} automatically establishing a containment relationship through the object's reference property <b>tableConstraint</b>.
	 */
	public TableConstraint createTableConstraint(){
		return (TableConstraint)super.createDataObject(Table.PROPERTY.tableConstraint.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>TableConstraint</b> set for the object's multi-valued property <b>tableConstraint</b>.
	 * @return an array of <b>TableConstraint</b> set for the object's multi-valued property <b>tableConstraint</b>.
	 */
	public TableConstraint[] getTableConstraint(){
		List<TableConstraint> list = (List<TableConstraint>)super.get(Table.PROPERTY.tableConstraint.name());
		if (list != null) {
			TableConstraint[] array = new TableConstraint[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new TableConstraint[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>TableConstraint</b> set for the object's multi-valued property <b>tableConstraint</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableConstraint</b> set for the object's multi-valued property <b>tableConstraint</b> based on the given index.
	 */
	public TableConstraint getTableConstraint(int idx){
		List<TableConstraint> list = (List<TableConstraint>)super.get(Table.PROPERTY.tableConstraint.name());
		if (list != null) {
			return (TableConstraint)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>tableConstraint</b>.
	 * @return a count for multi-valued property <b>tableConstraint</b>.
	 */
	public int getTableConstraintCount(){
		List<TableConstraint> list = (List<TableConstraint>)super.get(Table.PROPERTY.tableConstraint.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>TableConstraint</b> for the object's multi-valued property <b>tableConstraint</b>.
	 * @param value the array value
	 */
	public void setTableConstraint(TableConstraint[] value){
		List<TableConstraint> list = (List<TableConstraint>)super.get(Table.PROPERTY.tableConstraint.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<TableConstraint>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.tableConstraint.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetTableConstraint() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>TableConstraint</b> for the object's multi-valued property <b>tableConstraint</b>.
	 * @param value the value to add
	 */
	public void addTableConstraint(TableConstraint value){
		List<TableConstraint> list = (List<TableConstraint>)super.get(Table.PROPERTY.tableConstraint.name());
		if (list == null)
			list = new ArrayList<TableConstraint>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableConstraint.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>TableConstraint</b> for the object's multi-valued property <b>tableConstraint</b>.
	 * @param value the value to remove
	 */
	public void removeTableConstraint(TableConstraint value){
		List<TableConstraint> list = (List<TableConstraint>)super.get(Table.PROPERTY.tableConstraint.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableConstraint.name(), list);
	}


	/**
	 * Returns true if the <b>tableName</b> property is set.
	 * @return true if the <b>tableName</b> property is set.
	 */
	public boolean isSetTableName(){
		return super.isSet(Table.PROPERTY.tableName.name());
	}

	/**
	 * Unsets the <b>tableName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTableName(){
		super.unset(Table.PROPERTY.tableName.name());
	}

	/**
	 * Returns the value of the <b>tableName</b> property.
	 * @return the value of the <b>tableName</b> property.
	 */
	public String getTableName(){
		return (String)super.get(Table.PROPERTY.tableName.name());
	}

	/**
	 * Sets the value of the <b>tableName</b> property to the given value.
	 */
	public void setTableName(String value){
		super.set(Table.PROPERTY.tableName.name(), value);
	}


	/**
	 * Returns true if the <b>tableType</b> property is set.
	 * @return true if the <b>tableType</b> property is set.
	 */
	public boolean isSetTableType(){
		return super.isSet(Table.PROPERTY.tableType.name());
	}

	/**
	 * Unsets the <b>tableType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTableType(){
		super.unset(Table.PROPERTY.tableType.name());
	}

	/**
	 * Returns the value of the <b>tableType</b> property.
	 * @return the value of the <b>tableType</b> property.
	 */
	public String getTableType(){
		return (String)super.get(Table.PROPERTY.tableType.name());
	}

	/**
	 * Sets the value of the <b>tableType</b> property to the given value.
	 * <p></p>
	 * <b>Enumeration Constraints: </b><pre>
	 *     <b>name:</b> TableType
	 *     <b>URI:</b>http://org.plasma/sdo/mysql/5_5</pre>
	 */
	public void setTableType(String value){
		super.set(Table.PROPERTY.tableType.name(), value);
	}


	/**
	 * Returns true if the <b>view</b> property is set.
	 * @return true if the <b>view</b> property is set.
	 */
	public boolean isSetView(){
		return super.isSet(Table.PROPERTY.view.name());
	}

	/**
	 * Unsets the <b>view</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetView(){
		super.unset(Table.PROPERTY.view.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property, <b>view</b>.
	 * @return a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property <b>view</b>.
	 */
	public View createView(){
		return (View)super.createDataObject(Table.PROPERTY.view.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>View</b> set for the object's multi-valued property <b>view</b>.
	 * @return an array of <b>View</b> set for the object's multi-valued property <b>view</b>.
	 */
	public View[] getView(){
		List<View> list = (List<View>)super.get(Table.PROPERTY.view.name());
		if (list != null) {
			View[] array = new View[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new View[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>View</b> set for the object's multi-valued property <b>view</b> based on the given index.
	 * @param idx the index
	 * @return the <b>View</b> set for the object's multi-valued property <b>view</b> based on the given index.
	 */
	public View getView(int idx){
		List<View> list = (List<View>)super.get(Table.PROPERTY.view.name());
		if (list != null) {
			return (View)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>view</b>.
	 * @return a count for multi-valued property <b>view</b>.
	 */
	public int getViewCount(){
		List<View> list = (List<View>)super.get(Table.PROPERTY.view.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>View</b> for the object's multi-valued property <b>view</b>.
	 * @param value the array value
	 */
	public void setView(View[] value){
		List<View> list = (List<View>)super.get(Table.PROPERTY.view.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<View>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.view.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetView() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>View</b> for the object's multi-valued property <b>view</b>.
	 * @param value the value to add
	 */
	public void addView(View value){
		List<View> list = (List<View>)super.get(Table.PROPERTY.view.name());
		if (list == null)
			list = new ArrayList<View>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.view.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>View</b> for the object's multi-valued property <b>view</b>.
	 * @param value the value to remove
	 */
	public void removeView(View value){
		List<View> list = (List<View>)super.get(Table.PROPERTY.view.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.view.name(), list);
	}
}