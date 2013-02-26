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
package org.plasma.sdo;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.test.PlasmaTest;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.plasma.sdo.repository.PlasmaRepository;

import commonj.sdo.Type;

/**
 * 
 */
public class RepositoryTestCase extends PlasmaTest {
    private static Log log = LogFactory.getLog(RepositoryTestCase.class);
    
    public void setUp() throws Exception {
    }
       
    public void testRepositoryInit() {
        PlasmaRepository.getInstance();
    }
    
    public void testNamespaces() {
    	
    	List<String> uris = PlasmaRepository.getInstance().getAllNamespaceUris();
    	for (String uri : uris) {
        	List<Type> types = PlasmaTypeHelper.INSTANCE.getTypes(uri);
        	for (Type type : types)
        		log.info(type.getURI() + "#" + type.getName());
    	}
    }
    
    
    
}