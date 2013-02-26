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
