package org.plasma.sdo.xpath;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

public class XPathDataProperty extends DataGraphNodeAdapter {

	private Property target;
	
	public XPathDataProperty(Property target, DataObject source, Property sourceProperty) {
		super(source, sourceProperty);
		this.target = target;
	}

	public Property getProperty() {
		return this.target;
	}
	
	public Object get() {
		return this.source.get(this.target);
	}
	
	public void set(Object value) {
		this.source.set(this.target, value);
	}
	
	public void unset() {
		this.source.unset(this.target);
	}
	
	public boolean isSet() {
		return this.source.isSet(this.target);
	}

}
