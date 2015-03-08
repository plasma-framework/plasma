package org.plasma.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javanet.staxutils.events.EventAllocator;
import javanet.staxutils.events.NamespaceEvent;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.profile.ProfileArtifact;
import org.plasma.profile.ProfileConfig;
import org.plasma.profile.ProfileURN;
import org.plasma.profile.adapter.ProfileArtifactAdapter;

public class ProfileVersionFinder 
{
    private static Log log = LogFactory.getLog(ProfileVersionFinder.class);
    
    public static final String ELEM_XMI = "XMI";
    public static final String ATTR_OPF_VERSION = "";

    private XMLEventAllocator allocator = null;
    private XMLInputFactory factory;
    private String currentNamespaceUri = null;  
    private boolean abort = false;
    
    private ProfileArtifactAdapter result;
		 
	public ProfileArtifactAdapter getVersion(String artifactURN, InputStream stream) {
		try {
			setup();
		    read(stream);
		} catch (XMLStreamException e) {
			throw new ConfigurationException(e);
		} 
		finally {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
		if (this.result == null) {
			StringBuilder buf = new StringBuilder();
			ProfileURN[] v = ProfileURN.values();
			for (int i = 0; i < v.length; i++) {
				if (i > 0)
					buf.append(", ");
				buf.append(v[i].value());
			}
			throw new ProfileVersionDetectionException("no profile version detected for artifact '" 
		        + artifactURN + "' - all UML artifacts are required to be annotated with one of the following versions "
		        + "of the PlasmaSDO UML Profile [" + buf.toString() + "]");
		}
		
		return result;
	}
	
	private void read(InputStream stream) throws XMLStreamException
	{		
		XMLStreamReader streamReader = factory.createXMLStreamReader(stream);
        int eventType;
        
        while(streamReader.hasNext() && !abort){
            eventType = streamReader.next();
            XMLEvent event = allocateXMLEvent(streamReader);
            //logEventInfo(event);
            switch (eventType){
            case XMLEvent.START_ELEMENT:
            	StartElement start = event.asStartElement();
        	    QName name = start.getName();
        	    String uri = name.getNamespaceURI();
        	    if (uri != null && uri.trim().length() > 0)
        	    	this.currentNamespaceUri = uri.trim(); 
         	    if (isRoot(name)) {         	    	          	    	
         	    	Iterator<NamespaceEvent> iter = start.getNamespaces();
         	    	while (iter.hasNext()) {
         	    		NamespaceEvent namespaceEvent = iter.next();
         	    		String value = namespaceEvent.getValue();
         	    		ProfileArtifactAdapter version = findVersion(value);
         	    		if (version != null) {
         	    			this.result = version;
         	    			this.abort = true;
         	    			break;
         	    		}
         	    	}
         	    }
        	    break;
            case XMLEvent.END_ELEMENT:
        	    name = event.asEndElement().getName();
        	    uri = name.getNamespaceURI();
       	        break;
            case XMLEvent.CHARACTERS:  
 		    	String data = event.asCharacters().getData();
 		    	if (data == null || data.trim().length() == 0) {
 		    		break; // ignore newlines and such
 		    	}
 		    	data = data.trim();
 		    	if (data.contains(">")) {
 		            Location loc = event.getLocation();               
 		            String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
 		            msg += " - document is not well-formed";
 		    		throw new XMLStreamException(msg);
 		    	}
 		    	break;
            default:
            }
        }		
	}
	
	private boolean isRoot(QName name)
	{
	    if (ELEM_XMI.equals(name.getLocalPart())) {
    		return true;
	    }
	    return false;
	}
	
	private ProfileArtifactAdapter findVersion(String value)
	{
		return ProfileConfig.getInstance().findArtifactByUri(value);
	}
	
	private void setup() {
		this.factory = XMLInputFactory.newInstance();
		factory.setXMLResolver(new XMLResolver() {
			public Object resolveEntity(String publicID,
                     String systemID,
                     String baseURI,
                     String namespace) throws XMLStreamException {
				log.info("resolve: " + publicID + ":" + systemID  + ":" + baseURI + ":" + namespace);
				return null;
			}				
		});
        factory.setEventAllocator(new EventAllocator());
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,Boolean.FALSE);
        factory.setProperty(XMLInputFactory.IS_VALIDATING,Boolean.FALSE);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,Boolean.FALSE);
        factory.setProperty(XMLInputFactory.SUPPORT_DTD,Boolean.FALSE);
         
        
        //set the IS_COALESCING property to true , if application desires to
        //get whole text data as one event.            
        //factory.setProperty(XMLInputFactory.IS_COALESCING , Boolean.TRUE);
        
        allocator = factory.getEventAllocator();		
	}
	
    /** Get the immutable XMLEvent from given XMLStreamReader using XMLEventAllocator */
    private XMLEvent allocateXMLEvent(XMLStreamReader reader) throws XMLStreamException{
        return this.allocator.allocate(reader);
    }  
    
	private void logEventInfo(XMLEvent event)
	{
	    if (log.isDebugEnabled())
	    {    
            Location loc = event.getLocation();               
            String msg = getEventTypeString(event.getEventType());
            msg += " line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "] - ";
            msg += event.toString();
            log.debug(msg);	   
	    }
	}
	
    /**
     * Returns the String representation of the given integer constant.
     *
     * @param eventType Type of event.
     * @return String representation of the event
     */    
    public final static String getEventTypeString(int eventType) {
        switch (eventType){
            case XMLEvent.START_ELEMENT:
                return "START_ELEMENT";
            case XMLEvent.END_ELEMENT:
                return "END_ELEMENT";
            case XMLEvent.PROCESSING_INSTRUCTION:
                return "PROCESSING_INSTRUCTION";
            case XMLEvent.CHARACTERS:
                return "CHARACTERS";
            case XMLEvent.COMMENT:
                return "COMMENT";
            case XMLEvent.START_DOCUMENT:
                return "START_DOCUMENT";
            case XMLEvent.END_DOCUMENT:
                return "END_DOCUMENT";
            case XMLEvent.ENTITY_REFERENCE:
                return "ENTITY_REFERENCE";
            case XMLEvent.ATTRIBUTE:
                return "ATTRIBUTE";
            case XMLEvent.DTD:
                return "DTD";
            case XMLEvent.CDATA:
                return "CDATA";
            case XMLEvent.SPACE:
                return "SPACE";
        }
        return "UNKNOWN_EVENT_TYPE , " + eventType;
    }


}
