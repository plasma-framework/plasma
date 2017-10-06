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
import org.plasma.metamodel.adapter.ProvisioningModel;
import org.plasma.metamodel.adapter.TypeAdapter;
import org.plasma.text.TextProvisioningException;

public class DefaultLang3GLContext implements Lang3GLContext {

  private ProvisioningModel ProvisioningModel;

  @SuppressWarnings("unused")
  private DefaultLang3GLContext() {
  }

  public DefaultLang3GLContext(ProvisioningModel ProvisioningModel) {
    this.ProvisioningModel = ProvisioningModel;
  }

  public Class findClass(ClassRef cref) {
    String key = cref.getUri() + "#" + cref.getName();
    return findClass(key);
  }

  public Class findClass(String qualifiedName) {
    TypeAdapter type = this.ProvisioningModel.findType(qualifiedName);
    if (!type.isClass())
      throw new TextProvisioningException("expected instanceof Class not, "
          + type.getType().getClass());

    return (Class) type.getType();
  }

  public Package findPackage(ClassRef cref) {
    String key = cref.getUri() + "#" + cref.getName();
    return this.findPackage(key);
  }

  public Package findPackage(String qualifiedName) {
    TypeAdapter type = this.ProvisioningModel.findType(qualifiedName);
    return this.ProvisioningModel.getPackage(type);
  }

  public boolean usePrimitives() {
    return true;
  }

  public String getIndentationToken() {
    return "\t";
  }

}
