package org.plasma.xml.uml;

import java.util.UUID;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.plasma.provisioning.Model;
import org.plasma.query.Query;
import org.plasma.sdo.profile.SDONamespace;
import org.plasma.xml.schema.Schema;

public class PapyrusModelAssembler extends DefaultUMLModelAssembler implements UMLModelAssembler {
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
	    // FIXME: hard baked namespaces w/versions
	    this.umlNs = Namespace.getNamespace("uml", "http://www.eclipse.org/uml2/5.0.0/UML"); 
	    this.xmiNs = Namespace.getNamespace("xmi", "http://www.omg.org/spec/XMI/20131001"); 
	    // FIXME: changes with every Profile change !!
	    this.plasmaNs = Namespace.getNamespace("PlasmaSDOProfile", "http:///schemas/PlasmaSDOProfile/_Cvni8DqeEeSIqNFH5qbSSw/28"); 
	    this.xmiVersion = "20131001";
	    this.dataTypeHRefPrefix = "pathmap://PLASMA_LIBRARIES/PlasmaSDODataTypes.uml#plasma-sdo-profile-datatypes-";
	    
	    this.platformNs = Namespace.getNamespace("ecore", "http://www.eclipse.org/emf/2002/Ecore"); 
	    
	    this.xsiNs = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
	    this.xsiSchemaLocation = this.plasmaNs.getURI() + " pathmap://PLASMA_PROFILES/PlasmaSDO.profile.uml#_Cvr0YDqeEeSIqNFH5qbSSw";
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
    		"http://www.eclipse.org/uml2/2.0.0/UML")); // FIXME: really? 
    	Element referencesElem = new Element("references");
    	eAnnotationsElem.addContent(referencesElem);
    	referencesElem.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	referencesElem.setAttribute(new Attribute("type", "ecore:EPackage", xmiNs));
    	referencesElem.setAttribute(new Attribute("href", 
        	"pathmap://PLASMA_PROFILES/PlasmaSDO.profile.uml#_Cvr0YDqeEeSIqNFH5qbSSw")); // FIXME: XMI ID required by Papyrus but could change
    	
    	Element appliedProfileElem = new Element("appliedProfile");
    	profileApplicationElem.addContent(appliedProfileElem);
    	appliedProfileElem.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	appliedProfileElem.setAttribute(new Attribute("type", "uml:Profile", xmiNs));
    	appliedProfileElem.setAttribute(new Attribute("href", 
    		"pathmap://PLASMA_PROFILES/PlasmaSDO.profile.uml#_NlmngCJUEeSXvPZh3aumVA")); // FIXME: XMI ID required by Papyrus but could change 
    	
    	return profileApplicationElem;

	}
	
}
