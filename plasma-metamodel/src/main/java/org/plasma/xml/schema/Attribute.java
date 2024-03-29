/** * Copyright 2017 TerraMeta Software, Inc. * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
package org.plasma.xml.schema;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

/**
 * <p>
 * Java class for attribute complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="attribute">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}annotated">
 *       &lt;sequence>
 *         &lt;element name="simpleType" type="{http://www.w3.org/2001/XMLSchema}localSimpleType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.w3.org/2001/XMLSchema}defRef"/>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="use" default="optional">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="prohibited"/>
 *             &lt;enumeration value="optional"/>
 *             &lt;enumeration value="required"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="default" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fixed" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="form" type="{http://www.w3.org/2001/XMLSchema}formChoice" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attribute", propOrder = { "simpleType" })
public class Attribute extends Annotated {

  protected LocalSimpleType simpleType;
  @XmlAttribute
  protected QName type;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String use;
  @XmlAttribute(name = "default")
  protected String _default;
  @XmlAttribute
  protected String fixed;
  @XmlAttribute
  protected FormChoice form;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "NCName")
  protected String name;
  @XmlAttribute
  protected QName ref;

  /**
   * Gets the value of the simpleType property.
   * 
   * @return possible object is {@link LocalSimpleType }
   * 
   */
  public LocalSimpleType getSimpleType() {
    return simpleType;
  }

  /**
   * Sets the value of the simpleType property.
   * 
   * @param value
   *          allowed object is {@link LocalSimpleType }
   * 
   */
  public void setSimpleType(LocalSimpleType value) {
    this.simpleType = value;
  }

  /**
   * Gets the value of the type property.
   * 
   * @return possible object is {@link QName }
   * 
   */
  public QName getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   * 
   * @param value
   *          allowed object is {@link QName }
   * 
   */
  public void setType(QName value) {
    this.type = value;
  }

  /**
   * Gets the value of the use property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getUse() {
    if (use == null) {
      return "optional";
    } else {
      return use;
    }
  }

  /**
   * Sets the value of the use property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setUse(String value) {
    this.use = value;
  }

  /**
   * Gets the value of the default property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDefault() {
    return _default;
  }

  /**
   * Sets the value of the default property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setDefault(String value) {
    this._default = value;
  }

  /**
   * Gets the value of the fixed property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getFixed() {
    return fixed;
  }

  /**
   * Sets the value of the fixed property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setFixed(String value) {
    this.fixed = value;
  }

  /**
   * Gets the value of the form property.
   * 
   * @return possible object is {@link FormChoice }
   * 
   */
  public FormChoice getForm() {
    return form;
  }

  /**
   * Sets the value of the form property.
   * 
   * @param value
   *          allowed object is {@link FormChoice }
   * 
   */
  public void setForm(FormChoice value) {
    this.form = value;
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

  /**
   * Gets the value of the ref property.
   * 
   * @return possible object is {@link QName }
   * 
   */
  public QName getRef() {
    return ref;
  }

  /**
   * Sets the value of the ref property.
   * 
   * @param value
   *          allowed object is {@link QName }
   * 
   */
  public void setRef(QName value) {
    this.ref = value;
  }

}
