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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.WordWrap;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.DataTypeRef;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.EnumerationConstraint;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;
import org.plasma.metamodel.TypeRef;
import org.plasma.metamodel.ValueConstraint;
import org.plasma.provisioning.PropertyNameCollisionException;
import org.plasma.provisioning.adapter.FieldAdapter;
import org.plasma.sdo.DataFlavor;
import org.plasma.sdo.DataType;
import org.plasma.sdo.helper.DataConverter;
import org.plasma.text.TextBuilder;
import org.plasma.text.TextProvisioningException;
import org.plasma.text.lang3gl.ClassNameResolver;
import org.plasma.text.lang3gl.Lang3GLContext;

public abstract class DefaultFactory {
    private static Log log =LogFactory.getLog(
    		DefaultFactory.class); 
	protected Lang3GLContext context;
	protected static final String LINE_SEP = System.getProperty("line.separator");
	protected static final String FILE_SEP = System.getProperty("file.separator");
	private Map<Character, String> reservedJavaCharToLiteralMap =  new HashMap<Character, String>();
	
	@SuppressWarnings("unused")
	private DefaultFactory() {}
	
	public DefaultFactory(Lang3GLContext context) {
		this.context = context;
		this.reservedJavaCharToLiteralMap.put(
				Character.valueOf('+'),  
				"_plus_");
		this.reservedJavaCharToLiteralMap.put(
				Character.valueOf('-'),  
				"_minus_");
		this.reservedJavaCharToLiteralMap.put(
				Character.valueOf('/'),  
				"_div_");
		this.reservedJavaCharToLiteralMap.put(
				Character.valueOf('*'),  
				"_mult_");
		this.reservedJavaCharToLiteralMap.put(
				Character.valueOf('%'),  
				"_mod_");
		this.reservedJavaCharToLiteralMap.put(
				Character.valueOf('('),  
				"_rp_");
		this.reservedJavaCharToLiteralMap.put(
				Character.valueOf(')'),  
				"_lp_");
		this.reservedJavaCharToLiteralMap.put(
				Character.valueOf('('),  
				"_rb_");
		this.reservedJavaCharToLiteralMap.put(
				Character.valueOf(')'),  
				"_lb_");
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
            throw new TextProvisioningException("unknown primitive type, " 
                    + dataType.toString());
        }
    }	
	
	@Deprecated
	protected void collectProvisioningClasses(Package pkg, Class clss, Map<String, Class> classMap) {
		
		for (Property field : clss.getProperty())
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

		if (clss.getSuperClass() != null && clss.getSuperClass().size() > 0) {
			for (ClassRef cref : clss.getSuperClass()) {
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
		
		if (clss.getSuperClass() != null && clss.getSuperClass().size() > 0) {
			for (ClassRef cref : clss.getSuperClass()) {
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
		if (clss.getSuperClass() != null)
		    for (ClassRef cref : clss.getSuperClass()) {
			   Class sclss = this.context.findClass(cref);
			   Package spkg = this.context.findPackage(cref);
			   collectProvisioningFields(targetPkg, targetClss, spkg, sclss, fields);
		    }
        for (Property field : clss.getProperty()) {
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
		TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
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
	
	protected MetaClassInfo getTypeClassName(TypeRef type)
    {
		MetaClassInfo result = null;
		if (type instanceof DataTypeRef) {
			DataTypeRef dataTypeRef = (DataTypeRef)type;
			DataType sdoType = DataType.valueOf(dataTypeRef.getName());
			
			java.lang.Class<?> primitiveTypeClass = getTypeClass(sdoType, true);
			java.lang.Class<?> wrapperTypeClass = getTypeClass(sdoType, false);
			
            result = new MetaClassInfo(dataTypeRef, sdoType,
			    primitiveTypeClass, wrapperTypeClass, 
			    this.context.usePrimitives());
		}
		else if (type instanceof ClassRef) {
			result = new MetaClassInfo((ClassRef)type, 
				this.context.usePrimitives());
		}
    	return result;
    }
	
	protected DataFlavor getDataFlavor(DataTypeRef dataTypeRef) {
		DataType sdoType = DataType.valueOf(dataTypeRef.getName());
		return DataFlavor.fromDataType(sdoType);
	}
	
	@Deprecated
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
	
	protected String toEnumLiteralName(String name) {
		name = toConstantName(name);		
    	TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
    	char[] array = name.toCharArray();
        for (int i = 0; i < array.length; i++) {
        	if (i == 0) {
        		if (Character.isDigit(array[i]))
    	            buf.append("_");
        	}
        	buf.append(array[i]);
        }
        return buf.toString();
    }
	
	protected String toConstantName(String name) {
		name = name.trim();
    	TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
    	char[] array = name.toCharArray();
        for (int i = 0; i < array.length; i++) {
    		String lit = reservedJavaCharToLiteralMap.get(Character.valueOf(array[i]));
    		if (lit != null) {
    			buf.append(lit.toUpperCase());
    			continue;
    		}
        	if (i > 0) {
        	   if (Character.isLetter(array[i]) && Character.isUpperCase(array[i])) {
        		   if (!Character.isUpperCase(array[i-1]))
        		       buf.append("_");
        	   }
        	}
        	if (Character.isLetterOrDigit(array[i])) {
        	    buf.append(Character.toUpperCase(array[i]));        		
        	}
        	else
        	    buf.append("_");
        }
        return buf.toString();
    }
	
	protected String toMethodFieldName(String name) {
		return firstToUpperCase(name);
	}
	
	protected String indent(int num) {
    	TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
    	for (int i = 0; i < num; i++)
    		buf.append(this.getContext().getIndentationToken());
    	return buf.toString();
	}

	protected String newline(int num) {
    	TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
		buf.append(LINE_SEP);			    
    	for (int i = 0; i < num; i++)
    		buf.append(this.getContext().getIndentationToken());
    	return buf.toString();
	}
	
	protected boolean hasOnlySingilarFields(Class clss) {
		if (clss.getProperty() != null)
		for (Property field : clss.getProperty())
			if (field.isMany())
				return false;
			
		return true;	
	}

	protected boolean hasOnlySingilarFieldsDeep(Class clss) {
		if (!hasOnlySingilarFields(clss))
			return false;
			
		if (clss.getSuperClass() != null && clss.getSuperClass().size() > 0)
			for (ClassRef cref : clss.getSuperClass()) {
		        Class superClass = this.context.findClass(cref);
		        return hasOnlySingilarFieldsDeep(superClass);
			}
		return true;	
	}
	
	protected void createSingularGetterDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Returns the value of the <b>");
		buf.append(field.getName());
		buf.append("</b> property.");	
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
			buf.appendln(1, " * <p></p>");
			buf.appendln(1, " * <b>Property Definition: </b>");
			buf.append(definition);
	    }	    

		buf.appendln(1, " * @return the value of the <b>");
		buf.append(field.getName());
		buf.append("</b> property.");	
	    
	    buf.appendln(1, " */"); // end javadoc
		
		buf.appendln(1, "public ");
		buf.append(typeClassName.getSimpleName());
		buf.append(" get");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("()");
	}
	
	protected void createSingularSetterDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		
		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Sets the value of the <b>");
		buf.append(field.getName());
		buf.append("</b> property to the given value.");	
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
			buf.appendln(1, " * <p></p>");
			buf.appendln(1, " * <b>Property Definition: </b>");
			buf.append(definition);
	    }	
	    
	    if (field.getValueConstraint() != null || field.getEnumerationConstraint() != null) {	    	
			if (field.getValueConstraint() != null) {
		    	buf.appendln(1, " * <p></p>");
				buf.appendln(1, " * <b>Value Constraints: </b>");
				buf.append("<pre>");	
				ValueConstraint vc = field.getValueConstraint();
				if (vc.getMinLength() != null) {
					buf.appendln(1, " *     minLength: ");	
					buf.append(vc.getMinLength());						
				}
				if (vc.getMaxLength() != null) {
					buf.appendln(1, " *     maxLength: ");	
					buf.append(vc.getMaxLength());						
				}
				if (vc.getMinInclusive() != null) {
					buf.appendln(1, " *     minInclusive: ");	
					buf.append(vc.getMinInclusive());						
				}
				if (vc.getMaxInclusive() != null) {
					buf.appendln(1, " *     maxInclusive: ");	
					buf.append(vc.getMaxInclusive());						
				}
				if (vc.getMinExclusive() != null) {
					buf.appendln(1, " *     minExclusive: ");	
					buf.append(vc.getMinExclusive());						
				}
				if (vc.getMaxExclusive() != null) {
					buf.appendln(1, " *     maxExclusive: ");	
					buf.append(vc.getMaxExclusive());						
				}
				if (vc.getFractionDigits() != null) {
					buf.appendln(1, " *     fractionDigits: ");	
					buf.append(vc.getFractionDigits());						
				}
				if (vc.getTotalDigits() != null) {
					buf.appendln(1, " *     totalDigits: ");	
					buf.append(vc.getTotalDigits());						
				}
				if (vc.getPattern() != null) {
					buf.appendln(1, " *     pattern: ");	
					buf.append(vc.getPattern());						
				}
				buf.append("</pre>");	
			}
			else if (field.getEnumerationConstraint() != null) {
		    	buf.appendln(1, " * <p></p>");
				buf.appendln(1, " * <b>Enumeration Constraints: </b>");
				buf.append("<pre>");	
				EnumerationConstraint ec = field.getEnumerationConstraint();
				buf.appendln(1, " *     <b>name:</b> ");	
				buf.append(ec.getValue().getName());						
				buf.appendln(1, " *     <b>URI:</b>");	
				buf.append(ec.getValue().getUri());						
				buf.append("</pre>");	
			}
	    }
	    
	    buf.appendln(1, " */"); // end javadoc
		
		buf.appendln(1, "public void set");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("(");
		buf.append(typeClassName.getSimpleName());
		buf.append(" value)");
	}

	protected void createUnsetterDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {

		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Unsets the <b>");
		buf.append(field.getName());
		buf.append("</b> property, ");
		if (field.isMany()) {
			buf.append("clearing the underlying collection. ");
		}
		else {
			buf.append("the value");
			buf.appendln(1, " * of the property of the object being set to the property's");
			buf.appendln(1, " * default value. ");
		}
		buf.append("The property will no longer be");
		buf.appendln(1, " * considered set.");		
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
	    	addPropertyModelDocLinks(clss, field, 
	    		typeClassName, buf);
	    }
	    
	    buf.appendln(1, " */"); // end javadoc
		
		buf.appendln(1, "public void unset");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("()");
	}	
	
	private void addPropertyModelDocLinks(Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{
		buf.appendln(1, " * <p></p>");
		buf.appendln(1, " * <b>Property Definition: </b>");
		buf.appendln(1, " * See {@link #get");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("() get");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("} or {@link #set");
		buf.append(toMethodFieldName(field.getName()));
		if (!field.isMany()) {
		    buf.append("(");
		    buf.append(typeClassName.getSimpleName());
		    buf.append(" value) ");
		}
		else {
			buf.append("(");
			buf.append(typeClassName.getSimpleName());
			buf.append("[] value) ");
		}
		buf.append("set");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("(...)} for a definition of property <b>");
		buf.append(field.getName());
		buf.append("</b>");		
	}
	
	protected void createIsSetDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {
		
		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Returns true if the <b>");
		buf.append(field.getName());
		buf.append("</b> property is set.");	
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
	    	addPropertyModelDocLinks(clss, field, 
		    		typeClassName, buf);
	    }
	    
	    // return
	    buf.appendln(1, " * @return true if the <b>");
		buf.append(field.getName());
		buf.append("</b> property is set.");	
	    
	    buf.appendln(1, " */"); // end javadoc
		
		buf.appendln(1, "public boolean isSet");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("()");
	}
	
	protected void createCreatorDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {

		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Creates and returns a new instance of Type {@link ");
		buf.append(typeClassName.getSimpleName());
		buf.append("} automatically establishing a containment relationship ");
		buf.append("through the object's reference property, <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
		
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
	    	addPropertyModelDocLinks(clss, field, 
		    		typeClassName, buf);
	    }
	    
	    // return
	    buf.appendln(1, " * @return a new instance of Type {@link ");
		buf.append(typeClassName.getSimpleName());
		buf.append("} automatically establishing a containment relationship ");
		buf.append("through the object's reference property <b>");
		buf.append(field.getName());
		buf.append("</b>.");		
	    
	    buf.appendln(1, " */"); // end javadoc

		buf.appendln(1, "public ");
		buf.append(typeClassName.getSimpleName());
		buf.append(" create");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("()");
	}	

	protected void createCreatorByAbstractClassDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf) {

		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Creates and returns a new instance of the given subclass Type for abstract base Type {@link ");
		buf.append(typeClassName.getSimpleName());
		buf.append("} automatically establishing a containment relationship ");
		buf.append("through the object's reference property, <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
		
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
	    	addPropertyModelDocLinks(clss, field, 
		    		typeClassName, buf);
	    }
	    
	    //params
		buf.appendln(1, " * @param clss the subclass Type");	

	    //return
		buf.appendln(1, " * Returns a new instance of the given subclass Type for abstract base Type {@link ");
		buf.append(typeClassName.getSimpleName());
		buf.append("}.");	
	    
	    buf.appendln(1, " */"); // end javadoc
		
		buf.appendln(1, "public ");
		buf.append(typeClassName.getSimpleName());
		buf.append(" create");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("(Class<? extends ");
		buf.append(typeClassName.getSimpleName());
		buf.append("> clss)");
	}	
	
	protected void createManyGetterDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Returns an array of <b>");
		buf.append(typeClassName.getSimpleName());
		buf.append("</b> set for the object's multi-valued property <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
			buf.appendln(1, " * <p></p>");
			buf.appendln(1, " * <b>Property Definition: </b>");
			buf.append(definition);
	    }

	    // return 
		buf.appendln(1, " * @return an array of <b>");
		buf.append(typeClassName.getSimpleName());
		buf.append("</b> set for the object's multi-valued property <b>");
		buf.append(field.getName());
		buf.append("</b>.");	

	    buf.appendln(1, " */"); // end javadoc
		
		buf.appendln(1, "public ");
		buf.append(typeClassName.getSimpleName());
		buf.append("[] get");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("()");
	}	

	protected void createManyIndexGetterDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Returns the <b>");
		buf.append(typeClassName.getSimpleName());
		buf.append("</b> set for the object's multi-valued property <b>");
		buf.append(field.getName());
		buf.append("</b> based on the given index.");	
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
	    	addPropertyModelDocLinks(clss, field, 
		    		typeClassName, buf);
	    }

	    // params
		buf.appendln(1, " * @param idx the index");
		
		// return 
		buf.appendln(1, " * @return the <b>");
		buf.append(typeClassName.getSimpleName());
		buf.append("</b> set for the object's multi-valued property <b>");
		buf.append(field.getName());
		buf.append("</b> based on the given index.");	
	    
	    buf.appendln(1, " */"); // end javadoc

		buf.appendln(1, "public ");
		buf.append(typeClassName.getSimpleName());
		buf.append(" get");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("(int idx)");
	}	

	protected void createManyCountDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Returns a count for multi-valued property <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
	    	addPropertyModelDocLinks(clss, field, 
		    		typeClassName, buf);
	    }

	    // return 
		buf.appendln(1, " * @return a count for multi-valued property <b>");
		buf.append(field.getName());
		buf.append("</b>.");	

	    buf.appendln(1, " */"); // end javadoc
		
		buf.appendln(1, "public int get");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("Count()");
	}	
	
	protected void createManySetterDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Sets the given array of Type <b>");
		buf.append(typeClassName.getSimpleName());
		buf.append("</b> for the object's multi-valued property <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
			buf.appendln(1, " * <p></p>");
			buf.appendln(1, " * <b>Property Definition: </b>");
			buf.append(definition);
	    }

	    // params
		buf.appendln(1, " * @param value the array value");
			    
	    buf.appendln(1, " */"); // end javadoc

		buf.appendln(1, "public void set");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("(");
		buf.append(typeClassName.getSimpleName());
		buf.append("[] value)");
	}
	 
	protected void createManyAdderDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Adds the given value of Type <b>");
		buf.append(typeClassName.getSimpleName());
		buf.append("</b> for the object's multi-valued property <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
	    	addPropertyModelDocLinks(clss, field, 
		    		typeClassName, buf);
	    }

	    // params
		buf.appendln(1, " * @param value the value to add");
			    
	    buf.appendln(1, " */"); // end javadoc

		buf.appendln(1, "public void add");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("(");
		buf.append(typeClassName.getSimpleName());
		buf.append(" value)");
	}
	
	protected void createManyRemoverDeclaration(Package pkg, Class clss, Property field, 
			MetaClassInfo typeClassName, TextBuilder buf)
	{		
		buf.appendln(1, "/**"); // begin javadoc
		buf.appendln(1, " * Removes the given value of Type <b>");
		buf.append(typeClassName.getSimpleName());
		buf.append("</b> for the object's multi-valued property <b>");
		buf.append(field.getName());
		buf.append("</b>.");	
	    
		String definition = this.getWrappedDocmentations(field.getDocumentation(), 1);
	    if (definition != null && definition.length() > 0) {
	    	addPropertyModelDocLinks(clss, field, 
		    		typeClassName, buf);
	    }

	    // params
		buf.appendln(1, " * @param value the value to remove");
			    
	    buf.appendln(1, " */"); // end javadoc

		buf.appendln(1, "public void remove");
		buf.append(toMethodFieldName(field.getName()));
		buf.append("(");
		buf.append(typeClassName.getSimpleName());
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
		
		for (Property field : clss.getProperty())
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
		
		for (Property field : clss.getProperty())
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
		
		if (clss.getSuperClass() != null && clss.getSuperClass().size() > 0) {
			for (ClassRef cref : clss.getSuperClass()) {
		        Class superClass = this.context.findClass(cref);
		        if (superClass.isAbstract() && !collectAbstractClasses)
		        	continue;
		        Package superClassPackage = this.context.findPackage(cref);
				String qualifiedName = resolver.getQualifiedName(superClass, superClassPackage); 				
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

		if (clss.getSuperClass() != null && clss.getSuperClass().size() > 0) {
			for (ClassRef cref : clss.getSuperClass()) {
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

		if (clss.getSuperClass() != null && clss.getSuperClass().size() > 0) {
			for (ClassRef cref : clss.getSuperClass()) {
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
		
		if (clss.getSuperClass() != null && clss.getSuperClass().size() > 0) {
			for (ClassRef cref : clss.getSuperClass()) {
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
		TextBuilder buf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
		
		for (String name : nameMap.values()) {
		    buf.append(LINE_SEP);	
		    buf.append("import ");
		    buf.append(name);
		    buf.append(";");
		}
		
		return buf.toString();
	}
	
	protected String getWrappedDocmentations(List<Documentation> docs, int indent) {
		// add formatted doc from UML or derived default doc
		TextBuilder docsBuf = new TextBuilder(LINE_SEP, 
				this.context.getIndentationToken());
		if (docs != null) {
			for (Documentation doc : docs) {
				if (doc.getBody() == null || doc.getBody().getValue() == null)
					continue;
				String docText = doc.getBody().getValue().trim();
				if (docText.length() == 0)
					continue;
				String wrappedDoc = WordWrap.wordWrap(docText, 60, Locale.ENGLISH);
				String[] docLines = wrappedDoc.split("\n");
				for (String line : docLines) {
					docsBuf.append(newline(indent));	
					docsBuf.append(" * ");
					docsBuf.append(line); 
				}
	        }
		}
		return docsBuf.toString();
	}
}
