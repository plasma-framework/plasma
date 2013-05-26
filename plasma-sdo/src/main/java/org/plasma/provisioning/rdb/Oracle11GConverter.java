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
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumn;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnComment;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableColumnConstraint;
import org.plasma.provisioning.rdb.oracle.g11.sys.Constraint;
import org.plasma.provisioning.rdb.oracle.g11.sys.ConstraintType;
import org.plasma.provisioning.rdb.oracle.g11.sys.SysDataType;
import org.plasma.provisioning.rdb.oracle.g11.sys.Table;
import org.plasma.provisioning.rdb.oracle.g11.sys.TableComment;
import org.plasma.provisioning.rdb.oracle.g11.sys.Version;
import org.plasma.provisioning.rdb.oracle.g11.sys.View;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumn;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumnComment;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewComment;
import org.plasma.provisioning.rdb.oracle.g11.sys.query.QTable;
import org.plasma.provisioning.rdb.oracle.g11.sys.query.QVersion;
import org.plasma.provisioning.rdb.oracle.g11.sys.query.QView;
import org.plasma.sdo.DataType;
import org.plasma.sdo.access.client.JDBCPojoDataAccessClient;
import org.plasma.sdo.helper.PlasmaXMLHelper;
import org.plasma.sdo.xml.DefaultOptions;
import org.plasma.provisioning.Alias;
import org.plasma.provisioning.Behavior;
import org.plasma.provisioning.BehaviorType;
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

public class Oracle11GConverter implements SchemaConverter {
	private static Log log = LogFactory.getLog(
			Oracle11GConverter.class); 
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

	public Oracle11GConverter(String[] schemaNames, 
			String destNamespaceURI) {
		this.schemaNames = schemaNames;
		this.destNamespaceURI = destNamespaceURI;
	}
	
	
	@Override
	public Model buildModel() {
		
		this.model = new Model();
		this.model.setId(UUID.randomUUID().toString());
        
        // if single schema
        if (this.schemaNames.length == 1) {
        	this.model.setUri(this.destNamespaceURI);
    		Alias alias = new Alias();
        	alias.setPhysicalName(this.schemaNames[0]);
        	this.model.setAlias(alias);
        }
        else {
        	this.model.setName("model");
        }

    	for (String schema : this.schemaNames) {
    		
			log.info("loading schema '" + schema + "'");
    		Package pkg = findPackage(schema);
    		loadTables(pkg, schema);
    		loadViews(pkg, schema);
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
					ConstraintType consType = ConstraintType.valueOf(info.getConstraint().getConstraintType());
					switch (consType) {
					case R:
						String qualifiedName = info.getConstraint().getRefOwner()
						    + "." + info.getConstraint().getRefConstraintName();
						Class targetClass = this.classQualifiedPriKeyConstrainatNameMap.get(qualifiedName);
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
				TableColumnComment[] comments = findComments(column, table);
				Property prop = buildProperty(pkg, clss, column, 
					constraints, comments);
				clss.getProperties().add(prop);					
				this.constraintMap.put(prop, constraints);
			}
		}		
	}
	
	private void loadViews(Package pkg, String schema)
	{
		for (String viewName : getViewNames(schema)) {
			log.info("loading view '" + viewName + "'");
			View view = getViewGraph(schema, viewName);
			if (log.isDebugEnabled())
				try {
				    log.debug(serializeGraph(view.getDataGraph()));
				}
			    catch (IOException e) {
			    	log.error(e.getMessage());
			    }
			
			Class clss = buildClass(pkg, view);
			pkg.getClazzs().add(clss);
			String key = pkg.getUri() + "#" + clss.getName();
			this.classQualifiedNameMap.put(key, clss);
			
			Behavior behavior = new Behavior();
			behavior.setName(view.getViewName() + "_create");
			behavior.setLanguage("SQL");
			behavior.setType(BehaviorType.CREATE);
			behavior.setValue(view.getText());
			clss.getBehaviors().add(behavior);
			
			for (ViewColumn column : view.getViewColumn()) {
				log.debug("\tloading column '" + column.getColumnName() + "'");
				ViewColumnComment[] comments = findComments(column, view);
				Property prop = buildProperty(pkg, clss, column, comments);
				clss.getProperties().add(prop);					
			}
		}		
	}
	
	
	private Package findPackage(String schema) {
		Package pkg = null;;
        if (this.schemaNames.length > 1) {
        	pkg = new Package();	
    		pkg.setName(schema.toLowerCase());
    		pkg.setId(UUID.randomUUID().toString());
    		pkg.setUri(this.destNamespaceURI + "/" + schema.toLowerCase());        	
    		Alias alias = new Alias();
        	alias.setPhysicalName(schema);
        	pkg.setAlias(alias);
        	this.model.getPackages().add(pkg);
	    }
	    else
	    	pkg = this.model;
        return pkg;
	}
	
	private TableColumnComment[] findComments(TableColumn column, 
			Table table) {
		TableColumnComment[] result = new TableColumnComment[0];
		if (table.getTableColumnComment() != null) {
			List<TableColumnComment> list = new ArrayList<TableColumnComment>();
			for (TableColumnComment comment : table.getTableColumnComment()) {
				if (comment.getColumnName().equalsIgnoreCase(column.getColumnName())) {
					list.add(comment);
				}
			}
			if (list.size() > 0) {
			    result = new TableColumnComment[list.size()];
			    list.toArray(result);
			}
		}
		return result;
    }
	
	private ViewColumnComment[] findComments(ViewColumn column, 
			View view) {
		ViewColumnComment[] result = new ViewColumnComment[0];
		if (view.getViewColumnComment() != null) {
			List<ViewColumnComment> list = new ArrayList<ViewColumnComment>();
			for (ViewColumnComment comment : view.getViewColumnComment()) {
				if (comment.getColumnName().equalsIgnoreCase(column.getColumnName())) {
					list.add(comment);
				}
			}
			if (list.size() > 0) {
			    result = new ViewColumnComment[list.size()];
			    list.toArray(result);
			}
		}
		return result;
    }

	private ConstraintInfo[] findConstraints(TableColumn column, 
			Table table) {
		return findConstraints(column, table.getConstraint(),
				table.getTableColumnConstraint());
	}
	
	class ConstraintInfo {
		private Constraint constraint;
		private TableColumnConstraint columnConstraint;
		public ConstraintInfo(Constraint constraint,
				TableColumnConstraint columnConstraint) {
			super();
			this.constraint = constraint;
			this.columnConstraint = columnConstraint;
		}
		public Constraint getConstraint() {
			return constraint;
		}
		public TableColumnConstraint getTableColumnConstraint() {
			return columnConstraint;
		}		
	}
	
	private ConstraintInfo[] findConstraints(TableColumn column, 
			Constraint[] constraints, TableColumnConstraint[] columnConstraints) {		
		List<ConstraintInfo> list = new ArrayList<ConstraintInfo>();
		if (columnConstraints != null && constraints != null)
		for (TableColumnConstraint colConst : columnConstraints) {
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
	
	private Constraint getConstraint(TableColumnConstraint constraint,
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
		table.select(table.tableColumn().wildcard());
		table.select(table.tableComment().wildcard());
		table.select(table.tableColumnComment().wildcard());
		table.select(table.constraint().wildcard());
		table.select(table.tableColumnConstraint().wildcard());
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

	private View getViewGraph(String schema, String viewName)
	{
		QView view = QView.newQuery();		
		view.select(view.wildcard())
		    .select(view.viewColumn().wildcard()) 
		    .select(view.viewComment().wildcard()) 
		    .select(view.viewColumnComment().wildcard());
		view.where(view.owner().eq(schema)
			 .and(view.viewName().eq(viewName)));
        DataGraph[] results = this.client.find(view);
		return (View)results[0].getRootObject();
	}
 
	private List<String> getViewNames(String schema)
	{
		List<String> result = new ArrayList<String>();
		QView query = QView.newQuery();		
		query.select(query.viewName());
		query.where(query.owner().eq(schema));		
		for (DataGraph graph : client.find(query)) {
			View view = (View)graph.getRootObject();
			result.add(view.getViewName());
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
    			body.setValue(filter(comment.getComments()));
    			documentation.setBody(body);
    			clss.getDocumentations().add(documentation);
    		}
    	
    	return clss;
    }	
    
    public Class buildClass(Package pkg, View view) {
    	Class clss = new Class();
    	clss.setId(UUID.randomUUID().toString());
    	clss.setName(NameUtils.firstToUpperCase(
    		NameUtils.toCamelCase(view.getViewName())));
    	clss.setUri(pkg.getUri());
    	Alias alias = new Alias();
    	alias.setPhysicalName(view.getViewName());
    	clss.setAlias(alias);
    	    	
    	if (view.getViewComment() != null)
    		for (ViewComment comment : view.getViewComment()) {
    			if (comment.getComments() == null || comment.getComments().trim().length() == 0)
    			    continue;
    	        Documentation documentation = new Documentation();
    			documentation.setType(DocumentationType.DEFINITION);
    			Body body = new Body();
    			body.setValue(filter(comment.getComments()));
    			documentation.setBody(body);
    			clss.getDocumentations().add(documentation);
    		}
    	return clss;
    }	
    
	/** 
	 * Getting bad unicode characters in DB comments
	 * which cause sax to barf. 
	 * "SAXParseException: An invalid XML character (Unicode: 0x92)" 
	 * No real solution other than
	 * escaping them or filtering them. JAXB does
	 * not support CDATA sections. There are alot
	 * of illegal unicode chars according to many sources, so filtering
	 * seems best.  
	 */    
    private String filter(String src) {
    	char[] chars = src.toCharArray();
    	char[] result = new char[chars.length];
    	int i = 0;
    	for (char ch : chars) {
    	    if (Character.isLetter(ch) || Character.isDigit(ch) || Character.isWhitespace(ch)) {
    	    	result[i] = ch;
    	    	i++;
    	    }
    	    else if (isPunctuation(ch) || isOtherAllowed(ch)) {
    	    	result[i] = ch;
    	    	i++;
    	    }
    	}
    	return new String(result, 0, i);
    }
    
    public static boolean isPunctuation(char c) {
        return c == ','
            || c == '.'
            || c == '!'
            || c == '?'
            || c == ':'
            || c == ';';
    }
    
    public static boolean isOtherAllowed(char c) {
        return c == '\''
            || c == '"'
            || c == '@'
            || c == '#'
            || c == '$'
            || c == '%'
            || c == '*'
            || c == '&'
            || c == '('
            || c == ')'
            || c == '-'
            || c == '+';
    }
   
    public Property buildProperty(Package pkg, Class clss, TableColumn column,
    		ConstraintInfo[] constraints, TableColumnComment[] comments) {
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
				break; 
			default:
				break;
			}
        }
        
		for (TableColumnComment comment : comments) {
			if (comment.getComments() == null || comment.getComments().trim().length() == 0)
		        continue;
	        Documentation documentation = new Documentation();
			documentation.setType(DocumentationType.DEFINITION);
			Body body = new Body();
			body.setValue(filter(comment.getComments()));
			documentation.setBody(body);
			property.getDocumentations().add(documentation);
		}
        
        return property;
    }
    
    public Property buildProperty(Package pkg, Class clss, 
    		ViewColumn column, ViewColumnComment[] comments) {
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

		for (ViewColumnComment comment : comments) {
			if (comment.getComments() == null || comment.getComments().trim().length() == 0)
		        continue;
	        Documentation documentation = new Documentation();
			documentation.setType(DocumentationType.DEFINITION);
			Body body = new Body();
			body.setValue(filter(comment.getComments()));
			documentation.setBody(body);
			property.getDocumentations().add(documentation);
		}

		return property;
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
    	case ROWID:				
    	case UROWID:
    		if (dataPrecision > 0)
    			log.warn("ignoring precision for datatype '" 
    		        + oracleType + "' when creating valud constraints");
    		if (dataScale > 0)
    			log.warn("ignoring scale for datatype '" 
    		        + oracleType + "' when creating valud constraints");
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
    	case LONG:	// Note: LONG is an Oracle data type for storing character data of variable length up to 2 Gigabytes in length		
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
    	case BINARY__FLOAT:	
    	case BINARY__DOUBLE:	
    	case BLOB:			
    	case BFILE:			
    	case RAW:				
    	case ROWID:				
    	case UROWID:
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
