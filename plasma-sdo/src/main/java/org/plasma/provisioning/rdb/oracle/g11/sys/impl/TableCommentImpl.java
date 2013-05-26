package org.plasma.provisioning.rdb.oracle.g11.sys.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.lang.String;
import org.plasma.provisioning.rdb.oracle.g11.sys.Table;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableComment;

/**
 * A comment on a table
 * <p></p>
 * Generated implementation class representing the domain model entity <b>TableComment</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_TAB_COMMENTS</b>.
 * <p></p>
 *
 */
public class TableCommentImpl extends CoreDataObject implements Serializable, TableComment
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public TableCommentImpl() {
		super();
	}
	public TableCommentImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>comments</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getComments() getComments} or {@link #setComments(String value) setComments(...)} for a definition of property <b>comments</b>
	 * @return true if the <b>comments</b> property is set.
	 */
	public boolean isSetComments(){
		return super.isSet(TableComment.PROPERTY.comments.name());
	}

	/**
	 * Unsets the <b>comments</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getComments() getComments} or {@link #setComments(String value) setComments(...)} for a definition of property <b>comments</b>
	 */
	public void unsetComments(){
		super.unset(TableComment.PROPERTY.comments.name());
	}

	/**
	 * Returns the value of the <b>comments</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The comment body
	 * @return the value of the <b>comments</b> property.
	 */
	public String getComments(){
		return (String)super.get(TableComment.PROPERTY.comments.name());
	}

	/**
	 * Sets the value of the <b>comments</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The comment body
	 */
	public void setComments(String value){
		super.set(TableComment.PROPERTY.comments.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(TableComment.PROPERTY.owner.name());
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
		super.unset(TableComment.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The owner schema name
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(TableComment.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The owner schema name
	 */
	public void setOwner(String value){
		super.set(TableComment.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>table</b> property is set.
	 * @return true if the <b>table</b> property is set.
	 */
	public boolean isSetTable(){
		return super.isSet(TableComment.PROPERTY.table.name());
	}

	/**
	 * Unsets the <b>table</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTable(){
		super.unset(TableComment.PROPERTY.table.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property, <b>table</b>.
	 * @return a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property <b>table</b>.
	 */
	public Table createTable(){
		return (Table)super.createDataObject(TableComment.PROPERTY.table.name());
	}

	/**
	 * Returns the value of the <b>table</b> property.
	 * @return the value of the <b>table</b> property.
	 */
	public Table getTable(){
		return (Table)super.get(TableComment.PROPERTY.table.name());
	}

	/**
	 * Sets the value of the <b>table</b> property to the given value.
	 */
	public void setTable(Table value){
		super.set(TableComment.PROPERTY.table.name(), value);
	}


	/**
	 * Returns true if the <b>tableType</b> property is set.
	 * @return true if the <b>tableType</b> property is set.
	 */
	public boolean isSetTableType(){
		return super.isSet(TableComment.PROPERTY.tableType.name());
	}

	/**
	 * Unsets the <b>tableType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTableType(){
		super.unset(TableComment.PROPERTY.tableType.name());
	}

	/**
	 * Returns the value of the <b>tableType</b> property.
	 * @return the value of the <b>tableType</b> property.
	 */
	public String getTableType(){
		return (String)super.get(TableComment.PROPERTY.tableType.name());
	}

	/**
	 * Sets the value of the <b>tableType</b> property to the given value.
	 * <p></p>
	 * <b>Enumeration Constraints: </b><pre>
	 *     <b>name:</b> TableType
	 *     <b>URI:</b>http://org.plasma/sdo/oracle/11g/sys</pre>
	 */
	public void setTableType(String value){
		super.set(TableComment.PROPERTY.tableType.name(), value);
	}
}