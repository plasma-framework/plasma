/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.sdo.access.provider.common;

import org.plasma.sdo.PlasmaProperty;

/**
 * Associates an SDO property with a data value and optional 
 * column index or sequence.
 */
/**
 * @author scinnamond
 *
 */
public class PropertyPair {
	private PlasmaProperty prop;
    /** the property which corresponds to the value. Can be null in which case the original property is used */	
	private PlasmaProperty valueProp;
	private Object value;
	/** The previous value, important when key properties are modified 
	 * and the old key value is required to fetch and obtain a lock on the entity meing modified. */
	private Object oldValue;
	/** the JDBC column id */
	private int column;
	/** the JDBC column id associated with the old value */
	private int oldValueColumn;
	private String propertyPath;
    /** whether the property was explicitly named in the originating query */	
	private boolean queryProperty = true; 
	
	@SuppressWarnings("unused")
	private PropertyPair() {}
	public PropertyPair(PlasmaProperty prop, Object value) {
    	this.prop = prop;
    	this.value = value;
    	if (this.prop == null)
    		throw new IllegalArgumentException("expected non-null 'prop' argument");
    	if (this.value == null)
    		throw new IllegalArgumentException("expected non-null 'value' argument");
    }
	public PropertyPair(PlasmaProperty prop, Object value, Object oldValue) {
		this(prop, value);
		this.oldValue = oldValue;
    	if (this.oldValue == null)
    		throw new IllegalArgumentException("expected non-null 'oldValue' argument");
	}
	public PlasmaProperty getProp() {
		return prop;
	}
	public Object getValue() {
		return value;
	}
	public Object getOldValue() {
		return oldValue;
	}
	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}
	public int getColumn() {
		return column;
	}
		
	public PlasmaProperty getValueProp() {
		return valueProp;
	}
	public void setValueProp(PlasmaProperty valueProp) {
		this.valueProp = valueProp;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getOldValueColumn() {
		return oldValueColumn;
	}
	public void setOldValueColumn(int oldValueColumn) {
		this.oldValueColumn = oldValueColumn;
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
	public boolean isQueryProperty() {
		return queryProperty;
	}
	public void setQueryProperty(boolean queryProperty) {
		this.queryProperty = queryProperty;
	}
	
}

