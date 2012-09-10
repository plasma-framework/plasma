package org.plasma.text.lang3gl.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.DataTypeRef;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Property;
import org.plasma.provisioning.TypeRef;
import org.plasma.provisioning.adapter.FieldAdapter;
import org.plasma.sdo.DataType;
import org.plasma.sdo.core.CoreDataObject;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.ClassNameResolver;


public class SDOClassFactory extends SDODefaultFactory 
    implements ClassFactory {

	public SDOClassFactory(Lang3GLContext context) {
		super(context);
	}
	
	public String createFileName(Class clss) {
		StringBuilder buf = new StringBuilder();
		String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clss.getUri(), clss.getName());
		buf.append(name);
		buf.append(".java");		
		return buf.toString();
	}

	public String createContent(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		buf.append(this.createPackageDeclaration(pkg));
		buf.append(LINE_SEP);
		buf.append(this.createThirdPartyImportDeclarations(pkg, clss));
				
		ClassNameResolver resolver = new SDOInterfaceNameResolver();
		Map<String, String> importMap = this.createFieldImportMap(pkg, clss, resolver);
		collectSuperClassNames(pkg, clss, importMap, resolver);
		
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(pkg.getUri());
		String interfacePackageName = sdoNamespace.getProvisioning().getPackageName();
        String packageName = this.createPackageName(pkg);		
		
		// if impl class is in different package, need import
		if (!packageName.equals(interfacePackageName)) {
			String qualifiedName = interfacePackageName + "." + clss.getName(); 
			importMap.put(qualifiedName, qualifiedName);
		}
		buf.append(this.createImportDeclarations(importMap));
		
		buf.append(LINE_SEP);
		buf.append(LINE_SEP);
		buf.append(this.createTypeDeclaration(pkg, clss));
		buf.append(LINE_SEP);
		buf.append(this.beginBody());
		
		buf.append(LINE_SEP);
		buf.append(this.createStaticFieldDeclarations(clss));    			

		buf.append(LINE_SEP);
		buf.append(this.createConstructors(pkg, clss));    			

		for (Property field : clss.getProperties()) {
			buf.append(this.createPrivateFieldDeclaration(clss, field));
		}
		
		Map<String, FieldAdapter> fields = new TreeMap<String, FieldAdapter>();
		collectProvisioningFields(pkg, clss, pkg, clss, fields);
		Iterator<String> fieldIter = fields.keySet().iterator();
		while (fieldIter.hasNext()) {
			String name = fieldIter.next();
			FieldAdapter adapter = fields.get(name);
			buf.append(LINE_SEP);
			buf.append(this.createOperations(adapter.getFieldPackage(), 
					adapter.getFieldClass(), adapter.getField()));
		}

		buf.append(LINE_SEP);
		buf.append(this.endBody());
		return buf.toString();
	}
	
	
	protected String createConstructors(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("/**");
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(" * Default No-arg constructor required for serialization operations. This method");
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(" * is NOT intended to be used within application source code.");
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(" */");
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public ");
		buf.append(getImplementationClassName(clss));
		buf.append("() {");
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("super();");
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("}");

		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public ");
		buf.append(getImplementationClassName(clss));
		buf.append("(");
		buf.append(commonj.sdo.Type.class.getName());
		buf.append(" type) {");
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("super(type);"); 
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("}");		
		
	    return buf.toString();
    }
	
	protected String createOperations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		if (clss.getSuperClasses() != null)
		    for (ClassRef cref : clss.getSuperClasses()) {
			   Class sclss = this.context.findClass(cref);
			   for (Property field : sclss.getProperties()) {
				   String sclassOpers = createOperations(pkg, sclss, field);
				   buf.append(sclassOpers);
			   }
			   Package spkg = this.context.findPackage(cref);
			   buf.append(createOperations(spkg, sclss));
		    }
		return buf.toString();
	}

	protected String createOperations(Package pkg, Class clss, Property field) {
		
		StringBuilder buf = new StringBuilder();
		String typeClassName = getTypeClassName(field.getType());
		
		buf.append(LINE_SEP);			    
		createIsSet(pkg, clss, field, typeClassName, buf);			

		buf.append(LINE_SEP);			    
		createUnsetter(pkg, clss, field, typeClassName, buf);			
		
		if (field.getType() instanceof ClassRef) {
			buf.append(LINE_SEP);			    
			createCreator(pkg, clss, field, typeClassName, buf);			
		}
		if (!field.isMany()) {
			buf.append(LINE_SEP);			    
			createSingularGetter(pkg, clss, field, typeClassName, buf);
			buf.append(LINE_SEP);			    
			createSingularSetter(pkg, clss, field, typeClassName, buf);
		}
		else {
			buf.append(LINE_SEP);			    
			createManyGetter(pkg, clss, field, typeClassName, buf);
			buf.append(LINE_SEP);			    
			createManyIndexGetter(pkg, clss, field, typeClassName, buf);
			buf.append(LINE_SEP);			    
			createManyCount(pkg, clss, field, typeClassName, buf);
			buf.append(LINE_SEP);			    
			createManySetter(pkg, clss, field, typeClassName, buf);
			buf.append(LINE_SEP);			    
			createManyAdder(pkg, clss, field, typeClassName, buf);
			buf.append(LINE_SEP);			    
			createManyRemover(pkg, clss, field, typeClassName, buf);
		}
		

	    return buf.toString();
	}	
	
	private void createSingularGetter(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		
		createSingularGetterDeclaration(pkg, clss, field, typeClassName, buf);
		createSingularGetterBody(pkg, clss, field, typeClassName, buf);
	}

	private void createSingularGetterBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {	
		
		buf.append(this.beginBody());
		buf.append(LINE_SEP);			    
		buf.append(indent(2));		
		
		TypeRef typeRef = field.getType();
		if (typeRef instanceof DataTypeRef) {
			DataType sdoType = DataType.valueOf(typeRef.getName());
			java.lang.Class<?> typeClass = this.getTypeClass(sdoType);
			// first check null so autoboxing won't break
			if (typeClass.isPrimitive() && this.context.usePrimitives()) {
				java.lang.Class<?> objectPrimitiveClass = this.getTypeClass(sdoType, false);
				buf.append(objectPrimitiveClass.getSimpleName());
				buf.append(" result = (");
				buf.append(objectPrimitiveClass.getSimpleName());
				buf.append(")");
				buf.append("super.get(");
				buf.append(clss.getName());
				buf.append(".PTY_");
				buf.append(toConstantName(field.getName()));
				buf.append(");");			
				buf.append(LINE_SEP);			    
				buf.append(indent(2));		
				buf.append("if (result != null)");
				buf.append(LINE_SEP);			    
				buf.append(indent(3));
				buf.append("return result.");
				buf.append(typeClass.getSimpleName());
				buf.append("Value();");
				buf.append(LINE_SEP);			    
				buf.append(indent(2));
				buf.append("else return ");
				buf.append(getPrimitiveTypeDefault(sdoType));
				buf.append(";");				
			}
			else { // just cast it and return
				buf.append("return (");
				buf.append(typeClassName);
				buf.append(")");
				buf.append("super.get(");
				buf.append(clss.getName());
				buf.append(".PTY_");
				buf.append(toConstantName(field.getName()));
				buf.append(");");			
			}
		} else {
			buf.append("return (");
			buf.append(typeClassName);
			buf.append(")");
			buf.append("super.get(");
			buf.append(clss.getName());
			buf.append(".PTY_");
			buf.append(toConstantName(field.getName()));
			buf.append(");");		
		}
				
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());		
	}
	
	private void createSingularSetter(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		createSingularSetterDeclaration(pkg, clss, field, typeClassName, buf);
		createSingularSetterBody(pkg, clss, field, typeClassName, buf);
	}

	private void createSingularSetterBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		buf.append(this.beginBody());
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
	    buf.append("super.set(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(", value);");				
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());		
	}
	
	private void createUnsetter(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		createUnsetterDeclaration(pkg, clss, field, typeClassName, buf);
		createUnsetterBody(pkg, clss, field, typeClassName, buf);
	}	

	private void createUnsetterBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		buf.append(this.beginBody());
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
        buf.append("super.unset(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(");");				
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());		
	}	
	
	private void createIsSet(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		createIsSetDeclaration(pkg, clss, field, typeClassName, buf);
		createIsSetBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createIsSetBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		buf.append(this.beginBody());
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
        buf.append("super.isSet(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(");");				
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());		
	}	

	private void createCreator(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		createCreatorDeclaration(pkg, clss, field, typeClassName, buf);
		createCreatorBody(pkg, clss, field, typeClassName, buf);
	}	

	private void createCreatorBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		buf.append(this.beginBody());
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("return (");
		buf.append(typeClassName);
        buf.append(")super.createDataObject(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(");");				
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());		
	}	
	
	private void createManyGetter(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{	
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyGetterDeclaration(pkg, clss, field, typeClassName, buf);
		createManyGetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyGetterBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("List<");
		buf.append(typeClassName);
		buf.append("> list = (List<");
		buf.append(typeClassName);
		buf.append(">)super.get(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(");");	
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("if (list != null) {");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append(typeClassName);
		buf.append("[] array = new ");
		buf.append(typeClassName);
		buf.append("[list.size()];");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("list.toArray(array);");

		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("return array;");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("}");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("else");

		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("return new ");
		buf.append(typeClassName);
		buf.append("[0];");

		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}

	private void createManyIndexGetter(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{	
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyIndexGetterDeclaration(pkg, clss, field, typeClassName, buf);
		createManyIndexGetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyIndexGetterBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("List<");
		buf.append(typeClassName);
		buf.append("> list = (List<");
		buf.append(typeClassName);
		buf.append(">)super.get(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(");");	
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("if (list != null) {");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("return (");
		buf.append(typeClassName);
		buf.append(")list.get(idx);");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("}");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("else");

		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("return null;");

		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}

	private void createManyCount(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{	
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyCountDeclaration(pkg, clss, field, typeClassName, buf);
		createManyCountBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyCountBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("List<");
		buf.append(typeClassName);
		buf.append("> list = (List<");
		buf.append(typeClassName);
		buf.append(">)super.get(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(");");	
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("if (list != null) {");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("return list.size();");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("}");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("else");

		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("return 0;");

		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}
	
	private void createManySetter(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManySetterDeclaration(pkg, clss, field, typeClassName, buf);
		createManySetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManySetterBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("List<");
		buf.append(typeClassName);
		buf.append("> list = (List<");
		buf.append(typeClassName);
		buf.append(">)super.get(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(");");	
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("if (list != null) {");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append(typeClassName);
		buf.append("[] array = new ");
		buf.append(typeClassName);
		buf.append("[list.size()];");
		buf.append(" //Clear/replace existing list as per SDO 2.1 spec - See DataObject.set(Property property, Object value)");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("for (int i = 0; i < array.length; i++)");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(4));
		buf.append("list.add(array[i]);");

		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("super.set(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(", list);");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("}");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("else");

		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("throw new IllegalArgumentException(\"expected non-null argument 'array' - use unset");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("() method to remove this property\");");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}
	
	private void createManyAdder(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyAdderDeclaration(pkg, clss, field, typeClassName, buf);
		createManyAdderBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyAdderBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("List<");
		buf.append(typeClassName);
		buf.append("> list = (List<");
		buf.append(typeClassName);
		buf.append(">)super.get(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(");");	
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("if (list == null)");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("list = new ArrayList<");
		buf.append(typeClassName);
		buf.append(">();");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(4));
		buf.append("list.add(value);");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation");
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("super.setList(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(", list);");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}

	private void createManyRemover(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyRemoverDeclaration(pkg, clss, field, typeClassName, buf);
		createManyRemoverBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyRemoverBody(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("List<");
		buf.append(typeClassName);
		buf.append("> list = (List<");
		buf.append(typeClassName);
		buf.append(">)super.get(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(");");	
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("if (list != null)");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(4));
		buf.append("list.remove(value);");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation");
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("super.setList(");
		buf.append(clss.getName());
		buf.append(".PTY_");
		buf.append(toConstantName(field.getName()));
		buf.append(", list);");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}

	
	public String createDirectoryName(Package pkg) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(pkg.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String subpackage = PlasmaConfig.getInstance().getSDO().getGlobalProvisioning().getImplementation().getChildPackageName();		
		if (subpackage != null && subpackage.trim().length() > 0)
			packageName = packageName + "." + subpackage;			
		String packageDir = packageName.replace(".", "/");
		StringBuilder buf = new StringBuilder();
		buf.append(packageDir);
		return buf.toString();
	}

	protected String createPackageName(Package pkg) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(pkg.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String subpackage = PlasmaConfig.getInstance().getSDO().getGlobalProvisioning().getImplementation().getChildPackageName();		
		if (subpackage != null && subpackage.trim().length() > 0)
			packageName = packageName + "." + subpackage;
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

	protected String createStaticFieldDeclarations(Class clss) {
		StringBuilder buf = new StringBuilder();
		
		buf.append(this.getContext().getIndentationToken());
		buf.append("private static final long serialVersionUID = 1L;");
	    
		// FIXME: a config option for what to log??
		//@SuppressWarnings("unused")
		//buf.append(LINE_SEP);			    
		//buf.append(this.getContext().getIndentationToken());
		//buf.append("private static Log log = LogFactory.getFactory().getInstance(");
		//buf.append(getImplementationClassName(clss));
		//buf.append(".class);");
	    
		buf.append(LINE_SEP);
		buf.append(this.getContext().getIndentationToken());
		buf.append("/** The SDO namespace URI associated with the SDO Type for this class */");
		buf.append(LINE_SEP);
		buf.append(this.getContext().getIndentationToken());
		buf.append("public static final String NAMESPACE_URI = \"");
		buf.append(clss.getUri());
		buf.append("\";");

		return buf.toString();
	}	
	
	protected String createTypeDeclaration(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();

		SDOInterfaceNameResolver interfaceResolver = new SDOInterfaceNameResolver();
		buf.append("public class ");
		buf.append(getImplementationClassName(clss));
		buf.append(" extends ");
		buf.append(CoreDataObject.class.getSimpleName());
		buf.append(" implements Serializable, ");
		buf.append(interfaceResolver.getName(clss));		
		
		return buf.toString();
	}

	protected String createPrivateFieldDeclaration(Class clss, Property field) {
		return ""; // no fixed fields, methods delegate to SDO DataObject impl class structures for data accces
	}

	protected String createThirdPartyImportDeclarations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		//buf.append(LINE_SEP);
		//buf.append(this.createImportDeclaration(pkg, clss, LogFactory.class.getName()));
		//buf.append(LINE_SEP);
		//buf.append(this.createImportDeclaration(pkg, clss, Log.class.getName()));
		//buf.append(LINE_SEP);
		//buf.append(this.createImportDeclaration(pkg, clss, commonj.sdo.Type.class.getName()));
		buf.append(LINE_SEP);
		buf.append(this.createImportDeclaration(pkg, clss, Serializable.class.getName()));
		buf.append(LINE_SEP);
		buf.append(this.createImportDeclaration(pkg, clss, CoreDataObject.class.getName()));
		
		if (!hasOnlySingilarFieldsDeep(clss)) {
			buf.append(LINE_SEP);
			buf.append(this.createImportDeclaration(pkg, clss, List.class.getName()));
			buf.append(LINE_SEP);
			buf.append(this.createImportDeclaration(pkg, clss, ArrayList.class.getName()));
		}
		return buf.toString();
	}

}
