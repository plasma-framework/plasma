package org.plasma.provisioning.rdb.mysql.any.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.lang.String;
import org.plasma.provisioning.rdb.mysql.any.GlobalVariable;

/**
 * Generated implementation class representing the domain model entity <b>GlobalVariable</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>GLOBAL_VARIABLES</b>.
 * <p></p>
 *
 */
public class GlobalVariableImpl extends CoreDataObject implements Serializable, GlobalVariable
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/any";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public GlobalVariableImpl() {
		super();
	}
	public GlobalVariableImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>name</b> property is set.
	 * @return true if the <b>name</b> property is set.
	 */
	public boolean isSetName(){
		return super.isSet(GlobalVariable.PROPERTY.name.name());
	}

	/**
	 * Unsets the <b>name</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetName(){
		super.unset(GlobalVariable.PROPERTY.name.name());
	}

	/**
	 * Returns the value of the <b>name</b> property.
	 * @return the value of the <b>name</b> property.
	 */
	public String getName(){
		return (String)super.get(GlobalVariable.PROPERTY.name.name());
	}

	/**
	 * Sets the value of the <b>name</b> property to the given value.
	 */
	public void setName(String value){
		super.set(GlobalVariable.PROPERTY.name.name(), value);
	}


	/**
	 * Returns true if the <b>value</b> property is set.
	 * @return true if the <b>value</b> property is set.
	 */
	public boolean isSetValue(){
		return super.isSet(GlobalVariable.PROPERTY.value.name());
	}

	/**
	 * Unsets the <b>value</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetValue(){
		super.unset(GlobalVariable.PROPERTY.value.name());
	}

	/**
	 * Returns the value of the <b>value</b> property.
	 * @return the value of the <b>value</b> property.
	 */
	public String getValue(){
		return (String)super.get(GlobalVariable.PROPERTY.value.name());
	}

	/**
	 * Sets the value of the <b>value</b> property to the given value.
	 */
	public void setValue(String value){
		super.set(GlobalVariable.PROPERTY.value.name(), value);
	}
}