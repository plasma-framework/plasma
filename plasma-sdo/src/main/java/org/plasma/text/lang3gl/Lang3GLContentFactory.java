/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.text.lang3gl;

import org.plasma.metamodel.Package;
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
