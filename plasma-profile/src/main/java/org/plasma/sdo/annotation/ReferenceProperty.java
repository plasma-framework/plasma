package org.plasma.sdo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReferenceProperty {
    public boolean isNullable() default true;
    public boolean isMany() default false;
    public java.lang.Class<?> targetClass();
    public String targetProperty();
    public boolean readOnly() default false;
}
