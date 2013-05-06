package org.plasma.provisioning.rdb.oracle.sys.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;
import org.plasma.provisioning.rdb.oracle.sys.Column;
import org.plasma.provisioning.rdb.oracle.sys.ColumnComment;
import org.plasma.provisioning.rdb.oracle.sys.ColumnConstraint;
import org.plasma.provisioning.rdb.oracle.sys.Constraint;
import org.plasma.provisioning.rdb.oracle.sys.Table;
import org.plasma.provisioning.rdb.oracle.sys.TableComment;

/**
 * Represents a system table definition
 * <p></p>
 * Generated implementation class representing the domain model entity <b>Table</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_TABLES</b>.
 * <p></p>
 *
 */
public class TableImpl extends CoreDataObject implements Serializable, Table
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/sys";

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
	 * Returns true if the <b>column</b> property is set.
	 * @return true if the <b>column</b> property is set.
	 */
	public boolean isSetColumn(){
		return super.isSet(Table.PROPERTY.column.name());
	}

	/**
	 * Unsets the <b>column</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetColumn(){
		super.unset(Table.PROPERTY.column.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link Column} automatically establishing a containment relationship through the object's reference property, <b>column</b>.
	 * @return a new instance of Type {@link Column} automatically establishing a containment relationship through the object's reference property <b>column</b>.
	 */
	public Column createColumn(){
		return (Column)super.createDataObject(Table.PROPERTY.column.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>Column</b> set for the object's multi-valued property <b>column</b>.
	 * @return an array of <b>Column</b> set for the object's multi-valued property <b>column</b>.
	 */
	public Column[] getColumn(){
		List<Column> list = (List<Column>)super.get(Table.PROPERTY.column.name());
		if (list != null) {
			Column[] array = new Column[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new Column[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>Column</b> set for the object's multi-valued property <b>column</b> based on the given index.
	 * @param idx the index
	 * @return the <b>Column</b> set for the object's multi-valued property <b>column</b> based on the given index.
	 */
	public Column getColumn(int idx){
		List<Column> list = (List<Column>)super.get(Table.PROPERTY.column.name());
		if (list != null) {
			return (Column)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>column</b>.
	 * @return a count for multi-valued property <b>column</b>.
	 */
	public int getColumnCount(){
		List<Column> list = (List<Column>)super.get(Table.PROPERTY.column.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>Column</b> for the object's multi-valued property <b>column</b>.
	 * @param value the array value
	 */
	public void setColumn(Column[] value){
		List<Column> list = (List<Column>)super.get(Table.PROPERTY.column.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<Column>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.column.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetColumn() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>Column</b> for the object's multi-valued property <b>column</b>.
	 * @param value the value to add
	 */
	public void addColumn(Column value){
		List<Column> list = (List<Column>)super.get(Table.PROPERTY.column.name());
		if (list == null)
			list = new ArrayList<Column>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.column.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>Column</b> for the object's multi-valued property <b>column</b>.
	 * @param value the value to remove
	 */
	public void removeColumn(Column value){
		List<Column> list = (List<Column>)super.get(Table.PROPERTY.column.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.column.name(), list);
	}


	/**
	 * Returns true if the <b>columnComment</b> property is set.
	 * @return true if the <b>columnComment</b> property is set.
	 */
	public boolean isSetColumnComment(){
		return super.isSet(Table.PROPERTY.columnComment.name());
	}

	/**
	 * Unsets the <b>columnComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnComment(){
		super.unset(Table.PROPERTY.columnComment.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link ColumnComment} automatically establishing a containment relationship through the object's reference property, <b>columnComment</b>.
	 * @return a new instance of Type {@link ColumnComment} automatically establishing a containment relationship through the object's reference property <b>columnComment</b>.
	 */
	public ColumnComment createColumnComment(){
		return (ColumnComment)super.createDataObject(Table.PROPERTY.columnComment.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>ColumnComment</b> set for the object's multi-valued property <b>columnComment</b>.
	 * @return an array of <b>ColumnComment</b> set for the object's multi-valued property <b>columnComment</b>.
	 */
	public ColumnComment[] getColumnComment(){
		List<ColumnComment> list = (List<ColumnComment>)super.get(Table.PROPERTY.columnComment.name());
		if (list != null) {
			ColumnComment[] array = new ColumnComment[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new ColumnComment[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>ColumnComment</b> set for the object's multi-valued property <b>columnComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ColumnComment</b> set for the object's multi-valued property <b>columnComment</b> based on the given index.
	 */
	public ColumnComment getColumnComment(int idx){
		List<ColumnComment> list = (List<ColumnComment>)super.get(Table.PROPERTY.columnComment.name());
		if (list != null) {
			return (ColumnComment)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>columnComment</b>.
	 * @return a count for multi-valued property <b>columnComment</b>.
	 */
	public int getColumnCommentCount(){
		List<ColumnComment> list = (List<ColumnComment>)super.get(Table.PROPERTY.columnComment.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>ColumnComment</b> for the object's multi-valued property <b>columnComment</b>.
	 * @param value the array value
	 */
	public void setColumnComment(ColumnComment[] value){
		List<ColumnComment> list = (List<ColumnComment>)super.get(Table.PROPERTY.columnComment.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<ColumnComment>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.columnComment.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetColumnComment() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>ColumnComment</b> for the object's multi-valued property <b>columnComment</b>.
	 * @param value the value to add
	 */
	public void addColumnComment(ColumnComment value){
		List<ColumnComment> list = (List<ColumnComment>)super.get(Table.PROPERTY.columnComment.name());
		if (list == null)
			list = new ArrayList<ColumnComment>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.columnComment.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>ColumnComment</b> for the object's multi-valued property <b>columnComment</b>.
	 * @param value the value to remove
	 */
	public void removeColumnComment(ColumnComment value){
		List<ColumnComment> list = (List<ColumnComment>)super.get(Table.PROPERTY.columnComment.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.columnComment.name(), list);
	}


	/**
	 * Returns true if the <b>columnConstraint</b> property is set.
	 * @return true if the <b>columnConstraint</b> property is set.
	 */
	public boolean isSetColumnConstraint(){
		return super.isSet(Table.PROPERTY.columnConstraint.name());
	}

	/**
	 * Unsets the <b>columnConstraint</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnConstraint(){
		super.unset(Table.PROPERTY.columnConstraint.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link ColumnConstraint} automatically establishing a containment relationship through the object's reference property, <b>columnConstraint</b>.
	 * @return a new instance of Type {@link ColumnConstraint} automatically establishing a containment relationship through the object's reference property <b>columnConstraint</b>.
	 */
	public ColumnConstraint createColumnConstraint(){
		return (ColumnConstraint)super.createDataObject(Table.PROPERTY.columnConstraint.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>ColumnConstraint</b> set for the object's multi-valued property <b>columnConstraint</b>.
	 * @return an array of <b>ColumnConstraint</b> set for the object's multi-valued property <b>columnConstraint</b>.
	 */
	public ColumnConstraint[] getColumnConstraint(){
		List<ColumnConstraint> list = (List<ColumnConstraint>)super.get(Table.PROPERTY.columnConstraint.name());
		if (list != null) {
			ColumnConstraint[] array = new ColumnConstraint[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new ColumnConstraint[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>ColumnConstraint</b> set for the object's multi-valued property <b>columnConstraint</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ColumnConstraint</b> set for the object's multi-valued property <b>columnConstraint</b> based on the given index.
	 */
	public ColumnConstraint getColumnConstraint(int idx){
		List<ColumnConstraint> list = (List<ColumnConstraint>)super.get(Table.PROPERTY.columnConstraint.name());
		if (list != null) {
			return (ColumnConstraint)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>columnConstraint</b>.
	 * @return a count for multi-valued property <b>columnConstraint</b>.
	 */
	public int getColumnConstraintCount(){
		List<ColumnConstraint> list = (List<ColumnConstraint>)super.get(Table.PROPERTY.columnConstraint.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>ColumnConstraint</b> for the object's multi-valued property <b>columnConstraint</b>.
	 * @param value the array value
	 */
	public void setColumnConstraint(ColumnConstraint[] value){
		List<ColumnConstraint> list = (List<ColumnConstraint>)super.get(Table.PROPERTY.columnConstraint.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<ColumnConstraint>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.columnConstraint.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetColumnConstraint() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>ColumnConstraint</b> for the object's multi-valued property <b>columnConstraint</b>.
	 * @param value the value to add
	 */
	public void addColumnConstraint(ColumnConstraint value){
		List<ColumnConstraint> list = (List<ColumnConstraint>)super.get(Table.PROPERTY.columnConstraint.name());
		if (list == null)
			list = new ArrayList<ColumnConstraint>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.columnConstraint.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>ColumnConstraint</b> for the object's multi-valued property <b>columnConstraint</b>.
	 * @param value the value to remove
	 */
	public void removeColumnConstraint(ColumnConstraint value){
		List<ColumnConstraint> list = (List<ColumnConstraint>)super.get(Table.PROPERTY.columnConstraint.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.columnConstraint.name(), list);
	}


	/**
	 * Returns true if the <b>constraint</b> property is set.
	 * @return true if the <b>constraint</b> property is set.
	 */
	public boolean isSetConstraint(){
		return super.isSet(Table.PROPERTY.constraint.name());
	}

	/**
	 * Unsets the <b>constraint</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetConstraint(){
		super.unset(Table.PROPERTY.constraint.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link Constraint} automatically establishing a containment relationship through the object's reference property, <b>constraint</b>.
	 * @return a new instance of Type {@link Constraint} automatically establishing a containment relationship through the object's reference property <b>constraint</b>.
	 */
	public Constraint createConstraint(){
		return (Constraint)super.createDataObject(Table.PROPERTY.constraint.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>Constraint</b> set for the object's multi-valued property <b>constraint</b>.
	 * @return an array of <b>Constraint</b> set for the object's multi-valued property <b>constraint</b>.
	 */
	public Constraint[] getConstraint(){
		List<Constraint> list = (List<Constraint>)super.get(Table.PROPERTY.constraint.name());
		if (list != null) {
			Constraint[] array = new Constraint[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new Constraint[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>Constraint</b> set for the object's multi-valued property <b>constraint</b> based on the given index.
	 * @param idx the index
	 * @return the <b>Constraint</b> set for the object's multi-valued property <b>constraint</b> based on the given index.
	 */
	public Constraint getConstraint(int idx){
		List<Constraint> list = (List<Constraint>)super.get(Table.PROPERTY.constraint.name());
		if (list != null) {
			return (Constraint)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>constraint</b>.
	 * @return a count for multi-valued property <b>constraint</b>.
	 */
	public int getConstraintCount(){
		List<Constraint> list = (List<Constraint>)super.get(Table.PROPERTY.constraint.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>Constraint</b> for the object's multi-valued property <b>constraint</b>.
	 * @param value the array value
	 */
	public void setConstraint(Constraint[] value){
		List<Constraint> list = (List<Constraint>)super.get(Table.PROPERTY.constraint.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<Constraint>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.constraint.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetConstraint() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>Constraint</b> for the object's multi-valued property <b>constraint</b>.
	 * @param value the value to add
	 */
	public void addConstraint(Constraint value){
		List<Constraint> list = (List<Constraint>)super.get(Table.PROPERTY.constraint.name());
		if (list == null)
			list = new ArrayList<Constraint>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.constraint.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>Constraint</b> for the object's multi-valued property <b>constraint</b>.
	 * @param value the value to remove
	 */
	public void removeConstraint(Constraint value){
		List<Constraint> list = (List<Constraint>)super.get(Table.PROPERTY.constraint.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.constraint.name(), list);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
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
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 */
	public void unsetOwner(){
		super.unset(Table.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The schema owner
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(Table.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The schema owner
	 */
	public void setOwner(String value){
		super.set(Table.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>tableComment</b> property is set.
	 * @return true if the <b>tableComment</b> property is set.
	 */
	public boolean isSetTableComment(){
		return super.isSet(Table.PROPERTY.tableComment.name());
	}

	/**
	 * Unsets the <b>tableComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableComment(){
		super.unset(Table.PROPERTY.tableComment.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link TableComment} automatically establishing a containment relationship through the object's reference property, <b>tableComment</b>.
	 * @return a new instance of Type {@link TableComment} automatically establishing a containment relationship through the object's reference property <b>tableComment</b>.
	 */
	public TableComment createTableComment(){
		return (TableComment)super.createDataObject(Table.PROPERTY.tableComment.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>TableComment</b> set for the object's multi-valued property <b>tableComment</b>.
	 * @return an array of <b>TableComment</b> set for the object's multi-valued property <b>tableComment</b>.
	 */
	public TableComment[] getTableComment(){
		List<TableComment> list = (List<TableComment>)super.get(Table.PROPERTY.tableComment.name());
		if (list != null) {
			TableComment[] array = new TableComment[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new TableComment[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>TableComment</b> set for the object's multi-valued property <b>tableComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableComment</b> set for the object's multi-valued property <b>tableComment</b> based on the given index.
	 */
	public TableComment getTableComment(int idx){
		List<TableComment> list = (List<TableComment>)super.get(Table.PROPERTY.tableComment.name());
		if (list != null) {
			return (TableComment)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>tableComment</b>.
	 * @return a count for multi-valued property <b>tableComment</b>.
	 */
	public int getTableCommentCount(){
		List<TableComment> list = (List<TableComment>)super.get(Table.PROPERTY.tableComment.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>TableComment</b> for the object's multi-valued property <b>tableComment</b>.
	 * @param value the array value
	 */
	public void setTableComment(TableComment[] value){
		List<TableComment> list = (List<TableComment>)super.get(Table.PROPERTY.tableComment.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<TableComment>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.tableComment.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetTableComment() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>TableComment</b> for the object's multi-valued property <b>tableComment</b>.
	 * @param value the value to add
	 */
	public void addTableComment(TableComment value){
		List<TableComment> list = (List<TableComment>)super.get(Table.PROPERTY.tableComment.name());
		if (list == null)
			list = new ArrayList<TableComment>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableComment.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>TableComment</b> for the object's multi-valued property <b>tableComment</b>.
	 * @param value the value to remove
	 */
	public void removeTableComment(TableComment value){
		List<TableComment> list = (List<TableComment>)super.get(Table.PROPERTY.tableComment.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableComment.name(), list);
	}


	/**
	 * Returns true if the <b>tableName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTableName() getTableName} or {@link #setTableName(String value) setTableName(...)} for a definition of property <b>tableName</b>
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
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTableName() getTableName} or {@link #setTableName(String value) setTableName(...)} for a definition of property <b>tableName</b>
	 */
	public void unsetTableName(){
		super.unset(Table.PROPERTY.tableName.name());
	}

	/**
	 * Returns the value of the <b>tableName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The table physical names
	 * @return the value of the <b>tableName</b> property.
	 */
	public String getTableName(){
		return (String)super.get(Table.PROPERTY.tableName.name());
	}

	/**
	 * Sets the value of the <b>tableName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The table physical names
	 */
	public void setTableName(String value){
		super.set(Table.PROPERTY.tableName.name(), value);
	}
}