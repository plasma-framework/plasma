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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.config.NamespaceProvisioning;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.SchemaConverter;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.provisioning.rdb.OracleConverter;
import org.plasma.provisioning.rdb.RDBConstants;
import org.plasma.provisioning.rdb.oracle.sys.Version;
import org.plasma.sdo.repository.PlasmaRepository;
import org.plasma.text.ddl.DDLFactory;
import org.plasma.text.ddl.DDLModelAssembler;
import org.plasma.text.ddl.DDLModelDataBinding;
import org.plasma.text.ddl.DDLOperation;
import org.plasma.text.ddl.DDLStreamAssembler;
import org.plasma.text.ddl.MySQLFactory;
import org.plasma.text.ddl.OracleFactory;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

/**
 * The Relational Database (RDB) Tool is used to provision
 * various artifacts useful for management and migration
 * to and from Relational Database.  
 * <p></p> 
 * <b>Usage:</b> java org.plasma.provisioning.cli.RDBTool  
 * [-command &lt;create | drop | truncate | reverse&gt;]  
 * [dialect &lt;oracle | mysql, ...&gt;] [dest-file] [dest-namespace-URI]  
 * [schema1, schema2, ...]<b>*</b>
 * <p></p> 
 * <b>Where:</b> 
 * <li><b>-command</b> is one of [create | drop | truncate | reverse]. The <i>create</i>, <i>drop</i> and 
 * <i>truncate</i> commands generate a complete DDL script representing the configured
 * set of UML artifacts. 
 * The <i>reverse</i> command interrogates one or more database schemas, using
 * vendor specific system tables, and generates a UML model which captures the
 * the physical attributes of the database including all tables, 
 * columns, constraints, sequences and comments. Check constraints where the
 * search condition involves limiting the associated property to a list of values
 * are captured and used to produce annotated UML enumerations which 
 * are automatically linked as UML enumeration constraints to the 
 * source property. Note the <i>reverse</i> command requires a Plasma JDBC service
 * to be configured within the Plasma configuration, where the user
 * specified has read privileges for system schema(s) for the respective
 * database vendor</li>
 * <li><b>dialect</b> is one of [oracle | mysql, ...] and the specific database product version is determined at runtime</li>
 * <li><b>dest-file</b> is the file name for the target artifact</li> 
 * <li><b>dest-namespace-URI</b> is the namespace URI used to annotate the UML package(s). If more than one 
 * schema is used, each schema name is used as a suffix. If no dest-namespace-URI is present
 * a nemsapace URI based on the destination file name is constructed.</li> 
 * <li><b>schema1, schema2, ...</b> is a set of source RDB schemas separated by commas. This argument
 * <i>reverse</i> command as the physical schema names and namespace URI
 * associations are expected to be
 * part of the configured PlasmaSDO UML Profile annotated UML model artifact(s).</li> 
 */
public class RDBTool extends ProvisioningTool {
    
    private static Log log =LogFactory.getLog(
            RDBTool.class); 
      
    /**
     * Command line entry point. 
     * <p></p>
	 * <b>Usage:</b> java org.plasma.provisioning.cli.RDBTool  
	 * [-command &lt;create | drop | truncate | reverse&gt;]  
	 * [dialect &lt;oracle | mysql, ...&gt;] [dest-file] [dest-namespace-URI]  
	 * [schema1, schema2, ...]<b>*</b>
	 * <p></p> 
	 * <b>Where:</b> 
	 * <li><b>-command</b> is one of [create | drop | truncate | reverse]. The <i>create</i>, <i>drop</i> and 
	 * <i>truncate</i> commands generate a complete DDL script representing the configured
	 * set of UML artifacts. For these commands the <i>schema-name, namespace-uri</i>
	 * pairs are not applicable as the physical schema names and namespace URI
	 * associations are expected to be
	 * part of the configured PlasmaSDO UML Profile annotated UML model artifact(s).
	 * The <i>reverse</i> command interrogates one or more database schemas, using
	 * vendor specific system tables, and generates a UML model which captures the
	 * the physical attributes of the database including all tables, 
	 * columns, constraints, sequences and comments. Check constraints where the
	 * search condition involves limiting the associated property to a list of values
	 * are captured and used to produce annotated UML enumerations which 
	 * are automatically linked as UML enumeration constraints to the 
	 * source property.</li>
	 * <li><b>dialect</b> is one of [oracle | mysql, ...] and the specific database product version is determined at runtime</li>
	 * <li><b>dest-file</b> is the file name for the target artifact</li> 
	 * <li><b>dest-namespace-URI</b> is the namespace URI used to annotate the UML package(s). If more than one 
	 * schema is used, each schema name is used as a suffix. If no dest-namespace-URI is present
	 * a nemsapace URI based on the destination file name is constructed.</li> 
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
        RDBToolAction command = null;
    	try {
    		command = RDBToolAction.valueOf(
                    args[0].substring(1));
    	}
    	catch (IllegalArgumentException e) {
    		StringBuilder buf = new StringBuilder();
    		for (int i = 0; i < RDBToolAction.values().length; i++) {
    			if (i > 0)
    				buf.append(", ");
    			buf.append(RDBToolAction.values()[i].name());
    		}
    			
    		throw new IllegalArgumentException("'" + args[0].substring(1) + "' - expected one of ["
    				+ buf.toString() + "]");
    	}
        switch (command) {
        case create:
        case drop:
        case truncate:
            if (args.length != 3) {
                printUsage();
                return;
            }
        	break;
        case reverse:
            if (args.length != 4 && args.length != 5) {
                printUsage();
                return;
            }
        	break;
        }
        
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
       	
        	DDLFactory factory = null;
        	switch (dialect) {
        	case oracle: factory = new OracleFactory();
        	    break;
        	case mysql: factory = new MySQLFactory();
        	    break;
        	default:
        		throw new RDBException("unknown dialect, '"
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
            
            break;
        case reverse:
        	String destNamespaceURI = "http://" + dest.getName();
        	String[] schemaNames = null;
        	if (args.length == 5) {
        		destNamespaceURI = args[3];
        	    schemaNames = args[4].split(",");
        	}
        	else
        		schemaNames = args[3].split(",");
        	
        	PlasmaRepository.getInstance(); // just force an init
        	
        	//FIXME: determine the vendor and version
        	
        	NamespaceProvisioning provisioning = new NamespaceProvisioning();
        	provisioning.setPackageName(Version.class.getPackage().getName());
        	
            PlasmaConfig.getInstance().addDynamicSDONamespace(
            		RDBConstants.ARTIFACT_NAMESPACE_ORACLE_SYS, 
            		RDBConstants.ARTIFACT_RESOURCE,
            		provisioning);

        	InputStream stream2 = RDBTool.class.getClassLoader().getResourceAsStream(
        			RDBConstants.ARTIFACT_RESOURCE);
        	
    	    if (log.isDebugEnabled())
    	    	log.info("loading UML/XMI model");
            Fuml.load(new ResourceArtifact(
            	RDBConstants.ARTIFACT_RESOURCE, 
            	RDBConstants.ARTIFACT_RESOURCE, 
                stream2));              	
        	
            Model model = null;
        	switch (dialect) {
        	case oracle: 
        		SchemaConverter converter = 
        		    new OracleConverter(schemaNames, destNamespaceURI);
        		model = converter.buildModel();            	
        	    break;
        	case mysql:  
        	default:
        		throw new RDBException("unknown dialect, '"
        				+ dialect.name() + "'");
        	}
        	if (log.isDebugEnabled()) {
        		ProvisioningModelDataBinding provBinding = new ProvisioningModelDataBinding(
            			new DefaultValidationEventHandler());
            	String xml = provBinding.marshal(model);
        		File outFile = new File(dest.getParentFile(), "technical-model.xml");
        		stream = new FileOutputStream(outFile);
        		stream.write(xml.getBytes());
        		stream.flush();
        		stream.close();
        		log.debug("wrote merged model file to: " 
        				+ outFile.getAbsoluteFile());
        	}
    	    ModelAdapter helper = 
    				   new ModelAdapter(model);
    			   
    		UMLModelAssembler umlAssembler = new UMLModelAssembler(model, 
    				destNamespaceURI, "tns");
    	    Document document = umlAssembler.getDocument();
    	        
	    	log.info("marshaling XMI model to file, '"
	    			+ dest.getName() + "'");
	        try {
				FileOutputStream os = new FileOutputStream(dest);
			    XMLOutputter outputter = new XMLOutputter();
			    outputter.output(document, os);
			} catch (FileNotFoundException e) {
	            throw new ProvisioningException(e);
			} catch (IOException e) {
	            throw new ProvisioningException(e);
			}
        	
            break;        	
        default:
            throw new RDBException("unknown command '"
                    + command.toString() + "'");
        } 
       
    }
    
    private static void printUsage() {
    	log.info("Usage: java org.plasma.provisioning.cli.RDBTool "
    		+ "[-command <create | drop | truncate | reverse>] "
    		+ "[dialect <oracle | mysql>] [dest-file] [dest-namespace-URI]"
    		+ "[schema-name]* ");
    }
}
