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
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.core.CoreChangeSummary;
import org.plasma.sdo.core.CoreConstants;
import org.plasma.sdo.core.CoreDataObject;
import org.plasma.sdo.core.CoreNode;
import org.plasma.sdo.core.CoreObject;
import org.plasma.sdo.helper.PlasmaDataFactory;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;

/**
 * Creates a copy of any arbitrary DataGraph. For all
 * DataObject nodes with a SDO type found in the given referenceTypes
 * array clears these from the change summary.
 */
public class DataGraphCopyVisitor implements PlasmaDataGraphVisitor {
    private static Log log = LogFactory.getLog(DataGraphCopyVisitor.class);
	
	private PlasmaDataObject result;
	private Map<String, PlasmaDataObject> resultMap = new HashMap<String, PlasmaDataObject>();
	private HashSet<Type> referenceTypes;
	
	public DataGraphCopyVisitor() {
	}
	
	public DataGraphCopyVisitor(Type[] referenceTypes) {
		this.referenceTypes = new HashSet<Type>(); 
		for (Type t : referenceTypes)
		   this.referenceTypes.add(t);
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
					+ targetObject.getUUIDAsString());
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
		if (target.getContainer().equals(source)) {
			if (targetResult == null) {
				if (log.isDebugEnabled())
					log.debug("creating contained object "
						+ targetObject.toString());
    	        targetResult = (PlasmaDataObject)sourceResult.createDataObject(sourceProperty);
    	        copyDataProperties(targetObject, targetResult);
    	        resultMap.put(targetObject.getUUIDAsString(), targetResult);
			}
			else {
				// reparent
			     if (log.isDebugEnabled())
						log.debug("reparenting contained object "
							+ targetObject.toString());
				targetResult.setContainer(sourceResult);
				targetResult.setContainmentProperty(sourceProperty);
			}
		}	
		else {
			 if (targetResult != null) {				 
			     if (log.isDebugEnabled())
						log.debug("linking existing object "
							+ targetObject.toString());
			     sourceResult.set(sourceProperty, targetResult);
			 }
			 else {
				 // leave it orphaned till we get an event where source is the container
			     if (log.isDebugEnabled())
						log.debug("copying/linking orphaned object "
							+ targetObject.toString());
				 targetResult = (PlasmaDataObject)PlasmaDataFactory.INSTANCE.create(targetObject.getType());
	    	     copyDataProperties(targetObject, targetResult);
			     sourceResult.set(sourceProperty, targetResult);
			     resultMap.put(targetObject.getUUIDAsString(), targetResult);
			 }
		}
		
		if (isReferenceType(targetResult.getType())) {
			CoreChangeSummary changeSummary = (CoreChangeSummary)targetResult.getChangeSummary();
			changeSummary.clear(targetResult);
		    if (log.isDebugEnabled())
				log.debug("clearing change summary for reference object "
						+ targetObject.toString());
		}
	}
	
	private boolean isReferenceType(Type type) {	
		return referenceTypes != null && referenceTypes.contains(type);
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
                else
                	((CoreNode)copy).getValueObject().put(property.getName(), value); // FIXME: what about change summary
            }
        }
        Object timestamp = ((CoreDataObject)source).getValueObject().get(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP);
        if (timestamp != null)
            ((CoreDataObject)copy).getValueObject().put(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, timestamp);
	}
	
	public DataObject getResult() {
		return result;
	}

}
