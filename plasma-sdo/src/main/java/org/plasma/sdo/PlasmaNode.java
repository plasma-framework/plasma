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

import java.util.List;
import java.util.UUID;

import org.plasma.sdo.core.Node;


public interface PlasmaNode extends Node {
	/**
	 * Returns a list of 
	 * {@link PlasmaEdge edges} associated with the given property
	 * regardless of it's multiplicity. 
	 * @param property the property
	 * @return a list of edges associated with the given property
	 * regardless of it's multiplicity, or an empty list if
	 * no edges are associated with the given property.
	 * @see PlasmaEdge 
	 */
	public List <PlasmaEdge> getEdges(PlasmaProperty property);
    public PlasmaDataObject getDataObject();
    public UUID getUUID();
    public String getUUIDAsString();
    public void accept(PlasmaDataGraphVisitor visitor);
    public void acceptDepthFirst(PlasmaDataGraphVisitor visitor);
    public void accept(PlasmaDataGraphEventVisitor visitor);
}
