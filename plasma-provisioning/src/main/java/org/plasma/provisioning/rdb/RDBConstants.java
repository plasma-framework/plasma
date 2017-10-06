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

package org.plasma.provisioning.rdb;

public interface RDBConstants {

  public static final String ARTIFACT_RESOURCE_ORACLE = "Plasma_RDB_Oracle.mdxml";

  public static final String ARTIFACT_NAMESPACE_ORACLE_ANY_SYS = "http://org.plasma/sdo/oracle/any/sys";
  public static final String ARTIFACT_NAMESPACE_ORACLE_11G_SYS = "http://org.plasma/sdo/oracle/11g/sys";

  public static final String ARTIFACT_RESOURCE_MYSQL = "Plasma_RDB_MySql.mdxml";
  public static final String ARTIFACT_NAMESPACE_MYSQL_ANY = "http://org.plasma/sdo/mysql/any";
  public static final String ARTIFACT_NAMESPACE_MYSQL_5_5 = "http://org.plasma/sdo/mysql/5_5";
}
