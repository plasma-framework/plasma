package org.plasma.sdo.access;



public class RequiredPropertyException extends DataAccessException
{
    public RequiredPropertyException(String message)
    {
        super(message);
    }

    public RequiredPropertyException(Throwable t)
    {
        super(t);
    }
}