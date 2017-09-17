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

package org.plasma.xml.sdox;

public interface SDOXConstants {
  public static String SDOX_NAMESPACE_URI = "commonj.sdo/xml";
  public static String SDOX_NAMESPACE_PREFIX = "sdox";

  public static String LOCAL_NAME_NAME = "name";
  public static String LOCAL_NAME_PROPERTY_TYPE = "propertyType";
  public static String LOCAL_NAME_OPPOSITE_PROPERTY = "oppositeProperty";
  public static String LOCAL_NAME_SEQUENCE = "sequence";
  public static String LOCAL_NAME_STRING = "string";
  public static String LOCAL_NAME_DATATYPE = "dataType";
  public static String LOCAL_NAME_ALIAS_NAME = "aliasName";
  public static String LOCAL_NAME_READ_ONLY = "readOnly";
  public static String LOCAL_NAME_MANY = "many";
  // taken from 3.0 SDOX
  public static String LOCAL_NAME_ORPHAN_HOLDER = "orphanHolder";
  public static String LOCAL_NAME_KEY = "key";
  public static String LOCAL_NAME_EMBEDDED_KEY = "embeddedKey";
  public static String LOCAL_NAME_KEY_TYPE = "keyType";

}
