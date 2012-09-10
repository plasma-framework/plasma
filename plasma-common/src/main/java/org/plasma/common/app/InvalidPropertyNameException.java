package org.plasma.common.app;

public class InvalidPropertyNameException extends AppException
{
    public String name;
    public InvalidPropertyNameException(String name)
    {
        super(name);
    }
    
    public String getName() { return name; }
}