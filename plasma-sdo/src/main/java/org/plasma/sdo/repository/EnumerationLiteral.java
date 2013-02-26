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
package org.plasma.sdo.repository;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.sdo.profile.SDOAlias;

public class EnumerationLiteral {
	private org.modeldriven.fuml.repository.EnumerationLiteral literal;

	public EnumerationLiteral(org.modeldriven.fuml.repository.EnumerationLiteral literal) {
		super();
		this.literal = literal;		
	}
	
	public String getName() {
		return this.literal.getName();
	}

	public List<Comment> getComments()
	{
		List<Comment> result = new ArrayList<Comment>();
		for (fUML.Syntax.Classes.Kernel.Comment comment : literal.getDelegate().ownedComment)
			result.add(new Comment(comment));
		return result;	
	}   
	
	
	public String getPhysicalName() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(this.literal);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	SDOAlias sdoAliasStereotype = (SDOAlias)stereotype.getDelegate();
                    if (sdoAliasStereotype.getPhysicalName() != null)
                        return sdoAliasStereotype.getPhysicalName();
                    else    
                        throw new PlasmaRuntimeException("expected SDOAlias 'physicalName' property for Enumerationliteral, '"
                                + this.literal.getEnumeration().getName() + "." 
                                + this.literal.getName() + "'");
                    
                }
        }
        return null;
    }    

	public SDOAlias findAlias() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(this.literal);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	return (SDOAlias)stereotype.getDelegate();
                }
        }
        return null;
    }    
	
}
