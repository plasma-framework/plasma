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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * 
 * base attribute and simpleType child are mutually exclusive, but one or other
 * is required
 * 
 * 
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}annotated">
 *       &lt;group ref="{http://www.w3.org/2001/XMLSchema}simpleRestrictionModel"/>
 *       &lt;attribute name="base" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "simpleType", "minExclusivesAndMinInclusivesAndMaxExclusives" })
@XmlRootElement(name = "restriction")
public class Restriction extends Annotated {

  protected LocalSimpleType simpleType;
  @XmlElementRefs({
      @XmlElementRef(name = "enumeration", namespace = "http://www.w3.org/2001/XMLSchema", type = Enumeration.class),
      @XmlElementRef(name = "maxExclusive", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "minExclusive", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "whiteSpace", namespace = "http://www.w3.org/2001/XMLSchema", type = WhiteSpace.class),
      @XmlElementRef(name = "minInclusive", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "totalDigits", namespace = "http://www.w3.org/2001/XMLSchema", type = TotalDigits.class),
      @XmlElementRef(name = "fractionDigits", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "length", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "pattern", namespace = "http://www.w3.org/2001/XMLSchema", type = Pattern.class),
      @XmlElementRef(name = "minLength", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "maxInclusive", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "maxLength", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class) })
  protected List<Object> minExclusivesAndMinInclusivesAndMaxExclusives;
  @XmlAttribute
  protected QName base;

  /**
   * Gets the value of the simpleType property.
   * 
   * @return possible object is {@link LocalSimpleType }
   * 
   */
  public LocalSimpleType getSimpleType() {
    return simpleType;
  }

  /**
   * Sets the value of the simpleType property.
   * 
   * @param value
   *          allowed object is {@link LocalSimpleType }
   * 
   */
  public void setSimpleType(LocalSimpleType value) {
    this.simpleType = value;
  }

  /**
   * Gets the value of the minExclusivesAndMinInclusivesAndMaxExclusives
   * property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the minExclusivesAndMinInclusivesAndMaxExclusives property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getMinExclusivesAndMinInclusivesAndMaxExclusives().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link Enumeration } {@link JAXBElement }{@code <}{@link Facet }{@code >}
   * {@link JAXBElement }{@code <}{@link Facet }{@code >} {@link WhiteSpace }
   * {@link TotalDigits } {@link JAXBElement }{@code <}{@link Facet }{@code >}
   * {@link JAXBElement }{@code <}{@link NumFacet }{@code >} {@link JAXBElement }
   * {@code <}{@link NumFacet }{@code >} {@link Pattern } {@link JAXBElement }
   * {@code <}{@link NumFacet }{@code >} {@link JAXBElement }{@code <}
   * {@link Facet }{@code >} {@link JAXBElement }{@code <}{@link NumFacet }{@code >}
   * 
   * 
   */
  public List<Object> getMinExclusivesAndMinInclusivesAndMaxExclusives() {
    if (minExclusivesAndMinInclusivesAndMaxExclusives == null) {
      minExclusivesAndMinInclusivesAndMaxExclusives = new ArrayList<Object>();
    }
    return this.minExclusivesAndMinInclusivesAndMaxExclusives;
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
