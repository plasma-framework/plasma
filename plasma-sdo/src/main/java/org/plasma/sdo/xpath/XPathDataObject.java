package org.plasma.sdo.xpath;

import org.plasma.sdo.PlasmaDataObject;

import commonj.sdo.Property;

public class XPathDataObject extends DataGraphNodeAdapter {

	private PlasmaDataObject target;
	
	public XPathDataObject(PlasmaDataObject target, PlasmaDataObject source, Property sourceProperty) {
		super(source, sourceProperty);
		this.target = target;
	}

	public PlasmaDataObject getDataObject() {
		return target;
	}
	
	public Object get() {
		return this.target;
	}
	
	public void set(Object value) {
		this.target.set(this.sourceProperty, value);
	}


}