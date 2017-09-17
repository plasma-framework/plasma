/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public abstract class ClassRealmMojo extends AbstractMojo {
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
   * 
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
    if (this.classRealm instanceof org.codehaus.plexus.classworlds.realm.ClassRealm) {
      org.codehaus.plexus.classworlds.realm.ClassRealm realm = (org.codehaus.plexus.classworlds.realm.ClassRealm) this.classRealm;
      realm.addURL(url);
    } else if (this.classRealm instanceof org.codehaus.classworlds.ClassRealm) {
      org.codehaus.classworlds.ClassRealm realm = (org.codehaus.classworlds.ClassRealm) this.classRealm;
      realm.addConstituent(url);
    }
  }

  public void execute() throws MojoExecutionException {
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
      Enumeration names = this.systemProperties.propertyNames();
      while (names.hasMoreElements()) {
        String name = (String) names.nextElement();
        String value = this.systemProperties.getProperty(name);
        getLog().debug("system prop: " + name + ":" + value);
        System.setProperty(name, value);
      }
    }
  }
}