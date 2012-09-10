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
