package org.plasma.sdo.xpath;

import org.plasma.common.exception.PlasmaCheckedException;

public class XPathException extends PlasmaCheckedException {

	private static final long serialVersionUID = 1L;

	public XPathException() {
		super();
	}

	public XPathException(String msg, Throwable t) {
		super(msg, t);
	}

	public XPathException(String msg) {
		super(msg);
	}

	public XPathException(Throwable t) {
		super(t);
	}

}
