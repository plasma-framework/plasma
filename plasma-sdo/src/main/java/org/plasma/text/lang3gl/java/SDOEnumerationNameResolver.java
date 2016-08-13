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
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Package;
import org.plasma.text.lang3gl.ClassNameResolver;

public class SDOEnumerationNameResolver extends DefaultNameResolver
    implements ClassNameResolver 
{
    private static Log log =LogFactory.getLog(
    		SDOEnumerationNameResolver.class); 

	@Override
	public String getName(Class clss) {
		if (clss.getAlias() != null && clss.getAlias().getLocalName() != null)
		    return clss.getAlias().getLocalName();
		else
			return clss.getName();
	}
	
	@Override
	public String getQualifiedName(Class clss, Package pkg) {		
		String className = null; 				
		if (clss.getAlias() != null && clss.getAlias().getLocalName() != null)
			className = clss.getAlias().getLocalName();
		else
			className = clss.getName();

		String pkgName = null; 				
		if (pkg.getAlias() != null && pkg.getAlias().getLocalName() != null) {
			pkgName = pkg.getAlias().getLocalName();
		}
		else {
			pkgName = pkg.getName();
		}
		StringBuilder qualifiedName = new StringBuilder();
		qualifiedName.append(pkgName);
		qualifiedName.append(".");
		qualifiedName.append(className);
		String result = qualifiedName.toString();
		return result;
	}

	@Override
	public String getQualifiedName(ClassRef clssRef) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(clssRef.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		
		StringBuilder qualifiedName = new StringBuilder();
		qualifiedName.append(packageName);
		qualifiedName.append(".");
		qualifiedName.append(clssRef.getName());
		return qualifiedName.toString();
	}


}
