package org.plasma.common.app;

public class InvalidCommandLineArgValueException extends AppException
{
    public String arg;
    public String value;
    public InvalidCommandLineArgValueException(String arg, String value)
    {
        super(value);
        this.arg = arg;
        this.value = value;
    }
    
    public String getArg() { return arg; }
    public String getValue() { return value; }
}