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
package org.plasma.xml.sdox;

public interface SDOXConstants {
    public static String SDOX_NAMESPACE_URI = "commonj.sdo/xml";
    public static String SDOX_NAMESPACE_PREFIX = "sdox";

    public static String LOCAL_NAME_NAME 			= "name";
    public static String LOCAL_NAME_PROPERTY_TYPE 	= "propertyType";
    public static String LOCAL_NAME_OPPOSITE_PROPERTY = "oppositeProperty";
    public static String LOCAL_NAME_SEQUENCE 		= "sequence";
    public static String LOCAL_NAME_STRING 			= "string";
    public static String LOCAL_NAME_DATATYPE 		= "dataType";
    public static String LOCAL_NAME_ALIAS_NAME 		= "aliasName";
    public static String LOCAL_NAME_READ_ONLY 		= "readOnly";
    public static String LOCAL_NAME_MANY 			= "many";
    // taken from 3.0 SDOX
    public static String LOCAL_NAME_ORPHAN_HOLDER 	= "orphanHolder";
    public static String LOCAL_NAME_KEY 			= "key";
    public static String LOCAL_NAME_EMBEDDED_KEY    = "embeddedKey";
    public static String LOCAL_NAME_KEY_TYPE 		= "keyType";
    
}
