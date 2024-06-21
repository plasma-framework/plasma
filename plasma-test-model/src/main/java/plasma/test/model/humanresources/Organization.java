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
package plasma.test.model.humanresources;

import org.plasma.sdo.DataType;
import org.plasma.sdo.annotation.Alias;
import org.plasma.sdo.annotation.DataProperty;
import org.plasma.sdo.annotation.EnumConstraint;
import org.plasma.sdo.annotation.Key;
import org.plasma.sdo.annotation.ReferenceProperty;
import org.plasma.sdo.annotation.Type;
import org.plasma.sdo.annotation.ValueConstraint;
import org.plasma.sdo.profile.KeyType;

@Alias(physicalName = "ORG")
@Type(superTypes = { Party.class })
public enum Organization {
  @Key(type = KeyType.primary)
  @ValueConstraint(maxLength = "36")
  @Alias(physicalName = "NAME")
  @DataProperty(dataType = DataType.String, isNullable = false)
  name, @EnumConstraint(targetEnum = OrgCat.class)
  @Alias(physicalName = "ORG_CAT")
  @DataProperty(dataType = DataType.String, isNullable = false)
  category, @Alias(physicalName = "PARENT")
  @ReferenceProperty(isNullable = true, isMany = false, targetClass = Organization.class, targetProperty = "child")
  parent, @Alias(physicalName = "CHILD")
  @ReferenceProperty(isNullable = true, isMany = true, targetClass = Organization.class, targetProperty = "parent")
  child, @Alias(physicalName = "EMPLOYEE")
  @ReferenceProperty(isNullable = true, isMany = true, targetClass = Person.class, targetProperty = "employer")
  employee;
}
