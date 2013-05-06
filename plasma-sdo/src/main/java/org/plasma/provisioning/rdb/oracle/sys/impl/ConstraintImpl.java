package org.plasma.provisioning.rdb.oracle.sys.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.lang.String;
import org.plasma.provisioning.rdb.oracle.sys.Constraint;
import org.plasma.provisioning.rdb.oracle.sys.Table;

/**
 * A constraint definition
 * <p></p>
 * Generated implementation class representing the domain model entity <b>Constraint</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_CONSTRAINTS</b>.
 * <p></p>
 *
 */
public class ConstraintImpl extends CoreDataObject implements Serializable, Constraint
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/sys";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public ConstraintImpl() {
		super();
	}
	public ConstraintImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>constraintName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getConstraintName() getConstraintName} or {@link #setConstraintName(String value) setConstraintName(...)} for a definition of property <b>constraintName</b>
	 * @return true if the <b>constraintName</b> property is set.
	 */
	public boolean isSetConstraintName(){
		return super.isSet(Constraint.PROPERTY.constraintName.name());
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
		super.unset(Constraint.PROPERTY.constraintName.name());
	}

	/**
	 * Returns the value of the <b>constraintName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the constraint definition
	 * @return the value of the <b>constraintName</b> property.
	 */
	public String getConstraintName(){
		return (String)super.get(Constraint.PROPERTY.constraintName.name());
	}

	/**
	 * Sets the value of the <b>constraintName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the constraint definition
	 */
	public void setConstraintName(String value){
		super.set(Constraint.PROPERTY.constraintName.name(), value);
	}


	/**
	 * Returns true if the <b>constraintType</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getConstraintType() getConstraintType} or {@link #setConstraintType(String value) setConstraintType(...)} for a definition of property <b>constraintType</b>
	 * @return true if the <b>constraintType</b> property is set.
	 */
	public boolean isSetConstraintType(){
		return super.isSet(Constraint.PROPERTY.constraintType.name());
	}

	/**
	 * Unsets the <b>constraintType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getConstraintType() getConstraintType} or {@link #setConstraintType(String value) setConstraintType(...)} for a definition of property <b>constraintType</b>
	 */
	public void unsetConstraintType(){
		super.unset(Constraint.PROPERTY.constraintType.name());
	}

	/**
	 * Returns the value of the <b>constraintType</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Type of constraint definition:
	 * C (check constraint on a table)
	 * P (primary key)
	 * U (unique key)
	 * R (referential integrity)
	 * V (with check option, on a view)
	 * O (with read only, on a view)
	 * @return the value of the <b>constraintType</b> property.
	 */
	public String getConstraintType(){
		return (String)super.get(Constraint.PROPERTY.constraintType.name());
	}

	/**
	 * Sets the value of the <b>constraintType</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Type of constraint definition:
	 * C (check constraint on a table)
	 * P (primary key)
	 * U (unique key)
	 * R (referential integrity)
	 * V (with check option, on a view)
	 * O (with read only, on a view)
	 * <p></p>
	 * <b>Enumeration Constraints: </b><pre>
	 *     <b>name:</b> ConstraintType
	 *     <b>URI:</b>http://org.plasma/sdo/oracle/sys</pre>
	 */
	public void setConstraintType(String value){
		super.set(Constraint.PROPERTY.constraintType.name(), value);
	}


	/**
	 * Returns true if the <b>deleteRule</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDeleteRule() getDeleteRule} or {@link #setDeleteRule(String value) setDeleteRule(...)} for a definition of property <b>deleteRule</b>
	 * @return true if the <b>deleteRule</b> property is set.
	 */
	public boolean isSetDeleteRule(){
		return super.isSet(Constraint.PROPERTY.deleteRule.name());
	}

	/**
	 * Unsets the <b>deleteRule</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getDeleteRule() getDeleteRule} or {@link #setDeleteRule(String value) setDeleteRule(...)} for a definition of property <b>deleteRule</b>
	 */
	public void unsetDeleteRule(){
		super.unset(Constraint.PROPERTY.deleteRule.name());
	}

	/**
	 * Returns the value of the <b>deleteRule</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Delete rule for a referential constraint (CASCADE or NO 
	 * ACTION)
	 * @return the value of the <b>deleteRule</b> property.
	 */
	public String getDeleteRule(){
		return (String)super.get(Constraint.PROPERTY.deleteRule.name());
	}

	/**
	 * Sets the value of the <b>deleteRule</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Delete rule for a referential constraint (CASCADE or NO 
	 * ACTION)
	 */
	public void setDeleteRule(String value){
		super.set(Constraint.PROPERTY.deleteRule.name(), value);
	}


	/**
	 * Returns true if the <b>indexName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getIndexName() getIndexName} or {@link #setIndexName(String value) setIndexName(...)} for a definition of property <b>indexName</b>
	 * @return true if the <b>indexName</b> property is set.
	 */
	public boolean isSetIndexName(){
		return super.isSet(Constraint.PROPERTY.indexName.name());
	}

	/**
	 * Unsets the <b>indexName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getIndexName() getIndexName} or {@link #setIndexName(String value) setIndexName(...)} for a definition of property <b>indexName</b>
	 */
	public void unsetIndexName(){
		super.unset(Constraint.PROPERTY.indexName.name());
	}

	/**
	 * Returns the value of the <b>indexName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the index (only shown for unique and primary-key 
	 * constraints)
	 * @return the value of the <b>indexName</b> property.
	 */
	public String getIndexName(){
		return (String)super.get(Constraint.PROPERTY.indexName.name());
	}

	/**
	 * Sets the value of the <b>indexName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the index (only shown for unique and primary-key 
	 * constraints)
	 */
	public void setIndexName(String value){
		super.set(Constraint.PROPERTY.indexName.name(), value);
	}


	/**
	 * Returns true if the <b>indexOwner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getIndexOwner() getIndexOwner} or {@link #setIndexOwner(String value) setIndexOwner(...)} for a definition of property <b>indexOwner</b>
	 * @return true if the <b>indexOwner</b> property is set.
	 */
	public boolean isSetIndexOwner(){
		return super.isSet(Constraint.PROPERTY.indexOwner.name());
	}

	/**
	 * Unsets the <b>indexOwner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getIndexOwner() getIndexOwner} or {@link #setIndexOwner(String value) setIndexOwner(...)} for a definition of property <b>indexOwner</b>
	 */
	public void unsetIndexOwner(){
		super.unset(Constraint.PROPERTY.indexOwner.name());
	}

	/**
	 * Returns the value of the <b>indexOwner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the user owning the index
	 * @return the value of the <b>indexOwner</b> property.
	 */
	public String getIndexOwner(){
		return (String)super.get(Constraint.PROPERTY.indexOwner.name());
	}

	/**
	 * Sets the value of the <b>indexOwner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the user owning the index
	 */
	public void setIndexOwner(String value){
		super.set(Constraint.PROPERTY.indexOwner.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(Constraint.PROPERTY.owner.name());
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
		super.unset(Constraint.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the constraint definition
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(Constraint.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of the constraint definition
	 */
	public void setOwner(String value){
		super.set(Constraint.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>refConstraintName</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getRefConstraintName() getRefConstraintName} or {@link #setRefConstraintName(String value) setRefConstraintName(...)} for a definition of property <b>refConstraintName</b>
	 * @return true if the <b>refConstraintName</b> property is set.
	 */
	public boolean isSetRefConstraintName(){
		return super.isSet(Constraint.PROPERTY.refConstraintName.name());
	}

	/**
	 * Unsets the <b>refConstraintName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getRefConstraintName() getRefConstraintName} or {@link #setRefConstraintName(String value) setRefConstraintName(...)} for a definition of property <b>refConstraintName</b>
	 */
	public void unsetRefConstraintName(){
		super.unset(Constraint.PROPERTY.refConstraintName.name());
	}

	/**
	 * Returns the value of the <b>refConstraintName</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the unique constraint definition for referenced 
	 * table
	 * @return the value of the <b>refConstraintName</b> property.
	 */
	public String getRefConstraintName(){
		return (String)super.get(Constraint.PROPERTY.refConstraintName.name());
	}

	/**
	 * Sets the value of the <b>refConstraintName</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Name of the unique constraint definition for referenced 
	 * table
	 */
	public void setRefConstraintName(String value){
		super.set(Constraint.PROPERTY.refConstraintName.name(), value);
	}


	/**
	 * Returns true if the <b>refOwner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getRefOwner() getRefOwner} or {@link #setRefOwner(String value) setRefOwner(...)} for a definition of property <b>refOwner</b>
	 * @return true if the <b>refOwner</b> property is set.
	 */
	public boolean isSetRefOwner(){
		return super.isSet(Constraint.PROPERTY.refOwner.name());
	}

	/**
	 * Unsets the <b>refOwner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getRefOwner() getRefOwner} or {@link #setRefOwner(String value) setRefOwner(...)} for a definition of property <b>refOwner</b>
	 */
	public void unsetRefOwner(){
		super.unset(Constraint.PROPERTY.refOwner.name());
	}

	/**
	 * Returns the value of the <b>refOwner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of table referred to in a referential constraint
	 * @return the value of the <b>refOwner</b> property.
	 */
	public String getRefOwner(){
		return (String)super.get(Constraint.PROPERTY.refOwner.name());
	}

	/**
	 * Sets the value of the <b>refOwner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Owner of table referred to in a referential constraint
	 */
	public void setRefOwner(String value){
		super.set(Constraint.PROPERTY.refOwner.name(), value);
	}


	/**
	 * Returns true if the <b>searchCondition</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getSearchCondition() getSearchCondition} or {@link #setSearchCondition(String value) setSearchCondition(...)} for a definition of property <b>searchCondition</b>
	 * @return true if the <b>searchCondition</b> property is set.
	 */
	public boolean isSetSearchCondition(){
		return super.isSet(Constraint.PROPERTY.searchCondition.name());
	}

	/**
	 * Unsets the <b>searchCondition</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getSearchCondition() getSearchCondition} or {@link #setSearchCondition(String value) setSearchCondition(...)} for a definition of property <b>searchCondition</b>
	 */
	public void unsetSearchCondition(){
		super.unset(Constraint.PROPERTY.searchCondition.name());
	}

	/**
	 * Returns the value of the <b>searchCondition</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Text of search condition for a check constraint
	 * @return the value of the <b>searchCondition</b> property.
	 */
	public String getSearchCondition(){
		return (String)super.get(Constraint.PROPERTY.searchCondition.name());
	}

	/**
	 * Sets the value of the <b>searchCondition</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * Text of search condition for a check constraint
	 */
	public void setSearchCondition(String value){
		super.set(Constraint.PROPERTY.searchCondition.name(), value);
	}


	/**
	 * Returns true if the <b>table</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTable() getTable} or {@link #setTable(Table value) setTable(...)} for a definition of property <b>table</b>
	 * @return true if the <b>table</b> property is set.
	 */
	public boolean isSetTable(){
		return super.isSet(Constraint.PROPERTY.table.name());
	}

	/**
	 * Unsets the <b>table</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTable() getTable} or {@link #setTable(Table value) setTable(...)} for a definition of property <b>table</b>
	 */
	public void unsetTable(){
		super.unset(Constraint.PROPERTY.table.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property, <b>table</b>.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getTable() getTable} or {@link #setTable(Table value) setTable(...)} for a definition of property <b>table</b>
	 * @return a new instance of Type {@link Table} automatically establishing a containment relationship through the object's reference property <b>table</b>.
	 */
	public Table createTable(){
		return (Table)super.createDataObject(Constraint.PROPERTY.table.name());
	}

	/**
	 * Returns the value of the <b>table</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The reference to the owning table
	 * @return the value of the <b>table</b> property.
	 */
	public Table getTable(){
		return (Table)super.get(Constraint.PROPERTY.table.name());
	}

	/**
	 * Sets the value of the <b>table</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The reference to the owning table
	 */
	public void setTable(Table value){
		super.set(Constraint.PROPERTY.table.name(), value);
	}
}