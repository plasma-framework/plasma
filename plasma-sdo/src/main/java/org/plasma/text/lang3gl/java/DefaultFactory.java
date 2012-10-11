package org.plasma.text.lang3gl.java;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.DataTypeRef;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Property;
import org.plasma.provisioning.PropertyNameCollisionException;
import org.plasma.provisioning.TypeRef;
import org.plasma.provisioning.adapter.FieldAdapter;
import org.plasma.sdo.DataType;
import org.plasma.sdo.helper.DataConverter;
import org.plasma.text.TextException;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.ClassNameResolver;

public abstract class DefaultFactory {
    private static Log log =LogFactory.getLog(
    		DefaultFactory.class); 
	protected Lang3GLContext context;
	protected static final String LINE_SEP = System.getProperty("line.separator");
	protected static final String FILE_SEP = System.getProperty("file.separator");
	
	public DefaultFactory(Lang3GLContext context) {
		this.context = context;
	}

	
	public Lang3GLContext getContext() {
		return this.context;
	}
	/**
     * Returns a 3Gl language specific class name for the given SDO data-type (as 
     * per the SDO Specification 2.10 Section 8.1) where primitive type names or
     * wrapper type names returned based on the current context. 
     * @see Lang3GLModelContext
     * @param dataType the SDO datatype
     * @return the 3Gl language specific type class name.
     */
	public java.lang.Class<?> getTypeClass(DataType dataType) {
		return getTypeClass(dataType, this.context.usePrimitives());
	}
	
	/**
     * Returns a 3Gl language specific class name for the given SDO data-type (as 
     * per the SDO Specification 2.10 Section 8.1) where primitive type names or
     * wrapper type names returned based on the current context. 
     * @see Lang3GLModelContext
     * @param dataType the SDO datatype
     * @param primitives whether to return a primitive type if possible
     * based on the given SDO datatype
     * @return the 3Gl language specific type class name.
     */
	public java.lang.Class<?> getTypeClass(DataType dataType, 
			boolean primitives) {
        
		if (primitives)
            return DataConverter.INSTANCE.toPrimitiveJavaClass(dataType);
		else
	        return DataConverter.INSTANCE.toWrapperJavaClass(dataType);
    }	

	public String getPrimitiveTypeDefault(DataType dataType) {
        
        switch (dataType) {
        case Boolean:  
        	return "false";   
        case Byte:       
        case Character:  
        case Float:      
        case Double:      
        case Int:        
        case Integer:    
        case Short:      
        case Long:       
        case Decimal:       
        	return "0"; 
        default:
            throw new TextException("unknown primitive type, " 
                    + dataType.toString());
        }
    }	
	
	@Deprecated
	protected void collectProvisioningClasses(Package pkg, Class clss, Map<String, Class> classMap) {
		
		for (Property field : clss.getProperties())
		{
			TypeRef type = field.getType();
			if (type instanceof ClassRef) {
				ClassRef cref = ((ClassRef)type);
				Class refClass = this.context.findClass(cref);
				classMap.put(refClass.getUri() + "#" + refClass.getName(), refClass);				
			}
		}
	}

	/**
	 * Collects class names from reference fields within this class and its superclass ancestry. 
	 * @param pkg the package
	 * @param clss the Class
	 * @param nameMap the name map
	 */
	//FIXME: move to provisioning tool/helper/whatever
	@Deprecated
	protected void collectProvisioningClassesDeep(Package pkg, Class clss, Map<String, Class> classMap) {
		
		collectProvisioningClasses(pkg, clss, classMap);

		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
			for (ClassRef cref : clss.getSuperClasses()) {
		        Class superClass = this.context.findClass(cref);
				String qualifiedName = cref.getUri() + "#" + cref.getName(); 				
				classMap.put(qualifiedName, superClass);
		        Package superClassPackage = this.context.findPackage(cref);
		        // recurse
		        collectProvisioningClassesDeep(superClassPackage, superClass, classMap);
		    }	
		}
	}	
	
	//FIXME: move to provisioning tool/helper/whatever
	@Deprecated
	protected void collectProvisioningSuperclasses(Package pkg, Class clss, Map<String, Class> classMap) {
		
		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
			for (ClassRef cref : clss.getSuperClasses()) {
				Class superClass = this.context.findClass(cref);
				classMap.put(superClass.getUri() + "#" + superClass.getName(), superClass);				
		        Package superClassPackage = this.context.findPackage(cref);
		        // recurse
		        collectProvisioningSuperclasses(superClassPackage, superClass, classMap);
		    }	
		}
	}	
	
	//FIXME: move to provisioning tool/helper/whatever
	@Deprecated
	protected void collectProvisioningFields(Package targetPkg, Class targetClss, Package pkg, Class clss, Map<String, FieldAdapter> fields) {
		if (clss.getSuperClasses() != null)
		    for (ClassRef cref : clss.getSuperClasses()) {
			   Class sclss = this.context.findClass(cref);
			   Package spkg = this.context.findPackage(cref);
			   collectProvisioningFields(targetPkg, targetClss, spkg, sclss, fields);
		    }
        for (Property field : clss.getProperties()) {
        	FieldAdapter existing = fields.get(field.getName());
        	if (existing != null) {
        		if (existing.getFieldClass().getName().equals(clss.getName()) &&
        		    existing.getFieldClass().getUri().equals(clss.getUri())) {
        		    log.warn("Classifier " 
        		    	+ targetClss.getUri() + "#" + targetClss.getName() 
        			    + " inherits multiple identical properties '"
        			    + existing.getFieldClass().getUri() 
            			+ "#" + existing.getFieldClass().getName() + "."
            			+ existing.getField().getName() + "'");
        		    continue;
        		}
        		else if (targetClss.getName().equals(clss.getName()) &&
        				targetClss.getUri().equals(clss.getUri()))
        		{
        		    throw new PropertyNameCollisionException("Classifier " + 
            			clss.getUri() + "#" + clss.getName()
            			+ " with property '" + field.getName()
            			+ "' inherits an identically named property from classifier "
            			+  existing.getFieldClass().getUri() 
            			+ "#" + existing.getFieldClass().getName());
        		}
        		else {
        		    throw new PropertyNameCollisionException("Classifier " + 
        		    		targetClss.getUri() + "#" + targetClss.getName()
            			+ " inherits multiple properties named '" + field.getName()
            			+ "' from 2 parent classifiers, "
            			+  existing.getFieldClass().getUri() 
            			+ "#" + existing.getFieldClass().getName()
            			+ " and " + clss.getUri() + "#" + clss.getName());
        		}
        	}
        	FieldAdapter adapter = new FieldAdapter(pkg, clss, field);
        	fields.put(field.getName(), adapter);
		}
	}
	
	protected String createImportDeclaration(Package pkg, Class clss, String qualifiedname) {
		StringBuilder buf = new StringBuilder();
	    buf.append("import ");
	    buf.append(qualifiedname);
	    buf.append(";");
	    return buf.toString();
	}
	
	protected String beginBody() { 
		return "{";
	}
	
	protected String endBody() { 
		return "}";
	}
	
	protected String getTypeClassName(TypeRef type)
    {
		String typeClassName = null;
		if (type instanceof DataTypeRef) {
			DataType sdoType = DataType.valueOf(((DataTypeRef)type).getName());
			java.lang.Class<?> typeClass = this.getTypeClass(sdoType);
			typeClassName = typeClass.getSimpleName();
		}
		else if (type instanceof ClassRef) {
			typeClassName = ((ClassRef)type).getName();
		}
    	return typeClassName;
    }	
	
	protected String getTypeClassName(Class clss)
    {
    	return clss.getName();
    }	
	
	protected String firstToUpperCase(String name) {
		if (!Character.isUpperCase(name.charAt(0))) {
		    return name.substring(0, 1).toUpperCase() + name.substring(1);	
		}
			
		return name;
    }
	
	protected String toConstantName(String name) {
		name = name.trim();
    	StringBuilder buf = new StringBuilder();
    	char[] array = name.toCharArray();
        for (int i = 0; i < array.length; i++) {
        	if (i > 0) {
        	   if (Character.isLetter(array[i]) && Character.isUpperCase(array[i]))
        		   buf.append("_");
        	}
        	if (Character.isLetterOrDigit(array[i])) {
        	    buf.append(Character.toUpperCase(array[i]));        		
        	}
        	else
        	    buf.append("_");
        }
        return buf.toString();
    }
	
	protected String indent(int num) {
    	StringBuilder buf = new StringBuilder();
    	for (int i = 0; i < num; i++)
    		buf.append(this.getContext().getIndentationToken());
    	return buf.toString();
	}

	protected String newline(int num) {
    	StringBuilder buf = new StringBuilder();
		buf.append(LINE_SEP);			    
    	for (int i = 0; i < num; i++)
    		buf.append(this.getContext().getIndentationToken());
    	return buf.toString();
	}
	
	protected boolean hasOnlySingilarFields(Class clss) {
		if (clss.getProperties() != null)
		for (Property field : clss.getProperties())
			if (field.isMany())
				return false;
			
		return true;	
	}

	protected boolean hasOnlySingilarFieldsDeep(Class clss) {
		if (!hasOnlySingilarFields(clss))
			return false;
			
		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0)
			for (ClassRef cref : clss.getSuperClasses()) {
		        Class superClass = this.context.findClass(cref);
		        return hasOnlySingilarFieldsDeep(superClass);
			}
		return true;	
	}
	
	protected void createSingularGetterDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public ");
		buf.append(typeClassName);
		buf.append(" get");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("()");
	}
	
	protected void createSingularSetterDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
	    buf.append("public void set");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("(");
		buf.append(typeClassName);
		buf.append(" value)");
	}

	protected void createUnsetterDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public void unset");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("()");
	}	
	
	protected void createIsSetDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public void isSet");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("()");
	}
	
	protected void createCreatorDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf) {
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public ");
		buf.append(typeClassName);
		buf.append(" create");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("()");
	}	
	
	protected void createManyGetterDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public ");
		buf.append(typeClassName);
		buf.append("[] get");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("()");
	}	

	protected void createManyIndexGetterDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public ");
		buf.append(typeClassName);
		buf.append(" get");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("(int idx)");
	}	

	protected void createManyCountDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public int get");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("Count()");
	}	
	
	protected void createManySetterDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public void set");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("(");
		buf.append(typeClassName);
		buf.append("[] value)");
	}
	
	protected void createManyAdderDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public void add");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("(");
		buf.append(typeClassName);
		buf.append(" value)");
	}
	
	protected void createManyRemoverDeclaration(Package pkg, Class clss, Property field, 
			String typeClassName, StringBuilder buf)
	{		
		buf.append(LINE_SEP);			    
		buf.append(indent(1));
		buf.append("public void remove");
		buf.append(firstToUpperCase(field.getName()));
		buf.append("(");
		buf.append(typeClassName);
		buf.append(" value)");
	}

	protected void collectReferenceClassNames(Package pkg, Class clss, 
			Map<String, String> nameMap, ClassNameResolver resolver) {
		collectReferenceClassNames(pkg, clss, nameMap, resolver, 
				true, -1);
	}	
	
	/**
	 * Collects class names from reference fields within this class. 
	 * @param pkg the package
	 * @param clss the Class
	 * @param nameMap the name map used to store collected results
	 * @param resolver the qualified name resolver
	 */
	protected void collectReferenceClassNames(Package pkg, Class clss, 
			Map<String, String> nameMap, ClassNameResolver resolver,
			boolean collectAbstractClasses, int maxLevel) {
		
		for (Property field : clss.getProperties())
		{
			TypeRef type = field.getType();
			if (type instanceof DataTypeRef)
			    continue;
			ClassRef ref = ((ClassRef)type);
			String qualifiedName = resolver.getQualifiedName(ref); 				
			nameMap.put(qualifiedName, qualifiedName);
		}
	}

	protected void collectDataClassNames(Package pkg, Class clss, 
			Map<String, String> nameMap, ClassNameResolver resolver) {
		collectDataClassNames(pkg, clss, nameMap, resolver, 
				true, -1);
	}	
	
	/**
	 * Collects class names from data fields within this class. 
	 * @param pkg the package
	 * @param clss the Class
	 * @param nameMap the name map used to store collected results
	 * @param resolver the qualified name resolver
	 */
	protected void collectDataClassNames(Package pkg, Class clss, 
			Map<String, String> nameMap, ClassNameResolver resolver,
			boolean collectAbstractClasses, int maxLevel) {
		
		for (Property field : clss.getProperties())
		{
			TypeRef type = field.getType();
			if (type instanceof ClassRef)			    
				continue;
			DataType sdoType = DataType.valueOf(((DataTypeRef)type).getName());
			java.lang.Class<?> typeClass = this.getTypeClass(sdoType);
			if (typeClass.isPrimitive())
				continue;
			if (typeClass.isArray())
				continue;
			nameMap.put(typeClass.getName(), typeClass.getName());
		}
	}
	
	protected void collectSuperClassNames(Package pkg, Class clss, 
			Map<String, String> nameMap, ClassNameResolver resolver) {
		collectSuperClassNames(pkg, clss, nameMap, resolver, 
				true, -1);
	}	
	
	/**
	 * Collects class names from super classes for this class. 
	 * @param pkg the package
	 * @param clss the Class
	 * @param nameMap the name map used to store collected results
	 * @param resolver the qualified name resolver
	 */
	protected void collectSuperClassNames(Package pkg, Class clss, 
			Map<String, String> nameMap, ClassNameResolver resolver,
			boolean collectAbstractClasses, int maxLevel) {
		
		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
			for (ClassRef cref : clss.getSuperClasses()) {
		        Class superClass = this.context.findClass(cref);
		        if (superClass.isAbstract() && !collectAbstractClasses)
		        	continue;
		        Package superClassPackage = this.context.findPackage(cref);
				String qualifiedName = resolver.getQualifiedName(superClass); 				
				nameMap.put(qualifiedName, qualifiedName);
		        // recurse
		        collectSuperClassNames(superClassPackage, superClass, 
		        		nameMap, resolver, collectAbstractClasses, maxLevel);
		    }	
		}
	}
	
	/**
	 * Collects class names from reference fields within this class and its superclass ancestry. 
	 * @param pkg the package
	 * @param clss the Class
	 * @param nameMap the name map used to store collected results
	 * @param resolver the qualified name resolver
	 */
	protected void collectReferenceFieldClassNamesDeep(Package pkg, Class clss, 
			Map<String, String> nameMap, ClassNameResolver resolver,
			boolean collectAbstractClasses, int maxLevel) {
		
		collectReferenceClassNames(pkg, clss, nameMap, resolver);

		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
			for (ClassRef cref : clss.getSuperClasses()) {
		        Class superClass = this.context.findClass(cref);
		        if (superClass.isAbstract() && !collectAbstractClasses)
		        	continue;
		        Package superClassPackage = this.context.findPackage(cref);
		        // recurse
		        collectReferenceFieldClassNamesDeep(superClassPackage, superClass, 
		        		nameMap, resolver, collectAbstractClasses, maxLevel);
		    }	
		}
	}
	
	/**
	 * Collects class names from reference fields within this class and its superclass ancestry. 
	 * @param pkg the package
	 * @param clss the Class
	 * @param nameMap the name map used to store collected results
	 * @param resolver the qualified name resolver
	 */
	protected void collectDataFieldClassNamesDeep(Package pkg, Class clss, 
			Map<String, String> nameMap, ClassNameResolver resolver,
			boolean collectAbstractClasses, int maxLevel) {
		
		collectDataClassNames(pkg, clss, nameMap, resolver);

		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
			for (ClassRef cref : clss.getSuperClasses()) {
		        Class superClass = this.context.findClass(cref);
		        if (superClass.isAbstract() && !collectAbstractClasses)
		        	continue;
		        // Note: don't map the superclass qualified name - we are collecting data field names hers
		        Package superClassPackage = this.context.findPackage(cref);
		        // recurse
		        collectDataFieldClassNamesDeep(superClassPackage, superClass, nameMap, 
		        		resolver, collectAbstractClasses, maxLevel);
		    }	
		}
	}
	
	/**
	 * Collects class names from the ancestry of the given class. 
	 * @param pkg the package
	 * @param clss the Class
	 * @param nameMap the name map used to store collected results
	 * @param resolver the qualified name resolver
	 */
	protected void collectSuperclassNames(Package pkg, Class clss, 
			Map<String, String> nameMap, ClassNameResolver resolver) {
		
		if (clss.getSuperClasses() != null && clss.getSuperClasses().size() > 0) {
			for (ClassRef cref : clss.getSuperClasses()) {
				String qualifiedName = resolver.getQualifiedName(cref); 				
		        Class superClass = this.context.findClass(cref);
				nameMap.put(qualifiedName, qualifiedName);
		        Package superClassPackage = this.context.findPackage(cref);
		        // recurse
		        collectSuperclassNames(superClassPackage, superClass, nameMap, resolver);
		    }	
		}
	}
	
	protected Map<String, String> createFieldImportMap(Package pkg, Class clss,
			ClassNameResolver resolver) {
		Map<String, String> nameMap = new TreeMap<String, String>();
		collectDataFieldClassNamesDeep(pkg, clss, nameMap, resolver,
				true, -1);
		collectReferenceFieldClassNamesDeep(pkg, clss, nameMap, resolver,
				true, -1);
		return nameMap;
	}
	
	protected String createImportDeclarations(Map<String, String> nameMap) {
		StringBuilder buf = new StringBuilder();
		
		for (String name : nameMap.values()) {
		    buf.append(LINE_SEP);	
		    buf.append("import ");
		    buf.append(name);
		    buf.append(";");
		}
		
		return buf.toString();
	}
}
