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
package org.plasma.platform.sdo;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.output.XMLOutputter;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.common.test.PlasmaTestSetup;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ProvisioningModelAssembler;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.query.Query;
import org.plasma.query.bind.PlasmaQueryDataBinding;
import org.plasma.sdo.DataFlavor;
import org.plasma.sdo.PlasmaDataGraphEventVisitor;
import org.plasma.sdo.PlasmaDataGraphVisitor;
import org.plasma.sdo.PlasmaDataObject;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.DASClientTestCase;
import org.plasma.sdo.helper.PlasmaXMLHelper;
import org.plasma.sdo.helper.PlasmaXSDHelper;
import org.plasma.sdo.xml.DefaultOptions;
import org.plasma.xml.schema.SchemaDataBinding;
import org.plasma.xml.schema.SchemaModelAssembler;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

import commonj.sdo.DataGraph;
import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.helper.XMLDocument;

/**
 */
public class DynamicSDOModelTest extends DASClientTestCase {
    private static Log log = LogFactory.getLog(DynamicSDOModelTest.class);

   
    public static Test suite() {
        return PlasmaTestSetup.newTestSetup(DynamicSDOModelTest.class);
    }
    
    public void setUp() throws Exception {
        super.setUp();      
    }
    
    public void tearDown() throws Exception {
        super.tearDown();	
    }
     
    public void testProfileExport() throws Exception {
        log.info("testProfileExport()");
        String targetNamespaceURI = "http://plasma.org/test/sdo/export/profile";
        Query query = ProfileQuery.createProfileQuery("user1");
        query.setName("deep-profile-query");
        query.setStartRange(0);
        query.setEndRange(5);
        File loadDir = new File(targetDir, "../../test/data/org/plasma/platform/sdo");
        File loadFile = new File(loadDir, "profile-export-dataload.xml");
        
        processModel((Query)query.getModel(), targetNamespaceURI, "profile-export",
        	loadFile);
    }    

     
    public void testExportTaxonomy() throws Exception {
        log.info("testExportTaxonomy()");
        String targetNamespaceURI = "http://plasma.org/test/sdo/export/taxonomy";
               
        Query query = TaxonomyQuery.createQuery("FEA Performance Reference Model (PRM)");
        //query.getFromClause().getEntity().setNamespaceURI(targetNamespaceURI);
        
        query.setName("deep-taxonomy-query");
        
        File loadDir = new File(targetDir, "../../test/data/org/plasma/platform/sdo");
        File loadFile = new File(loadDir, "PerformanceReferenceModel.xml");
        
        processModel(query, targetNamespaceURI, "taxonomy-export",
        	loadFile);
    } 
            
        
    private void processModel(Query query, final String targetNamespaceURI, String prefix, 
    		File loadFile) throws Exception {
        
    	validate(query, prefix);
        
        ProvisioningModelAssembler stagingAssembler = new ProvisioningModelAssembler(query, 
        		targetNamespaceURI, "ex");
        Model model = stagingAssembler.getModel();
	    ProvisioningModelDataBinding binding = 
			   new ProvisioningModelDataBinding(new DefaultValidationEventHandler());
        OutputStream provos = new FileOutputStream(new File(targetDir, prefix + "-provisioning.xml"));
		binding.marshal(model, provos);        
		provos.flush();
		provos.close();  

		SchemaModelAssembler xsdAssembler = new SchemaModelAssembler(model, targetNamespaceURI, "ex");
		xsdAssembler.setCreateNonContainmentReferenceTypes(true);
        OutputStream provXsdos = new FileOutputStream(new File(targetDir, prefix + "-provisioning.xsd"));
        try {
            SchemaDataBinding xsdBinding = new SchemaDataBinding(
                    new DefaultValidationEventHandler());
            xsdBinding.marshal(xsdAssembler.getSchema(), provXsdos);
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } finally {
            provXsdos.flush();
            provXsdos.close();
        }
        
		
        log.info("generating query XSD");
        File schemaFile = new File(targetDir, prefix + ".xsd");
        OutputStream xsdos = new FileOutputStream(schemaFile);
        PlasmaXSDHelper.INSTANCE.generate(query, 
        	targetNamespaceURI, "ex", null, 
        	xsdos);
        xsdos.flush();
        xsdos.close();  
        
        log.info("generating query XMI");
        OutputStream xmios = new FileOutputStream(new File(targetDir, prefix + ".mdxml"));
	    UMLModelAssembler assembler = new UMLModelAssembler(query, 
			targetNamespaceURI, "tns");	    
	    XMLOutputter outputer = new XMLOutputter();
	    outputer.getFormat().setIndent("    ");
	    outputer.output(assembler.getDocument(), xmios);
	    xmios.flush();
	    xmios.close();

	    // if not loaded via config, define on the fly
	    log.info("defining XSD so new export-specific types are known");
        FileInputStream xsdis = new FileInputStream(new File(targetDir, prefix + "-provisioning.xsd"));
        PlasmaXSDHelper.INSTANCE.define(xsdis, targetDir);
                
        log.info("loading data");
        DefaultOptions options = new DefaultOptions(targetNamespaceURI);
        options.setRootNamespacePrefix("xyz");
        options.setValidate(true);
        options.setFailOnValidationError(false);
        
        InputStream xmlloadis = new FileInputStream(loadFile);
        XMLDocument doc = PlasmaXMLHelper.INSTANCE.load(xmlloadis, 
        		schemaFile.toURI().toString(), options); 
        doc.setSchemaLocation(options.getRootElementNamespaceURI()
        		+ " " + prefix + ".xsd");
        DataFixUpVisitor fixUp = new DataFixUpVisitor();
        ((PlasmaDataObject)doc.getRootObject()).accept(fixUp);
        //log.info(((PlasmaDataObject)doc.getRootObject()).dump());

    	File loadOutFile = new File(targetDir, 
    			loadFile.getName().substring(0, 
    			loadFile.getName().indexOf(".")) + "-save.xml");
        OutputStream loadOutos = new FileOutputStream(loadOutFile);
        PlasmaXMLHelper.INSTANCE.save(doc,loadOutos, options);        
        loadOutos.flush();
        loadOutos.close();        

        // now reload what we just saved/generated
        options.setValidate(true);
        xmlloadis = new FileInputStream(loadOutFile);
        doc = PlasmaXMLHelper.INSTANCE.load(xmlloadis, 
        		schemaFile.toURI().toString(), options);         
        log.info(((PlasmaDataObject)doc.getRootObject()).dump());        
        
        //PlasmaDataGraphVisitor visitor = new DataFixUpVisitor();
        //((PlasmaDataGraph)doc.getRootObject().getDataGraph()).accept(visitor);
        
        // Note; what is not being addressed here is that part of a graph
        // may be "reference/lookup" data. In which case if the
        // lookup data is already populated, we'll bomb
        log.info("commiting...");
        service.commit(doc.getRootObject().getDataGraph(), "me");                              

        log.info("executing query");
        //query.getFromClause().getEntity().setNamespaceURI(targetNamespaceURI);
        DataGraph[] results = service.find(query);        
        log.info("processing "+results.length+" results");
/*        
        for (int i = 0; i < results.length; i++) {
        	PlasmaDataObject dataObject = (PlasmaDataObject)results[i].getRootObject();
        	Long id = dataObject.getLong("seqId");
        	File file = new File(targetDir, 
            		prefix + "-" + String.valueOf(id) + ".xml");
        	log.info("writing file " + file.getName());
            OutputStream xmlos = new FileOutputStream(file);
            PlasmaXMLHelper.INSTANCE.save(dataObject, 
            		targetNamespaceURI, null, xmlos);        
            xmlos.flush();
            xmlos.close();
        } 
         
        // FIXME: getting circular reference error here 
        for (int i = 0; i < results.length; i++) {
        	results[i].getRootObject().delete();
            service.commit(results[i], "me");       
        }
        
        // FIXME getting java.lang.IllegalArgumentException: found unexpected DELETED 
        //data-object (42ac2509-2bc3-4800-b69f-2237dc3ddc2f) of 
        // type http://plasma.org/test/sdo/export/taxonomy#Category
        
        log.info(((PlasmaDataObject)doc.getRootObject()).dump());
        log.info("commiting delete...");
        log.info(doc.getRootObject().getDataGraph().getChangeSummary().toString());
        doc.getRootObject().delete();
        service.commit(doc.getRootObject().getDataGraph(), "me");       
        
*/        
    }  

    private void validate(Query query, String prefix) 
    {
        try {
            PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                    new DefaultValidationEventHandler());
            log.info("marshaling query: " + query.getName());
            OutputStream qryos = new FileOutputStream(new File(targetDir, prefix + "-query.xml"));
            binding.marshal(query, qryos);
            qryos.flush();
            qryos.close();  
            String xml = binding.marshal(query);
            query = (Query)binding.validate(xml); 
            log.info("validated marshaled query: " + query.getName());
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } 
    	
    }
    
    //log.info(((PlasmaDataObject)doc.getRootObject()).dump());
    class DataFixUpVisitor implements PlasmaDataGraphVisitor {
    	
    	
		public void visit(DataObject target, DataObject source,
				String sourcePropertyName, int level) {
			for (Property p : target.getType().getProperties()) {
				PlasmaProperty property = (PlasmaProperty)p;
				if (!property.getType().isDataType())
					continue;
				if (property.getDataFlavor().ordinal() != DataFlavor.string.ordinal())
					continue;
				String value = target.getString(property);
				if (value == null) {
					if (!property.isNullable())
						target.setString(property, "foo");
					continue;
				}
				if (property.getValueConstraint() != null && property.getValueConstraint().getMaxLength() != null) {
					String maxStr = property.getValueConstraint().getMaxLength();
					int max = Integer.valueOf(maxStr);
					target.setString(property, truncate(value, max));
				}
			}
		}
		
		private String truncate(String value, int max) {
			
			String result = value;
			if (value.length() > max)
				result = result.substring(0, max-3) + "...";
			return result;
		}
    }
     
	class DebugEventVisitor implements PlasmaDataGraphEventVisitor {
		public void start(PlasmaDataObject target, PlasmaDataObject source, String sourcePropertyName, int level){
            if (source == null)
                log.info("start event: " + target.getType().getName() 
                        + "("+((PlasmaNode)target).getUUIDAsString()+")");
            else
                log.info("start event: " + source.getType().getName() 
                        + "("+((PlasmaNode)source).getUUIDAsString()+ ")."
                        + sourcePropertyName + "->"
                        + target.getType().getName() + "("+((PlasmaNode)target).getUUIDAsString()+")");
		}		
		public void end(PlasmaDataObject target, PlasmaDataObject source, String sourcePropertyName, int level){
            if (source == null)
                log.info("end event: " + target.getType().getName() 
                        + "("+((PlasmaNode)target).getUUIDAsString()+")");
            else
                log.info("end event: " + source.getType().getName() 
                        + "("+((PlasmaNode)source).getUUIDAsString()+ ")."
                        + sourcePropertyName + "->"
                        + target.getType().getName() + "("+((PlasmaNode)target).getUUIDAsString()+")");
		}		
	}
	
	class DebugVisitor implements PlasmaDataGraphVisitor {
		public void visit(DataObject target, DataObject source,
				String sourcePropertyName, int level) {
            if (source == null)
                log.info("visit: " + target.getType().getName() 
                        + "("+((PlasmaNode)target).getUUIDAsString()+")");
            else
                log.info("visit: " + source.getType().getName() 
                        + "("+((PlasmaNode)source).getUUIDAsString()+ ")."
                        + sourcePropertyName + "->"
                        + target.getType().getName() + "("+((PlasmaNode)target).getUUIDAsString()+")");
		}		
	}
    
}