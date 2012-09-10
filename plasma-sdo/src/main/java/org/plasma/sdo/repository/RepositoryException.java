package org.plasma.sdo.repository;

import org.plasma.common.exception.PlasmaRuntimeException;



public class RepositoryException extends PlasmaRuntimeException
{
    private static final long serialVersionUID = 1L;
    public RepositoryException(String message)
    {
        super(message);
    }
    public RepositoryException(Throwable t)
    {
        super(t);
    }
}