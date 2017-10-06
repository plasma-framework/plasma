package org.plasma.provisioning.rdb.oracle.any.sys;

import org.plasma.sdo.PlasmaDataObject;

/**
 * Generated interface representing the domain model entity <b>Version</b>. This
 * <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the class
 * (single or multiple) inheritance lattice of the source domain model(s) and is
 * part of namespace <b>http://org.plasma/sdo/oracle/any/sys</b> defined within
 * the <a href=
 * "http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html"
 * >Configuration</a>.
 *
 * <p>
 * </p>
 * <b>Data Store Mapping:</b> Corresponds to the physical data store entity
 * <b>v$VERSION</b>.
 * <p>
 * </p>
 *
 */
public interface Version extends PlasmaDataObject {
  /**
   * The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with
   * the <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">
   * Type</a> for this class.
   */
  public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/any/sys";

  /**
   * The entity or <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a> logical name associated with this class.
   */
  public static final String TYPE_NAME_VERSION = "Version";

  /**
   * The declared logical property names for this <a
   * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
   * >Type</a>.
   */
  public static enum PROPERTY {

    /**
     * Represents the logical <a href=
     * "http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html"
     * >Property</a> <b>banner</b> which is part of the <a
     * href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html"
     * >Type</a> <b>Version</b>.
     *
     * <p>
     * </p>
     * <b>Data Store Mapping:</b> Corresponds to the physical data store element
     * <b>v$VERSION.BANNER</b>.
     */
    banner
  }

  /**
   * Returns true if the <b>banner</b> property is set.
   * 
   * @return true if the <b>banner</b> property is set.
   */
  public boolean isSetBanner();

  /**
   * Unsets the <b>banner</b> property, the value of the property of the object
   * being set to the property's default value. The property will no longer be
   * considered set.
   */
  public void unsetBanner();

  /**
   * Returns the value of the <b>banner</b> property.
   * 
   * @return the value of the <b>banner</b> property.
   */
  public String getBanner();

  /**
   * Sets the value of the <b>banner</b> property to the given value.
   */
  public void setBanner(String value);
}