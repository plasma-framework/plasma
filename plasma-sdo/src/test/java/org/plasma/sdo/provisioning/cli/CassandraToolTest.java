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




import joptsimple.OptionException;
import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.test.PlasmaTest;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.provisioning.cli.CassandraTool;
import org.plasma.provisioning.cli.ProvisioningToolOption;

/**
 * Tests for Cassandra tool
 */
public class CassandraToolTest extends PlasmaTest {
    private static Log log = LogFactory.getLog(CassandraToolTest.class);
        
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(CassandraToolTest.class);
    }
    
    public void setUp() throws Exception {        
        super.setUp();
    }
    
    public void testNoOptions() throws Exception {
    	String[] args = {
	    		"-" + ProvisioningToolOption.silent.name(),
    	};
    	try { 
    		CassandraTool.main(args);
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
 	
    	CassandraTool.main(args);
    }
    
    public void testUnknownOption() throws Exception {
    	try {
	    	String[] args = {
	    		"-" + ProvisioningToolOption.silent.name(),
	    		"-FOO" 
	    	};
	 	
	    	CassandraTool.main(args);
		    assertFalse(true);
    	}
    	catch (OptionException e) {
    	}
    }
     
  }
