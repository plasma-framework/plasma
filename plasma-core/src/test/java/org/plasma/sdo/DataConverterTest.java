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
package org.plasma.sdo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Duration;
import org.plasma.common.test.PlasmaTest;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.runtime.PlasmaRuntime;
import org.plasma.sdo.helper.DataConverter;

import org.apache.jena.ext.com.google.common.primitives.Ints;
import org.apache.jena.ext.com.google.common.primitives.Longs;
import org.apache.jena.ext.com.google.common.primitives.Shorts;
import org.apache.jena.ext.com.google.common.primitives.UnsignedInteger;
import org.apache.jena.ext.com.google.common.primitives.UnsignedLong;

import commonj.sdo.Type;
import commonj.sdo.helper.TypeHelper;

/**
 * Tests all combinations of data type conversions.
 */
public class DataConverterTest extends PlasmaTest {
  private static Log log = LogFactory.getLog(DataConverterTest.class);

  private Object[][] testValues = new Object[DataType.values().length][DataType.values().length];

  public static Test suite() {
    return PlasmaTestSetup.newTestSetup(DataConverterTest.class);
  }

  public void setUp() throws Exception {

    super.setUp();

    List<String> list = new ArrayList<String>();
    list.add("23");
    list.add("24");
    list.add("25");

    for (DataType dataType : DataType.values())
      testValues[DataType.Boolean.ordinal()][dataType.ordinal()] = true;

    for (DataType dataType : DataType.values())
      testValues[DataType.Byte.ordinal()][dataType.ordinal()] = (byte) 23;
    // conversion should be hex values
    for (DataType dataType : DataType.values())
      testValues[DataType.Bytes.ordinal()][dataType.ordinal()] = new byte[] { 9, 10, 11, 12, 13, 14 }; // string

    testValues[DataType.Bytes.ordinal()][DataType.Short.ordinal()] = Shorts.toByteArray((short) 23);
    testValues[DataType.Bytes.ordinal()][DataType.Int.ordinal()] = Ints.toByteArray(23);
    testValues[DataType.Bytes.ordinal()][DataType.UnsignedInt.ordinal()] = Ints.toByteArray(23);
    testValues[DataType.Bytes.ordinal()][DataType.Long.ordinal()] = Longs.toByteArray(23L);
    testValues[DataType.Bytes.ordinal()][DataType.UnsignedLong.ordinal()] = Longs.toByteArray(23L);
    testValues[DataType.Bytes.ordinal()][DataType.Bytes.ordinal()] = Longs.toByteArray(23L);

    for (DataType dataType : DataType.values())
      testValues[DataType.Character.ordinal()][dataType.ordinal()] = (char) 3;
    for (DataType dataType : DataType.values())
      testValues[DataType.Decimal.ordinal()][dataType.ordinal()] = new BigDecimal("23");
    for (DataType dataType : DataType.values())
      testValues[DataType.Double.ordinal()][dataType.ordinal()] = (double) 23;
    for (DataType dataType : DataType.values())
      testValues[DataType.Float.ordinal()][dataType.ordinal()] = (float) 23;
    for (DataType dataType : DataType.values())
      testValues[DataType.Int.ordinal()][dataType.ordinal()] = (int) 23;

    // unsigned int
    for (DataType dataType : DataType.values())
      testValues[DataType.UnsignedInt.ordinal()][dataType.ordinal()] = UnsignedInteger
          .valueOf(4294967295L);
    testValues[DataType.UnsignedInt.ordinal()][DataType.Byte.ordinal()] = UnsignedInteger
        .valueOf(Byte.MAX_VALUE);
    testValues[DataType.UnsignedInt.ordinal()][DataType.Int.ordinal()] = UnsignedInteger
        .valueOf(Integer.MAX_VALUE);
    testValues[DataType.UnsignedInt.ordinal()][DataType.Short.ordinal()] = UnsignedInteger
        .valueOf(Short.MAX_VALUE);
    // testValues[DataType.UnsignedInt.ordinal()][DataType.Bytes.ordinal()] =
    // Ints.toByteArray(23);

    for (DataType dataType : DataType.values())
      testValues[DataType.Integer.ordinal()][dataType.ordinal()] = new BigInteger("23");
    for (DataType dataType : DataType.values())
      testValues[DataType.Long.ordinal()][dataType.ordinal()] = (long) 23;

    // unsigned long
    for (DataType dataType : DataType.values())
      testValues[DataType.UnsignedLong.ordinal()][dataType.ordinal()] = UnsignedLong
          .valueOf(4294967295L);
    testValues[DataType.UnsignedLong.ordinal()][DataType.Byte.ordinal()] = UnsignedLong
        .valueOf(Byte.MAX_VALUE);
    testValues[DataType.UnsignedLong.ordinal()][DataType.Int.ordinal()] = UnsignedLong
        .valueOf(Integer.MAX_VALUE);
    testValues[DataType.UnsignedLong.ordinal()][DataType.UnsignedInt.ordinal()] = UnsignedLong
        .valueOf(23L);
    testValues[DataType.UnsignedLong.ordinal()][DataType.Short.ordinal()] = UnsignedLong
        .valueOf(Short.MAX_VALUE);
    // testValues[DataType.UnsignedLong.ordinal()][DataType.Bytes.ordinal()] =
    // Longs.toByteArray(23);

    for (DataType dataType : DataType.values())
      testValues[DataType.Short.ordinal()][dataType.ordinal()] = (short) 23;

    // populate all with int, then override where needed
    for (DataType dataType : DataType.values())
      testValues[DataType.String.ordinal()][dataType.ordinal()] = "23";
    testValues[DataType.String.ordinal()][DataType.Date.ordinal()] = "2001-12-23T23:23:23";
    testValues[DataType.String.ordinal()][DataType.DateTime.ordinal()] = "2001-12-23T23:23:23.334";
    testValues[DataType.String.ordinal()][DataType.Time.ordinal()] = "23:23:23.334";
    testValues[DataType.String.ordinal()][DataType.Day.ordinal()] = "23";
    testValues[DataType.String.ordinal()][DataType.Month.ordinal()] = "12";
    testValues[DataType.String.ordinal()][DataType.MonthDay.ordinal()] = "12-23";
    testValues[DataType.String.ordinal()][DataType.Year.ordinal()] = "2001";
    testValues[DataType.String.ordinal()][DataType.YearMonth.ordinal()] = "2001-12";
    testValues[DataType.String.ordinal()][DataType.YearMonthDay.ordinal()] = "2001-12-23";

    for (DataType dataType : DataType.values())
      testValues[DataType.Strings.ordinal()][dataType.ordinal()] = list;

    for (DataType dataType : DataType.values())
      testValues[DataType.Date.ordinal()][dataType.ordinal()] = new Date();

    for (DataType dataType : DataType.values())
      testValues[DataType.DateTime.ordinal()][dataType.ordinal()] = DataConverter.INSTANCE
          .getDateTimeFormat().format(new Date());
    for (DataType dataType : DataType.values())
      testValues[DataType.Time.ordinal()][dataType.ordinal()] = DataConverter.INSTANCE
          .getTimeFormat().format(new Date());
    for (DataType dataType : DataType.values())
      testValues[DataType.URI.ordinal()][dataType.ordinal()] = "org.something";
    for (DataType dataType : DataType.values())
      testValues[DataType.Day.ordinal()][dataType.ordinal()] = DataConverter.INSTANCE
          .getDayFormat().format(new Date());
    for (DataType dataType : DataType.values())
      testValues[DataType.Duration.ordinal()][dataType.ordinal()] = Duration.standardDays(23)
          .toString();
    for (DataType dataType : DataType.values())
      testValues[DataType.Month.ordinal()][dataType.ordinal()] = DataConverter.INSTANCE
          .getMonthFormat().format(new Date());
    for (DataType dataType : DataType.values())
      testValues[DataType.MonthDay.ordinal()][dataType.ordinal()] = DataConverter.INSTANCE
          .getMonthDayFormat().format(new Date());
    for (DataType dataType : DataType.values())
      testValues[DataType.Year.ordinal()][dataType.ordinal()] = DataConverter.INSTANCE
          .getYearFormat().format(new Date());
    for (DataType dataType : DataType.values())
      testValues[DataType.YearMonth.ordinal()][dataType.ordinal()] = DataConverter.INSTANCE
          .getYearMonthFormat().format(new Date());
    for (DataType dataType : DataType.values())
      testValues[DataType.YearMonthDay.ordinal()][dataType.ordinal()] = DataConverter.INSTANCE
          .getYearMonthDayFormat().format(new Date());
    for (DataType dataType : DataType.values())
      testValues[DataType.Object.ordinal()][dataType.ordinal()] = new Object();

  }

  public void testBoolean() throws Throwable {
    checkConvertTo(DataType.Boolean);
    checkConvertFrom(DataType.Boolean);
  }

  public void testByte() throws Throwable {

    checkConvertTo(DataType.Byte);
    checkConvertFrom(DataType.Byte);
  }

  public void testBytes() throws Throwable {

    checkConvertTo(DataType.Bytes);
    checkConvertFrom(DataType.Bytes);
  }

  public void testCharacter() throws Throwable {

    checkConvertTo(DataType.Character);
    checkConvertFrom(DataType.Character);
  }

  public void testDecimal() throws Throwable {

    checkConvertTo(DataType.Decimal);
    checkConvertFrom(DataType.Decimal);
  }

  public void testDouble() throws Throwable {
    checkConvertTo(DataType.Double);
    checkConvertFrom(DataType.Double);
  }

  public void testFloat() throws Throwable {
    checkConvertTo(DataType.Float);
    checkConvertFrom(DataType.Float);
  }

  public void testInt() throws Throwable {
    checkConvertTo(DataType.Int);
    checkConvertFrom(DataType.Int);
  }

  public void testUnsignedInt() throws Throwable {
    checkConvertTo(DataType.UnsignedInt);
    checkConvertFrom(DataType.UnsignedInt);
  }

  public void testInteger() throws Throwable {
    checkConvertTo(DataType.Integer);
    checkConvertFrom(DataType.Integer);
  }

  public void testLong() throws Throwable {
    checkConvertTo(DataType.Long);
    checkConvertFrom(DataType.Long);
  }

  public void testUnsignedLong() throws Throwable {
    checkConvertTo(DataType.UnsignedLong);
    checkConvertFrom(DataType.UnsignedLong);
  }

  public void testShort() throws Throwable {
    checkConvertTo(DataType.Short);
    checkConvertFrom(DataType.Short);
  }

  public void testString() throws Throwable {
    checkConvertTo(DataType.String);
    checkConvertFrom(DataType.String);
  }

  public void testStrings() throws Throwable {
    checkConvertTo(DataType.Strings);
    checkConvertFrom(DataType.Strings);
  }

  public void testDate() throws Throwable {
    // checkConvertTo(DataType.Date);
    checkConvertFrom(DataType.Date);
  }

  /**
   * Checks the given data type as a source (argument) for all other SDO
   * datatypes, expecting conversion errors thrown from the DataConverter based
   * on the DataConverter.getAllowableTargetTypes() method.
   * 
   * @param testDataType
   * @throws Throwable
   */
  private void checkConvertFrom(DataType testDataType) throws Throwable {
    DataType[] allTypes = DataType.values();
    for (int i = 0; i < allTypes.length; i++) {
      DataType currentDataType = allTypes[i];
      Type currentType = TypeHelper.INSTANCE.getType(PlasmaRuntime.getInstance()
          .getSDODataTypesNamespace().getUri(), currentDataType.name());

      List<DataType> allowedFromTestDataType = DataConverter.INSTANCE
          .getAllowableTargetTypes(testDataType);
      // check the current type as a target type
      Class[] fromArgsTypes = { Type.class,
          DataConverter.INSTANCE.toPrimitiveJavaClass(testDataType) };
      String fromMethodName = "from" + testDataType.name();
      Method fromMethod = DataConverter.INSTANCE.getClass()
          .getMethod(fromMethodName, fromArgsTypes);
      Object[] fromArgs = { currentType,
          testValues[testDataType.ordinal()][currentDataType.ordinal()] };

      if (findDataType(currentDataType, allowedFromTestDataType)) {

        try {
          Object result = fromMethod.invoke(DataConverter.INSTANCE, fromArgs);
          log.info("(" + testDataType.name() + ") converted to " + currentDataType.toString()
              + " from " + testDataType.toString() + " (" + String.valueOf(result) + ")");
        } catch (InvocationTargetException e) {
          throw e.getTargetException();
        } catch (IllegalArgumentException e) {
          throw e;
        }
      } else {
        try {
          fromMethod.invoke(DataConverter.INSTANCE, fromArgs);
          assertTrue(
              "expected error on conversion " + testDataType.name() + "->" + currentDataType.name(),
              false); // force assert
        } catch (InvocationTargetException e) {
          assertTrue("expected class-cast-exception",
              e.getTargetException() instanceof ClassCastException);
          log.debug("(" + testDataType.name() + ") prevented conversion to "
              + currentDataType.toString() + " from " + testDataType.toString() + " ");
        }
      }
    }
  }

  /**
   * Checks the given data type as a target (result) for conversion to all other
   * SDO specified datatypes, expecting conversion errors thrown from the
   * DataConverter based on the DataConverter.getAllowableTargetTypes() method.
   * 
   * @param testDataType
   * @throws Throwable
   */
  private void checkConvertTo(DataType testDataType) throws Throwable {
    DataType[] allTypes = DataType.values();
    for (int i = 0; i < allTypes.length; i++) {
      DataType currentDataType = allTypes[i];
      Type currentType = TypeHelper.INSTANCE.getType(PlasmaRuntime.getInstance()
          .getSDODataTypesNamespace().getUri(), currentDataType.name());

      // check the current type as a source type
      List<DataType> allowedToTestDataType = DataConverter.INSTANCE
          .getAllowableTargetTypes(currentDataType);
      Class[] toArgsTypes = { Type.class, Object.class };
      String toMethodName = "to" + testDataType.name();
      Method toMethod = DataConverter.INSTANCE.getClass().getMethod(toMethodName, toArgsTypes);
      Object[] toArgs = { currentType,
          testValues[currentDataType.ordinal()][currentDataType.ordinal()] };
      if (findDataType(testDataType, allowedToTestDataType)) {
        try {
          Object result = toMethod.invoke(DataConverter.INSTANCE, toArgs);
          log.info("(" + testDataType.name() + ") converted from " + currentDataType.toString()
              + " to " + testDataType.toString() + " (" + String.valueOf(result) + ")");
        } catch (InvocationTargetException e) {
          throw e.getTargetException();
        }
      } else {
        try {
          toMethod.invoke(DataConverter.INSTANCE, toArgs);
          assertTrue(
              "expected error on conversion " + currentType.getName() + "->" + testDataType.name(),
              false);
        } catch (InvocationTargetException e) {
          // log.error(e.getMessage(), e);
          assertTrue("expected class-cast-exception not "
              + e.getTargetException().getClass().getName(),
              e.getTargetException() instanceof ClassCastException);
          log.debug("(" + testDataType.name() + ") prevented conversion from "
              + currentDataType.toString() + " to " + testDataType.toString() + " ");
        }
      }
    }
  }

  private boolean findDataType(DataType type, List<DataType> types) {
    for (DataType t : types)
      if (t.ordinal() == type.ordinal())
        return true;
    return false;
  }
}