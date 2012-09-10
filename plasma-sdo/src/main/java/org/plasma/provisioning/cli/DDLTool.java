package org.plasma.provisioning.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.text.ddl.DDLDialect;
import org.plasma.text.ddl.DDLException;
import org.plasma.text.ddl.DDLFactory;
import org.plasma.text.ddl.DDLModelAssembler;
import org.plasma.text.ddl.DDLModelDataBinding;
import org.plasma.text.ddl.DDLOperation;
import org.plasma.text.ddl.DDLStreamAssembler;
import org.plasma.text.ddl.MySQLFactory;
import org.plasma.text.ddl.OracleFactory;
import org.xml.sax.SAXException;

public class DDLTool extends ProvisioningTool {
    
    private static Log log =LogFactory.getLog(
            DDLTool.class); 

    /*
    public static void provision(File src, File dest, 
            URL styleSheet, Properties params) 
        throws TransformerConfigurationException, 
            IOException, TransformerException {       
    }
    */
        
    public static void main(String[] args) throws JAXBException, SAXException, IOException {
        if (args.length != 3) {
            printUsage();
            return;
        }
        if (!args[0].startsWith("-")) {
            printUsage();
            return;
        }
        DDLToolAction command = null;
    	try {
    		command = DDLToolAction.valueOf(
                    args[0].substring(1));
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < DDLToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(DDLToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + args[0].substring(1) + "' - expected one of ["
    				+ buf.toString() + "]");
    	}
        
        switch (command) {
        case create:
        case drop:
        case truncate:
        	DDLOperation operation = DDLOperation.valueOf(command.name());

        	DDLDialect dialect = null;
        	try {
        		dialect = DDLDialect.valueOf(args[1]);
        	}
        	catch (IllegalArgumentException e) {
        		StringBuilder buf = new StringBuilder();
        		for (int i = 0; i < DDLDialect.values().length; i++) {
        			if (i > 0)
        				buf.append(", ");
        			buf.append(DDLDialect.values()[i].name());
        		}
        			
        		throw new IllegalArgumentException("'" + args[1] + "' - expected one of ["
        				+ buf.toString() + "]");
        	}
        	
            File dest = new File(args[2]);
            if (!dest.getParentFile().exists())
                throw new RuntimeException("given destination dir '"
                        + dest.getParentFile().getName() + "' does not exist");
            
        	DDLModelAssembler modelAssembler = new DDLModelAssembler();
        	DDLModelDataBinding binding = new DDLModelDataBinding(
        			new DefaultValidationEventHandler());
            if (log.isDebugEnabled()) {
            	File file = new File(dest.getParentFile(), "ddl-model.xml");
            	FileOutputStream fos = new FileOutputStream(file);
            	try {
            	    binding.marshal(modelAssembler.getSchemas(), fos);
            	    fos.flush();
            	}
            	finally {
            	    fos.close();
            	}
            }
       	
        	DDLFactory factory = null;
        	switch (dialect) {
        	case oracle: factory = new OracleFactory();
        	    break;
        	case mysql: factory = new MySQLFactory();
        	    break;
        	default:
        		throw new DDLException("unknown DDL dialect, '"
        				+ dialect.name() + "'");
        	}
        	
        	FileOutputStream stream = new FileOutputStream(dest);
        	DDLStreamAssembler assembler = new DDLStreamAssembler(
        		modelAssembler.getSchemas(),
        		factory, 
        		operation,
        		stream);
        	assembler.start();
        	stream.flush();
            
            break;
        default:
            throw new RuntimeException("unknown command '"
                    + command.toString() + "'");
        } 
       
    }
    
    private static void printUsage() {
        log.info("usage: [command, dialect, dest-file]");
    }
}
