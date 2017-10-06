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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.runtime.DataAccessProviderName;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.runtime.SequenceConfiguration;
import org.plasma.sdo.Alias;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.SequenceGenerator;
import org.plasma.sdo.jdbc.connect.RDBConnectionManager;

import commonj.sdo.DataObject;
import commonj.sdo.Type;

public class StoredProcSequenceGenerator implements SequenceGenerator {
  private static Log log = LogFactory.getLog(StoredProcSequenceGenerator.class);
  private Connection conn;

  public StoredProcSequenceGenerator() {
  }

  public Long get(DataObject dataObject) {
    return getSeqNum(getSeqName(dataObject.getType()));
  }

  private Long getSeqNum(String seqName) {
    CallableStatement cstmt1 = null;
    try {
      if (conn == null)
        initialize();

      cstmt1 = (CallableStatement) conn.prepareCall("{ call GET_SQNC_NMBR (?, ?) }");
      cstmt1.registerOutParameter(2, Types.NUMERIC);
      cstmt1.setString(1, seqName);
      cstmt1.execute();
      long id = cstmt1.getLong(2);
      return new Long(id);
    } catch (Throwable t) {
      throw new RuntimeException(t);
    } finally {
      if (cstmt1 != null)
        try {
          cstmt1.close();
        } catch (Throwable t2) {
        }
    }
  }

  private String getSeqName(Type type) {
    SequenceConfiguration config = PlasmaRuntime.getInstance()
        .getDataAccessProvider(DataAccessProviderName.JDBC).getSequenceConfiguration();

    Alias alias = ((PlasmaType) type).getAlias();
    if (alias == null)
      throw new RuntimeException("type has no alias, " + type.getURI() + "#" + type.getName());
    StringBuilder buf = new StringBuilder();
    if (config.getPrefix() != null && config.getPrefix().trim().length() > 0) {
      buf.append(config.getPrefix());
    }
    buf.append(alias.getPhysicalName());
    if (config.getSuffix() != null && config.getSuffix().trim().length() > 0) {
      buf.append(config.getSuffix());
    }

    return buf.toString();
  }

  public void initialize() {
    try {
      this.conn = RDBConnectionManager.instance().getConnection();
    } catch (SQLException e2) {
      throw new DataAccessException(e2);
    }
  }

  public void close() {
    try {
      if (this.conn != null) {
        this.conn.close(); // return to pool
        this.conn = null;
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
  }
}
