package org.plasma.query.model;

import org.plasma.query.Wildcard;
import org.plasma.sdo.access.model.EntityConstants;

public interface QueryConstants 
{    
    public static final String PATH_DELIMITER = "/";
    //public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
    //public static final String TIMESTAMP_FORMAT = "MM/dd/yyyy HH:mm:ss";
    public static final String WILDCARD = Wildcard.WILDCARD_CHAR;
    public static final int MAX_WILDCARDS = 2;    
    public static final int MAX_RESULTS = 15000;    

    public static final String FREE_TEXT_SELECT_PATH_DELIMITER_REGEXP = "['" + EntityConstants.DATA_ACCESS_TRAVERSAL_PATH_DELIMITER + "']";
    public static final String FREE_TEXT_SELECT_DECLARATION_DELIMITER_REGEXP = "['" + EntityConstants.DATA_ACCESS_DECLARATION_DELIMITER + "']";
}