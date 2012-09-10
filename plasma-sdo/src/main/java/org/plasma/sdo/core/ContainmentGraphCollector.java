package org.plasma.sdo.core;

import java.util.ArrayList;
import java.util.List;

import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;

import commonj.sdo.DataObject;

/**
 * Visits a data graph from a given source/root, 
 * collecting the source and
 * any contained nodes from the source which are
 * part of the contiguous containment hierarchy. Clients
 * must call {@link PlasmaDataObject#accept accept()} passing
 * this visitor to trigger the traversal from a given source
 * data object.
 * @see PlasmaDataGraphVisitor 
 */
public class ContainmentGraphCollector implements PlasmaDataGraphVisitor {

	private List<ContainmentNode> result = new ArrayList<ContainmentNode>();
	
	/**
	 * Called by the graph traversal algorithm (as
	 * part of the visitor pattern) when a graph node
	 * is encountered.
	 */
	@Override
	public void visit(DataObject target, DataObject source,
			String sourcePropertyName, int level) 
	{
		PlasmaDataObject targetObject = (PlasmaDataObject)target; 
		PlasmaDataObject sourceObject = (PlasmaDataObject)source; 
		PlasmaProperty sourceProperty = null;
		if (sourceObject != null)
			sourceProperty = (PlasmaProperty)source.getType().getProperty(sourcePropertyName);
		
		// always add the root, otherwise
		// checking contains() ensures a contiguous
		// containment graph starting from the root
    	if (sourceObject == null) {
    		result.add(new ContainmentNode(
    			targetObject, null, null));    	
    	}
    	else if (sourceObject.contains(target)) {
    		result.add(new ContainmentNode(
        			targetObject, sourceObject, sourceProperty));    	
    	}
	}
	
	/**
	 * Returns the nodes which are
     * part of the contiguous containment hierarchy from
     * the given source.
	 * @return the nodes which are
     * part of the contiguous containment hierarchy from
     * the given source.
	 */
	public List<ContainmentNode> getResult() {
		return result;
	}

}
