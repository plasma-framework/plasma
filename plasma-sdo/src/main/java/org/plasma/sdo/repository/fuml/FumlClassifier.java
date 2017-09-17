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
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.profile.SDODerivation;
import org.plasma.sdo.repository.Classifier;
import org.plasma.sdo.repository.Comment;
import org.plasma.sdo.repository.Element;
import org.plasma.sdo.repository.Visibility;

class FumlClassifier<T extends org.modeldriven.fuml.repository.Classifier> extends
    FumlElement<org.modeldriven.fuml.repository.Classifier> implements Classifier {

  public FumlClassifier(T delegate) {
    super(delegate);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#toString()
   */
  @Override
  public String toString() {
    return this.getNamespaceURI() + "#" + this.getName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getPackageName()
   */
  @Override
  public String getPackageName() {
    if (element.getPackage() != null)
      return this.element.getPackage().getName();
    else
      return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getPackagePhysicalName()
   */
  @Override
  public String getPackagePhysicalName() {
    SDOAlias alias = findPackageAlias();
    if (alias != null)
      return alias.getPhysicalName();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#findPackageAlias()
   */
  @Override
  public SDOAlias findPackageAlias() {
    if (element.getPackage() != null) {
      List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
          element.getPackage());
      if (stereotypes != null) {
        for (Stereotype stereotype : stereotypes)
          if (stereotype.getDelegate() instanceof SDOAlias) {
            return (SDOAlias) stereotype.getDelegate();
          }
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#findDerivation()
   */
  @Override
  public SDODerivation findDerivation() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDODerivation) {
          return (SDODerivation) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getComments()
   */
  @Override
  public List<Comment> getComments() {
    List<Comment> result = new ArrayList<Comment>();
    for (fUML.Syntax.Classes.Kernel.Comment comment : element.getDelegate().ownedComment)
      result.add(new FumlComment(comment));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getVisibility()
   */
  @Override
  public Visibility getVisibility() {
    return Visibility.valueOf(this.element.getDelegate().visibility.name());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#isAbstract()
   */
  @Override
  public boolean isAbstract() {
    return this.element.isAbstract();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#isDataType()
   */
  @Override
  public boolean isDataType() {
    return this.element.isDataType();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getGeneralization()
   */
  @Override
  public List<Classifier> getGeneralization() {
    List<Classifier> result = new ArrayList<>();
    for (org.modeldriven.fuml.repository.Classifier c : element.getGeneralization())
      result.add(new FumlClassifier<org.modeldriven.fuml.repository.Classifier>(c));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getSpecializations()
   */
  @Override
  public List<Classifier> getSpecializations() {
    List<Classifier> result = new ArrayList<>();
    for (org.modeldriven.fuml.repository.Classifier c : FumlRepository.getFumlRepositoryInstance()
        .getSpecializations(this.element))
      result.add(new FumlClassifier<org.modeldriven.fuml.repository.Classifier>(c));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return super.getNamespaceURI(this.element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getPhysicalName()
   */
  @Override
  public String getPhysicalName() {
    SDOAlias alias = findAlias();
    if (alias != null)
      return alias.getPhysicalName();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getLocalName()
   */
  @Override
  public String getLocalName() {
    SDOAlias alias = findAlias();
    if (alias != null)
      return alias.getLocalName();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getBusinessName()
   */
  @Override
  public String getBusinessName() {
    SDOAlias alias = findAlias();
    if (alias != null)
      return alias.getBusinessName();
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#findAlias()
   */
  @Override
  public SDOAlias findAlias() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
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
   * @see org.plasma.sdo.repository.fuml.Classifier#getDerivationSupplier()
   */
  @Override
  public Classifier getDerivationSupplier() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDODerivation) {
          SDODerivation sdoDerivationStereotype = (SDODerivation) stereotype.getDelegate();
          if (sdoDerivationStereotype.getSupplier() != null) {
            SDODerivation deriv = (SDODerivation) stereotype.getDelegate();
            fUML.Syntax.Classes.Kernel.NamedElement namedElem = deriv.getSupplier();
            Element elem = FumlRepository.getFumlRepositoryInstance().getElementById(
                namedElem.getXmiId());
            if (elem instanceof Classifier) {
              return (Classifier) elem;
            }
          }
        }
    }
    return null;
  }

  @Override
  public String getArtifactNamespaceURI() {
    return this.element.getArtifact().getNamespaceURI();
  }

}
