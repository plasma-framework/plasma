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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import org.plasma.query.DataProperty;
import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Variable", propOrder = { "property" })
@XmlRootElement(name = "Variable")
public class Variable {

  @XmlElement(name = "Property", required = true)
  protected Property property;

  // ----------------/
  // - Constructors -/
  // ----------------/

  public Variable() {
    super();
  } // -- org.plasma.mda.query.Variable()

  public Variable(Property property) {
    this();
    this.property = property;
  }

  /**
   * Gets the value of the property property.
   * 
   * @return possible object is {@link Property }
   * 
   */
  public DataProperty getProperty() {
    return property;
  }

  /**
   * Sets the value of the property property.
   * 
   * @param value
   *          allowed object is {@link Property }
   * 
   */
  public void setProperty(Property value) {
    this.property = value;
  }

  public void accept(QueryVisitor visitor) {
    visitor.start(this);
    if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal()) {
      if (property != null)
        property.accept(visitor);
    }
    visitor.end(this);
  }

}
