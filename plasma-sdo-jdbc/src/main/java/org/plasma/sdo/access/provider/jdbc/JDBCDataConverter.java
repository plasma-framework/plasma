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

package org.plasma.sdo.access.provider.jdbc;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.DataFlavor;
import org.plasma.sdo.DataType;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.helper.DataConverter;

import commonj.sdo.Type;

public class JDBCDataConverter {
  private static Log log = LogFactory.getFactory().getInstance(JDBCDataConverter.class);

  static public JDBCDataConverter INSTANCE = initializeInstance();
  private Map<Integer, String> sqlTypeMap = new HashMap<Integer, String>();

  private JDBCDataConverter() {

    // Get all field in java.sql.Types
    Field[] fields = java.sql.Types.class.getFields();
    for (int i = 0; i < fields.length; i++) {
      try {
        String name = fields[i].getName();
        Integer value = (Integer) fields[i].get(null);
        sqlTypeMap.put(value, name);
      } catch (IllegalAccessException e) {
      }
    }
  }

  private static synchronized JDBCDataConverter initializeInstance() {
    if (INSTANCE == null)
      INSTANCE = new JDBCDataConverter();
    return INSTANCE;
  }

  public Object fromJDBCDataType(ResultSet rs, int columnIndex, int sourceType,
      PlasmaProperty targetProperty) throws SQLException {

    Object result = null;

    if (targetProperty.getType().isDataType()) {
      DataType targetDataType = DataType.valueOf(targetProperty.getType().getName());

      switch (targetDataType) {
      case String:
      case URI:
      case Month:
      case MonthDay:
      case Day:
      case Time:
      case Year:
      case YearMonth:
      case YearMonthDay:
      case Duration:
        result = rs.getString(columnIndex);
        break;
      case Date:
        java.sql.Timestamp ts = rs.getTimestamp(columnIndex);
        if (ts != null)
          result = new java.util.Date(ts.getTime());
        break;
      case DateTime:
        ts = rs.getTimestamp(columnIndex);
        if (ts != null)
          result = new java.util.Date(ts.getTime());
        break;
      case Decimal:
        result = rs.getBigDecimal(columnIndex);
        break;
      case Bytes:
        result = rs.getBytes(columnIndex);
        break;
      case Byte:
        result = rs.getByte(columnIndex);
        break;
      case Boolean:
        result = rs.getBoolean(columnIndex);
        break;
      case Character:
        result = rs.getInt(columnIndex);
        break;
      case Double:
        result = rs.getDouble(columnIndex);
        break;
      case Float:
        result = rs.getFloat(columnIndex);
        break;
      case Int:
        result = rs.getInt(columnIndex);
        break;
      case Integer:
        result = new BigInteger(rs.getString(columnIndex));
        break;
      case Long:
        result = rs.getLong(columnIndex);
        break;
      case Short:
        result = rs.getShort(columnIndex);
        break;
      case Strings:
        String value = rs.getString(columnIndex);
        String[] values = value.split("\\s");
        List<String> list = new ArrayList<String>(values.length);
        for (int i = 0; i < values.length; i++)
          list.add(values[i]); // what no Java 5 sugar for this ??
        result = list;
        break;
      case Object:
      default:
        result = rs.getObject(columnIndex);
        break;
      }
    } else {
      // FIXME: or get the opposite containing type
      // of the property and get its pri-key(s)
      result = rs.getObject(columnIndex);
    }

    return result;
  }

  public int toJDBCDataType(PlasmaProperty sourceProperty, Object value) throws SQLException {

    int result;

    if (sourceProperty.getType().isDataType()) {
      DataType dataType = DataType.valueOf(sourceProperty.getType().getName());

      switch (dataType) {
      case String:
      case URI:
      case Month:
      case MonthDay:
      case Day:
      case Time:
      case Year:
      case YearMonth:
      case YearMonthDay:
      case Duration:
      case Strings:
        result = java.sql.Types.VARCHAR;
        break;
      case Date:
        // Plasma SDO allows more precision than just month/day/year
        // in an SDO date datatype, and using java.sql.Date will truncate
        // here so use java.sql.Timestamp.
        result = java.sql.Types.TIMESTAMP;
        break;
      case DateTime:
        result = java.sql.Types.TIMESTAMP;
        // FIXME: so what SDO datatype maps to a SQL timestamp??
        break;
      case Decimal:
        result = java.sql.Types.DECIMAL;
        break;
      case Bytes:
        result = java.sql.Types.VARBINARY;
        break;
      case Byte:
        result = java.sql.Types.VARBINARY;
        break;
      case Boolean:
        result = java.sql.Types.BOOLEAN;
        break;
      case Character:
        result = java.sql.Types.CHAR;
        break;
      case Double:
        result = java.sql.Types.DOUBLE;
        break;
      case Float:
        result = java.sql.Types.FLOAT;
        break;
      case Int:
        result = java.sql.Types.INTEGER;
        break;
      case Integer:
        result = java.sql.Types.BIGINT;
        break;
      case Long:
        result = java.sql.Types.INTEGER; // FIXME: no JDBC long??
        break;
      case Short:
        result = java.sql.Types.SMALLINT;
        break;
      case Object:
      default:
        result = java.sql.Types.VARCHAR;
        break;
      }
    } else {
      // FIXME: HACK
      // FIXME: or get the opposite containing type
      // of the property and get its pri-key(s)
      result = java.sql.Types.INTEGER;
    }
    return result;
  }

  public Object toJDBCDataValue(PlasmaProperty sourceProperty, Object value) throws SQLException {

    Object result;
    if (sourceProperty.getType().isDataType()) {
      DataType dataType = DataType.valueOf(sourceProperty.getType().getName());

      switch (dataType) {
      case String:
      case URI:
      case Month:
      case MonthDay:
      case Day:
      case Time:
      case Year:
      case YearMonth:
      case YearMonthDay:
      case Duration:
        result = DataConverter.INSTANCE.toString(sourceProperty.getType(), value);
        break;
      case Date:
        // Plasma SDO allows more precision than just month/day/year
        // in an SDO date datatype, and using java.sql.Date will truncate
        // here so use java.sql.Timestamp.
        Date date = DataConverter.INSTANCE.toDate(sourceProperty.getType(), value);
        result = new java.sql.Timestamp(date.getTime());
        break;
      case DateTime:
        date = DataConverter.INSTANCE.toDate(sourceProperty.getType(), value);
        result = new java.sql.Timestamp(date.getTime());
        break;
      case Decimal:
        result = DataConverter.INSTANCE.toDecimal(sourceProperty.getType(), value);
        break;
      case Bytes:
        result = DataConverter.INSTANCE.toBytes(sourceProperty.getType(), value);
        break;
      case Byte:
        result = DataConverter.INSTANCE.toByte(sourceProperty.getType(), value);
        break;
      case Boolean:
        result = DataConverter.INSTANCE.toBoolean(sourceProperty.getType(), value);
        break;
      case Character:
        result = DataConverter.INSTANCE.toString(sourceProperty.getType(), value);
        break;
      case Double:
        result = DataConverter.INSTANCE.toDouble(sourceProperty.getType(), value);
        break;
      case Float:
        result = DataConverter.INSTANCE.toDouble(sourceProperty.getType(), value);
        break;
      case Int:
        result = DataConverter.INSTANCE.toInt(sourceProperty.getType(), value);
        break;
      case Integer:
        result = DataConverter.INSTANCE.toInteger(sourceProperty.getType(), value);
        break;
      case Long:
        result = DataConverter.INSTANCE.toLong(sourceProperty.getType(), value);
        break;
      case Short:
        result = DataConverter.INSTANCE.toShort(sourceProperty.getType(), value);
        break;
      case Strings:
        result = DataConverter.INSTANCE.toString(sourceProperty.getType(), value);
        break;
      case Object:
      default:
        result = DataConverter.INSTANCE.toString(sourceProperty.getType(), value);
        break;
      }
    } else {
      result = (Long) value;
    }
    return result;
  }

  public String toJDBCString(Type sourceType, PlasmaProperty sourceProperty, Object value) {

    String result = null;
    DataFlavor flavor = sourceProperty.getDataFlavor();

    switch (flavor) {
    case integral:
    case real:
      result = value.toString();
      break;
    case string:
      result = "'" + value.toString() + "'";
      break;
    default:
      result = value.toString();
    }

    return result;
  }

  public String getJdbcTypeName(int jdbcType) {

    return sqlTypeMap.get(jdbcType);
  }
}
