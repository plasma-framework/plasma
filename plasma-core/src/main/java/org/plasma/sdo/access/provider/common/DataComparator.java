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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.query.model.AbstractPathElement;
import org.plasma.query.model.Path;
import org.plasma.query.model.PathElement;
import org.plasma.query.model.Property;
import org.plasma.query.model.WildcardPathElement;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.DataAccessException;

import commonj.sdo.DataObject;

public abstract class DataComparator {
  private static Log log = LogFactory.getLog(DataComparator.class);
  protected static int ASCENDING = 1;
  protected static int DESCENDING = 2;
  protected List<PathInfo> paths = new ArrayList<PathInfo>();

  public void addAscending(Property property) {
    paths.add(new PathInfo(property, ASCENDING));
  }

  public void addDescending(Property property) {
    paths.add(new PathInfo(property, DESCENDING));
  }

  protected int compare(DataObject vo1, DataObject vo2) {
    Iterator<PathInfo> iterator = paths.iterator();
    while (iterator.hasNext()) {
      PathInfo info = iterator.next();
      Object ep1 = findEndpoint(vo1, info.property, info.property.getPath(), 0);
      Object ep2 = findEndpoint(vo2, info.property, info.property.getPath(), 0);
      int result;
      if (info.direction == ASCENDING)
        result = comp(ep1, ep2);
      else
        result = comp(ep2, ep1);
      if (result != 0)
        return result;
      else
        continue;
    }
    return 0; // equal
  }

  protected int comp(Object ep1, Object ep2) {
    int result = 0;
    if (ep1 != null && ep2 != null) {
      if (ep1 instanceof Comparable)
        result = ((Comparable) ep1).compareTo(ep2);
      else
        log.warn("endpoint class not comparable, " + ep1.getClass().getName());
    } else if (ep1 == null && ep2 != null)
      result = -1; // assume a null object is "less than" a non-null object
    else if (ep2 == null && ep1 != null)
      result = 1;

    return result;
  }

  protected Object findEndpoint(DataObject targetObject, Property property, Path path, int pathIndex) {
    PlasmaType targetType = (PlasmaType) targetObject.getType();
    if (path != null && pathIndex < path.getPathNodes().size()) {
      AbstractPathElement pathElem = path.getPathNodes().get(pathIndex).getPathElement();
      if (pathElem instanceof WildcardPathElement)
        throw new DataAccessException(
            "wildcard path elements applicable for 'Select' clause paths only, not 'Where' clause paths");
      String elem = ((PathElement) pathElem).getValue();
      PlasmaProperty prop = (PlasmaProperty) targetType.getProperty(elem);
      if (targetObject.isSet(prop)) {
        if (prop.isMany()) {
          @SuppressWarnings("unchecked")
          List<DataObject> list = targetObject.getList(prop);
          if (list.size() == 1) {
            DataObject next = list.get(0);
            return findEndpoint(next, property, path, pathIndex + 1);
          } else if (list.size() > 1) {
            log.warn("ordering not supported across collection properties with multiple values present - "
                + targetType.toString()
                + "."
                + prop.getName()
                + " is a collection property - ignoring all but first value");
          }
        } else {
          DataObject next = targetObject.getDataObject(prop);
          return findEndpoint(next, property, path, pathIndex + 1);
        }
      }
    } else {
      PlasmaProperty endpointProp = (PlasmaProperty) targetType.getProperty(property.getName());
      if (!endpointProp.getType().isDataType())
        throw new DataAccessException("expected datatype property for, " + endpointProp);
      if (targetObject.isSet(endpointProp)) {
        if (endpointProp.isMany()) {
          @SuppressWarnings("unchecked")
          List<Object> list = targetObject.getList(endpointProp);
          if (list.size() == 1) {
            Object value = list.get(0);
            return value;
          } else if (list.size() > 1) {
            log.warn("ordering not supported across collection properties with multiple values present - "
                + targetType.toString()
                + "."
                + endpointProp.getName()
                + " is a collection property - ignoring all but first value");
          }
        } else {
          Object value = targetObject.get(endpointProp);
          return value;
        }
      }
    }
    return null;
  }

  protected class PathInfo {
    public Property property;
    public int direction;

    @SuppressWarnings("unused")
    private PathInfo() {
    }

    public PathInfo(Property property, int direction) {
      this.property = property;
      this.direction = direction;
    }
  }
}
