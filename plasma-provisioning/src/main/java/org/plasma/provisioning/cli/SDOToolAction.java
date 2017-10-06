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

package org.plasma.provisioning.cli;

public enum SDOToolAction implements OptionEnum {
  /**
   * Generate SDO interfaces, classes and enumerations based on the current
   * configuration.
   */
  create("Generate SDO interfaces, classes and enumerations based on the current configuration"),
  /**
   * Exports the configured repository artifacts as a merged XML based
   * provisioning or "technical" model. This can be used for application
   * specific transformations.
   */
  export(
      "Exports the configured repository artifacts as a merged XML based provisioning or \"technical\" model. This can be used for application specific transformations");

  private String description;

  private SDOToolAction(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  public static String asString() {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < SDOToolAction.values().length; i++) {
      if (i > 0)
        buf.append(", ");
      buf.append(SDOToolAction.values()[i].name());
    }
    return buf.toString();
  }
}
