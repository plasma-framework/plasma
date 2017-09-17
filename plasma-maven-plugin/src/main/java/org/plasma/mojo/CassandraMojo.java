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

import org.apache.maven.plugin.MojoExecutionException;
import org.plasma.provisioning.cli.CassandraTool;
import org.plasma.provisioning.cli.ProvisioningToolOption;

/**
 * Mojo implementation for generating Cassandra NoSQL Database related
 * artifacts, such as database schemas and UML models, which sets up the mojo
 * environment and then calls the Cassandra command-line (CLI) tool, passing it
 * mojo args.
 * 
 * @author Scott Cinnamond
 * @since 1.2.2
 * 
 * @goal cassandra
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.CassandraTool
 */
public class CassandraMojo extends ClassRealmMojo {
  /**
   * The tool action to be performed
   * 
   * @parameter expression="${cassandra.action}" default-value="create"
   */
  private String action;

  /**
   * The target directory for generated artifacts
   * 
   * @parameter expression="${cassandra.outputDirectory}" default-value="./"
   */
  private String outputDirectory;

  /**
   * The target file for generated artifacts
   * 
   * @parameter expression="${cassandra.outputFile}" default-value="rdbtool.out"
   */
  private String outputFile;

  /**
   * The destination or target namespace URIs. These are separated by commas and
   * mapped (in order) to schema names in the resulting document.
   * 
   * @parameter expression="${cassandra.namespaces}"
   */
  private String namespaces;

  /**
   * The names for schema(s) to process, separated by commas.
   * 
   * @parameter expression="${cassandra.schemaNames}"
   */
  private String schemaNames;

  public void execute() throws MojoExecutionException {
    super.execute();

    try {
      getLog().info("executing tool: " + CassandraTool.class.getName());
      if (this.schemaNames == null) {
        String[] args = { "-" + ProvisioningToolOption.command.name(), this.action,
            "-" + ProvisioningToolOption.dest.name(), this.outputDirectory + "/" + outputFile, };
        CassandraTool.main(args);
      } else {
        String[] args = { "-" + ProvisioningToolOption.command.name(), this.action,
            "-" + ProvisioningToolOption.dest.name(), this.outputDirectory + "/" + outputFile,
            "-" + ProvisioningToolOption.namespaces.name(),
            this.namespaces != null ? this.namespaces : "http://" + outputFile,
            "-" + ProvisioningToolOption.schemas.name(), this.schemaNames };
        CassandraTool.main(args);
      }
    } catch (IllegalArgumentException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    } catch (Exception e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

}