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
