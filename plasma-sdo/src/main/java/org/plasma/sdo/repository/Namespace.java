package org.plasma.sdo.repository;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.profile.SDONamespace;

public class Namespace {

	private org.modeldriven.fuml.repository.Package namespace;
	
	@SuppressWarnings("unused")
	private Namespace() {}
	
	Namespace(org.modeldriven.fuml.repository.Package namespace) {
		this.namespace = namespace;
	}
	
	public String getName() {
		return this.namespace.getName();
	}
	
	public String getUri() {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(this.namespace);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDONamespace) {
                	SDONamespace sdoNamespaceStereotype = (SDONamespace)stereotype.getDelegate();
                	return sdoNamespaceStereotype.getUri();
                }
        }
        return null;
	}

    public String getPhysicalName() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(this.namespace);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	SDOAlias sdoAliasStereotype = (SDOAlias)stereotype.getDelegate();
                    if (sdoAliasStereotype.getPhysicalName() != null)
                        return sdoAliasStereotype.getPhysicalName();
                }
        }
        return null;
    }  
    
    public SDOAlias findAlias() 
    {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(this.namespace);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	return (SDOAlias)stereotype.getDelegate();
                }
        }
        return null;
    }  
    
	public List<Comment> getComments() {
		List<Comment> result = new ArrayList<Comment>();
		for (fUML.Syntax.Classes.Kernel.Comment comment : namespace.getDelegate().ownedComment)
			result.add(new Comment(comment));
		return result;	
	}
}
