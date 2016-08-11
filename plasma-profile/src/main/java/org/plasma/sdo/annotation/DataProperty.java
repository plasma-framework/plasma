package org.plasma.sdo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.plasma.sdo.DataType;

/**
 * Associates property related meta data with a Java enum literal. Only Java enums (enumerations) should 
 * include this annotation.  
 * <p></p>
 * Note: Only Java enum types (enumerations) should 
 * include this annotation, otherwise an <@link InvalidAnnotationException> will occur during processing. 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataProperty {
    public DataType dataType();
    public boolean isNullable() default true;
    public boolean isMany() default false;
    public boolean isReadOnly() default false;
}
