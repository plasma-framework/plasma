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
package org.plasma.sdo.xpath;

import commonj.sdo.DataObject;
import commonj.sdo.Property;

/**
 * Wraps a result or "endpoint" of a Jaxen XPATH parse, supplying 
 * source traversal information, e.g. the Data Object and Property which served as the
 * source or "parent" of a particular traversal path.  
 */
public abstract class DataGraphNodeAdapter {
	protected DataObject source;
	protected Property sourceProperty;
    @SuppressWarnings("unused")
	private DataGraphNodeAdapter() {}
	public DataGraphNodeAdapter(DataObject source, Property sourceProperty) {
		super();
		this.source = source; 
		this.sourceProperty = sourceProperty; 
		if (this.source == null)
			throw new IllegalArgumentException("expected non-null argument 'source'");
		if (this.sourceProperty == null)
			throw new IllegalArgumentException("expected non-null argument 'sourceProperty'");
	}
	
	/**
	 * Returns the dereferenced value of an XPATH parse result. 
	 * This method generalizes how subclasses containing
	 * either data xpath result (e.g. a Data Object or value) or meta-data 
	 * xpath results (e.g. a Property) return or dereference
	 * the results differently.  
	 * @return the result 
	 */
	public abstract Object get();

	/**
	 * Sets the given value into the XPATH parse result.
	 * Abstract method to generalize how subclasses containing
	 * either data xpath result (e.g. a Data Object or value) or meta-data 
	 * xpath results (e.g. a Property) dereference
	 * the results differently. 
	 */
	public abstract void set(Object value);
	

	/**
	 * Returns the data object traversal source
	 * @return the source data object
	 */
	public DataObject getSource() {
		return this.source;
	}
	
	/**
	 * Returns the property traversal source
	 * @return the source property
	 */
	public Property getSourceProperty() {
		return this.sourceProperty;
	}
}
