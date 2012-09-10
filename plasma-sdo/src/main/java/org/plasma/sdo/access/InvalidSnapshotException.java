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