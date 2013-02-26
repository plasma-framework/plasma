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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaSequence;
import org.plasma.sdo.PlasmaSetting;

import commonj.sdo.Property;

public class CoreSequence implements PlasmaSequence {
	
	private PlasmaDataObject dataObject;
	private List<PlasmaSetting> settings = new ArrayList<PlasmaSetting>();
	
	@SuppressWarnings("unused")
	private CoreSequence() {}
	
	CoreSequence(PlasmaDataObject dataObject) {
		this.dataObject = dataObject;

	}	

	/**
	 * Returns the number of entries in the sequence.
	 * @return the number of entries.
	 */
	public int size() {
		return this.settings.size();
	}

	/**
	 * Returns the property for the given entry index. Returns <code>null</code>
	 * for mixed text entries.
	 * 
	 * @param index
	 *            the index of the entry.
	 * @return the property or <code>null</code> for the given entry index.
	 */
	public Property getProperty(int index) {
		return this.settings.get(index).getProperty(); 
	}

	/**
	 * Returns the property value for the given entry index.
	 * 
	 * @param index
	 *            the index of the entry.
	 * @return the value for the given entry index.
	 */
	public Object getValue(int index) {
		return this.settings.get(index).getValue(); 
	}

	/**
	 * Sets the entry at a specified index to the new value.
	 * 
	 * @param index
	 *            the index of the entry.
	 * @param value
	 *            the new value for the entry.
	 */
	public Object setValue(int index, Object value) {
		return null; // FIXME: not implemented
	}

	/**
	 * Adds a new entry with the specified property name and value to the end of
	 * the entries.
	 * 
	 * @param propertyName
	 *            the name of the entry's property.
	 * @param value
	 *            the value for the entry.
	 */
	public boolean add(String propertyName, Object value) {
		return false; // FIXME: not implemented
	}

	/**
	 * Adds a new entry with the specified property index and value to the end
	 * of the entries.
	 * 
	 * @param propertyIndex
	 *            the index of the entry's property.
	 * @param value
	 *            the value for the entry.
	 */
	public boolean add(int propertyIndex, Object value) {
		return false; // FIXME: not implemented
	}

	/**
	 * Adds a new entry with the specified property and value to the end of the
	 * entries.
	 * 
	 * @param property
	 *            the property of the entry.
	 * @param value
	 *            the value for the entry.
	 */
	public boolean add(Property property, Object value) {
		return false; // FIXME: not implemented
	}

	/**
	 * Adds a new entry with the specified property name and value at the
	 * specified entry index.
	 * 
	 * @param index
	 *            the index at which to add the entry.
	 * @param propertyName
	 *            the name of the entry's property.
	 * @param value
	 *            the value for the entry.
	 */
	public void add(int index, String propertyName, Object value) {
		// FIXME: not implemented
	}

	/**
	 * Adds a new entry with the specified property index and value at the
	 * specified entry index.
	 * 
	 * @param index
	 *            the index at which to add the entry.
	 * @param propertyIndex
	 *            the index of the entry's property.
	 * @param value
	 *            the value for the entry.
	 */
	public void add(int index, int propertyIndex, Object value) {
		// FIXME: not implemented
	}

	/**
	 * Adds a new entry with the specified property and value at the specified
	 * entry index.
	 * 
	 * @param index
	 *            the index at which to add the entry.
	 * @param property
	 *            the property of the entry.
	 * @param value
	 *            the value for the entry.
	 */
	public void add(int index, Property property, Object value) {
		// FIXME: not implemented
	}

	/**
	 * Removes the entry at the given entry index.
	 * 
	 * @param index
	 *            the index of the entry.
	 */
	public void remove(int index) {
		// FIXME: not implemented
	}

	/**
	 * Moves the entry at <code>fromIndex</code> to <code>toIndex</code>.
	 * 
	 * @param toIndex
	 *            the index of the entry destination.
	 * @param fromIndex
	 *            the index of the entry to move.
	 */
	public void move(int toIndex, int fromIndex) {
		// FIXME: not implemented
	}

	/**
	 * @deprecated replaced by {@link #addText(String)} in 2.1.0
	 */
	public void add(String text) {
		// FIXME: not implemented
	}

	/**
	 * @deprecated replaced by {@link #addText(int, String)} in 2.1.0
	 */
	public void add(int index, String text) {
		// FIXME: not implemented
	}

	/**
	 * Adds a new text entry to the end of the Sequence.
	 * 
	 * @param text
	 *            value of the entry.
	 */
	public void addText(String text) {
		// FIXME: not implemented
	}

	/**
	 * Adds a new text entry at the given index.
	 * 
	 * @param index
	 *            the index at which to add the entry.
	 * @param text
	 *            value of the entry.
	 */
	public void addText(int index, String text) {
		// FIXME: not implemented
	}
}
