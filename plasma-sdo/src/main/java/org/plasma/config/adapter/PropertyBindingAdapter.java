package org.plasma.config.adapter;

import org.plasma.config.PropertyBinding;

public class PropertyBindingAdapter {

	private PropertyBinding binding;

	@SuppressWarnings("unused")
	private PropertyBindingAdapter() {}
	public PropertyBindingAdapter(PropertyBinding binding) {
		super();
		this.binding = binding;
	}

	public PropertyBinding getBinding() {
		return binding;
	}
	
}
