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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

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
 *       &lt;attGroup ref="{http://www.w3.org/1999/XSL/Transform}name"/>
 *       &lt;attribute name="match" use="required" type="{http://www.w3.org/1999/XSL/Transform}pattern" />
 *       &lt;attribute name="use" use="required" type="{http://www.w3.org/1999/XSL/Transform}expression" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Key extends AnyType {

  @XmlAttribute(required = true)
  protected String match;
  @XmlAttribute(required = true)
  protected String use;
  @XmlAttribute(required = true)
  protected String name;

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
   * Gets the value of the use property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getUse() {
    return use;
  }

  /**
   * Sets the value of the use property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setUse(String value) {
    this.use = value;
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

}
