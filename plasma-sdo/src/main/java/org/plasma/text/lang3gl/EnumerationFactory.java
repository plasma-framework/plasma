package org.plasma.text.lang3gl;

import org.plasma.provisioning.Enumeration;
import org.plasma.provisioning.Package;

public interface EnumerationFactory extends Lang3GLContentFactory {
	
	/**
	 * Returns an 3Gl language specific file name for the given Enumeration based on
	 * configuration settings.
	 * @see Enumeration
	 * @param clss the Enumeration
	 * @return an 3Gl language specific file name for the given Enumeration based on
	 * configuration settings
	 */
	public String createFileName(Enumeration clss);

	/**
	 * Returns an 3Gl language specific content for the given Package and Type
	 * @param pkg the Package
	 * @param type the Type
	 * @return the content
	 */
	public String createContent(Package pkg, Enumeration type); 
	
}
