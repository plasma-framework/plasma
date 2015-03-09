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

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.metamodel.Model;
import org.plasma.provisioning.ProvisioningModelAssembler;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.query.Query;
import org.plasma.query.bind.PlasmaQueryDataBinding;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.access.client.SDODataAccessClient;
import org.plasma.sdo.core.CoreXMLDocument;
import org.plasma.sdo.helper.PlasmaXSDHelper;
import org.plasma.sdo.xml.MarshallerException;
import org.plasma.sdo.xml.StreamMarshaller;
import org.plasma.xml.uml.MDModelAssembler;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

import commonj.sdo.DataGraph;
import commonj.sdo.helper.XMLDocument;

/**
 * Query tool. 
 * <p></p> 
 * <b>Usage:</b> java <@link org.plasma.provisioning.cli.QueryTool>  
 * Option                          Description
 * ------                          -----------
 * --command <QueryToolAction>     the primary action or command performed by this tool - one of [compile, run] is expected
 * --dest [File]                   the fully qualified tool output destination file or directory name
 * --destType <ProvisioningTarget> a qualifier describing the output destination - one of [xsd, xmi] is expected
 * --help                          prints help on this tool
 * --namespace                     a single namespace URI
 * --namespacePrefix               a single namespace prefix
 * --silent                        whether to log or print no messages at all (typically for testing)
 * --source [File]                 the fully qualified tool input source file or directory name
 * --verbose                       whether to log or print detailed messages   
*/
public class QueryTool extends ProvisioningTool {
    
    private static Log log = LogFactory.getLog(
            QueryTool.class); 
    
    /**
     * Command line entry point. 
     * <p></p> 
     * <b>Usage:</b> java <@link org.plasma.provisioning.cli.QueryTool>  
     * Option                          Description
     * ------                          -----------
     * --command <QueryToolAction>     the primary action or command performed by this tool - one of [compile, run] is expected
     * --dest [File]                   the fully qualified tool output destination file or directory name
     * --destType <ProvisioningTarget> a qualifier describing the output destination - one of [xsd, xmi] is expected
     * --help                          prints help on this tool
     * --namespace                     a single namespace URI
     * --namespacePrefix               a single namespace prefix
     * --silent                        whether to log or print no messages at all (typically for testing)
     * --source [File]                 the fully qualified tool input source file or directory name
     * --verbose                       whether to log or print detailed messages   
    */
    public static void main(String[] args) 
        throws TransformerConfigurationException, 
        IOException, TransformerException, ClassNotFoundException {

    	OptionParser parser = new OptionParser();    	  
    	OptionSpecBuilder verboseOpt = parser.accepts( ProvisioningToolOption.verbose.name(), ProvisioningToolOption.verbose.getDescription() );    	 
    	OptionSpecBuilder silentOpt = parser.accepts( ProvisioningToolOption.silent.name(), ProvisioningToolOption.silent.getDescription() );    	 
    	
    	OptionSpecBuilder helpOpt = parser.accepts( ProvisioningToolOption.help.name(), ProvisioningToolOption.help.getDescription() );    	 
    	OptionSpecBuilder commandOpt = parser.accepts( ProvisioningToolOption.command.name(), 
    			ProvisioningToolOption.command.getDescription() + " - one of [" + QueryToolAction.asString() + "] is expected");    	 
    	commandOpt.withRequiredArg().ofType( QueryToolAction.class );    	 
    	
       	OptionSpec<File> srcOpt = parser.accepts( ProvisioningToolOption.source.name(), 
    			ProvisioningToolOption.source.getDescription()).withOptionalArg().ofType( File.class );
    	OptionSpec<File> destOpt = parser.accepts( ProvisioningToolOption.dest.name(), 
    			ProvisioningToolOption.dest.getDescription()).withOptionalArg().ofType( File.class );
    	OptionSpecBuilder destTypeOpt = parser.accepts( ProvisioningToolOption.destType.name(), 
    			ProvisioningToolOption.destType.getDescription() + " - one of [" + ProvisioningTarget.asString() + "] is expected");    	 
    	destTypeOpt.withRequiredArg().ofType( ProvisioningTarget.class );    	 
    	OptionSpec<String> namespaceOpt = parser.accepts( ProvisioningToolOption.namespace.name(), 
    			ProvisioningToolOption.namespace.getDescription()).withOptionalArg().ofType( String.class );
    	OptionSpec<String> namespacePrefixOpt = parser.accepts( ProvisioningToolOption.namespacePrefix.name(), 
    			ProvisioningToolOption.namespacePrefix.getDescription()).withOptionalArg().ofType( String.class );

    	OptionSet options = parser.parse(args);  

    	if (options.has(helpOpt)) {
    		printUsage(parser, log);
    		return;
    	}
    	
    	if (!options.has(ProvisioningToolOption.command.name())) {
    		if (!options.has(silentOpt))
    		    printUsage(parser, log);
    		throw new IllegalArgumentException("expected option '" + ProvisioningToolOption.command.name() + "'");
    	}
    	QueryToolAction command = (QueryToolAction)options.valueOf(ProvisioningToolOption.command.name());

    	File sourceFile = null;
        if (options.has(srcOpt)) {
        	sourceFile = srcOpt.value(options);
    	}
        else
    		throw new IllegalArgumentException("expected option '" + ProvisioningToolOption.source.name() + "'");
        if (!sourceFile.exists())
            throw new IllegalArgumentException("given source file '"
                    + sourceFile.getName() + "' does not exist");
        
        
        File destFile = new File("./target/" + QueryTool.class.getSimpleName() + ".out");
        if (options.has(destOpt)) {
        	destFile = destOpt.value(options);
    	}
        if (destFile.getParentFile() != null)
            if (!destFile.getParentFile().exists())
                if (!destFile.getParentFile().mkdirs())
                    throw new IllegalArgumentException("directory path cannot be created for given dest file '"
                        + destFile.getAbsolutePath() + "' ");
        
    	if (!options.has(ProvisioningToolOption.destType.name())) {
    		if (!options.has(silentOpt))
    		    printUsage(parser, log);
    		throw new IllegalArgumentException("expected option '" + ProvisioningToolOption.destType.name() + "'");
    	}
        ProvisioningTarget targetType = (ProvisioningTarget)options.valueOf(ProvisioningToolOption.destType.name());
    
    	if (!options.has(ProvisioningToolOption.namespace.name())) {
    		if (!options.has(silentOpt))
    		    printUsage(parser, log);
    		throw new IllegalArgumentException("expected option '" + ProvisioningToolOption.namespace.name() + "'");
    	}
    	String destNamespaceURI = (String)options.valueOf(ProvisioningToolOption.namespace.name());

    	String destNamespacePrefix = "tns";
    	if (options.has(ProvisioningToolOption.namespacePrefix.name())) {
    		destNamespacePrefix = (String)options.valueOf(ProvisioningToolOption.namespacePrefix.name());
    	}
                        
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
		UMLModelAssembler modelAssembler = new MDModelAssembler(query, 
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
 }
