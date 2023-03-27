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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Function complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="Function">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.terrameta.org/plasma/query}FunctionArg" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.terrameta.org/plasma/query}FunctionValues" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Function", propOrder = { "functionArgs" })
@XmlRootElement(name = "Function")
public class Function {

  public Function() {
  }

  public Function(FunctionName name) {
    this.name = name;
  }

  @XmlElement(name = "FunctionArg")
  protected List<FunctionArg> functionArgs;
  @XmlAttribute(name = "name", required = true)
  protected FunctionName name;
  @XmlAttribute(name = "appFunctionName")
  protected String appFunctionName;

  /**
   * Gets the value of the functionArgs property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the functionArgs property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getFunctionArgs().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link FunctionArg }
   * 
   * 
   */
  public List<FunctionArg> getFunctionArgs() {
    if (functionArgs == null) {
      functionArgs = new ArrayList<FunctionArg>();
    }
    return this.functionArgs;
  }

  /**
   * Gets the value of the name property.
   * 
   * @return possible object is {@link FunctionName }
   * 
   */
  public FunctionName getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   * 
   * @param value
   *          allowed object is {@link FunctionName }
   * 
   */
  public void setName(FunctionName value) {
    this.name = value;
  }

  /**
   * Gets the value of the appFunctionName property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getAppFunctionName() {
    return appFunctionName;
  }

  /**
   * Sets the value of the appFunctionName property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setAppFunctionName(String value) {
    this.appFunctionName = value;
  }
}
