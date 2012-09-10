package org.plasma.common.app;

public class InvalidCommandLineArgException extends AppException
{
    public String arg;
    public InvalidCommandLineArgException(String arg)
    {
        super(arg);
        this.arg = arg;
    }
    
    public String getArg() { return arg; }
}