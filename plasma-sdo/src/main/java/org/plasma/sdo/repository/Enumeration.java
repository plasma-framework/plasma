package org.plasma.sdo.repository;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.sdo.profile.SDOAlias;



public class Enumeration {

	private org.modeldriven.fuml.repository.Enumeration enumeration;

	public Enumeration(org.modeldriven.fuml.repository.Enumeration enumeration) {
		super();
		this.enumeration = enumeration;
	}
	
	public String getName() {
		return this.enumeration.getName();
	}
	
	public List<Comment> getComments()
	{
		List<Comment> result = new ArrayList<Comment>();
		for (fUML.Syntax.Classes.Kernel.Comment comment : enumeration.getDelegate().ownedComment)
			result.add(new Comment(comment));
		return result;	
	}   
	
	public List<EnumerationLiteral> getOwnedLiteral() {
		List<EnumerationLiteral> result = new ArrayList<EnumerationLiteral>();
		
		for (org.modeldriven.fuml.repository.EnumerationLiteral literal : this.enumeration.getOwnedLiteral()) {
			result.add(new EnumerationLiteral(literal));
		}
	    return result;	
	}
	
	public SDOAlias findAlias() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(this.enumeration);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	return (SDOAlias)stereotype.getDelegate();
                }
        }
        return null;
    }    
}
