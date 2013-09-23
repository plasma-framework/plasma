package org.plasma.provisioning.rdb.mysql.v5_5.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.lang.String;
import org.plasma.provisioning.rdb.mysql.v5_5.Table;
import org.plasma.provisioning.rdb.mysql.v5_5.View;

/**
 * Generated implementation class representing the domain model entity <b>View</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>VIEWS</b>.
 * <p></p>
 *
 */
public class ViewImpl extends CoreDataObject implements Serializable, View
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public ViewImpl() {
		super();
	}
	public ViewImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>isUpdatable</b> property is set.
	 * @return true if the <b>isUpdatable</b> property is set.
	 */
	public boolean isSetIsUpdatable(){
		return super.isSet(View.PROPERTY.isUpdatable.name());
	}

	/**
	 * Unsets the <b>isUpdatable</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetIsUpdatable(){
		super.unset(View.PROPERTY.isUpdatable.name());
	}

	/**
	 * Returns the value of the <b>isUpdatable</b> property.
	 * @return the value of the <b>isUpdatable</b> property.
	 */
	public String getIsUpdatable(){
		return (String)super.get(View.PROPERTY.isUpdatable.name());
	}

	/**
	 * Sets the value of the <b>isUpdatable</b> property to the given value.
	 */
	public void setIsUpdatable(String value){
		super.set(View.PROPERTY.isUpdatable.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(View.PROPERTY.owner.name());
	}

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOwner(){
		super.unset(View.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(View.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 */
	public void setOwner(String value){
		super.set(View.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>table</b> property is set.
	 * @return true if the <b>table</b> property is set.
	 */
	public boolean isSetTable(){
		return super.isSet(View.PROPERTY.table.name());
	}

	/**
	 * Unsets the <b>table</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTable(){
		super.unset(View.PROPERTY.table.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property, <b>table</b>.
	 * @return a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property <b>table</b>.
	 */
	public Table createTable(){
		return (Table)super.createDataObject(View.PROPERTY.table.name());
	}

	/**
	 * Returns the value of the <b>table</b> property.
	 * @return the value of the <b>table</b> property.
	 */
	public Table getTable(){
		return (Table)super.get(View.PROPERTY.table.name());
	}

	/**
	 * Sets the value of the <b>table</b> property to the given value.
	 */
	public void setTable(Table value){
		super.set(View.PROPERTY.table.name(), value);
	}


	/**
	 * Returns true if the <b>tableComment</b> property is set.
	 * @return true if the <b>tableComment</b> property is set.
	 */
	public boolean isSetTableComment(){
		return super.isSet(View.PROPERTY.tableComment.name());
	}

	/**
	 * Unsets the <b>tableComment</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTableComment(){
		super.unset(View.PROPERTY.tableComment.name());
	}

	/**
	 * Returns the value of the <b>tableComment</b> property.
	 * @return the value of the <b>tableComment</b> property.
	 */
	public String getTableComment(){
		return (String)super.get(View.PROPERTY.tableComment.name());
	}

	/**
	 * Sets the value of the <b>tableComment</b> property to the given value.
	 */
	public void setTableComment(String value){
		super.set(View.PROPERTY.tableComment.name(), value);
	}


	/**
	 * Returns true if the <b>viewDefinition</b> property is set.
	 * @return true if the <b>viewDefinition</b> property is set.
	 */
	public boolean isSetViewDefinition(){
		return super.isSet(View.PROPERTY.viewDefinition.name());
	}

	/**
	 * Unsets the <b>viewDefinition</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetViewDefinition(){
		super.unset(View.PROPERTY.viewDefinition.name());
	}

	/**
	 * Returns the value of the <b>viewDefinition</b> property.
	 * @return the value of the <b>viewDefinition</b> property.
	 */
	public String getViewDefinition(){
		return (String)super.get(View.PROPERTY.viewDefinition.name());
	}

	/**
	 * Sets the value of the <b>viewDefinition</b> property to the given value.
	 */
	public void setViewDefinition(String value){
		super.set(View.PROPERTY.viewDefinition.name(), value);
	}
}