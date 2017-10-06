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

public enum DSLToolAction implements OptionEnum {
  /**
   * Provisions query Domain Specific Language (DSL) classes for the current
   * configuration.
   */
  create("provisions query Domain Specific Language (DSL) classes for the current configuration");

  private String description;

  private DSLToolAction(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  public static String asString() {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < DSLToolAction.values().length; i++) {
      if (i > 0)
        buf.append(", ");
      buf.append(DSLToolAction.values()[i].name());
    }
    return buf.toString();
  }
}
