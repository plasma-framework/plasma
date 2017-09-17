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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
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
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}annotated">
 *       &lt;choice>
 *         &lt;element name="restriction" type="{http://www.w3.org/2001/XMLSchema}simpleRestrictionType"/>
 *         &lt;element name="extension" type="{http://www.w3.org/2001/XMLSchema}simpleExtensionType"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "extension", "restriction" })
@XmlRootElement(name = "simpleContent")
public class SimpleContent extends Annotated {

  protected SimpleExtensionType extension;
  protected SimpleRestrictionType restriction;

  /**
   * Gets the value of the extension property.
   * 
   * @return possible object is {@link SimpleExtensionType }
   * 
   */
  public SimpleExtensionType getExtension() {
    return extension;
  }

  /**
   * Sets the value of the extension property.
   * 
   * @param value
   *          allowed object is {@link SimpleExtensionType }
   * 
   */
  public void setExtension(SimpleExtensionType value) {
    this.extension = value;
  }

  /**
   * Gets the value of the restriction property.
   * 
   * @return possible object is {@link SimpleRestrictionType }
   * 
   */
  public SimpleRestrictionType getRestriction() {
    return restriction;
  }

  /**
   * Sets the value of the restriction property.
   * 
   * @param value
   *          allowed object is {@link SimpleRestrictionType }
   * 
   */
  public void setRestriction(SimpleRestrictionType value) {
    this.restriction = value;
  }

}
