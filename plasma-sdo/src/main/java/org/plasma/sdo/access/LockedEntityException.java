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

import java.util.Date;


public class LockedEntityException extends DataAccessException
{
    private String entityName;
    private String userName;
    private Date lockedDate;
    
    private LockedEntityException()
    {
        super("unsuppoted");
    }

    private LockedEntityException(String message)
    {
        super("unsuppoted");
    }
    
    private LockedEntityException(Throwable t)
    {
        super("unsuppoted");
    }


    public LockedEntityException(String entityName, String userName, Date lockedDate)
    {
        super("found existing lock for entity '" 
            + entityName + "' by user '" 
            + userName + "' at time '" 
            + String.valueOf(lockedDate));
        this.entityName = entityName;
        this.userName = userName;
        this.lockedDate = lockedDate;
    }
    
    public String getEntityName()
    {
        return entityName;
    }

    public String getUserName()
    {
        return userName;
    }

    public Date getLockedDate()
    {
        return lockedDate;
    }
}