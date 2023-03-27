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

/**
 * <p>
 * Java class for GroupBy complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="GroupBy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.terrameta.org/plasma/query}Property" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.terrameta.org/plasma/query}TextContent" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupBy", propOrder = { "properties", "textContent" })
@XmlRootElement(name = "GroupBy")
public class GroupBy implements org.plasma.query.GroupBy {

  @XmlElement(name = "Property")
  protected List<Property> properties;
  @XmlElement(name = "TextContent")
  protected TextContent textContent;

  public GroupBy() {
    super();
  } // -- org.plasma.mda.query.GroupBy()

  public GroupBy(Property p1) {
    this();
    getProperties().add(p1);
  }

  public GroupBy(Property p1, Property p2) {
    this();
    getProperties().add(p1);
    getProperties().add(p2);
  }

  public GroupBy(Property p1, Property p2, Property p3) {
    this();
    getProperties().add(p1);
    getProperties().add(p2);
    getProperties().add(p3);
  }

  public GroupBy(Property p1, Property p2, Property p3, Property p4) {
    this();
    getProperties().add(p1);
    getProperties().add(p2);
    getProperties().add(p3);
    getProperties().add(p4);
  }

  public GroupBy(Property[] properties) {
    this();
    for (int i = 0; i < properties.length; i++)
      getProperties().add(properties[i]);
  }

  /**
   * Gets the value of the properties property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the properties property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getProperties().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link Property }
   * 
   * 
   */
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
