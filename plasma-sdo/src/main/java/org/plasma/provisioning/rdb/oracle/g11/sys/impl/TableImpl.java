package org.plasma.provisioning.rdb.oracle.g11.sys.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;
import org.plasma.provisioning.rdb.oracle.g11.sys.Constraint;
import org.plasma.provisioning.rdb.oracle.g11.sys.Table;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumn;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnComment;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnConstraint;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableComment;

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
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

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
	 * Returns true if the <b>tableColumnComment</b> property is set.
	 * @return true if the <b>tableColumnComment</b> property is set.
	 */
	public boolean isSetTableColumnComment(){
		return super.isSet(Table.PROPERTY.tableColumnComment.name());
	}

	/**
	 * Unsets the <b>tableColumnComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetTableColumnComment(){
		super.unset(Table.PROPERTY.tableColumnComment.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link TableColumnComment} automatically establishing a containment relationship through the object's reference property, <b>tableColumnComment</b>.
	 * @return a new instance of Type {@link TableColumnComment} automatically establishing a containment relationship through the object's reference property <b>tableColumnComment</b>.
	 */
	public TableColumnComment createTableColumnComment(){
		return (TableColumnComment)super.createDataObject(Table.PROPERTY.tableColumnComment.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>TableColumnComment</b> set for the object's multi-valued property <b>tableColumnComment</b>.
	 * @return an array of <b>TableColumnComment</b> set for the object's multi-valued property <b>tableColumnComment</b>.
	 */
	public TableColumnComment[] getTableColumnComment(){
		List<TableColumnComment> list = (List<TableColumnComment>)super.get(Table.PROPERTY.tableColumnComment.name());
		if (list != null) {
			TableColumnComment[] array = new TableColumnComment[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new TableColumnComment[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>TableColumnComment</b> set for the object's multi-valued property <b>tableColumnComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>TableColumnComment</b> set for the object's multi-valued property <b>tableColumnComment</b> based on the given index.
	 */
	public TableColumnComment getTableColumnComment(int idx){
		List<TableColumnComment> list = (List<TableColumnComment>)super.get(Table.PROPERTY.tableColumnComment.name());
		if (list != null) {
			return (TableColumnComment)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>tableColumnComment</b>.
	 * @return a count for multi-valued property <b>tableColumnComment</b>.
	 */
	public int getTableColumnCommentCount(){
		List<TableColumnComment> list = (List<TableColumnComment>)super.get(Table.PROPERTY.tableColumnComment.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>TableColumnComment</b> for the object's multi-valued property <b>tableColumnComment</b>.
	 * @param value the array value
	 */
	public void setTableColumnComment(TableColumnComment[] value){
		List<TableColumnComment> list = (List<TableColumnComment>)super.get(Table.PROPERTY.tableColumnComment.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<TableColumnComment>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(Table.PROPERTY.tableColumnComment.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetTableColumnComment() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>TableColumnComment</b> for the object's multi-valued property <b>tableColumnComment</b>.
	 * @param value the value to add
	 */
	public void addTableColumnComment(TableColumnComment value){
		List<TableColumnComment> list = (List<TableColumnComment>)super.get(Table.PROPERTY.tableColumnComment.name());
		if (list == null)
			list = new ArrayList<TableColumnComment>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableColumnComment.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>TableColumnComment</b> for the object's multi-valued property <b>tableColumnComment</b>.
	 * @param value the value to remove
	 */
	public void removeTableColumnComment(TableColumnComment value){
		List<TableColumnComment> list = (List<TableColumnComment>)super.get(Table.PROPERTY.tableColumnComment.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(Table.PROPERTY.tableColumnComment.name(), list);
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