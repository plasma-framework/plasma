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
package org.plasma.sdo.helper;



import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Duration;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.plasma.sdo.DataType;
import org.plasma.sdo.PlasmaDataObjectException;

import commonj.sdo.Property;
import commonj.sdo.Type;

public class DataConverter {
    
    private static Log log = LogFactory.getFactory().getInstance(DataConverter.class);
    static public DataConverter INSTANCE = initializeInstance();
    public static final String FORMAT_PATTERN_TIME 		= "HH:mm:ss'.'SSS'Z'";
    public static final String FORMAT_PATTERN_DATE 		= "yyyy-MM-dd'T'HH:mm:ss";
	// Note: the Java class for SDO dataTime datatype is String. This seems unfortunate
	// because a java.util.Date is certainly capable of representing
	// far more precision that than month/day/year. It seems also
    // that users would expect generated method signatures for
    // dataTime to be java.util.Date. Have to comply w/the spec though
    // but can compensate for this by allowing more precision
    // in the SDO date datatype. See above data format which allows
    // seconds precision.
    public static final String FORMAT_PATTERN_DATETIME 	= "yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'";
    public static final String FORMAT_PATTERN_DAY 		= "dd"; 
    public static final String FORMAT_PATTERN_MONTH 		= "MM";
    public static final String FORMAT_PATTERN_MONTHDAY 	= "MM-dd";
    public static final String FORMAT_PATTERN_YEAR 		= "yyyy";
    public static final String FORMAT_PATTERN_YEARMONTH 	= "yyyy-MM";
    public static final String FORMAT_PATTERN_YEARMONTHDAY = "yyyy-MM-dd";

    private static Map<Class<?>, Map<String,DataType>> javaClassToAllowableTypesMap;
    
    private static Map<Class<?>, Method> javaClassToConverterFromMethodMap;

    
    private DataConverter() {
     	
        if (javaClassToAllowableTypesMap == null) {
            javaClassToAllowableTypesMap = new HashMap<Class<?>, Map<String,DataType>>();
            for (int i = 0; i < DataType.values().length; i++) {
                DataType dataTypeEnum = DataType.values()[i];
                
                Class<?> javaClass = toPrimitiveJavaClass(dataTypeEnum);
                
                Map<String,DataType> allowableMap = new HashMap<String,DataType>();
                List<DataType> list = getAllowableTargetTypes(dataTypeEnum);
                for (DataType allowableType : list)
                    allowableMap.put(allowableType.toString(), allowableType);                
                javaClassToAllowableTypesMap.put(javaClass, allowableMap);
            } 
        }
        if (javaClassToConverterFromMethodMap == null) {
        	javaClassToConverterFromMethodMap = new HashMap<Class<?>, Method>();
        	Map<Class<?>, Method> map = javaClassToConverterFromMethodMap;
        	try {
        	
        		Method method = this.getClass().getMethod("fromBoolean", Type.class, boolean.class);
        		map.put(Boolean.class, method);
        		map.put(boolean.class, method);
        		
        		method = this.getClass().getMethod("fromByte", Type.class, byte.class);
        		map.put(Byte.class, method);
        		map.put(byte.class, method);
        		
        		method = this.getClass().getMethod("fromBytes", Type.class, byte[].class);
        		map.put(Byte[].class, method);
        		map.put(byte[].class, method);
        		
        		method = this.getClass().getMethod("fromCharacter", Type.class, char.class);
        		map.put(Character.class, method);
        		map.put(char.class, method);
        		
        		method = this.getClass().getMethod("fromDate", Type.class, Date.class);
        		map.put(Date.class, method);
        		
        		method = this.getClass().getMethod("fromDecimal", Type.class, BigDecimal.class);
        		map.put(BigDecimal.class, method);
        		
        		method = this.getClass().getMethod("fromDouble", Type.class, double.class);
        		map.put(Double.class, method);
        		map.put(double.class, method);
        		
        		method = this.getClass().getMethod("fromFloat", Type.class, float.class);
        		map.put(Float.class, method);
        		map.put(float.class, method);
        		
        		method = this.getClass().getMethod("fromInt", Type.class, int.class);
        		map.put(int.class, method);
        		map.put(Integer.class, method);
        		
        		method = this.getClass().getMethod("fromInteger", Type.class, BigInteger.class); 
        		map.put(BigInteger.class, method);
        		
        		method = this.getClass().getMethod("fromLong", Type.class, long.class);
        		map.put(Long.class, method);
        		map.put(long.class, method);

        		method = this.getClass().getMethod("fromShort", Type.class, short.class);
        		map.put(Short.class, method);
        		map.put(short.class, method);
        		
        		method = this.getClass().getMethod("fromString", Type.class, String.class);
        		map.put(String.class, method);
        		
        		//Class<?> stringListClass = (java.lang.Class<java.util.List<java.lang.String>>)new ArrayList<java.lang.String>().getClass();        		
        		method = this.getClass().getMethod("fromStrings", Type.class, List.class);         		
        		map.put(List.class, method);
        	
        	}
        	catch (NoSuchMethodException e) {
        		log.error(e.getMessage(), e);
        	}
        }
    }
    
    public DateFormat[] getDateFormats() {
    	DateFormat[] result = new DateFormat[9];
    	result[0] = DateFormatMap.get(FORMAT_PATTERN_TIME); 		
    	result[1] = DateFormatMap.get(FORMAT_PATTERN_DATE); 		
    	result[2] = DateFormatMap.get(FORMAT_PATTERN_DATETIME); 	
    	result[3] = DateFormatMap.get(FORMAT_PATTERN_DAY); 		
    	result[4] = DateFormatMap.get(FORMAT_PATTERN_MONTH); 		
    	result[5] = DateFormatMap.get(FORMAT_PATTERN_MONTHDAY); 	
    	result[6] = DateFormatMap.get(FORMAT_PATTERN_YEAR); 		
    	result[7] = DateFormatMap.get(FORMAT_PATTERN_YEARMONTH); 	
    	result[8] = DateFormatMap.get(FORMAT_PATTERN_YEARMONTHDAY);    	
    	return result;
    }
    
    /**
     * A factory for {@link SimpleDateFormat}s. The instances are stored in a
     * threadlocal way because SimpleDateFormat is not threadsafe as noted in
     * {@link SimpleDateFormat its javadoc}.
     */
    final static class DateFormatMap {
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> 
            THREADLOCAL_FORMATS = new ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>() {
            @Override
            protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
                return new SoftReference<Map<String, SimpleDateFormat>>(
                        new HashMap<String, SimpleDateFormat>());
            }
            
        };
        
        /**
         * creates a {@link SimpleDateFormat} for the requested format string.
         * 
         * @param pattern
         *            a non-<code>null</code> format String according to
         *            {@link SimpleDateFormat}. The format is not checked against
         *            <code>null</code> since all paths go through
         *            {@link DateUtils}.
         * @return the requested format. This simple dateformat should not be used
         *         to {@link SimpleDateFormat#applyPattern(String) apply} to a
         *         different pattern.
         */
        public static SimpleDateFormat get(String pattern) {
            SoftReference<Map<String, SimpleDateFormat>> ref = THREADLOCAL_FORMATS.get();
            Map<String, SimpleDateFormat> formats = ref.get();
            if (formats == null) {
                formats = new HashMap<String, SimpleDateFormat>();
                THREADLOCAL_FORMATS.set(
                        new SoftReference<Map<String, SimpleDateFormat>>(formats));    
            }
            SimpleDateFormat format = formats.get(pattern);
            if (format == null) {
                format = new SimpleDateFormat(pattern, Locale.US);
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                formats.put(pattern, format);
            }
            return format;
        }
        
    }   
    
    private static synchronized DataConverter initializeInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new DataConverter();
        return INSTANCE;
    }    
 
    public DateFormat getDateTimeFormat() {
        return DateFormatMap.get(FORMAT_PATTERN_DATETIME);
    }

    public DateFormat getTimeFormat() {
        return DateFormatMap.get(FORMAT_PATTERN_TIME);
	}

	public DateFormat getDateFormat() {
        return DateFormatMap.get(FORMAT_PATTERN_DATE);
	}

	public PeriodFormatter getDurationFormat() {
        return ISOPeriodFormat.standard();
	}

	public DateFormat getDayFormat() {
	       return DateFormatMap.get(FORMAT_PATTERN_DAY);
	}

	public DateFormat getMonthFormat() {
	       return DateFormatMap.get(FORMAT_PATTERN_MONTH);
	}

	public DateFormat getMonthDayFormat() {
	       return DateFormatMap.get(FORMAT_PATTERN_MONTHDAY);
	}

	public DateFormat getYearFormat() {
	       return DateFormatMap.get(FORMAT_PATTERN_YEAR);
	}

	public DateFormat getYearMonthFormat() {
	       return DateFormatMap.get(FORMAT_PATTERN_YEARMONTH);
	}

	public DateFormat getYearMonthDayFormat() {
	       return DateFormatMap.get(FORMAT_PATTERN_DAY);
	}

    public Object convert(Type targetType, Object value) {
        
        if (!targetType.isDataType())
            throw new IllegalArgumentException("type " + targetType.getURI() + "#" 
                    + targetType.getName() + " is not a data-type");

        Method method = javaClassToConverterFromMethodMap.get(value.getClass());
        try {
			return method.invoke(this, targetType, value);
		} catch (IllegalArgumentException e) {
		    throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
		    throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof InvalidDataConversionException)
			    throw (InvalidDataConversionException)e.getTargetException();
			else
			    throw new RuntimeException(e.getTargetException());
		}
    }
    
    public Object convert(Type targetType, Type sourceType, Object value) {
        if (!targetType.isDataType())
            throw new IllegalArgumentException("type " + targetType.getURI() + "#" 
                    + targetType.getName() + " is not a data-type");
        if (!sourceType.isDataType())
            throw new IllegalArgumentException("type " + sourceType.getURI() + "#" 
                    + sourceType.getName() + " is not a data-type");
        DataType targetDataType = DataType.valueOf(targetType.getName());
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
    
        switch (targetDataType) {
        case Boolean: return toBoolean(sourceType, value);
        case Byte:    return toByte(sourceType, value); 
        case Bytes:   return toBytes(sourceType, value); 
        case Character: return toCharacter(sourceType, value);
        case Decimal:   return toDecimal(sourceType, value);
        case Double:   return toDouble(sourceType, value);
        case Float:    return toFloat(sourceType, value);
        case Int:      return toInt(sourceType, value);
        case Integer:  return toInteger(sourceType, value);
        case Long:     return toLong(sourceType, value);
        case Short:    return toShort(sourceType, value);
        case String:   return toString(sourceType, value);
        case Strings:  return toStrings(sourceType, value);
        case Date:     return toDate(sourceType, value);
        case Duration: 
        case DateTime: 
        case Day:      
        case Month:    
        case MonthDay: 
        case Year:     
        case YearMonth:
        case YearMonthDay:
        case Time:  
            return toTemporalDataType(targetType, sourceType, value);
        case URI:      
        case Object:   
        default:
            throw new InvalidDataConversionException(targetDataType, sourceDataType, value);
        }        
    }       

    public boolean toBoolean(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Boolean:
            return ((Boolean)value).booleanValue();
        case String:
            return Boolean.parseBoolean(value.toString());
        default: 
            throw new InvalidDataConversionException(DataType.Boolean, 
                    sourceDataType, value);
        }
    }
    
    public Object fromBoolean(Type targetType, boolean value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Boolean:
            return Boolean.valueOf(value);
        case String:
            return String.valueOf(value);
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Boolean, value);
        }
    }    

    public byte toByte(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Byte:
            return ((Byte)value).byteValue();
        case Double:
            return ((Double) value).byteValue();
        case Float:
            return ((Float) value).byteValue();
        case Int:
            return ((Integer) value).byteValue();
        case Long:
            return ((Long) value).byteValue();
        case Short:
            return ((Short) value).byteValue();
        case String:
            // as per spec: 8 bits unsigned [0-9]+
            return Integer.valueOf((String)value).byteValue();
        default: 
            throw new InvalidDataConversionException(DataType.Byte, 
                    sourceDataType, value);
        }
    }

    public Object fromByte(Type targetType, byte value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        // as per spec: 8 bits unsigned [0-9]+
        switch (targetDataType) {
        case Byte:
            return Byte.valueOf(value);
        case Double:
            return Double.valueOf((int)value & 0xFF);
        case Float:
            return Float.valueOf((int)value & 0xFF);
        case Int:
            return Integer.valueOf((int)value & 0xFF);
        case Long:
            return Long.valueOf((int)value & 0xFF);
        case Short:
            return new Short(Integer.valueOf((int)value & 0xFF).shortValue());
        case String:
            return Integer.valueOf((int)value & 0xFF).toString();
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Byte, value);
        }
    }   
    
    public byte[] toBytes(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Bytes:
            return (byte[])value;
        case String:
            return ((String)value).getBytes();
        case Integer:
            return ((BigInteger)value).toByteArray();
        default: 
            throw new InvalidDataConversionException(DataType.Bytes, 
                    sourceDataType, value);
        }
    }

    public Object fromBytes(Type targetType, byte[] value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Bytes:
            return value;
        case String:
            return toHexString(value); // SDO Spec 2.1.1 Section 8.1 calls for byte array String representation to be HEX
        case Integer:
            return new BigInteger(value);
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Bytes, value);
        }
    }    
    
    public char toCharacter(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Character:
            return ((Character)value).charValue();
        case String:
            return ((String)value).charAt(0);
        default: 
            throw new InvalidDataConversionException(DataType.Character, 
                    sourceDataType, value);
        }
    }

    public Object fromCharacter(Type targetType, char value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Character:
            return Character.valueOf(value);
        case String:
            return String.valueOf(value);
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Character, value);
        }
    }    
    
    public BigDecimal toDecimal(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Decimal:
            return (BigDecimal)value;
        case Double:
        case Float:
        case Int:
        case Long:
        case Integer:
            return new BigDecimal(((Number)value).doubleValue());
        case String:
            return new BigDecimal((String)value);
        default: 
            throw new InvalidDataConversionException(DataType.Decimal, 
                    sourceDataType, value);
        }
    }

    public Object fromDecimal(Type targetType, BigDecimal value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Decimal:
            return value;
        case Double:
            return new Double(value.doubleValue());
        case Float:
            return new Float(value.floatValue());
        case Int:
            return new Integer(value.intValue());
        case Long:
            return new Long(value.longValue());
        case Integer:
            return value.toBigInteger();
        case String:
            // as per spec: ('+'|'-')? [0-9]* ('.'[0-9]*)? (('E'|'e') ('+'|'-')? [0-9]+)?
            /*
             *  [123,0]      "123"
             *   [-123,0]     "-123"
             *   [123,-1]     "1.23E+3"
             *   [123,-3]     "1.23E+5"
             *   [123,1]      "12.3"
             *   [123,5]      "0.00123"
             *   [123,10]     "1.23E-8"
             *   [-123,12]    "-1.23E-10"
             */
            return value.toString(); 
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Decimal, value);
        }
    }    
    
    public double toDouble(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Double:
            return ((Double)value).doubleValue();
        case Byte:
            return ((Byte)value).doubleValue();
        case Float:
            return ((Float)value).doubleValue();
        case Int:
            return ((Integer)value).doubleValue();
        case Long:
            return ((Long)value).doubleValue();
        case Short:
            return ((Short)value).doubleValue();
        case Integer:
            return ((BigInteger)value).doubleValue();
        case Decimal:
            return ((BigDecimal)value).doubleValue();
        case String:
            return Double.parseDouble((String)value);
            //return Double.valueOf((String)value).doubleValue();
        default: 
            throw new InvalidDataConversionException(DataType.Double, 
                    sourceDataType, value);
        }
    }

    public Object fromDouble(Type targetType, double value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Double:
            return Double.valueOf(value);
        case Byte:
            return new Byte(Double.valueOf(value).byteValue());
        case Float:
            return new Float(Double.valueOf(value).floatValue());
        case Int:
            return new Integer(Double.valueOf(value).intValue());
        case Long:
            return new Long(Double.valueOf(value).longValue());
        case Short:
            return new Short(Double.valueOf(value).shortValue());
        case Integer:
            return BigInteger.valueOf(Double.valueOf(value).longValue()); 
        case Decimal:
            return  BigDecimal.valueOf(value);
        case String:
            // as per spec:  Decimal | 'NaN' | '-NaN' | 'Infinity' | '-Infinity'
            return Double.toString(value);
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Double, value);
        }
    }    
    
    public float toFloat(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Float:
            return ((Float)value).floatValue();
        case Byte:
            return ((Byte) value).floatValue();
        case Double:
            return ((Double) value).floatValue();
        case Int:
            return ((Integer) value).floatValue();
        case Long:
            return ((Long) value).floatValue();
        case Short:
            return ((Short) value).floatValue();
        case Decimal:
            return ((BigDecimal) value).floatValue();
        case Integer:
            return ((BigInteger) value).floatValue();
        case String:
            return Float.valueOf((String) value).floatValue();
        default: 
            throw new InvalidDataConversionException(DataType.Float, 
                    sourceDataType, value);
        }        
    }

    public Object fromFloat(Type targetType, float value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Float:
            return Float.valueOf(value);
        case Byte:
            return new Byte(Float.valueOf(value).byteValue());
        case Double:
            return new Double(Float.valueOf(value).doubleValue());
        case Int:
            return new Integer(Float.valueOf(value).intValue());
        case Long:
            return new Long(Float.valueOf(value).longValue());
        case Short:
            return new Short(Float.valueOf(value).shortValue());
        case Decimal:
            return  BigDecimal.valueOf(value);
        case Integer:
            return BigInteger.valueOf(Double.valueOf(value).longValue()); 
        case String:
            // as per spec:  Decimal | 'NaN' | '-NaN' | 'Infinity' | '-Infinity'
            return Float.toString(value);
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Float, value);
        }
    }    
    
    public int toInt(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Int:
            return ((Integer)value).intValue();
        case Byte:
            return ((Byte) value).intValue();
        case Double:
            return ((Double) value).intValue();
        case Float:
            return ((Float) value).intValue();
        case Long:
            return ((Long) value).intValue();
        case Short:
            return ((Short) value).intValue();
        case Decimal:
            return ((BigDecimal) value).intValue();
        case Integer:
            return ((BigInteger) value).intValue();
        case String:
            return Integer.parseInt((String)value);
        default: 
            throw new InvalidDataConversionException(DataType.Int, 
                    sourceDataType, value);
        }
    }

    public Object fromInt(Type targetType, int value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Int:
            return Integer.valueOf(value);
        case Byte:
            return new Byte(Integer.valueOf(value).byteValue());
        case Double:
            return new Double(Integer.valueOf(value).doubleValue());
        case Float:
            return new Float(Integer.valueOf(value).floatValue());
        case Long:
            return new Long(Integer.valueOf(value).longValue());
        case Short:
            return new Short(Integer.valueOf(value).shortValue());
        case Decimal:
            return  BigDecimal.valueOf(value);
        case Integer:
            return BigInteger.valueOf(Integer.valueOf(value).longValue()); 
        case String:
            return Integer.toString(value);
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Int, value);
        }
    }    
    
    public BigInteger toInteger(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Integer:
            return (BigInteger)value;
        case Double:
            return BigInteger.valueOf(((Double) value).longValue());
        case Float:
            return BigInteger.valueOf(((Float) value).longValue());
        case Int:
            return BigInteger.valueOf(((Integer) value).longValue());
        case Long:
            return BigInteger.valueOf(((Long) value).longValue());
        case Decimal:
            return BigInteger.valueOf(((BigDecimal) value).longValue());
        case Bytes:
            return new BigInteger((byte[])value);
        case String:
            return new BigInteger((String)value);
        default: 
            throw new InvalidDataConversionException(DataType.Integer, 
                    sourceDataType, value);
        }
    }
    
    public Object fromInteger(Type targetType, BigInteger value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Integer:
            return value;
        case Double:
            return new Double(value.doubleValue());
        case Float:
            return new Float(value.floatValue());
        case Int:
            return new Integer(value.intValue());
        case Long:
            return new Long(value.longValue());
        case Decimal:
            return new BigDecimal(value.doubleValue());
        case Bytes:
            return value.toByteArray();
        case String:
            //as per spec: ('+'|'-')? [0-9]+
            return value.toString();
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Integer, value);
        }
    }    
 
    public long toLong(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Long:
            return ((Long)value).longValue();
        case Byte:
            return ((Byte) value).longValue();
        case Double:
            return ((Double) value).longValue();
        case Float:
            return ((Float) value).longValue();
        case Int:
            return ((Integer) value).longValue();
        case Short:
            return ((Short) value).longValue();
        case Decimal:
            return ((BigDecimal) value).longValue();
        case Integer:
            return ((BigInteger) value).longValue();
        case Date:
            return ((Date)value).getTime();
        case String:
            return Long.parseLong((String)value);
        default: 
            throw new InvalidDataConversionException(DataType.Long, 
                    sourceDataType, value);
        }
    }
    
    public Object fromLong(Type targetType, long value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Long:
            return Long.valueOf(value);
        case Byte:
            return new Byte(Long.valueOf(value).byteValue());
        case Double:
            return new Double(Long.valueOf(value).doubleValue());
        case Float:
            return new Float(Long.valueOf(value).floatValue());
        case Int:
            return new Integer(Long.valueOf(value).intValue());
        case Short:
            return new Short(Long.valueOf(value).shortValue());
        case Decimal:
            return new BigDecimal(Long.valueOf(value).longValue());
        case Integer:
            return BigInteger.valueOf(Long.valueOf(value).longValue());
        case Date:
            return new Date(value);
        case String:
            return Long.toString(value);
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Long, value);
        }
    }    

    public short toShort(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Short:
            return ((Short)value).shortValue();
        case Byte:
            return ((Byte) value).shortValue();
        case Double:
            return ((Double) value).shortValue();
        case Float:
            return ((Float) value).shortValue();
        case Int:
            return ((Integer) value).shortValue();
        case Long:
            return ((Long) value).shortValue();
        case String:
            return Short.parseShort((String)value);
        default: 
            throw new InvalidDataConversionException(DataType.Short, 
                    sourceDataType, value);
        }
    }

    public Object fromShort(Type targetType, short value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Short:
            return Short.valueOf(value);
        case Byte:
            return new Byte(Short.valueOf(value).byteValue());
        case Double:
            return new Double(Short.valueOf(value).doubleValue());
        case Float:
            return new Float(Short.valueOf(value).floatValue());
        case Int:
            return new Integer(Short.valueOf(value).intValue());
        case Long:
            return new Long(Short.valueOf(value).longValue());
        case String:
            return Short.valueOf(value).toString();
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Short, value);
        }
    }    
    
    /**
     * Converts the given value to a string. Uses java.util.Arrays
     * formatting if an array of objects of the target type is detected.   
     * @param sourceType the target datatype
     * @param value the value to convert
     * @return the string value
     * @throws InvalidDataConversionException if the given source 
     * type cannot be converted to string as per the SDO 2.1 
     * datatype conversion table.
     * @throws IllegalArgumentException if the given value is not
     * the expected Java type as per the SDO 2.1 specification
     */
    @SuppressWarnings("unchecked")
    public String toString(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case String:
        case DateTime: 
        case Month:    
        case MonthDay: 
        case Day:      
        case Time:     
        case Year:     
        case YearMonth:
        case YearMonthDay:
        case Duration: 
        case URI:     
        	if (value instanceof String) {
                return (String)value;
        	}
        	else {
        		if (value instanceof List) {
        			List<?> list = (List<?>)value;        			
        			for (Object listValue : (List<?>)value) 
        				if (!(listValue instanceof String))
        					throwExpectedInstance(sourceDataType, String.class, listValue.getClass());
        			return Arrays.toString(list.toArray());
        		}
        		else {
					throwExpectedInstance(sourceDataType, String.class, value.getClass());
        		}
        	}
        case Decimal: 
        	if (value instanceof BigDecimal) {
                return ((BigDecimal)value).toString();
        	}
        	else {
        		if (value instanceof List) {
        			List<?> list = (List<?>)value;        			
        			for (Object listValue : (List<?>)value) 
        				if (!(listValue instanceof BigDecimal))
        					throwExpectedInstance(sourceDataType, BigDecimal.class, listValue.getClass());
        			return Arrays.toString(list.toArray());
        		}
        		else {
					throwExpectedInstance(sourceDataType, BigDecimal.class, value.getClass());
        		}
        	}
        case Bytes:  
        	if (value instanceof byte[]) {
                return toHexString(((byte[])value)); // as per spec: [0-9A-F]+
        	}
        	else {
        		if (value instanceof List) {
        			List<String> result = new ArrayList<String>();
        			List<?> list = (List<?>)value;        			
        			for (Object listValue : list) {
        				if (!(listValue instanceof byte[]))
        					throwExpectedInstance(sourceDataType, byte[].class, listValue.getClass());
        				result.add(toHexString((byte[])listValue));
        			}       			
        			return Arrays.toString(result.toArray());
        		}
        		else {
					throwExpectedInstance(sourceDataType, byte[].class, value.getClass());
        		}
        	}
        case Byte:   
        	if (value instanceof Byte) {
        		// as per spec: 8 bits unsigned [0-9]+
                return Integer.valueOf(((Byte)value).byteValue() & 0xFF).toString();
        	}
        	else {
        		if (value instanceof List) {
        			List<String> result = new ArrayList<String>();
        			List<?> list = (List<?>)value;        			
        			for (Object listValue : list) {
        				if (!(listValue instanceof Byte))
        					throwExpectedInstance(sourceDataType, Byte.class, listValue.getClass());
        				result.add(Integer.valueOf(((Byte)listValue).byteValue() & 0xFF).toString());
        			}       			
        			return Arrays.toString(result.toArray());
        		}
        		else {
					throwExpectedInstance(sourceDataType, Byte.class, value.getClass());
        		}
        	}   	
        case Boolean:  
        	return toString(sourceDataType, Boolean.class, value);
        case Character:
        	return toString(sourceDataType, Character.class, value);
        case Double:   
        	return toString(sourceDataType, Double.class, value);
        case Float:    
        	return toString(sourceDataType, Float.class, value);
        case Int:      
        	return toString(sourceDataType, Integer.class, value);
        case Integer:  
        	return toString(sourceDataType, BigInteger.class, value);
        case Long:  
        	return toString(sourceDataType, Long.class, value);
        case Short:   
        	return toString(sourceDataType, Short.class, value);
        case Strings:  
        	// FIXME: add many condition
        	if (!(value instanceof List))
        		throw new IllegalArgumentException("expected value as class "
        			+ List.class.getName() + ", not " 
                    + value.getClass().getName() + ", for datatype '"
        			+ sourceDataType.name() + "'");
            StringBuffer buf = new StringBuffer();
            List<String> list = (List<String>)value;
            Iterator<String> iter = list.iterator();
            for (int i = 0; iter.hasNext(); i++) {
                if (i > 0)
                    buf.append(" ");
                buf.append(iter.next());
            }
            return buf.toString();
        case Date:     
        	if (value instanceof Date) {
        		return this.getDateFormat().format(value);
        	}
        	else {
        		if (value instanceof List) {
        			List<String> result = new ArrayList<String>();
        			List<?> dateList = (List<?>)value;        			
        			for (Object listValue : dateList) {
        				if (!(listValue instanceof Date))
        					throwExpectedInstance(sourceDataType, Date.class, listValue.getClass());
        				result.add(this.getDateFormat().format(value));
        			}       			
        			return Arrays.toString(result.toArray());
        		}
        		else {
					throwExpectedInstance(sourceDataType, Date.class, value.getClass());
        		}
        	}   	
        case Object:   
        	// FIXME: add many condition
            return String.valueOf(value);
        default: 
            throw new InvalidDataConversionException(DataType.String, 
                    sourceDataType, value);
        }
    }
    
    private String toString(DataType sourceDataType, Class<?> typeClass, Object value) {
    	
    	String result = null;
    	if (typeClass.isAssignableFrom(value.getClass())) {
    		result = String.valueOf(value);
    	}
    	else {
    		if (value instanceof List) {
    			List<?> list = (List<?>)value;        			
    			for (Object listValue : (List<?>)value) 
    				if (!(typeClass.isAssignableFrom(listValue.getClass())))
    					throwExpectedInstance(sourceDataType, typeClass, listValue.getClass());        			
    			result = Arrays.toString(list.toArray());
    		}
    		else {
				throwExpectedInstance(sourceDataType, typeClass, value.getClass());
    		}
    	}  
    	return result;
    }
    
    /**
     * Converts the given value to a string. Uses java.util.Arrays
     * formatting if the target property is a 'many' property. 
     * If an array of objects of the target type is detected for the given value, 
     * and the given property is not a 'many' property, 
     * an IllegalArgumentException is thrown.   
     * @param sourceProperty the property type for the given property
     * @param value the value to convert
     * @return the string value
     * @throws InvalidDataConversionException If an array of objects of the target type 
     * is detected for the given value, 
     * and the given property is not a 'many' property
     * @throws IllegalArgumentException if the given value is not
     * the expected Java type as per the SDO 2.1 specification
     * @throws IllegalArgumentException if an array of objects of the target type is detected for the given value, 
     * and the given property is not a 'many' property
     */
    @SuppressWarnings("unchecked")
    public String toString(Property property, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(property.getType().getName());
        switch (sourceDataType) {
        case String:
        case DateTime: 
        case Month:    
        case MonthDay: 
        case Day:      
        case Time:     
        case Year:     
        case YearMonth:
        case YearMonthDay:
        case Duration: 
        case URI:     
        	if (!property.isMany()) {
        		if (value instanceof String) {
        			return (String)value;
        		}
        		else
        			throwExpectedInstance(sourceDataType, String.class, value.getClass());
        	}
        	else {
        		if (value instanceof List) {
        			List<?> list = (List<?>)value;        			
        			for (Object listValue : (List<?>)value) 
        				if (!(listValue instanceof String))
        					throwExpectedInstance(sourceDataType, String.class, listValue.getClass());
        			return Arrays.toString(list.toArray());
        		}
        		else {
        			throw new IllegalArgumentException("expected value as instanceof List for 'many' property, "
        					+ property.toString());
        		}
        	}
        case Decimal: 
        	if (!property.isMany()) {
        		if (value instanceof BigDecimal) {
        			return ((BigDecimal)value).toString();
        		}
        		else
        			throwExpectedInstance(sourceDataType, BigDecimal.class, value.getClass());
        	}
        	else {
        		if (value instanceof List) {
        			List<?> list = (List<?>)value;        			
        			for (Object listValue : (List<?>)value) 
        				if (!(listValue instanceof BigDecimal))
        					throwExpectedInstance(sourceDataType, BigDecimal.class, listValue.getClass());
        			return Arrays.toString(list.toArray());
        		}
        		else {
        			throw new IllegalArgumentException("expected value as instanceof List for 'many' property, "
        					+ property.toString());
        		}
        	}
        case Bytes:  
        	if (!property.isMany()) {
        		if (value instanceof byte[]) {
        			return toHexString(((byte[])value)); // as per spec: [0-9A-F]+
        		}
        		else
        			throwExpectedInstance(sourceDataType, byte[].class, value.getClass());
        	}
        	else {
        		if (value instanceof List) {
        			List<String> result = new ArrayList<String>();
        			List<?> list = (List<?>)value;        			
        			for (Object listValue : list) {
        				if (!(listValue instanceof byte[]))
        					throwExpectedInstance(sourceDataType, byte[].class, listValue.getClass());
        				result.add(toHexString((byte[])listValue));
        			}       			
        			return Arrays.toString(result.toArray());
        		}
        		else {
        			throw new IllegalArgumentException("expected value as instanceof List for 'many' property, "
        					+ property.toString());
        		}
        	}
        case Byte:  
        	if (!property.isMany()) {
        		if (value instanceof Byte) {
            		// as per spec: 8 bits unsigned [0-9]+
                    return Integer.valueOf(((Byte)value).byteValue() & 0xFF).toString();
        		}
        		else
        			throwExpectedInstance(sourceDataType, Byte.class, value.getClass());
        	}
        	else {
        		if (value instanceof List) {
        			List<String> result = new ArrayList<String>();
        			List<?> list = (List<?>)value;        			
        			for (Object listValue : list) {
        				if (!(listValue instanceof Byte))
        					throwExpectedInstance(sourceDataType, Byte.class, listValue.getClass());
        				result.add(Integer.valueOf(((Byte)listValue).byteValue() & 0xFF).toString());
        			}       			
        			return Arrays.toString(result.toArray());
        		}
        		else {
        			throw new IllegalArgumentException("expected value as instanceof List for 'many' property, "
        					+ property.toString());
        		}
        	}
        case Boolean:  
        	return toString(property, Boolean.class, value);
        case Character:
        	return toString(property, Character.class, value);
        case Double:   
        	return toString(property, Double.class, value);
        case Float:    
        	return toString(property, Float.class, value);
        case Int:      
        	return toString(property, Integer.class, value);
        case Integer:  
        	return toString(property, BigInteger.class, value);
        case Long:  
        	return toString(property, Long.class, value);
        case Short:   
        	return toString(property, Short.class, value);
        case Strings:  
        	// FIXME: add many condition
        	if (!(value instanceof List))
        		throw new IllegalArgumentException("expected value as class "
        			+ List.class.getName() + ", not " 
                    + value.getClass().getName() + ", for datatype '"
        			+ sourceDataType.name() + "'");
            StringBuffer buf = new StringBuffer();
            List<String> list = (List<String>)value;
            Iterator<String> iter = list.iterator();
            for (int i = 0; iter.hasNext(); i++) {
                if (i > 0)
                    buf.append(" ");
                buf.append(iter.next());
            }
            return buf.toString();
        case Date:   
        	if (!property.isMany()) {
        		if (value instanceof Date) {
        			return this.getDateFormat().format(value);
        		}
        		else
        			throwExpectedInstance(sourceDataType, Date.class, value.getClass());
        	}
        	else {
        		if (value instanceof List) {
        			List<String> result = new ArrayList<String>();
        			List<?> dateList = (List<?>)value;        			
        			for (Object listValue : dateList) {
        				if (!(listValue instanceof Date))
        					throwExpectedInstance(sourceDataType, Date.class, listValue.getClass());
        				result.add(this.getDateFormat().format(value));
        			}       			
        			return Arrays.toString(result.toArray());
        		}
        		else {
        			throw new IllegalArgumentException("expected value as instanceof List for 'many' property, "
        					+ property.toString());
        		}
        	}
        case Object:   
        	// FIXME: add many condition
            return String.valueOf(value);
        default: 
            throw new InvalidDataConversionException(DataType.String, 
                    sourceDataType, value);
        }
    }
    
    private String toString(Property property, Class<?> typeClass, Object value) {
    	
    	DataType sourceDataType = DataType.valueOf(property.getType().getName());
    	String result = null;
   	    	
    	if (!property.isMany()) {
    		if (typeClass.isAssignableFrom(value.getClass())) {
    			result = String.valueOf(value);
    		}
    		else
    			throwExpectedInstance(sourceDataType, Date.class, value.getClass());
    	}
    	else {
    		if (value instanceof List) {
        		if (value instanceof List) {
        			List<?> list = (List<?>)value;        			
        			for (Object listValue : (List<?>)value) 
        				if (!(typeClass.isAssignableFrom(listValue.getClass())))
        					throwExpectedInstance(sourceDataType, typeClass, listValue.getClass());        			
        			result = Arrays.toString(list.toArray());
        		}
        		else {
    				throwExpectedInstance(sourceDataType, typeClass, value.getClass());
        		}
    		}
    		else {
    			throw new IllegalArgumentException("expected value as instanceof List for 'many' property, "
    					+ property.toString());
    		}
    	}
    	return result;
    }
    
    private void throwExpectedInstance(DataType dataType, 
    		Class expectedClass, Class foundClass) {
	    throw new IllegalArgumentException("expected value or list of class "
			    + expectedClass.getName() + ", not " 
			    + foundClass.getName() + ", for datatype '"
			    + dataType.name() + "'");    	
    }

    /**
     * Converts the given string value to an object appropriate
     * for the target type. If java.util.Arrays formatting
     * is detected for the given string value, the formatting is
     * removed and the arrays converted into a list
     * of elements appropriate for the target type.
     * @param targetType the target data type
     * @param value the value
     * @return the converted value
     */
    public Object fromString(Type targetType, String value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case String:
        	if (!value.startsWith("[")) { 
                return value;
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<String> list = new ArrayList<String>();            	
            	for (String arrayValue : strings)
            		list.add(arrayValue);
            	return list;
        	}
        case Decimal: 
        	if (!value.startsWith("[")) { 
                return new BigDecimal(value);
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<BigDecimal> list = new ArrayList<BigDecimal>();            	
            	for (String arrayValue : strings)
            		list.add(new BigDecimal(arrayValue));
            	return list;
        	}
        case Bytes:
        	if (!value.startsWith("[")) { 
                return value.getBytes(); // FIXME: charset?
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<byte[]> list = new ArrayList<byte[]>();            	
            	for (String arrayValue : strings)
            		list.add(value.getBytes()); // FIXME: charset?
            	return list;
        	}
        case Byte:   
        	if (!value.startsWith("[")) { 
        		byte[] byteArray = value.getBytes(); // FIXME: charset?
                return new Byte(byteArray[0]); //TODO: truncation warning?
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<Byte> list = new ArrayList<Byte>();            	
            	byte[] byteArray = null;
            	for (String arrayValue : strings) {
            		byteArray = arrayValue.getBytes();
            		list.add( new Byte(byteArray[0]));
            	}
            	return list;
        	}
        case Boolean:  
        	if (!value.startsWith("[")) { 
                return Boolean.valueOf(value);
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<Boolean> list = new ArrayList<Boolean>();            	
            	for (String arrayValue : strings)
            		list.add(Boolean.valueOf(arrayValue));
            	return list;
        	}
        case Character:
        	if (!value.startsWith("[")) { 
                return Character.valueOf(value.charAt(0)); // TODO: truncation warning?
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<Character> list = new ArrayList<Character>();            	
            	for (String arrayValue : strings)
            		list.add(Character.valueOf(arrayValue.charAt(0)));
            	return list;
        	}
        case Double:  
        	if (!value.startsWith("[")) { 
                return Double.valueOf(value);
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<Double> list = new ArrayList<Double>();            	
            	for (String arrayValue : strings)
            		list.add(Double.valueOf(arrayValue));
            	return list;
        	}
        case Float:    
        	if (!value.startsWith("[")) { 
                return Float.valueOf(value);
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<Float> list = new ArrayList<Float>();            	
            	for (String arrayValue : strings)
            		list.add(Float.valueOf(arrayValue));
            	return list;
        	}
        case Int:      
        	if (!value.startsWith("[")) { 
                return Integer.valueOf(value);
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<Integer> list = new ArrayList<Integer>();            	
            	for (String arrayValue : strings)
            		list.add(Integer.valueOf(arrayValue));
            	return list;
        	}
        case Integer:  
        	if (!value.startsWith("[")) { 
                return new BigInteger(value);
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<BigInteger> list = new ArrayList<BigInteger>();            	
            	for (String arrayValue : strings)
            		list.add(new BigInteger(arrayValue));
            	return list;
        	}
        case Long:     
        	if (!value.startsWith("[")) { 
                return Long.valueOf(value);
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<Long> list = new ArrayList<Long>();            	
            	for (String arrayValue : strings)
            		list.add(Long.valueOf(arrayValue));
            	return list;
        	}
        case Short:    
        	if (!value.startsWith("[")) { 
                return Short.valueOf(value);
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<Short> list = new ArrayList<Short>();            	
            	for (String arrayValue : strings)
            		list.add(Short.valueOf(arrayValue));
            	return list;
        	}
        case Strings:  
        	if (!value.startsWith("[")) { 
                String[] values = value.split("\\s");
                List<String> list = new ArrayList<String>(values.length);
                for (int i = 0; i < values.length; i++)
                    list.add(values[i]); 
                return list;
        	}
        	else {
        		// don't replace whitespace internal to individual 'strings' value
        		String tempValue = value.replaceAll("[\\[\\]]", "");
        		tempValue.trim(); // just trim Arrays formatting
        		String[] strings = tempValue.split(",");
            	List<List<String>> list = new ArrayList<List<String>>();            	
            	for (String arrayValue : strings) {
                    String[] values = arrayValue.split("\\s");
                    List<String> subList = new ArrayList<String>(values.length);
                    for (int i = 0; i < values.length; i++)
                    	subList.add(values[i]); 
                    list.add(subList);
            	}
            	return list;
        	}
        case Date:                 
            try {
	        	if (!value.startsWith("[")) { 
	                return getDateFormat().parse(value);
	        	}
	        	else {
	        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
	            	List<Date> list = new ArrayList<Date>();            	
	            	for (String arrayValue : strings)
	            		list.add(getDateFormat().parse(arrayValue));
	            	return list;
	        	}
            } catch (ParseException e) {
                throw new PlasmaDataObjectException(e);
            }
        case DateTime:
        case Month:    
        case MonthDay: 
        case URI:  
        case Day:      
        case Duration: 
        case Time:     
        case Year:     
        case YearMonth:
        case YearMonthDay:
        	// TODO: See lexical XML Schema string representation for these types
        	if (!value.startsWith("[")) { 
                return value;
        	}
        	else {
        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
            	List<String> list = new ArrayList<String>();            	
            	for (String arrayValue : strings)
            		list.add(arrayValue);
            	return list;
        	}
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.String, value);
        }
    }    

    
    /**
     * Converts the given string value to an object appropriate
     * for the target property and its type. If the given property is a 'many' property,
     * java.util.Arrays formatting is expected.
     * Any java.util.Arrays formatting is
     * removed and the arrays converted into a list
     * of elements appropriate for the target type.
     * @param targetType the target data type
     * @param value the value to convert from string
     * @return the converted value
     * @throws IllegalArgumentException if the given property is a 'many' property and no  
     * java.util.Arrays formatting is detected for the given value.
     */
    public Object fromString(Property targetProperty, String value)
    {    
        DataType targetDataType = DataType.valueOf(targetProperty.getType().getName());
        switch (targetDataType) {
        case String:
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
                    return value;
            	}
            	else { // ignore arrays formatting for singular properties as these are allowed to contain '['
            		return value;
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<String> list = new ArrayList<String>();            	
                	for (String arrayValue : strings)
                		list.add(arrayValue);
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Decimal: 
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
            		return new BigDecimal(value);
            	}
            	else { 
            		throw new IllegalArgumentException("no java.util.Arrays formatting expected for the given value, for the given singular property, "
            				+ targetProperty.toString());
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<BigDecimal> list = new ArrayList<BigDecimal>();            	
                	for (String arrayValue : strings)
                		list.add(new BigDecimal(arrayValue));
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Bytes:
        	if (!targetProperty.isMany()) {
        		return value.getBytes(); // FIXME: charset?
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<byte[]> list = new ArrayList<byte[]>();            	
                	for (String arrayValue : strings)
                		list.add(value.getBytes()); // FIXME: charset?
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Byte:  
        	if (!targetProperty.isMany()) {
        		byte[] byteArray = value.getBytes(); // FIXME: charset?
                return new Byte(byteArray[0]); //TODO: truncation warning?
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<Byte> list = new ArrayList<Byte>();            	
                	byte[] byteArray = null;
                	for (String arrayValue : strings) {
                		byteArray = arrayValue.getBytes();
                		list.add( new Byte(byteArray[0]));
                	}
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Boolean:  
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
                    return Boolean.valueOf(value);
            	}
            	else { 
            		throw new IllegalArgumentException("no java.util.Arrays formatting expected for the given value, for the given singular property, "
            				+ targetProperty.toString());
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<Boolean> list = new ArrayList<Boolean>();            	
                	for (String arrayValue : strings)
                		list.add(Boolean.valueOf(arrayValue));
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Character:
        	if (!targetProperty.isMany()) {
                return Character.valueOf(value.charAt(0)); // TODO: truncation warning?
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<Character> list = new ArrayList<Character>();            	
                	for (String arrayValue : strings)
                		list.add(Character.valueOf(arrayValue.charAt(0)));
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Double:  
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
            		return Double.valueOf(value);
            	}
            	else { 
            		throw new IllegalArgumentException("no java.util.Arrays formatting expected for the given value, for the given singular property, "
            				+ targetProperty.toString());
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<Double> list = new ArrayList<Double>();            	
                	for (String arrayValue : strings)
                		list.add(Double.valueOf(arrayValue));
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Float:    
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
            		return Float.valueOf(value);
            	}
            	else { 
            		throw new IllegalArgumentException("no java.util.Arrays formatting expected for the given value, for the given singular property, "
            				+ targetProperty.toString());
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<Float> list = new ArrayList<Float>();            	
                	for (String arrayValue : strings)
                		list.add(Float.valueOf(arrayValue));
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Int:      
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
            		return Integer.valueOf(value);
            	}
            	else { 
            		throw new IllegalArgumentException("no java.util.Arrays formatting expected for the given value, for the given singular property, "
            				+ targetProperty.toString());
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<Integer> list = new ArrayList<Integer>();            	
                	for (String arrayValue : strings)
                		list.add(Integer.valueOf(arrayValue));
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Integer:  
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
            		return new BigInteger(value);
            	}
            	else { 
            		throw new IllegalArgumentException("no java.util.Arrays formatting expected for the given value, for the given singular property, "
            				+ targetProperty.toString());
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<BigInteger> list = new ArrayList<BigInteger>();            	
                	for (String arrayValue : strings)
                		list.add(new BigInteger(arrayValue));
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Long:     
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
            		return Long.valueOf(value);
            	}
            	else { 
            		throw new IllegalArgumentException("no java.util.Arrays formatting expected for the given value, for the given singular property, "
            				+ targetProperty.toString());
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<Long> list = new ArrayList<Long>();            	
                	for (String arrayValue : strings)
                		list.add(Long.valueOf(arrayValue));
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Short:    
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
            		 return Short.valueOf(value);
            	}
            	else { 
            		throw new IllegalArgumentException("no java.util.Arrays formatting expected for the given value, for the given singular property, "
            				+ targetProperty.toString());
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<Short> list = new ArrayList<Short>();            	
                	for (String arrayValue : strings)
                		list.add(Short.valueOf(arrayValue));
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Strings:  
        	if (!targetProperty.isMany()) {
                String[] values = value.split("\\s");
                List<String> list = new ArrayList<String>(values.length);
                for (int i = 0; i < values.length; i++)
                    list.add(values[i]); 
                return list;
        	}
        	else {
            	if (value.startsWith("[")) { 
            		// don't replace whitespace internal to individual 'strings' value
            		String tempValue = value.replaceAll("[\\[\\]]", "");
            		tempValue.trim(); // just trim Arrays formatting
            		String[] strings = tempValue.split(",");
                	List<List<String>> list = new ArrayList<List<String>>();            	
                	for (String arrayValue : strings) {
                        String[] values = arrayValue.split("\\s");
                        List<String> subList = new ArrayList<String>(values.length);
                        for (int i = 0; i < values.length; i++)
                        	subList.add(values[i]); 
                        list.add(subList);
                	}
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        case Date: 
        	try {
	        	if (!targetProperty.isMany()) {
	        		 return getDateFormat().parse(value);
	        	}
	        	else {
	            	if (value.startsWith("[")) { 
		        		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
		            	List<Date> list = new ArrayList<Date>();            	
		            	for (String arrayValue : strings)
		            		list.add(getDateFormat().parse(arrayValue));
		            	return list;
	            	} 
	            	else {
	            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
	            				+ targetProperty.toString());
	            	}
	        	}
            } catch (ParseException e) {
                throw new PlasmaDataObjectException(e);
            }
        case DateTime:
        case Month:    
        case MonthDay: 
        case URI:  
        case Day:      
        case Duration: 
        case Time:     
        case Year:     
        case YearMonth:
        case YearMonthDay:
        	if (!targetProperty.isMany()) {
            	if (!value.startsWith("[")) { 
                	// TODO: See lexical XML Schema string representation for these types
                    return value;
            	}
            	else { 
            		throw new IllegalArgumentException("no java.util.Arrays formatting expected for the given value, for the given singular property, "
            				+ targetProperty.toString());
            	}
        	}
        	else {
            	if (value.startsWith("[")) { 
            		String[] strings = value.replaceAll("[\\[\\]\\s]", "").split(",");
                	List<String> list = new ArrayList<String>();            	
                	for (String arrayValue : strings)
                		list.add(arrayValue);
                	return list;
            	} 
            	else {
            		throw new IllegalArgumentException("no java.util.Arrays formatting detected for the given value, for the given 'many' property, "
            				+ targetProperty.toString());
            	}
        	}
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.String, value);
        }
    }    
    
    public List<String> toStrings(Type sourceType, Object value)
    {    
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Strings:
            return (List<String>)value;
        case String:
            String[] values = ((String)value).split("\\s");
            List<String> list = new ArrayList<String>(values.length);
            for (int i = 0; i < values.length; i++)
                list.add(values[i]); // what no Java 5 sugar for this ??
            return list;
        default: 
            throw new InvalidDataConversionException(DataType.Strings, 
                    sourceDataType, value);
        }
    }

    public Object fromStrings(Type targetType, List<String> value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Strings:
            return value;
        case String:
            StringBuffer buf = new StringBuffer();
            Iterator<String> iter = value.iterator();
            for (int i = 0; iter.hasNext(); i++) {
                if (i > 0)
                    buf.append(" ");
                buf.append(iter.next());
            }
            return buf.toString();
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Strings, value);
        }
    }    
    
    public java.util.Date toDate(Type sourceType, Object value)
    {    
    	DateFormat format = null;
    	
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Date:
            return (Date)value;
        case Long:
            return new Date(((Long)value).longValue());
        case Day:
        	format = getDayFormat();
        	break;
        case Month:
        	format = getMonthFormat();
        	break;
        case MonthDay:
        	format = getMonthDayFormat();
        	break;
        case Time:
        	format = getTimeFormat();
        	break;
        case Year:
        	format = getYearFormat();
        	break;
        case YearMonth:
        	format = getYearMonthFormat();
        	break;
        case YearMonthDay:
        	format = getYearMonthDayFormat();
        	break;
        case Duration: // FIXME use correct parse
        case DateTime:
        case String:
        	format = getDateTimeFormat();
        	break;
        default: 
            throw new InvalidDataConversionException(DataType.Date, 
                    sourceDataType, value);
        }
	     
	    try {
	    	return format.parse((String)value);
        } catch (ParseException e) {
        	if (format instanceof SimpleDateFormat)
                throw new InvalidDataFormatException(
            	    "expected "+sourceDataType.toString()+" pattern '" + ((SimpleDateFormat)format).toPattern() 
            	     + "' for value '" + value.toString() + "'", e);
        	else
                throw new InvalidDataFormatException(
                	    "expected "+sourceDataType.toString()+" pattern '" + "unknown" 
                	     + "' for value '" + value.toString() + "'", e);
        }
    }

    public Object fromDate(Type targetType, Date value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        switch (targetDataType) {
        case Date:
            return value;
        case DateTime:
            return this.getDateTimeFormat().format(value);
        case Day:
            return this.getDayFormat().format(value);
        case Month:
            return this.getMonthFormat().format(value);
        case MonthDay:
            return this.getMonthDayFormat().format(value);
        case Time:
            return this.getTimeFormat().format(value);
        case Year:
            return this.getYearFormat().format(value);
        case YearMonth:
            return this.getYearMonthFormat().format(value);
        case YearMonthDay:
            return this.getYearMonthDayFormat().format(value);
        case Long:
            return new Long(value.getTime());
        case String: // use format with max precision
            return this.getDateTimeFormat().format(value);
        case Duration:    
            return new Duration(value.getTime());
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    DataType.Date, value);
        }
    }    
    
    public String toTemporalDataType(Type targetType, Type sourceType, Object value)
    { 
        DataType targetDataType = DataType.valueOf(targetType.getName());
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (sourceDataType) {
        case Date:
        case String:
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    sourceDataType, value);
        }
    }

    public Object fromTemporalDataType(Type targetType, Type sourceType, String value)
    {    
        DataType targetDataType = DataType.valueOf(targetType.getName());
        DataType sourceDataType = DataType.valueOf(sourceType.getName());
        switch (targetDataType) {
        default: 
            throw new InvalidDataConversionException(targetDataType, 
                    sourceDataType, value);
        }
    }    
    
    /**
     * Returns a primitive Java class, wherever possible, for 
     * the given SDO data-type (as 
     * per the SDO Specification 2.10 Section 8.1), and where
     * no primitive exists for the give SDO datatype, the appropriate
     * class is returned.  
     * @param dataType the SDO datatype
     * @return the primitive Java class.
     */
    public Class<?> toPrimitiveJavaClass(DataType dataType) {
        
        switch (dataType) {
        case Boolean:  return boolean.class;   
        case Byte:     return byte.class;  
        case Bytes:    return byte[].class;  
        case Character:  return char.class;
        case Date:       return java.util.Date.class;
        case DateTime:   return String.class;
        case Day:        return String.class;
        case Decimal:    return java.math.BigDecimal.class;
        case Double:     return double.class;
        case Duration:   return String.class;
        case Float:      return float.class;
        case Int:        return int.class;
        case Integer:    return java.math.BigInteger.class;
        case Long:       return long.class;
        case Month:      return String.class;
        case MonthDay:   return String.class;
        case Object:     return java.lang.Object.class;
        case Short:      return short.class;
        case String:     return String.class;
        case Strings:    return java.util.List.class; // of String
        case Time:       return String.class;
        case URI:        return String.class;
        case Year:       return String.class;
        case YearMonth:  return String.class;
        case YearMonthDay:    return String.class;  
        default:
            throw new PlasmaDataObjectException("unknown SDO datatype, " 
                    + dataType.toString());
        }
    }

    /**
     * Returns a primitive wrapper Java class, wherever possible, for 
     * the given SDO data-type (as 
     * per the SDO Specification 2.10 Section 8.1), and where
     * no primitive exists for the give SDO datatype, the appropriate
     * class is returned.  
     * @param dataType the SDO datatype
     * @return the primitive Java class.
     */
    public Class<?> toWrapperJavaClass(DataType dataType) {
        
        switch (dataType) {
        case Boolean:  return Boolean.class;   
        case Byte:     return Byte.class;  
        case Bytes:    return byte[].class;  
        case Character:  return Character.class;
        case Date:       return java.util.Date.class;
        case DateTime:   return String.class;
        case Day:        return String.class;
        case Decimal:    return java.math.BigDecimal.class;
        case Double:     return Double.class;
        case Duration:   return String.class;
        case Float:      return Float.class;
        case Int:        return Integer.class;
        case Integer:    return java.math.BigInteger.class;
        case Long:       return Long.class;
        case Month:      return String.class;
        case MonthDay:   return String.class;
        case Object:     return java.lang.Object.class;
        case Short:      return Short.class;
        case String:     return String.class;
        case Strings:    return java.util.List.class; // of String
        case Time:       return String.class;
        case URI:        return String.class;
        case Year:       return String.class;
        case YearMonth:  return String.class;
        case YearMonthDay:    return String.class;  
        default:
            throw new PlasmaDataObjectException("unknown SDO datatype, " 
                    + dataType.toString());
        }
    }
    
    public List<DataType> getAllowableTargetTypes(DataType dataType) {
        List<DataType> result = new ArrayList<DataType>();
        
        switch (dataType) {
        case Boolean:  
            result.add(DataType.Boolean);
            result.add(DataType.String);
            break;
        case Byte:     
            result.add(DataType.Byte);
            result.add(DataType.Double);
            result.add(DataType.Float);
            result.add(DataType.Int);
            result.add(DataType.Long);
            result.add(DataType.Short);
            result.add(DataType.String);
            break;
        case Bytes:    
            result.add(DataType.Bytes);
            result.add(DataType.String);
            result.add(DataType.Integer);
            break;
        case Character:
            result.add(DataType.Character);
            result.add(DataType.String);
            break;
        case Decimal:  
            result.add(DataType.Decimal);
            result.add(DataType.Double);
            result.add(DataType.Float);
            result.add(DataType.Int);
            result.add(DataType.Long);
            result.add(DataType.Integer);
            result.add(DataType.String);
            break;
        case Double:   
            result.add(DataType.Double);
            result.add(DataType.Byte);
            result.add(DataType.Float);
            result.add(DataType.Int);
            result.add(DataType.Long);
            result.add(DataType.Short);
            result.add(DataType.Decimal);
            result.add(DataType.Integer);
            result.add(DataType.String);
            break;
        case Float:    
            result.add(DataType.Float);
            result.add(DataType.Byte);
            result.add(DataType.Double);
            result.add(DataType.Int);
            result.add(DataType.Long);
            result.add(DataType.Short);
            result.add(DataType.Decimal);
            result.add(DataType.Integer);
            result.add(DataType.String);
            break;
        case Int:      
            result.add(DataType.Int);
            result.add(DataType.Byte);
            result.add(DataType.Double);
            result.add(DataType.Float);
            result.add(DataType.Long);
            result.add(DataType.Short);
            result.add(DataType.Decimal);
            result.add(DataType.Integer);
            result.add(DataType.String);
            break;
        case Integer:  
            result.add(DataType.Integer);
            result.add(DataType.Double);
            result.add(DataType.Float);
            result.add(DataType.Int);
            result.add(DataType.Long);
            result.add(DataType.Bytes);
            result.add(DataType.Decimal);
            result.add(DataType.String);
            break;
        case Long:     
            result.add(DataType.Long);
            result.add(DataType.Byte);
            result.add(DataType.Double);
            result.add(DataType.Float);
            result.add(DataType.Int);
            result.add(DataType.Short);
            result.add(DataType.Decimal);
            result.add(DataType.Integer);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case Short:    
            result.add(DataType.Short);
            result.add(DataType.Byte);
            result.add(DataType.Double);
            result.add(DataType.Float);
            result.add(DataType.Int);
            result.add(DataType.Long);
            result.add(DataType.String);
            break;
        case String:   
            result.add(DataType.String);  
            result.add(DataType.Boolean);  
            result.add(DataType.Byte);     
            result.add(DataType.Bytes);    
            result.add(DataType.Character);
            result.add(DataType.Date);     
            result.add(DataType.DateTime); 
            result.add(DataType.Day);      
            result.add(DataType.Decimal);  
            result.add(DataType.Double);   
            result.add(DataType.Duration); 
            result.add(DataType.Float);    
            result.add(DataType.Int);      
            result.add(DataType.Integer);  
            result.add(DataType.Long);     
            result.add(DataType.Month);    
            result.add(DataType.MonthDay); 
            result.add(DataType.Short);    
            result.add(DataType.Strings);  
            result.add(DataType.Time);     
            result.add(DataType.URI);      
            result.add(DataType.Year);     
            result.add(DataType.YearMonth);
            result.add(DataType.YearMonthDay);
            break;
        case Strings:  
            result.add(DataType.Strings);
            result.add(DataType.String);
            break;
        case Date:     
            result.add(DataType.Date);
            result.add(DataType.Long);
            result.add(DataType.String);
            break;
        case Duration: 
            result.add(DataType.Duration);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case DateTime: 
            result.add(DataType.DateTime);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case Day:      
            result.add(DataType.Day);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case Month:    
            result.add(DataType.Month);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case MonthDay: 
            result.add(DataType.MonthDay);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case Year:     
            result.add(DataType.Year);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case YearMonth:
            result.add(DataType.YearMonth);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case YearMonthDay:
            result.add(DataType.YearMonthDay);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case Time:     
            result.add(DataType.Time);
            result.add(DataType.Date);
            result.add(DataType.String);
            break;
        case URI:      
            result.add(DataType.String);
            break;
        case Object:   
        default:
        }
        
        return result;
    }
    
    private String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        char[] hexChars = { 
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
            'A', 'B', 'C', 'D', 'E', 'F' };
        int len = block.length;
        int high = 0;
        int low = 0;
        for (int i = 0; i < len; i++) {
            high = ((block[i] & 0xf0) >> 4);
            low = (block[i] & 0x0f);
            buf.append(hexChars[high]);
            buf.append(hexChars[low]);
        } 
        return buf.toString();
    }    
}
