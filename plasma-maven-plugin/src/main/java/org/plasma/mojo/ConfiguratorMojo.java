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

import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;


/**
 * Common superclass for Plasma mojo's which uses the configurator
 * javadoc annotation and a custom Plexus conponent configurator
 * add the project's runtime classpath elements
 * to the mojo classpath.   
 * 
 * @author Scott Cinnamond
 * @since 1.1.3
 * 
 * @configurator include-project-dependencies
 * @requiresDependencyResolution compile+runtime
 * 
 * @see org.plasma.sdo.maven.IncludeProjectDependenciesComponentConfigurator
 *
 */
public abstract class ConfiguratorMojo extends AbstractMojo
{
	
    /** @parameter expression="${project}" */
    protected MavenProject project;
    
    
    public void execute() throws MojoExecutionException
    {
        getLog().info( "Goal: create" );
        getLog().info( "GroupId: " + this.project.getGroupId());
                
        Properties props = this.project.getProperties();
        
        props.put("message", "Plasma: Hello, My World.");        
    }
}