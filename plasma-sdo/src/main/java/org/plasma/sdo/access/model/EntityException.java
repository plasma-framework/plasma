package org.plasma.sdo.access.model;


import org.plasma.common.exception.PlasmaRuntimeException;

public class EntityException extends PlasmaRuntimeException
{

	private static final long serialVersionUID = 1L;
	public EntityException(String message)
    {
        super(message);
    }
    public EntityException(Throwable t)
    {
        super(t);
    }
}