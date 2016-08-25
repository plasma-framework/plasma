package org.plasma.provisioning;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.test.PlasmaTest;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.metamodel.Model;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.provisioning.adapter.ProvisioningModel;
import org.xml.sax.SAXException;

import junit.framework.Test;

public class AnnotationConverterTest extends PlasmaTest {
    private static Log log = LogFactory.getLog(AnnotationConverterTest.class);
        
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(AnnotationConverterTest.class);
    }
    
    public void setUp() throws Exception {
        
        super.setUp();
    }
    
    public void testConvert() throws IOException, JAXBException, SAXException {
    	AnnotationConverter converter = new AnnotationMetamodelAssembler();
    	assertTrue("no annotated classes found", converter.hasAnnotatedClasses());
    	Model model = converter.buildModel();
    	assertTrue(model != null);
    	ProvisioningModel validator = 
				new ModelAdapter(model);
    	MetamodelDataBinding binding = new MetamodelDataBinding(
    			new DefaultValidationEventHandler());
		javax.xml.bind.JAXBElement<Model> element = new JAXBElement<Model>(new QName("root"), 
				Model.class, model);

    	String xml = binding.marshal(element);
		File outFile = new File("./target", AnnotationConverterTest.class.getSimpleName().toLowerCase()  + "_technical-model.xml");
		FileOutputStream stream = new FileOutputStream(outFile);
		stream.write(xml.getBytes());
		stream.flush();
		stream.close();
    }
    
}
