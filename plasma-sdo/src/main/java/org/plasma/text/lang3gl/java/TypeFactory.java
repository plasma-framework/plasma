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

import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.EnumerationFactory;
import org.plasma.text.lang3gl.InterfaceFactory;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLFactory;

public class TypeFactory implements Lang3GLFactory {

  private Lang3GLContext context;

  public TypeFactory(Lang3GLContext context) {
    this.context = context;
  }

  public ClassFactory getClassFactory() {
    return new TypeClassFactory(this.context);
  }

  public EnumerationFactory getEnumerationFactory() {
    return new TypeEnumerationFactory(this.context);
  }

  public InterfaceFactory getInterfaceFactory() {
    throw new RuntimeException("not implemented");
  }

}
