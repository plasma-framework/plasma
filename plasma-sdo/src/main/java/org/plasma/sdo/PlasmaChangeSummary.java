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
