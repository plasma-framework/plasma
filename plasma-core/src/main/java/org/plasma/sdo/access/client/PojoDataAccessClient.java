/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.sdo.access.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.query.Query;
import org.plasma.runtime.DataAccessProviderName;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.access.PlasmaDataAccessService;
import org.plasma.sdo.core.SnapshotMap;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;

/**
 */
public class PojoDataAccessClient extends ClientSupport implements DataAccessClient {
  private static Log log = LogFactory.getLog(PojoDataAccessClient.class);

  protected PlasmaDataAccessService service;

  public PojoDataAccessClient() {
    PlasmaRuntime config = PlasmaRuntime.getInstance();
    if (config.getDefaultProviderName() != null)
      this.service = createProvider(config.getDataAccessProvider(config.getDefaultProviderName())
          .getClassName());
  }

  public PojoDataAccessClient(DataAccessProviderName providerName) {
    this.service = createProvider(PlasmaRuntime.getInstance().getDataAccessProvider(providerName)
        .getClassName());
  }

  public PojoDataAccessClient(PlasmaDataAccessService provider) {
    service = provider;
  }

  public DataGraph[] find(Query query) {
    log.debug("calling data access service...");
    DataGraph[] dataGraphs = service.find(query.getModel());
    if (log.isDebugEnabled()) {
      log.debug("returned " + String.valueOf(dataGraphs.length) + " results graphs");
    }
    for (int i = 0; i < dataGraphs.length; i++) {
      if (log.isDebugEnabled()) {
        log.debug(dataGraphs[i].toString());
      }
      dataGraphs[i].getChangeSummary().beginLogging();
    }

    return dataGraphs;
  }

  public DataGraph[] find(Query query, int maxResults) {
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

  public List<DataGraph[]> find(Query[] queries) {
    log.debug("calling data access service...");
    org.plasma.query.model.Query[] queryModels = new org.plasma.query.model.Query[queries.length];
    for (int i = 0; i < queries.length; i++)
      queryModels[i] = (org.plasma.query.model.Query) queries[i].getModel();

    List<DataGraph[]> resultList = service.find(queryModels);
    if (log.isDebugEnabled()) {
      log.debug("returned " + String.valueOf(resultList.size()) + " results lists");
    }

    Iterator<DataGraph[]> iter = resultList.iterator();
    for (int i = 0; iter.hasNext(); i++) {
      DataGraph[] result = iter.next();

      List<DataObject> list = new ArrayList<DataObject>(result.length);

      for (int j = 0; j < result.length; j++) {
        result[j].getChangeSummary().beginLogging();
      }
    }

    return resultList;
  }

  public int count(Query query) {
    log.debug("calling data access service...");
    return service.count(query.getModel());
  }

  public int[] count(Query[] queries) {
    log.debug("calling data access service...");
    org.plasma.query.model.Query[] queryModels = new org.plasma.query.model.Query[queries.length];
    for (int i = 0; i < queries.length; i++)
      queryModels[i] = (org.plasma.query.model.Query) queries[i].getModel();
    return service.count(queryModels);
  }

  public void commit(DataGraph dataGraph, String username) {

    SnapshotMap idMap = service.commit(dataGraph, username);
    List<DataObject> changedObjects = dataGraph.getChangeSummary().getChangedDataObjects();
    for (DataObject dataObject : changedObjects)
      if (!dataGraph.getChangeSummary().isDeleted(dataObject))
        ((PlasmaNode) dataObject).getDataObject().reset(idMap, username);
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
          ((PlasmaNode) dataObject).getDataObject().reset(idMap, username);
      dataGraph.getChangeSummary().endLogging();
      dataGraph.getChangeSummary().beginLogging();
    }
    if (log.isDebugEnabled()) {
      log.debug("committed " + dataGraphs.length + " data-graph(s)");
    }
  }

}