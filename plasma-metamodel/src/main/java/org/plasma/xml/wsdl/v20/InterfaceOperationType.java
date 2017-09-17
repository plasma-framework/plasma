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

package org.plasma.xml.wsdl.v20;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.dom.Element;

/**
 * <p>
 * Java class for InterfaceOperationType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="InterfaceOperationType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/ns/wsdl}ExtensibleDocumentedType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="input" type="{http://www.w3.org/ns/wsdl}MessageRefType"/>
 *         &lt;element name="output" type="{http://www.w3.org/ns/wsdl}MessageRefType"/>
 *         &lt;element name="infault" type="{http://www.w3.org/ns/wsdl}MessageRefFaultType"/>
 *         &lt;element name="outfault" type="{http://www.w3.org/ns/wsdl}MessageRefFaultType"/>
 *         &lt;any/>
 *       &lt;/choice>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="pattern" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="safe" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InterfaceOperationType", propOrder = { "inputsAndOutputsAndInfaults" })
public class InterfaceOperationType extends ExtensibleDocumentedType {

  @XmlElementRefs({
      @XmlElementRef(name = "infault", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class),
      @XmlElementRef(name = "output", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class),
      @XmlElementRef(name = "input", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class),
      @XmlElementRef(name = "outfault", namespace = "http://www.w3.org/ns/wsdl", type = JAXBElement.class) })
  @XmlAnyElement
  protected List<Object> inputsAndOutputsAndInfaults;
  @XmlAttribute(required = true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "NCName")
  protected String name;
  @XmlAttribute
  @XmlSchemaType(name = "anyURI")
  protected String pattern;
  @XmlAttribute
  protected Boolean safe;
  @XmlAttribute
  @XmlSchemaType(name = "anyURI")
  protected String style;

  /**
   * Gets the value of the inputsAndOutputsAndInfaults property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the inputsAndOutputsAndInfaults property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getInputsAndOutputsAndInfaults().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link JAXBElement }{@code <}{@link MessageRefFaultType }{@code >}
   * {@link JAXBElement }{@code <}{@link MessageRefType }{@code >}
   * {@link JAXBElement }{@code <}{@link MessageRefType }{@code >} {@link Element }
   * {@link JAXBElement }{@code <}{@link MessageRefFaultType }{@code >}
   * 
   * 
   */
  public List<Object> getInputsAndOutputsAndInfaults() {
    if (inputsAndOutputsAndInfaults == null) {
      inputsAndOutputsAndInfaults = new ArrayList<Object>();
    }
    return this.inputsAndOutputsAndInfaults;
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
   * Gets the value of the pattern property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * Sets the value of the pattern property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setPattern(String value) {
    this.pattern = value;
  }

  /**
   * Gets the value of the safe property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isSafe() {
    return safe;
  }

  /**
   * Sets the value of the safe property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setSafe(Boolean value) {
    this.safe = value;
  }

  /**
   * Gets the value of the style property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getStyle() {
    return style;
  }

  /**
   * Sets the value of the style property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setStyle(String value) {
    this.style = value;
  }

}
