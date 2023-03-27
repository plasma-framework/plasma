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

import java.math.BigInteger;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

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
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}wildcard">
 *       &lt;attGroup ref="{http://www.w3.org/2001/XMLSchema}occurs"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "any")
public class Any extends Wildcard {

  @XmlAttribute
  @XmlSchemaType(name = "nonNegativeInteger")
  protected BigInteger minOccurs;
  @XmlAttribute
  @XmlSchemaType(name = "allNNI")
  protected String maxOccurs;

  /**
   * Gets the value of the minOccurs property.
   * 
   * @return possible object is {@link BigInteger }
   * 
   */
  public BigInteger getMinOccurs() {
    if (minOccurs == null) {
      return new BigInteger("1");
    } else {
      return minOccurs;
    }
  }

  /**
   * Sets the value of the minOccurs property.
   * 
   * @param value
   *          allowed object is {@link BigInteger }
   * 
   */
  public void setMinOccurs(BigInteger value) {
    this.minOccurs = value;
  }

  /**
   * Gets the value of the maxOccurs property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getMaxOccurs() {
    if (maxOccurs == null) {
      return "1";
    } else {
      return maxOccurs;
    }
  }

  /**
   * Sets the value of the maxOccurs property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setMaxOccurs(String value) {
    this.maxOccurs = value;
  }

}
