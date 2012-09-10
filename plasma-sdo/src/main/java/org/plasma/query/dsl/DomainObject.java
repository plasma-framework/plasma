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
