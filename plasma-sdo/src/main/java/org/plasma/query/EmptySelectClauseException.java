package org.plasma.query;




public class EmptySelectClauseException extends QueryException
{
	private static final long serialVersionUID = 1L;
	public EmptySelectClauseException(String message)
    {
        super(message);
    }
    public EmptySelectClauseException(Throwable t)
    {
        super(t);
    }
}