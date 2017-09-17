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
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.DataAccessException;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;

public class CreatedObjectCollector extends SimpleCollector implements CommitCollector {
  private static Log log = LogFactory.getFactory().getInstance(CreatedObjectCollector.class);

  @SuppressWarnings("unused")
  private CreatedObjectCollector() {
  }

  public CreatedObjectCollector(DataGraph dataGraph) {

    this.changeSummary = (PlasmaChangeSummary) dataGraph.getChangeSummary();
    collect();
  }

  private void collect() {
    List<DataObject> list = changeSummary.getChangedDataObjects();
    for (DataObject changed : list) {
      if (!changeSummary.isCreated(changed))
        continue;
      if (log.isDebugEnabled())
        log.debug("processing changed object: " + changed.toString());
      PlasmaType changedType = (PlasmaType) changed.getType();
      int changedDepth = changeSummary.getPathDepth(changed);
      // convert to array to avoid concurrent mods of collection
      DataObject[] resultArray = super.toArray();

      boolean childFound = false;
      for (int i = 0; i < resultArray.length; i++) {
        PlasmaType resultType = (PlasmaType) resultArray[i].getType();
        // If existing result object is a "child" of the changed/created
        // object, prepend the changed object ahead of the
        // existing, so will be created first.
        // This determination is made by checking either metadata
        // for the 2 data objects, or their reference values (instance data).
        // If the source and target types are different, compare their metadata
        // otherwise we are forced to compare their actual reference
        // values to determine ordering
        boolean prepend = false;
        if (resultType.getQualifiedName() != changedType.getQualifiedName()) {
          if (isRelation(resultArray[i], changed, AssociationPath.singular))
            prepend = true;
        } else {
          // give precedence to reference links, then to
          // graph path depth
          if (hasChildLink(resultArray[i], changed)) {
            prepend = true;
          } else {
            int resultDepth = changeSummary.getPathDepth(resultArray[i]);
            if (changedDepth < resultDepth)
              prepend = true;
          }
        }

        if (super.contains(changed)) {
          throw new DataAccessException("unexpected changed object: " + changed.toString());
        }

        if (prepend) {
          if (log.isDebugEnabled())
            log.debug("prepending changed object: " + changed.toString() + " at position " + i);
          super.add(i, (PlasmaDataObject) changed);
          childFound = true;
          break;
        }

      } // for

      if (!childFound) {
        if (log.isDebugEnabled())
          log.debug("appending changed object: " + changed.toString());
        super.add((PlasmaDataObject) changed); // append it
      }
    }
  }

}
