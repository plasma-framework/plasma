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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import org.plasma.xml.wsdl.v11.TExtensibilityElement;

/**
 * <p>
 * Java class for tBinding complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="tBinding">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.xmlsoap.org/wsdl/}tExtensibilityElement">
 *       &lt;attribute name="transport" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="style" type="{http://schemas.xmlsoap.org/wsdl/soap/}tStyleChoice" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tBinding")
@XmlRootElement(name = "binding")
public class Binding extends TExtensibilityElement {

  @XmlAttribute(required = true)
  @XmlSchemaType(name = "anyURI")
  protected String transport;
  @XmlAttribute
  protected TStyleChoice style;

  /**
   * Gets the value of the transport property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getTransport() {
    return transport;
  }

  /**
   * Sets the value of the transport property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setTransport(String value) {
    this.transport = value;
  }

  /**
   * Gets the value of the style property.
   * 
   * @return possible object is {@link TStyleChoice }
   * 
   */
  public TStyleChoice getStyle() {
    return style;
  }

  /**
   * Sets the value of the style property.
   * 
   * @param value
   *          allowed object is {@link TStyleChoice }
   * 
   */
  public void setStyle(TStyleChoice value) {
    this.style = value;
  }

}
