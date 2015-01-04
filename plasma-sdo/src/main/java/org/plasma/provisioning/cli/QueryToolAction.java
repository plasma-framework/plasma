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

public enum QueryToolAction implements OptionEnum {
    
    /** 
     * Takes the XML representation for a query, as described by a PlasmaQuery<sup>TM</sup> or other supported
     * declarative query framework, and generates an output model based on the
     * target type.
     * @see org.plasma.query.Query 
     * @see QueryToolTargetType
     */
    compile("takes the XML representation for a query, as described by a PlasmaQuery<sup>TM</sup> or other supported declarative query framework, and generates an output model based on the target type"),  
    /** 
     * Takes the XML representation for a query, as described by a PlasmaQuery<sup>TM</sup> or other supported
     * declarative query framework, runs the query against the Plasma SDO runtime, 
     * and generates/returns SDO compliant XML. 
     * @see org.plasma.query.Query 
     */
    run("takes the XML representation for a query, as described by a PlasmaQuery<sup>TM</sup> or other supported declarative query framework, runs the query against the Plasma SDO runtime, and generates/returns SDO compliant XML");

	private String description;
	   
	private QueryToolAction(String description) {
	    this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	 
	public static String asString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < QueryToolAction.values().length; i++) {
			if (i > 0)
				buf.append(", ");
			buf.append(QueryToolAction.values()[i].name());
		}
		return buf.toString();
	}
}
