package org.plasma.provisioning.rdb.oracle.g11.sys;

import org.plasma.sdo.PlasmaDataObject;

/**
 * A constraint definition
 * <p>
 * </p>
 * Generated interface representing the domain model entity <b>Constraint</b>.
 * This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)
 * and is part of namespace <b>http://org.plasma/sdo/oracle/11g/sys</b> defined
 * within the <a href=
 * "http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html"
 * >Configuration</a>.
 *
 * <p>
 * </p>
 * <b>Data Store Mapping:</b> Corresponds to the physical data store entity
 * <b>ALL_CONSTRAINTS</b>.
 * <p>
 * </p>
 *
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.Table Table
 */
public interface Constraint extends PlasmaDataObject {
  /**
   * The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with
   * the <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">
   * Type</a> for this class.
   */
  public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

  /**
   * The entity or <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a> logical name associated with this class.
   */
  public static final String TYPE_NAME_CONSTRAINT = "Constraint";

  /**
   * The declared logical property names for this <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a>.
   */
  public static enum PROPERTY {

    /**
     * Owner of the constraint definition
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>owner</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.OWNER</b>.
     */
    owner,

    /**
     * The reference to the owning table
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>table</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.TABLE_NAME</b>.
     */
    table,

    /**
     * Name of the constraint definition
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>constraintName</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.CONSTRAINT_NAME</b>.
     */
    constraintName,

    /**
     * Type of constraint definition: C (check constraint on a table) P (primary
     * key) U (unique key) R (referential integrity) V (with check option, on a
     * view) O (with read only, on a view)
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>constraintType</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.CONSTRAINT_TYPE</b>.
     */
    constraintType,

    /**
     * Text of search condition for a check constraint
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>searchCondition</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.SEARCH_CONDITION</b>.
     */
    searchCondition,

    /**
     * Owner of table referred to in a referential constraint
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>refOwner</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.R_OWNER</b>.
     */
    refOwner,

    /**
     * Name of the unique constraint definition for referenced table
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>refConstraintName</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.R_CONSTRAINT_NAME</b>.
     */
    refConstraintName,

    /**
     * Delete rule for a referential constraint (CASCADE or NO ACTION)
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>deleteRule</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.DELETE_RULE</b>.
     */
    deleteRule,

    /**
     * Name of the user owning the index
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>indexOwner</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.INDEX_OWNER</b>.
     */
    indexOwner,

    /**
     * Name of the index (only shown for unique and primary-key constraints)
     * <p>
     * </p>
     *
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>indexName</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Constraint</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>ALL_CONSTRAINTS.INDEX_NAME</b>.
     */
    indexName
  }

  /**
   * Returns true if the <b>owner</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getOwner() getOwner} or
   * {@link #setOwner(String value) setOwner(...)} for a definition of property
   * <b>owner</b>
   * 
   * @return true if the <b>owner</b> property is set.
   */
  public boolean isSetOwner();

  /**
   * Unsets the <b>owner</b> property, the value of the property of the object
   * being set to the property's default value. The property will no longer be
   * considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getOwner() getOwner} or
   * {@link #setOwner(String value) setOwner(...)} for a definition of property
   * <b>owner</b>
   */
  public void unsetOwner();

  /**
   * Returns the value of the <b>owner</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> Owner of the constraint definition
   * 
   * @return the value of the <b>owner</b> property.
   */
  public String getOwner();

  /**
   * Sets the value of the <b>owner</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> Owner of the constraint definition
   */
  public void setOwner(String value);

  /**
   * Returns true if the <b>table</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getTable() getTable} or
   * {@link #setTable(Table value) setTable(...)} for a definition of property
   * <b>table</b>
   * 
   * @return true if the <b>table</b> property is set.
   */
  public boolean isSetTable();

  /**
   * Unsets the <b>table</b> property, the value of the property of the object
   * being set to the property's default value. The property will no longer be
   * considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getTable() getTable} or
   * {@link #setTable(Table value) setTable(...)} for a definition of property
   * <b>table</b>
   */
  public void unsetTable();

  /**
   * Creates and returns a new instance of Type {@link Table} automatically
   * establishing a containment relationship through the object's reference
   * property, <b>table</b>.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getTable() getTable} or
   * {@link #setTable(Table value) setTable(...)} for a definition of property
   * <b>table</b>
   * 
   * @return a new instance of Type {@link Table} automatically establishing a
   *         containment relationship through the object's reference property
   *         <b>table</b>.
   */
  public Table createTable();

  /**
   * Returns the value of the <b>table</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> The reference to the owning table
   * 
   * @return the value of the <b>table</b> property.
   */
  public Table getTable();

  /**
   * Sets the value of the <b>table</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> The reference to the owning table
   */
  public void setTable(Table value);

  /**
   * Returns true if the <b>constraintName</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getConstraintName()
   * getConstraintName} or {@link #setConstraintName(String value)
   * setConstraintName(...)} for a definition of property <b>constraintName</b>
   * 
   * @return true if the <b>constraintName</b> property is set.
   */
  public boolean isSetConstraintName();

  /**
   * Unsets the <b>constraintName</b> property, the value of the property of the
   * object being set to the property's default value. The property will no
   * longer be considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getConstraintName()
   * getConstraintName} or {@link #setConstraintName(String value)
   * setConstraintName(...)} for a definition of property <b>constraintName</b>
   */
  public void unsetConstraintName();

  /**
   * Returns the value of the <b>constraintName</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> Name of the constraint definition
   * 
   * @return the value of the <b>constraintName</b> property.
   */
  public String getConstraintName();

  /**
   * Sets the value of the <b>constraintName</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> Name of the constraint definition
   */
  public void setConstraintName(String value);

  /**
   * Returns true if the <b>constraintType</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getConstraintType()
   * getConstraintType} or {@link #setConstraintType(String value)
   * setConstraintType(...)} for a definition of property <b>constraintType</b>
   * 
   * @return true if the <b>constraintType</b> property is set.
   */
  public boolean isSetConstraintType();

  /**
   * Unsets the <b>constraintType</b> property, the value of the property of the
   * object being set to the property's default value. The property will no
   * longer be considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getConstraintType()
   * getConstraintType} or {@link #setConstraintType(String value)
   * setConstraintType(...)} for a definition of property <b>constraintType</b>
   */
  public void unsetConstraintType();

  /**
   * Returns the value of the <b>constraintType</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> Type of constraint definition: C (check
   * constraint on a table) P (primary key) U (unique key) R (referential
   * integrity) V (with check option, on a view) O (with read only, on a view)
   * 
   * @return the value of the <b>constraintType</b> property.
   */
  public String getConstraintType();

  /**
   * Sets the value of the <b>constraintType</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> Type of constraint definition: C (check
   * constraint on a table) P (primary key) U (unique key) R (referential
   * integrity) V (with check option, on a view) O (with read only, on a view)
   * <p>
   * </p>
   * <b>Enumeration Constraints: </b>
   * 
   * <pre>
   *     <b>name:</b> ConstraintType
   *     <b>URI:</b>http://org.plasma/sdo/oracle/11g/sys
   * </pre>
   */
  public void setConstraintType(String value);

  /**
   * Returns true if the <b>searchCondition</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getSearchCondition()
   * getSearchCondition} or {@link #setSearchCondition(String value)
   * setSearchCondition(...)} for a definition of property
   * <b>searchCondition</b>
   * 
   * @return true if the <b>searchCondition</b> property is set.
   */
  public boolean isSetSearchCondition();

  /**
   * Unsets the <b>searchCondition</b> property, the value of the property of
   * the object being set to the property's default value. The property will no
   * longer be considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getSearchCondition()
   * getSearchCondition} or {@link #setSearchCondition(String value)
   * setSearchCondition(...)} for a definition of property
   * <b>searchCondition</b>
   */
  public void unsetSearchCondition();

  /**
   * Returns the value of the <b>searchCondition</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> Text of search condition for a check
   * constraint
   * 
   * @return the value of the <b>searchCondition</b> property.
   */
  public String getSearchCondition();

  /**
   * Sets the value of the <b>searchCondition</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> Text of search condition for a check
   * constraint
   */
  public void setSearchCondition(String value);

  /**
   * Returns true if the <b>refOwner</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getRefOwner() getRefOwner} or
   * {@link #setRefOwner(String value) setRefOwner(...)} for a definition of
   * property <b>refOwner</b>
   * 
   * @return true if the <b>refOwner</b> property is set.
   */
  public boolean isSetRefOwner();

  /**
   * Unsets the <b>refOwner</b> property, the value of the property of the
   * object being set to the property's default value. The property will no
   * longer be considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getRefOwner() getRefOwner} or
   * {@link #setRefOwner(String value) setRefOwner(...)} for a definition of
   * property <b>refOwner</b>
   */
  public void unsetRefOwner();

  /**
   * Returns the value of the <b>refOwner</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> Owner of table referred to in a referential
   * constraint
   * 
   * @return the value of the <b>refOwner</b> property.
   */
  public String getRefOwner();

  /**
   * Sets the value of the <b>refOwner</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> Owner of table referred to in a referential
   * constraint
   */
  public void setRefOwner(String value);

  /**
   * Returns true if the <b>refConstraintName</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getRefConstraintName()
   * getRefConstraintName} or {@link #setRefConstraintName(String value)
   * setRefConstraintName(...)} for a definition of property
   * <b>refConstraintName</b>
   * 
   * @return true if the <b>refConstraintName</b> property is set.
   */
  public boolean isSetRefConstraintName();

  /**
   * Unsets the <b>refConstraintName</b> property, the value of the property of
   * the object being set to the property's default value. The property will no
   * longer be considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getRefConstraintName()
   * getRefConstraintName} or {@link #setRefConstraintName(String value)
   * setRefConstraintName(...)} for a definition of property
   * <b>refConstraintName</b>
   */
  public void unsetRefConstraintName();

  /**
   * Returns the value of the <b>refConstraintName</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> Name of the unique constraint definition for
   * referenced table
   * 
   * @return the value of the <b>refConstraintName</b> property.
   */
  public String getRefConstraintName();

  /**
   * Sets the value of the <b>refConstraintName</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> Name of the unique constraint definition for
   * referenced table
   */
  public void setRefConstraintName(String value);

  /**
   * Returns true if the <b>deleteRule</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getDeleteRule() getDeleteRule} or
   * {@link #setDeleteRule(String value) setDeleteRule(...)} for a definition of
   * property <b>deleteRule</b>
   * 
   * @return true if the <b>deleteRule</b> property is set.
   */
  public boolean isSetDeleteRule();

  /**
   * Unsets the <b>deleteRule</b> property, the value of the property of the
   * object being set to the property's default value. The property will no
   * longer be considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getDeleteRule() getDeleteRule} or
   * {@link #setDeleteRule(String value) setDeleteRule(...)} for a definition of
   * property <b>deleteRule</b>
   */
  public void unsetDeleteRule();

  /**
   * Returns the value of the <b>deleteRule</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> Delete rule for a referential constraint
   * (CASCADE or NO ACTION)
   * 
   * @return the value of the <b>deleteRule</b> property.
   */
  public String getDeleteRule();

  /**
   * Sets the value of the <b>deleteRule</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> Delete rule for a referential constraint
   * (CASCADE or NO ACTION)
   */
  public void setDeleteRule(String value);

  /**
   * Returns true if the <b>indexOwner</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getIndexOwner() getIndexOwner} or
   * {@link #setIndexOwner(String value) setIndexOwner(...)} for a definition of
   * property <b>indexOwner</b>
   * 
   * @return true if the <b>indexOwner</b> property is set.
   */
  public boolean isSetIndexOwner();

  /**
   * Unsets the <b>indexOwner</b> property, the value of the property of the
   * object being set to the property's default value. The property will no
   * longer be considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getIndexOwner() getIndexOwner} or
   * {@link #setIndexOwner(String value) setIndexOwner(...)} for a definition of
   * property <b>indexOwner</b>
   */
  public void unsetIndexOwner();

  /**
   * Returns the value of the <b>indexOwner</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> Name of the user owning the index
   * 
   * @return the value of the <b>indexOwner</b> property.
   */
  public String getIndexOwner();

  /**
   * Sets the value of the <b>indexOwner</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> Name of the user owning the index
   */
  public void setIndexOwner(String value);

  /**
   * Returns true if the <b>indexName</b> property is set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getIndexName() getIndexName} or
   * {@link #setIndexName(String value) setIndexName(...)} for a definition of
   * property <b>indexName</b>
   * 
   * @return true if the <b>indexName</b> property is set.
   */
  public boolean isSetIndexName();

  /**
   * Unsets the <b>indexName</b> property, the value of the property of the
   * object being set to the property's default value. The property will no
   * longer be considered set.
   * <p>
   * </p>
   * <b>Property Definition: </b> See {@link #getIndexName() getIndexName} or
   * {@link #setIndexName(String value) setIndexName(...)} for a definition of
   * property <b>indexName</b>
   */
  public void unsetIndexName();

  /**
   * Returns the value of the <b>indexName</b> property.
   * <p>
   * </p>
   * <b>Property Definition: </b> Name of the index (only shown for unique and
   * primary-key constraints)
   * 
   * @return the value of the <b>indexName</b> property.
   */
  public String getIndexName();

  /**
   * Sets the value of the <b>indexName</b> property to the given value.
   * <p>
   * </p>
   * <b>Property Definition: </b> Name of the index (only shown for unique and
   * primary-key constraints)
   */
  public void setIndexName(String value);
}