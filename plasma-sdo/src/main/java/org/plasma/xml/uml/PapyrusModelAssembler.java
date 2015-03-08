package org.plasma.xml.uml;

import java.util.UUID;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.plasma.metamodel.Model;
import org.plasma.profile.ProfileConfig;
import org.plasma.profile.ProfileURN;
import org.plasma.profile.adapter.ProfileArtifactAdapter;
import org.plasma.query.Query;
import org.plasma.sdo.profile.SDONamespace;
import org.plasma.xml.schema.Schema;

public class PapyrusModelAssembler extends DefaultUMLModelAssembler implements UMLModelAssembler {
	private ProfileArtifactAdapter profile; 
	public PapyrusModelAssembler(Model model, String destNamespaceURI,
    		String destNamespacePrefix) {
		super(model, destNamespaceURI, destNamespacePrefix);
		construct(); 
	}

	@Deprecated
	public PapyrusModelAssembler(Query query, String destNamespaceURI,
			String destNamespacePrefix) {
		super(query, destNamespaceURI, destNamespacePrefix);
		construct(); 
	}

	@Deprecated
	public PapyrusModelAssembler(Schema schema, String destNamespaceURI,
			String destNamespacePrefix) {
		super(schema, destNamespaceURI, destNamespacePrefix);
		construct(); 
	}

	private void construct() {
		// FIXME profile version as arg
		this.profile = ProfileConfig.getInstance().findArtifactByUrn(ProfileURN.PLASMA_SDO___PROFILE___V_1___1___UML);
	    this.umlNs = Namespace.getNamespace("uml", this.profile.getUmlNamespaceUri()); 
	    this.xmiNs = Namespace.getNamespace("xmi", this.profile.getXmiNamespaceUri()); 
	    
	    this.plasmaNs = Namespace.getNamespace("PlasmaSDOProfile", this.profile.getNamespaceUri()); 
	    this.xmiVersion = this.profile.getXmiVersion();
	    this.dataTypeHRefPrefix = "pathmap://PLASMA_LIBRARIES/PlasmaSDODataTypes.uml#plasma-sdo-profile-datatypes-";
	    
	    this.platformNs = Namespace.getNamespace("ecore", this.profile.getEcoreNamespaceUri()); 
	    
	    this.xsiNs = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	    this.xsiSchemaLocation = this.plasmaNs.getURI() + " pathmap://PLASMA_PROFILES/PlasmaSDO.profile.uml#" + this.profile.getId();
	}
	
	protected Element buildProfileApplication() 
	{
    	Element profileApplicationElem = new Element("profileApplication");
    	profileApplicationElem.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	profileApplicationElem.setAttribute(new Attribute("type", "uml:ProfileApplication", xmiNs));
    	
    	Element eAnnotationsElem = new Element("eAnnotations");
    	profileApplicationElem.addContent(eAnnotationsElem);
    	eAnnotationsElem.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	eAnnotationsElem.setAttribute(new Attribute("type", "ecore:EAnnotation", xmiNs));
    	eAnnotationsElem.setAttribute(new Attribute("source", 
    			this.profile.getUmlNamespaceUri()));   
    	Element referencesElem = new Element("references");
    	eAnnotationsElem.addContent(referencesElem);
    	referencesElem.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	referencesElem.setAttribute(new Attribute("type", "ecore:EPackage", xmiNs));
    	referencesElem.setAttribute(new Attribute("href", 
        	"pathmap://PLASMA_PROFILES/PlasmaSDO.profile.uml#" + this.profile.getId()));  
    	
    	Element appliedProfileElem = new Element("appliedProfile");
    	profileApplicationElem.addContent(appliedProfileElem);
    	appliedProfileElem.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	appliedProfileElem.setAttribute(new Attribute("type", "uml:Profile", xmiNs));
    	appliedProfileElem.setAttribute(new Attribute("href", 
    		"pathmap://PLASMA_PROFILES/PlasmaSDO.profile.uml#" + this.profile.getId()));  
    	
    	return profileApplicationElem;

	}
	
}
