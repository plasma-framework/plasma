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
package org.plasma.provisioning.adapter;

import org.plasma.metamodel.Class;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;

public class FieldAdapter {
    private Package fieldPackage;
    private Class fieldClass;
    private Property field;

    @SuppressWarnings("unused")
	private FieldAdapter() {}
    
	public FieldAdapter(Package fieldPackage, Class fieldClass, Property field) {
		super();
		this.fieldPackage = fieldPackage;
		this.fieldClass = fieldClass;
		this.field = field;
		if (this.fieldPackage == null)
			throw new IllegalArgumentException("missing argument, 'fieldPackage'");
		if (this.fieldClass == null)
			throw new IllegalArgumentException("missing argument, 'fieldClass'");
		if (this.field == null)
			throw new IllegalArgumentException("missing argument, 'field'");
	}
	public Package getFieldPackage() {
		return fieldPackage;
	}
	public Class getFieldClass() {
		return fieldClass;
	}
	public Property getField() {
		return field;
	}   
}
