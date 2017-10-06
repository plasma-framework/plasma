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

package org.plasma.sdo.core.collector;

import java.util.ArrayList;
import java.util.List;

import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * Visits an entire data graph, searching for data objects which have link(s) to
 * the given target data object. Used to accommodate operations on directed
 * associations, i.e. where the association is on-way and no opposite property
 * is available.
 * 
 * @see PlasmaDataGraphVisitor
 */
public class SourceNodeCollector implements PlasmaDataGraphVisitor {

  private List<LinkedNode> result = new ArrayList<LinkedNode>();

  private DataObject target;

  public SourceNodeCollector(DataObject target) {
    super();
    this.target = target;
  }

  /**
   * Called by the graph traversal algorithm (as part of the visitor pattern)
   * when a graph node is encountered.
   */
  @Override
  public void visit(DataObject target, DataObject source, String sourcePropertyName, int level) {
    PlasmaDataObject targetObject = (PlasmaDataObject) target;
    PlasmaDataObject sourceObject = (PlasmaDataObject) source;
    PlasmaProperty sourceProperty = null;
    if (sourceObject != null)
      sourceProperty = (PlasmaProperty) source.getType().getProperty(sourcePropertyName);

    if (targetObject.equals(this.target))
      return;
    // FIXME: we could really use a value iterator here. See github #70
    for (Property prop : target.getType().getProperties()) {
      if (prop.getType().isDataType())
        continue;
      if (!targetObject.isSet(prop))
        continue;
      if (prop.isMany()) {
        @SuppressWarnings("unchecked")
        List<DataObject> list = targetObject.getList(prop);
        for (DataObject dataObject : list)
          if (dataObject.equals(this.target)) {
            addNode(targetObject, sourceObject, sourceProperty, (PlasmaProperty) prop);
          }
      } else {
        DataObject dataObject = targetObject.getDataObject(prop);
        if (dataObject.equals(this.target)) {
          addNode(targetObject, sourceObject, sourceProperty, (PlasmaProperty) prop);
        }
      }
    }
  }

  private void addNode(PlasmaDataObject targetObject, PlasmaDataObject sourceObject,
      PlasmaProperty sourceProperty, PlasmaProperty targetProperty) {
    LinkedNode node = null;
    if (sourceObject == null) {
      node = new LinkedNode(targetObject, null, null);
    } else {
      node = new LinkedNode(targetObject, sourceObject, sourceProperty);
    }
    node.setTargetProperty(targetProperty);
    result.add(node);
  }

  /**
   * Returns the nodes which are part of the contiguous containment hierarchy
   * from the given source.
   * 
   * @return the nodes which are part of the contiguous containment hierarchy
   *         from the given source.
   */
  public List<LinkedNode> getResult() {
    return result;
  }

}
