package org.plasma.sdo;


import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * A change summary is used to record changes to DataObjects,
 * allowing applications to efficiently and incrementally update back-end 
 * storage when required.
 */
public interface PlasmaChangeSummary extends ChangeSummary {

    /**
     * Register the given data object as a created object
     * within this change summary.
     * @param dataObject the created data object
     */
    public void created(DataObject dataObject);

    /**
     * Register the given data object as a deleted object
     * within this change summary.
     * @param dataObject the deleted data object
     */
    public void deleted(DataObject dataObject);
    
    /**
     * Register the given data object as a modified object
     * within this change summary for the given property.
     * @param dataObject the modified data object
     * @param property the modified property
     * @param oldValue the previous value for the given property 
     */
    public void modified(DataObject dataObject, Property property, Object oldValue);
    
    /**
     * Removes all change information for the given data object
     * within this change summary.
     * @param dataObject the data object
     */
    public void clear(DataObject dataObject);

    /**
     * Returns the path depth within the data graph for the given
     * data object.
     * @param dataObject the data object
     * @return the path depth
     */
    public int getPathDepth(DataObject dataObject);
    
    
}
