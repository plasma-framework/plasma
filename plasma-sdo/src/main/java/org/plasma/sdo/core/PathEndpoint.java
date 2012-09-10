package org.plasma.sdo.core;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * Stores the property which is the end point of a path
 * in association with the data object which was its "source"
 * along the path.
 */
public class PathEndpoint {
	private Property property;
	private DataObject source;
	@SuppressWarnings("unused")
	private PathEndpoint() {}
	public PathEndpoint(Property property, DataObject source) {
		super();
		this.property = property;
		this.source = source;
	}
	public Property getProperty() {
		return property;
	}
	public DataObject getSource() {
		return source;
	}    
}
