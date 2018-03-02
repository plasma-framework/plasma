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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.runtime.Namespace;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.core.CoreDataGraph;
import org.plasma.sdo.core.CoreDataObject;

import commonj.sdo.DataObject;
import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;

public class PlasmaDataFactory implements DataFactory {

  private static Log log = LogFactory.getLog(PlasmaDataFactory.class);
  static public volatile PlasmaDataFactory INSTANCE = initializeInstance();
  private Map<String, Class<?>> interfaceImplClassMap = new HashMap<>();

  private PlasmaDataFactory() {
  }

  public static PlasmaDataFactory instance() {
    if (INSTANCE == null)
      initializeInstance();
    return INSTANCE;
  }

  private static synchronized PlasmaDataFactory initializeInstance() {
    if (INSTANCE == null)
      INSTANCE = new PlasmaDataFactory();
    return INSTANCE;
  }

  public PlasmaDataGraph createDataGraph() {
    return new CoreDataGraph();
  }

  public DataObject create(String uri, String typeName) {
    Type type = PlasmaTypeHelper.INSTANCE.getType(uri, typeName);
    String packageName = PlasmaRuntime.getInstance().getSDOImplementationPackageName(uri);
    if (packageName != null) {
      return this.create(type);
    } else
      return new CoreDataObject(type);
  }

  public DataObject create(Type type) {
    if (type.isAbstract())
      throw new IllegalArgumentException("attempt to create an abstract type '" + type.getURI()
          + "#" + type.getName() + "'");
    if (type.isDataType())
      throw new IllegalArgumentException("attempt to create a type which is a datatype '"
          + type.getURI() + "#" + type.getName() + "'");
    String qualifiedName = findPackageQualifiedClassName(type);
    Class<?> interfaceImplClass = null;
    if (qualifiedName != null) {
      interfaceImplClass = interfaceImplClassMap.get(qualifiedName);
      if (interfaceImplClass == null) {
        try {
          interfaceImplClass = Class.forName(qualifiedName);
          interfaceImplClassMap.put(qualifiedName, interfaceImplClass);
        } catch (ClassNotFoundException e) {
          interfaceImplClass = findBaseInterfaceClass(type);
          if (interfaceImplClass == null) {
            if (log.isDebugEnabled())
              log.debug("no interface class found for qualified name '" + qualifiedName
                  + "' - using generic DataObject");
            interfaceImplClass = CoreDataObject.class;
            interfaceImplClassMap.put(qualifiedName, interfaceImplClass);
          }
        }
      }
    } else {
      interfaceImplClass = findBaseInterfaceClass(type);
      if (interfaceImplClass == null) {
        if (log.isDebugEnabled())
          log.debug("no interface class found for qualified name '" + qualifiedName
              + "' - using generic DataObject");
        interfaceImplClass = CoreDataObject.class;
        interfaceImplClassMap.put(qualifiedName, interfaceImplClass);
      }
    }
    return this.createDataObject(interfaceImplClass, type);
  }

  /**
   * Traverses the non-abstract ancestry for an interface class which can be
   * instantiated, ignoring instances of multiple inheritance in the ancestry.
   * 
   * @param type
   *          the type
   * @return the interface class or null if none found
   */
  private Class<?> findBaseInterfaceClass(Type type) {
    if (type.getBaseTypes().size() == 1) {
      Type baseType = type.getBaseTypes().get(0);
      if (!baseType.isAbstract()) {
        String qualifiedName = findPackageQualifiedClassName(baseType);
        if (qualifiedName != null) {
          Class<?> interfaceImplClass = this.interfaceImplClassMap.get(qualifiedName);
          if (interfaceImplClass != null) {
            return interfaceImplClass;
          } else {
            try {
              interfaceImplClass = Class.forName(qualifiedName);
              this.interfaceImplClassMap.put(qualifiedName, interfaceImplClass);
              return interfaceImplClass;
            } catch (ClassNotFoundException e) {
              // keep searching
              return findBaseInterfaceClass(baseType);
            }
          }
        } else { // keep searching
          return findBaseInterfaceClass(baseType);
        }
      }
    }
    return null;
  }

  /**
   * Returns the qualified class name if the given type has a physical package
   * defined, otherwise returns null.
   * 
   * @param type
   *          the type
   * @return the qualified class name if the given type has a physical package
   *         defined, otherwise returns null.
   */
  private String findPackageQualifiedClassName(Type type) {
    String packageName = PlasmaRuntime.getInstance().getSDOImplementationPackageName(type.getURI());
    if (packageName != null) {
      String className = PlasmaRuntime.getInstance().getSDOImplementationClassName(type.getURI(),
          type.getName());
      StringBuilder buf = new StringBuilder();
      buf.append(packageName);
      buf.append(".");
      buf.append(className);
      return buf.toString();
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  public DataObject create(Class interfaceClass, Type type) {
    CoreDataObject result = null;
    Namespace sdoNamespace = PlasmaRuntime.getInstance().getSDONamespaceByInterfacePackage(
        interfaceClass.getPackage().getName());

    String packageName = PlasmaRuntime.getInstance().getSDOImplementationPackageName(
        sdoNamespace.getUri());
    String className = PlasmaRuntime.getInstance().getSDOImplementationClassName(
        sdoNamespace.getUri(), interfaceClass.getSimpleName());
    String qualifiedName = packageName + "." + className;

    try {
      Class<?> interfaceImplClass = interfaceImplClassMap.get(qualifiedName);
      if (interfaceImplClass == null) {
        interfaceImplClass = Class.forName(qualifiedName); // expensive under
                                                           // load
        interfaceImplClassMap.put(qualifiedName, interfaceImplClass);
        log.warn("cache miss: " + interfaceImplClass.getName());
      }
      result = createDataObject(interfaceImplClass, type);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  @SuppressWarnings("rawtypes")
  public DataObject create(Class interfaceClass) {
    CoreDataObject result = null;
    Namespace sdoNamespace = PlasmaRuntime.getInstance().getSDONamespaceByInterfacePackage(
        interfaceClass.getPackage().getName());

    String packageName = PlasmaRuntime.getInstance().getSDOImplementationPackageName(
        sdoNamespace.getUri());
    String className = PlasmaRuntime.getInstance().getSDOImplementationClassName(
        sdoNamespace.getUri(), interfaceClass.getSimpleName());
    String qualifiedName = packageName + "." + className;

    Class<?>[] types = new Class<?>[0];
    Object[] args = new Object[0];
    try {
      Class<?> interfaceImplClass = interfaceImplClassMap.get(qualifiedName);
      if (interfaceImplClass == null) {
        interfaceImplClass = Class.forName(qualifiedName); // expensive under
                                                           // load
        interfaceImplClassMap.put(qualifiedName, interfaceImplClass);
        log.warn("cache miss: " + interfaceImplClass.getName());
      }
      result = createDataObject(interfaceImplClass, types, args);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  private CoreDataObject createDataObject(Class<?> interfaceClass, Type type) {
    Class<?>[] types = { Type.class };
    Object[] args = { type };
    CoreDataObject result = createDataObject(interfaceClass, types, args);
    return result;
  }

  private CoreDataObject createDataObject(Class<?> interfaceClass, Type type, UUID uuid) {
    Class<?>[] types = { Type.class, UUID.class };
    Object[] args = { type, uuid };
    CoreDataObject result = createDataObject(interfaceClass, types, args);
    return result;
  }

  private CoreDataObject createDataObject(Class<?> interfaceClass, Class<?>[] types, Object[] args) {
    CoreDataObject result = null;
    try {
      Constructor<?> constructor = interfaceClass.getConstructor(types);
      result = (CoreDataObject) constructor.newInstance(args);
    } catch (SecurityException e) {
      throw new PlasmaRuntimeException(e);
    } catch (NoSuchMethodException e) {
      throw new PlasmaRuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new PlasmaRuntimeException(e);
    } catch (InstantiationException e) {
      throw new PlasmaRuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new PlasmaRuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new PlasmaRuntimeException(e);
    }
    return result;
  }
}
