package org.plasma.provisioning.rdb.oracle.sys.impl;

import java.io.Serializable;
import org.plasma.sdo.core.CoreDataObject;
import java.lang.String;
import org.plasma.provisioning.rdb.oracle.sys.Version;

/**
 * Generated implementation class representing the domain model entity <b>Version</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>v$VERSION</b>.
 * <p></p>
 *
 */
public class VersionImpl extends CoreDataObject implements Serializable, Version
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/sys";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public VersionImpl() {
		super();
	}
	public VersionImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>banner</b> property is set.
	 * @return true if the <b>banner</b> property is set.
	 */
	public boolean isSetBanner(){
		return super.isSet(Version.PROPERTY.banner.name());
	}

	/**
	 * Unsets the <b>banner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetBanner(){
		super.unset(Version.PROPERTY.banner.name());
	}

	/**
	 * Returns the value of the <b>banner</b> property.
	 * @return the value of the <b>banner</b> property.
	 */
	public String getBanner(){
		return (String)super.get(Version.PROPERTY.banner.name());
	}

	/**
	 * Sets the value of the <b>banner</b> property to the given value.
	 */
	public void setBanner(String value){
		super.set(Version.PROPERTY.banner.name(), value);
	}
}