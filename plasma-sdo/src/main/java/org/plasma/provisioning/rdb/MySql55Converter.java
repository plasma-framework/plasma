package org.plasma.provisioning.rdb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.PlasmaConfig;
import org.plasma.metamodel.Alias;
import org.plasma.metamodel.Behavior;
import org.plasma.metamodel.BehaviorType;
import org.plasma.metamodel.Body;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.DataTypeRef;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.DocumentationType;
import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.EnumerationConstraint;
import org.plasma.metamodel.EnumerationLiteral;
import org.plasma.metamodel.EnumerationRef;
import org.plasma.metamodel.Key;
import org.plasma.metamodel.KeyType;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;
import org.plasma.metamodel.TypeRef;
import org.plasma.metamodel.UniqueConstraint;
import org.plasma.metamodel.ValueConstraint;
import org.plasma.metamodel.VisibilityType;
import org.plasma.provisioning.NameUtils;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.SchemaConverter;
import org.plasma.provisioning.rdb.mysql.v5_5.ColumnKeyType;
//import org.plasma.provisioning.rdb.mysql.v5_5.Constraint;
import org.plasma.provisioning.rdb.mysql.v5_5.ConstraintType;
import org.plasma.provisioning.rdb.mysql.v5_5.SysDataType;
import org.plasma.provisioning.rdb.mysql.v5_5.Table;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumn;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumnConstraint;
import org.plasma.provisioning.rdb.mysql.v5_5.TableColumnKeyUsage;
import org.plasma.provisioning.rdb.mysql.v5_5.TableConstraint;
import org.plasma.provisioning.rdb.mysql.v5_5.TableType;
import org.plasma.provisioning.rdb.mysql.v5_5.View;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QTable;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QTableColumn;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QTableColumnConstraint;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QTableColumnKeyUsage;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QTableConstraint;
import org.plasma.provisioning.rdb.mysql.v5_5.query.QView;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewComment;
import org.plasma.sdo.DataType;
import org.plasma.sdo.helper.PlasmaXMLHelper;
import org.plasma.sdo.xml.DefaultOptions;

import commonj.sdo.DataGraph;
import commonj.sdo.helper.XMLDocument;

public class MySql55Converter extends ConverterSupport implements SchemaConverter {
	private static Log log = LogFactory.getLog(
			MySql55Converter.class); 
    /** Maps physical name qualified property names to classes */
    protected Map<String, Class> classQualifiedPropertyPhysicalNameMap = new HashMap<String, Class>();
    /** maps properties to physical constraints */
    protected Map<Property, ConstraintInfo[]> constraintMap = new HashMap<Property, ConstraintInfo[]>();
    

	public MySql55Converter(String[] schemaNames, 
			String[] namespaces) {
		this.schemaNames = schemaNames;
		this.namespaces = namespaces;
	}
	
	
	@Override
	public Model buildModel() {
		
		this.model = new Model();
		this.model.setId(UUID.randomUUID().toString());
        
        // if single schema
        if (this.schemaNames.length == 1) {
        	this.model.setUri(this.namespaces[0]);
    		Alias alias = new Alias();
        	alias.setPhysicalName(this.schemaNames[0]);
        	this.model.setAlias(alias);
        	this.model.setName(this.schemaNames[0]);
        }
        else {
        	this.model.setName("model");
        }

    	for (int i = 0; i < this.schemaNames.length; i++) {
    		
			log.info("loading schema '" + this.schemaNames[i] + "'");
    		Package pkg = findPackage(this.schemaNames[i], this.namespaces[i]);
    		loadTables(pkg, this.schemaNames[i]);
    		//loadViews(pkg, schema);
		} // schema
    	
    	// process referential constraints
    	for (Class clss : this.classQualifiedNameMap.values()) {
    		Property[] props = new Property[clss.getProperties().size()];
    		clss.getProperties().toArray(props);
    		for (Property prop : props) {
    			ConstraintInfo[] infos = this.constraintMap.get(prop);
    			if (infos == null)
    				continue;
				for (ConstraintInfo info : infos) {
					String constraintType = info.getConstraint().getConstraintType().replace(' ', '_');
					ConstraintType consType = ConstraintType.valueOf(constraintType);
					switch (consType) {
					case FOREIGN_KEY:
						String qualifiedName = info.getTableColumnConstraint().getReferencedTableName()
						    + "." + info.getTableColumnConstraint().getReferencedColumnName();
						Class targetClass = this.classQualifiedPropertyPhysicalNameMap.get(qualifiedName);
						if (targetClass == null)
							throw new ProvisioningException("no target class found for, "
									+ qualifiedName);
				        // not that we know its a reference prop, tweak
						// its name and type
						String name = NameUtils.firstToLowerCase(
					        	targetClass.getName());
						int count = countPropertiesByLogicalNamePrefix(clss, name);
						if (count > 0)
							name += String.valueOf(count);						
				        prop.setName(name);
						TypeRef typeRef = new ClassRef();
				    	typeRef.setName(targetClass.getName());
				    	typeRef.setUri(targetClass.getUri());
				        prop.setType(typeRef); 				        
									        
				        Property targetPkProp = this.propertyQualifiedPriKeyConstrainatNameMap.get(qualifiedName);
						
				        String oppositeName = NameUtils.firstToLowerCase(
				        		clss.getName());
						count = countPropertiesByLogicalNamePrefix(targetClass, oppositeName);
						if (count > 0)
							oppositeName += String.valueOf(count);
						
						prop.setOpposite(oppositeName);
				        
				        // create derived opposite
				        Property derived = createDerivedPropertyOpposite(clss, prop);
				        targetClass.getProperties().add(derived);
				        
						break; 
					default:
						break;
					}
				}
    		}
    	}    	
	
		return model;
	}
	
	private int countPropertiesByLogicalNamePrefix(Class clss, String namePrefix) {
		int result = 0;
		for (Property prop : clss.getProperties())
			if (prop.getName().endsWith(namePrefix))
				result++;
		return result;
	}
	
    private Property createDerivedPropertyOpposite(Class clss, Property sourceProperty)
    {
    	Property derived = new Property();
        derived.setId(UUID.randomUUID().toString());
        derived.setName(sourceProperty.getOpposite()); // actual SDO type name stored as sdox name        
        Documentation documentation = new Documentation();
        documentation.setType(DocumentationType.DEFINITION);
        Body docBody = new Body();
        documentation.setBody(docBody);
        docBody.setValue("private derived opposite for, "
        	+ clss.getUri() + "#" + clss.getName() + "." + sourceProperty.getName());
		derived.getDocumentations().add(documentation);
        derived.setVisibility(VisibilityType.PRIVATE); 
        
        derived.setNullable(true);
        derived.setMany(true); 
        
        derived.setDerived(true);
        derived.setContainment(false);
              
        derived.setOpposite(sourceProperty.getName());   
        
        ClassRef targetClassRef = new ClassRef();
        targetClassRef.setName(clss.getName());
        targetClassRef.setUri(clss.getUri());
        derived.setType(targetClassRef);
        
        return derived;
    }
	
	private void loadTables(Package pkg, String schema)
	{
		for (String tableName : getTableNames(schema)) {
			log.info("loading table '" + tableName + "'");
			Table table = getTableGraph(schema, tableName);
			if (log.isDebugEnabled())
				try {
				    log.debug(serializeGraph(table.getDataGraph()));
				}
			    catch (IOException e) {
			    	log.error(e.getMessage());
			    }
			
			Class clss = buildClass(pkg, table);
			pkg.getClazzs().add(clss);
			String key = pkg.getUri() + "#" + clss.getName();
			this.classQualifiedNameMap.put(key, clss);
			
			for (TableColumn column : table.getTableColumn()) {
				log.debug("\tloading column '" + column.getColumnName() + "'");
				ConstraintInfo[] constraints = findConstraints(column, table);
				Property prop = buildProperty(pkg, clss, column, 
					constraints);
				clss.getProperties().add(prop);	
				if ( prop.getAlias() != null && prop.getAlias().getPhysicalName() != null)
				    this.classQualifiedPropertyPhysicalNameMap.put(
					    clss.getAlias().getPhysicalName() + "." + prop.getAlias().getPhysicalName(), 
					    clss);
				this.constraintMap.put(prop, constraints);
			}
		}		
	}	
	
	private Package findPackage(String schema, String namespace) {
		Package pkg = null;;
        if (this.schemaNames.length > 1) {
        	pkg = new Package();	
    		pkg.setName(schema.toLowerCase());
    		pkg.setId(UUID.randomUUID().toString());
    		pkg.setUri(namespace);        	
    		Alias alias = new Alias();
        	alias.setPhysicalName(schema);
        	pkg.setAlias(alias);
        	this.model.getPackages().add(pkg);
	    }
	    else
	    	pkg = this.model;
        return pkg;
	}
	
	class ConstraintInfo {
		private TableConstraint constraint;
		private TableColumnKeyUsage columnConstraint;
		public ConstraintInfo(TableConstraint constraint,
				TableColumnKeyUsage columnConstraint) {
			super();
			this.constraint = constraint;
			this.columnConstraint = columnConstraint;
		}
		public TableConstraint getConstraint() {
			return constraint;
		}
		public TableColumnKeyUsage getTableColumnConstraint() {
			return columnConstraint;
		}		
	}
	
	private ConstraintInfo[] findConstraints(TableColumn column, 
			Table table) {
		return findConstraints(column, table.getTableConstraint(),
				table.getTableColumnConstraint(),
				table.getTableColumnKeyUsage());
	}
	
	
	private ConstraintInfo[] findConstraints(TableColumn column, 
			TableConstraint[] constraints, TableColumnConstraint[] columnConstraints,
			TableColumnKeyUsage[] columnKeyUsages) {		
		List<ConstraintInfo> list = new ArrayList<ConstraintInfo>();
		if (columnConstraints != null && constraints != null)
		for (TableColumnKeyUsage colConst : columnKeyUsages) {
			if (!colConst.getColumnName().equals(column.getColumnName()))
				continue;
			list.add(new ConstraintInfo(
					getTableConstraint(colConst, constraints),
					getColumnConstraint(colConst, columnKeyUsages) 
					));
		}
		ConstraintInfo[] result = new ConstraintInfo[list.size()];
		list.toArray(result);
		return result;
	}
	
	private TableConstraint getTableConstraint(TableColumnKeyUsage constraint,
			TableConstraint[] constraints)
	{
		if (constraints != null)
		for (TableConstraint c : constraints)
			if (c.getName().equals(constraint.getName())) 
			    return c;
		throw new IllegalArgumentException("no constraint found for given constraint name '"
				+ constraint.getName() + "'");
	}
	
	private TableColumnKeyUsage getColumnConstraint(TableColumnKeyUsage constraint,
			TableColumnKeyUsage[] constraints)
	{
		if (constraints != null)
		for (TableColumnKeyUsage c : constraints)
			if (c.getName().equals(constraint.getName())) 
			    return c;
		throw new IllegalArgumentException("no constraint found for given constraint name '"
				+ constraint.getName() + "'");
	}
			
	private Table getTableGraph(String schema, String tableName)
	{
		QTable table = QTable.newQuery();	
		QTableColumn tableColumn = QTableColumn.newQuery();
		QTableConstraint tableConstraint = QTableConstraint.newQuery();
		QTableColumnKeyUsage columnKeyUsage = QTableColumnKeyUsage.newQuery();
		QTableColumnConstraint tableColumnConstraint = QTableColumnConstraint.newQuery();
		QView view = QView.newQuery();
		table.select(table.wildcard()) 
		     .select(table.tableColumn(tableColumn.owner().eq(schema)).wildcard()) 
		     .select(table.tableConstraint(tableConstraint.owner().eq(schema)).wildcard()) 
		     .select(table.tableColumnKeyUsage(columnKeyUsage.owner().eq(schema)).wildcard()) 
		     .select(table.tableColumnConstraint(tableColumnConstraint.owner().eq(schema)).wildcard())
		     .select(table.view(view.owner().eq(schema)).wildcard());
		table.where(table.owner().eq(schema)
			 .and(table.tableName().eq(tableName)));
        DataGraph[] results = this.client.find(table);
		return (Table)results[0].getRootObject();
	}
	
	private List<String> getTableNames(String schema)
	{
		List<String> result = new ArrayList<String>();
		QTable query = QTable.newQuery();		
		query.select(query.tableName());
		query.where(query.owner().eq(schema));
		
		for (DataGraph graph : client.find(query)) {
			Table tab = (Table)graph.getRootObject();
			result.add(tab.getTableName());
		}
		return result;
	}

	
    public Class buildClass(Package pkg, Table table) {
    	Class clss = new Class();
    	clss.setId(UUID.randomUUID().toString());
    	clss.setName(NameUtils.firstToUpperCase(
    		NameUtils.toCamelCase(table.getTableName())));
    	clss.setUri(pkg.getUri());
    	Alias alias = new Alias();
    	alias.setPhysicalName(table.getTableName());
    	clss.setAlias(alias);
    	
    	if (table.getTableComment() != null) {
	        Documentation documentation = new Documentation();
			documentation.setType(DocumentationType.DEFINITION);
			Body body = new Body();
			body.setValue(filter(table.getTableComment()));
			documentation.setBody(body);
			clss.getDocumentations().add(documentation);
		}
    	
		String tableTypeStr = table.getTableType().replace(' ', '_');
    	TableType tableType = TableType.valueOf(tableTypeStr);
    	if (tableType.ordinal() == TableType.VIEW.ordinal()) {
    		if (table.getViewCount() == 1) {
	    		View view = table.getView(0);
	        	// add the view creation content
	        	if (view.getViewDefinition() != null && view.getViewDefinition().length() > 0) {
	        		Behavior create = new Behavior();
	        		create.setLanguage("SQL");
	        		create.setType(BehaviorType.CREATE);
	        		create.setName(BehaviorType.CREATE.name().toLowerCase());
	        		String definition = filter(view.getViewDefinition());
	        		create.setValue("CREATE OR REPLACE VIEW " + pkg.getAlias().getPhysicalName() 
		    				+ "." + clss.getAlias().getPhysicalName() + " AS " + definition);
	        		clss.getBehaviors().add(create);
	        	}
	        	
	    		Behavior drop = new Behavior();
	    		drop.setLanguage("SQL");
	    		drop.setType(BehaviorType.DROP);
	    		drop.setName(BehaviorType.DROP.name().toLowerCase());
	    		drop.setValue("DROP VIEW " + pkg.getAlias().getPhysicalName() 
	    				+ "." + clss.getAlias().getPhysicalName() + ";");
	    		clss.getBehaviors().add(drop);   
    		}
    		else
    			log.warn("no view definition found for '" + table.getTableName() + "'");
    	}
    	
    	
    	return clss;
    }	
    
    
    public Property buildProperty(Package pkg, Class clss, TableColumn column,
    		ConstraintInfo[] constraints) {
        Property property = new Property();
        property.setId(UUID.randomUUID().toString());
        property.setVisibility(VisibilityType.PUBLIC); 
        property.setName(NameUtils.firstToLowerCase(
        		NameUtils.toCamelCase(column.getColumnName())));

    	Alias alias = new Alias();
    	alias.setPhysicalName(column.getColumnName());
    	property.setAlias(alias);
    	
        // nullable
    	if ("YES".equalsIgnoreCase(column.getNullable()))
    		property.setNullable(true);
    	else
    		property.setNullable(false);
    	
    	SysDataType oracleType = SysDataType.valueOf(column.getDataType().toUpperCase());
    	DataType sdoType = mapType(oracleType, column.getCharMaxLength(),
    			column.getDataPrecision(), column.getDataScale());
    	DataTypeRef dataTypeRef = new DataTypeRef();
        dataTypeRef.setName(sdoType.name());
        dataTypeRef.setUri(PlasmaConfig.getInstance().getSDODataTypesNamespace().getUri());
        property.setType(dataTypeRef);  
        
        ValueConstraint valueConstraint = buildValueConstraint(oracleType, column.getCharMaxLength(),
    		column.getDataPrecision(), column.getDataScale());
        if (valueConstraint != null) 
        	property.setValueConstraint(valueConstraint);
        
        if (oracleType.ordinal() == SysDataType.ENUM.ordinal()) {
			String condition = column.getColumnType();
			if (condition != null) {
				String[] literals = parseLiterals(column, condition);
				if (literals != null) {
					Enumeration enm = buildEnumeration(pkg, clss, property,
				    	literals);
					pkg.getEnumerations().add(enm);
					this.enumQualifiedNameMap.put(enm.getUri() + "#" + enm.getName(), enm);
					EnumerationRef enumRef = new EnumerationRef();
					enumRef.setName(enm.getName());
					enumRef.setUri(enm.getUri());	
					EnumerationConstraint enumConst = new EnumerationConstraint();
					enumConst.setValue(enumRef);
					property.setEnumerationConstraint(enumConst);
				}
			}
        }
        
        // MySql metemodel defines PK constraints in 2 ways..possible
        // for backwards compatibility
        if (column.getColumnKey() != null && column.getColumnKey().trim().length() > 0) {
        	ColumnKeyType keyType = ColumnKeyType.valueOf(column.getColumnKey().trim());
        	switch (keyType) {
        	case PRI:
				Key prikey = new Key();
				prikey.setType(KeyType.PRIMARY);
				property.setKey(prikey);
				String constraintName = getSyntheticPriKeyConstraintName(pkg, clss, property);
				String qualifiedName = clss.getAlias().getPhysicalName()
						+ "." + constraintName;
				this.propertyQualifiedPriKeyConstrainatNameMap.put(
					qualifiedName, property);				
        		break;
        	default:
        		break;
        	}
        }        

        for (ConstraintInfo info : constraints) {
        	String constraintType = info.getConstraint().getConstraintType().replace(' ', '_'); // as will be used in an enum
			ConstraintType consType = ConstraintType.valueOf(constraintType);
			switch (consType) {
			case PRIMARY_KEY: // pk
				Key prikey = new Key();
				prikey.setType(KeyType.PRIMARY);
				property.setKey(prikey);
				String qualifiedName = clss.getAlias().getPhysicalName()
						+ "." + info.getConstraint().getName();
				this.propertyQualifiedPriKeyConstrainatNameMap.put(
					qualifiedName, property);				
				UniqueConstraint uniqueConstraint = new UniqueConstraint();
				uniqueConstraint.setGroup(info.getConstraint().getName());
				property.setUniqueConstraint(uniqueConstraint);
				break; 
			case FOREIGN_KEY: // referential
				// deal with on second pass after all tables processed 
				break; 
			case UNIQUE: // unique
				uniqueConstraint = new UniqueConstraint();
				uniqueConstraint.setGroup(info.getConstraint().getName());
				property.setUniqueConstraint(uniqueConstraint);
				break; 
			default:
				break;
			}
        }
        
		if (column.getColumnComment() != null) {
	        Documentation documentation = new Documentation();
			documentation.setType(DocumentationType.DEFINITION);
			Body body = new Body();
			body.setValue(filter(column.getColumnComment()));
			documentation.setBody(body);
			property.getDocumentations().add(documentation);
		}
        
        return property;
    }
    
    private String getSyntheticPriKeyConstraintName(Package pkg, Class clss, Property property) {
    	return "pk_" + property.getAlias().getPhysicalName().toLowerCase();
    }
    
    private Enumeration buildEnumeration(Package pkg, Class clss, Property property,
    		String[] literals) {
    	Enumeration enm = new Enumeration();
    	enm.setId(UUID.randomUUID().toString());
    	String baseName = NameUtils.firstToUpperCase(
    			property.getName()) + "Values";  
    	
    	String suffix = getNameSequence(clss.getUri(), baseName, this.enumQualifiedNameMap);
    	String name = baseName + suffix;
    	
    	// ensure name is unique per package
    	int count = countExistingEnumsByName(pkg, name);
    	if (count > 0)
    		name += String.valueOf(count);
    	enm.setName(name);
    	
    	enm.setUri(clss.getUri());
        Documentation documentation = new Documentation();
		documentation.setType(DocumentationType.DEFINITION);
		Body body = new Body();
		body.setValue("This enumeration was derived from enum column " 
			+ clss.getAlias().getPhysicalName() + "." 
			+ property.getAlias().getPhysicalName() 
			+ " and linked as an SDO enumeration constraint to logical"
			+ " property " + clss.getName() + "." + property.getName() 
			+ ".");
		documentation.setBody(body); 
		enm.getDocumentations().add(documentation);
		
		for (String literalStr : literals) {
			EnumerationLiteral literal = new EnumerationLiteral();
			enm.getEnumerationLiterals().add(literal);
			literal.setName(NameUtils.toCamelCase(literalStr));
			literal.setValue(NameUtils.toCamelCase(literalStr));
    		literal.setId(UUID.randomUUID().toString());
    		Alias alias = new Alias();
            literal.setAlias(alias);       
            alias.setPhysicalName(literalStr); 
		}
    	return enm;
    }
    
    private int countExistingEnumsByName(Package pkg, String name) {
    	int i = 0;
    	for (Enumeration enm : pkg.getEnumerations()) {
    		if (enm.getName().startsWith(name))
    		  i++;
    	}
    	return i;
    }
    
    private String getNameSequence(String uri, String baseName,
    		Map<String, ? extends Object> map) {
    	String qualifiedName = uri + "#" + baseName;
    	int num = 0;
    	while (map.get(qualifiedName) != null) {
    		qualifiedName = uri + "#" 
    	        + baseName + String.valueOf(num);
    		num++;
    	}
    	if (num > 0)
    		return String.valueOf(num);
    	else
    		return "";
    }
    
    /**
     * Extracts and returns literals as a string array 
     * from the given condition, or null if the condition does
     * not apply.  
     * 
     * @param column the column
     * @param condition the search condition
     * @return the literals
     */
    private String[] parseLiterals(TableColumn column, String condition) {
    	String[] literals = null;
    	
    	int rightIndex = condition.indexOf("(");
		int leftIndex = condition.indexOf(")");
		if (rightIndex >= 0 && leftIndex > 0 && leftIndex > rightIndex) {
			String rawTokens = condition.substring(rightIndex, leftIndex);
			literals = rawTokens.split(","); 
			for (int i = 0; i < literals.length; i++) {
				int rightAposIndex = literals[i].indexOf("'");
				int leftAposIndex = literals[i].lastIndexOf("'");
				if (rightAposIndex >= 0 && leftAposIndex > 0) {
				    literals[i] = literals[i].substring(rightAposIndex+1, leftAposIndex);
				}
				literals[i] = literals[i].trim();
			}
		}
    	return literals;
    }
    
    private boolean findTokenIgnoreCase(String value, String[] tokens) {
    	for (String token : tokens) {
    		if (token.equalsIgnoreCase(value))
    			return true;
    	}
    	return false;
    }
    
    private ValueConstraint buildValueConstraint(SysDataType rdbType, int dataLength,
    		int dataPrecision, int dataScale) {
        ValueConstraint constraint = null;
    	switch (rdbType) {
    	case CHAR:	
    	case VARCHAR:	
    	case ENUM:	
    	case SET:
    	case TEXT:
    	case TINYTEXT:
    	case MEDIUMTEXT:
    	case LONGTEXT:	
    		if (dataLength > 0) {
    			constraint = new ValueConstraint();
    			constraint.setMaxLength(String.valueOf(dataLength));
    		}
    		break;
    	case BINARY:
    	case VARBINARY:
    	case TINYBLOB:
    	case BLOB:
    	case MEDIUMBLOB:
    	case LONGBLOB:
    		if (dataLength > 0) {
    			constraint = new ValueConstraint();
    			constraint.setMaxLength(String.valueOf(dataLength));
    		}
    		break;    		
    	case DECIMAL:
    	case DEC:
    	case NUMERIC:
    	case FIXED:
    	case FLOAT:
    	case DOUBLE:
    	case DOUBLE__PRECISION:
    		if (dataPrecision > 0) {
    			constraint = new ValueConstraint();
    			constraint.setTotalDigits(String.valueOf(dataPrecision));
    			if (dataScale > 0)
    				constraint.setFractionDigits(String.valueOf(dataScale));
    		} 
    		break;
    	default:
    		// just don't build one
    	}
    	return constraint;
    }    
    
    private DataType mapType(SysDataType rdbType, int dataLength,
    		int dataPrecision, int dataScale) {
    	switch (rdbType) {
    	case CHAR:	
    	case VARCHAR:	
    	case ENUM:	
    	case SET:
    	case TEXT:
    	case TINYTEXT:
    	case MEDIUMTEXT:
    	case LONGTEXT:	
    		return DataType.String;
    	case BIT:	
    		if (dataLength == 1) {
        		return DataType.Boolean;
    		} 
    		else  
    		    return DataType.Bytes;    		
    	case TINYINT:
    		if (dataLength == 1) {
        		return DataType.Boolean;
    		} 
    		else  
    		    return DataType.Short;    		
    	case BOOL:	
    		return DataType.Boolean;
    	case BOOLEAN:	
    		return DataType.Boolean;
    	case SMALLINT:
    		return DataType.Short;  
    	case MEDIUMINT:
    		return DataType.Int;  
    	case INT:
    		return DataType.Int;  
    	case INTEGER:
    		return DataType.Int;  
    	case BIGINT:
    		return DataType.Integer;  
    	case DECIMAL:
    	case DEC:
    	case NUMERIC:
    	case FIXED:
    		return DataType.Decimal;
    	case FLOAT:
    		return DataType.Float;
    	case DOUBLE:
    	case DOUBLE__PRECISION:
    		return DataType.Double;
    	case DATE:			
    		return DataType.Date;
    	case DATETIME:			
    		return DataType.DateTime;
    	case TIMESTAMP:		
    		return DataType.DateTime;
    	case TIME:		
    		return DataType.Time;
    	case YEAR:		
    		return DataType.Year;
    	case BINARY:
    	case VARBINARY:
    	case TINYBLOB:
    	case BLOB:
    	case MEDIUMBLOB:
    	case LONGBLOB:
    		return DataType.Bytes;
    	default:
    		log.warn("unknown datatype '"
    				+ rdbType.name() + "' - using String");
    		return DataType.String;
    	}
    }
    
    /**
     * Given only a max number of digits (precision), determine
     * the appropriate integral SDO type. 
     * @param dataPrecision the number of digits
     * @return the data type
     */
    private DataType mapIntegralType(int dataPrecision)
    {
		if (dataPrecision == 1) 
		    return DataType.Boolean;
		else if (dataPrecision <= 5) // -32,768 to 32,768
		    return DataType.Short;
		else if (dataPrecision <= 10) // -2,147,483,647 to 2,147,483,647
		    return DataType.Int;
		else if (dataPrecision <= 19) // -9,223,372,036,854,775,807 to 9,223,372,036,854,775,807
		    return DataType.Long;
		else
			return DataType.Integer; // SDO type maps to Java BigInteger
    }
    
    /**
     * Given the max number of digits (precision) and scale
     * determine the appropriate floating point SDO type. 
     * @param dataPrecision the number of digits
     * @param dataScale the number of fractional digits (i.e. to right of decimal)
     * @return the data type
     */
    private DataType mapFloatingPointType(int dataPrecision, 
    		int dataScale)
    {
		if (dataPrecision <= 19) // FLOAT(16) in MSSql, Sybase
		    return DataType.Float;
		else if (dataPrecision <= 35) // FLOAT(32) in MSSql, Sybase
		    return DataType.Double;
		else
			return DataType.Decimal; // SDO type maps to Java BigDEcimal
    }
           
    private String serializeGraph(DataGraph graph) throws IOException
    {
        DefaultOptions options = new DefaultOptions(
        		graph.getRootObject().getType().getURI());
        options.setRootNamespacePrefix("dump");
        
        XMLDocument doc = PlasmaXMLHelper.INSTANCE.createDocument(graph.getRootObject(), 
        		graph.getRootObject().getType().getURI(), 
        		null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
	    PlasmaXMLHelper.INSTANCE.save(doc, os, options);        
        os.flush();
        os.close(); 
        String xml = new String(os.toByteArray());
        return xml;
    }
}
