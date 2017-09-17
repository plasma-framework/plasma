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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/1999/XSL/Transform}when" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.w3.org/1999/XSL/Transform}otherwise" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}space"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "whens", "otherwise" })
public class Choose extends AnyType {

  @XmlElement(name = "when", required = true)
  protected List<When> whens;
  protected Otherwise otherwise;
  @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String space;

  /**
   * Gets the value of the whens property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the whens property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getWhens().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link When }
   * 
   * 
   */
  public List<When> getWhens() {
    if (whens == null) {
      whens = new ArrayList<When>();
    }
    return this.whens;
  }

  /**
   * Gets the value of the otherwise property.
   * 
   * @return possible object is {@link Otherwise }
   * 
   */
  public Otherwise getOtherwise() {
    return otherwise;
  }

  /**
   * Sets the value of the otherwise property.
   * 
   * @param value
   *          allowed object is {@link Otherwise }
   * 
   */
  public void setOtherwise(Otherwise value) {
    this.otherwise = value;
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

}
