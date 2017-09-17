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

package org.plasma.sdo.xml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;

class StreamObject extends StreamNode {
  protected PlasmaProperty source;
  private PlasmaDataObject dataObject;
  protected Map<PlasmaProperty, Object> values;
  private boolean isNonContainmentReference;
  private String serialId;

  public StreamObject(PlasmaType type, QName name, Location loc) {
    super(type, name, loc);
  }

  public StreamObject(PlasmaType type, PlasmaProperty source, QName name, Location loc) {
    super(type, name, loc);
    this.source = source;
  }

  public PlasmaProperty getSource() {
    return source;
  }

  public boolean isNonContainmentReference() {
    return this.isNonContainmentReference;
  }

  public void setNonContainmentReference(boolean isReference) {
    this.isNonContainmentReference = isReference;
  }

  public String getSerialId() {
    return serialId;
  }

  public void setSerialId(String serialId) {
    this.serialId = serialId;
  }

  public PlasmaDataObject getDataObject() {
    return dataObject;
  }

  public void setDataObject(PlasmaDataObject dataObject) {
    this.dataObject = dataObject;
  }

  public Map<PlasmaProperty, Object> getValues() {
    if (values == null)
      values = new HashMap<PlasmaProperty, Object>();
    return this.values;
  }

  public Object get(PlasmaProperty property) {
    return this.getValues().get(property);
  }

  public Object set(PlasmaProperty property, Object value) {
    if (value == null)
      throw new IllegalArgumentException("expected non-null value arg");
    return this.getValues().put(property, value);
  }

  public void accept(StreamObjectVisitor visitor) {
    accept(visitor, this, null, null, this, false, 0,
        new HashMap<StreamObject, HashSet<StreamObject>>());
  }

  private void accept(StreamObjectVisitor visitor, StreamObject target, StreamObject source,
      PlasmaProperty sourceProperty, StreamObject root, boolean depthFirst, int level,
      HashMap<StreamObject, HashSet<StreamObject>> visitedObjects) {
    if (source != null) {
      // Lets make sure the parent's hashset is setup
      if (!visitedObjects.containsKey(source))
        visitedObjects.put(source, new HashSet<StreamObject>());

      // Now lets see if the current child has ever been visited.
      HashSet<StreamObject> visitedChildren = (HashSet<StreamObject>) visitedObjects.get(source);

      if (!visitedChildren.contains(target)) {
        visitedChildren.add(target);
      } else {
        return;
      }
    }

    if (!depthFirst)
      visitor.visit(target, source, sourceProperty, level);

    Iterator<PlasmaProperty> iter = target.getValues().keySet().iterator();
    while (iter.hasNext()) {
      PlasmaProperty property = iter.next();
      if (property.getType().isDataType())
        continue;
      if (!property.isMany()) {
        StreamObject child = (StreamObject) target.get(property);
        if (child != null && !child.equals(root)) {
          child.accept(visitor, child, target, property, root, depthFirst, level + 1,
              visitedObjects);
        }
      } else {
        List<StreamObject> childList = (List<StreamObject>) target.get(property);
        if (childList != null) {
          StreamObject[] childArray = new StreamObject[childList.size()];
          childList.toArray(childArray); // avoids collection concurrent mod
                                         // errors on recursion
          for (int i = 0; i < childArray.length; i++) {
            StreamObject child = childArray[i];
            if (!child.equals(root)) {
              child.accept(visitor, child, target, property, root, depthFirst, level + 1,
                  visitedObjects);
            }
          }
        }
      }
    }

    if (depthFirst)
      visitor.visit(target, source, sourceProperty, level);
  }
}
