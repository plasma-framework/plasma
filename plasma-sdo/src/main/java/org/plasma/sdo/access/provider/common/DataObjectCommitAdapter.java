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

package org.plasma.sdo.access.provider.common;

import org.plasma.sdo.PlasmaDataObject;

import commonj.sdo.DataObject;

public class DataObjectCommitAdapter {

  private DataObject dataObject;

  @SuppressWarnings("unused")
  private DataObjectCommitAdapter() {
  }

  public DataObjectCommitAdapter(DataObject dataObject) {
    this.dataObject = dataObject;
  }

  public DataObject getDataObject() {
    return dataObject;
  }

  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    DataObjectCommitAdapter otherAdapter = (DataObjectCommitAdapter) obj;
    PlasmaDataObject otherDataObject = (PlasmaDataObject) otherAdapter.getDataObject();
    PlasmaDataObject thisDataObject = (PlasmaDataObject) this.dataObject;
    return thisDataObject.getUUIDAsString().equals(otherDataObject.getUUIDAsString());
  }
}
