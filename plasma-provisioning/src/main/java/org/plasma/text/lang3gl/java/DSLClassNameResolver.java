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

import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Package;
import org.plasma.runtime.Namespace;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.text.lang3gl.ClassNameResolver;

public class DSLClassNameResolver extends DefaultNameResolver implements ClassNameResolver {

  @Override
  public String getQualifiedName(Class clss, Package pkg) {
    String qualifiedName = getQualifiedName(clss.getUri(), clss.getName());
    checkUnresolvableNameCollision(qualifiedName, clss, pkg);
    return qualifiedName;
  }

  @Override
  public String getQualifiedName(ClassRef clssRef) {
    return getQualifiedName(clssRef.getUri(), clssRef.getName());
  }

  private String getQualifiedName(String uri, String name) {
    Namespace sdoNamespace = PlasmaRuntime.getInstance().getSDONamespaceByURI(uri);
    String packageName = sdoNamespace.getProvisioning().getPackageName();
    String subpackage = PlasmaRuntime.getInstance().getSDO().getGlobalProvisioning().getQueryDSL()
        .getImplementation().getChildPackageName();
    if (subpackage != null && subpackage.trim().length() > 0)
      packageName = packageName + "." + subpackage;
    String className = PlasmaRuntime.getInstance().getQueryDSLImplementationClassName(uri, name);
    className = this.replaceReservedCharacters(className);
    String qualifiedName = packageName + "." + className;
    return qualifiedName;
  }

  @Override
  public String getName(Class clss) {
    String className = PlasmaRuntime.getInstance().getQueryDSLImplementationClassName(
        clss.getUri(), clss.getName());
    className = this.replaceReservedCharacters(className);
    return className;
  }

}
