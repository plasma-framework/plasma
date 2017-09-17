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

package org.plasma.xml.uml;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.plasma.metamodel.Model;
import org.plasma.profile.ProfileConfig;
import org.plasma.profile.ProfileURN;
import org.plasma.profile.adapter.ProfileArtifactAdapter;
import org.plasma.query.Query;
import org.plasma.xml.schema.Schema;

public class MDModelAssembler extends DefaultUMLModelAssembler implements UMLModelAssembler {
  private ProfileArtifactAdapter profile;

  public MDModelAssembler(Model model, String destNamespaceURI, String destNamespacePrefix) {
    super(model, destNamespaceURI, destNamespacePrefix);
    construct();
  }

  @Deprecated
  public MDModelAssembler(Query query, String destNamespaceURI, String destNamespacePrefix) {
    super(query, destNamespaceURI, destNamespacePrefix);
    construct();
  }

  @Deprecated
  public MDModelAssembler(Schema schema, String destNamespaceURI, String destNamespacePrefix) {
    super(schema, destNamespaceURI, destNamespacePrefix);
    construct();
  }

  private void construct() {
    // FIXME profile version as arg
    this.profile = ProfileConfig.getInstance().findArtifactByUrn(
        ProfileURN.PLASMA_SDO_PROFILE_V_1_1_MDXML);
    this.umlNs = Namespace.getNamespace("uml", this.profile.getUmlNamespaceUri());
    this.xmiNs = Namespace.getNamespace("xmi", this.profile.getXmiNamespaceUri());
    this.plasmaNs = Namespace.getNamespace("Plasma_SDO_Profile", this.profile.getNamespaceUri());
    this.xmiVersion = this.profile.getXmiVersion();
    this.dataTypeHRefPrefix = this.profile.getUrn().value() + "#plasma-sdo-profile-datatypes-";
  }

  protected Element buildProfileApplication() {
    return null;
  }

}
