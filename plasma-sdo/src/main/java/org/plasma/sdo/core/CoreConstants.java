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

import java.math.BigDecimal;

public interface CoreConstants {
  /** When we encode strings, we always specify UTF8 encoding */
  public static final String UTF8_ENCODING = "UTF-8";

  public static String NULL_STRING = "NULLVALUE";
  public static Integer NULL_INTEGER = new Integer(Integer.MIN_VALUE);
  public static Long NULL_LONG = new Long(Long.MIN_VALUE);
  public static Short NULL_SHORT = new Short(Short.MIN_VALUE);
  public static Float NULL_FLOAT = new Float(Float.MIN_VALUE);
  public static Double NULL_DOUBLE = new Double(Double.MIN_VALUE);
  public static BigDecimal NULL_DECIMAL = new BigDecimal(Double.MIN_VALUE);

  public static String NULL_REFERENCE_STRING = NULL_STRING;
  public static Integer NULL_REFERENCE_INTEGER = NULL_INTEGER;
  public static Long NULL_REFERENCE_LONG = NULL_LONG;
  public static Short NULL_REFERENCE_SHORT = NULL_SHORT;
  public static Float NULL_REFERENCE_FLOAT = NULL_FLOAT;
  public static Double NULL_REFERENCE_DOUBLE = NULL_DOUBLE;
  public static BigDecimal NULL_REFERENCE_DECIMAL = new BigDecimal(-1);

  public static final String PROPERTY_NAME_SNAPSHOT_TIMESTAMP = "snapshotTimestamp";
  public static final String PROPERTY_NAME_ENTITY_NAME = "entityName";
  public static final String PROPERTY_NAME_ENTITY_LOCKED = "entityLocked";
  public static final String PROPERTY_NAME_ENTITY_UNLOCKED = "entityUnlocked";
  public static final String PROPERTY_NAME_SEQUENCE = "__SEQ__";
  public static final String PROPERTY_NAME_ROWKEY = "__ROWKEY__";

  // @Deprecated
  // public static final String PROPERTY_NAME_UUID = "__UUID__";
  public static final String PROPERTY_NAME_UUID_BYTES = "__UUID_BYTES__";
  public static final String PROPERTY_NAME_UUID_STRING = "__UUID_STR__";

}