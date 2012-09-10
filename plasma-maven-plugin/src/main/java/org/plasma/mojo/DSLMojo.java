package org.plasma.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import org.plasma.provisioning.cli.DSLTool;
import org.plasma.provisioning.cli.DSLToolAction;
import commonj.sdo.DataObject;

/**
 * Mojo implementation for generating DSL artifacts, such as
 * source code, which sets up the mojo envoronment and then calls
 * the Plasma DSL command-line (CLI) tool, passing it mojo args. 
 * 
 * @author Scott Cinnamond
 * @since 1.1.3
 * 
 * @goal dsl
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.DSLTool
 */
public class DSLMojo extends ClassRealmMojo
{    
    /**
    * The tool action to be performed
    * @parameter expression="${dsl.action}" default-value="create"
    */
    private String action;

    /**
    * The target directory foer ganerated artifacts
    * @parameter expression="${dsl.outputDirectory}" default-value="./"
    */
    private String outputDirectory;
    
    /**
    * The target 3GL language for generated source.
    * @parameter expression="${dsl.dialect}" default-value="java"
    */
    private String dialect;
        
    
    
    public void execute() throws MojoExecutionException
    {
    	super.execute();
    	
        getLog().info( "tool: " + DSLTool.class.getName());
        getLog().info( "dialect: " + this.dialect);
        getLog().info( "classRealm: " + this.classRealm);
        
        try
        {        
        	DSLToolAction toolAction = getToolAction(this.action);
        	
            String[] args = {
                	"-"+toolAction.name(), this.dialect, this.outputDirectory	
                };
        	
            getLog().info( "executing tool: "  + DSLTool.class.getName());
            
            DSLTool.main(args);
        }
        catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }        
    }
    
    private DSLToolAction getToolAction(String action)
    {
    	DSLToolAction command = null;
    	try {
    		command = DSLToolAction.valueOf(action);
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < DSLToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(DSLToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + action + "' - expected one of ["
    				+ buf.toString() + "]");
    	}  
    	return command;
    }
}