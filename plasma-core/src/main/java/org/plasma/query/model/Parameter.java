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

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.math.BigDecimal;
import java.util.Collection;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Parameter", propOrder = { "collectionParameter", "dateParameter",
    "decimalParameter", "floatParameter", "intParameter", "stringParameter" })
@XmlRootElement(name = "Parameter")
public class Parameter {

  @XmlElement(name = "CollectionParameter", namespace = "")
  protected CollectionParameter collectionParameter;
  @XmlElement(name = "DateParameter", namespace = "")
  protected DateParameter dateParameter;
  @XmlElement(name = "DecimalParameter", namespace = "")
  protected DecimalParameter decimalParameter;
  @XmlElement(name = "FloatParameter", namespace = "")
  protected FloatParameter floatParameter;
  @XmlElement(name = "IntParameter", namespace = "")
  protected IntParameter intParameter;
  @XmlElement(name = "StringParameter", namespace = "")
  protected StringParameter stringParameter;

  public Parameter() {
    super();
  } // -- org.plasma.mda.query.Parameter()

  public Parameter(String value) {
    this();
    stringParameter = new StringParameter(value);
  }

  public Parameter(int value) {
    this();
    intParameter = new IntParameter(value);
  }

  public Parameter(float value) {
    this();
    floatParameter = new FloatParameter(value);
  }

  public Parameter(BigDecimal value) {
    this();
    decimalParameter = new DecimalParameter(value);
  }

  public Parameter(Collection value) {
    this();
    collectionParameter = new CollectionParameter(value);
  }

  public Parameter(java.util.Date value) {
    this();
    dateParameter = new DateParameter(value);
  }

  // -----------/
  // - Methods -/
  // -----------/

  public Object getValue() {
    if (stringParameter != null)
      return stringParameter.getValue();
    else if (intParameter != null)
      return new Integer(intParameter.getValue());
    else if (floatParameter != null)
      return new Float(floatParameter.getValue());
    else if (decimalParameter != null)
      return decimalParameter.getValue();
    else if (collectionParameter != null)
      return collectionParameter.getValue();
    else if (dateParameter != null)
      return dateParameter.getValue();
    else
      return null;
  }

  /**
   * Gets the value of the collectionParameter property.
   * 
   * @return possible object is {@link CollectionParameter }
   * 
   */
  public CollectionParameter getCollectionParameter() {
    return collectionParameter;
  }

  /**
   * Sets the value of the collectionParameter property.
   * 
   * @param value
   *          allowed object is {@link CollectionParameter }
   * 
   */
  public void setCollectionParameter(CollectionParameter value) {
    this.collectionParameter = value;
  }

  /**
   * Gets the value of the dateParameter property.
   * 
   * @return possible object is {@link DateParameter }
   * 
   */
  public DateParameter getDateParameter() {
    return dateParameter;
  }

  /**
   * Sets the value of the dateParameter property.
   * 
   * @param value
   *          allowed object is {@link DateParameter }
   * 
   */
  public void setDateParameter(DateParameter value) {
    this.dateParameter = value;
  }

  /**
   * Gets the value of the decimalParameter property.
   * 
   * @return possible object is {@link DecimalParameter }
   * 
   */
  public DecimalParameter getDecimalParameter() {
    return decimalParameter;
  }

  /**
   * Sets the value of the decimalParameter property.
   * 
   * @param value
   *          allowed object is {@link DecimalParameter }
   * 
   */
  public void setDecimalParameter(DecimalParameter value) {
    this.decimalParameter = value;
  }

  /**
   * Gets the value of the floatParameter property.
   * 
   * @return possible object is {@link FloatParameter }
   * 
   */
  public FloatParameter getFloatParameter() {
    return floatParameter;
  }

  /**
   * Sets the value of the floatParameter property.
   * 
   * @param value
   *          allowed object is {@link FloatParameter }
   * 
   */
  public void setFloatParameter(FloatParameter value) {
    this.floatParameter = value;
  }

  /**
   * Gets the value of the intParameter property.
   * 
   * @return possible object is {@link IntParameter }
   * 
   */
  public IntParameter getIntParameter() {
    return intParameter;
  }

  /**
   * Sets the value of the intParameter property.
   * 
   * @param value
   *          allowed object is {@link IntParameter }
   * 
   */
  public void setIntParameter(IntParameter value) {
    this.intParameter = value;
  }

  /**
   * Gets the value of the stringParameter property.
   * 
   * @return possible object is {@link StringParameter }
   * 
   */
  public StringParameter getStringParameter() {
    return stringParameter;
  }

  /**
   * Sets the value of the stringParameter property.
   * 
   * @param value
   *          allowed object is {@link StringParameter }
   * 
   */
  public void setStringParameter(StringParameter value) {
    this.stringParameter = value;
  }

}
