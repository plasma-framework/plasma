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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

/**
 * 
 * itemType attribute and simpleType child are mutually exclusive, but one or
 * other is required
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
 *       &lt;sequence>
 *         &lt;element name="simpleType" type="{http://www.w3.org/2001/XMLSchema}localSimpleType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="itemType" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "simpleType" })
@XmlRootElement(name = "list")
public class List extends Annotated {

  protected LocalSimpleType simpleType;
  @XmlAttribute
  protected QName itemType;

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
   * Gets the value of the itemType property.
   * 
   * @return possible object is {@link QName }
   * 
   */
  public QName getItemType() {
    return itemType;
  }

  /**
   * Sets the value of the itemType property.
   * 
   * @param value
   *          allowed object is {@link QName }
   * 
   */
  public void setItemType(QName value) {
    this.itemType = value;
  }

}
