package org.plasma.provisioning.cli;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.xslt.XSLTUtils;
import org.plasma.config.Artifact;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.xml.sax.SAXException;

public abstract class ProvisioningTool {
    private static Log log =LogFactory.getLog(
    		ProvisioningTool.class); 

    protected static File createStagingModel(File source, File classesDir) 
        throws IOException, TransformerConfigurationException, TransformerException, JAXBException, SAXException 
    {
        String stagingXsl = "PlasmaUMLModelToStagingModel.xsl";
        URL stagingXslUrl = ProvisioningTool.class.getResource(stagingXsl);
        if (stagingXslUrl == null) 
            stagingXslUrl = ProvisioningTool.class.getClassLoader().getResource(stagingXsl);
        if (stagingXslUrl == null)    
            throw new RuntimeException("templte file '"
                    + stagingXsl + "' does not exist as resource associated with class, " +
                    ProvisioningTool.class.getName() + " or anywhere on the current classpath");
        Properties props = new Properties();
        File pimFile = null;
        if (classesDir != null) {
            pimFile = new File(classesDir, "pim.xml");
            if (pimFile.exists() && pimFile.lastModified() > source.lastModified())
                return pimFile;
        }
        else {
            File baseTempDir = new File(System.getProperty("java.io.tmpdir"));  
            
            pimFile = new File(baseTempDir, "pim.xml");
            if (pimFile.exists() && pimFile.lastModified() > source.lastModified())
                return pimFile;
        }
        
        XSLTUtils xslt = new XSLTUtils();
        xslt.transform(pimFile, source, stagingXslUrl, props); 
        
        
        return pimFile;
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
    
    protected static File createTempFileFromJar(String javaXsl, File baseDir) throws IOException
    {
        InputStream javaXslStream = QueryTool.class.getResourceAsStream(javaXsl);
        if (javaXslStream == null)    
            throw new RuntimeException("templte file '"
                + javaXsl + "' does not exist as stream resource associated with class, " +
                QueryTool.class.getName());        
        File javaXslUrlFile = new File(baseDir, javaXsl);
        OutputStream javaXslOutputStream = new FileOutputStream(javaXslUrlFile);
        writeContent(javaXslStream, javaXslOutputStream);
        return javaXslUrlFile;
    }
    
    protected static boolean regenerate(long lastExecution)
    {
    	boolean fileStale = false;
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
            		fileStale = true;
                    log.debug("detected stale repository artifact '"
                    		+ url.getFile() + "' against time: " 
                    		+ String.valueOf(lastExecution));
            		break;
            	}
            }
        }           
        if (!fileStale)
        	return false;
        else
            return true;
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
