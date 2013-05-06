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


/**
 * Constructs a provisioning model
 * based on the a given XML Schema
 */
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
