package org.plasma.provisioning.rdb.mysql.v5_5.impl;

import java.io.Serializable;

import org.plasma.provisioning.rdb.mysql.v5_5.Table;
import org.plasma.provisioning.rdb.mysql.v5_5.TableConstraint;
import org.plasma.sdo.core.CoreDataObject;

/**
 * Generated implementation class representing the domain model entity <b>TableConstraint</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>TABLE_CONSTRAINTS</b>.
 * <p></p>
 *
 */
public class TableConstraintImpl extends CoreDataObject implements Serializable, TableConstraint
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/5_5";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public TableConstraintImpl() {
		super();
	}
	public TableConstraintImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>constraintType</b> property is set.
	 * @return true if the <b>constraintType</b> property is set.
	 */
	public boolean isSetConstraintType(){
		return super.isSet(TableConstraint.PROPERTY.constraintType.name());
	}

	/**
	 * Unsets the <b>constraintType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetConstraintType(){
		super.unset(TableConstraint.PROPERTY.constraintType.name());
	}

	/**
	 * Returns the value of the <b>constraintType</b> property.
	 * @return the value of the <b>constraintType</b> property.
	 */
	public String getConstraintType(){
		return (String)super.get(TableConstraint.PROPERTY.constraintType.name());
	}

	/**
	 * Sets the value of the <b>constraintType</b> property to the given value.
	 * <p></p>
	 * <b>Enumeration Constraints: </b><pre>
	 *     <b>name:</b> ConstraintType
	 *     <b>URI:</b>http://org.plasma/sdo/mysql/5_5</pre>
	 */
	public void setConstraintType(String value){
		super.set(TableConstraint.PROPERTY.constraintType.name(), value);
	}


	/**
	 * Returns true if the <b>name</b> property is set.
	 * @return true if the <b>name</b> property is set.
	 */
	public boolean isSetName(){
		return super.isSet(TableConstraint.PROPERTY.name.name());
	}

	/**
	 * Unsets the <b>name</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetName(){
		super.unset(TableConstraint.PROPERTY.name.name());
	}

	/**
	 * Returns the value of the <b>name</b> property.
	 * @return the value of the <b>name</b> property.
	 */
	public String getName(){
		return (String)super.get(TableConstraint.PROPERTY.name.name());
	}

	/**
	 * Sets the value of the <b>name</b> property to the given value.
	 */
	public void setName(String value){
		super.set(TableConstraint.PROPERTY.name.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(TableConstraint.PROPERTY.owner.name());
	}

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOwner(){
		super.unset(TableConstraint.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(TableConstraint.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 */
	public void setOwner(String value){
		super.set(TableConstraint.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>table</b> property is set.
	 * @return true if the <b>table</b> property is set.
	 */
	public boolean isSetTable(){
		return super.isSet(TableConstraint.PROPERTY.table.name());
	}

	/**
	 * Unsets the <b>table</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTable(){
		super.unset(TableConstraint.PROPERTY.table.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property, <b>table</b>.
	 * @return a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property <b>table</b>.
	 */
	public Table createTable(){
		return (Table)super.createDataObject(TableConstraint.PROPERTY.table.name());
	}

	/**
	 * Returns the value of the <b>table</b> property.
	 * @return the value of the <b>table</b> property.
	 */
	public Table getTable(){
		return (Table)super.get(TableConstraint.PROPERTY.table.name());
	}

	/**
	 * Sets the value of the <b>table</b> property to the given value.
	 */
	public void setTable(Table value){
		super.set(TableConstraint.PROPERTY.table.name(), value);
	}
}