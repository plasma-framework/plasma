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

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.plasma.common.io.StreamAssembler;

public class DDLStreamAssembler extends DDLAssembler 
    implements StreamAssembler {

    private OutputStream stream;
    private boolean indent = false;
    private String indentationToken = "\t";
	private Map<String, Table> tableMap = new HashMap<String, Table>();
    
	public DDLStreamAssembler(Schemas schemas, 
			DDLFactory factory, 
			DDLOperation operation,
			OutputStream stream) {
		super(schemas, factory, operation);
    	this.stream = stream;
    	if (this.schemas == null)
    		throw new IllegalArgumentException("expected 'schemas' argument");
    	if (this.stream == null)
    		throw new IllegalArgumentException("expected 'stream' argument");		
    	if (this.factory == null)
    		throw new IllegalArgumentException("expected 'factory' argument");		
	}
	
    public void start() {
        try {
        	// map tables
	    	for (Schema schema : this.schemas.getSchemas()) {
	    		for (Table table : schema.getTables()) {
	    			tableMap.put(schema.getName() + "." + table.getName(), table);
	    		}
	    	}
			switch (this.operation) {
			case create:
		    	createTables();
		    	createViews();
		    	createForeignKeys();
		    	createUniqueConstraints();
		    	createCheckConstraints();
		    	createIndexes();
		    	createSequences();
			    break;
			case drop:
		    	dropForeignKeys();
		    	dropUniqueConstraints();
		    	dropCheckConstraints();
		    	dropIndexes();
		    	dropSequences();
		    	dropTables();
		    	dropViews();
			    break;
			case truncate:
		    	enableForeignKeys(false);
		    	truncateTables();
		    	enableForeignKeys(true);
			    break;
			}
		} catch (IOException e) {
			throw new DDLException(e);
		}
    }
    
    private void createTables() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
    			if (!hasBehavior(BehaviorType.CREATE, schema, table)) {
    			    String ddl = createTable(schema, table);
				    this.stream.write(ddl.getBytes());
    			}
    		}
    	}    	
    }
    
    private void dropTables() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
    			if (!hasBehavior(BehaviorType.CREATE, schema, table)) {
    			    String ddl = dropTable(schema, table);
				    this.stream.write(ddl.getBytes());
    			}
    		}
    	}    	
    }
    
    private void truncateTables() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
    			if (!hasBehavior(BehaviorType.CREATE, schema, table)) {
    			    String ddl = truncateTable(schema, table);
				    this.stream.write(ddl.getBytes());
    			}
    		}
    	}    	
    }
    
    private void createViews() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
    			if (!hasBehavior(BehaviorType.CREATE, schema, table))
    			    continue;
				this.stream.write("\n".getBytes());
				Behavior behavior = getBehavior(BehaviorType.CREATE, schema, table);
			    this.stream.write(behavior.getValue().getBytes());
				if (!behavior.getValue().trim().endsWith(";"))
					this.stream.write(";".getBytes());
    		}
    	}
		this.stream.write("\n".getBytes());
    }
    
    private void dropViews() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
    			if (!hasBehavior(BehaviorType.DROP, schema, table))
    			    continue;
				this.stream.write("\n".getBytes());
				Behavior behavior = getBehavior(BehaviorType.DROP, schema, table);
			    this.stream.write(behavior.getValue().getBytes());
				if (!behavior.getValue().trim().endsWith(";"))
					this.stream.write(";".getBytes());
    		}
    	}    	
    }
 
    private void createForeignKeys() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Fk fk : table.getFks()) {
    			    Table toTable = getTable(fk.getToTable(), schema);
    			    String ddl = createForeignKeyConstraint(schema, table, fk, toTable); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }    

    private void dropForeignKeys() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Fk fk : table.getFks()) {
    			    Table toTable = getTable(fk.getToTable(), schema);
    			    String ddl = dropForeignKeyConstraint(schema, table, fk, toTable); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }  
 
    private void enableForeignKeys(boolean enable) throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Fk fk : table.getFks()) {
    			    Table toTable = getTable(fk.getToTable(), schema);
    			    String ddl = enableForeignKeyConstraint(schema, table, fk, toTable, enable); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }    
    
    private void createUniqueConstraints() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Unique unique : table.getUniques()) {
    			    String ddl = createUniqueConstraint(schema, table, unique); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }

    private void dropUniqueConstraints() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Unique unique : table.getUniques()) {
    			    String ddl = dropUniqueConstraint(schema, table, unique); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }
    
    private void enableUniqueConstraints(boolean enable) throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Unique unique : table.getUniques()) {
    			    String ddl = enableUniqueConstraint(schema, table, unique, enable); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }
    
    private void createCheckConstraints() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Check check : table.getChecks()) {
    			    String ddl = createCheckConstraint(schema, table, check); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }
    
    private void dropCheckConstraints() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Check check : table.getChecks()) {
    			    String ddl = dropCheckConstraint(schema, table, check); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }
    
    private void enableCheckConstraints(boolean enable) throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Check check : table.getChecks()) {
    			    String ddl = enableCheckConstraint(schema, table, check, enable); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }   
    
    private void createIndexes() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Index index : table.getIndices()) {
    			    String ddl = createIndex(schema, table, index); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }
    
    private void dropIndexes() throws IOException 
    {
    	for (Schema schema : this.schemas.getSchemas()) {
    		for (Table table : schema.getTables()) {
                for (Index index : table.getIndices()) {
    			    String ddl = dropIndex(schema, table, index); 
    				this.stream.write(ddl.getBytes());
                }
    		}
    	}    	
    }
    
    private void createSequences() throws IOException 
    {
		for (Schema schema : this.schemas.getSchemas()) {
			for (Table table : schema.getTables()) {
    			if (hasBehavior(BehaviorType.CREATE, schema, table))
    			    continue;
				if (table.getPk() == null) {
					continue;
				}	
			    String ddl = createSequence(schema, table); 
				this.stream.write(ddl.getBytes());
			}
		} 
    }

    private void dropSequences() throws IOException 
    {
		for (Schema schema : this.schemas.getSchemas()) {
			for (Table table : schema.getTables()) {
    			if (hasBehavior(BehaviorType.CREATE, schema, table))
    			    continue;
				if (table.getPk() == null) {
					continue;
				}	
			    String ddl = dropSequence(schema, table); 
				this.stream.write(ddl.getBytes());
			}
		} 
    }
    
    private Table getTable(String name, Schema schema) {
    	String key = schema.getName() + "." + name;
    	Table result = tableMap.get(key);
    	if (result != null)
    		return result;
		throw new IllegalArgumentException("could not find table '"+key+"' in schema '"
				+ schema.getName() + "'");
    }
    
    private String createTable(Schema schema, Table table) {
    	return this.factory.createTable(schema, table);
    }		
			
	private String dropTable(Schema schema, Table table) {
		return this.factory.dropTable(schema, table);
	}
	
	private String truncateTable(Schema schema, Table table) {
		return this.factory.truncateTable(schema, table);
    }
    
    private String createSequence(Schema schema, Table table) {
    	return this.factory.createSequence(schema, table);
    }  
    
    private String dropSequence(Schema schema, Table table) {
    	return this.factory.dropSequence(schema, table);
    }  
    
    private String createIndex(Schema schema, Table table, Index index) {
    	return this.factory.createIndex(schema, table, index);
    }  
 
    private String dropIndex(Schema schema, Table table, Index index) {
    	return this.factory.dropIndex(schema, table, index);
    }  
    
    private String createCheckConstraint(Schema schema, Table table, Check check) {
    	return this.factory.createCheckConstraint(schema, table, check);
    }
    
    private String dropCheckConstraint(Schema schema, Table table, Check check) {
    	return this.factory.dropCheckConstraint(schema, table, check);
    }

    private String enableCheckConstraint(Schema schema, Table table, Check check, boolean enable) {
    	return this.factory.enableCheckConstraint(schema, table, check, enable);
    }
    
    private String createUniqueConstraint(Schema schema, Table table, Unique unique) {
    	return this.factory.createUniqueConstraint(schema, table, unique);
    }
    
    private String dropUniqueConstraint(Schema schema, Table table, Unique unique) {
    	return this.factory.dropUniqueConstraint(schema, table, unique);
    }
    
    private String enableUniqueConstraint(Schema schema, Table table, Unique unique, boolean enable) {
    	return this.factory.enableUniqueConstraint(schema, table, unique, enable);
    }
     
    private String createForeignKeyConstraint(Schema schema, Table table, Fk fk, Table toTable) {
    	return this.factory.createForeignKeyConstraint(schema, table, fk, toTable);
    }
    
    private String dropForeignKeyConstraint(Schema schema, Table table, Fk fk, Table toTable) {
    	return this.factory.dropForeignKeyConstraint(schema, table, fk, toTable);
    }
 
    private String enableForeignKeyConstraint(Schema schema, Table table, Fk fk, Table toTable, boolean enable) {
    	return this.factory.enableForeignKeyConstraint(schema, table, fk, toTable, enable);
    }
    
    private boolean hasBehavior(BehaviorType type, Schema schema, Table table) {
    	for (Behavior behavior : table.getBehaviors()) {
    	    if (behavior.getType().ordinal() == type.ordinal())
    	    	return true;
    	}
    	return false;
    }
    
    private Behavior getBehavior(BehaviorType type, Schema schema, Table table) {
    	for (Behavior behavior : table.getBehaviors()) {
    	    if (behavior.getType().ordinal() == type.ordinal())
    	    	return behavior;
    	}
    	return null;
    }    
	
	public boolean isIndent() {
		return indent;
	}

	public void setIndent(boolean indent) {
		this.indent = indent;
	}

	public String getIndentationToken() {
		return indentationToken;
	}

	public void setIndentationToken(String indentationToken) {
		this.indentationToken = indentationToken;
	}
	
}
