package org.plasma.text.lang3gl.java;

public abstract class DefaultNameResolver {

	
	/**
	 * Replace characters in the given name not applicable as
	 * a Java class name. 
	 * @param name the original name
	 * @return the new name
	 */
	protected String replaceReservedCharacters(String name) {
		String result = name;
		result = result.replace('-', '_');
		result = result.replace('+', '_');
		result = result.replace('/', '_');
		result = result.replace('\\', '_');
		result = result.replace('!', '_');
		result = result.replace('@', '_');
		result = result.replace(' ', '_');
		return result;
	}
}
