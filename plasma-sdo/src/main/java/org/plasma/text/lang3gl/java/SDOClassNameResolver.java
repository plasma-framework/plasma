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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.text.lang3gl.ClassNameResolver;

public class SDOClassNameResolver extends DefaultNameResolver
    implements ClassNameResolver 
{
    private static Log log =LogFactory.getLog(
    		SDOClassNameResolver.class); 

	@Override
	public String getName(Class clss) {		
		String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clss.getUri(), clss.getName());
		return replaceReservedCharacters(name);
	}
	
	@Override
	public String getQualifiedName(Class clss) {		
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(clss.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clss.getUri(), clss.getName());
		String qualifiedName = packageName + "." 
		    + replaceReservedCharacters(name); 				
		
		return qualifiedName;
	}

	@Override
	public String getQualifiedName(ClassRef clssRef) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(clssRef.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clssRef.getUri(), clssRef.getName());
		String qualifiedName = packageName + "." 
		    + replaceReservedCharacters(name); 				
		return qualifiedName;
	}


}
