package org.plasma.sdo.core;

import org.plasma.sdo.xml.XMLOptions;

import commonj.sdo.DataObject;
import commonj.sdo.helper.XMLDocument;

public class CoreXMLDocument implements XMLDocument {

	private String encoding;
	private boolean isSetXmlDeclaration = true;
	private String xmlVersion = "1.0";
	private DataObject rootDataObject;
	private String rootElementURI;
	private String rootElementName;
	private String schemaLocation;
	private String noNamespaceSchemaLocation;

	@SuppressWarnings("unused")
	private CoreXMLDocument() {
	}

	public CoreXMLDocument(DataObject rootDataObject) {
		this.rootDataObject = rootDataObject;
    	if (this.rootDataObject == null)
    		throw new IllegalArgumentException("expected 'rootDataObject' argument");
	}
	
	public CoreXMLDocument(DataObject rootDataObject, XMLOptions xmlOptions) {
		this.rootDataObject = rootDataObject;
    	if (this.rootDataObject == null)
    		throw new IllegalArgumentException("expected 'rootDataObject' argument");
    	this.rootElementURI = xmlOptions.getRootElementNamespaceURI();
    	this.rootElementName = xmlOptions.getRootElementName();
	}
	
	public CoreXMLDocument(DataObject rootDataObject, 
			String rootElementURI, String rootElementName) {
		this.rootDataObject = rootDataObject;
		this.rootElementURI = rootElementURI;
		this.rootElementName = rootElementName;
    	if (this.rootDataObject == null)
    		throw new IllegalArgumentException("expected 'rootDataObject' argument");
    	if (this.rootElementURI == null)
    		throw new IllegalArgumentException("expected 'rootElementURI' argument");
	    // rootElementName name optional
	}

	/**
	 * Return the XML encoding of the document, or null if not specified. The
	 * default value is "UTF-8". Specification of other values is
	 * implementation-dependent.
	 * 
	 * @return the XML encoding of the document, or null if not specified.
	 */
	public String getEncoding() {
		return this.encoding;
	}

	/**
	 * Return the value of the noNamespaceSchemaLocation declaration for the
	 * http://www.w3.org/2001/XMLSchema-instance namespace in the root element,
	 * or null if not present.
	 * 
	 * @return the value of the noNamespaceSchemaLocation declaration, or null
	 *         if not present.
	 */
	public String getNoNamespaceSchemaLocation() {
		return this.noNamespaceSchemaLocation;
	}

	/**
	 * Return the name of the root element. The root element is a global element
	 * of the XML Schema with a type compatible to the DataObject.
	 * 
	 * @return the name of the root element.
	 */
	public String getRootElementName() {
		return this.rootElementName;
	}

	/**
	 * Return the targetNamespace URI for the root element. If there is no
	 * targetNamespace URI, the value is null. The root element is a global
	 * element of the XML Schema with a type compatible to the DataObject.
	 * 
	 * @return the targetNamespace URI for the root element.
	 */
	public String getRootElementURI() {
		return this.rootElementURI;
	}

	/**
	 * Return the root DataObject for the XMLDocument.
	 * 
	 * @return root DataObject for the XMLDocument.
	 */
	public DataObject getRootObject() {
		return this.rootDataObject;
	}

	/**
	 * Return the value of the schemaLocation declaration for the
	 * http://www.w3.org/2001/XMLSchema-instance namespace in the root element,
	 * or null if not present.
	 * 
	 * @return the value of the schemaLocation declaration, or null if not
	 *         present.
	 */
	public String getSchemaLocation() {
		return this.schemaLocation;
	}

	public String getXMLVersion() {
		return this.xmlVersion;
	}

	public boolean isXMLDeclaration() {
		return this.isSetXmlDeclaration;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setNoNamespaceSchemaLocation(String schemaLocation) {
		this.noNamespaceSchemaLocation = schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public void setXMLDeclaration(boolean xmlDeclaration) {
		this.isSetXmlDeclaration = xmlDeclaration;
	}

	public void setXMLVersion(String xmlVersion) {
		this.xmlVersion = xmlVersion;
	}

}
