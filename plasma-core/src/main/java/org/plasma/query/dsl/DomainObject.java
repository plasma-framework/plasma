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

package org.plasma.query.dsl;

import java.util.ArrayList;
import java.util.List;

/**
 * Super class for elements within a domain query graph.
 */
public abstract class DomainObject {
  protected PathNode source;
  protected String sourceProperty;

  @SuppressWarnings("unused")
  private DomainObject() {
  }

  protected DomainObject(PathNode source, String sourceProperty) {
    super();
    this.source = source;
    this.sourceProperty = sourceProperty;
  }

  @Deprecated
  String[] getPath() {
    PathNode current = source;
    List<String> list = new ArrayList<String>();
    while (current != null && !current.isRoot()) {
      list.add(0, current.getSourceProperty());
      current = current.getSource();
    }
    String[] path = new String[list.size()];
    list.toArray(path);
    return path;
  }

  PathNode[] getPathNodes() {
    PathNode current = source;
    List<PathNode> list = new ArrayList<PathNode>();
    while (current != null && !current.isRoot()) {
      list.add(0, current);
      current = current.getSource();
    }
    PathNode[] path = new PathNode[list.size()];
    list.toArray(path);
    return path;
  }
}
