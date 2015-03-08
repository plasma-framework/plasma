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

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.plasma.provisioning.cli.OptionPair;
import org.plasma.provisioning.cli.ProvisioningToolOption;
import org.plasma.provisioning.cli.RDBDialect;
import org.plasma.provisioning.cli.UMLPlatform;
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
    * The help option
    * @parameter expression="${uml.help}"  
    */
    private String help;

    /**
    * The verbose option
    * @parameter expression="${uml.verbose}"  
    */
    private String verbose;
    
    /**
    * The silent option
    * @parameter expression="${uml.silent}"  
    */
    private String silent;

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
        	List<OptionPair> pairs = new ArrayList<OptionPair>();
        	if (this.help != null)
        		pairs.add(new OptionPair(ProvisioningToolOption.help, this.help));
        	if (this.verbose != null)
        		pairs.add(new OptionPair(ProvisioningToolOption.verbose, this.verbose));
        	if (this.silent != null)
        		pairs.add(new OptionPair(ProvisioningToolOption.silent, this.silent));
        	if (this.source != null)
        		pairs.add(new OptionPair(ProvisioningToolOption.sourceType, this.source));
        	if (this.dialect != null)
        		pairs.add(new OptionPair(ProvisioningToolOption.dialect, this.dialect));
        	if (this.platform != null)
        		pairs.add(new OptionPair(ProvisioningToolOption.platform, this.platform));
        	if (this.namespaces != null)
        		pairs.add(new OptionPair(ProvisioningToolOption.namespaces, this.namespaces));
        	if (this.schemaNames != null)
        		pairs.add(new OptionPair(ProvisioningToolOption.schemas, this.schemaNames));
    		pairs.add(new OptionPair(ProvisioningToolOption.dest, this.outputDirectory + "/" + outputFile));
    		
    		UMLTool.main(MojoUtils.toArgs(pairs));
        }
        catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }        
    }
}