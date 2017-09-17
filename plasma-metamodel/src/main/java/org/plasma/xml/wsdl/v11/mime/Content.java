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

package org.plasma.xml.wsdl.v11.mime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.plasma.xml.wsdl.v11.TExtensibilityElement;

/**
 * <p>
 * Java class for contentType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="contentType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.xmlsoap.org/wsdl/}tExtensibilityElement">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="part" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contentType")
@XmlRootElement(name = "content")
public class Content extends TExtensibilityElement {

  @XmlAttribute
  protected String type;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "NMTOKEN")
  protected String part;

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

}
