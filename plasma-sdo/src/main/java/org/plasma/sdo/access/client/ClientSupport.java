package org.plasma.sdo.access.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.plasma.config.PlasmaConfig;
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
