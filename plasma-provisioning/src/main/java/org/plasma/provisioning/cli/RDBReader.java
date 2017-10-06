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

package org.plasma.provisioning.cli;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.plasma.common.exception.ProvisioningException;
import org.plasma.metamodel.Model;
import org.plasma.provisioning.rdb.MySql55Converter;
import org.plasma.provisioning.rdb.MySqlVersion;
import org.plasma.provisioning.rdb.MySqlVersionFinder;
import org.plasma.provisioning.rdb.Oracle11GConverter;
import org.plasma.provisioning.rdb.OracleVersion;
import org.plasma.provisioning.rdb.OracleVersionFinder;
import org.plasma.provisioning.rdb.RDBConstants;
import org.plasma.runtime.NamespaceProvisioning;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.sdo.repository.fuml.FumlRepository;
import org.plasma.xml.schema.SchemaConverter;

public class RDBReader implements RDBConstants {
  private static Log log = LogFactory.getLog(RDBReader.class);

  public Model read(RDBDialect dialect, String[] schemaNames, String[] namespaces) {

    FumlRepository.getInstance(); // just force an init

    // convert the schemas
    Model model = null;
    switch (dialect) {
    case oracle:
      loadDynamicArtifact(RDBConstants.ARTIFACT_RESOURCE_ORACLE);
      loadDynamicNamespace(ARTIFACT_NAMESPACE_ORACLE_ANY_SYS,
          org.plasma.provisioning.rdb.oracle.any.sys.Version.class.getPackage(),
          RDBConstants.ARTIFACT_RESOURCE_ORACLE);
      OracleVersionFinder versionFinder = new OracleVersionFinder();
      OracleVersion version = versionFinder.findVersion();
      log.info("detected version '" + version + "'");
      SchemaConverter converter = null;
      switch (version) {
      case _9i:
      case _10g:
      case _11g:
        loadDynamicNamespace(ARTIFACT_NAMESPACE_ORACLE_11G_SYS,
            org.plasma.provisioning.rdb.oracle.g11.sys.Version.class.getPackage(),
            RDBConstants.ARTIFACT_RESOURCE_ORACLE);
        converter = new Oracle11GConverter(schemaNames, namespaces);
        break;
      case _unknown:
      default:
        log.warn("unknown Oracle version - using 11g metamodel");
        loadDynamicNamespace(ARTIFACT_NAMESPACE_ORACLE_11G_SYS,
            org.plasma.provisioning.rdb.oracle.g11.sys.Version.class.getPackage(),
            RDBConstants.ARTIFACT_RESOURCE_ORACLE);
        converter = new Oracle11GConverter(schemaNames, namespaces);
        break;
      }

      model = converter.buildModel();
      break;
    case mysql:
      loadDynamicArtifact(RDBConstants.ARTIFACT_RESOURCE_MYSQL);
      loadDynamicNamespace(ARTIFACT_NAMESPACE_MYSQL_ANY,
          org.plasma.provisioning.rdb.mysql.any.GlobalVariable.class.getPackage(),
          RDBConstants.ARTIFACT_RESOURCE_MYSQL);
      MySqlVersionFinder mysqlVersionFinder = new MySqlVersionFinder();
      MySqlVersion mysqlVersion = mysqlVersionFinder.findVersion();
      log.info("detected version '" + mysqlVersion + "'");
      converter = null;
      switch (mysqlVersion) {
      case _5_5:
        loadDynamicNamespace(ARTIFACT_NAMESPACE_MYSQL_5_5,
            org.plasma.provisioning.rdb.mysql.v5_5.GlobalVariable.class.getPackage(),
            RDBConstants.ARTIFACT_RESOURCE_MYSQL);
        converter = new MySql55Converter(schemaNames, namespaces);
        break;
      case _unknown:
      default:
        log.warn("unknown MySql version - using 5.5 metamodel");
        loadDynamicNamespace(ARTIFACT_NAMESPACE_MYSQL_5_5,
            org.plasma.provisioning.rdb.mysql.v5_5.GlobalVariable.class.getPackage(),
            RDBConstants.ARTIFACT_RESOURCE_MYSQL);
        converter = new MySql55Converter(schemaNames, namespaces);
        break;
      }

      model = converter.buildModel();
      break;
    default:
      throw new ProvisioningException("unknown dialect, '" + dialect.name() + "'");
    }

    return model;
  }

  /**
   * Dynamically loads the vendor metamodel for the given namespace and
   * provisioning package adding a dynamic SDO namespace.
   * 
   * @param namespace
   *          the SDO namespace URI
   * @param pkg
   *          the provisioning package
   */
  private static void loadDynamicNamespace(String namespace, java.lang.Package pkg,
      String artifactResourceName) {

    NamespaceProvisioning provisioning = new NamespaceProvisioning();
    provisioning.setPackageName(pkg.getName());

    PlasmaRuntime.getInstance().addDynamicSDONamespace(namespace, artifactResourceName,
        provisioning);
  }

  private static void loadDynamicArtifact(String artifactResourceName) {
    InputStream stream = UMLTool.class.getClassLoader().getResourceAsStream(artifactResourceName);

    if (log.isDebugEnabled())
      log.info("loading UML/XMI model");
    Fuml.load(new ResourceArtifact(artifactResourceName, artifactResourceName, stream));
  }

}
