/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.sdo.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.sdo.repository.fuml.FumlRepository;

/**
 * A repository implementation with a dynamic set of "delegate" repositories,
 * which are searched in order for results. FIXME: make all this configurable.
 */
public class PlasmaRepository implements Repository {
  private static Log log = LogFactory.getLog(PlasmaRepository.class);
  private static volatile Repository instance;
  private List<Repository> delegates = new ArrayList<>();

  private PlasmaRepository() {
    for (org.plasma.runtime.Repository repoConfig : PlasmaRuntime.getInstance().getRepositories()) {
      if (repoConfig.getRepositoryClassName() != null) {
        try {
          Class<?> interfaceImplClass = Class.forName(repoConfig.getRepositoryClassName());
          Method method = interfaceImplClass.getMethod("getInstance");
          if (log.isDebugEnabled())
            log.debug("initializing repository for class: " + repoConfig.getRepositoryClassName());
          Repository repo = (Repository) method.invoke(null, new Object[] {});
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

  private static synchronized void initializeInstance() throws RepositoryException {
    if (instance == null) {
      instance = new PlasmaRepository();
    }
  }

  // * FIXME: make all this configurable.
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

  public List<Classifier> getEnumerations(String uri) {
    List<Classifier> results = new ArrayList<Classifier>();
    for (Repository repo : this.delegates) {
      results.addAll(repo.getEnumerations(uri));
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
    throw new RepositoryException("no classifier found for name, " + name);
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
