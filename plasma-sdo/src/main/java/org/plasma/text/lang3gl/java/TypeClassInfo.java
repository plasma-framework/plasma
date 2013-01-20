package org.plasma.text.lang3gl.java;

import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.DataTypeRef;
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
