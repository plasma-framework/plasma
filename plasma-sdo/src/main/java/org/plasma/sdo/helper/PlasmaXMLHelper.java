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
package org.plasma.sdo.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.xmi.stream.StreamReader;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.common.io.StreamCopier;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaDataObjectException;
import org.plasma.sdo.core.CoreXMLDocument;
import org.plasma.sdo.xml.DefaultErrorHandler;
import org.plasma.sdo.xml.DocumentMarshaller;
import org.plasma.sdo.xml.MarshallerException;
import org.plasma.sdo.xml.StreamMarshaller;
import org.plasma.sdo.xml.StreamMarshaller;
import org.plasma.sdo.xml.StreamUnmarshaller;
import org.plasma.sdo.xml.UnmarshallerException;
import org.plasma.sdo.xml.UnmarshallerRuntimeException;
import org.plasma.sdo.xml.XMLConstants;
import org.plasma.sdo.xml.XMLOptions;
import org.plasma.xml.schema.SchemaConstants;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import commonj.sdo.DataObject;
import commonj.sdo.helper.XMLDocument;
import commonj.sdo.helper.XMLHelper;

/**
 * A helper to convert XML documents into DataObects and 
 * DataObjects into XML documents.
 */
public class PlasmaXMLHelper implements XMLHelper {

    private static Log log = LogFactory.getLog(PlasmaXMLHelper.class);
    
    static public PlasmaXMLHelper INSTANCE = initializeInstance();

    private PlasmaXMLHelper() {       
    }
 
    private static synchronized PlasmaXMLHelper initializeInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new PlasmaXMLHelper();
        return INSTANCE;
    }
    
    /**
     * Creates an XMLDocument with the specified XML rootElement for the DataObject.
     * @param dataObject specifies DataObject to be saved
     * @param rootElementURI the Target Namespace URI of the root XML element
     * @param rootElementName the Name of the root XML element
     * @return XMLDocument a new XMLDocument set with the specified parameters.
     */
    public XMLDocument createDocument(DataObject dataObject, String rootElementURI,
            String rootElementName) {
    	return new CoreXMLDocument(
    			dataObject, rootElementURI, rootElementName);
    }

    /**
     * Creates and returns an XMLDocument from the input String.
     * By default does not perform XSD validation.
     * Same as
     *   load(new StringReader(inputString), null, null);
     * 
     * @param inputString specifies the String to read from
     * @return the new XMLDocument loaded
     * @throws RuntimeException for errors in XML parsing or 
     *   implementation-specific validation.
     */
    public XMLDocument load(String inputString) {
        try {
			return load(new ByteArrayInputStream(inputString.getBytes("UTF-8")), null, null);
		} catch (IOException e) {
			throw new PlasmaDataObjectException(e);
		}
    }

    /**
     * Creates and returns an XMLDocument from the inputStream.
     * The InputStream will be closed after reading.
     * By default does not perform XSD validation.
     * Same as
     *   load(inputStream, null, null);
     * 
     * @param inputStream specifies the InputStream to read from
     * @return the new XMLDocument loaded
     * @throws IOException for stream exceptions.
     * @throws RuntimeException for errors in XML parsing or 
     *   implementation-specific validation.
     */
    public XMLDocument load(InputStream inputStream) throws IOException {
        return load(inputStream, null, null);
    }

    /**
     * Creates and returns an XMLDocument from the inputStream.
     * The InputStream will be closed after reading.
     * By default does not perform XSD validation.
     * @param inputStream specifies the InputStream to read from
     * @param locationURI specifies the URI of the document for relative schema locations
     * @param options implementation-specific options.
     * @return the new XMLDocument loaded
     * @throws IOException for stream exceptions.
     * @throws RuntimeException for errors in XML parsing or 
     *   implementation-specific validation.
     */
    public XMLDocument load(InputStream inputStream, String locationURI, Object options)
            throws IOException { 
    	if (options != null)
    	    if (!(options instanceof XMLOptions))
    	    	throw new IllegalArgumentException("expected 'options' as instance of "
    	    			+ XMLOptions.class.getName());
    	
    	XMLOptions xmlOptions = (XMLOptions)options;
    	StreamUnmarshaller unmarshaler = 
    		new StreamUnmarshaller(xmlOptions, locationURI);
    	
    	InputStream unmarshalStream = inputStream;
    	if (xmlOptions != null && xmlOptions.isValidate()) {
	    	// XSD validation using StAX at least at the same time
	    	// it is reading or if multiple schemas are involved is not
	    	// currently possible with current StAX libs. Can explore Woodstix from
	    	// codehaus.org. Hence, creates a temp stream in order to 
	    	// execute the validation before unmarshaling in a new stream. 
	    	File tempFile = File.createTempFile(PlasmaXMLHelper.class.getSimpleName(), "xml");
	    	StreamCopier.copy(inputStream, 
	    			new FileOutputStream(tempFile));    	
	    	validateDOM(new FileInputStream(tempFile), 
	    			locationURI, (XMLOptions)options);
	    	unmarshalStream = new FileInputStream(tempFile);
    	}
    	
    	try {
			unmarshaler.unmarshal(unmarshalStream);
		} catch (UnmarshallerException e) {
			throw new PlasmaDataObjectException(e);
		} catch (XMLStreamException e) {
			throw new PlasmaDataObjectException(e);
		}
    	
    	XMLDocument result = unmarshaler.getResult();
    	result.setNoNamespaceSchemaLocation(locationURI);
        return result;
    }

    /**
     * Creates and returns an XMLDocument from the inputReader.
     * The InputStream will be closed after reading.
     * By default does not perform XSD validation.
     * @param inputReader specifies the Reader to read from
     * @param locationURI specifies the URI of the document for relative schema locations
     * @param options implementation-specific options.
     * @return the new XMLDocument loaded
     * @throws IOException for stream exceptions.
     * @throws RuntimeException for errors in XML parsing or 
     *   implementation-specific validation.
     */
    public XMLDocument load(Reader inputReader, String locationURI, Object options)
            throws IOException {
    	if (options != null)
    	    if (!(options instanceof XMLOptions))
    	    	throw new IllegalArgumentException("expected 'options' as instance of "
    	    			+ XMLOptions.class.getName());
    	XMLOptions xmlOptions = (XMLOptions)options;
    	StreamUnmarshaller unmarshaler = 
    		new StreamUnmarshaller(xmlOptions, locationURI);
    	try {
			unmarshaler.unmarshal(inputReader);
		} catch (UnmarshallerException e) {
			throw new PlasmaDataObjectException(e);
		} catch (XMLStreamException e) {
			throw new PlasmaDataObjectException(e);
		}
		
		return unmarshaler.getResult();
    }

    
    /**
     * Creates and returns an XMLDocument from the inputSource.
     * The InputSource will be closed after reading.
     * By default does not perform XSD validation.
     * @param inputSource specifies the Source to read from
     * @param locationURI specifies the URI of the document for relative schema locations
     * @param options implementation-specific options.
     * @return the new XMLDocument loaded
     * @throws IOException for stream exceptions.
     * @throws RuntimeException for errors in XML parsing or 
     *   implementation-specific validation.
     */
    public XMLDocument load(Source inputSource, String locationURI, Object options)
            throws IOException {
    	if (options != null)
    	    if (!(options instanceof XMLOptions))
    	    	throw new IllegalArgumentException("expected 'options' as instance of "
    	    			+ XMLOptions.class.getName());
    	XMLOptions xmlOptions = (XMLOptions)options;
    	StreamUnmarshaller unmarshaler = 
    		new StreamUnmarshaller((XMLOptions)options, locationURI);
    	try {
			unmarshaler.unmarshal(inputSource);
		} catch (UnmarshallerException e) {
			throw new PlasmaDataObjectException(e);
		} catch (XMLStreamException e) {
			throw new PlasmaDataObjectException(e);
		}
		return unmarshaler.getResult();
    }

    /**
     * Returns the DataObject saved as an XML document with the specified root element.
     * Same as
     *   StringWriter stringWriter = new StringWriter();
     *   save(createDocument(dataObject, rootElementURI, rootElementName), 
     *     stringWriter, null);
     *   stringWriter.toString();
     *
     * @param dataObject specifies DataObject to be saved
     * @param rootElementURI the Target Namespace URI of the root XML element
     * @param rootElementName the Name of the root XML element
     * @return the saved XML document as a string
     * @throws IllegalArgumentException if the dataObject tree
     *   is not closed or has no container.
     */
    public String save(DataObject dataObject, String rootElementURI, String rootElementName) {
        String result = null;
    	ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
			save(dataObject, rootElementURI, rootElementName, os);
	        os.flush();
	        result = os.toString();
		} catch (IOException e) {
			throw new PlasmaDataObjectException(e);
		}
        return result;
    }

    /**
     * Saves the DataObject as an XML document with the specified root element.
     * Same as
     *   save(createDocument(dataObject, rootElementURI, rootElementName),
     *     outputStream, null);
     * 
     * @param dataObject specifies DataObject to be saved
     * @param rootElementURI the Target Namespace URI of the root XML element
     * @param rootElementName the Name of the root XML element
     * @param outputStream specifies the OutputStream to write to.
     * @throws IOException for stream exceptions.
     * @throws IllegalArgumentException if the dataObject tree
     *   is not closed or has no container.
     */
    public void save(DataObject dataObject, String rootElementURI, String rootElementName,
            OutputStream outputStream) throws IOException {
    	
    	XMLDocument doc = new CoreXMLDocument(dataObject, 
    	    rootElementURI, rootElementName);
    	StreamMarshaller marshaler = 
    		new StreamMarshaller(doc, (XMLOptions)null);
    	try {
    		marshaler.marshal(outputStream);
		} catch (XMLStreamException e) {
			throw new IOException(e);
		} catch (MarshallerException e) {
			throw new IOException(e);
		}
    }

    /**
     * Serializes an XMLDocument as an XML document into the outputStream.
     * If the DataObject's Type was defined by an XSD, the serialization
     *   will follow the XSD.
     * Otherwise the serialization will follow the format as if an XSD
     *   were generated as defined by the SDO specification.
     * The OutputStream will be flushed after writing.
     * Does not perform validation to ensure compliance with an XSD.
     * @param xmlDocument specifies XMLDocument to be saved
     * @param outputStream specifies the OutputStream to write to.
     * @param options implementation-specific options.
     * @throws IOException for stream exceptions.
     * @throws IllegalArgumentException if the dataObject tree
     *   is not closed or has no container.
     */
    public void save(XMLDocument xmlDocument, OutputStream outputStream, Object options)
            throws IOException {
    	if (options != null)
    	    if (!(options instanceof XMLOptions))
    	    	throw new IllegalArgumentException("expected 'options' as instance of "
    	    			+ XMLOptions.class.getName());
    	XMLOptions xmlOptions = (XMLOptions)options;
    	StreamMarshaller marshaler = new StreamMarshaller(xmlDocument);
		marshaler.setOptions(xmlOptions);
    	try {
    		marshaler.marshal(outputStream);
		} catch (XMLStreamException e) {
			throw new IOException(e);
		} catch (MarshallerException e) {
			throw new IOException(e);
		}
    }

    /**
     * Serializes an XMLDocument as an XML document into the outputWriter.
     * If the DataObject's Type was defined by an XSD, the serialization
     *   will follow the XSD.
     * Otherwise the serialization will follow the format as if an XSD
     *   were generated as defined by the SDO specification.
     * The OutputStream will be flushed after writing.
     * Does not perform validation to ensure compliance with an XSD.
     * @param xmlDocument specifies XMLDocument to be saved
     * @param outputWriter specifies the Writer to write to.
     * @param options implementation-specific options.
     * @throws IOException for stream exceptions.
     * @throws IllegalArgumentException if the dataObject tree
     *   is not closed or has no container.
     */
    public void save(XMLDocument xmlDocument, Writer outputWriter, Object options)
            throws IOException {
    	if (options != null)
    	    if (!(options instanceof XMLOptions))
    	    	throw new IllegalArgumentException("expected 'options' as instance of "
    	    			+ XMLOptions.class.getName());
    	XMLOptions xmlOptions = (XMLOptions)options;
    	StreamMarshaller marshaler = new StreamMarshaller(xmlDocument);
		marshaler.setOptions(xmlOptions);
    	try {
    		marshaler.marshal(outputWriter);
		} catch (XMLStreamException e) {
			throw new IOException(e);
		} catch (MarshallerException e) {
			throw new IOException(e);
		}
    }

    /**
     * Serializes an XMLDocument as an XML document into the outputResult in a 
     * serialization technology independent format (as specified in 
     * javax.xml.transform).
     * The OutputResult will be flushed after writing.
     * Does not perform validation to ensure compliance with an XSD.
     * @param xmlDocument specifies XMLDocument to be saved
     * @param outputResult specifies Result to be saved
     * @param options implementation-specific options.
     * @throws IOException for stream exceptions.
     * @throws IllegalArgumentException if the dataObject tree
     *   is not closed or has no container.
     */
    public void save(XMLDocument xmlDocument, Result outputResult, Object options)
            throws IOException {
    	if (options != null)
    	    if (!(options instanceof XMLOptions))
    	    	throw new IllegalArgumentException("expected 'options' as instance of "
    	    			+ XMLOptions.class.getName());
    	XMLOptions xmlOptions = (XMLOptions)options;
    	StreamMarshaller marshaler = new StreamMarshaller(xmlDocument);
		marshaler.setOptions(xmlOptions);
    	try {
    		marshaler.marshal(outputResult);
		} catch (XMLStreamException e) {
			throw new IOException(e);
		} catch (MarshallerException e) {
			throw new IOException(e);
		}
    }

    private void validateDOM(InputStream inputStream, String locationURI, XMLOptions options) throws IOException {
    	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		try {
			factory.setAttribute(XMLConstants.JAXP_SCHEMA_LANGUAGE, SchemaConstants.XMLSCHEMA_NAMESPACE_URI);
			factory.setValidating(true);
			//factory.setNamespaceAware(true);
			//factory.setFeature("http://apache.org/xml/features/validation/schema",true);
			//factory.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
		} catch (IllegalArgumentException e) {
			log.warn("parser does not support JAXP 1.2");
		} /*catch (ParserConfigurationException e) {
			throw new PlasmaDataObjectException(e);
		}*/    	
				
		if (locationURI != null) {
		    factory.setAttribute(XMLConstants.JAXP_NO_NAMESPACE_SCHEMA_SOURCE, 
				locationURI);
		}
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			if (options.getErrorHandler() == null)
			    builder.setErrorHandler(new DefaultErrorHandler(options));
			else
				builder.setErrorHandler(options.getErrorHandler());
			if (log.isDebugEnabled())
				log.debug("validating...");
			builder.parse(inputStream);

		} catch (ParserConfigurationException e) {
			throw new PlasmaDataObjectException(e);
		} catch (SAXException e) {
			throw new PlasmaDataObjectException(e);
		} 
    	
    }
 
    private void validateSAX(InputStream inputStream, String locationURI, XMLOptions options) throws IOException {
    	
    	SAXParserFactory factory = SAXParserFactory.newInstance();
    	factory.setValidating(true);
    	factory.setNamespaceAware(true);
    	SAXParser parser;
    	
    	try {
			factory.setFeature("http://xml.org/sax/features/validation", true);
	    	factory.setFeature("http://apache.org/xml/features/validation/schema",true);
	    	factory.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
	    	parser = factory.newSAXParser();	
	    	try {
	    	    parser.setProperty(XMLConstants.JAXP_SCHEMA_LANGUAGE, SchemaConstants.XMLSCHEMA_NAMESPACE_URI);
	    	}
	    	catch (SAXNotRecognizedException e) {
	    		log.warn("parses does not support JAXP 1.2");
	    	}
	    	if (locationURI != null) {
	    		parser.setProperty(XMLConstants.JAXP_NO_NAMESPACE_SCHEMA_SOURCE, 
					locationURI);
			}
			XMLReader xmlReader = parser.getXMLReader();
			//xmlReader.setEntityResolver(new SchemaLoader());
			if (options.getErrorHandler() == null)
				xmlReader.setErrorHandler(new DefaultErrorHandler(options));
			else
				xmlReader.setErrorHandler(options.getErrorHandler());
			if (log.isDebugEnabled())
				log.debug("validating...");
			xmlReader.parse(new InputSource(inputStream));
			
		} catch (SAXNotRecognizedException e) {
			throw new PlasmaDataObjectException(e);
		} catch (SAXNotSupportedException e) {
			throw new PlasmaDataObjectException(e);
		} catch (ParserConfigurationException e) {
			throw new PlasmaDataObjectException(e);
		} catch (SAXException e) {
			throw new PlasmaDataObjectException(e);
		}
    }
    
}
