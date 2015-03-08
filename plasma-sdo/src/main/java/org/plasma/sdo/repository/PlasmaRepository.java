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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Element;
import org.modeldriven.fuml.repository.Extension;
import org.modeldriven.fuml.repository.Package;
import org.modeldriven.fuml.repository.RepositoryMapping;
import org.modeldriven.fuml.repository.RepositorylException;
import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.config.Artifact;
import org.plasma.config.PlasmaConfig;
import org.plasma.sdo.profile.SDONamespace;

import fUML.Syntax.Classes.Kernel.PackageableElement;

public class PlasmaRepository implements Repository {

    private static Log log = LogFactory.getLog(PlasmaRepository.class);
    private static Repository instance;
    private static org.modeldriven.fuml.repository.Repository delegate;
    private RelationCache relationCache = new RelationCache();
    
    private PlasmaRepository() {}
    
    public static Repository getInstance() throws RepositoryException {
        if (instance == null)
            initializeInstance();
        return instance;
    }

    private static synchronized void initializeInstance() throws RepositorylException {
        if (instance == null) {
            
            for (Artifact artifact : PlasmaConfig.getInstance().getRepository().getArtifacts()) {
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
            
            delegate = org.modeldriven.fuml.repository.Repository.INSTANCE;
            instance = new PlasmaRepository();
        }
    }

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

	@Override
	public List<Namespace> getAllNamespaces() {
		List<Namespace> result = new ArrayList<Namespace>();
		List<Stereotype> list = delegate.getStereotypes(SDONamespace.class);
		for (Stereotype s : list) {			
			SDONamespace namespace = (SDONamespace)s.getDelegate();			
			fUML.Syntax.Classes.Kernel.Package pkg = namespace.getBase_Package();
			org.modeldriven.fuml.repository.Package repoPkg = 
				(org.modeldriven.fuml.repository.Package)delegate.getElementById(pkg.getXmiId());
			result.add(new Namespace(repoPkg));
		}		
		return result;
	}
	
	@Override
	public Namespace getNamespaceForUri(String uri) {
		List<Stereotype> list = delegate.getStereotypes(SDONamespace.class);
		Namespace result = null;
		for (Stereotype s : list) {
			SDONamespace namespace = (SDONamespace)s.getDelegate();
			if (namespace.getUri().equals(uri)) {
				if (result != null)
					throw new RepositoryException("multiple namespaces stereotyped as " 
				        + SDONamespace.class.getSimpleName() + " with URI " + uri);
				String packageXmiId = namespace.getBase_Package().getXmiId();
				org.modeldriven.fuml.repository.Package pkg = 
					(org.modeldriven.fuml.repository.Package)delegate.findElementById(packageXmiId);			
				result = new Namespace(pkg);
			}
		}		
		return result;
	}

	/**
     * Returns a list of classifiers contained by the package, as well as all 
     * classifiers within its contained packages, linked/stereotyped through an SDONamespace
     * stereotype where the stereotype uri matches the given uri. 
     * @see org.plasma.sdo.profile.SDONamespace
     * @param uri the SDO namespace URI
     * @return a list of classifiers contained by the package, as wells as all 
     * classifiers within its contained packages, linked through an SDONamespace
     * stereotype where the uri which matches the given uri.  
     */
	public List<Classifier> getClassifiers(String uri) {
		List<Classifier> result = new ArrayList<Classifier>();
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
			List<Classifier> result) {
		for (PackageableElement pe : pkg.packagedElement) {
			if (pe instanceof fUML.Syntax.Classes.Kernel.Package) {
				addClassifiers((fUML.Syntax.Classes.Kernel.Package)pe, result);
			}
			else if (pe instanceof fUML.Syntax.Classes.Kernel.Class_) {
				Classifier classifier = (Classifier)this.getElementById(pe.getXmiId());
				result.add(classifier);
			}	
		}
	}
	
	public Element getElementById(String id) {
		return delegate.getElementById(id);
	}

	public Classifier getClassifier(String name) {
		Classifier result = delegate.getClassifier(name);
		if (result == null)
			throw new RepositoryException("no classifier defined for qualified name, "
				+ name);
		return result;
	}
	
    public List<Stereotype> getStereotypes(Element element) {
    	return delegate.getStereotypes(element);
    }

	public Classifier findClassifier(String name) {
		return delegate.findClassifier(name);
	}

	public Element findElementById(String id) {
		return delegate.findElementById(id);
	}

	public Element findElementByName(String name) {
		return delegate.findElementByName(name);
	}

	public Element findElementByQualifiedName(String qualifiedName) {
		return delegate.findElementByQualifiedName(qualifiedName);
	}

	public String findJavaPackageNamePackageForClass(Classifier classifier) {
		throw new RepositoryException("not implemented");
	}

	public List<Stereotype> getAllStereotypes() {
		return delegate.getAllStereotypes();
	}

	public Classifier getClassifierByName(String name) {
		return delegate.getClassifierByName(name);
	}

	public Classifier getClassifierByQualifiedName(String qualifiedName) {
		return delegate.getClassifierByQualifiedName(qualifiedName);
	}

	public String getDefaultUMLNamespaceURI() {
		return delegate.getDefaultUMLNamespaceURI();
	}

	public Element getElementByName(String name) {
		return delegate.getElementByName(name);
	}

	public Element getElementByQualifiedName(String qualifiedName) {
		return delegate.getElementByQualifiedName(qualifiedName);
	}

	public int getElementCount(
			Class<? extends fUML.Syntax.Classes.Kernel.Element> clss) {
		throw new RepositoryException("not implemented");
	}

	public String[] getElementNames(
			Class<? extends fUML.Syntax.Classes.Kernel.Element> clss) {
		throw new RepositoryException("not implemented");
	}

	public List<Extension> getExtensions(Element element) {
		return delegate.getExtensions(element);
	}

	public String getJavaPackageNameForClass(Classifier classifier) {
		throw new RepositoryException("not implemented");
	}

	public RepositoryMapping getMapping() {
		throw new RepositoryException("not implemented");
	}

	public Package getPackageByQualifiedName(String qualifiedName) {
		throw new RepositoryException("not implemented");
	}

	public List<Stereotype> getStereotypes(Class<?> clss) {
		return delegate.getStereotypes(clss);
	}

	public boolean isIgnoredClassifier(Classifier classifier) {
		throw new RepositoryException("not implemented");
	}

	public void loadClass(Class_ clss) {
		throw new RepositoryException("not implemented");
	}
   
	public RelationCache getRelationCache() {
		return this.relationCache;
	}

	@Override
	public List<Classifier> getSpecializations(Classifier classifier) {
		return delegate.getSpecializations(classifier);
	}
}
