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

package org.plasma.xml.schema;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

/**
 * 
 * The element element can be used either at the top level to define an
 * element-type binding globally, or within a content model to either reference
 * a globally-defined element or type or declare an element-type binding
 * locally. The ref form is not allowed at the top level.
 * 
 * <p>
 * Java class for element complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="element">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}annotated">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="simpleType" type="{http://www.w3.org/2001/XMLSchema}localSimpleType"/>
 *           &lt;element name="complexType" type="{http://www.w3.org/2001/XMLSchema}localComplexType"/>
 *         &lt;/choice>
 *         &lt;group ref="{http://www.w3.org/2001/XMLSchema}identityConstraint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.w3.org/2001/XMLSchema}occurs"/>
 *       &lt;attGroup ref="{http://www.w3.org/2001/XMLSchema}defRef"/>
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="substitutionGroup" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="default" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fixed" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="nillable" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="abstract" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="final" type="{http://www.w3.org/2001/XMLSchema}derivationSet" />
 *       &lt;attribute name="block" type="{http://www.w3.org/2001/XMLSchema}blockSet" />
 *       &lt;attribute name="form" type="{http://www.w3.org/2001/XMLSchema}formChoice" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "element", propOrder = { "complexType", "simpleType", "uniquesAndKeiesAndKeyreves" })
public abstract class AbstractElement extends Annotated {

  protected LocalComplexType complexType;
  protected LocalSimpleType simpleType;
  @XmlElementRefs({
      @XmlElementRef(name = "key", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "unique", namespace = "http://www.w3.org/2001/XMLSchema", type = JAXBElement.class),
      @XmlElementRef(name = "keyref", namespace = "http://www.w3.org/2001/XMLSchema", type = Keyref.class) })
  protected List<Object> uniquesAndKeiesAndKeyreves;
  @XmlAttribute
  protected QName type;
  @XmlAttribute
  protected QName substitutionGroup;
  @XmlAttribute(name = "default")
  protected String _default;
  @XmlAttribute
  protected String fixed;
  @XmlAttribute
  protected Boolean nillable;
  @XmlAttribute(name = "abstract")
  protected Boolean _abstract;
  @XmlAttribute(name = "final")
  @XmlSchemaType(name = "derivationSet")
  protected List<String> finals;
  @XmlAttribute(name = "block")
  @XmlSchemaType(name = "blockSet")
  protected List<String> blocks;
  @XmlAttribute
  protected FormChoice form;
  @XmlAttribute
  @XmlSchemaType(name = "nonNegativeInteger")
  protected BigInteger minOccurs;
  @XmlAttribute
  @XmlSchemaType(name = "allNNI")
  protected String maxOccurs;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "NCName")
  protected String name;
  @XmlAttribute
  protected QName ref;

  /**
   * Gets the value of the complexType property.
   * 
   * @return possible object is {@link LocalComplexType }
   * 
   */
  public LocalComplexType getComplexType() {
    return complexType;
  }

  /**
   * Sets the value of the complexType property.
   * 
   * @param value
   *          allowed object is {@link LocalComplexType }
   * 
   */
  public void setComplexType(LocalComplexType value) {
    this.complexType = value;
  }

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
   * Gets the value of the uniquesAndKeiesAndKeyreves property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the uniquesAndKeiesAndKeyreves property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getUniquesAndKeiesAndKeyreves().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link JAXBElement }{@code <}{@link Keybase }{@code >} {@link JAXBElement }
   * {@code <}{@link Keybase }{@code >} {@link Keyref }
   * 
   * 
   */
  public List<Object> getUniquesAndKeiesAndKeyreves() {
    if (uniquesAndKeiesAndKeyreves == null) {
      uniquesAndKeiesAndKeyreves = new ArrayList<Object>();
    }
    return this.uniquesAndKeiesAndKeyreves;
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
   * Gets the value of the substitutionGroup property.
   * 
   * @return possible object is {@link QName }
   * 
   */
  public QName getSubstitutionGroup() {
    return substitutionGroup;
  }

  /**
   * Sets the value of the substitutionGroup property.
   * 
   * @param value
   *          allowed object is {@link QName }
   * 
   */
  public void setSubstitutionGroup(QName value) {
    this.substitutionGroup = value;
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
   * Gets the value of the nillable property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public boolean isNillable() {
    if (nillable == null) {
      return false;
    } else {
      return nillable;
    }
  }

  /**
   * Sets the value of the nillable property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setNillable(Boolean value) {
    this.nillable = value;
  }

  /**
   * Gets the value of the abstract property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public boolean isAbstract() {
    if (_abstract == null) {
      return false;
    } else {
      return _abstract;
    }
  }

  /**
   * Sets the value of the abstract property.
   * 
   * @param value
   *          allowed object is {@link Boolean }
   * 
   */
  public void setAbstract(Boolean value) {
    this._abstract = value;
  }

  /**
   * Gets the value of the finals property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the finals property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getFinals().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link String }
   * 
   * 
   */
  public List<String> getFinals() {
    if (finals == null) {
      finals = new ArrayList<String>();
    }
    return this.finals;
  }

  /**
   * Gets the value of the blocks property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the blocks property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getBlocks().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link String }
   * 
   * 
   */
  public List<String> getBlocks() {
    if (blocks == null) {
      blocks = new ArrayList<String>();
    }
    return this.blocks;
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

  public boolean hasMinOccurs() {
    return minOccurs != null;
  }

  /**
   * Gets the value of the minOccurs property.
   * 
   * @return possible object is {@link BigInteger }
   * 
   */
  public BigInteger getMinOccurs() {
    if (minOccurs == null) {
      return new BigInteger("1");
    } else {
      return minOccurs;
    }
  }

  /**
   * Sets the value of the minOccurs property.
   * 
   * @param value
   *          allowed object is {@link BigInteger }
   * 
   */
  public void setMinOccurs(BigInteger value) {
    this.minOccurs = value;
  }

  /**
   * Gets the value of the maxOccurs property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getMaxOccurs() {
    if (maxOccurs == null) {
      return "1";
    } else {
      return maxOccurs;
    }
  }

  public boolean hasMaxOccurs() {
    return maxOccurs != null;
  }

  /**
   * Sets the value of the maxOccurs property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setMaxOccurs(String value) {
    this.maxOccurs = value;
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
