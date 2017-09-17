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

package org.plasma.provisioning.xsd;

import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Restriction;

public class SimpleTypeUtils {
  public static boolean isEnumeration(AbstractSimpleType simpleType) {
    Restriction restriction = simpleType.getRestriction();
    if (restriction != null) {
      if (restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().size() > 0)
        for (Object obj : restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives())
          if (obj instanceof org.plasma.xml.schema.Enumeration)
            return true;

    }
    return false;
  }

}
