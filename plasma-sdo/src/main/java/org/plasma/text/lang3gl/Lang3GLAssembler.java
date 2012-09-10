package org.plasma.text.lang3gl;

import java.io.IOException;

public interface Lang3GLAssembler {
	public void createEnumerationClasses() throws IOException; 
	public void createInterfaceClasses() throws IOException; 
	public void createInterfacePackageDocs() throws IOException; 
	public void createImplementationClasses() throws IOException; 
}
