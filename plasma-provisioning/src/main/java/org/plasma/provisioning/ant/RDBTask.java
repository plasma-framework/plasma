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
   * The destination or target namespace URIs. These are separated by commas and
   * mapped (in order) to schema names in the resulting document.
   */
  private String namespaces;

  /**
   * The names for RDB schema(s) to process, separated by commas.
   */
  private String schemaNames;

  public void execute() throws BuildException {
    getCommandLine().setClassname(RDBTool.class.getName());
    if (this.schemaNames == null) {
      getCommandLine().getJavaCommand().addArguments(
          new String[] { "-" + action, dialect, this.outputDirectory + "/" + outputFile });
    } else {
      getCommandLine().getJavaCommand()
          .addArguments(
              new String[] { "-" + action, dialect, this.outputDirectory + "/" + outputFile,
                  this.namespaces != null ? this.namespaces : "http://" + outputFile,
                  this.schemaNames });
    }
    // setFork(true);
    // setNewenvironment(true);
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
