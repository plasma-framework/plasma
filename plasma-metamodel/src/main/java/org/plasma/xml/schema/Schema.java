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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}openAttrs">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{http://www.w3.org/2001/XMLSchema}include"/>
 *           &lt;element ref="{http://www.w3.org/2001/XMLSchema}import"/>
 *           &lt;element ref="{http://www.w3.org/2001/XMLSchema}redefine"/>
 *           &lt;element ref="{http://www.w3.org/2001/XMLSchema}annotation"/>
 *         &lt;/choice>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;group ref="{http://www.w3.org/2001/XMLSchema}schemaTop"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="targetNamespace" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="finalDefault" type="{http://www.w3.org/2001/XMLSchema}fullDerivationSet" default="" />
 *       &lt;attribute name="blockDefault" type="{http://www.w3.org/2001/XMLSchema}blockSet" default="" />
 *       &lt;attribute name="attributeFormDefault" type="{http://www.w3.org/2001/XMLSchema}formChoice" default="unqualified" />
 *       &lt;attribute name="elementFormDefault" type="{http://www.w3.org/2001/XMLSchema}formChoice" default="unqualified" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "includesAndImportsAndRedefines",
    "simpleTypesAndComplexTypesAndGroups" })
@XmlRootElement(name = "schema")
public class Schema extends OpenAttrs {

  @XmlElements({ @XmlElement(name = "import", type = Import.class),
      @XmlElement(name = "redefine", type = Redefine.class),
      @XmlElement(name = "include", type = Include.class),
      @XmlElement(name = "annotation", type = Annotation.class) })
  protected List<OpenAttrs> includesAndImportsAndRedefines;
  @XmlElements({ @XmlElement(name = "complexType", type = ComplexType.class),
      @XmlElement(name = "group", type = Group.class),
      @XmlElement(name = "attributeGroup", type = AttributeGroup.class),
      @XmlElement(name = "notation", type = Notation.class),
      @XmlElement(name = "attribute", type = TopLevelAttributeType.class),
      @XmlElement(name = "element", type = Element.class),
      @XmlElement(name = "simpleType", type = SimpleType.class) })
  protected List<Annotated> simpleTypesAndComplexTypesAndGroups;
  @XmlAttribute
  @XmlSchemaType(name = "anyURI")
  protected String targetNamespace;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "token")
  protected String version;
  @XmlAttribute(name = "finalDefault")
  @XmlSchemaType(name = "fullDerivationSet")
  protected List<String> finalDefaults;
  @XmlAttribute(name = "blockDefault")
  @XmlSchemaType(name = "blockSet")
  protected List<String> blockDefaults;
  @XmlAttribute
  protected FormChoice attributeFormDefault;
  @XmlAttribute
  protected FormChoice elementFormDefault;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  /**
   * Gets the value of the includesAndImportsAndRedefines property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the includesAndImportsAndRedefines property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getIncludesAndImportsAndRedefines().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link Import }
   * {@link Redefine } {@link Include } {@link Annotation }
   * 
   * 
   */
  public List<OpenAttrs> getIncludesAndImportsAndRedefines() {
    if (includesAndImportsAndRedefines == null) {
      includesAndImportsAndRedefines = new ArrayList<OpenAttrs>();
    }
    return this.includesAndImportsAndRedefines;
  }

  /**
   * Gets the value of the simpleTypesAndComplexTypesAndGroups property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the simpleTypesAndComplexTypesAndGroups property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getSimpleTypesAndComplexTypesAndGroups().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link ComplexType } {@link Group } {@link AttributeGroup } {@link Notation }
   * {@link TopLevelAttributeType } {@link Element } {@link SimpleType }
   * 
   * 
   */
  public List<Annotated> getSimpleTypesAndComplexTypesAndGroups() {
    if (simpleTypesAndComplexTypesAndGroups == null) {
      simpleTypesAndComplexTypesAndGroups = new ArrayList<Annotated>();
    }
    return this.simpleTypesAndComplexTypesAndGroups;
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
   * Gets the value of the version property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getVersion() {
    return version;
  }

  /**
   * Sets the value of the version property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setVersion(String value) {
    this.version = value;
  }

  /**
   * Gets the value of the finalDefaults property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the finalDefaults property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getFinalDefaults().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link String }
   * 
   * 
   */
  public List<String> getFinalDefaults() {
    if (finalDefaults == null) {
      finalDefaults = new ArrayList<String>();
    }
    return this.finalDefaults;
  }

  /**
   * Gets the value of the blockDefaults property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the blockDefaults property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getBlockDefaults().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link String }
   * 
   * 
   */
  public List<String> getBlockDefaults() {
    if (blockDefaults == null) {
      blockDefaults = new ArrayList<String>();
    }
    return this.blockDefaults;
  }

  /**
   * Gets the value of the attributeFormDefault property.
   * 
   * @return possible object is {@link FormChoice }
   * 
   */
  public FormChoice getAttributeFormDefault() {
    if (attributeFormDefault == null) {
      return FormChoice.UNQUALIFIED;
    } else {
      return attributeFormDefault;
    }
  }

  /**
   * Sets the value of the attributeFormDefault property.
   * 
   * @param value
   *          allowed object is {@link FormChoice }
   * 
   */
  public void setAttributeFormDefault(FormChoice value) {
    this.attributeFormDefault = value;
  }

  /**
   * Gets the value of the elementFormDefault property.
   * 
   * @return possible object is {@link FormChoice }
   * 
   */
  public FormChoice getElementFormDefault() {
    if (elementFormDefault == null) {
      return FormChoice.UNQUALIFIED;
    } else {
      return elementFormDefault;
    }
  }

  /**
   * Sets the value of the elementFormDefault property.
   * 
   * @param value
   *          allowed object is {@link FormChoice }
   * 
   */
  public void setElementFormDefault(FormChoice value) {
    this.elementFormDefault = value;
  }

  /**
   * Gets the value of the id property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the value of the id property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setId(String value) {
    this.id = value;
  }

}
