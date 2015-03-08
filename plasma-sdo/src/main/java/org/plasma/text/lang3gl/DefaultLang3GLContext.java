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

import java.util.HashMap;
import java.util.Map;

import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.Package;

public class DefaultLang3GLContext implements Lang3GLContext {

	private Map<String, Class> classMap = new HashMap<String, Class>();
	private Map<String, Package> packageMap = new HashMap<String, Package>();
	
	@SuppressWarnings("unused")
	private DefaultLang3GLContext() {}
	
	public DefaultLang3GLContext(Model packages) {
		for (Package pkg : packages.getPackages()) {
			for (Class cls : pkg.getClazzs()) {
				String key = pkg.getUri() + "#" + cls.getName();
				classMap.put(key, cls);
				packageMap.put(key, pkg);
			}
		}
	}

	public Class findClass(ClassRef cref) {
		String key = cref.getUri() + "#" + cref.getName();
		return classMap.get(key);
	}
	
	public Class findClass(String qualifiedName) {
		return classMap.get(qualifiedName);
	}
	
	public Package findPackage(ClassRef cref) {
		String key = cref.getUri() + "#" + cref.getName();
		return packageMap.get(key);
	}
	
	public Package findPackage(String qualifiedName) {
		return packageMap.get(qualifiedName);
	}

	public boolean usePrimitives() {
		return true;
	}
	
	public String getIndentationToken() {
		return "\t";
	}


}
