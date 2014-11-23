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
package org.plasma.provisioning.cli;

public enum RDBToolAction {
	/** 
	 * Generate a DDL 'create' script which creates all 
	 * tables, views, indexes, constraints and
	 * other artifacts defined within the source domain
	 * model artifact(s).
	 */
    create,  
	
    /** 
	 * Generate a DDL 'drop' script which drops all 
	 * tables, views, indexes, constraints and
	 * other artifacts defined within the source domain
	 * model artifact(s).
	 */
    drop,  
	
    /** 
	 * Generate a DDL 'truncate' script which first disables
	 * constraints then truncates all tables defined 
	 * within the source domain model artifact(s). Constraints are
	 * then re-enabled.
	 */
    truncate;  
    
    public static String asString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < RDBToolAction.values().length; i++) {
			if (i > 0)
				buf.append(", ");
			buf.append(RDBToolAction.values()[i].name());
		}
		return buf.toString();
    }
}
