/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    return Collections.unmodifiableList(artifact.getProperties());
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
