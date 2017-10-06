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

// java imports
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.query.QueryException;
import org.plasma.query.model.AbstractPathElement;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;
import org.plasma.query.model.PathElement;
import org.plasma.query.model.Property;
import org.plasma.query.model.Select;
import org.plasma.query.model.WildcardPathElement;
import org.plasma.query.model.WildcardProperty;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.QueryVisitor;
import org.plasma.runtime.DataAccessProviderName;
import org.plasma.sdo.access.DataAccessException;

import commonj.sdo.Type;

public class TraversalMapAssembler {
  private static Log log = LogFactory.getLog(TraversalMapAssembler.class);

  private Type rootType;
  private TraversalMap map;

  @SuppressWarnings("unused")
  private TraversalMapAssembler() {
  }

  public TraversalMapAssembler(Select select, Type rootType, DataAccessProviderName providerName) {
    this.rootType = rootType;
    map = new TraversalMap(providerName);
    if (select.getProperties().size() == 0)
      throw new DataAccessException("Select clause has no properties");
    QueryVisitor visitor = new DefaultQueryVisitor() {
      @Override
      public void start(Property property) {
        assembleProperty(property);
        super.start(property);
      }

      @Override
      public void start(WildcardProperty wildcardProperty) {
        assembleProperty(wildcardProperty);
        super.start(wildcardProperty);
      }
    };
    select.accept(visitor);
  }

  public TraversalMap getMap() {
    return map;
  }

  private void assembleProperty(AbstractProperty abstractProperty) {
    Path path = null;
    if (abstractProperty instanceof Property) {
      path = ((Property) abstractProperty).getPath();
    } else if (abstractProperty instanceof WildcardProperty) {
      path = ((WildcardProperty) abstractProperty).getPath();
    } else
      throw new IllegalArgumentException("unknown property class, "
          + abstractProperty.getClass().getName());
    if (path == null) {
      String[] names = findPropertyNames(this.rootType, abstractProperty);
      if (names.length == 1) {
        map.add(TraversalMap.DELIM_PATH, names[0]); // add a path-oriented entry
        map.add(this.rootType.getURI() + "#" + this.rootType.getName(), names[0]); // add
                                                                                   // an
                                                                                   // entity-oriented
                                                                                   // entry
      } else {
        map.add(TraversalMap.DELIM_PATH, names); // add a path-oriented entry
        map.add(this.rootType.getURI() + "#" + this.rootType.getName(), names); // add
                                                                                // an
                                                                                // entity-oriented
                                                                                // entry
      }
    } else {
      assemblePath(path, this.rootType, TraversalMap.DELIM_PATH, path.getPathNodes().get(0)
          .getPathElement(), 0, abstractProperty);
    }
  }

  private void assemblePath(Path path, Type currType, String currTraversalPath,
      AbstractPathElement currPathElement, int curPathElementIndex,
      AbstractProperty abstractProperty) {

    // FIXME: use enum here
    if (currPathElement instanceof PathElement) {
      PathElement pathElement = (PathElement) currPathElement;
      commonj.sdo.Property prop = currType.getProperty(pathElement.getValue());
      if (prop.getType().isDataType())
        if (abstractProperty instanceof Property)
          throw new QueryException("traversal path for property '"
              + ((Property) abstractProperty).getName() + "' from root '" + this.rootType.getName()
              + "' contains a non-reference property '" + prop.getName() + "'");
        else
          throw new QueryException("traversal path for wildcard property " + "' from root '"
              + this.rootType.getName() + "' contains a non-reference property '" + prop.getName()
              + "'");

      // map.add(currType.getName(), pdef.getName()); // add an entity-oriented
      // entry for path element
      String nextTraversalPath = currTraversalPath + prop.getName() + TraversalMap.DELIM_PATH;
      Type nextType = prop.getType();
      if (path.getPathNodes().size() > curPathElementIndex + 1) {
        int nextPathElementIndex = curPathElementIndex + 1;
        AbstractPathElement nextPathElement = path.getPathNodes().get(nextPathElementIndex)
            .getPathElement();
        assemblePath(path, nextType, nextTraversalPath, nextPathElement, nextPathElementIndex,
            abstractProperty);
      } else {
        String[] names = findPropertyNames(nextType, abstractProperty);
        if (names.length == 1) {
          map.add(nextTraversalPath, names[0]); // add a path-oriented entry
          map.add(nextType.getURI() + "#" + nextType.getName(), names[0]); // add
                                                                           // an
                                                                           // entity-oriented
                                                                           // entry
        } else {
          map.add(nextTraversalPath, names); // add a path-oriented entry
          map.add(nextType.getURI() + "#" + nextType.getName(), names); // add
                                                                        // an
                                                                        // entity-oriented
                                                                        // entry
        }
      }
    } else if (currPathElement instanceof WildcardPathElement) {
      List<commonj.sdo.Property> properties = currType.getDeclaredProperties();

      for (commonj.sdo.Property prop : properties) {
        if (prop.getType().isDataType())
          continue;
        Type nextType = prop.getType();
        String nextTraversalPath = currTraversalPath + prop.getName() + TraversalMap.DELIM_PATH;
        if (path.getPathNodes().size() > curPathElementIndex + 1) {
          int nextPathElementIndex = curPathElementIndex + 1;
          AbstractPathElement nextPathElement = path.getPathNodes().get(nextPathElementIndex)
              .getPathElement();
          assemblePath(path, nextType, nextTraversalPath, nextPathElement, nextPathElementIndex,
              abstractProperty);
        } else {
          String[] names = findPropertyNames(nextType, abstractProperty);
          if (names.length == 1) {
            map.add(nextTraversalPath, names[0]); // add a path-oriented entry
            map.add(nextType.getURI() + "#" + nextType.getName(), names[0]); // add
                                                                             // an
                                                                             // entity-oriented
                                                                             // entry
          } else {
            map.add(nextTraversalPath, names); // add a path-oriented entry
            map.add(nextType.getURI() + "#" + nextType.getName(), names); // add
                                                                          // an
                                                                          // entity-oriented
                                                                          // entry
          }
        }
      }
    } else
      throw new IllegalArgumentException("unknown path element class, "
          + currPathElement.getClass().getName());
  }

  private String[] findPropertyNames(Type type, AbstractProperty abstractProperty) {
    String[] result = null;

    if (abstractProperty instanceof Property) {
      result = new String[1];
      String name = ((Property) abstractProperty).getName();
      type.getProperty(name);// just validates name
      result[0] = name;
    } else if (abstractProperty instanceof WildcardProperty) {
      WildcardProperty wildcardProperty = (WildcardProperty) abstractProperty;
      List<String> list = new ArrayList<String>();
      List<commonj.sdo.Property> props = type.getProperties();
      switch (wildcardProperty.getType()) {
      default:
      case ALL:
        for (commonj.sdo.Property prop : props)
          if (!prop.isMany())
            list.add(prop.getName());
        break;
      case REFERENCE:
        for (commonj.sdo.Property prop : props)
          if (!prop.isMany() && !prop.getType().isDataType())
            list.add(prop.getName());
        break;
      case DATA:
        for (commonj.sdo.Property prop : props)
          if (!prop.isMany() && prop.getType().isDataType())
            list.add(prop.getName());
        break;
      }
      result = new String[list.size()];
      list.toArray(result);

    } else
      throw new IllegalArgumentException("unknown property class, "
          + abstractProperty.getClass().getName());

    return result;
  }

}