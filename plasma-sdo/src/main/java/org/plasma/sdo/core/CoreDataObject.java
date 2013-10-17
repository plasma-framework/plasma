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

// java imports

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jaxen.JaxenException;
import org.plasma.sdo.PlasmaChangeSummary;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.PlasmaDataGraphEventVisitor;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataLink;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaEdge;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.provider.common.PropertyPair;
import org.plasma.sdo.helper.DataConverter;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.profile.ConcurrencyType;
import org.plasma.sdo.profile.ConcurrentDataFlavor;
import org.plasma.sdo.profile.KeyType;
import org.plasma.sdo.xpath.DataGraphNodeAdapter;
import org.plasma.sdo.xpath.DataGraphXPath;
import org.plasma.sdo.xpath.InvalidEndpointException;
import org.plasma.sdo.xpath.InvalidMultiplicityException;
import org.plasma.sdo.xpath.XPathDataProperty;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Sequence;
import commonj.sdo.Type;

/**
 * A data object is a representation of some structured data. 
 * It is the fundamental component in the SDO (Service Data Objects) package.
 * Data objects support reflection, path-based accesss, convenience creation and deletion methods, 
 * and the ability to be part of a {@link DataGraph data graph}.
 * <p>
 * Each data object holds its data as a series of {@link Property Properties}. 
 * Properties can be accessed by name, property index, or using the property meta object itself. 
 * A data object can also contain references to other data objects, through reference-type Properties.
 * <p>
 * A data object has a series of convenience accessors for its Properties. 
 * These methods either use a path (String), 
 * a property index, 
 * or the {@link Property property's meta object} itself, to identify the property.
 * Some examples of the path-based accessors are as follows:
 *<pre>
 * DataObject company = ...;
 * company.get("name");                   is the same as company.get(company.getType().getProperty("name"))
 * company.set("name", "acme");
 * company.get("department.0/name")       is the same as ((DataObject)((List)company.get("department")).get(0)).get("name")
 *                                        .n  indexes from 0 ... implies the name property of the first department
 * company.get("department[1]/name")      [] indexes from 1 ... implies the name property of the first department
 * company.get("department[number=123]")  returns the first department where number=123
 * company.get("..")                      returns the containing data object
 * company.get("/")                       returns the root containing data object
 *</pre> 
 * <p> There are general accessors for Properties, i.e., {@link #get(Property) get} and {@link #set(Property, Object) set}, 
 * as well as specific accessors for the primitive types and commonly used data types like 
 * String, Date, List, BigInteger, and BigDecimal.
 */
public class CoreDataObject extends CoreNode 
    implements PlasmaDataObject 
{
    private static final long serialVersionUID = 1L;

    private static Log log = LogFactory.getFactory().getInstance(CoreDataObject.class);

    /** 
     * The cached UUID value. Caching UUID has bacome important
     * as continually pulling the UUID out of the underlying
     * value map/hash has a performance impact as UUID is
     * used in every equals comparison etc..
     */
    private UUID uuid; 
    /** The cached integral hash code value */
    private int hashCode;
    
    private DataGraph dataGraph; // TODO - consider hashing these
    private transient Type type;
    /** used for serialization only */
    private String typeName;
    /** used for serialization only */
    private String typeUri;
    
    
    /**
     * The current container for this DataObject
     */
    private DataObject container;
    /** 
     * The declared reference Property within the Type for the 
     * container which is our current containment reference property 
     */
    private transient Property containmentProperty;
    /** used for serialization only */
    private String containmentPropertyName;
    /** used for serialization only */
    private String containmentPropertyTypeName;
    /** used for serialization only */
    private String containmentPropertyTypeUri;

    /**
     * Default No-arg constructor required for serialization operations. This method 
     * is NOT intended to be used within application source code.  
     */
    protected CoreDataObject() {
        super();
    }
    
    protected CoreDataObject(Type type, CoreObject values) {
        super(values);        
        this.type = type;
        this.uuid = UUID.randomUUID();
        this.hashCode = uuid.hashCode();
    }

    public CoreDataObject(Type type) {
        super(new CoreObject(type.getName()));       
        this.type = type;
        this.uuid = UUID.randomUUID();
    }   

    public int hashCode() {
    	return this.hashCode;
    }
    
    public boolean equals(Object obj) {
    	return this.uuid.equals(((CoreDataObject)obj).uuid);
    }
    
    /**
     * Writes out metadata logical names as string
     * for serialization.
     * @param out the stream
     * @throws IOException
     */
    private void writeObject(java.io.ObjectOutputStream out) 
    		throws IOException {
    	this.typeName = this.type.getName();
    	this.typeUri = this.type.getURI();
    	this.type = null;
    	if (this.containmentProperty != null) {
    	    this.containmentPropertyName = this.containmentProperty.getName();
    	    this.containmentPropertyTypeName = this.containmentProperty.getContainingType().getName();
    	    this.containmentPropertyTypeUri = this.containmentProperty.getContainingType().getURI();
    	    this.containmentProperty = null;
    	}
    	out.defaultWriteObject();
    }
    
    /**
     * Reads in metadata logical names as string
     * during de-serialization looks up and
     * restores references.
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream in)
    	     throws IOException, ClassNotFoundException {
    	in.defaultReadObject();
    	this.type = PlasmaTypeHelper.INSTANCE.getType(
    			this.typeUri, this.typeName);
    	this.typeName = null;
    	this.typeUri = null;
    	if (this.containmentPropertyName != null) {
    		Type propertyType = PlasmaTypeHelper.INSTANCE.getType(
    				this.containmentPropertyTypeUri, this.containmentPropertyTypeName);
    		this.containmentProperty = propertyType.getProperty(this.containmentPropertyName);
    	    this.containmentPropertyName = null;
    	    this.containmentPropertyTypeName = null;
    	    this.containmentPropertyTypeUri = null;
    	}
    }
    
    /**
     * Returns the {@link DataGraph data graph} for this object or
     * <code>null</code> if there isn't one.
     * @return the containing data graph or <code>null</code>.
     */
    public DataGraph getDataGraph() {
        return this.dataGraph;
    }
    
    public void setDataGraph(DataGraph dataGraph) {
        this.dataGraph = dataGraph;
    }
    
    public ChangeSummary getChangeSummary() {
        return this.dataGraph.getChangeSummary();
    }
    
    public DataObject getRootObject() {
        return this.dataGraph.getRootObject();
    }
    
    public UUID getUUID() {
   	    return this.uuid;
    }
    
    /**
     * Resets the UUID after creation for cases where the UUID
     * is stored externally and services creating data objects
     * need to preserve the stored UUIDs across service calls.
     * Refreshes the integral hash and other elements dependent
     * on the cached UUID. 
     * @param uuid the UUID
     */
    public void resetUUID(UUID uuid) {
   	    this.uuid = uuid;
        this.hashCode = uuid.hashCode();
    }
    
    @Deprecated
    public String getUUIDAsString() {   
   	    return this.uuid.toString();
    }
    
    public String uuidToBase64(String str) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(this.uuid.getMostSignificantBits());
        bb.putLong(this.uuid.getLeastSignificantBits());
        return Base64.encodeBase64URLSafeString(bb.array());
    }
    
    public String uuidFromBase64(String str) {
        byte[] bytes = Base64.decodeBase64(str);
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }    
    
    public PlasmaDataObject getDataObject() {
        return this;
    }    

    /**
     * Returns a new {@link DataObject data object} contained by this object
     * using the specified property, which must be a
     * {@link Property#isContainment containment property}. The type of the
     * created object is the {@link Property#getType declared type} of the
     * specified property.
     * 
     * @param propertyName
     *            the name of the specified containment property.
     * @return the created data object.
     * @see #createDataObject(String, String, String)
     */
    public DataObject createDataObject(String propertyName) {

        Property property = this.getType().getProperty(propertyName);
        
        return createContainedDataObject(property, property.getType());
    }

    /**
     * Returns a new {@link DataObject data object} contained by this object using the specified property,
     * which must be a {@link Property#isContainment containment property}.
     * The type of the created object is the {@link Property#getType declared type} of the specified property.
     * @param propertyIndex the index of the specified containment property.
     * @return the created data object.
     * @see #createDataObject(int, String, String)
     */
    public DataObject createDataObject(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return createContainedDataObject(property, property.getType());
    }

    /**
     * Returns a new {@link DataObject data object} contained by this object using the specified property,
     * which must be a {@link Property#isContainment containment property}.
     * The type of the created object is the {@link Property#getType declared type} of the specified property.
     * @param property the specified containment property.
     * @return the created data object.
     * @see #createDataObject(Property, Type)
     */
    public DataObject createDataObject(Property property) {

        return createContainedDataObject(property, property.getType());

    }
    
    /**
     * Returns a new {@link DataObject data object} contained by this object using the specified property,
     * which must be a {@link Property#isContainment containment property}.
     * The type of the created object is specified by the packageURI and typeName arguments.
     * The specified type must be a compatible target for the property identified by propertyName.
     * @param propertyName the name of the specified containment property.
     * @param namespaceURI the namespace URI of the package containing the type of object to be created.
     * @param typeName the name of a type in the specified package.
     * @return the created data object.
     * @see #createDataObject(String)
     * @see DataGraph#getType
     */
    public DataObject createDataObject(String propertyName, String namespaceURI, String typeName) {
        Property property = (Property)this.getType().getProperty(propertyName);       
        Type type = PlasmaTypeHelper.INSTANCE.getType(namespaceURI, typeName);
        return createContainedDataObject(property, type);
    }

    /**
     * Returns a new {@link DataObject data object} contained by this object using the specified property,
     * which must be a {@link Property#isContainment containment property}.
     * The type of the created object is specified by the packageURI and typeName arguments.
     * The specified type must be a compatible target for the property identified by propertyIndex.
     * @param propertyIndex the index of the specified containment property.
     * @param namespaceURI the namespace URI of the package containing the type of object to be created.
     * @param typeName the name of a type in the specified package.
     * @return the created data object.
     * @see #createDataObject(int)
     * @see DataGraph#getType
     */
    public DataObject createDataObject(int propertyIndex, String namespaceURI, String typeName) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        Type type = PlasmaTypeHelper.INSTANCE.getType(namespaceURI, typeName);
        return createContainedDataObject(property, type);
    }

    /**
     * Returns a new {@link DataObject data object} contained by this object using the specified property,
     * which must be of {@link Property#isContainment containment type}.
     * The type of the created object is specified by the type argument,
     * which must be a compatible target for the specified property.
     * @param property a containment property of this object.
     * @param type the type of object to be created.
     * @return the created data object.
     * @see #createDataObject(int)
     */
    public DataObject createDataObject(Property property, Type type) {
        return createContainedDataObject(property, type);
    }

    /**
     * Creates a data object of the given type associated through the given containment reference property.
     * @param property a containment reference property defined within the type of this
     * data-object.
     * @param type the type for the newly created data-object, typically just the type of the
     * given containment reference property, but may vary for open-content properties. 
     * @return
     */
    private DataObject createContainedDataObject(Property property, Type type) {

        if (property.getType().isDataType())
            throw new IllegalArgumentException("property '" 
                    + this.getType().getName() + "." + property.getName() 
                    + "' is a datatype property (" 
                    + property.getType().getName() 
                    + "), not a containment reference property");
        
        if (!property.isContainment())
            throw new IllegalArgumentException("property '" 
                    + this.getType().getName() + "." + property.getName() 
                    + "' is not a containment property");
 
        if (!(property.getType().getName().equals(type.getName()) && 
        	  property.getType().getURI().equals(type.getURI())))
        {        	
        	PlasmaType targetType = (PlasmaType)type;
        	if (!targetType.isBaseType((PlasmaType)property.getType()))        	 
                throw new IllegalArgumentException("the type for property '" 
                    + this.getType().getName() + "." + property.getName()
                    + "(" + property.getType().getURI() + "#" + property.getType().getName() + ")"
                    + "' is not compatible with (i.e. a base type of) the given type, "
                    + type.getURI() + "#" + type.getName());
        }

        PlasmaDataObject dataObject = (PlasmaDataObject)PlasmaDataFactory.INSTANCE.create(
                type);

        if (property.isMany())
            this.add(property, dataObject);
        else
            this.set(property, dataObject);
        
        // make this the new object's container
        dataObject.setContainer(this);
        dataObject.setContainmentProperty(property);
        
 
        if (this.getDataGraph() != null) {
                        
        	dataObject.setDataGraph(this.getDataGraph());           
            
            PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)this.getDataGraph().getChangeSummary();
             
            changeSummary.created(dataObject);
            changeSummary.modified(this, property, dataObject);
        }   
        else
            throw new IllegalStateException("source data-object has no data-graph");
                
        return dataObject;
    }

    // FIXME use instance properties
    public void setContainer(DataObject container) {
        this.container = container;
    }

    public void setContainmentProperty(Property containmentProperty) {
        this.containmentProperty = containmentProperty;
    }

    
    /**
     * Remove this object from its container and then unset all its non-
     * {@link Property#isReadOnly readOnly} properties. If this object is
     * contained by a {@link Property#isReadOnly readOnly}
     * {@link Property#isContainment containment property}, its non-
     * {@link Property#isReadOnly readOnly} properties will be unset but the
     * object will not be removed from its container. All DataObjects
     * recursively contained by {@link Property#isContainment containment
     * properties} will also be deleted.
     */
    @SuppressWarnings("unchecked")
	public void delete() {
    	
    	// collect the containment graph in a flat list
    	ContainmentGraphCollector collector = 
    		new ContainmentGraphCollector();
    	this.accept(collector);
    	List<ContainmentNode> nodes = collector.getResult();

    	// For each node, update the change summary.
        // Capture delete before removing properties, as the current state
        // is required in the change-summary
        for (ContainmentNode node : nodes) {
        	
        	PlasmaDataObject toDelete = node.getDataObject();
            if (toDelete.getContainer() == null) {
                String rootKey = ((PlasmaNode)this.getDataGraph().getRootObject()).getUUIDAsString();
                if (!toDelete.getUUIDAsString().equals(rootKey))
                    throw new IllegalStateException("non-root data-object (" 
                            + toDelete.getUUIDAsString() + ") of type "
                            + toDelete.getType().getURI() + "#" 
                            + toDelete.getType().getName() + " has no container");
            }
            if (toDelete.getDataGraph() != null) {
                PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)toDelete.getDataGraph().getChangeSummary();
                changeSummary.deleted(toDelete);
            }  
        }  
        
    	// unset non-readonly datatype properties
        for (ContainmentNode node : nodes) {
        	PlasmaDataObject toDelete = node.getDataObject();
            for (Property property : toDelete.getType().getDeclaredProperties()) {
                if (property.getType().isDataType() && !property.isReadOnly()) {
                	PlasmaProperty prop = (PlasmaProperty)property;
                	// services need PK's to be left set such that they can construct 
                	// queries to find entities to delete
                	if (!prop.isKey(KeyType.primary)) {
                        if (toDelete.isSet(property))
                    	    toDelete.unset(property);  
                	}
                }
            }
        }  
        
        // process linked objects
        for (ContainmentNode node : nodes) {
        	// FIXME: detect readonly containment property
        	// and abort removal. 
        	PlasmaDataObject toDelete = node.getDataObject();
            for (Property property : toDelete.getType().getDeclaredProperties()) {
                if (property.getType().isDataType()) 
                    continue;
                if (!property.isMany()) {
                    DataObject dataObject = toDelete.getDataObject(property);
                    if (dataObject == null)
                        continue; 
                	PlasmaProperty prop = (PlasmaProperty)property;
                	if (!prop.isKey(KeyType.primary)) {
                        //FIXME: currently unset() is not removing opposites. See comments in unset()
                        toDelete.unset(property);   
                	}
                }
                else {
                    List<DataObject> dataObjectList = toDelete.getList(property);
                    if (dataObjectList == null)
                        continue;
                    // Note: remove() changes the structure of the graph
                	for (DataObject dataObject : dataObjectList)
                    	toDelete.remove(property, dataObject);                     	              	
                }
            } // next property
        }
    }
    

    /**
     * Returns true if this data object is the container for the
     * given data object. 
     * @param dataObject the data object
     * @return true if this data object is the container for the
     * given data object
     */
    public boolean contains(DataObject dataObject) {
    	CoreDataObject container = (CoreDataObject)dataObject.getContainer();
    	if (container == null)
            throw new IllegalArgumentException("the given data-object (" 
                    + ((CoreDataObject)dataObject).getUUIDAsString() + ") of type "
                    + dataObject.getType().getURI() + "#" 
                    + dataObject.getType().getName() + " has no container");            
        return container.equals(this);
    }
    
    /**
     * Removes this DataObject from its data graph and container, if any. Same as
     * getContainer().getList(getContainmentProperty()).remove(this) or
     * getContainer().unset(getContainmentProperty()) depending on
     * getContainmentProperty().isMany() respectively.
     */
    @Override
    public void detach() {
    	
    	if (this.containmentProperty != null) {
    		if (this.getContainer() != null) // could be detached already
		    	if (this.containmentProperty.isMany()) {
		    		this.getContainer().getList(this.containmentProperty).remove(this);
		    	}
		    	else {
		    		this.getContainer().unset(this.containmentProperty);
		    	}
    	}
    	else {
    		if (!((CoreDataObject)this.getDataGraph().getRootObject()).equals(this))
    			log.warn("detected non root data object of type "
    		        + this.getType().getURI() + "#" + this.getType().getName()
    		        +" with no container");
    	}
    	
    	this.dataGraph = null;
    }

    public Object get(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return get(property);
    }

    /**
     * Returns the value of the given property of this object.
     * <p>
     * If the property is {@link Property#isMany many-valued}, the result will
     * be a {@link java.util.List} and each object in the List will be
     * {@link Type#isInstance an instance of} the property's
     * {@link Property#getType type}. Otherwise the result will directly be an
     * instance of the property's type.
     * 
     * @param property
     *            the property of the value to fetch.
     * @return the value of the given property of the object.
     * @see #set(Property, Object)
     * @see #unset(Property)
     * @see #isSet(Property)
     */
    public Object get(Property property) {
        
        Object result = super.getValue(property.getName());
        if (result instanceof NullValue) // don't expose null-value to SDO clients
            result = null;
        
        if (result != null && !property.getType().isDataType())
            if (!property.isMany())
            {           
                result = ((PlasmaEdge)result).getOpposite(this);
            }
            else {
                List<PlasmaEdge> edgeList = (List<PlasmaEdge>)result;  
                List<DataObject> resultList = new ArrayList<DataObject>(edgeList.size());
                result = resultList;
                for (PlasmaEdge edge : edgeList) 
                    resultList.add((DataObject)edge.getOpposite(this));                                     
            }
        
        return result;
    }

    /**
     * Returns the value of a property of either this object or an object reachable 
     * from it, as identified by the specified path.
     * @param path the path to a valid object and property.
     * @return the value of the specified property.
     * @see #get(Property)
     */
    public Object get(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
            Property property = this.getType().getProperty(path);
            return get(property);
    	}
    	else {
    		Object result = null;
			DataGraphNodeAdapter[] nodes = null;
			try {
				DataGraphXPath xpath = new DataGraphXPath(path);
				nodes = xpath.getResults(this);
			} catch (JaxenException e) {
				throw new IllegalArgumentException(e);
			} catch (InvalidMultiplicityException e) {
				throw new IllegalArgumentException(e);
			}
			if (nodes.length ==1) {
				result = nodes[0].get();
			}
			else {
				List<Object> list = new ArrayList<Object>(nodes.length);
				for (DataGraphNodeAdapter node : nodes)
					list.add(node.get());
				result = list;
			}
    		return result; 
    	}
    }    

    /**
     * Sets a property of either this object or an object or objects reachable 
     * from it, as identified by the specified path.
     * @param path the path to a valid object or set of objects and property.
     * @param value the new value for the property.
     * @see #set(Property, Object)
     */
    public void set(String path, Object value) {
    	if (!DataGraphXPath.isXPath(path)) {
            Property property = this.getType().getProperty(path);
            this.set(property, value);
    	}
    	else {
    		XPathDataProperty[] nodes = null;
			try {
				DataGraphXPath xpath = new DataGraphXPath(path);
				nodes = xpath.findProperties(this);
			} catch (JaxenException e) {
				throw new IllegalArgumentException(e);
			} catch (InvalidEndpointException e) {
				throw new IllegalArgumentException(e);
			}
			for (DataGraphNodeAdapter node : nodes)
				node.set(value);
    	}
    }
    
    /**
     * Conversions are specified in Java [6] and the DataHelper. The supported conversions are
specified in Section 16 (DataType Conversions).
Note that when calling the primitive DataObject.set() methods, no automatic conversion
is performed. In this case, type conversion can be explicitly performed by calling
DataHelper.convert() before calling the set() method. For example:
dataObject.set(property, dataHelper.convert(property, value));
     */
    public void set(int propertyIndex, Object value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);  
        this.set(property, value);
    }

    /**
     * Sets the value of the given property of the object to the new value.
     * <p>
     * If the property is {@link Property#isMany many-valued},
     * the new value must be a {@link java.util.List}
     * and each object in that list must be {@link Type#isInstance an instance of} 
     * the property's {@link Property#getType type};
     * the existing contents are cleared and the contents of the new value are added.
     * Otherwise the new value directly must be an instance of the property's type
     * and it becomes the new value of the property of the object.
     * @param property the property of the value to set.
     * @param value the new value for the property.
     * @see #unset(Property)
     * @see #isSet(Property)
     * @see #get(Property)
     */
    public void set(Property property, Object value) {
        
        if (property.isReadOnly())
            throw new UnsupportedOperationException(this.getType().getURI() + "#"
                    + this.getType().getName() + "." + property.getName() + " is a read-only property");
        if (value == null)
            throw new IllegalArgumentException("unexpected null value - use DataObject.unset() to clear a property");
        
        Object propertyValue = value;
        Class<?> instanceClass = property.getType().getInstanceClass();
        
        if (!property.isMany())
        {
            if (!instanceClass.isAssignableFrom(value.getClass())) 
                throw new ClassCastException("expected instance of " 
                        + property.getType().getInstanceClass().getName()
                        + " as value for property "
                        + this.type.getURI() + "#" + this.type.getName() + "." + property.getName()
                        + " - not class, " + value.getClass().getName()); 
            
            // add graph edge
            if (!property.getType().isDataType()) {
                PlasmaNode targetNode = (PlasmaNode)value;
                PlasmaDataLink existingLink = (PlasmaDataLink)super.getValue(property.getName());
                if (existingLink != null) {                   
                    PlasmaNode existingOpposite = existingLink.getOpposite(this);
                    if (existingOpposite.equals(targetNode))
                    {
                        log.warn("detected 'set' operation with identical " 
                            + targetNode.getClass().getSimpleName() 
                            + " object (" + targetNode.getUUIDAsString() + ") for property "
                            + this.type.getURI() + "#" + this.type.getName() + "." + property.getName() 
                            + " - ignoring");
                        return;
                    }
                }
                PlasmaDataLink link = createLink(property, targetNode);
                propertyValue = link; // set the link into this node below
            }
            else {
                Object existingValue = this.get(property);
                if (existingValue != null && existingValue.equals(propertyValue))
                {
                    if (log.isDebugEnabled())
                        log.debug("detected 'set' operation with identical " 
                                + existingValue.getClass().getSimpleName() 
                                + " object (" + String.valueOf(existingValue) + ") for property "
                                + this.type.getURI() + "#" + this.type.getName() + "." + property.getName() 
                                + " - ignoring");
                    return;
                }
            }
        }
        else {
            if (value instanceof List)
            {
                List<Object> list = (List)value;
               
                List<PlasmaEdge> edgeList = null;
                if (!property.getType().isDataType()) {
                    edgeList = new ArrayList<PlasmaEdge>(list.size());
                    propertyValue = edgeList; // set the link list into this node below
                }
            
                for (Object listValue : list) {
                    if (!instanceClass.isAssignableFrom(listValue.getClass())) 
                        throw new ClassCastException("expected instance of " 
                                + property.getType().getInstanceClass().getName()
                                + " as value for property "
                                + this.type.getURI() + "#" + this.type.getName() + "." + property.getName()
                                + " - not class, " + listValue.getClass().getName());  
                    if (edgeList != null) {
                    	PlasmaDataLink link = createLink(property, (PlasmaNode)listValue);
                        edgeList.add(link); 
                    }
                }                                    
            }
            else
                throw new ClassCastException("expected java.util.List Java Class for property "
                        + this.type.getURI() + "#" + this.type.getName() + "." + property.getName()
                        + " - not class, " + value.getClass().getName());
        }
               
        if (this.getDataGraph() != null) {
            Object oldValue = this.get(property);
            if (oldValue == null)
                oldValue = new NullValue();
            PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)this.getDataGraph().getChangeSummary();
            changeSummary.modified(this, property, oldValue);
        }        
        
        super.setValue(property.getName(), propertyValue);
        
        // register an opposite change after the link between objects is created
        // as change summary creates a path between them
        /*
        if (!property.getType().isDataType()) {
            if (!property.isMany()) {
        	    this.oppositeModified(this, property, (PlasmaDataLink)propertyValue);        	
            }
            else {
        	    this.oppositeModified(this, property, (List<PlasmaEdge>)propertyValue);        	
            }
        }
        */
    }   
    

    /**
     * Adds the given value to the given multi=valued property. 
     * @param property the multi-valued property
     * @param value the value to add - can be a list of values
     */
    public void add(Property property, Object value) {
        if (property.isReadOnly())
            throw new UnsupportedOperationException(this.getType().getURI() + "#"
                    + this.getType().getName() + "." + property.getName() 
                    + " is a read-only property");
        
        Class<?> instanceClass = property.getType().getInstanceClass();
        
        if (!property.isMany())
            throw new UnsupportedOperationException(this.getType().getURI() + "#"
                    + this.getType().getName() + "." + property.getName() 
                    + " is not a multi-valued property");
        
        if (value instanceof List) // FIXME or just append all ??
            throw new IllegalArgumentException("unexpected List argument - use addList()");

        if (!instanceClass.isAssignableFrom(value.getClass())) 
            throw new ClassCastException("expected instance of " 
                    + property.getType().getInstanceClass().getName()
                    + " as value for property "
                    + this.type.getURI() + "#" + this.type.getName() + "." + property.getName()
                    + " - not class, " + value.getClass().getName()); 
        
        if (!property.getType().isDataType()) {
            
            List<PlasmaEdge> edgeList = (List<PlasmaEdge>)super.getValue(property.getName());
            if (edgeList == null) {                
                edgeList = new ArrayList<PlasmaEdge>();
                super.setValue(property.getName(), edgeList);
            }
            PlasmaDataLink link = createLink(property, (PlasmaNode)value);
            edgeList.add(link); 
        }
        else {
            List<Object> list = (List<Object>)super.getValue(property.getName());
            if (list == null) {                
                list = new ArrayList<Object>();
                super.setValue(property.getName(), list);
            }
            list.add(value);
        }
    }    

    /**
     * Removes the given value from this data object for 
     * the given multi-valued property. 
     * @param property multi-valued the property
     * @param value the value to remove
     * @throws UnsupportedOperationException if the given property
     * is not multi-valued 
     */
    @SuppressWarnings("unchecked")
	public void remove(Property property, Object value) {
        if (log.isDebugEnabled())
            log.debug("removing " + this.getType().getURI() + "#"
                    + this.getType().getName() + "." + property.getName());

        if (property.isReadOnly())
            throw new UnsupportedOperationException(this.getType().getURI() + "#"
                    + this.getType().getName() + "." + property.getName() 
                    + " is a read-only property");
        
        Class<?> instanceClass = property.getType().getInstanceClass();
        
        if (!property.isMany())
            throw new UnsupportedOperationException(this.getType().getURI() + "#"
                    + this.getType().getName() + "." + property.getName() 
                    + " is not a multi-valued property");
        
        if (value instanceof List) // FIXME or just append all ??
            throw new IllegalArgumentException("unexpected List argument");

        if (!instanceClass.isAssignableFrom(value.getClass())) 
            throw new ClassCastException("expected instance of " 
                    + property.getType().getInstanceClass().getName()
                    + " as value for property "
                    + this.type.getURI() + "#" + this.type.getName() + "." + property.getName()
                    + " - not class, " + value.getClass().getName()); 
        
        Object oldValue = this.get(property);
        
        if (!property.getType().isDataType()) {
            
            List<PlasmaEdge> edgeList = (List<PlasmaEdge>)super.getValue(property.getName());
            if (edgeList == null)               
                throw new IllegalStateException("no list found for property "
                    + this.type.getURI() + "#" + this.type.getName() + "." + property.getName());
            PlasmaNode nodeValue = (PlasmaNode)value;
            PlasmaEdge link = null;
        	CoreDataObject oppositeDataObject = null;
            for (PlasmaEdge edge : edgeList) {
            	// FIXME: extra work to navigate 'this' edge to get the opposite
            	// edge or edge list when were already given the opposite data object value
            	// FIXME: get rid of this hash key comparison crap ASAP - override equals and hashCode
            	oppositeDataObject = null;
            	if (edge.getLeft().getUUIDAsString().equals(nodeValue.getUUIDAsString())) {
                	link = edge; 
                	oppositeDataObject = (CoreDataObject)edge.getLeft().getDataObject(); 
                    break;
            	}
                else if (edge.getRight().getUUIDAsString().equals(nodeValue.getUUIDAsString())) {
                	link = edge; 
                  	oppositeDataObject = (CoreDataObject)edge.getRight().getDataObject();
                    break;
                }
                else
                	continue;
            }
            if (oppositeDataObject == null)
                throw new IllegalStateException("could not find link for property "
                    + this.type.getURI() + "#" + this.type.getName() + "." + property.getName());
            
            Property oppositeProperty = property.getOpposite();
            if (oppositeProperty != null) {
	            Object oppositeValue = oppositeDataObject.get(oppositeProperty);
	            if (oppositeValue != null) {
	                if (oppositeProperty.isMany()) {
	                    List<PlasmaEdge> oppositeEdgeList = (List<PlasmaEdge>)oppositeDataObject.getValue(
	                            oppositeProperty.getName());
	                    if (!oppositeEdgeList.remove(link))
	                        throw new IllegalStateException("could not remove opposite link for property "
	                            + this.type.getURI() + "#" + this.type.getName() + "." + property.getName());
	                }
	                else {
	                	oppositeDataObject.removeValue(oppositeProperty.getName());
	                }
	            }
	            else
	                throw new IllegalStateException("could not find opposite value for property "
	                        + this.type.getURI() + "#" + this.type.getName() + "." + property.getName());
	            
	            // register the change on opposite
	            if (this.getDataGraph() != null && oldValue != null) {
	                PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)this.getDataGraph().getChangeSummary();
	                changeSummary.modified(oppositeDataObject, oppositeProperty, oppositeValue);
	            } 
            } // has opposite prop 
 
            if (!edgeList.remove(link))
                throw new IllegalStateException("could not remove link for property "
                        + this.type.getURI() + "#" + this.type.getName() + "." + property.getName());
        }
        else { // datatype prop
            List<Object> list = (List<Object>)super.getValue(property.getName());
            if (list == null) 
                throw new IllegalStateException("no values found for property "
                    + this.type.getURI() + "#" + this.type.getName() + "." + property.getName());
            if (!list.remove(value))                 
                throw new IllegalArgumentException("value not found in list for property "
                    + this.type.getURI() + "#" + this.type.getName() + "." + property.getName());
        }
        
        // register the change on this object
        if (this.getDataGraph() != null && oldValue != null) {
            PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)this.getDataGraph().getChangeSummary();
            changeSummary.modified(this, property, oldValue);
        }               
    }    
    
    /**
     * Creates a link or graph edge between this data object node
     * and the given node via the given property. 
     * @param property the property defined within this node
     * @param node the target node
     * @return the new link
     */
    private PlasmaDataLink createLink(Property property, PlasmaNode node) {
        
        PlasmaDataObject target = node.getDataObject();
        if (target.getDataGraph() == null)
            target.setDataGraph(this.getDataGraph());
        else if (!target.getDataGraph().equals(this.getDataGraph()))
            throw new IllegalArgumentException("given data-object already belongs to a data-graph");   

        PlasmaDataLink link = new PlasmaDataLink(this, node);
    	if (log.isDebugEnabled())
            log.debug("created link (" + link.hashCode() + ") from/to (" + this.getUUIDAsString() + ") "
                    + this.getType().getURI() + "#" + this.getType().getName() 
                    + "." + property.getName() + "->("
                    + target.getUUIDAsString() + ") "
                    + target.getType().getURI() + "#" + target.getType().getName());
  
    	// add the new link to the opposite property if exists
        Property opposite = property.getOpposite();
        if (opposite != null) {
	        if (opposite.isMany()) {
	            // FIXME: do we want to wipe the opposite list here on a set?
	            List<PlasmaEdge> oppositeList = (List<PlasmaEdge>) ((CoreDataObject)target).getValue(opposite.getName());
	            if (oppositeList == null) 
	                oppositeList = new ArrayList<PlasmaEdge>(1);            
	        	if (log.isDebugEnabled())
	                log.debug("adding link (" + link.hashCode() + ") from/to (" + target.getUUIDAsString() + ") "
	                        + target.getType().getURI() + "#" + target.getType().getName() 
	                        + "." + opposite.getName() + "->("
	                        + this.getUUIDAsString() + ") "
	                        + this.getType().getURI() + "#" + this.getType().getName());
	            oppositeList.add(link);                    
	            ((CoreDataObject)target).setValue(opposite.getName(), oppositeList);
	        }
	        else {
	        	if (target.isSet(opposite))
	        		throw new IllegalArgumentException("cannot link this "
	        				+ this.getType().getURI() + "#" + this.getType().getName()
	        				+ " object to singular opposite property "
	                        + target.getType().getURI() + "#" + target.getType().getName() 
	                        + "." + opposite.getName() + " because a value is already set "
	                        + "- consider making this property a 'many' property");
	        	((CoreDataObject)target).setValue(opposite.getName(), link); 
	        }
        }
        
        return link;
        
    }

    /**
     * Unsets a property of either this object or an object reachable from it, as 
     * identified by the specified path.
     * @param path the path to a valid object and property.
     * @see #unset(Property)
     */
    public void unset(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
            Property property = this.getType().getProperty(path);
            this.unset(property);
    	}
    	else {
    		XPathDataProperty[] nodes = null;
			try {
				DataGraphXPath xpath = new DataGraphXPath(path);
				nodes = xpath.findProperties(this);
			} catch (JaxenException e) {
				throw new IllegalArgumentException(e);
			} catch (InvalidEndpointException e) {
				throw new IllegalArgumentException(e);
			}
			for (XPathDataProperty node : nodes)
				node.unset();
    	}
    }

    /**
     * Unsets the property at the specified index in {@link Type#getProperties property list} of this object's {@link Type type}.
     * @param propertyIndex the index of the property.
     * @see #unset(Property)
     */
    public void unset(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex); 
        this.unset(property);
    }

    /**
     * Unsets the property of the object.
     * <p>
     * If the property is {@link Property#isMany many-valued}, the value must be
     * an {@link java.util.List} and that list is cleared. Otherwise, the value
     * of the property of the object is set to the property's
     * {@link Property#getDefault default value}. The property will no longer be
     * considered {@link #isSet set}.
     * 
     * @param property
     *            the property in question.
     * @see #isSet(Property)
     * @see #set(Property, Object)
     * @see #get(Property)
     */
   public void unset(Property property) {
       
       if (this.getType().getProperty(property.getName()) == null)
           throw new IllegalArgumentException("given property "
                   + property.getType().getURI() + "#"
                   + property.getType().getName() + "." + property.getName()
                   + " is not found within this Data Objects type, " 
                   + this.getType().getURI() + "#" + this.getType().getName());

       if (log.isDebugEnabled())
           log.debug("unsetting " + this.getType().getURI() + "#"
                   + this.getType().getName() + "." + property.getName() + "("+this.getUUIDAsString()+")");
       
       if (!this.isSet(property)) {
           if (log.isWarnEnabled())
               log.warn("cannot unset property " + this.getType().getURI() + "#"
                       + this.getType().getName() + "." + property.getName());
           return;
       }
       
       
       // FIXME - is unset intended to flag a delete ??
       // FIXME - we want to null a reference property, not delete the referenced object
       // FIXME - do what flags that ?? On change-summary modified object and has an old value
       if (property.isReadOnly())
            throw new UnsupportedOperationException(this.getType().getURI() + "#"
                    + this.getType().getName() + "." + property.getName() 
                    + " is a read-only property");
        
        Object oldValue = this.get(property); // de-reference any links 
        
        if (!property.isMany()) // singular
        {
            if (property.getType().isDataType()) {
            	if (this.getDataGraph() != null) { 
	                PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)this.getDataGraph().getChangeSummary();
	                if (oldValue != null) {
	                    changeSummary.modified(this, property, oldValue);
	                } 
            	}
                this.setValue(property.getName(), new NullValue());
            }
            else {   
            	// first register a modification on the opposite AND this data 
            	// object before modifying the graph, as change summary
            	// needs the intact graph path(s)
            	oppositeModified(this, property);
            	if (this.getDataGraph() != null) { 
	                PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)this.getDataGraph().getChangeSummary();
	                if (oldValue != null) {
	                    changeSummary.modified(this, property, oldValue);
	                } 
            	}
            	PlasmaDataObject oldDataObject = (PlasmaDataObject)oldValue;
            	 
                // now remove/null values changing the graph structure 
            	unsetOpposite(this, property); 
            	// NOTE: When we automatically unset/remove opposites on an unset
                // it changes the structure of the data graph such that subsequent operations which
                // must register a change, such as delete, may/cannot find the change target in the graph
            	// Do we need to flag edges as deleted??
                
            	this.setValue(property.getName(), new NullValue());
            }
        }
        else { // many
            if (property.getType().isDataType()) {
            	if (this.getDataGraph() != null) { 
	                PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)this.getDataGraph().getChangeSummary();
	                if (oldValue != null) {
	                    changeSummary.modified(this, property, oldValue);
	                } 
            	}
                this.removeValue(property.getName());
            }
            else {
            	// first register a modification before modifying the graph, as change summary
            	// needs the intact graph path(s)
            	oppositeModified(this, property);

            	if (this.getDataGraph() != null) { 
	                PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)this.getDataGraph().getChangeSummary();
	                if (oldValue != null) {
	                    changeSummary.modified(this, property, oldValue);
	                } 
            	}
                // now remove/null values creating unset state
            	unsetOpposite(this, property); // when we automatically unset/remove opposites on an unset
                // it changes the structure of the data graph such that subsequent operations which
                // must register a change, such as delete, may/cannot find the change target in the graph
                this.removeValue(property.getName());
            }
        }
    }

    /**
     * Just registers a modification on the opposite property not changing
     * the structure of the graph. This method is useless as it resets the same
     * value in the change summary !!
     * @param dataObject the data object
     * @param property the property
     */
	@SuppressWarnings("unchecked")
	@Deprecated
	private void oppositeModified(CoreDataObject dataObject, Property property)
    {
	    Property oppositeProperty = property.getOpposite();
	    if (oppositeProperty == null)
	    	return;
	    
	    if (!property.isMany()) {
	        PlasmaDataLink link = (PlasmaDataLink)dataObject.getValue(property.getName());
	        if (link != null) {
		        PlasmaNode oppositeNode = link.getOpposite(dataObject);
		        PlasmaDataObject oppositeDataObject = oppositeNode.getDataObject();
		        
	            if (!oppositeProperty.isMany()) { // one-to-one
	                PlasmaDataLink oppositeLink = (PlasmaDataLink)((CoreDataObject)oppositeDataObject).getValue(oppositeProperty.getName());
	                if (oppositeLink == null || !oppositeLink.equals(link))
	                    throw new IllegalStateException("expected equivalent link for property, "
	                        + this.getType().getURI() + "#" + this.getType().getName() 
	                        + "." + property.getName());
	                // register change on opposite
	                if (oppositeDataObject.getDataGraph() != null) {
	                    PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)oppositeDataObject.getDataGraph().getChangeSummary();
	                    if (!changeSummary.isCreated(oppositeDataObject) && !changeSummary.isDeleted(oppositeDataObject))
	                        changeSummary.modified(oppositeDataObject, oppositeProperty, this);
	                }               
	            }
	            else {
	                // register change on opposite
	                List<DataObject> oppositeDataObjectList = oppositeDataObject.getList(oppositeProperty);
	                if (oppositeDataObject.getDataGraph() != null) {
	                    PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)oppositeDataObject.getDataGraph().getChangeSummary();
	                    if (!changeSummary.isCreated(oppositeDataObject) && !changeSummary.isDeleted(oppositeDataObject))
	                        changeSummary.modified(oppositeDataObject, oppositeProperty, oppositeDataObjectList);
	                }               
	            } 
	        }
		}
		else { // is Many
	        List<PlasmaDataLink> links = (List<PlasmaDataLink>)dataObject.getValue(property.getName());
	        if (links != null) {
		        for (PlasmaDataLink link : links) {
		            PlasmaNode oppositeNode = link.getOpposite(dataObject);
		            PlasmaDataObject oppositeDataObject = oppositeNode.getDataObject();
		            if (!oppositeProperty.isMany()) { // one-to-one
		                PlasmaDataLink oppositeLink = (PlasmaDataLink)((CoreDataObject)oppositeDataObject).getValue(oppositeProperty.getName());
		                if (oppositeLink == null || !oppositeLink.equals(link))
		                    throw new IllegalStateException("expected equivalent link for property, "
		                        + this.getType().getURI() + "#" + this.getType().getName() 
		                        + "." + property.getName());
		                // register change on opposite
		                if (oppositeDataObject.getDataGraph() != null) {
		                    PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)oppositeDataObject.getDataGraph().getChangeSummary();
		                    if (!changeSummary.isCreated(oppositeDataObject) && !changeSummary.isDeleted(oppositeDataObject))
		                        changeSummary.modified(oppositeDataObject, oppositeProperty, this);
		                }               
		            }
		            else {
		                // register change on opposite
		                List<DataObject> oppositeDataObjectList = oppositeDataObject.getList(oppositeProperty);
		                if (oppositeDataObject.getDataGraph() != null) {
		                    PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)oppositeDataObject.getDataGraph().getChangeSummary();
		                    if (!changeSummary.isCreated(oppositeDataObject) && !changeSummary.isDeleted(oppositeDataObject))
		                        changeSummary.modified(oppositeDataObject, oppositeProperty, oppositeDataObjectList);
		                }               
		            }   
		        }
	        }
		}
    }

	private void oppositeModified(CoreDataObject dataObject, Property property, PlasmaDataLink link)
    {
	    Property oppositeProperty = property.getOpposite();
	    if (oppositeProperty == null)
	    	return;
	    
	    if (property.isMany()) {
	    	log.warn("expected singular property not multi-property, " + property.toString());
	    	return;
	    }
        PlasmaNode oppositeNode = link.getOpposite(dataObject);
        PlasmaDataObject oppositeDataObject = oppositeNode.getDataObject();
        
        if (!oppositeProperty.isMany()) { // one-to-one
            PlasmaDataLink oppositeLink = (PlasmaDataLink)((CoreDataObject)oppositeDataObject).getValue(oppositeProperty.getName());
            if (oppositeLink == null || !oppositeLink.equals(link))
                throw new IllegalStateException("expected equivalent link for property, "
                    + this.getType().getURI() + "#" + this.getType().getName() 
                    + "." + property.getName());
            // register change on opposite
            if (oppositeDataObject.getDataGraph() != null) {
                PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)oppositeDataObject.getDataGraph().getChangeSummary();
                if (!changeSummary.isCreated(oppositeDataObject) && !changeSummary.isDeleted(oppositeDataObject))
                    changeSummary.modified(oppositeDataObject, oppositeProperty, this);
            }               
        }
        else {
            // register change on opposite
            List<DataObject> oppositeDataObjectList = oppositeDataObject.getList(oppositeProperty);
            if (oppositeDataObject.getDataGraph() != null) {
                PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)oppositeDataObject.getDataGraph().getChangeSummary();
                if (!changeSummary.isCreated(oppositeDataObject) && !changeSummary.isDeleted(oppositeDataObject))
                    changeSummary.modified(oppositeDataObject, oppositeProperty, oppositeDataObjectList);
            }               
        } 
	}
	    	    
	private void oppositeModified(CoreDataObject dataObject, Property property, List<PlasmaEdge> links)
    {
	    Property oppositeProperty = property.getOpposite();
	    if (oppositeProperty == null)
	    	return;
	    if (!property.isMany()) {
	    	log.warn("expected multi property not sungular property, " + property.toString());
	    	return;
	    }
	    
        for (PlasmaEdge link : links) {
            PlasmaNode oppositeNode = link.getOpposite(dataObject);
            PlasmaDataObject oppositeDataObject = oppositeNode.getDataObject();
            if (!oppositeProperty.isMany()) { // one-to-one
                PlasmaDataLink oppositeLink = (PlasmaDataLink)((CoreDataObject)oppositeDataObject).getValue(oppositeProperty.getName());
                if (oppositeLink == null || !oppositeLink.equals(link))
                    throw new IllegalStateException("expected equivalent link for property, "
                        + this.getType().getURI() + "#" + this.getType().getName() 
                        + "." + property.getName());
                // register change on opposite
                if (oppositeDataObject.getDataGraph() != null) {
                    PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)oppositeDataObject.getDataGraph().getChangeSummary();
                    if (!changeSummary.isCreated(oppositeDataObject) && !changeSummary.isDeleted(oppositeDataObject))
                        changeSummary.modified(oppositeDataObject, oppositeProperty, this);
                }               
            }
            else {
                // register change on opposite
                List<DataObject> oppositeDataObjectList = oppositeDataObject.getList(oppositeProperty);
                if (oppositeDataObject.getDataGraph() != null) {
                    PlasmaChangeSummary changeSummary = (PlasmaChangeSummary)oppositeDataObject.getDataGraph().getChangeSummary();
                    if (!changeSummary.isCreated(oppositeDataObject) && !changeSummary.isDeleted(oppositeDataObject))
                        changeSummary.modified(oppositeDataObject, oppositeProperty, oppositeDataObjectList);
                }               
            }   
        }
    }
	
    /**
     * Looks at the opposite property, if exists, for the given data object
     * and reference property, makes the structural the change appropriate
     * for an unset operation on the given property. Does not
     * effect the change summary.
     * @param dataObject the data object
     * @param property the property being unset
     */
    @SuppressWarnings("unchecked")
	private void unsetOpposite(CoreDataObject dataObject, Property property)
    {
    	if (!property.isMany()) {
	        PlasmaDataLink link = (PlasmaDataLink)dataObject.getValue(property.getName());
	        unsetLink(link, dataObject, property);
    	}
    	else {
	        List<PlasmaDataLink> links = (List<PlasmaDataLink>)dataObject.getValue(property.getName());
    		if (links != null)
    			for (PlasmaDataLink link : links) {
    		        unsetLink(link, dataObject, property);
    			}
    	}
    }
    
    private void unsetLink(PlasmaDataLink link, CoreDataObject dataObject, Property property)
    {
        PlasmaNode oppositeNode = link.getOpposite(dataObject);
        PlasmaDataObject oppositeDataObject = oppositeNode.getDataObject();
        Property oppositeProperty = property.getOpposite();
        if (oppositeProperty != null) {
            if (!oppositeProperty.isMany()) { // one-to-one
                PlasmaDataLink oppositeLink = (PlasmaDataLink)((CoreDataObject)oppositeDataObject).getValue(oppositeProperty.getName());
                if (oppositeLink == null || !oppositeLink.equals(link))
                    throw new IllegalStateException("expected equivalent link for property, "
                        + this.getType().getURI() + "#" + this.getType().getName() 
                        + "." + property.getName());
                ((CoreDataObject)oppositeDataObject).setValue(oppositeProperty.getName(), new NullValue());
            }
            else {
                // register change on opposite
                List<PlasmaEdge> oppositeList = (List<PlasmaEdge>)((CoreDataObject)oppositeDataObject).getValue(oppositeProperty.getName());
                if (!oppositeList.remove(link))
                    throw new IllegalStateException("could not remove opposite link for property, "
                        + dataObject.getType().getURI() + "#" + this.getType().getName() 
                        + "." + property.getName());	                
            }   
        }
    }
   
    public boolean isSet(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
            Property property = this.getType().getProperty(path);
            return this.isSet(property);
    	}
    	else {
    		XPathDataProperty[] nodes = null;
			try {
				DataGraphXPath xpath = new DataGraphXPath(path);
				nodes = xpath.findProperties(this);
			} catch (JaxenException e) {
				throw new IllegalArgumentException(e);
			} catch (InvalidEndpointException e) {
				throw new IllegalArgumentException(e);
			}
			if (nodes.length > 1)
				throw new IllegalArgumentException("given XPath '" + path + "' resulted in "
						+ "multiple results");
			return nodes[0].isSet();
    	}
    }

    public boolean isSet(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return isSet(property);
    }

    public boolean isSet(Property property) {
        // don't de-reference for performance 
        Object value = super.getValue(property.getName());
        
        // has a value that was not explicitly added by unset(), e.g. NullValue
        return value != null && !(value instanceof NullValue); 
        // FIXME - what about default property values. SDO spec seems to indicate these are to be supported ??     
    }
  
    public BigDecimal getBigDecimal(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getBigDecimal(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getBigDecimal(endpoint.getProperty());
    	    else
    		    return null;
    	}
    }

    public BigDecimal getBigDecimal(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex); 
        return this.getBigDecimal(property);
    }

    public BigDecimal getBigDecimal(Property property) {
        Object value = this.get(property);
        if (value != null)
            return DataConverter.INSTANCE.toDecimal(property.getType(), value);  
        else
            return null;
    }

    public BigInteger getBigInteger(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getBigInteger(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getBigInteger(endpoint.getProperty());
    	    else
    		    return null;
    	}
    }

    public BigInteger getBigInteger(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return this.getBigInteger(property);
    }

    public BigInteger getBigInteger(Property property) {
        Object value = this.get(property);
        if (value != null)
        	try {
                return DataConverter.INSTANCE.toInteger(property.getType(), value); 
        	}
            catch (NumberFormatException e) {
            	throw new ClassCastException(e.getMessage()); // as required by SDO 1.2 
            }
        else
            return null;
    }

    public boolean getBoolean(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getBoolean(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getBoolean(endpoint.getProperty());
    	    else
    		    return false;
    	}
    }

    public boolean getBoolean(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return getBoolean(property);
    }

    public boolean getBoolean(Property property) {
        Object value = this.get(property);
        if (value != null)
            return DataConverter.INSTANCE.toBoolean(property.getType(), value); 
        else
            return false;
    }

    public byte getByte(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getByte(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getByte(endpoint.getProperty());
    	    else
    		    return 0;
    	}
    }

    public byte getByte(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return this.getByte(property);
    }

    public byte getByte(Property property) {
        Object value = this.get(property);
        if (value != null)
            return DataConverter.INSTANCE.toByte(property.getType(), value);  
        else
            return 0;
    }

    public byte[] getBytes(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getBytes(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getBytes(endpoint.getProperty());
    	    else
    		    return new byte[0];
    	}
    }

    public byte[] getBytes(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return this.getBytes(property);
    }

    public byte[] getBytes(Property property) {
        Object value = this.get(property);
        if (value != null)
            return DataConverter.INSTANCE.toBytes(property.getType(), value);  
        else
            return null;
    }

    public char getChar(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getChar(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getChar(endpoint.getProperty());
    	    else
    		    return 0;
    	}
    }

    public char getChar(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex); 
        return getChar(property);
    }

    public char getChar(Property property) {
        Object value = this.get(property);
        if (value != null)
            return DataConverter.INSTANCE.toCharacter(property.getType(), value);  
        else
            return 0;
    }

    public Date getDate(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getDate(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getDate(endpoint.getProperty());
    	    else
    		    return null;
    	}
    }

    public Date getDate(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex); 
        return getDate(property);
    }

    public Date getDate(Property property) {
        Object value = this.get(property);
        if (value != null)
            return DataConverter.INSTANCE.toDate(property.getType(), value);  
        else
            return null;
    }

    public double getDouble(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getDouble(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getDouble(endpoint.getProperty());
    	    else
    		    return 0;
    	}
    }

    public double getDouble(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex); 
        return getDouble(property);
    }

    public double getDouble(Property property) {
        Object value = this.get(property);
        if (value != null)
            return DataConverter.INSTANCE.toDouble(property.getType(), value);  
        else
            return 0;
    }

    public float getFloat(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getFloat(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getFloat(endpoint.getProperty());
    	    else
    		    return 0;
    	}
    }

    public float getFloat(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex); 
        return getFloat(property);
    }

    public float getFloat(Property property) {
        Object value = this.get(property);
        if (value != null)
            return DataConverter.INSTANCE.toFloat(property.getType(), value);  
        else
            return 0;
    }

    public int getInt(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getInt(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getInt(endpoint.getProperty());
    	    else
    		    return 0;
    	}
    }

    public int getInt(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return getInt(property);
    }

    public int getInt(Property property) {
        Object value = this.get(property);
        if (value != null)
        	try {
                return DataConverter.INSTANCE.toInt(property.getType(), value); 
        	}
            catch (NumberFormatException e) {
            	throw new ClassCastException(e.getMessage()); // as required by SDO 1.2 
            }
        else
            return 0;
    }

    /**
     * Returns the value of a <code>List</code> property identified by the specified path.
     * @param path the path to a valid object and property.
     * @return the <code>List</code> value of the specified property.
     * @see #get(String)
     */
    @SuppressWarnings("unchecked")
	public List getList(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getList(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getList(endpoint.getProperty());
    	    else
    		    return new ArrayList();
    	}
    }

    /**
     * Returns the value of a <code>List</code> property identified by the specified property index.
     * @param propertyIndex the index of the property.
     * @return the <code>List</code> value of the specified property.
     * @see #get(int)
     */
    @SuppressWarnings("unchecked")
	public List getList(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return getList(property);
    }

    /**
     * Returns the value of the specified <code>List</code> property.
     * The List returned contains the current values.
     * Updates through the List interface operate on the current values of the DataObject.
     * Each access returns the same List object.
     * @param property the property to get.
     * @return the <code>List</code> value of the specified property.
     * @see #get(Property)
     */
    public List getList(Property property) {
        return (List)get(property);
    }

    public long getLong(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getLong(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getLong(endpoint.getProperty());
    	    else
    		    return 0;
    	}
    }

    public long getLong(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return getLong(property);
    }

    public long getLong(Property property) {
        Object value = this.get(property);
        if (value != null)
        	try {
                return DataConverter.INSTANCE.toLong(property.getType(), value); 
        	}
            catch (NumberFormatException e) {
            	throw new ClassCastException(e.getMessage()); // as required by SDO 1.2 
            }
        else
            return 0;
    }

    public Property getProperty(String propertyName) {
        return getType().getProperty(propertyName); 
    }

    public short getShort(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getShort(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getShort(endpoint.getProperty());
    	    else
    		    return 0;
    	}
    }

    public short getShort(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return getShort(property);
    }

    public short getShort(Property property) {
        Object value = this.get(property);
        if (value != null)
        	try {
                return DataConverter.INSTANCE.toShort(property.getType(), value); 
        	}
            catch (NumberFormatException e) {
            	throw new ClassCastException(e.getMessage()); // as required by SDO 1.2 
            }
        else
            return 0;
    }

    public String getString(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getString(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getString(endpoint.getProperty());
    	    else
    		    return null;
    	}
    }

    public String getString(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return this.getString(property);
    }

    public String getString(Property property) {
        Object value = this.get(property);
        if (value != null)
            return DataConverter.INSTANCE.toString(property.getType(), value); 
        else
            return null;
    }

    public Type getType() {
        return this.type;
    }
 
    public void setBigDecimal(String path, BigDecimal value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setBigDecimal(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setBigDecimal(endpoint.getProperty(), value);
    	}
    }

    public void setBigDecimal(int propertyIndex, BigDecimal value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setBigDecimal(property, value);
    }

    public void setBigDecimal(Property property, BigDecimal value) {
        this.set(property, value);
    }

    public void setBigInteger(String path, BigInteger value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setBigInteger(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setBigInteger(endpoint.getProperty(), value);
    	}
    }

    public void setBigInteger(int propertyIndex, BigInteger value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setBigInteger(property, value);
    }

    public void setBigInteger(Property property, BigInteger value) {
        this.set(property, value);
    }

    public void setBoolean(String path, boolean value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setBoolean(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setBoolean(endpoint.getProperty(), value);
    	}
    }

    public void setBoolean(int propertyIndex, boolean value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setBoolean(property, value);
    }

    public void setBoolean(Property property, boolean value) {
        this.set(property, value);
    }

    public void setByte(String path, byte value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setByte(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setByte(endpoint.getProperty(), value);
    	}
    }

    public void setByte(int propertyIndex, byte value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setByte(property, value);
    }

    public void setByte(Property property, byte value) {
        this.set(property, value);
    }

    public void setBytes(String path, byte[] value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setBytes(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setBytes(endpoint.getProperty(), value);
    	}
    }

    public void setBytes(int propertyIndex, byte[] value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setBytes(property, value);
    }

    public void setBytes(Property property, byte[] value) {
        this.set(property, value);
    }

    public void setChar(String path, char value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setChar(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setChar(endpoint.getProperty(), value);
    	}
    }

    public void setChar(int propertyIndex, char value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setChar(property, value);
    }

    public void setChar(Property property, char value) {
        this.set(property, value);
    }

    public void setDataObject(String path, DataObject value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setDataObject(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setDataObject(endpoint.getProperty(), value);
    	}
    }

    public void setDataObject(int propertyIndex, DataObject value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setDataObject(property, value);
    }

    public void setDataObject(Property property, DataObject value) {
        this.set(property, value);
    }

    public void setDate(String path, Date value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setDate(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setDate(endpoint.getProperty(), value);
    	}
    }

    public void setDate(int propertyIndex, Date value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setDate(property, value);
    }

    public void setDate(Property property, Date value) {
        this.set(property, value);
    }

    public void setDouble(String path, double value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setDouble(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setDouble(endpoint.getProperty(), value);
    	}
    }

    public void setDouble(int propertyIndex, double value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setDouble(property, value);
    }

    public void setDouble(Property property, double value) {
        this.set(property, value);
    }

    public void setFloat(String path, float value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setFloat(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setFloat(endpoint.getProperty(), value);
    	}
    }

    public void setFloat(int propertyIndex, float value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setFloat(property, value);
    }

    public void setFloat(Property property, float value) {
        this.set(property, value);
    }

    public void setInt(String path, int value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setInt(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setInt(endpoint.getProperty(), value);
    	}
    }

    public void setInt(int propertyIndex, int value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setInt(property, value);
    }

    public void setInt(Property property, int value) {
        this.set(property, value);
    }

    public void setList(String path, List value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setList(this.getType().getProperty(path), value);
    	}
    	else {
	    	PathEndpoint endpoint = findEndpoint(path);
	    	if (endpoint != null)
	    		endpoint.getSource().setList(endpoint.getProperty(), value);
    	}
    }

    public void setList(int propertyIndex, List value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setList(property, value);
    }

    public void setList(Property property, List value) {
        this.set(property, value);
    }

    public void setLong(String path, long value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setLong(this.getType().getProperty(path), value);
    	}
    	else {
	    	PathEndpoint endpoint = findEndpoint(path);
	    	if (endpoint != null)
	    		endpoint.getSource().setLong(endpoint.getProperty(), value);
    	}
    }

    public void setLong(int propertyIndex, long value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setLong(property, value);
    }

    public void setLong(Property property, long value) {
        this.set(property, value);
    }

    public void setShort(String path, short value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setShort(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setShort(endpoint.getProperty(), value);
    	}
    }

    public void setShort(int propertyIndex, short value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setShort(property, value);
    }

    public void setShort(Property property, short value) {
        this.set(property, value);
    }

    public void setString(String path, String value) {
    	if (!DataGraphXPath.isXPath(path)) {
    		this.setString(this.getType().getProperty(path), value);
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    endpoint.getSource().setString(endpoint.getProperty(), value);
    	}
    }

    public void setString(int propertyIndex, String value) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        this.setString(property, value);
    }

    public void setString(Property property, String value) {
        this.set(property, value);
    }
    
    /**
     * Determines whether the given path is an XPATH and if so finds the
     * single property endpoint resulting from the XPATH traversal. Where
     * multiple property endpoints are the result the first is used
     * as mentioned in SDO 2.1 Page 114. Where the given path is not an XPATH,
     * the path determined to be property name  
     * @param path the xpath or simple property name
     * @return the 
     * @see DataGraphXPath
     */
    private PathEndpoint findEndpoint(String path) {
		try {
			DataGraphXPath xpath = new DataGraphXPath(path);
			XPathDataProperty adapter = xpath.findProperty(this);
			if (adapter == null)
				return null; 
			return new PathEndpoint(adapter.getProperty(), adapter.getSource());
		} catch (JaxenException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidEndpointException e) {
			throw new IllegalArgumentException(e);
		}
    }
    
    /**
     * Returns the containing {@link DataObject data object}
     * or <code>null</code> if there is no container.
     * @return the containing data object or <code>null</code>.
     */
    public DataObject getContainer() {
        return this.container;
    }

   /**
    * Return the Property of the {@link DataObject data object} containing this data object
    * or <code>null</code> if there is no container.
    * @return the property containing this data object.
    */
    public Property getContainmentProperty() {
        return this.containmentProperty;
    }

    public DataObject getDataObject(String path) {
    	if (!DataGraphXPath.isXPath(path)) {
    		return this.getDataObject(this.getType().getProperty(path));
    	}
    	else {
    	    PathEndpoint endpoint = findEndpoint(path);
    	    if (endpoint != null)
    		    return endpoint.getSource().getDataObject(endpoint.getProperty());
    	    else
    		    return null;
    	}
    }
    
    public DataObject getDataObject(int propertyIndex) {
        Property property = (Property)this.getType().getProperties().get(
                propertyIndex);       
        return (DataObject)this.get(property);
    }

    public DataObject getDataObject(Property property) {
        return (DataObject)this.get(property);
    }

    /**
     * Returns a read-only List of the Properties currently used in this DataObject.
     * This list will contain all of the Properties in getType().getProperties()
     * and any Properties where isSet(property) is true.
     * For example, Properties resulting from the use of
     * open or mixed XML content are present if allowed by the Type.
     * the List does not contain duplicates. 
     * The order of the Properties in the List begins with getType().getProperties()
     * and the order of the remaining Properties is determined by the implementation.
     * The same list will be returned unless the DataObject is updated so that 
     * the contents of the List change.
     * @return the List of Properties currently used in this DataObject.
     */
    public List getInstanceProperties() {
    	List<Property> result = this.getType().getProperties();
        // FIXME: define instance properties in a 'base type'
        return result;
    }

    /**
     * Returns the named Property from the current instance properties,
     * or null if not found.  The instance properties are getInstanceProperties().  
     * @param propertyName the name of the Property
     * @return the named Property from the DataObject's current instance properties, or null.
     */
    public Property getInstanceProperty(String propertyName) {
        // TODO Auto-generated method stub
       // FIXME: define instance properties in a 'base type'
        return null;
    }
    
    /**
     * @see #getSequence()
     * Returns the value of a <code>Sequence</code> property identified by the specified path.
     * An implementation may throw an UnsupportedOperationException.
     * @param path the path to a valid object and property.
     * @return the <code>Sequence</code> value of the specified property.
     * @see #get(String)
     * @deprecated in 2.1.0.
     */
    public Sequence getSequence(String path) {
    	DataObject dataObject = this.getDataObject(path);
    	if (dataObject != null) {
    	    if (dataObject.getType().isSequenced())
    		    return dataObject.getSequence(); 
    	    else
        	    throw new UnsupportedOperationException(dataObject.getType().getURI()
        	    		+ "#" + dataObject.getType().getName()
        	    		+ " is not a sequenced type");	
    	}
        return null;
    }

    /**
     * @see #getSequence()
     * Returns the value of a <code>Sequence</code> property identified by the specified property index.
     * An implementation may throw an UnsupportedOperationException.
     * @param propertyIndex the index of the property.
     * @return the <code>Sequence</code> value of the specified property.
     * @see #get(int)
     * @deprecated in 2.1.0.
     */
    public Sequence getSequence(int propertyIndex) {
    	DataObject dataObject = this.getDataObject(propertyIndex);
    	if (dataObject != null) {
    	    if (dataObject.getType().isSequenced())
    		    return dataObject.getSequence(); 
    	    else
        	    throw new UnsupportedOperationException(dataObject.getType().getURI()
        	    		+ "#" + dataObject.getType().getName()
        	    		+ " is not a sequenced type");	
    	}
        return null;
    }

    /**
     * @see #getSequence()
     * Returns the value of the specified <code>Sequence</code> property.
     * An implementation may throw an UnsupportedOperationException.
     * @param property the property to get.
     * @return the <code>Sequence</code> value of the specified property.
     * @see #get(Property)
     * @deprecated in 2.1.0.
     */
    public Sequence getSequence(Property property) {
    	if (!property.getType().isDataType())
    	    throw new UnsupportedOperationException(this.getType().getURI()
    	    		+ "#" + this.getType().getName() + "." + property.getName()
    	    		+ " is a data type property");	
    	DataObject dataObject = this.getDataObject(property);
    	if (dataObject != null) {
    	    if (dataObject.getType().isSequenced())
    		    return dataObject.getSequence(); 
    	    else
        	    throw new UnsupportedOperationException(dataObject.getType().getURI()
        	    		+ "#" + dataObject.getType().getName()
        	    		+ " is not a sequenced type");	
    	}
        return null;
    }

    /**
     * Returns the <code>Sequence</code> for this DataObject.
     * When getType().isSequencedType() == true,
     * the Sequence of a DataObject corresponds to the
     * XML elements representing the values of its Properties.
     * Updates through DataObject and the Lists or Sequences returned
     * from DataObject operate on the same data.
     * When getType().isSequencedType() == false, null is returned.  
     * @return the <code>Sequence</code> or null.
     */
    public Sequence getSequence() {
	    throw new UnsupportedOperationException(this.getType().getURI()
	    		+ "#" + this.getType().getName()
	    		+ " is not a sequenced type");	
    }
       
    /**
     * Sync up the state of this data object to that of it's data store
     * given the map of UUID's and associated info resulting from
     * a successful commit.   
     * @param idMap - the hash of UUID's mapped to newly inserted pk's
     * @param username - the user who committed the modifications.
     */
     public void reset(SnapshotMap idMap, String username)
     {
         if (this.getDataGraph().getChangeSummary().isCreated(this))
         {
        	 List<PropertyPair> props = idMap.get(this.getUUID()); 
        	 if (props != null) {
            	 for (PropertyPair pair : props) { 
            		 if (!pair.getProp().isReadOnly()) {
            			 this.set(pair.getProp(), pair.getValue());
            		 }
            		 else {
            		     valueObject.put(pair.getProp().getName(), pair.getValue());
            		 }
                 }
        	 }
        	 else 
                 if (log.isDebugEnabled())
                     log.debug("no PK value mapped to UUID '" + uuid + "' for entity '"   
                         + this.getType().getName() + "' - ignoring");                                                                              
             
             // FIXME - could be a reference to a user
             Property originationUserProperty = ((PlasmaType)this.getType()).findProperty(ConcurrencyType.origination, 
                     	ConcurrentDataFlavor.user);
             if (originationUserProperty != null)
            	 valueObject.put(originationUserProperty.getName(), username); 
             else
                 if (log.isDebugEnabled()) 
                     log.debug("could not find origination (username) property for type, "
                         + this.getType().getURI() + "#" + this.getType().getName());  
             
             Property originationTimestampProperty = ((PlasmaType)this.getType()).findProperty(ConcurrencyType.origination, 
                      	ConcurrentDataFlavor.time);
             if (originationTimestampProperty != null) {
             	 // Convert to date which is convert-able to
            	 // all possible SDO 2.1 temporal data-types
            	 Date dateSnapshot = new Date(
             		  idMap.getSnapshotDate().getTime());
            	 // convert date to the target type
                 Object snapshot = DataConverter.INSTANCE.convert(
                	  originationTimestampProperty.getType(), dateSnapshot);
                 valueObject.put(originationTimestampProperty.getName(), 
            	     snapshot);
             }
             else
                 if (log.isDebugEnabled()) 
                     log.debug("could not find origination timestamp property for type, "
                         + this.getType().getURI() + "#" + this.getType().getName());  

             valueObject.put(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, idMap.getSnapshotDate());
         }
         else if (this.getDataGraph().getChangeSummary().isModified(this))
         {       	 
             Property concurrencyUserProperty = ((PlasmaType)this.getType()).findProperty(ConcurrencyType.optimistic, 
                  	ConcurrentDataFlavor.user);
             if (concurrencyUserProperty != null)
            	 valueObject.put(concurrencyUserProperty.getName(), username);
             else
                 if (log.isDebugEnabled())
                     log.debug("could not find optimistic concurrency (username) property for type, "
                         + this.getType().getURI() + "#" + this.getType().getName());  
             
             Property concurrencyVersionProperty = ((PlasmaType)this.getType()).findProperty(ConcurrencyType.optimistic, 
                   	ConcurrentDataFlavor.version);
             if (concurrencyVersionProperty != null)
            	 valueObject.put(concurrencyVersionProperty.getName(), idMap.getSnapshotDate());
             else
                 if (log.isDebugEnabled())
                     log.debug("could not find optimistic concurrency version property for type, "
                         + this.getType().getURI() + "#" + this.getType().getName());  
        	 
             //reset the locked information to match the values in the database if the object is locked
             if (valueObject.isFlaggedLocked()) 
             {
                 Property lockingUserProperty = (Property)this.getType().get(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_LOCKING_USER);
                 if (lockingUserProperty != null)
                	 valueObject.put(lockingUserProperty.getName(), username); 
                 else
                     if (log.isDebugEnabled())
                         log.debug("could not find locking user property for type, "
                             + this.getType().getURI() + "#" + this.getType().getName());  
                 Property lockingTimestampProperty = (Property)this.getType().get(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_LOCKING_TIMESTAMP);
                 if (lockingTimestampProperty != null) {
                 	 // Convert to date which is convert-able to
                	 // all possible SDO 2.1 temporal data-types
                	 Date dateSnapshot = new Date(
                 		  idMap.getSnapshotDate().getTime());
                	 // convert date to the target type
                     Object snapshot = DataConverter.INSTANCE.convert(
                    		 lockingTimestampProperty.getType(), dateSnapshot);
                	 valueObject.put(lockingTimestampProperty.getName(), snapshot);
                 }
                 else
                     if (log.isDebugEnabled())
                         log.debug("could not find locking timestamp property for type, "
                             + this.getType().getURI() + "#" + this.getType().getName());  
             }

             valueObject.put(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, idMap.getSnapshotDate());
         }
         else if (this.getDataGraph().getChangeSummary().isDeleted(this))
         {
             valueObject.put(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, idMap.getSnapshotDate());
         }
     }
     
     private Property findCachedProperty(PlasmaType type, Property instanceProp) {
     	List<Object> result = type.search(instanceProp);
         if (result != null && result.size() >0) {
         	if (result.size() > 1)
         		log.warn("expected single value for instance property '"
         				+ instanceProp.getName() + "' withing type '"
         				+ type.getURI() + "#" + type.getName() + "' and all its base types");
         	Object obj = result.get(0);
         	if (obj instanceof Property) {
         		return (Property)obj;
         	}
         	else
         		log.warn("expected value for instance property '"
         				+ instanceProp.getName() + "' for type '"
         				+ type.getURI() + "#" + type.getName() 
         				+ "' or one of its base types to be a instnace of class, "
         				+ Property.class.getName());
         }
         return null;
     }

     public void remove()
     {
         final String thisKey = ((PlasmaNode)this).getUUIDAsString();
         if (log.isDebugEnabled())
             log.debug("removing " + thisKey);
         
         List<Property> properties = this.getType().getProperties();
         for (Property property : properties) {
             if (!property.getType().isDataType()) {
                 Property targetProperty = property.getOpposite();
                 if (!targetProperty.isMany())
                 {
                     DataObject target = this.getDataObject(targetProperty);                    
                     if (target != null) {
                         if (log.isDebugEnabled())
                             log.debug("removing " + ((PlasmaNode)this).getUUIDAsString()
                                 + " from " + property.getName() + "->"
                                 + ((PlasmaNode)target).getUUIDAsString());
                         target.unset(targetProperty);   
                     }
                 }
                 else
                 {
                     List<DataObject> list = this.getList(targetProperty);
                     if (list == null) 
                         continue;
                     
                     int toRemove = -1;
                     DataObject target = null;
                     for (int i = 0; i < list.size(); i++) {
                         DataObject targetDataObject = list.get(i);
                         PlasmaNode targetNode = (PlasmaNode)targetDataObject;
                         if (log.isDebugEnabled())
                             log.debug("checking source "
                                 + ((PlasmaNode)this).getUUIDAsString()
                                 + " against node "
                                 + targetNode.getUUIDAsString());
                         if (targetNode.getUUIDAsString().equals(thisKey)) {
                             toRemove = i;
                             target = targetDataObject;
                         }
                     }
                     
                     if (toRemove > 0) {
                         log.info("removing " + ((PlasmaNode)this).getUUIDAsString()
                                 + " from list " + property.getName() + "->"
                                 + ((PlasmaNode)target).getUUIDAsString());
                         list.remove(toRemove);
                         target.set(targetProperty, list);
                     }
                     else
                         log.warn("could not remove " + ((PlasmaNode)this).getUUIDAsString()
                                 + " from list " + property.getName() + "->"
                                 + ((PlasmaNode)target).getUUIDAsString());
                 }   
                 
                 this.unset(property);
             }
             else if (!property.isReadOnly())
                 this.unset(property);
         }
     }
     
     /**
      * Begin breadth-first traversal of a DataGraph with this DataObject as the graph root, the given
      * visitor receiving "visit" events for each graph node traversed.  
      * @param visitor the graph visitor receiving traversal events
      * @see commonj.sdo.DataGraph
      * @see commonj.sdo.DataObject
      * @see PlasmaDataGraph 
      * @see PlasmaDataObject 
      * @see PlasmaDataGraphVisitor.visit()
      */
     public void accept(PlasmaDataGraphVisitor visitor)
     {
         accept(visitor, this, null, null, this, false, 0, -1,
        		 new HashMap<CoreDataObject, HashSet<CoreDataObject>>());
     }

     /**
      * Begin breadth-first traversal of a DataGraph with this DataObject as the graph root, the given
      * visitor receiving "visit" events for each graph node traversed.  
      * @param visitor the graph visitor receiving traversal events
      * @param maxLevel the maximum number of "hierarchical" levels to traverse 
      * @see commonj.sdo.DataGraph
      * @see commonj.sdo.DataObject
      * @see PlasmaDataGraph 
      * @see PlasmaDataObject 
      * @see PlasmaDataGraphVisitor.visit()
      */
     public void accept(PlasmaDataGraphVisitor visitor, int maxLevel)
     {
         accept(visitor, this, null, null, this, false, 0, maxLevel,
        		 new HashMap<CoreDataObject, HashSet<CoreDataObject>>());
     }
     
     /**
      * Begin depth-first traversal of a DataGraph with this DataObject as the graph root, the given
      * visitor receiving "visit" events for each graph node traversed.  
      * @param visitor the graph visitor receiving traversal events
      * @see commonj.sdo.DataGraph
      * @see commonj.sdo.DataObject
      * @see PlasmaDataGraph 
      * @see PlasmaDataObject 
      * @see PlasmaDataGraphVisitor.visit()
      */
     public void acceptDepthFirst(PlasmaDataGraphVisitor visitor)
     {
         accept(visitor, this, null, null, this, true, 0, -1,
        		 new HashMap<CoreDataObject, HashSet<CoreDataObject>>());
     }

     // TODO: add abort traversal capability
     private void accept(PlasmaDataGraphVisitor visitor, CoreDataObject target, CoreDataObject source, String sourceKey, 
             PlasmaDataObject root, boolean depthFirst, int level, int maxLevel,
             HashMap<CoreDataObject, HashSet<CoreDataObject>> visitedObjects)
     {
         //if (log.isTraceEnabled())
         //    if (source == null)
         //        log.trace(String.valueOf(level) + "node: " + target.getType().getName() + "(" + target.getHashKey() + ")");
         //    else
         //        log.trace(String.valueOf(level) + "node: " + target.getType().getName() + "( " + target.getHashKey() + ") \tSRC: " + source.getType().getName() + "." + sourceKey+ "(" + source.getHashKey() + ")");

    	 
         if(source != null)
         {
             //Lets make sure the parent's hashset is setup
             if (!visitedObjects.containsKey(source))
                 visitedObjects.put(source, new HashSet<CoreDataObject>());
             
             //Now lets see if the current child has ever been visited.
             HashSet<CoreDataObject> visitedChildren = (HashSet<CoreDataObject>)visitedObjects.get(source);
             
             if (!visitedChildren.contains(target))
             {
                 visitedChildren.add(target);
             }
             else
             {
                 //if (log.isTraceEnabled())
                 //    log.trace("already visited relationship - parent object: " 
                 //            + source + " - child Object - "+ target + "  - skipping duplicate accept.");
                 return;
             }
         }
                  
         if (!depthFirst && (maxLevel == -1 || level <= maxLevel))
             visitor.visit(target, source, sourceKey, level);
  
         List<Property> properties = target.getType().getProperties();
         for (Property property : properties)
         {
             if (property.getType().isDataType())
                 continue; // not a reference property
             
             //if (log.isTraceEnabled())
             //    log.trace("property: " + property.getName()); 
             
             if (!property.isMany()) 
             {
                  PlasmaEdge edge = (PlasmaEdge)target.getValue(property.getName());
                  if (edge != null && edge.canTraverse(target)) {
                      PlasmaNode node = edge.getRight();
                      CoreDataObject child = (CoreDataObject)node.getDataObject();                
                      if (!child.equals(root))
                      {
                          //if (log.isTraceEnabled())
                          //    log.trace("accept: " + property.getName());
                          child.accept(visitor, child, target, property.getName(), root, 
                                  depthFirst, level+1, maxLevel, visitedObjects);
                      }
                  }
             }
             else
             {
                 List<PlasmaEdge> edgeList = (List<PlasmaEdge>) target.getValue(property.getName());  
                 if (edgeList != null) {                    
                     PlasmaEdge[] edgeArray = new PlasmaEdge[edgeList.size()];
                     edgeList.toArray(edgeArray); // avoids collection concurrent mod errors on recursion
                     for (int i = 0; i < edgeArray.length; i++)
                     {
                         PlasmaEdge edge = edgeArray[i];
                         if (edge.canTraverse(target)) 
                         {    
                             PlasmaNode node = edge.getRight();
                             CoreDataObject child = (CoreDataObject)node.getDataObject();                
                             if (!child.equals(root))
                             {
                                 //if (log.isTraceEnabled())
                                 //    log.trace("accept: " + property.getName());
                                 child.accept(visitor, child, target, property.getName(), root, 
                                     depthFirst, level+1, maxLevel, visitedObjects);
                             }                             
                         }
                     }
                 }
             }
         }
         
         if (depthFirst && (maxLevel == -1 || level <= maxLevel))
             visitor.visit(target, source, sourceKey, level);
    }
     
	/**
	 * Begin breadth-first traversal of a DataGraph with this DataObject as the
	 * graph root, the given visitor receiving various events for each graph
	 * node traversed.
	 * 
	 * @param visitor
	 *            the graph visitor receiving traversal events
	 * @see commonj.sdo.DataGraph
	 * @see commonj.sdo.DataObject
	 * @see PlasmaDataGraph
	 * @see PlasmaDataObject
	 * @see PlasmaDataGraphVisitor.visit()
	 */
	public void accept(PlasmaDataGraphEventVisitor visitor) {
		accept(visitor, this, null, null, this, 0,
				new HashMap<CoreDataObject, HashSet<CoreDataObject>>(), 0);
	}

	@SuppressWarnings("unchecked")
	private void accept(PlasmaDataGraphEventVisitor visitor,
			CoreDataObject target, CoreDataObject source, String sourceKey,
			CoreDataObject root, int level,
			HashMap<CoreDataObject, HashSet<CoreDataObject>> visitedObjects, int visitedCount) {
		
        if (log.isDebugEnabled())
            if (source == null)
                log.debug(String.valueOf(level) + "node: " + target.getType().getName() + "(" + target.getUUIDAsString() + ")");
            else
                log.debug(String.valueOf(level) + "node: " + target.getType().getName() + "( " + target.getUUIDAsString() + ") \tSRC: " + source.getType().getName() + "." + sourceKey+ "(" + source.getUUIDAsString() + ")");
		int currentVisitedCount = visitedCount;
		if (source != null) {
			// Even though we may have visited this source
			// object before, must traverse one level past
			// this point in order to call the 'end' method
			// such that client can terminate, say an XML tag. 
			if (!visitedObjects.containsKey(source)) {
				visitedObjects.put(source, new HashSet<CoreDataObject>());
			}			 

			// Now lets see if the current child has ever been visited.
			HashSet<CoreDataObject> visitedChildren = (HashSet<CoreDataObject>) visitedObjects
					.get(source);

			if (!visitedChildren.contains(target)) {
				visitedChildren.add(target);
			} else {
                if (log.isDebugEnabled())
                    log.debug("already visited child - parent object: " 
                            + source + " - child Object - " + target + "  - skipping duplicate accept.");
				currentVisitedCount = currentVisitedCount + 1;
				if (currentVisitedCount >= 1000) {
					throw new IllegalStateException("data graph contains a circularity: "
							+ source.getType().getName() 
	                        + "("+((PlasmaNode)source).getUUIDAsString()+ ")."
	                        + sourceKey + "->"
	                        + target.getType().getName() + "("+((PlasmaNode)target).getUUIDAsString()+")");
				}
                return;
			}
		}

		visitor.start(target, source, sourceKey, level);
		List<Property> list = target.getType().getProperties();
    	PlasmaProperty[] properties = new PlasmaProperty[list.size()];
    	list.toArray(properties);
    	Arrays.sort(properties, new Comparator<PlasmaProperty>() {
			public int compare(PlasmaProperty p1, PlasmaProperty p2) {
				if (p1.getSort() != null && p2.getSort() != null) {
					if (p1.getSort().getKey() != null && p2.getSort().getKey() != null)
					    return p1.getSort().getKey().compareTo(p2.getSort().getKey());
					else
					    return p1.getName().compareTo(p2.getName());
				}
				else
				    return p1.getName().compareTo(p2.getName());
			}
    	});		
		
		for (Property property : properties) {
			if (property.getType().isDataType())
				continue; // not a reference property
            if (log.isDebugEnabled())
                log.debug("property: " + property.getName()); 

			if (!property.isMany()) {
				PlasmaEdge edge = (PlasmaEdge) target.getValue(property.getName());
				if (edge != null && edge.canTraverse(target)) {
					PlasmaNode node = edge.getRight();
					CoreDataObject child = (CoreDataObject)node.getDataObject();					 
					if (!child.equals(root)) {
                        if (log.isDebugEnabled())
                            log.debug("accept: " + property.getName());
						child.accept(visitor, child, target,
							property.getName(), root, level + 1, 
							visitedObjects, currentVisitedCount);
					}
				}
			} else {
				List<PlasmaEdge> edgeList = (List<PlasmaEdge>) target
						.getValue(property.getName());
				if (edgeList != null) {
					// toArray - avoid concurrent mod errors on recursion
					PlasmaEdge[] edgeArray = new PlasmaEdge[edgeList.size()];
					edgeList.toArray(edgeArray); 
					for (int i = 0; i < edgeArray.length; i++) {
						PlasmaEdge edge = edgeArray[i];
						if (edge.canTraverse(target)) {
							PlasmaNode node = edge.getRight();
							CoreDataObject child = (CoreDataObject)node.getDataObject();
							if (!child.equals(root)) {
		                        if (log.isDebugEnabled())
		                            log.debug("accept: " + property.getName());
								child.accept(visitor, child, target, property
										.getName(), root, 
										level + 1, visitedObjects,
										currentVisitedCount);
							}
						}
					}
				}
			}
		}
		visitor.end(target, source, sourceKey, level);
	}
     
    public DataObject find(String key)
    {
        Finder finder = new Finder(key);
        this.accept(finder);
        return finder.getResult();
    }
     
    public String toString() {
        return this.type.toString() + "/" + this.getUUIDAsString();
    }

    public String dump() {
    	return dump(false);
    }

    public String dumpDepthFirst() {
    	return dump(true);
    }
    
    private String dump(boolean depthFirst) {
        final StringBuilder buf = new StringBuilder();
        PlasmaDataGraphVisitor visitor = new PlasmaDataGraphVisitor() {
            public void visit(DataObject target, DataObject source, String sourceKey, int level) {
                buf.append("\r\n");
                for (int i = 0; i < level + 1; i++)
                    buf.append("  ");
                if (sourceKey != null)
                {
                    buf.append("(" + String.valueOf(level) + ")" + sourceKey + ":" + target.getType().getURI() + "#" + target.getType().getName() + "[");
                }
                else
                    buf.append("(" + String.valueOf(level) + ")" + target.getType().getURI() + "#" + target.getType().getName() + "[");
                                
                String uuid = (String)((CoreDataObject)target).getUUIDAsString();
                if (uuid != null)
                    buf.append("<" + "__UUID__"
                            + ":" + uuid + ">");
                
                List<Property> properties = target.getType().getProperties();
                for (Property property : properties)
                {
                    if (!property.getType().isDataType())
                        continue; // covered by visitor traversal
                    Object value = target.get(property);
                    if (value != null)
                        buf.append("<" + property.getName() + ":" + String.valueOf(value) + ">");
                }               

                buf.append("]");
            }
        };
        if (!depthFirst)
            this.accept(visitor);
        else
            this.acceptDepthFirst(visitor);
        return buf.toString();
    }
    
    class Finder implements PlasmaDataGraphVisitor {

        private DataObject result;
        private String key;
        private Finder() {}
        public Finder(String key) {
            this.key = key;  
        } 
        public void visit(DataObject target, DataObject source, String sourceKey, int level) {
            if (((PlasmaNode)target).getUUIDAsString().equals(this.key))
                result = target;
        }

        public DataObject getResult() {
            return result;
        }
        
    }
}