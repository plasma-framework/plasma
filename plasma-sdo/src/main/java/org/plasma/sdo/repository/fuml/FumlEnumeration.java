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

import org.plasma.sdo.repository.Enumeration;
import org.plasma.sdo.repository.EnumerationLiteral;

class FumlEnumeration extends FumlElement<org.modeldriven.fuml.repository.Enumeration> implements
    Enumeration {

  public FumlEnumeration(org.modeldriven.fuml.repository.Enumeration enumeration) {
    super(enumeration);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Enumeration#getOwnedLiteral()
   */
  @Override
  public List<EnumerationLiteral> getOwnedLiteral() {
    List<EnumerationLiteral> result = new ArrayList<EnumerationLiteral>();

    for (org.modeldriven.fuml.repository.EnumerationLiteral literal : this.element
        .getOwnedLiteral()) {
      result.add(new FumlEnumerationLiteral(literal));
    }
    return result;
  }

}
