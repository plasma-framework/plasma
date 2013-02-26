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

import org.plasma.sdo.access.RequiredPropertyException;

import commonj.sdo.DataObject;

@Deprecated
public class CoreHelper {
    
    public static Object get(DataObject dataObject, String propertyName) {
        return ((CoreDataObject)dataObject).getValueObject().get(propertyName);
    }

    public static void set(DataObject dataObject, String propertyName, Object value) {
        ((CoreDataObject)dataObject).getValueObject().put(propertyName, value);
    }

    public static void unset(DataObject dataObject, String propertyName) {
        ((CoreDataObject)dataObject).getValueObject().remove(propertyName);
    }
 
    public static boolean isFlaggedLocked(DataObject dataObject)
    {
        Boolean value = (Boolean)CoreHelper.get(dataObject, CoreConstants.PROPERTY_NAME_ENTITY_LOCKED);
        return value != null ? value.booleanValue() : false;
    }

    public static boolean isFlaggedUnlocked(DataObject dataObject)
    {
        Boolean value = (Boolean)CoreHelper.get(dataObject, CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED);
        return value != null ? value.booleanValue() : false;
    }
    public static void flagLocked(DataObject dataObject)
    {
        if (!CoreHelper.isFlaggedLocked(dataObject)) {
          //FIXME: manual bypass of setting history
            CoreHelper.set(dataObject, CoreConstants.PROPERTY_NAME_ENTITY_LOCKED, new Boolean(true));  
        }
    }

    public static void unflagLocked(DataObject dataObject)
    {
      //FIXME: manual bypass of setting history
        ((CoreDataObject)dataObject).getValueObject().remove(CoreConstants.PROPERTY_NAME_ENTITY_LOCKED);
    }

    public static void flagUnlocked(DataObject dataObject)
    {
      //FIXME: manual bypass of setting history
        if (!CoreHelper.isFlaggedUnlocked(dataObject)) {
            CoreHelper.set(dataObject, CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED, new Boolean(true));
        }
    }

    public static void unflagUnlocked(DataObject dataObject)
    {
      //FIXME: manual bypass of setting history
        ((CoreDataObject)dataObject).getValueObject().remove(CoreConstants.PROPERTY_NAME_ENTITY_UNLOCKED);
    }

    @Deprecated
    public static String createHashKeyFromUUID(DataObject dataObject) {
        
        String uuid = (String)((CoreDataObject)dataObject).getUUIDAsString();
        if (uuid == null)
            throw new RequiredPropertyException("UUID is required for entity '" 
                + dataObject.getType().getURI() + "#" + dataObject.getType().getName() + "'");        
            
        return uuid;
    }

    
    
}
