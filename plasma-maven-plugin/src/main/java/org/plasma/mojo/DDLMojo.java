package org.plasma.mojo;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.maven.plugin.MojoExecutionException;
import org.plasma.provisioning.cli.DDLTool;
import org.plasma.provisioning.cli.DDLToolAction;
import org.plasma.text.ddl.DDLDialect;

/**
 * Mojo implementation for generating DDL artifacts, such as
 * relational database schemas, which sets up the mojo envoronment and then calls
 * the Plasma DDL command-line (CLI) tool, passing it mojo args. 
 * 
 * @author Scott Cinnamond
 * @since 1.1.3
 * 
 * @goal ddl
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.DSLTool
 */
public class DDLMojo extends ClassRealmMojo
{    
    /**
    * The tool action to be performed
    * @parameter expression="${ddl.action}" default-value="create"
    */
    private String action;

    /**
    * The target directory for generated artifacts
    * @parameter expression="${ddl.outputDirectory}" default-value="./"
    */
    private String outputDirectory;
    
    /**
    * The target file for generated artifacts
    * @parameter expression="${ddl.outputFile}" default-value="model.ddl"
    */
    private String outputFile;

    /**
    * The target vendor DDL dialect for generated source.
    * @parameter expression="${ddl.dialect}" default-value="oracle"
    */
    private String dialect;
        
    
    
    public void execute() throws MojoExecutionException
    {
    	super.execute();
    	
        getLog().debug( "tool: " + DDLTool.class.getName());
        getLog().debug( "dialect: " + this.dialect);
        getLog().debug( "classRealm: " + this.classRealm);
        
        try
        {        
        	DDLToolAction toolAction = getToolAction(this.action);
        	DDLDialect toolDialect = getToolDialect(this.dialect);

        	        	
            String[] args = {
                	"-"+toolAction.name(), 
                	"-"+toolDialect.name(), 
                	this.outputDirectory + "/" + outputFile
                };
        	
            getLog().info( "executing tool: "  + DDLTool.class.getName());
            
            DDLTool.main(args);

        }
        catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }        
    }
    
    private DDLToolAction getToolAction(String action)
    {
    	DDLToolAction command = null;
    	try {
    		command = DDLToolAction.valueOf(action);
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < DDLToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(DDLToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + action + "' - expected one of ["
    				+ buf.toString() + "]");
    	}  
    	return command;
    }
    
    private DDLDialect getToolDialect(String dialectValue)
    {
    	DDLDialect dialect = null;
    	try {
    		dialect = DDLDialect.valueOf(dialectValue);
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < DDLDialect.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(DDLDialect.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + dialectValue + "' - expected one of ["
    				+ buf.toString() + "]");
    	}
    	return dialect;
    }
    
}