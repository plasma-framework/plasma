package org.plasma.text.lang3gl.java;

import java.util.Map;
import java.util.TreeMap;

import org.plasma.config.DataAccessProviderName;
import org.plasma.config.NamespaceProvisioning;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.DataTypeRef;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Property;
import org.plasma.provisioning.TypeRef;
import org.plasma.sdo.DataType;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.ClassNameResolver;

public abstract class JDODefaultFactory extends DefaultFactory {
	
	public JDODefaultFactory(Lang3GLContext context) {
		super(context);
	}
	
	public String createBaseDirectoryName(Package pkg)
	{
		String packageName = PlasmaConfig.getInstance().getServiceImplementationPackageName(
				DataAccessProviderName.JDO, pkg.getUri());
		String packageDir = packageName.replace(".", "/");
		StringBuilder buf = new StringBuilder();
		buf.append(packageDir);
		return buf.toString();
	}
	
	public String createDirectoryName(Package pkg) {
		String packageName = PlasmaConfig.getInstance().getServiceImplementationPackageName(
				DataAccessProviderName.JDO, pkg.getUri());
		String packageDir = packageName.replace(".", "/");
		StringBuilder buf = new StringBuilder();
		buf.append(packageDir);
		return buf.toString();
	}
		
	protected String createClassReferenceImportDeclarations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		// for interfaces we extend our superclasses, so need to reference them
		// FIXME: only 1 level though
		ClassNameResolver resolver = new JDOClassNameResolver();
		Map<String, String> nameMap = new TreeMap<String, String>();
		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0)		
		    this.collectSuperclassNames(pkg, clss, nameMap, resolver);
		
		//for interfaces we have definitions for all methods generated
		// based on local fields, not fields from superclasses
		collectDataClassNames(pkg, clss, nameMap, resolver);		
		collectReferenceClassNames(pkg, clss, nameMap, resolver);
		
		for (String name : nameMap.values()) {
			if (name.startsWith("java.lang."))
				continue;
		    buf.append(LINE_SEP);	
		    buf.append("import ");
		    buf.append(name);
		    buf.append(";");
		}
		
		return buf.toString();		
	}
	
	protected String createInterfaceReferenceImportDeclarations(Package pkg, Class clss)
	{
		StringBuilder buf = new StringBuilder();
		// for interfaces we extend our superclasses, so need to reference them
		// FIXME: only 1 level though
		ClassNameResolver resolver = new JDOInterfaceNameResolver();
		Map<String, String> nameMap = new TreeMap<String, String>();
		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0)		
		    this.collectSuperclassNames(pkg, clss, nameMap, resolver);
		
		//for interfaces we have definitions for all methods generated
		// based on local fields, not fields from superclasses
		collectDataClassNames(pkg, clss, nameMap, resolver);		
		collectReferenceClassNames(pkg, clss, nameMap, resolver);
		
		for (String name : nameMap.values()) {
			if (name.startsWith("java.lang."))
				continue;
		    buf.append(LINE_SEP);	
		    buf.append("import ");
		    buf.append(name);
		    buf.append(";");
		}
		
		return buf.toString();		
	}
	
	protected String createPackageName(Package pkg) {
		NamespaceProvisioning provisioning = PlasmaConfig.getInstance().getProvisioningByNamespaceURI(DataAccessProviderName.JDO, pkg.getUri());
		String packageName = provisioning.getPackageName();
		StringBuilder buf = new StringBuilder();
		buf.append(packageName);
		return buf.toString();
	}
		
	protected String createPackageDeclaration(Package pkg) {
		StringBuilder buf = new StringBuilder();
		buf.append("package " + createPackageName(pkg));
		buf.append(";");
		return buf.toString();
	}

	/**
	 * Collects class names from reference fields within this class. 
	 * @param pkg the package
	 * @param clss the Class
	 * @param nameMap the name map
	 */
	protected void collectClassNames(Package pkg, Class clss, 
			Map<String, String> nameMap, boolean collectAbstractClasses) {
		
		for (Property field : clss.getProperties())
		{
			TypeRef type = field.getType();
			if (type instanceof DataTypeRef) {
				DataType sdoType = DataType.valueOf(((DataTypeRef)type).getName());
				java.lang.Class<?> typeClass = this.getTypeClass(sdoType);
				if (typeClass.isPrimitive())
					continue;
				nameMap.put(typeClass.getName(), typeClass.getName());
			}
			else if (type instanceof ClassRef) {
				ClassRef cref = ((ClassRef)type);
				Class refClass = this.getContext().findClass(cref);
				if (refClass.isAbstract() && !collectAbstractClasses)
					continue;
				String className = PlasmaConfig.getInstance().getServiceImplementationClassName(
						DataAccessProviderName.JDO, cref.getUri(), cref.getName());
				String packageName = PlasmaConfig.getInstance().getServiceImplementationPackageName(
						DataAccessProviderName.JDO, cref.getUri());
				String qualifiedName = packageName + "." + className; 				
				nameMap.put(qualifiedName, qualifiedName);
			}
		}
	}
	
	protected void collectClassNamesDeep(Package pkg, Class clss, 
			Map<String, String> nameMap) {
		collectClassNamesDeep(pkg, clss, nameMap, true);
	}
	
	/**
	 * Collects class names from reference fields within this class and its superclass ancestry. 
	 * @param pkg the package
	 * @param clss the Class
	 * @param nameMap the name map
	 */
	protected void collectClassNamesDeep(Package pkg, Class clss, Map<String, String> nameMap, 
			boolean collectAbstractClasses) {
		
		collectClassNames(pkg, clss, nameMap, collectAbstractClasses);

		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
			for (ClassRef cref : clss.getSuperClasses()) {
		        Class superClass = this.context.findClass(cref);
		        Package superClassPackage = this.context.findPackage(cref);
		        // recurse
		        collectClassNamesDeep(superClassPackage, superClass, nameMap, collectAbstractClasses);
		    }	
		}
	}
	
	protected void collectSuperclassNames(Package pkg, Class clss, Map<String, String> nameMap) {
		
		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
			for (ClassRef cref : clss.getSuperClasses()) {
				String className = PlasmaConfig.getInstance().getServiceImplementationClassName(
						DataAccessProviderName.JDO, cref.getUri(), cref.getName());
				String packageName = PlasmaConfig.getInstance().getServiceImplementationPackageName(
						DataAccessProviderName.JDO, cref.getUri());
				String qualifiedName = packageName + "." + className; 				
				nameMap.put(qualifiedName, qualifiedName);
		        Class superClass = this.context.findClass(cref);
		        Package superClassPackage = this.context.findPackage(cref);
		        // recurse
		        collectSuperclassNames(superClassPackage, superClass, nameMap);
		    }	
		}
	}
	

}
