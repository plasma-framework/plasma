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
package org.plasma.config.adapter;

import java.util.HashMap;
import java.util.Map;

import org.plasma.config.ConfigurationException;
import org.plasma.config.PropertyBinding;
import org.plasma.config.TypeBinding;

public class TypeBindingAdapter {

	private NamespaceAdapter namespace;
	private TypeBinding binding;
	private Map<String, PropertyBindingAdapter> propertyBindings;

	@SuppressWarnings("unused")
	private TypeBindingAdapter() {}
	public TypeBindingAdapter(NamespaceAdapter namespace, TypeBinding binding) {
		super();
		this.namespace = namespace;
		this.binding = binding;
        for (PropertyBinding propertyBinding : binding.getPropertyBindings()) {
        	addPropertyBinding(propertyBinding);
        }
	}

	public TypeBinding getBinding() {
		return binding;
	}
	
	public void addPropertyBinding(PropertyBinding propertyBinding) {
		if (this.propertyBindings != null && this.propertyBindings.get(propertyBinding.getProperty()) != null)
			throw new ConfigurationException("duplicate type binding - "
				+ "a type binding for type '" + propertyBinding.getProperty() + "' already exists "
				+ "within the configucation for namespace, "
				+ namespace.getNamespace().getUri());
		if (propertyBindings == null)
			propertyBindings = new HashMap<String, PropertyBindingAdapter>();
		this.propertyBindings.put(propertyBinding.getProperty(), new PropertyBindingAdapter(propertyBinding));
	}
	
	public PropertyBindingAdapter findPropertyBinding(String propertyName) {
		if (this.propertyBindings != null) {
			PropertyBindingAdapter adapter = this.propertyBindings.get(propertyName);
			if (adapter != null)
				return adapter;
			else
				return null;
		}
		else
			return null;
	}
	
}
