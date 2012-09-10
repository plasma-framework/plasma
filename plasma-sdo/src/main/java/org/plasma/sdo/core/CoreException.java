package org.plasma.sdo.core;



public class CoreException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public CoreException(String message)
    {
        super(message);
    }
    public CoreException(Throwable t)
    {
        super(t);
    }
}