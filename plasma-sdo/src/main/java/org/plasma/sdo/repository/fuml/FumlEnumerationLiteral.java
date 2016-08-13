/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.sdo.repository.fuml;

import java.util.List;

import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.repository.EnumerationLiteral;

class FumlEnumerationLiteral extends FumlElement<org.modeldriven.fuml.repository.EnumerationLiteral> implements EnumerationLiteral {

	public FumlEnumerationLiteral(org.modeldriven.fuml.repository.EnumerationLiteral literal) {
		super(literal);
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.EnumerationLiteral#getPhysicalName()
	 */
	@Override
	public String getPhysicalName() 
    {
        List<Stereotype> stereotypes = FumlRepository.getInstance().getStereotypes(this.element);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	SDOAlias sdoAliasStereotype = (SDOAlias)stereotype.getDelegate();
                    if (sdoAliasStereotype.getPhysicalName() != null)
                        return sdoAliasStereotype.getPhysicalName();
                    else    
                        throw new PlasmaRuntimeException("expected SDOAlias 'physicalName' property for Enumerationliteral, '"
                                + this.element.getEnumeration().getName() + "." 
                                + this.element.getName() + "'");
                    
                }
        }
        return null;
    }    

}
