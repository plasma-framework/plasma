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

import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Package;

public interface ClassNameResolver {
	
	/**
	 * Returns an unqualified class name for the given provisioning model class.
	 * @param clss the provisioning class
	 * @return a unqualified class name
	 */
    public String getName(Class clss);

    /**
	 * Returns a qualified class name for the given provisioning model class.
	 * @param clss the provisioning class
	 * @param pkg the provisioning package
	 * @return a qualified class name
	 */
    public String getQualifiedName(Class clss, Package pkg);
    
	/**
	 * Returns a qualified class name for the given provisioning model class reference.
	 * @param clssRef the provisioning class reference
	 * @return a qualified class name
	 */
    public String getQualifiedName(ClassRef clssRef);
}
