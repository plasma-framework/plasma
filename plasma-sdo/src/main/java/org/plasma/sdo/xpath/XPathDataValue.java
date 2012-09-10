package org.plasma.sdo.xpath;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

public class XPathDataValue extends DataGraphNodeAdapter {

	private Object target;
	
	public XPathDataValue(Object target, DataObject source, Property sourceProperty) {
		super(source, sourceProperty);
		this.target = target;
	}

	public Object getValue() {
		return target;
	}
	
	public Object get() {
		return this.target;
	}
	
	public void set(Object value) {
		throw new IllegalStateException("cannot set a value for a XPATH value endpoint");
	}
}
