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
import org.plasma.provisioning.rdb.oracle.any.sys.Version;
import org.plasma.provisioning.rdb.oracle.any.sys.query.QVersion;
import org.plasma.sdo.access.client.JDBCPojoDataAccessClient;

import commonj.sdo.DataGraph;

public class OracleVersionFinder {
  private static Log log = LogFactory.getLog(OracleVersionFinder.class);
  protected JDBCPojoDataAccessClient client = new JDBCPojoDataAccessClient();

  public OracleVersion findVersion() {
    QVersion version = QVersion.newQuery();
    version.select(version.wildcard());
    version.where(version.banner().like("Oracle Database*"));
    DataGraph[] results = client.find(version);
    if (results == null || results.length == 0)
      throw new ProvisioningException("no Oracle version information found");
    if (results.length > 1)
      log.warn("found multiple version rows - ignoring");
    Version vers = (Version) results[0].getRootObject();
    if (vers.getBanner().contains("11g")) {
      return OracleVersion._11g;
    } else if (vers.getBanner().contains("10g")) {
      return OracleVersion._10g;
    } else if (vers.getBanner().contains("91")) {
      return OracleVersion._9i;
    } else
      return OracleVersion._unknown;
  }
}
