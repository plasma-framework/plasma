/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.query.model;

import org.plasma.query.Wildcard;
import org.plasma.sdo.access.provider.common.EntityConstants;

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