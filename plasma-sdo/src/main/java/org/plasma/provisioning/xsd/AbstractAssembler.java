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
package org.plasma.provisioning.xsd;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.ElementNSImpl;
import org.plasma.metamodel.Body;
import org.plasma.metamodel.Documentation;
import org.plasma.metamodel.DocumentationType;
import org.plasma.provisioning.NameUtils;
import org.plasma.xml.schema.Annotated;
import org.plasma.xml.schema.Appinfo;

public abstract class AbstractAssembler {
	private static Log log = LogFactory.getLog(
			AbstractAssembler.class); 
    protected String destNamespaceURI;
    protected String destNamespacePrefix;
    protected ConverterSupport support;

    @SuppressWarnings("unused")
	private AbstractAssembler() {}
    
    public AbstractAssembler(String destNamespaceURI, 
    		String destNamespacePrefix, 
    		ConverterSupport converterSupport) {
		super();
		this.destNamespaceURI = destNamespaceURI;
		this.destNamespacePrefix = destNamespacePrefix;
		this.support = converterSupport;
	}

	protected Documentation createDocumentation(DocumentationType type, 
    		String content)
    {
        Documentation documentation = new Documentation();
		documentation.setType(type);
		Body body = new Body();
		body.setValue(content);
		documentation.setBody(body); 
		return documentation;
    }
    
    protected String formatLocalClassName(String localName) {
    	if (localName == null || localName.trim().length() == 0)
    		throw new IllegalArgumentException("expected localName argument");
    	String result = localName;
    	result = NameUtils.firstToUpperCase(result);
    	return result;
    }

    protected String formatLocalPropertyName(String localName) {
    	if (localName == null || localName.trim().length() == 0)
    		throw new IllegalArgumentException("expected localName argument");
    	String result = localName;
    	result = NameUtils.firstToLowerCase(result);
    	return result;
    }
    
	protected String getDocumentationContent(Annotated annotated) {
		StringBuilder buf = new StringBuilder();
		if (annotated != null && annotated.getAnnotation() != null)
			for (Object annotationObj : annotated.getAnnotation()
					.getAppinfosAndDocumentations()) {
				if (annotationObj instanceof org.plasma.xml.schema.Documentation) {
					org.plasma.xml.schema.Documentation doc = (org.plasma.xml.schema.Documentation) annotationObj;
					for (Object content : doc.getContent())
						if (content instanceof String) {
							buf.append(content);
						} else if (content instanceof ElementNSImpl) {
							ElementNSImpl nsElem = (ElementNSImpl) content;
							buf.append(serializeElement(nsElem));
						} else
							throw new IllegalStateException(
									"unexpected content class, "
											+ annotationObj.getClass()
													.getName());
				} else if (annotationObj instanceof Appinfo) {
					log.warn("ignoring app-info: "
							+ String.valueOf(annotationObj));
				}
			}
		return buf.toString();
	}
	
    protected String findAppInfoValue(org.plasma.xml.schema.Enumeration schemaEnum)
    {
    	String result = null;
		if (schemaEnum.getAnnotation() != null)
	    for (Object o2 : schemaEnum.getAnnotation().getAppinfosAndDocumentations()) {
	    	if (o2 instanceof Appinfo) {
	    		Appinfo appinfo = (Appinfo)o2;
	    		result = (String)appinfo.getContent().get(0);
	    		if (result != null) {
	    			result.trim();
	    			if (result.length() == 0)
	    				result = null;
	    		}
	    		break;
	    	}
	    }
    	return result;
    }

	protected String serializeElement(ElementNSImpl nsElem)
	{
		String result = "";
        TransformerFactory transFactory = TransformerFactory.newInstance();
        log.debug("transformer factory: " + transFactory.getClass().getName());
        //transFactory.setAttribute("indent-number", 2);
        Transformer idTransform = null;
        ByteArrayOutputStream stream = null;
		try {
			idTransform = transFactory.newTransformer();
			idTransform.setOutputProperty(OutputKeys.METHOD, "xml");
	        idTransform.setOutputProperty(OutputKeys.INDENT,"yes"); 
	        idTransform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes"); 
	        Source input = new DOMSource(nsElem.getOwnerDocument());
	        stream = new ByteArrayOutputStream();
	        Result output = new StreamResult(stream);
			idTransform.transform(input, output);
			stream.flush();
			result = new String(stream.toByteArray());
			return result;
		} catch (TransformerConfigurationException e1) {
			log.error(e1.getMessage(), e1);
	    } catch (TransformerException e) {
		    log.error(e.getMessage(), e);
	    } catch (IOException e) {
		    log.error(e.getMessage(), e);
		} finally {
			if (stream != null)
				try {
					stream.close();
				} catch (Throwable t) {
				}
			
		}
		return result;
	}
}
