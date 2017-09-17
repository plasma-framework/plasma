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

package org.plasma.sdo.repository.fuml;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.sdo.profile.SDONamespace;
import org.plasma.sdo.repository.Namespace;

class FumlNamespace extends FumlElement<org.modeldriven.fuml.repository.Package> implements
    Namespace {

  private String packageQualifiedName;

  public FumlNamespace(org.modeldriven.fuml.repository.Package namespace) {
    super(namespace);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Namespace#getUri()
   */
  @Override
  public String getUri() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        this.element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDONamespace) {
          SDONamespace sdoNamespaceStereotype = (SDONamespace) stereotype.getDelegate();
          return sdoNamespaceStereotype.getUri();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Namespace#getQualifiedPackageName()
   */
  @Override
  public String getQualifiedPackageName() {
    if (packageQualifiedName == null) {
      List<String> names = new ArrayList<String>();
      names.add(this.element.getName());
      org.modeldriven.fuml.repository.Package parent = this.element.getNestingPackage();
      while (parent != null) {
        names.add(parent.getName());
        parent = parent.getNestingPackage();
      }
      StringBuilder buf = new StringBuilder();
      int len = names.size();
      for (int i = len - 1; i >= 0; i--) {
        if (i < len - 1)
          buf.append(".");
        String name = names.get(i);
        buf.append(name);
      }
      this.packageQualifiedName = buf.toString();
    }
    return this.packageQualifiedName;
  }

}
