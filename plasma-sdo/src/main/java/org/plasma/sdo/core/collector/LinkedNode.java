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

package org.plasma.sdo.core.collector;

import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;

/**
 * Stores a data object, the source/container data object and source property
 * that links or references it.
 */
public class LinkedNode {
  private PlasmaDataObject dataObject;
  private PlasmaProperty sourceProperty;
  private PlasmaDataObject source;
  private PlasmaProperty targetProperty;

  @SuppressWarnings("unused")
  private LinkedNode() {
  }

  public LinkedNode(PlasmaDataObject dataObject, PlasmaDataObject container,
      PlasmaProperty containmentProperty) {
    this.dataObject = dataObject;
    this.source = container;
    this.sourceProperty = containmentProperty;
  }

  public PlasmaProperty getSourceProperty() {
    return sourceProperty;
  }

  public PlasmaDataObject getSourceContainer() {
    return source;
  }

  public PlasmaDataObject getDataObject() {
    return dataObject;
  }

  public PlasmaProperty getTargetProperty() {
    return targetProperty;
  }

  public void setTargetProperty(PlasmaProperty targetProperty) {
    this.targetProperty = targetProperty;
  }

}
