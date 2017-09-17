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

package org.plasma.sdo.jdbc.service;

// java imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.query.bind.PlasmaQueryDataBinding;
import org.plasma.query.collector.PropertySelectionCollector;
import org.plasma.query.model.From;
import org.plasma.query.model.GroupBy;
import org.plasma.query.model.OrderBy;
import org.plasma.query.model.Query;
import org.plasma.query.model.QueryConstants;
import org.plasma.query.model.Select;
import org.plasma.query.model.Variable;
import org.plasma.query.model.Where;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.QueryVisitor;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.MaxResultsExceededException;
import org.plasma.sdo.access.QueryDispatcher;
import org.plasma.sdo.access.provider.common.DataObjectHashKeyAssembler;
import org.plasma.sdo.access.provider.common.PropertyPair;
import org.plasma.sdo.access.provider.jdbc.AliasMap;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.jdbc.connect.RDBConnectionManager;
import org.plasma.sdo.jdbc.filter.FilterAssembler;
import org.plasma.sdo.jdbc.filter.GroupingDeclarationAssembler;
import org.plasma.sdo.jdbc.filter.OrderingDeclarationAssembler;
import org.xml.sax.SAXException;

import commonj.sdo.Type;

public class GraphQuery extends JDBCSupport implements QueryDispatcher {
  private static Log log = LogFactory.getLog(GraphQuery.class);

  public GraphQuery() {
  }

  public PlasmaDataGraph[] find(Query query, Timestamp snapshotDate) {
    return find(query, -1, snapshotDate);
  }

  public PlasmaDataGraph[] find(Query query, int requestMax, Timestamp snapshotDate) {
    Connection con = null;
    try {
      con = RDBConnectionManager.instance().getConnection();
    } catch (SQLException e2) {
      throw new DataAccessException(e2);
    }
    From from = query.getFromClause();
    PlasmaType type = (PlasmaType) PlasmaTypeHelper.INSTANCE.getType(from.getEntity()
        .getNamespaceURI(), from.getEntity().getName());

    PropertySelectionCollector collector = new PropertySelectionCollector(query.getSelectClause(),
        type);
    collector.setOnlySingularProperties(false);
    collector.setOnlyDeclaredProperties(false); // collect from superclasses
    List<List<PropertyPair>> queryResults = findResults(query, collector, type, con);

    GraphAssembler assembler = new GraphAssembler(type, collector, snapshotDate, con);

    if (log.isDebugEnabled()) {
      log.debug("assembling results");
    }

    PlasmaDataGraph[] results = null;
    try {
      if (!query.getSelectClause().hasDistinctProperties())
        results = assembleResults(queryResults, requestMax, assembler);
      else
        results = trimResults(queryResults, requestMax, assembler, query.getSelectClause(), type);
    } finally {
      try {
        con.close();
      } catch (SQLException e) {
        log.error(e.getMessage(), e);
      }
    }

    return results;
  }

  /**
   * Returns a count of the given query. This does NOT return any results but
   * causes a "count(*)" to be issued.
   * 
   * @param query
   *          the Query Object Model (QOM) query
   * @return the query results size
   */
  public int count(Query query) {
    Connection con = null;
    try {
      con = RDBConnectionManager.instance().getConnection();
    } catch (SQLException e2) {
      throw new DataAccessException(e2);
    }
    From from = query.getFromClause();
    PlasmaType type = (PlasmaType) PlasmaTypeHelper.INSTANCE.getType(from.getEntity()
        .getNamespaceURI(), from.getEntity().getName());
    int size = this.countResults(con, query, type);
    try {
      con.close();
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    }
    return size;
  }

  /**
   * Iterate over the given results collection up to the given limit, assembling
   * a data-object graph for each result. In the simplest case, only a single
   * root data-object is assembled.
   * 
   * @param collection
   *          - the results collection
   * @param requestMax
   *          - the requested max or limit of items required in the results. If
   *          results exceed the given maximum, then they are truncated. If no
   *          maximum value is given (e.g. -1) then a default max is enforced.
   * @param assembler
   *          - the value object assembler
   * @throws MaxResultsExceededException
   *           when no request maximum is given and the default max is exceeded.
   */
  private PlasmaDataGraph[] assembleResults(List<List<PropertyPair>> collection, int requestMax,
      GraphAssembler assembler) {
    ArrayList<PlasmaDataGraph> list = new ArrayList<PlasmaDataGraph>(20);
    Iterator<List<PropertyPair>> iter = collection.iterator();
    for (int i = 1; iter.hasNext(); i++) {
      List<PropertyPair> pairs = iter.next();

      if (requestMax <= 0) {
        if (i > QueryConstants.MAX_RESULTS) // note: this is cheezy but checking
                                            // the collection size is expensive
          throw new MaxResultsExceededException(i, QueryConstants.MAX_RESULTS);
      } else if (i > requestMax) {
        if (log.isDebugEnabled()) {
          log.debug("truncating results at " + String.valueOf(requestMax));
        }
        break;
      }
      assembler.assemble(pairs);

      list.add(assembler.getDataGraph());
      assembler.clear();
    }
    PlasmaDataGraph[] results = new PlasmaDataGraph[list.size()];
    list.toArray(results);
    if (log.isDebugEnabled()) {
      log.debug("assembled " + String.valueOf(results.length) + " results");
    }
    return results;
  }

  /**
   * Iterates over the given results collection pruning value-object results
   * after assembly based on any "distinct" property(ies) in the results object
   * graph. Results must be pruned/trimmed after assembly because the assembly
   * invokes POM traversals/queries where the "distinct" propertiy(ies) are
   * realized. For each result, a hash key is assembled to determine it's
   * uniqueness based on a search of the assembled value-object graph for
   * "distinct" properties as defined in the given Query select clause.
   * 
   * @param collection
   *          - the results collection
   * @param requestMax
   *          - the requested max or limit of items required in the results. If
   *          results exceed the given maximum, then they are truncated. If no
   *          maximum value is given (e.g. -1) then a default max is enforced.
   * @param assembler
   *          - the value object assembler
   * @param select
   *          the Query select clause
   * @param type
   *          the candidate Type definition
   * @throws MaxResultsExceededException
   *           when no request maximum is given and the default max is exceeded.
   */
  private PlasmaDataGraph[] trimResults(List<List<PropertyPair>> collection, int requestMax,
      GraphAssembler assembler, Select select, Type type) {
    DataObjectHashKeyAssembler hashKeyAssembler = new DataObjectHashKeyAssembler(select, type);
    // FIXME rely on non-ordered map to retain initial ordering and get
    // rid of list collection.
    Map<String, PlasmaDataObject> distinctMap = new HashMap<String, PlasmaDataObject>(20);
    List<PlasmaDataObject> distinctList = new ArrayList<PlasmaDataObject>(20);
    Iterator<List<PropertyPair>> iter = collection.iterator();

    int i;
    for (i = 1; iter.hasNext(); i++) {
      if (requestMax <= 0 && i > QueryConstants.MAX_RESULTS) // note: this is
                                                             // cheezy but
                                                             // checking the
                                                             // collection size
                                                             // is expensive
        throw new MaxResultsExceededException(i, QueryConstants.MAX_RESULTS);
      List<PropertyPair> pairs = iter.next();
      assembler.assemble(pairs);
      PlasmaDataGraph dataGraph = assembler.getDataGraph();
      assembler.clear();
      String key = hashKeyAssembler.getHashKey((PlasmaDataObject) dataGraph.getRootObject());
      if (distinctMap.get(key) == null) {
        if (requestMax <= 0) {
          if (i > QueryConstants.MAX_RESULTS) // note: this is cheezy but
                                              // checking the collection size is
                                              // expensive
            throw new MaxResultsExceededException(distinctList.size(), QueryConstants.MAX_RESULTS);
        } else if (i > requestMax) {
          if (log.isDebugEnabled()) {
            log.debug("truncating results at " + String.valueOf(requestMax));
          }
          break;
        }
        distinctMap.put(key, (PlasmaDataObject) dataGraph.getRootObject());
        distinctList.add((PlasmaDataObject) dataGraph.getRootObject());
      }
    }
    PlasmaDataGraph[] trimmedResults = new PlasmaDataGraph[distinctList.size()];
    for (i = 0; i < trimmedResults.length; i++)
      trimmedResults[i] = (PlasmaDataGraph) distinctList.get(i).getDataGraph();

    if (log.isDebugEnabled()) {
      log.debug("assembled " + String.valueOf(trimmedResults.length) + " results out of "
          + String.valueOf(i));
    }
    return trimmedResults;
  }

  private int countResults(Connection con, Query query, PlasmaType type) {
    int result = 0;
    Object[] params = new Object[0];
    FilterAssembler filterAssembler = null;

    StringBuilder sqlQuery = new StringBuilder();
    AliasMap aliasMap = new AliasMap(type);

    sqlQuery.append("SELECT COUNT(*)");
    sqlQuery.append(aliasMap.getAlias(type));
    Statement statement = null;
    ResultSet rs = null;

    try {
      Where where = query.findWhereClause();
      if (where != null) {
        filterAssembler = new FilterAssembler(where, type, aliasMap);
        sqlQuery.append(" ");
        sqlQuery.append(filterAssembler.getFilter());
        params = filterAssembler.getParams();

        if (log.isDebugEnabled()) {
          log.debug("filter: " + filterAssembler.getFilter());
        }
      } else {
        sqlQuery.append(" FROM ");
        sqlQuery.append(getQualifiedPhysicalName(type));
        sqlQuery.append(" ");
        sqlQuery.append(aliasMap.getAlias(type));
      }
      if (query.getStartRange() != null && query.getEndRange() != null)
        log.warn("query range (start: " + query.getStartRange() + ", end: " + query.getEndRange()
            + ") ignored for count operation");

      if (log.isDebugEnabled()) {
        log.debug("queryString: " + sqlQuery.toString());
        log.debug("executing...");
      }

      statement = con
          .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      statement.execute(sqlQuery.toString());
      rs = statement.getResultSet();
      rs.first();
      result = rs.getInt(1);
    } catch (Throwable t) {
      StringBuffer buf = this.generateErrorDetail(t, sqlQuery.toString(), filterAssembler);
      log.error(buf.toString());
      throw new DataAccessException(t);
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (statement != null)
          statement.close();
      } catch (SQLException e) {
        log.error(e.getMessage(), e);
      }
    }
    return result;
  }

  private List<List<PropertyPair>> findResults(Query query, PropertySelectionCollector collector,
      PlasmaType type, Connection con) {
    Object[] params = new Object[0];
    RDBDataConverter converter = RDBDataConverter.INSTANCE;

    if (log.isDebugEnabled()) {
      log(query);
    }

    AliasMap aliasMap = new AliasMap(type);

    Map<Type, List<String>> selectMap = collector.getResult();

    // construct a filter adding to alias map
    FilterAssembler filterAssembler = null;
    Where where = query.findWhereClause();
    if (where != null) {
      filterAssembler = new FilterAssembler(where, type, aliasMap);
      params = filterAssembler.getParams();
    }

    OrderingDeclarationAssembler orderingDeclAssembler = null;
    OrderBy orderby = query.findOrderByClause();
    if (orderby != null)
      orderingDeclAssembler = new OrderingDeclarationAssembler(orderby, type, aliasMap);
    GroupingDeclarationAssembler groupingDeclAssembler = null;
    GroupBy groupby = query.findGroupByClause();
    if (groupby != null)
      groupingDeclAssembler = new GroupingDeclarationAssembler(groupby, type, aliasMap);

    String rootAlias = aliasMap.getAlias(type);
    StringBuilder sqlQuery = new StringBuilder();
    sqlQuery.append("SELECT ");

    int i = 0;
    List<String> names = selectMap.get(type);
    for (String name : names) {
      PlasmaProperty prop = (PlasmaProperty) type.getProperty(name);
      if (prop.isMany() && !prop.getType().isDataType())
        continue;
      if (i > 0)
        sqlQuery.append(", ");
      sqlQuery.append(rootAlias);
      sqlQuery.append(".");
      sqlQuery.append(prop.getPhysicalName());
      i++;
    }

    // construct a FROM clause from alias map
    sqlQuery.append(" FROM ");
    Iterator<PlasmaType> it = aliasMap.getTypes();
    int count = 0;
    while (it.hasNext()) {
      PlasmaType aliasType = it.next();
      String alias = aliasMap.getAlias(aliasType);
      if (count > 0)
        sqlQuery.append(", ");
      sqlQuery.append(getQualifiedPhysicalName(aliasType));
      sqlQuery.append(" ");
      sqlQuery.append(alias);
      count++;
    }

    // append WHERE filter
    if (filterAssembler != null) {
      sqlQuery.append(" ");
      sqlQuery.append(filterAssembler.getFilter());
    }

    // set the result range
    // FIXME: Oracle specific
    if (query.getStartRange() != null && query.getEndRange() != null) {
      if (where == null)
        sqlQuery.append(" WHERE ");
      else
        sqlQuery.append(" AND ");
      sqlQuery.append("ROWNUM >= ");
      sqlQuery.append(String.valueOf(query.getStartRange()));
      sqlQuery.append(" AND ROWNUM <= ");
      sqlQuery.append(String.valueOf(query.getEndRange()));
    }

    if (orderingDeclAssembler != null) {
      sqlQuery.append(" ");
      sqlQuery.append(orderingDeclAssembler.getOrderingDeclaration());
    }

    if (groupingDeclAssembler != null) {
      sqlQuery.append(" ");
      sqlQuery.append(groupingDeclAssembler.getGroupingDeclaration());
    }

    List<List<PropertyPair>> rows = new ArrayList<List<PropertyPair>>();
    PreparedStatement statement = null;
    ResultSet rs = null;
    try {
      statement = con.prepareStatement(sqlQuery.toString(), ResultSet.TYPE_FORWARD_ONLY,/*
                                                                                         * ResultSet
                                                                                         * .
                                                                                         * TYPE_SCROLL_INSENSITIVE
                                                                                         * ,
                                                                                         */
          ResultSet.CONCUR_READ_ONLY);

      // set params
      // note params are pre-converted
      // to string in filter assembly
      // FIXME: are parameters relevant in SQL in this context?
      if (filterAssembler != null) {
        params = filterAssembler.getParams();
        if (params != null)
          for (i = 0; i < params.length; i++)
            statement.setString(i + 1, String.valueOf(params[i]));
      }

      if (log.isDebugEnabled()) {
        if (params == null || params.length == 0) {
          log.debug("executing: " + sqlQuery.toString());
        } else {
          StringBuilder paramBuf = new StringBuilder();
          paramBuf.append(" [");
          for (int p = 0; p < params.length; p++) {
            if (p > 0)
              paramBuf.append(", ");
            paramBuf.append(String.valueOf(params[p]));
          }
          paramBuf.append("]");
          log.debug("executing: " + sqlQuery.toString() + " " + paramBuf.toString());
        }
      }

      statement.execute();
      rs = statement.getResultSet();
      int numcols = rs.getMetaData().getColumnCount();
      ResultSetMetaData rsMeta = rs.getMetaData();
      List<PropertyPair> row = null;
      PropertyPair pair = null;
      while (rs.next()) {
        row = new ArrayList<PropertyPair>();
        rows.add(row);
        for (i = 1; i <= numcols; i++) {
          String columnName = rsMeta.getColumnName(i);
          int columnType = rsMeta.getColumnType(i);
          PlasmaProperty prop = (PlasmaProperty) type.getProperty(columnName);
          Object value = converter.fromJDBCDataType(rs, i, columnType, prop);
          pair = new PropertyPair(prop, value);
          pair.setColumn(i);
          row.add(pair);
        }
      }
    } catch (Throwable t) {
      StringBuffer buf = this.generateErrorDetail(t, sqlQuery.toString(), filterAssembler);
      log.error(buf.toString());
      throw new DataAccessException(t);
    } finally {
      try {
        if (rs != null)
          rs.close();
        if (statement != null)
          statement.close();
      } catch (SQLException e) {
        log.error(e.getMessage(), e);
      }
    }
    return rows;
  }

  private StringBuffer generateErrorDetail(Throwable t, String queryString,
      FilterAssembler filterAssembler) {
    StringBuffer buf = new StringBuffer(2048);
    buf.append("QUERY FAILED: ");
    buf.append(t.getMessage());
    buf.append(" \n");
    if (queryString != null) {
      buf.append("queryString: ");
      buf.append(queryString);
      buf.append(" \n");
    }
    if (filterAssembler != null) {
      if (filterAssembler.hasImportDeclarations()) {
        buf.append("import decl: ");
        buf.append(filterAssembler.getImportDeclarations());
        buf.append(" \n");
      }
      Object[] params = filterAssembler.getParams();
      if (params != null) {
        buf.append("parameters: [");
        for (int i = 0; i < params.length; i++) {
          if (i > 0)
            buf.append(", ");
          buf.append(String.valueOf(params[i]));
        }
        buf.append("]");
        buf.append(" \n");
      }
      if (filterAssembler.hasParameterDeclarations()) {
        buf.append("param decl: ");
        buf.append(filterAssembler.getParameterDeclarations());
        buf.append(" \n");
      }
      if (filterAssembler.hasVariableDeclarations()) {
        buf.append("variable decl: ");
        buf.append(filterAssembler.getVariableDeclarations());
        buf.append(" \n");
      }
    }
    return buf;
  }

  public List<Variable> getVariables(Where where) {
    final List<Variable> list = new ArrayList<Variable>(1);
    QueryVisitor visitor = new DefaultQueryVisitor() {
      public void start(Variable var) {
        list.add(var);
      }
    };
    where.accept(visitor);
    return list;
  }

  public void close() {
    // TODO Auto-generated method stub

  }

  protected void log(Query root) {
    String xml = "";
    PlasmaQueryDataBinding binding;
    try {
      binding = new PlasmaQueryDataBinding(new DefaultValidationEventHandler());
      xml = binding.marshal(root);
    } catch (JAXBException e) {
      log.debug(e);
    } catch (SAXException e) {
      log.debug(e);
    }
    log.debug("where: " + xml);
  }

}
