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
