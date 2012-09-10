package org.plasma.sdo.xpath;

/**
 * Checked exception used when the result or terminating node or nodes
 * for an XPATH expression are invalid for a given context.
 */
public class InvalidEndpointException extends XPathException {

	private static final long serialVersionUID = 1L;

	public InvalidEndpointException() {
		super();
	}

	public InvalidEndpointException(String msg, Throwable t) {
		super(msg, t);
	}

	public InvalidEndpointException(String msg) {
		super(msg);
	}

	public InvalidEndpointException(Throwable t) {
		super(t);
	}

}
