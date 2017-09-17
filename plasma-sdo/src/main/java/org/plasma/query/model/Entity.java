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

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;

/**
 * <p>
 * Java class for Entity complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Entity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Entity")
@XmlRootElement(name = "Entity")
public class Entity implements Comparable<Entity> {

  @XmlAttribute(required = true)
  protected String name;
  @XmlAttribute
  protected String namespaceURI;
  @XmlAttribute(name = "alias")
  protected String alias;

  /**
   * Stores the physical name associated with this property. Can be used by
   * service providers for query post processing. This field is not processed
   * during XML or other serialization operations.
   */
  protected transient String physicalName;
  /**
   * Stores the physical name bytes associated with this property. Can be used
   * by service providers for query post processing. This field is not processed
   * during XML or other serialization operations.
   */
  protected transient byte[] physicalNameBytes;

  /**
   * A qualified name user for identity
   */
  private transient String qualifiedName;

  public Entity() {
    super();
  }

  public Entity(String name, String namespaceURI) {
    this();
    this.setName(name);
    this.setNamespaceURI(namespaceURI);
  }

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
   * Gets the value of the name property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setName(String value) {
    this.name = value;
  }

  /**
   * Gets the value of the namespaceURI property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getNamespaceURI() {
    return namespaceURI;
  }

  /**
   * Gets the value of the alias property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getAlias() {
    return alias;
  }

  /**
   * Sets the value of the alias property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setAlias(String value) {
    this.alias = value;
  }

  public boolean hasAlias() {
    return this.alias != null;
  }

  public String getPhysicalName() {
    return physicalName;
  }

  public void setPhysicalName(String value) {
    this.physicalName = value;
  }

  public byte[] getPhysicalNameBytes() {
    return physicalNameBytes;
  }

  public void setPhysicalNameBytes(byte[] value) {
    this.physicalNameBytes = value;
  }

  /**
   * Sets the value of the namespaceURI property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setNamespaceURI(String value) {
    this.namespaceURI = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    visitor.end(this);
  }

  public static String qualifiedNameFor(String namespaceURI, String name) {
    StringBuilder buf = new StringBuilder();
    buf.append(namespaceURI);
    buf.append("#");
    buf.append(name);
    return buf.toString();
  }

  public static String qualifiedNameFor(String namespaceURI, String name, String alias) {
    StringBuilder buf = new StringBuilder();
    buf.append(namespaceURI);
    buf.append("#");
    buf.append(name);
    buf.append(" (");
    buf.append(alias);
    buf.append("}");
    return buf.toString();
  }

  public String getQualifiedName() {
    if (qualifiedName == null) {
      if (this.namespaceURI != null && this.alias != null) {
        qualifiedName = qualifiedNameFor(this.namespaceURI, this.name, this.alias);
      } else {
        qualifiedName = qualifiedNameFor(this.namespaceURI, this.name);
      }
    }
    return qualifiedName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getQualifiedName() == null) ? 0 : getQualifiedName().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Entity other = (Entity) obj;
    if (getQualifiedName() == null) {
      if (other.getQualifiedName() != null)
        return false;
    } else if (!getQualifiedName().equals(other.getQualifiedName()))
      return false;
    return true;
  }

  @Override
  public int compareTo(Entity o) {
    return getQualifiedName().compareTo(o.getQualifiedName());
  }

}
