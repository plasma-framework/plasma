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
import org.plasma.provisioning.cli.RDBDialect;
import org.plasma.provisioning.cli.RDBTool;
import org.plasma.provisioning.cli.RDBToolAction;

/**
 * Mojo implementation for generating RDB related artifacts, such as
 * relational database schemas and UML models, which sets up the mojo environment and then calls
 * the Plasma RDB command-line (CLI) tool, passing it mojo args. 
 * 
 * @author Scott Cinnamond
 * @since 1.1.3
 * 
 * @goal rdb
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.RDBTool
 */
public class RDBMojo extends ClassRealmMojo
{    
    /**
    * The tool action to be performed
    * @parameter expression="${rdb.action}" default-value="create"
    */
    private String action;

    /**
    * The target directory for generated artifacts
    * @parameter expression="${rdb.outputDirectory}" default-value="./"
    */
    private String outputDirectory;
    
    /**
    * The target file for generated artifacts
    * @parameter expression="${rdb.outputFile}" default-value="rdbtool.out"
    */
    private String outputFile;

    /**
    * The target vendor DDL dialect for generated source.
    * @parameter expression="${rdb.dialect}" default-value="oracle"
    */
    private String dialect;
        
    /**
    * The destination or target namespace URIs. These are
    * separated by commas and mapped (in order) to schema
    * names in the resulting document.
    * @parameter expression="${rdb.namespaces}" 
    */
    private String namespaces;

    /**
    * The names for RDB schema(s) to process, separated
    * by commas.   
    * @parameter expression="${rdb.schemaNames}"
    */
    private String schemaNames;
    
    
    public void execute() throws MojoExecutionException
    {
    	super.execute();
    	        
        try
        {        
        	RDBToolAction toolAction = getToolAction(this.action);
        	RDBDialect toolDialect = getToolDialect(this.dialect);

            getLog().info( "executing tool: "  + RDBTool.class.getName());                
        	if (this.schemaNames == null) {
    			String[] args = {
	                	"-"+toolAction.name(), 
	                	toolDialect.name(), 
	                	this.outputDirectory + "/" + outputFile,
                    	this.namespaces != null ? this.namespaces : "http://" + outputFile, 
	                };
                    RDBTool.main(args);
        	}
        	else {
        		String[] args = {
                    	"-"+toolAction.name(), 
                    	toolDialect.name(), 
                    	this.outputDirectory + "/" + outputFile,
                    	this.namespaces != null ? this.namespaces : "http://" + outputFile, 
                    	this.schemaNames
                    };
                RDBTool.main(args);
        	}
        }
        catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }        
    }
    
    private RDBToolAction getToolAction(String action)
    {
    	RDBToolAction command = null;
    	try {
    		command = RDBToolAction.valueOf(action);
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < RDBToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(RDBToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + action + "' - expected one of ["
    				+ buf.toString() + "]");
    	}  
    	return command;
    }
    
    private RDBDialect getToolDialect(String dialectValue)
    {
    	RDBDialect dialect = null;
    	try {
    		dialect = RDBDialect.valueOf(dialectValue);
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < RDBDialect.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(RDBDialect.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + dialectValue + "' - expected one of ["
    				+ buf.toString() + "]");
    	}
    	return dialect;
    }
    
}
