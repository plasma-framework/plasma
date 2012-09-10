package org.plasma.text;

import org.plasma.common.exception.PlasmaRuntimeException;



public class TextException extends PlasmaRuntimeException
{
    private static final long serialVersionUID = 1L;
    public TextException(String message)
    {
        super(message);
    }
    public TextException(Throwable t)
    {
        super(t);
    }
}