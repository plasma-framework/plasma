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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;

public class ModifiedObjectCollector implements PlasmaDataGraphVisitor {
  private static Log log = LogFactory.getFactory().getInstance(ModifiedObjectCollector.class);
  private HashSet<PlasmaDataObject> result = new HashSet<PlasmaDataObject>();
  private PlasmaChangeSummary changeSummary;

  @SuppressWarnings("unused")
  private ModifiedObjectCollector() {
  }

  public ModifiedObjectCollector(DataGraph dataGraph) {

    this.changeSummary = (PlasmaChangeSummary) dataGraph.getChangeSummary();
    PlasmaDataObject root = (PlasmaDataObject) dataGraph.getRootObject();
    root.accept(this);
  }

  public void visit(DataObject target, DataObject source, String sourceKey, int level) {

    if (changeSummary.isModified(target)) {
      if (!result.contains(target))
        this.result.add((PlasmaDataObject) target);
    }
  }

  public List<PlasmaDataObject> getResult() {
    PlasmaDataObject[] array = new PlasmaDataObject[this.result.size()];
    this.result.toArray(array);
    return Arrays.asList(array);
  }
}
