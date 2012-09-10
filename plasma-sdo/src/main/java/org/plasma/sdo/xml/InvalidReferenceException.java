package org.plasma.sdo.xml;



public class InvalidReferenceException extends UnmarshallerRuntimeException
{
    private static final long serialVersionUID = 1L;
    public InvalidReferenceException(String message)
    {
        super(message);
    }
    public InvalidReferenceException(Throwable t)
    {
        super(t);
    }
}