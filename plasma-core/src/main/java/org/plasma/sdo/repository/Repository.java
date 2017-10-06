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

/**
 * 
 * @author Scott Cinnamond
 * @since 1.2.4
 */
public interface Repository {

  List<String> getAllNamespaceUris();

  List<Namespace> getAllNamespaces();

  Namespace getNamespaceForUri(String uri);

  /**
   * Returns a list of classifiers contained by the package, as well as all
   * classifiers within its contained packages, linked/stereotyped through an
   * SDONamespace stereotype where the stereotype uri matches the given uri.
   * 
   * @see org.plasma.sdo.profile.SDONamespace
   * @param uri
   *          the SDO namespace URI
   * @return a list of classifiers contained by the package, as wells as all
   *         classifiers within its contained packages, linked through an
   *         SDONamespace stereotype where the uri which matches the given uri.
   */
  List<Classifier> getClassifiers(String uri);

  /**
   * Fetches a classifier using the given artifact, package or namespace
   * qualified name.
   * 
   * @param qualifiedName
   *          the artifact, package or namespace qualified name
   * @return the classifier
   * @throws RepositoryException
   *           if the classifier is not found
   */
  Classifier getClassifier(String qualifiedName);

  /**
   * Searches for a classifier using the given artifact, package or namespace
   * qualified name.
   * 
   * @param qualifiedName
   *          the artifact, package or namespace qualified name
   * @return the classifier or null if not found
   */
  Classifier findClassifier(String qualifiedName);

  // Classifier getClassifierById(String id);
  // Classifier getClassifierByName(String name);
  // Classifier getClassifierByQualifiedName(String qualifiedName);

  // Element findElementById(String id);

  // Element findElementByName(String name);

  // Element findElementByQualifiedName(String qualifiedName);

  Class_ getClassById(String id);

  // Enumeration getEnumerationById(String id);
  // EnumerationLiteral getEnumerationLiteralById(String id);
  // Property getPropertyById(String id);
  // Element getElementById(String id);

  // Element getElementByName(String name);

  // Element getElementByQualifiedName(String qualifiedName);

  // String getDefaultUMLNamespaceURI();

  RelationCache getRelationCache();

}