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

import java.io.IOException;
import java.util.UUID;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;

/**
 * Implementation specific extensions to SDO {@link DataGraph data graph} API.
 * <p>
 * A data graph is used to package a graph of {@link DataObject data objects}
 * along with their metadata, that is, data describing the data. A data graph
 * also contains a {@link #getChangeSummary change summary} which is used to
 * record changes made to the objects in the graph.
 * </p>
 */
public interface PlasmaDataGraph extends DataGraph {

  /**
   * Returns the UUID for this data graph, which is the same UUID used for the
   * root Data Object.
   * 
   * @return the UUID for this data graph, which is the same UUID used for the
   *         root Data Object.
   */
  public UUID getUUID();

  /**
   * Returns the UUID for this data graph, which is the same UUID used for the
   * root Data Object, as a character string.
   * 
   * @return the UUID for this data graph, which is the same UUID used for the
   *         root Data Object, as a character string.
   */
  public String getUUIDAsString();

  /**
   * Sets an object to be used and managed by client
   * {@link org.plasma.sdo.access.DataAccessService Data Access Services} as an
   * identifier for a Data Graph.
   * 
   * @param id
   *          the identifier
   */
  public void setId(Object id);

  /**
   * Returns an object to be used and managed by client
   * {@link org.plasma.sdo.access.DataAccessService Data Access Services} as an
   * identifier for a Data Graph.
   * 
   * @return the identifier
   */
  public Object getId();

  /**
   * Detaches and returns the root {@link DataObject data object} of this data
   * graph.
   * 
   * @return the root data object.
   * @see DataObject#getDataGraph
   */
  public DataObject removeRootObject();

  /**
   * Calculates and returns the path to the given Data Object relative to the
   * Data Graph root.
   * 
   * @param dataObject
   *          the target Data Object
   * @return the path to the given Data Object relative to the Data Graph root.
   */
  public String getPath(DataObject dataObject);

  /**
   * Begin breadth-first traversal of this DataGraph, the given visitor
   * receiving "visit" events for each graph node traversed.
   * 
   * @param visitor
   *          the graph visitor receiving traversal events
   * @see commonj.sdo.DataGraph
   * @see commonj.sdo.DataObject
   * @see PlasmaDataGraph
   * @see PlasmaDataObject
   * @see PlasmaDataGraphVisitor.visit()
   */
  public void accept(PlasmaDataGraphVisitor visitor);

  /**
   * Begin depth-first traversal of this DataGraph, the given visitor receiving
   * "visit" events for each graph node traversed.
   * 
   * @param visitor
   *          the graph visitor receiving traversal events
   * @see commonj.sdo.DataGraph
   * @see commonj.sdo.DataObject
   * @see PlasmaDataGraph
   * @see PlasmaDataObject
   * @see PlasmaDataGraphVisitor.visit()
   */
  public void acceptDepthFirst(PlasmaDataGraphVisitor visitor);

  /**
   * Begin breadth-first traversal of this DataGraph, the given visitor
   * receiving various events for each graph node traversed.
   * 
   * @param visitor
   *          the graph visitor receiving traversal events
   * @see commonj.sdo.DataGraph
   * @see commonj.sdo.DataObject
   * @see PlasmaDataGraph
   * @see PlasmaDataObject
   * @see PlasmaDataGraphEventVisitor.start()
   * @see PlasmaDataGraphEventVisitor.end()
   */
  public void accept(PlasmaDataGraphEventVisitor visitor);

  /**
   * Traverses, serializes and returns the data graph formatted as XML.
   * 
   * @return the data graph formatted as XML
   */
  public String asXml() throws IOException;

  /**
   * Traverses, serializes and returns the data graph in an internal text format
   * useful for debugging.
   * 
   * @return the data graph in an internal text format useful for debugging
   */
  public String dump();

  /**
   * Traverses the data graph depth first, serializing and returning the data
   * graph in an internal text format useful for debugging.
   * 
   * @return the data graph in an internal text format useful for debugging
   */
  public String dumpDepthFirst();
}
