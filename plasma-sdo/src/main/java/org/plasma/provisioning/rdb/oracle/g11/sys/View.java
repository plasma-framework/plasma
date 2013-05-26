package org.plasma.provisioning.rdb.oracle.g11.sys;


import org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumn;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumnComment;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewComment;
import org.plasma.sdo.PlasmaDataObject;

/**
 * Represents a system view definition
 * <p></p>
 * Generated interface representing the domain model entity <b>View</b>. This <a href="http://plasma-sdo.org">SDO</a> interface directly reflects the
 * class (single or multiple) inheritance lattice of the source domain model(s)  and is part of namespace <b>http://org.plasma/sdo/oracle/11g/sys</b> defined within the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/package-summary.html">Configuration</a>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_VIEWS</b>.
 * <p></p>
 *
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumn ViewColumn
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumnComment ViewColumnComment
 * @see org.plasma.provisioning.rdb.oracle.g11.sys.ViewComment ViewComment
 */
public interface View extends PlasmaDataObject
{
	/** The <a href="http://plasma-sdo.org">SDO</a> namespace URI associated with the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> for this class. */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

	/** The entity or <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> logical name associated with this class. */
	public static final String TYPE_NAME_VIEW = "View";
	
	/** The declared logical property names for this <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a>. */
	public static enum PROPERTY {
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>owner</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.OWNER</b>.
		 */
		owner,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>viewName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.VIEW_NAME</b>.
		 */
		viewName,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>textLength</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.TEXT_LENGTH</b>.
		 */
		textLength,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>text</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.TEXT</b>.
		 */
		text,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>typeTextLength</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.TYPE_TEXT_LENGTH</b>.
		 */
		typeTextLength,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>typeText</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.TYPE_TEXT</b>.
		 */
		typeText,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>oidTextLength</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.OID_TEXT_LENGTH</b>.
		 */
		oidTextLength,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>oidText</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.OID_TEXT</b>.
		 */
		oidText,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>viewTypeOwner</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.VIEW_TYPE_OWNER</b>.
		 */
		viewTypeOwner,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>viewType</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.VIEW_TYPE</b>.
		 */
		viewType,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>superviewName</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.SUPERVIEW_NAME</b>.
		 */
		superviewName,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>editioningView</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.EDITIONING_VIEW</b>.
		 */
		editioningView,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>readOnly</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 *
		 * <p></p>
		 * <b>Data Store Mapping:</b>
		 * Corresponds to the physical data store element <b>ALL_VIEWS.READ_ONLY</b>.
		 */
		readOnly,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>viewColumn</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 */
		viewColumn,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>viewComment</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 */
		viewComment,
		
		/**
		 * Represents the logical <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaProperty.html">Property</a> <b>viewColumnComment</b> which is part of the <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>View</b>.
		 */
		viewColumnComment
	}



	/**
	 * Returns true if the <b>owner</b> property is set.
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner();

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOwner();

	/**
	 * Returns the value of the <b>owner</b> property.
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner();

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 */
	public void setOwner(String value);


	/**
	 * Returns true if the <b>viewName</b> property is set.
	 * @return true if the <b>viewName</b> property is set.
	 */
	public boolean isSetViewName();

	/**
	 * Unsets the <b>viewName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetViewName();

	/**
	 * Returns the value of the <b>viewName</b> property.
	 * @return the value of the <b>viewName</b> property.
	 */
	public String getViewName();

	/**
	 * Sets the value of the <b>viewName</b> property to the given value.
	 * <p></p>
	 * <b>Value Constraints: </b><pre>
	 *     maxLength: 30</pre>
	 */
	public void setViewName(String value);


	/**
	 * Returns true if the <b>textLength</b> property is set.
	 * @return true if the <b>textLength</b> property is set.
	 */
	public boolean isSetTextLength();

	/**
	 * Unsets the <b>textLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTextLength();

	/**
	 * Returns the value of the <b>textLength</b> property.
	 * @return the value of the <b>textLength</b> property.
	 */
	public int getTextLength();

	/**
	 * Sets the value of the <b>textLength</b> property to the given value.
	 */
	public void setTextLength(int value);


	/**
	 * Returns true if the <b>text</b> property is set.
	 * @return true if the <b>text</b> property is set.
	 */
	public boolean isSetText();

	/**
	 * Unsets the <b>text</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetText();

	/**
	 * Returns the value of the <b>text</b> property.
	 * @return the value of the <b>text</b> property.
	 */
	public String getText();

	/**
	 * Sets the value of the <b>text</b> property to the given value.
	 */
	public void setText(String value);


	/**
	 * Returns true if the <b>typeTextLength</b> property is set.
	 * @return true if the <b>typeTextLength</b> property is set.
	 */
	public boolean isSetTypeTextLength();

	/**
	 * Unsets the <b>typeTextLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTypeTextLength();

	/**
	 * Returns the value of the <b>typeTextLength</b> property.
	 * @return the value of the <b>typeTextLength</b> property.
	 */
	public int getTypeTextLength();

	/**
	 * Sets the value of the <b>typeTextLength</b> property to the given value.
	 */
	public void setTypeTextLength(int value);


	/**
	 * Returns true if the <b>typeText</b> property is set.
	 * @return true if the <b>typeText</b> property is set.
	 */
	public boolean isSetTypeText();

	/**
	 * Unsets the <b>typeText</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTypeText();

	/**
	 * Returns the value of the <b>typeText</b> property.
	 * @return the value of the <b>typeText</b> property.
	 */
	public String getTypeText();

	/**
	 * Sets the value of the <b>typeText</b> property to the given value.
	 */
	public void setTypeText(String value);


	/**
	 * Returns true if the <b>oidTextLength</b> property is set.
	 * @return true if the <b>oidTextLength</b> property is set.
	 */
	public boolean isSetOidTextLength();

	/**
	 * Unsets the <b>oidTextLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOidTextLength();

	/**
	 * Returns the value of the <b>oidTextLength</b> property.
	 * @return the value of the <b>oidTextLength</b> property.
	 */
	public int getOidTextLength();

	/**
	 * Sets the value of the <b>oidTextLength</b> property to the given value.
	 */
	public void setOidTextLength(int value);


	/**
	 * Returns true if the <b>oidText</b> property is set.
	 * @return true if the <b>oidText</b> property is set.
	 */
	public boolean isSetOidText();

	/**
	 * Unsets the <b>oidText</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOidText();

	/**
	 * Returns the value of the <b>oidText</b> property.
	 * @return the value of the <b>oidText</b> property.
	 */
	public String getOidText();

	/**
	 * Sets the value of the <b>oidText</b> property to the given value.
	 */
	public void setOidText(String value);


	/**
	 * Returns true if the <b>viewTypeOwner</b> property is set.
	 * @return true if the <b>viewTypeOwner</b> property is set.
	 */
	public boolean isSetViewTypeOwner();

	/**
	 * Unsets the <b>viewTypeOwner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetViewTypeOwner();

	/**
	 * Returns the value of the <b>viewTypeOwner</b> property.
	 * @return the value of the <b>viewTypeOwner</b> property.
	 */
	public String getViewTypeOwner();

	/**
	 * Sets the value of the <b>viewTypeOwner</b> property to the given value.
	 */
	public void setViewTypeOwner(String value);


	/**
	 * Returns true if the <b>viewType</b> property is set.
	 * @return true if the <b>viewType</b> property is set.
	 */
	public boolean isSetViewType();

	/**
	 * Unsets the <b>viewType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetViewType();

	/**
	 * Returns the value of the <b>viewType</b> property.
	 * @return the value of the <b>viewType</b> property.
	 */
	public String getViewType();

	/**
	 * Sets the value of the <b>viewType</b> property to the given value.
	 */
	public void setViewType(String value);


	/**
	 * Returns true if the <b>superviewName</b> property is set.
	 * @return true if the <b>superviewName</b> property is set.
	 */
	public boolean isSetSuperviewName();

	/**
	 * Unsets the <b>superviewName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetSuperviewName();

	/**
	 * Returns the value of the <b>superviewName</b> property.
	 * @return the value of the <b>superviewName</b> property.
	 */
	public String getSuperviewName();

	/**
	 * Sets the value of the <b>superviewName</b> property to the given value.
	 * <p></p>
	 * <b>Value Constraints: </b><pre>
	 *     maxLength: 30</pre>
	 */
	public void setSuperviewName(String value);


	/**
	 * Returns true if the <b>editioningView</b> property is set.
	 * @return true if the <b>editioningView</b> property is set.
	 */
	public boolean isSetEditioningView();

	/**
	 * Unsets the <b>editioningView</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetEditioningView();

	/**
	 * Returns the value of the <b>editioningView</b> property.
	 * @return the value of the <b>editioningView</b> property.
	 */
	public String getEditioningView();

	/**
	 * Sets the value of the <b>editioningView</b> property to the given value.
	 * <p></p>
	 * <b>Value Constraints: </b><pre>
	 *     maxLength: 1</pre>
	 */
	public void setEditioningView(String value);


	/**
	 * Returns true if the <b>readOnly</b> property is set.
	 * @return true if the <b>readOnly</b> property is set.
	 */
	public boolean isSetReadOnly();

	/**
	 * Unsets the <b>readOnly</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetReadOnly();

	/**
	 * Returns the value of the <b>readOnly</b> property.
	 * @return the value of the <b>readOnly</b> property.
	 */
	public String getReadOnly();

	/**
	 * Sets the value of the <b>readOnly</b> property to the given value.
	 * <p></p>
	 * <b>Value Constraints: </b><pre>
	 *     maxLength: 1</pre>
	 */
	public void setReadOnly(String value);


	/**
	 * Returns true if the <b>viewColumn</b> property is set.
	 * @return true if the <b>viewColumn</b> property is set.
	 */
	public boolean isSetViewColumn();

	/**
	 * Unsets the <b>viewColumn</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetViewColumn();

	/**
	 * Creates and returns a new instance of Type {@link ViewColumn} automatically establishing a containment relationship through the object's reference property, <b>viewColumn</b>.
	 * @return a new instance of Type {@link ViewColumn} automatically establishing a containment relationship through the object's reference property <b>viewColumn</b>.
	 */
	public ViewColumn createViewColumn();

	/**
	 * Returns an array of <b>ViewColumn</b> set for the object's multi-valued property <b>viewColumn</b>.
	 * @return an array of <b>ViewColumn</b> set for the object's multi-valued property <b>viewColumn</b>.
	 */
	public ViewColumn[] getViewColumn();

	/**
	 * Returns the <b>ViewColumn</b> set for the object's multi-valued property <b>viewColumn</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ViewColumn</b> set for the object's multi-valued property <b>viewColumn</b> based on the given index.
	 */
	public ViewColumn getViewColumn(int idx);

	/**
	 * Returns a count for multi-valued property <b>viewColumn</b>.
	 * @return a count for multi-valued property <b>viewColumn</b>.
	 */
	public int getViewColumnCount();

	/**
	 * Sets the given array of Type <b>ViewColumn</b> for the object's multi-valued property <b>viewColumn</b>.
	 * @param value the array value
	 */
	public void setViewColumn(ViewColumn[] value);

	/**
	 * Adds the given value of Type <b>ViewColumn</b> for the object's multi-valued property <b>viewColumn</b>.
	 * @param value the value to add
	 */
	public void addViewColumn(ViewColumn value);

	/**
	 * Removes the given value of Type <b>ViewColumn</b> for the object's multi-valued property <b>viewColumn</b>.
	 * @param value the value to remove
	 */
	public void removeViewColumn(ViewColumn value);


	/**
	 * Returns true if the <b>viewComment</b> property is set.
	 * @return true if the <b>viewComment</b> property is set.
	 */
	public boolean isSetViewComment();

	/**
	 * Unsets the <b>viewComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetViewComment();

	/**
	 * Creates and returns a new instance of Type {@link ViewComment} automatically establishing a containment relationship through the object's reference property, <b>viewComment</b>.
	 * @return a new instance of Type {@link ViewComment} automatically establishing a containment relationship through the object's reference property <b>viewComment</b>.
	 */
	public ViewComment createViewComment();

	/**
	 * Returns an array of <b>ViewComment</b> set for the object's multi-valued property <b>viewComment</b>.
	 * @return an array of <b>ViewComment</b> set for the object's multi-valued property <b>viewComment</b>.
	 */
	public ViewComment[] getViewComment();

	/**
	 * Returns the <b>ViewComment</b> set for the object's multi-valued property <b>viewComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ViewComment</b> set for the object's multi-valued property <b>viewComment</b> based on the given index.
	 */
	public ViewComment getViewComment(int idx);

	/**
	 * Returns a count for multi-valued property <b>viewComment</b>.
	 * @return a count for multi-valued property <b>viewComment</b>.
	 */
	public int getViewCommentCount();

	/**
	 * Sets the given array of Type <b>ViewComment</b> for the object's multi-valued property <b>viewComment</b>.
	 * @param value the array value
	 */
	public void setViewComment(ViewComment[] value);

	/**
	 * Adds the given value of Type <b>ViewComment</b> for the object's multi-valued property <b>viewComment</b>.
	 * @param value the value to add
	 */
	public void addViewComment(ViewComment value);

	/**
	 * Removes the given value of Type <b>ViewComment</b> for the object's multi-valued property <b>viewComment</b>.
	 * @param value the value to remove
	 */
	public void removeViewComment(ViewComment value);


	/**
	 * Returns true if the <b>viewColumnComment</b> property is set.
	 * @return true if the <b>viewColumnComment</b> property is set.
	 */
	public boolean isSetViewColumnComment();

	/**
	 * Unsets the <b>viewColumnComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetViewColumnComment();

	/**
	 * Creates and returns a new instance of Type {@link ViewColumnComment} automatically establishing a containment relationship through the object's reference property, <b>viewColumnComment</b>.
	 * @return a new instance of Type {@link ViewColumnComment} automatically establishing a containment relationship through the object's reference property <b>viewColumnComment</b>.
	 */
	public ViewColumnComment createViewColumnComment();

	/**
	 * Returns an array of <b>ViewColumnComment</b> set for the object's multi-valued property <b>viewColumnComment</b>.
	 * @return an array of <b>ViewColumnComment</b> set for the object's multi-valued property <b>viewColumnComment</b>.
	 */
	public ViewColumnComment[] getViewColumnComment();

	/**
	 * Returns the <b>ViewColumnComment</b> set for the object's multi-valued property <b>viewColumnComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ViewColumnComment</b> set for the object's multi-valued property <b>viewColumnComment</b> based on the given index.
	 */
	public ViewColumnComment getViewColumnComment(int idx);

	/**
	 * Returns a count for multi-valued property <b>viewColumnComment</b>.
	 * @return a count for multi-valued property <b>viewColumnComment</b>.
	 */
	public int getViewColumnCommentCount();

	/**
	 * Sets the given array of Type <b>ViewColumnComment</b> for the object's multi-valued property <b>viewColumnComment</b>.
	 * @param value the array value
	 */
	public void setViewColumnComment(ViewColumnComment[] value);

	/**
	 * Adds the given value of Type <b>ViewColumnComment</b> for the object's multi-valued property <b>viewColumnComment</b>.
	 * @param value the value to add
	 */
	public void addViewColumnComment(ViewColumnComment value);

	/**
	 * Removes the given value of Type <b>ViewColumnComment</b> for the object's multi-valued property <b>viewColumnComment</b>.
	 * @param value the value to remove
	 */
	public void removeViewColumnComment(ViewColumnComment value);
}