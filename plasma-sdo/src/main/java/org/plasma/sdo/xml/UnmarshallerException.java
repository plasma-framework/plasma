package org.plasma.sdo.xml;



public class UnmarshallerException extends Exception
{
    private static final long serialVersionUID = 1L;
    public UnmarshallerException(String message)
    {
        super(message);
    }
    public UnmarshallerException(Throwable t)
    {
        super(t);
    }
}