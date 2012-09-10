package org.plasma.text.lang3gl;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.Package;

public interface InterfaceFactory extends Lang3GLContentFactory {

	/**
	 * Returns an 3Gl language specific file name for the given Class based on
	 * configuration settings.
	 * @see Class
	 * @param clss the class
	 * @return an 3Gl language specific file name for the given Class based on
	 * configuration settings
	 */
	public String createFileName(Class clss);
 
	/**
	 * Returns an 3Gl language specific content for the given Package and Type
	 * @param pkg the Package
	 * @param type the Type
	 * @return the content
	 */
	public String createContent(Package pkg, Class type); 


}
