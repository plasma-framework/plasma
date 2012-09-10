package org.plasma.text.lang3gl;

import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;

public interface ClassNameResolver {
	
	/**
	 * Returns an unqualified class name for the given provisioning model class.
	 * @param clss the provisioning class
	 * @return a unqualified class name
	 */
    public String getName(Class clss);

    /**
	 * Returns a qualified class name for the given provisioning model class.
	 * @param clss the provisioning class
	 * @return a qualified class name
	 */
    public String getQualifiedName(Class clss);
    
	/**
	 * Returns a qualified class name for the given provisioning model class reference.
	 * @param clssRef the provisioning class reference
	 * @return a qualified class name
	 */
    public String getQualifiedName(ClassRef clssRef);
}
