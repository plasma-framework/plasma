package org.plasma.sdo.access;

import java.sql.Timestamp;
import java.util.Date;

import org.plasma.query.model.Query;
import org.plasma.sdo.PlasmaDataGraph;

public interface QueryDispatcher {
    
    public int count(Query query);
    public PlasmaDataGraph[] find(Query query, Timestamp snapshotDate);
    public PlasmaDataGraph[] find(Query query, int requestMax, Timestamp snapshotDate);
    public void close();

}
