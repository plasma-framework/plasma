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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
 *       &lt;attGroup ref="{http://www.w3.org/1999/XSL/Transform}disable-output-escaping"/>
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/>
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}space"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Text extends AnyType {

  @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
  protected String lang;
  @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String space;
  @XmlAttribute(name = "disable-output-escaping")
  protected YesOrNo disableOutputEscaping;

  /**
   * Gets the value of the lang property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getLang() {
    return lang;
  }

  /**
   * Sets the value of the lang property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setLang(String value) {
    this.lang = value;
  }

  /**
   * Gets the value of the space property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSpace() {
    return space;
  }

  /**
   * Sets the value of the space property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setSpace(String value) {
    this.space = value;
  }

  /**
   * Gets the value of the disableOutputEscaping property.
   * 
   * @return possible object is {@link YesOrNo }
   * 
   */
  public YesOrNo getDisableOutputEscaping() {
    if (disableOutputEscaping == null) {
      return YesOrNo.NO;
    } else {
      return disableOutputEscaping;
    }
  }

  /**
   * Sets the value of the disableOutputEscaping property.
   * 
   * @param value
   *          allowed object is {@link YesOrNo }
   * 
   */
  public void setDisableOutputEscaping(YesOrNo value) {
    this.disableOutputEscaping = value;
  }

}
