package org.plasma.common.bind;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;

public class DataBindingSupport {

	protected JAXBContext context;

	@SuppressWarnings("unused")
	private DataBindingSupport() {}
	
	public DataBindingSupport(JAXBContext context)
	{
		this.context = context;
	}
	
    public String marshal(Object root) 
        throws JAXBException
	{	    
	    StringWriter writer = new StringWriter();
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.marshal(root, writer);
	    return writer.toString();
	}    

    public void marshal(Object root, OutputStream stream) 
        throws JAXBException
    {       
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.marshal(root, stream);
    }
    
    public void marshal(Object root, OutputStream stream, boolean formattedOutput) 
	    throws JAXBException
	{       
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(formattedOutput));
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    m.marshal(root, stream);
	}    
    
    public Object unmarshal(String xml) 
        throws JAXBException
	{
	    Unmarshaller u = context.createUnmarshaller();
	    return u.unmarshal(new StringReader(xml));
	}  
    
    public Object unmarshal(Reader reader) 
	    throws JAXBException
	{
	    Unmarshaller u = context.createUnmarshaller();
	    return u.unmarshal(reader);
	}  
    
    public Object unmarshal(Source source) 
	    throws JAXBException
	{
	    Unmarshaller u = context.createUnmarshaller();
	    return u.unmarshal(source);
	}    

    public Object unmarshal(InputStream stream) 
        throws JAXBException
	{
	    StringWriter writer = new StringWriter();
	    Unmarshaller u = context.createUnmarshaller();
	    return u.unmarshal(stream);
	}    
    
}
