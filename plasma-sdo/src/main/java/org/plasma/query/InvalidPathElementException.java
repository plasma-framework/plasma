package org.plasma.query;




public class InvalidPathElementException extends QueryException
{
	private static final long serialVersionUID = 1L;
	public InvalidPathElementException(String message)
    {
        super(message);
    }
    public InvalidPathElementException(Throwable t)
    {
        super(t);
    }
}