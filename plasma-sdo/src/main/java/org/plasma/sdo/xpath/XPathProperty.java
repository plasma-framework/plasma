package org.plasma.sdo.xpath;

import commonj.sdo.Property;
import commonj.sdo.Type;

public class XPathProperty extends MetaDataNodeAdapter {

	private Property target;
	
	public XPathProperty(Property target, Type source) {
		super(source);
		this.target = target;
	}

	public XPathProperty(Property target, Type source, Property sourceProperty) {
		super(source, sourceProperty);
		this.target = target;
	}

	public Property getProperty() {
		return this.target;
	}	
}
