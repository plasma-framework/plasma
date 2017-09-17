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

import org.plasma.sdo.AssociationPath;

/**
 * 
 * @author Scott Cinnamond
 * @since 1.2.4
 */
public interface Class_ extends Classifier {

  List<Property> getDeclaredProperties();

  List<Property> getAllProperties();

  String findOpaqueBehaviorBody(String name, String language);

  String getOpaqueBehaviorBody(String name, String language);

  List<OpaqueBehavior> getOpaqueBehaviors(String language);

  boolean isRelation(Class_ other, AssociationPath relation);

}