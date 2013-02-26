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
package org.plasma.sdo.helper;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.helper.PlasmaDataFactory;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;

/**
 * Creates a copy of any arbitrary DataGraph while exempting all
 * DataObject nodes with a SDO type found in the given referenceTypes
 * array. For all "reference" types found in the source graph, these are
 * "re-parented" to the target graph. Rather than removing, reference nodes
 * from the source graph, it is required that the source graph be 
 * discarded. 
 */
public class DataGraphCopyVisitor implements PlasmaDataGraphVisitor {
    private static Log log = LogFactory.getLog(DataGraphCopyVisitor.class);
	
	private PlasmaDataObject result;
	private Type[] referenceTypes;
	private Map<String, PlasmaDataObject> resultMap = new HashMap<String, PlasmaDataObject>();
	
	public DataGraphCopyVisitor() {
		this.referenceTypes = new Type[0];
	}
	
	public DataGraphCopyVisitor(Type[] referenceTypes) {
		this.referenceTypes = referenceTypes;
	}
	
	public void visit(DataObject target, DataObject source,
			String sourceKey, int level) {
		
		PlasmaDataObject targetObject = (PlasmaDataObject)target;
		
		// process the root and exit
		if (source == null) {
			DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
			dataGraph.getChangeSummary().beginLogging(); // log changes from this point
	            	
			if (log.isDebugEnabled())
				log.debug("copying root object "
					+ targetObject.getType().getURI() + "#" + targetObject.getType().getName()
					+ " (" + targetObject.getUUIDAsString() + ")");
			
	    	Type rootType = target.getType();
	    	result = (PlasmaDataObject)dataGraph.createRootObject(rootType);
	    	copyDataProperties(targetObject, result);
	    	resultMap.put(targetObject.getUUIDAsString(), result);
	    	return;
		}
		PlasmaDataObject sourceObject = (PlasmaDataObject)source;
    	Property sourceProperty = sourceObject.getType().getProperty(sourceKey);
		
		PlasmaDataObject sourceResult = resultMap.get(sourceObject.getUUIDAsString());
		if (sourceResult == null)
			throw new IllegalStateException("expected source result object "
					+ sourceObject.getType().getURI() + "#" + sourceObject.getType().getName()
					+ " (" + sourceObject.getUUIDAsString() + ")");
		
		PlasmaDataObject targetResult = resultMap.get(targetObject.getUUIDAsString());
		if (targetResult == null) {
			// copy if not a reference type in this context
			if (!isReferenceType(target.getType())) {
				if (log.isDebugEnabled())
					log.debug("copying/linking non-reference object "
						+ targetObject.getType().getURI() + "#" + targetObject.getType().getName()
						+ " (" + targetObject.getUUIDAsString() + ")");
	    	    targetResult = (PlasmaDataObject)sourceResult.createDataObject(sourceProperty);
	    	    copyDataProperties(targetObject, targetResult);
	    	    resultMap.put(targetObject.getUUIDAsString(), targetResult);
			}
			else { // target is a reference obj
				targetObject.setDataGraph(null); // set up to re-parent it
				targetResult = targetObject;
				
				if (log.isDebugEnabled())
					log.debug("linking reference object "
						+ targetResult.getType().getURI() + "#" + targetResult.getType().getName()
						+ " (" + targetResult.getUUIDAsString() + ")");
			    if (!isReferenceType(sourceResult.getType())) {
				    sourceResult.set(sourceProperty, targetResult);
			    }
			    else {	
			    	targetResult.setDataGraph(sourceResult.getDataGraph());
			    	// ignore since we should have re-parented it when it was a target
			    }	
			    resultMap.put(targetResult.getUUIDAsString(), targetResult);
			}			
		}
		else {
			if (log.isDebugEnabled())
				log.debug("linking existing non-reference copy "
					+ targetResult.getType().getURI() + "#" + targetResult.getType().getName()
					+ " (" + targetResult.getUUIDAsString() + ")");
		    if (!isReferenceType(sourceResult.getType())) {
			    sourceResult.set(sourceProperty, targetResult);
		    }
		    else {
		    	// noop
		    }	
		}		
	}
	
	private boolean isReferenceType(Type type) {
		boolean found = false;
		for (Type t : referenceTypes)
            if (t.getName().equals(type.getName()) && 
            	t.getURI().equals(type.getURI())) {	
            	found = true;
            	break;
            }	
		return found;
	}
	
	/**
	 * copies data properties from the source to the given copy such that the
	 * change history is captured in the DataGraph change summary.
	 * @param source the source object
	 * @param copy the replicated object
	 */
	private void copyDataProperties(PlasmaDataObject source, 
			PlasmaDataObject copy) {        
		Object value = null;
        for (Property property : source.getType().getDeclaredProperties())
        {
            value = source.get(property);
            if (value == null)
                continue;
            if (property.getType().isDataType()) {
                if (!property.isReadOnly())
                	copy.set(property, value);
            }
        }		
	}
	
	public DataObject getResult() {
		return result;
	}

}
