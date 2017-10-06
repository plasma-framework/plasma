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

package org.plasma.sdo;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * A change summary is used to record changes to DataObjects, allowing
 * applications to efficiently and incrementally update back-end storage when
 * required.
 */
public interface PlasmaChangeSummary extends ChangeSummary {

  /**
   * Register the given data object as a created object within this change
   * summary.
   * 
   * @param dataObject
   *          the created data object
   */
  public void created(DataObject dataObject);

  /**
   * Register the given data object as a deleted object within this change
   * summary.
   * 
   * @param dataObject
   *          the deleted data object
   */
  public void deleted(DataObject dataObject);

  /**
   * Register the given data object as a modified object within this change
   * summary for the given property.
   * 
   * @param dataObject
   *          the modified data object
   * @param property
   *          the modified property
   * @param oldValue
   *          the previous value for the given property
   */
  public void modified(DataObject dataObject, Property property, Object oldValue);

  /**
   * Removes all change information for the given data object within this change
   * summary.
   * 
   * @param dataObject
   *          the data object
   */
  public void clear(DataObject dataObject);

  /**
   * Returns the path depth within the data graph for the given data object.
   * 
   * @param dataObject
   *          the data object
   * @return the path depth
   */
  public int getPathDepth(DataObject dataObject);

}
