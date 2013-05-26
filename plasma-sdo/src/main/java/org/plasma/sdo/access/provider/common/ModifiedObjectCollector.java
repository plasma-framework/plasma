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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;

public class ModifiedObjectCollector implements PlasmaDataGraphVisitor {
    private static Log log = LogFactory.getFactory().getInstance(
            ModifiedObjectCollector.class);
    private HashSet<PlasmaDataObject> result = new HashSet<PlasmaDataObject>();
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
                this.result.add((PlasmaDataObject)target);
        }
    }
    
    public List<PlasmaDataObject> getResult() {
    	PlasmaDataObject[] array = new PlasmaDataObject[this.result.size()];
    	this.result.toArray();
        return Arrays.asList(array);
    } 
}

