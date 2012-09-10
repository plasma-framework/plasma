package org.plasma.sdo;

import org.plasma.common.exception.PlasmaRuntimeException;



public class PlasmaDataObjectException extends PlasmaRuntimeException
{
    private static final long serialVersionUID = 1L;
    public PlasmaDataObjectException(String message)
    {
        super(message);
    }
    public PlasmaDataObjectException(Throwable t)
    {
        super(t);
    }
}