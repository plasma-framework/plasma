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
package org.plasma.text.ddl;

import org.plasma.sdo.DataType;

/**
 * 
 */
public class CassandraFactory extends DefaultDDLFactory 
    implements DDLFactory 
{	
    /**
     * Return the CQL datatype. 
     * see http://www.datastax.com/documentation/cql/3.0/cql/cql_reference/cql_data_types_c.html
     * see http://www.datastax.com/documentation/developer/java-driver/1.0/java-driver/reference/javaClass2Cql3Datatypes_r.html
     */
	@Override
	public String getType(Table table, Column column) {
		DataType sdoType = DataType.valueOf(column.getType());
		switch (sdoType) {
		case String:     
		case URI:        
            return "TEXT"; // or VARCHAR which is currently (CQL 3) just an alias and takes no length
		case Strings:    
            return "SET"; // or LIST
		case Boolean: 
            return "BOOLEAN"; 
		case Byte:        
            return "INT"; // no variable numeric CQL type
		case Character:  
			return "TEXT";
		case Decimal:   
			return "DECIMAL";
		case Double:    
			return "DOUBLE";
		case Float:      
			return "FLOAT";
		case Short:  
			return "INT";
		case Integer:   
			return "VARINT"; 
		case Int:        
			return "INT";
		case Long:  // FIXME: add sequence indicator to DDL column - see issue #43 - if sequence return counter datatype
		    return "BIGINT";
		case Bytes:      
			return "BLOB";
		case Date:     
		case DateTime:   
		case Time:       
			return "TIMESTAMP";
		case Day:        
		case Duration:   
		case Month:      
		case MonthDay:   
		case Year:       
		case YearMonth:  
		case YearMonthDay:
			return "TEXT";
 		case Object: // could result in MAP type if more profile metadata available    
		default:
			throw new DDLException("unsupported SDO type, "
					+ sdoType.toString());
		}		
    }
	
	@Override
	public String createTable(Schema schema, Table table) {
		StringBuilder buf = new StringBuilder();

		buf.append("CREATE TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" ( ");
		int i = 0;
		for (Column column : table.getColumn()) {
			if (i > 0)
				buf.append(", ");
			buf.append(column.getName());
			buf.append(" ");
		    buf.append(getType(table, column));
			i++;
		}
		if (table.getPk() != null) {
			buf.append(", PRIMARY KEY (");
			i = 0;
			for (On on : table.getPk().getOn()) {
				if (i > 0)
					buf.append(", ");
				buf.append(on.getColumn());
				i++;
			}
			buf.append(" )");
		}
		buf.append(" );\n");
		return buf.toString();
	}
	
	public String createView(Schema schema, Table table, Behavior create) {
		StringBuilder buf = new StringBuilder();
		buf.append("/* view create statement omitted for behaviored entity ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" */");
		return buf.toString();
	}
	 
	public String dropView(Schema schema, Table table, Behavior drop) {
		StringBuilder buf = new StringBuilder();
		buf.append("/* view drop statement omitted for behaviored entity ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" */");
		return buf.toString();
	}	
		
	@Override
	public String createForeignKeyConstraint(Schema schema, Table table,
			Fk fk, Table toTable) {
		return "";
	}

	@Override
	public String dropForeignKeyConstraint(Schema schema, Table table, Fk fk,
			Table toTable) {
		return "";
	}
	
	public String enableForeignKeyConstraint(Schema schema, Table table,
			Fk fk, Table toTable, boolean enable) {
		return ""; // not currently supported in CQL
	}
	
	@Override
	public String createUniqueConstraint(Schema schema, Table table,
			Unique unique) {
		return ""; // not currently supported in CQL
	}
	
	@Override
	public String dropUniqueConstraint(Schema schema, Table table,
			Unique unique) {
		return ""; // not currently supported in CQL
	}	
	
	@Override
	public String createSequence(Schema schema, Table table) 
	{
		return ""; // no sequence facility in CQL however can use counter datatype
	}

	@Override
	public String dropSequence(Schema schema, Table table) {		
		return ""; // no sequence facility in CQL however can use counter datatype
	}
	
	@Override
	public String createCheckConstraint(Schema schema, Table table, Check check) {		
		return ""; // not currently supported in CQL
	}

	@Override
	public String dropCheckConstraint(Schema schema, Table table, Check check) {
		return ""; // not currently supported in CQL
	}
	
	public String enableUniqueConstraint(Schema schema, Table table,
			Unique unique, boolean enable) {
		return ""; // not currently supported in CQL
	}

	public String createIndex(Schema schema, Table table, Index index) {
		Column indexColumn = getColumn(table, index.getColumn());
		if (!this.isPk(table, indexColumn)) {
			StringBuilder buf = new StringBuilder();
			buf.append("CREATE INDEX ");
			buf.append(index.getName());
			buf.append(" ON ");
			buf.append(schema.getName());
			buf.append(".");
			buf.append(table.getName());
			buf.append(" ( ");
			buf.append(index.getColumn());
			buf.append(" );\n");
			return buf.toString();
		}
		else
			return "";
	}

	public String dropIndex(Schema schema, Table table, Index index) {
		Column indexColumn = getColumn(table, index.getColumn());
		if (!this.isPk(table, indexColumn)) {
			StringBuilder buf = new StringBuilder();
			buf.append("DROP INDEX ");
			buf.append(index.getName());
			buf.append(";\n");
			return buf.toString();
		}
		else
			return "";
	}
	
}
