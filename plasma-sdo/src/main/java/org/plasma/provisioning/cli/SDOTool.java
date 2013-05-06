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
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.ProvisioningModelAssembler;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.text.lang3gl.DefaultLang3GLContext;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLDialect;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;
import org.plasma.text.lang3gl.java.SDOAssembler;
import org.plasma.text.lang3gl.java.SDOFactory;
import org.xml.sax.SAXException;

public class SDOTool extends ProvisioningTool {
    
    private static Log log =LogFactory.getLog(
            SDOTool.class); 

        
    public static void main(String[] args) throws JAXBException, SAXException, IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException(getUsage());
        }
        if (!args[0].startsWith("-")) {
            throw new IllegalArgumentException(getUsage());
        }
        SDOToolAction command = null;
    	try {
    		command = SDOToolAction.valueOf(
                    args[0].substring(1));
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < SDOToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(SDOToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + args[0].substring(1) + "' - expected one of ["
    				+ buf.toString() + "]");
    	}
    	
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
        
        switch (command) {
        case export:
            File destFile = new File(args[2]);
            if (destFile.getParentFile() == null || !destFile.getParentFile().exists()) {
                log.info("given destination directory '"
                        + destFile.getParentFile().getAbsolutePath() + "' does not exist, creating");
                if (!destFile.getParentFile().mkdirs())
            		throw new IllegalArgumentException("given destination directory '"
                            + destFile.getParentFile() + "' could not be created");                	
            }
            if (destFile.isDirectory())
            	destFile = new File(destFile, "technical-model.xml");
            log.info("dest: " + destFile.getAbsoluteFile());
            ProvisioningModelAssembler modelAssembler = new ProvisioningModelAssembler();
            ProvisioningModelDataBinding binding = new ProvisioningModelDataBinding(
        			new DefaultValidationEventHandler());
        	String xml = binding.marshal(modelAssembler.getModel());
    		FileOutputStream stream = new FileOutputStream(destFile);
    		stream.write(xml.getBytes());
    		stream.flush();
    		stream.close();
    		validateStagingModel(destFile);
    		log.info("wrote merged model file to: " 
    				+ destFile.getAbsoluteFile());
            break;        	
        case create:
        	Lang3GLOperation operation = Lang3GLOperation.valueOf(command.name());
        	
            File destDir = new File(args[2]);
            if (!destDir.exists()) {
                log.debug("given destination dir '"
                        + destDir.getName() + "' does not exist");
                if (!destDir.mkdirs())
            		throw new IllegalArgumentException("given destination dir '"
                            + destDir.getName() + "' could not be created");                	
            }
            log.debug("dest: " + destDir.getName());
            
        	long lastExecution = 0L;
            if (args.length >= 4) {
            	try {
            		lastExecution = Long.valueOf(args[3]).longValue();
            	}
            	catch (NumberFormatException e) {
            		throw new IllegalArgumentException(getUsage());                	
            	}
            }
    	    
            if (!regenerate(lastExecution)) {
                log.info("skipping SDO creation - no stale artifacts detected");	
                return;
            }            	
            
            modelAssembler = new ProvisioningModelAssembler();
            binding = new ProvisioningModelDataBinding(
        			new DefaultValidationEventHandler());
        	xml = binding.marshal(modelAssembler.getModel());
        	if (log.isDebugEnabled()) {
        		File outFile = new File(destDir, "technical-model.xml");
        		stream = new FileOutputStream(outFile);
        		stream.write(xml.getBytes());
        		stream.flush();
        		stream.close();
        		log.debug("wrote merged model file to: " 
        				+ outFile.getAbsoluteFile());
        		//log.debug(xml);
        	}
        	
            // FIXME: pass this to factories
    		ModelAdapter validator = 
    				new ModelAdapter(modelAssembler.getModel());
            Lang3GLContext factoryContext = new DefaultLang3GLContext(modelAssembler.getModel());
            
            Lang3GLFactory factory = null;
        	switch (dialect) {
        	case java: factory = new SDOFactory(factoryContext);
        	    break;
        	default:
        		throw new ProvisioningException("unknown 3GL language dialect, '"
        				+ dialect.name() + "'");
        	}           
       	
        	DefaultStreamAssembler assembler = new SDOAssembler(
        		modelAssembler.getModel(),
        		factory, 
        		operation,
        		destDir);
        	assembler.start();
            log.info("generated "
                	+ assembler.getResultInterfacesCount()
                	+" interfaces to output to directory: " 
                	+ destDir.getAbsolutePath());
            log.info("generated "
                	+ assembler.getResultClassesCount()
                	+" classes to output to directory: " 
                	+ destDir.getAbsolutePath());
            log.info("generated "
                	+ assembler.getResultEnumerationsCount()
                	+" enumerations to output to directory: " 
                	+ destDir.getAbsolutePath());
            
            break;
        default:
            throw new ProvisioningException("unknown command '"
                    + command.toString() + "'");
        }        
    }
    
    private static String getUsage() {
    	return "usage: -command dialect dest-dir [last-execution-time]";
    }
    
    private static void printUsage() {
        log.info(getUsage());
    }
}
