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
