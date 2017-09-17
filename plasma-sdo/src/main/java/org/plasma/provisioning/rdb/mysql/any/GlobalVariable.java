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

package org.plasma.provisioning.rdb.mysql.any;

import org.plasma.sdo.PlasmaDataObject;

/**
 * Generated interface representing the domain model entity
 * <b>GlobalVariable</b>. This <a href="http://plasma-sdo.org">SDO</a> interface
 * directly reflects the class (single or multiple) inheritance lattice of the
 * source domain model(s) and is part of namespace
 * <b>http://org.plasma/sdo/mysql/any</b> defined within the <a href=
 * "http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html"
 * >Configuration</a>.
 *
 * <p>
 * </p>
 * <b>Data Store Mapping:</b> Corresponds to the physical data store entity
 * <b>GLOBAL_VARIABLES</b>.
 * <p>
 * </p>
 *
 */
public interface GlobalVariable extends PlasmaDataObject {
  /**
   * The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with
   * the <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">
   * Type</a> for this class.
   */
  public static final String NAMESPACE_URI = "http://org.plasma/sdo/mysql/any";

  /**
   * The entity or <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a> logical name associated with this class.
   */
  public static final String TYPE_NAME_GLOBAL_VARIABLE = "GlobalVariable";

  /**
   * The declared logical property names for this <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a>.
   */
  public static enum PROPERTY {

    /**
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>name</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>GlobalVariable</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>GLOBAL_VARIABLES.VARIABLE_NAME</b>.
     */
    name,

    /**
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>value</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>GlobalVariable</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>GLOBAL_VARIABLES.VARIABLE_VALUE</b>.
     */
    value
  }

  /**
   * Returns true if the <b>name</b> property is set.
   * 
   * @return true if the <b>name</b> property is set.
   */
  public boolean isSetName();

  /**
   * Unsets the <b>name</b> property, the value of the property of the object
   * being set to the property's default value. The property will no longer be
   * considered set.
   */
  public void unsetName();

  /**
   * Returns the value of the <b>name</b> property.
   * 
   * @return the value of the <b>name</b> property.
   */
  public String getName();

  /**
   * Sets the value of the <b>name</b> property to the given value.
   */
  public void setName(String value);

  /**
   * Returns true if the <b>value</b> property is set.
   * 
   * @return true if the <b>value</b> property is set.
   */
  public boolean isSetValue();

  /**
   * Unsets the <b>value</b> property, the value of the property of the object
   * being set to the property's default value. The property will no longer be
   * considered set.
   */
  public void unsetValue();

  /**
   * Returns the value of the <b>value</b> property.
   * 
   * @return the value of the <b>value</b> property.
   */
  public String getValue();

  /**
   * Sets the value of the <b>value</b> property to the given value.
   */
  public void setValue(String value);
}