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