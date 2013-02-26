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
package org.plasma.text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;

import javax.xml.bind.JAXBException;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.test.PlasmaTest;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.text.ddl.DDLModelAssembler;
import org.plasma.text.ddl.DDLModelDataBinding;
import org.plasma.text.ddl.DDLOperation;
import org.plasma.text.ddl.DDLStreamAssembler;
import org.plasma.text.ddl.OracleFactory;
import org.xml.sax.SAXException;

public class DDLModelAssemblerTest extends PlasmaTest {
    private static Log log = LogFactory.getLog(DDLModelAssemblerTest.class);
    
    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(DDLModelAssemblerTest.class);
    }
    
    public void setUp() throws Exception {
        
        super.setUp();
    }
    
    public void testDDLModelAssembler() throws JAXBException, SAXException, IOException {
    	DDLModelAssembler modelAssembler = new DDLModelAssembler();
    	DDLModelDataBinding binding = new DDLModelDataBinding(
    			new DefaultValidationEventHandler());
    	String xml = binding.marshal(modelAssembler.getSchemas());
    	log.info(xml);
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	DDLStreamAssembler assembler = new DDLStreamAssembler(
    		modelAssembler.getSchemas(),
    		new OracleFactory(), DDLOperation.create,
    		stream);
    	assembler.start();
    	stream.flush();
    	String ddl = new String(stream.toString());
    	log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");    	   	
    	log.info(ddl);    	   	

    	stream = new ByteArrayOutputStream();
    	assembler = new DDLStreamAssembler(
        		modelAssembler.getSchemas(),
        		new OracleFactory(), DDLOperation.drop,
        		stream);
    	assembler.start();
    	stream.flush();
    	ddl = new String(stream.toString());
    	log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");    	   	
    	log.info(ddl);  
    	
    	stream = new ByteArrayOutputStream();
    	assembler = new DDLStreamAssembler(
        		modelAssembler.getSchemas(),
        		new OracleFactory(), DDLOperation.truncate,
        		stream);
    	assembler.start();
    	stream.flush();
    	ddl = new String(stream.toString());
    	log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");    	   	
    	log.info(ddl);    	   	
    	
    }
    
}
