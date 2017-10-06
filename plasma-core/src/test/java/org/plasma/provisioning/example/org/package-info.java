@Alias(physicalName = "ORG")
@Namespace(uri = "http://plasma-example/organization")
@NamespaceProvisioning(rootPackageName = "examples.pojo.model")
@NamespaceService(storeType = DataStoreType.RDBMS, providerName = DataAccessProviderName.JDBC)
package org.plasma.provisioning.example.org;

import org.plasma.runtime.annotation.NamespaceProvisioning;
import org.plasma.runtime.annotation.NamespaceService;
import org.plasma.sdo.annotation.Namespace;
import org.plasma.sdo.annotation.Alias;
import org.plasma.config.DataAccessProviderName;
import org.plasma.config.DataStoreType;

