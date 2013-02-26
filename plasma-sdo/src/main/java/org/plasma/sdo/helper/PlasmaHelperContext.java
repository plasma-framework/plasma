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
