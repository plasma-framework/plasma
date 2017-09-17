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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;attribute name="namespace" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="schemaLocation" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "import")
public class Import extends Annotated {

  @XmlAttribute
  @XmlSchemaType(name = "anyURI")
  protected String namespace;
  @XmlAttribute
  @XmlSchemaType(name = "anyURI")
  protected String schemaLocation;

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

  /**
   * Gets the value of the schemaLocation property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSchemaLocation() {
    return schemaLocation;
  }

  /**
   * Sets the value of the schemaLocation property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setSchemaLocation(String value) {
    this.schemaLocation = value;
  }

}
