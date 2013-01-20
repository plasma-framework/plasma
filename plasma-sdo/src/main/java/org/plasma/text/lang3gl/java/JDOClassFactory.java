package org.plasma.text.lang3gl.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Property;
import org.plasma.provisioning.adapter.FieldAdapter;
import org.plasma.sdo.access.model.Entity;
import org.plasma.sdo.access.model.EntityConstants;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.ClassNameResolver;


public class JDOClassFactory extends JDODefaultFactory 
    implements ClassFactory {

	public JDOClassFactory(Lang3GLContext context) {
		super(context);
	}
	
	public String createFileName(Class clss) {
		StringBuilder buf = new StringBuilder();
		JDOClassNameResolver classNameResolver = new JDOClassNameResolver();
		buf.append(classNameResolver.getName(clss));
		buf.append(".java");		
		return buf.toString();
	}

	public String createContent(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		
		buf.append(this.createPackageDeclaration(pkg));
		buf.append(LINE_SEP);
		buf.append(this.createThirdPartyImportDeclarations(pkg, clss));

		ClassNameResolver resolver = new JDOClassNameResolver();
		Map<String, String> importMap = this.createFieldImportMap(pkg, clss, resolver);
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

		buf.append(this.createFields(pkg, clss));
		buf.append(this.createOperations(pkg, clss));
		
		buf.append(LINE_SEP);
		buf.append(this.endBody());
		return buf.toString();
	}
	
	protected Map<String, String> createFieldImportMap(Package pkg, Class clss,
			ClassNameResolver resolver) {
		Map<String, String> nameMap = new TreeMap<String, String>();
		collectDataFieldClassNamesDeep(pkg, clss, nameMap, resolver,
				true, -1);
		collectReferenceFieldClassNamesDeep(pkg, clss, nameMap, resolver,
				false, -1);
		return nameMap;
	}
	
	private String createFields(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();

		Map<String, FieldAdapter> fields = new TreeMap<String, FieldAdapter>();
		collectProvisioningFields(pkg, clss, pkg, clss, fields);
		Iterator<String> fieldIter = fields.keySet().iterator();
		while (fieldIter.hasNext()) {
			String name = fieldIter.next();
			FieldAdapter adapter = fields.get(name);
			buf.append(LINE_SEP);
			buf.append(this.createPrivateFieldDeclaration(clss, adapter.getField()));
		}

		return buf.toString();
	}
	
	protected String createConstructors(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		JDOClassNameResolver classNameResolver = new JDOClassNameResolver();
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public ");
		buf.append(classNameResolver.getName(clss));
		buf.append("() {");
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("super();");
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("}");
		
		
	    return buf.toString();
    }

	protected String createOperations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		Map<String, FieldAdapter> fields = new TreeMap<String, FieldAdapter>();
		collectProvisioningFields(pkg, clss, pkg, clss, fields);
		Iterator<String> fieldIter = fields.keySet().iterator();
		while (fieldIter.hasNext()) {
			String name = fieldIter.next();
			FieldAdapter adapter = fields.get(name);
			buf.append(createOperations(pkg, clss, adapter.getField()));
		}
		return buf.toString();
	}

	protected String createOperations(Package pkg, Class clss, Property field) {
		
		StringBuilder buf = new StringBuilder();
		
		TypeClassInfo typeClassName = getTypeClassName(field.getType());
		
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
			TypeClassInfo typeClassName, StringBuilder buf) {
		
		createSingularGetterDeclaration(pkg, clss, field, typeClassName, buf);
		createSingularGetterBody(pkg, clss, field, typeClassName, buf);
	}

	private void createSingularGetterBody(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf) {	
		buf.append(this.beginBody());
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("return this.");
		buf.append(buildPrivateFieldName(clss, field));
		buf.append(";");				
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());		
	}
	
	private void createSingularSetter(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf) {
		createSingularSetterDeclaration(pkg, clss, field, typeClassName, buf);
		createSingularSetterBody(pkg, clss, field, typeClassName, buf);
	}

	private void createSingularSetterBody(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf) {
		buf.append(this.beginBody());
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("this.");
		buf.append(buildPrivateFieldName(clss, field));
		buf.append(" = value;");
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());		
	}	
	
	private void createManyGetter(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{	
		createManyGetterDeclaration(pkg, clss, field, typeClassName, buf);
		createManyGetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyGetterBody(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
			
		String fieldName = this.buildPrivateFieldName(clss, field);
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append(typeClassName.getSimpleName());
		buf.append("[] array = new ");
		buf.append(typeClassName.getSimpleName());
		buf.append("[this.");
		buf.append(fieldName);
		buf.append(".size()];");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("this.");
		buf.append(fieldName);
		buf.append(".toArray(array);");

		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("return array;");

		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}

	private void createManyIndexGetter(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{	
		createManyIndexGetterDeclaration(pkg, clss, field, typeClassName, buf);
		createManyIndexGetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyIndexGetterBody(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());

		String fieldName = this.buildPrivateFieldName(clss, field);
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("return this.");
		buf.append(fieldName);
		buf.append(".get(idx);");

		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}

	private void createManyCount(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{	
		createManyCountDeclaration(pkg, clss, field, typeClassName, buf);
		createManyCountBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyCountBody(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
		
		String fieldName = this.buildPrivateFieldName(clss, field);
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("return this.");
		buf.append(fieldName);
		buf.append(".size();");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}
	
	private void createManySetter(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{		
		createManySetterDeclaration(pkg, clss, field, typeClassName, buf);
		createManySetterBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManySetterBody(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());

		String fieldName = this.buildPrivateFieldName(clss, field);
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("this.");
		buf.append(fieldName);
		buf.append(".clear();");
			
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("for (int i = 0; i < value.length; i++) {");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(3));
		buf.append("this.");
		buf.append(fieldName);
		buf.append(".add(value[i]);");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("}");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}
	
	private void createManyAdder(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{		
		createManyAdderDeclaration(pkg, clss, field, typeClassName, buf);
		createManyAdderBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyAdderBody(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
		
		String fieldName = this.buildPrivateFieldName(clss, field);
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("this.");
		buf.append(fieldName);
		buf.append(".add(value);");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}

	private void createManyRemover(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{		
		createManyRemoverDeclaration(pkg, clss, field, typeClassName, buf);
		createManyRemoverBody(pkg, clss, field, typeClassName, buf);
	}
	
	private void createManyRemoverBody(Package pkg, Class clss, Property field, 
			TypeClassInfo typeClassName, StringBuilder buf)
	{		
		buf.append(this.beginBody());
		
		String fieldName = this.buildPrivateFieldName(clss, field);
		
		buf.append(LINE_SEP);			    
		buf.append(indent(2));
		buf.append("this.");
		buf.append(fieldName);
		buf.append(".remove(value);");
		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append(this.endBody());
	}
	

	protected String createStaticFieldDeclarations(Class clss) {
		StringBuilder buf = new StringBuilder();	
	    
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("/** The SDO namespace URI associated with the SDO Type for this class */");
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("public static final String NAMESPACE_URI = \"");
		buf.append(clss.getUri());
		buf.append("\";");

		return buf.toString();
	}	
	
	protected String createTypeDeclaration(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		JDOClassNameResolver classNameResolver = new JDOClassNameResolver();
		//JPAInterfaceNameResolver interfaceNameResolver = new JPAInterfaceNameResolver();
			
		//if (!clss.isAbstract())
		    buf.append("public class ");
		//else
		//	buf.append("public abstract class ");
		buf.append(classNameResolver.getName(clss));
		buf.append(" extends ");
		buf.append(Entity.class.getName()); // use qualified name in case model declares class 'Entity' 
		//buf.append(LINE_SEP);
		//buf.append(indent(1));
 		//buf.append(" implements ");
 		//buf.append(interfaceNameResolver.getName(clss));
		
		return buf.toString();
	}
	
	protected String createPrivateFieldDeclaration(Class clss, Property field) {
		StringBuilder buf = new StringBuilder();
		
		TypeClassInfo typeClassName = getTypeClassName(field.getType());
		
		String fieldName = (buildPrivateFieldName(clss, field));
		buf.append(LINE_SEP);
		buf.append(indent(1));
		buf.append("private ");
		if (!field.isMany()) {
			buf.append(typeClassName.getSimpleName());
			buf.append(" ");
			buf.append(fieldName);
			buf.append(";");
		}
		else {
			buf.append("List<");
			buf.append(typeClassName.getCollectionSimpleName());
			buf.append("> ");
			buf.append(fieldName);
			buf.append(" = new ArrayList<");
			buf.append(typeClassName.getCollectionSimpleName());
			buf.append(">();");
		}
		
		return buf.toString();
	}
	
	private String buildPrivateFieldName(Class clss, Property field) {
		StringBuilder buf = new StringBuilder();
		buf.append(EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX);
		buf.append(field.getName());
		if (field.isMany())
			buf.append(EntityConstants.DATA_ACCESS_CLASS_MEMBER_MULTI_VALUED_SUFFIX);
		return buf.toString();		
	}
	
	protected String createThirdPartyImportDeclarations(Package pkg, Class clss) {
		StringBuilder buf = new StringBuilder();
		//buf.append(LINE_SEP);
		//buf.append(this.createImportDeclaration(pkg, clss, LogFactory.class.getName()));
		//buf.append(LINE_SEP);
		//buf.append(this.createImportDeclaration(pkg, clss, Log.class.getName()));
		//buf.append(LINE_SEP);
		//buf.append(this.createImportDeclaration(pkg, clss, Serializable.class.getName()));

		//buf.append(LINE_SEP);
		//buf.append(this.createImportDeclaration(pkg, clss, Entity.class.getName()));
		
		if (!hasOnlySingilarFieldsDeep(clss)) {
			buf.append(LINE_SEP);
			buf.append(this.createImportDeclaration(pkg, clss, List.class.getName()));
			buf.append(LINE_SEP);
			buf.append(this.createImportDeclaration(pkg, clss, ArrayList.class.getName()));
		}
		return buf.toString();
	}

}
