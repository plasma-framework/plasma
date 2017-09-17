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

public enum QueryToolAction implements OptionEnum {

  /**
   * Takes the XML representation for a query, as described by a
   * PlasmaQuery<sup>TM</sup> or other supported declarative query framework,
   * and generates an output model based on the target type.
   * 
   * @see org.plasma.query.Query
   * @see QueryToolTargetType
   */
  compile(
      "takes the XML representation for a query, as described by a PlasmaQuery<sup>TM</sup> or other supported declarative query framework, and generates an output model based on the target type"),
  /**
   * Takes the XML representation for a query, as described by a
   * PlasmaQuery<sup>TM</sup> or other supported declarative query framework,
   * runs the query against the Plasma SDO runtime, and generates/returns SDO
   * compliant XML.
   * 
   * @see org.plasma.query.Query
   */
  run(
      "takes the XML representation for a query, as described by a PlasmaQuery<sup>TM</sup> or other supported declarative query framework, runs the query against the Plasma SDO runtime, and generates/returns SDO compliant XML");

  private String description;

  private QueryToolAction(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  public static String asString() {
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < QueryToolAction.values().length; i++) {
      if (i > 0)
        buf.append(", ");
      buf.append(QueryToolAction.values()[i].name());
    }
    return buf.toString();
  }
}
