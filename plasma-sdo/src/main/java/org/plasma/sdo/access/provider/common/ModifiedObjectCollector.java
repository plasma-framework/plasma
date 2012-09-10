package org.plasma.sdo.access.provider.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Property;

public class ModifiedObjectCollector implements PlasmaDataGraphVisitor {
    private static Log log = LogFactory.getFactory().getInstance(
            ModifiedObjectCollector.class);
    private List<PlasmaDataObject> result = new ArrayList<PlasmaDataObject>();
    private PlasmaChangeSummary changeSummary;
    @SuppressWarnings("unused")
    private ModifiedObjectCollector() {}
    public ModifiedObjectCollector(DataGraph dataGraph) {
        
        this.changeSummary = (PlasmaChangeSummary)dataGraph.getChangeSummary();
        PlasmaDataObject root = (PlasmaDataObject)dataGraph.getRootObject();
        root.accept(this);
    }
    
    public void visit(DataObject target, DataObject source, String sourceKey, int level) {
        
        if (changeSummary.isModified(target)) {
            if (!result.contains(target))
                result.add((PlasmaDataObject)target);
        }
    }
    
    public List<PlasmaDataObject> getResult() {
        return result;
    } 
}

