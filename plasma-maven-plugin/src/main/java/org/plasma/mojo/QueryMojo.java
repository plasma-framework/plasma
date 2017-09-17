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
import org.plasma.provisioning.cli.QueryTool;
import org.plasma.provisioning.cli.QueryToolAction;

/**
 * Mojo implementation for generating SDO artifacts, such as source code, which
 * sets up the mojo environment and then calls the Plasma Query command-line
 * (CLI) tool, passing it mojo args.
 * 
 * @author Scott Cinnamond
 * @since 1.1.3
 * 
 * @goal query
 * @phase generate-sources
 * 
 * @see org.plasma.provisioning.cli.QueryTool
 */
public class QueryMojo extends ClassRealmMojo {
  /**
   * The tool action to be performed
   * 
   * @parameter expression="${query.action}" default-value="compile"
   */
  private String action;

  /**
   * The query XML source file name
   * 
   * @parameter expression="${query.sourceFile}"
   */
  private String sourceFile;

  /**
   * The destination or target file name
   * 
   * @parameter expression="${query.destFile}"
   */
  private String destFile;

  /**
   * The destination or target file type
   * 
   * @parameter expression="${query.destFileType}"
   * @see org.plasma.provisioning.cli.ProvisioningTarget
   */
  private String destFileType;

  /**
   * The destination or target namespage URI
   * 
   * @parameter expression="${query.destNamespaceURI}"
   */
  private String destNamespaceURI;

  /**
   * The destination or target namespage prefix
   * 
   * @parameter expression="${query.destNamespacePrefix}" default-value="tns"
   */
  private String destNamespacePrefix;

  public void execute() throws MojoExecutionException {
    super.execute();

    getLog().debug("tool: " + QueryTool.class.getName());
    getLog().debug("classRealm: " + this.classRealm);

    try {
      QueryToolAction toolAction = getToolAction(this.action);

      String[] args = { "-" + toolAction.name(), this.sourceFile, this.destFile, this.destFileType,
          this.destNamespaceURI, this.destNamespacePrefix };

      getLog().info("executing tool: " + QueryTool.class.getName());
      QueryTool.main(args);
    } catch (IllegalArgumentException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    } catch (Exception e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }

  private QueryToolAction getToolAction(String action) {
    QueryToolAction command = null;
    try {
      command = QueryToolAction.valueOf(action);
    } catch (IllegalArgumentException e) {
      StringBuilder buf = new StringBuilder();
      for (int i = 0; i < QueryToolAction.values().length; i++) {
        if (i > 0)
          buf.append(", ");
        buf.append(QueryToolAction.values()[i].name());
      }

      throw new IllegalArgumentException("'" + action + "' - expected one of [" + buf.toString()
          + "]");
    }
    return command;
  }
}