package org.plasma.mojo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.maven.plugin.MojoExecutionException;
import org.plasma.provisioning.cli.SDOTool;
import org.plasma.provisioning.cli.SDOToolAction;

/**
 * Mojo implementation for generating SDO artifacts, such as
 * source code, which sets up the mojo environment and then calls
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
    * The target directory for generated artifacts
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
    	
        getLog().debug( "tool: " + SDOTool.class.getName());
        getLog().debug( "dialect: " + this.dialect);
        getLog().debug( "classRealm: " + this.classRealm);
        
        try
        {        
        	SDOToolAction toolAction = getToolAction(this.action);

        	long lastExecution = 0L;
        	File mojoDir = new File(MojoConstants.MOJO_DIR);
            if (mojoDir.exists()) {
                File mojoFile = new File(mojoDir, 
                		this.getClass().getSimpleName()
                		+ "_" + MojoConstants.MOJO_STALE_FLAG);
                if (mojoFile.exists()) {
                	lastExecution = mojoFile.lastModified();
                	getLog().info("last successful execution: "
                		+ String.valueOf(new Date(lastExecution)));
                }
            }
        	
            String[] args = {
                	"-"+toolAction.name(), 
                	this.dialect, 
                	this.outputDirectory,
                	String.valueOf(lastExecution)
                };        	
            getLog().info("executing tool: "  + SDOTool.class.getName());
            SDOTool.main(args);
            
            mojoDir.mkdirs();
            if (mojoDir.exists()) {
                File mojoFile = new File(mojoDir, 
                		this.getClass().getSimpleName()
                		+ "_" + MojoConstants.MOJO_STALE_FLAG);
            	if (mojoFile.exists())
            		mojoFile.delete();
            	FileOutputStream out = null;
            	try {
            	    out = new FileOutputStream(mojoFile);
            	    out.write(this.getClass().getSimpleName().getBytes());
            	    out.flush();
            	}
            	finally {
            		if (out != null)
            		    out.close();
            	}
            }
            else
                getLog().warn("mojo: "  + this.getClass().getName() 
                		+ " - could not create dir, " + MojoConstants.MOJO_DIR);
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