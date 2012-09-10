package org.plasma.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import org.plasma.provisioning.cli.SDOTool;
import org.plasma.provisioning.cli.SDOToolAction;
import commonj.sdo.DataObject;

/**
 * Mojo implementation for generating SDO artifacts, such as
 * source code, which sets up the mojo envoronment and then calls
 * the Plasma SDO command-line (CLI) tool, passing it mojo args. 
 * 
 * @author Scott Cinnamond
 * @since 1.1.3
 * 
 * @goal sdo
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.SDOTool
 */
public class SDOMojo extends ClassRealmMojo
{    
    /**
    * The tool action to be performed
    * @parameter expression="${sdo.action}" default-value="create"
    */
    private String action;

    /**
    * The target directory foer generated artifacts
    * @parameter expression="${sdo.outputDirectory}" default-value="./"
    */
    private String outputDirectory;
    
    /**
    * The target 3GL language for generated source.
    * @parameter expression="${sdo.dialect}" default-value="java"
    */
    private String dialect;
        
    
    
    public void execute() throws MojoExecutionException
    {
    	super.execute();
    	
        getLog().info( "tool: " + SDOTool.class.getName());
        getLog().info( "dialect: " + this.dialect);
        getLog().info( "classRealm: " + this.classRealm);

        
        try
        {        
        	SDOToolAction toolAction = getToolAction(this.action);
        	
            String[] args = {
                	"-"+toolAction.name(), this.dialect, this.outputDirectory	
                };
        	
            getLog().info( "executing tool: "  + SDOTool.class.getName());
            SDOTool.main(args);
        }
        catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }        
    }
    
    private SDOToolAction getToolAction(String action)
    {
        SDOToolAction command = null;
    	try {
    		command = SDOToolAction.valueOf(action);
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < SDOToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(SDOToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + action + "' - expected one of ["
    				+ buf.toString() + "]");
    	}  
    	return command;
    }
}