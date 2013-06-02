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
package org.plasma.sdo.core;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.config.TypeBinding;
import org.plasma.sdo.Alias;
import org.plasma.sdo.AssociationPath;
import org.plasma.sdo.DataType;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.helper.DataConverter;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.profile.ConcurrencyType;
import org.plasma.sdo.profile.ConcurrentDataFlavor;
import org.plasma.sdo.profile.KeyType;
import org.plasma.sdo.repository.Class_;
import org.plasma.sdo.repository.Classifier;
import org.plasma.sdo.repository.Comment;
import org.plasma.sdo.repository.PlasmaRepository;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;

import fUML.Syntax.Classes.Kernel.VisibilityKind;

/**
 * A representation of the type of a {@link Property property} or 
 * a {@link DataObject data object}.
 */
public class CoreType implements PlasmaType {
    
    private static final long serialVersionUID = 1L;
    private static Log log = LogFactory.getLog(CoreType.class);
    private static final List<Type> EMPTY_TYPE_LIST = new ArrayList<Type>();
    
    // cached names, used/valued conditionally based on binding customizations
    // FIXME: cache these as instance properties
    private String name;
    private String physicalName;
    private String localName;
    private QName qname;
    private int qnameHashCode;
    
    private Classifier classifier;
    private List<Type> baseTypes;
    private List<Type> subTypes;
    
    /** 
     * Provides fast property lookup by logical 
     * name, physical name and other aliases 
     */
    private Map<String, PlasmaProperty> declaredPropertiesMap;
    private Map<PlasmaProperty, Object> instancePropertiesMap;
    /** 
     * Accommodates SDO API's where a unique property 
     * list is required as this is not derivable from the above
     * map which could have duplicate values.  
     */
    private List<Property> declaredPropertiesList;

    private String artifactURI; 
    private String namespaceURI; 
    @SuppressWarnings("unused")
    private CoreType() {}
    
    public CoreType(String uri, String typeName) {
        if (uri == null || uri.length() == 0)
            throw new IllegalArgumentException("namespace URI is a required argument");
        
        // ensure URI exists
        Namespace namespace = PlasmaConfig.getInstance().getSDONamespaceByURI(uri);          

        this.artifactURI = namespace.getArtifact();
        this.namespaceURI = namespace.getUri();
        
        this.name = typeName;
        this.qname = new QName(this.namespaceURI, this.name);
        this.qnameHashCode = this.qname.hashCode();
        
        String lookupName = this.name;
        
        TypeBinding binding = PlasmaConfig.getInstance().findTypeBinding(uri, typeName);
        if (binding != null) {
        	if (binding.getLogicalName() != null && binding.getLogicalName().trim().length() > 0) {
        	    this.name = binding.getLogicalName().trim();
        	    lookupName = binding.getType();
        	    // so we can find this by its new logical name
        	    PlasmaConfig.getInstance().remapTypeBinding(uri, binding);
        	}
        	if (binding.getPhysicalName() != null && binding.getPhysicalName().trim().length() > 0)
        	    this.physicalName = binding.getPhysicalName().trim();
        	if (binding.getLocalName() != null && binding.getLocalName().trim().length() > 0)
        	    this.localName = binding.getLocalName().trim();
        }
        
        // Since the repository knows nothing about SDO namespace URIs,
        // we use the config to map namespaces to Artifact URIs, 
        // which the repo does know about.
        String artifactQualifiedName = this.artifactURI
            + "#" + lookupName;
        
        org.modeldriven.fuml.repository.Classifier classifier = PlasmaRepository.getInstance().getClassifier(artifactQualifiedName);
        
        if (org.modeldriven.fuml.repository.Class_.class.isAssignableFrom(classifier.getClass()))        	
            this.classifier = new Class_(
        		(org.modeldriven.fuml.repository.Class_)classifier);
        else
        	this.classifier = new Classifier(classifier);
    }
    
    public int hashCode() {
    	return this.qnameHashCode;
    }
    
    public boolean equals(Object other) {
    	if (other != null) {
    		CoreType otherType = (CoreType)other;
    		return this.qnameHashCode == otherType.hashCode();
    	}
    	return false;	
    }
    
    public String toString() {
    	return this.qname.toString();
    }

    private void lazyLoadProperties() {
        
        this.declaredPropertiesMap = new HashMap<String, PlasmaProperty>();
        this.declaredPropertiesList = new ArrayList<Property>();
                
        this.instancePropertiesMap = new HashMap<PlasmaProperty, Object>();
 
        this.instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_STRING_PHYSICAL_NAME, 
        		this.classifier.getPhysicalName());                     

        //this.instanceProperties.put(PlasmaProperty.INSTANCE_PROPERTY_STRING_NAMESPACE_PHYSICAL_NAME, 
        //        getSDONamespacePhysicalName(this.classifier.getPackage()));                     
        this.instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_STRING_NAMESPACE_URI, 
        		this.classifier.getNamespaceURI());                     
        
        
        if (this.classifier.getComments() != null) {
            instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_DESCRIPTION, 
            		this.classifier.getComments());            
        } 
        
        // UUID, visibility and other instance properties on Type
        instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_STRING_UUID, 
        		UUID.randomUUID().toString());        
        VisibilityKind visibility = VisibilityKind.public_;
        if (this.classifier.getVisibility() != null)
        	visibility = this.classifier.getVisibility();
        instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_VISIBILITY, 
        		visibility);        
        
        List<org.modeldriven.fuml.repository.Property> properties = this.classifier.getDeclaredProperties();
                
        for (org.modeldriven.fuml.repository.Property prop : properties) 
        {                
            Type propertyType = null;
            // if a reference prop
            if (!prop.isDataType()) {
                
                org.modeldriven.fuml.repository.Property oppositeProperty = prop.getOpposite();
                if (oppositeProperty != null) {
                	org.modeldriven.fuml.repository.Class_ oppositeClass = oppositeProperty.getClass_();
	                String oppositeClassNamespaceURI = this.classifier.getNamespaceURI(
	                        oppositeClass);
	                propertyType = PlasmaTypeHelper.INSTANCE.getType(oppositeClassNamespaceURI, 
	                        oppositeClass.getName()); 
                }
                else {
        			// Note where one end (Property) of an association is linked to a read-only class, tools (MagicDraw)
        			// will necessarily create an association owned-end as it cannot modify the target read-only class. This
        			// can be the case when imported read-only modules are used. In such cases a property may not 
                	// have an opposite. 
                	org.modeldriven.fuml.repository.Classifier oppositeClassifier = prop.getType();
                	Classifier repoClassifier = new Classifier(oppositeClassifier);
                	String oppositeClassNamespaceURI = repoClassifier.getNamespaceURI();
                	propertyType = PlasmaTypeHelper.INSTANCE.getType(oppositeClassNamespaceURI, 
                    		repoClassifier.getName()); 
                	//continue;
                }
            }
            else
            {            
            	org.modeldriven.fuml.repository.Classifier propertyClassifier = prop.getType();	
        		DataType dataType = DataType.valueOf(propertyClassifier.getName());
        		propertyType = PlasmaTypeHelper.INSTANCE.getType(
                        PlasmaConfig.getInstance().getSDO().getDefaultNamespace().getUri(),
                        dataType.name());
            }
            
            PlasmaProperty property = new CoreProperty(
            		(CoreType)propertyType, 
            		new org.plasma.sdo.repository.Property(prop), 
            		this);
            
            this.declaredPropertiesMap.put(property.getName(), property);
        	PlasmaProperty existing = null;
            for (String alias : property.getAliasNames()) {
            	if ((existing = this.declaredPropertiesMap.get(alias)) != null)
            		if (!existing.getName().equals(property.getName()))
            		    throw new IllegalStateException("found existing property, "
            	 			+ existing.getContainingType().toString() + "." + existing.getName()
            	 	        + ", already mapped to alias '" + alias + "' while loading property "
            			    + this.toString() + "." + property.getName()); 
            	this.declaredPropertiesMap.put(alias, property);
            }
            this.declaredPropertiesList.add(property);            
            
            // Cache operational (meta) properties/tags/facets as instance properties on Type for quick access
            if (property.isKey(KeyType.primary))
            {
                List<Property> pkPropList = (List<Property>)this.instancePropertiesMap.get(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_PRIKEY_PROPERTIES);
                if (pkPropList == null) {
                    pkPropList = new ArrayList<Property>();
                    this.instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_PRIKEY_PROPERTIES, pkPropList);                     
                }
                pkPropList.add(property);
            }
            
            if (property.isConcurrent(ConcurrencyType.origination, ConcurrentDataFlavor.time))
                this.instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_ORIGINATION_TIMESTAMP, property);                     

            if (property.isConcurrent(ConcurrencyType.origination, ConcurrentDataFlavor.user))
                this.instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_ORIGINATION_USER, property);                     
            
            if (property.isConcurrent(ConcurrencyType.pessimistic, ConcurrentDataFlavor.time))
                this.instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_LOCKING_TIMESTAMP, property);                     

            if (property.isConcurrent(ConcurrencyType.origination, ConcurrentDataFlavor.user))
                this.instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_LOCKING_USER, property);                     
            
            // FIXME: time, version ??
            if (property.isConcurrent(ConcurrencyType.optimistic, ConcurrentDataFlavor.time))
                this.instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_CONCURRENCY_VERSION, property);                     

            if (property.isConcurrent(ConcurrencyType.optimistic, ConcurrentDataFlavor.user))
                this.instancePropertiesMap.put(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_CONCURRENCY_USER, property);                     
 
        }
    }


    /**
     * Returns the name of the type.
     * 
     * @return the type name.
     */
    public String getName() {
    	if (this.name == null)
            return classifier.getName();
    	else
    		return name;
    }
    
    /**
     * Returns the name of this Type as a byte array which may be cached
     * or lazily cached on demand. 
     * <p>
     * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access Services} for sparse, 
     * distributed "cloud" data stores typically storing lexicographically 
     * ordered row and column keys as uninterpreted arrays of bytes. Fast dynamic
     * construction of such keys is important as such services may necessarily construct
     * unique composite keys based in part on qualified or unqualified logical or physical
     * type names. 
     * </p>
     * @return the name of this Type as a byte array
     */
    public byte[] getNameBytes() {
    	byte[] result = (byte[])this.instancePropertiesMap.get(PlasmaProperty.INSTANCE_PROPERTY_BYTES_NAME_BYTES);
        if (result == null) {
        	result = this.getName().getBytes(
        		Charset.forName( CoreConstants.UTF8_ENCODING ) );
			this.instancePropertiesMap.put(
				PlasmaProperty.INSTANCE_PROPERTY_BYTES_NAME_BYTES, result);
        }
        return result;
    }

    /**
     * Return the namespace URI qualified name for this
     * type. This method is provided as using a QName
     * may be more efficient than performing
     * string concatenations within various client contexts, particularly
     * where hashing and hash lookups by qualified Type name are
     * required.      
     * @return the namespace URI qualified name for this
     * type. 
     */
    public QName getQualifiedName() {
    	return this.qname;
    }

    /**
     * Returns the namespace qualified logical name of this Type as a byte array which may be cached
     * or lazily cached on demand. 
     * <p>
     * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access Services} for sparse, 
     * distributed "cloud" data stores typically storing lexicographically 
     * ordered row and column keys as uninterpreted arrays of bytes. Fast dynamic
     * construction of such keys is important as such services may necessarily construct
     * unique composite keys based in part on qualified or unqualified logical or physical
     * type names. 
     * </p>
     * @return the namespace qualified logical name of this Type as a byte array
     */
    public byte[] getQualifiedNameBytes() {
    	byte[] result = (byte[])this.instancePropertiesMap.get(PlasmaProperty.INSTANCE_PROPERTY_BYTES_QUALIFIED_NAME_BYTES);
        if (result == null) {
        	result = this.getQualifiedName().toString().getBytes(
        		Charset.forName( CoreConstants.UTF8_ENCODING ) );
			this.instancePropertiesMap.put(
				PlasmaProperty.INSTANCE_PROPERTY_BYTES_QUALIFIED_NAME_BYTES, result);
        }
        return result;
    }
    
    /**
     * Return the namespace qualified physical name for this
     * type or null if no physical alias name exists. This method is provided as using a QName
     * may be more efficient, depending on the client usage context, than performing
     * string concatenations, particularly
     * where hashing and hash lookups by qualified Type name are
     * required.      
     * @return the namespace qualified logical name for this
     * type or null if no physical alias name exists. 
     */
    public QName getQualifiedPhysicalName() {
    	String physicalName = this.getPhysicalName();
    	if (physicalName != null)
    		return new QName(this.namespaceURI, physicalName);   	    	
    	return null;
    }
    
    /**
     * Returns a qualified logical-name hash code 
     * for this type based on the 
     * @return a qualified logical-name hash code 
     * for this type. 
     */
    public int getQualifiedNameHashCode() {
    	return this.qnameHashCode;
    }
    
    /**
     * Returns the namespace qualified physical name of this Type as a byte array which may be cached
     * or lazily cached on demand, or null if no physical alias name exists. 
     * <p>
     * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access Services} for sparse, 
     * distributed "cloud" data stores typically storing lexicographically 
     * ordered row and column keys as uninterpreted arrays of bytes. Fast dynamic
     * construction of such keys is important as such services may necessarily construct
     * unique composite keys based in part on qualified or unqualified logical or physical
     * type names. 
     * </p>
     * @return the namespace qualified physical name of this Type as a byte array, or null if no physical alias name exists.
     */
    public byte[] getQualifiedPhysicalNameBytes() {
    	byte[] result = (byte[])this.instancePropertiesMap.get(PlasmaProperty.INSTANCE_PROPERTY_BYTES_QUALIFIED_PHYSICAL_NAME_BYTES);
        if (result == null) {
        	QName qname = getQualifiedPhysicalName(); 
        	if (qname != null) {
        	    result = qname.toString().getBytes(
        		    Charset.forName( CoreConstants.UTF8_ENCODING ) );
			    this.instancePropertiesMap.put(
				    PlasmaProperty.INSTANCE_PROPERTY_BYTES_QUALIFIED_PHYSICAL_NAME_BYTES, result);
        	}
        }
        return result;
    }
    
    
	/**
     * Returns the namespace URI of the type.
     * @return the namespace URI.
     */
    public String getURI() {
        return namespaceURI;
    }
    
    /**
     * Returns the URI of this Type as a byte array which may be cached
     * or lazily cached on demand. 
     * <p>
     * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access Services} for sparse, 
     * distributed "cloud" data stores typically storing lexicographically 
     * ordered row and column keys as uninterpreted arrays of bytes. Fast dynamic
     * construction of such keys is important as such services may necessarily construct
     * unique composite keys based in part on qualified or unqualified logical or physical
     * type names. 
     * </p>
     * @return the URI of this Type as a byte array
     */    
    public byte[] getURIBytes() {
    	byte[] result = (byte[])this.instancePropertiesMap.get(PlasmaProperty.INSTANCE_PROPERTY_BYTES_URI_BYTES);
        if (result == null) {
        	result = this.getURI().getBytes(
        		Charset.forName( CoreConstants.UTF8_ENCODING ) );
			this.instancePropertiesMap.put(
				PlasmaProperty.INSTANCE_PROPERTY_BYTES_URI_BYTES, result);
        }
        return result;
    }

    /**
     * Return the physical name for this
     * type or null if no physical alias name exists. 
     * @return the physical name for this
     * type or null if no physical alias name exists.    
     */
    public String getPhysicalName() {
      
    	if (this.physicalName == null)
            return this.classifier.getPhysicalName();
    	else
    		return this.physicalName;
    }

    /**
     * Returns the physical name alias of this Type as a byte array which may be cached
     * or lazily cached on demand, or null if no physical alias name exists. 
     * <p>
     * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access Services} for sparse, 
     * distributed "cloud" data stores typically storing lexicographically 
     * ordered row and column keys as uninterpreted arrays of bytes. Fast dynamic
     * construction of such keys is important as such services may necessarily construct
     * unique composite keys based in part on qualified or unqualified logical or physical
     * type names. 
     * </p>
     * @return the physical name alias of this Type as a byte array, or null if no physical alias name exists.
     */
    public byte[] getPhysicalNameBytes() {
    	byte[] result = (byte[])this.instancePropertiesMap.get(PlasmaProperty.INSTANCE_PROPERTY_BYTES_PHYSICAL_NAME_BYTES);
        if (result == null) {
        	String name = this.getPhysicalName();
        	if (name != null) {
	        	result = name.getBytes(
	        		Charset.forName( CoreConstants.UTF8_ENCODING ) );
				this.instancePropertiesMap.put(
					PlasmaProperty.INSTANCE_PROPERTY_BYTES_PHYSICAL_NAME_BYTES, result);
        	}
        }
        return result;
    }
    
    public String getLocalName() {
    	if (this.localName != null) {
    		return this.localName;
    	}
    	else {
		    Alias alias = this.classifier.findAlias();
		    if (alias != null && alias.getLocalName() != null)
			    return alias.getLocalName();
    	}
    
    	return this.name;
    }
    
    /**
     * Returns the local name of this Type as a byte array which may be cached
     * or lazily cached on demand. 
     * <p>
     * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access Services} for sparse, 
     * distributed "cloud" data stores typically storing lexicographically 
     * ordered row and column keys as uninterpreted arrays of bytes. Fast dynamic
     * construction of such keys is important as such services may necessarily construct
     * unique composite keys based in part on qualified or unqualified logical or physical
     * type names. 
     * </p>
     * @return the local name of this Type as a byte array
     */
    public byte[] getLocalNameBytes() {
    	byte[] result = (byte[])this.instancePropertiesMap.get(PlasmaProperty.INSTANCE_PROPERTY_BYTES_LOCAL_NAME_BYTES);
        if (result == null) {
        	result = this.getLocalName().getBytes(
	        	Charset.forName( CoreConstants.UTF8_ENCODING ) );
			this.instancePropertiesMap.put(
				PlasmaProperty.INSTANCE_PROPERTY_BYTES_LOCAL_NAME_BYTES, result);
        }
        return result;
    }
    
    /**
     * Returns the logical name of the 
     * model package (if any) associated with this Type 
     * as a string, or null if no package exists.
     * @return the logical name of the 
     * model package (if any) associated with this Type 
     * as a string, or null if no package exists.
     */    
    public String getPackageName() {
    	return this.classifier.getPackageName();
    }

    /**
     * Returns the physical name alias of the 
     * model package (if any) associated with this Type 
     * as a string, or null if no physical 
     * name alias exists. The package physical name alias is
     * useful for various services, such as those
     * providing access to relational data stores. 
     * @return the physical name alias of the 
     * model package (if any) associated with this Type 
     * as a string, or null if no physical 
     * name alias exists.
     */    
    public String getPackagePhysicalName() {
    	return this.classifier.getPackagePhysicalName();    	
    }
    
	@SuppressWarnings("unchecked")
	public List<Comment> getDescription() {
		return (List<Comment>)this.get(
	    		PlasmaProperty.INSTANCE_PROPERTY_OBJECT_DESCRIPTION);
	}
	
	@SuppressWarnings("unchecked")
	public String getDescriptionText() {
		StringBuilder buf = new StringBuilder();
		List<Comment> list = (List<Comment>)this.get(
	    		PlasmaProperty.INSTANCE_PROPERTY_OBJECT_DESCRIPTION);
	    if (list != null)
		    for (Comment comment : list)
		    	buf.append(comment.getBody());
		return buf.toString();
	}

	public Alias getAlias() {
    	return this.classifier.findAlias();
    }   
    
    public Classifier getClassifier() {
		return classifier;
	}

	/**
     * Indicates if this Type is abstract.  If true, this Type cannot be
     * instantiated.  Abstract types cannot be used in DataObject or 
     * DataFactory create methods.
     * @return true if this Type is abstract.
     */
    public boolean isAbstract() {
        return this.classifier.isAbstract(); 
    }

    /**
     * Indicates if this Type specifies DataTypes (true) or DataObjects (false).
     * When false, any object that is an instance of this type
     * also implements the DataObject interface.
     * True for simple types such as Strings and numbers.
     * For any object:
     * <pre>
     *   isInstance(object) && !isDataType() implies
     *   DataObject.class.isInstance(object) returns true. 
     * </pre>
     * @return true if Type specifies DataTypes, false for DataObjects.
     */
    public boolean isDataType() {
        return this.classifier.isDataType();
    }


    /**
     * Return a list of alias names for this Type.
     * @return a list of alias names for this Type.
     */
    public List<String> getAliasNames() {       
        if (this.instancePropertiesMap == null)
            lazyLoadProperties();
        List<String> aliasList = new ArrayList<String>();
        String physicalName = (String)this.instancePropertiesMap.get(PlasmaProperty.INSTANCE_PROPERTY_STRING_PHYSICAL_NAME);
        if (physicalName != null)
            aliasList.add(physicalName);
        return aliasList;
    }

    /**
     * Returns the List of immediate base Types for this Type, or an
     * empty list if there are no base Types.  
     * XSD <extension>, <restriction>, and
     * Java extends keyword are mapped to this list.
     * @return the List of immediate base Types for this Type, or an
     * empty list if there are no base Types. 
     */
    public List<Type> getBaseTypes() {    	
    	if (this.baseTypes == null) {
    		this.baseTypes = new ArrayList<Type>();
    		PlasmaTypeHelper helper = PlasmaTypeHelper.INSTANCE;
	    	List<org.modeldriven.fuml.repository.Classifier> generalizations = this.classifier.getGeneralization();
	    	for (org.modeldriven.fuml.repository.Classifier classifier : generalizations) {                
	    		String namespaceURI = this.classifier.getNamespaceURI(
	    				classifier);
	    		Type type = helper.getType(namespaceURI, 
	    				classifier.getName());
	    		this.baseTypes.add(type);
	    	}
    	}
    	
        return this.baseTypes;
    }
    
    /**
     * Returns true if the given type is part of the (base type)
     * ancestry for this type.  
     * @param other the base type candidate
     * @return true if the given type is a base type for this
     * type. 
     */
    public boolean isBaseType(PlasmaType other) {    	
    	for (Type t : getBaseTypes()) {
    		PlasmaType baseType = (PlasmaType)t;
    		if (baseType.getQualifiedNameHashCode() == 
    				other.getQualifiedNameHashCode()) {
    			return true;
    		}
    		else if (baseType.isBaseType(other))
    			return true;
    	}
    	return false;
    }
    
    /**
     * Returns a list of types which specialize or inherit from
     * this type. An empty list is returned if no sub types exist. 
     * @return a list of types which specialize or inherit from
     * this type. An empty list is returned if no sub types exist. 
     */
    public List<Type> getSubTypes() {
    	if (this.subTypes == null) {
    		this.subTypes = new ArrayList<Type>();
    		PlasmaTypeHelper helper = PlasmaTypeHelper.INSTANCE;
	    	List<org.modeldriven.fuml.repository.Classifier> specializations = this.classifier.getSpecializations();
	    	for (org.modeldriven.fuml.repository.Classifier classifier : specializations) {                
	    		String namespaceURI = this.classifier.getNamespaceURI(
	    				classifier);
	    		Type type = helper.getType(namespaceURI, 
	    				classifier.getName());
	    		this.subTypes.add(type);
	    	}
    	}
    	return this.subTypes;
    }
    
    /**
     * Returns true if the given type is a specialization or 
     * inherits from this type. 
     * @param other the sub type candidate
     * @return true if the given type is a specialization or 
     * inherits from this type.
     */
    public boolean isSubType(PlasmaType other) 
    {
    	for (Type t : getSubTypes()) {
    		PlasmaType subType = (PlasmaType)t;
    		if (subType.getQualifiedNameHashCode() == 
    				other.getQualifiedNameHashCode()) {
    			return true;
    		}
    		else if (subType.isSubType(other))
    			return true;
    	}
    	return false;	
    }
    
    /**
     * Returns the Properties declared in this Type as opposed to
     * those declared in base Types.
     * @return the Properties declared in this Type.
     */
    public List<Property> getDeclaredProperties() {
        if (this.declaredPropertiesList == null)
            lazyLoadProperties();
        return this.declaredPropertiesList;
    }
    
    /**
     * Returns an alphabetically sorted list of the Properties declared 
     * in this Type as opposed to
     * those declared in base Types. Properties are alphabetically sorted by name. 
     * @return the Properties declared in this Type.
     */
	public List<Property> getDeclaredPropertiesSorted() {
    	List<Property> list = getDeclaredProperties();
    	Property[] properties = new Property[list.size()];
    	list.toArray(properties);
    	Arrays.sort(properties, new Comparator<Property>() {
			public int compare(Property o1, Property o2) {
				return o1.getName().compareTo(o2.getName());
			}
    	});
    	List<Property> result = new ArrayList<Property>(properties.length);
    	for (Property prop : properties)
    		result.add(prop);
    	return result;
    }
    
    public List<Property> getInstanceProperties() {
        if (this.declaredPropertiesMap == null)
            lazyLoadProperties();

        Iterator<PlasmaProperty> iter = this.instancePropertiesMap.keySet().iterator();
        List<Property> result = new ArrayList<Property>(this.instancePropertiesMap.size());
        while (iter.hasNext())
            result.add(iter.next());
        return result;
    }
    
    /**
     * Returns the List of the {@link Property Properties} of this type and
     * all base types.
     * <p>
     * The expression
     *<pre>
     *   type.getProperties().indexOf(property)
     *</pre>
     * yields the property's index relative to this type.
     * As such, these expressions are equivalent:
     *<pre>
     *    dataObject.{@link DataObject#get(int) get}(i)
     *    dataObject.{@link DataObject#get(Property) get}((Property)dataObject.getType().getProperties().get(i));
     *</pre>
     * </p>
     * @return the Properties of the type.
     * @see Property#getContainingType
     */
    public List<Property> getProperties() {
    	List<Property> result = new ArrayList<Property>();
    	collectDeclaredProperties(this, result);
        return result; 
    }

    /**
     * Returns the value of the specified instance property of this Type.
     * 
     * @param property
     *            one of the properties returned by
     *            {@link #getInstanceProperties()}.
     * @return the value of the specified property.
     * @see DataObject#get(Property)
     */
    public Object get(Property property) {
        if (this.declaredPropertiesMap == null)
            lazyLoadProperties();
        return this.instancePropertiesMap.get(property); 
    }
    
    public List<Object> search(Property property) {
        if (this.declaredPropertiesMap == null)
            lazyLoadProperties();
        List<Object> result = new ArrayList<Object>();
        this.collectInstancePropertyValues(this, property, result);
        return result;
    }    

    private void collectInstancePropertyValues(Type type, Property property,
    		List<Object> result) {
        Object value = type.get(property);
        if (value != null)
        	result.add(value);
        for (Type baseType : type.getBaseTypes()) {
        	collectInstancePropertyValues(baseType, property, result);
        }        
    }
    
    
    /**
     * Returns from {@link #getProperties all the Properties} of this type, the 
     * property with the specified name.
     * As such, these expressions are equivalent:
     *<pre>
     *    dataObject.{@link DataObject#get(String) get}("name")
     *    dataObject.{@link DataObject#get(Property) get}(dataObject.getType().getProperty("name"))
     *</pre>
     * </p>
     * @return the Property with the specified name.
     * throws IllegalArgumentException if the 
     * property with the specified name is not found 
     * @see #getProperties
     */
    public Property getProperty(String propertyName) {
        Property result = findDeclaredProperty(this, propertyName);
        if (result == null)
            throw new IllegalArgumentException("given property name '"
                    + propertyName + "' is undefined for type "
                    + this.getURI() + "#" + this.getName() 
                    + " and all its base types");

        return result;
    }
    
    /**
     * Returns the declared property from this type and its base types  with the
     * given property name, or null if not exists 
     * @param name the property name
     * @return the declared property from this type and its base types  with the
     * given property name, or null if not exists 
     */
    public Property findProperty(String propertyName) {
    	return findDeclaredProperty(this, propertyName);
    }
    
    public Property findProperty(ConcurrencyType concurrencyType, ConcurrentDataFlavor dataFlavor) {
    	List<Property> properties = new ArrayList<Property>();
    	
    	collectDeclaredProperties(this, 
    	    concurrencyType, dataFlavor, properties);
    	if (properties.size() == 0)
    		return null;
    	else if (properties.size() == 1)
    		return properties.get(0);
    	else {
    		log.warn("Found multiple declared properties for type "
    				+ this.getURI() + "#" + this.getName() + " tagged with "
    				+ ConcurrencyType.class.getSimpleName() + " '" + concurrencyType.name() 
    				+ "' and concurrency data flavor '"
    				+ dataFlavor.name() + "'");
    		return properties.get(0);
    	}
    }

    public Property findProperty(KeyType keyType) {
    	List<Property> properties = new ArrayList<Property>();
    	
    	collectDeclaredProperties(this, 
    			keyType, properties);
    	if (properties.size() == 0)
    		return null;
    	else if (properties.size() == 1)
    		return properties.get(0);
    	else {
    		log.warn("Found multiple declared properties for type "
    				+ this.getURI() + "#" + this.getName() + " tagged with "
    				+ KeyType.class.getSimpleName() + " '" + keyType.name() + "'");
    		return properties.get(0);
    	}
    }
    
    public List<Property> findProperties(KeyType keyType) {
    	List<Property> properties = new ArrayList<Property>();
    	
    	collectDeclaredProperties(this, 
    			keyType, properties);
    	return properties;
    }
    
    private Property findDeclaredProperty(PlasmaType currentType, String propertyName) {
        if (currentType.getDeclaredProperties() == null)
        	((CoreType)currentType).lazyLoadProperties();
                
        Property result = ((CoreType)currentType).declaredPropertiesMap.get(propertyName);
        if (result != null) {
        	return result;
        }
        else {
            for (Type base : currentType.getBaseTypes()) {
            	PlasmaType baseType = (PlasmaType)base;
            	result = findDeclaredProperty(baseType, propertyName);
            	if (result != null)
            		return result;
            }
        }
        return null;
    }

    private Property findDeclaredProperty(PlasmaType currentType, 
    		ConcurrencyType concurrencyType, ConcurrentDataFlavor dataFlavor) {
        if (currentType.getDeclaredProperties() == null)
        	((CoreType)currentType).lazyLoadProperties();
        Property result = null;        
        for (Property property : getDeclaredProperties()) {
        	if (((PlasmaProperty)property).isConcurrent(concurrencyType, dataFlavor)) {
        		result = property;
        		break;
        	}
        }
        for (Type base : currentType.getBaseTypes()) {
        	PlasmaType baseType = (PlasmaType)base;
        	result = findDeclaredProperty(baseType, concurrencyType, dataFlavor);
        	if (result != null)
        		return result;
        }
        return null;
    }
    
	private void collectDeclaredProperties(PlasmaType type, List<Property> properties) {
		for (Property p : type.getDeclaredProperties())
			properties.add(p);
		for (Type t : type.getBaseTypes())
			collectDeclaredProperties((PlasmaType)t, properties);			
	}	

	private void collectDeclaredProperties(PlasmaType type, 
			ConcurrencyType concurrencyType, ConcurrentDataFlavor dataFlavor,
			List<Property> properties) {
		for (Property p : type.getDeclaredProperties()) {
			PlasmaProperty plasmaProp = ((PlasmaProperty)p);
			if (plasmaProp.isConcurrent(concurrencyType, dataFlavor)) {
			    properties.add(p);
			}
		}
		for (Type t : type.getBaseTypes())
			collectDeclaredProperties((PlasmaType)t, 
					concurrencyType, dataFlavor, properties);			
	}	

	private void collectDeclaredProperties(PlasmaType type, 
			KeyType keyType,
			List<Property> properties) {
		for (Property p : type.getDeclaredProperties()) {
			PlasmaProperty plasmaProp = ((PlasmaProperty)p);
			if (plasmaProp.isKey(keyType)) {
			    properties.add(p);
			}
		}
		for (Type t : type.getBaseTypes())
			collectDeclaredProperties((PlasmaType)t, 
					keyType, properties);			
	}	
	
		
    /**
     * Returns the Java class that this type represents or the generic
     * data object implementation class if no provisioned implementation
     * class is found. 
     * @return the Java class that this type represents or the generic
     * data object implementation class if no provisioned implementation
     * class is found. 
     */
    public Class<?> getInstanceClass() {
        if (!isDataType()) {
            Namespace namespace = PlasmaConfig.getInstance().getSDONamespaceByURI(this.getURI());          
            if (namespace != null && namespace.getProvisioning() != null) {
	            String qualifiedName = namespace.getProvisioning().getPackageName() 
	                + "." + this.getName();
	            try {
	                Class<?> interfaceImplClass = Class.forName(qualifiedName);
	                return interfaceImplClass;
	            } catch (ClassNotFoundException e) {
	            	return CoreDataObject.class;
	            }
            }
            else
            	return CoreDataObject.class;
        }
        else
            return toDataTypeInstanceClass(DataType.valueOf(this.getName()));
    }
    
    /**
     * Returns whether the specified object is an instance of this type.
     * @param object the object in question.
     * @return <code>true</code> if the object is an instance.
     * @see Class#isInstance
     */
    public boolean isInstance(Object object) {
        
        boolean result = false;
        if (DataObject.class.isAssignableFrom(object.getClass()))
        {
            DataObject dataObject = (DataObject)object;
            Type type = dataObject.getType();
            result = type.getName().equals(this.getName()) &&
                type.getURI().equals(this.getURI());
        }
        else if (this.isDataType())
        {
            Class<?> instanceClass = toDataTypeInstanceClass(DataType.valueOf(this.getName()));
            return instanceClass.isAssignableFrom(object.getClass());
        }
        return result;
    }

    /**
     * Indicates if this Type allows any form of open content.  If false,
     * dataObject.getInstanceProperties() must be the same as 
     * dataObject.getType().getProperties() for any DataObject dataObject of this Type.
     * @return true if this Type allows open content.
     */
    public boolean isOpen() {
        return false;
    }

    /**
     * Indicates if this Type specifies Sequenced DataObjects.
     * Sequenced DataObjects are used when the order of values 
     * between Properties must be preserved.
     * When true, a DataObject will return a Sequence.  For example,
     * <pre>
     *  Sequence elements = dataObject.{@link DataObject#getSequence() getSequence}();
     * </pre>
     * @return true if this Type specifies Sequenced DataObjects.
     */
    public boolean isSequenced() {
        return false;
    }

    /**
     * Returns a Java primitive wrapper class for the given SDO data-type (as 
     * per the SDO Specification 2.10 Section 8.1). 
     * @param dataType
     * @return the SDO Java class.
     */
    public Class<?> toDataTypeInstanceClass(DataType dataType) {
        return DataConverter.INSTANCE.toWrapperJavaClass(dataType);
    }

	public boolean isRelation(PlasmaType other, AssociationPath relationPath) {
		return this.classifier.isRelation(other.getClassifier(), relationPath);
	}
        
}
