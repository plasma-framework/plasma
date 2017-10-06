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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.Wildcard;

/**
 * <p>
 * Java class for PathNode complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="PathNode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pathElement" type="{http://www.terrameta.org/plasma/query}AbstractPathElement"/>
 *         &lt;element ref="{http://www.terrameta.org/plasma/query}Where" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PathNode", propOrder = { "pathElement", "where" })
@XmlRootElement(name = "PathNode")
public class PathNode {

  @XmlElementRef
  // IMPORTANT - maps single container/field to multiple subclass 'ref' elements
  // in XSD choice
  protected AbstractPathElement pathElement;
  @XmlElement(name = "Where")
  protected Where where;

  public PathNode() {
  }

  public PathNode(String name) {
    this.pathElement = createPathElement(name);
  }

  /**
   * Gets the value of the pathElement property.
   * 
   * @return possible object is {@link AbstractPathElement }
   * 
   */
  public AbstractPathElement getPathElement() {
    return pathElement;
  }

  /**
   * Sets the value of the pathElement property.
   * 
   * @param value
   *          allowed object is {@link AbstractPathElement }
   * 
   */
  public void setPathElement(AbstractPathElement value) {
    this.pathElement = value;
  }

  /**
   * Gets the value of the where property.
   * 
   * @return possible object is {@link Where }
   * 
   */
  public Where getWhere() {
    return where;
  }

  /**
   * Sets the value of the where property.
   * 
   * @param value
   *          allowed object is {@link Where }
   * 
   */
  public void setWhere(Where value) {
    this.where = value;
  }

  public AbstractPathElement createPathElement(String name) {
    if (!Wildcard.WILDCARD_CHAR.equals(name))
      return new PathElement(name);
    else
      return new WildcardPathElement(name);
  }

}
