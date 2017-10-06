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

import org.plasma.sdo.access.RequiredPropertyException;

import commonj.sdo.DataObject;

@Deprecated
public class CoreHelper {

  public static Object get(DataObject dataObject, String propertyName) {
    return ((CoreDataObject) dataObject).getValueObject().get(propertyName);
  }

  public static void set(DataObject dataObject, String propertyName, Object value) {
    ((CoreDataObject) dataObject).getValueObject().put(propertyName, value);
  }

  public static void unset(DataObject dataObject, String propertyName) {
    ((CoreDataObject) dataObject).getValueObject().remove(propertyName);
  }

  public static boolean isFlaggedLocked(DataObject dataObject) {
    Boolean value = (Boolean) CoreHelper.get(dataObject, CoreConstants.PROPERTY_NAME_ENTITY_LOCKED);
    return value != null ? value.booleanValue() : false;
  }

  public static boolean isFlaggedUnlocked(DataObject dataObject) {
    Boolean value = (Boolean) CoreHelper.get(dataObject,
        CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED);
    return value != null ? value.booleanValue() : false;
  }

  public static void flagLocked(DataObject dataObject) {
    if (!CoreHelper.isFlaggedLocked(dataObject)) {
      // FIXME: manual bypass of setting history
      CoreHelper.set(dataObject, CoreConstants.PROPERTY_NAME_ENTITY_LOCKED, new Boolean(true));
    }
  }

  public static void unflagLocked(DataObject dataObject) {
    // FIXME: manual bypass of setting history
    ((CoreDataObject) dataObject).getValueObject()
        .remove(CoreConstants.PROPERTY_NAME_ENTITY_LOCKED);
  }

  public static void flagUnlocked(DataObject dataObject) {
    // FIXME: manual bypass of setting history
    if (!CoreHelper.isFlaggedUnlocked(dataObject)) {
      CoreHelper.set(dataObject, CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED, new Boolean(true));
    }
  }

  public static void unflagUnlocked(DataObject dataObject) {
    // FIXME: manual bypass of setting history
    ((CoreDataObject) dataObject).getValueObject().remove(
        CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED);
  }

  @Deprecated
  public static String createHashKeyFromUUID(DataObject dataObject) {

    String uuid = (String) ((CoreDataObject) dataObject).getUUIDAsString();
    if (uuid == null)
      throw new RequiredPropertyException("UUID is required for entity '"
          + dataObject.getType().getURI() + "#" + dataObject.getType().getName() + "'");

    return uuid;
  }

}
