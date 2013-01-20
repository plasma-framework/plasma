package org.plasma.provisioning;

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.xsd.XSDSchemaConverter;
import org.plasma.provisioning.xsd.SDOXSchemaConverter;
import org.plasma.xml.schema.OpenAttrs;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.sdox.SDOXConstants;


public class SchemaProvisioningModelAssembler {
	   private static Log log = LogFactory.getLog(
			   SchemaProvisioningModelAssembler.class); 
	   	
	private SchemaConverter processor;
	
	@SuppressWarnings("unused")
	private SchemaProvisioningModelAssembler() {}
	
    public SchemaProvisioningModelAssembler(Schema schema, 
    	String destNamespaceURI,
    	String destNamespacePrefix) {
    	QName sdoxNamespace = findOpenAttributeQNameByValue(SDOXConstants.SDOX_NAMESPACE_URI, 
        		schema);
    	
    	if (sdoxNamespace != null) {
    		processor = new SDOXSchemaConverter(schema, 
    		    destNamespaceURI,
    		    destNamespacePrefix);
    	} 
    	else {
    		processor = new XSDSchemaConverter(schema, 
        		    destNamespaceURI,
        		    destNamespacePrefix);
    	}
	}
    
    private QName findOpenAttributeQNameByValue(String value, OpenAttrs attrs) {
    	Iterator<QName> iter = attrs.getOtherAttributes().keySet().iterator();
    	while (iter.hasNext()) {
    		QName key = iter.next();
    		String s = attrs.getOtherAttributes().get(key);
     	    if (s != null && s.equals(value)) 
    			return key;
    	}  
    	return null;
    }    

	public Model getModel() {
		return processor.buildModel();
	}	
}
