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

package org.plasma.sdo.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.plasma.sdo.PathAssembler;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.PlasmaDataGraphEventVisitor;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.helper.PlasmaXMLHelper;
import org.plasma.sdo.xml.DefaultOptions;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.Type;
import commonj.sdo.helper.XMLDocument;

public class CoreDataGraph implements PlasmaDataGraph {

  private static final long serialVersionUID = 1L;

  private Object id;
  private PlasmaDataObject rootObject;
  private CoreChangeSummary changeSummary;

  public CoreDataGraph() {
    this.changeSummary = new CoreChangeSummary(this);
  }

  public CoreDataGraph(PlasmaDataObject root) {
    this();
    this.rootObject = root;
    root.setDataGraph(this);
  }

  public String toString() {
    return this.rootObject.toString();
  }

  /**
   * Returns the UUID for this data graph, which is the same UUID used for the
   * root Data Object.
   * 
   * @return the UUID for this data graph, which is the same UUID used for the
   *         root Data Object.
   */
  public UUID getUUID() {
    return this.rootObject.getUUID();
  }

  /**
   * Returns the UUID for this data graph, which is the same UUID used for the
   * root Data Object, as a character string.
   * 
   * @return the UUID for this data graph, which is the same UUID used for the
   *         root Data Object, as a character string.
   */
  public String getUUIDAsString() {
    return this.rootObject.getUUIDAsString();
  }

  /**
   * Sets an object to be used and managed by client
   * {@link org.plasma.sdo.access.DataAccessService Data Access Services} as an
   * identifier for a Data Graph.
   * 
   * @param id
   *          the identifier
   */
  public void setId(Object id) {
    this.id = id;
  }

  /**
   * Returns an object to be used and managed by client
   * {@link org.plasma.sdo.access.DataAccessService Data Access Services} as an
   * identifier for a Data Graph.
   * 
   * @return the identifier
   */
  public Object getId() {
    return this.id;
  }

  /**
   * Creates a new root data object of the {@link #getType specified type}. An
   * exception is thrown if a root object exists.
   * 
   * @param namespaceURI
   *          namespace of the type.
   * @param typeName
   *          name of the type.
   * @return the new root.
   * @throws IllegalStateException
   *           if the root object already exists.
   * @see #createRootObject(Type)
   * @see #getType(String, String)
   */
  public DataObject createRootObject(String namespaceURI, String typeName) {
    if (this.rootObject != null)
      throw new IllegalStateException("a root data object already exists for this data graph");
    this.rootObject = (PlasmaDataObject) PlasmaDataFactory.INSTANCE.create(namespaceURI, typeName);
    PlasmaDataObject root = ((PlasmaNode) this.rootObject).getDataObject();
    root.setDataGraph(this);
    // FIXME: why does change summary only want a plasma object ??
    this.changeSummary.created(root);
    return this.rootObject;
  }

  /**
   * Returns the root {@link DataObject data object} of this data graph.
   * 
   * @return the root data object.
   * @see DataObject#getDataGraph
   */
  public DataObject createRootObject(Type type) {
    if (this.rootObject != null)
      throw new IllegalStateException("a root data object already exists for this data graph");
    this.rootObject = (PlasmaDataObject) PlasmaDataFactory.instance().create(type);
    PlasmaDataObject root = ((PlasmaNode) this.rootObject).getDataObject();
    root.setDataGraph(this);
    // FIXME: why does change summary only want a plasma object ??
    this.changeSummary.created(root);
    return this.rootObject;
  }

  /**
   * Returns the {@link ChangeSummary change summary} associated with this data
   * graph.
   * 
   * @return the change summary.
   * @see ChangeSummary#getDataGraph
   */
  public ChangeSummary getChangeSummary() {
    return this.changeSummary;
  }

  /**
   * Returns the root {@link DataObject data object} of this data graph.
   * 
   * @return the root data object.
   * @see DataObject#getDataGraph
   */
  public DataObject getRootObject() {
    return rootObject;
  }

  /**
   * Detaches and returns the root {@link DataObject data object} of this data
   * graph.
   * 
   * @return the root data object.
   * @see DataObject#getDataGraph
   */
  public DataObject removeRootObject() {
    DataObject oldRoot = this.rootObject;
    ((PlasmaNode) this.rootObject).getDataObject().setDataGraph(null);
    this.rootObject = null;
    this.changeSummary = null;
    return oldRoot;
  }

  /**
   * Returns the {@link Type type} with the given the {@link Type#getURI() URI},
   * or contained by the resource at the given URI, and with the given
   * {@link Type#getName name}.
   * 
   * @param uri
   *          the namespace URI of a type or the location URI of a resource
   *          containing a type.
   * @param typeName
   *          name of a type.
   * @return the type with the corresponding namespace and name.
   */
  public Type getType(String uri, String typeName) {
    return PlasmaTypeHelper.INSTANCE.getType(uri, typeName);
  }

  /**
   * Calculates and returns the path relative to the data-graph root for the
   * given data object.
   * 
   * @param dataObject
   *          the target data object
   * @return the path string
   */
  public String getPath(DataObject dataObject) {
    PathAssembler visitor = new CorePathAssembler(dataObject);
    ((PlasmaNode) this.rootObject).accept(visitor);
    return visitor.getMinimumPathString();
  }

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
  public void accept(PlasmaDataGraphVisitor visitor) {
    ((PlasmaNode) this.rootObject).accept(visitor);
  }

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
  public void acceptDepthFirst(PlasmaDataGraphVisitor visitor) {
    ((PlasmaNode) this.rootObject).acceptDepthFirst(visitor);
  }

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
  public void accept(PlasmaDataGraphEventVisitor visitor) {
    ((PlasmaNode) this.rootObject).accept(visitor);
  }

  public String dump() {
    return ((PlasmaDataObject) this.rootObject).dump();
  }

  public String dumpDepthFirst() {
    return ((PlasmaDataObject) this.rootObject).dumpDepthFirst();
  }

  @Override
  public String asXml() throws IOException {
    DefaultOptions options = new DefaultOptions(this.getRootObject().getType().getURI());
    options.setRootNamespacePrefix("ns1");

    XMLDocument doc = PlasmaXMLHelper.INSTANCE.createDocument(this.getRootObject(), this
        .getRootObject().getType().getURI(), null);
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      PlasmaXMLHelper.INSTANCE.save(doc, os, options);
      os.flush();
    } finally {
      os.close();
    }
    String xml = new String(os.toByteArray());
    return xml;
  }
}
