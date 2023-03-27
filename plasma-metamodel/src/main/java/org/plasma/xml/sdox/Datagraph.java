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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

/**
 * <p>
 * Java class for DataGraphType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DataGraphType">
 *   &lt;complexContent>
 *     &lt;extension base="{commonj.sdo}BaseDataGraphType">
 *       &lt;sequence>
 *         &lt;any/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataGraphType", propOrder = { "any" })
@XmlRootElement(name = "datagraph")
public class Datagraph extends BaseDataGraphType {

  @XmlAnyElement
  protected Element any;

  /**
   * Gets the value of the any property.
   * 
   * @return possible object is {@link Element }
   * 
   */
  public Element getAny() {
    return any;
  }

  /**
   * Sets the value of the any property.
   * 
   * @param value
   *          allowed object is {@link Element }
   * 
   */
  public void setAny(Element value) {
    this.any = value;
  }

}
