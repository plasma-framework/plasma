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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * This abstract type is intended to serve as the base type for extension
 * elements. It includes the wsdl:required attribute which it is anticipated
 * will be used by most extension elements
 * 
 * 
 * <p>
 * Java class for ExtensionElement complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ExtensionElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute ref="{http://www.w3.org/ns/wsdl}required"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtensionElement")
public abstract class ExtensionElement {

  @XmlAttribute(namespace = "http://www.w3.org/ns/wsdl")
  protected Boolean required;

  /**
   * Gets the value of the required property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isRequired() {
    return required;
  }

  /**
   * Sets the value of the required property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setRequired(Boolean value) {
    this.required = value;
  }

}
