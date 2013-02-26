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
package org.plasma.provisioning.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ProvisioningModelAssembler;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.query.bind.PlasmaQueryDataBinding;
import org.plasma.query.collector.PropertySelectionCollector;
import org.plasma.query.Query;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.access.client.SDODataAccessClient;
import org.plasma.sdo.core.CoreXMLDocument;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.helper.PlasmaXMLHelper;
import org.plasma.sdo.helper.PlasmaXSDHelper;
import org.plasma.sdo.xml.MarshallerException;
import org.plasma.sdo.xml.StreamMarshaller;
import org.plasma.sdo.xml.XMLOptions;
import org.plasma.text.lang3gl.DefaultLang3GLContext;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLDialect;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;
import org.plasma.text.lang3gl.java.DSLAssembler;
import org.plasma.text.lang3gl.java.DSLFactory;
import org.plasma.text.lang3gl.java.SDOAssembler;
import org.plasma.text.lang3gl.java.SDOFactory;
import org.plasma.xml.schema.Attribute;
import org.plasma.xml.schema.Element;
import org.plasma.xml.schema.ExplicitGroup;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

import commonj.sdo.DataGraph;
import commonj.sdo.Type;
import commonj.sdo.helper.XMLDocument;

public class QueryTool extends ProvisioningTool {
    
    private static Log log = LogFactory.getLog(
            QueryTool.class); 
        
    public static void main(String[] args) 
        throws TransformerConfigurationException, 
        IOException, TransformerException, ClassNotFoundException {
        
        if (args.length != 6) {
            printUsage();
            return;
        }
        if (!args[0].startsWith("-")) {
            printUsage();
            return;
        }
        QueryToolAction command = QueryToolAction.valueOf(
                args[0].substring(1));

        File sourceFile = new File(args[1]);
        if (!sourceFile.exists())
            throw new IllegalArgumentException("given source file '"
                    + args[1] + "' does not exist");
        File destFile = new File(args[2]);
        if (destFile.getParentFile() != null)
            if (!destFile.getParentFile().exists())
                if (!destFile.getParentFile().mkdirs())
                    throw new IllegalArgumentException("directory path cannot be created for given dest file '"
                        + args[2] + "' ");
        
        String destFileType = args[3];
        if (destFileType == null || destFileType.trim().length() == 0)
            throw new IllegalArgumentException("expected argument, destFileType"); 
        
        ProvisioningTarget targetType = null;
        try {
            targetType = ProvisioningTarget.valueOf(destFileType);
        }
        catch (IllegalArgumentException e) {
        	List<String> displayValues = new ArrayList<String>();
        	for (ProvisioningTarget v : ProvisioningTarget.values())
        		displayValues.add(v.name());
            throw new IllegalArgumentException("unknown target type '"
                    + destFileType + "' - expected one of "
                    + displayValues + "");
        }	
    
        String destNamespaceURI = args[4];
        if (destNamespaceURI == null || destNamespaceURI.trim().length() == 0)
            throw new IllegalArgumentException("expected argument, destNamespaceURI");            
        String destNamespacePrefix = args[5];
        if (destNamespacePrefix == null || destNamespacePrefix.trim().length() == 0)
        	destNamespacePrefix = "tns";            
                        
        switch (command) {
        case compile:
            File baseTempDir = new File(System.getProperty("java.io.tmpdir"));
                                    
            switch (targetType) {
            case xsd:
	        	log.info("compiling query into XML Schema model");
	            generateXMLSchemaModel(sourceFile,  
	                destFile, destNamespaceURI, destNamespacePrefix);
	            break;
            case xmi:
	        	log.info("compiling query into UML/XMI model");
	        	generateUMLModel(sourceFile,  
	                destFile, destNamespaceURI, destNamespacePrefix);
	            break;
	        default:
	            throw new IllegalArgumentException("unknown target type '"
	                    + targetType.name() + "'");
	        }	            
            break;           
        case run:
        	log.info("running query");
        	run(sourceFile,  
                destFile, destNamespaceURI, destNamespacePrefix);            
        	log.info("successfully provisioned XML to, " + destFile.getName());

            break;           
        default:
            throw new IllegalArgumentException("unknown command '"
                    + command.toString() + "'");
        }        
    }    

    private static void run(File queryFile, File destFile, 
            String destNamespaceURI, String destNamespacePrefix)  {
    	
    	log.info("unmarshaling query file '" 
    			+ queryFile.getName() + "'");
    	String rootElementName = "Data";
    	
        Query query = unmarshalQuery(queryFile);
    	SDODataAccessClient service = new SDODataAccessClient();
        DataGraph[] results = service.find(query);
        OutputStream xmlos;
		try {
			xmlos = new FileOutputStream(destFile);
		} catch (FileNotFoundException e) {
            throw new PlasmaRuntimeException(e);     
		}
        
        for (int i = 0; i < results.length; i++) {
            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
            //log.info(dataObject.dump());
            XMLDocument doc = new CoreXMLDocument(dataObject,
            		destNamespaceURI, rootElementName);
            StreamMarshaller assembler = 
            	new StreamMarshaller(doc);
            try {
				assembler.marshal(xmlos);
			} catch (XMLStreamException e) {
	            throw new PlasmaRuntimeException(e);     
			} catch (MarshallerException e) {
	            throw new PlasmaRuntimeException(e);     
			}
        }

    }   

    private static void generateUMLModel(File queryFile, File destFile, 
            String destNamespaceURI, String destNamespacePrefix) {
    	
    	log.info("unmarshaling query file '" 
    			+ queryFile.getName() + "'");
        Query query = unmarshalQuery(queryFile);
        
        ProvisioningModelAssembler stagingAssembler = new ProvisioningModelAssembler(query, 
        		destNamespaceURI, destNamespacePrefix);
        Model model = stagingAssembler.getModel();
        
        try {
            FileOutputStream os = new FileOutputStream(new File(destFile.getParentFile(), "query-pim.xml"));
            ProvisioningModelDataBinding binding = new ProvisioningModelDataBinding(new DefaultValidationEventHandler());
			binding.marshal(model, os);
			
		} catch (FileNotFoundException e) {
            throw new PlasmaRuntimeException(e);     
		} catch (JAXBException e) {
            throw new PlasmaRuntimeException(e);
		} catch (SAXException e) {
            throw new PlasmaRuntimeException(e);
		}
		ModelAdapter validator = 
			new ModelAdapter(model);
        
    	log.info("assembling XMI model");
		UMLModelAssembler modelAssembler = new UMLModelAssembler(query, 
				destNamespaceURI, destNamespacePrefix);
        Document document = modelAssembler.getDocument();
        
    	log.info("marshaling XMI model to file, '"
    			+ destFile.getName() + "'");
        try {
			FileOutputStream os = new FileOutputStream(destFile);
		       XMLOutputter outputter = new XMLOutputter();
		       outputter.output(document, os);
		} catch (FileNotFoundException e) {
            throw new PlasmaRuntimeException(e);
		} catch (IOException e) {
            throw new PlasmaRuntimeException(e);
		}
     }    
 
    
    private static void generateXMLSchemaModel(File queryFile, File destFile, 
            String destNamespaceURI, String destNamespacePrefix) {
    	
    	log.info("unmarshaling query file '" 
    			+ queryFile.getName() + "'");
        Query query = unmarshalQuery(queryFile);
        
        FileOutputStream ouputStream;
		try {
			ouputStream = new FileOutputStream(destFile);
		} catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
		}
		
		try {
	    	log.info("marshaling XML schema to file, '"
	    			+ destFile.getName() + "'");
	        PlasmaXSDHelper.INSTANCE.generate(query, 
	        		destNamespaceURI, 
	        		destNamespacePrefix,
	        		null,
	        		ouputStream);	        
		}
		finally {
	    	try {
				ouputStream.flush();
		    	ouputStream.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
    	
    }    
        
    private static Query unmarshalQuery(File queryFile) {
        String queryXml = null;
        
        try {
            byte[] queryBytes = readContent(new FileInputStream(queryFile));
            queryXml = new String(queryBytes);
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }
        
        Query query = null;
        try {
            PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                    new DefaultValidationEventHandler());
                       
            if (log.isDebugEnabled())
                log.debug("query: " + queryXml);
            query = (Query)binding.validate(queryXml); 
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        } 
        return query;
    }
     
    private static void printUsage() {
        log.info("usage: -action sourceFile destFile destFileType destNamespaceURI [destNamespacePrefix]");
    }
}
