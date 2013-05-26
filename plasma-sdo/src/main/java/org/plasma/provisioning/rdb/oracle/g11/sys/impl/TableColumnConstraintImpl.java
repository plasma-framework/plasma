package org.plasma.provisioning.rdb.oracle.g11.sys.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.lang.String;
import org.plasma.provisioning.rdb.oracle.g11.sys.Table;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnConstraint;

/**
 * A constraint definition
 * <p></p>
 * Generated implementation class representing the domain model entity <b>TableColumnConstraint</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_CONS_COLUMNS</b>.
 * <p></p>
 *
 */
public class TableColumnConstraintImpl extends CoreDataObject implements Serializable, TableColumnConstraint
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

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
	 * Returns true if the <b>columnName</b> property is set.
	 * @return true if the <b>columnName</b> property is set.
	 */
	public boolean isSetColumnName(){
		return super.isSet(TableColumnConstraint.PROPERTY.columnName.name());
	}

	/**
	 * Unsets the <b>columnName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetColumnName(){
		super.unset(TableColumnConstraint.PROPERTY.columnName.name());
	}

	/**
	 * Returns the value of the <b>columnName</b> property.
	 * @return the value of the <b>columnName</b> property.
	 */
	public String getColumnName(){
		return (String)super.get(TableColumnConstraint.PROPERTY.columnName.name());
	}

	/**
	 * Sets the value of the <b>columnName</b> property to the given value.
	 */
	public void setColumnName(String value){
		super.set(TableColumnConstraint.PROPERTY.columnName.name(), value);
	}


	/**
	 * Returns true if the <b>constraintName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getConstraintName() getConstraintName} or {@link #setConstraintName(String value) setConstraintName(...)} for a definition of property <b>constraintName</b>
	 * @return true if the <b>constraintName</b> property is set.
	 */
	public boolean isSetConstraintName(){
		return super.isSet(TableColumnConstraint.PROPERTY.constraintName.name());
	}

	/**
	 * Unsets the <b>constraintName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getConstraintName() getConstraintName} or {@link #setConstraintName(String value) setConstraintName(...)} for a definition of property <b>constraintName</b>
	 */
	public void unsetConstraintName(){
		super.unset(TableColumnConstraint.PROPERTY.constraintName.name());
	}

	/**
	 * Returns the value of the <b>constraintName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the constraint definition
	 * @return the value of the <b>constraintName</b> property.
	 */
	public String getConstraintName(){
		return (String)super.get(TableColumnConstraint.PROPERTY.constraintName.name());
	}

	/**
	 * Sets the value of the <b>constraintName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the constraint definition
	 */
	public void setConstraintName(String value){
		super.set(TableColumnConstraint.PROPERTY.constraintName.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
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
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 */
	public void unsetOwner(){
		super.unset(TableColumnConstraint.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the constraint definition
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(TableColumnConstraint.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the constraint definition
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