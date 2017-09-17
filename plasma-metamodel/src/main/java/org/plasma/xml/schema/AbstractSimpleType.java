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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Java class for simpleType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="simpleType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.w3.org/2001/XMLSchema}annotated">
 *       &lt;group ref="{http://www.w3.org/2001/XMLSchema}simpleDerivation"/>
 *       &lt;attribute name="final" type="{http://www.w3.org/2001/XMLSchema}simpleDerivationSet" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simpleType", propOrder = { "union", "list", "restriction" })
public abstract class AbstractSimpleType extends Annotated {
  private static Log log = LogFactory.getLog(AbstractSimpleType.class);

  protected Union union;
  protected org.plasma.xml.schema.List list;
  protected Restriction restriction;
  @XmlAttribute(name = "final")
  @XmlSchemaType(name = "simpleDerivationSet")
  protected java.util.List<String> finals;
  @XmlAttribute
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name = "NCName")
  protected String name;

  /**
   * Gets the value of the union property.
   * 
   * @return possible object is {@link Union }
   * 
   */
  public Union getUnion() {
    return union;
  }

  /**
   * Sets the value of the union property.
   * 
   * @param value
   *          allowed object is {@link Union }
   * 
   */
  public void setUnion(Union value) {
    this.union = value;
  }

  /**
   * Gets the value of the list property.
   * 
   * @return possible object is {@link org.plasma.xml.schema.List }
   * 
   */
  public org.plasma.xml.schema.List getList() {
    return list;
  }

  /**
   * Sets the value of the list property.
   * 
   * @param value
   *          allowed object is {@link org.plasma.xml.schema.List }
   * 
   */
  public void setList(org.plasma.xml.schema.List value) {
    this.list = value;
  }

  /**
   * Gets the value of the restriction property.
   * 
   * @return possible object is {@link Restriction }
   * 
   */
  public Restriction getRestriction() {
    return restriction;
  }

  /**
   * Sets the value of the restriction property.
   * 
   * @param value
   *          allowed object is {@link Restriction }
   * 
   */
  public void setRestriction(Restriction value) {
    this.restriction = value;
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
  public java.util.List<String> getFinals() {
    if (finals == null) {
      finals = new ArrayList<String>();
    }
    return this.finals;
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
   * Encapsulates the (breadth-first) traversal logic for XSD SimpleType
   * hierarchies across restrictions, lists, unions and other elements, creating
   * "visit" events ala. the Visitor pattern for the given visitor.
   * 
   * @see org.plasma.xml.schema.Restriction
   * @see org.plasma.xml.schema.List
   * @see org.plasma.xml.schema.Union
   */
  public void accept(SimpleTypeVisitor visitor) {
    this.accept(this, null, visitor, 0);
  }

  /**
   * Encapsulates the (breadth-first) traversal logic for XSD SimpleType
   * hierarchies across restrictions, lists, unions and other elements, creating
   * "visit" events ala. the Visitor pattern for the given visitor.
   * 
   * @see org.plasma.xml.schema.Restriction
   * @see org.plasma.xml.schema.List
   * @see org.plasma.xml.schema.Union
   */
  private void accept(AbstractSimpleType target, AbstractSimpleType source,
      SimpleTypeVisitor visitor, int level) {

    // depth-first visit event
    visitor.visit(target, source, level);

    if (target.getRestriction() != null) {
      Restriction restriction = target.getRestriction();
      QName typeName = restriction.getBase();
      if (typeName != null) {
        if (typeName.getNamespaceURI() != null) {
          if (typeName.getNamespaceURI().equals(visitor.getTargetNamespace())) {
            SimpleType baseType = visitor.getTopLevelSimpleType(typeName);
            accept(baseType, target, visitor, level++);
          } else if (typeName.getNamespaceURI().equals(SchemaConstants.XMLSCHEMA_NAMESPACE_URI)) {
            // leaf
          } else
            log.warn("could not process namespace URI found for base type, " + typeName.toString());
        } else
          log.warn("could not process (no namespace) base type, " + typeName.toString());
      } else {
        LocalSimpleType localSimpleType = restriction.getSimpleType();
        if (localSimpleType != null) {
          accept(localSimpleType, target, visitor, level++);
        } else
          log.warn("no base type or local type found on restriction for type, " + target.getName());
      }
    } else if (target.getList() != null) {
      org.plasma.xml.schema.List typeList = target.getList();
      if (typeList.getSimpleType() != null)
        log.warn("ignoring local simple type child for simple type, " + target.getName());
      QName typeName = typeList.getItemType();
      if (typeName != null && typeName.getNamespaceURI() != null) {
        if (typeName.getNamespaceURI().equals(visitor.getTargetNamespace())) {
          SimpleType baseType = visitor.getTopLevelSimpleType(typeName);
          accept(baseType, target, visitor, level++);
        } else if (typeName.getNamespaceURI().equals(SchemaConstants.XMLSCHEMA_NAMESPACE_URI)) {
          // leaf
        } else
          log.warn("could not process namespace URI found for type, " + typeName.toString());
      } else
        log.warn("no namespace URI found for type, " + typeName.toString());
    } else if (target.getUnion() != null) {
      Union union = target.getUnion();
      for (QName member : union.getMemberTypes()) {
        SimpleType baseType = visitor.getTopLevelSimpleType(member);
        accept(baseType, target, visitor, level++);
      }

      // process leaf local simple types
      for (LocalSimpleType localBaseType : union.getSimpleTypes()) {
        accept(localBaseType, target, visitor, level++);
      }
    }
  }
}
