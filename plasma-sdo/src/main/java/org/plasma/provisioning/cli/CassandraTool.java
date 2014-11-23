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
import org.plasma.text.ddl.CassandraFactory;
import org.plasma.text.ddl.DDLFactory;
import org.plasma.text.ddl.DDLModelAssembler;
import org.plasma.text.ddl.DDLModelDataBinding;
import org.plasma.text.ddl.DDLOperation;
import org.plasma.text.ddl.DDLStreamAssembler;
import org.xml.sax.SAXException;

/**
 * The Cassandra NoSQL Database (CQL) Tool is used to provision
 * various artifacts useful for management and migration
 * to and from Cassandra NoSQL Database.  
 * <p></p> 
 * <b>Usage:</b> java org.plasma.provisioning.cli.CassandraTool  
 * [command &lt;create | drop | truncate&gt;]  
 * [platform &lt;papyrus | magicdraw, ...&gt;] 
 * [dest-file] [namespace1, namespace2, ...]  
 * [schema1, schema2, ...]<b>*</b>
 * <p></p> 
 * <b>Where:</b> 
 * <li><b>-command</b> is one of [create | drop | truncate]. The <i>create</i>, <i>drop</i> and 
 * <i>truncate</i> commands generate a complete DDL script representing the configured
 * set of UML artifacts. For these commands the <i>platform</i>, <i>namespace</i> and <i>schema</i>
 * arguments are not applicable as the physical schema names and namespace URI
 * associations are expected to be
 * part of the configured PlasmaSDO UML Profile annotated UML model artifact(s).
 * <li><b>dialect</b> is one of [oracle | mysql, ...] and the specific database product version is determined at runtime</li>
 * <li><b>platform</b> is one of [papyrus | magicdraw, ...]</li>
 * <li><b>dest-file</b> is the file name for the target artifact</li> 
 * <li><b>namespace1, namespace2, ...</b> is the comma separated set of namespace URIs used to annotate the UML package(s). If more than one 
 * schema is used, each schema name is used as a suffix. If no namespace-URI is present
 * a namespace URI based on the destination file name is constructed.</li> 
 * <li><b>schema1, schema2, ...</b> is a set of source RDB schemas separated by commas</li> 
 */
public class CassandraTool extends ProvisioningTool {
    
    private static Log log =LogFactory.getLog(
            CassandraTool.class); 
      
    /**
     * Command line entry point. 
     * <p></p>
	 * <b>Usage:</b> java org.plasma.provisioning.cli.CassandraTool  
	 * [command &lt;create | drop | truncate&gt;]  
	 * [dialect &lt;oracle | mysql, ...&gt;] 
	 * [platform &lt;papyrus | magicdraw, ...&gt;] 
	 * [dest-file] [namespace1, namespace2, ...]  
	 * [schema1, schema2, ...]<b>*</b>
	 * <p></p> 
	 * <b>Where:</b> 
	 * <li><b>-command</b> is one of [create | drop | truncate]. The <i>create</i>, <i>drop</i> and 
	 * <i>truncate</i> commands generate a complete DDL script representing the configured
	 * set of UML artifacts. For these commands the <i>platform</i>, <i>namespace</i> and <i>schema</i>
	 * arguments are not applicable as the physical schema names and namespace URI
	 * associations are expected to be
	 * part of the configured PlasmaSDO UML Profile annotated UML model artifact(s).
	 * <li><b>platform</b> is one of [papyrus | magicdraw, ...]</li>
	 * <li><b>dest-file</b> is the file name for the target artifact</li> 
	 * <li><b>namespace1, namespace2, ...</b> is the comma separated set of namespace URIs used to annotate the UML package(s). If more than one 
	 * schema is used, each schema name is used as a suffix. If no namespace-URI is present
	 * a namespace URI based on the destination file name is constructed.</li> 
	 * <li><b>schema1, schema2, ...</b> is a set of source RDB schemas separated by commas</li> 
     */
    public static void main(String[] args) throws JAXBException, SAXException, IOException {
        if (args.length < 1) {
            printUsage();
            return;
        }
        if (!args[0].startsWith("-")) {
            printUsage();
            return;
        }
        CassandraToolAction command = null;
    	try {
    		String commandArg = args[0];
    		if (commandArg.startsWith("-"))
    			commandArg = commandArg.substring(1);
    		command = CassandraToolAction.valueOf(commandArg);
    	}
    	catch (IllegalArgumentException e) {
    		throw new IllegalArgumentException("'" + args[0] + "' - expected one of ["
    				+ CassandraToolAction.asString() + "]");
    	}
        
        File dest = new File(args[1]);
        if (!dest.getParentFile().exists())
        	dest.getParentFile().mkdirs();
                
        switch (command) {
        case create:
        case drop:
        case truncate:
        	//FIXME: don't assume this command maps to operation
        	DDLOperation operation = DDLOperation.valueOf(command.name());
        	
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
       	
        	DDLFactory factory = new CassandraFactory();
        	
        	FileOutputStream stream = new FileOutputStream(dest);
        	DDLStreamAssembler ddlAssembler = new DDLStreamAssembler(
        		modelAssembler.getSchemas(),
        		factory, 
        		operation,
        		stream);
        	ddlAssembler.start();
        	stream.flush();
            break;        	
        default:
            throw new RDBException("unknown command '"
                    + command.toString() + "'");
        } 
       
    }
    
    private static void printUsage() {
    	log.info("Usage: java org.plasma.provisioning.cli.CassandraTool "
    		+ "[-command <create | drop | truncate>] "
    		+ "[dest-file] [dest-namespace-URIs]"
    		+ "[schema-names]* ");
    }
}
