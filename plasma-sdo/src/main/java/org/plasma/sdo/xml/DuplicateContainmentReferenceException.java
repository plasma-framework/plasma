package org.plasma.sdo.xml;



public class DuplicateContainmentReferenceException extends UnmarshallerRuntimeException
{
    private static final long serialVersionUID = 1L;
    public DuplicateContainmentReferenceException(String message)
    {
        super(message);
    }
    public DuplicateContainmentReferenceException(Throwable t)
    {
        super(t);
    }
}