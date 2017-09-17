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

/**
 * The UML editing tool dialect
 */
public enum UMLDialect implements OptionEnum {
  papyrus(
      "the Eclipse Papyrus specific UML dialect, particularly targeting the specifics related to UML profiles"), magicdraw(
      "the No Magic Inc. MagicDraw specific UML dialect, particularly targeting the specifics related to UML profiles");

  private String description;

  private UMLDialect(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  public static String asString() {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < UMLDialect.values().length; i++) {
      if (i > 0)
        buf.append(", ");
      buf.append(UMLDialect.values()[i].name());
    }
    return buf.toString();
  }
}
