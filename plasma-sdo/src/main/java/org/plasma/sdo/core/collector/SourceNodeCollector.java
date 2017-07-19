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
package org.plasma.sdo.core.collector;

import java.util.ArrayList;
import java.util.List;

import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * Visits an entire data graph, 
 * searching for data objects which have link(s) to the given target data object.
 * Used to accommodate operations on directed associations, i.e. where the
 * association is on-way and no opposite property is available. 
 *  
 * @see PlasmaDataGraphVisitor 
 */
public class SourceNodeCollector implements PlasmaDataGraphVisitor {

	private List<LinkedNode> result = new ArrayList<LinkedNode>();
	
	private DataObject target;
	
	public SourceNodeCollector(DataObject target) {
		super();
		this.target = target;
	}

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
		
	    if (targetObject.equals(this.target))
	    	return;
	    //FIXME: we could really use a value iterator here. See github #70
	    for (Property prop : target.getType().getProperties()) {
	    	if (prop.getType().isDataType())
	    		continue;
	    	if (!targetObject.isSet(prop))
	    		continue;
	    	if (prop.isMany()) {
	    		@SuppressWarnings("unchecked")
				List<DataObject> list = targetObject.getList(prop);
	    		for (DataObject dataObject : list)
	    			if (dataObject.equals(this.target)) {
	    				addNode(targetObject, sourceObject, sourceProperty,
	    						(PlasmaProperty)prop);
	    			}
	    	}
	    	else {
	    		DataObject dataObject = targetObject.getDataObject(prop);
    			if (dataObject.equals(this.target)) {
    				addNode(targetObject, sourceObject, sourceProperty,
    						(PlasmaProperty)prop);
    			}
	    	}
	    }		
	}
	
	private void addNode(PlasmaDataObject targetObject, PlasmaDataObject sourceObject, PlasmaProperty sourceProperty,
			PlasmaProperty targetProperty) {
		LinkedNode node = null;
    	if (sourceObject == null) {
    		node = new LinkedNode(
	    			targetObject, null, null);
    	}
    	else {
    		node = new LinkedNode(
        			targetObject, sourceObject, sourceProperty);
    	}	
    	node.setTargetProperty(targetProperty);
    	result.add(node); 		
	}
	
	/**
	 * Returns the nodes which are
     * part of the contiguous containment hierarchy from
     * the given source.
	 * @return the nodes which are
     * part of the contiguous containment hierarchy from
     * the given source.
	 */
	public List<LinkedNode> getResult() {
		return result;
	}

}
