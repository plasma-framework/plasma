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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.repository.RepositorylException;
import org.plasma.config.PlasmaConfig;
import org.plasma.sdo.repository.fuml.FumlRepository;

/**
 * A repository implementation with a dynamic set of "delegate" repositories, which are searched
 * in order for results. 
 * FIXME: make all this configurable. 
 */
public class PlasmaRepository implements Repository {
    private static Log log = LogFactory.getLog(PlasmaRepository.class);
    private static volatile Repository instance;
    private List<Repository> delegates = new ArrayList<>();
    
    private PlasmaRepository() {
        for (org.plasma.config.Repository repoConfig : PlasmaConfig.getInstance().getRepositories()) {
        	if (repoConfig.getRepositoryClassName() != null) {
        		try {
					Class<?> interfaceImplClass = Class.forName(repoConfig.getRepositoryClassName());
					Method method = interfaceImplClass.getMethod("getInstance");
					if (log.isDebugEnabled())
						log.debug("initializing repository for class: " + repoConfig.getRepositoryClassName());
					Repository repo = (Repository)method.invoke(null, new Object[] {});
					this.delegates.add(repo);
				} catch (ClassNotFoundException e) {
					throw new RepositoryException(e);
				} catch (NoSuchMethodException e) {
					throw new RepositoryException(e);
				} catch (SecurityException e) {
					throw new RepositoryException(e);
				} catch (IllegalAccessException e) {
					throw new RepositoryException(e);
				} catch (IllegalArgumentException e) {
					throw new RepositoryException(e);
				} catch (InvocationTargetException e) {
					throw new RepositoryException(e);
				}
        	}
        }
        if (this.delegates.size() == 0)
    	    this.delegates.add(FumlRepository.getInstance());
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
    
    //* FIXME: make all this configurable. 
    public synchronized void addRepository(Repository repo) {
    	this.delegates.add(repo);
    }

	public List<String> getAllNamespaceUris() {
		List<String> results = new ArrayList<String>();
		for (Repository repo : this.delegates) {
			results.addAll(repo.getAllNamespaceUris());
		}
		return results;
	}

	public List<Namespace> getAllNamespaces() {
		List<Namespace> results = new ArrayList<Namespace>();
		for (Repository repo : this.delegates) {
			results.addAll(repo.getAllNamespaces());
		}
		return results;
	}

	public Namespace getNamespaceForUri(String uri) {
		Namespace result = null;
		for (Repository repo : this.delegates) {
			result = repo.getNamespaceForUri(uri);
			if (result != null)
				return result;
		}
		return null;
	}

	public List<Classifier> getClassifiers(String uri) {
		List<Classifier> results = new ArrayList<Classifier>();
		for (Repository repo : this.delegates) {
			results.addAll(repo.getClassifiers(uri));
		}
		return results;
	}

	public Classifier getClassifier(String name) {
		Classifier result = null;
		for (Repository repo : this.delegates) {
			result = repo.findClassifier(name);
			if (result != null)
				return result;
		}
		throw new RepositorylException("no classifier found for name, " + name);
	}

	public Classifier findClassifier(String name) {
		Classifier result = null;
		for (Repository repo : this.delegates) {
			result = repo.findClassifier(name);
			if (result != null)
				return result;
		}
		return null;
	}
	
	public RelationCache getRelationCache() {
		RelationCache result = null;
		for (Repository repo : this.delegates) {
			result = repo.getRelationCache();
			if (result != null)
				return result;
		}
		return null;
	}

	public Class_ getClassById(String id) {
		Class_ result = null;
		for (Repository repo : this.delegates) {
			result = repo.getClassById(id);
			if (result != null)
				return result;
		}
		return null;
	}


}
