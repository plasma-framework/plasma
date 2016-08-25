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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Element;
import org.modeldriven.fuml.repository.NamedElement;
import org.modeldriven.fuml.repository.RepositorylException;
import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.config.Artifact;
import org.plasma.config.PlasmaConfig;
import org.plasma.sdo.profile.SDONamespace;
import org.plasma.sdo.repository.Class_;
import org.plasma.sdo.repository.Enumeration;
import org.plasma.sdo.repository.EnumerationLiteral;
import org.plasma.sdo.repository.Namespace;
import org.plasma.sdo.repository.Property;
import org.plasma.sdo.repository.RelationCache;
import org.plasma.sdo.repository.Repository;
import org.plasma.sdo.repository.RepositoryException;
import org.plasma.sdo.repository.RepositoryLoader;

import fUML.Syntax.Classes.Kernel.PackageableElement;

public class FumlRepository implements Repository {

    private static Log log = LogFactory.getLog(FumlRepository.class);
    private static volatile FumlRepository instance;
    private static org.modeldriven.fuml.repository.Repository delegate;
    private RelationCache relationCache = new FumlRelationCache();
    
    private FumlRepository() {}
    
    public static Repository getInstance() throws RepositoryException {
        return getFumlRepositoryInstance();
    }

    // package
    static FumlRepository getFumlRepositoryInstance() throws RepositoryException {
        if (instance == null) {
            initializeInstance();
        }
        return instance;
    }

    private static synchronized void initializeInstance() throws RepositorylException {
        if (instance == null) {
            
            for (org.plasma.config.Repository repo : PlasmaConfig.getInstance().getRepositories()) {
    	    	for (Artifact artifact : repo.getArtifact()) {
                    InputStream stream = PlasmaConfig.class.getResourceAsStream(artifact.getUrn());
                    if (stream == null)
                        stream = PlasmaConfig.class.getClassLoader().getResourceAsStream(artifact.getUrn());
                    if (stream == null)
                        throw new PlasmaRuntimeException("could not find artifact resource '" 
                                + artifact.getUrn() 
                                + "' on the current classpath");        
                    Fuml.load(new ResourceArtifact(
                        artifact.getUrn(), 
                        artifact.getNamespaceUri(), 
                        stream));                
    	    	}
            }
            
            delegate = org.modeldriven.fuml.repository.Repository.INSTANCE;
            instance = new FumlRepository();
        }
    }

    /* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getAllNamespaceUris()
	 */
	@Override
	public List<String> getAllNamespaceUris() {
		List<String> result = new ArrayList<String>();
		List<Stereotype> list = delegate.getStereotypes(SDONamespace.class);
		for (Stereotype s : list) {
			SDONamespace namespace = (SDONamespace)s.getDelegate();
			result.add(namespace.getUri());
		}		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getAllNamespaces()
	 */
	@Override
	public List<Namespace> getAllNamespaces() {
		List<Namespace> result = new ArrayList<Namespace>();
		List<Stereotype> list = delegate.getStereotypes(SDONamespace.class);
		for (Stereotype s : list) {			
			SDONamespace namespace = (SDONamespace)s.getDelegate();			
			fUML.Syntax.Classes.Kernel.Package pkg = namespace.getBase_Package();
			org.modeldriven.fuml.repository.Package repoPkg = 
				(org.modeldriven.fuml.repository.Package)delegate.getElementById(pkg.getXmiId());
			result.add(new FumlNamespace(repoPkg));
		}		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getNamespaceForUri(java.lang.String)
	 */
	@Override
	public FumlNamespace getNamespaceForUri(String uri) {
		List<Stereotype> list = delegate.getStereotypes(SDONamespace.class);
		FumlNamespace result = null;
		for (Stereotype s : list) {
			SDONamespace namespace = (SDONamespace)s.getDelegate();
			if (namespace.getUri().equals(uri)) {
				if (result != null)
					throw new RepositoryException("multiple namespaces stereotyped as " 
				        + SDONamespace.class.getSimpleName() + " with URI " + uri);
				String packageXmiId = namespace.getBase_Package().getXmiId();
				org.modeldriven.fuml.repository.Package pkg = 
					(org.modeldriven.fuml.repository.Package)delegate.findElementById(packageXmiId);			
				result = new FumlNamespace(pkg);
			}
		}		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getClassifiers(java.lang.String)
	 */
	@Override
	public List<org.plasma.sdo.repository.Classifier> getClassifiers(String uri) {
		List<org.plasma.sdo.repository.Classifier> result = new ArrayList<>();
		List<Stereotype> list = delegate.getStereotypes(SDONamespace.class);
		for (Stereotype s : list) {
			SDONamespace namespace = (SDONamespace)s.getDelegate();
			if (uri.equals(namespace.getUri())) {
				addClassifiers(namespace.getBase_Package(), result);
				return result;
			}
		}
		return result;
	}
	
	private void addClassifiers(fUML.Syntax.Classes.Kernel.Package pkg, 
			List<org.plasma.sdo.repository.Classifier> result) {
		for (PackageableElement pe : pkg.packagedElement) {
			if (pe instanceof fUML.Syntax.Classes.Kernel.Package) {
				addClassifiers((fUML.Syntax.Classes.Kernel.Package)pe, result);
			}
			else if (pe instanceof fUML.Syntax.Classes.Kernel.Class_) {
				org.plasma.sdo.repository.Classifier classifier = this.getClassifierById(pe.getXmiId());
				result.add(classifier);
			}	
		}
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getElementById(java.lang.String)
	 */
	//@Override
	public org.plasma.sdo.repository.Element getElementById(String id) {	
		NamedElement e = (NamedElement)delegate.getElementById(id);
		if (e == null)			
		    throw new RepositoryException("no element defined for id, " + id);
		return new FumlElement<org.modeldriven.fuml.repository.NamedElement>(e);			
	}

	//@Override
	public Class_ getClassById(String id) {
		Element e = (NamedElement)delegate.getElementById(id);
		if (e == null)			
		    throw new RepositoryException("no element defined for id, " + id);
		return new FumlClass_((org.modeldriven.fuml.repository.Class_)e);			
	}

	//@Override
	public Enumeration getEnumerationById(String id) {
		Element e = (NamedElement)delegate.getElementById(id);
		if (e == null)			
		    throw new RepositoryException("no element defined for id, " + id);
		return new FumlEnumeration((org.modeldriven.fuml.repository.Enumeration)e);			
	}

	//@Override
	public EnumerationLiteral getEnumerationLiteralById(String id) {
		Element e = (NamedElement)delegate.getElementById(id);
		if (e == null)			
		    throw new RepositoryException("no element defined for id, " + id);
		return new FumlEnumerationLiteral((org.modeldriven.fuml.repository.EnumerationLiteral)e);			
	}

	//@Override
	public org.plasma.sdo.repository.Classifier getClassifierById(String id) {
		Element e = (NamedElement)delegate.getElementById(id);
		if (e == null)			
		    throw new RepositoryException("no element defined for id, " + id);
		return new FumlClassifier<org.modeldriven.fuml.repository.Classifier>((org.modeldriven.fuml.repository.Classifier)e);			
	}

	//@Override
	public Property getPropertyById(String id) {
		Element e = (NamedElement)delegate.getElementById(id);
		if (e == null)			
		    throw new RepositoryException("no element defined for id, " + id);
		return new FumlProperty((org.modeldriven.fuml.repository.Property)e);			
	}	
	
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getClassifier(java.lang.String)
	 */
	@Override
	public org.plasma.sdo.repository.Classifier getClassifier(String name) {
		Classifier result = delegate.getClassifier(name);
		if (result == null)
			throw new RepositoryException("no classifier defined for qualified name, "
				+ name);
        if (org.modeldriven.fuml.repository.Class_.class.isAssignableFrom(result.getClass()))        	
		    return new FumlClass_((org.modeldriven.fuml.repository.Class_)result);
        else
		    return new FumlClassifier<org.modeldriven.fuml.repository.Classifier>(result);
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#findClassifier(java.lang.String)
	 */
	@Override
	public org.plasma.sdo.repository.Classifier findClassifier(String name) {
		org.modeldriven.fuml.repository.Classifier result = delegate.findClassifier(name);
		if (result != null)
	        if (org.modeldriven.fuml.repository.Class_.class.isAssignableFrom(result.getClass()))        	
			    return new FumlClass_((org.modeldriven.fuml.repository.Class_)result);
	        else
			    return new FumlClassifier<org.modeldriven.fuml.repository.Classifier>(result);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#findElementById(java.lang.String)
	 */
	//@Override
	public org.plasma.sdo.repository.Element findElementById(String id) {
		NamedElement e = (NamedElement)delegate.findElementById(id);
		if (e != null)
			return new FumlElement<org.modeldriven.fuml.repository.NamedElement>(e);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#findElementByName(java.lang.String)
	 */
	//@Override
	public org.plasma.sdo.repository.Element findElementByName(String name) {
		NamedElement e = (NamedElement)delegate.findElementByName(name);
		if (e != null)
			return new FumlElement<org.modeldriven.fuml.repository.NamedElement>(e);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#findElementByQualifiedName(java.lang.String)
	 */
	//@Override
	public org.plasma.sdo.repository.Element findElementByQualifiedName(String qualifiedName) {
		NamedElement e = (NamedElement)delegate.findElementByQualifiedName(qualifiedName);
		if (e != null)
			return new FumlElement<org.modeldriven.fuml.repository.NamedElement>(e);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getClassifierByName(java.lang.String)
	 */
	//@Override
	public org.plasma.sdo.repository.Classifier getClassifierByName(String name) {
		Classifier result = delegate.getClassifierByName(name);
		if (result == null)
			throw new RepositoryException("no classifier defined for qualified name, "
				+ name);
        if (org.modeldriven.fuml.repository.Class_.class.isAssignableFrom(result.getClass()))        	
		    return new FumlClass_((org.modeldriven.fuml.repository.Class_)result);
        else
		    return new FumlClassifier<org.modeldriven.fuml.repository.Classifier>(result);
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getClassifierByQualifiedName(java.lang.String)
	 */
	//@Override
	public org.plasma.sdo.repository.Classifier getClassifierByQualifiedName(String qualifiedName) {
		Classifier result = delegate.getClassifierByQualifiedName(qualifiedName);
		if (result == null)
			throw new RepositoryException("no classifier defined for qualified name, "
				+ qualifiedName);
        if (org.modeldriven.fuml.repository.Class_.class.isAssignableFrom(result.getClass()))        	
		    return new FumlClass_((org.modeldriven.fuml.repository.Class_)result);
        else
		    return new FumlClassifier<org.modeldriven.fuml.repository.Classifier>(result);
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getDefaultUMLNamespaceURI()
	 */
	//@Override
	public String getDefaultUMLNamespaceURI() {
		return delegate.getDefaultUMLNamespaceURI();
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getElementByName(java.lang.String)
	 */
	//@Override
	public org.plasma.sdo.repository.Element getElementByName(String name) {
		NamedElement e = (NamedElement)delegate.getElementByName(name);
		if (e == null)			
		    throw new RepositoryException("no element defined for name, "
						+ name);
		return new FumlElement<org.modeldriven.fuml.repository.NamedElement>(e);
	}

	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getElementByQualifiedName(java.lang.String)
	 */
	//@Override
	public org.plasma.sdo.repository.Element getElementByQualifiedName(String qualifiedName) {
		NamedElement e = (NamedElement)delegate.getElementByQualifiedName(qualifiedName);
		if (e == null)			
		    throw new RepositoryException("no element defined for qualified name, "
						+ qualifiedName);
		return new FumlElement<org.modeldriven.fuml.repository.NamedElement>(e);
	}
	/* (non-Javadoc)
	 * @see org.plasma.sdo.repository.fuml.Repository#getRelationCache()
	 */
	@Override
	public RelationCache getRelationCache() {
		return this.relationCache;
	}
	
	// package internal
	List<Stereotype> getStereotypes(org.modeldriven.fuml.repository.Element element) {
	    return delegate.getStereotypes(element);
    }

	// package internal
	List<Classifier> getSpecializations(Classifier classifier) {
		return delegate.getSpecializations(classifier);
	}

	
}
