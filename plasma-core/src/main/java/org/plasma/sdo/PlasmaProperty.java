/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.sdo;

import java.util.List;

import org.plasma.sdo.core.CoreConstants;
import org.plasma.sdo.core.CoreProperty;
import org.plasma.sdo.profile.ConcurrencyType;
import org.plasma.sdo.profile.ConcurrentDataFlavor;
import org.plasma.sdo.profile.KeyType;
import org.plasma.sdo.repository.Comment;
import org.plasma.sdo.repository.Enumeration;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;

/**
 * A representation of a Property in the {@link Type type} of a
 * {@link DataObject data object}.
 */
public interface PlasmaProperty extends Property {

  // instance properties on DataObject
  public static final PlasmaProperty INSTANCE_PROPERTY_STRING_UUID = CoreProperty
      .createInstanceProperty(CoreConstants.PROPERTY_NAME_UUID_STRING, DataType.String);
  public static final PlasmaProperty INSTANCE_PROPERTY_LONG_SNAPSHOT_TIMESTAMP = CoreProperty
      .createInstanceProperty(CoreConstants.PROPERTY_NAME_SNAPSHOT_TIMESTAMP, DataType.Long);

  public static final PlasmaProperty INSTANCE_PROPERTY_BOOLEAN_ISOPERATIONAL = CoreProperty
      .createInstanceProperty("IsOperational", DataType.Boolean);
  // public static PlasmaProperty INSTANCE_PROPERTY_BOOLEAN_ISLOGGING =
  // createInstanceProperty("IsLogging", DataType.Boolean);

  public static final PlasmaProperty INSTANCE_PROPERTY_INT_MAXLENGTH = CoreProperty
      .createInstanceProperty("MaxLength", DataType.Int);
  public static final PlasmaProperty INSTANCE_PROPERTY_BOOLEAN_ISUNIQUE = CoreProperty
      .createInstanceProperty("IsUnique", DataType.Boolean);

  // instance properties on Type
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_PRIKEY_PROPERTIES = CoreProperty
      .createInstanceProperty("PriKeyProperties", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_CONCURRENCY_USER = CoreProperty
      .createInstanceProperty("ConcurrencyUser", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_CONCURRENCY_VERSION = CoreProperty
      .createInstanceProperty("ConcurrencyVersion", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_LOCKING_USER = CoreProperty
      .createInstanceProperty("LockingUser", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_LOCKING_TIMESTAMP = CoreProperty
      .createInstanceProperty("LockingTimestamp", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_ORIGINATION_USER = CoreProperty
      .createInstanceProperty("OriginationUser", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_ORIGINATION_TIMESTAMP = CoreProperty
      .createInstanceProperty("OriginationTimestamp", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_DESCRIPTION = CoreProperty
      .createInstanceProperty("Description", DataType.String);
  public static final PlasmaProperty INSTANCE_PROPERTY_STRING_NAMESPACE_URI = CoreProperty
      .createInstanceProperty("NamespaceURI", DataType.String);
  public static final PlasmaProperty INSTANCE_PROPERTY_STRING_NAMESPACE_PHYSICAL_NAME = CoreProperty
      .createInstanceProperty("NamespacePhysicalName", DataType.String);

  // instance properties used on Properties and/or Types
  public static final PlasmaProperty INSTANCE_PROPERTY_STRING_PHYSICAL_NAME = CoreProperty
      .createInstanceProperty("PhysicalName", DataType.String);
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_VISIBILITY = CoreProperty
      .createInstanceProperty("Visibility", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_STRING_LOCAL_NAME = CoreProperty
      .createInstanceProperty("LocalName", DataType.String);
  public static final PlasmaProperty INSTANCE_PROPERTY_STRING_BUSINESS_NAME = CoreProperty
      .createInstanceProperty("BusinessName", DataType.String);

  public static final PlasmaProperty INSTANCE_PROPERTY_BYTES_NAME_BYTES = CoreProperty
      .createInstanceProperty("NameBytes", DataType.Bytes);
  public static final PlasmaProperty INSTANCE_PROPERTY_BYTES_URI_BYTES = CoreProperty
      .createInstanceProperty("URIBytes", DataType.Bytes);
  public static final PlasmaProperty INSTANCE_PROPERTY_BYTES_PACKAGE_PHYSICAL_NAME_BYTES = CoreProperty
      .createInstanceProperty("PackagePhysicalNameBytes", DataType.Bytes);
  public static final PlasmaProperty INSTANCE_PROPERTY_BYTES_PHYSICAL_NAME_BYTES = CoreProperty
      .createInstanceProperty("PhysicalNameBytes", DataType.Bytes);
  public static final PlasmaProperty INSTANCE_PROPERTY_BYTES_LOCAL_NAME_BYTES = CoreProperty
      .createInstanceProperty("LocalNameBytes", DataType.Bytes);

  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_QUALIFIED_NAME = CoreProperty
      .createInstanceProperty("QualifiedName", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_BYTES_QUALIFIED_NAME_BYTES = CoreProperty
      .createInstanceProperty("QualifiedNameBytes", DataType.Bytes);
  public static final PlasmaProperty INSTANCE_PROPERTY_BYTES_QUALIFIED_PHYSICAL_NAME_BYTES = CoreProperty
      .createInstanceProperty("QualifiedPhysicalNameBytes", DataType.Bytes);

  // FIXME: not used - seemingly should be instance properties on a base type
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_SNAPSHOT_DATE_PROPERTY = CoreProperty
      .createInstanceProperty("SnapshotDateProperty", DataType.Object);
  public static final PlasmaProperty INSTANCE_PROPERTY_OBJECT_IS_LOCKED_PROPERTY = CoreProperty
      .createInstanceProperty("IsLockedProperty", DataType.Object);

  /**
   * For data type properties, returns the data flavor associated with the
   * property data type.
   * 
   * @throws UnsupportedOperationException
   *           when the property is not a data type property i.e. when
   *           property.getType().isDataType() returns false.
   * @return the data flavor associated with the property data type
   */
  public DataFlavor getDataFlavor();

  public List<Comment> getDescription();

  public String getDescriptionText();

  public Enumeration getRestriction();

  /**
   * Returns true if this property is rendered within an XML document as an XML
   * attribute. This determination is based on 1.) whether the XmlProperty
   * information exists for this property and the {@link XmlNodeType nodeType}
   * is 'attribute' and then 2.) on a simple algorithm based on the data type
   * and data flavor.
   * 
   * @return true if this property is rendered within an XML document as an XML
   *         attribute.
   * @see XmlProperty
   * @see XmlNodeType
   */
  public boolean isXMLAttribute();

  /**
   * Returns the alias for this property if exists, or null if not exists
   * 
   * @return the alias, or null if not exists
   * @see Alias
   */
  public Alias getAlias();

  /**
   * Returns the logical name of this Property as a byte array which may be
   * cached or lazily cached on demand.
   * <p>
   * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access
   * Services} for sparse, distributed "cloud" data stores typically storing
   * lexicographically ordered row and column keys as uninterpreted arrays of
   * bytes. Fast dynamic construction of such keys is important as such services
   * may necessarily construct unique composite keys based in part on qualified
   * or unqualified logical or physical type names.
   * </p>
   * 
   * @return the logical name alias as a byte array.
   */
  public byte[] getNameBytes();

  /**
   * Returns the physical name alias for this property if exists, or null if not
   * exists
   * 
   * @return the physical name alias, or null if not exists
   * @see Alias
   */
  public String getPhysicalName();

  /**
   * Returns the physical name alias of this Property as a byte array which may
   * be cached or lazily cached on demand, or null if no physical alias name
   * exists.
   * <p>
   * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access
   * Services} for sparse, distributed "cloud" data stores typically storing
   * lexicographically ordered row and column keys as uninterpreted arrays of
   * bytes. Fast dynamic construction of such keys is important as such services
   * may necessarily construct unique composite keys based in part on qualified
   * or unqualified logical or physical type names.
   * </p>
   * 
   * @return the physical name alias of this Property as a byte array, or null
   *         if no physical alias name exists.
   */
  public byte[] getPhysicalNameBytes();

  /**
   * Returns the local name alias for this Property if exists, or otherwise the
   * property name
   * 
   * @return the local name alias for this Property if exists, or otherwise the
   *         property name
   * @see Alias
   */
  public String getLocalName();

  /**
   * Returns the local name of this Property as a byte array which may be cached
   * or lazily cached on demand.
   * <p>
   * Helps support {@link org.plasma.sdo.access.DataAccessService Data Access
   * Services} for sparse, distributed "cloud" data stores typically storing
   * lexicographically ordered row and column keys as uninterpreted arrays of
   * bytes. Fast dynamic construction of such keys is important as such services
   * may necessarily construct unique composite keys based in part on qualified
   * or unqualified logical or physical type names.
   * </p>
   * 
   * @return the local name of this Property as a byte array
   */
  public byte[] getLocalNameBytes();

  /**
   * Returns the key information for this property if exists, or null if not
   * exists
   * 
   * @return the key information, or null if not exists
   * @see Key
   */
  public Key getKey();

  /**
   * Returns true if the key information for this property if exists.
   * 
   * @param keyType
   *          the key type
   * @return true if the key information for this property if exists
   * @see Key
   */
  public boolean isKey();

  /**
   * Returns true if the key information for this property if exists and the key
   * type is set to the given type.
   * 
   * @param keyType
   *          the key type
   * @return true if the key information for this property if exists and the key
   *         type is set to the given type
   * @see KeyType
   */
  public boolean isKey(KeyType keyType);

  /**
   * Returns the key supplier property for this property or null if not exists.
   * 
   * @return the key supplier property for this property or null if not exists.
   */
  public PlasmaProperty getKeySupplier();

  /**
   * Returns the concurrent information for this property if exists, or null if
   * not exists
   * 
   * @return the concurrent information, or null if not exists
   * @see Concurrent
   */
  public Concurrent getConcurrent();

  /**
   * Returns true if this property is tagged as concurrent.
   * 
   * @return true if this property is tagged as concurrent.
   */
  public boolean isConcurrent();

  /**
   * Returns true if this property is tagged as concurrent for the given
   * concurrency type and data flavor.
   * 
   * @param type
   *          the concurrency type
   * @param dataFlavor
   *          concurrency data flavor
   * @return true if this property is tagged as concurrent for the given
   *         concurrency type and data flavor.
   */
  public boolean isConcurrent(ConcurrencyType type, ConcurrentDataFlavor dataFlavor);

  /**
   * Returns the temporal information for this property if exists, or null if
   * not exists
   * 
   * @return the temporal information, or null if not exists
   * @throws IllegalArgumentException
   *           if the property's type is not a data type, e.g. if
   *           property.getType().isDataType() returns false.
   * @see Temporal
   */
  public Temporal getTemporal();

  /**
   * Returns the enumeration constraint information for this property if exists,
   * or null if not exists
   * 
   * @return the enumeration constraint information, or null if not exists
   * @throws IllegalArgumentException
   *           if the property's type is not a data type, e.g. if
   *           property.getType().isDataType() returns false.
   * @see Temporal
   */
  public EnumerationConstraint getEnumerationConstraint();

  /**
   * Returns the value-set constraint information for this property if exists,
   * or null if not exists
   * 
   * @return the value-set constraint information, or null if not exists
   * @throws IllegalArgumentException
   *           if the property's type is not a data type, e.g. if
   *           property.getType().isDataType() returns false.
   * @see ValueSetConstraint
   */
  public ValueSetConstraint getValueSetConstraint();

  /**
   * Returns the sorting information for this property if exists, or null if not
   * exists
   * 
   * @return the sorting information, or null if not exists
   * @see Sequence
   */
  public Sort getSort();

  /**
   * Returns the maximum length allowed this property, or -1 if no maximum
   * length is defined.
   * 
   * @return the maximum length or -1 if no maximum length is defined
   */
  public long getMaxLength();

  /**
   * Returns the value constraint information for this property if exists, or
   * null if not exists
   * 
   * @return the value constraint, or null if not exists
   * @throws IllegalArgumentException
   *           if the property's type is not a data type, e.g. if
   *           property.getType().isDataType() returns false.
   * @see ValueConstraint
   */
  public ValueConstraint getValueConstraint();

  /**
   * Returns the unique constraint information for this property if exists, or
   * null if not exists
   * 
   * @return the unique constraint information, or null if not exists
   * @throws IllegalArgumentException
   *           if the property's type is not a data type, e.g. if
   *           property.getType().isDataType() returns false.
   * @see ValueConstraint
   */
  public UniqueConstraint getUniqueConstraint();

  /**
   * Returns the XML property information for this property if exists, or null
   * if not exists
   * 
   * @return the XML property information, or null if not exists
   * @throws IllegalArgumentException
   *           if the property's type is not a data type, e.g. if
   *           property.getType().isDataType() returns false.
   * @see XmlProperty
   */
  public XmlProperty getXmlProperty();

  /**
   * Returns the derivation information for this property if exists, or null if
   * not exists
   * 
   * @return the derivation information, or null if not exists
   */
  public Derivation getDerivation();

  /**
   * Returns the derivation supplier property for this property or null if not
   * exists.
   * 
   * @return the derivation supplier property for this property or null if not
   *         exists.
   */
  public PlasmaProperty getDerivationSupplier();

  /**
   * Returns the compression information for this property if exists, or null if
   * not exists
   * 
   * @return the compression information, or null if not exists
   * @see Compression
   */
  public Compression getCompression();

  /**
   * Returns the increment information for this property if exists, or null if
   * not exists
   * 
   * @return the increment information, or null if not exists
   * @see Increment
   */
  public Increment getIncrement();

  /**
   * Returns true if the increment information for this property exists.
   * 
   * @return true if the increment information for this property exists
   * @see Increment
   */
  public boolean isIncrement();

  /**
   * Returns the unique id for the element within its meta data repository.
   * 
   * @return the unique id for the element within its meta data repository.
   */
  public String getId();

}
