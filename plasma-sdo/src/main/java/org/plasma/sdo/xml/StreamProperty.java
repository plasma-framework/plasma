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
