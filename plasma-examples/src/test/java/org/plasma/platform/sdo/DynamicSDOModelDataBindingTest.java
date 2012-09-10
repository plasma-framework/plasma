package org.plasma.platform.sdo;




import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DataBinding;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.sdo.DASClientTestCase;

/**
 */
public class DynamicSDOModelDataBindingTest extends DASClientTestCase {
    private static Log log = LogFactory.getLog(DynamicSDOModelDataBindingTest.class);
    private String classesDir = "./target/classes";
    private String targetDir = "./target";

   
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(DynamicSDOModelDataBindingTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();      
    }
    
    public void tearDown() throws Exception {
        super.tearDown();	
    }
     
    public void testProfile() throws Exception {
        File loadDir = new File(targetDir, "../../test/data/org/plasma/platform/sdo/databinding");
        File loadFile = new File(loadDir, "profile-export-dataload.xml");
        
        processBusinessModel(loadFile,
        	new ProfileExportDataBinding(
        		new DefaultValidationEventHandler()));
    }    
          
    public void testTaxonomy() throws Exception {
        File loadDir = new File(targetDir, "../../test/data/org/plasma/platform/sdo/databinding");
        File loadFile = new File(loadDir, "PerformanceReferenceModel.xml");
        
        processBusinessModel(loadFile,
        	new TaxonomyExportDataBinding(
        		new DefaultValidationEventHandler()));
    }         
       
    private void processBusinessModel(File loadFile, DataBinding dataBinding) throws Exception {
        
        log.info("loading data");
    	log.info("validating file " + loadFile.getName());
        InputStream xmlis = new FileInputStream(loadFile);
        dataBinding.validate(xmlis);
    }  

}