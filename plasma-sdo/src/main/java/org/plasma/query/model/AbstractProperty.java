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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;

/**
 * <p>
 * Java class for AbstractProperty complex type.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractProperty", propOrder = { "path" })
public abstract class AbstractProperty implements Comparable<AbstractProperty> {

  @XmlElement(name = "Path", namespace = "")
  protected Path path;

  /**
   * A qualified name used for identity
   */
  protected transient String qualifiedName;

  public abstract String getQualifiedName();

  /**
   * A unique id, such as a repository UUID as string or xmi-id from UML source.
   */
  protected transient String uniqueId;

  public String getUniqueId() {
    return this.uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  /**
   * A type unique id, such as a repository UUID as string or xmi-id from UML
   * source.
   */
  protected transient String typeUniqueId;

  public String getTypeUniqueId() {
    return this.typeUniqueId;
  }

  public void setTypeUniqueId(String uniqueId) {
    this.typeUniqueId = uniqueId;
  }

  /**
   * Gets the value of the path property.
   * 
   * @return possible object is {@link Path }
   * 
   */
  public Path getPath() {
    return path;
  }

  /**
   * Sets the value of the path property.
   * 
   * @param value
   *          allowed object is {@link Path }
   * 
   */
  public void setPath(Path value) {
    this.path = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    visitor.end(this);
  }
}
