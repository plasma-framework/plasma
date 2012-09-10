package org.plasma.common.exception;

public class PlasmaRuntimeException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;
	public PlasmaRuntimeException() {
        super();
    }

    public PlasmaRuntimeException(Throwable t) {
        super(t);
    }
    public PlasmaRuntimeException(String msg) {
        super(msg);
    }
}
