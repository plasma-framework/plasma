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
package org.plasma.provisioning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.DataAccessProviderName;
import org.plasma.config.NonExistantNamespaceException;
import org.plasma.config.PlasmaConfig;
import org.plasma.metamodel.Alias;
import org.plasma.metamodel.Body;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.ConcurentDataFlavor;
import org.plasma.metamodel.ConcurrencyType;
import org.plasma.metamodel.Concurrent;
import org.plasma.metamodel.DataTypeRef;
import org.plasma.metamodel.Derivation;
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
import org.plasma.metamodel.PackageRef;
import org.plasma.metamodel.Property;
import org.plasma.metamodel.Sort;
import org.plasma.metamodel.TypeRef;
import org.plasma.metamodel.ValueConstraint;
import org.plasma.metamodel.VisibilityType;
import org.plasma.metamodel.XmlNodeType;
import org.plasma.metamodel.XmlProperty;
import org.plasma.query.Query;
import org.plasma.query.collector.PropertySelection;
import org.plasma.query.collector.PropertySelectionCollector;
import org.plasma.sdo.DataType;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.repository.Comment;
import org.plasma.sdo.repository.Namespace;
import org.plasma.sdo.repository.PlasmaRepository;

import commonj.sdo.Type;

/**
 * Assembles a provisioning model based on the configured SDO 
 * environment iterating through the configured SDO name-spaces
 * or taking a specific name-space list or various of the
 * constructor arguments. 
 */
public class MetamodelAssembler {
	private static final String DERIVED_ARTIFACT_URI_PREFIX = "http://derived-artifact/";
	private static Log log = LogFactory.getLog(MetamodelAssembler.class);
	private Model model;
	private List<Namespace> namespaces;
	private Map<String, Package> packageMap = new HashMap<String, Package>();
	private Map<String, Class> classMap = new HashMap<String, Class>();
	private Map<String, Enumeration> enumerationMap = new HashMap<String, Enumeration>();
	private String destNamespaceURI;
	private DataAccessProviderName serviceName;
	
	/**
	 * Constructs a provisioning model based
	 * on all configured SDO namespaces.
	 */
	public MetamodelAssembler() {
		this(PlasmaRepository.getInstance().getAllNamespaces());
	}
	
	/**
	 * Constructs a provisioning model based
	 * on the given provider. 
	 * @param serviceName the service provider
	 */
	public MetamodelAssembler(DataAccessProviderName serviceName) {
		this.serviceName = serviceName;
		construct(PlasmaRepository.getInstance().getAllNamespaces());
	}
	
	/**
	 * Constructs a provisioning model based
	 * on the given list of SDO name spaces. 
	 * @param namespaces the SDO name spaces
	 */
	public MetamodelAssembler(List<Namespace> namespaces) {
		construct(PlasmaRepository.getInstance().getAllNamespaces());
	}
	
	/**
	 * Constructs a provisioning model based
	 * on the given query.
	 * @param query the query
	 * @param destNamespaceURI the destination namespace URI
	 * @param destNamespacePrefix the destination namespace prefix
	 */
    public MetamodelAssembler(Query query, String destNamespaceURI,
    		String destNamespacePrefix) {
		super();
		construct(query, destNamespaceURI, destNamespacePrefix);
    }
    
	private void construct(List<Namespace> namespaces) {
		this.namespaces = namespaces;
		
		List<Namespace> namespacesToProcess = new ArrayList<Namespace>();
    	for (Namespace namespace : this.namespaces) {
    		if (this.serviceName != null) {
    			try {
    			    PlasmaConfig.getInstance().getProvisioningByNamespaceURI(this.serviceName, 
    			    		namespace.getUri());
	    		}
	    		catch (NonExistantNamespaceException e) {
	        		log.debug("ignoring non "+this.serviceName.name()+" namespace: " + namespace.getUri());
	    			continue; 
	    		}
	        }
    		namespacesToProcess.add(namespace);
    	}
    	
    	if (namespacesToProcess.size() == 0)
    		throw new ProvisioningException("no namespaces to process");
    	
    	if (namespacesToProcess.size() > 1) {
		    this.model = new Model();
		    this.model.setId(UUID.randomUUID().toString());
		    this.model.setName("derived model");
		    this.model.setUri(DERIVED_ARTIFACT_URI_PREFIX + UUID.randomUUID().toString());
			Documentation documentation = new Documentation();
			documentation.setType(DocumentationType.DEFINITION);
			Body body = new Body();
			body.setValue("derived model");
			documentation.setBody(body);
			this.model.getDocumentations().add(documentation);		    
    	}
    	
		// create and map packages, classes
    	for (Namespace namespace : namespacesToProcess) {
			if (log.isDebugEnabled())
    		log.debug("processing namespace: " + namespace.getUri());
    		
        	List<commonj.sdo.Type> types = PlasmaTypeHelper.INSTANCE.getTypes(namespace.getUri());
        	for (commonj.sdo.Type type : types) {
        		PlasmaType plasmaType = (PlasmaType)type;
        		        		
        		Package pkg = this.packageMap.get(plasmaType.getURI());
        		if (pkg == null) {
        			if (namespacesToProcess.size() > 1) {
        			    pkg = createPackage(namespace, plasmaType, false);        			
        		        this.model.getPackages().add(pkg);
        			}
        			else {
        			    pkg = createPackage(namespace, plasmaType, true);        			
        		        this.model = (Model)pkg;
        			}
        		    this.packageMap.put(plasmaType.getURI(), pkg);
        		}
        		String qualifiedName = plasmaType.getURI() + "#" + plasmaType.getName();
        		Class clss = this.classMap.get(qualifiedName);
        		if (clss == null) {
        			if (log.isDebugEnabled())
            		log.debug("processing type: " + qualifiedName);
        			clss = createClass(pkg, plasmaType);
        			assert(clss.getUri().equals(pkg.getUri()));
        			this.classMap.put(qualifiedName, clss);    
        			
        			createEnumerations(pkg, clss, plasmaType);
        		}        		
        	}
    	}       	   	

    	// create fields
    	for (Class clss : classMap.values()) {
    		PlasmaType sdoType = (PlasmaType)PlasmaTypeHelper.INSTANCE.getType(clss.getUri(), clss.getName());
    		Package pkg = this.packageMap.get(sdoType.getURI());
		    List<commonj.sdo.Property> properties = sdoType.getDeclaredProperties();
			if (log.isDebugEnabled())
				log.debug("adding "+properties.size()+" properties class: " + clss.getUri() + "#" + clss.getName());				
		    createFields(pkg, clss, properties);
    	}    	
	}
	
	/**
	 * Constructs a provisioning model based on the given PlasmaQuery#8482; query. Property
	 * ordering or sequence is imposed on all properties based on the query ordering.  
	 * @param query the PlasmaQuery#8482; query
	 * @param destNamespaceURI
	 * @param destNamespacePrefix
	 */
    private void construct(Query query, String destNamespaceURI,
    		String destNamespacePrefix) {
		if (destNamespaceURI == null || destNamespaceURI.trim().length() == 0)
			throw new IllegalArgumentException("expected argument 'destNamespaceURI'");
		this.destNamespaceURI = destNamespaceURI;
		 
		this.model = new Model();    	
		this.model.setId(UUID.randomUUID().toString());
		this.model.setUri(destNamespaceURI);
		this.model.setName(query.getName());  
		Derivation derivation = new Derivation();
		PackageRef packageRef = new PackageRef();
		packageRef.setName(query.getName());
		packageRef.setUri(query.getFromClause().getUri());
		derivation.setPackageSupplier(packageRef);
		this.model.setDerivation(derivation);
		Documentation documentation = new Documentation();
		documentation.setType(DocumentationType.DEFINITION);
		Body body = new Body();
		body.setValue("derived model");
		documentation.setBody(body);
		this.model.getDocumentations().add(documentation);		    
		
        
        // collect types/properties from the Select within the
        // given query, then iterate over collected types
		PlasmaType rootType = (PlasmaType)PlasmaTypeHelper.INSTANCE.getType(query.getFromClause().getUri(), 
        	query.getFromClause().getName());
        ClassRef rootClass = new ClassRef();
        rootClass.setName(rootType.getName());
        rootClass.setUri(rootType.getURI());
        this.model.setRootClass(rootClass);
        
        PropertySelectionCollector collector = new PropertySelectionCollector(
                (org.plasma.query.model.Select)query.getSelectClause(), rootType);
        Map<Type, List<String>> queryPropertyMap = collector.getResult();
        
        // create entities first pass
        Iterator<Type> iter = queryPropertyMap.keySet().iterator();
        while (iter.hasNext()) {
        	PlasmaType sdoType = (PlasmaType)iter.next();
            Class clss = createClass(model, sdoType);
            classMap.put(destNamespaceURI + "#" + clss.getName(), clss);
        }
        
        // create "hollow" base types if needed
        iter = queryPropertyMap.keySet().iterator();
        while (iter.hasNext()) {
        	PlasmaType sdoType = (PlasmaType)iter.next();
        	constructBaseTypes(query, sdoType,
        			collector, queryPropertyMap);
        }

        // add properties
        Iterator<Type> iter2 = queryPropertyMap.keySet().iterator();
        while (iter2.hasNext()) {
        	PlasmaType sdoType = (PlasmaType)iter2.next();
        	List<String> list = queryPropertyMap.get(sdoType);
            Class clss = classMap.get(destNamespaceURI + "#" + sdoType.getName());            
            
            // add datatype properties in query list
            for (commonj.sdo.Property p : sdoType.getDeclaredProperties()) {
            	PlasmaProperty plasmaProperty = (PlasmaProperty)p;
            	if (!plasmaProperty.getType().isDataType())
            		continue;
                if (list.contains(plasmaProperty.getName())) {
                	
                	Property field = createDataProperty(this.model, clss, plasmaProperty); 
        			Sort sequence = new Sort();
        			sequence.setKey(String.valueOf(list.indexOf(plasmaProperty.getName())));
        			field.setSort(sequence);
                	
                	clss.getProperties().add(field);                	
                }
            }  

            // add reference properties in query list
            for (commonj.sdo.Property p2 : sdoType.getDeclaredProperties()) {
            	PlasmaProperty plasmaProperty = (PlasmaProperty)p2;
            	if (plasmaProperty.getType().isDataType())
            		continue; // only reference props
            	if (!list.contains(plasmaProperty.getName())) 
            		continue;
			    PlasmaProperty oppositeProperty = (PlasmaProperty)plasmaProperty.getOpposite();           	
            	Property field = null;
        	    if (oppositeProperty != null) {
               		PlasmaType oppositeType = (PlasmaType)plasmaProperty.getType();
        	    	// Is the opposite is found in the query??
        	    	if (collector.hasProperty(oppositeType, oppositeProperty)) {
			            field = createReferenceProperty(this.model, clss, plasmaProperty,        			            		
			            		oppositeProperty); 
        	    	}
        	    	else {
        	    		if (plasmaProperty.isMany())
        	    		    field = createReferenceProperty(this.model, clss, plasmaProperty, oppositeProperty);
        	    		else
        	    			field = createReferenceProperty(this.model, clss, plasmaProperty, null);
        	    	}
        	    }
        	    else {
        	    	field = createReferenceProperty(this.model, clss, plasmaProperty, null);   
        	    }

    			Sort sequence = new Sort();
    			sequence.setKey(String.valueOf(list.indexOf(plasmaProperty.getName())));
    			field.setSort(sequence);
            	
            	clss.getProperties().add(field);                	
            }
            
            constructImplicitDatatypeProperties(query, sdoType, clss, list);
            
            // Note: must have singular opposite properties for RDBMS service
            // note these are visibility 'private'. 
            constructImplicitReferenceProperties(query, sdoType, clss, collector, list);
        
        } // type
    }

	public Model getModel() {
		return this.model;
	}	
	
    // For every type included in the query, include all base types
    // and their properties
	private void constructBaseTypes(Query query, Type sdoType,
			PropertySelection collector,
			Map<Type, List<String>> queryPropertyMap)
	{
        for (Type base : sdoType.getBaseTypes()) {
            PlasmaType sdoBaseType = (PlasmaType)PlasmaTypeHelper.INSTANCE.getType(
            		base.getURI(), base.getName());
        	Class existing = classMap.get(destNamespaceURI + "#" + base.getName());  
        	if (existing == null) {
                Class baseClss = createClass(model, (PlasmaType)base);
                classMap.put(destNamespaceURI + "#" + baseClss.getName(), baseClss);
                List<String> baseList = queryPropertyMap.get(base.getURI() + "#" + base.getName());
                constructImplicitDatatypeProperties(query, sdoBaseType, baseClss, baseList);
                constructImplicitReferenceProperties(query, sdoBaseType, baseClss, collector, baseList);
    		    //List<commonj.sdo.Property> baseProperties = base.getDeclaredProperties();
    		    //createFields(model, baseClss, baseProperties);
        	}
            constructBaseTypes (query, sdoBaseType, collector, queryPropertyMap); // recurse
        }
	}

    /*
     *  Add datatype properties not in query list but required to 
     *  facilitate completeness of down stream models. E.g. an
     *  XML Schema which we want to use to 'define' a dynamic
     *  SDO namespace/model must know about primary
     *  key fields. And other mandatory data fields??
     */
	private void constructImplicitDatatypeProperties(Query query, PlasmaType sdoType, 
			Class clss, List<String> list)
	{
        for (commonj.sdo.Property p : sdoType.getDeclaredProperties()) {
        	PlasmaProperty plasmaProperty = (PlasmaProperty)p;
        	if (!plasmaProperty.getType().isDataType())
        		continue;
            if (list == null || !list.contains(plasmaProperty.getName())) {
            	// Automatically add all key and concurency fields even though not specified in query
            	//, but make private and NOT required.
            	// FIXME: If we knew more at a profile level about the key,
            	// then more intelligent options might be possible
                if (plasmaProperty.getKey() != null || plasmaProperty.getConcurrent() != null) {
                	Property field = createDataProperty(this.model, clss, plasmaProperty);
                	field.setVisibility(VisibilityType.PRIVATE);
                	field.setNullable(true);
                	
        			Sort sequence = new Sort();
        			sequence.setKey(String.valueOf(clss.getProperties().size()));
        			field.setSort(sequence);
                	
                	clss.getProperties().add(field);               	
                }
            }
        } 		
	}

	private void constructImplicitReferenceProperties(Query query, PlasmaType sdoType, 
			Class clss, PropertySelection collector, List<String> list)
	{
        for (commonj.sdo.Property p2 : sdoType.getDeclaredProperties()) {
        	PlasmaProperty plasmaProperty = (PlasmaProperty)p2;
        	if (plasmaProperty.getType().isDataType())
        		continue; // only reference props
        	if (list != null && list.contains(plasmaProperty.getName()))
        		continue;
		    PlasmaProperty oppositeProperty = (PlasmaProperty)plasmaProperty.getOpposite();
        	
        	/*
        	// FIXME: what a bout mandatory ref props ????
        	if (oppositeProperty != null && !plasmaProperty.isNullable()) {
    		    Property property = createReferenceProperty(this.model, clss, 
    		    		plasmaProperty, oppositeProperty);
    		    property.setVisibility(VisibilityType.PRIVATE);
            	
    			Sort sequence = new Sort();
    			sequence.setKey(String.valueOf(clss.getProperties().size()));
    			property.setSort(sequence);
    		    
            	clss.getProperties().add(property);  
        	}
        	*/
        	       	
        	// Property is NOT directly referenced in the query
        	// and it has an opposite and the opposite IS referenced
        	// and this property is singular, create a private reference
        	// property linked to the opposite. Such a singular property
        	// will be required for RDBMS service meta data definitions
        	// as a collection property must "map to" a singular property
        	// linked to an RDBMS column.
        	if (oppositeProperty != null && !plasmaProperty.isMany()) {
        		PlasmaType oppositeType = (PlasmaType)plasmaProperty.getType();
        		if (collector.hasProperty(oppositeType, oppositeProperty))
        		{
        			String oppositeQualName = this.destNamespaceURI + "#" + oppositeType.getName();
        		    Class oppositeClass = classMap.get(oppositeQualName); 
        		    if (oppositeClass == null)
        		    	throw new IllegalStateException("could not find class, "
        		    		+ oppositeQualName);
        		    Property property = createReferenceProperty(this.model, clss, 
        		    		plasmaProperty, oppositeProperty);
        		    property.setVisibility(VisibilityType.PRIVATE);
                	
        			Sort sequence = new Sort();
        			sequence.setKey(String.valueOf(clss.getProperties().size()));
        			property.setSort(sequence);
        		    
                	clss.getProperties().add(property);  
        		}
        	}
        }		
	}
	
	private void createFields(Package pkg, Class clss, List<commonj.sdo.Property> properties)
	{
		for (commonj.sdo.Property prop : properties) {
			PlasmaProperty plasmaProperty = (PlasmaProperty)prop;
			if (plasmaProperty.getPhysicalName() == null && !plasmaProperty.isMany()) {
				log.warn("no physical name found for singular property, "
						+ plasmaProperty.getContainingType().getURI() + "#"
						+ plasmaProperty.getContainingType().getName() + "."
						+ plasmaProperty.getName());
			}	
			Property field = null;
			if (plasmaProperty.getType().isDataType()) {
			    field = createDataProperty(pkg, clss, plasmaProperty);
				if (log.isDebugEnabled())
					log.debug("adding data property " + clss.getUri() + "#" + clss.getName() + "." + field.getName());				
			}
			else {
				PlasmaProperty oppositeProperty = (PlasmaProperty)plasmaProperty.getOpposite();
				field = createReferenceProperty(pkg, clss, plasmaProperty, oppositeProperty);
				if (log.isDebugEnabled())
					log.debug("adding reference property " + clss.getUri() + "#" + clss.getName() + "." + field.getName());				
			}
			clss.getProperties().add(field);
			
		}
	}

	private void createEnumerations(Package pkg, Class clss, PlasmaType sdoType)
	{
	    List<commonj.sdo.Property> properties = sdoType.getDeclaredProperties();
		for (commonj.sdo.Property prop : properties) {
			PlasmaProperty plasmaProperty = (PlasmaProperty)prop;
			if (plasmaProperty.getPhysicalName() == null) {
				if (!plasmaProperty.isMany())
					log.warn("no physical name found for singular property, "
							+ plasmaProperty.getContainingType().getURI() + "#"
							+ plasmaProperty.getContainingType().getName() + "."
							+ plasmaProperty.getName());
				continue;
			}	
			if (!plasmaProperty.getType().isDataType())
				continue;
			if (plasmaProperty.getRestriction() == null)
				continue;
			
			// FIXME - assumes class URI and enum URI are same
			org.plasma.sdo.repository.Enumeration sdoEnumeration = plasmaProperty.getRestriction();
			String enumKey = clss.getUri() + "#" + sdoEnumeration.getName();
			Enumeration enumeration = this.enumerationMap.get(enumKey);
			if (enumeration == null) {
			    enumeration = createEnumeration(clss, sdoEnumeration);
			    pkg.getEnumerations().add(enumeration);
			    this.enumerationMap.put(enumKey, enumeration);
			}
		}
	}
	
	
	private Enumeration createEnumeration(Class clss, org.plasma.sdo.repository.Enumeration sdoEnum) {
		Enumeration enumeration = new Enumeration();
		enumeration.setName(sdoEnum.getName());
		enumeration.setUri(clss.getUri()); // FIXME - a bad assumption that these are same URI
		enumeration.setId(UUID.randomUUID().toString());
		if (sdoEnum.findAlias() != null) {
			org.plasma.sdo.Alias sdoAlias = sdoEnum.findAlias();
			Alias alias = new Alias();
			enumeration.setAlias(alias);			
			alias.setPhysicalName(sdoAlias.getPhysicalName());
			alias.setLocalName(sdoAlias.getLocalName());
			alias.setBusinessName(sdoAlias.getBusinessName());
		}				
		
		for (org.plasma.sdo.repository.EnumerationLiteral lit : sdoEnum.getOwnedLiteral()) {
			EnumerationLiteral literal = new EnumerationLiteral();
			literal.setName(lit.getName());
			literal.setValue(lit.getName());
			if (lit.findAlias() != null) {
				org.plasma.sdo.Alias sdoAlias = lit.findAlias();
				Alias alias = new Alias();
				literal.setAlias(alias);
				
				alias.setPhysicalName(sdoAlias.getPhysicalName());
				alias.setLocalName(sdoAlias.getLocalName());
				alias.setBusinessName(sdoAlias.getBusinessName());
			}				

			if (lit.getComments() != null && lit.getComments().size() > 0) {
				for (Comment comment : lit.getComments()) {
					Documentation documentation = new Documentation();
					documentation.setType(DocumentationType.DEFINITION);
					Body body = new Body();
					body.setValue(comment.getBody());
					documentation.setBody(body);
					literal.getDocumentations().add(documentation);
			    }
			}
			
			enumeration.getEnumerationLiterals().add(literal);
		}		
		
		return enumeration;
	}

	private Property createDataProperty(Package pkg, Class clss, PlasmaProperty sourceProperty) 
	{
		Property property = createProperty(pkg, clss, sourceProperty);
		if (!sourceProperty.getType().isDataType())			
			throw new IllegalArgumentException("given property is not a data property, " 
				+ clss.getUri() + "#" + clss.getName() + "." + sourceProperty.getName());

	    DataType sdoType = DataType.valueOf(sourceProperty.getType().getName());		    
	    TypeRef type = createDatatype(sdoType.name()); 
	    property.setType(type);
	    
		if (sourceProperty.getKey() != null) {
			org.plasma.sdo.Key sdoKey = sourceProperty.getKey();
			Key key = new Key();
			// target provisioning enum is JAXB generated so upper case
			key.setType(KeyType.valueOf(sdoKey.getType().name().toUpperCase()));
			property.setKey(key);
		}
		
		if (sourceProperty.getConcurrent() != null) {
			org.plasma.sdo.Concurrent sdoConcurrent = sourceProperty.getConcurrent();
			Concurrent conc = new Concurrent();
			// target provisioning enum is JAXB generated so upper case
			conc.setType(ConcurrencyType.valueOf(sdoConcurrent.getType().name().toUpperCase()));
			conc.setDataFlavor(ConcurentDataFlavor.valueOf(sdoConcurrent.getDataFlavor().name().toUpperCase()));
			property.setConcurrent(conc);
		}
		
		if (sourceProperty.getXmlProperty() != null) {
			org.plasma.sdo.XmlProperty sdoXmlProperty = sourceProperty.getXmlProperty();
			XmlProperty xmlProp = new XmlProperty();
			// target provisioning enum is JAXB generated so upper case
			xmlProp.setNodeType(XmlNodeType.valueOf(sdoXmlProperty.getNodeType().name().toUpperCase()));
			property.setXmlProperty(xmlProp);
		}
					
		if (sourceProperty.getValueConstraint() != null) {
			org.plasma.sdo.ValueConstraint sdoValConst = sourceProperty.getValueConstraint();
			
			ValueConstraint valueConstraint = new ValueConstraint();
			if (sdoValConst.getFractionDigits() != null)
			    valueConstraint.setFractionDigits(sdoValConst.getFractionDigits());
			if (sdoValConst.getMaxExclusive() != null)
			    valueConstraint.setMaxExclusive(sdoValConst.getMaxExclusive());
			if (sdoValConst.getMaxInclusive() != null)
			    valueConstraint.setMaxInclusive(sdoValConst.getMaxInclusive());
			if (sdoValConst.getMaxLength() != null)
			    valueConstraint.setMaxLength(sdoValConst.getMaxLength());
			if (sdoValConst.getMinExclusive() != null)
			    valueConstraint.setMinExclusive(sdoValConst.getMinExclusive());
			if (sdoValConst.getMinInclusive() != null)
			    valueConstraint.setMinInclusive(sdoValConst.getMinInclusive());
			if (sdoValConst.getMinLength() != null)
			    valueConstraint.setMinLength(sdoValConst.getMinLength());
			if (sdoValConst.getPattern() != null)
			    valueConstraint.setPattern(sdoValConst.getPattern());
			
			property.setValueConstraint(valueConstraint);
		}			
		
		org.plasma.sdo.repository.Enumeration sdoEnum = sourceProperty.getRestriction();
		if (sdoEnum != null) {
			// FIXME - really, same URI as class??
			String enumKey = clss.getUri() + "#" + sdoEnum.getName();
			Enumeration enumeration = this.enumerationMap.get(enumKey);
            if (enumeration == null) {
			    enumeration = createEnumeration(clss, sdoEnum);
			    pkg.getEnumerations().add(enumeration);
			    this.enumerationMap.put(enumKey, enumeration);
            }
            
            EnumerationConstraint enumConstraint = new EnumerationConstraint();
			EnumerationRef enumRef = new EnumerationRef();
			enumRef.setName(enumeration.getName());
			enumRef.setUri(enumeration.getUri());
			enumConstraint.setValue(enumRef);
			property.setEnumerationConstraint(enumConstraint);
		}		
		return property;
	}
	
	private Property createReferenceProperty(Package pkg, Class clss, PlasmaProperty sourceProperty,
			PlasmaProperty oppositeProperty) {
		Property property = createProperty(pkg, clss, sourceProperty);
		if (!sourceProperty.getType().isDataType()) {
			String qualifiedName = null;
			if (this.destNamespaceURI != null)
				qualifiedName = destNamespaceURI + "#" + sourceProperty.getType().getName();
			else
				qualifiedName = sourceProperty.getType().getURI() + "#" + sourceProperty.getType().getName();
			
			Class propertyClass = this.classMap.get(qualifiedName);
			if (propertyClass == null)
				throw new ProvisioningException("could not find class, "
						+ qualifiedName);
			
			ClassRef ref = new ClassRef();
			ref.setName(propertyClass.getName());
			ref.setUri(propertyClass.getUri());
		    property.setType(ref);			    
		    if (oppositeProperty != null)
		    	property.setOpposite(oppositeProperty.getName());
		}
		else
			throw new IllegalArgumentException("given property is not a reference property, " 
					+ clss.getUri() + "#" + clss.getName() + "." + sourceProperty.getName());
		return property;
	}
	
	private Property createProperty(Package pkg, Class clss, PlasmaProperty sourceProperty) {
		Property property = new Property();
		property.setId(UUID.randomUUID().toString());
		property.setName(sourceProperty.getName());
		property.setNullable(sourceProperty.isNullable());
		property.setReadOnly(sourceProperty.isReadOnly());
		property.setMany(sourceProperty.isMany());
		property.setVisibility(VisibilityType.PUBLIC);
		
		if (sourceProperty.getAlias() != null) {
			org.plasma.sdo.Alias sdoAlias = sourceProperty.getAlias();
			Alias alias = new Alias();
			property.setAlias(alias);
			
			alias.setPhysicalName(sdoAlias.getPhysicalName());
			alias.setLocalName(sdoAlias.getLocalName());
			alias.setBusinessName(sdoAlias.getBusinessName());
		}	
		
		if (sourceProperty.getSort() != null) {
			org.plasma.sdo.Sort sdoSort = sourceProperty.getSort();
			Sort sequence = new Sort();
			sequence.setKey(sdoSort.getKey());
			property.setSort(sequence);
		}
				
		if (sourceProperty.getDescription() != null && sourceProperty.getDescription().size() > 0) {
			for (Comment comment : sourceProperty.getDescription()) {
				Documentation documentation = new Documentation();
				documentation.setType(DocumentationType.DEFINITION);
				Body body = new Body();
				body.setValue(comment.getBody());
				documentation.setBody(body);
				property.getDocumentations().add(documentation);
		    }
		}
		
		return property;
	}	
	
	
	private DataTypeRef createDatatype(String name) {
		DataTypeRef dataTypeRef = new DataTypeRef();
		dataTypeRef.setName(name);
        dataTypeRef.setUri(PlasmaConfig.getInstance().getSDODataTypesNamespace().getUri());
	    return dataTypeRef;
	}
	
	private Package createPackage(Namespace namespace, PlasmaType type, boolean modelPackage) {
		Package pkg = null;
		if (!modelPackage)
			pkg = new Package();
		else 
			pkg = new Model();
		pkg.setName(namespace.getName());
		pkg.setId(UUID.randomUUID().toString());
				
		if (namespace.findAlias() != null) {
			org.plasma.sdo.Alias sdoAlias = namespace.findAlias();
			Alias alias = new Alias();
			pkg.setAlias(alias);
			
			alias.setPhysicalName(sdoAlias.getPhysicalName());
			alias.setLocalName(sdoAlias.getLocalName());
			alias.setBusinessName(sdoAlias.getBusinessName());
		}				
		
		for (Comment cmt : namespace.getComments()) {
			Documentation doc = new Documentation();
			doc.setType(DocumentationType.DEFINITION);
			Body body = new Body();
			body.setValue(cmt.getBody());
			doc.setBody(body);
			pkg.getDocumentations().add(doc);
		}
	    pkg.setUri(type.getURI());
	    return pkg;
	}
	
	private Class createClass(Package pkg, PlasmaType plasmaType) {
		Class clss = new Class();
		pkg.getClazzs().add(clss);
		clss.setId(UUID.randomUUID().toString());
		clss.setName(plasmaType.getName());
		clss.setUri(plasmaType.getURI());		
		clss.setAbstract(plasmaType.isAbstract());
		if (plasmaType.getPhysicalName() != null) {
			Alias alias = new Alias();
			clss.setAlias(alias);
			alias.setPhysicalName(plasmaType.getPhysicalName());
		}		
		if (plasmaType.getAlias() != null) {
			org.plasma.sdo.Alias sdoAlias = plasmaType.getAlias();
			Alias alias = new Alias();
			clss.setAlias(alias);			
			alias.setPhysicalName(sdoAlias.getPhysicalName());
			alias.setLocalName(sdoAlias.getLocalName());
			alias.setBusinessName(sdoAlias.getBusinessName());
		}				
		
		for (commonj.sdo.Type baseType : plasmaType.getBaseTypes()) {
		    ClassRef ref = new ClassRef();
		    ref.setName(baseType.getName());
		    ref.setUri(baseType.getURI());
		    clss.getSuperClasses().add(ref);
		}
		if (plasmaType.getDescription() != null && plasmaType.getDescription().size() > 0) {
			for (Comment comment : plasmaType.getDescription()) {
				Documentation documentation = new Documentation();
				documentation.setType(DocumentationType.DEFINITION);
				Body body = new Body();
				body.setValue(comment.getBody());
				documentation.setBody(body);
				clss.getDocumentations().add(documentation);
		    }
		}

		return clss;
	}

}
