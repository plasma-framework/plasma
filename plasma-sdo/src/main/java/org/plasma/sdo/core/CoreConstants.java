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
package org.plasma.sdo.core;

import java.math.BigDecimal;


public interface CoreConstants 
{
	/** When we encode strings, we always specify UTF8 encoding */
	public static final String UTF8_ENCODING = "UTF-8";

	public static String    NULL_STRING = "NULLVALUE";
    public static Integer   NULL_INTEGER = new Integer(Integer.MIN_VALUE);
    public static Long      NULL_LONG = new Long(Long.MIN_VALUE);
    public static Short     NULL_SHORT = new Short(Short.MIN_VALUE);
    public static Float     NULL_FLOAT = new Float(Float.MIN_VALUE);
    public static Double    NULL_DOUBLE = new Double(Double.MIN_VALUE);
    public static BigDecimal NULL_DECIMAL = new BigDecimal(Double.MIN_VALUE);

    public static String    NULL_REFERENCE_STRING = NULL_STRING;
    public static Integer   NULL_REFERENCE_INTEGER = NULL_INTEGER;
    public static Long      NULL_REFERENCE_LONG = NULL_LONG;
    public static Short     NULL_REFERENCE_SHORT = NULL_SHORT;
    public static Float     NULL_REFERENCE_FLOAT = NULL_FLOAT;
    public static Double    NULL_REFERENCE_DOUBLE = NULL_DOUBLE;
    public static BigDecimal NULL_REFERENCE_DECIMAL = new BigDecimal(-1);

    public static final String PROPERTY_NAME_SNAPSHOT_TIMESTAMP      = "snapshotTimestamp";
    public static final String PROPERTY_NAME_ENTITY_NAME        = "entityName";
    public static final String PROPERTY_NAME_ENTITY_LOCKED      = "entityLocked";
    public static final String PROPERTY_NAME_ENTITY_UNLOCKED      = "entityUnlocked";
    public static final String PROPERTY_NAME_SEQUENCE      = "__SEQ__";
    public static final String PROPERTY_NAME_ROWKEY      = "__ROWKEY__";
    
    
    //@Deprecated
    //public static final String PROPERTY_NAME_UUID               = "__UUID__";
    public static final String PROPERTY_NAME_UUID_BYTES         = "__UUID_BYTES__";
    public static final String PROPERTY_NAME_UUID_STRING        = "__UUID_STR__";

}