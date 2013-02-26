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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.plasma.config.InterfaceProvisioning;
import org.plasma.config.PlasmaConfig;
import org.plasma.config.PropertyNameStyle;
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
	protected InterfaceProvisioning interfaceProvisioning;
	protected ClassNameResolver interfaceResolver = new SDOInterfaceNameResolver();
	protected ClassNameResolver classResolver = new SDOClassNameResolver();
	
	public SDODefaultFactory(Lang3GLContext context) {
		super(context);
		for (String name : SDO_RESERVED_NAMES)
			this.reservedGetterNameMap.put(name, name);
		
		this.interfaceProvisioning = PlasmaConfig.getInstance().getSDO().getGlobalProvisioning().getInterface();
		if (interfaceProvisioning == null) {
			interfaceProvisioning = new InterfaceProvisioning();
			interfaceProvisioning.setPropertyNameStyle(PropertyNameStyle.ENUMS);
		}
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
	
	protected String toQualifiedPropertyNameReference(Class clss, Property field) {
		StringBuilder buf = new StringBuilder();
		switch (this.interfaceProvisioning.getPropertyNameStyle()) {
		case ENUMS:
			buf.append(this.interfaceResolver.getName(clss));
			buf.append(".PROPERTY.");
			buf.append(field.getName());
			buf.append(".name()");
			break;
		case CONSTANTS:
			buf.append(this.interfaceResolver.getName(clss));
			buf.append(".");
			buf.append(toConstantName(field.getName()));
			break;
		case NONE:
		default:	
			buf.append("\"");
			buf.append(field.getName());
			buf.append("\"");
			break;
		}
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
