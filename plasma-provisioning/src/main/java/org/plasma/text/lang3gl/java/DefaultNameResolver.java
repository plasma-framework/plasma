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
import org.plasma.metamodel.Package;
import org.plasma.text.NamingCollisionException;

public abstract class DefaultNameResolver {

  /**
   * Replace characters in the given name not applicable as a Java class name.
   * 
   * @param name
   *          the original name
   * @return the new name
   */
  protected String replaceReservedCharacters(String name) {
    String result = name;
    result = result.replace('-', '_');
    result = result.replace('+', '_');
    result = result.replace('/', '_');
    result = result.replace('\\', '_');
    result = result.replace('!', '_');
    result = result.replace('@', '_');
    result = result.replace(' ', '_');
    return result;
  }

  protected void checkUnresolvableNameCollision(String qualifiedName, Class clss, Package pkg) {
    String localName = null;
    if (pkg.getAlias() != null && pkg.getAlias().getLocalName() != null
        && pkg.getAlias().getLocalName().length() > 0)
      localName = pkg.getAlias().getLocalName();
    if (clss.getAlias() != null && clss.getAlias().getLocalName() != null
        && clss.getAlias().getLocalName().length() > 0)
      localName = localName + "." + clss.getAlias().getLocalName();
    if (localName != null && localName.equals(qualifiedName))
      throw new NamingCollisionException(
          "unresolvable name collision - identical local source and provisioning target classes ("
              + qualifiedName
              + ") - please adjust the class or package, source or provisioning target name(s)");
  }
}
