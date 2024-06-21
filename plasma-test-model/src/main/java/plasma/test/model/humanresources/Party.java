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
import org.plasma.sdo.annotation.Type;

@Type(name = "Party", isAbstract = true)
public enum Party {
  @Alias(physicalName = "CRTD_DT")
  @DataProperty(dataType = DataType.Date, isNullable = false)
  createdDate
}
