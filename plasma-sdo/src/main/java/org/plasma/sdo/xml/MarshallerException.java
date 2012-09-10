package org.plasma.sdo.xml;



public class MarshallerException extends Exception
{
    private static final long serialVersionUID = 1L;
    public MarshallerException(String message)
    {
        super(message);
    }
    public MarshallerException(Throwable t)
    {
        super(t);
    }
}