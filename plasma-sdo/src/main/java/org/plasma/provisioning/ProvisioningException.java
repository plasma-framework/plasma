package org.plasma.provisioning;

import org.plasma.common.exception.PlasmaRuntimeException;



public class ProvisioningException extends PlasmaRuntimeException
{
    private static final long serialVersionUID = 1L;
    public ProvisioningException(String message)
    {
        super(message);
    }
    public ProvisioningException(Throwable t)
    {
        super(t);
    }
}