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

import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;

/**
 * A domain object which is an end point for a path within a query graph.
 */
public class DomainEndpoint extends DomainObject {

  protected AbstractProperty property;

  protected DomainEndpoint(PathNode source, String sourceProperty) {
    super(source, sourceProperty);
  }

  protected Path createPath() {
    Path path = null;
    PathNode[] pathNodes = this.getPathNodes();
    for (PathNode pathNode : pathNodes) {
      if (path == null)
        path = new Path();
      org.plasma.query.model.PathNode modelPathNode = new org.plasma.query.model.PathNode();
      org.plasma.query.model.PathElement modelPathElement = new org.plasma.query.model.PathElement();
      modelPathElement.setValue(pathNode.getSourceProperty());
      modelPathNode.setPathElement(modelPathElement);
      if (pathNode.getPredicate() != null) {
        org.plasma.query.model.Where where = new org.plasma.query.model.Where();
        where.addExpression((org.plasma.query.model.Expression) pathNode.getPredicate());
        modelPathNode.setWhere(where);
      }
      path.addPathNode(modelPathNode);
    }
    return path;
  }

}
