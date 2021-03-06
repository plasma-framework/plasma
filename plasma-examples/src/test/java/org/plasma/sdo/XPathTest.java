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

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.platform.sdo.categorization.Category;
import org.plasma.platform.sdo.categorization.Taxonomy;
import org.plasma.platform.sdo.personalization.Person;
import org.plasma.platform.sdo.personalization.Profile;
import org.plasma.platform.sdo.personalization.User;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Type;

/**
 * Query tests which return data-graphs of various depths. 
 */
public class XPathTest extends DASClientTestCase {
    private static Log log = LogFactory.getLog(XPathTest.class);
    private Profile profile; 
    private Taxonomy taxonomy;
    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(XPathTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();      
    	DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
	    dataGraph.getChangeSummary().beginLogging(); // log changes from this point  
	    Type rootType = PlasmaTypeHelper.INSTANCE.getType(Profile.class);
	    profile = (Profile)dataGraph.createRootObject(rootType); 
	    User user = profile.createUser();
	    user.setUsername("user1");
	    
	    Person person1 = user.createPerson();
	    person1.setLastName("Kilmer");
	    person1.setFirstName("Val");
	    person1.setSsn("333445555");
	    
	    Person person2 = user.createPerson();
	    person2.setLastName("Thornburg");
	    person2.setFirstName("Wilber");
	    person2.setSsn("111334444");
 	    
	    Person person3 = user.createPerson();
	    person3.setLastName("Thompson");
	    person3.setFirstName("Fred");
	    person3.setSsn("888334444");

    	dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
	    dataGraph.getChangeSummary().beginLogging(); // log changes from this point  
	    rootType = PlasmaTypeHelper.INSTANCE.getType(Taxonomy.class);
	    taxonomy = (Taxonomy)dataGraph.createRootObject(rootType);
	    taxonomy.setVersion("1.1");
	    
	    Category root = taxonomy.createCategory();
	    root.setId(1);
	    root.setName("Test Taxonomy");
	    root.setDefinition("Definition for root cat");
	    
	    // level 1
	    Category child1 = root.createChild();
	    child1.setId(11);
	    child1.setName("Child1");
	    child1.setDefinition("Definition for Child1 cat");
	    Category child2 = root.createChild();
	    child2.setId(22);
	    child2.setName("Child2");
	    child2.setDefinition("Definition for Child2 cat");
	    Category child3 = root.createChild();
	    child3.setId(33);
	    child3.setName("Child3");
	    child3.setDefinition("Definition for Child3 cat");
	    
	    // level 2
	    Category child21 = child2.createChild();
	    child21.setId(221);
	    child21.setName("Child21");
	    child21.setDefinition("Definition for Child21 cat");
	    Category child22 = child2.createChild();
	    child22.setId(222);
	    child22.setName("Child22");
	    child22.setDefinition("Definition for Child22 cat");
	    
    }
/* 
    public void testRoot() {
        
        log.info("testRoot()");
	    
	    DataObject result = (DataObject)profile.get("/");
	    assertTrue("expected result", result != null);
    }  
*/ 
          
    public void testGet() {
	    DataObject result = (DataObject)profile.get("user/person[firstName='Fred']");
	    assertTrue("expected result", result != null);
	    Person resultPerson = (Person)result;
	    assertTrue("expected 'Fred' as firstName", 
	    		"Fred".equals(resultPerson.getFirstName()));
    } 
    
    public void testGetMulti() {
    	
	    Object result = profile.get("user/person[firstName='Fred' or lastName='Kilmer']");
	    assertTrue("expected result", result != null);
	    assertTrue("expected result List not, " + result.getClass().getName(), 
	    		result instanceof List);
	    List list = (List)result;
	    assertTrue("expected 2 results", list.size() == 2);
	    Person p1 = (Person)list.get(0);
	    Person p2 = (Person)list.get(1);
	    assertTrue(p1.getFirstName().equals("Fred") ||
	    		p1.getLastName().equals("Kilmer"));
	    assertTrue(p2.getFirstName().equals("Fred") ||
	    		p2.getLastName().equals("Kilmer"));
    } 
   
    public void testPropertyValueGet() {
	    Object result = profile.get("user/person[firstName='Fred']/lastName");
	    assertTrue("expected result", result != null);
	    assertTrue("expected result String not, " + result.getClass().getName(), 
	    		result instanceof String);
	    assertTrue("expected 'Thompson' as lastName", 
	    		"Thompson".equals(result));
	    Object result2 = profile.get("user/person[@firstName='Fred']/@lastName");
	    assertTrue("expected result String not, " + result2.getClass().getName(), 
	    		result2 instanceof String);
	    assertTrue("expected 'Thompson' as lastName", 
	    		"Thompson".equals(result2));
    }  
      
    
    public void testSet() {
    	
	    profile.set("user/person[lastName='Kilmer']/firstName",
	    		"Skippy");
	    Object result = profile.get("user/person[lastName='Kilmer' and firstName='Skippy']");
	    assertTrue("expected result", result != null);
	    assertTrue("expected instanceof Person not, "
	    		+ result.getClass().getName(), result instanceof Person);	    
    } 

    public void testSetMulti() {
    	
	    profile.set("user/person[firstName='Fred' or lastName='Kilmer']/firstName",
	    		"Skippy");
	    Object result = profile.get("user/person[firstName='Skippy']");
	    assertTrue("expected result", result != null);
	    assertTrue("expected result List not, " + result.getClass().getName(), 
	    		result instanceof List);
	    List list = (List)result;
	    assertTrue("expected 2 results", list.size() == 2);
	    Person p1 = (Person)list.get(0);
	    Person p2 = (Person)list.get(1);
	    assertTrue(p1.getFirstName().equals("Skippy"));
	    assertTrue(p2.getFirstName().equals("Skippy"));
    } 
    
    public void testPositionXPathSyntax() {
	    DataObject result = (DataObject)profile.get("user[position()=1]/person[position()=2]");
	    assertTrue("expected result", result != null);
	    Person resultPerson = (Person)result;
	    assertTrue("expected 'Wilber' as firstName", 
	    		"Wilber".equals(resultPerson.getFirstName()));
    }  
    
    public void testPositionSDOSyntax() {
	    DataObject result = (DataObject)profile.get("user/person.1");
	    assertTrue("expected result", result != null);
	    Person resultPerson = (Person)result;
	    assertTrue("expected 'Wilber' as firstName", 
	    		"Wilber".equals(resultPerson.getFirstName()));
    }  
    
    public void testLast() {
	    DataObject result = (DataObject)profile.get("user/person[last()-1]");
	    assertTrue("expected result", result != null);
	    Person resultPerson = (Person)result;
	    assertTrue("expected 'Wilber' as firstName", 
	    		"Wilber".equals(resultPerson.getFirstName()));
    }  
       
    public void testGetInt() {
	    int result = taxonomy.getInt("category/child[name='Child2']/id");	    
	    assertTrue("expected result 22", result == 22);
    } 
    
    public void testSetInt() {
	    taxonomy.setInt("category/child[name='Child2']/id", 444);
	    int result = taxonomy.getInt("category/child[name='Child2']/id");	    
	    assertTrue("expected result 444", result == 444);
    }  
     
    public void testGetString() {
	    String result = taxonomy.getString("//child[name='Child2']/definition");	    
	    assertTrue("expected result ", result != null);
	    assertTrue("expected result 'Definition for Child2 cat'", 
	    		result.equals("Definition for Child2 cat"));
    } 
    
    public void testSetString() {
	    taxonomy.setString("category/child[name='Child2']/definition", "New Child2 cat definition");
	    String result = taxonomy.getString("category/child[name='Child2']/definition");	    
	    assertTrue("expected result ", result != null);
	    assertTrue("expected result 'New Child2 cat definition'", 
	    		result.equals("New Child2 cat definition"));
    }  
     
    public void testGetUpperCase() {
    	Object result = profile.get("user/person[upper-case(firstName)='FRED']");
	    assertTrue("expected result", result != null);
	    assertTrue("expected result DataObject", result instanceof DataObject);
		DataObject resultDataObject = (DataObject)result;
	    assertTrue("expected result Person", resultDataObject instanceof Person);
	    Person resultPerson = (Person)resultDataObject;
	    assertTrue("expected 'Fred' as firstName", 
	    		"Fred".equals(resultPerson.getFirstName()));
    } 
    
    public void testGetLowerCase() {
    	Object result = profile.get("user/person[lower-case(firstName)='fred']");
	    assertTrue("expected result", result != null);
	    assertTrue("expected result DataObject", result instanceof DataObject);
		DataObject resultDataObject = (DataObject)result;
	    assertTrue("expected result Person", resultDataObject instanceof Person);
	    Person resultPerson = (Person)resultDataObject;
	    assertTrue("expected 'Fred' as firstName", 
	    		"Fred".equals(resultPerson.getFirstName()));
    }     
    
    public void testGetStartsWith() {
    	Object result = profile.get("user/person[starts-with(lastName, 'T')]");
	    assertTrue("expected result", result != null);
	    assertTrue("expected result List", result instanceof List);
	    List<Object> resultList = (List<Object>)result;
	    for (Object obj : resultList) {
		    assertTrue("expected result DataObject", obj instanceof DataObject);
			DataObject resultDataObject = (DataObject)obj;
		    assertTrue("expected result Person", resultDataObject instanceof Person);
		    Person resultPerson = (Person)resultDataObject;
		    assertTrue("expected last name starts-with 'T'", 
		    		resultPerson.getLastName().startsWith("T"));
	    }
    } 
    
    public void testGetLowerCaseStartsWith() {
    	Object result = profile.get("user/person[starts-with(lower-case(lastName), 't')]");
	    assertTrue("expected result", result != null);
	    assertTrue("expected result List", result instanceof List);
	    List<Object> resultList = (List<Object>)result;
	    for (Object obj : resultList) {
		    assertTrue("expected result DataObject", obj instanceof DataObject);
			DataObject resultDataObject = (DataObject)obj;
		    assertTrue("expected result Person", resultDataObject instanceof Person);
		    Person resultPerson = (Person)resultDataObject;
		    assertTrue("expected last name starts-with 'T'", 
		    		resultPerson.getLastName().startsWith("T"));
	    }
    } 
    
    
    
    
    
    
}