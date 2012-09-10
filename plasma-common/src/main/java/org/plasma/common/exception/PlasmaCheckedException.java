package org.plasma.common.exception;

public class PlasmaCheckedException extends Exception {
    
	private static final long serialVersionUID = 1L;
	public PlasmaCheckedException() {
        super();
    }

    public PlasmaCheckedException(String msg, Throwable t) {
		super(msg, t);
	}

	public PlasmaCheckedException(Throwable t) {
        super(t);
    }
    public PlasmaCheckedException(String msg) {
        super(msg);
    }
}
