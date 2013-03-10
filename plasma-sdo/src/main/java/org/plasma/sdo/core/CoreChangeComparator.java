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

import java.util.Comparator;

/**
 * Ensures ordering of changed data objects within
 * the change summary. 
 */
public class CoreChangeComparator implements Comparator<CoreChange>{

	@Override
	public int compare(CoreChange o1, CoreChange o2) {
		Integer depth1 = o1.getPathDepthFromRoot();
		Integer depth2 = o2.getPathDepthFromRoot();		
		return depth1.compareTo(depth2);
	}
}
