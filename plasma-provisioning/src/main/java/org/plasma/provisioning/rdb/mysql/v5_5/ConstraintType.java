package org.plasma.provisioning.rdb.mysql.v5_5;

import org.plasma.sdo.PlasmaEnum;

/**
 * This generated <a
 * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaEnum.html"
 * >Enumeration</a> represents the domain model enumeration
 * <b>ConstraintType</b> which is part of namespace
 * <b>http://org.plasma/sdo/mysql/5_5</b> as defined within the <a href=
 * "http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html"
 * >Configuration</a>.
 * <p>
 * </p>
 * * Generated <a href="http://plasma-sdo.org">SDO</a> enumerations embody not
 * only logical-name literals but also physical or instance names, which are
 * often shorter (possibly abbreviated) and applicable as a data-store
 * space-saving device. Application programs should typically use the physical
 * or instance name for an enumeration literal when setting a data object
 * property which is <a href=
 * "http://docs.plasma-sdo.org/api/org/plasma/sdo/EnumerationConstraint.html"
 * >constrained</a> by an enumeration.
 */
public enum ConstraintType implements PlasmaEnum {

  /**
   * A primary key constraint
   * <p>
   * </p>
   * Holds the logical and physical names for literal <b>PRIMARY KEY</b>.
   */
  PRIMARY_KEY("PRIMARY KEY", "A primary key constraint"),

  /**
   * A primary key constraint
   * <p>
   * </p>
   * Holds the logical and physical names for literal <b>FOREIGN KEY</b>.
   */
  FOREIGN_KEY("FOREIGN KEY", "A primary key constraint"),

  /**
   * Holds the logical and physical names for literal <b>UNIQUE</b>.
   */
  UNIQUE("UNIQUE", "");

  private String instanceName;
  private String description;

  private ConstraintType(String instanceName, String description) {
    this.instanceName = instanceName;
    this.description = description;
  }

  /**
   * Returns the logical name associated with this enumeration literal.
   */
  public String getName() {
    return this.name();
  }

  /**
   * Returns the physical or instance name associated with this enumeration
   * literal.
   */
  public String getInstanceName() {
    return this.instanceName;
  }

  /**
   * Returns the descriptive text associated with this enumeration literal.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns the enum values for this class as an array of implemented
   * interfaces
   * 
   * @see PlasmaEnum
   */
  public static PlasmaEnum[] enumValues() {
    return values();
  }

  /**
   * Returns the enumeration value matching the given name.
   */
  public static ConstraintType fromName(String name) {
    for (PlasmaEnum enm : enumValues()) {
      if (enm.getName().equals(name))
        return (ConstraintType) enm;
    }
    throw new IllegalArgumentException("no enumeration value found for name '" + name + "'");
  }

  /**
   * Returns the enumeration value matching the given physical or instance name.
   */
  public static ConstraintType fromInstanceName(String instanceName) {
    for (PlasmaEnum enm : enumValues()) {
      if (enm.getInstanceName().equals(instanceName))
        return (ConstraintType) enm;
    }
    throw new IllegalArgumentException("no enumeration value found for instance name '"
        + instanceName + "'");
  }

}