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
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

import org.w3c.dom.Element;

/**
 * <p>
 * Java class for Type complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="baseType" type="{commonj.sdo}URI" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="property" type="{commonj.sdo}Property" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="aliasName" type="{commonj.sdo}String" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="uri" type="{commonj.sdo}URI" />
 *       &lt;attribute name="dataType" type="{commonj.sdo}Boolean" />
 *       &lt;attribute name="open" type="{commonj.sdo}Boolean" />
 *       &lt;attribute name="sequenced" type="{commonj.sdo}Boolean" />
 *       &lt;attribute name="abstract" type="{commonj.sdo}Boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Type", propOrder = { "baseTypes", "properties", "aliasNames", "anies" })
@XmlRootElement(name = "type")
public class Type {

  @XmlElement(name = "baseType")
  protected List<String> baseTypes;
  @XmlElement(name = "property")
  protected List<Property> properties;
  @XmlElement(name = "aliasName")
  protected List<String> aliasNames;
  @XmlAnyElement
  protected List<Element> anies;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String name;
  @XmlAttribute
  protected String uri;
  @XmlAttribute
  protected Boolean dataType;
  @XmlAttribute
  protected Boolean open;
  @XmlAttribute
  protected Boolean sequenced;
  @XmlAttribute(name = "abstract")
  protected Boolean _abstract;
  @XmlAnyAttribute
  private Map<QName, String> otherAttributes = new HashMap<QName, String>();

  /**
   * Gets the value of the baseTypes property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the baseTypes property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getBaseTypes().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link String }
   * 
   * 
   */
  public List<String> getBaseTypes() {
    if (baseTypes == null) {
      baseTypes = new ArrayList<String>();
    }
    return this.baseTypes;
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
   * Gets the value of the uri property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getUri() {
    return uri;
  }

  /**
   * Sets the value of the uri property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setUri(String value) {
    this.uri = value;
  }

  /**
   * Gets the value of the dataType property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isDataType() {
    return dataType;
  }

  /**
   * Sets the value of the dataType property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setDataType(Boolean value) {
    this.dataType = value;
  }

  /**
   * Gets the value of the open property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isOpen() {
    return open;
  }

  /**
   * Sets the value of the open property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setOpen(Boolean value) {
    this.open = value;
  }

  /**
   * Gets the value of the sequenced property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isSequenced() {
    return sequenced;
  }

  /**
   * Sets the value of the sequenced property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setSequenced(Boolean value) {
    this.sequenced = value;
  }

  /**
   * Gets the value of the abstract property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isAbstract() {
    return _abstract;
  }

  /**
   * Sets the value of the abstract property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setAbstract(Boolean value) {
    this._abstract = value;
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
