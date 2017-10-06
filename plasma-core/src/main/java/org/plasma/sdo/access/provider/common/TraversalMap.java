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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.plasma.runtime.DataAccessProviderName;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import commonj.sdo.Property;
import commonj.sdo.Type;

/**
 * This class stores hashed key-value pairs where the key is one of 2 formats.
 * The first key format represents a traversal path and is delimited by the
 * defined delimiter. See Path Oriented examples below. Each path element is a
 * reference property name. This path-oriented key format is necessary because
 * within an object graph there can be multiple paths to different occurrences
 * of the same entity. For one occurrence along a particular path, the user may
 * want to return a different set of properties than for another occurrence
 * along another path. The second key format assumes that the same properties
 * will be returned for every occurrence of an entity. So the key is simply the
 * namespace qualified entity name. For both key formats, each value is a
 * java.lang.String array where each element is a non-reference MOM property
 * name.
 *
 * ------------------------------- Path Oriented Format Examples
 * -------------------------------- key value(properties)
 * -------------------------------- / [r,s] /a/ [x,y,z] /a/b/ [x,y] /a/b/c/ [*]
 * /g/h/i/j/ [u,v]
 * 
 * /n/o/p/q/r/ [*] /u/v/a/ [y,z]
 *
 * /gbloc1/org/ [*] /gbloc2/org/ [*]
 *
 * ------------------------------- Namespace Qualified Entity Name Oriented
 * Format Examples -------------------------------- key value(properties)
 * -------------------------------- http://my.namespace/foo#Person [name,ssn,id]
 * http://my.namespace/foo#Org [name,dunsNumber]
 */
public class TraversalMap {
  public static String DELIM_PATH = "/";
  public static String WILDCARD = "*";

  private Map<String, String[]> map = new HashMap<String, String[]>();
  private DataAccessProviderName dataAccessProviderName;

  public TraversalMap(DataAccessProviderName providerName) {
    this.dataAccessProviderName = providerName;
  }

  public Iterator<String> iterator() {
    return map.keySet().iterator();
  }

  public int calculateDepth() {
    int depth = 0;
    Iterator<String> iter = map.keySet().iterator();
    while (iter.hasNext()) {
      String[] path = ((String) iter.next()).split(TraversalMap.DELIM_PATH);
      if (path != null && path.length > depth)
        depth = path.length;
    }
    return depth;
  }

  public int count(String key) {
    String[] values = this.get(key);
    if (values != null)
      return values.length;
    return 0;
  }

  /**
   * Appends or merges the given values to an underlying array of values for the
   * given key. For clients where only a single value is to be added, use of the
   * below method with a key and single string is more efficient, as a merge is
   * not required
   * 
   * @param key
   *          the string key mapped to an array
   * @param value
   *          the string value
   */
  public void add(String key, String[] values) {
    String[] oldValues = (String[]) map.get(key);
    if (oldValues == null) {
      map.put(key, values);
    } else {
      // Don't allocate ArrayList here just for the sake
      // of merging some strings, only allocate
      // string arrays and perform a "manual" merge
      int newCount = 0;
      String[] newValues = new String[values.length]; // can need all values
      for (int i = 0; i < values.length; i++) {
        boolean found = false;
        for (int j = 0; j < oldValues.length; j++)
          if (oldValues[j].equals(values[i])) {
            found = true;
            break;
          }
        if (!found) {
          newValues[newCount] = values[i];
          newCount++;
        }
      }
      String[] mergedValues = new String[oldValues.length + newCount];
      System.arraycopy(oldValues, 0, mergedValues, 0, oldValues.length);
      System.arraycopy(newValues, 0, mergedValues, oldValues.length, newCount);

      map.put(key, mergedValues);
    }
  }

  /**
   * Appends the given string value to an underlying array of values for the
   * given key. A convenience method for use where a client knows a single value
   * is to be added.
   * 
   * @param key
   *          the string key mapped to an array
   * @param value
   *          the string value
   */
  public void add(String key, String value) {
    String[] values = (String[]) map.get(key);
    if (values == null) {
      values = new String[1];
      values[0] = value;
      map.put(key, values);
    } else {
      for (int i = 0; i < values.length; i++)
        if (values[i].equals(value))
          return; // we have it already, gracias

      // manually resize array
      String[] oldValues = values;
      values = new String[oldValues.length + 1];
      System.arraycopy(oldValues, 0, values, 0, oldValues.length);
      values[values.length - 1] = value;
      map.put(key, values);
    }
  }

  public String[] get(String key) {
    return (String[]) map.get(key);
  }

  public String[] asFieldArray() {
    List<String> list = new ArrayList<String>();
    Iterator<String> iter = map.keySet().iterator();
    while (iter.hasNext()) {
      String key = (String) iter.next();
      if (key.startsWith(DELIM_PATH))
        continue; // it's a path-oriented key, we're looking for entity-oriented
      String[] values = (String[]) map.get(key);
      for (int i = 0; i < values.length; i++)
        list.add(key + "." + EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX + values[i]);
    }
    String[] result = new String[list.size()];
    list.toArray(result);
    return result;
  }

  public Map<Object, String[]> toClassMap() {
    // Model mom = Model.instance();
    Map<Object, String[]> classMap = new HashMap<Object, String[]>();
    Iterator<String> iter = map.keySet().iterator();
    try {
      while (iter.hasNext()) {
        String key = (String) iter.next();
        if (key.startsWith(DELIM_PATH))
          continue; // it's a path-oriented key, we're looking for
                    // entity-oriented

        String[] tokens = key.split("#");
        Type type = PlasmaTypeHelper.INSTANCE.getType(tokens[0], tokens[1]);
        String pkgName = PlasmaRuntime.getInstance()
            .getProvisioningByNamespaceURI(this.dataAccessProviderName, type.getURI())
            .getPackageName();

        String[] values = (String[]) map.get(key);
        String[] fieldValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
          Property property = type.getProperty(values[i]);
          if (property.isMany())
            fieldValues[i] = EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX + values[i]
                + EntityConstants.DATA_ACCESS_CLASS_MEMBER_MULTI_VALUED_SUFFIX;
          else
            fieldValues[i] = EntityConstants.DATA_ACCESS_CLASS_MEMBER_PREFIX + values[i];
        }
        classMap.put(Class.forName(pkgName + "." + tokens[1]), fieldValues);
      }
    } catch (ClassNotFoundException e) {
      throw new DataAccessException(e);
    }
    return classMap;
  }

  public String dump() {
    StringBuffer buf = new StringBuffer();
    Iterator<String> iter = map.keySet().iterator();
    while (iter.hasNext()) {
      String key = (String) iter.next();
      buf.append("\n\t");
      buf.append(key + "[");
      String[] values = get(key);
      for (int i = 0; i < values.length; i++) {
        if (i > 0)
          buf.append(",");
        buf.append(values[i]);
      }
      buf.append("]");
    }
    return buf.toString();
  }
}