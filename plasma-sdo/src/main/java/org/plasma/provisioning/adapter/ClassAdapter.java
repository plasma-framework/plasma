package org.plasma.provisioning.adapter;

import org.plasma.provisioning.Class;
import org.plasma.provisioning.Type;

public class ClassAdapter extends TypeAdapter {

	public ClassAdapter(Type type) {
		super(type);
	}

	public Class getClss() {
		return (Class)this.getType();
	}
}
