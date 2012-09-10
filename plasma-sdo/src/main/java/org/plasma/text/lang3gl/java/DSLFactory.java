package org.plasma.text.lang3gl.java;

import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.EnumerationFactory;
import org.plasma.text.lang3gl.InterfaceFactory;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLFactory;

public class DSLFactory implements Lang3GLFactory {
	
	private Lang3GLContext context;
	
	public DSLFactory(Lang3GLContext context) {
		this.context = context;
	}

	public ClassFactory getClassFactory() {
		return new DSLClassFactory(this.context);
	}

	public EnumerationFactory getEnumerationFactory() {
		throw new RuntimeException("not implemented");
	}

	public InterfaceFactory getInterfaceFactory() {
		throw new RuntimeException("not implemented");
	}

}
