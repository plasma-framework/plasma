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
import org.plasma.provisioning.Model;
import org.plasma.provisioning.SchemaConverter;
import org.plasma.provisioning.rdb.oracle.sys.Column;
import org.plasma.provisioning.rdb.oracle.sys.ColumnComment;
import org.plasma.provisioning.rdb.oracle.sys.ColumnConstraint;
import org.plasma.provisioning.rdb.oracle.sys.Constraint;
import org.plasma.provisioning.rdb.oracle.sys.ConstraintType;
import org.plasma.provisioning.rdb.oracle.sys.SysDataType;
import org.plasma.provisioning.rdb.oracle.sys.Table;
import org.plasma.provisioning.rdb.oracle.sys.TableComment;
import org.plasma.provisioning.rdb.oracle.sys.query.QTable;
import org.plasma.sdo.DataType;
import org.plasma.sdo.access.client.JDBCPojoDataAccessClient;
import org.plasma.sdo.helper.PlasmaXMLHelper;
import org.plasma.sdo.xml.DefaultOptions;
import org.plasma.provisioning.Alias;
import org.plasma.provisioning.Body;
import org.plasma.provisioning.DataTypeRef;
import org.plasma.provisioning.Enumeration;
import org.plasma.provisioning.EnumerationConstraint;
import org.plasma.provisioning.EnumerationLiteral;
import org.plasma.provisioning.EnumerationRef;
import org.plasma.provisioning.Key;
import org.plasma.provisioning.KeyType;
import org.plasma.provisioning.Package;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.Property;
import org.plasma.provisioning.ClassRef;
import org.plasma.provisioning.Documentation;
import org.plasma.provisioning.DocumentationType;
import org.plasma.provisioning.NameUtils;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.TypeRef;
import org.plasma.provisioning.UniqueConstraint;
import org.plasma.provisioning.ValueConstraint;
import org.plasma.provisioning.VisibilityType;

import commonj.sdo.DataGraph;
import commonj.sdo.helper.XMLDocument;

public class OracleConverter implements SchemaConverter {
	private static Log log = LogFactory.getLog(
			OracleConverter.class); 
	private String[] schemaNames;
    protected String destNamespaceURI;
    private Model model;
    /** Maps URI qualified names to classes */
    protected Map<String, Class> classQualifiedNameMap = new HashMap<String, Class>();
    /** Maps URI qualified names to enumerations */
    protected Map<String, Enumeration> enumQualifiedNameMap = new HashMap<String, Enumeration>();
    
    /** 
     * Maps physical schema 
     * qualified primary key constraint names to
     * classes.
     */
    protected Map<String, Class> classQualifiedPriKeyConstrainatNameMap = new HashMap<String, Class>();
    /** 
     * Maps physical schema 
     * qualified primary key constraint names to
     * properties.
     */
    protected Map<String, Property> propertyQualifiedPriKeyConstrainatNameMap = new HashMap<String, Property>();
    /** maps classes to properties */
    protected Map<Class, Map<String, Property>> classPropertyMap = new HashMap<Class, Map<String, Property>>();
    /** maps properties to physical constraints */
    protected Map<Property, ConstraintInfo[]> constraintMap = new HashMap<Property, ConstraintInfo[]>();
    protected JDBCPojoDataAccessClient client = new JDBCPojoDataAccessClient();

	public OracleConverter(String[] schemaNames, 
			String destNamespaceURI) {
		this.schemaNames = schemaNames;
		this.destNamespaceURI = destNamespaceURI;
	}
	
	@Override
	public Model buildModel() {
		
		model = new Model();
    	model.setName(this.schemaNames[0].toLowerCase());
        model.setId(UUID.randomUUID().toString());
        
        if (this.schemaNames.length == 1) {
            model.setUri(this.destNamespaceURI);
    		Alias alias = new Alias();
        	alias.setPhysicalName(this.schemaNames[0]);
        	model.setAlias(alias);
        }

    	for (String schema : this.schemaNames) {
    		
    		Package pkg = null;;
            if (this.schemaNames.length > 1) {
            	pkg = new Package();	
	    		pkg.setName(schema.toLowerCase());
	    		pkg.setId(UUID.randomUUID().toString());
	    		pkg.setUri(this.destNamespaceURI + "/" + schema.toLowerCase());        	
	    		Alias alias = new Alias();
	        	alias.setPhysicalName(schema);
	        	pkg.setAlias(alias);
	    		model.getPackages().add(pkg);
    	    }
    	    else
    	    	pkg = model;

			log.info("loading schema '" + schema + "'");
			
			for (String tableName : getTableNames(schema)) {
				log.info("converting table '" + tableName + "'");
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
				
				for (Column column : table.getColumn()) {
					log.debug("\tcolumn '" + column.getColumnName() + "'");
					ConstraintInfo[] constraints = findConstraints(column, table);
					ColumnComment[] comments = findComments(column, table);
					Property prop = buildProperty(pkg, clss, column, 
						constraints, comments);
					clss.getProperties().add(prop);					
					this.constraintMap.put(prop, constraints);
				}
			}
		}
    	
    	// process referential constraints
    	for (Class clss : this.classQualifiedNameMap.values()) {
    		for (Property prop : clss.getProperties()) {
    			ConstraintInfo[] infos = this.constraintMap.get(prop);
    			if (infos == null)
    				continue;
				for (ConstraintInfo info : infos) {
					ConstraintType consType = ConstraintType.valueOf(info.getConstraint().getConstraintType());
					switch (consType) {
					case R:
						String qualifiedName = info.getConstraint().getRefOwner()
						    + "." + info.getConstraint().getRefConstraintName();
						Class targetClass = this.classQualifiedPriKeyConstrainatNameMap.get(qualifiedName);
						Property targetProp = this.propertyQualifiedPriKeyConstrainatNameMap.get(qualifiedName);
				    	TypeRef typeRef = new ClassRef();
				    	typeRef.setName(targetClass.getName());
				    	typeRef.setUri(targetClass.getUri());
				        prop.setType(typeRef); 
				        //Alias alias = new Alias();
				        //alias.setPhysicalName(targetProp.getAlias().getPhysicalName());
				        //prop.setAlias(alias);
						break; 
					default:
						break;
					}
				}
    		}
    	}    	
	
		return model;
	}	
	
	private ColumnComment[] findComments(Column column, 
			Table table) {
		ColumnComment[] result = new ColumnComment[0];
		if (table.getColumnComment() != null) {
			List<ColumnComment> list = new ArrayList<ColumnComment>();
			for (ColumnComment comment : table.getColumnComment()) {
				if (comment.getColumnName().equalsIgnoreCase(column.getColumnName())) {
					list.add(comment);
				}
			}
			if (list.size() > 0) {
			    result = new ColumnComment[list.size()];
			    list.toArray(result);
			}
		}
		return result;
    }

	private ConstraintInfo[] findConstraints(Column column, 
			Table table) {
		return findConstraints(column, table.getConstraint(),
				table.getColumnConstraint());
	}
	
	class ConstraintInfo {
		private Constraint constraint;
		private ColumnConstraint columnConstraint;
		public ConstraintInfo(Constraint constraint,
				ColumnConstraint columnConstraint) {
			super();
			this.constraint = constraint;
			this.columnConstraint = columnConstraint;
		}
		public Constraint getConstraint() {
			return constraint;
		}
		public ColumnConstraint getColumnConstraint() {
			return columnConstraint;
		}		
	}
	
	private ConstraintInfo[] findConstraints(Column column, 
			Constraint[] constraints, ColumnConstraint[] columnConstraints) {		
		List<ConstraintInfo> list = new ArrayList<ConstraintInfo>();
		if (columnConstraints != null && constraints != null)
		for (ColumnConstraint colConst : columnConstraints) {
			if (!colConst.getColumnName().equals(column.getColumnName()))
				continue;
			list.add(new ConstraintInfo(
					getConstraint(colConst, constraints),
					colConst));
		}
		ConstraintInfo[] result = new ConstraintInfo[list.size()];
		list.toArray(result);
		return result;
	}
	
	private Constraint getConstraint(ColumnConstraint constraint,
			Constraint[] constraints)
	{
		if (constraints != null)
		for (Constraint c : constraints)
			if (c.getConstraintName().equals(constraint.getConstraintName())) 
			    return c;
		throw new IllegalArgumentException("no constraint found for given constraint name '"
				+ constraint.getConstraintName() + "'");
	}
			
	private Table getTableGraph(String schema, String tableName)
	{
		QTable table = QTable.newQuery();		
		table.select(table.wildcard());
		table.select(table.column().wildcard());
		table.select(table.tableComment().wildcard());
		table.select(table.columnComment().wildcard());
		table.select(table.constraint().wildcard());
		table.select(table.columnConstraint().wildcard());
		table.where(table.owner().eq(schema)
			 .and(table.tableName().eq(tableName)));
        DataGraph[] results = this.client.find(table);
		return (Table)results[0].getRootObject();
	}
	
	private List<String> getTableNames(String schema)
	{
		List<String> result = new ArrayList<String>();
		QTable tables = QTable.newQuery();		
		tables.select(tables.tableName());
		tables.where(tables.owner().eq(schema));
		
		for (DataGraph graph : client.find(tables)) {
			Table table = (Table)graph.getRootObject();
			result.add(table.getTableName());
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
    	
    	if (table.getTableComment() != null)
    		for (TableComment comment : table.getTableComment()) {
    			if (comment.getComments() == null || comment.getComments().trim().length() == 0)
    			    continue;
    	        Documentation documentation = new Documentation();
    			documentation.setType(DocumentationType.DEFINITION);
    			Body body = new Body();
    			body.setValue(comment.getComments());
    			documentation.setBody(body);
    			clss.getDocumentations().add(documentation);
    		}
    	
    	return clss;
    }	
   
    public Property buildProperty(Package pkg, Class clss, Column column,
    		ConstraintInfo[] constraints, ColumnComment[] comments) {
        Property property = new Property();
        property.setId(UUID.randomUUID().toString());
        property.setVisibility(VisibilityType.PUBLIC); 
        property.setName(NameUtils.firstToLowerCase(
        		NameUtils.toCamelCase(column.getColumnName())));

    	Alias alias = new Alias();
    	alias.setPhysicalName(column.getColumnName());
    	property.setAlias(alias);
    	
        // nullable
    	if ("Y".equalsIgnoreCase(column.getNullable()))
    		property.setNullable(true);
    	else
    		property.setNullable(false);
    	
    	SysDataType oracleType = SysDataType.valueOf(column.getDataType());
    	DataType sdoType = mapType(oracleType, column.getDataLength(),
    			column.getDataPrecision(), column.getDataScale());
    	DataTypeRef dataTypeRef = new DataTypeRef();
        dataTypeRef.setName(sdoType.name());
        dataTypeRef.setUri(PlasmaConfig.getInstance().getSDO().getDefaultNamespace().getUri());
        property.setType(dataTypeRef);  
        
        ValueConstraint valueConstraint = buildValueConstraint(oracleType, column.getDataLength(),
    		column.getDataPrecision(), column.getDataScale());
        if (valueConstraint != null) 
        	property.setValueConstraint(valueConstraint);

        for (ConstraintInfo info : constraints) {
			ConstraintType consType = ConstraintType.valueOf(info.getConstraint().getConstraintType());
			switch (consType) {
			case P: // pk
				Key prikey = new Key();
				prikey.setType(KeyType.PRIMARY);
				property.setKey(prikey);
				String qualifiedName = pkg.getAlias().getPhysicalName()
						+ "." + info.getConstraint().getConstraintName();
				this.classQualifiedPriKeyConstrainatNameMap.put(
					qualifiedName, clss);
				this.propertyQualifiedPriKeyConstrainatNameMap.put(
					qualifiedName, property);				
				UniqueConstraint uniqueConstraint = new UniqueConstraint();
				uniqueConstraint.setGroup(info.getConstraint().getConstraintName());
				property.setUniqueConstraint(uniqueConstraint);
				break; 
			case R: // referential
				// deal with on second pass after all tables processed 
				break; 
			case U: // unique
				uniqueConstraint = new UniqueConstraint();
				uniqueConstraint.setGroup(info.getConstraint().getConstraintName());
				property.setUniqueConstraint(uniqueConstraint);
				break; 
			case C: // check
				String condition = info.getConstraint().getSearchCondition();
				if (condition != null) {
					String[] literals = parseLiterals(column, condition);
					if (literals != null) {
						Enumeration enm = buildEnumeration(clss, property,
					    	literals);
						model.getEnumerations().add(enm);
						this.enumQualifiedNameMap.put(enm.getUri() + "#" + enm.getName(), enm);
						EnumerationRef enumRef = new EnumerationRef();
						enumRef.setName(enm.getName());
						enumRef.setUri(enm.getUri());	
						EnumerationConstraint enumConst = new EnumerationConstraint();
						enumConst.setValue(enumRef);
						property.setEnumerationConstraint(enumConst);
					}
				}
				break; 
			default:
				break;
			}
        }
        
		for (ColumnComment comment : comments) {
			if (comment.getComments() == null || comment.getComments().trim().length() == 0)
		        continue;
	        Documentation documentation = new Documentation();
			documentation.setType(DocumentationType.DEFINITION);
			Body body = new Body();
			body.setValue(comment.getComments());
			documentation.setBody(body);
			property.getDocumentations().add(documentation);
		}
        
        return property;
    }
    
    private Enumeration buildEnumeration(Class clss, Property property,
    		String[] literals) {
    	Enumeration enm = new Enumeration();
    	enm.setId(UUID.randomUUID().toString());
    	String baseName = NameUtils.firstToUpperCase(
    			property.getName()) + "Values";    	
    	String suffix = getNameSequence(clss.getUri(), baseName, this.enumQualifiedNameMap);
    	enm.setName(baseName + suffix);
    	enm.setUri(clss.getUri());
        Documentation documentation = new Documentation();
		documentation.setType(DocumentationType.DEFINITION);
		Body body = new Body();
		body.setValue("This enumeration was derived from a check " 
		    + "constraint on column "
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
    private String[] parseLiterals(Column column, String condition) {
    	String[] literals = null;
    	
    	int rightIndex = condition.indexOf("(");
		int leftIndex = condition.indexOf(")");
		if (rightIndex >= 0 && leftIndex > 0 && leftIndex > rightIndex) {
			String[] conditionTokens = condition.split(" "); 
			if (findTokenIgnoreCase(column.getColumnName(), conditionTokens) &&
				findTokenIgnoreCase("IN", conditionTokens)) {
				String literalSubstr = condition.substring(rightIndex, leftIndex);
				literals = literalSubstr.split(",");
				for (int i = 0; i < literals.length; i++) {
					int rightAposIndex = literals[i].indexOf("'");
					int leftAposIndex = literals[i].lastIndexOf("'");
					if (rightAposIndex >= 0 && leftAposIndex > 0) {
					    literals[i] = literals[i].substring(rightAposIndex+1, leftAposIndex);
					}
					literals[i] = literals[i].trim();
				}
			}
			else
				log.warn("expected constraint search condition on column, "
					+ column.getColumnName());
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
    
    private ValueConstraint buildValueConstraint(SysDataType oracleType, int dataLength,
    		int dataPrecision, int dataScale) {
        ValueConstraint constraint = null;
    	switch (oracleType) {
    	case CHAR:	
    	case VARCHAR2:		
    	case VARCHAR:			
    	case NCHAR:			
    	case NVARCHAR2:		
    	case CLOB:			
    	case NCLOB:	
    		if (dataLength > 0) {
    			constraint = new ValueConstraint();
    			constraint.setMaxLength(String.valueOf(dataLength));
    		}
    		break;
    	case NUMBER:
    		if (dataPrecision > 0) {
    			constraint = new ValueConstraint();
    			constraint.setTotalDigits(String.valueOf(dataPrecision));
    			if (dataScale > 0)
    				constraint.setFractionDigits(String.valueOf(dataScale));
    		} 
    		break;
    	case LONG:			
    	case BINARY__FLOAT:	
    	case BINARY__DOUBLE:	
    	case BLOB:			
    	case BFILE:			
    	case RAW:				
    	case DATE:			
    	case TIMESTAMP:	
    		break;
    	default:
    		throw new ProvisioningException("unknown datatype, "
    				+ oracleType.name());
    	}
    	return constraint;
    }    
    
    private DataType mapType(SysDataType oracleType, int dataLength,
    		int dataPrecision, int dataScale) {
    	switch (oracleType) {
    	case CHAR:	
    		return DataType.Character;
    	case VARCHAR2:		
    	case VARCHAR:			
    	case NCHAR:			
    	case NVARCHAR2:		
    	case CLOB:			
    	case NCLOB:			
    		return DataType.String;
    	case NUMBER:
    		if (dataPrecision > 0) {
    			if (dataScale == 0) { // Oracle does not give us a scale, must assume
    				return mapIntegralType(dataPrecision);
    	    	}
    			else {
    				return mapFloatingPointType(dataPrecision, dataScale);
    			}
    		} 
    		else // precision not specified, can't determine floating point type by magic
    		    return DataType.Long;    		
    	case LONG:			
    	case BINARY__FLOAT:	
    	case BINARY__DOUBLE:	
    	case BLOB:			
    	case BFILE:			
    	case RAW:				
    		return DataType.Bytes;
    	case DATE:			
    		return DataType.Date;
    	case TIMESTAMP:		
    		return DataType.DateTime;
    	default:
    		log.warn("unknown datatype '"
    				+ oracleType.name() + "' - using String");
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
