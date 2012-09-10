package org.plasma.sdo.xpath;

import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;

public abstract class DefaultXPath extends BaseXPath {

	private static final long serialVersionUID = -5689933743653203893L;

	protected DefaultXPath(String xpathExpr) throws JaxenException {
		super(xpathExpr);
	}

	public DefaultXPath(String xpathExpr, Navigator navigator)
			throws JaxenException {
		super(xpathExpr, navigator);
	}

	public static boolean isXPath(String path) {
		return XPathDetector.isXPath(path);
	}
	
}
