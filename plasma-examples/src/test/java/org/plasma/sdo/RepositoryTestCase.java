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