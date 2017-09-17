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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.rdb.mysql.any.GlobalVariable;
import org.plasma.provisioning.rdb.mysql.any.query.QGlobalVariable;
import org.plasma.sdo.access.client.JDBCPojoDataAccessClient;

import commonj.sdo.DataGraph;

public class MySqlVersionFinder {
  private static Log log = LogFactory.getLog(MySqlVersionFinder.class);
  protected JDBCPojoDataAccessClient client = new JDBCPojoDataAccessClient();

  public MySqlVersion findVersion() {
    QGlobalVariable variable = QGlobalVariable.newQuery();
    variable.select(variable.wildcard());
    variable.where(variable.name().eq("VERSION"));
    DataGraph[] results = client.find(variable);
    if (results == null || results.length == 0)
      throw new ProvisioningException("no MySql version information found");
    if (results.length > 1)
      log.warn("found multiple version rows - ignoring");
    GlobalVariable var = (GlobalVariable) results[0].getRootObject();

    if (var.getValue().startsWith("5.5")) {
      return MySqlVersion._5_5;
    } else
      return MySqlVersion._unknown;

  }
}
