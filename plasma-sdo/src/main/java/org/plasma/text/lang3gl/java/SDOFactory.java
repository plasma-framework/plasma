package org.plasma.text.lang3gl.java;

import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.EnumerationFactory;
import org.plasma.text.lang3gl.InterfaceFactory;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLFactory;

public class SDOFactory implements Lang3GLFactory {
	
	private Lang3GLContext context;
	
	public SDOFactory(Lang3GLContext context) {
		this.context = context;
	}

	public ClassFactory getClassFactory() {
		return new SDOClassFactory(this.context);
	}

	public EnumerationFactory getEnumerationFactory() {
		return new SDOEnumerationFactory(this.context);
	}

	public InterfaceFactory getInterfaceFactory() {
		return new SDOInterfaceFactory(this.context);
	}

}
