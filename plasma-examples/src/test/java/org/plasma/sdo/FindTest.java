package org.plasma.sdo;




import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.platform.sdo.categorization.Category;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.From;
import org.plasma.query.model.Property;
import org.plasma.query.model.Query;
import org.plasma.query.model.Select;
import org.plasma.query.model.Where;

import commonj.sdo.DataGraph;

/**
 * Query tests which return data-graphs of various depths. 
 */
public class FindTest extends DASClientTestCase {
    private static Log log = LogFactory.getLog(FindTest.class);

    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(FindTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();      
    }
   
    public void testCountShallowGraph() {
        
        log.info("testCountShallowGraph()");
        int results = service.count(createShallowQuery());
        log.info("count: " + String.valueOf(results));
    }  
    
    public void testFindShallowGraph() {
        log.info("testFindShallowGraph()");
        //try {
        
        DataGraph[] results = service.find(createShallowQuery());
        for (int i = 0; i < results.length; i++) {
            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
            log.info(dataObject.dump());
        }
        
        //}
        //catch (Throwable t) {
        //    log.error(t.getMessage(), t);
        //}
    }  
  
    protected Query createShallowQuery() {
        Select select = new Select(new AbstractProperty[] { 
            Property.forName(Category.PTY_NAME),
        });
        From from = new From(Category.ETY_CATEGORY,
        		Category.NAMESPACE_URI);
        Where where = new Where();
        Query query = new Query(select, from, where);
        query.setStartRange(0);
        query.setEndRange(10);
        return query;
    }
     
}