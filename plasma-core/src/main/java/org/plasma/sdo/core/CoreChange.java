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
  private static Log log = LogFactory.getFactory().getInstance(Change.class);
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
    // it is root
    if (root.equals(dataObject)) {
      this.pathFromRoot = CorePathAssembler.PATH_PREFIX;
      this.pathDepthFromRoot = 0;
    } else if (root.contains(dataObject)) { // one hop
      initializePath(root, null, dataObject);
    } else {
      PlasmaDataObject container = (PlasmaDataObject) dataObject.getContainer();
      CoreChange containerChange = changes.get(container.getUUID());
      if (containerChange != null) {
        initializePath(container, containerChange, dataObject);
      } else {
        if (log.isDebugEnabled())
          log.debug("calculating min path for graph: "
              + ((PlasmaDataObject) dataObject.getDataGraph().getRootObject()).dump());
        PathAssembler visitor = new CorePathAssembler(dataObject);
        root.accept(visitor);
        pathFromRoot = visitor.getMinimumPathString();
        if (log.isDebugEnabled())
          log.debug("selected min path: " + this.pathFromRoot);
        pathDepthFromRoot = visitor.getMinimumPathDepth();
      }
    }
  }

  private void initializePath(DataObject source, CoreChange sourceChange, DataObject target) {
    // create a simple path from root to the target
    // no need to traverse the graph, they are connected
    StringBuilder buf = new StringBuilder();
    if (sourceChange == null) {
      buf.append(CorePathAssembler.PATH_PREFIX);
      this.pathDepthFromRoot = 1;
    } else {
      buf.append(sourceChange.getPathFromRoot());
      buf.append(CorePathAssembler.PATH_DELIM);
      this.pathDepthFromRoot = sourceChange.getPathDepthFromRoot() + 1;
    }
    if (target.getContainmentProperty() == null)
      throw new IllegalStateException("data-object, " + target.toString()
          + " has no containment property");

    if (!target.getContainmentProperty().isMany()) {
      buf.append(target.getContainmentProperty().getName());
    } else {
      @SuppressWarnings("unchecked")
      List<DataObject> list = source.getList(target.getContainmentProperty());
      int index = -1;
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i).equals(target)) {
          index = i;
          break;
        }
      }
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
    this.pathFromRoot = buf.toString();
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
