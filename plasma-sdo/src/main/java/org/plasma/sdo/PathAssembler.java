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

/**
 * A graph visitor which stores a list of candidate paths from the root
 * data-object to the given target data-object and return the minimum (least
 * hops) path or set of paths as SDO compliant path-strings.
 */
public interface PathAssembler extends PlasmaDataGraphVisitor {

  public String getMinimumPathString();

  public int getMinimumPathDepth();

  public int getPathCount();

  public String[] getAllPaths();
}
