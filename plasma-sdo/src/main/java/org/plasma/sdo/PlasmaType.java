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
package org.plasma.sdo;

import java.util.List;

import javax.xml.namespace.QName;

import org.plasma.sdo.profile.ConcurrencyType;
import org.plasma.sdo.profile.ConcurrentDataFlavor;
import org.plasma.sdo.profile.KeyType;
import org.plasma.sdo.repository.Classifier;
import org.plasma.sdo.repository.Comment;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;

/**
 * A representation of the type of a {@link Type type} of 
 * a {@link DataObject data object}.
 */
public interface PlasmaType extends Type {
    
    
	public List<Comment> getDescription(); 
    public Classifier getClassifier();
    public String getDescriptionText();
	
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
    public byte[] getNameBytes();

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
    public byte[] getURIBytes();
    
    /**
     * Returns the physical name alias of this Type as a string, or null if no physical alias name exists.
     * @return the physical name alias of this Type as a string, or null if no physical alias name exists.. 
     */    
    public String getPhysicalName();   

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
    public byte[] getPhysicalNameBytes();
    
    /**
     * Returns the local name alias of this Type as a string.
     * @return the local name alias of this Type as a string. 
     */    
    public String getLocalName(); 
    
    
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
    public byte[] getLocalNameBytes();
    
    /**
     * Return the namespace qualified logical name for this
     * type. This method is provided as using a QName
     * may be more efficient, depending on the client usage context, than performing
     * string concatenations, particularly
     * where hashing and hash lookups by qualified Type name are
     * required.      
     * @return the namespace qualified logical name for this
     * type. 
     */
    public QName getQualifiedName();
    
    
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
    public byte[] getQualifiedNameBytes();
    
    /**
     * Returns a qualified logical-name hash code 
     * for this type. 
     * @return a qualified logical-name hash code 
     * for this type. 
     */
    public int getQualifiedNameHashCode();
    
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
    public QName getQualifiedPhysicalName();
    
    /**
     * Returns the namespace qualified physical name of this Type as a byte array which may be cached
     * or lazily cached on demand. 
     * <p>
     * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access Services} for sparse, 
     * distributed "cloud" data stores typically storing lexicographically 
     * ordered row and column keys as uninterpreted arrays of bytes. Fast dynamic
     * construction of such keys is important as such services may necessarily construct
     * unique composite keys based in part on qualified or unqualified logical or physical
     * type names. 
     * </p>
     * @return the namespace qualified physical name of this Type as a byte array
     */
    public byte[] getQualifiedPhysicalNameBytes();
    
    /**
     * Returns the alias for this type
     * @return the alias
     */
    public Alias getAlias();
    
    /**
     * Returns an alphabetically sorted list of the Properties declared 
     * in this Type as opposed to
     * those declared in base Types. Properties are alphabetically sorted by name. 
     * @return the Properties declared in this Type.
     */
	public List<Property> getDeclaredPropertiesSorted();
    	
    /**
     * Collects and returns all values from this type and its base types 
     * mapped to the given instance property. 
     * @param property one of the properties returned by
     *            {@link #getInstanceProperties()}.
     * @return all values from this type and its base types 
     * mapped to the given instance property 
     */ 
    public List<Object> search(Property property);

    /**
     * Returns the declared property from this type and its base types with the
     * given property name, or null if not exists 
     * @param name the property name
     * @return the declared property from this type and its base types with the
     * given property name, or null if not exists 
     */
    public Property findProperty(String name);

    /**
     * Returns the declared property from this type and its base types tagged with the
     * given concurrency type and data flavor. 
     * @param concurrencyType the concurrency type
     * @param dataFlavor the concurrency data flavor
     * @return the declared property from this type and its base types tagged with the
     * given concurrency type and data flavor, or null if not exists 
     */
    public Property findProperty(ConcurrencyType concurrencyType, ConcurrentDataFlavor dataFlavor);
    //public Property findProperty(TemporalType type, TemporalState state);    
    
    /**
     * Returns the declared property from this type and its base types tagged with the
     * given key type. If more than one property is found, a warning is logged
     * and the first property is returned. 
     * @param keyType the key type
     * @return declared property from this type and its base types tagged with the
     * given key type, or null if not exists. If more than one property is found, a warning is logged
     * and the first property is returned. 
     */
    public Property findProperty(KeyType keyType);

    /**
     * Returns the declared property from this type and its base types tagged with the
     * given key type.  
     * @param keyType the key type
     * @return declared property from this type and its base types tagged with the
     * given key type, or null if not exists. 
     */
    public List<Property> findProperties(KeyType keyType);
    
    /**
     * Returns a Java class for the given SDO data-type (as 
     * per the SDO Specification 2.10 Section 8.1). 
     * @param dataType
     * @return the SDO Java class.
     */
    public Class<?> toDataTypeInstanceClass(DataType dataType);
          
    /**
     * Returns true if the given type is a base type for this
     * type. 
     * @param other the base type candidate
     * @return true if the given type is a base type for this
     * type. 
     */
    public boolean isBaseType(PlasmaType other);
    
    /**
     * Returns a list of types which specialize or inherit from
     * this type. An empty list is returned if no sub types exist. 
     * @return a list of types which specialize or inherit from
     * this type. An empty list is returned if no sub types exist. 
     */
    public List<Type> getSubTypes();

    /**
     * Returns true if the given type is a specialization or 
     * inherits from this type. 
     * @param other the sub type candidate
     * @return true if the given type is a specialization or 
     * inherits from this type.
     */
    public boolean isSubType(PlasmaType other);
    
    /**
     * Returns true if the given type is linked or related to this type 
     * across any number of traversals or hops as qualified by the given
     * relation path type.  
     * @param other the other type
     * @param relationPath the relation path type
     * @return true if the given type is linked or related to this type 
     * across any number of traversals or hops as qualified by the given
     * relation path type.
     */
    public boolean isRelation(PlasmaType other, AssociationPath relationPath);
    
     
}
