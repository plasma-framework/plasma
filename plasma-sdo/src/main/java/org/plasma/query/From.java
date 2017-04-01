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
package org.plasma.query;

/**
 * A clause describing the query extent or root type
 */
public interface From {

	/**
	 * Returns the URI
	 * 
	 * @return the URI
	 */
	public String getUri();

	/**
	 * Returns the type (logical) name
	 * 
	 * @return the type (logical) name
	 */
	public String getName();

	/**
	 * Gets the value of the randomSample property. A floating point value
	 * between zero (result entity is never included) and 1 (result entity is
	 * always included) used to sample or filter results. Cannot be used with
	 * with (potentially) many types of predicates depending on the restrictions
	 * within the underlying data store.
	 * 
	 * @return possible object is {@link Float }
	 * 
	 */
	public Float getRandomSample();
}