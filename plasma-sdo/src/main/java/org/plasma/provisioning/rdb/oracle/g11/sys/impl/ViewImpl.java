package org.plasma.provisioning.rdb.oracle.g11.sys.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.plasma.provisioning.rdb.oracle.g11.sys.View;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumn;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewColumnComment;
import org.plasma.provisioning.rdb.oracle.g11.sys.ViewComment;
import org.plasma.sdo.core.CoreDataObject;

/**
 * Represents a system view definition
 * <p></p>
 * Generated implementation class representing the domain model entity <b>View</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>ALL_VIEWS</b>.
 * <p></p>
 *
 */
public class ViewImpl extends CoreDataObject implements Serializable, View
{
	private static final long serialVersionUID = 1L;
	/** The SDO namespace URI associated with the SDO Type for this class */
	public static final String NAMESPACE_URI = "http://org.plasma/sdo/oracle/11g/sys";

	/**
	 * Default No-arg constructor required for serialization operations. This method
	 * is NOT intended to be used within application source code.
	 */
	public ViewImpl() {
		super();
	}
	public ViewImpl(commonj.sdo.Type type) {
		super(type);
	}


	/**
	 * Returns true if the <b>editioningView</b> property is set.
	 * @return true if the <b>editioningView</b> property is set.
	 */
	public boolean isSetEditioningView(){
		return super.isSet(View.PROPERTY.editioningView.name());
	}

	/**
	 * Unsets the <b>editioningView</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetEditioningView(){
		super.unset(View.PROPERTY.editioningView.name());
	}

	/**
	 * Returns the value of the <b>editioningView</b> property.
	 * @return the value of the <b>editioningView</b> property.
	 */
	public String getEditioningView(){
		return (String)super.get(View.PROPERTY.editioningView.name());
	}

	/**
	 * Sets the value of the <b>editioningView</b> property to the given value.
	 * <p></p>
	 * <b>Value Constraints: </b><pre>
	 *     maxLength: 1</pre>
	 */
	public void setEditioningView(String value){
		super.set(View.PROPERTY.editioningView.name(), value);
	}


	/**
	 * Returns true if the <b>oidText</b> property is set.
	 * @return true if the <b>oidText</b> property is set.
	 */
	public boolean isSetOidText(){
		return super.isSet(View.PROPERTY.oidText.name());
	}

	/**
	 * Unsets the <b>oidText</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOidText(){
		super.unset(View.PROPERTY.oidText.name());
	}

	/**
	 * Returns the value of the <b>oidText</b> property.
	 * @return the value of the <b>oidText</b> property.
	 */
	public String getOidText(){
		return (String)super.get(View.PROPERTY.oidText.name());
	}

	/**
	 * Sets the value of the <b>oidText</b> property to the given value.
	 */
	public void setOidText(String value){
		super.set(View.PROPERTY.oidText.name(), value);
	}


	/**
	 * Returns true if the <b>oidTextLength</b> property is set.
	 * @return true if the <b>oidTextLength</b> property is set.
	 */
	public boolean isSetOidTextLength(){
		return super.isSet(View.PROPERTY.oidTextLength.name());
	}

	/**
	 * Unsets the <b>oidTextLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOidTextLength(){
		super.unset(View.PROPERTY.oidTextLength.name());
	}

	/**
	 * Returns the value of the <b>oidTextLength</b> property.
	 * @return the value of the <b>oidTextLength</b> property.
	 */
	public int getOidTextLength(){
		Integer result = (Integer)super.get(View.PROPERTY.oidTextLength.name());
		if (result != null)
			return result.intValue();
		else return 0;
	}

	/**
	 * Sets the value of the <b>oidTextLength</b> property to the given value.
	 */
	public void setOidTextLength(int value){
		super.set(View.PROPERTY.oidTextLength.name(), value);
	}


	/**
	 * Returns true if the <b>owner</b> property is set.
	 * @return true if the <b>owner</b> property is set.
	 */
	public boolean isSetOwner(){
		return super.isSet(View.PROPERTY.owner.name());
	}

	/**
	 * Unsets the <b>owner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetOwner(){
		super.unset(View.PROPERTY.owner.name());
	}

	/**
	 * Returns the value of the <b>owner</b> property.
	 * @return the value of the <b>owner</b> property.
	 */
	public String getOwner(){
		return (String)super.get(View.PROPERTY.owner.name());
	}

	/**
	 * Sets the value of the <b>owner</b> property to the given value.
	 */
	public void setOwner(String value){
		super.set(View.PROPERTY.owner.name(), value);
	}


	/**
	 * Returns true if the <b>readOnly</b> property is set.
	 * @return true if the <b>readOnly</b> property is set.
	 */
	public boolean isSetReadOnly(){
		return super.isSet(View.PROPERTY.readOnly.name());
	}

	/**
	 * Unsets the <b>readOnly</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetReadOnly(){
		super.unset(View.PROPERTY.readOnly.name());
	}

	/**
	 * Returns the value of the <b>readOnly</b> property.
	 * @return the value of the <b>readOnly</b> property.
	 */
	public String getReadOnly(){
		return (String)super.get(View.PROPERTY.readOnly.name());
	}

	/**
	 * Sets the value of the <b>readOnly</b> property to the given value.
	 * <p></p>
	 * <b>Value Constraints: </b><pre>
	 *     maxLength: 1</pre>
	 */
	public void setReadOnly(String value){
		super.set(View.PROPERTY.readOnly.name(), value);
	}


	/**
	 * Returns true if the <b>superviewName</b> property is set.
	 * @return true if the <b>superviewName</b> property is set.
	 */
	public boolean isSetSuperviewName(){
		return super.isSet(View.PROPERTY.superviewName.name());
	}

	/**
	 * Unsets the <b>superviewName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetSuperviewName(){
		super.unset(View.PROPERTY.superviewName.name());
	}

	/**
	 * Returns the value of the <b>superviewName</b> property.
	 * @return the value of the <b>superviewName</b> property.
	 */
	public String getSuperviewName(){
		return (String)super.get(View.PROPERTY.superviewName.name());
	}

	/**
	 * Sets the value of the <b>superviewName</b> property to the given value.
	 * <p></p>
	 * <b>Value Constraints: </b><pre>
	 *     maxLength: 30</pre>
	 */
	public void setSuperviewName(String value){
		super.set(View.PROPERTY.superviewName.name(), value);
	}


	/**
	 * Returns true if the <b>text</b> property is set.
	 * @return true if the <b>text</b> property is set.
	 */
	public boolean isSetText(){
		return super.isSet(View.PROPERTY.text.name());
	}

	/**
	 * Unsets the <b>text</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetText(){
		super.unset(View.PROPERTY.text.name());
	}

	/**
	 * Returns the value of the <b>text</b> property.
	 * @return the value of the <b>text</b> property.
	 */
	public String getText(){
		return (String)super.get(View.PROPERTY.text.name());
	}

	/**
	 * Sets the value of the <b>text</b> property to the given value.
	 */
	public void setText(String value){
		super.set(View.PROPERTY.text.name(), value);
	}


	/**
	 * Returns true if the <b>textLength</b> property is set.
	 * @return true if the <b>textLength</b> property is set.
	 */
	public boolean isSetTextLength(){
		return super.isSet(View.PROPERTY.textLength.name());
	}

	/**
	 * Unsets the <b>textLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTextLength(){
		super.unset(View.PROPERTY.textLength.name());
	}

	/**
	 * Returns the value of the <b>textLength</b> property.
	 * @return the value of the <b>textLength</b> property.
	 */
	public int getTextLength(){
		Integer result = (Integer)super.get(View.PROPERTY.textLength.name());
		if (result != null)
			return result.intValue();
		else return 0;
	}

	/**
	 * Sets the value of the <b>textLength</b> property to the given value.
	 */
	public void setTextLength(int value){
		super.set(View.PROPERTY.textLength.name(), value);
	}


	/**
	 * Returns true if the <b>typeText</b> property is set.
	 * @return true if the <b>typeText</b> property is set.
	 */
	public boolean isSetTypeText(){
		return super.isSet(View.PROPERTY.typeText.name());
	}

	/**
	 * Unsets the <b>typeText</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTypeText(){
		super.unset(View.PROPERTY.typeText.name());
	}

	/**
	 * Returns the value of the <b>typeText</b> property.
	 * @return the value of the <b>typeText</b> property.
	 */
	public String getTypeText(){
		return (String)super.get(View.PROPERTY.typeText.name());
	}

	/**
	 * Sets the value of the <b>typeText</b> property to the given value.
	 */
	public void setTypeText(String value){
		super.set(View.PROPERTY.typeText.name(), value);
	}


	/**
	 * Returns true if the <b>typeTextLength</b> property is set.
	 * @return true if the <b>typeTextLength</b> property is set.
	 */
	public boolean isSetTypeTextLength(){
		return super.isSet(View.PROPERTY.typeTextLength.name());
	}

	/**
	 * Unsets the <b>typeTextLength</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetTypeTextLength(){
		super.unset(View.PROPERTY.typeTextLength.name());
	}

	/**
	 * Returns the value of the <b>typeTextLength</b> property.
	 * @return the value of the <b>typeTextLength</b> property.
	 */
	public int getTypeTextLength(){
		Integer result = (Integer)super.get(View.PROPERTY.typeTextLength.name());
		if (result != null)
			return result.intValue();
		else return 0;
	}

	/**
	 * Sets the value of the <b>typeTextLength</b> property to the given value.
	 */
	public void setTypeTextLength(int value){
		super.set(View.PROPERTY.typeTextLength.name(), value);
	}


	/**
	 * Returns true if the <b>viewColumn</b> property is set.
	 * @return true if the <b>viewColumn</b> property is set.
	 */
	public boolean isSetViewColumn(){
		return super.isSet(View.PROPERTY.viewColumn.name());
	}

	/**
	 * Unsets the <b>viewColumn</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetViewColumn(){
		super.unset(View.PROPERTY.viewColumn.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link ViewColumn} automatically establishing a containment relationship through the object's reference property, <b>viewColumn</b>.
	 * @return a new instance of Type {@link ViewColumn} automatically establishing a containment relationship through the object's reference property <b>viewColumn</b>.
	 */
	public ViewColumn createViewColumn(){
		return (ViewColumn)super.createDataObject(View.PROPERTY.viewColumn.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>ViewColumn</b> set for the object's multi-valued property <b>viewColumn</b>.
	 * @return an array of <b>ViewColumn</b> set for the object's multi-valued property <b>viewColumn</b>.
	 */
	public ViewColumn[] getViewColumn(){
		List<ViewColumn> list = (List<ViewColumn>)super.get(View.PROPERTY.viewColumn.name());
		if (list != null) {
			ViewColumn[] array = new ViewColumn[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new ViewColumn[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>ViewColumn</b> set for the object's multi-valued property <b>viewColumn</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ViewColumn</b> set for the object's multi-valued property <b>viewColumn</b> based on the given index.
	 */
	public ViewColumn getViewColumn(int idx){
		List<ViewColumn> list = (List<ViewColumn>)super.get(View.PROPERTY.viewColumn.name());
		if (list != null) {
			return (ViewColumn)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>viewColumn</b>.
	 * @return a count for multi-valued property <b>viewColumn</b>.
	 */
	public int getViewColumnCount(){
		List<ViewColumn> list = (List<ViewColumn>)super.get(View.PROPERTY.viewColumn.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>ViewColumn</b> for the object's multi-valued property <b>viewColumn</b>.
	 * @param value the array value
	 */
	public void setViewColumn(ViewColumn[] value){
		List<ViewColumn> list = (List<ViewColumn>)super.get(View.PROPERTY.viewColumn.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<ViewColumn>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(View.PROPERTY.viewColumn.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetViewColumn() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>ViewColumn</b> for the object's multi-valued property <b>viewColumn</b>.
	 * @param value the value to add
	 */
	public void addViewColumn(ViewColumn value){
		List<ViewColumn> list = (List<ViewColumn>)super.get(View.PROPERTY.viewColumn.name());
		if (list == null)
			list = new ArrayList<ViewColumn>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(View.PROPERTY.viewColumn.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>ViewColumn</b> for the object's multi-valued property <b>viewColumn</b>.
	 * @param value the value to remove
	 */
	public void removeViewColumn(ViewColumn value){
		List<ViewColumn> list = (List<ViewColumn>)super.get(View.PROPERTY.viewColumn.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(View.PROPERTY.viewColumn.name(), list);
	}


	/**
	 * Returns true if the <b>viewColumnComment</b> property is set.
	 * @return true if the <b>viewColumnComment</b> property is set.
	 */
	public boolean isSetViewColumnComment(){
		return super.isSet(View.PROPERTY.viewColumnComment.name());
	}

	/**
	 * Unsets the <b>viewColumnComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetViewColumnComment(){
		super.unset(View.PROPERTY.viewColumnComment.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link ViewColumnComment} automatically establishing a containment relationship through the object's reference property, <b>viewColumnComment</b>.
	 * @return a new instance of Type {@link ViewColumnComment} automatically establishing a containment relationship through the object's reference property <b>viewColumnComment</b>.
	 */
	public ViewColumnComment createViewColumnComment(){
		return (ViewColumnComment)super.createDataObject(View.PROPERTY.viewColumnComment.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>ViewColumnComment</b> set for the object's multi-valued property <b>viewColumnComment</b>.
	 * @return an array of <b>ViewColumnComment</b> set for the object's multi-valued property <b>viewColumnComment</b>.
	 */
	public ViewColumnComment[] getViewColumnComment(){
		List<ViewColumnComment> list = (List<ViewColumnComment>)super.get(View.PROPERTY.viewColumnComment.name());
		if (list != null) {
			ViewColumnComment[] array = new ViewColumnComment[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new ViewColumnComment[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>ViewColumnComment</b> set for the object's multi-valued property <b>viewColumnComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ViewColumnComment</b> set for the object's multi-valued property <b>viewColumnComment</b> based on the given index.
	 */
	public ViewColumnComment getViewColumnComment(int idx){
		List<ViewColumnComment> list = (List<ViewColumnComment>)super.get(View.PROPERTY.viewColumnComment.name());
		if (list != null) {
			return (ViewColumnComment)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>viewColumnComment</b>.
	 * @return a count for multi-valued property <b>viewColumnComment</b>.
	 */
	public int getViewColumnCommentCount(){
		List<ViewColumnComment> list = (List<ViewColumnComment>)super.get(View.PROPERTY.viewColumnComment.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>ViewColumnComment</b> for the object's multi-valued property <b>viewColumnComment</b>.
	 * @param value the array value
	 */
	public void setViewColumnComment(ViewColumnComment[] value){
		List<ViewColumnComment> list = (List<ViewColumnComment>)super.get(View.PROPERTY.viewColumnComment.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<ViewColumnComment>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(View.PROPERTY.viewColumnComment.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetViewColumnComment() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>ViewColumnComment</b> for the object's multi-valued property <b>viewColumnComment</b>.
	 * @param value the value to add
	 */
	public void addViewColumnComment(ViewColumnComment value){
		List<ViewColumnComment> list = (List<ViewColumnComment>)super.get(View.PROPERTY.viewColumnComment.name());
		if (list == null)
			list = new ArrayList<ViewColumnComment>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(View.PROPERTY.viewColumnComment.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>ViewColumnComment</b> for the object's multi-valued property <b>viewColumnComment</b>.
	 * @param value the value to remove
	 */
	public void removeViewColumnComment(ViewColumnComment value){
		List<ViewColumnComment> list = (List<ViewColumnComment>)super.get(View.PROPERTY.viewColumnComment.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(View.PROPERTY.viewColumnComment.name(), list);
	}


	/**
	 * Returns true if the <b>viewComment</b> property is set.
	 * @return true if the <b>viewComment</b> property is set.
	 */
	public boolean isSetViewComment(){
		return super.isSet(View.PROPERTY.viewComment.name());
	}

	/**
	 * Unsets the <b>viewComment</b> property, clearing the underlying collection. The property will no longer be
	 * considered set.
	 */
	public void unsetViewComment(){
		super.unset(View.PROPERTY.viewComment.name());
	}

	/**
	 * Creates and returns a new instance of Type {@link ViewComment} automatically establishing a containment relationship through the object's reference property, <b>viewComment</b>.
	 * @return a new instance of Type {@link ViewComment} automatically establishing a containment relationship through the object's reference property <b>viewComment</b>.
	 */
	public ViewComment createViewComment(){
		return (ViewComment)super.createDataObject(View.PROPERTY.viewComment.name());
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns an array of <b>ViewComment</b> set for the object's multi-valued property <b>viewComment</b>.
	 * @return an array of <b>ViewComment</b> set for the object's multi-valued property <b>viewComment</b>.
	 */
	public ViewComment[] getViewComment(){
		List<ViewComment> list = (List<ViewComment>)super.get(View.PROPERTY.viewComment.name());
		if (list != null) {
			ViewComment[] array = new ViewComment[list.size()];
			for (int i = 0; i < list.size(); i++)
				array[i] = list.get(i);
			return array;
		}
		else
			return new ViewComment[0];
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns the <b>ViewComment</b> set for the object's multi-valued property <b>viewComment</b> based on the given index.
	 * @param idx the index
	 * @return the <b>ViewComment</b> set for the object's multi-valued property <b>viewComment</b> based on the given index.
	 */
	public ViewComment getViewComment(int idx){
		List<ViewComment> list = (List<ViewComment>)super.get(View.PROPERTY.viewComment.name());
		if (list != null) {
			return (ViewComment)list.get(idx);
		}
		else
			throw new ArrayIndexOutOfBoundsException(idx);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Returns a count for multi-valued property <b>viewComment</b>.
	 * @return a count for multi-valued property <b>viewComment</b>.
	 */
	public int getViewCommentCount(){
		List<ViewComment> list = (List<ViewComment>)super.get(View.PROPERTY.viewComment.name());
		if (list != null) {
			return list.size();
		}
		else
			return 0;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Sets the given array of Type <b>ViewComment</b> for the object's multi-valued property <b>viewComment</b>.
	 * @param value the array value
	 */
	public void setViewComment(ViewComment[] value){
		List<ViewComment> list = (List<ViewComment>)super.get(View.PROPERTY.viewComment.name());
		if (value != null && value.length > 0) {
			if (list == null)
				list = new ArrayList<ViewComment>();
			for (int i = 0; i < value.length; i++)
				list.add(value[i]);
			super.set(View.PROPERTY.viewComment.name(), list);
		}
		else
			throw new IllegalArgumentException("expected non-null and non-zero length array argument 'value' - use unsetViewComment() method to remove this property");
	}

	@SuppressWarnings("unchecked")
	/**
	 * Adds the given value of Type <b>ViewComment</b> for the object's multi-valued property <b>viewComment</b>.
	 * @param value the value to add
	 */
	public void addViewComment(ViewComment value){
		List<ViewComment> list = (List<ViewComment>)super.get(View.PROPERTY.viewComment.name());
		if (list == null)
			list = new ArrayList<ViewComment>();
				list.add(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(View.PROPERTY.viewComment.name(), list);
	}

	@SuppressWarnings("unchecked")
	/**
	 * Removes the given value of Type <b>ViewComment</b> for the object's multi-valued property <b>viewComment</b>.
	 * @param value the value to remove
	 */
	public void removeViewComment(ViewComment value){
		List<ViewComment> list = (List<ViewComment>)super.get(View.PROPERTY.viewComment.name());
		if (list != null)
				list.remove(value);
		// NOTE: SDO 2.1 spec specifies replacing the whole list on a multi-valued 'set' operation
		super.setList(View.PROPERTY.viewComment.name(), list);
	}


	/**
	 * Returns true if the <b>viewName</b> property is set.
	 * @return true if the <b>viewName</b> property is set.
	 */
	public boolean isSetViewName(){
		return super.isSet(View.PROPERTY.viewName.name());
	}

	/**
	 * Unsets the <b>viewName</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetViewName(){
		super.unset(View.PROPERTY.viewName.name());
	}

	/**
	 * Returns the value of the <b>viewName</b> property.
	 * @return the value of the <b>viewName</b> property.
	 */
	public String getViewName(){
		return (String)super.get(View.PROPERTY.viewName.name());
	}

	/**
	 * Sets the value of the <b>viewName</b> property to the given value.
	 * <p></p>
	 * <b>Value Constraints: </b><pre>
	 *     maxLength: 30</pre>
	 */
	public void setViewName(String value){
		super.set(View.PROPERTY.viewName.name(), value);
	}


	/**
	 * Returns true if the <b>viewType</b> property is set.
	 * @return true if the <b>viewType</b> property is set.
	 */
	public boolean isSetViewType(){
		return super.isSet(View.PROPERTY.viewType.name());
	}

	/**
	 * Unsets the <b>viewType</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetViewType(){
		super.unset(View.PROPERTY.viewType.name());
	}

	/**
	 * Returns the value of the <b>viewType</b> property.
	 * @return the value of the <b>viewType</b> property.
	 */
	public String getViewType(){
		return (String)super.get(View.PROPERTY.viewType.name());
	}

	/**
	 * Sets the value of the <b>viewType</b> property to the given value.
	 */
	public void setViewType(String value){
		super.set(View.PROPERTY.viewType.name(), value);
	}


	/**
	 * Returns true if the <b>viewTypeOwner</b> property is set.
	 * @return true if the <b>viewTypeOwner</b> property is set.
	 */
	public boolean isSetViewTypeOwner(){
		return super.isSet(View.PROPERTY.viewTypeOwner.name());
	}

	/**
	 * Unsets the <b>viewTypeOwner</b> property, the value
	 * of the property of the object being set to the property's
	 * default value. The property will no longer be
	 * considered set.
	 */
	public void unsetViewTypeOwner(){
		super.unset(View.PROPERTY.viewTypeOwner.name());
	}

	/**
	 * Returns the value of the <b>viewTypeOwner</b> property.
	 * @return the value of the <b>viewTypeOwner</b> property.
	 */
	public String getViewTypeOwner(){
		return (String)super.get(View.PROPERTY.viewTypeOwner.name());
	}

	/**
	 * Sets the value of the <b>viewTypeOwner</b> property to the given value.
	 */
	public void setViewTypeOwner(String value){
		super.set(View.PROPERTY.viewTypeOwner.name(), value);
	}
}