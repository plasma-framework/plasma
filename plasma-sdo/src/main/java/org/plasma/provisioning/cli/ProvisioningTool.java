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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.bind.JAXBException;

import joptsimple.BuiltinHelpFormatter;
import joptsimple.HelpFormatter;
import joptsimple.OptionParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.config.Artifact;
import org.plasma.config.PlasmaConfig;
import org.plasma.metamodel.Model;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.xml.sax.SAXException;

public abstract class ProvisioningTool {
    private static Log log =LogFactory.getLog(
    		ProvisioningTool.class); 

    protected static void printUsage(OptionParser parser, Log theLog) throws IOException
    {
		HelpFormatter helpFormat = new BuiltinHelpFormatter(640, 1);
		parser.formatHelpWith(helpFormat);
		ByteArrayOutputStream helpOs = new ByteArrayOutputStream();
		parser.printHelpOn(helpOs);
		theLog.info("\n" + new String(helpOs.toByteArray()));   	
    }    

    protected static Model validateStagingModel(File source) 
        throws JAXBException, SAXException, IOException 
    {
		FileInputStream is = new FileInputStream(source);
		ProvisioningModelDataBinding binding = new ProvisioningModelDataBinding(
				new DefaultValidationEventHandler());
		Model model = null;
		try {
			model = (Model)binding.validate(is);
		}
		finally {
			is.close();
		}
		
		ModelAdapter validator = 
			new ModelAdapter(model);
		
		return model;
    }
    
    protected static boolean regenerate(long lastExecution)
    {
    	boolean stale = false;
    	
    	//check config file
    	if (PlasmaConfig.getInstance().getConfigFileLastModifiedDate() > lastExecution) {
    		stale = true;
    		log.debug("detected stale configuration file '"
            		+ PlasmaConfig.getInstance().getConfigFileName() + "' against time: " 
            		+ String.valueOf(lastExecution));
    	}
    	
    	if (!stale) {
	    	// check repo artifacts
	        for (Artifact artifact : PlasmaConfig.getInstance().getRepository().getArtifacts()) {
	            URL url = PlasmaConfig.class.getResource(artifact.getUrn());
	            if (url == null)
	            	url = PlasmaConfig.class.getClassLoader().getResource(artifact.getUrn());            
	            log.debug("checking modified state of repository artifact '"
	            		+ url.getFile() + "' against time: " 
	            		+ String.valueOf(lastExecution));
	            File urlFile = new File(url.getFile());
	            if (urlFile.exists()) {
	            	if (urlFile.lastModified() > lastExecution) {
	            		stale = true;
	                    log.debug("detected stale repository artifact '"
	                    		+ url.getFile() + "' against time: " 
	                    		+ String.valueOf(lastExecution));
	            		break;
	            	}
	            }
	        }
    	}
        
    	return stale;
    }
    
    protected static void writeContent(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[4000];
        int len = -1;
        try {
        while ((len = is.read(buf)) != -1)
            os.write(buf, 0, len);
        }
        finally {
            is.close();
            os.flush();
        } 
    }        

    public static byte[] readContent(InputStream is)
        throws IOException
    {
        byte[] result;
        byte[] buf = new byte[4000];
        ByteArrayOutputStream os = new ByteArrayOutputStream(buf.length);
        int len = -1;                                                
        try {
            while ((len = is.read(buf, 0, buf.length)) != -1)
                os.write(buf, 0, len); 
            result = os.toByteArray();
        }
        finally {
            is.close();
            os.flush();
            os.close();
        } 
        return result;
    }
}
