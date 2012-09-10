package org.plasma.sdo.repository;

import java.util.List;

import org.modeldriven.fuml.repository.Package;
import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.sdo.profile.SDONamespace;

public abstract class Element {
	
	
    public String getNamespaceURI(org.modeldriven.fuml.repository.Classifier classifier) {
        org.modeldriven.fuml.repository.Package p = classifier.getPackage();
        String uri = findSDONamespaceURI(p);
        if (uri == null)
            throw new PlasmaRuntimeException("no SDO Namespace uri found for classifier, '"
                + classifier.getName() + "'");
        return uri;
    } 
    
    private String findSDONamespaceURI(Package pkg) {
    	
    	SDONamespace sdoNamespaceStereotype = findSDONamespace(pkg);
    	if (sdoNamespaceStereotype != null) {
    		return sdoNamespaceStereotype.getUri();
    	}
        
        if (pkg.getNestingPackage() != null)
            return findSDONamespaceURI(pkg.getNestingPackage());
            
        return null;
    } 
    
    private SDONamespace findSDONamespace(Package pkg) {
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(pkg);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDONamespace) {
                    return (SDONamespace)stereotype.getDelegate();
                }
        }
        return null;
    } 

}
