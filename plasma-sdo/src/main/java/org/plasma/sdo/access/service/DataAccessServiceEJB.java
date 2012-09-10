package org.plasma.sdo.access.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.DataAccessProviderName;
import org.plasma.config.PlasmaConfig;
import org.plasma.query.model.Query;
import org.plasma.sdo.access.DataAccessService;
import org.plasma.sdo.core.SnapshotMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.PlasmaDataAccessService;

import commonj.sdo.DataGraph;

@Stateless
@Local
@TransactionManagement (TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NEVER)
public class DataAccessServiceEJB implements DataAccessService 
{ 
    private static Log log = LogFactory.getLog(DataAccessServiceEJB.class);

    private org.plasma.sdo.access.PlasmaDataAccessService service;
  
    
    @PostConstruct
    public void initialize()
    {
        try {
            if (service == null)
            {
            	service = createProvider(
            			PlasmaConfig.getInstance().getDataAccessProvider(
            					DataAccessProviderName.JDBC).getClassName());
                service.initialize(); 
            }
        }
        catch (RuntimeException e) {
            log.error(e.getMessage(), e); // 
            throw e;
        }
    } 

    public void close() {
        if (service != null)
        {
            service.close();
            service = null;
        }
    }
    
    @PreDestroy 
    public void preDestroy() { 
        log.debug("preDestroy()");
        close();
    }   

    public DataGraph[] find(Query query) 
    {
        if (log.isDebugEnabled()) {
            log.debug("find(Query query) - (" + this.toString() + ")"); 
        }
        if (service == null)
        {
        	service = createProvider(
        			PlasmaConfig.getInstance().getDataAccessProvider(
        					DataAccessProviderName.JDBC).getClassName());
            service.initialize(); 
        }
        return service.find(query);
    }

    public DataGraph[] find(Query query, int maxResults) 
    {
        if (log.isDebugEnabled()) {
            log.debug("find(Query query) - (" + this.toString() + ")"); 
        }
        if (service == null)
        {
        	service = createProvider(
        			PlasmaConfig.getInstance().getDataAccessProvider(
        					DataAccessProviderName.JDBC).getClassName());
            service.initialize(); 
        }
        return service.find(query, maxResults);
    }

    public List find(Query[] queries) 
    {
        if (log.isDebugEnabled()) {
            log.debug("find(Query[] queries) - (" + this.toString() + ")"); 
        }
        
        if (service == null)
        {
        	service = createProvider(
        			PlasmaConfig.getInstance().getDataAccessProvider(
        					DataAccessProviderName.JDBC).getClassName());
            service.initialize(); 
        }
        return service.find(queries);
    }
    
    public int[] count(Query[] queries) 
    {
        if (log.isDebugEnabled()) {
            log.debug("count(Query[] queries) - (" + this.toString() + ")"); 
        }
        
        if (service == null)
        {
        	service = createProvider(
        			PlasmaConfig.getInstance().getDataAccessProvider(
        					DataAccessProviderName.JDBC).getClassName());
            service.initialize(); 
        }
        return service.count(queries);
    }

    public int count(Query query) 
    {
        if (log.isDebugEnabled()) {
            log.debug("count(Query query) - (" + this.toString() + ")"); 
        }
        if (service == null)
        {
        	service = createProvider(
        			PlasmaConfig.getInstance().getDataAccessProvider(
        					DataAccessProviderName.JDBC).getClassName());
            service.initialize(); 
        }
        return service.count(query);
    }

    @Override
    public SnapshotMap commit(DataGraph dataGraph, String username) {
        if (service == null)
        {
        	service = createProvider(
        			PlasmaConfig.getInstance().getDataAccessProvider(
        					DataAccessProviderName.JDBC).getClassName());
            service.initialize(); 
        }
        return service.commit(dataGraph, username);
    }

    @Override
    public SnapshotMap commit(DataGraph[] dataGraphs, String username) {
        if (service == null)
        {
        	service = createProvider(
        			PlasmaConfig.getInstance().getDataAccessProvider(
        					DataAccessProviderName.JDBC).getClassName());
            service.initialize(); 
        }
        return service.commit(dataGraphs, username);
    }

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
