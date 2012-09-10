package org.plasma.sdo.access.provider.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.DataAccessException;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Property;

public class DeletedObjectCollector extends SimpleCollector {
    private static Log log = LogFactory.getFactory().getInstance(
            DeletedObjectCollector.class);
    private PlasmaChangeSummary changeSummary;
    @SuppressWarnings("unused")
    private DeletedObjectCollector() {}
    public DeletedObjectCollector(DataGraph dataGraph) {
        
        this.changeSummary = (PlasmaChangeSummary)dataGraph.getChangeSummary();
        PlasmaDataObject root = (PlasmaDataObject)dataGraph.getRootObject();
        collect();
    }
    
    private void collect() {
        List<DataObject> list = changeSummary.getChangedDataObjects();
        for (DataObject changed : list) {
            if (!changeSummary.isDeleted(changed))
                continue;
            
            // convert to array to avoid concurrent mods of collection
            DataObject[] resultArray = new DataObject[result.size()];
            result.toArray(resultArray);
            
            boolean found = false;
            for (int i = 0; i < resultArray.length; i++) {
                // is changed/deleted object a child/descendant of an existing result delete
                // if so add it AHEAD of the existing
            	if (isRelation(changed, resultArray[i], 
            			AssociationPath.singular)) {            		
                //if (isDescendant(changed, resultArray[i])) {
                    if (result.contains(changed)) {
                        throw new DataAccessException("unexpected changed object: "
                            + changed.getType().getURI() + "#"+ changed.getType().getName() + "(" 
                            + ((PlasmaDataObject)changed).getUUIDAsString() + ")");
                    }
                    found = true;
                    if (log.isDebugEnabled())
                        log.debug("adding changed object: "
                            + changed.getType().getName() + "(" 
                            + ((PlasmaDataObject)changed).getUUIDAsString() + ") at position " + i);
                    result.add(i, (PlasmaDataObject)changed);
                    break;
                }
            }
            if (!found) {
                if (log.isDebugEnabled())
                    log.debug("appending changed object: "
                        + changed.getType().getURI() + "#" + changed.getType().getName() + "(" 
                        + ((PlasmaDataObject)changed).getUUIDAsString() + ")");
                result.add((PlasmaDataObject)changed); // append it 
            }
        }
    }
    
    
    public List<PlasmaDataObject> getResult() {
        return result;
    } 

}

