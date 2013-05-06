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
package org.plasma.sdo.jdbc.service;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.core.CoreDataObject;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

public abstract class CommitComparator implements Comparator<CoreDataObject>, Serializable {

	private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getFactory().getInstance(
    		CommitComparator.class);
    
    protected boolean isSingularRelation(DataObject target, DataObject source) {
    	return (((PlasmaType)target.getType()).isRelation((PlasmaType)source.getType(), 
    			AssociationPath.singular));
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
