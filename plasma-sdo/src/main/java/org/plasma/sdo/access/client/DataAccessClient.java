package org.plasma.sdo.access.client;

import java.util.List;

import org.plasma.query.Query;

import commonj.sdo.DataGraph;

public interface DataAccessClient
{
    public DataGraph[] find(Query query);
    public DataGraph[] find(Query query, int maxResults);    
    public List<DataGraph[]> find(Query[] queries);
    public int count(Query query);
    public int[] count(Query[] queries);
    public void commit(DataGraph dataGraph, String username);
    public void commit(DataGraph[] dataGraphs, String username);
}