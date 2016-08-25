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
import org.plasma.sdo.Derivation;

/**
 * 
 * @author Scott Cinnamond
 * @since 1.2.4
 */public interface Classifier extends Element {

	/**
	 * Returns the (UML) artifact URI associated with the classifier, if available, or
	 * null e.g. if the classifier was not loaded as part of an artifact.    
	 * @return the (UML) artifact URI associated with the classifier, if available, or
	 * null e.g. if the classifier was not loaded as part of an artifact.
	 */
	String getArtifactNamespaceURI();

	String getPackageName();

	String getPackagePhysicalName();

	Alias findPackageAlias();

	Derivation findDerivation();

	List<Comment> getComments();

	Visibility getVisibility();

	boolean isAbstract();

	boolean isDataType();

	List<Classifier> getGeneralization();

	List<Classifier> getSpecializations();

	/**
	 * Traverses the given classifier's package ancestry looking for
	 * an SDO namespace stereotype, and if found, returns the URI.
	 * @param classifier the classifier
	 * @return the SDO namespace URI or null if not found.
	 * @throws RepositoryException if the URI is not found
	 */
	String getNamespaceURI();

	String getPhysicalName();

	String getLocalName();

	String getBusinessName();

	Alias findAlias();

	Classifier getDerivationSupplier();


}