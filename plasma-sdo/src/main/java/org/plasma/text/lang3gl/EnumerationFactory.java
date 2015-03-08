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

import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.Package;

public interface EnumerationFactory extends Lang3GLContentFactory {
	
	/**
	 * Returns an 3Gl language specific file name for the given Enumeration based on
	 * configuration settings.
	 * @see Enumeration
	 * @param clss the Enumeration
	 * @return an 3Gl language specific file name for the given Enumeration based on
	 * configuration settings
	 */
	public String createFileName(Enumeration clss);

	/**
	 * Returns an 3Gl language specific content for the given Package and Type
	 * @param pkg the Package
	 * @param type the Type
	 * @return the content
	 */
	public String createContent(Package pkg, Enumeration type); 
	
}
