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
