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
	public MDModelAssembler(Model model, String destNamespaceURI,
    		String destNamespacePrefix) {
		super(model, destNamespaceURI, destNamespacePrefix);
		construct(); 
	}

	@Deprecated
	public MDModelAssembler(Query query, String destNamespaceURI,
			String destNamespacePrefix) {
		super(query, destNamespaceURI, destNamespacePrefix);
		construct(); 
	}

	@Deprecated
	public MDModelAssembler(Schema schema, String destNamespaceURI,
			String destNamespacePrefix) {
		super(schema, destNamespaceURI, destNamespacePrefix);
		construct(); 
	}
	
	private void construct() {
		// FIXME profile version as arg
		this.profile = ProfileConfig.getInstance().findArtifactByUrn(ProfileURN.PLASMA_SDO_PROFILE_V_1_1_MDXML);
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
