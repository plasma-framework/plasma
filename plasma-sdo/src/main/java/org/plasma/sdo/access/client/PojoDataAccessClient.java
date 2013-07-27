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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.DataAccessProviderName;
import org.plasma.config.PlasmaConfig;
import org.plasma.query.Query;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.access.PlasmaDataAccessService;
import org.plasma.sdo.core.SnapshotMap;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;

/**
 */
public class PojoDataAccessClient extends ClientSupport 
    implements DataAccessClient
{
    private static Log log = LogFactory.getLog(PojoDataAccessClient.class);

     
    protected PlasmaDataAccessService service;


    public PojoDataAccessClient()
    {
    	PlasmaConfig config = PlasmaConfig.getInstance();
    	if (config.getDefaultProviderName() != null)
    	    this.service = createProvider(
    			config.getDataAccessProvider(
    					config.getDefaultProviderName()).getClassName());
    }
    
    public PojoDataAccessClient(DataAccessProviderName providerName)
    {
    	this.service = createProvider(
    			PlasmaConfig.getInstance().getDataAccessProvider(
    					providerName).getClassName());
    }

    public PojoDataAccessClient(PlasmaDataAccessService provider)
    {
        service = provider;
    } 
    
    public DataGraph[] find(Query query)
    {
        log.debug("calling data access service..."); 
        DataGraph[] dataGraphs = service.find(query.getModel());   
        if (log.isDebugEnabled()) {
            log.debug("returned " + String.valueOf(dataGraphs.length) + " results graphs");      
        }
        for (int i = 0; i < dataGraphs.length; i++)
        {    
            if (log.isDebugEnabled()) {
                log.debug(dataGraphs[i].toString());
            }    
            dataGraphs[i].getChangeSummary().beginLogging();
        }    
        
        return dataGraphs; 
    }

    public DataGraph[] find(Query query, int maxResults)
    {
        log.debug("calling data access service..."); 
        DataGraph[] dataGraphs = service.find(query.getModel(), maxResults);                                    
        if (log.isDebugEnabled()) {
            log.debug("returned " + String.valueOf(dataGraphs.length) + " results graphs");      
        }
        
        for (int i = 0; i < dataGraphs.length; i++) {
            dataGraphs[i].getChangeSummary().beginLogging();
        }
        
        return dataGraphs;                                                                 
    }

    public List<DataGraph[]> find(Query[] queries)
    {
        log.debug("calling data access service...");  
        org.plasma.query.model.Query[] queryModels = new org.plasma.query.model.Query[queries.length];
        for (int i = 0; i < queries.length; i++)
        	queryModels[i] = (org.plasma.query.model.Query)queries[i].getModel();
        
        List<DataGraph[]> resultList = service.find(queryModels);                                          
        if (log.isDebugEnabled()) {
            log.debug("returned " + String.valueOf(resultList.size()) + " results lists");     
        }
        
        Iterator<DataGraph[]> iter = resultList.iterator();
        for (int i = 0; iter.hasNext(); i++)
        {
            DataGraph[] result = iter.next(); 
            
            List<DataObject> list = new ArrayList<DataObject>(result.length);
            
            for (int j = 0 ; j < result.length; j++) {
                result[j].getChangeSummary().beginLogging();
            }       
        }
        
        return resultList;  
    }

    public int count(Query query)
    {
        log.debug("calling data access service...");                                     
        return service.count(query.getModel());
    }

    public int[] count(Query[] queries)
    {
        log.debug("calling data access service...");                                     
        org.plasma.query.model.Query[] queryModels = new org.plasma.query.model.Query[queries.length];
        for (int i = 0; i < queries.length; i++)
        	queryModels[i] = (org.plasma.query.model.Query)queries[i].getModel();
        return service.count(queryModels);
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
            log.debug("committed " + dataGraphs.length + " data-graph(s)");                                
        }
    }   

}