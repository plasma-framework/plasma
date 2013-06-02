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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.Change;
import org.plasma.sdo.ChangeType;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaSetting;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Sequence;

/**
 * A change summary is used to record changes to DataObjects,
 * allowing applications to efficiently and incrementally update back-end 
 * storage when required.
 */
public class CoreChangeSummary implements PlasmaChangeSummary, Serializable {

	private static final long serialVersionUID = 1L;


	private static Log log = LogFactory.getFactory().getInstance(CoreChangeSummary.class);

    
    private Map<UUID, CoreChange> changedDataObjects;
    
    private PlasmaDataGraph dataGraph;
    
    private long loggingBeganMillis = -1;
    private long loggingEndedMillis;
    
    @SuppressWarnings("unused")
    private CoreChangeSummary() {}
    
    public CoreChangeSummary(PlasmaDataGraph dataGraph) {
        this.dataGraph = dataGraph;
    }
    
    public void created(DataObject dataObject) {
        if (!isLogging())
            return;

        if (changedDataObjects == null) {
            changedDataObjects = new HashMap<UUID, CoreChange>();
        }    
        
        PlasmaNode dataNode = (PlasmaNode)dataObject;
        UUID hashKey = dataNode.getUUID();
    	CoreChange dataObjectSettings = changedDataObjects.get(hashKey);
        if (dataObjectSettings == null)
        {
        	if (log.isDebugEnabled())
        		log.debug("created: " + dataObject.getType().getURI() + "#" 
        				+ dataObject.getType().getName() + "(" + hashKey + ")");
            dataObjectSettings = new CoreChange(dataObject, 
            		ChangeType.CREATED, changedDataObjects);
            changedDataObjects.put(hashKey, dataObjectSettings);
        }
        else
            throw new IllegalStateException("attempt to set data object to 'created' where"
            		+ " existing settings found");
    }

    public void deleted(DataObject dataObject) {
        if (!isLogging())
            return;

        if (changedDataObjects == null) {
            changedDataObjects = new HashMap<UUID, CoreChange>();
        }    
        
        PlasmaNode dataNode = (PlasmaNode)dataObject;
        UUID hashKey = dataNode.getUUID();
    	CoreChange dataObjectSettings = changedDataObjects.get(dataNode.getUUID());
        if (dataObjectSettings == null)
        {
        	if (log.isDebugEnabled())
        		log.debug("deleted: " + dataObject.getType().getURI() + "#" 
        				+ dataObject.getType().getName() + "(" + hashKey + ")");
            dataObjectSettings = new CoreChange(dataObject, 
            	ChangeType.DELETED, changedDataObjects);
            changedDataObjects.put(hashKey, dataObjectSettings);
        }
        else {            
            // could have been modified as a result of deleting
            // a linked object. Just ensure only reference properties
            // were changed
            if (ChangeType.MODIFIED.ordinal() == 
                dataObjectSettings.getChangeType().ordinal()) {
                List<Setting> settings = dataObjectSettings.getAllSettings();
                for (Setting setting : settings)
                    if (setting.getProperty().getType().isDataType())
                        throw new IllegalArgumentException("found unexpected modified property '"
                            + setting.getProperty().getName() 
                            + "' for delete operation on data-object (" + dataNode.getUUIDAsString() + ") of type "
                            + dataObject.getType().getURI() + "#" 
                            + dataObject.getType().getName());
                
                // overwrite any modified ref property settings
            	if (log.isDebugEnabled())
            		log.debug("deleted: " + dataObject.getType().getURI() + "#" 
            				+ dataObject.getType().getName() + "(" + hashKey + ")");
                dataObjectSettings = new CoreChange(dataObject, 
                	ChangeType.DELETED, changedDataObjects);
                changedDataObjects.put(hashKey, dataObjectSettings);
            }  
            else 
                throw new IllegalArgumentException("found unexpected "
                    + dataObjectSettings.getChangeType().toString()
                    + " data-object (" + hashKey + ") of type "
                    + dataObject.getType().getURI() + "#" 
                    + dataObject.getType().getName());
        }
    }
    
    public void modified(DataObject dataObject, Property property, Object value) {
        
        if (!isLogging())
            return;

        if (changedDataObjects == null) {
            changedDataObjects = new HashMap<UUID, CoreChange>();
        }    
        
        PlasmaNode dataNode = (PlasmaNode)dataObject;
        UUID hashKey = dataNode.getUUID();
        if (isCreated(dataObject)) {
            Change dataObjectSettings = changedDataObjects.get(hashKey);
            if (dataObjectSettings == null)
                throw new IllegalArgumentException("expected created data-object");
            // as per SDO spec don't log old value changes for created DO's
            return;
        }            
        
        CoreChange dataObjectSettings = changedDataObjects.get(hashKey);
        if (dataObjectSettings == null) {
            dataObjectSettings = new CoreChange(dataObject, 
            	ChangeType.MODIFIED, changedDataObjects);
            changedDataObjects.put(hashKey, dataObjectSettings);
        }
    	if (log.isDebugEnabled())
    		log.debug("modified: " + dataObject.getType().getURI() + "#" 
    			+ dataObject.getType().getName() + "." + property.getName() 
    			+ "(" + hashKey + ")");
        dataObjectSettings.add(property, value);        
    }   
    
    public void clear(DataObject dataObject) {
        
        if (!isLogging())
            return;

        if (changedDataObjects == null) {
            return;
        }    
        
        PlasmaNode dataNode = (PlasmaNode)dataObject;
    	String hashKey = dataNode.getUUIDAsString();
        changedDataObjects.remove(hashKey);
    	if (log.isDebugEnabled())
    		log.debug("clear: " + dataObject.getType().getURI() + "#" 
    			+ dataObject.getType().getName() 
    			+ "(" + hashKey + ")");
    }   
    
    
    /**
     * Clears the List of {@link #getChangedDataObjects changes} and turns change logging on.
     * No operation occurs if logging is already on.
     * @see #endLogging
     * @see #isLogging
     */
    public void beginLogging() {        
        if (this.loggingBeganMillis > 0)
            throw new IllegalStateException("logging already active - first call endLogging()");
        if (changedDataObjects != null)
            changedDataObjects.clear();
        this.loggingBeganMillis = System.currentTimeMillis();
    }

    /**
     * An implementation that requires logging may throw an UnsupportedOperationException.
     * Turns change logging off.  No operation occurs if logging is already off.
     * @see #beginLogging
     * @see #isLogging
     */
    public void endLogging() {
        this.loggingBeganMillis = -1;
        this.loggingEndedMillis = System.currentTimeMillis();
    }

    /**
     * Returns a list consisting of all the {@link DataObject data objects} that 
     * have been changed while {@link #isLogging logging}.
     * <p>
     * The {@link #isCreated new} and {@link #isModified modified} objects in the 
     * List are references to objects
     * associated with this ChangeSummary. 
     * The {@link #isDeleted deleted} objects in the List are references to objects 
     * at the time that event logging was enabled; 
     * <p> Each changed object must have exactly one of the following methods return true:
     *   {@link #isCreated isCreated}, 
     *   {@link #isDeleted isDeleted}, or
     *   {@link #isModified isModified}.
     * @return a list of changed data objects.
     * @see #isCreated(DataObject)
     * @see #isDeleted(DataObject)
     * @see #isModified(DataObject)
     */
    public List<DataObject> getChangedDataObjects() {
        
        List<DataObject> result = new ArrayList<DataObject>();
        if (changedDataObjects != null) {
        	Collection<CoreChange> coll = changedDataObjects.values();
        	CoreChange[] changes = new CoreChange[coll.size()];
        	coll.toArray(changes);        	
        	Arrays.sort(changes, new CoreChangeComparator());
        
            for (Change change : changes) {
                result.add(change.getDataObject());
            }    
        }
        return result;
    }

    /**
     * Returns the {@link DataGraph data graph} associated with this change summary or null.
     * @return the data graph.
     * @see DataGraph#getChangeSummary
     */
    public DataGraph getDataGraph() {
        return this.dataGraph;
    }

    /**
     * Returns the value of the {@link DataObject#getContainer container} data object
     * at the point when logging {@link #beginLogging() began}.
     * @param dataObject the object in question.
     * @return the old container data object.
     */
    public DataObject getOldContainer(DataObject dataObject) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns the value of the {@link DataObject#getContainmentProperty containment property} data object property
     * at the point when logging {@link #beginLogging() began}.
     * @param dataObject the object in question.
     * @return the old containment property.
     */
    public Property getOldContainmentProperty(DataObject dataObject) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns the value of the {@link DataObject#getSequence sequence} for the data object
     * at the point when logging {@link #beginLogging() began}.
     * @param dataObject the object in question.
     * @return the old containment property.
     */
    public Sequence getOldSequence(DataObject dataObject) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns a {@link ChangeSummary.Setting setting} for the specified property
     * representing the property value of the given <code>dataObject</code>
     * at the point when logging {@link #beginLogging() began}.
     * <p>Returns null if the property was not modified and 
     * has not been {@link #isDeleted(DataObject) deleted}. 
     * @param dataObject the object in question.
     * @param property the property of the object.
     * @return the Setting for the specified property.
     * @see #getChangedDataObjects
     */
    public Setting getOldValue(DataObject dataObject, Property property) {
        
        if (dataObject == null)
            throw new IllegalArgumentException("expected data-object arg");
        if (property == null)
            throw new IllegalArgumentException("expected property arg");
        
        PlasmaNode dataNode = (PlasmaNode)dataObject;
        if (changedDataObjects == null)
            return null;
        Change dataObjectSettings = changedDataObjects.get(dataNode.getUUID());
        if (dataObjectSettings == null)
            return null;
        
        List<PlasmaSetting> propertySettings = dataObjectSettings.getSettings(property.getName());
        if (propertySettings == null)
            return null;
        
        if (propertySettings.size() == 0)
            throw new IllegalStateException("expected settings list with at least 1 member");
        
        return propertySettings.get(0);
    }

    /**
     * Returns a list of {@link ChangeSummary.Setting settings} 
     * that represent the property values of the given <code>dataObject</code>
     * at the point when logging {@link #beginLogging() began}.
     * <p>In the case of a {@link #isDeleted(DataObject) deleted} object, 
     * the List will include settings for all the Properties.
     * <p> An old value setting indicates the value at the
     * point logging begins.  A setting is only produced for 
     * {@link #isModified modified} objects if 
     * either the old value differs from the current value or
     * if the isSet differs from the current value. 
     * <p> No settings are produced for {@link #isCreated created} objects.
     * @param dataObject the object in question.
     * @return a list of settings.
     * @see #getChangedDataObjects
     */
    public List<ChangeSummary.Setting> getOldValues(DataObject dataObject) {
        List<ChangeSummary.Setting> result = new ArrayList<ChangeSummary.Setting>();
        PlasmaNode dataNode = (PlasmaNode)dataObject;
        Change dataObjectSettings = changedDataObjects.get(dataNode.getUUID());
        if (dataObjectSettings == null)
            return result;
        
        return dataObjectSettings.getAllSettings();
    }

    /**
     * Returns the ChangeSummary root DataObject - the object from which 
     * changes are tracked.  
     * When a DataGraph is used, this is the same as getDataGraph().getRootObject().
     * @return the ChangeSummary root DataObject
     */
    public DataObject getRootObject() {
        return this.dataGraph.getRootObject();
    }

    /**
     * Returns whether or not the specified data object was created while {@link #isLogging logging}.
     * Any object that was added to the scope
     * but was not in the scope when logging began, 
     * will be considered created.
     * @param dataObject the data object in question.
     * @return <code>true</code> if the specified data object was created.
     * @see #getChangedDataObjects
     */
    public boolean isCreated(DataObject dataObject) { 
        if (dataObject == null)
            throw new IllegalArgumentException("expected non-null data-object argument");
        PlasmaNode dataNode = (PlasmaNode)dataObject;
        if (changedDataObjects != null) {
            Change info = changedDataObjects.get(dataNode.getUUID());
            return info != null && info.getChangeType() == ChangeType.CREATED;
        }
        else
            return false;
    }
    
    /**
     * Returns whether or not the specified data object was updated while {@link #isLogging logging}.
     * An object that was contained in the scope when logging began
     * and remains in the scope when logging ends will be considered potentially modified.
     * <p> An object considered modified must have at least one old value setting.
     * @param dataObject the data object in question.
     * @return <code>true</code> if the specified data object was modified.
     * @see #getChangedDataObjects
     */
    public boolean isModified(DataObject dataObject) {
        if (dataObject == null)
            throw new IllegalArgumentException("expected non-null data-object argument");
        PlasmaNode dataNode = (PlasmaNode)dataObject;
        if (changedDataObjects != null) {
            Change info = changedDataObjects.get(dataNode.getUUID());
            return info != null && info.getChangeType() == ChangeType.MODIFIED;
        }
        else
            return false;
    }

    /**
     * Returns whether or not the specified data object was deleted while {@link #isLogging logging}.
     * Any object that is not in scope but was in scope when logging began 
     * will be considered deleted.
     * @param dataObject the data object in question.
     * @return <code>true</code> if the specified data object was deleted.
     * @see #getChangedDataObjects
     */
    public boolean isDeleted(DataObject dataObject) {
        if (dataObject == null)
            throw new IllegalArgumentException("expected non-null data-object argument");
        PlasmaNode dataNode = (PlasmaNode)dataObject;
        if (changedDataObjects != null) {
            Change info = changedDataObjects.get(dataNode.getUUID());
            return info != null && info.getChangeType() == ChangeType.DELETED;
        }
        else
            return false;
    }

    public int getPathDepth(DataObject dataObject) {
        if (dataObject == null)
            throw new IllegalArgumentException("expected non-null data-object argument");
        PlasmaNode dataNode = (PlasmaNode)dataObject;
        Change info = changedDataObjects.get(dataNode.getUUID());
        if (info == null)
            throw new IllegalArgumentException("given data-object (" +
                    dataNode.getUUIDAsString() + ") not registed in change summary");
        return info.getPathDepthFromRoot();
    }
    
    
    /**
     * Indicates whether change logging is on (<code>true</code>) or off (<code>false</code>).
     * @return <code>true</code> if change logging is on.
     * @see #beginLogging
     * @see #endLogging
     */
    public boolean isLogging() {
        return this.loggingBeganMillis > 0;
    }


    /**
     * This method is intended for use by service implementations only.
     * Undoes all changes in the log to restore the tree of 
     * DataObjects to its original state when logging began.
     * isLogging() is unchanged.  The log is cleared.
     * @see #beginLogging
     * @see #endLogging
     * @see #isLogging
     */
    public void undoChanges() {
    	//FIXME: 
    }
    
    public String toString() {
        final StringBuffer result = new StringBuffer();
        result.append("\n\r");
        result.append("<ChangeSummary logging=\"" 
                + String.valueOf(this.isLogging()) + ">");        
        List<DataObject> changedObjects = this.getChangedDataObjects();
        for (DataObject dataObject : changedObjects) {
            PlasmaNode dataNode = (PlasmaNode)dataObject;
            Change info = changedDataObjects.get(dataNode.getUUID());
            List<Setting> settings = info.getAllSettings();

            result.append("\n\t<" + info.getChangeType().toString().toLowerCase() 
                    + " type=\"" + dataObject.getType().getName() + "\""
                    + " path=\"" + info.getPathFromRoot() + "\""
                    + " id=\"" + ((PlasmaNode)dataObject).getUUIDAsString() + "\"");
            if (settings.size() == 0)
                result.append("\\>");
            else
                result.append(">");
            
            if (settings != null) {
                for (Setting setting : settings) {
                    PlasmaSetting plasmaSetting = (PlasmaSetting)setting;
                    result.append("\n\t\t<property name=\"" + setting.getProperty().getName() + "\"");
                    result.append(" isSet=\"" + setting.isSet() + "\"");
                    if (setting.getProperty().getType().isDataType())
                        result.append(" value=\"" + String.valueOf(setting.getValue()) + "\"");
                    else
                        result.append(" value=\"" + plasmaSetting.getValuePath() + "\"");
                    result.append(" timestamp=\"" + String.valueOf(((PlasmaSetting)setting).getCreatedMillis()) + "\"");
                    result.append("</property>");
                }
            }
            if (settings.size() > 0)
                result.append("<\\" + info.getChangeType().toString().toLowerCase() + ">");
        }
        result.append("\n</ChangeSummary>");
        return result.toString();
/*
 path-based change summary XML
         
<sdo:datagraph xmlns:company="company.xsd"
xmlns:sdo="commonj.sdo">
<changeSummary create="E0004" delete="E0002">
<company sdo:ref="#/company:company[1]"
name="ACME" employeeOfTheMonth="E0002"/>
<departments sdo:ref="#/company:company[1]/departments[1]">
<employees sdo:ref="E0001"/>
<employees name="Mary Smith" SN="E0002" manager="true"/>
<employees sdo:ref="E0003"/>
</departments>
</changeSummary>
<company:company name="MegaCorp" employeeOfTheMonth="E0004">
<departments name="Advanced Technologies"
location="NY" number="123">
<employees name="John Jones" SN="E0001"/>
<employees name="Jane Doe" SN="E0003"/>
<employees name="Al Smith" SN="E0004" manager="true"/>
</departments>
</company:company>
</sdo:datagraph>         
         
 */
        
        
    }
    
    
}
