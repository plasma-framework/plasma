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
 *     &lt;extension base="{http://www.w3.org/1999/XSL/Transform}anyType">
 *       &lt;attribute name="select" type="{http://www.w3.org/1999/XSL/Transform}expression" default="." />
 *       &lt;attribute name="lang" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" />
 *       &lt;attribute name="data-type" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" default="text" />
 *       &lt;attribute name="order" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" default="ascending" />
 *       &lt;attribute name="case-order" type="{http://www.w3.org/1999/XSL/Transform}expr-avt" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "sort")
public class Sort extends AnyType {

  @XmlAttribute
  protected String select;
  @XmlAttribute
  protected String lang;
  @XmlAttribute(name = "data-type")
  protected String dataType;
  @XmlAttribute
  protected String order;
  @XmlAttribute(name = "case-order")
  protected String caseOrder;

  /**
   * Gets the value of the select property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSelect() {
    if (select == null) {
      return ".";
    } else {
      return select;
    }
  }

  /**
   * Sets the value of the select property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setSelect(String value) {
    this.select = value;
  }

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
   * Gets the value of the dataType property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDataType() {
    if (dataType == null) {
      return "text";
    } else {
      return dataType;
    }
  }

  /**
   * Sets the value of the dataType property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setDataType(String value) {
    this.dataType = value;
  }

  /**
   * Gets the value of the order property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getOrder() {
    if (order == null) {
      return "ascending";
    } else {
      return order;
    }
  }

  /**
   * Sets the value of the order property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setOrder(String value) {
    this.order = value;
  }

  /**
   * Gets the value of the caseOrder property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getCaseOrder() {
    return caseOrder;
  }

  /**
   * Sets the value of the caseOrder property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setCaseOrder(String value) {
    this.caseOrder = value;
  }

}
