package org.plasma.provisioning.rdb.mysql.v5_5.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.lang.String;
import org.plasma.provisioning.rdb.mysql.v5_5.Table;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumnConstraint;

/**
 * Generated implementation class representing the domain model entity <b>TableColumnConstraint</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>REFERENTIAL_CONSTRAINTS</b>.
 * <p></p>
 *
 */
public class TableColumnConstraintImpl extends CoreDataObject implements Serializable, TableColumnConstraint
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public TableColumnConstraintImpl() {
		super();
	}
	public TableColumnConstraintImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>name</b> property is set.
	 * @return true if the <b>name</b> property is set.
	 */
	public boolean isSetName(){
		return super.isSet(TableColumnConstraint.PROPERTY.name.name());
	}

	/**
	 * Unsets the <b>name</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetName(){
		super.unset(TableColumnConstraint.PROPERTY.name.name());
	}

	/**
	 * Returns the value of the <b>name</b> property.
	 * @return the value of the <b>name</b> property.
	 */
	public String getName(){
		return (String)super.get(TableColumnConstraint.PROPERTY.name.name());
	}

	/**
	 * Sets the value of the <b>name</b> property to the given value.
	 */
	public void setName(String value){
		super.set(TableColumnConstraint.PROPERTY.name.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(TableColumnConstraint.PROPERTY.owner.name());
	}

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOwner(){
		super.unset(TableColumnConstraint.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(TableColumnConstraint.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 */
	public void setOwner(String value){
		super.set(TableColumnConstraint.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>table</b> property is set.
	 * @return true if the <b>table</b> property is set.
	 */
	public boolean isSetTable(){
		return super.isSet(TableColumnConstraint.PROPERTY.table.name());
	}

	/**
	 * Unsets the <b>table</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTable(){
		super.unset(TableColumnConstraint.PROPERTY.table.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property, <b>table</b>.
	 * @return a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property <b>table</b>.
	 */
	public Table createTable(){
		return (Table)super.createDataObject(TableColumnConstraint.PROPERTY.table.name());
	}

	/**
	 * Returns the value of the <b>table</b> property.
	 * @return the value of the <b>table</b> property.
	 */
	public Table getTable(){
		return (Table)super.get(TableColumnConstraint.PROPERTY.table.name());
	}

	/**
	 * Sets the value of the <b>table</b> property to the given value.
	 */
	public void setTable(Table value){
		super.set(TableColumnConstraint.PROPERTY.table.name(), value);
	}
}