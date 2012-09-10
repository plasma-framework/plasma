package org.plasma.text.lang3gl;

import org.plasma.provisioning.Package;
import org.plasma.sdo.DataType;

public interface Lang3GLContentFactory {
	/**
	 * Returns the language context for this factory. 
	 * @return the language context for this factory.
	 */
	public Lang3GLContext getContext();
	
    /**
     * Returns a 3Gl language specific class for the given SDO data-type (as 
     * per the SDO Specification 2.10 Section 8.1) where primitive type names or
     * wrapper type names returned based on the current context. 
     * @see Lang3GLModelContext
     * @see DataType
     * @param dataType the SDO datatype
     * @return the 3Gl language specific type class.
     */
	public java.lang.Class<?> getTypeClass(DataType dataType);
	
	/**
	 * Returns an 3Gl language specific base directory name for the given Package based on
	 * configuration settings.
	 * @see Package
	 * @param pkg the package
	 * @return an 3Gl language specific directory name for the given Package based on
	 * configuration settings
	 */
	public String createBaseDirectoryName(Package pkg);
	
	/**
	 * Returns an 3Gl language specific directory name for the given Package based on
	 * configuration settings.
	 * @see Package
	 * @param pkg the package
	 * @return an 3Gl language specific directory name for the given Package based on
	 * configuration settings
	 */
	public String createDirectoryName(Package pkg);
	
	
	
}
