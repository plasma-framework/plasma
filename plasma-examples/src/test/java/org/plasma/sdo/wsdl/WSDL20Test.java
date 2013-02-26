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




import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.platform.sdo.TaxonomyQuery;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ProvisioningConstants;
import org.plasma.provisioning.ProvisioningModelAssembler;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.query.model.Query;
import org.plasma.sdo.DASClientTestCase;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SchemaModelAssembler;
import org.plasma.xml.wsdl.v20.Binding;
import org.plasma.xml.wsdl.v20.BindingFaultType;
import org.plasma.xml.wsdl.v20.BindingOperationMessageType;
import org.plasma.xml.wsdl.v20.BindingOperationType;
import org.plasma.xml.wsdl.v20.Endpoint;
import org.plasma.xml.wsdl.v20.MessageRefFaultType;
import org.plasma.xml.wsdl.v20.ObjectFactory;
import org.plasma.xml.wsdl.v20.Description;
import org.plasma.xml.wsdl.v20.Interface;
import org.plasma.xml.wsdl.v20.InterfaceFaultType;
import org.plasma.xml.wsdl.v20.InterfaceOperationType;
import org.plasma.xml.wsdl.v20.MessageRefType;
import org.plasma.xml.wsdl.v20.Service;
import org.plasma.xml.wsdl.v20.Types;
import org.plasma.xml.wsdl.v20.WSDLDataBinding;
import org.xml.sax.SAXException;

/**
 * Query tests which return data-graphs of various depths. 
 */
public class WSDL20Test extends DASClientTestCase {
    private static Log log = LogFactory.getLog(WSDL20Test.class);
    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(WSDL20Test.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();      
	    
    }
  
    
    public void testVersion20() throws JAXBException, SAXException, FileNotFoundException {
        
        log.info("testVersion20()");
        String targetNamespaceURI = "http://plasma.org/test/sdo/wsdl/profile";
       
        
        Description root = new Description();
        root.setTargetNamespace(targetNamespaceURI);
        QName targetNamespace = new QName(targetNamespaceURI, 
        		"tns");
        root.getOtherAttributes().put(targetNamespace, 
        		targetNamespaceURI);

        QName wsoap = new QName("http://www.w3.org/2004/08/wsdl/soap12", 
        		"wsoap", "wsoap");
        root.getOtherAttributes().put(wsoap, 
        		"http://www.w3.org/2004/08/wsdl/soap12");
        
        
        Types types = new Types();
        root.getImportsAndIncludesAndTypes().add(types);
        
         
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
        types.getAnies().add(schema); 
        
        Interface itf = new Interface();
        root.getImportsAndIncludesAndTypes().add(itf);
        itf.setName("DataGraphInterface");
 
        ObjectFactory fac = new ObjectFactory();
        
        InterfaceFaultType fault = new InterfaceFaultType();
        fault.setName("invalidDataFault");
        fault.setElement("tns:invalidDataError");
        itf.getOperationsAndFaultsAndAnies().add(fac.createInterfaceFault(fault));        
        
        InterfaceOperationType operation = new InterfaceOperationType();
        operation.setName("LoadGraph");
        operation.setPattern("http://www.w3.org/ns/wsdl/in-out");
        operation.setStyle("http://www.w3.org/ns/wsdl/style/iri");
        operation.setSafe(true);
        itf.getOperationsAndFaultsAndAnies().add(fac.createInterfaceOperation(operation));
        
        MessageRefType input = new MessageRefType();
        input.setElement("tns:TaxonomyDatagraph");
        input.setMessageLabel("In");
        operation.getInputsAndOutputsAndInfaults().add(fac.createInterfaceOperationTypeInput(input));
        
        MessageRefType output = new MessageRefType();
        output.setElement("tns:TaxonomyDatagraph");
        output.setMessageLabel("Out");
        operation.getInputsAndOutputsAndInfaults().add(fac.createInterfaceOperationTypeOutput(output));
        
        MessageRefFaultType refFault = new MessageRefFaultType();
        QName faultName = new QName(targetNamespaceURI, "invalidDataFault", "tns");
        refFault.setRef(faultName);
        refFault.setMessageLabel("Out");
        operation.getInputsAndOutputsAndInfaults().add(fac.createInterfaceOperationTypeOutfault(refFault));

        // binding section
        QName bindingName = new QName(targetNamespaceURI, "DataGraphSoapBinding", "tns");
        Binding binding = fac.createBinding();
        binding.setName(bindingName.getLocalPart());
        QName interfaceName = new QName(targetNamespaceURI, "DataGraphInterface", "tns");
        binding.setInterface(interfaceName);
        binding.setType("http://www.w3.org/2004/08/wsdl/soap12");
        QName protocol = new QName("http://www.w3.org/2004/08/wsdl/soap12", 
        		"protocol", "wsoap");
        binding.getOtherAttributes().put(protocol, "http://www.w3.org/2003/05/soap/bindings/HTTP");
        root.getImportsAndIncludesAndTypes().add(binding);
        
         
        BindingOperationType bindingOperation = fac.createBindingOperationType();
        QName operationName = new QName(targetNamespaceURI, "loadGraph", "tns");
        bindingOperation.setRef(operationName);
        QName mep = new QName("http://www.w3.org/2004/08/wsdl/soap12", 
        		"mep", "wsoap");
        bindingOperation.getOtherAttributes().put(mep, "http://www.w3.org/2003/05/soap/mep/request-response");
        binding.getOperationsAndFaultsAndAnies().add(fac.createBindingOperation(bindingOperation));
        
        BindingFaultType bindingFault = fac.createBindingFaultType();
        bindingFault.setRef(faultName);
        QName code = new QName("http://www.w3.org/2004/08/wsdl/soap12", 
        		"code", "wsoap");
        bindingFault.getOtherAttributes().put(code, "soap:Sender");
        binding.getOperationsAndFaultsAndAnies().add(fac.createBindingFault(bindingFault));

        // service section
        Service service = fac.createService();
        service.setName("DataGraphService");
        service.setInterface(interfaceName);
        root.getImportsAndIncludesAndTypes().add(service);
        
        Endpoint endpoint = fac.createEndpoint();
        endpoint.setName("DataGraphEndpoint");
        endpoint.setBinding(bindingName);
        endpoint.setAddress(targetNamespaceURI);
        service.getEndpointsAndAnies().add(endpoint);
        
/*        
        <service name="reservationService" 
            interface="tns:reservationInterface">

          <endpoint name="reservationEndpoint" 
                    binding="tns:reservationSOAPBinding"
                    address ="http://greath.example.com/2004/reservation"/>
             
       </service>   
*/            
        // output the WSDL
        org.plasma.xml.wsdl.v20.WSDLDataBinding wsdlDatabinding = new org.plasma.xml.wsdl.v20.WSDLDataBinding(
        		new DefaultValidationEventHandler());
        String xml = wsdlDatabinding.marshal(root);
        log.info(xml); 
        OutputStream wsdlos = new FileOutputStream(new File(targetDir, "test.wsdl"));
        wsdlDatabinding.marshal(root, wsdlos);
	    
    }  
     
}