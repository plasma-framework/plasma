package org.plasma.text.lang3gl;

import org.plasma.provisioning.Model;


public abstract class DefaultLang3GLAssembler {
	protected Model packages;
	protected Lang3GLFactory factory;
	protected Lang3GLOperation operation;
	
	@SuppressWarnings("unused")
	private DefaultLang3GLAssembler() {}
	
	protected DefaultLang3GLAssembler(Model packages,
			Lang3GLFactory factory, 
			Lang3GLOperation operation) {
		this.packages = packages;
		this.operation = operation;
    	this.factory = factory;
	}

}
