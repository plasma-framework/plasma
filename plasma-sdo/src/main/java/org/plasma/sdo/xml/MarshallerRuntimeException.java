package org.plasma.sdo.xml;

import org.plasma.common.exception.PlasmaRuntimeException;

public class MarshallerRuntimeException extends PlasmaRuntimeException {
    
	private static final long serialVersionUID = 1L;
	public MarshallerRuntimeException() {
        super();
    }

    public MarshallerRuntimeException(Throwable t) {
        super(t);
    }
    public MarshallerRuntimeException(String msg) {
        super(msg);
    }
}
