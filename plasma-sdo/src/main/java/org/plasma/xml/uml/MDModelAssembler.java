package org.plasma.xml.uml;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.plasma.provisioning.Model;
import org.plasma.query.Query;
import org.plasma.xml.schema.Schema;

public class MDModelAssembler extends DefaultUMLModelAssembler implements UMLModelAssembler {
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
	    // FIXME: hard baked namespaces w/versions
	    this.umlNs = Namespace.getNamespace("uml", "http://schema.omg.org/spec/UML/2.1.2"); 
	    this.xmiNs = Namespace.getNamespace("xmi", "http://schema.omg.org/spec/XMI/2.1"); 
	    this.plasmaNs = Namespace.getNamespace("Plasma_SDO_Profile", "http://www.magicdraw.com/schemas/Plasma_SDO_Profile.xmi"); 
	    this.xmiVersion = "2.1";
	    this.dataTypeHRefPrefix = "Plasma_SDO_Profile.mdxml#plasma-sdo-profile-datatypes-";
	}
	
	protected Element buildProfileApplication() {
		return null;
	}

}
