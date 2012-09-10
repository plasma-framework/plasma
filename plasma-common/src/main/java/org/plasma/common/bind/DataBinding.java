package org.plasma.common.bind;

import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

public interface DataBinding {

    public String marshal(Object root) 
        throws JAXBException;

    public Object unmarshal(String xml) 
        throws JAXBException;

    public Object unmarshal(InputStream stream) 
        throws JAXBException;

    public Object validate(String xml) 
        throws JAXBException;

    public Object validate(InputStream xml)
        throws JAXBException, UnmarshalException;
    
    public BindingValidationEventHandler getValidationEventHandler()
    throws JAXBException;
}
