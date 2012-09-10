package org.plasma.config;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.test.PlasmaTest;
import org.plasma.config.PlasmaConfig;

/**
 * 
 */
public class ConfigTestCase extends PlasmaTest {
    private static Log log = LogFactory.getLog(ConfigTestCase.class);
    
    public void testConfigLoad() {
        PlasmaConfig.getInstance();
    }
    
    public void testJPAConfig() {
    	DataAccessProvider jpa = PlasmaConfig.getInstance().getDataAccessProvider(DataAccessProviderName.JDO);
        
        assertTrue(jpa != null);
    }        
}