package org.plasma.sdo.access;



public class SequenceGeneratorException extends DataAccessException
{
	private static final long serialVersionUID = 1L;
	public SequenceGeneratorException(String message)
    {
        super(message);
    }
    public SequenceGeneratorException(Throwable t)
    {
        super(t);
    }
}