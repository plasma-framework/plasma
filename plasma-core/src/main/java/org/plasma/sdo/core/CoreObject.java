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

package org.plasma.sdo.core;

// java imports
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.plasma.common.Key;

/**
 * Class used to describe a value object model of arbitrary complexity. Can
 * accommodate strict hierarchical structures as well as object networks and
 * graphs.
 */
public final class CoreObject implements Cloneable, Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * let's NOT serialize the collection structure for efficiency, only it's
   * contents.
   */
  private transient Map<String, Object> values;

  private CoreObject() {
    values = new HashMap<String, Object>();
  }

  public CoreObject(String entityName) {
    this();
    put(CoreConstants.PROPERTY_NAME_ENTITY_NAME, entityName);
  }

  public String getEntityName() {
    return (String) get(CoreConstants.PROPERTY_NAME_ENTITY_NAME);
  }

  public Map<String, Object> getValues() {
    return values;
  }

  public String[] getKeys() {
    Set<String> keys = values.keySet();
    String[] result = new String[keys.size()];
    keys.toArray(result);
    return result;
  }

  public void put(String key, Object value) {
    values.put(key, value);
  }

  public Object get(String key) {
    return values.get(key);
  }

  public Object remove(String key) {
    return values.remove(key);
  }

  public boolean isFlaggedLocked() {
    Boolean locked = (Boolean) values.get(CoreConstants.PROPERTY_NAME_ENTITY_LOCKED);
    return locked != null && locked.booleanValue();
  }

  public boolean isFlaggedUnlocked() {
    Boolean unlocked = (Boolean) values.get(CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED);
    return unlocked != null && unlocked.booleanValue();
  }

  public void flagLocked() {
    Boolean locked = (Boolean) values.get(CoreConstants.PROPERTY_NAME_ENTITY_LOCKED);
    if (locked == null || !locked.booleanValue())
      values.put(CoreConstants.PROPERTY_NAME_ENTITY_LOCKED, new Boolean(true));
  }

  public void unflagLocked() {
    values.remove(CoreConstants.PROPERTY_NAME_ENTITY_LOCKED);
  }

  public void flagUnlocked() {
    Boolean locked = (Boolean) values.get(CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED);
    if (locked == null || !locked.booleanValue())
      values.put(CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED, new Boolean(true));
  }

  public void unflagUnlocked() {
    values.remove(CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED);
  }

  public int getAbsoluteSize() {
    int total = 0;

    Iterator<Object> itr = values.values().iterator();
    while (itr.hasNext()) {
      Object obj = itr.next();
      if (obj instanceof CoreObject)
        total += ((CoreObject) obj).getAbsoluteSize() + 1;
      else
        total++;
    }

    return total;
  }

  public int getDepth() {
    int total = 0;

    Iterator<Object> itr = values.values().iterator();
    while (itr.hasNext()) {
      Object obj = itr.next();
      if (obj instanceof CoreObject)
        total += ((CoreObject) obj).getDepth() + 1;
    }

    return total;
  }

  /**
   * Custom serialization method. Writes the mapped key/value pairs not the hash
   * itself.
   */
  private void writeObject(ObjectOutputStream out) throws IOException {
    // debugging
    // System.out.println("start - ValueObject.writeObject(" + this.getHashKey()
    // + ") - depth: " + String.valueOf(writeDepth));
    // System.out.flush();
    // ++writeDepth;

    int size = values.size();
    out.writeInt(size);

    Iterator keys = values.keySet().iterator();
    while (keys.hasNext()) {
      String key = (String) keys.next();
      Object obj = values.get(key);
      out.writeObject(key);
      out.writeObject(obj);
    }

    // debugging
    // if(writeDepth - 1> maxDepth)
    // maxDepth = writeDepth - 1;
    // --writeDepth;
    // if(writeDepth == 0)
    // maxDepth = 0;
    // System.out.println("end - ValueObject.writeObject(" + this.getHashKey() +
    // ") - depth: " + String.valueOf(writeDepth));
    // System.out.flush();
  }

  /**
   * Custom de-serialization method. Reads the mapped key/value pairs not the
   * hash itself.
   */
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    int size = in.readInt();

    // default constructors not called during deserialization, must
    // re-create transient Map data member
    values = new HashMap<String, Object>(size);

    for (int i = 0; i < size; i++) {
      String key = (String) in.readObject();
      Object value = in.readObject();
      values.put(key, value);
    }
  }

  public Object clone() throws CloneNotSupportedException {
    CoreObject clone = (CoreObject) super.clone();
    clone.values = new HashMap<String, Object>(this.values);
    return clone;
  }

}