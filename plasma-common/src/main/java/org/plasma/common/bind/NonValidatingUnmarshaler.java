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

    private static Log log = LogFactory.getLog(NonValidatingUnmarshaler.class);
    private Unmarshaller unmarshaler;

    private NonValidatingUnmarshaler() {
        super(null);
    }

    public NonValidatingUnmarshaler(URL url, JAXBContext context) throws JAXBException,
            SAXException {
        super(context);
        if (url == null)
            throw new IllegalArgumentException("non-null argument expected, url");
        this.unmarshaler = createUnmarshaler(url, context);
    }

    /**
     * Creates an unmarshaler using the given factories and URL. Loads only the
     * given (subclass) schema as this is the "root" schema and it should
     * include any other schema resources it needs, and so on. Note all included
     * schemas MUST be found at the same class level as the root schema.
     * 
     * @param url
     *            the Schema URL
     * @param context
     *            the SAXB context
     * @return the unmarshaler
     * @throws JAXBException
     * @throws SAXException
     */
    private Unmarshaller createUnmarshaler(URL url, JAXBContext context) throws JAXBException, SAXException {
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
