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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for tDefinitions complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="tDefinitions">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.xmlsoap.org/wsdl/}tExtensibleDocumented">
 *       &lt;sequence>
 *         &lt;group ref="{http://schemas.xmlsoap.org/wsdl/}anyTopLevelOptionalElement" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="targetNamespace" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDefinitions", propOrder = { "importsAndTypesAndMessages" })
@XmlRootElement(name = "definitions")
public class Definitions extends TExtensibleDocumented {

  @XmlElements({ @XmlElement(name = "portType", type = TPortType.class),
      @XmlElement(name = "binding", type = TBinding.class),
      @XmlElement(name = "message", type = TMessage.class),
      @XmlElement(name = "import", type = TImport.class),
      @XmlElement(name = "types", type = TTypes.class),
      @XmlElement(name = "service", type = TService.class) })
  protected List<TDocumented> importsAndTypesAndMessages;
  @XmlAttribute
  @XmlSchemaType(name = "anyURI")
  protected String targetNamespace;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "NCName")
  protected String name;

  /**
   * Gets the value of the importsAndTypesAndMessages property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the importsAndTypesAndMessages property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getImportsAndTypesAndMessages().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link TPortType }
   * {@link TBinding } {@link TMessage } {@link TImport } {@link TTypes }
   * {@link TService }
   * 
   * 
   */
  public List<TDocumented> getImportsAndTypesAndMessages() {
    if (importsAndTypesAndMessages == null) {
      importsAndTypesAndMessages = new ArrayList<TDocumented>();
    }
    return this.importsAndTypesAndMessages;
  }

  /**
   * Gets the value of the targetNamespace property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getTargetNamespace() {
    return targetNamespace;
  }

  /**
   * Sets the value of the targetNamespace property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setTargetNamespace(String value) {
    this.targetNamespace = value;
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
