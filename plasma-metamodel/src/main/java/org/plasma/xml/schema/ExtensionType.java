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

package org.plasma.xml.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * <p>
 * Java class for extensionType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="extensionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}annotated">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.w3.org/2001/XMLSchema}typeDefParticle" minOccurs="0"/>
 *         &lt;group ref="{http://www.w3.org/2001/XMLSchema}attrDecls"/>
 *       &lt;/sequence>
 *       &lt;attribute name="base" use="required" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "extensionType", propOrder = { "sequence", "choice", "all", "group",
    "attributesAndAttributeGroups", "anyAttribute" })
public class ExtensionType extends Annotated {

  protected ExplicitGroup sequence;
  protected ExplicitGroup choice;
  protected All all;
  protected GroupRef group;
  @XmlElements({ @XmlElement(name = "attribute", type = Attribute.class),
      @XmlElement(name = "attributeGroup", type = AttributeGroupRef.class) })
  protected List<Annotated> attributesAndAttributeGroups;
  protected Wildcard anyAttribute;
  @XmlAttribute(required = true)
  protected QName base;

  /**
   * Gets the value of the sequence property.
   * 
   * @return possible object is {@link ExplicitGroup }
   * 
   */
  public ExplicitGroup getSequence() {
    return sequence;
  }

  /**
   * Sets the value of the sequence property.
   * 
   * @param value
   *          allowed object is {@link ExplicitGroup }
   * 
   */
  public void setSequence(ExplicitGroup value) {
    this.sequence = value;
  }

  /**
   * Gets the value of the choice property.
   * 
   * @return possible object is {@link ExplicitGroup }
   * 
   */
  public ExplicitGroup getChoice() {
    return choice;
  }

  /**
   * Sets the value of the choice property.
   * 
   * @param value
   *          allowed object is {@link ExplicitGroup }
   * 
   */
  public void setChoice(ExplicitGroup value) {
    this.choice = value;
  }

  /**
   * Gets the value of the all property.
   * 
   * @return possible object is {@link All }
   * 
   */
  public All getAll() {
    return all;
  }

  /**
   * Sets the value of the all property.
   * 
   * @param value
   *          allowed object is {@link All }
   * 
   */
  public void setAll(All value) {
    this.all = value;
  }

  /**
   * Gets the value of the group property.
   * 
   * @return possible object is {@link GroupRef }
   * 
   */
  public GroupRef getGroup() {
    return group;
  }

  /**
   * Sets the value of the group property.
   * 
   * @param value
   *          allowed object is {@link GroupRef }
   * 
   */
  public void setGroup(GroupRef value) {
    this.group = value;
  }

  /**
   * Gets the value of the attributesAndAttributeGroups property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the attributesAndAttributeGroups property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getAttributesAndAttributeGroups().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link Attribute }
   * {@link AttributeGroupRef }
   * 
   * 
   */
  public List<Annotated> getAttributesAndAttributeGroups() {
    if (attributesAndAttributeGroups == null) {
      attributesAndAttributeGroups = new ArrayList<Annotated>();
    }
    return this.attributesAndAttributeGroups;
  }

  /**
   * Gets the value of the anyAttribute property.
   * 
   * @return possible object is {@link Wildcard }
   * 
   */
  public Wildcard getAnyAttribute() {
    return anyAttribute;
  }

  /**
   * Sets the value of the anyAttribute property.
   * 
   * @param value
   *          allowed object is {@link Wildcard }
   * 
   */
  public void setAnyAttribute(Wildcard value) {
    this.anyAttribute = value;
  }

  /**
   * Gets the value of the base property.
   * 
   * @return possible object is {@link QName }
   * 
   */
  public QName getBase() {
    return base;
  }

  /**
   * Sets the value of the base property.
   * 
   * @param value
   *          allowed object is {@link QName }
   * 
   */
  public void setBase(QName value) {
    this.base = value;
  }

}
