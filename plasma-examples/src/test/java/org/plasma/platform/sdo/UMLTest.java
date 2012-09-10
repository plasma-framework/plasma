package org.plasma.platform.sdo;




import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.platform.sdo.uml.Association;
import org.plasma.platform.sdo.uml.Classifier;
import org.plasma.platform.sdo.uml.Clazz;
import org.plasma.platform.sdo.uml.DataType;
import org.plasma.platform.sdo.uml.Enumeration;
import org.plasma.platform.sdo.uml.EnumerationLiteral;
import org.plasma.platform.sdo.uml.Generalization;
import org.plasma.platform.sdo.uml.InstanceSpecification;
import org.plasma.platform.sdo.uml.LiteralInteger;
import org.plasma.platform.sdo.uml.LiteralString;
import org.plasma.platform.sdo.uml.Package;
import org.plasma.platform.sdo.uml.PackageableType;
import org.plasma.platform.sdo.uml.Property;
import org.plasma.platform.sdo.uml.Slot;
import org.plasma.platform.sdo.uml.ValueSpecification;
import org.plasma.platform.sdo.uml.VisibilityKind;
import org.plasma.platform.sdo.uml.query.QClazz;
import org.plasma.query.Query;
import org.plasma.sdo.PlasmaDataGraph;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.DASClientTestCase;
import org.plasma.sdo.helper.PlasmaDataFactory;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import commonj.sdo.DataGraph;
import commonj.sdo.Type;

/**
 * Performs and tests various SDO CRUD operations.
 */
public class UMLTest extends DASClientTestCase {
    private static Log log = LogFactory.getLog(UMLTest.class);

    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(UMLTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();
    } 
    
    public void testInsertUpdateDelete()       
    {
    	int countBefore = getClassCount();
    	
    	DataGraph dataGraph = PlasmaDataFactory.INSTANCE.createDataGraph();
        dataGraph.getChangeSummary().beginLogging(); // log changes from this point
    	Type rootType = PlasmaTypeHelper.INSTANCE.getType(Package.class);
    	PlasmaDataObject root = (PlasmaDataObject)dataGraph.createRootObject(rootType);
    	
    	Package pkg = (Package)root;
    	pkg.setName("my meta package");
    	
    	PackageableType stringType = pkg.createPackageableType();
    	Classifier stringClassifier = stringType.createClassifier();
    	DataType stringDatatype = stringClassifier.createDataType();
    	stringClassifier.setName("String");
    	
    	PackageableType intType = pkg.createPackageableType();
    	Classifier intClassifier = intType.createClassifier();
    	DataType intDatatype = intClassifier.createDataType();
    	intClassifier.setName("int");
    	    	
    	PackageableType floatType = pkg.createPackageableType();
    	Classifier floatClassifier = floatType.createClassifier();
    	DataType floatDatatype = floatClassifier.createDataType();
    	floatClassifier.setName("float");

    	// create a small class hierarchy
    	PackageableType superclassType = pkg.createPackageableType();    	
    	Classifier superclassClassifier = superclassType.createClassifier();
    	Clazz superclass = superclassClassifier.createClazz();
    	superclassClassifier.setName("SuperClass");    	
    	
    	PackageableType subclassType = pkg.createPackageableType();    	
    	Classifier subclassClassifier = subclassType.createClassifier();
    	Clazz subclass = subclassClassifier.createClazz();
    	subclassClassifier.setName("SubClass"); 
    	
    	Generalization gen = subclass.createGeneralization();
    	gen.setTargetClassifier(superclass);
    	gen.setName(subclassClassifier.getName() + "->" + superclassClassifier.getName());
    	
    	// add properties to subclass
    	Property floatProp = subclass.createOwnedAttribute();
    	floatProp.setName("quantity");
    	floatProp.setVisibility(VisibilityKind.PUBLIC.getInstanceName()); 
    	floatProp.setDataType(floatDatatype);  
    	floatProp.setLowerValue(1);
       	floatProp.setUpperValue("1");
           	
    	Property stringProp = subclass.createOwnedAttribute();
    	stringProp.setName("name");
    	stringProp.setVisibility(VisibilityKind.PUBLIC.getInstanceName()); 
    	stringProp.setDataType(stringDatatype);
    	stringProp.setLowerValue(1);
    	stringProp.setUpperValue("1");
    	
    	// add an association property
    	Property parentAssocProp = subclass.createOwnedAttribute();
    	parentAssocProp.setName("parent");
    	parentAssocProp.setVisibility(VisibilityKind.PUBLIC.getInstanceName()); 
    	parentAssocProp.setLowerValue(1);
    	parentAssocProp.setUpperValue("1");
    	
    	Property childAssocProp = subclass.createOwnedAttribute();
    	childAssocProp.setName("child");
    	childAssocProp.setVisibility(VisibilityKind.PUBLIC.getInstanceName()); 
    	childAssocProp.setLowerValue(0);
    	childAssocProp.setUpperValue("*");
    	
    	PackageableType associationType = pkg.createPackageableType();    	
    	Classifier associationClassifier = associationType.createClassifier();
    	associationClassifier.setName(parentAssocProp.getName() + "->" + childAssocProp.getName());
    	Association association = associationClassifier.createAssociation();
    	association.setName(parentAssocProp.getName() + "->" + childAssocProp.getName());
    	association.addMemberEnd(parentAssocProp);
    	association.addMemberEnd(childAssocProp);

    	parentAssocProp.setAssociation(association);
    	childAssocProp.setAssociation(association);

    	PackageableType enumType = pkg.createPackageableType();
    	Classifier enumClassifier = enumType.createClassifier();
    	DataType enumDatatype = enumClassifier.createDataType();
    	Enumeration enum1 = enumDatatype.createEnumeration();
    	enumClassifier.setName("enum 1");
    	EnumerationLiteral l1 = enum1.createOwnedLiteral();
    	l1.setName("l1");
    	EnumerationLiteral l2 = enum1.createOwnedLiteral();
    	l2.setName("l2");

    	// create an instance spec
    	PackageableType instanceSpecType = pkg.createPackageableType();    	
    	InstanceSpecification instanceSpec = subclass.createInstanceSpecification();
    	instanceSpec.setPackageableType(instanceSpecType);
    	instanceSpec.setClassifier(subclass);
    	
    	Slot floatSlot = instanceSpec.createSlot();
    	floatSlot.setDefiningFeature(floatProp);    	
    	
    	ValueSpecification floatValueSpec = floatSlot.createValue();
    	floatValueSpec.setName("NA");
    	
    	LiteralInteger literalInt = floatValueSpec.createLiteralInteger();
    	literalInt.setValue(23);
    	
    	Slot stringSlot = instanceSpec.createSlot();
    	stringSlot.setDefiningFeature(stringProp);
    	ValueSpecification stringValueSpec = stringSlot.createValue();
    	stringValueSpec.setName("NA");
    	
    	LiteralString literalString = stringValueSpec.createLiteralString();
    	literalString.setValue("foo bar");   	

    	
    	
    	log.info(root.dump());    	
    	
    	        
    	//save the graph
        service.commit(dataGraph, "test-user");
                
        subclassClassifier.setName("temp class updated"); // make a change
        
        service.commit(dataGraph, "test-user2");
        //assertTrue(foo.getCreatedByName().equals("test-user")); //  check did not change
        
    	int countAfterInsert = getClassCount();
    	assertTrue(countBefore + 2 == countAfterInsert);
    	
    	
    	DataGraph[] results = service.find(createGraphQuery(subclass.getSeqId()));
    	assertTrue(results != null);
    	assertTrue(results.length == 1);
    	log.info(((PlasmaDataGraph)results[0]).dump());
    	
       
        //Delete the graph and commit 
    	pkg.delete(); // delete whole package by containment
        service.commit(dataGraph, "test-user");        

        // ensure we have the same number of entities, after delete
        int countAfterDelete = getClassCount();
    	
    	assertTrue(countBefore == countAfterDelete);
    }

    
    
	public static Query createGraphQuery(Long seqId) {
		QClazz query = QClazz.newQuery();
		query.select(query.wildcard())
		     .select(query.ownedAttribute().wildcard());
		query.where(query.seqId().eq(seqId));		
        return query;		
	}		
    
     
    protected int getClassCount() {    	
    	QClazz query = QClazz.newQuery();
    	query.select(query.wildcard());
        return service.count(query);
    }

    
}