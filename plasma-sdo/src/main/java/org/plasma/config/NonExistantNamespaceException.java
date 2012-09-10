package org.plasma.config;




public class NonExistantNamespaceException extends ConfigurationException
{
    private static final long serialVersionUID = 1L;
    public NonExistantNamespaceException(String message)
    {
        super(message);
    }
    public NonExistantNamespaceException(Throwable t)
    {
        super(t);
    }
}