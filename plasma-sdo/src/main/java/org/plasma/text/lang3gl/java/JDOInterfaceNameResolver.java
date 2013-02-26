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

import org.plasma.config.DataAccessProviderName;
import org.plasma.config.Namespace;
import org.plasma.config.NamespaceProvisioning;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.text.lang3gl.ClassNameResolver;

public class JDOInterfaceNameResolver extends DefaultNameResolver implements ClassNameResolver {

	public JDOInterfaceNameResolver() {}
	
	@Override
	public String getName(Class clss) {		
		return clss.getName();
	}

	@Override
	public String getQualifiedName(Class clss) {		
		NamespaceProvisioning provisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(
				DataAccessProviderName.JDO, clss.getUri());
		String packageName = provisioning.getPackageName();
		String name = this.replaceReservedCharacters(clss.getName());
		String qualifiedName = packageName + "." + name; 				
		return qualifiedName;
	}

	@Override
	public String getQualifiedName(ClassRef clssRef) {
		NamespaceProvisioning provisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(
				DataAccessProviderName.JDO, clssRef.getUri());
		String packageName = provisioning.getPackageName();
		String name = this.replaceReservedCharacters(clssRef.getName());
		String qualifiedName = packageName + "." + name; 				
		return qualifiedName;
	}

}
