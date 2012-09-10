package org.plasma.common.app;

public class MissingCommandLineArgException extends AppException
{
    String arg;
    
    public MissingCommandLineArgException(String arg)
    {
        super(arg);
        this.arg = arg;
    }
    public String getArg() { return arg; }
}