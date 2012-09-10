package org.plasma.sdo.access.service;


public class InvalidSnapshotEJBException extends javax.ejb.EJBException
{
    private InvalidSnapshotEJBException(String message)
    {
        super(message);
    }

    public InvalidSnapshotEJBException(Throwable t)
    {
        super(t.getMessage());
    }
}