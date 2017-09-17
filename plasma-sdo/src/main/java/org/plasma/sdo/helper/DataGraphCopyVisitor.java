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

package org.plasma.sdo.helper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.core.CoreChangeSummary;
import org.plasma.sdo.core.CoreConstants;
import org.plasma.sdo.core.CoreDataObject;
import org.plasma.sdo.core.CoreNode;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;

/**
 * Creates a copy of any arbitrary DataGraph. For all DataObject nodes with a
 * SDO type found in the given referenceTypes array clears these from the change
 * summary.
 */
public class DataGraphCopyVisitor implements PlasmaDataGraphVisitor {
  private static Log log = LogFactory.getLog(DataGraphCopyVisitor.class);

  private PlasmaDataObject result;
  private Map<String, PlasmaDataObject> resultMap = new HashMap<String, PlasmaDataObject>();
  private HashSet<Type> referenceTypes;
  private boolean copyUUIDs = false;

  public DataGraphCopyVisitor() {
  }

  public DataGraphCopyVisitor(Type[] referenceTypes) {
    this.referenceTypes = new HashSet<Type>();
    for (Type t : referenceTypes)
      this.referenceTypes.add(t);
  }

  public boolean isCopyUUIDs() {
    return copyUUIDs;
  }

  public void setCopyUUIDs(boolean copyUUIDs) {
    this.copyUUIDs = copyUUIDs;
  }

  public void visit(DataObject target, DataObject source, String sourceKey, int level) {

    PlasmaDataObject targetObject = (PlasmaDataObject) target;

    // process the root and exit
    if (source == null) {
      DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
      dataGraph.getChangeSummary().beginLogging(); // log changes from this
                                                   // point
      if (log.isDebugEnabled())
        log.debug("copying root object " + targetObject.getUUIDAsString());
      Type rootType = target.getType();
      result = (PlasmaDataObject) dataGraph.createRootObject(rootType);
      if (this.copyUUIDs)
        result.resetUUID(((PlasmaDataObject) target).getUUID());
      copyDataProperties(targetObject, result);
      resultMap.put(targetObject.getUUIDAsString(), result);
      return;
    }
    PlasmaDataObject sourceObject = (PlasmaDataObject) source;
    Property sourceProperty = sourceObject.getType().getProperty(sourceKey);

    PlasmaDataObject sourceResult = resultMap.get(sourceObject.getUUIDAsString());
    if (sourceResult == null)
      throw new IllegalStateException("expected source result object "
          + sourceObject.getType().getURI() + "#" + sourceObject.getType().getName() + " ("
          + sourceObject.getUUIDAsString() + ")");

    PlasmaDataObject targetResult = resultMap.get(targetObject.getUUIDAsString());
    if (target.getContainer().equals(source)) {
      if (targetResult == null) {
        if (log.isDebugEnabled())
          log.debug("creating contained object " + targetObject.toString());
        targetResult = (PlasmaDataObject) sourceResult.createDataObject(sourceProperty,
            target.getType());
        if (this.copyUUIDs)
          targetResult.resetUUID(sourceResult.getUUID());
        copyDataProperties(targetObject, targetResult);
        resultMap.put(targetObject.getUUIDAsString(), targetResult);
      } else {
        // reparent
        if (log.isDebugEnabled())
          log.debug("reparenting contained object " + targetObject.toString());
        targetResult.setContainer(sourceResult);
        targetResult.setContainmentProperty(sourceProperty);
      }
    } else {
      if (targetResult != null) {
        if (log.isDebugEnabled())
          log.debug("linking existing object " + targetObject.toString());
        sourceResult.set(sourceProperty, targetResult);
      } else {
        // leave it orphaned till we get an event where source is the container
        if (log.isDebugEnabled())
          log.debug("copying/linking orphaned object " + targetObject.toString());
        targetResult = (PlasmaDataObject) PlasmaDataFactory.INSTANCE.create(targetObject.getType());
        if (this.copyUUIDs)
          targetResult.resetUUID(sourceResult.getUUID());
        copyDataProperties(targetObject, targetResult);
        sourceResult.set(sourceProperty, targetResult);
        resultMap.put(targetObject.getUUIDAsString(), targetResult);
      }
    }

    if (isReferenceType(targetResult.getType())) {
      CoreChangeSummary changeSummary = (CoreChangeSummary) targetResult.getChangeSummary();
      changeSummary.clear(targetResult);
      if (log.isDebugEnabled())
        log.debug("clearing change summary for reference object " + targetObject.toString());
    }
  }

  private boolean isReferenceType(Type type) {
    return referenceTypes != null && referenceTypes.contains(type);
  }

  /**
   * copies data properties from the source to the given copy such that the
   * change history is captured in the DataGraph change summary.
   * 
   * @param source
   *          the source object
   * @param copy
   *          the replicated object
   */
  private void copyDataProperties(PlasmaDataObject source, PlasmaDataObject copy) {
    PlasmaType type = (PlasmaType) source.getType();
    CoreDataObject sourceObject = (CoreDataObject) source;
    CoreDataObject result = (CoreDataObject) copy;
    Object value = null;
    for (String key : sourceObject.getValueObject().getKeys()) {
      Property property = type.findProperty(key);
      if (property != null) {
        value = source.get(property);
        if (value == null)
          continue;
        if (property.getType().isDataType()) {
          if (!property.isReadOnly()) {
            result.set(property, value);
          } else {
            result.getValueObject().put(property.getName(), value);
          }
        }
      } else { // assume instance property
        value = sourceObject.getValueObject().get(key);
        if (value == null)
          continue;
        result.getValueObject().put(key, value);
      }
    }
  }

  public DataObject getResult() {
    return result;
  }

}
