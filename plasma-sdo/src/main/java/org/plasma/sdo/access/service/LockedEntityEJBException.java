package org.plasma.sdo.access.service;

import java.util.Date;

public class LockedEntityEJBException extends javax.ejb.EJBException
{
    private String entityName;
    private String userName;
    private Date lockedDate;
    
    private LockedEntityEJBException()
    {
        super("unsuppoted");
    }

    private LockedEntityEJBException(String message)
    {
        super("unsuppoted");
    }
    
    private LockedEntityEJBException(Throwable t)
    {
        super("unsuppoted");
    }


    public LockedEntityEJBException(org.plasma.sdo.access.LockedEntityException e)
    {
        super("found existing lock for entity '" 
            + e.getEntityName() + "' by user '" 
            + e.getUserName() + "' at time '" 
            + String.valueOf(e.getLockedDate()));
        this.entityName = e.getEntityName();
        this.userName = e.getUserName();
        this.lockedDate = e.getLockedDate();
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