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

package org.plasma.xml.wsdl.v11;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for tBindingOperation complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="tBindingOperation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.xmlsoap.org/wsdl/}tExtensibleDocumented">
 *       &lt;sequence>
 *         &lt;element name="input" type="{http://schemas.xmlsoap.org/wsdl/}tBindingOperationMessage" minOccurs="0"/>
 *         &lt;element name="output" type="{http://schemas.xmlsoap.org/wsdl/}tBindingOperationMessage" minOccurs="0"/>
 *         &lt;element name="fault" type="{http://schemas.xmlsoap.org/wsdl/}tBindingOperationFault" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tBindingOperation", propOrder = { "input", "output", "faults" })
public class TBindingOperation extends TExtensibleDocumented {

  protected TBindingOperationMessage input;
  protected TBindingOperationMessage output;
  @XmlElement(name = "fault")
  protected List<TBindingOperationFault> faults;
  @XmlAttribute(required = true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "NCName")
  protected String name;

  /**
   * Gets the value of the input property.
   * 
   * @return possible object is {@link TBindingOperationMessage }
   * 
   */
  public TBindingOperationMessage getInput() {
    return input;
  }

  /**
   * Sets the value of the input property.
   * 
   * @param value
   *          allowed object is {@link TBindingOperationMessage }
   * 
   */
  public void setInput(TBindingOperationMessage value) {
    this.input = value;
  }

  /**
   * Gets the value of the output property.
   * 
   * @return possible object is {@link TBindingOperationMessage }
   * 
   */
  public TBindingOperationMessage getOutput() {
    return output;
  }

  /**
   * Sets the value of the output property.
   * 
   * @param value
   *          allowed object is {@link TBindingOperationMessage }
   * 
   */
  public void setOutput(TBindingOperationMessage value) {
    this.output = value;
  }

  /**
   * Gets the value of the faults property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the faults property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getFaults().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link TBindingOperationFault }
   * 
   * 
   */
  public List<TBindingOperationFault> getFaults() {
    if (faults == null) {
      faults = new ArrayList<TBindingOperationFault>();
    }
    return this.faults;
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
