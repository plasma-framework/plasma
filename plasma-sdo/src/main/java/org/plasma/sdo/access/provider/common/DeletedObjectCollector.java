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

package org.plasma.sdo.access.provider.common;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.access.DataAccessException;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;

public class DeletedObjectCollector extends SimpleCollector {
  private static Log log = LogFactory.getFactory().getInstance(DeletedObjectCollector.class);
  private PlasmaChangeSummary changeSummary;

  @SuppressWarnings("unused")
  private DeletedObjectCollector() {
  }

  public DeletedObjectCollector(DataGraph dataGraph) {

    this.changeSummary = (PlasmaChangeSummary) dataGraph.getChangeSummary();
    PlasmaDataObject root = (PlasmaDataObject) dataGraph.getRootObject();
    collect();
  }

  private void collect() {
    List<DataObject> list = changeSummary.getChangedDataObjects();
    for (DataObject changed : list) {
      if (!changeSummary.isDeleted(changed))
        continue;

      // convert to array to avoid concurrent mods of collection
      DataObject[] resultArray = super.toArray();

      boolean found = false;
      for (int i = 0; i < resultArray.length; i++) {
        // is changed/deleted object a child/descendant of an existing result
        // delete
        // if so add it AHEAD of the existing
        if (isRelation(changed, resultArray[i], AssociationPath.singular)) {
          // if (isDescendant(changed, resultArray[i])) {
          if (super.contains(changed)) {
            throw new DataAccessException("unexpected changed object: "
                + changed.getType().getURI() + "#" + changed.getType().getName() + "("
                + ((PlasmaDataObject) changed).getUUIDAsString() + ")");
          }
          found = true;
          if (log.isDebugEnabled())
            log.debug("adding changed object: " + changed.getType().getName() + "("
                + ((PlasmaDataObject) changed).getUUIDAsString() + ") at position " + i);
          super.add(i, (PlasmaDataObject) changed);
          break;
        }
      }
      if (!found) {
        if (log.isDebugEnabled())
          log.debug("appending changed object: " + changed.getType().getURI() + "#"
              + changed.getType().getName() + "(" + ((PlasmaDataObject) changed).getUUIDAsString()
              + ")");
        super.add((PlasmaDataObject) changed); // append it
      }
    }
  }

}
