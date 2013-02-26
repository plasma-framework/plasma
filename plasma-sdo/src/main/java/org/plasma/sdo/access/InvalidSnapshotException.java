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

import java.sql.Timestamp;
import java.util.Date;


public class InvalidSnapshotException extends DataAccessException
{
	private static final long serialVersionUID = 1L;
	private Timestamp snapshotDate;
    private String updatedByUser;
    private Date updatedDate;
    
    private InvalidSnapshotException(String message)
    {
        super(message);
    }

    private InvalidSnapshotException(Throwable t)
    {
        super(t);
    }

    public InvalidSnapshotException(String entityName, String currentUser, Timestamp snapshotDate, String updatedByUser, Date updatedDate)
    {
        super("Invalid shapshot date, " + String.valueOf(snapshotDate)
            + (currentUser != null ? " for user '" + currentUser + "'": "")
            + " - entity " 
            + (entityName != null ? "'" + entityName + "'": "") 
            + " already updated by user '" + updatedByUser 
            + "' at " + String.valueOf(updatedDate.getTime()));
        this.snapshotDate = snapshotDate;
        this.updatedByUser = updatedByUser;
        this.updatedDate = updatedDate;
    }
    
    
    public Timestamp getSnapshotDate() { return snapshotDate; }
    public String getUpdatedByUser() { return updatedByUser; }
    public Date getUpdatedDate() { return updatedDate; }
}