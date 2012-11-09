package org.plasma.sdo.helper;

import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.core.CoreDataObject;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
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

    static public PlasmaCopyHelper INSTANCE = initializeInstance();

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
     *   The value of such a Property property in copiedDataObject is: 
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
     *     Logging state is the same as the source ChangeSummary.
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
     * @param dataObject to be copied
     * @return copy of dataObject 
     */    
    public DataObject copyShallow(DataObject dataObject) {
        
    	CoreDataObject result = (CoreDataObject)PlasmaDataFactory.INSTANCE.create(dataObject.getType());
        Object value = null;
        for (Property property : dataObject.getType().getProperties())
        {
            value = dataObject.get(property);
            if (value == null)
                continue;
            if (property.getType().isDataType()) {
                if (!property.isReadOnly())
                    result.set(property, value);
                else
                    result.getValueObject().put(property.getName(), value);                    
            }
        }
        return result;
    }

    /**
     * Create a deep copy of the DataObject tree:
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
     *   If a ChangeSummary is part of the copy tree the new 
     *     ChangeSummary refers to objects in the new DataObject tree.
     *     Logging state is the same as the source ChangeSummary.
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
     * @return copy of dataObject
     * @throws IllegalArgumentException if any referenced DataObject
     *   is not part of the containment tree.
     */
    public DataObject copy(DataObject dataObject) {
    	DataGraphCopyVisitor visitor = new DataGraphCopyVisitor();
    	((PlasmaDataObject)dataObject).accept(visitor); 
        return visitor.getResult();
    }
}
