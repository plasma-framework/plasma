package org.plasma.text.lang3gl.java;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.DataTypeRef;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Property;
import org.plasma.provisioning.TypeRef;
import org.plasma.sdo.DataType;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.ClassNameResolver;

public abstract class SDODefaultFactory extends DefaultFactory {
	
	protected static String[] SDO_RESERVED_NAMES = {
		"type",
		"dataobject",
		"sequence",
		"list"
	};
	
	private Map<String, String> reservedGetterNameMap =  new HashMap<String, String>();
	
	public SDODefaultFactory(Lang3GLContext context) {
		super(context);
		for (String name : SDO_RESERVED_NAMES)
			this.reservedGetterNameMap.put(name, name);
		
	}

	protected String createPackageDeclaration(Package pkg) {
		String packageName = PlasmaConfig.getInstance().getSDOInterfacePackageName(pkg.getUri());
		StringBuilder buf = new StringBuilder();
		buf.append("package " + packageName);
		buf.append(";");
		return buf.toString();
	}
	
	public String createBaseDirectoryName(Package pkg)
	{
		String packageName = PlasmaConfig.getInstance().getSDOInterfacePackageName(pkg.getUri());
		String packageDir = packageName.replace(".", "/");
		StringBuilder buf = new StringBuilder();
		buf.append(packageDir);
		return buf.toString();
	}
	
	public String createDirectoryName(Package pkg) {
		String packageName = PlasmaConfig.getInstance().getSDOInterfacePackageName(pkg.getUri());
		String packageDir = packageName.replace(".", "/");
		StringBuilder buf = new StringBuilder();
		buf.append(packageDir);
		return buf.toString();
	}
		
	protected String createSDOInterfaceReferenceImportDeclarations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		Map<String, String> nameMap = new TreeMap<String, String>();
		ClassNameResolver resolver = new SDOInterfaceNameResolver();
		collectSuperclassNames(pkg, clss, nameMap, resolver);
		for (String name : nameMap.values()) {
		    buf.append(LINE_SEP);	
		    buf.append("import ");
		    buf.append(name);
		    buf.append(";");
		}
		
		return buf.toString();
	}

	protected String getImplementationClassName(Class clss) {
		SDOClassNameResolver resolver = new SDOClassNameResolver();
		String name = resolver.getName(clss);
		return name;
	}	

	protected String toMethodFieldName(String name) {
		String result = firstToUpperCase(name);
		if (this.reservedGetterNameMap.get(name.toLowerCase()) != null)
			result += "_";
		return result;
	}
}
