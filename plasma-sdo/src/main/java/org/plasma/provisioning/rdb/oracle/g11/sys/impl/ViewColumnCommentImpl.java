package org.plasma.provisioning.rdb.oracle.g11.sys.impl;

import java.io.Serializable;

import org.plasma.provisioning.rdb.oracle.g11.sys.View;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumnComment;
import org.plasma.sdo.core.CoreDataObject;

/**
 * The comment for a column
 * <p></p>
 * Generated implementation class representing the domain model entity <b>ViewColumnComment</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_COL_COMMENTS</b>.
 * <p></p>
 *
 */
public class ViewColumnCommentImpl extends CoreDataObject implements Serializable, ViewColumnComment
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public ViewColumnCommentImpl() {
		super();
	}
	public ViewColumnCommentImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>columnName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getColumnName() getColumnName} or {@link #setColumnName(String value) setColumnName(...)} for a definition of property <b>columnName</b>
	 * @return true if the <b>columnName</b> property is set.
	 */
	public boolean isSetColumnName(){
		return super.isSet(ViewColumnComment.PROPERTY.columnName.name());
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
		super.unset(ViewColumnComment.PROPERTY.columnName.name());
	}

	/**
	 * Returns the value of the <b>columnName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The name of the associated column
	 * @return the value of the <b>columnName</b> property.
	 */
	public String getColumnName(){
		return (String)super.get(ViewColumnComment.PROPERTY.columnName.name());
	}

	/**
	 * Sets the value of the <b>columnName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The name of the associated column
	 */
	public void setColumnName(String value){
		super.set(ViewColumnComment.PROPERTY.columnName.name(), value);
	}


	/**
	 * Returns true if the <b>comments</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getComments() getComments} or {@link #setComments(String value) setComments(...)} for a definition of property <b>comments</b>
	 * @return true if the <b>comments</b> property is set.
	 */
	public boolean isSetComments(){
		return super.isSet(ViewColumnComment.PROPERTY.comments.name());
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
		super.unset(ViewColumnComment.PROPERTY.comments.name());
	}

	/**
	 * Returns the value of the <b>comments</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The comment body
	 * @return the value of the <b>comments</b> property.
	 */
	public String getComments(){
		return (String)super.get(ViewColumnComment.PROPERTY.comments.name());
	}

	/**
	 * Sets the value of the <b>comments</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The comment body
	 */
	public void setComments(String value){
		super.set(ViewColumnComment.PROPERTY.comments.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(ViewColumnComment.PROPERTY.owner.name());
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
		super.unset(ViewColumnComment.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The owner schema name
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(ViewColumnComment.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The owner schema name
	 */
	public void setOwner(String value){
		super.set(ViewColumnComment.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>view</b> property is set.
	 * @return true if the <b>view</b> property is set.
	 */
	public boolean isSetView(){
		return super.isSet(ViewColumnComment.PROPERTY.view.name());
	}

	/**
	 * Unsets the <b>view</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetView(){
		super.unset(ViewColumnComment.PROPERTY.view.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property, <b>view</b>.
	 * @return a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property <b>view</b>.
	 */
	public View createView(){
		return (View)super.createDataObject(ViewColumnComment.PROPERTY.view.name());
	}

	/**
	 * Returns the value of the <b>view</b> property.
	 * @return the value of the <b>view</b> property.
	 */
	public View getView(){
		return (View)super.get(ViewColumnComment.PROPERTY.view.name());
	}

	/**
	 * Sets the value of the <b>view</b> property to the given value.
	 */
	public void setView(View value){
		super.set(ViewColumnComment.PROPERTY.view.name(), value);
	}
}