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
import org.plasma.text.ddl.DDLException;
import org.plasma.text.ddl.DDLFactory;
import org.plasma.text.ddl.DDLModelAssembler;
import org.plasma.text.ddl.DDLModelDataBinding;
import org.plasma.text.ddl.DDLOperation;
import org.plasma.text.ddl.DDLStreamAssembler;
import org.plasma.text.ddl.MySQLFactory;
import org.plasma.text.ddl.OracleFactory;
import org.xml.sax.SAXException;

@Deprecated
public class DDLTool extends ProvisioningTool {
    
    private static Log log =LogFactory.getLog(
            DDLTool.class); 

        
    public static void main(String[] args) throws JAXBException, SAXException, IOException {
        if (args.length != 3) {
            printUsage();
            return;
        }
        if (!args[0].startsWith("-")) {
            printUsage();
            return;
        }
        if (!args[1].startsWith("-")) {
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
        	//FIXME: don't assume this command maps to operation
        	DDLOperation operation = DDLOperation.valueOf(command.name());

        	RDBDialect dialect = null;
        	try {
        		String dialectArg = args[1];
        		if (dialectArg.startsWith("-"))
        			dialectArg = dialectArg.substring(1);
        		dialect = RDBDialect.valueOf(dialectArg);
        	}
        	catch (IllegalArgumentException e) {
        		StringBuilder buf = new StringBuilder();
        		for (int i = 0; i < RDBDialect.values().length; i++) {
        			if (i > 0)
        				buf.append(", ");
        			buf.append(RDBDialect.values()[i].name());
        		}
        			
        		throw new IllegalArgumentException("'" + args[1] + "' - expected one of ["
        				+ buf.toString() + "]");
        	}
        	
            File dest = new File(args[2]);
            if (!dest.getParentFile().exists())
            	dest.getParentFile().mkdirs();
            
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
