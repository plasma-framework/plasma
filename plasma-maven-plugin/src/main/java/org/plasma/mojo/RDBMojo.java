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
import org.plasma.provisioning.cli.ProvisioningToolOption;
import org.plasma.provisioning.cli.RDBDialect;
import org.plasma.provisioning.cli.RDBTool;
import org.plasma.provisioning.cli.RDBToolAction;

/**
 * Mojo implementation for generating RDB related artifacts, such as relational
 * database schemas and UML models, which sets up the mojo environment and then
 * calls the Plasma RDB command-line (CLI) tool, passing it mojo args.
 * 
 * @author Scott Cinnamond
 * @since 1.1.3
 * 
 * @goal rdb
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.RDBTool
 */
public class RDBMojo extends ClassRealmMojo {
  /**
   * The tool action to be performed
   * 
   * @parameter expression="${rdb.action}" default-value="create"
   */
  private String action;

  /**
   * The target directory for generated artifacts
   * 
   * @parameter expression="${rdb.outputDirectory}" default-value="./"
   */
  private String outputDirectory;

  /**
   * The target file for generated artifacts
   * 
   * @parameter expression="${rdb.outputFile}" default-value="rdbtool.out"
   */
  private String outputFile;

  /**
   * The target vendor DDL dialect for generated source.
   * 
   * @parameter expression="${rdb.dialect}" default-value="oracle"
   */
  private String dialect;

  /**
   * The destination or target namespace URIs. These are separated by commas and
   * mapped (in order) to schema names in the resulting document.
   * 
   * @parameter expression="${rdb.namespaces}"
   */
  private String namespaces;

  /**
   * The names for RDB schema(s) to process, separated by commas.
   * 
   * @parameter expression="${rdb.schemaNames}"
   */
  private String schemaNames;

  public void execute() throws MojoExecutionException {
    super.execute();

    try {

      getLog().info("executing tool: " + RDBTool.class.getName());
      if (this.schemaNames == null) {
        String[] args = { "-" + ProvisioningToolOption.command.name(), this.action,
            "-" + ProvisioningToolOption.dialect.name(), this.dialect,
            "-" + ProvisioningToolOption.dest.name(), this.outputDirectory + "/" + outputFile };
        RDBTool.main(args);
      } else {
        String[] args = { "-" + ProvisioningToolOption.command.name(), this.action,
            "-" + ProvisioningToolOption.dialect.name(), this.dialect,
            "-" + ProvisioningToolOption.dest.name(), this.outputDirectory + "/" + outputFile,
            "-" + ProvisioningToolOption.namespaces.name(),
            this.namespaces != null ? this.namespaces : "http://" + outputFile,
            "-" + ProvisioningToolOption.schemas.name(), this.schemaNames };
        RDBTool.main(args);
      }
    } catch (IllegalArgumentException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    } catch (Exception e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

  private RDBToolAction getToolAction(String action) {
    RDBToolAction command = null;
    try {
      command = RDBToolAction.valueOf(action);
    } catch (IllegalArgumentException e) {
      StringBuilder buf = new StringBuilder();
      for (int i = 0; i < RDBToolAction.values().length; i++) {
        if (i > 0)
          buf.append(", ");
        buf.append(RDBToolAction.values()[i].name());
      }

      throw new IllegalArgumentException("'" + action + "' - expected one of [" + buf.toString()
          + "]");
    }
    return command;
  }

  private RDBDialect getToolDialect(String dialectValue) {
    RDBDialect dialect = null;
    try {
      dialect = RDBDialect.valueOf(dialectValue);
    } catch (IllegalArgumentException e) {
      StringBuilder buf = new StringBuilder();
      for (int i = 0; i < RDBDialect.values().length; i++) {
        if (i > 0)
          buf.append(", ");
        buf.append(RDBDialect.values()[i].name());
      }

      throw new IllegalArgumentException("'" + dialectValue + "' - expected one of ["
          + buf.toString() + "]");
    }
    return dialect;
  }

}
