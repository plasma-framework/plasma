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
package org.plasma.sdo.access.provider.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaType;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

public abstract class SimpleCollector {

    private static Log log = LogFactory.getFactory().getInstance(
    		SimpleCollector.class);
    private List<PlasmaDataObject> result = new ArrayList<PlasmaDataObject>();
    private HashSet<PlasmaDataObject> resultMap = new HashSet<PlasmaDataObject>();
    protected PlasmaChangeSummary changeSummary;
    protected Map<String, Boolean> isDescendantResultsMap = new HashMap<String, Boolean>();
   
    public List<PlasmaDataObject> getResult() {
        return this.result;
    } 
    
    protected DataObject[] toArray() {
    	DataObject[] resultArray = new DataObject[result.size()];
        result.toArray(resultArray);
        return resultArray;
    }
    
    protected boolean contains(DataObject dataObject) {
    	return this.resultMap.contains(dataObject);
    }
    
    protected void add(PlasmaDataObject dataObject) {
    	result.add(dataObject);
    	resultMap.add(dataObject);
    }

    protected void add(int index, PlasmaDataObject dataObject) {
    	result.add(index, dataObject);
    	resultMap.add(dataObject);
    }
    
    protected boolean isRelation(DataObject target, DataObject source, AssociationPath relationPath) {
    	return (((PlasmaType)target.getType()).isRelation((PlasmaType)source.getType(), 
    			relationPath));
    }
    
    protected boolean hasChildLink(DataObject target, DataObject source) {
        
        if (log.isDebugEnabled())
            log.debug("comparing "+ target.toString() 
                    + "/" + source.toString());
        
        for (Property property : target.getType().getProperties()) {
            if (property.getType().isDataType()) 
                continue;               
            if (property.isMany()) 
                continue;               
                        
            Object value = target.get(property);
            if (value == null)
            	continue;

            if (log.isDebugEnabled())
                log.debug("checking property " + target.getType().getName()
                        + "." + property.getName());
            
            if (isLinked(source, value))
            {
                if (log.isDebugEnabled())
                    log.debug("found child data link " + target.getType().getName()
                            + "." + property.getName()
                            + "->" + source.getType().getName());
                return true; 
            }           
        } 
        return false;
    } 
       
    protected boolean isLinked(DataObject other, Object value) {
        if (value instanceof List) {
            List<DataObject> list = (List<DataObject>)value;
            for (DataObject dataObject : list) {
                PlasmaNode dataNode = (PlasmaNode)dataObject;
                if (dataNode.getUUIDAsString().equals(((PlasmaNode)other).getUUIDAsString())) {
                    return true;        
                }
            }
        }
        else {
            DataObject dataObject = (DataObject)value;
            PlasmaNode dataNode = (PlasmaNode)dataObject;
            if (dataNode.getUUIDAsString().equals(((PlasmaNode)other).getUUIDAsString())) {
                return true;
            }
        }
        return false;
    }
	
}
