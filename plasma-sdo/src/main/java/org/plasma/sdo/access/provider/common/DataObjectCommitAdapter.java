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

import org.plasma.sdo.PlasmaDataObject;

import commonj.sdo.DataObject;

public class DataObjectCommitAdapter {
    
    private DataObject dataObject;    

    @SuppressWarnings("unused")
    private DataObjectCommitAdapter() {}
    
    public DataObjectCommitAdapter(DataObject dataObject) {
        this.dataObject = dataObject;
    }

    public DataObject getDataObject() {
        return dataObject;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        DataObjectCommitAdapter otherAdapter = (DataObjectCommitAdapter)obj;
        PlasmaDataObject otherDataObject = (PlasmaDataObject)otherAdapter.getDataObject();
        PlasmaDataObject thisDataObject = (PlasmaDataObject)this.dataObject;
        return thisDataObject.getUUIDAsString().equals(otherDataObject.getUUIDAsString());
    }
}
