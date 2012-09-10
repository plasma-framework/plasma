package org.plasma.sdo.repository;




public class IllegalStereotypeApplicationException extends RepositoryException
{
    private static final long serialVersionUID = 1L;
    public IllegalStereotypeApplicationException(String message)
    {
        super(message);
    }
    public IllegalStereotypeApplicationException(Throwable t)
    {
        super(t);
    }
}