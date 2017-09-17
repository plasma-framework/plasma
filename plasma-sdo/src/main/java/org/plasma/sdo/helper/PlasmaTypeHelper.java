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

package org.plasma.sdo.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaDataObjectConstants;
import org.plasma.sdo.PlasmaDataObjectException;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.core.CoreType;
import org.plasma.sdo.repository.Classifier;
import org.plasma.sdo.repository.InvalidClassifierNameException;
import org.plasma.sdo.repository.PlasmaRepository;
import org.plasma.sdo.repository.Stereotype;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.TypeHelper;

/**
 * Look up a Type given the uri and typeName or interfaceClass. SDO Types are
 * available through the getType(uri, typeName) method. Defines Types from
 * DataObjects.
 */
public class PlasmaTypeHelper implements TypeHelper {

  private static Log log = LogFactory.getLog(PlasmaTypeHelper.class);
  static public volatile PlasmaTypeHelper INSTANCE = initializeInstance();

  private Map<String, PlasmaType> namespaceQualifiedNameToTypeMap = new HashMap<String, PlasmaType>();
  private Map<String, List<PlasmaType>> namespaceToTypesMap = new HashMap<String, List<PlasmaType>>();
  private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

  private PlasmaTypeHelper() {
  }

  private static synchronized PlasmaTypeHelper initializeInstance() {
    if (INSTANCE == null)
      INSTANCE = new PlasmaTypeHelper();
    return INSTANCE;
  }

  /**
   * Define the DataObject as a Type. The Type is available through TypeHelper
   * and DataGraph getType() methods.
   * 
   * @param type
   *          the DataObject representing the Type.
   * @return the defined Type.
   * @throws IllegalArgumentException
   *           if the Type could not be defined.
   */
  public Type define(DataObject type) {
    throw new RuntimeException("not yet supported");
  }

  /**
   * Define the list of DataObjects as Types. The Types are available through
   * TypeHelper and DataGraph getType() methods.
   * 
   * @param types
   *          a List of DataObjects representing the Types.
   * @return the defined Types.
   * @throws IllegalArgumentException
   *           if the Types could not be defined.
   */
  public List define(List types) {
    // TODO Auto-generated method stub
    throw new RuntimeException("not yet supported");
  }

  /**
   * Define the DataObject as a Property for setting open content. The
   * containing Type of the open content property is not specified by SDO. If
   * the specified uri is not null the defined property is accessible through
   * TypeHelper.getOpenContentProperty(uri, propertyName). If a null uri is
   * specified, the location and management of the open content property is not
   * specified by SDO.
   * 
   * @param uri
   *          the namespace URI of the open content Property or null.
   * @return the defined open content Property.
   * @throws IllegalArgumentException
   *           if the Property could not be defined.
   */
  public Property defineOpenContentProperty(String uri, DataObject property) {
    // TODO Auto-generated method stub
    throw new RuntimeException("not yet supported");
  }

  /**
   * Get the open content (global) Property with the specified uri and name, or
   * null if not found.
   * 
   * @param uri
   *          the namespace URI of the open content Property.
   * @param propertyName
   *          the name of the open content Property.
   * @return the global Property.
   */
  public Property getOpenContentProperty(String uri, String propertyName) {
    // TODO Auto-generated method stub
    throw new RuntimeException("not yet supported");
  }

  /**
   * Return the Type specified by typeName with the given uri, or null if the
   * underlying classifier is not found.
   * 
   * @param uri
   *          the logical uri of the Type - See Type.getURI() or
   *          PlasmaType.getURIPhysicalName();
   * @param typeName
   *          The logical name of the Type - Type.getName() or
   *          PlasmaType.getPhysicalName();
   * @return the Type specified by typeName with the given uri, or null if not
   *         found.
   */
  public Type getType(String uri, String typeName) {
    String qualifiedName = uri + "#" + typeName;
    // rwl.readLock().lock();
    PlasmaType result = namespaceQualifiedNameToTypeMap.get(qualifiedName);
    if (result == null) {
      // if (log.isDebugEnabled())
      // log.debug("cache miss: " + qualifiedName);
      // rwl.readLock().unlock();
      rwl.writeLock().lock();
      try {
        result = namespaceQualifiedNameToTypeMap.get(qualifiedName);
        if (result == null) {
          try {
            result = new CoreType(uri, typeName);
          } catch (InvalidClassifierNameException e) {
            log.warn(e.getMessage(), e);
            return null;
          }

          namespaceQualifiedNameToTypeMap.put(qualifiedName, result);
          // New type could have a name based on config type-binding
          // also map by new logical name if exists
          if (!result.getName().equals(typeName)) {
            qualifiedName = uri + "#" + result.getName();
            namespaceQualifiedNameToTypeMap.put(qualifiedName, result);
          }

          String qualifiedPhysicalName = getQualifiedPhysicalName(result);
          if (qualifiedPhysicalName != null)
            namespaceQualifiedNameToTypeMap.put(qualifiedPhysicalName, result);

          List<PlasmaType> namespaceTypes = namespaceToTypesMap.get(uri);
          if (namespaceTypes == null)
            namespaceTypes = new ArrayList<PlasmaType>();
          namespaceTypes.add(result);

          // rwl.readLock().lock(); // downgrade to read
        }
      } finally {
        rwl.writeLock().unlock(); // Unlock write, still hold read
      }
    }
    return result;
  }

  /**
   * Return the Type specified by physical typeName with the given physical uri,
   * or null if not found.
   * 
   * @param uriPhisicalName
   *          the physical uri of the Type - See Type.getURI() or
   *          PlasmaType.getURIPhysicalName();
   * @param typeName
   *          The logical or physical name of the Type - Type.getName() or
   *          PlasmaType.getPhysicalName();
   * @return the Type specified by typeName with the given uri, or null if not
   *         found.
   */
  public Type findTypeByPhysicalName(String uriPhisicalName, String typePhysicalName) {
    String qualifiedPhysicalName = uriPhisicalName + "#" + typePhysicalName;
    rwl.readLock().lock();
    try {
      return namespaceQualifiedNameToTypeMap.get(qualifiedPhysicalName);
    } finally {
      rwl.readLock().unlock(); // Unlock read
    }
  }

  /**
   * Releases resources associated with the given type and removes it from
   * cache.
   * 
   * @param type
   *          the type
   */
  public void releaseType(Type type) {
    releaseType(type.getURI(), type.getName());
  }

  /**
   * Releases resources associated with the given type and removes it from
   * cache.
   * 
   * @param uri
   *          The logical uri of the Type - type.getURI();
   * @param typeName
   *          The logical name of the Type - type.getName();
   */
  public void releaseType(String uri, String typeName) {
    String qualifiedName = uri + "#" + typeName;
    if (log.isDebugEnabled())
      log.debug("releasing type: " + qualifiedName);
    rwl.writeLock().lock();
    try {
      PlasmaType removed = namespaceQualifiedNameToTypeMap.remove(qualifiedName);
      if (removed != null) {
        String physicalName = getQualifiedPhysicalName(removed);
        if (physicalName != null)
          namespaceQualifiedNameToTypeMap.remove(physicalName);
        List<PlasmaType> namespaceTypes = namespaceToTypesMap.get(uri);
        if (namespaceTypes != null) {
          if (!namespaceTypes.remove(removed))
            log.warn("could not remove type from namespace mapping, " + qualifiedName);
        } else
          log.warn("could not remove type from namespace mapping, " + qualifiedName);
      } else
        log.warn("could not remove type, " + qualifiedName);
    } finally {
      rwl.writeLock().unlock();
    }
  }

  /**
   * Return the Type for this interfaceClass or null if not found. The
   * interfaceClass is the Java interface that follows the SDO code generation
   * pattern.
   * 
   * @param interfaceClass
   *          is the interface for the DataObject's Type -
   *          type.getInstanceClass();
   * @return the Type for this interfaceClass or null if not found.
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  public Type getType(Class interfaceClass) {
    String uri = null;
    try {
      Field uriField = interfaceClass
          .getDeclaredField(PlasmaDataObjectConstants.NAMESPACE_URI_FIELD_NAME);
      uri = (String) uriField.get(null);
    } catch (SecurityException e) {
      throw new PlasmaDataObjectException(e);
    } catch (NoSuchFieldException e) {
      throw new PlasmaDataObjectException(e);
    } catch (IllegalArgumentException e) {
      throw new PlasmaDataObjectException(e);
    } catch (IllegalAccessException e) {
      throw new PlasmaDataObjectException(e);
    }
    return getType(uri, interfaceClass.getSimpleName());
  }

  /**
   * Return the Type instances specified by the given uri.
   * 
   * @param uri
   *          The namespace uri;
   * @return the Type instances specified by the given uri.
   */
  public List<Type> getTypes(String uri) {
    List<Type> result = new ArrayList<Type>();
    List<Classifier> list = PlasmaRepository.getInstance().getClassifiers(uri);
    for (Classifier classifier : list) {
      if (classifier instanceof Stereotype) {
        log.warn("ignoring stereotype: " + classifier.getName());
        continue;
      }

      Type type = getType(uri, classifier.getName());
      result.add(type);
    }
    return result;
  }

  private String getQualifiedName(PlasmaType type) {
    return type.getURI() + "#" + type.getName();
  }

  /**
   * Returns the qualified physical name for the given type or null if either
   * the namespace physical name OR type physical name are not found.
   * 
   * @param type
   *          the type.
   * @return the qualified physical name for the given type or null if either
   *         the namespace physical name OR type physical name are not found.
   */
  private String getQualifiedPhysicalName(PlasmaType type) {
    String uriPhysicalName = type.getURIPhysicalName();
    if (uriPhysicalName == null)
      return null;

    String physicalName = type.getPhysicalName();
    if (physicalName == null)
      return null;
    return uriPhysicalName + "#" + physicalName;
  }

}
