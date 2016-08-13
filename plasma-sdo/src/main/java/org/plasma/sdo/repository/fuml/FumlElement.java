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

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.Package;
import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.profile.SDONamespace;
import org.plasma.sdo.repository.Comment;
import org.plasma.sdo.repository.Element;
import org.plasma.sdo.repository.RepositoryException;

class FumlElement<T extends org.modeldriven.fuml.repository.NamedElement> implements Element {
		
	protected T element;
	
	protected FumlElement(T delegate) {
		this.element = delegate;
		if (this.element == null)
			throw new IllegalArgumentException("element cannot be null");
	}
	
    public T getDelegate() {
		return element;
	}
    
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Element#getName()
	 */
	@Override
	public String getName() {
		return this.element.getName();
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Element#getId()
	 */
	@Override
	public String getId() {
		return this.element.getDelegate().getXmiId();
	}	
	
	protected String getNamespaceURI(org.modeldriven.fuml.repository.Classifier classifier) {
        org.modeldriven.fuml.repository.Package p = classifier.getPackage();
        String uri = findSDONamespaceURI(p);
        if (uri == null)
            throw new RepositoryException("no SDO Namespace uri found for classifier, '"
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
        List<Stereotype> stereotypes = FumlRepository.getInstance().getStereotypes(pkg);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDONamespace) {
                    return (SDONamespace)stereotype.getDelegate();
                }
        }
        return null;
    } 

    /* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Element#getPhysicalName()
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
                }
        }
        return null;
    }  
    
    /* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Element#findAlias()
	 */
    @Override
	public SDOAlias findAlias() 
    {
        List<Stereotype> stereotypes = FumlRepository.getInstance().getStereotypes(this.element);
        if (stereotypes != null) {
            for (Stereotype stereotype : stereotypes)
                if (stereotype.getDelegate() instanceof SDOAlias) {
                	return (SDOAlias)stereotype.getDelegate();
                }
        }
        return null;
    }  
    
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Element#getComments()
	 */
	@Override
	public List<Comment> getComments() {
		List<Comment> result = new ArrayList<Comment>();
		for (fUML.Syntax.Classes.Kernel.Comment comment : element.getDelegate().ownedComment)
			result.add(new FumlComment(comment));
		return result;	
	}
}
