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
package org.plasma.sdo.xml;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javanet.staxutils.events.EventAllocator;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.transform.Source;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.DataFlavor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.core.CoreHelper;
import org.plasma.sdo.core.CoreXMLDocument;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.helper.PlasmaDataHelper;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.profile.KeyType;
import org.plasma.xml.schema.SchemaConstants;
import org.plasma.xml.schema.SchemaUtil;

import commonj.sdo.DataGraph;
import commonj.sdo.Property;
import commonj.sdo.helper.XMLDocument;


/**
 * A Streaming API for XML (StAX) parser based XML unmarshaller which converts 
 * various (stream-based) XML input source types into an SDO 
 * data graph. The resulting data graph is ready to be used, e.g. modified or
 * committed using a Data Access Service (DAS). 
 * 
 * This unmarshaller coalesces or merges XML nodes associated
 * with SDO types which have external key properties, creating both containment
 * and non-containment reference (property) associations between data objects.  
 * 
 * Since the final coalesced data graph structure is not known until the entire 
 * stream is parsed, lightweight "scaffolding" structures are first used to collect
 * objects and property values into an initial node graph. This graph is then
 * walked and the nodes coalesced into the final data-object graph. The
 * scaffolding nodes are then discarded.   
 *  
 */
//FIXME: add XML Option to specify desired state of the graph e.g. whether the 
//change summary is set up for (insert, update, merge, ...). Currently is is set 
//up only for insert. 
public class StreamUnmarshaller extends Unmarshaller { 
    private static Log log = LogFactory.getLog(StreamUnmarshaller.class);
    
    private XMLEventAllocator allocator = null;
    private XMLInputFactory factory;
    private Stack<StreamNode> stack = new Stack<StreamNode>();
    private Map<Object, StreamObject> keyMap = new HashMap<Object, StreamObject>();
    private String currentNamespaceUri = null;        
    private XMLOptions options;
    private String locationURI;
    private Timestamp snapshotDate = new Timestamp((new Date()).getTime());
    
	public StreamUnmarshaller(XMLOptions options, String locationURI) {
		super(UnmarshallerFlavor.STAX);
		this.options = options;
		this.locationURI = locationURI;
		setup();
    }    
	
	public XMLDocument getResult() {
		if (this.result == null)
			throw new IllegalStateException("unmarshaling not yet performed - call unmarshal(...)");
		return this.result;
	}
	
	private void setup() {
		this.factory = XMLInputFactory.newInstance();
		factory.setXMLResolver(new XMLResolver() {
			public Object resolveEntity(String publicID,
                     String systemID,
                     String baseURI,
                     String namespace) throws XMLStreamException {
				log.debug("resolve: " + publicID + ":" + systemID  + ":" + baseURI + ":" + namespace);
				return null;
			}				
		});
        factory.setEventAllocator(new EventAllocator());
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,Boolean.FALSE);
        factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,Boolean.TRUE);
        //set the IS_COALESCING property to true , if application desires to
        //get whole text data as one event.            
        //factory.setProperty(XMLInputFactory.IS_COALESCING , Boolean.TRUE);
        
        allocator = factory.getEventAllocator();		
	}
	
	/**
	 * Reads the given input stream in its entirety, and closes the stream when 
	 * complete. The data graph result is retrieved using the {@link #getResult() getResult}
	 * method. 
	 * @param stream the XML stream to read
	 * @throws XMLStreamException if the given document is found to be malformed
	 * @throws UnmarshallerException if undefined properties, or classes are found in the 
	 * XML stream. 
	 */
	public void unmarshal(InputStream stream) throws XMLStreamException, UnmarshallerException {
		XMLStreamReader streamReader = factory.createXMLStreamReader(stream);

		try {
		    unmarshal(streamReader);
        }
		finally {
			streamReader.close();
		    this.stack.clear();
		    this.allocator = null;
		}
	}
	
	/**
	 * Reads the given source as a stream in its entirety, and closes the stream when 
	 * complete. The data graph result is retrieved using the {@link #getResult() getResult}
	 * method. 
	 * @param source the XML source to read
	 * @throws XMLStreamException if the given document is found to be malformed
	 * @throws UnmarshallerException if undefined properties, or classes are found in the 
	 * XML stream. 
	 */
	public void unmarshal(Source source) throws XMLStreamException, UnmarshallerException {
		XMLStreamReader streamReader = factory.createXMLStreamReader(source);
		try {
		    unmarshal(streamReader);
        }
		finally {
			streamReader.close();
		    this.stack.clear();
		    this.allocator = null;
		}
	}
	
	/**
	 * Reads the given reader as a stream in its entirety, and closes the stream when 
	 * complete. The data graph result is retrieved using the {@link #getResult() getResult}
	 * method. 
	 * @param reader the XML reader to read
	 * @throws XMLStreamException if the given document is found to be malformed
	 * @throws UnmarshallerException if undefined properties, or classes are found in the 
	 * XML stream. 
	 */
	public void unmarshal(Reader reader) throws XMLStreamException, UnmarshallerException {
		XMLStreamReader streamReader = factory.createXMLStreamReader(reader);
		try {
		    unmarshal(streamReader);
        }
		finally {
			streamReader.close();
		    this.stack.clear();
		    this.allocator = null;
		}
	}

	private void unmarshal(XMLStreamReader streamReader) 
	    throws XMLStreamException, UnmarshallerException 
	{
		// read the stream
		StreamObject root = read(streamReader);
		
		final DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
	    dataGraph.getChangeSummary().beginLogging(); // log changes from this point    	 
		
    	
    	// setup containment graph
    	StreamObjectVisitor visitor = new StreamObjectVisitor() {
			public void visit(StreamObject target,
					StreamObject source, PlasmaProperty sourceProperty,
					int level) {
				
				if (target.isNonContainmentReference())
					return; // ignore non-containment reference objects
				
				PlasmaDataObject dataObject = null;
				
		        if (source == null) {
        	    	dataObject = (PlasmaDataObject)dataGraph.createRootObject(target.getType());   	    	
		        }
		        else {
		        	PlasmaDataObject parent = source.getDataObject();
		        	dataObject = (PlasmaDataObject)parent.createDataObject(target.getSource());
		        }
    	    	target.setDataObject(dataObject);
    	    	
    	    	// Map containment reference objects w/an serial key
    	    	// Determine a containment reference based on an
    	    	// xsi:type match with the SDO type. 
	    		Object key = target.getSerialId();
        		StreamObject existing = keyMap.get(key);
        		if (existing != null) {
 		            String msg = "line:col[" + target.getLine() + ":" + target.getColumn() + "]";
 		            msg += " - attempt to instantiate multiple instances of '" + target.getLocalName() 
 		                + "' using the same reference key '" + String.valueOf(key) + "'";
        			throw new DuplicateContainmentReferenceException(msg);
        		}
    			keyMap.put(key, target);
		        
	    		// set properties
    	    	Iterator<PlasmaProperty> iter = target.getValues().keySet().iterator();
	        	while (iter.hasNext()) {
	        		PlasmaProperty property = iter.next();
	        		if (property.getType().isDataType()) {
		        		Object value = target.get(property);
		    	    	if (!property.isReadOnly()) {
		        			dataObject.set(property, value);
		    	    	}
		    	    	else {
		                    CoreHelper.set(dataObject, property.getName(), value);
		    	    	}
	        		}
	        	}
			}
    	};
    	root.accept(visitor); // traverse

    	// setup non-containment references
      	visitor = new StreamObjectVisitor() {
			@SuppressWarnings("unchecked")
			public void visit(StreamObject target,
					StreamObject source, PlasmaProperty sourceProperty,
					int level) {
				if (!target.isNonContainmentReference())
					return; 
        		Object key = target.getSerialId();
        		if (key == null)
        			throw new IllegalStateException("expected key");
        		StreamObject existing = keyMap.get(key);
        		if (existing != null) {
        			if (!target.getSource().isMany()) {
        			    source.getDataObject().set(target.getSource(), 
        					existing.getDataObject());
        			}
        			else {
        				List list = (List)source.getDataObject().get(target.getSource());
        				if (list == null)
        					list = new ArrayList();
        				list.add(existing.getDataObject());
        				source.getDataObject().set(target.getSource(), list);
        			}
        		}
        		else {	        			
 		            String msg = "line:col[" + target.getLine() + ":" + target.getColumn() + "]";
 		            msg += " - reference value '" + String.valueOf(key) 
 		                + "' not found within document";
        			throw new InvalidReferenceException(msg);
        		}
			}
    	};
    	root.accept(visitor); // traverse
    	
    	visitor = new StreamObjectVisitor() {
			public void visit(StreamObject target,
					StreamObject source, PlasmaProperty sourceProperty,
					int level) {
				target.getValues().clear();
				target.setDataObject(null);
			}
    	};
    	root.accept(visitor); // traverse
    	
    	this.result = new CoreXMLDocument(dataGraph.getRootObject(),
    			this.options);    	
	}
		 
	private StreamObject read(XMLStreamReader streamReader) 
	    throws XMLStreamException, UnmarshallerException 
	{
        int eventType;
    	StreamObject root = null;
        
        while(streamReader.hasNext()){
            eventType = streamReader.next();
            XMLEvent event = allocateXMLEvent(streamReader);
            switch (eventType){
            case XMLEvent.START_ELEMENT:
        	    QName name = event.asStartElement().getName();
        	    String uri = name.getNamespaceURI();
        	    if (uri != null && uri.trim().length() > 0)
        	    	this.currentNamespaceUri = uri.trim(); 
        	    if (stack.size() == 0) {
            	    String typeName = name.getLocalPart();
            	    PlasmaType type = (PlasmaType)PlasmaTypeHelper.INSTANCE.getType(currentNamespaceUri, typeName);
            	    if (log.isDebugEnabled())
            	    	log.debug("unmarshaling root: " + type.getURI() + "#" + type.getName());
            	    root = new StreamObject(type, name, event.getLocation());
        	    	stack.push(root);
        	    }
        	    else {
        	    	StreamObject sreamObject = (StreamObject)stack.peek();
         			PlasmaType type = sreamObject.getType();
         			QName elemName = event.asStartElement().getName();
        	    	PlasmaProperty property = getPropertyByLocalName(event, type, elemName.getLocalPart());
        	    	if (property.getType().isDataType()) {
        	    		// still need characters event to populate this property. We expect to
        	    		// pop this back off the stack after its characters event is processed below
        	    		stack.push(new StreamProperty((PlasmaType)property.getType(), 
        	    				property, name, event.getLocation()));        	    		
        	    		break; // we expect no attributes !!
        	    	}
        	    	else {
                	    if (log.isDebugEnabled())
                	    	log.debug("unmarshaling: " + property.getType().getURI() + "#" + property.getType().getName());
        	    		// The source is a reference property but we don't know at this point
        	    		// whether its a reference object.
        	    		
    	    		    // Push the new DO so we can value its contents on subsequent events
        	    		stack.push(new StreamObject((PlasmaType)property.getType(), 
        	    				property, name, event.getLocation()));        	    		
        	    	}
        	    }
        	    StreamObject streamObject = (StreamObject)stack.peek();
    	    	readAttributes(event, streamObject);
        	    break;
            case XMLEvent.END_ELEMENT:
            	StreamNode node = stack.pop();
            	if (stack.size() == 0)
            		break;
            	
        		// link stream objects creating an initial graph
            	StreamObject other = (StreamObject)stack.peek(); 
        		if (node instanceof StreamProperty) {
        			link((StreamProperty)node, other);
        		}
        		else {
        			link((StreamObject)node, other); 
        		}
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
 		    	StreamNode streamNode = stack.peek();
 		    	if (streamNode instanceof StreamProperty) {
 		    		readCharacters((StreamProperty)streamNode, 
 		    				data, event);
 		    	}
 		    	else {
 		    		StreamObject streamObj = (StreamObject)streamNode;
 		            Location loc = event.getLocation();               
 		            String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
 		    		msg += " - cannot character(s) data for complex type "
 		    			+ streamObj.getType().getURI() + "#" 
 		    			+ streamObj.getType().getName(); 
 		    		throw new UnmarshallerException(msg);
 		    		//readCharacters((StreamObject)streamNode, 
 		    		//	data, event);
 		    	}
        	    break;
            default:
                logEventInfo(event);
            }
        }
	    return root;        
	}

	private void readCharacters(StreamProperty streamProperty, 
			String data, XMLEvent event) throws UnmarshallerException
	{
    	PlasmaType type = streamProperty.getType();
	    PlasmaProperty property = streamProperty.getProperty();
    	if (!property.getType().isDataType()) {    		    		
            Location loc = event.getLocation();               
            String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
    		msg += " - cannot set reference propery ("
    			+ type.getURI() + "#" + type.getName() + "." + property.getName() 
    			+ ") - from character data"; 
    		throw new UnmarshallerException(msg);
    	}     		    	
    	if (!property.isReadOnly()) {
            Object value = PlasmaDataHelper.INSTANCE.convert(property, data);
    		if (!property.isMany()) {
    			streamProperty.set(value);
    		}
    		else {
	            Location loc = event.getLocation();               
	            String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
	    		msg += " - cannot set many propery ("
	    			+ type.getURI() + "#" + type.getName() + "." + property.getName() 
	    			+ ") - from character data"; 
    			throw new UnmarshallerException(msg);
    		}
    	}
    	else {
            Location loc = event.getLocation();               
            String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
    		msg += " - cannot set readonly propery ("
    			+ property.getName() + ") - ignoring character data '" 
    			+ data + "' for readonly propery, "
    			+ type.getURI() + "#" + type.getName() + "." + property.getName();
            log.warn(msg);   
    	}		
	}
	
	private void link(StreamProperty propertyNode, StreamObject parent)
	{
		Object value = propertyNode.get();
		if (value != null) { // maybe no characters data
			if (!propertyNode.getProperty().isMany()) {
				parent.set(propertyNode.getProperty(), value);
			}
			else {
    			List<Object> list = (List<Object>)parent.get(propertyNode.getProperty());
    			if (list == null) {
    				list = new ArrayList<Object>();
    				parent.set(propertyNode.getProperty(), list);
    			}
    			list.add(value);
			}            		
		}		
	}
	
	private void link(StreamObject objectNode, StreamObject parent) throws UnmarshallerException {
		if (!objectNode.getSource().isMany()) {
			StreamObject existingValue = (StreamObject)parent.get(objectNode.getSource());
			if (existingValue != null) {
	            String msg = "line:col[" + objectNode.getLine() + ":" + objectNode.getColumn() + "]";
	            msg += " - invalid element '" + objectNode.getLocalName() + "' - found existing element "
	                + "for singular property, "
    			    + parent.getType().getURI() + "#" + parent.getType().getName() 
    			    + "." + objectNode.getSource().getName();
			    throw new UnmarshallerException(msg);
			}
			parent.set(objectNode.getSource(), objectNode);
		}
		else {
 			List<Object> list = (List<Object>)parent.get(objectNode.getSource());
 			if (list == null) {
 				list = new ArrayList<Object>();
 				parent.set(objectNode.getSource(), list);
 			}
 			list.add(objectNode);
		}            			
		
	}
	
	@SuppressWarnings("unchecked")
	private void readAttributes(XMLEvent event, StreamObject prototype) throws UnmarshallerException
	{		
		boolean serialIdAttributeFound = false;
		PlasmaType type = prototype.getType();
        Iterator<Attribute> iter = event.asStartElement().getAttributes();
	    while (iter.hasNext()) {
	    	Attribute attrib = (Attribute)iter.next();
			QName attribName = attrib.getName();
			String localPart = attribName.getLocalPart();
			if (XMLConstants.ATTRIB_TARGET_NAMESPACE.equals(localPart)) {
				continue;
			}
			else if (XMLConstants.XMLSCHEMA_INSTANCE_NAMESPACE_URI.equals(attribName.getNamespaceURI())) {
				// its an XSI attribute we care about
				if ("type".equals(localPart)) {
					String xsiTypeLocalName = attrib.getValue();
					int delim = xsiTypeLocalName.indexOf(":");
					if (delim >= 0)
						xsiTypeLocalName = xsiTypeLocalName.substring(delim+1);
					// In order to validate XML graphs with both containment
					// and non containment references with an XML schema,
					// the Schema types must be generated with XSI 'xsi:type'
					// attribute to indicate a subclass. The subclass name will be
					// either 1.) matching the local for the type, wherein we
					// assume a containment reference or 2.) matching a generated
					// synthetic name for the non-containment reference for the
					// type, e.g. 'MyTypeRef'. So for containment references
					// we should find 'xsi:type="prefix:MyType" and for 
					// non-containment references we should find 'xsi:type="prefix:MyTypeRef"
					// or some other generated suffix with no name collisions. 
					if (!xsiTypeLocalName.equals(type.getLocalName())) {
						if (log.isDebugEnabled())
							log.debug("type as non-containment reference, " 
								+ type.getURI() + "#" + type.getName());
						prototype.setNonContainmentReference(true);
					}
				}
				continue;
			}
			
	    	PlasmaProperty property = findPropertyByLocalName(type, localPart);
	    	if (property == null) {
	    		if (!SchemaUtil.getSerializationAttributeName().equals(localPart)) {	    		
			        Location loc = event.getLocation();               
	 		        String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
	 		        msg += " - no property '" + localPart + "' found defined for type "
		    			+ type.getURI() + "#" + type.getName();
		    		throw new UnmarshallerException(msg);
	    		}
	    		else {
	    			prototype.setSerialId(attrib.getValue());
	    			serialIdAttributeFound = true;
	    			continue;
	    		}
	    	}
	    	
	    	if (property.getType().isDataType()) {
		    	if (property.isMany()) {
			        Location loc = event.getLocation();               
	 		        String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
	 		        msg += " - unexpected 'many' propery ("
		    			+ type.getURI() + "#" + type.getName() + "." + property.getName() 
		    			+ ") can only set singular properties from attribute '" 
		    			+ localPart + "'";
		    		throw new UnmarshallerException(msg);
		    	}
		    	Object value = PlasmaDataHelper.INSTANCE.convert(property, attrib.getValue());
		    	if (!property.isReadOnly()) {
			    	prototype.set(property, value);
		    	}
		    	else {
	            	DataFlavor dataFlavor = property.getDataFlavor();
	            	switch (dataFlavor) {
	            	case integral:
	    		        Location loc = event.getLocation();               
	     		        String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
	     		        msg += " - cannot set integral readonly propery ("
	    	    			+ property.getName() + ") - ignoring attribute data '" 
	    	    			+ attrib.getValue() + "' for readonly propery, "
	    	    			+ type.getURI() + "#" + type.getName() + "." + property.getName();
	    	    		log.warn(msg);
	            	    break;
	            	default:
	    		    	prototype.set(property, value);
	            	}	
		    	}
	    	} 
	    	else {
		        Location loc = event.getLocation();               
 		        String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
 		        msg += " - expected datatype property - attribute '"+ localPart 
 		            + "' is a reference property as defined in type "
	    			+ type.getURI() + "#" + type.getName();
	    		throw new UnmarshallerException(msg);
	    	}
	    }	
	    
	    if (!serialIdAttributeFound) {
	        Location loc = event.getLocation();               
		        String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
		        msg += " - expected serialization attribute '" 
		        	+ SchemaUtil.getSerializationAttributeName() + "' for type "
    			+ type.getURI() + "#" + type.getName();
    		throw new UnmarshallerException(msg);	    	
	    }
	}
	
	private PlasmaProperty getPropertyByLocalName(XMLEvent event, PlasmaType type, String localName) 
	    throws UnmarshallerException 
	{
    	PlasmaProperty property = findPropertyByLocalName(type, localName);
    	if (property == null) {
            Location loc = event.getLocation();               
            String msg = "line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "]";
            msg += " - no property with local name (" + localName + ") found defined for type "
		        + type.getURI() + "#" + type.getName();;
	        throw new UnmarshallerException(msg);
    	}
    	return property;
	}
	
    /**
     * Returns a property from the given type based on the given local name.
     * @param type the containing SDO Type
     * @param localName the property local name
     * @return the property
     */
    // FIXME: does this justify mapping by local names?
	private PlasmaProperty findPropertyByLocalName(PlasmaType type, String localName) {
		List<Property> props = type.getProperties();
		if (props != null)
			for (Property p : type.getProperties()) {
				PlasmaProperty property = (PlasmaProperty)p;
				if (property.getLocalName().equals(localName))
					return property;
			}
		return null;
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

/*
Attempt at Schema validation with StAX
if (this.locationURI != null) {
SchemaFactory factory = SchemaFactory.newInstance(SchemaConstants.XMLSCHEMA_NAMESPACE_URI);

try {
	
	URI uri = URI.create(this.locationURI);
    javax.xml.validation.Schema schema = factory.newSchema(new File(uri));
    Validator validator = schema.newValidator();
    
	validator.validate(new StreamSource(stream));
	
} catch (SAXException e) {
	throw new UnmarshallerRuntimeException(e);
} catch (IOException e) {
	throw new UnmarshallerRuntimeException(e);
}
}
*/

