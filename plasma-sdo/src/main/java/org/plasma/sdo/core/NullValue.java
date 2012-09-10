package org.plasma.sdo.core;

import java.io.Serializable;


/**
 * Class to explicitly represent null values across all Java Map collection 
 * types some of which do not allow nulls.
 */
public class NullValue 
    implements Serializable
{
    
    public NullValue() {}

    public String toString()
    {
        return CoreConstants.NULL_STRING;
    }    
}