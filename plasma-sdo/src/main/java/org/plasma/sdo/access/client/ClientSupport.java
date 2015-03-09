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
package org.plasma.sdo.access.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.PlasmaDataAccessService;

public abstract class ClientSupport {
	
    protected PlasmaDataAccessService createProvider(String qualifiedName) {
	    try {
	        Class<?> providerClass = Class.forName(qualifiedName); 
	        Class<?>[] argClasses = {};
	        Object[] args = {};
	        Constructor<?> constructor = providerClass.getConstructor(argClasses);
	        return (PlasmaDataAccessService)constructor.newInstance(args);   
	    } 
	    catch (ClassNotFoundException e) {
	        throw new DataAccessException(e);
	    }    
	    catch (NoSuchMethodException e) {
	        throw new DataAccessException(e);
	    }    
	    catch (InstantiationException e) {
	        throw new DataAccessException(e);
	    }    
	    catch (IllegalAccessException e) {
	        throw new DataAccessException(e);
	    }    
	    catch (InvocationTargetException e) {
	        throw new DataAccessException(e);
	    }    
    }

}
