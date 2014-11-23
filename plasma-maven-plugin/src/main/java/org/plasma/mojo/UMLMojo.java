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

import org.apache.maven.plugin.MojoExecutionException;
import org.plasma.provisioning.cli.RDBDialect;
import org.plasma.provisioning.cli.UMLDialect;
import org.plasma.provisioning.cli.UMLTool;
import org.plasma.provisioning.cli.UMLToolSource;

/**
 * Mojo implementation for generating  
 * UML modeling artifacts from various sources.   
 * 
 * @author Scott Cinnamond
 * @since 1.2.2
 * 
 * @goal uml
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.UMLTool
 */
public class UMLMojo extends ClassRealmMojo
{    
    /**
    * The tool source to be read
    * @parameter expression="${uml.source}" default-value="rdb"
    */
    private String source;

    /**
    * The target directory for generated artifacts
    * @parameter expression="${uml.outputDirectory}" default-value="./"
    */
    private String outputDirectory;
    
    /**
    * The target file for generated artifacts
    * @parameter expression="${uml.outputFile}" default-value="umltool.uml"
    */
    private String outputFile;

    /**
    * The target vendor RDB vendor dialect when reading from relational database 
    * @parameter expression="${uml.dialect}" default-value="oracle"
    */
    private String dialect;
        

    /**
    * The target UML editing platform for generated UML source.
    * @parameter expression="${uml.platform}" default-value="papyrus"
    */
    private String platform;
    
    /**
    * The destination or target namespace URIs. These are
    * separated by commas and mapped (in order) to schema
    * names in the resulting document.
    * @parameter expression="${uml.namespaces}" 
    */
    private String namespaces;

    /**
    * The names for RDB schema(s) to process, separated
    * by commas.   
    * @parameter expression="${uml.schemaNames}"
    */
    private String schemaNames;    
    
    public void execute() throws MojoExecutionException
    {
    	super.execute();
    	        
        try
        {        
        	UMLToolSource toolSource = getToolSource(this.source);
        	RDBDialect toolDialect = getToolDialect(this.dialect);

            getLog().info( "executing tool: "  + UMLTool.class.getName());                
        	if (this.schemaNames == null) {
    			if (this.platform == null)
    				this.platform = UMLDialect.papyrus.name();
    			UMLDialect platform = getToolPlatform(this.platform);
    			String[] args = {
                	"-"+toolSource.name(), 
                	toolDialect.name(), 
                	this.outputDirectory + "/" + outputFile,
                	platform.name()
                };
    			UMLTool.main(args);
        	}
        	else {
    			if (this.platform == null)
    				this.platform = UMLDialect.papyrus.name();
    			UMLDialect platform = getToolPlatform(this.platform);
        		String[] args = {
                    	"-"+toolSource.name(), 
                    	toolDialect.name(), 
                    	this.outputDirectory + "/" + outputFile,
                    	platform.name(),
                    	this.namespaces != null ? this.namespaces : "http://" + outputFile, 
                    	this.schemaNames
                    };
        		UMLTool.main(args);
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
    
    private UMLToolSource getToolSource(String source)
    {
    	UMLToolSource command = null;
    	try {
    		command = UMLToolSource.valueOf(source);
    	}
    	catch (IllegalArgumentException e) {
    		throw new IllegalArgumentException("'" + source + "' - expected one of ["
    				+ UMLToolSource.asString() + "]");
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
    		throw new IllegalArgumentException("'" + dialectValue + "' - expected one of ["
    				+ RDBDialect.asString() + "]");
    	}
    	return dialect;
    }
    
    private UMLDialect getToolPlatform(String platformValue)
    {
    	UMLDialect platform = null;
    	try {
    		platform = UMLDialect.valueOf(platformValue);
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < UMLDialect.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(UMLDialect.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + platformValue + "' - expected one of ["
    				+ buf.toString() + "]");
    	}
    	return platform;
    }
}