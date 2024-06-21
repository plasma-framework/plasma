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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.Change;
import org.plasma.sdo.ChangeType;
import org.plasma.sdo.PathAssembler;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaNode;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.Property;

public class CoreChange implements Change, Serializable {
  private static final long serialVersionUID = 1L;
  private static Log log = LogFactory.getFactory().getInstance(CoreChange.class);
  private static final boolean DEBUG_ENABLED = log.isDebugEnabled();
  private static final boolean TRACE_ENABLED = log.isTraceEnabled();
  private static List<ChangeSummary.Setting> emptySettingList = new ArrayList<ChangeSummary.Setting>();

  private DataObject dataObject;
  private ChangeType changeType;
  private Map<String, List<ChangeSummary.Setting>> settings;
  private String pathFromRoot;
  private int pathDepthFromRoot;

  public CoreChange(DataObject dataObject, ChangeType changeType, Map<UUID, CoreChange> changes) {
    this.dataObject = dataObject;
    this.changeType = changeType;

    PlasmaDataObject root = (PlasmaDataObject) dataObject.getDataGraph().getRootObject();
    // if root, short circuit
    if (root.equals(dataObject)) {
      this.pathFromRoot = CorePathAssembler.PATH_PREFIX;
      this.pathDepthFromRoot = 0;
    } else if (root.contains(dataObject)) { // one hop
      initializeOneHopPath(root, dataObject);
    } else {
      PlasmaDataObject container = (PlasmaDataObject) dataObject.getContainer();
      CoreChange containerChange = changes.get(container.getUUID());
      if (containerChange != null) {
        initializeOneHopContainerChangePath(container, containerChange, dataObject);
      } else {
        List<String> pathElements = collectContainmentPropertyPathElements(root, dataObject);
        if (pathElements != null) {
          pathFromRoot = toFullPath(pathElements);
          pathDepthFromRoot = pathElements.size();
          if (DEBUG_ENABLED)
            log.debug("assembled ancestry path: " + pathFromRoot);
        } else {
          if (TRACE_ENABLED)
            log.trace("calculating min path for graph: "
                + ((PlasmaDataObject) dataObject.getDataGraph().getRootObject()).dump());
          PathAssembler visitor = new CorePathAssembler(dataObject);
          root.accept(visitor);
          pathFromRoot = visitor.getMinimumPathString();
          if (DEBUG_ENABLED)
            log.debug("selected min path: " + this.pathFromRoot);
          pathDepthFromRoot = visitor.getMinimumPathDepth();
        }
      }
    }
  }

  /**
   * Traverses the containment hierarchy, attempting to find the given root, as
   * the final ancestor of the given target, and return a list of property path
   * elements, otherwise returns null;
   * <p>
   * </> Many graphs are structured as trees with a strict hierarchical
   * containment structure, such that the containment links can be walked,
   * terminating at the root. Through a data-graph can accommodate network,
   * graph or other structures, a tree structure is very common, if not the most
   * common case.
   * 
   * @param root
   *          the graph root
   * @param target
   *          the graph child target
   * @return the path elements or null if the target not directly connected to
   *         the root via. the containment ancestry.
   */
  private List<String> collectContainmentPropertyPathElements(DataObject root, DataObject target) {
    List<String> result = new ArrayList<>();
    DataObject current = target;
    DataObject source = null;
    while (current != null && !current.equals(root)) {
      if (current.getContainmentProperty() == null)
        throw new IllegalStateException("data-object, " + current + " has no containment property");
      source = current.getContainer();
      if (source != null)
        result.add(createPropertyPath(source, current));
      current = source;
    }
    if (current.equals(root)) {
      Collections.reverse(result);
      return result;
    } else {
      // graph not a strict hierarchy	
      return null;
    }
  }

  private String toFullPath(List<String> propertyPath) {
    StringBuilder buf = new StringBuilder();
    buf.append(CorePathAssembler.PATH_PREFIX);
    int elem = 0;
    for (String pathElem : propertyPath) {
      if (elem > 0)
        buf.append(CorePathAssembler.PATH_DELIM);
      buf.append(pathElem);
      elem++;
    }
    return buf.toString();
  }

  private void initializeOneHopPath(DataObject source, DataObject target) {
    // create a simple path from root to the target
    // no need to traverse the graph, they are connected
    StringBuilder buf = new StringBuilder();
    buf.append(CorePathAssembler.PATH_PREFIX);
    this.pathDepthFromRoot = 1; // one hop
    if (target.getContainmentProperty() == null)
      throw new IllegalStateException("data-object, " + target + " has no containment property");

    buf.append(createPropertyPath(source, target));
    this.pathFromRoot = buf.toString();
  }

  private void initializeOneHopContainerChangePath(DataObject source, CoreChange sourceChange,
      DataObject target) {
    // create a simple path from root to the target
    // no need to traverse the graph, they are connected
    StringBuilder buf = new StringBuilder();
    buf.append(sourceChange.getPathFromRoot());
    buf.append(CorePathAssembler.PATH_DELIM);
    this.pathDepthFromRoot = sourceChange.getPathDepthFromRoot() + 1;
    if (target.getContainmentProperty() == null)
      throw new IllegalStateException("data-object, " + target + " has no containment property");

    buf.append(createPropertyPath(source, target));
    this.pathFromRoot = buf.toString();
  }

  private String createPropertyPath(DataObject source, DataObject target) {
    StringBuilder buf = new StringBuilder();
    if (!target.getContainmentProperty().isMany()) {
      buf.append(target.getContainmentProperty().getName());
    } else {
      @SuppressWarnings("unchecked")
      List<DataObject> list = source.getList(target.getContainmentProperty());
      // FIXME: find some collection which has fast indexOf as well as preserves
      // ordering
      // which seems to be a white elephant
      int index = list.indexOf(target);
      if (index == -1)
        throw new IllegalStateException("expected data-object, " + target.getType().toString()
            + " (" + ((PlasmaNode) target).getUUIDAsString() + ") " + "as contained by root, "
            + source.getType().toString() + " (" + ((PlasmaNode) source).getUUIDAsString()
            + ") through containment property, " + target.getContainmentProperty().getName());
      buf.append(target.getContainmentProperty().getName());
      buf.append(CorePathAssembler.PATH_INDEX_RIGHT);
      buf.append(String.valueOf(index));
      buf.append(CorePathAssembler.PATH_INDEX_LEFT);
    }
    return buf.toString();
  }

  public boolean equals(Object obj) {
    CoreChange other = (CoreChange) obj;
    return this.dataObject.equals(other.dataObject);
  }

  public int hashCode() {
    return this.dataObject.hashCode();
  }

  public DataObject getDataObject() {
    return dataObject;
  }

  public ChangeType getChangeType() {
    return changeType;
  }

  public List<ChangeSummary.Setting> getSettings(String propertyName) {
    if (settings == null || settings.size() == 0)
      return emptySettingList;

    return settings.get(propertyName);
  }

  public void add(Property property, Object value) {
    if (settings == null) {
      settings = new HashMap<String, List<ChangeSummary.Setting>>();
    }

    List<ChangeSummary.Setting> dataObjectPropertySettings = settings.get(property.getName());
    if (dataObjectPropertySettings == null) {
      dataObjectPropertySettings = new ArrayList<ChangeSummary.Setting>();
      settings.put(property.getName(), dataObjectPropertySettings);
    }

    dataObjectPropertySettings.add(new CoreSetting(dataObject, property, value));
  }

  public List<ChangeSummary.Setting> getAllSettings() {

    if (settings == null || settings.size() == 0)
      return emptySettingList;

    List<ChangeSummary.Setting> result = new ArrayList<ChangeSummary.Setting>();

    Iterator<String> iter = settings.keySet().iterator();
    while (iter.hasNext()) {
      List<ChangeSummary.Setting> propertySettings = settings.get(iter.next());
      for (ChangeSummary.Setting setting : propertySettings)
        result.add(setting);
    }

    return result;
  }

  public String getPathFromRoot() {
    return pathFromRoot;
  }

  public int getPathDepthFromRoot() {
    return pathDepthFromRoot;
  }
}
