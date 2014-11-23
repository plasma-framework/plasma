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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ProvisioningException;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.provisioning.rdb.RDBConstants;
import org.plasma.xml.uml.MDModelAssembler;
import org.plasma.xml.uml.PapyrusModelAssembler;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

/**
 * The Unified Modeling Language (UML) Tool is used to provision
 * UML modeling artifacts from various sources.  
 * <p></p> 
 * <b>Usage:</b> java org.plasma.provisioning.cli.UMLTool  
 * [source &lt;rdb | xsd, ...&gt;] 
 * [dialect &lt;oracle | mysql, ...&gt;] 
 * [platform &lt;papyrus | magicdraw, ...&gt;] 
 * [dest-file] [namespace1, namespace2, ...]  
 * [schema1, schema2, ...] <b>*</b>
 * <p></p> 
 * <b>Where:</b> 
 * <li><b>-source</b> is one of [rdb | xsd, ...]. 
 * The <i>rdb</i> source interrogates one or more database schemas, using
 * vendor specific system tables, and generates a UML model for the given UML editing <i>platform</i> 
 * which captures the physical attributes of the database including all tables, 
 * columns, constraints, sequences and comments. Check constraints where the
 * search condition involves limiting the associated property to a list of values
 * are captured and used to produce annotated UML enumerations which 
 * are automatically linked as UML enumeration constraints to the 
 * source property.</li>
 * <li><b>dialect</b> is one of [oracle | mysql, ...] and the specific database product version is determined at runtime</li>
 * <li><b>platform</b> is one of [papyrus | magicdraw, ...]</li>
 * <li><b>dest-file</b> is the file name for the target artifact</li> 
 * <li><b>namespace1, namespace2, ...</b> is the comma separated set of namespace URIs used to annotate the UML package(s). If more than one 
 * schema is used, each schema name is used as a suffix. If no namespace-URI is present
 * a namespace URI based on the destination file name is constructed.</li> 
 * <li><b>schema1, schema2, ...</b> is a set of source RDB schemas separated by commas</li> 
 */
public class UMLTool extends ProvisioningTool implements RDBConstants {
    
    private static Log log =LogFactory.getLog(
            UMLTool.class); 
      
    /**
     * Command line entry point. 
     * <p></p>
	 * <b>Usage:</b> java org.plasma.provisioning.cli.UMLTool  
	 * [source &lt;rdb | xsd, ...&gt;] 
	 * [dialect &lt;oracle | mysql, ...&gt;] 
	 * [platform &lt;papyrus | magicdraw, ...&gt;] 
	 * [dest-file] [namespace1, namespace2, ...]  
	 * [schema1, schema2, ...] <b>*</b>
	 * <p></p> 
	 * <b>Where:</b> 
	 * <li><b>-source</b> is one of [rdb | xsd, ...]. 
	 * The <i>rdb</i> source interrogates one or more database schemas, using
	 * vendor specific system tables, and generates a UML model for the given UML editing <i>platform</i> 
	 * which captures the physical attributes of the database including all tables, 
	 * columns, constraints, sequences and comments. Check constraints where the
	 * search condition involves limiting the associated property to a list of values
	 * are captured and used to produce annotated UML enumerations which 
	 * are automatically linked as UML enumeration constraints to the 
	 * source property.</li>
	 * <li><b>dialect</b> is one of [oracle | mysql, ...] and the specific database product version is determined at runtime</li>
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
        UMLToolSource source = null;
    	try {
    		String sourceArg = args[0];
    		if (sourceArg.startsWith("-"))
    			sourceArg = sourceArg.substring(1);
    		source = UMLToolSource.valueOf(sourceArg);
    	}
    	catch (IllegalArgumentException e) {
    		throw new IllegalArgumentException("'" + args[0] + "' - expected one of ["
    				+ UMLToolSource.asString() + "]");
    	}    	
    	RDBDialect dialect = null;
    	
        switch (source) {
        case rdb:
        	try {
        		String dialectArg = args[1];
        		if (dialectArg.startsWith("-"))
        			dialectArg = dialectArg.substring(1);
        		dialect = RDBDialect.valueOf(dialectArg);
        	}
        	catch (IllegalArgumentException e) {
                printUsage();
        		throw new IllegalArgumentException("'" + args[1] + "' - expected one of ["
        				+ RDBDialect.asString() + "]");
        	}
            File dest = new File(args[2]);
            if (!dest.getParentFile().exists())
            	dest.getParentFile().mkdirs();
            break;
        case xsd:
        default:
            printUsage();
        	break;
        }
        
        	UMLDialect platform = UMLDialect.papyrus;
        	String[] schemaNames = null;
        	String[] namespaces = null;
        	if (args.length == 6) {
        		namespaces = args[4].split(",");
        	    schemaNames = args[5].split(",");
        	    if (namespaces.length != schemaNames.length)
            		throw new RDBException("expected 'schemaNames' and 'namespaces' arguments with equal number of comma seperated  values");
                
            	try {
            		String platformArg = args[3];
            		if (platformArg.startsWith("-"))
            			platformArg = platformArg.substring(1);
            	    platform = UMLDialect.valueOf(platformArg);
            	}
            	catch (IllegalArgumentException e) {
            		throw new IllegalArgumentException("'" + args[3] + "' - expected one of ["
            				+ UMLDialect.asString() + "]");
            	}
        	}
        	else if (args.length == 5) {
        		namespaces = args[3].split(",");
        	    schemaNames = args[4].split(",");
        	    if (namespaces.length != schemaNames.length)
            		throw new RDBException("expected 'schemaNames' and 'namespaces' arguments with equal number of comma seperated  values");

        	}
        	else {
        		schemaNames = args[3].split(",");
        		namespaces = new String[schemaNames.length];
        		for (int i = 0; i < schemaNames.length; i++)
        			namespaces[i] = "http://" + schemaNames[i];
        	}
        	
        	Model model = (new RDBReader()).read(dialect, schemaNames, namespaces);

            File dest = new File(args[2]);
            if (!dest.getParentFile().exists())
            	dest.getParentFile().mkdirs();
       	
        	if (log.isDebugEnabled()) {
        		ProvisioningModelDataBinding provBinding = new ProvisioningModelDataBinding(
            			new DefaultValidationEventHandler());
            	String xml = provBinding.marshal(model);
        		File outFile = new File(dest.getParentFile(), "technical-model.xml");
        		OutputStream stream = new FileOutputStream(outFile);
        		stream.write(xml.getBytes());
        		stream.flush();
        		stream.close();
        		log.debug("wrote merged model file to: " 
        				+ outFile.getAbsoluteFile());
        		log.debug("reading merged model file: " 
        				+ outFile.getAbsoluteFile());
        		model = (Model)provBinding.unmarshal(
        			new FileInputStream(outFile));
        	}
        	
    	    ModelAdapter helper = 
    				   new ModelAdapter(model);
    	    UMLModelAssembler umlAssembler = null;
    		switch (platform) {
    		case papyrus:
    			umlAssembler = new PapyrusModelAssembler(model, 
        				namespaces[0], "tns");
    			break;
    		case magicdraw:
    			umlAssembler = new MDModelAssembler(model, 
        				namespaces[0], "tns");
    			break;
    		}
    		umlAssembler.setDerivePackageNamesFromURIs(false);
    	    Document document = umlAssembler.getDocument();
    	        
	    	log.info("marshaling XMI model to "
	    			+ dest.getAbsolutePath());
	        try {
				FileOutputStream os = new FileOutputStream(dest);
			    XMLOutputter outputter = new XMLOutputter();
			    outputter.setFormat(Format.getPrettyFormat());
			    outputter.output(document, os);
			} catch (FileNotFoundException e) {
	            throw new ProvisioningException(e);
			} catch (IOException e) {
	            throw new ProvisioningException(e);
			}
       
    }
        
    private static void printUsage() {
    	log.info("Usage: java "+UMLTool.class.getName()+" "
    	  + "[source &lt;rdb | xsd, ...&gt;]" 
    	  + "[dialect &lt;oracle | mysql, ...&gt;]" 
    	  + "[platform &lt;papyrus | magicdraw, ...&gt;]" 
    	  + "[dest-file] [namespace1, namespace2, ...]"  
    	  + "[schema1, schema2, ...] *");
    }
}
