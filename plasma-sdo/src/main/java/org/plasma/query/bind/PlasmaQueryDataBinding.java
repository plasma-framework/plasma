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
package org.plasma.query.bind;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.transform.Source;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.BindingValidationEventHandler;
import org.plasma.common.bind.DataBinding;
import org.plasma.common.bind.ValidatingUnmarshaler;
import org.plasma.query.Query;
import org.xml.sax.SAXException;

public class PlasmaQueryDataBinding implements DataBinding {

    private static Log log = LogFactory.getLog(PlasmaQueryDataBinding.class);
    public static String FILENAME_SCHEMA_CHAIN_ROOT = "query.xsd";

    // just classes in the same package where can find the above respective
    // schema files via Class.getResource*
    public static Class<?> RESOURCE_CLASS = Query.class;

    private static ValidatingUnmarshaler validatingUnmarshaler;

    public static Class<?>[] FACTORIES = { org.plasma.query.model.ObjectFactory.class, };

    @SuppressWarnings("unused")
    private PlasmaQueryDataBinding() {
    }

    public PlasmaQueryDataBinding(BindingValidationEventHandler validationEventHandler)
            throws JAXBException, SAXException {
    	if (validatingUnmarshaler == null) {
	        log.info("loading schema chain...");
	        URL url = RESOURCE_CLASS.getResource(FILENAME_SCHEMA_CHAIN_ROOT);
	        if (url == null)
	        	url = RESOURCE_CLASS.getClassLoader().getResource(FILENAME_SCHEMA_CHAIN_ROOT);
	        if (url == null)
	        	throw new IllegalArgumentException("could not find schema root file '"
	        		+ FILENAME_SCHEMA_CHAIN_ROOT + "' on classpath");
	        validatingUnmarshaler = new ValidatingUnmarshaler(url, 
	        		JAXBContext.newInstance(FACTORIES),
	                validationEventHandler);
    	}
    }

    public Class<?>[] getObjectFactories() {
        return FACTORIES;
    }

    public String marshal(Object root) throws JAXBException {
        return validatingUnmarshaler.marshal(root);
    }

    public void marshal(Object root, OutputStream stream) throws JAXBException {
        validatingUnmarshaler.marshal(root, stream);
    }
    
    public Object unmarshal(String xml) throws JAXBException {
        return validatingUnmarshaler.unmarshal(xml);
    }

    public Object unmarshal(InputStream stream) throws JAXBException {
        return validatingUnmarshaler.unmarshal(stream);
    }
    
    public Object unmarshal(Reader reader) throws JAXBException {
        return validatingUnmarshaler.unmarshal(reader);
    }
    
    public Object unmarshal(Source source) throws JAXBException {
        return validatingUnmarshaler.unmarshal(source);
    }

    public Object validate(String xml) throws JAXBException {
        return validatingUnmarshaler.validate(xml);
    }

    public Object validate(InputStream xml) throws JAXBException, UnmarshalException {
        return validatingUnmarshaler.validate(xml);
    }

    public BindingValidationEventHandler getValidationEventHandler() throws JAXBException {
        return validatingUnmarshaler.getValidationEventHandler();
    }

}
