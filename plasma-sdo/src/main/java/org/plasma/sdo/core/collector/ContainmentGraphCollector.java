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

/**
 * Visits a data graph from a given source/root, collecting the source and any
 * contained nodes from the source which are part of the contiguous containment
 * hierarchy. Clients must call {@link PlasmaDataObject#accept accept()} passing
 * this visitor to trigger the traversal from a given source data object.
 * 
 * @see PlasmaDataGraphVisitor
 */
public class ContainmentGraphCollector implements PlasmaDataGraphVisitor {

  private List<LinkedNode> result = new ArrayList<LinkedNode>();

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

    // always add the root, otherwise
    // checking contains() ensures a contiguous
    // containment graph starting from the root
    if (sourceObject == null) {
      result.add(new LinkedNode(targetObject, null, null));
    } else if (sourceObject.contains(target)) { // ensure its part of
                                                // containment hierarchy
      result.add(new LinkedNode(targetObject, sourceObject, sourceProperty));
    }
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
