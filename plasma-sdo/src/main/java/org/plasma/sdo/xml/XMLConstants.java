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

public interface XMLConstants {
	public static final String XMLSCHEMA_INSTANCE_NAMESPACE_URI = "http://www.w3.org/2001/XMLSchema-instance";
	public static final String ATTRIB_TARGET_NAMESPACE = "targetNamespace";
	public static final String JAXP_SCHEMA_LANGUAGE =
	    "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	public static final String JAXP_NO_NAMESPACE_SCHEMA_SOURCE = 
		"http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
	public static final String JAXP_SCHEMA_SOURCE =
	    "http://java.sun.com/xml/jaxp/properties/schemaSource";
}
