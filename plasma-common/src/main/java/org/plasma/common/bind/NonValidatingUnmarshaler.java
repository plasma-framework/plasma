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
package org.plasma.common.bind;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class NonValidatingUnmarshaler extends DataBindingSupport {

    private Unmarshaller unmarshaler;

    private NonValidatingUnmarshaler() {
        super(null);
    }

    public NonValidatingUnmarshaler(JAXBContext context) throws JAXBException,
            SAXException {
        super(context);
        this.unmarshaler = createUnmarshaler(context);
    }

    /**
     * Creates an unmarshaler using the given factories.
     * @param context
     *            the JAXB context
     * @return the unmarshaler
     * @throws JAXBException
     * @throws SAXException
     */
    private Unmarshaller createUnmarshaler(JAXBContext context) throws JAXBException, SAXException {
        Unmarshaller u = context.createUnmarshaller();
        // adds a custom object factory
        // u.setProperty("com.sun.xml.bind.ObjectFactory",new
        // ObjectFactoryEx());

        return u;
    }

    public Object unmarshal(String xml) throws JAXBException, UnmarshalException {
    	return unmarshaler.unmarshal(new StringReader(xml));
    }

    public Object unmarshal(InputStream xml) throws JAXBException, UnmarshalException {
        return unmarshaler.unmarshal(xml);
    }

    public Object unmarshal(Reader reader) throws JAXBException, UnmarshalException {
    	return unmarshaler.unmarshal(reader);
    }
    
    public Object unmarshal(Source source) throws JAXBException, UnmarshalException {
    	return unmarshaler.unmarshal(source);
    }    
}
