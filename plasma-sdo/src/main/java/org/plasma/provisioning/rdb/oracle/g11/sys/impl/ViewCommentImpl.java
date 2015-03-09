package org.plasma.provisioning.rdb.oracle.g11.sys.impl;

import java.io.Serializable;

import org.plasma.provisioning.rdb.oracle.g11.sys.View;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewComment;
import org.plasma.sdo.core.CoreDataObject;

/**
 * A comment on a table
 * <p></p>
 * Generated implementation class representing the domain model entity <b>ViewComment</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_TAB_COMMENTS</b>.
 * <p></p>
 *
 */
public class ViewCommentImpl extends CoreDataObject implements Serializable, ViewComment
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public ViewCommentImpl() {
		super();
	}
	public ViewCommentImpl(commonj.sdo.Type type) {
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
		return super.isSet(ViewComment.PROPERTY.comments.name());
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
		super.unset(ViewComment.PROPERTY.comments.name());
	}

	/**
	 * Returns the value of the <b>comments</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The comment body
	 * @return the value of the <b>comments</b> property.
	 */
	public String getComments(){
		return (String)super.get(ViewComment.PROPERTY.comments.name());
	}

	/**
	 * Sets the value of the <b>comments</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The comment body
	 */
	public void setComments(String value){
		super.set(ViewComment.PROPERTY.comments.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(ViewComment.PROPERTY.owner.name());
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
		super.unset(ViewComment.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The owner schema name
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(ViewComment.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The owner schema name
	 */
	public void setOwner(String value){
		super.set(ViewComment.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>tableType</b> property is set.
	 * @return true if the <b>tableType</b> property is set.
	 */
	public boolean isSetTableType(){
		return super.isSet(ViewComment.PROPERTY.tableType.name());
	}

	/**
	 * Unsets the <b>tableType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTableType(){
		super.unset(ViewComment.PROPERTY.tableType.name());
	}

	/**
	 * Returns the value of the <b>tableType</b> property.
	 * @return the value of the <b>tableType</b> property.
	 */
	public String getTableType(){
		return (String)super.get(ViewComment.PROPERTY.tableType.name());
	}

	/**
	 * Sets the value of the <b>tableType</b> property to the given value.
	 * <p></p>
	 * <b>Enumeration Constraints: </b><pre>
	 *     <b>name:</b> TableType
	 *     <b>URI:</b>http://org.plasma/sdo/oracle/11g/sys</pre>
	 */
	public void setTableType(String value){
		super.set(ViewComment.PROPERTY.tableType.name(), value);
	}


	/**
	 * Returns true if the <b>view</b> property is set.
	 * @return true if the <b>view</b> property is set.
	 */
	public boolean isSetView(){
		return super.isSet(ViewComment.PROPERTY.view.name());
	}

	/**
	 * Unsets the <b>view</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetView(){
		super.unset(ViewComment.PROPERTY.view.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property, <b>view</b>.
	 * @return a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property <b>view</b>.
	 */
	public View createView(){
		return (View)super.createDataObject(ViewComment.PROPERTY.view.name());
	}

	/**
	 * Returns the value of the <b>view</b> property.
	 * @return the value of the <b>view</b> property.
	 */
	public View getView(){
		return (View)super.get(ViewComment.PROPERTY.view.name());
	}

	/**
	 * Sets the value of the <b>view</b> property to the given value.
	 */
	public void setView(View value){
		super.set(ViewComment.PROPERTY.view.name(), value);
	}
}