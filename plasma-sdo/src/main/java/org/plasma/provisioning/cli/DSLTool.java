package org.plasma.provisioning.cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.ProvisioningModelAssembler;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.text.ddl.DDLModelDataBinding;
import org.plasma.text.lang3gl.DefaultLang3GLContext;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLDialect;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.java.DSLAssembler;
import org.plasma.text.lang3gl.java.DSLFactory;
import org.xml.sax.SAXException;

public class DSLTool extends ProvisioningTool {
    
    private static Log log =LogFactory.getLog(
            DSLTool.class); 

        
    public static void main(String[] args) throws JAXBException, SAXException, IOException {
        if (args.length < 2) {
            printUsage();
            return;
        }
        if (!args[0].startsWith("-")) {
            printUsage();
            return;
        }
        DSLToolAction command = null;
    	try {
    		command = DSLToolAction.valueOf(
                    args[0].substring(1));
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < DSLToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(DSLToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + args[0].substring(1) + "' - expected one of ["
    				+ buf.toString() + "]");
    	}
        
        switch (command) {
        case create:
        	Lang3GLOperation operation = Lang3GLOperation.valueOf(command.name());

        	Lang3GLDialect dialect = null;
        	try {
        		dialect = Lang3GLDialect.valueOf(args[1]);
        	}
        	catch (IllegalArgumentException e) {
        		StringBuilder buf = new StringBuilder();
        		for (int i = 0; i < Lang3GLDialect.values().length; i++) {
        			if (i > 0)
        				buf.append(", ");
        			buf.append(Lang3GLDialect.values()[i].name());
        		}
        			
        		throw new IllegalArgumentException("'" + args[1] + "' - expected one of ["
        				+ buf.toString() + "]");
        	}
        	
            File destDir = new File(args[2]);
            if (!destDir.exists()) {
                log.debug("given destination dir '"
                        + destDir.getName() + "' does not exist");
                if (!destDir.mkdirs())
            		throw new IllegalArgumentException("given destination dir '"
                            + destDir.getName() + "' could not be created");                	
            }
            log.debug("dest: " + destDir.getName());
    	    
            ProvisioningModelAssembler modelAssembler = new ProvisioningModelAssembler();
            // FIXME: pass this to factories
    		ModelAdapter validator = 
    				new ModelAdapter(modelAssembler.getModel());
            
            Lang3GLContext factoryContext = new DefaultLang3GLContext(modelAssembler.getModel());
            
            Lang3GLFactory factory = null;
        	switch (dialect) {
        	case java: factory = new DSLFactory(factoryContext);
        	    break;
        	default:
        		throw new ProvisioningException("unknown 3GL language dialect, '"
        				+ dialect.name() + "'");
        	}           
       	
        	DefaultStreamAssembler assembler = new DSLAssembler(
        		modelAssembler.getModel(),
        		factory, 
        		operation,
        		destDir);
        	assembler.start();
            log.info("generated "
            	+ assembler.getResultClassesCount()
            	+" classes to output to directory: " 
            	+ destDir.getAbsolutePath());
            
            break;
        default:
            throw new ProvisioningException("unknown command '"
                    + command.toString() + "'");
        } 
       
    }
    
    private static void printUsage() {
        log.info("usage: [command, dialect, dest-dir]");
    }
}
