package org.plasma.text.ddl;

import org.plasma.common.exception.PlasmaRuntimeException;



public class DDLException extends PlasmaRuntimeException
{
    private static final long serialVersionUID = 1L;
    public DDLException(String message)
    {
        super(message);
    }
    public DDLException(Throwable t)
    {
        super(t);
    }
}