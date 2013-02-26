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
package org.plasma.sdo.wsdl;




import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.platform.sdo.TaxonomyQuery;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ProvisioningModelAssembler;
import org.plasma.query.model.Query;
import org.plasma.sdo.DASClientTestCase;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SchemaDataBinding;
import org.plasma.xml.schema.SchemaModelAssembler;
import org.plasma.xml.wsdl.v11.Definitions;
import org.plasma.xml.wsdl.v11.TBinding;
import org.plasma.xml.wsdl.v11.TBindingOperation;
import org.plasma.xml.wsdl.v11.TBindingOperationMessage;
import org.plasma.xml.wsdl.v11.TMessage;
import org.plasma.xml.wsdl.v11.TOperation;
import org.plasma.xml.wsdl.v11.TParam;
import org.plasma.xml.wsdl.v11.TPart;
import org.plasma.xml.wsdl.v11.TPort;
import org.plasma.xml.wsdl.v11.TPortType;
import org.plasma.xml.wsdl.v11.TService;
import org.plasma.xml.wsdl.v11.TTypes;
import org.plasma.xml.wsdl.v11.WSDLDataBinding;
import org.plasma.xml.wsdl.v11.ObjectFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Query tests which return data-graphs of various depths. 
 */
public class WSDL11Test extends DASClientTestCase {
    private static Log log = LogFactory.getLog(WSDL11Test.class);
    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(WSDL11Test.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();      
	    
    }
 
    public void testVersion11() throws JAXBException, SAXException, ParserConfigurationException, IOException {
        
        log.info("testVersion11()");
        
        String targetNamespaceURI = "http://plasma.org/test/sdo/wsdl/profile";
        ObjectFactory fac = new ObjectFactory();
       
        DocumentBuilderFactory docFac = 
            DocumentBuilderFactory.newInstance();
        DocumentBuilder dom = docFac.newDocumentBuilder();
        Document doc = dom.newDocument();
        Element foo = doc.createElement("foo");
            
            
        Definitions root = fac.createDefinitions();
        root.setTargetNamespace(targetNamespaceURI);
             
        QName targetNamespace = new QName(targetNamespaceURI, 
        		"tns");
        //root.getAnies().add(foo);
        
        //root.getOtherAttributes().put(targetNamespace, 
        //		targetNamespaceURI);

        QName wsoap = new QName("http://www.w3.org/2004/08/wsdl/soap12", 
        		"wsoap", "wsoap");
        //root.getOtherAttributes().put(wsoap, 
        //		"http://www.w3.org/2004/08/wsdl/soap12");
        
        
        TTypes types = new TTypes();
        root.getImportsAndTypesAndMessages().add(types);
        
         
        Query query = TaxonomyQuery.createQuery("FEA Performance Reference Model (PRM)");
        //query.getFromClause().getEntity().setNamespaceURI(targetNamespaceURI);
        
        query.setName("deep-taxonomy-query");
        
        File loadDir = new File(targetDir, "../../test/data/org/plasma/platform/sdo");
        File loadFile = new File(loadDir, "profile-export-dataload.xml");
        
        ProvisioningModelAssembler stagingAssembler = new ProvisioningModelAssembler(query, 
        		targetNamespaceURI, "ex");
        Model model = stagingAssembler.getModel();
		SchemaModelAssembler xsdAssembler = new SchemaModelAssembler(model, 
				targetNamespaceURI, "ex");
		xsdAssembler.setCreateNonContainmentReferenceTypes(true);
        Schema schema = xsdAssembler.getSchema();
        
        ByteArrayOutputStream byteos = new ByteArrayOutputStream();
        SchemaDataBinding schemaBinding = new SchemaDataBinding(new DefaultValidationEventHandler());
        schemaBinding.marshal(schema, byteos, false);
        byteos.flush();
        byte[] buf = byteos.toByteArray();
        byteos.close();
        ByteArrayInputStream byteis = new ByteArrayInputStream(buf);        
        
        Document schemaDoc = dom.parse(byteis);
        types.getAnies().add(schemaDoc.getDocumentElement());
        
        // messages section
        QName inputMessageName = new QName(targetNamespaceURI, "LoadGraphInput", "tns"); // FIXME no TNS
        TMessage message = fac.createTMessage();
        root.getImportsAndTypesAndMessages().add(message);
        message.setName(inputMessageName.getLocalPart());
        TPart part = fac.createTPart();
        message.getParts().add(part);
        part.setName("body");
        QName rootTypeName = new QName(targetNamespaceURI, "TaxonomyDatagraph", "tns"); // FIXME no TNS
        part.setElement(rootTypeName);
        
        QName outputMessageName = new QName(targetNamespaceURI, "LoadGraphOutput", "tns"); // FIXME no TNS
        message = fac.createTMessage();
        root.getImportsAndTypesAndMessages().add(message);
        message.setName(outputMessageName.getLocalPart());
        part = fac.createTPart();
        message.getParts().add(part);
        part.setName("body");
        part.setElement(rootTypeName);
        
        // port type section
        QName portTypeName = new QName(targetNamespaceURI, "DataGraphPortType", "tns"); // FIXME no TNS
        TPortType portType = fac.createTPortType();
        root.getImportsAndTypesAndMessages().add(portType);
        portType.setName(portTypeName.getLocalPart());
        TOperation operation = fac.createTOperation();
        operation.setName("LoadGraph");
        portType.getOperations().add(operation);
               
        TParam param = fac.createTParam();
        param.setMessage(inputMessageName);
        operation.getInputsAndOutputs().add(fac.createTOperationInput(param));
        
        param = fac.createTParam();
        param.setMessage(outputMessageName);
        operation.getInputsAndOutputs().add(fac.createTOperationOutput(param));
        
        // binding section
        QName bindingName = new QName(targetNamespaceURI, "DataGraphBinding", "tns"); // FIXME no TNS
        TBinding binding = fac.createTBinding();
        root.getImportsAndTypesAndMessages().add(binding);
        binding.setName(bindingName.getLocalPart());
        binding.setType(portTypeName);
        
        Element soapBinding = dom.newDocument().createElementNS(
        		"http://schemas.xmlsoap.org/wsdl/soap/", 
        		"soap:binding");
        soapBinding.setAttribute("style", "document");
        soapBinding.setAttribute("transport", 
        	"http://schemas.xmlsoap.org/soap/http");
        binding.getAnies().add(soapBinding);

        TBindingOperation bindingOper = fac.createTBindingOperation();
        binding.getOperations().add(bindingOper);
        bindingOper.setName("LoadGraph");
        
        Element soapBindingOper = dom.newDocument().createElementNS(
        		"http://schemas.xmlsoap.org/wsdl/soap/", 
        		"soap:operation");
        soapBindingOper.setAttribute("soapAction", 
        		targetNamespaceURI + "/" + "LoadGraph");
        
        TBindingOperationMessage input = fac.createTBindingOperationMessage();
        bindingOper.setInput(input);
        Element body = dom.newDocument().createElementNS(
        		"http://schemas.xmlsoap.org/wsdl/soap/", 
        		"soap:body");
        body.setAttribute("use", "literal");
        input.getAnies().add(body);

        TBindingOperationMessage output = fac.createTBindingOperationMessage();
        bindingOper.setOutput(output);
        output.getAnies().add(body);
        
        // service section
        TService service = fac.createTService();
        root.getImportsAndTypesAndMessages().add(service);
        TPort port = fac.createTPort();
        service.getPorts().add(port);
        port.setName("DataGraphPort");
        port.setBinding(bindingName);
        Element soapAddress = dom.newDocument().createElementNS(
        		"http://schemas.xmlsoap.org/wsdl/soap/", 
        		"soap:address");
        soapAddress.setAttribute("location", 
        		targetNamespaceURI + "/" + "service");
        port.getAnies().add(soapAddress);
           
        // output the WSDL
        org.plasma.xml.wsdl.v11.WSDLDataBinding wsdlDatabinding = new org.plasma.xml.wsdl.v11.WSDLDataBinding(
        		new DefaultValidationEventHandler());
        String xml = wsdlDatabinding.marshal(root);
        log.info(xml); 
        OutputStream wsdlos = new FileOutputStream(new File(targetDir, "test.wsdl"));
        wsdlDatabinding.marshal(root, wsdlos);
	    
    }  
    
     
}