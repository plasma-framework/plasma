package org.plasma.query;


import org.plasma.common.exception.PlasmaRuntimeException;

public class QueryException extends PlasmaRuntimeException
{
	private static final long serialVersionUID = 1L;
	public QueryException(String message)
    {
        super(message);
    }
    public QueryException(Throwable t)
    {
        super(t);
    }
}