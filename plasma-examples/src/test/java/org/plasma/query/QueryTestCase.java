package org.plasma.query;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.test.PlasmaTest;
import org.plasma.platform.sdo.personalization.query.QProfile;
import org.plasma.platform.sdo.personalization.query.QProfileElementSetting;
import org.plasma.query.bind.PlasmaQueryDataBinding;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.access.client.SDODataAccessClient;
import org.xml.sax.SAXException;

import commonj.sdo.DataGraph;

/**
 * 
 */
public class QueryTestCase extends PlasmaTest {
    private static Log log = LogFactory.getLog(QueryTestCase.class);
    protected String classesDir = "./target/classes";
    protected String targetDir = "./target"; 
    int runs = 10;
/*          
    public void testGroupBy() throws Exception {
    	
    	QProfile profile = QProfile.newQuery();
    	profile.select(profile.seqId().max());
    	profile.where(profile.user().person().firstName().like("skip*")
    	    .or(profile.user().person().firstName().like("loo*")));  
    	profile.groupBy(profile.seqId());

    	Query q2 = marshal(profile.getModel());
        
        SDODataAccessClient service = new SDODataAccessClient();
        DataGraph[] results = service.find(q2);
        for (int i = 0; i < results.length; i++) {
            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
            log.info(dataObject.dump());
        }
    }
*/
/*
    public void testSubquery() throws Exception {
    	
    	QUser subquery = QUser.newQuery(); 
    	subquery.select(subquery.username());
    	subquery.where(subquery.username().eq("mohawk"));
    	
    	QProfile profile = QProfile.newQuery();
    	profile.select(profile.seqId())
    	       .select(profile.user().username());
    	
    	profile.where(profile.user().username().notIn(subquery)
    			.and(profile.user().username().in(subquery)));
    	profile.orderBy(profile.user().username());

    	Query q2 = marshal(profile.getModel());
        
        SDODataAccessClient service = new SDODataAccessClient();
        DataGraph[] results = service.find(q2);
        for (int i = 0; i < results.length; i++) {
            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
            log.info(dataObject.dump());
        }
     }
*/ 
    
/*    
     public void testWildcards() throws Exception {
    	QProfile profile = QProfile.newQuery();
    	profile.select(profile.wildcard());
    	profile.select(profile.user().wildcard());
    	profile.select(profile.user().person().wildcard());

    	profile.where(
    		profile.user().person().firstName().like("skip*")
    	   .or(profile.user().person().firstName().like("foo*")).group()
    	   .and(profile.user().person().firstName().like("bar*")).group()); 
    	//profile.where(profile.user().username().exists(subquery));

    	profile.orderBy(profile.user().username());

    	Query q2 = marshal(profile.getModel());
        
        SDODataAccessClient service = new SDODataAccessClient();
        DataGraph[] results = service.find(q2);
        for (int i = 0; i < results.length; i++) {
            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
            log.info(dataObject.dump());
        }
    }
    
*/    
    /*
    public void testRange() throws Exception {
    	
     	QProfile profile = QProfile.newQuery();
    	profile.select(profile.wildcard());
    	profile.select(profile.user().wildcard());
    	profile.select(profile.user().person().wildcard());

    	profile.where(
    		profile.user().person().firstName().like("*")
    	   .or(profile.user().person().firstName().like("*")).group()
    	   .and(profile.user().person().firstName().like("bar*")).group()); 

    	profile.orderBy(profile.user().username());
    	profile.setStartRange(10);
    	profile.setEndRange(20);

    	Query q2 = marshal(profile.getModel());
        long before = System.currentTimeMillis();
        SDODataAccessClient service = new SDODataAccessClient();
        for (int count = 0; count < runs; count++) {
	        DataGraph[] results = service.find(q2);
	        for (int i = 0; i < results.length; i++) {
	            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
	            //log.info(dataObject.dump());
	        }
        }
        long after = System.currentTimeMillis();
        float timeSeconds = ((float)(after - before)) / 1000;
        log.info("testRange: " + String.valueOf(timeSeconds));
    }
    */
     
    /*
    public void testSimple() throws Exception {
    	
    	QProfile profile = QProfile.newQuery();
    	profile.select(profile.wildcard())
    	      .select(profile.user().wildcard())
    	      .select(profile.profileElementSetting().wildcard())
    	      .select(profile.profileElementSetting().setting().wildcard())
    	      .select(profile.user().userRole().wildcard())
    	      .select(profile.user().userRole().role().wildcard());

    	Query q2 = marshal(profile.getModel());
        
        long before = System.currentTimeMillis();
        SDODataAccessClient service = new SDODataAccessClient();
        for (int count = 0; count < runs; count++) {
            DataGraph[] results = service.find(q2);
	        for (int i = 0; i < results.length; i++) {
	            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
	            //log.info(dataObject.dump());
	        }
        }
        long after = System.currentTimeMillis();
        float timeSeconds = ((float)(after - before)) / 1000;
        log.info("testSimple: " + String.valueOf(timeSeconds));
    }
    */
    
    /*
    public void testSimple2() throws Exception {
    	
    	QUser user = QUser.newQuery();
    	user.select(user.wildcard())
    	      .select(user.profile().wildcard())
    	      .select(user.profile().profileElementSetting().wildcard())
    	      .select(user.profile().profileElementSetting().setting().wildcard());

    	Query q2 = marshal(user.getModel());
        
        long before = System.currentTimeMillis();
        SDODataAccessClient service = new SDODataAccessClient();
        for (int count = 0; count < runs; count++) {
            DataGraph[] results = service.find(q2);
	        for (int i = 0; i < results.length; i++) {
	            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
	            //log.info(dataObject.dump());
	        }
        }
        long after = System.currentTimeMillis();
        float timeSeconds = ((float)(after - before)) / 1000;
        log.info("testSimple2: " + String.valueOf(timeSeconds));
    }
    */

    public void testPathPredicates() throws Exception {
    	
    	QProfile profile = QProfile.newQuery();
    	profile.setName("my predicate query");
    	QProfileElementSetting setting = QProfileElementSetting.newQuery();
    	profile.select(profile.wildcard())
    	       .select(profile.user().wildcard())
    	       .select(profile.profileElementSetting(setting.seqId().eq(12)
    	          .or(setting.seqId().gt(13))
    	          .or(setting.seqId().lt(14))).wildcard())
    	       .select(profile.profileElementSetting(
    	    		   setting.seqId().gt(777)).setting().wildcard())
    	       .select(profile.user().userRole().wildcard())
    	       .select(profile.user().userRole().role().wildcard());
    	profile.where(profile.user().username().like("foo*")
    		   .or(profile.user().username().like("bar*")
    		   .or(profile.user().username().like("bas*"))));

    	Query q2 = marshal(profile.getModel());
        
        long before = System.currentTimeMillis();
        SDODataAccessClient service = new SDODataAccessClient();
        for (int count = 0; count < runs; count++) {
            DataGraph[] results = service.find(q2);
	        for (int i = 0; i < results.length; i++) {
	            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
	            //log.info(dataObject.dump());
	        }
        }
        long after = System.currentTimeMillis();
        float timeSeconds = ((float)(after - before)) / 1000;
        log.info("testSimple: " + String.valueOf(timeSeconds));
    }
    
    private Query marshal(Query query) throws JAXBException, FileNotFoundException, SAXException {
        PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                new DefaultValidationEventHandler());
        String xml = binding.marshal(query);
        //log.info("query: " + xml);
        
        FileOutputStream fos = new FileOutputStream(new File(targetDir, "test-query1.xml"));
        binding.marshal(query, fos);
        
        FileInputStream fis = new FileInputStream(new File(targetDir, "test-query1.xml"));
        Query q2 = (Query)binding.unmarshal(fis);
    	return q2;
    }
}