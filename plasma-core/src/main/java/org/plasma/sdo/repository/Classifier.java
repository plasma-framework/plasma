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

package org.plasma.sdo.repository;

import java.util.List;

import org.plasma.sdo.Alias;
import org.plasma.sdo.Derivation;

/**
 * 
 * @author Scott Cinnamond
 * @since 1.2.4
 */
public interface Classifier extends Element {

  /**
   * Returns the (UML) artifact URI associated with the classifier, if
   * available, or null e.g. if the classifier was not loaded as part of an
   * artifact.
   * 
   * @return the (UML) artifact URI associated with the classifier, if
   *         available, or null e.g. if the classifier was not loaded as part of
   *         an artifact.
   */
  String getArtifactNamespaceURI();

  String getPackageName();

  String getPackagePhysicalName();

  Alias findPackageAlias();

  Derivation findDerivation();

  List<Comment> getComments();

  Visibility getVisibility();

  boolean isAbstract();

  boolean isDataType();

  List<Classifier> getGeneralization();

  List<Classifier> getSpecializations();

  /**
   * Traverses the given classifier's package ancestry looking for an SDO
   * namespace stereotype, and if found, returns the URI.
   * 
   * @param classifier
   *          the classifier
   * @return the SDO namespace URI or null if not found.
   * @throws RepositoryException
   *           if the URI is not found
   */
  String getNamespaceURI();

  String getPhysicalName();

  String getLocalName();

  String getBusinessName();

  Alias findAlias();

  Classifier getDerivationSupplier();

}