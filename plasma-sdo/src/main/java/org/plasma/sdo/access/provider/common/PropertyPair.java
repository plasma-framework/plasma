package org.plasma.sdo.access.provider.common;

import org.plasma.sdo.PlasmaProperty;

/**
 * Associates an SDO property with a data value and optional 
 * column index or sequence.
 */
public class PropertyPair {
	private PlasmaProperty prop;
	private Object value;
	private int column;
	private String propertyPath;
	
	@SuppressWarnings("unused")
	private PropertyPair() {}
	public PropertyPair(PlasmaProperty prop, Object value) {
    	this.prop = prop;
    	this.value = value;
    }
	public PlasmaProperty getProp() {
		return prop;
	}
	public Object getValue() {
		return value;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	
	public String getPropertyPath() {
		return propertyPath;
	}
	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}
	public String toString() {
		return this.prop.getName() + "/" + String.valueOf(this.value);
	}
}

