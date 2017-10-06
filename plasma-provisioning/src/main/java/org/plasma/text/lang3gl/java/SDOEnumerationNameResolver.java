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

package org.plasma.text.lang3gl.java;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.Namespace;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Package;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.text.lang3gl.ClassNameResolver;

public class SDOEnumerationNameResolver extends DefaultNameResolver implements ClassNameResolver {
  private static Log log = LogFactory.getLog(SDOEnumerationNameResolver.class);

  @Override
  public String getName(Class clss) {
    if (clss.getAlias() != null && clss.getAlias().getLocalName() != null)
      return clss.getAlias().getLocalName();
    else
      return clss.getName();
  }

  @Override
  public String getQualifiedName(Class clss, Package pkg) {
    String className = null;
    if (clss.getAlias() != null && clss.getAlias().getLocalName() != null)
      className = clss.getAlias().getLocalName();
    else
      className = clss.getName();

    String pkgName = null;
    if (pkg.getNamespaceProvisioning() != null
        && pkg.getNamespaceProvisioning().getOriginatingPackageName() != null) {
      pkgName = pkg.getNamespaceProvisioning().getOriginatingPackageName();
    } else {
      pkgName = pkg.getName();
    }
    StringBuilder qualifiedName = new StringBuilder();
    qualifiedName.append(pkgName);
    qualifiedName.append(".");
    qualifiedName.append(className);
    String result = qualifiedName.toString();
    return result;
  }

  @Override
  public String getQualifiedName(ClassRef clssRef) {
    Namespace sdoNamespace = PlasmaRuntime.getInstance().getSDONamespaceByURI(clssRef.getUri());
    String packageName = sdoNamespace.getProvisioning().getPackageName();

    StringBuilder qualifiedName = new StringBuilder();
    qualifiedName.append(packageName);
    qualifiedName.append(".");
    qualifiedName.append(clssRef.getName());
    return qualifiedName.toString();
  }

}
