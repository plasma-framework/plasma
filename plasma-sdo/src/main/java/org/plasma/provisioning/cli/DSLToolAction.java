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

public enum DSLToolAction implements OptionEnum {
	/**
	 * Provisions query Domain Specific Language (DSL) classes for the current
	 * configuration.
	 */
	create("provisions query Domain Specific Language (DSL) classes for the current configuration");

	private String description;

	private DSLToolAction(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public static String asString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < DSLToolAction.values().length; i++) {
			if (i > 0)
				buf.append(", ");
			buf.append(DSLToolAction.values()[i].name());
		}
		return buf.toString();
	}
}
