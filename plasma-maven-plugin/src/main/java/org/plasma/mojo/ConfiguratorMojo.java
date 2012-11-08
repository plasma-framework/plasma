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