package org.plasma.sdo.xml;

import org.plasma.common.exception.PlasmaRuntimeException;

public class UnmarshallerRuntimeException extends PlasmaRuntimeException {
    
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private UnmarshallerRuntimeException() {
        super();
    }

    public UnmarshallerRuntimeException(Throwable t) {
        super(t);
    }
    public UnmarshallerRuntimeException(String msg) {
        super(msg);
    }
}
