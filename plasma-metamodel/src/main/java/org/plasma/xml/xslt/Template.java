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

package org.plasma.xml.xslt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

/**
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
 *     &lt;extension base="{http://www.w3.org/1999/XSL/Transform}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/1999/XSL/Transform}param" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{http://www.w3.org/1999/XSL/Transform}top-level-element-and-char-instruction"/>
 *           &lt;element ref="{http://www.w3.org/1999/XSL/Transform}instruction"/>
 *           &lt;group ref="{http://www.w3.org/1999/XSL/Transform}result-element"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="match" type="{http://www.w3.org/1999/XSL/Transform}pattern" />
 *       &lt;attribute name="name" type="{http://www.w3.org/1999/XSL/Transform}QName" />
 *       &lt;attribute name="priority" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *       &lt;attribute name="mode" type="{http://www.w3.org/1999/XSL/Transform}QName" />
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}space"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "content" })
public class Template {

  @XmlElementRefs({
      @XmlElementRef(name = "instruction", namespace = "http://www.w3.org/1999/XSL/Transform", type = JAXBElement.class),
      @XmlElementRef(name = "top-level-element-and-char-instruction", namespace = "http://www.w3.org/1999/XSL/Transform", type = JAXBElement.class),
      @XmlElementRef(name = "param", namespace = "http://www.w3.org/1999/XSL/Transform", type = JAXBElement.class),
      @XmlElementRef(name = "result-element", namespace = "http://www.w3.org/1999/XSL/Transform", type = JAXBElement.class) })
  @XmlMixed
  @XmlAnyElement
  protected List<Object> content;
  @XmlAttribute
  protected String match;
  @XmlAttribute
  protected String name;
  @XmlAttribute
  protected BigDecimal priority;
  @XmlAttribute
  protected String mode;
  @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String space;
  @XmlAnyAttribute
  private Map<QName, String> otherAttributes = new HashMap<QName, String>();

  /**
   * Gets the value of the content property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the content property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getContent().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link JAXBElement }{@code <}{@link AttributeType }{@code >}
   * {@link JAXBElement }{@code <}{@link CopyOf }{@code >} {@link JAXBElement }
   * {@code <}{@link Copy }{@code >} {@link JAXBElement }{@code <}{@link Choose }
   * {@code >} {@link String } {@link JAXBElement }{@code <}
   * {@link ProcessingInstruction }{@code >} {@link JAXBElement }{@code <}
   * {@link AnyType }{@code >} {@link JAXBElement }{@code <}{@link Comment }
   * {@code >} {@link JAXBElement }{@code <}{@link AnyType }{@code >}
   * {@link JAXBElement }{@code <}{@link AnyType }{@code >} {@link JAXBElement }
   * {@code <}{@link Variable }{@code >} {@link JAXBElement }{@code <}
   * {@link Object }{@code >} {@link JAXBElement }{@code <}{@link If }{@code >}
   * {@link JAXBElement }{@code <}{@link ValueOf }{@code >} {@link JAXBElement }
   * {@code <}{@link ForEach }{@code >} {@link JAXBElement }{@code <}
   * {@link Variable }{@code >} {@link JAXBElement }{@code <}{@link Number }
   * {@code >} {@link JAXBElement }{@code <}{@link CallTemplate }{@code >}
   * {@link JAXBElement }{@code <}{@link ApplyTemplates }{@code >}
   * {@link JAXBElement }{@code <}{@link Fallback }{@code >}
   * {@link org.w3c.dom.Element } {@link JAXBElement }{@code <}{@link Message }
   * {@code >} {@link JAXBElement }{@code <}{@link org.plasma.xml.xslt.Element }
   * {@code >} {@link JAXBElement }{@code <}{@link Text }{@code >}
   * {@link JAXBElement }{@code <}{@link AnyType }{@code >}
   * 
   * 
   */
  public List<Object> getContent() {
    if (content == null) {
      content = new ArrayList<Object>();
    }
    return this.content;
  }

  /**
   * Gets the value of the match property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getMatch() {
    return match;
  }

  /**
   * Sets the value of the match property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setMatch(String value) {
    this.match = value;
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
   * Gets the value of the priority property.
   * 
   * @return possible object is {@link BigDecimal }
   * 
   */
  public BigDecimal getPriority() {
    return priority;
  }

  /**
   * Sets the value of the priority property.
   * 
   * @param value
   *          allowed object is {@link BigDecimal }
   * 
   */
  public void setPriority(BigDecimal value) {
    this.priority = value;
  }

  /**
   * Gets the value of the mode property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getMode() {
    return mode;
  }

  /**
   * Sets the value of the mode property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setMode(String value) {
    this.mode = value;
  }

  /**
   * Gets the value of the space property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSpace() {
    return space;
  }

  /**
   * Sets the value of the space property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setSpace(String value) {
    this.space = value;
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
