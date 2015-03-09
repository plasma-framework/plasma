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
package org.plasma.xml.uml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.Text;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.plasma.common.bind.BindingValidationEventHandler;
import org.plasma.config.ConfigUtils;
import org.plasma.metamodel.Alias;
import org.plasma.metamodel.Behavior;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.ClassRef;
import org.plasma.metamodel.DataTypeRef;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.Enumeration;
import org.plasma.metamodel.EnumerationConstraint;
import org.plasma.metamodel.EnumerationLiteral;
import org.plasma.metamodel.EnumerationRef;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.Package;
import org.plasma.metamodel.Property;
import org.plasma.metamodel.ValueConstraint;
import org.plasma.provisioning.ProvisioningModelAssembler;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.SchemaProvisioningModelAssembler;
import org.plasma.query.Query;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.profile.SDOEnumerationConstraint;
import org.plasma.sdo.profile.SDOKey;
import org.plasma.sdo.profile.SDONamespace;
import org.plasma.sdo.profile.SDOSort;
import org.plasma.sdo.profile.SDOUniqueConstraint;
import org.plasma.sdo.profile.SDOValueConstraint;
import org.plasma.sdo.profile.SDOXmlProperty;
import org.plasma.xml.schema.Schema;
import org.xml.sax.SAXException;

public abstract class DefaultUMLModelAssembler implements UMLModelAssembler {
    private static Log log = LogFactory.getLog(
    		DefaultUMLModelAssembler.class); 
	
    protected String xmiVersion;
    protected Namespace umlNs; 
    protected Namespace xmiNs; 
    protected Namespace plasmaNs; 
    protected String dataTypeHRefPrefix;
    protected Namespace platformNs; 
    protected Namespace xsiNs;
    protected String xsiSchemaLocation;
	
    private Model provisioningModel;
	private String destNamespaceURI;
	private String destNamespacePrefix;
	private boolean derivePackageNamesFromURIs = true;
    private Document document;
    private Map<String, Package> packageMap = new HashMap<String, Package>();
    private Map<String, Class> classMap = new HashMap<String, Class>();
    private Map<Class, Package> classPackageMap = new HashMap<Class, Package>();
    private Map<String, Enumeration> enumMap = new HashMap<String, Enumeration>();
    private Map<Enumeration, Package> enumPackageMap = new HashMap<Enumeration, Package>();
    
     
    private Element xmiElem;
	private Element modelElem;
	private Map<String, Element> elementMap = new HashMap<String, Element>();
	private Map<String, Element> associationElementMap = new HashMap<String, Element>();
	private Map<String, Element> enumElementMap = new HashMap<String, Element>();
    
	@SuppressWarnings("unused")
	private DefaultUMLModelAssembler() {}
	
	@Deprecated
    protected DefaultUMLModelAssembler(Query query, String destNamespaceURI,
    		String destNamespacePrefix) {
		super();
		this.destNamespaceURI = destNamespaceURI;
		this.destNamespacePrefix = destNamespacePrefix;
		if (destNamespaceURI == null || destNamespaceURI.trim().length() == 0)
			throw new IllegalArgumentException("expected 'destNamespaceURI' argument");
		if (destNamespacePrefix == null || destNamespacePrefix.trim().length() == 0)
			throw new IllegalArgumentException("expected 'destNamespacePrefix' argument");
		this.document = buildDocumentModel(query, 
				this.destNamespaceURI, this.destNamespacePrefix);
	}
    
	@Deprecated
	protected DefaultUMLModelAssembler(Schema schema, String destNamespaceURI,
    		String destNamespacePrefix) {
		super();
		this.destNamespaceURI = destNamespaceURI;
		this.destNamespacePrefix = destNamespacePrefix;
		if (destNamespaceURI == null || destNamespaceURI.trim().length() == 0)
			throw new IllegalArgumentException("expected 'destNamespaceURI' argument");
		if (destNamespacePrefix == null || destNamespacePrefix.trim().length() == 0)
			throw new IllegalArgumentException("expected 'destNamespacePrefix' argument");
		this.document = buildDocumentModel(schema, 
				this.destNamespaceURI, this.destNamespacePrefix);
	}

	protected DefaultUMLModelAssembler(Model model, String destNamespaceURI,
    		String destNamespacePrefix) {
		super();
		this.destNamespaceURI = destNamespaceURI;
		this.destNamespacePrefix = destNamespacePrefix;
		if (destNamespaceURI == null || destNamespaceURI.trim().length() == 0)
			throw new IllegalArgumentException("expected 'destNamespaceURI' argument");
		if (destNamespacePrefix == null || destNamespacePrefix.trim().length() == 0)
			throw new IllegalArgumentException("expected 'destNamespacePrefix' argument");
		this.provisioningModel = model;
	}
    
	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#getDestNamespaceURI()
	 */
	@Override
	public String getDestNamespaceURI() {
		return destNamespaceURI;
	}

	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#setDestNamespaceURI(java.lang.String)
	 */
	@Override
	public void setDestNamespaceURI(String destNamespaceURI) {
		this.destNamespaceURI = destNamespaceURI;
	}

	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#getDestNamespacePrefix()
	 */
	@Override
	public String getDestNamespacePrefix() {
		return destNamespacePrefix;
	}

	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#setDestNamespacePrefix(java.lang.String)
	 */
	@Override
	public void setDestNamespacePrefix(String destNamespacePrefix) {
		this.destNamespacePrefix = destNamespacePrefix;
	}

	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#isDerivePackageNamesFromURIs()
	 */
	@Override
	public boolean isDerivePackageNamesFromURIs() {
		return derivePackageNamesFromURIs;
	}

	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#setDerivePackageNamesFromURIs(boolean)
	 */
	@Override
	public void setDerivePackageNamesFromURIs(boolean derivePackageNamesFromURIs) {
		this.derivePackageNamesFromURIs = derivePackageNamesFromURIs;
	}

	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#getDocument()
	 */
	@Override
	public Document getDocument() {
		if (this.document == null)
		    this.document = buildDocumentModel(this.provisioningModel, 
				this.destNamespaceURI, this.destNamespacePrefix);
		return this.document;
	}
		
	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#getContent()
	 */
	@Override
	@SuppressWarnings("deprecation")
	public String getContent() {
		return getContent(new XMLOutputter());
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#getContent(java.lang.String, boolean)
	 */
	@Override
	@SuppressWarnings("deprecation")
	public String getContent(java.lang.String indent, boolean newlines) {
		XMLOutputter outputter = new XMLOutputter();
		
		outputter.getFormat().setIndent(indent);
		
		return getContent(outputter);
	}

	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#getContent(org.jdom2.output.Format)
	 */
	@Override
	public String getContent(Format format) {
		return getContent(new XMLOutputter(format));
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#getContent(java.io.OutputStream)
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void getContent(OutputStream stream) {
		XMLOutputter outputter = new XMLOutputter();
		getContent(stream, outputter);
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#getContent(java.io.OutputStream, java.lang.String, boolean)
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void getContent(OutputStream stream, java.lang.String indent, boolean newlines) {
		XMLOutputter outputter = new XMLOutputter();
		outputter.getFormat().setIndent(indent);
		getContent(stream, outputter);
	}

	/* (non-Javadoc)
	 * @see org.plasma.xml.uml.UMLModelAssembler#getContent(java.io.OutputStream, org.jdom2.output.Format)
	 */
	@Override
	public void getContent(OutputStream stream, Format format) {
		getContent(stream, new XMLOutputter(format));
	}
	
	private String getContent(XMLOutputter outputter) {
		String result = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			if (this.document == null)
			    this.document = buildDocumentModel(this.provisioningModel, 
						this.destNamespaceURI, this.destNamespacePrefix);
		    outputter.output(this.document, os);
			os.flush();
			result = new String(os.toByteArray());
		} catch (java.io.IOException e) {
		}
		finally {
			try {
				os.close();
			} catch (IOException e) {
			}
		}
		return result;
	}
	
	private void getContent(OutputStream stream, XMLOutputter outputter) {
		String result = null;
		try {
			if (this.document == null)
			    this.document = buildDocumentModel(this.provisioningModel, 
						this.destNamespaceURI, this.destNamespacePrefix);
		    outputter.output(this.document, stream);
		    stream.flush();
		} catch (java.io.IOException e) {
		}
		finally {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
	}

	private Document buildDocumentModel(Query query, 
    		String destNamespaceURI, String destNamespacePrefix) 
    {
        ProvisioningModelAssembler stagingAssembler = new ProvisioningModelAssembler(query, 
        		destNamespaceURI, destNamespacePrefix);
        Model model = stagingAssembler.getModel();
        
 	    if (log.isDebugEnabled()) {
		   try {
			    BindingValidationEventHandler debugHandler = new BindingValidationEventHandler() {
					public int getErrorCount() {
						return 0;
					}
					public boolean handleEvent(ValidationEvent ve) {
				        ValidationEventLocator vel = ve.getLocator();
				        
				        String message = "Line:Col:Offset[" + vel.getLineNumber() + ":" + vel.getColumnNumber() + ":" 
				            + String.valueOf(vel.getOffset())
				            + "] - " + ve.getMessage();
				        String sev = "";
				        switch (ve.getSeverity()) {
				        case ValidationEvent.WARNING:
				        	sev = "WARNING";
				            break;
				        case ValidationEvent.ERROR:
				        	sev = "ERROR";
				            break;
				        case ValidationEvent.FATAL_ERROR:
				        	sev = "FATAL_ERROR";
				            break;
				        default:
				        }
			            log.debug(sev + " - " + message);
				        
				        
				        return true;
					}			    	
			    };
			    ProvisioningModelDataBinding binding = 
				   new ProvisioningModelDataBinding(debugHandler);
			    String xml = binding.marshal(model);
			    binding.validate(xml);
			    log.debug(xml);
			} catch (JAXBException e) {
				log.debug(e.getMessage(), e);
			} catch (SAXException e) {
				log.debug(e.getMessage(), e);
			}
	   }
        
       return buildDocumentModel(model, 
        		destNamespaceURI, destNamespacePrefix);
    }              

	private Document buildDocumentModel(Schema schema, 
    		String destNamespaceURI, String destNamespacePrefix) 
    {
		SchemaProvisioningModelAssembler stagingAssembler = new SchemaProvisioningModelAssembler(schema, 
        		destNamespaceURI, destNamespacePrefix);
        Model model = stagingAssembler.getModel();
        return buildDocumentModel(model, destNamespaceURI, destNamespacePrefix);
    }  
		
	private Document buildDocumentModel(Model model, 
        		String destNamespaceURI, String destNamespacePrefix) 
    {
    	this.xmiElem = new Element("XMI");
    	this.xmiElem.setNamespace(xmiNs);
    	Document document = new Document(this.xmiElem);
    	this.xmiElem.setAttribute(new Attribute("version", this.xmiVersion, this.xmiNs)); 
    	this.xmiElem.addNamespaceDeclaration(umlNs);
    	this.xmiElem.addNamespaceDeclaration(xmiNs);
    	this.xmiElem.addNamespaceDeclaration(plasmaNs);   
    	if (this.platformNs != null)
    		this.xmiElem.addNamespaceDeclaration(platformNs);       		
    	if (this.xsiNs != null) {
    		this.xmiElem.addNamespaceDeclaration(xsiNs);  
        	this.xmiElem.setAttribute(new Attribute("schemaLocation", this.xsiSchemaLocation, this.xsiNs));     		
    	}
    	
    	if (this.derivePackageNamesFromURIs)
    	    this.modelElem = this.buildModelFromURITokens(model);
    	else
	        this.modelElem = this.buildModelFromPackageNames(model);
    	Element profileApplicationElem = buildProfileApplication();
    	if (profileApplicationElem != null)
    		this.modelElem.addContent(profileApplicationElem);
    	
    	collectPackages(model);
    	collectClasses(model);
    	collectEnumerations(model);
    	
	    for (Class clss : this.classMap.values()) {
	    	Package pkg = this.classPackageMap.get(clss);
        	Element clssElem = buildClass(clss);        	
        	elementMap.put(clss.getId(), clssElem);
        	// build properties w/o any associations
        	for (Property property : clss.getProperties()) {
        		
        		Element ownedAttribute = buildProperty(pkg, clss, property, clssElem);
            	elementMap.put(property.getId(), ownedAttribute);        		
         	}
    	}
    	
    	// create associations
	    for (Class clss : this.classMap.values()) {
        	for (Property prop : clss.getProperties()) {
        		
        		if (prop.getType() instanceof DataTypeRef) 
        			continue;
        		
        		if (associationElementMap.get(prop.getId()) != null)
        			continue; // we created it
        		
    			String associationUUID = UUID.randomUUID().toString();
    			
    			// link assoc to both properties
    			Element leftOwnedAttribute = elementMap.get(prop.getId());
           	    leftOwnedAttribute.setAttribute(new Attribute("association", associationUUID));
    			
    			Property targetProp = getOppositeProperty(clss, prop);
    			if (targetProp != null) {
	    			Element rightOwnedAttribute = elementMap.get(targetProp.getId());
	    			rightOwnedAttribute.setAttribute(new Attribute("association", associationUUID));
	
	    			Element association = buildAssociation(prop, targetProp, this.modelElem, associationUUID);
	    			
	    			// map it to both props so we can know not to create it again
	            	associationElementMap.put(prop.getId(), association);
	            	associationElementMap.put(targetProp.getId(), association);
    			}
    			else {
    				Class targetClass = getOppositeClass(clss, prop);
	    			Element association = buildAssociation(prop, targetClass, this.modelElem, associationUUID);
	    			
	    			// map it to both props so we can know not to create it again
	            	associationElementMap.put(prop.getId(), association);
    				
    			}
         	}
    	}   	
    	
		return document;
    }
	
	private void collectPackages(Model model) {
        if (model.getUri() != null) {
    	    packageMap.put(model.getUri() + "#" + model.getName(), model);    	
        }
        for (Package pkg : model.getPackages())	{
	        if (pkg.getUri() != null) {
        	    packageMap.put(pkg.getUri() + "#" + pkg.getName(), pkg);    	
	        }
        }
	}

	//FIXME: only a model can have packages? A package cannot?
	private void collectClasses(Model model) {
		collectClasses((Package)model);
        for (Package pkg : model.getPackages())	
        	collectClasses(pkg);
	}
	
	private void collectClasses(Package pkg) {
	    for (Class clss : pkg.getClazzs()) {
	        classMap.put(clss.getUri() + "#" + clss.getName(), clss); 
	        classPackageMap.put(clss, pkg);
	    } 
	}
	
	private void collectEnumerations(Model model) {
		collectEnumerations((Package)model);
        for (Package pkg : model.getPackages())	
        	collectEnumerations(pkg);
	}
	
	private void collectEnumerations(Package pkg) {
	    for (Enumeration enm : pkg.getEnumerations()) {
	        enumMap.put(enm.getUri() + "#" + enm.getName(), enm);    	
	        enumPackageMap.put(enm, pkg);
	    } 
	}
	
	private Element buildModelFromPackageNames(Model model)
	{
    	Element modelElem = new Element("Model");
    	modelElem.setNamespace(umlNs);
    	this.xmiElem.addContent(modelElem);
    	modelElem.setAttribute(new Attribute("id", model.getId(), xmiNs));
    	modelElem.setAttribute(new Attribute("name", model.getName()));
    	modelElem.setAttribute(new Attribute("visibility", "public"));
    	elementMap.put(model.getId(), modelElem);
    	if (model.getDocumentations() != null)
    	    for (Documentation doc : model.getDocumentations()) {
        		addOwnedComment(modelElem, model.getId(), 
        				doc.getBody().getValue());
    	    }
     	
    	// Only a root (model) package
    	// tag the model w/a SDO namespace streotype, else tag the 
    	// last package descendant below
    	if (model.getPackages().size() == 0) {
	    	Element modelStereotype = new Element(SDONamespace.class.getSimpleName(), plasmaNs);
	    	this.xmiElem.addContent(modelStereotype);
	    	modelStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	    	modelStereotype.setAttribute(new Attribute(SDONamespace.BASE__PACKAGE, model.getId()));
	    	modelStereotype.setAttribute(new Attribute(SDONamespace.URI, model.getUri())); 
	    	if (model.getAlias() != null)
	    	    addAlias(model.getAlias(), model.getId());
    	}
    	
    	for (Package pkg : model.getPackages()) {

        	Element pkgElem = new Element("packagedElement");
        	modelElem.addContent(pkgElem); // add package child
        	pkgElem.setAttribute(new Attribute("type", "uml:Package", xmiNs));
        	pkgElem.setAttribute(new Attribute("id", pkg.getId(), xmiNs));
        	pkgElem.setAttribute(new Attribute("name", pkg.getName()));
        	pkgElem.setAttribute(new Attribute("visibility", "public"));
        	elementMap.put(pkg.getId(), pkgElem);
	    	if (pkg.getAlias() != null)
	    	    addAlias(pkg.getAlias(), pkg.getId());
        	
        	Element pkgStereotypeElem = new Element(SDONamespace.class.getSimpleName(), plasmaNs);
        	this.xmiElem.addContent(pkgStereotypeElem);
        	pkgStereotypeElem.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
        	pkgStereotypeElem.setAttribute(new Attribute(SDONamespace.BASE__PACKAGE, pkg.getId()));
        	pkgStereotypeElem.setAttribute(new Attribute(SDONamespace.URI, pkg.getUri()));               	
        	if (pkg.getDocumentations() != null)
        	    for (Documentation doc : model.getDocumentations()) {
            		addOwnedComment(pkgElem, pkg.getId(), 
            				doc.getBody().getValue());
	        	}
        }    	
    	
    	return modelElem;
	}
		
	private Element buildModelFromURITokens(Model model)
	{
		Element rootPackageElem = null;

		
		String[] packageNames = ConfigUtils.toPackageTokens(model.getUri());

    	Element modelElem = new Element("Model");
    	modelElem.setNamespace(umlNs);
    	this.xmiElem.addContent(modelElem);
    	modelElem.setAttribute(new Attribute("id", model.getId(), xmiNs));
    	modelElem.setAttribute(new Attribute("name", packageNames[0]));
    	modelElem.setAttribute(new Attribute("visibility", "public"));
    	elementMap.put(model.getId(), modelElem);
    	// FIXME: duplicating model level docs at package
    	// descendant level
    	if (model.getDocumentations() != null)
    	    for (Documentation doc : model.getDocumentations()) {
        		addOwnedComment(modelElem, model.getId(), 
        				doc.getBody().getValue());
    	    }
     	
    	// tag the model w/a SDO namespace streotype, else tag the 
    	// last package descendant below
    	if (packageNames.length == 1) {
	    	Element modelStereotype = new Element(SDONamespace.class.getSimpleName(), plasmaNs);
	    	this.xmiElem.addContent(modelStereotype);
	    	modelStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	    	modelStereotype.setAttribute(new Attribute(SDONamespace.BASE__PACKAGE, model.getId()));
	    	modelStereotype.setAttribute(new Attribute(SDONamespace.URI, this.destNamespaceURI));               	
    	}
    	
    	rootPackageElem = modelElem;
    	
    	for (int i = 1; i < packageNames.length; i++) {

        	Element pkgElem = new Element("packagedElement");
        	rootPackageElem.addContent(pkgElem);
        	String id = UUID.randomUUID().toString();
        	pkgElem.setAttribute(new Attribute("type", "uml:Package", xmiNs));
        	pkgElem.setAttribute(new Attribute("id", id, xmiNs));
        	pkgElem.setAttribute(new Attribute("name", packageNames[i]));
        	pkgElem.setAttribute(new Attribute("visibility", "public"));
        	elementMap.put(id, pkgElem);
        	
        	if (i == packageNames.length-1) {
	        	Element pkgStereotypeElem = new Element(SDONamespace.class.getSimpleName(), plasmaNs);
	        	this.xmiElem.addContent(pkgStereotypeElem);
	        	pkgStereotypeElem.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	        	pkgStereotypeElem.setAttribute(new Attribute(SDONamespace.BASE__PACKAGE, id));
	        	pkgStereotypeElem.setAttribute(new Attribute(SDONamespace.URI, this.destNamespaceURI));               	
	        	// FIXME: no packages or package-level docs in provisioning model
	        	// use model-level docs here
	        	if (model.getDocumentations() != null)
	        	    for (Documentation doc : model.getDocumentations()) {
	            		addOwnedComment(pkgElem, id, 
	            				doc.getBody().getValue());
		        	}
        	}
        	
        	rootPackageElem = pkgElem;        	
        }
    	
    	return rootPackageElem;
	}
	
	/**
	 * Creates and element which is a profile application for the model
	 * @return the profile application or null if not applicable for concerned subclass
	 */
	protected abstract Element buildProfileApplication();
	
	private Element buildClass(Class clss)
	{
		if (log.isDebugEnabled())
			log.debug("creating class " + clss.getName());
		// find parent element
		Package pkgParent = this.classPackageMap.get(clss);
		Element parent = this.elementMap.get(pkgParent.getId());
		
    	Element clssElem = new Element("packagedElement");
    	parent.addContent(clssElem);
    	elementMap.put(clss.getId(), clssElem);
    	clssElem.setAttribute(new Attribute("type", "uml:Class", xmiNs));
   	    clssElem.setAttribute(new Attribute("id", clss.getId(), xmiNs));
    	clssElem.setAttribute(new Attribute("name", clss.getName()));
    	clssElem.setAttribute(new Attribute("visibility", "public"));  
    	if (clss.getDocumentations() != null)
    	    for (Documentation doc : clss.getDocumentations()) {
        		addOwnedComment(clssElem, clss.getId(), 
        				doc.getBody().getValue());
    	    }
    	
    	if (clss.getAlias() != null) {
    		addAlias(clss.getAlias(), clss.getId());
    	}
    	
    	for (ClassRef baseRef : clss.getSuperClasses()) {
        	Element generalizationElem = new Element("generalization");
        	generalizationElem.setAttribute(new Attribute("type", "uml:Generalization", xmiNs));
        	generalizationElem.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
        	clssElem.addContent(generalizationElem);
        	Class baseClass = classMap.get(baseRef.getUri() + "#" + baseRef.getName());
        	generalizationElem.setAttribute(new Attribute("general", baseClass.getId()));
        	
        	/* --for an external reference
        	Element generalElem = new Element("general");
        	generalizationElem.addContent(generalElem);
        	generalElem.setAttribute(new Attribute("type", "uml:Class", xmiNs));
        	generalElem.setAttribute(new Attribute("type", "uml:Class"));
        	generalElem.setAttribute(new Attribute("href", baseClass.getId()));
        	*/
    	}
/*
  --for an external reference
		<generalization xmi:type='uml:Generalization' xmi:id='_16_0_1_1707042b_1325701279933_851156_1151'>
			<general xmi:type='uml:Class' href='plasma-platform-common.mdxml#_16_0_1_1707042b_1318367153137_813743_576'>
			</general>
		</generalization>
		
 */
    	if (clss.getBehaviors().size() > 0)
    		addBehaviors(clss, clssElem, clss.getBehaviors());
    		
    	
    	return clssElem;
	}

	/**
	 * Adds a UML owned opaque behavior ala. below for each provisioning
	 * behavior.
	 * 
	 * <ownedBehavior xmi:type='uml:OpaqueBehavior' xmi:id='_17_0_6_1707042b_1376433440849_3356_7405' name='create' visibility='public'>
	 *   <body>FOO BAR;</body>
	 *	 <language>SQL</language>
	 * </ownedBehavior>
	 * 
	 * @param clss the 
	 * @param clssElem the element
	 * @param behaviors the bahaviors
	 */
	private void addBehaviors(Class clss, Element clssElem, 
			List<Behavior> behaviors) {
		
		for (Behavior behavior : behaviors) {
	    	Element ownedBehavior = new Element("ownedBehavior");
	    	clssElem.addContent(ownedBehavior);
	    	ownedBehavior.setAttribute(new Attribute("type", "uml:OpaqueBehavior", xmiNs));
	    	ownedBehavior.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	    	ownedBehavior.setAttribute(new Attribute("visibility", "public")); 
	    	ownedBehavior.setAttribute(new Attribute("name", behavior.getType().name())); 
	    	
	    	Element language = new Element("language");
	    	language.setText(behavior.getLanguage());
	    	ownedBehavior.addContent(language);
	    	
	    	Element body = new Element("body");
	    	Text text = new Text(behavior.getValue());	    	 
	    	body.addContent(text);
	    	ownedBehavior.addContent(body);
	    	
		}

	}
	
	private void addAlias(Alias alias, String namedElementId) {
    	Element aliasStereotype = new Element(SDOAlias.class.getSimpleName(), plasmaNs);
    	this.xmiElem.addContent(aliasStereotype);
    	aliasStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	aliasStereotype.setAttribute(new Attribute(SDOAlias.BASE__NAMED_ELEMENT, namedElementId));
    	if (alias.getPhysicalName() != null)
    	    aliasStereotype.setAttribute(new Attribute(SDOAlias.PHYSICAL_NAME, 
    	    		alias.getPhysicalName()));
    	if (alias.getLocalName() != null)
    	    aliasStereotype.setAttribute(new Attribute(SDOAlias.LOCAL_NAME, 
    	    		alias.getLocalName().trim()));
    	if (alias.getBusinessName() != null)
    	    aliasStereotype.setAttribute(new Attribute(SDOAlias.BUSINESS_NAME, 
    	    		alias.getBusinessName().trim()));		
	}

	private Element buildProperty(Package pkg, Class clss, Property property, Element parentElem) {
		if (log.isDebugEnabled())
			log.debug("creating property " + property.getName());

		Element ownedAttribute = new Element("ownedAttribute");
    	parentElem.addContent(ownedAttribute);
    	ownedAttribute.setAttribute(new Attribute("type", "uml:Property", xmiNs));
    	ownedAttribute.setAttribute(new Attribute("id", property.getId(), xmiNs));
    	ownedAttribute.setAttribute(new Attribute("name", property.getName()));
    	if (property.getVisibility() != null)
    	    ownedAttribute.setAttribute(new Attribute("visibility", property.getVisibility().value())); 
    	if (property.getDocumentations() != null)
    	    for (Documentation doc : property.getDocumentations()) {
    	    	if (doc.getBody() != null)
        		    addOwnedComment(ownedAttribute, property.getId(), 
        				doc.getBody().getValue());
    	    }
    	
    	Element upperValue = new Element("upperValue");
    	ownedAttribute.addContent(upperValue);
    	upperValue.setAttribute(new Attribute("type", "uml:LiteralUnlimitedNatural", xmiNs));
   	    upperValue.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	upperValue.setAttribute(new Attribute("visibility", "public")); 
    	if (property.isMany())
    	    upperValue.setAttribute(new Attribute("value", "*")); 
    	else
	        upperValue.setAttribute(new Attribute("value", "1"));  
    	
    	Element lowerValue = new Element("lowerValue");
    	ownedAttribute.addContent(lowerValue);
    	lowerValue.setAttribute(new Attribute("type", "uml:LiteralInteger", xmiNs));
    	lowerValue.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	lowerValue.setAttribute(new Attribute("visibility", "public")); 
    	if (property.isNullable())
    		lowerValue.setAttribute(new Attribute("value", "0")); 
    	else
    		lowerValue.setAttribute(new Attribute("value", "1"));  
	
    	
    	if (property.getType() instanceof DataTypeRef) {
        	Element type = new Element("type");
        	ownedAttribute.addContent(type);
        	type.setAttribute(new Attribute("type", "uml:DataType", xmiNs));
        	//type.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
        	type.setAttribute(new Attribute("href", 
        		this.dataTypeHRefPrefix 
        			+ ((DataTypeRef)property.getType()).getName()));        			        	        	
		}
		else {			
			// set reference specific attribs
			ClassRef targetClassRef = (ClassRef)property.getType();
			Class targetClass = classMap.get(targetClassRef.getUri() + "#" + targetClassRef.getName());
        	ownedAttribute.setAttribute(new Attribute("type", targetClass.getId()));         	               	
		}
    	
    	// add stereotypes in order of "priority" in terms of how we
    	// would like them to appear in UML tools
    	if (property.getKey() != null) {
	    	Element keyStereotype = new Element(SDOKey.class.getSimpleName(), plasmaNs);
	    	this.xmiElem.addContent(keyStereotype);
	    	keyStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	    	keyStereotype.setAttribute(new Attribute(SDOKey.BASE__PROPERTY, property.getId()));
	    	keyStereotype.setAttribute(new Attribute(SDOKey.TYPE, // provisioning key-type is JAXB generated and upper-case
	    			property.getKey().getType().name().toLowerCase()));
    	}
    	
    	if (property.getUniqueConstraint() != null) {
	    	Element uniqueStereotype = new Element(SDOUniqueConstraint.class.getSimpleName(), plasmaNs);
	    	this.xmiElem.addContent(uniqueStereotype);
	    	uniqueStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	    	uniqueStereotype.setAttribute(new Attribute(SDOUniqueConstraint.BASE__PROPERTY, property.getId()));
	    	uniqueStereotype.setAttribute(new Attribute(SDOUniqueConstraint.GROUP, 
	    			property.getUniqueConstraint().getGroup()));
    	}

    	if (property.getAlias() != null) {
    		addAlias(property.getAlias(), property.getId());
    	}

    	if (property.getSort() != null) {
	    	Element sequenceStereotype = new Element(SDOSort.class.getSimpleName(), plasmaNs);
	    	this.xmiElem.addContent(sequenceStereotype);
	    	sequenceStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	    	sequenceStereotype.setAttribute(new Attribute(SDOSort.BASE__PROPERTY, property.getId()));
	    	sequenceStereotype.setAttribute(new Attribute(SDOSort.KEY, 
	    			String.valueOf(property.getSort().getKey())));
    	}

    	if (property.getXmlProperty() != null) {
	    	Element xmlPropertyStereotype = new Element(SDOXmlProperty.class.getSimpleName(), plasmaNs);
	    	this.xmiElem.addContent(xmlPropertyStereotype);
	    	xmlPropertyStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	    	xmlPropertyStereotype.setAttribute(new Attribute(SDOXmlProperty.BASE__PROPERTY, property.getId()));
	    	xmlPropertyStereotype.setAttribute(new Attribute(SDOXmlProperty.NODE_TYPE, 
	    			property.getXmlProperty().getNodeType().name().toLowerCase()));
    	}
    	    	
    	if (property.getValueConstraint() != null) {
	    	Element valueContStereotype = new Element(SDOValueConstraint.class.getSimpleName(), plasmaNs);
	    	this.xmiElem.addContent(valueContStereotype);
	    	valueContStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	    	valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.BASE__PROPERTY, property.getId()));
 	    	
	    	ValueConstraint vc = property.getValueConstraint();
	    	if (vc.getTotalDigits() != null)
	    	    valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.TOTAL_DIGITS, 
	    	    		String.valueOf(vc.getTotalDigits())));
	    	if (vc.getFractionDigits() != null)
	    	    valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.FRACTION_DIGITS, 
	    	    		String.valueOf(vc.getFractionDigits())));
	    	if (vc.getMaxInclusive() != null)
	    	    valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.MAX_INCLUSIVE, 
	    	    		String.valueOf(vc.getMaxInclusive())));
	    	if (vc.getMaxExclusive() != null)
	    	    valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.MAX_EXCLUSIVE, 
	    	    		String.valueOf(vc.getMaxExclusive())));
	    	if (vc.getMaxLength() != null)
	    	    valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.MAX_LENGTH, 
	    	    		String.valueOf(vc.getMaxLength())));
	    	if (vc.getMinInclusive() != null)
	    	    valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.MIN_INCLUSIVE, 
	    	    		String.valueOf(vc.getMinInclusive())));
	    	if (vc.getMinExclusive() != null)
	    	    valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.MIN_EXCLUSIVE, 
	    	    		String.valueOf(vc.getMinExclusive())));
	    	if (vc.getMinLength() != null)
	    	    valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.MIN_LENGTH, 
	    	    		String.valueOf(vc.getMinLength())));
	    	if (vc.getPattern() != null)
	    	    valueContStereotype.setAttribute(new Attribute(SDOValueConstraint.PATTERN, 
	    	    		String.valueOf(vc.getPattern())));
    	}
    	    			
	    if (property.getEnumerationConstraint() != null) {
	    	Element enumConstraintStereotype = new Element(SDOEnumerationConstraint.class.getSimpleName(), plasmaNs);
	    	this.xmiElem.addContent(enumConstraintStereotype);
	    	enumConstraintStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
	    	enumConstraintStereotype.setAttribute(new Attribute(SDOUniqueConstraint.BASE__PROPERTY, property.getId()));

	    	EnumerationConstraint constraint = property.getEnumerationConstraint();
	    	EnumerationRef enumRef = constraint.getValue();
	    	String enumRefId = enumRef.getUri() + "#" + enumRef.getName();
	    	
    		Element enumeration = this.enumElementMap.get(enumRefId);
    		if (enumeration == null) {
    			enumeration = this.buildEnumeration(constraint);
    			Element pkgElement = this.elementMap.get(pkg.getId());
    			pkgElement.addContent(enumeration);
        	    this.enumElementMap.put(enumRefId, enumeration);
    		}
    		Attribute enumId = enumeration.getAttribute("id", xmiNs);
    		enumConstraintStereotype.setAttribute(new Attribute(SDOEnumerationConstraint.VALUE, enumId.getValue()));
	    }
    	
		
		return ownedAttribute;
	}
	
	private Element buildAssociation(Property property, Property targetProperty, 
			Element parentElem, String uuid)
	{
    	Element associationElem = new Element("packagedElement");
    	parentElem.addContent(associationElem);
    	associationElementMap.put(property.getId(), associationElem);
    	associationElementMap.put(targetProperty.getId(), associationElem);
    	associationElem.setAttribute(new Attribute("type", "uml:Association", xmiNs));
    	associationElem.setAttribute(new Attribute("id", uuid, xmiNs));
    	associationElem.setAttribute(new Attribute("visibility", "public")); 

        Element leftMemberEnd = new Element("memberEnd");
    	associationElem.addContent(leftMemberEnd);
    	leftMemberEnd.setAttribute(new Attribute("idref", property.getId(), xmiNs));
		
    	Element rightMemberEnd = new Element("memberEnd");
    	associationElem.addContent(rightMemberEnd);
    	rightMemberEnd.setAttribute(new Attribute("idref", targetProperty.getId(), xmiNs));
		
    	return associationElem;
	}
	
	private Element buildAssociation(Property property, Class targetClass, 
			Element parentElem, String uuid)
	{
    	Element association = new Element("packagedElement");
    	parentElem.addContent(association);
    	association.setAttribute(new Attribute("type", "uml:Association", xmiNs));
    	association.setAttribute(new Attribute("id", uuid, xmiNs));
    	association.setAttribute(new Attribute("visibility", "public")); 

        Element leftMemberEnd = new Element("memberEnd");
    	association.addContent(leftMemberEnd);
    	leftMemberEnd.setAttribute(new Attribute("idref", property.getId(), xmiNs));
		
		String rightPropId = UUID.randomUUID().toString();
    	Element rightMemberEnd = new Element("memberEnd");
    	association.addContent(rightMemberEnd);
    	rightMemberEnd.setAttribute(new Attribute("idref", rightPropId, xmiNs));

    	Element navigableOwnedEnd = new Element("navigableOwnedEnd");
    	association.addContent(navigableOwnedEnd);
    	navigableOwnedEnd.setAttribute(new Attribute("idref", rightPropId, xmiNs));

    	Element ownedEnd = new Element("ownedEnd");
    	association.addContent(ownedEnd);
    	ownedEnd.setAttribute(new Attribute("type", "uml:Property", xmiNs));
    	ownedEnd.setAttribute(new Attribute("id", rightPropId, xmiNs));
    	ownedEnd.setAttribute(new Attribute("visibility", "private"));
    	ownedEnd.setAttribute(new Attribute("type", targetClass.getId()));
   	
    	// if its external we'll need this goo
    	/*
    	{
        	targetClassId = targetClassInfo.getSharedPackage().getExternalReferenceBase() + "#" + targetClassInfo.getUuid();
        	Element type = new Element("type");
        	ownedEnd.addContent(type);
        	type.setAttribute(new Attribute("type", "uml:Class", xmiNs));
        	type.setAttribute(new Attribute("href", targetClassId));        	
    	}    	
        */

    	Element upperValue = new Element("upperValue");
    	ownedEnd.addContent(upperValue);
    	upperValue.setAttribute(new Attribute("type", "uml:LiteralUnlimitedNatural", xmiNs));
    	upperValue.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	upperValue.setAttribute(new Attribute("visibility", "public"));
    	upperValue.setAttribute(new Attribute("value", "*"));

    	Element lowerValue = new Element("lowerValue");
    	ownedEnd.addContent(lowerValue);
    	lowerValue.setAttribute(new Attribute("type", "uml:LiteralInteger", xmiNs));
    	lowerValue.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	lowerValue.setAttribute(new Attribute("visibility", "public"));
    	
    	return association;
	}
	
	private Element buildEnumeration(EnumerationConstraint constraint)
	{
		Enumeration enm = this.enumMap.get(
				constraint.getValue().getUri() + "#" + constraint.getValue().getName());
		
    	Element enumerationElem = new Element("packagedElement");
    	enumerationElem.setAttribute(new Attribute("type", "uml:Enumeration", xmiNs));
    	String enumId = UUID.randomUUID().toString();
    	enumerationElem.setAttribute(new Attribute("id", enumId, xmiNs));
    	enumerationElem.setAttribute(new Attribute("name", enm.getName()));
    	enumerationElem.setAttribute(new Attribute("visibility", "public")); 

    	if (enm.getDocumentations() != null)
    	    for (Documentation doc : enm.getDocumentations()) {
        		addOwnedComment(enumerationElem, enumId, 
        				doc.getBody().getValue());
    	    }

	    for (EnumerationLiteral lit : enm.getEnumerationLiterals()) {
        	Element literal = new Element("ownedLiteral");
        	enumerationElem.addContent(literal);
        	literal.setAttribute(new Attribute("type", "uml:EnumerationLiteral", xmiNs));
        	String literalId = UUID.randomUUID().toString();
        	literal.setAttribute(new Attribute("id", literalId, xmiNs));
        	literal.setAttribute(new Attribute("name", lit.getValue()));
        	literal.setAttribute(new Attribute("visibility", "public")); 
        	if (lit.getDocumentations() != null)
        	    for (Documentation doc : lit.getDocumentations()) {
            		addOwnedComment(literal, literalId, 
            				doc.getBody().getValue());
        	    }

        	if (lit.getAlias() != null && lit.getAlias().getPhysicalName() != null) {
    	    	Element aliasStereotype = new Element(SDOAlias.class.getSimpleName(), plasmaNs);
    	    	this.xmiElem.addContent(aliasStereotype);
    	    	aliasStereotype.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));
    	    	aliasStereotype.setAttribute(new Attribute(SDOAlias.BASE__NAMED_ELEMENT, literalId));
    	    	aliasStereotype.setAttribute(new Attribute(SDOAlias.PHYSICAL_NAME, 
    	    			lit.getAlias().getPhysicalName()));
        	}
        }
		return enumerationElem;
	    
	}
	
	private void addOwnedComment(Element owner, String ownerId, String commentBody) {
    	Element comment = new Element("ownedComment");
    	owner.addContent(comment);
    	comment.setAttribute(new Attribute("type", "uml:Comment", xmiNs));
    	comment.setAttribute(new Attribute("id", UUID.randomUUID().toString(), xmiNs));

    	Element body = new Element("body");
    	comment.addContent(body);
    	body.addContent(new CDATA(commentBody));
    	
    	Element annotatedElement = new Element("annotatedElement");
    	comment.addContent(annotatedElement);
    	annotatedElement.setAttribute(new Attribute("idref", ownerId, xmiNs));    				
	}
	
	private Class getOppositeClass(Class def, Property propertyDef) {
		Class targetDef = classMap.get(propertyDef.getType().getUri() 
    			+ "#" + propertyDef.getType().getName());
    	return targetDef;
	}

	private Property getOppositeProperty(Class def, Property propertyDef) {
		Class targetDef = classMap.get(propertyDef.getType().getUri() 
    			+ "#" + propertyDef.getType().getName());
    	for (Property p : targetDef.getProperties()) {
    		if (p.getName().equals(propertyDef.getOpposite()))
    			return p;
    	}
    	return null;
	}
	
}
