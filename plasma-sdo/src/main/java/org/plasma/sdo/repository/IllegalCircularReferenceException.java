package org.plasma.sdo.repository;




public class IllegalCircularReferenceException extends RepositoryException
{
    private static final long serialVersionUID = 1L;
    public IllegalCircularReferenceException(String message)
    {
        super(message);
    }
    public IllegalCircularReferenceException(Throwable t)
    {
        super(t);
    }
}