package org.plasma.sdo.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.Change;
import org.plasma.sdo.ChangeType;
import org.plasma.sdo.PathAssembler;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaSetting;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.Property;

public class CoreChange implements Change {
    
    private static Log log = LogFactory.getFactory().getInstance(Change.class);
    private static List<ChangeSummary.Setting> emptySettingList = 
        new ArrayList<ChangeSummary.Setting>();
    
    private DataObject dataObject;
    private ChangeType changeType; 
    private Map<String, List<PlasmaSetting>> propetyMap;
    private String pathFromRoot;
    private int pathDepthFromRoot;
    
    public CoreChange(DataObject dataObject, ChangeType changeType) {
        this.dataObject = dataObject;
        this.changeType = changeType;

        if (log.isDebugEnabled())
            log.debug("calculating min path for graph: "
                    + ((PlasmaDataObject)dataObject.getDataGraph().getRootObject()).dump());
        PathAssembler visitor = new CorePathAssembler(dataObject);
        ((PlasmaNode)dataObject.getDataGraph().getRootObject()).accept(visitor);
        pathFromRoot = visitor.getMinimumPathString();
        if (log.isDebugEnabled())
            log.debug("selected min path: " + this.pathFromRoot);
        pathDepthFromRoot = visitor.getMinimumPathDepth();
    }   
    
    public DataObject getDataObject() {
        return dataObject;
    }

    public ChangeType getChangeType() {
        return changeType;
    }  
     
    public List<PlasmaSetting> getSettings(String propertyName) {
        return propetyMap.get(propertyName);
    }
    
    public void add(Property property, Object value) {
        if (propetyMap == null) {
            propetyMap = new HashMap<String, List<PlasmaSetting>>();
        }
        
        List<PlasmaSetting> dataObjectPropertySettings = propetyMap.get(property.getName());
        if (dataObjectPropertySettings == null) {
            dataObjectPropertySettings = new ArrayList<PlasmaSetting>();
            propetyMap.put(property.getName(), dataObjectPropertySettings);
        }           
        
        dataObjectPropertySettings.add(new CoreSetting(dataObject,
                property, value));            
    }
    
    public List<ChangeSummary.Setting> getAllSettings() {
        
        if (propetyMap == null || propetyMap.size() == 0)
            return emptySettingList;
        
        List<ChangeSummary.Setting> result = new ArrayList<ChangeSummary.Setting>();
        
        Iterator<String> iter = propetyMap.keySet().iterator();
        while (iter.hasNext()) {
            List<PlasmaSetting> propertySettings = propetyMap.get(iter.next()); 
            for (PlasmaSetting setting : propertySettings)
                result.add(setting);
        }
        
        return result;                
    }

    public String getPathFromRoot() {
        return pathFromRoot;
    } 
    
    public int getPathDepthFromRoot() {
        return pathDepthFromRoot;
    }
}


