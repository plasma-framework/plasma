package org.plasma.sdo.access;



public class MaxResultsExceededException extends DataAccessException
{
    private int numResults;
    private int maxResults;

    public MaxResultsExceededException(int numResults, int maxResults)
    {
        super("maximum results exceeded - expected max of " 
            + String.valueOf(maxResults) + " results but found " 
            + String.valueOf(numResults) + " results");
        this.numResults = numResults;
        this.maxResults = maxResults;
    }
    
    public int getNumResults()  { return numResults; }
    public int getMaxResults()  { return maxResults; }
}