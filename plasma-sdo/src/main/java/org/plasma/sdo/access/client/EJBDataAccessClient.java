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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.naming.ServiceLocator;
import org.plasma.query.Query;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.DataAccessService;
import org.plasma.sdo.core.SnapshotMap;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;

/**
 */
public class EJBDataAccessClient implements DataAccessClient
{
    private static Log log = LogFactory.getLog(EJBDataAccessClient.class);  
    
    private static DataAccessService service;
    
    public DataGraph[] find(Query query)
    {
        try {
            log.debug("calling local persistence service..."); 
            DataAccessService service = locateService();
            DataGraph[] results = service.find(query.getModel());  
            if (log.isDebugEnabled()) {
                log.debug("returned " + String.valueOf(results.length) + " results roots");      
            }
            return results;                                                                 
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }       
    }

    public DataGraph[] find(Query query, int maxResults)
    {
        try {
            log.debug("calling persistence service..."); 
            DataAccessService service = locateService();
            DataGraph[] results = service.find(query.getModel(), maxResults);                                    
            if (log.isDebugEnabled()) {
                log.debug("returned " + String.valueOf(results.length) + " results roots");      
            }
            return results;                                                                 
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }       
    }

    public List find(Query[] queries)
    {
        try {
            log.debug("calling persistence service...");                                    
            org.plasma.query.model.Query[] queryModels = new org.plasma.query.model.Query[queries.length];
            for (int i = 0; i < queries.length; i++)
            	queryModels[i] = (org.plasma.query.model.Query)queries[i].getModel();
            DataAccessService service = locateService();
            List results = service.find(queryModels);                                          
            if (log.isDebugEnabled()) {
                log.debug("returned " + String.valueOf(results.size()) + " results roots");     
            }
            return results;  
        }                                                              
        catch (Exception e) {
            throw new DataAccessException(e);
        }       
    }

    public int count(Query query)
    {
        try {
            log.debug("calling persistence service...");                                     
            DataAccessService service = locateService();
            return service.count(query.getModel());                                   
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }        
    }

    public int[] count(Query[] queries)
    {
        try {
            log.debug("calling persistence service...");                                     
            DataAccessService service = locateService();
            org.plasma.query.model.Query[] queryModels = new org.plasma.query.model.Query[queries.length];
            for (int i = 0; i < queries.length; i++)
            	queryModels[i] = (org.plasma.query.model.Query)queries[i].getModel();
            return service.count(queryModels);                                   
        }
        catch (Exception e) {
            log.info("caught4: " + e.getClass().getName());
            throw new DataAccessException(e);
        }     
    }

    public void commit(DataGraph dataGraph, String username) {
        SnapshotMap idMap = service.commit(dataGraph, username);  
        List<DataObject> changedObjects = dataGraph.getChangeSummary().getChangedDataObjects();
        for (DataObject dataObject : changedObjects)
            if (!dataGraph.getChangeSummary().isDeleted(dataObject))
                ((PlasmaNode)dataObject).getDataObject().reset(idMap, username);
        dataGraph.getChangeSummary().endLogging();
        dataGraph.getChangeSummary().beginLogging();
        if (log.isDebugEnabled()) {
            log.debug("committed 1 data-graph");                                
        }
    }   

    public void commit(DataGraph[] dataGraphs, String username) {
        SnapshotMap idMap = service.commit(dataGraphs, username);  
        for (int i = 0; i < dataGraphs.length; i++) {
            DataGraph dataGraph = dataGraphs[i];
            List<DataObject> changedObjects = dataGraph.getChangeSummary().getChangedDataObjects();
            for (DataObject dataObject : changedObjects)
                if (!dataGraph.getChangeSummary().isDeleted(dataObject))
                    ((PlasmaNode)dataObject).getDataObject().reset(idMap, username);
            dataGraph.getChangeSummary().endLogging();
            dataGraph.getChangeSummary().beginLogging();
        }        
        if (log.isDebugEnabled()) {
            log.debug("committed 1 data-graph");                                
        }
    }   
    
    private DataAccessService locateService() throws Exception
    {
    	if (service == null) {
    	    service = (DataAccessService)ServiceLocator.getInstance().getStub(DataAccessService.class);
    	}
        return service;
    }

}