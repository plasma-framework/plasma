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
package org.plasma.sdo.jdbc.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.query.bind.PlasmaQueryDataBinding;
import org.plasma.query.model.From;
import org.plasma.query.model.Query;
import org.plasma.query.model.QueryValidator;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.DataGraphDispatcher;
import org.plasma.sdo.access.PlasmaDataAccessService;
import org.plasma.sdo.core.SnapshotMap;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.jdbc.connect.RDBConnectionManager;
import org.xml.sax.SAXException;

import commonj.sdo.DataGraph;
import commonj.sdo.Type;

public class JDBCGraphService implements PlasmaDataAccessService {

  private static Log log = LogFactory.getLog(JDBCGraphService.class);

  public JDBCGraphService() {
  }

  public void initialize() {
  }

  public void close() {
  }

  public int count(Query query) {
    if (query == null)
      throw new IllegalArgumentException("expected non-null 'query' argument");
    validate(query);
    if (log.isDebugEnabled()) {
      log(query);
    }
    GraphQuery dispatcher = new GraphQuery();
    return dispatcher.count(query);
  }

  public int[] count(Query[] queries) {
    if (queries == null)
      throw new IllegalArgumentException("expected non-null 'queries' argument");
    int[] counts = new int[queries.length];
    for (int i = 0; i < queries.length; i++)
      counts[i] = count(queries[i]);
    return counts;
  }

  public DataGraph[] find(Query query) {
    if (query == null)
      throw new IllegalArgumentException("expected non-null 'query' argument");
    // validate(query);
    if (log.isDebugEnabled()) {
      log(query);
    }
    GraphQuery dispatcher = new GraphQuery();
    Timestamp snapshotDate = new Timestamp((new Date()).getTime());
    return dispatcher.find(query, snapshotDate);
  }

  public DataGraph[] find(Query query, int maxResults) {
    if (query == null)
      throw new IllegalArgumentException("expected non-null 'query' argument");
    validate(query);
    if (log.isDebugEnabled()) {
      log(query);
    }
    GraphQuery dispatcher = new GraphQuery();
    DataGraph[] results = dispatcher.find(query, -1, new Timestamp((new Date()).getTime()));
    return results;
  }

  public List<DataGraph[]> find(Query[] queries) {
    if (queries == null)
      throw new IllegalArgumentException("expected non-null 'queries' argument");
    GraphQuery dispatcher = new GraphQuery();
    List<DataGraph[]> list = new ArrayList<DataGraph[]>();
    Timestamp snapshotDate = new Timestamp((new Date()).getTime());
    for (int i = 0; i < queries.length; i++) {
      validate(queries[i]);
      if (log.isDebugEnabled()) {
        log(queries[i]);
      }
      DataGraph[] results = dispatcher.find(queries[i], snapshotDate);
      list.add(results);
    }
    return list;
  }

  public SnapshotMap commit(DataGraph dataGraph, String username) {
    if (dataGraph == null)
      throw new IllegalArgumentException("expected non-null 'dataGraph' argument");
    if (username == null)
      throw new IllegalArgumentException("expected non-null 'username' argument");
    if (username.trim().length() == 0)
      throw new IllegalArgumentException("unexpected zero length 'username' argument");
    SnapshotMap snapshotMap = new SnapshotMap(new Timestamp((new Date()).getTime()));
    Connection con = null;
    try {
      con = RDBConnectionManager.instance().getConnection();
      con.setAutoCommit(false);
    } catch (SQLException e2) {
      throw new DataAccessException(e2);
    }
    DataGraphDispatcher dispatcher = new GraphDispatcher(snapshotMap, username, con);
    try {
      dispatcher.commit(dataGraph);
      con.commit();
      return snapshotMap;
    } catch (DataAccessException e) {
      if (log.isDebugEnabled())
        log.debug(e.getMessage(), e);
      try {
        con.rollback();
      } catch (SQLException e1) {
        log.error(e.getMessage(), e1);
      }
      throw e;
    } catch (Throwable t) {
      if (log.isDebugEnabled())
        log.debug(t.getMessage(), t);
      try {
        con.rollback();
      } catch (SQLException e) {
        log.error(e.getMessage(), e);
      }
      throw new DataAccessException(t);
    } finally {

      try {
        con.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
      dispatcher.close();
    }
  }

  public SnapshotMap commit(DataGraph[] dataGraphs, String username) {
    if (dataGraphs == null)
      throw new IllegalArgumentException("expected non-null 'dataGraphs' argument");
    if (username == null)
      throw new IllegalArgumentException("expected non-null 'username' argument");
    if (username.trim().length() == 0)
      throw new IllegalArgumentException("unexpected zero length 'username' argument");
    SnapshotMap snapshotMap = new SnapshotMap(new Timestamp((new Date()).getTime()));
    Connection con = null;
    try {
      con = RDBConnectionManager.instance().getConnection();
      con.setAutoCommit(false);
    } catch (SQLException e2) {
      throw new DataAccessException(e2);
    }
    DataGraphDispatcher dispatcher = new GraphDispatcher(snapshotMap, username, con);

    try {
      for (int i = 0; i < dataGraphs.length; i++) {
        if (log.isDebugEnabled())
          log.debug("commiting: " + dataGraphs[i].getChangeSummary().toString());
        dispatcher.commit(dataGraphs[i]);
      }
      con.commit();
      return snapshotMap;
    } catch (DataAccessException e) {
      try {
        con.rollback();
      } catch (SQLException e1) {
        log.error(e1.getMessage(), e1);
      }
      throw e;
    } catch (Throwable t) {
      try {
        con.rollback();
      } catch (SQLException e) {
        log.error(e.getMessage(), e);
      }
      throw new DataAccessException(t);
    } finally {
      try {
        con.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
      dispatcher.close();
    }
  }

  private void validate(Query query) {
    From from = (From) query.getFromClause();
    Type type = PlasmaTypeHelper.INSTANCE.getType(from.getEntity().getNamespaceURI(), from
        .getEntity().getName());
    log.debug("validating query");
    new QueryValidator((Query) query, type);
  }

  private void log(Query query) {
    String xml = "";
    PlasmaQueryDataBinding binding;
    try {
      binding = new PlasmaQueryDataBinding(new DefaultValidationEventHandler());
      xml = binding.marshal(query);
    } catch (JAXBException e) {
    } catch (SAXException e) {
    }
    log.debug("query: " + xml);
  }
}
