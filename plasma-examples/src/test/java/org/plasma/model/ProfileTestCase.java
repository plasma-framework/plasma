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
package org.plasma.model;



import java.io.File;
import java.util.List;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.FileArtifact;
import org.plasma.common.test.PlasmaTest;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.common.xslt.XSLTUtils;
import org.plasma.platform.sdo.common.OptimisticConcurrent;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.profile.SDOConcurrent;
import org.plasma.sdo.profile.SDODerivation;
import org.plasma.sdo.profile.SDOEnumerationConstraint;
import org.plasma.sdo.repository.PlasmaRepository;

import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Extension;
import org.modeldriven.fuml.repository.Property;
import org.modeldriven.fuml.repository.Stereotype;


/**
 * 
 */
public class ProfileTestCase extends PlasmaTest {
    private static Log log = LogFactory.getLog(ProfileTestCase.class);
    
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(ProfileTestCase.class);
    }
    
    public void setUp() throws Exception {
    }
    
    public void testExtension() throws Exception {    
        log.info("testExtension");

        
        Classifier extension = PlasmaRepository.getInstance().findClassifier("Extension");
        assertTrue("could not find class for 'Extension'", extension != null);
        log.info("----------------------------");
        log.info("properties for Extension");
        List<Property> props = ((Class_)extension).getAllProperties();
        for (Property prop : props) {
        	log.info(prop.getClass_().getName() + "." + prop.getName() 
        			+ " (required=" + String.valueOf(
        			        prop.isRequired()) + 
        				")");
        	Property opposite = prop.getOpposite();
            if (opposite != null)
            	log.info("opposite: " + prop.getClass_().getName() + "." + prop.getName() + "->" 
            			+ opposite.getClass_().getName() + "." + opposite.getName());
        }      

        Classifier stereotype = PlasmaRepository.getInstance().findClassifier("Stereotype");
        assertTrue("could not find class for 'Stereotype'", stereotype != null);        
        log.info("----------------------------");
        log.info("properties for Stereotype");
        props = ((Class_)stereotype).getAllProperties();
        for (Property prop : props) {
            log.info(prop.getClass_().getName() + "." + prop.getName() 
                    + " (required=" + String.valueOf(
                            prop.isRequired()) + 
                        ")");
            Property opposite = prop.getOpposite();
            if (opposite != null)
                log.info("opposite: " + prop.getClass_().getName() + "." + prop.getName() + "->" 
                        + opposite.getClass_().getName() + "." + opposite.getName());
        }      
        log.info("----------------------------");

        Property nameProp = ((Class_)stereotype).findProperty("name");
        assertTrue("No ownedAttribute 'name' found for class 'Stereotype'", nameProp != null);
        
        Property visibilityProp = ((Class_)stereotype).findProperty("visibility");
        assertTrue("No ownedAttribute 'visibility' found for class 'Stereotype'", visibilityProp != null);
        
        Property ownedAttributeProp = ((Class_)stereotype).findProperty("ownedAttribute");
        assertTrue("No ownedAttribute 'ownedAttribute' found for class 'Stereotype'", ownedAttributeProp != null);
        
        Property generalizationProp = ((Class_)stereotype).findProperty("generalization");
        assertTrue("No ownedAttribute 'generalization' found for class 'Stereotype'", generalizationProp != null);
        
       
        log.info("done");
    }  
 
    
    
    public void testLoad2() throws Exception { 

        String namespaceURI = "http://www.magicdraw.com/schemas/Plasma_SDO_Profile.xmi";

        // after profile load ensure a 'Property' is still a UML (default NS) property
        Classifier propertyClassifier = PlasmaRepository.getInstance().findClassifier("Property");
        assertTrue("could not find class for 'Property'", propertyClassifier != null);
        Property nameProp = ((Class_)propertyClassifier).findProperty("name");
        assertTrue("No ownedAttribute 'name' found for class 'Property'", nameProp != null);
        Property visibilityProp = ((Class_)propertyClassifier).findProperty("visibility");
        assertTrue("No ownedAttribute 'visibility' found for class 'Property'", visibilityProp != null);

        Classifier enumConstraintClassifier = PlasmaRepository.getInstance().findClassifier(
                namespaceURI + "#" + SDOEnumerationConstraint.class.getSimpleName());
        assertTrue("could not find class for '" 
        		+ SDOEnumerationConstraint.class.getSimpleName()+ "'", enumConstraintClassifier != null);
        assertTrue(enumConstraintClassifier instanceof Stereotype);
        Property restrictionProp = ((Class_)enumConstraintClassifier).findProperty(SDOEnumerationConstraint.VALUE);
        assertTrue("No ownedAttribute "+SDOEnumerationConstraint.VALUE+" found for class '"
        		+SDOEnumerationConstraint.class.getSimpleName()+"'", restrictionProp != null);
        Classifier restrictionType = restrictionProp.getType();
        assertTrue("Enumeration expected as type for restriction property", 
        		restrictionType.getName().equals("Enumeration"));
        
        
        Classifier plasmaTypeClassifier = PlasmaRepository.getInstance().findClassifier(
                namespaceURI + "#" + SDODerivation.class.getSimpleName());
        assertTrue("could not find class for 'SDOType'", plasmaTypeClassifier != null);
        assertTrue(plasmaTypeClassifier instanceof Stereotype);
 
        Classifier plasmaAliasClassifier = PlasmaRepository.getInstance().findClassifier(
                namespaceURI + "#" + SDOAlias.class.getSimpleName());
        assertTrue("could not find class for 'SDOAlias'", plasmaAliasClassifier != null);
        assertTrue(plasmaAliasClassifier instanceof Stereotype);
        Property physicalNameProp = ((Class_)plasmaAliasClassifier).findProperty("physicalName");
        assertTrue("No ownedAttribute 'physicalName' found for class 'SDOAlias'", physicalNameProp != null);
        
        // find by package qualified name
        //Classifier applicationClassifier = PlasmaRepository.getInstance().findClassifier("us.fed.fs.bao.appinv.core.Application");
        //assertTrue("could not find class 'Application' classifier by package qualified name", 
        //        applicationClassifier != null);

        // find by namespace qualified name
        String id = OptimisticConcurrent.NAMESPACE_URI + "#" + OptimisticConcurrent.ETY_OPTIMISTIC_CONCURRENT;
        Classifier appClassifier = PlasmaRepository.getInstance().findClassifier(id);
        assertTrue("could not find "+id+" classifier by artifact namespace qualified name", 
        		appClassifier != null);
        
        List<Extension> extensions = PlasmaRepository.getInstance().getExtensions(appClassifier);
        assertTrue(extensions != null);
        assertTrue(extensions.size() == 1);
        Extension extension = extensions.get(0);
        
        List<Stereotype> stereotypes = PlasmaRepository.getInstance().getStereotypes(appClassifier);
        assertTrue(stereotypes != null);
        assertTrue(stereotypes.size() == 1);
        Stereotype stereotype = stereotypes.get(0);
        
        List<Property> props = ((Class_)appClassifier).getAllProperties();
        for (Property prop : props) {
            log.info(prop.getClass_().getName() + "." + prop.getName() 
                    + " (required=" + String.valueOf(
                            prop.isRequired()) + 
                        ")");
            List<Stereotype> stereotypes2 = PlasmaRepository.getInstance().getStereotypes(prop);
            assertTrue(stereotypes2 != null);
            for (Stereotype st : stereotypes2) {
            	if (st.getDelegate() instanceof SDOConcurrent) {
            		SDOConcurrent concurrent = (SDOConcurrent)st.getDelegate();
            		assertTrue("expected mandatory property 'type' on stereotype, " + concurrent.getClass().getName(), 
            				concurrent.getType() != null);
            		assertTrue("expected mandatory property 'dataFlavor' on stereotype, " + concurrent.getClass().getName(), 
            				concurrent.getDataFlavor() != null);
            	}
            }
        }
        log.info("done");
        
    } 
 }