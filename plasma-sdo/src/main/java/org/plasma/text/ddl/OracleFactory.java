package org.plasma.text.ddl;

import org.plasma.sdo.DataType;

public class OracleFactory extends DefaultDDLFactory 
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
    	            return "CLOB"; 
        		}
        		else
        	        return "VARCHAR2(" + column.getSize() + ")"; 
        	}
        	else
	            return "VARCHAR2(255)"; // FIXME - were does the default for this live ??
		case Boolean: 
		case Byte:        
			return "NUMBER(1)";
		case Character:  
			return "VARCHAR2(1)";
		case Decimal:   
			return "NUMBER(22)";
		case Double:    
			return "FLOAT(49)";
		case Float:      
			return "FLOAT(23)";
		case Short:  
			return "NUMBER(6)";
		case Integer:   
			return "NUMBER(24)";
		case Int:        
		case Long:  
			return "NUMBER(10)";
		case Bytes:      
 		case Object:     
		case Date:     
		case DateTime:   
			return "DATE";
		case Time:       
			return "TIMESTAMP";
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
}
