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
package org.plasma.text.lang3gl.java;

import org.plasma.text.NamingCollisionException;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Package;

public abstract class DefaultNameResolver {

	
	/**
	 * Replace characters in the given name not applicable as
	 * a Java class name. 
	 * @param name the original name
	 * @return the new name
	 */
	protected String replaceReservedCharacters(String name) {
		String result = name;
		result = result.replace('-', '_');
		result = result.replace('+', '_');
		result = result.replace('/', '_');
		result = result.replace('\\', '_');
		result = result.replace('!', '_');
		result = result.replace('@', '_');
		result = result.replace(' ', '_');
		return result;
	}
	
	
	protected void checkUnresolvableNameCollision(String qualifiedName, Class clss, Package pkg) {
		String localName = null;
		if (pkg.getAlias() != null && pkg.getAlias().getLocalName() != null && pkg.getAlias().getLocalName().length() > 0)
			localName = pkg.getAlias().getLocalName();
		if (clss.getAlias() != null && clss.getAlias().getLocalName() != null && clss.getAlias().getLocalName().length() > 0)
			localName = localName + "." + clss.getAlias().getLocalName(); 		
		if (localName != null && localName.equals(qualifiedName))
			throw new NamingCollisionException("unresolvable name collision - identical local source and provisioning target classes ("
				+ qualifiedName + ") - please adjust the class or package, source or provisioning target name(s)");
	}
}
