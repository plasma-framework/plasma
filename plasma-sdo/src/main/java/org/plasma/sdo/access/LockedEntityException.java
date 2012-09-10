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