package org.plasma.provisioning.rdb.oracle.g11.sys;


import org.plasma.sdo.PlasmaDataObject;

/**
 * A comment on a table
 * <p></p>
 * Generated interface representing the domain model entity <b>ViewComment</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/oracle/11g/sys</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_TAB_COMMENTS</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.View View
 */
public interface ViewComment extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

	/** The entity or <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> logical name associated with this class. */
	public static final String TYPE_NAME_VIEW_COMMENT = "ViewComment";
	
	/** The declared logical property names for this <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a>. */
	public static enum PROPERTY {
		
		/**
		 * The owner schema name
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>owner</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>ViewComment</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COMMENTS.OWNER</b>.
		 */
		owner,
		
		/**
		 * The comment body
		 * <p></p>
		 *
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>comments</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>ViewComment</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COMMENTS.COMMENTS</b>.
		 */
		comments,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>tableType</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>ViewComment</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COMMENTS.TABLE_TYPE</b>.
		 */
		tableType,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>view</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>ViewComment</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_TAB_COMMENTS.TABLE_NAME</b>.
		 */
		view
	}



	/**
	 * Returns true if the <b>owner</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner();

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getOwner() getOwner} or {@link #setOwner(String value) setOwner(...)} for a definition of property <b>owner</b>
	 */
	public void unsetOwner();

	/**
	 * Returns the value of the <b>owner</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The owner schema name
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner();

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The owner schema name
	 */
	public void setOwner(String value);


	/**
	 * Returns true if the <b>comments</b> property is set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getComments() getComments} or {@link #setComments(String value) setComments(...)} for a definition of property <b>comments</b>
	 * @return true if the <b>comments</b> property is set.
	 */
	public boolean isSetComments();

	/**
	 * Unsets the <b>comments</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * See {@link #getComments() getComments} or {@link #setComments(String value) setComments(...)} for a definition of property <b>comments</b>
	 */
	public void unsetComments();

	/**
	 * Returns the value of the <b>comments</b> property.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The comment body
	 * @return the value of the <b>comments</b> property.
	 */
	public String getComments();

	/**
	 * Sets the value of the <b>comments</b> property to the given value.
	 * <p></p>
	 * <b>Property Definition: </b>
	 * The comment body
	 */
	public void setComments(String value);


	/**
	 * Returns true if the <b>tableType</b> property is set.
	 * @return true if the <b>tableType</b> property is set.
	 */
	public boolean isSetTableType();

	/**
	 * Unsets the <b>tableType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTableType();

	/**
	 * Returns the value of the <b>tableType</b> property.
	 * @return the value of the <b>tableType</b> property.
	 */
	public String getTableType();

	/**
	 * Sets the value of the <b>tableType</b> property to the given value.
	 * <p></p>
	 * <b>Enumeration Constraints: </b><pre>
	 *     <b>name:</b> TableType
	 *     <b>URI:</b>http://org.plasma/sdo/oracle/11g/sys</pre>
	 */
	public void setTableType(String value);


	/**
	 * Returns true if the <b>view</b> property is set.
	 * @return true if the <b>view</b> property is set.
	 */
	public boolean isSetView();

	/**
	 * Unsets the <b>view</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetView();

	/**
	 * Creates and returns a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property, <b>view</b>.
	 * @return a new instance of Type {@link View} automatically establishing a containment relationship through the object's reference property <b>view</b>.
	 */
	public View createView();

	/**
	 * Returns the value of the <b>view</b> property.
	 * @return the value of the <b>view</b> property.
	 */
	public View getView();

	/**
	 * Sets the value of the <b>view</b> property to the given value.
	 */
	public void setView(View value);
}