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

package org.plasma.xml.wsdl.v11.soap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

import org.plasma.xml.wsdl.v11.TExtensibilityElement;

/**
 * <p>
 * Java class for tHeader complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="tHeader">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.xmlsoap.org/wsdl/}tExtensibilityElement">
 *       &lt;sequence>
 *         &lt;element ref="{http://schemas.xmlsoap.org/wsdl/soap/}headerfault" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://schemas.xmlsoap.org/wsdl/soap/}tHeaderAttributes"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tHeader", propOrder = { "headerfaults" })
@XmlRootElement(name = "header")
public class Header extends TExtensibilityElement {

  @XmlElement(name = "headerfault")
  protected List<Headerfault> headerfaults;
  @XmlAttribute(required = true)
  protected QName message;
  @XmlAttribute(required = true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "NMTOKEN")
  protected String part;
  @XmlAttribute(required = true)
  protected UseChoice use;
  @XmlAttribute(name = "encodingStyle")
  protected List<String> encodingStyles;
  @XmlAttribute
  @XmlSchemaType(name = "anyURI")
  protected String namespace;

  /**
   * Gets the value of the headerfaults property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the headerfaults property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getHeaderfaults().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link Headerfault }
   * 
   * 
   */
  public List<Headerfault> getHeaderfaults() {
    if (headerfaults == null) {
      headerfaults = new ArrayList<Headerfault>();
    }
    return this.headerfaults;
  }

  /**
   * Gets the value of the message property.
   * 
   * @return possible object is {@link QName }
   * 
   */
  public QName getMessage() {
    return message;
  }

  /**
   * Sets the value of the message property.
   * 
   * @param value
   *          allowed object is {@link QName }
   * 
   */
  public void setMessage(QName value) {
    this.message = value;
  }

  /**
   * Gets the value of the part property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getPart() {
    return part;
  }

  /**
   * Sets the value of the part property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setPart(String value) {
    this.part = value;
  }

  /**
   * Gets the value of the use property.
   * 
   * @return possible object is {@link UseChoice }
   * 
   */
  public UseChoice getUse() {
    return use;
  }

  /**
   * Sets the value of the use property.
   * 
   * @param value
   *          allowed object is {@link UseChoice }
   * 
   */
  public void setUse(UseChoice value) {
    this.use = value;
  }

  /**
   * Gets the value of the encodingStyles property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the encodingStyles property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getEncodingStyles().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link String }
   * 
   * 
   */
  public List<String> getEncodingStyles() {
    if (encodingStyles == null) {
      encodingStyles = new ArrayList<String>();
    }
    return this.encodingStyles;
  }

  /**
   * Gets the value of the namespace property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Sets the value of the namespace property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setNamespace(String value) {
    this.namespace = value;
  }

}
