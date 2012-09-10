package org.plasma.platform.sdo;




import javax.xml.bind.JAXBException;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.platform.sdo.personalization.Element;
import org.plasma.platform.sdo.personalization.ElementType;
import org.plasma.platform.sdo.personalization.Person;
import org.plasma.platform.sdo.personalization.Profile;
import org.plasma.platform.sdo.personalization.ProfileElementSetting;
import org.plasma.platform.sdo.personalization.Role;
import org.plasma.platform.sdo.personalization.Setting;
import org.plasma.platform.sdo.personalization.User;
import org.plasma.platform.sdo.personalization.UserRole;
import org.plasma.query.Query;
import org.plasma.query.bind.PlasmaQueryDataBinding;
import org.plasma.query.model.From;
import org.plasma.query.model.Select;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.DASClientTestCase;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.helper.PlasmaTypeHelper;
import org.xml.sax.SAXException;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Type;

/**
 * Performs and tests various SDO CRUD operations.
 */
public class ProfileTest extends DASClientTestCase {
    private static Log log = LogFactory.getLog(ProfileTest.class);

    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(ProfileTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();
    }
 
    public void testProfileQuery() throws Exception {
        log.info("testProfileQuery()");
        
        int num = 1;
        long before = System.currentTimeMillis();        
        
        Query query = ProfileQuery.createProfileQuery("skippy");
        try {
            PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                    new DefaultValidationEventHandler());
            String xml = binding.marshal(query.getModel());
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
        
        DataGraph[] results = service.find(query);
        for (int i = 0; i < results.length; i++) {
            PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
            log.info(dataObject.dump());
        }
        long after = System.currentTimeMillis();
        float totalSeconds = (after - before) / 1000;
        float averageSecondsPerGraph = ((float)(after - before) / num) / 1000;
        log.info("total seconds: " + totalSeconds + " average seconds per graph: " + averageSecondsPerGraph);        
    }
 
     public void testPersonGraphInsertUpdateDelete()       
    {
    	int personCountBefore = getPersonCount();
    	
    	DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
        dataGraph.getChangeSummary().beginLogging(); // log changes from this point
    	Type rootType = PlasmaTypeHelper.INSTANCE.getType(Person.class);
    	DataObject root = dataGraph.createRootObject(rootType);
    	
    	Person person = (Person)root;
    	//person.setUUID(UUID.randomUUID().toString());
    	person.setFirstName("Westie");
    	person.setLastName("Paw");
    	//person.setEffectiveDate(new Date());
    	
    	User user = person.createUser();
    	user.setUsername("wpaw");
        
    	//save the graph
        service.commit(dataGraph, "test-user");
        
        //assertTrue("test-user".equals(person.getCreatedByName()));
        //assertTrue(person.getCreatedDate() != null);        
        
        // update and commit
    	person.setFirstName("Baby");
    	person.setLastName("Dog");
    	user.setUsername("bdog");
        service.commit(dataGraph, "test-user2");
        
    	int personCountAfterInsert = getPersonCount();
    	assertTrue(personCountBefore + 1 == personCountAfterInsert);
       
        //Delete the graph and commit 
        person.delete(); // delete ProjectElement by SDO containment
        service.commit(dataGraph, "test-user");        

        // ensure we have the same number of entities, after delete
        int personCountAfterDelete = getPersonCount();
    	
    	assertTrue(personCountBefore == personCountAfterDelete);
    }
     
    public void testProfileGraphInsertUpdateDelete()       
    {
    	int userCountBefore = getUserCount();
    	
    	DataGraph userDataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
    	userDataGraph.getChangeSummary().beginLogging(); // log changes from this point
    	Type rootType = PlasmaTypeHelper.INSTANCE.getType(User.class);
    	User user = (User)userDataGraph.createRootObject(rootType);  	
    	//person.setUUID(UUID.randomUUID().toString());
    	//person.setFirstName("Westie");
    	//person.setLastName("Paw");
    	//User user = person.createUser();
    	user.setUsername("wpaw");
    	
    	UserRole personRole = user.createUserRole();
    	Role role = personRole.createRole();
    	//role.setUUID(UUID.randomUUID().toString());
    	role.setName("superuser");

    	//save the person graph, use person as reference obj
        service.commit(userDataGraph, "test-user");
        
        ((PlasmaDataObject)user).setDataGraph(null); // so we can use this in another graph
        //((PlasmaDataObject)person).setDataGraph(null); // so we can use this in another graph
        ((PlasmaDataObject)role).setDataGraph(null); // so we can use this in another graph
        
    	int userCountAfterInsert = getUserCount();
    	assertTrue(userCountBefore + 1 == userCountAfterInsert);
        
    	    	
    	// create a profile graph and link to above 
    	// "reference" data objects    	    	
    	int profileCountBefore = getProfileCount();
    	int elementCountBefore = getElementCount();

    	DataGraph profileDataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
        profileDataGraph.getChangeSummary().beginLogging(); // log changes from this point
    	rootType = PlasmaTypeHelper.INSTANCE.getType(Profile.class);
    	Profile profile = (Profile)profileDataGraph.createRootObject(rootType);  	    	    	
    	profile.setUser(user);
    	ProfileElementSetting pes = profile.createProfileElementSetting();
    	pes.setRole(role);
    	Element element = pes.createElement();
    	//element.setUUID(UUID.randomUUID().toString());
    	element.setName("foo page");
    	element.setElementType(ElementType.PAGE.getInstanceName());
    	Setting setting = pes.createSetting();
    	//setting.setUUID(UUID.randomUUID().toString());
    	setting.setName("foo");
    	setting.setValue("bar");
        
    	//save the profile graph
        service.commit(profileDataGraph, "test-user2");

    	int profileCountAfterInsert = getProfileCount();
    	assertTrue(profileCountBefore + 1 == profileCountAfterInsert);

    	int elementCountAfterInsert = getElementCount();
    	assertTrue(elementCountBefore + 1 == elementCountAfterInsert);
    	
    	// remove ref objects and delete profile graph
    	profile.unsetUser();
    	pes.unsetRole();
    	profile.delete();
        service.commit(profileDataGraph, "test-user"); 
        
        ((PlasmaDataObject)user).setDataGraph(userDataGraph); 
        ((PlasmaDataObject)role).setDataGraph(userDataGraph); 
    	
        //Delete person the graph and commit 
        user.delete(); // delete person
        service.commit(userDataGraph, "test-user");        

        // ensure we have the same number of entities, after delete
        int personCountAfterDelete = getPersonCount();    	
    	assertTrue(userCountBefore == personCountAfterDelete);
    	
    	int profileCountAfterDelete = getProfileCount();
    	assertTrue(profileCountBefore == profileCountAfterDelete);

    	int elementCountAfterDelete = getElementCount();
    	assertTrue(elementCountBefore == elementCountAfterDelete);
    } 
    
    protected int getPersonCount() {
        Query query = new org.plasma.query.model.Query(
        	new Select("*"), 
        	new From(Person.ETY_PERSON,
        			 Person.NAMESPACE_URI));
        return service.count(query);
    }
    
    protected int getUserCount() {
        Query query = new org.plasma.query.model.Query(
        	new Select("*"), 
        	new From(User.ETY_USER,
        			User.NAMESPACE_URI));
        return service.count(query);
    }

    protected int getProfileCount() {
        Query query = new org.plasma.query.model.Query(
        	new Select("*"), 
        	new From(Profile.ETY_PROFILE,
        			Profile.NAMESPACE_URI));
        return service.count(query);
    }
    
    protected int getElementCount() {
        Query query = new org.plasma.query.model.Query(
        	new Select("*"), 
        	new From(Element.ETY_ELEMENT,
        			Element.NAMESPACE_URI));
        return service.count(query);
    }
    
}