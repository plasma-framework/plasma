package org.plasma.config;

import org.plasma.common.exception.PlasmaRuntimeException;



public class ConfigurationException extends PlasmaRuntimeException
{
    private static final long serialVersionUID = 1L;
    public ConfigurationException(String message)
    {
        super(message);
    }
    public ConfigurationException(Throwable t)
    {
        super(t);
    }
}