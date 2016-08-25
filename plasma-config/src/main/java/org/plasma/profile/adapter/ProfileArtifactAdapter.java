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
package org.plasma.profile.adapter;

import java.util.Collections;
import java.util.List;

import org.plasma.profile.ProfileArtifact;
import org.plasma.profile.ProfileURN;
import org.plasma.profile.Property;

/**
 * 
 */
public class ProfileArtifactAdapter {
    private ProfileArtifact artifact;

	public ProfileArtifactAdapter(ProfileArtifact artifact) {
		super();
		this.artifact = artifact;
	}

	public List<Property> getProperties() {
		return Collections.unmodifiableList(artifact.getProperty());
	}

	public ProfileURN getUrn() {
		return artifact.getUrn();
	}

	public String getNamespaceUri() {
		return artifact.getNamespaceUri();
	}

	public String getId() {
		return artifact.getId();
	}

	public String getUmlNamespaceUri() {
		return artifact.getUmlNamespaceUri();
	}

	public String getXmiNamespaceUri() {
		return artifact.getXmiNamespaceUri();
	}

	public String getEcoreNamespaceUri() {
		return artifact.getEcoreNamespaceUri();
	}

	public String getVersion() {
		return artifact.getVersion();
	}

	public String getUmlVersion() {
		return artifact.getUmlVersion();
	}

	public String getXmiVersion() {
		return artifact.getXmiVersion();
	}

	public String getEcoreVersion() {
		return artifact.getEcoreVersion();
	}

	public String getEcoreId() {
		return artifact.getEcoreId();
	}
    
}
