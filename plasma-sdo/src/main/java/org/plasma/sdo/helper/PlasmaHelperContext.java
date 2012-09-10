package org.plasma.sdo.helper;


import commonj.sdo.helper.CopyHelper;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.DataHelper;
import commonj.sdo.helper.EqualityHelper;
import commonj.sdo.helper.HelperContext;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XMLHelper;
import commonj.sdo.helper.XSDHelper;

public class PlasmaHelperContext implements HelperContext {

    static public PlasmaHelperContext INSTANCE = initializeInstance();

    private PlasmaHelperContext() {       
    }
    
    public static PlasmaHelperContext instance()
    {
        if (INSTANCE == null)
            initializeInstance();
        return INSTANCE;
    }
 
    private static synchronized PlasmaHelperContext initializeInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new PlasmaHelperContext();
        return INSTANCE;
    }
    
	public CopyHelper getCopyHelper() {
		return PlasmaCopyHelper.INSTANCE;
	}

	public DataFactory getDataFactory() {
		return PlasmaDataFactory.INSTANCE;
	}

	public DataHelper getDataHelper() {
		return PlasmaDataHelper.INSTANCE;
	}

	public EqualityHelper getEqualityHelper() {
		return PlasmaEqualityHelper.INSTANCE;
	}

	public TypeHelper getTypeHelper() {
		return PlasmaTypeHelper.INSTANCE;
	}

	public XMLHelper getXMLHelper() {
		return PlasmaXMLHelper.INSTANCE;
	}

	public XSDHelper getXSDHelper() {
		return PlasmaXSDHelper.INSTANCE;
	}

}
