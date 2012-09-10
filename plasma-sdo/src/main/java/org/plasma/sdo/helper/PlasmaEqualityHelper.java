package org.plasma.sdo.helper;

import commonj.sdo.DataObject;
import commonj.sdo.helper.EqualityHelper;

public class PlasmaEqualityHelper implements EqualityHelper {

    static public PlasmaEqualityHelper INSTANCE = initializeInstance();

    private PlasmaEqualityHelper() {       
    }
    
    public static PlasmaEqualityHelper instance()
    {
        if (INSTANCE == null)
            initializeInstance();
        return INSTANCE;
    }
 
    private static synchronized PlasmaEqualityHelper initializeInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new PlasmaEqualityHelper();
        return INSTANCE;
    }
    
    @Override
    public boolean equal(DataObject dataObject1, DataObject dataObject2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean equalShallow(DataObject dataObject1, DataObject dataObject2) {
        // TODO Auto-generated method stub
        return false;
    }

}
