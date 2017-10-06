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
import org.plasma.provisioning.cli.SDOTool;

public class SDOTask extends ProvisioningTask {
  private static Log log = LogFactory.getLog(SDOTask.class);

  private String dialect;
  private String dest;

  public void execute() throws BuildException {
    getCommandLine().setClassname(SDOTool.class.getName());
    if (this.dialect != null)
      getCommandLine().getJavaCommand().addArguments(
          new String[] { COMMAND_PREFIX + command, dialect, dest });
    else
      getCommandLine().getJavaCommand().addArguments(
          new String[] { COMMAND_PREFIX + command, dest });
    // setFork(true);
    // setNewenvironment(true);
    super.execute();
  }

  public String getDialect() {
    return dialect;
  }

  public String getDest() {
    return dest;
  }

  public void setDest(String dest) {
    this.dest = dest;
  }

  public void setDialect(String dialect) {
    this.dialect = dialect;
  }

}
