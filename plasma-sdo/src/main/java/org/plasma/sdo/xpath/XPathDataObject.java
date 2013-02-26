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