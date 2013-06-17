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
package org.plasma.sdo.access.provider.common;

// java imports
import java.util.Comparator;

import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.core.CoreDataObject;


public class DataGraphComparator extends DataComparator
    implements Comparator<PlasmaDataGraph>
{
	protected DataGraphComparator()
    {
    }

    public int compare(PlasmaDataGraph o1, PlasmaDataGraph o2)
    {
    	CoreDataObject vo1 = (CoreDataObject)o1.getRootObject();
    	CoreDataObject vo2 = (CoreDataObject)o2.getRootObject();
        return super.compare(vo1, vo2);
    } 

}