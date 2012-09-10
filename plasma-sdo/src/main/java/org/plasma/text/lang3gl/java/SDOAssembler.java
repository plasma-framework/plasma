package org.plasma.text.lang3gl.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.Documentation;
import org.plasma.provisioning.Enumeration;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.Package;
import org.plasma.text.lang3gl.ClassFactory;
import org.plasma.text.lang3gl.DefaultStreamAssembler;
import org.plasma.text.lang3gl.EnumerationFactory;
import org.plasma.text.lang3gl.InterfaceFactory;
import org.plasma.text.lang3gl.Lang3GLFactory;
import org.plasma.text.lang3gl.Lang3GLOperation;

public class SDOAssembler extends DefaultStreamAssembler {

    private static Log log =LogFactory.getLog(
    		SDOAssembler.class); 
    
	public SDOAssembler(Model packages, Lang3GLFactory factory,
			Lang3GLOperation operation, File dest) {
		super(packages, factory, operation, dest);
	}

	public void createEnumerationClasses() throws IOException 
    {
    	EnumerationFactory enumFactory = factory.getEnumerationFactory();
    	for (Package pkg : this.packages.getPackages()) {
			File dir = new File(dest, factory.getEnumerationFactory().createBaseDirectoryName(pkg));
			log.debug("processing package: " + dir.getAbsolutePath());
			if (!dir.exists()) {
                if (!dir.mkdirs())
        		    throw new IllegalArgumentException("package directory '"
                        + dir.getAbsolutePath() + "' could not be created");  
    			log.debug("created package: " + dir.getAbsolutePath());
			}
    		for (Enumeration enumeration : pkg.getEnumerations()) {
                File file = new File(dir, factory.getEnumerationFactory().createFileName(enumeration));
    			log.debug("creating file: " + file.getAbsolutePath());
    			FileOutputStream stream = new FileOutputStream(file);
    			StringBuilder buf = new StringBuilder();
    			buf.append(enumFactory.createContent(pkg, enumeration));    			
    			stream.write(buf.toString().getBytes());
            	stream.flush();
            	stream.close();
            	this.resultEnumerationsCount++;
    		}
    	}    	
    }

	
    public void createInterfaceClasses() throws IOException 
    {
    	InterfaceFactory interfaceFactory = factory.getInterfaceFactory();
    	
    	for (Package pkg : this.packages.getPackages()) {
			File dir = new File(dest, interfaceFactory.createBaseDirectoryName(pkg));
			log.debug("processing package: " + dir.getAbsolutePath());
			if (!dir.exists()) {
                if (!dir.mkdirs())
        		    throw new IllegalArgumentException("package directory '"
                        + dir.getAbsolutePath() + "' could not be created");  
    			log.debug("created package: " + dir.getAbsolutePath());
			}
    		for (Class clss : pkg.getClazzs()) {
                File file = new File(dir, interfaceFactory.createFileName(clss));
    			log.debug("creating file: " + file.getAbsolutePath());
    			FileOutputStream stream = new FileOutputStream(file);
    			StringBuilder buf = new StringBuilder();
    			buf.append(interfaceFactory.createContent(pkg, clss));    			
    			stream.write(buf.toString().getBytes());
    			stream.flush();
            	stream.close();
            	this.resultInterfacesCount++;
    		}
    	}    	
    }
    
    public void createImplementationClasses() throws IOException 
    {
    	ClassFactory classFactory = factory.getClassFactory();
    	
    	for (Package pkg : this.packages.getPackages()) {
			File dir = new File(dest, classFactory.createDirectoryName(pkg));
			log.debug("processing package: " + dir.getAbsolutePath());
			if (!dir.exists()) {
                if (!dir.mkdirs())
        		    throw new IllegalArgumentException("package directory '"
                        + dir.getAbsolutePath() + "' could not be created");  
    			log.debug("created package: " + dir.getAbsolutePath());
			}
    		for (Class clss : pkg.getClazzs()) {
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

	public void createInterfacePackageDocs() throws IOException {
    	for (Package pkg : this.packages.getPackages()) {
			File dir = new File(dest, factory.getInterfaceFactory().createBaseDirectoryName(pkg));
			log.debug("processing package: " + dir.getAbsolutePath());
			if (!dir.exists()) {
                if (!dir.mkdirs())
        		    throw new IllegalArgumentException("package directory '"
                        + dir.getAbsolutePath() + "' could not be created");  
    			log.debug("created package: " + dir.getAbsolutePath());
			}
            File file = new File(dir, "package.html");
			log.debug("creating file: " + file.getAbsolutePath());
			FileOutputStream stream = new FileOutputStream(file);
			StringBuilder buf = new StringBuilder();
			buf.append("<html><head></head>");
			buf.append("<body>");
			if (pkg.getDocumentations() != null)
			for (Documentation doc : pkg.getDocumentations()) {
				buf.append(doc.getBody().getValue());
			}
			buf.append("</body>");
			buf.append("</html>");

			stream.write(buf.toString().getBytes());
        	stream.flush();
        	stream.close();
    	}    	
	}   

}
