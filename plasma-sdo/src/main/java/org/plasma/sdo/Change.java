package org.plasma.sdo;

import java.util.List;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.Property;

public interface Change {
    
    public DataObject getDataObject();

    public ChangeType getChangeType();
     
    public List<PlasmaSetting> getSettings(String propertyName);
    
    public void add(Property property, Object value);
    
    public List<ChangeSummary.Setting> getAllSettings();

    public String getPathFromRoot();
    
    public int getPathDepthFromRoot();
}


