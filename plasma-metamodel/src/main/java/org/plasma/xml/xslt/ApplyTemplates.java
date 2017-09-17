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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.w3.org/1999/XSL/Transform}sort"/>
 *         &lt;element ref="{http://www.w3.org/1999/XSL/Transform}with-param"/>
 *       &lt;/choice>
 *       &lt;attribute name="select" type="{http://www.w3.org/1999/XSL/Transform}expression" default="node()" />
 *       &lt;attribute name="mode" type="{http://www.w3.org/1999/XSL/Transform}QName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "sortsAndWithParams" })
public class ApplyTemplates extends AnyType {

  @XmlElements({ @XmlElement(name = "with-param", type = Variable.class),
      @XmlElement(name = "sort", type = Sort.class) })
  protected List<Object> sortsAndWithParams;
  @XmlAttribute
  protected String select;
  @XmlAttribute
  protected String mode;

  /**
   * Gets the value of the sortsAndWithParams property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the sortsAndWithParams property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getSortsAndWithParams().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link Variable }
   * {@link Sort }
   * 
   * 
   */
  public List<Object> getSortsAndWithParams() {
    if (sortsAndWithParams == null) {
      sortsAndWithParams = new ArrayList<Object>();
    }
    return this.sortsAndWithParams;
  }

  /**
   * Gets the value of the select property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSelect() {
    if (select == null) {
      return "node()";
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
   * Gets the value of the mode property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getMode() {
    return mode;
  }

  /**
   * Sets the value of the mode property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setMode(String value) {
    this.mode = value;
  }

}
