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

// java imports
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.exception.ErrorConstants;
import org.plasma.common.exception.UserException;
import org.plasma.query.MaxWildcardsExceededException;
import org.plasma.query.Query;
import org.plasma.sdo.access.InvalidSnapshotException;
import org.plasma.sdo.access.LockedEntityException;
import org.plasma.sdo.access.MaxResultsExceededException;

import commonj.sdo.DataGraph;

/**
 */
public class SDODataAccessClient implements DataAccessClient {
  private static Log log = LogFactory.getLog(SDODataAccessClient.class);

  private DataAccessClient serviceProxy;

  public SDODataAccessClient() {
    serviceProxy = new PojoDataAccessClient();
  }

  public SDODataAccessClient(DataAccessClient proxy) {
    this.serviceProxy = proxy;
  }

  public DataGraph[] find(Query query) {
    DataGraph[] results = null;
    try {
      results = serviceProxy.find(query);
    } catch (MaxResultsExceededException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_INPUT, ErrorConstants.ERROR_MESSAGE_MAX_RESULTS_EXCEEDED,
          new Object[] { new Integer(e.getMaxResults()) }, "max results exceeded");
    } catch (MaxWildcardsExceededException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_INPUT,
          ErrorConstants.ERROR_MESSAGE_MAX_WILDCARDS_EXCEEDED, "max wildcards exceeded");
    }
    return results;

  }

  public DataGraph[] find(Query query, int maxResults) {
    DataGraph[] results = null;
    try {
      results = serviceProxy.find(query, maxResults);
    } catch (MaxResultsExceededException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_INPUT, ErrorConstants.ERROR_MESSAGE_MAX_RESULTS_EXCEEDED,
          new Object[] { new Integer(e.getMaxResults()) }, "max results exceeded");
    } catch (MaxWildcardsExceededException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_INPUT,
          ErrorConstants.ERROR_MESSAGE_MAX_WILDCARDS_EXCEEDED, "max wildcards exceeded");
    }
    return results;
  }

  public List<DataGraph[]> find(Query[] queries) {
    List<DataGraph[]> valueList = null;

    try {
      valueList = serviceProxy.find(queries);
    } catch (MaxResultsExceededException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_INPUT, ErrorConstants.ERROR_MESSAGE_MAX_RESULTS_EXCEEDED,
          new Object[] { new Integer(e.getMaxResults()) }, "max results exceeded");
    } catch (MaxWildcardsExceededException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_INPUT,
          ErrorConstants.ERROR_MESSAGE_MAX_WILDCARDS_EXCEEDED, "max wildcards exceeded");
    }

    return valueList;
  }

  public int count(Query query) {
    return serviceProxy.count(query);
  }

  public int[] count(Query[] queries) {
    return serviceProxy.count(queries);
  }

  public void commit(DataGraph dataGraph, String username) {
    try {
      serviceProxy.commit(dataGraph, username);
    } catch (LockedEntityException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_CONCURRENCY, ErrorConstants.ERROR_MESSAGE_RECORD_LOCKED,
          new Object[] { e.getUserName(), e.getLockedDate() }, "locked entity");
    } catch (InvalidSnapshotException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_CONCURRENCY,
          ErrorConstants.ERROR_MESSAGE_INVALID_SNAPSHOT, new Object[] { e.getUpdatedByUser(),
              e.getUpdatedDate() },
          "invalid query snapshot - entity already updated by another user");
    }
  }

  public void commit(DataGraph[] dataGraphs, String username) {
    try {
      serviceProxy.commit(dataGraphs, username);
    } catch (LockedEntityException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_CONCURRENCY, ErrorConstants.ERROR_MESSAGE_RECORD_LOCKED,
          new Object[] { e.getUserName(), e.getLockedDate() }, "locked entity");
    } catch (InvalidSnapshotException e) {
      throw new UserException(ErrorConstants.ERROR_SEVERITY_FATAL,
          ErrorConstants.ERROR_TYPE_USER_CONCURRENCY,
          ErrorConstants.ERROR_MESSAGE_INVALID_SNAPSHOT, new Object[] { e.getUpdatedByUser(),
              e.getUpdatedDate() },
          "invalid query snapshot - entity already updated by another user");
    }
  }

}