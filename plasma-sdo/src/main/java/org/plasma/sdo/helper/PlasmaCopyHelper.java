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
package org.plasma.sdo.helper;

import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.core.CoreConstants;
import org.plasma.sdo.core.CoreDataObject;
import org.plasma.sdo.core.CoreNode;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.CopyHelper;

/**
 * Copy utilities class. 
 * <p>
 * Note that copied data objects 
 * have the same data properties as the source but have new  
 * (and therefore different) underlying <a target="#" href="http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html">UUID</a> 
 * and other management properties which are not defined within the
 * source Type. Use copied data objects to help automate and save
 * save effort when creating <b>NEW</b> data objects. To simply link/add 
 * and existing data object to a new data graph, first use {@link DataObject.detach()} to
 * remove it from its graph. Than add it to a another graph.     
 * </p>
 */
public class PlasmaCopyHelper implements CopyHelper {

    static public volatile PlasmaCopyHelper INSTANCE = initializeInstance();

    private PlasmaCopyHelper() {       
    }
    
    public static PlasmaCopyHelper instance()
    {
        if (INSTANCE == null)
            initializeInstance();
        return INSTANCE;
    }
 
    private static synchronized PlasmaCopyHelper initializeInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new PlasmaCopyHelper();
        return INSTANCE;
    }
    
    /**
     * Create a shallow copy of the DataObject dataObject:
     *   Creates a new DataObject copiedDataObject with the same values
     *     as the source dataObject for each property where
     *       property.getType().isDataType() is true.
     *   The value of such a Property in copiedDataObject is: 
     *       dataObject.get(property) for single-valued Properties 
     *       (copiedDataObject.get(property) equals() dataObject.get(property)), or 
     *       a List where each member is equal to the member at the 
     *       same index in dataObject for multi-valued Properties
     *        copiedDataObject.getList(property).get(i) equals() dataObject.getList(property).get(i)
     *   The copied Object is unset for each Property where
     *       property.getType().isDataType() is false
     *       since they are not copied.
     *   Read-only properties are copied.
     *   A copied object shares metadata with the source object
     *     sourceDO.getType() == copiedDO.getType()
     *   If a ChangeSummary is part of the source DataObject
     *     the copy has a new, empty ChangeSummary.
     *   The Logging state in the source ChangeSummary is not replicated in the
     *   target ChangeSummary. All copied data objects are in a 'created' state
     *   ready to be used for insert operations. 
     * 
	 * <p>
	 * Note that in general copied data objects 
	 * have the same data properties as the source but have new  
	 * (and therefore different) underlying <a target="#" href="http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html">UUID</a> 
	 * and other management properties which are not defined within the
	 * source Type. Use copied data objects to help automate and save
	 * save effort when creating <b>NEW</b> data objects. To simply link/add 
	 * and existing data object to a new data graph, first use {@link DataObject.detach()} to
	 * remove it from its graph. Than add it to a another graph.     
	 * </p>
	 * 
     * @param dataObject to be copied
     * @return copy of dataObject 
     */    
    public DataObject copyShallow(DataObject dataObject) {
    	CoreDataObject result = (CoreDataObject)PlasmaDataFactory.INSTANCE.create(
    			dataObject.getType());
		copy((CoreDataObject)dataObject, result);
        return result;
    }
    
    /**
     * Create a shallow copy of the given dataObject but where 
     * the <a target="#" href="http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html">UUID</a>
     * and other instance properties are copied from the given data object copied as well. 
     * <p></p>
     * Note that data objects copied using this method should typically be used as "references" in
     * <b>UPDATE</b> and <b>DELETE</b> operations, in order to avoid unnecessary queries. 
     *   
     * @param dataObject
     * @return the copied data object
     */
    public DataObject copyShallowAsReference(DataObject dataObject) {
    	CoreDataObject source = (CoreDataObject)dataObject;    	
    	CoreDataObject result = (CoreDataObject)PlasmaDataFactory.INSTANCE.create(
    			source.getType());
		((CoreNode)result).setValue(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, 
				((CoreNode)dataObject).getValue(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP)); 
		result.resetUUID(source.getUUID());
		copy(source, result);
   	    return result;
    }
    
    private void copy(CoreDataObject source, CoreDataObject result)
    {
    	PlasmaType type = (PlasmaType)source.getType();   	    	
        Object value = null;
        for (String key : source.getValueObject().getKeys()) {
        	Property property = type.findProperty(key);
        	if (property != null) {
        		value = source.get(property);
        		if (value == null)
        			continue;
        		if (property.getType().isDataType()) {
        			if (!property.isReadOnly()) {
        				result.set(property, value);
        			}
        			else {
        				result.getValueObject().put(property.getName(), value);
        			}
        		}
        	}
        	else { // assume instance property
        		value = source.getValueObject().get(key);
        		if (value == null)
        			continue;
        		result.getValueObject().put(key, value);
        	}
        }        
    }
    
    /**
     * Create a deep copy of the DataObject tree.
     *   Copies the dataObject and all its {@link commonj.sdo.Property#isContainment() contained}
     *     DataObjects recursively.
     *   Values of Properties are copied as in shallow copy,
     *     and values of Properties where 
     *       property.getType().isDataType() is false
     *     are copied where each value copied must be a
     *       DataObject contained by the source dataObject.
     *     If a DataObject is outside the DataObject tree and the
     *       property is bidirectional, then the DataObject is skipped.
     *     If a DataObject is outside the DataObject tree and the
     *       property is unidirectional, then the same DataObject is referenced.
     *   Read-only properties are copied.
     *   If any DataObject referenced is not in the containment
     *     tree an IllegalArgumentException is thrown.
     *   The Logging state in the source ChangeSummary is not replicated in the
     *   target ChangeSummary. All copied data objects are in a 'created' state
     *   ready to be used for insert operations. 
     * 
	 * <p>
	 * Note that in general copied data objects 
	 * have the same data properties as the source but have new  
	 * (and therefore different) underlying <a target="#" href="http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html">UUID</a> 
	 * and other management properties which are not defined within the
	 * source Type. Use copied data objects to help automate and save
	 * save effort when creating <b>NEW</b> data objects. To simply link/add 
	 * an existing data object to a new data graph, first use {@link DataObject.detach()} to
	 * remove it from its graph. Than add it to a another graph.     
	 * </p>
	 *      
	 * @param dataObject to be copied.
     * @return copy of dataObject
     * @throws IllegalArgumentException if any referenced DataObject
     *   is not part of the containment tree.
     */
    public DataObject copy(DataObject dataObject) {
    	DataGraphCopyVisitor visitor = new DataGraphCopyVisitor();
    	((PlasmaDataObject)dataObject).accept(visitor); 
        return visitor.getResult();
    }

    /**
     * Create a deep copy of the given dataObject tree but where 
     * the <a target="#" href="http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html">UUID</a>
     * and other instance properties are copied from the given data object copied as well. 
     * <p></p>
     * Note that data graph and data objects copied using this method should typically be used in
     * <b>UPDATE</b> and <b>DELETE</b> operations on the resulting data graph, in order to avoid 
     * unnecessary queries. To simply link/add 
	 * an existing data object to a new data graph, first use {@link DataObject.detach()} to
	 * remove it from its graph. Than add it to a another graph.  
     *   
     * @param dataObject
     * @return
     */
    public DataObject copyAsReference(DataObject dataObject) {
    	DataGraphCopyVisitor visitor = new DataGraphCopyVisitor();
    	visitor.setCopyUUIDs(true);
    	((PlasmaDataObject)dataObject).accept(visitor); 
        return visitor.getResult();
    }
    
    /**
     * Create a deep copy of the DataObject tree while exempting all
     * DataObject nodes with an SDO type found in the given reference Types
     * array. For all "reference" types found in the source graph, these are
     * not copied but "re-parented" to the target graph and not added to the
     * change summary. Reference nodes are not removed from the source graph. 
     * 
     *   Copies the dataObject and all its {@link commonj.sdo.Property#isContainment() contained}
     *     DataObjects recursively.
     *   Values of Properties are copied as in shallow copy,
     *     and values of Properties where 
     *       property.getType().isDataType() is false
     *     are copied where each value copied must be a
     *       DataObject contained by the source dataObject.
     *     If a DataObject is outside the DataObject tree and the
     *       property is bidirectional, then the DataObject is skipped.
     *     If a DataObject is outside the DataObject tree and the
     *       property is unidirectional, then the same DataObject is referenced.
     *   Read-only properties are copied.
     *   If any DataObject referenced is not in the containment
     *     tree an IllegalArgumentException is thrown.
     *   The Logging state in the source ChangeSummary is not replicated in the
     *   target ChangeSummary. All copied data objects are in a 'created' state
     *   ready to be used for insert operations. 
     * 
	 * <p>
	 * Note that copied data objects 
	 * have the same data properties as the source but have new  
	 * (and therefore different) underlying <a target="#" href="http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html">UUID</a> 
	 * and other management properties which are not defined within the
	 * source Type. Use copied data objects to help automate and save
	 * save effort when creating <b>NEW</b> data objects. To simply link/add 
	 * and existing data object to a new data graph, first use {@link DataObject.detach()} to
	 * remove it from its graph. Than add it to a another graph.     
	 * </p>
	 *      
	 * @param dataObject to be copied.
	 * @param referenceTypes an array of types which are 
     * @return copy of dataObject
     * @throws IllegalArgumentException if any referenced DataObject
     *   is not part of the containment tree.
     */
    public DataObject copy(DataObject dataObject, Type[] referenceTypes) {
    	DataGraphCopyVisitor visitor = new DataGraphCopyVisitor(referenceTypes);
    	((PlasmaDataObject)dataObject).accept(visitor); 
        return visitor.getResult();
    }    
    
}
