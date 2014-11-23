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
import org.plasma.provisioning.cli.CassandraTool;
import org.plasma.provisioning.cli.CassandraToolAction;
import org.plasma.provisioning.cli.RDBDialect;
import org.plasma.provisioning.cli.RDBTool;
import org.plasma.provisioning.cli.RDBToolAction;

/**
 * Mojo implementation for generating Cassandra NoSQL Database related artifacts, such as
 * database schemas and UML models, which sets up the mojo environment and then calls
 * the Cassandra command-line (CLI) tool, passing it mojo args. 
 * 
 * @author Scott Cinnamond
 * @since 1.2.2
 * 
 * @goal cassandra
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.CassandraTool
 */
public class CassandraMojo extends ClassRealmMojo
{    
    /**
    * The tool action to be performed
    * @parameter expression="${cassandra.action}" default-value="create"
    */
    private String action;

    /**
    * The target directory for generated artifacts
    * @parameter expression="${cassandra.outputDirectory}" default-value="./"
    */
    private String outputDirectory;
    
    /**
    * The target file for generated artifacts
    * @parameter expression="${cassandra.outputFile}" default-value="rdbtool.out"
    */
    private String outputFile;
        
    /**
    * The destination or target namespace URIs. These are
    * separated by commas and mapped (in order) to schema
    * names in the resulting document.
    * @parameter expression="${cassandra.namespaces}" 
    */
    private String namespaces;

    /**
    * The names for schema(s) to process, separated
    * by commas.   
    * @parameter expression="${cassandra.schemaNames}"
    */
    private String schemaNames;
    
    
    public void execute() throws MojoExecutionException
    {
    	super.execute();
    	        
        try
        {        
        	CassandraToolAction toolAction = getToolAction(this.action);

            getLog().info( "executing tool: "  + CassandraTool.class.getName());                
        	if (this.schemaNames == null) {
    			String[] args = {
	                	"-"+toolAction.name(), 
	                	this.outputDirectory + "/" + outputFile
	                };
    			CassandraTool.main(args);
        	}
        	else {
        		String[] args = {
                    	"-"+toolAction.name(), 
                    	this.outputDirectory + "/" + outputFile,
                    	this.namespaces != null ? this.namespaces : "http://" + outputFile, 
                    	this.schemaNames
                    };
        		CassandraTool.main(args);
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
    
    private CassandraToolAction getToolAction(String action)
    {
    	CassandraToolAction command = null;
    	try {
    		command = CassandraToolAction.valueOf(action);
    	}
    	catch (IllegalArgumentException e) {
    		throw new IllegalArgumentException("'" + action + "' - expected one of ["
    				+ CassandraToolAction.asString() + "]");
    	}  
    	return command;
    }
    
    
}