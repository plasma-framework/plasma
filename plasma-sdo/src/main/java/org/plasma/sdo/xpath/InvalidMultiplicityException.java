package org.plasma.sdo.xpath;

/**
 * Checked exception used when multiple results are detected for a
 * singular property.
 */
public class InvalidMultiplicityException extends XPathException {

	private static final long serialVersionUID = 1L;

	public InvalidMultiplicityException() {
		super();
	}

	public InvalidMultiplicityException(String msg, Throwable t) {
		super(msg, t);
	}

	public InvalidMultiplicityException(String msg) {
		super(msg);
	}

	public InvalidMultiplicityException(Throwable t) {
		super(t);
	}

}
