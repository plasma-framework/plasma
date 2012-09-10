package org.plasma.query;



public class InvalidPathPredicateException extends QueryException
{
	private static final long serialVersionUID = 1L;
	public InvalidPathPredicateException(String message)
    {
        super(message);
    }
    public InvalidPathPredicateException(Throwable t)
    {
        super(t);
    }
}