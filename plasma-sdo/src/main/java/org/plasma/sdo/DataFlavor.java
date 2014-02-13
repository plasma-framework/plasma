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
package org.plasma.sdo;

public enum DataFlavor {
    temporal,
    integral,
    real,
    string,
    other;
    
	public static DataFlavor fromDataType(DataType dataType) {
        switch (dataType) {
        case Decimal:  
        case Double:   
        case Float:    
        	return DataFlavor.real;
        case Boolean:  
        case Int:      
        case Integer:  
        case Long:     
        case Short:    
        	return DataFlavor.integral;
        case Character:
        case String:   
        case Strings:  
        	return DataFlavor.string;
        case Date:     
        case Duration: 
        case DateTime: 
        case Day:      
        case Month:    
        case MonthDay: 
        case Year:     
        case YearMonth:
        case YearMonthDay:
        case Time:     
        	return DataFlavor.temporal;
        case Byte:     
        case Bytes:    
        case URI:      
        case Object:   
        default:
        	return DataFlavor.other;
        }
	}
    
}
