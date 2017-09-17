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

import java.util.Comparator;

/**
 * Ensures ordering of changed data objects within the change summary.
 */
public class CoreChangeComparator implements Comparator<CoreChange> {

  @Override
  public int compare(CoreChange o1, CoreChange o2) {
    Integer depth1 = o1.getPathDepthFromRoot();
    Integer depth2 = o2.getPathDepthFromRoot();
    return depth1.compareTo(depth2);
  }
}
