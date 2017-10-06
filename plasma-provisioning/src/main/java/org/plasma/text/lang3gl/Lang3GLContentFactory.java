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

import org.plasma.metamodel.Package;
import org.plasma.sdo.DataType;

public interface Lang3GLContentFactory {
  /**
   * Returns the language context for this factory.
   * 
   * @return the language context for this factory.
   */
  public Lang3GLContext getContext();

  /**
   * Returns a 3Gl language specific class for the given SDO data-type (as per
   * the SDO Specification 2.10 Section 8.1) where primitive type names or
   * wrapper type names returned based on the current context.
   * 
   * @see Lang3GLModelContext
   * @see DataType
   * @param dataType
   *          the SDO datatype
   * @return the 3Gl language specific type class.
   */
  public java.lang.Class<?> getTypeClass(DataType dataType);

  /**
   * Returns an 3Gl language specific base directory name for the given Package
   * based on configuration settings.
   * 
   * @see Package
   * @param pkg
   *          the package
   * @return an 3Gl language specific directory name for the given Package based
   *         on configuration settings
   */
  public String createBaseDirectoryName(Package pkg);

  /**
   * Returns an 3Gl language specific directory name for the given Package based
   * on configuration settings.
   * 
   * @see Package
   * @param pkg
   *          the package
   * @return an 3Gl language specific directory name for the given Package based
   *         on configuration settings
   */
  public String createDirectoryName(Package pkg);

}
