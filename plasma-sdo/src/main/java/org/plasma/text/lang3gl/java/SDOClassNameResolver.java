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
import org.plasma.config.PlasmaConfig;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Package;
import org.plasma.text.lang3gl.ClassNameResolver;

public class SDOClassNameResolver extends DefaultNameResolver implements ClassNameResolver {
  private static Log log = LogFactory.getLog(SDOClassNameResolver.class);

  @Override
  public String getName(Class clss) {
    String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clss.getUri(),
        clss.getName());
    return replaceReservedCharacters(name);
  }

  @Override
  public String getQualifiedName(Class clss, Package pkg) {
    Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(clss.getUri());
    String packageName = sdoNamespace.getProvisioning().getPackageName();
    String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clss.getUri(),
        clss.getName());
    String qualifiedName = packageName + "." + replaceReservedCharacters(name);

    checkUnresolvableNameCollision(qualifiedName, clss, pkg);

    return qualifiedName;
  }

  @Override
  public String getQualifiedName(ClassRef clssRef) {
    Namespace sdoNamespace = PlasmaConfig.getInstance().getSDONamespaceByURI(clssRef.getUri());
    String packageName = sdoNamespace.getProvisioning().getPackageName();
    String name = PlasmaConfig.getInstance().getSDOImplementationClassName(clssRef.getUri(),
        clssRef.getName());
    String qualifiedName = packageName + "." + replaceReservedCharacters(name);
    return qualifiedName;
  }

}
