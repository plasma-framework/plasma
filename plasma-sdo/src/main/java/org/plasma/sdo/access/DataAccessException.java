package org.plasma.sdo.access;


import org.plasma.common.exception.PlasmaRuntimeException;

public class DataAccessException extends PlasmaRuntimeException
{
    public DataAccessException(String message)
    {
        super(message);
    }
    public DataAccessException(Throwable t)
    {
        super(t);
    }
}