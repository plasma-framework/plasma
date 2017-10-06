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
import org.plasma.sdo.Concurrent;
import org.plasma.sdo.Derivation;
import org.plasma.sdo.EnumerationConstraint;
import org.plasma.sdo.Key;
import org.plasma.sdo.Sort;
import org.plasma.sdo.Temporal;
import org.plasma.sdo.UniqueConstraint;
import org.plasma.sdo.ValueConstraint;
import org.plasma.sdo.ValueSetConstraint;
import org.plasma.sdo.XmlProperty;

/**
 * 
 * @author Scott Cinnamond
 * @since 1.2.4
 */
public interface Property extends Element {

  Class_ getClass_();

  Object findPropertyDefault();

  boolean isMany();

  boolean isNullable();

  /**
   * Return the associated opposite for this property, or null if non exists.
   * 
   * @return the associated opposite for this property, or null if non exists.
   */
  Property getOpposite();

  List<Comment> getComments();

  boolean isDataType();

  Classifier getType();

  Visibility getVisibility();

  boolean getIsReadonly();

  String findPhysicalName();

  String getLocalName();

  Alias findAlias();

  Key findKey();

  Property findKeySupplier();

  Concurrent findConcurrent();

  Temporal findTemporal();

  EnumerationConstraint findEnumerationConstraint();

  ValueSetConstraint findValueSetConstraint();

  ValueConstraint findValueConstraint();

  Sort findSort();

  UniqueConstraint findUniqueConstraint();

  Property findDerivationSupplier();

  Derivation findDerivation();

  boolean getIsPriKey();

  Long getMaxLength();

  Enumeration getRestriction();

  XmlProperty findXmlProperty();

  boolean getIsConcurrencyUser();

  boolean getIsConcurrencyVersion();

  boolean getIsLockingUser();

  boolean getIsLockingTimestamp();

  boolean getIsOriginationUser();

  boolean getIsOriginationTimestamp();

  boolean getIsUnique();

}