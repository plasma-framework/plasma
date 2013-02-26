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

import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.text.lang3gl.ClassNameResolver;

public class DSLClassNameResolver  extends DefaultNameResolver
    implements ClassNameResolver {

	@Override
	public String getQualifiedName(Class clss) {		
		return getQualifiedName(clss.getUri(), clss.getName());
	}

	@Override
	public String getQualifiedName(ClassRef clssRef) {
		return getQualifiedName(clssRef.getUri(), clssRef.getName());
	}
	
	private String getQualifiedName(String uri, String name) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(uri);
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String subpackage = PlasmaConfig.getInstance().getSDO().getGlobalProvisioning().getQueryDSL().getImplementation().getChildPackageName();		
		if (subpackage != null && subpackage.trim().length() > 0)
			packageName = packageName + "." + subpackage;
		String className = PlasmaConfig.getInstance().getQueryDSLImplementationClassName(uri, name);
		className = this.replaceReservedCharacters(className);
		String qualifiedName = packageName + "." + className; 				
		return qualifiedName;
	}

	@Override
	public String getName(Class clss) {
		String className = PlasmaConfig.getInstance().getQueryDSLImplementationClassName(clss.getUri(), clss.getName());
		className = this.replaceReservedCharacters(className);
		return className;
	}

}
