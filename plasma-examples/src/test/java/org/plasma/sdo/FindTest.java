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
            Property.forName(Category.PROPERTY.name.name()),
        });
        From from = new From(Category.TYPE_NAME_CATEGORY,
        		Category.NAMESPACE_URI);
        Where where = new Where();
        Query query = new Query(select, from, where);
        query.setStartRange(0);
        query.setEndRange(10);
        return query;
    }
     
}