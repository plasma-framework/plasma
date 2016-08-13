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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.repository.RepositorylException;
import org.plasma.sdo.repository.fuml.FumlRepository;

public class PlasmaRepository implements Repository {
    private static Log log = LogFactory.getLog(PlasmaRepository.class);
    private static volatile Repository instance;
    private Repository delegate;
    
    private PlasmaRepository() {
        delegate = FumlRepository.getInstance();
    }
    
    public static Repository getInstance() throws RepositoryException {
        if (instance == null)
            initializeInstance();
        return instance;
    }

    private static synchronized void initializeInstance() throws RepositorylException {
        if (instance == null) {
            instance = new PlasmaRepository();
        }
    }

	public List<String> getAllNamespaceUris() {
		return delegate.getAllNamespaceUris();
	}

	public List<Namespace> getAllNamespaces() {
		return delegate.getAllNamespaces();
	}

	public Namespace getNamespaceForUri(String uri) {
		return delegate.getNamespaceForUri(uri);
	}

	public List<Classifier> getClassifiers(String uri) {
		return delegate.getClassifiers(uri);
	}

	public Element getElementById(String id) {
		return delegate.getElementById(id);
	}

	public Classifier getClassifier(String name) {
		return delegate.getClassifier(name);
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

	public RelationCache getRelationCache() {
		return delegate.getRelationCache();
	}

	public Class_ getClassById(String id) {
		return delegate.getClassById(id);
	}

	public Enumeration getEnumerationById(String id) {
		return delegate.getEnumerationById(id);
	}

	public EnumerationLiteral getEnumerationLiteralById(String id) {
		return delegate.getEnumerationLiteralById(id);
	}

	public Classifier getClassifierById(String id) {
		return delegate.getClassifierById(id);
	}

	public Property getPropertyById(String id) {
		return delegate.getPropertyById(id);
	}

}
