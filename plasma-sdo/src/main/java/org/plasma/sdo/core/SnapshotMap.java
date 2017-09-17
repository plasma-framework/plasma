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
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.plasma.sdo.access.provider.common.PropertyPair;

import commonj.sdo.Property;

/**
 * Holds internally managed concurrency property values and data store generated
 * keys, for return back to clients, resulting from insert, update or delete
 * operations, in association with a single time stamp value. The time stamp
 * holds the time value used for all relevant concurrency operations, e.g.
 * last-modified optimistic concurrency dates or time stamps.
 */
public class SnapshotMap implements Serializable {
  private static final long serialVersionUID = 1L;
  private Map<UUID, List<PropertyPair>> values = new HashMap<UUID, List<PropertyPair>>();
  private Timestamp snapshotDate;
  private Long snapshotNannoTime;

  public SnapshotMap() {
    this.snapshotDate = new Timestamp((new Date()).getTime());
    this.snapshotNannoTime = System.nanoTime();
  }

  public SnapshotMap(Timestamp snapshotDate) {
    this.snapshotDate = snapshotDate;
    this.snapshotNannoTime = System.nanoTime();
  }

  public Timestamp getSnapshotDate() {
    return snapshotDate;
  }

  public Long getSnapshotNannoTime() {
    return snapshotNannoTime;
  }

  public UUID[] getKeys() {
    Set<UUID> keys = values.keySet();
    UUID[] result = new UUID[keys.size()];
    keys.toArray(result);
    return result;
  }

  public void put(UUID key, PropertyPair value) {
    List<PropertyPair> list = values.get(key);
    if (list == null) {
      list = new ArrayList<PropertyPair>();
      values.put(key, list);
    }
    list.add(value);
  }

  public List<PropertyPair> get(UUID key) {
    return values.get(key);
  }

  public PropertyPair get(UUID key, Property prop) {
    List<PropertyPair> list = values.get(key);
    if (list != null) {
      for (PropertyPair pair : list) {
        if (pair.getProp().getName().equals(prop.getName()))
          return pair;
      }
    }
    return null;
  }

  public List<PropertyPair> remove(UUID key) {
    return values.remove(key);
  }

}