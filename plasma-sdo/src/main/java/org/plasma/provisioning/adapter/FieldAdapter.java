package org.plasma.provisioning.adapter;

import org.plasma.provisioning.Package;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.Property;

public class FieldAdapter {
    private Package fieldPackage;
    private Class fieldClass;
    private Property field;

    @SuppressWarnings("unused")
	private FieldAdapter() {}
    
	public FieldAdapter(Package fieldPackage, Class fieldClass, Property field) {
		super();
		this.fieldPackage = fieldPackage;
		this.fieldClass = fieldClass;
		this.field = field;
	}
	public Package getFieldPackage() {
		return fieldPackage;
	}
	public Class getFieldClass() {
		return fieldClass;
	}
	public Property getField() {
		return field;
	}   
}
