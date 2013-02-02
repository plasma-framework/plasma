package org.plasma.provisioning.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.plasma.common.bind.BindingValidationEventHandler;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.SchemaProvisioningModelAssembler;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SchemaDataBinding;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

public class XSDTool extends ProvisioningTool {
    
    private static Log log = LogFactory.getLog(
            XSDTool.class); 
        
    public static void main(String[] args) 
        throws TransformerConfigurationException, 
        IOException, TransformerException, ClassNotFoundException {
        
        if (args.length < 6) {
            printUsage();
            return;
        }
        if (!args[0].startsWith("-")) {
            printUsage();
            return;
        }
        
        XSDToolAction command = null;
        try {
            command = XSDToolAction.valueOf(
                args[0].substring(1));
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < XSDToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(XSDToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + args[0].substring(1) + "' - expected one of ["
    				+ buf.toString() + "]");
    	}

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

    	long lastExecution = 0L;
        if (args.length >= 7) {
        	try {
        		lastExecution = Long.valueOf(args[6]).longValue();
        	}
        	catch (NumberFormatException e) {
        		throw new IllegalArgumentException(getUsage());                	
        	}
        }
        
        switch (command) {
        case convert:
            if (sourceFile.lastModified() < lastExecution) {
                log.info("skipping conversion - no stale source XSD detected");	
                return;
            }            	
            switch (targetType) {
            case xmi:
	        	log.info("compiling Schema into UML/XMI model");
	        	generateUMLModel(sourceFile,  
	                destFile, destNamespaceURI, destNamespacePrefix);
	            break;
	        default:
	            throw new IllegalArgumentException("unknown target type '"
	                    + targetType.name() + "'");
	        }	            
            break;           
        default:
            throw new IllegalArgumentException("unknown command '"
                    + command.toString() + "'");
        }        
    }    

    private static void generateUMLModel(File schemaFile, File destFile, 
            String destNamespaceURI, String destNamespacePrefix) throws FileNotFoundException {
    	
    	log.info("unmarshaling schema file '" 
    			+ schemaFile.getName() + "'");
        Schema schema = unmarshalSchema(new FileInputStream(
        		schemaFile));
  	    
  	    String namespaceURI = destNamespaceURI;
  	    if (namespaceURI == null)
  	    	namespaceURI = schema.getTargetNamespace();
  	    String namespacePrefix = destNamespacePrefix;
  	    if (namespacePrefix == null)
  	    	namespacePrefix = "tns";
  	  	SchemaProvisioningModelAssembler stagingAssembler = 
  		    new SchemaProvisioningModelAssembler(schema, 
  		    		namespaceURI, namespacePrefix);	    
  	    
  	    Model stagingModel = stagingAssembler.getModel();
	    if (log.isDebugEnabled()) {
	    	String schemaName = schema.getId();
	    	if (schemaName == null || schemaName.trim().length() == 0)
	    		schemaName = schemaFile.getName();
		    writeSchemaStagingModel(stagingModel,  
		       ".", XSDTool.class.getSimpleName()
	    	   + "-" + schemaName + "-model.xml");
	    }
	    ModelAdapter helper = 
			   new ModelAdapter(stagingModel);
		   
	    UMLModelAssembler assembler = new UMLModelAssembler(stagingModel, 
	    		namespaceURI, namespacePrefix);
        Document document = assembler.getDocument();
        
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
       
     private static void writeSchemaStagingModel(Model stagingModel, 
    		String location, String fileName) {
	   try {
		    BindingValidationEventHandler debugHandler = new BindingValidationEventHandler() {
				public int getErrorCount() {
					return 0;
				}
				public boolean handleEvent(ValidationEvent ve) {
			        ValidationEventLocator vel = ve.getLocator();
			        
			        String message = "Line:Col:Offset[" + vel.getLineNumber() + ":" + vel.getColumnNumber() + ":" 
			            + String.valueOf(vel.getOffset())
			            + "] - " + ve.getMessage();
			        
			        switch (ve.getSeverity()) {
			        default:
			            //log.debug(message);
			        }
			        return true;
				}			    	
		    };
		    ProvisioningModelDataBinding binding = 
			   new ProvisioningModelDataBinding(debugHandler);
		    String xml = binding.marshal(stagingModel);
		    binding.validate(xml);
		    
		    File provDebugFile = null;
		    if (location != null)
		    	provDebugFile = new File(location, 
		    			fileName);
		    else
		    	provDebugFile = File.createTempFile(fileName, "");
		    FileOutputStream provDebugos = new FileOutputStream(provDebugFile);
			log.debug("Writing provisioning model to: " + provDebugFile.getAbsolutePath());
		    binding.marshal(stagingModel, provDebugos);
		} catch (JAXBException e) {
			log.debug(e.getMessage(), e);
		} catch (SAXException e) {
			log.debug(e.getMessage(), e);
		} catch (IOException e) {
			log.debug(e.getMessage(), e);
		}

    }
        
    private static Schema unmarshalSchema(InputStream stream) {
        try {
            SchemaDataBinding binding = new SchemaDataBinding(
                    new DefaultValidationEventHandler());
            return (Schema)binding.unmarshal(stream);
                        
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }
    
    private static String getUsage() {
    	return "usage: -action sourceFile destFile destFileType destNamespaceURI destNamespacePrefix  [last-execution-time]";
    }
     
    private static void printUsage() {
        log.info(getUsage());
    }
}
