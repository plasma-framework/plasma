package org.plasma.config.adapter;

import java.util.HashMap;
import java.util.Map;

import org.plasma.config.ConfigurationException;
import org.plasma.config.Namespace;
import org.plasma.config.TypeBinding;

public class NamespaceAdapter {

	private Namespace namespace;
	private Map<String, TypeBindingAdapter> typeBindings;

	@SuppressWarnings("unused")
	private NamespaceAdapter() {}
	public NamespaceAdapter(Namespace namespace) {
		super();
		this.namespace = namespace;
	}
	public Namespace getNamespace() {
		return namespace;
	}
	
	public void addTypeBinding(TypeBinding typeBinding) {
		if (this.typeBindings != null && this.typeBindings.get(typeBinding.getType()) != null)
			throw new ConfigurationException("duplicate type binding - "
				+ "a type binding for type '" + typeBinding.getType() + "' already exists "
				+ "within the configucation for namespace with URI, "
				+ namespace.getUri());
		if (this.typeBindings == null)
			this.typeBindings = new HashMap<String, TypeBindingAdapter>();
		this.typeBindings.put(typeBinding.getType(), new TypeBindingAdapter(this, typeBinding));
	}
	
	public TypeBindingAdapter findTypeBinding(String typeName) {
		if (this.typeBindings != null) {
			TypeBindingAdapter adapter = this.typeBindings.get(typeName);
			if (adapter != null)
				return adapter;
			else
				return null;
		}
		else
			return null;
	}
}
