package org.plasma.sdo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.atteo.classindex.IndexAnnotated;
import org.plasma.sdo.DataType;

import java.lang.annotation.ElementType;

/**
 * Associates entity related meta data with a Java enum type. 
 * 
 * <p></p>
 * Note: Only Java enum types (enumerations) should 
 * include this annotation, otherwise an <@link InvalidAnnotationException> will occur during annotation processing. 
 * 
 * <p></p>
 * <pre>
 * {@code
 * @Type(name = "Organization")
 * public enum Organization {
 *     @ValueConstraint(maxLength = "12")
 * 	   @Alias(physicalName = "NM")
 * 	   @DataProperty(dataType = DataType.String, isNullable = false)
 *     name,
 *     @EnumConstraint(targetEnum = OrgType.class)
 * 	   @Alias(physicalName = "TYP")
 * 	   @DataProperty(dataType = DataType.String, isNullable = false)
 *     type,
 * 	   @Alias(physicalName = "EMP")
 * 	   @ReferenceProperty(isNullable = false, isMany = true, targetClass = Person.class, targetProperty = "org")
 *     employees;
 * }
 * }
 * </pre> 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface Type {
	/**
	 * Whether the type is abstract
	 * @return whether the type is abstract
	 */
    public boolean isAbstract() default false;
    /**
     * Returns an array of types which this type extends
     * @return an array of types which this type extends
     */
    public Class<?>[] superTypes() default {};
    
    
    /**
     * Return the logical name for this type, and if null, the logical is assumed to be actual
     * Java enum name. 
     * @return the logical name for this type
     */
    public String name();
}
