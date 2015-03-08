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
package org.plasma.text.lang3gl.java;

import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.DataTypeRef;
import org.plasma.sdo.DataType;

public class TypeClassInfo {
	
	private boolean usePrimitives;
	
	// used for data properties
	private DataTypeRef dataTypeRef;
    private DataType sdoType;
    private java.lang.Class<?> primitiveTypeClass;
    private java.lang.Class<?> primitiveWrapperTypeClass;
    
    // used for reference properties
    private ClassRef classRef;    
    
	public TypeClassInfo(DataTypeRef dataTypeRef, DataType sdoType,
			java.lang.Class<?> primitiveTypeClass, 
			java.lang.Class<?> primitiveWrapperTypeClass,
			boolean usePrimitives) {
		super();
		this.dataTypeRef = dataTypeRef;
		this.sdoType = sdoType;
		this.primitiveTypeClass = primitiveTypeClass;
		this.primitiveWrapperTypeClass = primitiveWrapperTypeClass;
		this.usePrimitives = usePrimitives;
	}
	
	public TypeClassInfo(ClassRef classRef, boolean usePrimitives) {
		super();
		this.classRef = classRef;
		this.usePrimitives = usePrimitives;
	}
	
	public String getSimpleName() {
		if (this.dataTypeRef != null) {
			if (this.usePrimitives)
				return this.primitiveTypeClass.getSimpleName();
			else
				return this.primitiveWrapperTypeClass.getSimpleName();
		}
		else {
			return this.classRef.getName();
		}
	}	
	
	/**
	 * Return the simple (unqualified) type name applicable for use
	 * in collection classes, for primitive types (e.g. double) the
	 * equivalent wrapper class (Double) name is used.  
	 * @return
	 */
	public String getCollectionSimpleName() {
		if (this.dataTypeRef != null) {
			return this.primitiveWrapperTypeClass.getSimpleName();
		}
		else {
			return this.classRef.getName();
		}
	}
}
