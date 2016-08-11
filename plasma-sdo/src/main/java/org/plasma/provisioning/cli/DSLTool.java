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
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.AnnotationMetamodelAssembler;
import org.plasma.provisioning.MetamodelAssembler;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.provisioning.adapter.ProvisioningModel;
import org.plasma.text.lang3gl.DefaultLang3GLContext;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLContext;
import org.plasma.text.lang3gl.Lang3GLDialect;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;
import org.plasma.text.lang3gl.java.DSLAssembler;
import org.plasma.text.lang3gl.java.DSLFactory;
import org.xml.sax.SAXException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

/**
 * The DSL Tool is used to provision Query DSL code artifacts.  
 * <p></p> 
 * <b>Usage:</b> java <@link org.plasma.provisioning.cli.DSLTool>  
 * Option                    Description
 * ------                    -----------
 * --command <DSLToolAction> the primary action or command performed by this tool - one of [create] is expected
 * --dest [File]             the fully qualified tool output destination file or directory name
 * --help                    prints help on this tool
 * --lastExecution <Long>    a long integer representing the last time the tool was executed
 * --silent                  whether to log or print no messages at all (typically for testing)
 * --verbose                 whether to log or print detailed messages     *     
 */
public class DSLTool extends ProvisioningTool {
    
    private static Log log =LogFactory.getLog(
            DSLTool.class); 

    /**
     * Command line entry point. 
     * Option                    Description
     * ------                    -----------
     * --command <DSLToolAction> the primary action or command performed by this tool - one of [create] is expected
     * --dest [File]             the fully qualified tool output destination file or directory name
     * --help                    prints help on this tool
     * --lastExecution <Long>    a long integer representing the last time the tool was executed
     * --silent                  whether to log or print no messages at all (typically for testing)
     * --verbose                 whether to log or print detailed messages     *     
     * @param args
     * @throws JAXBException
     * @throws SAXException
     * @throws IOException
     */
    public static void main(String[] args) throws JAXBException, SAXException, IOException {
    	OptionParser parser = new OptionParser();    	  
    	OptionSpecBuilder verboseOpt = parser.accepts( ProvisioningToolOption.verbose.name(), ProvisioningToolOption.verbose.getDescription() );    	 
    	OptionSpecBuilder silentOpt = parser.accepts( ProvisioningToolOption.silent.name(), ProvisioningToolOption.silent.getDescription() );    	 
    	
    	OptionSpecBuilder helpOpt = parser.accepts( ProvisioningToolOption.help.name(), ProvisioningToolOption.help.getDescription() );    	 
    	OptionSpecBuilder commandOpt = parser.accepts( ProvisioningToolOption.command.name(), 
    			ProvisioningToolOption.command.getDescription() + " - one of [" + DSLToolAction.asString() + "] is expected");    	 
    	commandOpt.withRequiredArg().ofType( DSLToolAction.class );    	 
    	
    	OptionSpec<Long> lastExecutionOpt = parser.accepts( ProvisioningToolOption.lastExecution.name(),
    			ProvisioningToolOption.lastExecution.getDescription() ).withRequiredArg().ofType( Long.class );
    	OptionSpec<File> destOpt = parser.accepts( ProvisioningToolOption.dest.name(), 
    			ProvisioningToolOption.dest.getDescription()).withOptionalArg().ofType( File.class );

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
    	DSLToolAction command = (DSLToolAction)options.valueOf(ProvisioningToolOption.command.name());
    	
    	Lang3GLDialect dialect = Lang3GLDialect.java;

        File destDir = new File("./target/");
        if (options.has(destOpt)) {
        	destDir = destOpt.value(options);
    	}
        if (!destDir.exists()) {
        	if (!options.has(silentOpt))
                log.debug("given destination dir '"
                    + destDir.getName() + "' does not exist");
            if (!destDir.mkdirs())
        		throw new IllegalArgumentException("given destination dir '"
                        + destDir.getName() + "' could not be created");                	
        }
    	
        switch (command) {
        case create:
        	Lang3GLOperation operation = Lang3GLOperation.valueOf(command.name());

        	long lastExecution = 0L;
        	
        	if (options.has(lastExecutionOpt)) {
        		lastExecution = lastExecutionOpt.value(options).longValue();
            }
    	    
            if (!regenerate(lastExecution)) {
            	if (!options.has(silentOpt))
                    log.info("skipping DSL creation - no stale artifacts detected");	
                return;
            }            	
            AnnotationMetamodelAssembler annotationAssembler = new AnnotationMetamodelAssembler();
            if (annotationAssembler.hasAnnotatedClasses())
            	loadAnnotationModel(annotationAssembler, destDir,
            			options, silentOpt);
            
            MetamodelAssembler modelAssembler = new MetamodelAssembler();
            ProvisioningModel validator = 
    				new ModelAdapter(modelAssembler.getModel());
            
            Lang3GLContext factoryContext = new DefaultLang3GLContext(validator);
            
            Lang3GLFactory factory = null;
        	switch (dialect) {
        	case java: factory = new DSLFactory(factoryContext);
        	    break;
        	default:
        		throw new ProvisioningException("unknown 3GL language dialect, '"
        				+ dialect.name() + "'");
        	}           
       	
        	DefaultStreamAssembler assembler = new DSLAssembler(
        		validator,
        		factory, 
        		operation,
        		destDir);
        	assembler.start();
        	if (!options.has(silentOpt))
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
}
