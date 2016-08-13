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
 
/**
 * 
 * @author Scott Cinnamond
 * @since 1.2.4
 */
public interface Repository {

	List<String> getAllNamespaceUris();

	List<Namespace> getAllNamespaces();

	Namespace getNamespaceForUri(String uri);

	/**
	 * Returns a list of classifiers contained by the package, as well as all 
	 * classifiers within its contained packages, linked/stereotyped through an SDONamespace
	 * stereotype where the stereotype uri matches the given uri. 
	 * @see org.plasma.sdo.profile.SDONamespace
	 * @param uri the SDO namespace URI
	 * @return a list of classifiers contained by the package, as wells as all 
	 * classifiers within its contained packages, linked through an SDONamespace
	 * stereotype where the uri which matches the given uri.  
	 */
	List<Classifier> getClassifiers(String uri);

	Classifier getClassifier(String name);

	Classifier findClassifier(String name);

	Element findElementById(String id);

	Element findElementByName(String name);

	Element findElementByQualifiedName(String qualifiedName);

	Class_ getClassById(String id);
	Enumeration getEnumerationById(String id);
	EnumerationLiteral getEnumerationLiteralById(String id);
	Classifier getClassifierById(String id);
	Property getPropertyById(String id);
	Element getElementById(String id);

	Element getElementByName(String name);

	Element getElementByQualifiedName(String qualifiedName);

	Classifier getClassifierByName(String name);

	Classifier getClassifierByQualifiedName(String qualifiedName);

	String getDefaultUMLNamespaceURI();

	RelationCache getRelationCache();

}