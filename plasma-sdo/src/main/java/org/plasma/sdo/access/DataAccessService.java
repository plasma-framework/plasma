package org.plasma.sdo.access;

import java.util.List;

import org.plasma.query.model.Query;
import org.plasma.sdo.core.SnapshotMap;

import commonj.sdo.DataGraph;

public interface DataAccessService 
{ 
    public void initialize();
    public void close();
    
    public int count(Query query);
    public int[] count(Query[] queries);
        
    public DataGraph[] find(Query query);
    public DataGraph[] find(Query query, int maxResults);
    public List<DataGraph[]> find(Query[] queries);
    
    public SnapshotMap commit(DataGraph dataGraph, String username);   
    public SnapshotMap commit(DataGraph[] dataGraphs, String username);
} 
