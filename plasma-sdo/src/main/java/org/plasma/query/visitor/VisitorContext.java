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
package org.plasma.query.visitor;

/**
 * Encapsulates logic and data specific to
 * a single traversal.
 */
public class VisitorContext {
    private Traversal traversal = Traversal.CONTINUE;

	public Traversal getTraversal() {
		return traversal;
	}

	public void setTraversal(Traversal traversal) {
		this.traversal = traversal;
	}
	
	/**
	 * Returns true if traversal should continue.
	 * @return true if traversal should continue
	 */
	public boolean isContunue() {
		return this.traversal.ordinal() == Traversal.CONTINUE.ordinal();
	}
	
	/**
	 * Signals the termination of traversal.
	 */
	public void abort() {
		traversal = Traversal.ABORT;
	}
}
