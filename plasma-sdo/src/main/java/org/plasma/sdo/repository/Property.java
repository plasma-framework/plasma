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

import fUML.Syntax.Classes.Kernel.VisibilityKind;

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

	VisibilityKind getVisibility();

	boolean getIsReadonly();

	String findPhysicalName();

	String getLocalName();

	SDOAlias findAlias();

	SDOKey findKey();

	Property findKeySupplier();

	SDOConcurrent findConcurrent();

	SDOTemporal findTemporal();

	SDOEnumerationConstraint findEnumerationConstraint();

	SDOValueSetConstraint findValueSetConstraint();

	SDOValueConstraint findValueConstraint();

	SDOSort findSort();

	SDOUniqueConstraint findUniqueConstraint();

	Property findDerivationSupplier();

	SDODerivation findDerivation();

	boolean getIsPriKey();

	Long getMaxLength();

	Enumeration getRestriction();

	SDOXmlProperty findXmlProperty();

	boolean getIsConcurrencyUser();

	boolean getIsConcurrencyVersion();

	boolean getIsLockingUser();

	boolean getIsLockingTimestamp();

	boolean getIsOriginationUser();

	boolean getIsOriginationTimestamp();

	boolean getIsUnique();

}