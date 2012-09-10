package org.plasma.sdo;




import javax.xml.bind.JAXBException;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.platform.sdo.TaxonomyQuery;
import org.plasma.query.bind.PlasmaQueryDataBinding;
import org.plasma.query.model.Query;
import org.xml.sax.SAXException;

import commonj.sdo.DataGraph;

/**
 * Query tests which return data-graphs of various depths. 
 */
public class DeepFindTest extends DASClientTestCase {
    private static Log log = LogFactory.getLog(DeepFindTest.class);

    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(DeepFindTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();      
    }
   

    public void testTaxonomyQuery() throws Exception {
        log.info("testTaxonomyQuery()");
        
        int num = 1;
        long before = System.currentTimeMillis();
        
        
        Query query = TaxonomyQuery.createQuery("FEA Technical Reference Model (TRM)");
        try {
            PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                    new DefaultValidationEventHandler());
            String xml = binding.marshal(query);
            log.info("query: " + xml);
            
            query = (Query)binding.validate(xml); 
            
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        
        try {
        DataGraph[] results = service.find(query);
        for (int i = 0; i < results.length; i++) {
            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
            log.info(dataObject.dump());
        }
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        
        long after = System.currentTimeMillis();
        float totalSeconds = (after - before) / 1000;
        float averageSecondsPerGraph = ((float)(after - before) / num) / 1000;
        log.info("total seconds: " + totalSeconds + " average seconds per graph: " + averageSecondsPerGraph);        
    }  
    
    
     
}