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

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

/**
 * <p>
 * Java class for Join complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Join">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.terrameta.org/plasma/query}Entity"/>
 *         &lt;element ref="{http://www.terrameta.org/plasma/query}On"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required" type="{http://www.terrameta.org/plasma/query}JoinType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Join", propOrder = { "entity", "on", "configurationProperty" })
public class Join {

  @XmlElement(name = "Entity", required = true)
  protected Entity entity;
  @XmlElement(name = "On", required = true)
  protected On on;
  @XmlElement(name = "ConfigurationProperty")
  protected List<ConfigurationProperty> configurationProperty;
  @XmlAttribute(name = "type", required = true)
  protected JoinType type;

  /**
   * Gets the value of the entity property.
   * 
   * @return possible object is {@link Entity }
   * 
   */
  public Entity getEntity() {
    return entity;
  }

  /**
   * Sets the value of the entity property.
   * 
   * @param value
   *          allowed object is {@link Entity }
   * 
   */
  public void setEntity(Entity value) {
    this.entity = value;
  }

  /**
   * Gets the value of the on property.
   * 
   * @return possible object is {@link On }
   * 
   */
  public On getOn() {
    return on;
  }

  /**
   * Sets the value of the on property.
   * 
   * @param value
   *          allowed object is {@link On }
   * 
   */
  public void setOn(On value) {
    this.on = value;
  }

  /**
   * public List<ConfigurationProperty> getConfigurationProperty() { if
   * (configurationProperty == null) { configurationProperty = new
   * ArrayList<ConfigurationProperty>(); } return this.configurationProperty; }
   * Gets the value of the type property.
   * 
   * @return possible object is {@link JoinType }
   * 
   */
  public JoinType getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   * 
   * @param value
   *          allowed object is {@link JoinType }
   * 
   */
  public void setType(JoinType value) {
    this.type = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal()) {
      this.getEntity().accept(visitor);
      this.getOn().getExpression().accept(visitor);
    }
    visitor.end(this);
  }
}
