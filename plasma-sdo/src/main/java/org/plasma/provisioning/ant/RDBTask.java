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
package org.plasma.provisioning.ant;


import org.apache.tools.ant.BuildException;
import org.plasma.provisioning.cli.RDBTool;

public class RDBTask extends ProvisioningTask {
    
    /**
    * The tool action to be performed
    */
    private String action;

    /**
    * The target vendor DDL dialect for generated source.
    */
    private String dialect;
    
    /**
    * The target directory for generated artifacts
    */
    private String outputDirectory;
    
    /**
    * The target file for generated artifacts
    */
    private String outputFile;
    
    /**
    * The destination or target namespace URIs. These are
    * separated by commas and mapped (in order) to schema
    * names in the resulting document.
    */
    private String namespaces;

    /**
    * The names for RDB schema(s) to process, separated
    * by commas.   
    */
    private String schemaNames;

    public void execute() throws BuildException {
        getCommandLine().setClassname(RDBTool.class.getName()); 
    	if (this.schemaNames == null) {
    		getCommandLine().getJavaCommand().addArguments(new String[] {
            	"-"+action, 
            	dialect, 
            	this.outputDirectory + "/" + outputFile
    		});
    	}
    	else {
    		getCommandLine().getJavaCommand().addArguments(new String[] {
            	"-"+action, 
            	dialect, 
            	this.outputDirectory + "/" + outputFile,
            	this.namespaces != null ? this.namespaces : "http://" + outputFile, 
            	this.schemaNames
            });
    	}
        //setFork(true);  
        //setNewenvironment(true);            
        super.execute();
    }

    public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public String getNamespaces() {
		return namespaces;
	}

	public void setNamespaces(String namespaces) {
		this.namespaces = namespaces;
	}

	public String getSchemaNames() {
		return schemaNames;
	}

	public void setSchemaNames(String schemaNames) {
		this.schemaNames = schemaNames;
	}

	
}
