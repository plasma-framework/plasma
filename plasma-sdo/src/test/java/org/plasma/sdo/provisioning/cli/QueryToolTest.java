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
package org.plasma.sdo.provisioning.cli;




import java.io.IOException;

import javax.xml.bind.JAXBException;

import joptsimple.OptionException;
import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.test.PlasmaTest;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.provisioning.cli.CassandraTool;
import org.plasma.provisioning.cli.DSLTool;
import org.plasma.provisioning.cli.QueryTool;
import org.plasma.provisioning.cli.RDBDialect;
import org.plasma.provisioning.cli.RDBTool;
import org.plasma.provisioning.cli.ProvisioningToolOption;
import org.plasma.provisioning.cli.RDBToolAction;
import org.plasma.provisioning.cli.SDOTool;
import org.xml.sax.SAXException;

/**
 * Tests for DSL tool
 */
public class QueryToolTest extends PlasmaTest {
    private static Log log = LogFactory.getLog(QueryToolTest.class);
        
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(QueryToolTest.class);
    }
    
    public void setUp() throws Exception {        
        super.setUp();
    }
    
    public void testNoOptions() throws Exception {
    	String[] args = {
	    	"-" + ProvisioningToolOption.silent.name(),
    	};
    	try { 
    		QueryTool.main(args);
    	    assertFalse(true);
    	}
    	catch (IllegalArgumentException e) {
    	}
    }
    
    public void testHelpOption() throws Exception {
    	String[] args = {
	    	"-" + ProvisioningToolOption.silent.name(),
    		"-" + ProvisioningToolOption.help.name()
    	};
 	
    	QueryTool.main(args);
    }
    
    public void testUnknownOption() throws Exception {
    	try {
	    	String[] args = {
	    		"-" + ProvisioningToolOption.silent.name(),
	    		"-FOO" 
	    	};
	 	
	    	QueryTool.main(args);
		    assertFalse(true);
    	}
    	catch (OptionException e) {
    	}
    }
     
  }
