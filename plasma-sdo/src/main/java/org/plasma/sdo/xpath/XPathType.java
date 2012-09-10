package org.plasma.sdo.xpath;

import commonj.sdo.Property;
import commonj.sdo.Type;

public class XPathType extends MetaDataNodeAdapter {

	private Type target;
	
	public XPathType(Type target, Type source, Property sourceProperty) {
		super(source, sourceProperty);
		this.target = target;
	}

	public Type getType() {
		return this.target;
	}	
}
