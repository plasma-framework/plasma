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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

/**
 * 
 * group type for explicit groups, named top-level groups and group references
 * 
 * <p>
 * Java class for group complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="group">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}annotated">
 *       &lt;group ref="{http://www.w3.org/2001/XMLSchema}particle" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;attGroup ref="{http://www.w3.org/2001/XMLSchema}occurs"/>
 *       &lt;attGroup ref="{http://www.w3.org/2001/XMLSchema}defRef"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "group", propOrder = { "elementsAndGroupsAndAlls" })
public abstract class AbstractGroup extends Annotated {

  @XmlElementRefs({
      @XmlElementRef(name = "all", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "any", namespace = "http://www.w3.org/2001/XMLSchema", type = Any.class),
      @XmlElementRef(name = "sequence", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "element", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "group", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "choice", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class) })
  protected List<Object> elementsAndGroupsAndAlls;
  @XmlAttribute
  @XmlSchemaType(name = "nonNegativeInteger")
  protected BigInteger minOccurs;
  @XmlAttribute
  @XmlSchemaType(name = "allNNI")
  protected String maxOccurs;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "NCName")
  protected String name;
  @XmlAttribute
  protected QName ref;

  /**
   * Gets the value of the elementsAndGroupsAndAlls property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the elementsAndGroupsAndAlls property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getElementsAndGroupsAndAlls().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link JAXBElement }{@code <}{@link LocalElement }{@code >}
   * {@link JAXBElement }{@code <}{@link All }{@code >} {@link JAXBElement }
   * {@code <}{@link ExplicitGroup }{@code >} {@link Any } {@link JAXBElement }
   * {@code <}{@link GroupRef }{@code >} {@link JAXBElement }{@code <}
   * {@link ExplicitGroup }{@code >}
   * 
   * 
   */
  public List<Object> getElementsAndGroupsAndAlls() {
    if (elementsAndGroupsAndAlls == null) {
      elementsAndGroupsAndAlls = new ArrayList<Object>();
    }
    return this.elementsAndGroupsAndAlls;
  }

  public boolean hasMinOccurs() {
    return minOccurs != null;
  }

  /**
   * Gets the value of the minOccurs property.
   * 
   * @return possible object is {@link BigInteger }
   * 
   */
  public BigInteger getMinOccurs() {
    if (minOccurs == null) {
      return new BigInteger("1");
    } else {
      return minOccurs;
    }
  }

  /**
   * Sets the value of the minOccurs property.
   * 
   * @param value
   *          allowed object is {@link BigInteger }
   * 
   */
  public void setMinOccurs(BigInteger value) {
    this.minOccurs = value;
  }

  public boolean hasMaxOccurs() {
    return maxOccurs != null;
  }

  /**
   * Gets the value of the maxOccurs property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getMaxOccurs() {
    if (maxOccurs == null) {
      return "1";
    } else {
      return maxOccurs;
    }
  }

  /**
   * Sets the value of the maxOccurs property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setMaxOccurs(String value) {
    this.maxOccurs = value;
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
   * Gets the value of the ref property.
   * 
   * @return possible object is {@link QName }
   * 
   */
  public QName getRef() {
    return ref;
  }

  /**
   * Sets the value of the ref property.
   * 
   * @param value
   *          allowed object is {@link QName }
   * 
   */
  public void setRef(QName value) {
    this.ref = value;
  }

}
