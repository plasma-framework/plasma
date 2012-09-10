package org.plasma.text.lang3gl.java;

import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.EnumerationFactory;
import org.plasma.text.lang3gl.InterfaceFactory;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLFactory;

public class JDOFactory implements Lang3GLFactory {
	
	private Lang3GLContext context;
	
	public JDOFactory(Lang3GLContext context) {
		this.context = context;
	}

	public ClassFactory getClassFactory() {
		return new JDOClassFactory(this.context);
	}

	public EnumerationFactory getEnumerationFactory() {
		throw new RuntimeException("not implemented");
	}

	public InterfaceFactory getInterfaceFactory() {
		return new JDOInterfaceFactory(this.context);
	}

}
