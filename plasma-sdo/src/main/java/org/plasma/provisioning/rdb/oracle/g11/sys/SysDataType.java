package org.plasma.provisioning.rdb.oracle.g11.sys;

import org.plasma.sdo.PlasmaEnum;


/**
 * This generated <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaEnum.html">Enumeration</a> represents the domain model enumeration <b>SysDataType</b> which is part of namespace <b>http://org.plasma/sdo/oracle/11g/sys</b> as defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 * <p></p> * Generated <a href="http://plasma-sdo.org">SDO</a> enumerations embody not only logical-name literals 
 * but also physical or instance names, which are often shorter (possibly abbreviated) 
 * and applicable as a data-store space-saving device. 
 * Application programs should typically use the physical or instance name 
 * for an enumeration literal when setting a data object property which is <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/EnumerationConstraint.html">constrained</a> by an enumeration.
 */
public enum SysDataType implements PlasmaEnum
{

	
	/**
	 * The CHAR datatype stores fixed-length character strings. 
	 * When you create a table with a CHAR column, you must specify
	 * a string length (in bytes or characters) between 1 and 2000
	 * bytes for the CHAR column width. The default is 1 byte. 
	 * Oracle then guarantees that:
	 * 
	 * When you insert or update a row in the table, the value for 
	 * the CHAR column has the fixed length.
	 * 
	 * If you give a shorter value, then the value is blank-padded 
	 * to the fixed length.
	 * 
	 * If a value is too large, Oracle Database returns an error.
	 * 
	 * Oracle Database compares CHAR values using blank-padded comp
	 * arison semantics.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>CHAR</b>.
	 */
	CHAR("CHAR","The CHAR datatype stores fixed-length character strings. When you create a table with a CHAR column, you must specify a string length (in bytes or characters) between 1 and 2000 bytes for the CHAR column width. The default is 1 byte. Oracle then guarantees that:  When you insert or update a row in the table, the value for the CHAR column has the fixed length.  If you give a shorter value, then the value is blank-padded to the fixed length.  If a value is too large, Oracle Database returns an error.  Oracle Database compares CHAR values using blank-padded comparison semantics."),
	
	/**
	 * The VARCHAR2 datatype stores variable-length character 
	 * strings. When you create a table with a VARCHAR2 column, you
	 * specify a maximum string length (in bytes or characters) 
	 * between 1 and 4000 bytes for the VARCHAR2 column. For 
	 * each row, Oracle Database stores each value in the column as
	 * a variable-length field unless a value exceeds the column's
	 * maximum length, in which case Oracle Database returns an 
	 * error. Using VARCHAR2 and VARCHAR saves on space used by the
	 * table.
	 * 
	 * For example, assume you declare a column VARCHAR2 with a max
	 * imum size of 50 characters. In a single-byte character 
	 * set, if only 10 characters are given for the VARCHAR2 column
	 * value in a particular row, the column in the row's row 
	 * piece stores only the 10 characters (10 bytes), not 50.
	 * 
	 * Oracle Database compares VARCHAR2 values using nonpadded com
	 * parison semantics.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>VARCHAR2</b>.
	 */
	VARCHAR2("VARCHAR2","The VARCHAR2 datatype stores variable-length character strings. When you create a table with a VARCHAR2 column, you specify a maximum string length (in bytes or characters) between 1 and 4000 bytes for the VARCHAR2 column. For each row, Oracle Database stores each value in the column as a variable-length field unless a value exceeds the column's maximum length, in which case Oracle Database returns an error. Using VARCHAR2 and VARCHAR saves on space used by the table.  For example, assume you declare a column VARCHAR2 with a maximum size of 50 characters. In a single-byte character set, if only 10 characters are given for the VARCHAR2 column value in a particular row, the column in the row's row piece stores only the 10 characters (10 bytes), not 50.  Oracle Database compares VARCHAR2 values using nonpadded comparison semantics."),
	
	/**
	 * The VARCHAR datatype is synonymous with the VARCHAR2 
	 * datatype. To avoid possible changes in behavior, always 
	 * use the VARCHAR2 datatype to store variable-length character
	 * strings.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>VARCHAR</b>.
	 */
	VARCHAR("VARCHAR","The VARCHAR datatype is synonymous with the VARCHAR2 datatype. To avoid possible changes in behavior, always use the VARCHAR2 datatype to store variable-length character strings."),
	
	/**
	 * The NCHAR datatype stores fixed-length character strings 
	 * that correspond to the national character set. The 
	 * maximum length of an NCHAR column is 2000 bytes. It can hold
	 * up to 2000 characters. The actual data is subject to the 
	 * maximum byte limit of 2000. The two size constraints must be
	 * satisfied simultaneously at run time.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>NCHAR</b>.
	 */
	NCHAR("NCHAR","The NCHAR datatype stores fixed-length character strings that correspond to the national character set. The maximum length of an NCHAR column is 2000 bytes. It can hold up to 2000 characters. The actual data is subject to the maximum byte limit of 2000. The two size constraints must be satisfied simultaneously at run time."),
	
	/**
	 * The NVARCHAR2 datatype stores variable length character 
	 * strings. The maximum length of an NVARCHAR2 column is 
	 * 4000 bytes. It can hold up to 4000 characters. The actual 
	 * data is subject to the maximum byte limit of 4000. The 
	 * two size constraints must be satisfied simultaneously at run
	 * time.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>NVARCHAR2</b>.
	 */
	NVARCHAR2("NVARCHAR2","The NVARCHAR2 datatype stores variable length character strings. The maximum length of an NVARCHAR2 column is 4000 bytes. It can hold up to 4000 characters. The actual data is subject to the maximum byte limit of 4000. The two size constraints must be satisfied simultaneously at run time."),
	
	/**
	 * Columns defined as LONG can store variable-length 
	 * character data containing up to 2 gigabytes of 
	 * information. LONG data is text data that is to be 
	 * appropriately converted when moving among different systems.
	 * 
	 * LONG datatype columns are used in the data dictionary to sto
	 * re the text of view definitions. You can use LONG columns in
	 * SELECT lists, SET clauses of UPDATE statements, and 
	 * VALUES clauses of INSERT statements.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>LONG</b>.
	 */
	LONG("LONG","Columns defined as LONG can store variable-length character data containing up to 2 gigabytes of information. LONG data is text data that is to be appropriately converted when moving among different systems.  LONG datatype columns are used in the data dictionary to store the text of view definitions. You can use LONG columns in SELECT lists, SET clauses of UPDATE statements, and VALUES clauses of INSERT statements."),
	
	/**
	 * The NUMBER datatype stores fixed and floating-point numbers.
	 * Numbers of virtually any magnitude can be stored and are 
	 * guaranteed portable among different systems operating Oracle
	 * Database, up to 38 digits of precision.
	 * 
	 * The following numbers can be stored in a NUMBER column:
	 * 
	 * Positive numbers in the range 1 x 10-130 to 9.99...9 x 10125
	 *  with up to 38 significant digits
	 * 
	 * Negative numbers from -1 x 10-130 to 9.99...99 x 10125 with 
	 * up to 38 significant digits
	 * 
	 * Zero
	 * 
	 * Positive and negative infinity (generated only by importing 
	 * from an Oracle Database, Version 5)
	 * <p></p>
	 * Holds the logical and physical names for literal <b>NUMBER</b>.
	 */
	NUMBER("NUMBER","The NUMBER datatype stores fixed and floating-point numbers. Numbers of virtually any magnitude can be stored and are guaranteed portable among different systems operating Oracle Database, up to 38 digits of precision.  The following numbers can be stored in a NUMBER column:  Positive numbers in the range 1 x 10-130 to 9.99...9 x 10125 with up to 38 significant digits  Negative numbers from -1 x 10-130 to 9.99...99 x 10125 with up to 38 significant digits  Zero  Positive and negative infinity (generated only by importing from an Oracle Database, Version 5)"),
	
	/**
	 * BINARY_FLOAT is a 32-bit, single-precision floating-point 
	 * number datatype. Each BINARY_FLOAT value requires 5 
	 * bytes, including a length byte.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BINARY_FLOAT</b>.
	 */
	BINARY__FLOAT("BINARY_FLOAT","BINARY_FLOAT is a 32-bit, single-precision floating-point number datatype. Each BINARY_FLOAT value requires 5 bytes, including a length byte."),
	
	/**
	 * BINARY_DOUBLE is a 64-bit, double-precision floating-
	 * point number datatype. Each BINARY_DOUBLE value requires 
	 * 9 bytes, including a length byte.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BINARY_DOUBLE</b>.
	 */
	BINARY__DOUBLE("BINARY_DOUBLE","BINARY_DOUBLE is a 64-bit, double-precision floating-point number datatype. Each BINARY_DOUBLE value requires 9 bytes, including a length byte."),
	
	/**
	 * The DATE datatype stores point-in-time values (dates and 
	 * times) in a table. The DATE datatype stores the year 
	 * (including the century), the month, the day, the hours, 
	 * the minutes, and the seconds (after midnight).
	 * 
	 * Oracle Database can store dates in the Julian era, ranging f
	 * rom January 1, 4712 BCE through December 31, 9999 CE (Common
	 * Era, or 'AD'). Unless BCE ('BC' in the format mask) is 
	 * specifically used, CE date entries are the default.
	 * 
	 * Oracle Database uses its own internal format to store dates.
	 *  Date data is stored in fixed-length fields of seven 
	 * bytes each, corresponding to century, year, month, day, 
	 * hour, minute, and second.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>DATE</b>.
	 */
	DATE("DATE","The DATE datatype stores point-in-time values (dates and times) in a table. The DATE datatype stores the year (including the century), the month, the day, the hours, the minutes, and the seconds (after midnight).  Oracle Database can store dates in the Julian era, ranging from January 1, 4712 BCE through December 31, 9999 CE (Common Era, or 'AD'). Unless BCE ('BC' in the format mask) is specifically used, CE date entries are the default.  Oracle Database uses its own internal format to store dates. Date data is stored in fixed-length fields of seven bytes each, corresponding to century, year, month, day, hour, minute, and second."),
	
	/**
	 * Holds the logical and physical names for literal <b>TIMESTAMP</b>.
	 */
	TIMESTAMP("TIMESTAMP",""),
	
	/**
	 * The BLOB datatype stores unstructured binary data in the 
	 * database. BLOBs can store up to 128 terabytes of binary 
	 * data.
	 * 
	 * BLOBs participate fully in transactions. Changes made to a B
	 * LOB value by the DBMS_LOB package, PL/SQL, or the OCI can be
	 * committed or rolled back. However, BLOB locators cannot 
	 * span transactions or sessions.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BLOB</b>.
	 */
	BLOB("BLOB","The BLOB datatype stores unstructured binary data in the database. BLOBs can store up to 128 terabytes of binary data.  BLOBs participate fully in transactions. Changes made to a BLOB value by the DBMS_LOB package, PL/SQL, or the OCI can be committed or rolled back. However, BLOB locators cannot span transactions or sessions."),
	
	/**
	 * The CLOB and NCLOB datatypes store up to 128 terabytes of 
	 * character data in the database. CLOBs store database 
	 * character set data, and NCLOBs store Unicode national 
	 * character set data. Storing varying-width LOB data in a 
	 * fixed-width Unicode character set internally enables 
	 * Oracle Database to provide efficient character-based 
	 * random access on CLOBs and NCLOBs.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>CLOB</b>.
	 */
	CLOB("CLOB","The CLOB and NCLOB datatypes store up to 128 terabytes of character data in the database. CLOBs store database character set data, and NCLOBs store Unicode national character set data. Storing varying-width LOB data in a fixed-width Unicode character set internally enables Oracle Database to provide efficient character-based random access on CLOBs and NCLOBs."),
	
	/**
	 * The CLOB and NCLOB datatypes store up to 128 terabytes of 
	 * character data in the database. CLOBs store database 
	 * character set data, and NCLOBs store Unicode national 
	 * character set data. Storing varying-width LOB data in a 
	 * fixed-width Unicode character set internally enables 
	 * Oracle Database to provide efficient character-based 
	 * random access on CLOBs and NCLOBs.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>NCLOB</b>.
	 */
	NCLOB("NCLOB","The CLOB and NCLOB datatypes store up to 128 terabytes of character data in the database. CLOBs store database character set data, and NCLOBs store Unicode national character set data. Storing varying-width LOB data in a fixed-width Unicode character set internally enables Oracle Database to provide efficient character-based random access on CLOBs and NCLOBs."),
	
	/**
	 * The BFILE datatype stores unstructured binary data in 
	 * operating-system files outside the database. A BFILE 
	 * column or attribute stores a file locator that points to 
	 * an external file containing the data. The amount of BFILE 
	 * data that can be stored is limited by the operating system.
	 * 
	 * BFILEs are read only; you cannot modify them. They support o
	 * nly random (not sequential) reads, and they do not 
	 * participate in transactions. The underlying operating system
	 * must maintain the file integrity, security, and 
	 * durability for BFILEs. The database administrator must 
	 * ensure that the file exists and that Oracle Database 
	 * processes have operating-system read permissions on the 
	 * file.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>BFILE</b>.
	 */
	BFILE("BFILE","The BFILE datatype stores unstructured binary data in operating-system files outside the database. A BFILE column or attribute stores a file locator that points to an external file containing the data. The amount of BFILE data that can be stored is limited by the operating system.  BFILEs are read only; you cannot modify them. They support only random (not sequential) reads, and they do not participate in transactions. The underlying operating system must maintain the file integrity, security, and durability for BFILEs. The database administrator must ensure that the file exists and that Oracle Database processes have operating-system read permissions on the file."),
	
	/**
	 * RAW is a variable-length datatype like the VARCHAR2 
	 * character datatype, except Oracle Net Services (which 
	 * connects user sessions to the instance) and the Import 
	 * and Export utilities do not perform character conversion 
	 * when transmitting RAW or LONG RAW data. In contrast, 
	 * Oracle Net Services and Import/Export automatically 
	 * convert CHAR, VARCHAR2, and LONG data between the 
	 * database character set and the user session character 
	 * set, if the two character sets are different.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>RAW</b>.
	 */
	RAW("RAW","RAW is a variable-length datatype like the VARCHAR2 character datatype, except Oracle Net Services (which connects user sessions to the instance) and the Import and Export utilities do not perform character conversion when transmitting RAW or LONG RAW data. In contrast, Oracle Net Services and Import/Export automatically convert CHAR, VARCHAR2, and LONG data between the database character set and the user session character set, if the two character sets are different."),
	
	/**
	 * Oracle Database uses a ROWID datatype to store the 
	 * address (rowid) of every row in the database.
	 * 
	 * Physical rowids store the addresses of rows in ordinary tabl
	 * es (excluding index-organized tables), clustered tables, 
	 * table partitions and subpartitions, indexes, and index 
	 * partitions and subpartitions.
	 * 
	 * Logical rowids store the addresses of rows in index-organize
	 * d tables.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>ROWID</b>.
	 */
	ROWID("ROWID","Oracle Database uses a ROWID datatype to store the address (rowid) of every row in the database.  Physical rowids store the addresses of rows in ordinary tables (excluding index-organized tables), clustered tables, table partitions and subpartitions, indexes, and index partitions and subpartitions.  Logical rowids store the addresses of rows in index-organized tables."),
	
	/**
	 * Oracle Database uses a ROWID datatype to store the 
	 * address (rowid) of every row in the database.
	 * 
	 * Physical rowids store the addresses of rows in ordinary tabl
	 * es (excluding index-organized tables), clustered tables, 
	 * table partitions and subpartitions, indexes, and index 
	 * partitions and subpartitions.
	 * 
	 * Logical rowids store the addresses of rows in index-organize
	 * d tables.
	 * <p></p>
	 * Holds the logical and physical names for literal <b>UROWID</b>.
	 */
	UROWID("UROWID","Oracle Database uses a ROWID datatype to store the address (rowid) of every row in the database.  Physical rowids store the addresses of rows in ordinary tables (excluding index-organized tables), clustered tables, table partitions and subpartitions, indexes, and index partitions and subpartitions.  Logical rowids store the addresses of rows in index-organized tables.");

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