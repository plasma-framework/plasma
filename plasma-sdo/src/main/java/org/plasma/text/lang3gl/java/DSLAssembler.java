package org.plasma.text.lang3gl.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.Package;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;

public class DSLAssembler extends DefaultStreamAssembler {

    private static Log log =LogFactory.getLog(
    		DSLAssembler.class); 
    
	public DSLAssembler(Model packages, Lang3GLFactory factory,
			Lang3GLOperation operation, File dest) {
		super(packages, factory, operation, dest);
	}

	public void createEnumerationClasses() throws IOException 
    {
    }
	
	public void createInterfaceClasses() throws IOException 
    {
    }
    
	public void createImplementationClasses() throws IOException 
    {
    	ClassFactory classFactory = factory.getClassFactory();
    	
    	for (Package pkg : this.packages.getPackages()) {
			File dir = new File(dest, classFactory.createDirectoryName(pkg));
			log.debug("processing package: " + dir.getAbsolutePath());
    		for (Class clss : pkg.getClazzs()) {    			
    			if (!PlasmaConfig.getInstance().generateQueryDSL(clss.getUri(), clss.getName()))
    				continue; 			
    			
    			if (!dir.exists()) {
                    if (!dir.mkdirs())
            		    throw new IllegalArgumentException("package directory '"
                            + dir.getAbsolutePath() + "' could not be created");  
        			log.debug("created package: " + dir.getAbsolutePath());
    			}

    			File file = new File(dir, classFactory.createFileName(clss));
    			log.debug("creating file: " + file.getAbsolutePath());
    			FileOutputStream stream = new FileOutputStream(file);
    			
    			StringBuilder buf = new StringBuilder();
    			buf.append(classFactory.createContent(pkg, clss));    			
    			
    			stream.write(buf.toString().getBytes());
            	stream.flush();
            	stream.close();
            	
            	this.resultClassesCount++;
    		}
    	}    	
    }

	@Override
	public void createInterfacePackageDocs() throws IOException {
		// TODO Auto-generated method stub
		
	}   
	

}
