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
import org.plasma.metamodel.Package;

public interface InterfaceFactory extends Lang3GLContentFactory {

  /**
   * Returns an 3Gl language specific file name for the given Class based on
   * configuration settings.
   * 
   * @see Class
   * @param clss
   *          the Class
   * @param pkg
   *          the Package
   * @return an 3Gl language specific file name for the given Class based on
   *         configuration settings
   */
  public String createFileName(Class clss, Package pkg);

  /**
   * Returns an 3Gl language specific content for the given Package and Type
   * 
   * @param pkg
   *          the Package
   * @param type
   *          the Type
   * @return the content
   */
  public String createContent(Package pkg, Class type);

}
