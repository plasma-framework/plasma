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

import java.util.Comparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaChangeSummary;

import commonj.sdo.DataObject;

/**
 * Compares its two data-objects for path depth relative to a data-graph root.
 * 
 */
public class DataObjectPathDepthComparator implements Comparator<DataObject> {

  private static Log log = LogFactory.getFactory().getInstance(DataObjectPathDepthComparator.class);

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
      if (targetDepth > sourceDepth)
        result = 1;
      else if (sourceDepth > targetDepth)
        result = -1;
      else
        result = 0;
    } else if (changeSummary.isModified(target)) {
      if (targetDepth > sourceDepth)
        result = 1;
      else if (sourceDepth > targetDepth)
        result = -1;
      else
        result = 0;
    } else { // deleted
      if (targetDepth > sourceDepth)
        result = -1;
      else if (sourceDepth > targetDepth)
        result = 1;
      else
        result = 0;
    }

    return result;
  }
}
