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

package org.plasma.metamodel.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.exception.ProvisioningException;
import org.plasma.metamodel.Alias;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.EnumerationRef;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;

public class ModelAdapter implements ProvisioningModel {
  private static Log log = LogFactory.getLog(ModelAdapter.class);

  private Model model;
  private Map<String, TypeAdapter> typeMap = new HashMap<String, TypeAdapter>();
  private Map<String, TypeAdapter> physicalNameTypeMap = new HashMap<String, TypeAdapter>();
  private List<Package> leafPackages = new ArrayList<Package>();
  private List<Package> allPackages = new ArrayList<Package>();
  private Map<TypeAdapter, Package> packageTypeMap = new HashMap<TypeAdapter, Package>();

  @SuppressWarnings("unused")
  private ModelAdapter() {
  }

  public ModelAdapter(Model model) {
    this.model = model;
    construct();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.provisioning.adapter.MetamodelAdapter#getModel()
   */
  @Override
  public Model getModel() {
    return model;
  }

  @Override
  public List<Package> getPackages() {
    return Collections.unmodifiableList(this.allPackages);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.provisioning.adapter.MetamodelAdapter#getLeafPackages()
   */
  @Override
  public List<Package> getLeafPackages() {
    return Collections.unmodifiableList(this.leafPackages);
  }

  @Override
  public Package getPackage(TypeAdapter type) {
    return this.packageTypeMap.get(type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.provisioning.adapter.MetamodelAdapter#getTypes()
   */
  @Override
  public Collection<TypeAdapter> getTypes() {
    return typeMap.values();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.provisioning.adapter.MetamodelAdapter#getTypesArray()
   */
  @Override
  public TypeAdapter[] getTypesArray() {
    TypeAdapter[] result = new TypeAdapter[typeMap.size()];
    typeMap.values().toArray(result);
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.provisioning.adapter.MetamodelAdapter#getTypesArray()
   */
  @Override
  public Map<String, TypeAdapter> getTypeMap() {
    return Collections.unmodifiableMap(typeMap);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.plasma.provisioning.adapter.MetamodelAdapter#findType(java.lang.String)
   */
  @Override
  public TypeAdapter findType(String key) {
    TypeAdapter result = typeMap.get(key);
    return result;
  }

  private void findPackages(Package parent, List<Package> packages) {
    packages.add(parent);
    for (Package childPkg : parent.getPackages()) {
      findPackages(childPkg, packages);
    }
  }

  private void construct() {
    if (log.isDebugEnabled())
      log.debug("constructing...");

    findPackages(this.model, this.allPackages);

    for (Package pkg : this.allPackages)
      if (pkg.getPackages().size() == 0)
        this.leafPackages.add(pkg);

    Map<String, Package> packageMap = new HashMap<>();
    for (Package pkg : this.leafPackages) {
      String key = pkg.getName();
      if (packageMap.containsKey(key))
        throw new PackageNameCollisionException("detected multiple (leaf) packages named '" + key
            + "' within the same provisioning context");
      packageMap.put(key, pkg);
    }
    Map<String, Package> packagePhysicalMap = new HashMap<>();
    for (Package pkg : this.leafPackages) {
      if (pkg.getAlias() != null && pkg.getAlias().getPhysicalName() != null) {
        String physicalName = pkg.getAlias().getPhysicalName();
        String key = physicalName;
        if (packagePhysicalMap.containsKey(key))
          throw new PackageNameCollisionException(
              "detected multiple (leaf) packages with physical name '" + key
                  + "' within the same provisioning context");
        packagePhysicalMap.put(key, pkg);
      }
    }

    for (Package pkg : this.allPackages)
      mapEnumerations(pkg);

    for (Package pkg : this.allPackages)
      mapClasses(pkg);

    for (TypeAdapter adapter : typeMap.values()) {
      if (adapter.getType() instanceof Class) {
        if (log.isDebugEnabled())
          log.debug("constructing class: " + adapter.getKey());
        construct(adapter, null);
      }
    }

    for (TypeAdapter adapter : typeMap.values()) {
      if (adapter.getType() instanceof Class) {
        for (ClassRef baseClassRef : ((Class) adapter.getType()).getSuperClasses()) {
          String key = baseClassRef.getUri() + "#" + baseClassRef.getName();
          TypeAdapter baseAdapter = typeMap.get(key);
          if (baseAdapter == null)
            throw new IllegalStateException("no mapping found for base type: " + key);

          if (log.isDebugEnabled())
            log.debug("construct deep: " + adapter.getKey());
          constructDeep(adapter, baseAdapter);
        }
      }
    }
  }

  private void mapEnumerations(Package pkg) {
    if (log.isDebugEnabled())
      log.debug("mapping enumerations for package " + pkg.getUri() + " (" + pkg.getName() + ")");
    for (Enumeration enm : pkg.getEnumerations()) {
      String key = enm.getUri() + "#" + enm.getName();
      if (log.isDebugEnabled())
        log.debug("mapping enumeration: " + key);
      if (typeMap.get(key) != null)
        throw new TypeNameCollisionException("detected multiple types named '" + enm.getName()
            + "' under the same URI '" + enm.getUri() + "'");
      TypeAdapter adapter = new TypeAdapter(enm);
      this.typeMap.put(key, adapter);
      this.packageTypeMap.put(adapter, pkg);
      if (enm.getAlias() != null && enm.getAlias().getPhysicalName() != null) {
        String physicalName = enm.getAlias().getPhysicalName();
        key = enm.getUri() + "#" + physicalName;
        TypeAdapter existing = physicalNameTypeMap.get(key);
        if (existing != null)
          throw new TypeNameCollisionException("detected multiple types [" + existing.getName()
              + "," + enm.getName() + "] with the same types name '" + physicalName
              + "' under the same URI '" + enm.getUri() + "'");
        this.physicalNameTypeMap.put(key, adapter);
      }
    }
  }

  private void mapClasses(Package pkg) {
    for (Class cls : pkg.getClazzs()) {
      String key = cls.getUri() + "#" + cls.getName();
      if (log.isDebugEnabled())
        log.debug("mapping class: " + key);
      if (typeMap.get(key) != null)
        throw new TypeNameCollisionException("detected multiple types named '" + cls.getName()
            + "' under the same URI '" + cls.getUri() + "'");

      TypeAdapter adapter = new TypeAdapter(cls);
      this.typeMap.put(key, adapter);
      this.packageTypeMap.put(adapter, pkg);
      if (log.isDebugEnabled())
        log.debug("map: " + adapter.getKey());

      if (cls.getAlias() != null && cls.getAlias().getPhysicalName() != null) {
        String physicalName = cls.getAlias().getPhysicalName();
        key = cls.getUri() + "#" + physicalName;
        TypeAdapter existing = physicalNameTypeMap.get(key);
        if (existing != null)
          throw new TypeNameCollisionException("detected multiple types [" + existing.getName()
              + "," + cls.getName() + "] with the same physical name '" + physicalName
              + "' under the same URI '" + cls.getUri() + "'");
        this.physicalNameTypeMap.put(key, adapter);
      }
    }
  }

  private void construct(TypeAdapter adapter, TypeAdapter source) {
    for (Property prop : ((Class) adapter.getType()).getProperties()) {
      if (adapter.getDeclaredProperty(prop.getName()) != null)
        throw new PropertyNameCollisionException(
            "detected multiple properties with the same logical name '" + prop.getName()
                + "' defined for class '" + adapter.getKey()
                + "' the set of logical names for a class " + "must be unique");
      adapter.putDeclaredProperty(prop.getName(), prop);
      adapter.putProperty(prop.getName(), prop); // note: all property
                                                 // collection not declared only
      if (prop.getAlias() != null) {
        Alias alias = prop.getAlias();
        if (alias.getPhysicalName() != null && alias.getPhysicalName().trim().length() > 0) {
          String physicalName = alias.getPhysicalName().trim();
          if (adapter.getAliasedProperty(physicalName) != null)
            throw new PropertyNameCollisionException(
                "detected multiple properties with the same physical name '" + alias
                    + "' defined for class '" + adapter.getKey()
                    + "' the set of physical names for a class " + "must be unique");
          adapter.putAliasedProperty(physicalName, prop);
        }
        if (alias.getLocalName() != null && alias.getLocalName().trim().length() > 0) {
          String localName = prop.getAlias().getLocalName().trim();
          if (adapter.getAliasedProperty(localName) != null)
            throw new PropertyNameCollisionException(
                "detected multiple properties with the same local name '" + alias
                    + "' defined for class '" + adapter.getKey()
                    + "' the set of local names for a class " + "must be unique");
          adapter.putAliasedProperty(localName, prop);
        }
      }
    }
  }

  private void constructDeep(TypeAdapter adapter, TypeAdapter baseAdapter) {

    for (Property prop : ((Class) adapter.getType()).getProperties()) {
      validate(adapter, prop);
    }
    // copy base properties into subclass
    for (Property prop : ((Class) baseAdapter.getType()).getProperties()) {
      if (adapter.getProperty(prop.getName()) != null)
        throw new PropertyNameCollisionException(
            "detected multiple properties with the same logical name '" + prop.getName()
                + "' defined for class '" + adapter.getKey() + "' as well as its superclass '"
                + baseAdapter.getKey() + "' - the set of logical names for a class and "
                + "superclasses must be unique");
      validate(baseAdapter, prop);
      adapter.putProperty(prop.getName(), prop); // note: all property
                                                 // collection not declared only
      if (prop.getAlias() != null && prop.getAlias().getPhysicalName() != null
          && prop.getAlias().getPhysicalName().trim().length() > 0) {
        String alias = prop.getAlias().getPhysicalName().trim();
        if (adapter.getAliasedProperty(alias) != null)
          throw new PropertyNameCollisionException(
              "detected multiple properties with the same physical name '" + alias
                  + "' defined for class '" + adapter.getKey() + "' as well as its superclass '"
                  + baseAdapter.getKey() + "' - the set of logical names for a class and "
                  + "superclasses must be unique");
        adapter.putAliasedProperty(alias, prop);
      }
    }

    for (ClassRef baseClassRef : ((Class) baseAdapter.getType()).getSuperClasses()) {
      String key2 = baseClassRef.getUri() + "#" + baseClassRef.getName();
      TypeAdapter baseTypeAdapter = typeMap.get(key2);
      if (baseTypeAdapter == null)
        throw new IllegalStateException("no mapping found for base type: " + key2);

      constructDeep(adapter, baseTypeAdapter);
    }
  }

  private void validate(TypeAdapter adapter, Property prop) {
    if (prop.getType() instanceof ClassRef) {
      ClassRef ref = (ClassRef) prop.getType();
      String refkey = ref.getUri() + "#" + ref.getName();
      if (typeMap.get(refkey) == null)
        throw new ProvisioningException("invalid type reference detected for property '"
            + adapter.getKey() + "." + prop.getName() + "' no class or enumeration '" + refkey
            + "' is defined");
      if (prop.getOpposite() != null) {
        Class oppositeClass = (Class) typeMap.get(refkey).getType();
        Property oppositeProperty = findPropertyByName(oppositeClass, prop.getOpposite());
        if (oppositeProperty == null)
          throw new ProvisioningException("invalid opposite reference detected for property '"
              + adapter.getKey() + "." + prop.getName() + "' no opposite property '"
              + prop.getOpposite() + "' is defined for class '" + refkey + "'");
      }
    }
    if (prop.getType() instanceof EnumerationRef) {
      EnumerationRef ref = (EnumerationRef) prop.getType();
      String refkey = ref.getUri() + "#" + ref.getName();
      if (typeMap.get(refkey) == null)
        throw new ProvisioningException("invalid type reference detected for property '"
            + prop.getName() + "' defined for class '" + adapter.getKey() + "'");
    }
  }

  private Property findPropertyByName(Class clss, String name) {
    for (Property prop : clss.getProperties()) {
      if (name.equals(prop.getName()))
        return prop;
    }
    return null;
  }

}
