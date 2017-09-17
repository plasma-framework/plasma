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

import org.modeldriven.fuml.repository.OpaqueBehavior;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.repository.Class_;
import org.plasma.sdo.repository.Property;
import org.plasma.sdo.repository.RepositoryException;

class FumlClass_ extends FumlClassifier<org.modeldriven.fuml.repository.Class_> implements Class_ {

  public FumlClass_(org.modeldriven.fuml.repository.Class_ class_) {
    super(class_);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getDeclaredProperties()
   */
  @Override
  public List<Property> getDeclaredProperties() {
    List<Property> result = new ArrayList<>();
    for (org.modeldriven.fuml.repository.Property p : ((org.modeldriven.fuml.repository.Class_) this.element)
        .getDeclaredProperties())
      result.add(new FumlProperty(p));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Classifier#getAllProperties()
   */
  @Override
  public List<Property> getAllProperties() {
    List<Property> result = new ArrayList<>();
    for (org.modeldriven.fuml.repository.Property p : ((org.modeldriven.fuml.repository.Class_) this.element)
        .getAllProperties())
      result.add(new FumlProperty(p));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.plasma.sdo.repository.fuml.Class_#findOpaqueBehaviorBody(java.lang.
   * String, java.lang.String)
   */
  @Override
  public String findOpaqueBehaviorBody(String name, String language) {
    return getOpaqueBehaviorBody(name, language, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.plasma.sdo.repository.fuml.Class_#getOpaqueBehaviorBody(java.lang.String
   * , java.lang.String)
   */
  @Override
  public String getOpaqueBehaviorBody(String name, String language) {
    return getOpaqueBehaviorBody(name, language, false);
  }

  private String getOpaqueBehaviorBody(String name, String language, boolean supressError) {

    String result = null;
    for (OpaqueBehavior behavior : ((org.modeldriven.fuml.repository.Class_) this.element)
        .getOpaqueBehaviors()) {
      if (behavior.getName().equals(name) && behavior.getLanguage().equals(language)) {
        result = behavior.getBody();
      }
    }
    if (result == null && !supressError)
      throw new RepositoryException("could not find opaque behavior for name: " + name
          + ", language: " + language);

    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.plasma.sdo.repository.fuml.Class_#getOpaqueBehaviors(java.lang.String)
   */
  @Override
  public List<org.plasma.sdo.repository.OpaqueBehavior> getOpaqueBehaviors(String language) {

    List<org.plasma.sdo.repository.OpaqueBehavior> result = new ArrayList<org.plasma.sdo.repository.OpaqueBehavior>();
    for (OpaqueBehavior behavior : ((org.modeldriven.fuml.repository.Class_) this.element)
        .getOpaqueBehaviors()) {
      if (behavior.getLanguage().equalsIgnoreCase(language)) {
        result.add(new FumlOpaqueBehavior(behavior));
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.plasma.sdo.repository.fuml.Classifier#isRelation(org.plasma.sdo.repository
   * .fuml.FumlClassifier, org.plasma.sdo.AssociationPath)
   */
  @Override
  public boolean isRelation(org.plasma.sdo.repository.Class_ other, AssociationPath relation) {
    return FumlRepository.getFumlRepositoryInstance().getRelationCache()
        .isRelation(this, other, relation);
  }

}
