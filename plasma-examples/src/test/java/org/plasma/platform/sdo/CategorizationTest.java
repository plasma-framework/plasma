package org.plasma.platform.sdo;




import java.util.UUID;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.platform.sdo.categorization.Categorization;
import org.plasma.platform.sdo.categorization.Category;
import org.plasma.platform.sdo.categorization.query.QCategorization;
import org.plasma.query.Query;
import org.plasma.sdo.DASClientTestCase;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import com.examples.sdo.categorization.Example;
import com.examples.sdo.categorization.ExampleCategorization;
import com.examples.sdo.categorization.query.QExampleCategorization;
import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Type;

/**
 * Performs and tests various SDO CRUD operations.
 */
public class CategorizationTest extends DASClientTestCase {
    private static Log log = LogFactory.getLog(CategorizationTest.class);

    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(CategorizationTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();
    } 
    
    public void testCategorizationInsertUpdateDelete()       
    {
    	int catzCountBefore = getCategorizationCount();
    	
    	DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
        dataGraph.getChangeSummary().beginLogging(); // log changes from this point
    	Type rootType = PlasmaTypeHelper.INSTANCE.getType(Example.class);
    	DataObject root = dataGraph.createRootObject(rootType);
    	
    	Example exampleRoot = (Example)root;
    	exampleRoot.setName("my example 1");
    	exampleRoot.setUuid(UUID.randomUUID().toString());
    	
    	ExampleCategorization ec1 = exampleRoot.createExampleCategorization();
    	
    	Categorization catz = ec1.createCategorization();
    	Category cat = catz.createCategory();
    	cat.setName("my cat");
    	cat.setId(999);
    	cat.setDefinition("my test cat");    	
 
    	ExampleCategorization ec2 = exampleRoot.createExampleCategorization();
    	
    	Categorization catz2 = ec2.createCategorization();
    	catz2.setCategory(cat); // link previous cat creating circularity

    	ExampleCategorization ec3 = exampleRoot.createExampleCategorization();
    	
    	Categorization catz3 = ec3.createCategorization();
    	catz3.setCategory(cat); // link previous cat creating circularity
    	
    	//save the graph
        service.commit(dataGraph, "test-user");
        assertTrue(exampleRoot.getCreatedByName().equals("test-user")); // check auto populate of origination prop
        assertTrue(exampleRoot.getCreatedDate() != null); // check auto populate of origination prop
                
        // update a property
        cat.setName("updated my cat"); // make a change        
        service.commit(dataGraph, "test-user2");
        
        // delete a section of graph
        ec3.delete();
        service.commit(dataGraph, "test-user2");
        
        
        /*        
        assertTrue(foo.getCreatedByName().equals("test-user2")); //  check did not change
    	int catzCountAfterInsert = getCategorizationCount();
    	assertTrue(catzCountBefore + 1 == catzCountAfterInsert);
    	
    	
    	DataGraph[] results = service.find(createGraphQuery(fooCatz.getSeqId()));
    	assertTrue(results != null);
    	assertTrue(results.length == 1);
    	log.info(((PlasmaDataGraph)results[0]).dump());
    	
       
        //Delete the graph and commit 
    	fooCatz.delete(); // delete Foo by SDO containment
        service.commit(dataGraph, "test-user");        

        // ensure we have the same number of entities, after delete
        int catzCountAfterDelete = getCategorizationCount();
    	
    	assertTrue(catzCountBefore == catzCountAfterDelete);
*/
    }
/* 
    public void testCategorizationConcurrency()       
    {
    	int catzCountBefore = getCategorizationCount();
    	
    	DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
        dataGraph.getChangeSummary().beginLogging(); // log changes from this point
    	Type rootType = PlasmaTypeHelper.INSTANCE.getType(ExampleCategorization.class);
    	DataObject root = dataGraph.createRootObject(rootType);
    	
    	ExampleCategorization fooCatz = (ExampleCategorization)root;
    	Example foo = fooCatz.createExample();
    	foo.setName("my foo");
    	foo.setUuid(UUID.randomUUID().toString());
    	
    	Categorization catz = fooCatz.createCategorization();
    	Category cat = catz.createCategory();
    	cat.setName("cat1");
    	cat.setId(999);
    	cat.setDefinition("my test cat");    	
    	        
    	//save the graph
        service.commit(dataGraph, "test-user");
        assertTrue(foo.getCreatedByName().equals("test-user")); // check auto populate of origination prop
        assertTrue(foo.getCreatedDate() != null); // check auto populate of origination prop
                
        // re-query for 2 graphs at same time
    	DataGraph[] results1 = service.find(createGraphQuery(fooCatz.getSeqId()));
    	assertTrue(results1 != null);
    	assertTrue(results1.length == 1);
    	DataGraph graph1 = results1[0];
    	ExampleCategorization exampCat1 = (ExampleCategorization)graph1.getRootObject();
    	log.info("graph 1:" + exampCat1.dump());    	
    	
    	DataGraph[] results2 = service.find(createGraphQuery(fooCatz.getSeqId()));
    	assertTrue(results2 != null);
    	assertTrue(results2.length == 1);
    	DataGraph graph2 = results2[0];
     	ExampleCategorization exampCat2 = (ExampleCategorization)graph2.getRootObject();
       	log.info("graph 2:" + exampCat2.dump());

       	Object lock = new Object();
        synchronized (lock) {
	        try {
	        	log.info("waiting 2 seconds...");
	        	lock.wait(2000);
	        }
	        catch (InterruptedException e) {
	        	log.error(e.getMessage(), e);
	        }
        }
        log.info("...continue");
       	    	
        // wait at least 1 sec to commit first record so user-2 snapshot date '
        // is greater than user1 commit date 
    	exampCat1.getExample().setName("new name1");
    	service.commit(graph1, "test-user");
        
        synchronized (lock) {
	        try {
	        	log.info("waiting 5 seconds...");
	        	lock.wait(5000);
	        }
	        catch (InterruptedException e) {
	        	log.error(e.getMessage(), e);
	        }
        }
        log.info("...continue");
        
    	exampCat2.getExample().setName("new name333");
    	try {
    	    service.commit(graph2, "test-user2");
    	    assertTrue("expected commit failure", 1==2);
    	}
    	catch (UserException e) {
    		log.info("concurrency error: " + e.getMessage());
    		if (e.getCause() != null)
    			log.info("concurrency error: " + e.getCause().getMessage());
    	}
          
        //Delete the graph and commit 
    	DataGraph[] results3 = service.find(createGraphQuery(fooCatz.getSeqId()));
    	assertTrue(results3 != null);
    	assertTrue(results3.length == 1);
    	DataGraph graph3 = results3[0];
     	ExampleCategorization exampCat3 = (ExampleCategorization)graph3.getRootObject();
     	exampCat3.delete();
     	service.commit(graph3, "test-user3");
        
        // ensure we have the same number of entities, after delete
        int catzCountAfterDelete = getCategorizationCount();
    	
    	assertTrue(catzCountBefore == catzCountAfterDelete);
    }
*/     
    
	public static Query createGraphQuery(Long seqId) {
		QExampleCategorization query = QExampleCategorization.newQuery();
		query.select(query.wildcard())
		     .select(query.example().wildcard())
		     .select(query.categorization().wildcard())
		     .select(query.categorization().category().wildcard());
		query.where(query.seqId().eq(seqId));
		
		/*
        SelectModel select = new SelectModel(new String[] { 
                "*",
                "example/*",
                "categorization/*",
                "categorization/category/*",
            });         
                
    	FromModel from = new FromModel(ExampleCategorization.ETY_EXAMPLE_CATEGORIZATION, 
    			ExampleCategorization.NAMESPACE_URI);        
        WhereModel where = new WhereModel("[seqId='" + seqId + "']");
        //where.addExpression(Property.forName(ExampleCategorization.PTY_SEQ_ID).eq(
        //		seqId));
        
        QueryModel query = new QueryModel(select, from, where);
        */
        
        return query;		
	}		
    
     
    protected int getCategorizationCount() {    	
    	QCategorization query = QCategorization.newQuery();
    	query.select(query.wildcard());
        return service.count(query);
    }

    
}