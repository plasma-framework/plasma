package org.plasma.text.lang3gl;

import org.plasma.provisioning.Class;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.Package;

/**
 * Represents language preferences
 */
public interface Lang3GLContext {

	/**
	 * Returns a Class for the given ClassRef
	 * @param cref the ClassRef
	 * @see org.plasma.text.lang3gl.Class
	 * @see org.plasma.text.lang3gl.ClassRef
	 * @return the Class referenced by the given ClassRef
	 */
	public Class findClass(ClassRef cref);
	
	
	/**
	 * Returns a Class for the given qualified name where qualified names
	 * take the format uri#class-name, for example http://mysite.com/myapp#MyClass
	 * @param qualifiedName the qualified name, for example http://mysite.com/myapp#MyClass
	 * @see org.plasma.text.lang3gl.Class
	 * @return the Class referenced by the given qualified name
	 */
	public Class findClass(String qualifiedName);

	/**
	 * Returns a Package for the given ClassRef
	 * @param cref the ClassRef
	 * @see org.plasma.text.lang3gl.Package
	 * @see org.plasma.text.lang3gl.ClassRef
	 * @return the Package referenced by the given ClassRef
	 */
	public Package findPackage(ClassRef cref);
	
	
	/**
	 * Returns a Package for the given qualified name where qualified names
	 * take the format uri#class-name, for example http://mysite.com/myapp#MyClass
	 * @param qualifiedName the qualified name, for example http://mysite.com/myapp#MyClass
	 * @see org.plasma.text.lang3gl.Package
	 * @return the Package referenced by the given qualified name
	 */
	public Package findPackage(String qualifiedName);	
	
	
	/**
	 * Whether to use primitives over wrapper type classes.
	 * @return whether to use primitives over wrapper type classes
	 */
	public boolean usePrimitives();	
	public String getIndentationToken();
	
	
}
