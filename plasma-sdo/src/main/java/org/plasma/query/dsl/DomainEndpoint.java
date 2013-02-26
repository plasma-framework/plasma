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

import org.plasma.query.Wildcard;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;
import org.plasma.query.model.WildcardProperty;


/**
 * A domain object which is an end point
 * for a path within a query graph. 
 */
public class DomainEndpoint extends DomainObject {

	protected AbstractProperty property;

	protected DomainEndpoint(PathNode source, String sourceProperty) {
		super(source, sourceProperty);
	}
	
	protected Path createPath() {
		Path path = null;
		PathNode[] pathNodes = this.getPathNodes();
		for (PathNode pathNode : pathNodes) {
		    if (path == null)
		    	path = new Path();
		    org.plasma.query.model.PathNode modelPathNode = new org.plasma.query.model.PathNode();
		    org.plasma.query.model.PathElement modelPathElement = new org.plasma.query.model.PathElement();
		    modelPathElement.setValue(pathNode.getSourceProperty());
		    modelPathNode.setPathElement(modelPathElement);
		    if (pathNode.getPredicate() != null) {
		    	org.plasma.query.model.Where where = new org.plasma.query.model.Where(); 
		    	where.addExpression((org.plasma.query.model.Expression)pathNode.getPredicate());
		    	modelPathNode.setWhere(where);
		    }
		    path.addPathNode(modelPathNode);
		}
		return path;
	}

}
