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
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.config.PlasmaConfig;
import org.plasma.sdo.helper.DataConverter;

import commonj.sdo.Type;
import commonj.sdo.helper.TypeHelper;

/**
 * 
 */
public class DataConverterTest extends DASClientTestCase {
    private static Log log = LogFactory.getLog(DataConverterTest.class);
    
    
    private Object[][] testValues = new Object[25][1];
    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(DataConverterTest.class);
    }
    
    public void setUp() throws Exception {
        
        super.setUp();

        List<String> list = new ArrayList<String>();
        list.add("23");
        list.add("24");
        list.add("25");
        
        testValues[DataType.Boolean.ordinal()][0]        = true;
        testValues[DataType.Byte.ordinal()][0]           = (byte)23;
        testValues[DataType.Bytes.ordinal()][0]          = new byte[] {9, 10, 11, 12, 13, 14}; // string conversion should be hex values
        testValues[DataType.Character.ordinal()][0]      = (char)3;
        testValues[DataType.Decimal.ordinal()][0]        = new BigDecimal("23");
        testValues[DataType.Double.ordinal()][0]         = (double)23;
        testValues[DataType.Float.ordinal()][0]          = (float)23;
        testValues[DataType.Int.ordinal()][0]            = (int)23;
        testValues[DataType.Integer.ordinal()][0]        = new BigInteger("23");
        testValues[DataType.Long.ordinal()][0]           = (long)23;
        testValues[DataType.Short.ordinal()][0]          = (short)23;
        testValues[DataType.String.ordinal()][0]         = "23";
        testValues[DataType.Strings.ordinal()][0]        = list;
        testValues[DataType.Date.ordinal()][0]           = new Date();
        testValues[DataType.DateTime.ordinal()][0]       = DataConverter.INSTANCE.getDateTimeFormat().format(new Date());
        testValues[DataType.Time.ordinal()][0]           = DataConverter.INSTANCE.getDateTimeFormat().format(new Date());
        testValues[DataType.URI.ordinal()][0]            = "org.something";
        testValues[DataType.Day.ordinal()][0]            = DataConverter.INSTANCE.getDateTimeFormat().format(new Date());
        testValues[DataType.Duration.ordinal()][0]       = DataConverter.INSTANCE.getDateTimeFormat().format(new Date());
        testValues[DataType.Month.ordinal()][0]          = DataConverter.INSTANCE.getDateTimeFormat().format(new Date());
        testValues[DataType.MonthDay.ordinal()][0]       = DataConverter.INSTANCE.getDateTimeFormat().format(new Date());
        testValues[DataType.Year.ordinal()][0]           = DataConverter.INSTANCE.getDateTimeFormat().format(new Date());
        testValues[DataType.YearMonth.ordinal()][0]      = DataConverter.INSTANCE.getDateTimeFormat().format(new Date());
        testValues[DataType.YearMonthDay.ordinal()][0]   = DataConverter.INSTANCE.getDateTimeFormat().format(new Date());
        testValues[DataType.Object.ordinal()][0]         = new Object();
        
    }
 
    public void testBoolean() throws Throwable {
        checkAsSource(DataType.Boolean);
        checkAsTarget(DataType.Boolean);
    }

    public void testByte() throws Throwable {
        
        checkAsSource(DataType.Byte);
        checkAsTarget(DataType.Byte);
    }
    
    public void testBytes() throws Throwable {
        
        checkAsSource(DataType.Bytes);
        checkAsTarget(DataType.Bytes);
    }

    public void testCharacter() throws Throwable {
        
        checkAsSource(DataType.Character);
        checkAsTarget(DataType.Character);
    }

    public void testDecimal() throws Throwable {
        
        checkAsSource(DataType.Decimal);
        checkAsTarget(DataType.Decimal);
    }
    
    public void testDouble() throws Throwable {        
        checkAsSource(DataType.Double);
        checkAsTarget(DataType.Double);
    }

    public void testFloat() throws Throwable {        
        checkAsSource(DataType.Float);
        checkAsTarget(DataType.Float);
    }
    
    public void testInt() throws Throwable {        
        checkAsSource(DataType.Int);
        checkAsTarget(DataType.Int);
    }

    public void testInteger() throws Throwable {        
        checkAsSource(DataType.Integer);
        checkAsTarget(DataType.Integer);
    }

    public void testLong() throws Throwable {        
        checkAsSource(DataType.Long);
        checkAsTarget(DataType.Long);
    }

    public void testShort() throws Throwable {        
        checkAsSource(DataType.Short);
        checkAsTarget(DataType.Short);
    }

    public void testString() throws Throwable {        
        checkAsSource(DataType.String);
        checkAsTarget(DataType.String);
    }
    
    public void testStrings() throws Throwable {        
        checkAsSource(DataType.Strings);
        checkAsTarget(DataType.Strings);
    }

    public void testDate() throws Throwable {        
        checkAsSource(DataType.Date);
        checkAsTarget(DataType.Date);
    }
    
    /**
     * Checks the given data type as a target for all other SDO datatypes, expecting
     * conversion errors thrown from the DataConverter based on the DataConverter.getAllowableTargetTypes()
     * method. 
     * @param testDataType
     * @throws Throwable
     */
    private void checkAsTarget(DataType testDataType) throws Throwable {
        DataType[] allTypes = DataType.values();
        for (int i = 0; i< allTypes.length; i++)
        {
            DataType currentDataType = allTypes[i];
            Type currentType = TypeHelper.INSTANCE.getType(PlasmaConfig.getInstance().getSDO().getDefaultNamespace().getUri(), 
                    currentDataType.name());
            
            List<DataType> allowedFromTestDataType = DataConverter.INSTANCE.getAllowableTargetTypes(testDataType);
            // check the current type as a target type
            Class[] fromArgsTypes = { Type.class, DataConverter.INSTANCE.toPrimitiveJavaClass(testDataType)};
            String fromMethodName = "from" + testDataType.name();
            Method fromMethod = DataConverter.INSTANCE.getClass().getMethod(fromMethodName, fromArgsTypes);
            Object[] fromArgs = { currentType, testValues[testDataType.ordinal()][0] };
            
            if (findDataType(currentDataType, allowedFromTestDataType)) {
                
                try {
                    Object result = fromMethod.invoke(DataConverter.INSTANCE, fromArgs);
                    log.info("(" + testDataType.name() + ") converted to " + currentDataType.toString() 
                            + " from "+ testDataType.toString()
                            + " (" + String.valueOf(result) + ")");
                }
                catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
            else
            {
                try {
                    fromMethod.invoke(DataConverter.INSTANCE, fromArgs);
                    assertTrue("expected error on conversion " + testDataType.name()
                            + "->" + currentDataType.name(), false); // force assert
                }
                catch (InvocationTargetException e) {
                    assertTrue("expected class-cast-exception", 
                            e.getTargetException() instanceof ClassCastException);
                    log.debug("(" + testDataType.name() + ") prevented conversion to " 
                            + currentDataType.toString() + " from "+ testDataType.toString()+ " ");
                }
            }
        }       
    }
    
    /**
     * Checks the given data type as a source for conversion to all other SDO specified datatypes, expecting
     * conversion errors thrown from the DataConverter based on the DataConverter.getAllowableTargetTypes()
     * method. 
     * @param testDataType
     * @throws Throwable
     */
    private void checkAsSource(DataType testDataType) throws Throwable {
        DataType[] allTypes = DataType.values();
        for (int i = 0; i< allTypes.length; i++)
        {
            DataType currentDataType = allTypes[i];
            Type currentType = TypeHelper.INSTANCE.getType(PlasmaConfig.getInstance().getSDO().getDefaultNamespace().getUri(), 
                    currentDataType.name());
            
            
            // check the current type as a source type
            List<DataType> allowedToTestDataType = DataConverter.INSTANCE.getAllowableTargetTypes(currentDataType);
            Class[] toArgsTypes = { Type.class, Object.class};
            String toMethodName = "to" + testDataType.name();
            Method toMethod = DataConverter.INSTANCE.getClass().getMethod(toMethodName, toArgsTypes);
            Object[] toArgs = { currentType, testValues[currentDataType.ordinal()][0] };
            if (findDataType(testDataType, allowedToTestDataType)) {
                try {
                    Object result = toMethod.invoke(DataConverter.INSTANCE, toArgs);
                    log.info("(" + testDataType.name() + ") converted from " + currentDataType.toString() 
                            + " to "+ testDataType.toString()
                            + " (" + String.valueOf(result) + ")");
                }
                catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
            else
            {
                try {
                    toMethod.invoke(DataConverter.INSTANCE, toArgs);
                    assertTrue("expected error on conversion " + currentType.getName()
                            + "->" + testDataType.name(), false);
                }
                catch (InvocationTargetException e) {
                    assertTrue("expected class-cast-exception", 
                            e.getTargetException() instanceof ClassCastException);
                    log.debug("(" + testDataType.name() + ") prevented conversion from " + currentDataType.toString() 
                            + " to "+ testDataType.toString()+ " ");
                }
            }           
        }       
    }
    
    private boolean findDataType(DataType type, List<DataType> types) {
        for (DataType t: types)
            if (t.ordinal() == type.ordinal())
                return true;
        return false;
    }
}