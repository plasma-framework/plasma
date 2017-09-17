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

package org.plasma.xml.sdox;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.w3c.dom.Element;

/**
 * <p>
 * Java class for ChangeSummaryType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ChangeSummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="create" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="delete" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="logging" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangeSummaryType", propOrder = { "anies" })
public class ChangeSummaryType {

  @XmlAnyElement
  protected List<Element> anies;
  @XmlAttribute
  protected String create;
  @XmlAttribute
  protected String delete;
  @XmlAttribute
  protected Boolean logging;

  /**
   * Gets the value of the anies property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the anies property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getAnies().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link Element }
   * 
   * 
   */
  public List<Element> getAnies() {
    if (anies == null) {
      anies = new ArrayList<Element>();
    }
    return this.anies;
  }

  /**
   * Gets the value of the create property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getCreate() {
    return create;
  }

  /**
   * Sets the value of the create property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setCreate(String value) {
    this.create = value;
  }

  /**
   * Gets the value of the delete property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDelete() {
    return delete;
  }

  /**
   * Sets the value of the delete property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setDelete(String value) {
    this.delete = value;
  }

  /**
   * Gets the value of the logging property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isLogging() {
    return logging;
  }

  /**
   * Sets the value of the logging property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setLogging(Boolean value) {
    this.logging = value;
  }

}
