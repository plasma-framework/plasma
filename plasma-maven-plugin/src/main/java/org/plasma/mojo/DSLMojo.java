/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.mojo;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.maven.plugin.MojoExecutionException;
import org.plasma.provisioning.cli.DSLTool;
import org.plasma.provisioning.cli.DSLToolAction;

/**
 * Mojo implementation for generating DSL artifacts, such as
 * source code, which sets up the mojo environment and then calls
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
    * The target directory for generated artifacts
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
    	
        getLog().debug( "tool: " + DSLTool.class.getName());
        getLog().debug( "dialect: " + this.dialect);
        getLog().debug( "classRealm: " + this.classRealm);
        
        try
        {        
        	DSLToolAction toolAction = getToolAction(this.action);

        	long lastExecution = 0L;
        	File mojoDir = new File(MojoConstants.MOJO_DIR);
            if (mojoDir.exists()) {
                File mojoFile = new File(mojoDir, 
                		this.getClass().getSimpleName()
                		+ "_" + MojoConstants.MOJO_STALE_FLAG);
                if (mojoFile.exists())
                	lastExecution = mojoFile.lastModified();
            }
        	
            String[] args = {
                	"-"+toolAction.name(), 
                	this.dialect, 
                	this.outputDirectory,
                	String.valueOf(lastExecution)		
                };
        	
            getLog().info( "executing tool: "  + DSLTool.class.getName());
            
            DSLTool.main(args);
            
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