package org.plasma.sdo.xml;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;

class StreamProperty extends StreamNode {
	private PlasmaProperty property;
	private Object value;
	
	public StreamProperty(PlasmaType type, PlasmaProperty property,  
		QName name, Location loc) {
		super(type, name, loc);
		this.property = property;
	}
	
	public Object get() {
		return value;
	}
	
	public void set(Object value) {
		if (value == null)
			throw new IllegalArgumentException("expected non-null value arg");
		this.value = value;
	}
	
	public PlasmaProperty getProperty() {
		return property;
	}	
}
