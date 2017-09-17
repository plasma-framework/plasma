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

package org.plasma.text.lang3gl;

import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Package;

public interface ClassNameResolver {

  /**
   * Returns an unqualified class name for the given provisioning model class.
   * 
   * @param clss
   *          the provisioning class
   * @return a unqualified class name
   */
  public String getName(Class clss);

  /**
   * Returns a qualified class name for the given provisioning model class.
   * 
   * @param clss
   *          the provisioning class
   * @param pkg
   *          the provisioning package
   * @return a qualified class name
   */
  public String getQualifiedName(Class clss, Package pkg);

  /**
   * Returns a qualified class name for the given provisioning model class
   * reference.
   * 
   * @param clssRef
   *          the provisioning class reference
   * @return a qualified class name
   */
  public String getQualifiedName(ClassRef clssRef);
}
