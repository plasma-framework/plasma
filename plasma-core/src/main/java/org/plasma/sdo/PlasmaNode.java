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

package org.plasma.sdo;

import java.util.List;
import java.util.UUID;

import org.plasma.sdo.core.Node;

public interface PlasmaNode extends Node {
  /**
   * Returns a list of {@link PlasmaEdge edges} associated with the given
   * property regardless of it's multiplicity.
   * 
   * @param property
   *          the property
   * @return a list of edges associated with the given property regardless of
   *         it's multiplicity, or an empty list if no edges are associated with
   *         the given property.
   * @see PlasmaEdge
   */
  public List<PlasmaEdge> getEdges(PlasmaProperty property);

  public PlasmaDataObject getDataObject();

  public UUID getUUID();

  public String getUUIDAsString();

  public void accept(PlasmaDataGraphVisitor visitor);

  public void acceptDepthFirst(PlasmaDataGraphVisitor visitor);

  public void accept(PlasmaDataGraphEventVisitor visitor);
}
