package org.plasma.provisioning.rdb.mysql.v5_5;
import org.plasma.sdo.PlasmaEnum;


/**
 * This generated <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaEnum.html">Enumeration</a> represents the domain model enumeration <b>SysDataType</b> which is part of namespace <b>http://org.plasma/sdo/mysql/5_5</b> as defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 * <p></p> * Generated <a href="http://plasma-sdo.org">SDO</a> enumerations embody not only logical-name literals 
 * but also physical or instance names, which are often shorter (possibly abbreviated) 
 * and applicable as a data-store space-saving device. 
 * Application programs should typically use the physical or instance name 
 * for an enumeration literal when setting a data object property which is <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/EnumerationConstraint.html">constrained</a> by an enumeration.
 */
public enum SysDataType implements PlasmaEnum
{

	
	/**
	 * A bit-field type. M indicates the number of bits per 
	 * value, from 1 to 64. The default is 1 if M is omitted.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BIT</b>.
	 */
	BIT("BIT","A bit-field type. M indicates the number of bits per value, from 1 to 64. The default is 1 if M is omitted."),
	
	/**
	 * A very small integer. The signed range is -128 to 127. 
	 * The unsigned range is 0 to 255.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>TINYINT</b>.
	 */
	TINYINT("TINYINT","A very small integer. The signed range is -128 to 127. The unsigned range is 0 to 255."),
	
	/**
	 * These types are synonyms for TINYINT(1). A value of zero 
	 * is considered false.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BOOL</b>.
	 */
	BOOL("BOOL","These types are synonyms for TINYINT(1). A value of zero is considered false. "),
	
	/**
	 * These types are synonyms for TINYINT(1). A value of zero 
	 * is considered false.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BOOLEAN</b>.
	 */
	BOOLEAN("BOOLEAN","These types are synonyms for TINYINT(1). A value of zero is considered false. "),
	
	/**
	 * A small integer. The signed range is -32768 to 32767. The 
	 * unsigned range is 0 to 65535.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>SMALLINT</b>.
	 */
	SMALLINT("SMALLINT","A small integer. The signed range is -32768 to 32767. The unsigned range is 0 to 65535."),
	
	/**
	 * A medium-sized integer. The signed range is -8388608 to 
	 * 8388607. The unsigned range is 0 to 16777215.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>MEDIUMINT</b>.
	 */
	MEDIUMINT("MEDIUMINT","A medium-sized integer. The signed range is -8388608 to 8388607. The unsigned range is 0 to 16777215."),
	
	/**
	 * A normal-size integer. The signed range is -2147483648 to 
	 * 2147483647. The unsigned range is 0 to 4294967295.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>INT</b>.
	 */
	INT("INT","A normal-size integer. The signed range is -2147483648 to 2147483647. The unsigned range is 0 to 4294967295."),
	
	/**
	 * This type is a synonym for INT.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>INTEGER</b>.
	 */
	INTEGER("INTEGER","This type is a synonym for INT."),
	
	/**
	 * A large integer. The signed range is -9223372036854775808 to
	 * 9223372036854775807. The unsigned range is 0 to 
	 * 18446744073709551615.
	 * 
	 * SERIAL is an alias for BIGINT UNSIGNED NOT NULL AUTO_INCREME
	 * NT UNIQUE.
	 * 
	 * Some things you should be aware of with respect to BIGINT co
	 * lumns:
	 * 
	 *  All arithmetic is done using signed BIGINT or DOUBLE values
	 * , so you should not use unsigned big integers larger than 
	 * 9223372036854775807 (63 bits) except with bit functions! 
	 * If you do that, some of the last digits in the result may be
	 * wrong because of rounding errors when converting a 
	 * BIGINT value to a DOUBLE.
	 * 
	 * MySQL can handle BIGINT in the following cases:
	 * 
	 * When using integers to store large unsigned values in a BIGI
	 * NT column.
	 * 
	 * In MIN(col_name) or MAX(col_name), where col_name refers to 
	 * a BIGINT column.
	 * 
	 * When using operators (+, -, *, and so on) where both operand
	 * s are integers.
	 * 
	 * You can always store an exact integer value in a BIGINT colu
	 * mn by storing it using a string. In this case, MySQL 
	 * performs a string-to-number conversion that involves no 
	 * intermediate double-precision representation.
	 * 
	 * The -, +, and * operators use BIGINT arithmetic when both op
	 * erands are integer values. This means that if you 
	 * multiply two big integers (or results from functions that 
	 * return integers), you may get unexpected results when the 
	 * result is larger than 9223372036854775807.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BIGINT</b>.
	 */
	BIGINT("BIGINT","A large integer. The signed range is -9223372036854775808 to 9223372036854775807. The unsigned range is 0 to 18446744073709551615.  SERIAL is an alias for BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE.  Some things you should be aware of with respect to BIGINT columns:   All arithmetic is done using signed BIGINT or DOUBLE values, so you should not use unsigned big integers larger than 9223372036854775807 (63 bits) except with bit functions! If you do that, some of the last digits in the result may be wrong because of rounding errors when converting a BIGINT value to a DOUBLE.  MySQL can handle BIGINT in the following cases:  When using integers to store large unsigned values in a BIGINT column.  In MIN(col_name) or MAX(col_name), where col_name refers to a BIGINT column.  When using operators (+, -, *, and so on) where both operands are integers.  You can always store an exact integer value in a BIGINT column by storing it using a string. In this case, MySQL performs a string-to-number conversion that involves no intermediate double-precision representation.  The -, +, and * operators use BIGINT arithmetic when both operands are integer values. This means that if you multiply two big integers (or results from functions that return integers), you may get unexpected results when the result is larger than 9223372036854775807."),
	
	/**
	 * A packed ?exact? fixed-point number. M is the total 
	 * number of digits (the precision) and D is the number of 
	 * digits after the decimal point (the scale). The decimal 
	 * point and (for negative numbers) the ?-? sign are not 
	 * counted in M. If D is 0, values have no decimal point or 
	 * fractional part. The maximum number of digits (M) for 
	 * DECIMAL is 65. The maximum number of supported decimals 
	 * (D) is 30. If D is omitted, the default is 0. If M is 
	 * omitted, the default is 10.  UNSIGNED, if specified, 
	 * disallows negative values.  All basic calculations (+, -, *,
	 * /) with DECIMAL columns are done with a precision of 65 
	 * digits.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>DECIMAL</b>.
	 */
	DECIMAL("DECIMAL","A packed ?exact? fixed-point number. M is the total number of digits (the precision) and D is the number of digits after the decimal point (the scale). The decimal point and (for negative numbers) the ?-? sign are not counted in M. If D is 0, values have no decimal point or fractional part. The maximum number of digits (M) for DECIMAL is 65. The maximum number of supported decimals (D) is 30. If D is omitted, the default is 0. If M is omitted, the default is 10.  UNSIGNED, if specified, disallows negative values.  All basic calculations (+, -, *, /) with DECIMAL columns are done with a precision of 65 digits. "),
	
	/**
	 * These types are synonyms for DECIMAL. The FIXED synonym 
	 * is available for compatibility with other database systems.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>DEC</b>.
	 */
	DEC("DEC","These types are synonyms for DECIMAL. The FIXED synonym is available for compatibility with other database systems."),
	
	/**
	 * These types are synonyms for DECIMAL. The FIXED synonym 
	 * is available for compatibility with other database systems.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>NUMERIC</b>.
	 */
	NUMERIC("NUMERIC","These types are synonyms for DECIMAL. The FIXED synonym is available for compatibility with other database systems."),
	
	/**
	 * These types are synonyms for DECIMAL. The FIXED synonym 
	 * is available for compatibility with other database systems.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>FIXED</b>.
	 */
	FIXED("FIXED","These types are synonyms for DECIMAL. The FIXED synonym is available for compatibility with other database systems."),
	
	/**
	 * A small (single-precision) floating-point number. 
	 * Permissible values are -3.402823466E+38 to -1.175494351E-38,
	 * 0, and 1.175494351E-38 to 3.402823466E+38. These are the 
	 * theoretical limits, based on the IEEE standard. The 
	 * actual range might be slightly smaller depending on your 
	 * hardware or operating system.
	 * 
	 * M is the total number of digits and D is the number of digit
	 * s following the decimal point. If M and D are omitted, 
	 * values are stored to the limits permitted by the hardware. A
	 * single-precision floating-point number is accurate to 
	 * approximately 7 decimal places.
	 * 
	 * UNSIGNED, if specified, disallows negative values.
	 * 
	 * Using FLOAT might give you some unexpected problems because 
	 * all calculations in MySQL are done with double precision
	 * <p></p>
	 * Holds the logical and physical names for literal <b>FLOAT</b>.
	 */
	FLOAT("FLOAT","A small (single-precision) floating-point number. Permissible values are -3.402823466E+38 to -1.175494351E-38, 0, and 1.175494351E-38 to 3.402823466E+38. These are the theoretical limits, based on the IEEE standard. The actual range might be slightly smaller depending on your hardware or operating system.  M is the total number of digits and D is the number of digits following the decimal point. If M and D are omitted, values are stored to the limits permitted by the hardware. A single-precision floating-point number is accurate to approximately 7 decimal places.  UNSIGNED, if specified, disallows negative values.  Using FLOAT might give you some unexpected problems because all calculations in MySQL are done with double precision"),
	
	/**
	 * A normal-size (double-precision) floating-point number. 
	 * Permissible values are -1.7976931348623157E+308 to 
	 * -2.2250738585072014E-308, 0, and 2.2250738585072014E-308 
	 * to 1.7976931348623157E+308. These are the theoretical 
	 * limits, based on the IEEE standard. The actual range 
	 * might be slightly smaller depending on your hardware or 
	 * operating system.
	 * 
	 * M is the total number of digits and D is the number of digit
	 * s following the decimal point. If M and D are omitted, 
	 * values are stored to the limits permitted by the hardware. A
	 * double-precision floating-point number is accurate to 
	 * approximately 15 decimal places.
	 * 
	 * UNSIGNED, if specified, disallows negative values.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>DOUBLE</b>.
	 */
	DOUBLE("DOUBLE","A normal-size (double-precision) floating-point number. Permissible values are -1.7976931348623157E+308 to -2.2250738585072014E-308, 0, and 2.2250738585072014E-308 to 1.7976931348623157E+308. These are the theoretical limits, based on the IEEE standard. The actual range might be slightly smaller depending on your hardware or operating system.  M is the total number of digits and D is the number of digits following the decimal point. If M and D are omitted, values are stored to the limits permitted by the hardware. A double-precision floating-point number is accurate to approximately 15 decimal places.  UNSIGNED, if specified, disallows negative values."),
	
	/**
	 * These types are synonyms for DOUBLE. Exception: If the 
	 * REAL_AS_FLOAT SQL mode is enabled, REAL is a synonym for 
	 * FLOAT rather than DOUBLE.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>DOUBLE_PRECISION</b>.
	 */
	DOUBLE__PRECISION("DOUBLE_PRECISION","These types are synonyms for DOUBLE. Exception: If the REAL_AS_FLOAT SQL mode is enabled, REAL is a synonym for FLOAT rather than DOUBLE."),
	
	/**
	 * A date. The supported range is '1000-01-01' to '9999-12-31'.
	 * MySQL displays DATE values in 'YYYY-MM-DD' format, but 
	 * permits assignment of values to DATE columns using either 
	 * strings or numbers.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>DATE</b>.
	 */
	DATE("DATE","A date. The supported range is '1000-01-01' to '9999-12-31'. MySQL displays DATE values in 'YYYY-MM-DD' format, but permits assignment of values to DATE columns using either strings or numbers."),
	
	/**
	 * A date and time combination. The supported range is '1000-
	 * 01-01 00:00:00' to '9999-12-31 23:59:59'. MySQL displays 
	 * DATETIME values in 'YYYY-MM-DD HH:MM:SS' format, but permits
	 * assignment of values to DATETIME columns using either 
	 * strings or numbers.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>DATETIME</b>.
	 */
	DATETIME("DATETIME","A date and time combination. The supported range is '1000-01-01 00:00:00' to '9999-12-31 23:59:59'. MySQL displays DATETIME values in 'YYYY-MM-DD HH:MM:SS' format, but permits assignment of values to DATETIME columns using either strings or numbers."),
	
	/**
	 * A timestamp. The range is '1970-01-01 00:00:01' UTC to 
	 * '2038-01-19 03:14:07' UTC. TIMESTAMP values are stored as 
	 * the number of seconds since the epoch ('1970-01-01 00:00:00'
	 * UTC). A TIMESTAMP cannot represent the value '1970-01-01 
	 * 00:00:00' because that is equivalent to 0 seconds from 
	 * the epoch and the value 0 is reserved for representing 
	 * '0000-00-00 00:00:00', the zero TIMESTAMP value.
	 * 
	 * Unless specified otherwise, the first TIMESTAMP column in a 
	 * table is defined to be automatically set to the date and 
	 * time of the most recent modification if not explicitly 
	 * assigned a value. This makes TIMESTAMP useful for 
	 * recording the timestamp of an INSERT or UPDATE operation. 
	 * You can also set any TIMESTAMP column to the current date 
	 * and time by assigning it a NULL value, unless it has been 
	 * defined with the NULL attribute to permit NULL values.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>TIMESTAMP</b>.
	 */
	TIMESTAMP("TIMESTAMP","A timestamp. The range is '1970-01-01 00:00:01' UTC to '2038-01-19 03:14:07' UTC. TIMESTAMP values are stored as the number of seconds since the epoch ('1970-01-01 00:00:00' UTC). A TIMESTAMP cannot represent the value '1970-01-01 00:00:00' because that is equivalent to 0 seconds from the epoch and the value 0 is reserved for representing '0000-00-00 00:00:00', the zero TIMESTAMP value.  Unless specified otherwise, the first TIMESTAMP column in a table is defined to be automatically set to the date and time of the most recent modification if not explicitly assigned a value. This makes TIMESTAMP useful for recording the timestamp of an INSERT or UPDATE operation. You can also set any TIMESTAMP column to the current date and time by assigning it a NULL value, unless it has been defined with the NULL attribute to permit NULL values. "),
	
	/**
	 * A time. The range is '-838:59:59' to '838:59:59'. MySQL 
	 * displays TIME values in 'HH:MM:SS' format, but permits 
	 * assignment of values to TIME columns using either strings or
	 * numbers.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>TIME</b>.
	 */
	TIME("TIME","A time. The range is '-838:59:59' to '838:59:59'. MySQL displays TIME values in 'HH:MM:SS' format, but permits assignment of values to TIME columns using either strings or numbers."),
	
	/**
	 * A year in two-digit or four-digit format. The default is 
	 * four-digit format. YEAR(2) or YEAR(4) differ in display 
	 * format, but have the same range of values. In four-digit 
	 * format, values display as 1901 to 2155, and 0000. In two-
	 * digit format, values display as 70 to 69, representing years
	 * from 1970 to 2069. MySQL displays YEAR values in YYYY or 
	 * YYformat, but permits assignment of values to YEAR 
	 * columns using either strings or numbers.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>YEAR</b>.
	 */
	YEAR(null,"A year in two-digit or four-digit format. The default is four-digit format. YEAR(2) or YEAR(4) differ in display format, but have the same range of values. In four-digit format, values display as 1901 to 2155, and 0000. In two-digit format, values display as 70 to 69, representing years from 1970 to 2069. MySQL displays YEAR values in YYYY or YYformat, but permits assignment of values to YEAR columns using either strings or numbers."),
	
	/**
	 * The BINARY type is similar to the CHAR type, but stores 
	 * binary byte strings rather than nonbinary character strings.
	 * M represents the column length in bytes.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BINARY</b>.
	 */
	BINARY("BINARY","The BINARY type is similar to the CHAR type, but stores binary byte strings rather than nonbinary character strings. M represents the column length in bytes."),
	
	/**
	 * Holds the logical and physical names for literal <b>VARBINARY</b>.
	 */
	VARBINARY("VARBINARY",""),
	
	/**
	 * A BLOB column with a maximum length of 255 (28 � 1) 
	 * bytes. Each TINYBLOB value is stored using a 1-byte 
	 * length prefix that indicates the number of bytes in the 
	 * value.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>TINYBLOB</b>.
	 */
	TINYBLOB("TINYBLOB","A BLOB column with a maximum length of 255 (28 � 1) bytes. Each TINYBLOB value is stored using a 1-byte length prefix that indicates the number of bytes in the value."),
	
	/**
	 * A TEXT column with a maximum length of 255 (28 � 1) 
	 * characters. The effective maximum length is less if the 
	 * value contains multi-byte characters. Each TINYTEXT value is
	 * stored using a 1-byte length prefix that indicates the 
	 * number of bytes in the value.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>TINYTEXT</b>.
	 */
	TINYTEXT("TINYTEXT","A TEXT column with a maximum length of 255 (28 � 1) characters. The effective maximum length is less if the value contains multi-byte characters. Each TINYTEXT value is stored using a 1-byte length prefix that indicates the number of bytes in the value."),
	
	/**
	 * A BLOB column with a maximum length of 65,535 (216 � 1) 
	 * bytes. Each BLOB value is stored using a 2-byte length 
	 * prefix that indicates the number of bytes in the value.
	 * 
	 * An optional length M can be given for this type. If this is 
	 * done, MySQL creates the column as the smallest BLOB type 
	 * large enough to hold values M bytes long.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BLOB</b>.
	 */
	BLOB("BLOB","A BLOB column with a maximum length of 65,535 (216 � 1) bytes. Each BLOB value is stored using a 2-byte length prefix that indicates the number of bytes in the value.  An optional length M can be given for this type. If this is done, MySQL creates the column as the smallest BLOB type large enough to hold values M bytes long."),
	
	/**
	 * A TEXT column with a maximum length of 65,535 (216 � 1) 
	 * characters. The effective maximum length is less if the 
	 * value contains multi-byte characters. Each TEXT value is 
	 * stored using a 2-byte length prefix that indicates the 
	 * number of bytes in the value.
	 * 
	 * An optional length M can be given for this type. If this is 
	 * done, MySQL creates the column as the smallest TEXT type 
	 * large enough to hold values M characters long.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>TEXT</b>.
	 */
	TEXT("TEXT","A TEXT column with a maximum length of 65,535 (216 � 1) characters. The effective maximum length is less if the value contains multi-byte characters. Each TEXT value is stored using a 2-byte length prefix that indicates the number of bytes in the value.  An optional length M can be given for this type. If this is done, MySQL creates the column as the smallest TEXT type large enough to hold values M characters long."),
	
	/**
	 * A BLOB column with a maximum length of 16,777,215 (224 � 
	 * 1) bytes. Each MEDIUMBLOB value is stored using a 3-byte 
	 * length prefix that indicates the number of bytes in the 
	 * value.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>MEDIUMBLOB</b>.
	 */
	MEDIUMBLOB("MEDIUMBLOB","A BLOB column with a maximum length of 16,777,215 (224 � 1) bytes. Each MEDIUMBLOB value is stored using a 3-byte length prefix that indicates the number of bytes in the value."),
	
	/**
	 * A TEXT column with a maximum length of 16,777,215 (224 � 
	 * 1) characters. The effective maximum length is less if 
	 * the value contains multi-byte characters. Each MEDIUMTEXT 
	 * value is stored using a 3-byte length prefix that 
	 * indicates the number of bytes in the value.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>MEDIUMTEXT</b>.
	 */
	MEDIUMTEXT("MEDIUMTEXT","A TEXT column with a maximum length of 16,777,215 (224 � 1) characters. The effective maximum length is less if the value contains multi-byte characters. Each MEDIUMTEXT value is stored using a 3-byte length prefix that indicates the number of bytes in the value."),
	
	/**
	 * A BLOB column with a maximum length of 4,294,967,295 or 
	 * 4GB (232 � 1) bytes. The effective maximum length of 
	 * LONGBLOB columns depends on the configured maximum packet 
	 * size in the client/server protocol and available memory. 
	 * Each LONGBLOB value is stored using a 4-byte length 
	 * prefix that indicates the number of bytes in the value.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>LONGBLOB</b>.
	 */
	LONGBLOB("LONGBLOB","A BLOB column with a maximum length of 4,294,967,295 or 4GB (232 � 1) bytes. The effective maximum length of LONGBLOB columns depends on the configured maximum packet size in the client/server protocol and available memory. Each LONGBLOB value is stored using a 4-byte length prefix that indicates the number of bytes in the value."),
	
	/**
	 * A TEXT column with a maximum length of 4,294,967,295 or 
	 * 4GB (232 � 1) characters. The effective maximum length is 
	 * less if the value contains multi-byte characters. The 
	 * effective maximum length of LONGTEXT columns also depends on
	 * the configured maximum packet size in the client/server 
	 * protocol and available memory. Each LONGTEXT value is stored
	 * using a 4-byte length prefix that indicates the number 
	 * of bytes in the value.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>LONGTEXT</b>.
	 */
	LONGTEXT("LONGTEXT","A TEXT column with a maximum length of 4,294,967,295 or 4GB (232 � 1) characters. The effective maximum length is less if the value contains multi-byte characters. The effective maximum length of LONGTEXT columns also depends on the configured maximum packet size in the client/server protocol and available memory. Each LONGTEXT value is stored using a 4-byte length prefix that indicates the number of bytes in the value."),
	
	/**
	 * An enumeration. A string object that can have only one 
	 * value, chosen from the list of values 'value1', 'value2', 
	 * ..., NULL or the special '' error value. ENUM values are 
	 * represented internally as integers.
	 * 
	 * An ENUM column can have a maximum of 65,535 distinct element
	 * s. (The practical limit is less than 3000.) A table can have
	 * no more than 255 unique element list definitions among 
	 * its ENUM and SET columns considered as a group. For more 
	 * information on these limits,
	 * <p></p>
	 * Holds the logical and physical names for literal <b>ENUM</b>.
	 */
	ENUM("ENUM","An enumeration. A string object that can have only one value, chosen from the list of values 'value1', 'value2', ..., NULL or the special '' error value. ENUM values are represented internally as integers.  An ENUM column can have a maximum of 65,535 distinct elements. (The practical limit is less than 3000.) A table can have no more than 255 unique element list definitions among its ENUM and SET columns considered as a group. For more information on these limits,"),
	
	/**
	 * A set. A string object that can have zero or more values, 
	 * each of which must be chosen from the list of values 
	 * 'value1', 'value2', ... SET values are represented 
	 * internally as integers.
	 * 
	 * A SET column can have a maximum of 64 distinct members. A ta
	 * ble can have no more than 255 unique element list 
	 * definitions among its ENUM and SET columns considered as 
	 * a group.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>SET</b>.
	 */
	SET("SET","A set. A string object that can have zero or more values, each of which must be chosen from the list of values 'value1', 'value2', ... SET values are represented internally as integers.  A SET column can have a maximum of 64 distinct members. A table can have no more than 255 unique element list definitions among its ENUM and SET columns considered as a group."),
	
	/**
	 * The CHAR and VARCHAR types are similar, but differ in the 
	 * way they are stored and retrieved. They also differ in 
	 * maximum length and in whether trailing spaces are retained.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>CHAR</b>.
	 */
	CHAR("CHAR","The CHAR and VARCHAR types are similar, but differ in the way they are stored and retrieved. They also differ in maximum length and in whether trailing spaces are retained."),
	
	/**
	 * The CHAR and VARCHAR types are similar, but differ in the 
	 * way they are stored and retrieved. They also differ in 
	 * maximum length and in whether trailing spaces are retained.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>VARCHAR</b>.
	 */
	VARCHAR("VARCHAR","The CHAR and VARCHAR types are similar, but differ in the way they are stored and retrieved. They also differ in maximum length and in whether trailing spaces are retained.");

	private String instanceName;
	private String description;

	private SysDataType(String instanceName, String description) {
	    this.instanceName = instanceName;
	    this.description = description;
	}


	/**
	* Returns the logical name associated with this enumeration literal.
	*/
	public String getName()
	{
	    return this.name();
	}

	/**
	* Returns the physical or instance name associated with this enumeration literal.
	*/
	public String getInstanceName() {
	    return this.instanceName;
	}

	/**
	* Returns the descriptive text associated with this enumeration literal.
	*/
	public String getDescription() {
	    return this.description;
	}

	/**
	* Returns the enum values for this class as an array of implemented interfaces
	* @see PlasmaEnum
	*/
	public static PlasmaEnum[] enumValues()
	{
	    return values();
	}

	/**
	* Returns the enumeration value matching the given name.
	*/
	public static SysDataType fromName(String name) {
		for (PlasmaEnum enm : enumValues()) {
			if (enm.getName().equals(name))
				return (SysDataType)enm;
		}
			throw new IllegalArgumentException("no enumeration value found for name '" + name + "'");
	}

	/**
	* Returns the enumeration value matching the given physical or instance name.
	*/
	public static SysDataType fromInstanceName(String instanceName) {
		for (PlasmaEnum enm : enumValues()) {
			if (enm.getInstanceName().equals(instanceName))
				return (SysDataType)enm;
		}
			throw new IllegalArgumentException("no enumeration value found for instance name '" + instanceName + "'");
	}



































}