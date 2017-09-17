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

import org.plasma.query.Wildcard;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;
import org.plasma.query.model.WildcardProperty;
import org.plasma.query.model.WildcardPropertyType;

/**
 * A domain query node which is a wild card end point within a query graph.
 */
class WildcardNode extends DomainEndpoint implements Wildcard {

  public WildcardNode(PathNode source, String name, WildcardPropertyType wildcardType) {
    super(source, name);
    if (this.source != null) {
      Path path = createPath();
      if (!Wildcard.WILDCARD_CHAR.equals(name)) { // FIXME: why is this ever the
                                                  // case
        if (path != null)
          this.property = new org.plasma.query.model.Property(name, path);
        else
          this.property = new org.plasma.query.model.Property(name);
      } else {
        WildcardProperty wcProp = null;
        if (path != null)
          wcProp = new WildcardProperty(path);
        else
          wcProp = new WildcardProperty();
        wcProp.setType(wildcardType);
        this.property = wcProp;
      }
    } else {
      if (!Wildcard.WILDCARD_CHAR.equals(name)) { // FIXME: why is this ever the
                                                  // case
        this.property = new org.plasma.query.model.Property(name);
      } else {
        WildcardProperty wcProp = new WildcardProperty();
        wcProp.setType(wildcardType);
        this.property = wcProp;
      }
    }
  }

  AbstractProperty getModel() {
    return this.property;
  }

}
