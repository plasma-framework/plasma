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

package org.plasma.runtime.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.plasma.config.Namespace;
import org.plasma.config.NamespaceProvisioning;
import org.plasma.config.Property;
import org.plasma.config.TypeBinding;
import org.plasma.runtime.ConfigurationException;

public class NamespaceAdapter {

  private Namespace namespace;
  private Map<String, TypeBindingAdapter> typeBindings;
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private boolean systemArtifact = false;

  @SuppressWarnings("unused")
  private NamespaceAdapter() {
  }

  public NamespaceAdapter(Namespace namespace) {
    super();
    this.namespace = namespace;
  }

  public Namespace getNamespace() {
    return namespace;
  }

  public List<Property> getProperties() {
    return Collections.unmodifiableList(namespace.getProperties());
  }

  public NamespaceProvisioning getProvisioning() {
    return namespace.getProvisioning();
  }

  public List<TypeBinding> getTypeBindings() {
    return Collections.unmodifiableList(namespace.getTypeBindings());
  }

  public String getUri() {
    return namespace.getUri();
  }

  public String getArtifact() {
    return namespace.getArtifact();
  }

  public boolean isSystemArtifact() {
    return systemArtifact;
  }

  public void setSystemArtifact(boolean systemArtifact) {
    this.systemArtifact = systemArtifact;
  }

  public void addTypeBinding(TypeBinding typeBinding) {

    if (this.typeBindings != null && this.typeBindings.get(typeBinding.getType()) != null)
      throw new ConfigurationException("duplicate type binding - " + "a type binding for type '"
          + typeBinding.getType() + "' already exists "
          + "within the configucation for namespace with URI, " + namespace.getUri());
    if (this.typeBindings == null)
      this.typeBindings = new HashMap<String, TypeBindingAdapter>();
    this.lock.writeLock().lock();
    try {
      this.typeBindings.put(typeBinding.getType(), new TypeBindingAdapter(this, typeBinding));
    } finally {
      this.lock.writeLock().unlock();
    }
  }

  /**
   * Maps an existing type binding to the binding logical name.
   * 
   * @param typeBinding
   */
  public void remapTypeBinding(TypeBindingAdapter typeBinding) {
    if (typeBinding.getLogicalName() == null)
      throw new ConfigurationException("cloud not remap type binding " + "for type '"
          + typeBinding.getType() + "' with no logical name");
    if (this.typeBindings != null && this.typeBindings.get(typeBinding.getLogicalName()) != null)
      throw new ConfigurationException("duplicate type binding - " + "a type binding for type '"
          + typeBinding.getLogicalName() + "' already exists "
          + "within the configucation for namespace with URI, " + namespace.getUri());
    if (this.typeBindings == null)
      this.typeBindings = new HashMap<String, TypeBindingAdapter>();
    TypeBindingAdapter existing = this.typeBindings.remove(typeBinding.getType());
    if (existing == null)
      throw new ConfigurationException("missing type binding - " + "no type binding for type '"
          + typeBinding.getType() + "' exists "
          + "within the configucation for namespace with URI, " + namespace.getUri());
    this.lock.writeLock().lock();
    try {
      this.typeBindings.put(typeBinding.getLogicalName(), existing);
    } finally {
      this.lock.writeLock().unlock();
    }
  }

  public TypeBindingAdapter findTypeBinding(String typeName) {
    if (this.typeBindings != null) {
      TypeBindingAdapter adapter = this.typeBindings.get(typeName);
      if (adapter != null)
        return adapter;
      else
        return null;
    } else
      return null;
  }
}
