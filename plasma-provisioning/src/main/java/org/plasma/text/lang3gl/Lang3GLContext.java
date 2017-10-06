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

/**
 * Represents language preferences
 */
public interface Lang3GLContext {

  /**
   * Returns a Class for the given ClassRef
   * 
   * @param cref
   *          the ClassRef
   * @see org.plasma.text.lang3gl.Class
   * @see org.plasma.text.lang3gl.ClassRef
   * @return the Class referenced by the given ClassRef
   */
  public Class findClass(ClassRef cref);

  /**
   * Returns a Class for the given qualified name where qualified names take the
   * format uri#class-name, for example http://mysite.com/myapp#MyClass
   * 
   * @param qualifiedName
   *          the qualified name, for example http://mysite.com/myapp#MyClass
   * @see org.plasma.text.lang3gl.Class
   * @return the Class referenced by the given qualified name
   */
  public Class findClass(String qualifiedName);

  /**
   * Returns a Package for the given ClassRef
   * 
   * @param cref
   *          the ClassRef
   * @see org.plasma.text.lang3gl.Package
   * @see org.plasma.text.lang3gl.ClassRef
   * @return the Package referenced by the given ClassRef
   */
  public Package findPackage(ClassRef cref);

  /**
   * Returns a Package for the given qualified name where qualified names take
   * the format uri#class-name, for example http://mysite.com/myapp#MyClass
   * 
   * @param qualifiedName
   *          the qualified name, for example http://mysite.com/myapp#MyClass
   * @see org.plasma.text.lang3gl.Package
   * @return the Package referenced by the given qualified name
   */
  public Package findPackage(String qualifiedName);

  /**
   * Whether to use primitives over wrapper type classes.
   * 
   * @return whether to use primitives over wrapper type classes
   */
  public boolean usePrimitives();

  public String getIndentationToken();

}
