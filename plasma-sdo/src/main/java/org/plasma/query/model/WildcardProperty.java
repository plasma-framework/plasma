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

package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WildcardProperty")
@XmlRootElement(name = "WildcardProperty")
public class WildcardProperty extends AbstractProperty implements org.plasma.query.Term {
  @XmlAttribute(required = true)
  protected WildcardPropertyType type;

  public WildcardProperty() {
    super();
    type = WildcardPropertyType.DATA;
  } // -- org.plasma.mda.query.WildcardProperty()

  public WildcardProperty(Path path) {
    this();
    this.setPath(path);
  } // -- org.plasma.mda.query.WildcardProperty(Path path)

  /**
   * Gets the value of the type property.
   * 
   * @return possible object is {@link WildcardPropertyType }
   * 
   */
  public WildcardPropertyType getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   * 
   * @param value
   *          allowed object is {@link WildcardPropertyType }
   * 
   */
  public void setType(WildcardPropertyType value) {
    this.type = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    visitor.end(this);
  }

  @Override
  public int compareTo(AbstractProperty o) {
    return getQualifiedName().compareTo(o.getQualifiedName());
  }

  @Override
  public String getQualifiedName() {
    if (qualifiedName == null) {
      StringBuilder buf = new StringBuilder();
      if (this.path != null) {
        for (PathNode node : this.path.getPathNodes()) {
          buf.append(node.getPathElement().getValue());
          buf.append(".");
        }
      }
      buf.append("*:");
      buf.append(this.type);
      qualifiedName = buf.toString();
    }
    return qualifiedName;
  }

}
