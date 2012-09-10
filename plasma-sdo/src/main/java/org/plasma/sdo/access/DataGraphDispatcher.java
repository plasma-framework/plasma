package org.plasma.sdo.access;

import org.plasma.sdo.core.SnapshotMap;

import commonj.sdo.DataGraph;

public interface DataGraphDispatcher {

    public SnapshotMap commit(DataGraph dataGraph);
    public void close();
}
