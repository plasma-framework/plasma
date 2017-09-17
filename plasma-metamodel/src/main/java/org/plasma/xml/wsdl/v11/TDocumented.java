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

package org.plasma.xml.wsdl.v11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * This type is extended by component types to allow them to be documented
 * 
 * 
 * <p>
 * Java class for tDocumented complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="tDocumented">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentation" type="{http://schemas.xmlsoap.org/wsdl/}tDocumentation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDocumented", propOrder = { "documentation" })
public class TDocumented {

  protected TDocumentation documentation;

  /**
   * Gets the value of the documentation property.
   * 
   * @return possible object is {@link TDocumentation }
   * 
   */
  public TDocumentation getDocumentation() {
    return documentation;
  }

  /**
   * Sets the value of the documentation property.
   * 
   * @param value
   *          allowed object is {@link TDocumentation }
   * 
   */
  public void setDocumentation(TDocumentation value) {
    this.documentation = value;
  }

}
