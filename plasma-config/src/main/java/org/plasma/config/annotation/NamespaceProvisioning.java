package org.plasma.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import org.plasma.config.PropertyNameStyle;
import org.plasma.config.EnumSource;

/**
 * Specifies various provisioning properties within the context of a namespace.
 */
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NamespaceProvisioning {
	
	/**
	 * The qualified Java package for generated interfaces for this namespace.
	 * 
	 * @return the qualified Java package for generated interfaces for this
	 *         namespace
	 */
	public String rootPackageName();

	/**
	 * Specifies how logical property names are generated and referenced within
	 * SDO interfaces and implementation classes.
	 */
	public PropertyNameStyle propertyNameStyle() default PropertyNameStyle.ENUMS;

	/**
	 * When logical property names are generated and/or referenced as enums within generated code,
	 * specifies how enums are originated
	 * 
	 * @return how enums are originated when logical property names are
	 *         generated and/or referenced as enums within generated code
	 */
	public EnumSource enumSource() default EnumSource.EXTERNAL;

	/**
	 * The string to prepend to interface class names
	 * 
	 * @return the string to prepend to interface class names
	 */
	public String interfaceNamePrefix() default "";

	/**
	 * The string to append to interface class names
	 * 
	 * @return the string to append to interface class names
	 */
	public String interfaceNameSuffix() default "";

	/**
	 * The string to prepend to implementation class names
	 * 
	 * @return the string to prepend to implementation class names
	 */
	public String implementationClassNamePrefix() default "";

	/**
	 * The string to append to implementation class names
	 * 
	 * @return the string to append to implementation class names
	 */
	public String implementationClassNameSuffix() default "Impl";
	
	/**
	 * The name of the child or sub package for implementation classes for this provisioning context
	 * @return the name of the child or sub package for implementation classes for this provisioning context
	 */
	public String implementationPackageName() default "impl";

	/**
	 * Whether to generate Query DSL classes for this context
	 * 
	 * @return whether to generate Query DSL classes for this context
	 */
	public boolean queryDSLGenerate() default true;

	/**
	 * The string to prepend to Query DSL class names
	 * 
	 * @return the string to prepend to Query DSL class names
	 */
	public String queryDSLClassNamePrefix() default "Q";

	/**
	 * The string to append to Query DSL class names
	 * 
	 * @return the string to append to Query DSL class names
	 */
	public String queryDSLClassNameSuffix() default "";

	/**
	 * The name of the child or sub package for Query DSL classes for this provisioning context
	 * @return the name of the child or sub package for Query DSL classes for this provisioning context
	 */
	public String queryDSLPackageName() default "query";
}
