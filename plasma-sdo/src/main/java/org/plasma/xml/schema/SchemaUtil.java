package org.plasma.xml.schema;

import org.plasma.provisioning.adapter.TypeAdapter;
import org.plasma.sdo.PlasmaType;

public class SchemaUtil implements SchemaConstants {
	
    /**
     * Returns the name used for for XSI type attributes and as an external reference XSD 
     * ComplexType for the given SDO type. 
     * @param type the SDO type
     * @return the name used as an external reference XSD 
     * ComplexType for the given SDO type
     */
    public static String getNonContainmentReferenceName(PlasmaType type) {
    	// FIXME: search the namespace for local name collisions
	    //return type.getLocalName() + "Ref";	
	    return XMLSCHEMA_SERIALIZATION_TYPE_NAME;
	}
    
    public static String getContainmentReferenceName(PlasmaType type) {
    	// FIXME: search the namespace for local name collisions
	    return type.getLocalName();	
	}
    
    public static String getNonContainmentReferenceName(TypeAdapter type) {
    	// FIXME: search the namespace for local name collisions
	    //return type.getLocalName() + "Ref";	
	    return XMLSCHEMA_SERIALIZATION_TYPE_NAME;
	}
    
    public static String getContainmentReferenceName(TypeAdapter type) {
    	// FIXME: search the namespace for local name collisions
	    return type.getLocalName();	
	}
    
    
    public static String getSerializationBaseTypeName() {
    	return XMLSCHEMA_SERIALIZATION_TYPE_NAME;
    }
    
    public static String getSerializationAttributeName() {
    	return XMLSCHEMA_SERIALIZATION_ATTRIB_NAME;
    }
}
