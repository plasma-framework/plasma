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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import joptsimple.BuiltinHelpFormatter;
import joptsimple.HelpFormatter;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.rdb.RDBConstants;
import org.plasma.text.ddl.DDLFactory;
import org.plasma.text.ddl.DDLModelAssembler;
import org.plasma.text.ddl.DDLModelDataBinding;
import org.plasma.text.ddl.DDLOperation;
import org.plasma.text.ddl.DDLStreamAssembler;
import org.plasma.text.ddl.MySQLFactory;
import org.plasma.text.ddl.OracleFactory;
import org.xml.sax.SAXException;

/**
 * The Relational Database (RDB) Tool is used to provision
 * various artifacts useful for management and migration
 * to and from Relational Database.  
 * <p></p> 
 * <b>Usage:</b> java <@link org.plasma.provisioning.cli.RDBTool>  
 * <p></p>
 * 
 * Option                    Description
 * ------                    -----------
 * --command <@link RDBToolAction> the primary action or command performed by this tool - one of [create, drop, truncate] is expected
 * --dest [File]             the fully qualified tool output destination file or directory name
 * --dialect <@link RDBDialect>    the vocabulary or usage which is characteristic for this context - one of [oracle, mysql] is expected
 * --help                    prints help on this tool
 * --namespaces              a comma separated list of namespace URIs
 * --silent                  whether to log or print no messages at all (typically for testing)
 * --verbose                 whether to log or print detailed messages     
 */
public class RDBTool extends ProvisioningTool implements RDBConstants {
    
    private static Log log =LogFactory.getLog(
            RDBTool.class); 
      
    /**
     * Command line entry point. 
	 * <b>Usage:</b> java <@link org.plasma.provisioning.cli.RDBTool>  
     * <p></p>
     * Option                    Description
     * ------                    -----------
     * --command <@link RDBToolAction> the primary action or command performed by this tool - one of [create, drop, truncate] is expected
     * --dest [File]             the fully qualified tool output destination file or directory name
     * --dialect <@link RDBDialect>    the vocabulary or usage which is characteristic for this context - one of [oracle, mysql] is expected
     * --help                    prints help on this tool
     * --namespaces              a comma separated list of namespace URIs
     * --silent                  whether to log or print no messages at all (typically for testing)
     * --verbose                 whether to log or print detailed messages     
     */
    public static void main(String[] args) throws JAXBException, SAXException, IOException {
            	    	
    	OptionParser parser = new OptionParser();    	  
    	OptionSpecBuilder verboseOpt = parser.accepts( ProvisioningToolOption.verbose.name(), ProvisioningToolOption.verbose.getDescription() );    	 
    	OptionSpecBuilder silentOpt = parser.accepts( ProvisioningToolOption.silent.name(), ProvisioningToolOption.silent.getDescription() );    	 
    	
    	OptionSpecBuilder helpOpt = parser.accepts( ProvisioningToolOption.help.name(), ProvisioningToolOption.help.getDescription() );    	 
    	OptionSpecBuilder commandOpt = parser.accepts( ProvisioningToolOption.command.name(), 
    			ProvisioningToolOption.command.getDescription() + " - one of [" + RDBToolAction.asString() + "] is expected");    	 
    	commandOpt.withRequiredArg().ofType( RDBToolAction.class );    	 
    	OptionSpecBuilder dialectOpt = parser.accepts( ProvisioningToolOption.dialect.name(), 
    			ProvisioningToolOption.dialect.getDescription()  + " - one of [" + RDBDialect.asString() + "] is expected");    	 
    	dialectOpt.withRequiredArg().ofType( RDBDialect.class ); 
    	OptionSpec<String> namespacesOpt = parser.accepts( ProvisioningToolOption.namespaces.name(), 
    			ProvisioningToolOption.namespaces.getDescription()).withOptionalArg().ofType( String.class );
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
        RDBToolAction command = (RDBToolAction)options.valueOf(ProvisioningToolOption.command.name());

        if (!options.has(ProvisioningToolOption.dialect.name())) {
    		if (!options.has(silentOpt))
    		    printUsage(parser, log);
    		throw new IllegalArgumentException("expected option '" + ProvisioningToolOption.dialect.name() + "'");
    	}
    	RDBDialect dialect = (RDBDialect)options.valueOf(ProvisioningToolOption.dialect.name());

    	File dest = new File("./target/" + RDBTool.class.getSimpleName() + ".out");
        if (options.has(destOpt)) {
        	dest = destOpt.value(options);
    	}
        if (!dest.getParentFile().exists())
        	dest.getParentFile().mkdirs();
        
        String[] namespaces = null;
        if (options.has(namespacesOpt)) {
        	namespaces = namespacesOpt.value(options).split(",");
        }        
        
        switch (command) {
        case create:
        case drop:
        case truncate:
        	//FIXME: don't assume this command maps to operation
        	DDLOperation operation = DDLOperation.valueOf(command.name());
        	
        	DDLModelAssembler modelAssembler = null;
        	if (namespaces != null)
        		modelAssembler = new DDLModelAssembler(namespaces);
        	else
        		modelAssembler = new DDLModelAssembler();
        	DDLModelDataBinding binding = new DDLModelDataBinding(
        			new DefaultValidationEventHandler());
        	File file = new File(dest.getParentFile(), "ddl-model.xml");
        	FileOutputStream fos = new FileOutputStream(file);
        	try {
        	    binding.marshal(modelAssembler.getSchemas(), fos);
        	    fos.flush();
        	    if (!options.has(silentOpt))
        	        log.info("marshalled DDL model XML to " + file.getAbsolutePath());
        	}
        	finally {
        	    fos.close();
        	}
       	
        	DDLFactory factory = null;
        	switch (dialect) {
        	case oracle: factory = new OracleFactory();
        	    break;
        	case mysql: factory = new MySQLFactory();
        	    break;
        	default:
        		throw new ProvisioningException("unknown dialect, '"
        				+ dialect.name() + "'");
        	}
        	
        	FileOutputStream stream = new FileOutputStream(dest);
        	DDLStreamAssembler ddlAssembler = new DDLStreamAssembler(
        		modelAssembler.getSchemas(),
        		factory, 
        		operation,
        		stream);
        	ddlAssembler.start();
        	stream.flush();
        	if (!options.has(silentOpt))
        	    log.info("wrote DDL model to " + dest.getAbsolutePath());
            break;        	
        default:
            throw new ProvisioningException("unknown command '"
                    + command.toString() + "'");
        }        
    }
    
}


