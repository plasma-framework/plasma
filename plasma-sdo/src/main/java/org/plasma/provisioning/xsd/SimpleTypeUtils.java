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
package org.plasma.provisioning.xsd;

import org.plasma.xml.schema.AbstractSimpleType;
import org.plasma.xml.schema.Restriction;

public class SimpleTypeUtils {
	public static boolean isEnumeration(AbstractSimpleType simpleType) {
    	Restriction restriction = simpleType.getRestriction();
    	if (restriction != null) {
    	    if (restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives().size() > 0)
    	        for (Object obj : restriction.getMinExclusivesAndMinInclusivesAndMaxExclusives()) 
    		        if (obj instanceof org.plasma.xml.schema.Enumeration)
                        return true;
    	    
    	}
    	return false;
    }

}
