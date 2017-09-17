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

package org.plasma.provisioning.adapter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.plasma.metamodel.Model;
import org.plasma.metamodel.Package;

public interface ProvisioningModel {

  Model getModel();

  List<Package> getPackages();

  List<Package> getLeafPackages();

  Collection<TypeAdapter> getTypes();

  TypeAdapter[] getTypesArray();

  TypeAdapter findType(String key);

  Map<String, TypeAdapter> getTypeMap();

  Package getPackage(TypeAdapter type);

}