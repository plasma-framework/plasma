package org.plasma.sdo;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.test.PlasmaTest;
import org.plasma.sdo.access.client.SDODataAccessClient;

/**
 * 
 */
public abstract class DASClientTestCase extends PlasmaTest {
    private static Log log = LogFactory.getLog(DASClientTestCase.class);
    protected SDODataAccessClient service;
    protected String classesDir = "./target/classes";
    protected String targetDir = "./target";

    public void setUp() throws Exception {
        service = new SDODataAccessClient();
    }
        
}