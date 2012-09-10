package org.plasma.text.lang3gl;

import java.util.HashMap;
import java.util.Map;

import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Model;

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
