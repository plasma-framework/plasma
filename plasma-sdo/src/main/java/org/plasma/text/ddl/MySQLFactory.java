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

public class MySQLFactory extends DefaultDDLFactory 
    implements DDLFactory 
{
    
	public String getType(Table table, Column column) {
		DataType sdoType = DataType.valueOf(column.getType());
		switch (sdoType) {
		case String:     
		case Strings:    
		case URI:        
        	if (column.getSize() > 0) {
        		if (column.getSize() > 4000) {
    	            return "TEXT"; 
        		}
        		else
        	        return "VARCHAR(" + column.getSize() + ")"; 
        	}
        	else
	            return "VARCHAR(255)"; // FIXME - were does the default for this live ??
		case Boolean: 
		case Byte:        
			return "NUMERIC(1)";
		case Character:  
			return "VARCHAR(1)";
		case Decimal:   
			return "NUMERIC(22)";
		case Double:    
			return "FLOAT(49)";
		case Float:      
			return "FLOAT(23)";
		case Short:  
			return "SMALLINT";
		case Integer:   
			return "BIGINT";
		case Int:        
			return "INT";
		case Long:  
			return "BIGINT";
		case Date:     
		case DateTime:   
			return "DATE";
		case Time:       
			return "TIMESTAMP";
		case Bytes:      
 		case Object:     
		case Day:        
		case Duration:   
		case Month:      
		case MonthDay:   
		case Year:       
		case YearMonth:  
		case YearMonthDay:
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
			if (!isEnumerativeCheck(table, column)) {
			    buf.append(getType(table, column));
			}
			else {
				Check check = getEnumerativeCheck(table, column);
				buf.append("ENUM(");
				int j = 0;
				for (String s : check.getValue()) {
					if (j > 0)
						buf.append(", ");
					buf.append("'");
					buf.append(s);
					buf.append("'");
					j++;
				}
				buf.append(")");
			}
			if (!column.isNullable())
				buf.append(" NOT NULL");
			if (isSequence(table, column))
				buf.append(" AUTO_INCREMENT");
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
	
	@Override
	public String createView(Schema schema, Table table, Behavior create) {
		return create.getValue();
	}

	@Override
	public String dropView(Schema schema, Table table, Behavior drop) {
		return drop.getValue();
	}	
	
	private boolean isEnumerativeCheck(Table table, Column column) {
		for (Check check : table.getCheck()) {
			if (column.getName().equals(check.getColumn()))
				return true;
		}
		return false;
	}	
	
	private Check getEnumerativeCheck(Table table, Column column) {
		for (Check check : table.getCheck()) {
			if (column.getName().equals(check.getColumn()))
				return check;
		}
		throw new IllegalStateException("could nto find enumerative check on "
				+ table.getName() + "." + column.getName());
	}	
	
	/**
	 * Returns true if the given column is presumed to be a sequence. 
	 * @param table the table
	 * @param column the column
	 * @return true true if the given column is presumed to be a sequence. 
	 */
	private boolean isSequence(Table table, Column column) {		
		if (isPk(table, column) && !(isFk(table, column))) {
			DataType sdoType = DataType.valueOf(column.getType());
			switch (sdoType) {
			case Int:        
			case Integer:    
			case Long:  
				return true;
			}
		}
		return false;
	}	
	
	@Override
	public String createForeignKeyConstraint(Schema schema, Table table,
			Fk fk, Table toTable) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" ADD CONSTRAINT ");
		buf.append(fk.getName());
		buf.append(" FOREIGN KEY ( ");
		buf.append(fk.getColumn());
		buf.append(" ) REFERENCES ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(toTable.getName());
		buf.append(" ( ");
		int i = 0;
		for (On on : toTable.getPk().getOn()) {
			if (i > 0)
				buf.append(", ");
			buf.append(on.getColumn());
			i++;
		}
		buf.append(" );\n");
		return buf.toString();
	}

	@Override
	public String dropForeignKeyConstraint(Schema schema, Table table, Fk fk,
			Table toTable) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" DROP FOREIGN KEY ");
		buf.append(fk.getName());
		buf.append(";\n");
		return buf.toString();
	}
	
	public String enableForeignKeyConstraint(Schema schema, Table table,
			Fk fk, Table toTable, boolean enable) {
		if (enable) {
			return createForeignKeyConstraint(schema, table,
			    fk, toTable);
		}
		else {
			return dropForeignKeyConstraint(schema, table,
				    fk, toTable);
		}
	}
	
	@Override
	public String createUniqueConstraint(Schema schema, Table table,
			Unique unique) {
		StringBuilder buf = new StringBuilder();

		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(" ADD CONSTRAINT ");
		buf.append(unique.getName());
		buf.append(" UNIQUE ( ");
		int i = 0;
		for (On on : unique.getOn()) {
			if (i > 0)
				buf.append(", ");
			buf.append(on.getColumn());
			i++;
		}
		buf.append(" );\n");

		return buf.toString();
	}
	
	@Override
	public String dropUniqueConstraint(Schema schema, Table table,
			Unique unique) {
		StringBuilder buf = new StringBuilder();
		return buf.toString();
	}
	
	@Override
	public String dropIndex(Schema schema, Table table, Index index) {
		StringBuilder buf = new StringBuilder();
		buf.append("DROP INDEX ");
		buf.append(index.getName());
		buf.append(" ON ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		buf.append(";\n");
		return buf.toString();
	}
	
	@Override
	public String createSequence(Schema schema, Table table) 
	{
		return ""; // no sequence facility in MySql
	}

	@Override
	public String dropSequence(Schema schema, Table table) {		
		return ""; // no sequence facility in MySql
	}
	
	@Override
	public String createCheckConstraint(Schema schema, Table table, Check check) {		
		return ""; // implemented with MySql enum
	}

	@Override
	public String dropCheckConstraint(Schema schema, Table table, Check check) {
		return ""; // implemented with MySql enum
	}
	
	public String enableUniqueConstraint(Schema schema, Table table,
			Unique unique, boolean enable) {
		StringBuilder buf = new StringBuilder();
		buf.append("ALTER TABLE ");
		buf.append(schema.getName());
		buf.append(".");
		buf.append(table.getName());
		if (enable)
			buf.append(" ADD CONSTRAINT ");
		else
			buf.append(" DROP CONSTRAINT ");
		buf.append(unique.getName());
		buf.append(";\n");
		return buf.toString();
	}

}

