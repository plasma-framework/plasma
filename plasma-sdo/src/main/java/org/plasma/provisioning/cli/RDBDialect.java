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

public enum RDBDialect implements OptionEnum {
  /** the Plasma RDB dialect for Oracle RDBMS */
  oracle("the Plasma RDB dialect for Oracle RDBMS"),
  /** the Plasma RDB dialect for MySql RDBMS */
  mysql("the Plasma RDB dialect for MySql RDBMS");

  private String description;

  private RDBDialect(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  public static String asString() {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < RDBDialect.values().length; i++) {
      if (i > 0)
        buf.append(", ");
      buf.append(RDBDialect.values()[i].name());
    }
    return buf.toString();
  }
}
