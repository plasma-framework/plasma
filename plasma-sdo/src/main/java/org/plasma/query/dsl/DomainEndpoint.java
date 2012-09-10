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
