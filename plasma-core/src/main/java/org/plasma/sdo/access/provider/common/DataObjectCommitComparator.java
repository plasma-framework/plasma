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

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaNode;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * Compares its two data-objects for order within the context of data-store
 * commit operations.
 * 
 */
public class DataObjectCommitComparator implements Comparator<DataObject>, Serializable {

  private static Log log = LogFactory.getFactory().getInstance(DataObjectCommitComparator.class);

  /**
   * Returns a negative integer, zero, or a positive integer as the first
   * argument is less than, equal to, or greater than the second.
   */
  public int compare(DataObject target, DataObject source) {

    if (target.getDataGraph() == null)
      throw new IllegalArgumentException("target data-object has no data-graph - "
          + "data-objects compared for commit operations must have a data graph");
    if (source.getDataGraph() == null)
      throw new IllegalArgumentException("source data-object has no data-graph - "
          + "data-objects compared for commit operations must have a data graph");

    PlasmaChangeSummary changeSummary = (PlasmaChangeSummary) target.getChangeSummary();

    int targetDepth = changeSummary.getPathDepth(target);
    int sourceDepth = changeSummary.getPathDepth(source);

    int result = 0;

    if (changeSummary.isCreated(target)) {
      if (changeSummary.isCreated(source)) {
        if (isChild(target, source)) {
          result = 1; // target is a child, create it's parent first
        } else if (isChild(source, target)) {
          result = -1; // source is a child, create it's parent first
        } else {
          if (targetDepth > sourceDepth)
            result = 1;
          else if (sourceDepth > targetDepth)
            result = -1;
          else
            result = 0;
        }
      } else
        result = -1;

    } else if (changeSummary.isModified(target)) {

      if (changeSummary.isCreated(source)) {
        result = 1;
      } else if (changeSummary.isModified(source)) {
        if (targetDepth > sourceDepth)
          result = 1;
        else if (sourceDepth > targetDepth)
          result = -1;
        else
          result = 0;
      } else
        // deleted
        result = -1;
    } else { // deleted
      if (changeSummary.isDeleted(source)) {
        if (isChild(target, source)) {
          result = -1; // target is a child, delete it first
        } else if (isChild(source, target)) {
          result = 1; // source is a child, delete it first
        } else {
          if (targetDepth > sourceDepth)
            result = -1;
          else if (sourceDepth > targetDepth)
            result = 1;
          else
            result = 0;
        }
      } else
        // created or modified
        result = 1;
    }

    return result;
  }

  private boolean isChild(DataObject target, DataObject source) {
    return hasChildProperty(target, source);
  }

  private boolean hasChildLink(DataObject target, DataObject source) {

    if (log.isDebugEnabled())
      log.debug("comparing " + target.getType().getName() + "/" + source.getType().getName());

    // look at properties in target, check if linked to source
    List<ChangeSummary.Setting> settings = target.getDataGraph().getChangeSummary()
        .getOldValues(target);

    for (ChangeSummary.Setting setting : settings) {
      Property property = setting.getProperty();
      if (property.getType().isDataType())
        continue;

      // FIXME - equality method for Type ??
      if (!property.getType().getName().equals(source.getType().getName()))
        continue;

      if (log.isDebugEnabled())
        log.debug("checking property " + target.getType().getName() + "." + property.getName());
      if (isLinked(source, setting.getValue())) {
        // singular property linked to source
        if (!property.isMany()) {
          if (log.isDebugEnabled())
            log.debug("found child data link " + target.getType().getName() + "."
                + property.getName() + "->" + source.getType().getName());
          if (target.getType().getName().equals(source.getType().getName()))
            throw new IllegalStateException("potential circular reference");
          return true;
        }
      }
    }
    return false;
  }

  private boolean hasChildProperty(DataObject target, DataObject source) {

    if (log.isDebugEnabled())
      log.debug("comparing " + target.getType().getName() + "/" + source.getType().getName());

    for (Property p : target.getType().getDeclaredProperties()) {

      Property property = p; // so we can see it in Eclipse debugger
      if (property.getType().isDataType())
        continue;
      if (!property.getType().getName().equals(source.getType().getName()))
        continue;
      // singular property which could be linked in the data-store but
      // we have just not returned this property in a query
      if (!property.isMany()) {
        if (target.getType().getName().equals(source.getType().getName()))
          throw new IllegalStateException("potential circular reference");
        if (log.isDebugEnabled())
          log.debug("found child link property " + target.getType().getName() + "."
              + property.getName() + "->" + source.getType().getName());
        return true;
      }
    }

    return false;
  }

  private boolean isLinked(DataObject other, Object value) {
    if (value instanceof List) {
      List<DataObject> list = (List<DataObject>) value;
      for (DataObject dataObject : list) {
        PlasmaNode dataNode = (PlasmaNode) dataObject;
        if (dataNode.getUUIDAsString().equals(((PlasmaNode) other).getUUIDAsString())) {
          return true;
        }
      }
    } else {
      DataObject dataObject = (DataObject) value;
      PlasmaNode dataNode = (PlasmaNode) dataObject;
      if (dataNode.getUUIDAsString().equals(((PlasmaNode) other).getUUIDAsString())) {
        return true;
      }
    }
    return false;
  }
}
