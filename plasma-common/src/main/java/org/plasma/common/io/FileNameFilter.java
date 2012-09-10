package org.plasma.common.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.plasma.common.exception.PlasmaRuntimeException;


public class FileNameFilter implements FilenameFilter
{
    private Pattern pattern;
    
    public FileNameFilter(String expression)
    {
        try {
            pattern = Pattern.compile(expression);
        }
        catch (PatternSyntaxException e) {
            throw new PlasmaRuntimeException(e);
        }
    }
    
    public boolean accept(File dir, String name) 
    {
        return pattern.matcher(name).matches();    
    }   
}       
