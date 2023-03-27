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

package org.plasma.query.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextContent", propOrder = { "value" })
@XmlRootElement(name = "TextContent")
public class TextContent {

  @XmlValue
  protected String value;
  @XmlAttribute(required = true)
  protected QueryLanguageValues language;

  public TextContent() {
    super();
    setValue("");
    setLanguage(QueryLanguageValues.JDOQL);
  }

  public TextContent(QueryLanguageValues language) {
    super();
    setValue("");
    setLanguage(language);
  }

  /**
   * Gets the value of the value property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value of the value property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * Gets the value of the language property.
   * 
   * @return possible object is {@link QueryLanguageValues }
   * 
   */
  public QueryLanguageValues getLanguage() {
    return language;
  }

  /**
   * Sets the value of the language property.
   * 
   * @param value
   *          allowed object is {@link QueryLanguageValues }
   * 
   */
  public void setLanguage(QueryLanguageValues value) {
    this.language = value;
  }

}
