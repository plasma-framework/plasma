package org.plasma.common.app;

import org.plasma.common.exception.PlasmaRuntimeException;



public class AppException extends PlasmaRuntimeException
{
    public AppException(String message)
    {
        super(message);
    }
    public AppException(Throwable t)
    {
        super(t);
    }
}