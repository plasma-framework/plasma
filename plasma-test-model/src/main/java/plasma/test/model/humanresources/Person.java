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
import org.plasma.sdo.annotation.Key;
import org.plasma.sdo.annotation.ReferenceProperty;
import org.plasma.sdo.annotation.Type;
import org.plasma.sdo.annotation.ValueConstraint;
import org.plasma.sdo.profile.KeyType;

@Alias(physicalName = "PERSON")
@Type(superTypes = { Party.class })
public enum Person {
  @Key(type = KeyType.primary)
  @ValueConstraint(maxLength = "36")
  @Alias(physicalName = "FN")
  @DataProperty(dataType = DataType.String, isNullable = false)
  firstName, @Key(type = KeyType.primary)
  @ValueConstraint(maxLength = "36")
  @Alias(physicalName = "LN")
  @DataProperty(dataType = DataType.String, isNullable = false)
  lastName, @ValueConstraint(totalDigits = "3")
  @Alias(physicalName = "AGE")
  @DataProperty(dataType = DataType.Int)
  age, @Alias(physicalName = "DOB")
  @DataProperty(dataType = DataType.Date)
  dateOfBirth, @Alias(physicalName = "EMP")
  @DataProperty(dataType = DataType.Double, isMany = true)
  profileImageEmbed, @Alias(physicalName = "PIMG")
  @ReferenceProperty(targetClass = Organization.class, targetProperty = "employee")
  employer;
}
