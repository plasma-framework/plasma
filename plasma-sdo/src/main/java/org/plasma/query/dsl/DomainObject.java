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
package org.plasma.query.dsl;

import java.util.ArrayList;
import java.util.List;

/**
 * Super class for elements within a domain query graph. 
 */
public abstract class DomainObject {
	protected PathNode source;
	protected String sourceProperty;
	@SuppressWarnings("unused")
	private DomainObject() {}
	protected DomainObject(PathNode source, String sourceProperty) {
		super();
		this.source = source;
		this.sourceProperty = sourceProperty;
	}

	@Deprecated
	String[] getPath()
	{
		PathNode current = source;
		List<String> list = new ArrayList<String>();
		while (current != null && !current.isRoot()) {
			list.add(0, current.getSourceProperty());
			current = current.getSource();
		}
		String[] path = new String[list.size()];
		list.toArray(path);
		return path;
	}
	
	PathNode[] getPathNodes()
	{
		PathNode current = source;
		List<PathNode> list = new ArrayList<PathNode>();
		while (current != null && !current.isRoot()) {
			list.add(0, current);
			current = current.getSource();
		}
		PathNode[] path = new PathNode[list.size()];
		list.toArray(path);
		return path;
	}
}
