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
package org.plasma.sdo.repository;

import java.util.List;

import org.plasma.sdo.Alias;
import org.plasma.sdo.Concurrent;
import org.plasma.sdo.Derivation;
import org.plasma.sdo.EnumerationConstraint;
import org.plasma.sdo.Key;
import org.plasma.sdo.Sort;
import org.plasma.sdo.Temporal;
import org.plasma.sdo.UniqueConstraint;
import org.plasma.sdo.ValueConstraint;
import org.plasma.sdo.ValueSetConstraint;
import org.plasma.sdo.XmlProperty;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.profile.SDOConcurrent;
import org.plasma.sdo.profile.SDODerivation;
import org.plasma.sdo.profile.SDOEnumerationConstraint;
import org.plasma.sdo.profile.SDOKey;
import org.plasma.sdo.profile.SDOSort;
import org.plasma.sdo.profile.SDOTemporal;
import org.plasma.sdo.profile.SDOUniqueConstraint;
import org.plasma.sdo.profile.SDOValueConstraint;
import org.plasma.sdo.profile.SDOValueSetConstraint;
import org.plasma.sdo.profile.SDOXmlProperty;


/**
 * 
 * @author Scott Cinnamond
 * @since 1.2.4
 */
public interface Property extends Element {

	Class_ getClass_();

	Object findPropertyDefault();

	boolean isMany();

	boolean isNullable();

	/**
	 * Return the associated opposite for this property, or null if non exists. 
	 * @return the associated opposite for this property, or null if non exists.
	 */
	Property getOpposite();

	List<Comment> getComments();

	boolean isDataType();
	
	Classifier getType();

	Visibility getVisibility();

	boolean getIsReadonly();

	String findPhysicalName();

	String getLocalName();

	Alias findAlias();

	Key findKey();

	Property findKeySupplier();

	Concurrent findConcurrent();

	Temporal findTemporal();

	EnumerationConstraint findEnumerationConstraint();

	ValueSetConstraint findValueSetConstraint();

	ValueConstraint findValueConstraint();

	Sort findSort();

	UniqueConstraint findUniqueConstraint();

	Property findDerivationSupplier();

	Derivation findDerivation();

	boolean getIsPriKey();

	Long getMaxLength();

	Enumeration getRestriction();

	XmlProperty findXmlProperty();

	boolean getIsConcurrencyUser();

	boolean getIsConcurrencyVersion();

	boolean getIsLockingUser();

	boolean getIsLockingTimestamp();

	boolean getIsOriginationUser();

	boolean getIsOriginationTimestamp();

	boolean getIsUnique();

}