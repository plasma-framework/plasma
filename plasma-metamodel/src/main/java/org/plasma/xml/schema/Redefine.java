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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.w3.org/2001/XMLSchema}annotation"/>
 *         &lt;group ref="{http://www.w3.org/2001/XMLSchema}redefinable"/>
 *       &lt;/choice>
 *       &lt;attribute name="schemaLocation" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotationsAndSimpleTypesAndComplexTypes" })
@XmlRootElement(name = "redefine")
public class Redefine extends OpenAttrs {

  @XmlElements({ @XmlElement(name = "group", type = Group.class),
      @XmlElement(name = "annotation", type = Annotation.class),
      @XmlElement(name = "simpleType", type = SimpleType.class),
      @XmlElement(name = "complexType", type = ComplexType.class),
      @XmlElement(name = "attributeGroup", type = AttributeGroup.class) })
  protected List<OpenAttrs> annotationsAndSimpleTypesAndComplexTypes;
  @XmlAttribute(required = true)
  @XmlSchemaType(name = "anyURI")
  protected String schemaLocation;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name = "ID")
  protected String id;

  /**
   * Gets the value of the annotationsAndSimpleTypesAndComplexTypes property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the annotationsAndSimpleTypesAndComplexTypes property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getAnnotationsAndSimpleTypesAndComplexTypes().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link Group }
   * {@link Annotation } {@link SimpleType } {@link ComplexType }
   * {@link AttributeGroup }
   * 
   * 
   */
  public List<OpenAttrs> getAnnotationsAndSimpleTypesAndComplexTypes() {
    if (annotationsAndSimpleTypesAndComplexTypes == null) {
      annotationsAndSimpleTypesAndComplexTypes = new ArrayList<OpenAttrs>();
    }
    return this.annotationsAndSimpleTypesAndComplexTypes;
  }

  /**
   * Gets the value of the schemaLocation property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSchemaLocation() {
    return schemaLocation;
  }

  /**
   * Sets the value of the schemaLocation property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setSchemaLocation(String value) {
    this.schemaLocation = value;
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
