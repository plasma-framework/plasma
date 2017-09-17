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

package org.plasma.sdo.jdbc.connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.plasma.config.DataAccessProviderName;
import org.plasma.config.PlasmaConfig;
import org.plasma.config.Property;
import org.plasma.sdo.jdbc.service.JDBCServiceException;

/**
 */
public class RDBConnectionManager {

  private static final Log log = LogFactory.getLog(RDBConnectionManager.class);

  /**
   * Default: true The default auto-commit state of connections created by this
   * pool.
   */
  public static final String DBCP_DEFAULTAUTOCOMMIT = "defaultAutoCommit";

  /**
   * Default: driver default The default read-only state of connections created
   * by this pool. If not set then the setReadOnly method will not be called.
   * (Some drivers don't support read only mode, ex: Informix)
   */
  public static final String DBCP_DEFAULTREADONLY = "defaultReadOnly";

  /**
   * Default: driver default The default TransactionIsolation state of
   * connections created by this pool. One of the following: (see javadoc)
   */
  public static final String DBCP_DEFAULTTRANSACTIONISOLATION = "defaultTransactionIsolation";

  /**
   * Default: The default catalog of connections created by this pool.
   */
  public static final String DBCP_DEFAULTCATALOG = "defaultCatalog";

  /**
   * Default: 0 The initial number of connections that are created when the pool
   * is started. Since: 1.2
   */
  public static final String DBCP_INITIALSIZE = "initialSize";

  /**
   * Default: 8 The maximum number of active connections that can be allocated
   * from this pool at the same time, or negative for no limit.
   */
  public static final String DBCP_MAXACTIVE = "maxActive";

  /**
   * Default: 8 The maximum number of connections that can remain idle in the
   * pool, without extra ones being released, or negative for no limit.
   */
  public static final String DBCP_MAXIDLE = "maxIdle";

  /**
   * Default: 0 The minimum number of connections that can remain idle in the
   * pool, without extra ones being created, or zero to create none.
   * 
   */
  public static final String DBCP_MINIDLE = "minIdle";

  /**
   * 
   * Default: indefinitely The maximum number of milliseconds that the pool will
   * wait (when there are no available connections) for a connection to be
   * returned before throwing an exception, or -1 to wait indefinitely.
   */
  public static final String DBCP_MAXWAIT = "maxWait";

  /**
   * The SQL query that will be used to validate connections from this pool
   * before returning them to the caller. If specified, this query MUST be an
   * SQL SELECT statement that returns at least one row.
   * 
   */
  public static final String DBCP_VALIDATIONQUERY = "validationQuery";

  /**
   * Default: true The indication of whether objects will be validated before
   * being borrowed from the pool. If the object fails to validate, it will be
   * dropped from the pool, and we will attempt to borrow another. NOTE - for a
   * true value to have any effect, the validationQuery parameter must be set to
   * a non-null string.
   */
  public static final String DBCP_TESTONBORROW = "testOnBorrow";

  /**
   * Default: false The indication of whether objects will be validated before
   * being returned to the pool. NOTE - for a true value to have any effect, the
   * validationQuery parameter must be set to a non-null string.
   */
  public static final String DBCP_TESTONRETURN = "testOnReturn";

  /**
   * Default: false The indication of whether objects will be validated by the
   * idle object evictor (if any). If an object fails to validate, it will be
   * dropped from the pool. NOTE - for a true value to have any effect, the
   * validationQuery parameter must be set to a non-null string.
   */
  public static final String DBCP_TESTWHILEIDLE = "testWhileIdle";

  /**
   * Default: -1 The number of milliseconds to sleep between runs of the idle
   * object evictor thread. When non-positive, no idle object evictor thread
   * will be run.
   * 
   */
  public static final String DBCP_TIMEBETWEENEVICTIONRUNSMILLIS = "timeBetweenEvictionRunsMillis";

  /**
   * Default: 3 The number of objects to examine during each run of the idle
   * object evictor thread (if any).
   */
  public static final String DBCP_NUMTESTSPEREVICTIONRUN = "numTestsPerEvictionRun";

  /**
   * Default: 1000 * 60 * 30 The minimum amount of time an object may sit idle
   * in the pool before it is eligable for eviction by the idle object evictor
   * (if any).
   * 
   */
  public static final String DBCP_MINEVICTABLEIDLETIMEMILLIS = "minEvictableIdleTimeMillis";

  /**
   * NOTE: Versions 1.3 and 1.4 of DBCP incorrectly use "initConnectionSqls" as
   * the name of this property for JNDI object factory configuration. Until
   * 1.3.1/1.4.1 are released, "initConnectionSqls" must be used as the name for
   * this property when using BasicDataSoureFactory to create BasicDataSource
   * instances via JNDI. null A Collection of SQL statements that will be used
   * to initialize physical connections when they are first created. These
   * statements are executed only once - when the configured connection factory
   * creates the connection.
   */
  public static final String DBCP_CONNECTIONINITSQLS = "connectionInitSqls";

  /**
   * Default: false Flag to remove abandoned connections if they exceed the
   * removeAbandonedTimout. If set to true a connection is considered abandoned
   * and eligible for removal if it has been idle longer than the
   * removeAbandonedTimeout. Setting this to true can recover db connections
   * from poorly written applications which fail to close a connection.
   */
  public static final String DBCP_REMOVEABANDONED = "removeAbandoned";

  /**
   * Default: 300 Timeout in seconds before an abandoned connection can be
   * removed.
   */
  public static final String DBCP_REMOVEABANDONEDTIMEOUT = "removeAbandonedTimeout";

  /**
   * Default: false Flag to log stack traces for application code which
   * abandoned a Statement or Connection. Logging of abandoned Statements and
   * Connections adds overhead for every Connection open or new Statement
   * because a stack trace has to be generated.
   */
  public static final String DBCP_LOGABANDONED = "logAbandoned";

  private static RDBConnectionManager instance;
  private static DataSource DS = null;
  private static GenericObjectPool _pool = null;

  private RDBConnectionManager() {

    Map<String, String> props = new HashMap<String, String>();
    for (Property property : PlasmaConfig.getInstance()
        .getDataAccessProvider(DataAccessProviderName.JDBC).getProperties())
      props.put(property.getName(), property.getValue());

    String driverName = props.get("org.plasma.sdo.access.provider.jdbc.ConnectionDriverName");
    String url = props.get("org.plasma.sdo.access.provider.jdbc.ConnectionURL");
    String user = props.get("org.plasma.sdo.access.provider.jdbc.ConnectionUserName");
    String password = props.get("org.plasma.sdo.access.provider.jdbc.ConnectionPassword");
    int poolMinSize = Integer.valueOf(props
        .get("org.plasma.sdo.access.provider.jdbc.ConnectionPoolMinSize"));
    int poolMaxSize = Integer.valueOf(props
        .get("org.plasma.sdo.access.provider.jdbc.ConnectionPoolMaxSize"));

    try {
      java.lang.Class.forName(driverName).newInstance();
    } catch (Exception e) {
      log.error("Error when attempting to obtain DB Driver: " + driverName, e);
    }

    if (log.isDebugEnabled())
      log.debug("trying to connect to database...");
    try {
      RDBConnectionManager.DS = setup(url, user, password, poolMinSize, poolMaxSize, props);

      log.debug("Connection attempt to database succeeded.");
    } catch (Exception e) {
      log.error("Error when attempting to connect to DB ", e);
    }
  }

  public static RDBConnectionManager instance() {
    if (instance == null)
      initInstance(); // double-checked locking pattern
    return instance;
  }

  private static synchronized void initInstance() {
    if (instance == null)
      instance = new RDBConnectionManager();
  }

  protected void finalize() {
    log.debug("Finalizing ConnectionManager");
    try {
      super.finalize();
    } catch (Throwable ex) {
      log.error("ConnectionManager finalize failed to disconnect: ", ex);
    }
  }

  public Connection getConnection() throws SQLException {
    try {
      printDriverStats();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return DS.getConnection();
  }

  /**
   * 
   * @param connectURI
   *          - JDBC Connection URI
   * @param username
   *          - JDBC Connection username
   * @param password
   *          - JDBC Connection password
   * @param minIdle
   *          - Minimum number of idel connection in the connection pool
   * @param maxActive
   *          - Connection Pool Maximum Capacity (Size)
   * @throws Exception
   */
  private static DataSource setup(String connectURI, String username, String password, int minIdle,
      int maxActive, Map<String, String> props) throws Exception {
    //
    // Create an ObjectPool that serves as the
    // actual pool of connections.
    //
    // Use a GenericObjectPool instance, although
    // any ObjectPool implementation will suffice.
    //
    GenericObjectPool.Config config = createGenericObjectPoolConfig(props);

    GenericObjectPool connectionPool = new GenericObjectPool(null, config);

    connectionPool.setMinIdle(minIdle);
    connectionPool.setMaxActive(maxActive);

    RDBConnectionManager._pool = connectionPool;
    // We keep it for two reasons
    // #1 We need it for statistics/debugging
    // #2 PoolingDataSource does not have getPool()
    // method, for some obscure, weird reason.

    //
    // Create a ConnectionFactory that the
    // pool will use to create Connections.
    // Use the DriverManagerConnectionFactory,
    // using the connect string from configuration
    //
    ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, username,
        password);

    //
    // Create the PoolableConnectionFactory, which wraps
    // the "real" Connections created by the ConnectionFactory with
    // the classes that implement the pooling functionality.
    //
    PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
        connectionFactory, connectionPool, null, null, false, true);

    String value = getValue(DBCP_VALIDATIONQUERY, props);
    if (value != null) {
      try {
        poolableConnectionFactory.setValidationQuery(value);
      } catch (Exception e) {
        throw new JDBCServiceException(e);
      }
    }

    PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

    return dataSource;
  }

  public static void printDriverStats() throws Exception {
    ObjectPool connectionPool = RDBConnectionManager._pool;
    log.debug("NumActive: " + connectionPool.getNumActive());
    log.debug("NumIdle: " + connectionPool.getNumIdle());
  }

  /**
   * getNumLockedProcesses - gets the number of currently locked processes on
   * the MySQL db
   * 
   * @return Number of locked processes
   */
  public int getNumLockedProcesses() {
    int num_locked_connections = 0;
    Connection con = null;
    PreparedStatement p_stmt = null;
    ResultSet rs = null;
    try {
      con = RDBConnectionManager.DS.getConnection();
      p_stmt = con.prepareStatement("SHOW PROCESSLIST");
      rs = p_stmt.executeQuery();
      while (rs.next()) {
        if (rs.getString("State") != null && rs.getString("State").equals("Locked")) {
          num_locked_connections++;
        }
      }
    } catch (Exception e) {
      log.debug("Failed to get get Locked Connections - Exception: " + e.toString());
    } finally {
      try {
        rs.close();
        p_stmt.close();
        con.close();
      } catch (java.sql.SQLException ex) {
        log.error(ex.toString());
      }
    }
    return num_locked_connections;
  }

  private static GenericObjectPool.Config createGenericObjectPoolConfig(Map<String, String> props) {
    GenericObjectPool.Config config = new GenericObjectPool.Config();

    String value = getValue(DBCP_TESTONBORROW, props);
    if (value != null) {
      try {
        config.testOnBorrow = Boolean.parseBoolean(value);
      } catch (Exception e) {
        throw new JDBCServiceException(e);
      }
    }

    value = getValue(DBCP_TESTONRETURN, props);
    if (value != null) {
      try {
        config.testOnReturn = Boolean.parseBoolean(value);
      } catch (Exception e) {
        throw new JDBCServiceException(e);
      }
    }

    value = getValue(DBCP_TESTWHILEIDLE, props);
    if (value != null) {
      try {
        config.testWhileIdle = Boolean.parseBoolean(value);
      } catch (Exception e) {
        throw new JDBCServiceException(e);
      }
    }

    value = getValue(DBCP_TIMEBETWEENEVICTIONRUNSMILLIS, props);
    if (value != null) {
      try {
        config.timeBetweenEvictionRunsMillis = Long.parseLong(value);
      } catch (NumberFormatException e) {
        throw new JDBCServiceException(e);
      }
    }

    value = getValue(DBCP_NUMTESTSPEREVICTIONRUN, props);
    if (value != null) {
      try {
        config.numTestsPerEvictionRun = Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new JDBCServiceException(e);
      }
    }

    value = getValue(DBCP_MINEVICTABLEIDLETIMEMILLIS, props);
    if (value != null) {
      try {
        config.minEvictableIdleTimeMillis = Long.parseLong(value);
      } catch (NumberFormatException e) {
        throw new JDBCServiceException(e);
      }
    }

    value = getValue(DBCP_MAXIDLE, props);
    if (value != null) {
      try {
        config.maxIdle = Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new JDBCServiceException(e);
      }
    }

    value = getValue(DBCP_MINIDLE, props);
    if (value != null) {
      try {
        config.minIdle = Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new JDBCServiceException(e);
      }
    }

    value = getValue(DBCP_MAXACTIVE, props);
    if (value != null) {
      try {
        config.maxActive = Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new JDBCServiceException(e);
      }
    }

    value = getValue(DBCP_MAXWAIT, props);
    if (value != null) {
      try {
        config.maxWait = Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new JDBCServiceException(e);
      }
    }

    return config;
  }

  private static String getValue(String name, Map<String, String> props) {
    String value = props.get(name);
    if (value == null)
      value = props.get("org.apache.commons.dbcp." + name);
    return value;
  }

}
