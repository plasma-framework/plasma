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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.plasma.provisioning.cli.QueryTool;

public class QueryTask extends ProvisioningTask {
  private static Log log = LogFactory.getLog(QueryTask.class);

  private String sourceDir;
  private String classesDir;
  private String file;
  private String destFile;
  private String destFileType;
  private String destNamespaceURI;
  private String destNamespacePrefix = "tns";
  private String dialect;
  private String dest;

  // The method executing the task
  public void execute() throws BuildException {

    log.info("execute");
    getCommandLine().setClassname(QueryTool.class.getName());
    getCommandLine().getJavaCommand().addArguments(
        new String[] { COMMAND_PREFIX + command, file, destFile, destFileType, destNamespaceURI,
            destNamespacePrefix, sourceDir, classesDir });
    // setFork(true);
    // setNewenvironment(true);
    super.execute();
  }

  public String getFile() {
    return file;
  }

  public void setFile(String queryFile) {
    this.file = queryFile;
  }

  public String getDestFile() {
    return destFile;
  }

  public void setDestFile(String destFile) {
    this.destFile = destFile;
  }

  public String getDestNamespaceURI() {
    return destNamespaceURI;
  }

  public void setDestNamespaceURI(String destNamespaceURI) {
    this.destNamespaceURI = destNamespaceURI;
  }

  public String getDestNamespacePrefix() {
    return destNamespacePrefix;
  }

  public void setDestNamespacePrefix(String destNamespacePrefix) {
    this.destNamespacePrefix = destNamespacePrefix;
  }

  public String getSourceDir() {
    return sourceDir;
  }

  public void setSourceDir(String sourceDir) {
    this.sourceDir = sourceDir;
  }

  public String getClassesDir() {
    return classesDir;
  }

  public void setClassesDir(String classesDir) {
    this.classesDir = classesDir;
  }

  public String getDestFileType() {
    return destFileType;
  }

  public void setDestFileType(String destFileType) {
    this.destFileType = destFileType;
  }

}
