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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.DataTypeRef;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;
import org.plasma.metamodel.TypeRef;
import org.plasma.provisioning.adapter.FieldAdapter;
import org.plasma.sdo.DataType;
import org.plasma.sdo.core.CoreDataObject;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.text.TextBuilder;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.ClassNameResolver;
import org.plasma.text.lang3gl.Lang3GLContext;

import commonj.sdo.Type;



public class SDOClassFactory extends SDODefaultFactory 
    implements ClassFactory {

	public SDOClassFactory(Lang3GLContext context) {
		super(context);
	}
	
	public String createFileName(Class clss, Package pkg) {
		TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
		SDOClassNameResolver resolver = new SDOClassNameResolver();
		String name = resolver.getName(clss);
		buf.append(name);
		buf.append(".java");		
		return buf.toString();
	}

	public String createContent(Package pkg, Class clss) {
		TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
		
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
			String qualifiedName = interfacePackageName + "." + resolver.getName(clss); 
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

		for (Property field : clss.getProperty()) {
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
	
	protected String createTypeDeclaration(Package pkg, Class clss) {
		TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());

		buf.append(createTypeDeclarationJavadoc(pkg, clss));		

		buf.append(newline(0));	
		buf.append("public class ");
		buf.append(getImplementationClassName(clss, pkg));
		buf.append(" extends ");
		buf.append(CoreDataObject.class.getSimpleName());
		buf.append(" implements Serializable, ");
		buf.append(this.interfaceResolver.getName(clss));		
		
		return buf.toString();
	}
	
	private String createTypeDeclarationJavadoc(Package pkg, Class clss) {
		TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
		
		buf.append("/**"); // begin javadoc
		
		// add formatted doc from UML if exists		
		// always put model definition first so it appears
		// on package summary line for class
		String docs = getWrappedDocmentations(clss.getDocumentation(), 0);
		if (docs.trim().length() > 0) {
		    buf.append(docs);
		    
		    // if we have model docs, set up the next section w/a "header"
		    buf.append(newline(0));	
			buf.append(" * <p></p>");
		}
		
	    buf.append(newline(0));	
		buf.append(" * Generated implementation class representing the domain model entity <b>");
		buf.append(clss.getName());
		buf.append("</b>."); 
		
		// data store mapping
		if (clss.getAlias() != null && clss.getAlias().getPhysicalName() != null) {
		    buf.append(newline(0));	
			buf.append(" *"); 
		    buf.append(newline(0));	
			buf.append(" * <p></p>");
		    buf.append(newline(0));	
			buf.append(" * <b>Data Store Mapping:</b>");
		    buf.append(newline(0));	
			buf.append(" * Corresponds to the physical data store entity <b>");
			buf.append(clss.getAlias().getPhysicalName());
			buf.append("</b>.");
		    buf.append(newline(0));	
			buf.append(" * <p></p>");		
		    buf.append(newline(0));	
			buf.append(" *"); 
		}		

	    buf.append(newline(0));	
		buf.append(" */"); // end javadoc
		
		return buf.toString();
	}
	
	protected String createStaticFieldDeclarations(Class clss) {
		TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
		
		buf.append(this.getContext().getIndentationToken());
		buf.append("private static final long serialVersionUID = 1L;");
	    
		// FIXME: a config option for what to log??
		//@SuppressWarnings("unused")
		//buf.append(LINE_SEP);			    
		//buf.append(this.getContext().getIndentationToken());
		//buf.append("private static Log log = LogFactory.getFactory().getInstance(");
		//buf.append(getImplementationClassName(clss));
		//buf.append(".class);");
	    
		buf.appendln(1, "/** The SDO namespace URI associated with the SDO Type for this class */");
		buf.appendln(1, "public static final String NAMESPACE_URI = \"");
		buf.append(clss.getUri());
		buf.append("\";");

		return buf.toString();
	}	
	

	protected String createPrivateFieldDeclaration(Class clss, Property field) {
		return ""; // no fixed fields, methods delegate to SDO DataObject impl class structures for data accces
	}
	
	protected String createConstructors(Package pkg, Class clss) {
		TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
		buf.appendln(1, "/**");
		buf.appendln(1, " * Default No-arg constructor required for serialization operations. This method");
		buf.appendln(1, " * is NOT intended to be used within application source code.");
		buf.appendln(1, " */");
		buf.appendln(1, "public ");
		buf.append(getImplementationClassName(clss, pkg));
		buf.append("() {");
		buf.appendln(2, "super();");
		buf.appendln(1, "}");

		buf.appendln(1, "public ");
		buf.append(getImplementationClassName(clss, pkg));
		buf.append("(");
		buf.append(commonj.sdo.Type.class.getName());
		buf.append(" type) {");
		buf.appendln(2, "super(type);"); 
		buf.appendln(1, "}");		
		
	    return buf.toString();
    }
	
	protected String createOperations(Package pkg, Class clss) {
		TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
		if (clss.getSuperClass() != null)
		    for (ClassRef cref : clss.getSuperClass()) {
			   Class sclss = this.context.findClass(cref);
			   for (Property field : sclss.getProperty()) {
				   String sclassOpers = createOperations(pkg, sclss, field);
				   buf.append(sclassOpers);
			   }
			   Package spkg = this.context.findPackage(cref);
			   buf.append(createOperations(spkg, sclss));
		    }
		return buf.toString();
	}

	protected String createOperations(Package pkg, Class clss, Property field) {
		
		TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
		MetaClassInfo typeClassName = getTypeClassName(field.getType());
		
		buf.append(LINE_SEP);			    
		createIsSet(pkg, clss, field, typeClassName, buf);			

		buf.append(LINE_SEP);			    
		createUnsetter(pkg, clss, field, typeClassName, buf);			
		
		if (field.getType() instanceof ClassRef) {
			Class targetClass = this.context.findClass((ClassRef)field.getType());
			if (!targetClass.isAbstract()) { 
			    buf.append(LINE_SEP);			    
			    createCreator(pkg, clss, field, typeClassName, buf);
			}
			else { 
			    buf.append(LINE_SEP);			    
			    createCreatorByAbstractClass(pkg, clss, field, typeClassName, buf);
			}
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
			MetaClassInfo typeClassName, TextBuilder buf) {
		
		createSingularGetterDeclaration(pkg, clss, field, typeClassName, buf);
		createSingularGetterBody(pkg, clss, field, typeClassName, buf);
	}

	private void createSingularGetterBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {	
		
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
			    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
				buf.append(");");			
				buf.appendln(2, "if (result != null)");
				buf.appendln(3, "return result.");
				buf.append(typeClass.getSimpleName());
				buf.append("Value();");
				buf.appendln(2, "else return ");
				buf.append(getPrimitiveTypeDefault(sdoType));
				buf.append(";");				
			}
			else { // just cast it and return
				buf.append("return (");
				buf.append(typeClassName.getSimpleName());
				buf.append(")");
				buf.append("super.get(");
			    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
				buf.append(");");			
			}
		} else {
			buf.append("return (");
			buf.append(typeClassName.getSimpleName());
			buf.append(")");
			buf.append("super.get(");
		    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
			buf.append(");");		
		}
				
		buf.appendln(1, this.endBody());		
	}
	
	private void createSingularSetter(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		createSingularSetterDeclaration(pkg, clss, field, typeClassName, buf);
		createSingularSetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createSingularSetterBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		buf.append(this.beginBody());
		buf.appendln(2, "super.set(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(", value);");				
		buf.appendln(1, this.endBody());		
	}
	
	private void createUnsetter(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		createUnsetterDeclaration(pkg, clss, field, typeClassName, buf);
		createUnsetterBody(pkg, clss, field, typeClassName, buf);
	}	

	private void createUnsetterBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		buf.append(this.beginBody());
		buf.appendln(2, "super.unset(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(");");				
		buf.appendln(1, this.endBody());		
	}	
	
	private void createIsSet(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		createIsSetDeclaration(pkg, clss, field, typeClassName, buf);
		createIsSetBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createIsSetBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		buf.append(this.beginBody());
		buf.appendln(2, "return super.isSet(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(");");				
		buf.appendln(1, this.endBody());		
	}	

	private void createCreator(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		createCreatorDeclaration(pkg, clss, field, typeClassName, buf);
		createCreatorBody(pkg, clss, field, typeClassName, buf);
	}	

	private void createCreatorBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		buf.append(this.beginBody());
		buf.appendln(2, "return (");
		buf.append(typeClassName.getSimpleName());
        buf.append(")super.createDataObject(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(");");				
		buf.appendln(1, this.endBody());		
	}	

	private void createCreatorByAbstractClass(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		createCreatorByAbstractClassDeclaration(pkg, clss, field, typeClassName, buf);
		createCreatorByAbstractClassBody(pkg, clss, field, typeClassName, buf);
	}	

	private void createCreatorByAbstractClassBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {

		buf.append(this.beginBody());
		
		buf.appendln(2, Type.class.getName());
		buf.append(" classType = ");
		buf.append(PlasmaTypeHelper.class.getName());
		buf.append(".INSTANCE.getType(clss);");		
		
		buf.appendln(2, "return (");
		buf.append(typeClassName.getSimpleName());
        buf.append(")super.createDataObject(this.getType().getProperty(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append("), classType);");				
		
		buf.appendln(1, this.endBody());		
	}	
	
	private void createManyGetter(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{	
		buf.appendln(1, "@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyGetterDeclaration(pkg, clss, field, typeClassName, buf);
		createManyGetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyGetterBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.appendln(2, "List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append("> list = (List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append(">)super.get(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(");");	
		
		buf.appendln(2, "if (list != null) {");
		
		buf.appendln(3, typeClassName.getSimpleName());
		buf.append("[] array = new ");
		buf.append(typeClassName.getSimpleName());
		buf.append("[list.size()];");
		
		buf.appendln(3, "for (int i = 0; i < list.size(); i++)");
		buf.appendln(4, "array[i] = list.get(i);");

		buf.appendln(3, "return array;");

		buf.appendln(2, "}");

		buf.appendln(2, "else");

		buf.appendln(3, "return new ");
		buf.append(typeClassName.getSimpleName());
		buf.append("[0];");

		buf.appendln(1, this.endBody());
	}

	private void createManyIndexGetter(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{	
		buf.appendln(1, "@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyIndexGetterDeclaration(pkg, clss, field, typeClassName, buf);
		createManyIndexGetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyIndexGetterBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.appendln(2, "List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append("> list = (List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append(">)super.get(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(");");	
		
		buf.appendln(2, "if (list != null) {");
		
		buf.appendln(3, "return (");
		buf.append(typeClassName.getSimpleName());
		buf.append(")list.get(idx);");

		buf.appendln(2, "}");

		buf.appendln(2, "else");

		buf.appendln(3, "throw new ArrayIndexOutOfBoundsException(idx);");

		buf.appendln(1, this.endBody());
	}

	private void createManyCount(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{	
		buf.appendln(1, "@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyCountDeclaration(pkg, clss, field, typeClassName, buf);
		createManyCountBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyCountBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.appendln(2, "List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append("> list = (List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append(">)super.get(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(");");	
		
		buf.appendln(2, "if (list != null) {");
		
		buf.appendln(3, "return list.size();");

		buf.appendln(2, "}");

		buf.appendln(2, "else");

		buf.appendln(3, "return 0;");

		buf.appendln(1, this.endBody());
	}
	
	private void createManySetter(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManySetterDeclaration(pkg, clss, field, typeClassName, buf);
		createManySetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManySetterBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.appendln(2, "List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append("> list = (List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append(">)super.get(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(");");	
		
		buf.appendln(2, "if (value != null && value.length > 0) {");
		
		buf.appendln(3, "if (list == null)");
		buf.appendln(4, "list = new ArrayList<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append(">();");
				
		buf.appendln(3, "for (int i = 0; i < value.length; i++)");
		
		buf.appendln(4, "list.add(value[i]);");

		buf.appendln(3, "super.set(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(", list);");
		
		buf.appendln(2, "}");

		buf.appendln(2, "else");

		buf.appendln(3, "throw new IllegalArgumentException(\"expected non-null and non-zero length array argument 'value' - use unset");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("() method to remove this property\");");
		
		buf.appendln(1, this.endBody());
	}
	
	private void createManyAdder(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.appendln(1, "@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyAdderDeclaration(pkg, clss, field, typeClassName, buf);
		createManyAdderBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyAdderBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.appendln(2, "List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append("> list = (List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append(">)super.get(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(");");	
		
		buf.appendln(2, "if (list == null)");
		
		buf.appendln(3, "list = new ArrayList<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append(">();");
		
		buf.appendln(4, "list.add(value);");

		buf.appendln(2, "// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation");
		buf.appendln(2, "super.setList(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(", list);");
		
		buf.appendln(1, this.endBody());
	}

	private void createManyRemover(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.appendln(1, "@SuppressWarnings(\"unchecked\")"); // for cast from DataObject[] to specific array
		createManyRemoverDeclaration(pkg, clss, field, typeClassName, buf);
		createManyRemoverBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyRemoverBody(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.append(this.beginBody());
		
		buf.appendln(2, "List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append("> list = (List<");
		buf.append(typeClassName.getCollectionSimpleName());
		buf.append(">)super.get(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(");");	
		
		buf.appendln(2, "if (list != null)");
		
		buf.appendln(4, "list.remove(value);");

		buf.appendln(2, "// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation");
		buf.appendln(2, "super.setList(");
	    buf.append(toQualifiedPropertyNameReference(pkg, clss, field));
		buf.append(", list);");
		
		buf.appendln(1, this.endBody());
	}
	
	public String createDirectoryName(Package pkg) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(pkg.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String subpackage = PlasmaConfig.getInstance().getSDO().getGlobalProvisioning().getImplementation().getChildPackageName();		
		if (subpackage != null && subpackage.trim().length() > 0)
			packageName = packageName + "." + subpackage;			
		String packageDir = packageName.replace(".", "/");
		TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
		buf.append(packageDir);
		return buf.toString();
	}

	protected String createPackageName(Package pkg) {
		Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(pkg.getUri());
		String packageName = sdoNamespace.getProvisioning().getPackageName();
		String subpackage = PlasmaConfig.getInstance().getSDO().getGlobalProvisioning().getImplementation().getChildPackageName();		
		if (subpackage != null && subpackage.trim().length() > 0)
			packageName = packageName + "." + subpackage;
		TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
		buf.append(packageName);
		return buf.toString();
	}
		
	protected String createPackageDeclaration(Package pkg) {
		TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
		buf.append("package " + createPackageName(pkg));
		buf.append(";");
		return buf.toString();
	}


	protected String createThirdPartyImportDeclarations(Package pkg, Class clss) {
		TextBuilder buf = new TextBuilder(LINE_SEP, this.context.getIndentationToken());
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
