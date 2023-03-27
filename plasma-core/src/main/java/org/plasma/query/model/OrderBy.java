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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderBy", propOrder = { "properties", "textContent" })
@XmlRootElement(name = "OrderBy")
public class OrderBy implements org.plasma.query.OrderBy {

  @XmlElement(name = "Property")
  protected List<Property> properties;
  @XmlElement(name = "TextContent")
  protected TextContent textContent;

  public OrderBy() {
    super();
  } // -- org.plasma.mda.query.OrderBy()

  public OrderBy(Property p1) {
    this();
    getProperties().add(p1);
  }

  public OrderBy(Property p1, Property p2) {
    this();
    getProperties().add(p1);
    getProperties().add(p2);
  }

  public OrderBy(Property p1, Property p2, Property p3) {
    this();
    getProperties().add(p1);
    getProperties().add(p2);
    getProperties().add(p3);
  }

  public OrderBy(Property p1, Property p2, Property p3, Property p4) {
    this();
    getProperties().add(p1);
    getProperties().add(p2);
    getProperties().add(p3);
    getProperties().add(p4);
  }

  public OrderBy(Property[] properties) {
    this();
    for (int i = 0; i < properties.length; i++)
      getProperties().add(properties[i]);
  }

  public OrderBy(String content) {
    this();
    textContent = new TextContent();
    textContent.setValue(content);
  }

  public List<Property> getProperties() {
    if (properties == null) {
      properties = new ArrayList<Property>();
    }
    return this.properties;
  }

  public void addProperty(Property property) {
    this.getProperties().add(property);
  }

  /**
   * Gets the value of the textContent property.
   * 
   * @return possible object is {@link TextContent }
   * 
   */
  public TextContent getTextContent() {
    return textContent;
  }

  /**
   * Sets the value of the textContent property.
   * 
   * @param value
   *          allowed object is {@link TextContent }
   * 
   */
  public void setTextContent(TextContent value) {
    this.textContent = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal()) {
      for (int i = 0; i < this.getProperties().size(); i++)
        this.getProperties().get(i).accept(visitor);
    }
    visitor.end(this);
  }
}
