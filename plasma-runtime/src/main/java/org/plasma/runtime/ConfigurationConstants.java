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

package org.plasma.runtime;

public class ConfigurationConstants {

  public static final String JDBC_PROVIDER_PROPERTY_PREFIX = "org.plasma.sdo.access.provider.jdbc.";

  /**
   * The fully qualified Java class name of the connection pool or data source
   * provider
   */
  public static final String JDBC_PROVIDER_NAME = JDBC_PROVIDER_PROPERTY_PREFIX
      + "ConnectionProviderName";

  /** The fully qualified JNDI name of the JDBC datasource */
  public static final String JDBC_DATASOURCE_NAME = JDBC_PROVIDER_PROPERTY_PREFIX
      + "ConnectionDataSourceName";

  /** The fully qualified Java class name of the JDBC driver */
  public static final String JDBC_DRIVER_NAME = JDBC_PROVIDER_PROPERTY_PREFIX
      + "ConnectionDriverName";

  /** The name of the JDBC vendor */
  public static final String JDBC_VENDOR = JDBC_PROVIDER_PROPERTY_PREFIX + "Vendor";

  /** The driver specific JDBC connection URL */
  public static final String JDBC_URL = JDBC_PROVIDER_PROPERTY_PREFIX + "ConnectionURL";

  /** The user name */
  public static final String JDBC_USER = JDBC_PROVIDER_PROPERTY_PREFIX + "ConnectionUserName";

  /** The user password */
  public static final String JDBC_PASSWORD = JDBC_PROVIDER_PROPERTY_PREFIX + "ConnectionPassword";

  // FIXME: this is not extensible
  /** The Plasma SDO Profile artifact for MagicDraw */
  // public static final String ARTIFACT_RESOURCE_PLASMA_PROFILE_MAGICDRAW =
  // "Plasma_SDO_Profile.mdxml";
  // public static final String ARTIFACT_NAMESPACE_PLASMA_PROFILE_MAGICDRAW =
  // "http://www.magicdraw.com/schemas/Plasma_SDO_Profile.xmi";

  /** The Plasma SDO Profile artifact for Eclipse Papyrus */
  // public static final String ARTIFACT_RESOURCE_PLASMA_PROFILE_PAPYRUS =
  // "PlasmaSDO.profile.uml";
  // public static final String ARTIFACT_NAMESPACE_PLASMA_PROFILE_PAPYRUS =
  // "http:///schemas/PlasmaSDOProfile/_B_s5sDSPEeS0BI8SXekDLw/26";

  /** The Plasma SDO Profile Datatypes artifact for Eclipse Papyrus */
  // public static final String ARTIFACT_RESOURCE_PLASMA_DATATYPES_PAPYRUS =
  // "PlasmaSDODataTypes.uml";
  // public static final String ARTIFACT_NAMESPACE_PLASMA_DATATYPES_PAPYRUS =
  // "http:///schemas/PlasmaSDODataTypes";

}
