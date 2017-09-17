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

package org.plasma.xml.sdox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

/**
 * <p>
 * Java class for Property complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Property">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aliasName" type="{commonj.sdo}String" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{commonj.sdo}String" />
 *       &lt;attribute name="many" type="{commonj.sdo}Boolean" />
 *       &lt;attribute name="containment" type="{commonj.sdo}Boolean" />
 *       &lt;attribute name="default" type="{commonj.sdo}String" />
 *       &lt;attribute name="readOnly" type="{commonj.sdo}Boolean" />
 *       &lt;attribute name="type" type="{commonj.sdo}URI" />
 *       &lt;attribute name="opposite" type="{commonj.sdo}URI" />
 *       &lt;attribute name="nullable" type="{commonj.sdo}Boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Property", propOrder = { "aliasNames", "anies" })
public class Property {

  @XmlElement(name = "aliasName")
  protected List<String> aliasNames;
  @XmlAnyElement
  protected List<Element> anies;
  @XmlAttribute
  protected String name;
  @XmlAttribute
  protected Boolean many;
  @XmlAttribute
  protected Boolean containment;
  @XmlAttribute(name = "default")
  protected String _default;
  @XmlAttribute
  protected Boolean readOnly;
  @XmlAttribute
  protected String type;
  @XmlAttribute
  protected String opposite;
  @XmlAttribute
  protected Boolean nullable;
  @XmlAnyAttribute
  private Map<QName, String> otherAttributes = new HashMap<QName, String>();

  /**
   * Gets the value of the aliasNames property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the aliasNames property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getAliasNames().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link String }
   * 
   * 
   */
  public List<String> getAliasNames() {
    if (aliasNames == null) {
      aliasNames = new ArrayList<String>();
    }
    return this.aliasNames;
  }

  /**
   * Gets the value of the anies property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the anies property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getAnies().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link Element }
   * 
   * 
   */
  public List<Element> getAnies() {
    if (anies == null) {
      anies = new ArrayList<Element>();
    }
    return this.anies;
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
   * Gets the value of the many property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isMany() {
    return many;
  }

  /**
   * Sets the value of the many property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setMany(Boolean value) {
    this.many = value;
  }

  /**
   * Gets the value of the containment property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isContainment() {
    return containment;
  }

  /**
   * Sets the value of the containment property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setContainment(Boolean value) {
    this.containment = value;
  }

  /**
   * Gets the value of the default property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDefault() {
    return _default;
  }

  /**
   * Sets the value of the default property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setDefault(String value) {
    this._default = value;
  }

  /**
   * Gets the value of the readOnly property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isReadOnly() {
    return readOnly;
  }

  /**
   * Sets the value of the readOnly property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setReadOnly(Boolean value) {
    this.readOnly = value;
  }

  /**
   * Gets the value of the type property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setType(String value) {
    this.type = value;
  }

  /**
   * Gets the value of the opposite property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getOpposite() {
    return opposite;
  }

  /**
   * Sets the value of the opposite property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setOpposite(String value) {
    this.opposite = value;
  }

  /**
   * Gets the value of the nullable property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isNullable() {
    return nullable;
  }

  /**
   * Sets the value of the nullable property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setNullable(Boolean value) {
    this.nullable = value;
  }

  /**
   * Gets a map that contains attributes that aren't bound to any typed property
   * on this class.
   * 
   * <p>
   * the map is keyed by the name of the attribute and the value is the string
   * value of the attribute.
   * 
   * the map returned by this method is live, and you can add new attribute by
   * updating the map directly. Because of this design, there's no setter.
   * 
   * 
   * @return always non-null
   */
  public Map<QName, String> getOtherAttributes() {
    return otherAttributes;
  }

}
