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

import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Common superclass for Plasma mojo's which uses the configurator javadoc
 * annotation and a custom Plexus conponent configurator add the project's
 * runtime classpath elements to the mojo classpath.
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
public abstract class ConfiguratorMojo extends AbstractMojo {

  /** @parameter expression="${project}" */
  protected MavenProject project;

  public void execute() throws MojoExecutionException {
    getLog().info("Goal: create");
    getLog().info("GroupId: " + this.project.getGroupId());

    Properties props = this.project.getProperties();

    props.put("message", "Plasma: Hello, My World.");
  }
}