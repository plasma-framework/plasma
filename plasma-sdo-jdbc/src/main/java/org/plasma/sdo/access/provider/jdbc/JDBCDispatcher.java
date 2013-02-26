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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.provider.common.PropertyPair;
import org.plasma.sdo.core.CoreDataObject;
import org.plasma.sdo.profile.ConcurrencyType;
import org.plasma.sdo.profile.ConcurrentDataFlavor;
import org.plasma.sdo.profile.KeyType;

import commonj.sdo.Property;

public abstract class JDBCDispatcher {
	
    private static Log log = LogFactory.getFactory().getInstance(JDBCDispatcher.class);
	protected JDBCDataConverter converter = JDBCDataConverter.INSTANCE;
	
	protected JDBCDispatcher() {
		
	}	

	protected StringBuilder createSelectForUpdate(PlasmaType type,
			List<PropertyPair> keyValues, int waitSeconds) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");		
		List<Property> props = new ArrayList<Property>();
		for (PropertyPair pair : keyValues)
			props.add(pair.getProp());
		
		Property lockingUserProperty = type.findProperty(ConcurrencyType.pessimistic, 
            	ConcurrentDataFlavor.user);
        if (lockingUserProperty != null)
        	props.add(lockingUserProperty);
        else
            if (log.isDebugEnabled())
                log.debug("could not find locking user property for type, "
                    + type.getURI() + "#" + type.getName());  
        
        Property lockingTimestampProperty = type.findProperty(ConcurrencyType.pessimistic, 
            	ConcurrentDataFlavor.time);
        if (lockingTimestampProperty != null)
        	props.add(lockingTimestampProperty);
        else
            if (log.isDebugEnabled())
                log.debug("could not find locking timestamp property for type, "
                    + type.getURI() + "#" + type.getName());  

        Property concurrencyUserProperty = type.findProperty(ConcurrencyType.optimistic, 
        	ConcurrentDataFlavor.user);
        if (concurrencyUserProperty != null)
        	props.add(concurrencyUserProperty);
        else
            if (log.isDebugEnabled())
                log.debug("could not find optimistic concurrency (username) property for type, "
                    + type.getURI() + "#" + type.getName());          
        
        Property concurrencyTimestampProperty = type.findProperty(ConcurrencyType.optimistic, 
        	ConcurrentDataFlavor.time);
        if (concurrencyTimestampProperty != null)
        	props.add(concurrencyTimestampProperty);
        else
            if (log.isDebugEnabled())
                log.debug("could not find optimistic concurrency timestamp property for type, "
                    + type.getURI() + "#" + type.getName());  
				
		int i = 0;
		for (Property p : props) {
			PlasmaProperty prop = (PlasmaProperty)p;
			if (prop.isMany() && !prop.getType().isDataType())
				continue;
			if (i > 0)
				sql.append(", ");
			sql.append("t0.");
			sql.append(prop.getPhysicalName());
			i++;
		}
		sql.append(" FROM ");
		sql.append(type.getPhysicalName());
		sql.append(" t0 ");
		sql.append(" WHERE ");
        for (int k = 0; k < keyValues.size(); k++) {
        	if (k > 0)
        		sql.append(", ");
        	PropertyPair propValue = keyValues.get(k);
        	sql.append("t0.");  
        	sql.append(propValue.getProp().getPhysicalName());
        	sql.append(" = "); 
        	// FIXME: escape , etc...
        	sql.append(propValue.getValue()); // FIXME; use converter
        }
        sql.append(" FOR UPDATE WAIT ");
        sql.append(String.valueOf(waitSeconds));
        //sql.append(";");
		return sql;
	}
	
	protected StringBuilder createSelect(PlasmaType type, List<String> names, 
			List<PropertyPair> keyValues) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");		
		int i = 0;
		for (String name : names) {
			PlasmaProperty prop = (PlasmaProperty)type.getProperty(name);
			if (prop.isMany() && !prop.getType().isDataType())
				continue;
			if (i > 0)
				sql.append(", ");
			sql.append("t0.");
			sql.append(prop.getPhysicalName());
			i++;
		}
		sql.append(" FROM ");
		sql.append(type.getPhysicalName());
		sql.append(" t0 ");
		sql.append(" WHERE ");
        for (int k = 0; k < keyValues.size(); k++) {
        	if (k > 0)
        		sql.append(", ");
        	PropertyPair propValue = keyValues.get(k);
        	sql.append("t0.");  
        	sql.append(propValue.getProp().getPhysicalName());
        	sql.append(" = "); 
        	// FIXME: escape , etc...
        	sql.append(propValue.getValue()); // FIXME; use converter
        }
		
		return sql;
	}
	
	protected StringBuilder createInsert(PlasmaType type, 
			Map<String, PropertyPair> values) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(type.getPhysicalName());
		sql.append("(");
		int i = 0;
		for (PropertyPair pair : values.values()) {
			PlasmaProperty prop = pair.getProp();
			if (prop.isMany() && !prop.getType().isDataType())
				continue;
			if (i > 0)
				sql.append(", ");
			sql.append(pair.getProp().getPhysicalName());
			pair.setColumn(i+1);
			i++;
		}
		sql.append(") VALUES (");
		
		i = 0;
		for (PropertyPair pair : values.values()) {
			PlasmaProperty prop = pair.getProp();
			if (prop.isMany() && !prop.getType().isDataType())
				continue;
			if (i > 0)
				sql.append(", ");
			sql.append("?");
			i++;
		}
		sql.append(")");
		return sql;
	}

	protected StringBuilder createUpdate(PlasmaType type,  
			Map<String, PropertyPair> values) {
		StringBuilder sql = new StringBuilder();
		
		// construct an 'update' for all non pri-keys and
		// excluding many reference properties
		sql.append("UPDATE ");		
		sql.append(type.getPhysicalName());
		sql.append(" t0 SET ");
		int i = 0;
		for (PropertyPair pair : values.values()) {
			PlasmaProperty prop = pair.getProp();
			if (prop.isMany() && !prop.getType().isDataType())
				continue;
			if (prop.isKey(KeyType.primary))
				continue; // ignore keys here
			if (i > 0)
				sql.append(", ");
			sql.append("t0.");
			sql.append(prop.getPhysicalName());
        	sql.append(" = ?"); 
        	pair.setColumn(i+1);
			i++;
		}
        // construct a 'where' continuing to append parameters
		// for each pri-key
		sql.append(" WHERE ");
		for (PropertyPair pair : values.values()) {
			PlasmaProperty prop = pair.getProp();
			if (prop.isMany() && !prop.getType().isDataType())
				continue;
			if (!prop.isKey(KeyType.primary))
				continue;
        	sql.append("t0.");  
        	sql.append(pair.getProp().getPhysicalName());
        	sql.append(" = ?"); 
        	pair.setColumn(i+1);
        	i++;
        }
		
		return sql;
	}
	
	protected StringBuilder createDelete(PlasmaType type,  
			Map<String, PropertyPair> values) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ");		
		sql.append(type.getPhysicalName());
		sql.append(" t0 ");
		sql.append(" WHERE ");
		int i = 0;
		for (PropertyPair pair : values.values()) {
			PlasmaProperty prop = pair.getProp();
			if (prop.isMany() && !prop.getType().isDataType())
				continue;
			if (!prop.isKey(KeyType.primary))
				continue;
        	sql.append("t0.");  
        	sql.append(pair.getProp().getPhysicalName());
        	sql.append(" = ?"); 
        	pair.setColumn(i+1);
        	i++;
        }
		
		return sql;
	}
	
	protected List<List<PropertyPair>> fetch(PlasmaType type, StringBuilder sql, Connection con)
	{
		List<List<PropertyPair>> result = new ArrayList<List<PropertyPair>>();
        PreparedStatement statement = null;
        ResultSet rs = null; 
        try {
            statement = con.prepareStatement(sql.toString(),
               		ResultSet.TYPE_FORWARD_ONLY,/*ResultSet.TYPE_SCROLL_INSENSITIVE,*/
                    ResultSet.CONCUR_READ_ONLY);
		
            if (log.isDebugEnabled() ){
                log.debug("fetch: " + sql.toString());
            } 
            
            statement.execute();
            rs = statement.getResultSet();
            ResultSetMetaData rsMeta = rs.getMetaData();
            int numcols = rsMeta.getColumnCount();
            
            while (rs.next()) {
            	List<PropertyPair> row = new ArrayList<PropertyPair>(numcols);
            	result.add(row);
            	for(int i=1;i<=numcols;i++) {
            		String columnName = rsMeta.getColumnName(i);
            		int columnType = rsMeta.getColumnType(i);
            		PlasmaProperty prop = (PlasmaProperty)type.getProperty(columnName);
              		Object value = converter.fromJDBCDataType(rs, 
            				i, columnType, prop);
            		if (value != null) {
            		    PropertyPair pair = new PropertyPair(
            			    (PlasmaProperty)prop, value);
            		    row.add(pair);
            		}
                }
            }
        }
        catch (Throwable t) {
            throw new DataAccessException(t);
        }
        finally {
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


	protected List<PlasmaDataObject> fetch(PlasmaDataObject source, PlasmaProperty sourceProperty, StringBuilder sqlQuery, Connection con)
	{
		List<PlasmaDataObject> result = new ArrayList<PlasmaDataObject>();
        PreparedStatement statement = null;
        ResultSet rs = null; 
        try {
            statement = con.prepareStatement(sqlQuery.toString(),
               		ResultSet.TYPE_FORWARD_ONLY,/*ResultSet.TYPE_SCROLL_INSENSITIVE,*/
                    ResultSet.CONCUR_READ_ONLY);
		
            if (log.isDebugEnabled() ){
                log.debug("fetch: " + sqlQuery.toString());
            } 
            
            statement.execute();
            rs = statement.getResultSet();
            ResultSetMetaData rsMeta = rs.getMetaData();
            int numcols = rsMeta.getColumnCount();
            while(rs.next()) {
            	PlasmaDataObject target = (PlasmaDataObject)source.createDataObject(sourceProperty);
            	result.add(target);
            	for(int i=1;i<=numcols;i++) {
            		String columnName = rsMeta.getColumnName(i);
            		int columnType = rsMeta.getColumnType(i);
            		PlasmaProperty prop = (PlasmaProperty)target.getType().getProperty(columnName);
              		Object value = converter.fromJDBCDataType(rs, 
            				i, columnType, prop);
      
            		if (!prop.isReadOnly()) {
            			target.set(prop, value);
            		}
            		else {
            			CoreDataObject coreObject = (CoreDataObject)target;    			
            			coreObject.setValue(prop.getName(), value);
            		}
                }
            }
        }
        catch (Throwable t) {
            throw new DataAccessException(t);
        }
        finally {
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
	
	protected Map<String, PropertyPair> fetchRowMap(PlasmaType type, StringBuilder sql, Connection con)
	{
		Map<String, PropertyPair> result = new HashMap<String, PropertyPair>();
        PreparedStatement statement = null;
        ResultSet rs = null; 
        try {
            statement = con.prepareStatement(sql.toString(),
               		ResultSet.TYPE_FORWARD_ONLY,/*ResultSet.TYPE_SCROLL_INSENSITIVE,*/
                    ResultSet.CONCUR_READ_ONLY);
		
            if (log.isDebugEnabled() ){
                log.debug("fetch: " + sql.toString());
            } 
            
            statement.execute();
            rs = statement.getResultSet();
            ResultSetMetaData rsMeta = rs.getMetaData();
            int numcols = rsMeta.getColumnCount();
            while (rs.next()) 
            	for(int i=1;i<=numcols;i++) {
            		String columnName = rsMeta.getColumnName(i);
            		int columnType = rsMeta.getColumnType(i);
            		PlasmaProperty prop = (PlasmaProperty)type.getProperty(columnName);
              		Object value = converter.fromJDBCDataType(rs, 
            				i, columnType, prop);
            		if (value != null) {
            		    PropertyPair pair = new PropertyPair(
            			    (PlasmaProperty)prop, value);
            		    result.put(prop.getName(), pair);
            		}
                }
        }
        catch (Throwable t) {
            throw new DataAccessException(t);
        }
        finally {
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

	protected List<PropertyPair> fetchRow(PlasmaType type, StringBuilder sql, Connection con)
	{
		List<PropertyPair> result = new ArrayList<PropertyPair>();
        PreparedStatement statement = null;
        ResultSet rs = null; 
        try {
            statement = con.prepareStatement(sql.toString(),
               		ResultSet.TYPE_FORWARD_ONLY,/*ResultSet.TYPE_SCROLL_INSENSITIVE,*/
                    ResultSet.CONCUR_READ_ONLY);
		
            if (log.isDebugEnabled() ){
                log.debug("fetch: " + sql.toString());
            } 
            
            statement.execute();
            rs = statement.getResultSet();
            ResultSetMetaData rsMeta = rs.getMetaData();
            int numcols = rsMeta.getColumnCount();
            while (rs.next()) 
            	for(int i=1;i<=numcols;i++) {
            		String columnName = rsMeta.getColumnName(i);
            		int columnType = rsMeta.getColumnType(i);
            		PlasmaProperty prop = (PlasmaProperty)type.getProperty(columnName);
              		Object value = converter.fromJDBCDataType(rs, 
            				i, columnType, prop);
            		if (value != null) {
            		    PropertyPair pair = new PropertyPair(
            			    (PlasmaProperty)prop, value);
            		    result.add(pair);
            		}
                }
        }
        catch (Throwable t) {
            throw new DataAccessException(t);
        }
        finally {
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
	
	protected void execute(PlasmaType type, StringBuilder sql, 
			Map<String, PropertyPair> values,
			Connection con)
	{
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(sql.toString());
		
            StringBuilder paramBuf = null;
            if (log.isDebugEnabled() ){
                log.debug("execute: " + sql.toString());
                paramBuf = new StringBuilder();
                paramBuf.append("[");
            } 
            
    		int i = 1;
    		for (PropertyPair pair : values.values()) {
    			int jdbcType = converter.toJDBCDataType(pair.getProp(), pair.getValue());
    			Object jdbcValue = converter.toJDBCDataValue(pair.getProp(), pair.getValue());
    			statement.setObject(pair.getColumn(), 
    					jdbcValue, jdbcType);
                if (log.isDebugEnabled() ) {
                	if (i > 1) {
                		paramBuf.append(", ");
                	}
                	paramBuf.append("(");
                	paramBuf.append(jdbcValue.getClass().getSimpleName());
                	paramBuf.append("/");
                	paramBuf.append(converter.getJdbcTypeName(jdbcType));
                	paramBuf.append(")");
                	paramBuf.append(String.valueOf(jdbcValue));
                }                	
    			i++;		
    		}
            if (log.isDebugEnabled() ){
            	paramBuf.append("]");
                log.debug("params: " + paramBuf.toString());
            } 
            statement.executeUpdate();
        }
        catch (Throwable t) {
            throw new DataAccessException(t);
        }
        finally {
			try {
	        	if (statement != null)
				    statement.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
        }
 	}
	

}
