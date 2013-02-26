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
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;

public abstract class SimpleCollector {

    private static Log log = LogFactory.getFactory().getInstance(
    		SimpleCollector.class);
    protected List<PlasmaDataObject> result = new ArrayList<PlasmaDataObject>();
    protected PlasmaChangeSummary changeSummary;
    protected Map<String, Boolean> isDescendantResultsMap = new HashMap<String, Boolean>();
   
    public List<PlasmaDataObject> getResult() {
        return result;
    } 
    
    protected boolean isRelation(DataObject target, DataObject source, AssociationPath relationPath) {
    	return (((PlasmaType)target.getType()).isRelation((PlasmaType)source.getType(), 
    			AssociationPath.singular));
    }
    
    protected boolean hasChildLink(DataObject target, DataObject source) {
        
        if (log.isDebugEnabled())
            log.debug("comparing "+ target.getType().getName() 
                    + "/" + source.getType().getName());
        
        // look at properties in target, check if linked to source
        List<ChangeSummary.Setting> settings = 
            target.getDataGraph().getChangeSummary().getOldValues(target);
        
        for (ChangeSummary.Setting setting : settings) {
            Property property = setting.getProperty();
            if (property.getType().isDataType()) 
                continue;               
            
            // FIXME - equality method for Type ??
            if (!property.getType().getName().equals(source.getType().getName()))
                continue;
                                   
            if (log.isDebugEnabled())
                log.debug("checking property " + target.getType().getName()
                        + "." + property.getName());
            if (isLinked(source, setting.getValue()))
            {
                // singular property linked to source
                if (!property.isMany()) {
                    if (log.isDebugEnabled())
                        log.debug("found child data link " + target.getType().getName()
                                + "." + property.getName()
                                + "->" + source.getType().getName());
                    if (target.getType().getName().equals(source.getType().getName()))
                        throw new IllegalStateException("potential circular reference");
                    return true; 
                }    
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
