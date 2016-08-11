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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;


/**
 * Abstract mojo which has an injected Plexus class realm such that
 * subclass mojo's can add resource and other URL's etc..
 * to the class world as needed using the injected
 * {@link ClassRealmMojo.additionalClasspathElements additionalClasspathElements} List parameter, 
 * as well as system properties using the {@link ClassRealmMojo.systemProperties systemProperties) 
 * Properties collection parameter. 
 * 
 * @author Scott Cinnamond
 * @since 1.1.3
 * 
 */
public abstract class ClassRealmMojo extends AbstractMojo
{	
    /** 
     * @parameter expression="${project}" 
     */
    protected MavenProject project;
    
    /** 
     * @parameter expression="${plugin.classRealm}" 
     */
    protected Object classRealm;
    
    /**
    * @parameter  
    */
    protected List<String> additionalClasspathElements;
    
    /**
     * Extra System properties injected as a property collection.
     * @parameter
     */
    protected Properties systemProperties;
    
    public MavenProject getProject() {
    	return this.project;
    }
    
    public Properties getSystemProperties() {
    	return this.systemProperties;
    }
        
    protected void addURL(URL url) {
    	if (this.classRealm instanceof  org.codehaus.plexus.classworlds.realm.ClassRealm) {
    		 org.codehaus.plexus.classworlds.realm.ClassRealm realm = ( org.codehaus.plexus.classworlds.realm.ClassRealm)this.classRealm;
    		 realm.addURL(url);
    	}
    	else if (this.classRealm instanceof org.codehaus.classworlds.ClassRealm) {
    		 org.codehaus.classworlds.ClassRealm realm = ( org.codehaus.classworlds.ClassRealm)this.classRealm;
   		     realm.addConstituent(url);
   	    }
    }
    
    public void execute() throws MojoExecutionException
    {
        Iterator<String> iter = this.additionalClasspathElements.iterator();
        while (iter.hasNext()) {
        	URL url = null;
            try {
                url = new File(iter.next()).toURI().toURL();
            } catch (MalformedURLException e) {
                throw new MojoExecutionException(e.getMessage() + e);
            }
            getLog().info("adding URL:" + url.toString());
            this.addURL(url);
        }
        
        if (this.systemProperties != null) {
	        Enumeration names	= this.systemProperties.propertyNames(); 
	        while (names.hasMoreElements()) {
	        	String name = (String)names.nextElement();
	        	String value = this.systemProperties.getProperty(name); 
	          	getLog().debug( "system prop: " + name + ":" + value);
	        	System.setProperty(name, value);
	        }  
        }
    }
}