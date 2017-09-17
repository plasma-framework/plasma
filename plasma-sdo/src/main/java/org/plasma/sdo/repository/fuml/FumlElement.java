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

import org.modeldriven.fuml.repository.Package;
import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.sdo.Alias;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.profile.SDONamespace;
import org.plasma.sdo.repository.Comment;
import org.plasma.sdo.repository.Element;
import org.plasma.sdo.repository.RepositoryException;

class FumlElement<T extends org.modeldriven.fuml.repository.NamedElement> implements Element {

  protected T element;

  protected FumlElement(T delegate) {
    this.element = delegate;
    if (this.element == null)
      throw new IllegalArgumentException("element cannot be null");
  }

  public T getDelegate() {
    return element;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Element#getName()
   */
  @Override
  public String getName() {
    return this.element.getName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Element#getId()
   */
  @Override
  public String getId() {
    return this.element.getDelegate().getXmiId();
  }

  protected String getNamespaceURI(org.modeldriven.fuml.repository.Classifier classifier) {
    org.modeldriven.fuml.repository.Package p = classifier.getPackage();
    String uri = findSDONamespaceURI(p);
    if (uri == null)
      throw new RepositoryException("no SDO Namespace uri found for classifier, '"
          + classifier.getName() + "'");
    return uri;
  }

  private String findSDONamespaceURI(Package pkg) {

    SDONamespace sdoNamespaceStereotype = findSDONamespace(pkg);
    if (sdoNamespaceStereotype != null) {
      return sdoNamespaceStereotype.getUri();
    }

    if (pkg.getNestingPackage() != null)
      return findSDONamespaceURI(pkg.getNestingPackage());

    return null;
  }

  private SDONamespace findSDONamespace(Package pkg) {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(pkg);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDONamespace) {
          return (SDONamespace) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Element#getPhysicalName()
   */
  @Override
  public String getPhysicalName() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        this.element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOAlias) {
          SDOAlias sdoAliasStereotype = (SDOAlias) stereotype.getDelegate();
          if (sdoAliasStereotype.getPhysicalName() != null)
            return sdoAliasStereotype.getPhysicalName();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Element#findAlias()
   */
  @Override
  public Alias findAlias() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        this.element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOAlias) {
          return (SDOAlias) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Element#getComments()
   */
  @Override
  public List<Comment> getComments() {
    List<Comment> result = new ArrayList<Comment>();
    for (fUML.Syntax.Classes.Kernel.Comment comment : element.getDelegate().ownedComment)
      result.add(new FumlComment(comment));
    return result;
  }
}
