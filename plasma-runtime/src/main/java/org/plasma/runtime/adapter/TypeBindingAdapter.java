/** * Copyright 2017 TerraMeta Software, Inc. * * Licensed under the Apache License, Version 2.0 (the "License"); * you may not use this file except in compliance with the License. * You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
package org.plasma.runtime.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.plasma.config.PropertyBinding;
import org.plasma.config.TypeBinding;
import org.plasma.runtime.ConfigurationException;

public class TypeBindingAdapter {

  private NamespaceAdapter namespace;
  private TypeBinding binding;
  private Map<String, PropertyBindingAdapter> propertyBindings;
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  @SuppressWarnings("unused")
  private TypeBindingAdapter() {
  }

  public TypeBindingAdapter(NamespaceAdapter namespace, TypeBinding binding) {
    super();
    this.namespace = namespace;
    this.binding = binding;
    for (PropertyBinding propertyBinding : binding.getPropertyBindings()) {
      addPropertyBinding(propertyBinding);
    }
  }

  protected TypeBinding getBinding() {
    return binding;
  }

  public String getLogicalName() {
    return binding.getLogicalName();
  }

  public List<PropertyBinding> getPropertyBindings() {
    return binding.getPropertyBindings();
  }

  public String getPhysicalName() {
    return binding.getPhysicalName();
  }

  public String getLocalName() {
    return binding.getLocalName();
  }

  public String getType() {
    return binding.getType();
  }

  public void addPropertyBinding(PropertyBinding propertyBinding) {
    if (this.propertyBindings != null
        && this.propertyBindings.get(propertyBinding.getProperty()) != null)
      throw new ConfigurationException("duplicate type binding - " + "a type binding for type '"
          + propertyBinding.getProperty() + "' already exists "
          + "within the configucation for namespace, " + namespace.getNamespace().getUri());
    if (propertyBindings == null)
      propertyBindings = new HashMap<String, PropertyBindingAdapter>();
    this.lock.writeLock().lock();
    try {
      this.propertyBindings.put(propertyBinding.getProperty(), new PropertyBindingAdapter(
          propertyBinding));
    } finally {
      this.lock.writeLock().unlock();
    }
  }

  public PropertyBindingAdapter findPropertyBinding(String propertyName) {
    if (this.propertyBindings != null) {
      PropertyBindingAdapter adapter = this.propertyBindings.get(propertyName);
      if (adapter != null)
        return adapter;
      else
        return null;
    } else
      return null;
  }

}
