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
    private Map<String, List<PlasmaSetting>> settings;
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
    
    public boolean equals(Object obj) {
    	CoreChange other = (CoreChange)obj;
        return this.dataObject.equals(other.dataObject);    	
    }
    
    public int hashCode() {
    	return this.dataObject.hashCode();
    }
    
    public DataObject getDataObject() {
        return dataObject;
    }

    public ChangeType getChangeType() {
        return changeType;
    }  
     
    public List<PlasmaSetting> getSettings(String propertyName) {
        return settings.get(propertyName);
    }
    
    public void add(Property property, Object value) {
        if (settings == null) {
            settings = new HashMap<String, List<PlasmaSetting>>();
        }
        
        List<PlasmaSetting> dataObjectPropertySettings = settings.get(property.getName());
        if (dataObjectPropertySettings == null) {
            dataObjectPropertySettings = new ArrayList<PlasmaSetting>();
            settings.put(property.getName(), dataObjectPropertySettings);
        }           
        
        dataObjectPropertySettings.add(new CoreSetting(dataObject,
                property, value));            
    }
    
    public List<ChangeSummary.Setting> getAllSettings() {
        
        if (settings == null || settings.size() == 0)
            return emptySettingList;
        
        List<ChangeSummary.Setting> result = new ArrayList<ChangeSummary.Setting>();
        
        Iterator<String> iter = settings.keySet().iterator();
        while (iter.hasNext()) {
            List<PlasmaSetting> propertySettings = settings.get(iter.next()); 
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


