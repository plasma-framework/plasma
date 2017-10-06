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

import java.util.List;

import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.repository.EnumerationLiteral;

class FumlEnumerationLiteral extends
    FumlElement<org.modeldriven.fuml.repository.EnumerationLiteral> implements EnumerationLiteral {

  public FumlEnumerationLiteral(org.modeldriven.fuml.repository.EnumerationLiteral literal) {
    super(literal);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.EnumerationLiteral#getPhysicalName()
   */
  @Override
  public String getPhysicalName() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        this.element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOAlias) {
          SDOAlias sdoAliasStereotype = (SDOAlias) stereotype.getDelegate();
          if (sdoAliasStereotype.getPhysicalName() != null)
            return sdoAliasStereotype.getPhysicalName();
          else
            throw new PlasmaRuntimeException(
                "expected SDOAlias 'physicalName' property for Enumerationliteral, '"
                    + this.element.getEnumeration().getName() + "." + this.element.getName() + "'");

        }
    }
    return null;
  }

}
