package org.plasma.query;





/**
 */
public class MaxWildcardsExceededException extends QueryException 
{
	private static final long serialVersionUID = 1L;

	public MaxWildcardsExceededException() 
    {
        super("Max number of wildcards exceeded");
    }
}

