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
package org.plasma.sdo.access.provider.jdbc;

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

/**
 */
public class JDBCConnectionManager {

	private static final Log log = LogFactory.getLog(JDBCConnectionManager.class);

    private static JDBCConnectionManager instance;
    private static DataSource DS = null;
	private static GenericObjectPool _pool = null;

	private JDBCConnectionManager() {
	    
		Map<String, String> props = new HashMap<String, String>();
	    for (Property property : PlasmaConfig.getInstance().getDataAccessProvider(DataAccessProviderName.JDBC).getProperties())
	    	props.put(property.getName(), property.getValue());

	    String driverName = props.get("org.plasma.sdo.access.provider.jdbc.ConnectionDriverName");
	    String url = props.get("org.plasma.sdo.access.provider.jdbc.ConnectionURL");
	    String user = props.get("org.plasma.sdo.access.provider.jdbc.ConnectionUserName");
	    String password = props.get("org.plasma.sdo.access.provider.jdbc.ConnectionPassword");
	    int poolMinSize = Integer.valueOf(props.get("org.plasma.sdo.access.provider.jdbc.ConnectionPoolMinSize"));
	    int poolMaxSize = Integer.valueOf(props.get("org.plasma.sdo.access.provider.jdbc.ConnectionPoolMaxSize"));	    
	    
		try {
			java.lang.Class.forName(driverName).newInstance();
		} catch (Exception e) {
			log.error(
				"Error when attempting to obtain DB Driver: "
							+ driverName, e);
		}

		if (log.isDebugEnabled())
		    log.debug("trying to connect to database...");
		try {
			JDBCConnectionManager.DS = setup(
					url, 
					user, 
					password, 
					poolMinSize, 
					poolMaxSize);

			log.debug("Connection attempt to database succeeded.");
		} catch (Exception e) {
			log.error("Error when attempting to connect to DB ", e);
		}
	}

	public static JDBCConnectionManager instance()
    {
        if (instance == null)
            initInstance(); // double-checked locking pattern 
        return instance;     
    }

    private static synchronized void initInstance()
    {
        if (instance == null)
            instance = new JDBCConnectionManager();
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
		return DS.getConnection();
	}

	/**
	 * 
	 * @param connectURI
	 *            - JDBC Connection URI
	 * @param username
	 *            - JDBC Connection username
	 * @param password
	 *            - JDBC Connection password
	 * @param minIdle
	 *            - Minimum number of idel connection in the connection pool
	 * @param maxActive
	 *            - Connection Pool Maximum Capacity (Size)
	 * @throws Exception
	 */
	private static DataSource setup(String connectURI,
			String username, String password, int minIdle, int maxActive)
			throws Exception {
		//
		// First, we'll need a ObjectPool that serves as the
		// actual pool of connections.
		//
		// We'll use a GenericObjectPool instance, although
		// any ObjectPool implementation will suffice.
		//
		GenericObjectPool connectionPool = new GenericObjectPool(null);

		connectionPool.setMinIdle(minIdle);
		connectionPool.setMaxActive(maxActive);

		JDBCConnectionManager._pool = connectionPool;
		// we keep it for two reasons
		// #1 We need it for statistics/debugging
		// #2 PoolingDataSource does not have getPool()
		// method, for some obscure, weird reason.

		//
		// Next, we'll create a ConnectionFactory that the
		// pool will use to create Connections.
		// We'll use the DriverManagerConnectionFactory,
		// using the connect string from configuration
		//
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				connectURI, username, password);

		//
		// Now we'll create the PoolableConnectionFactory, which wraps
		// the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		//
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, connectionPool, null, null, false, true);

		PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

		return dataSource;
	}

	public static void printDriverStats() throws Exception {
		ObjectPool connectionPool = JDBCConnectionManager._pool;
		log.info("NumActive: " + connectionPool.getNumActive());
		log.info("NumIdle: " + connectionPool.getNumIdle());
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
			con = JDBCConnectionManager.DS.getConnection();
			p_stmt = con.prepareStatement("SHOW PROCESSLIST");
			rs = p_stmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("State") != null
						&& rs.getString("State").equals("Locked")) {
					num_locked_connections++;
				}
			}
		} catch (Exception e) {
			log.debug("Failed to get get Locked Connections - Exception: "
					+ e.toString());
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

}
