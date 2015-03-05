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
package org.plasma.sdo.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.transform.Source;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.plasma.common.bind.BindingValidationEventHandler;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.config.PlasmaConfig;
import org.plasma.provisioning.Model;
import org.plasma.provisioning.ModelAppInfo;
import org.plasma.provisioning.ProvisioningModelAssembler;
import org.plasma.provisioning.ProvisioningModelDataBinding;
import org.plasma.provisioning.SchemaProvisioningModelAssembler;
import org.plasma.provisioning.Class;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.query.Query;
import org.plasma.query.bind.PlasmaQueryDataBinding;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.repository.PlasmaRepository;
import org.plasma.xml.schema.OpenAttrs;
import org.plasma.xml.schema.Appinfo;
import org.plasma.xml.schema.Annotation;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SchemaModelAssembler;
import org.plasma.xml.schema.SchemaDataBinding;
import org.plasma.xml.uml.MDModelAssembler;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.XSDHelper;

public class PlasmaQueryHelper {
    private static Log log = LogFactory.getLog(
    		PlasmaQueryHelper.class); 

    static public volatile PlasmaQueryHelper INSTANCE = initializeInstance();

    private PlasmaQueryHelper() {       
    }
     
    private static synchronized PlasmaQueryHelper initializeInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new PlasmaQueryHelper();
        return INSTANCE;
    }
    
    /**
     * Define a Query as Types.
     * The Types are available through TypeHelper and DataGraph getType() methods.
     * Same as define(new StringReader(xsd), null)
     * @param queryXML the XML Query.
     * @param supplierUri the URI for the SDO namespace which supplied the types used in the given query 
     * @return the defined Types.
     * @throws IllegalArgumentException if the Types could not be defined.
     */
    public List<Type> define(String queryXML, String supplierUri) {
 	   if (log.isDebugEnabled())
		   log.debug("unmarshaling Query");
 	   Query query = unmarshalQuery(queryXML);
	   
       return define(query, null, "tns", supplierUri);
    }

    /**
     * Define a Query as Types.
     * The Types are available through TypeHelper and DataGraph getType() methods.
     * @param xsdReader reader to an XML Schema.
     * @param targetNamespaceURI the URI of the types to be defined
     * @param supplierUri the URI for the SDO namespace which supplied the types used in the given query 
     * @return the defined Types.
     * @throws IllegalArgumentException if the Types could not be defined.
     */
    public List<Type> define(Reader queryReader, 
    		String targetNamespaceURI, String supplierUri) {
  	   if (log.isDebugEnabled())
		   log.debug("unmarshaling Query");
  	   Query query = unmarshalQuery(queryReader);
	   
       return define(query, targetNamespaceURI, "tns", supplierUri);
    }

    /**
     * Define a Query as Types.
     * The Types are available through TypeHelper and DataGraph getType() methods.
     * @param queryInputStream input stream to a Query.
     * @param targetNamespaceURI the URI of the types to be defined
     * @param supplierUri the URI for the SDO namespace which supplied the types used in the given query 
     * @return the defined Types.
     * @throws IllegalArgumentException if the Types could not be defined.
     */
    public List<Type> define(InputStream queryInputStream,
    		String targetNamespaceURI, String supplierUri) {
	   
	   if (log.isDebugEnabled())
		   log.debug("unmarshaling schema");
	   Query query = unmarshalQuery(queryInputStream);
	   
       return define(query, targetNamespaceURI, "tns",supplierUri);
    }

    /**
     * Define a Query as Types.
     * The Types are available through TypeHelper and DataGraph getType() methods.
     * @param query the input Query.
     * @param targetNamespaceURI the URI of the types to be defined
     * @param supplierUri the URI for the SDO namespace which supplied the types used in the given query 
     * @return the defined Types.
     * @throws IllegalArgumentException if the Types could not be defined.
     */
    public List<Type> define(Query query, 
    		String targetNamespaceURI, String targetNamespacePrefix,
    		String supplierUri) {

    	String outputLocation = "."; // FIXME
    	
        ProvisioningModelAssembler stagingAssembler = 
    		new ProvisioningModelAssembler(query, targetNamespaceURI,
        	    targetNamespacePrefix);       
 	    Model stagingModel = stagingAssembler.getModel();
        if (log.isDebugEnabled())
		   log.debug("provisioning UML/XMI model");
 	    if (log.isDebugEnabled())
		   writeStagingModel(stagingModel,  
				   outputLocation, this.getClass().getSimpleName()
	    			+ "-" + query.getName() + "-model.xml");
	    ModelAdapter helper = 
		   new ModelAdapter(stagingModel);
	   
	    UMLModelAssembler assembler = new MDModelAssembler(stagingModel, 
			   targetNamespaceURI, targetNamespacePrefix);
	    String xmi = assembler.getContent();
	    if (log.isDebugEnabled()) {
		   File xmiDebugFile = null;
		   try {
			     if (outputLocation != null)
			    	 xmiDebugFile = new File(".", 
			    			 this.getClass().getSimpleName()+"-" + query.getName() + "-model.mdxml");
			     else
			    	 xmiDebugFile =File.createTempFile(query.getName(), "mdxml");
		   } catch (IOException e1) {
		   }			   
		   log.debug("Writing UML/XMI to: " + xmiDebugFile.getAbsolutePath());
		   try {
			    FileOutputStream os = new FileOutputStream(xmiDebugFile);
			    assembler.getContent(os);
		   } catch (FileNotFoundException e) {
		   }
	   }
	   
	   ByteArrayInputStream stream = new ByteArrayInputStream(
			   xmi.getBytes());
	   
	   if (log.isDebugEnabled())
		   log.debug("loading UML/XMI model");
       Fuml.load(new ResourceArtifact(
    		   targetNamespaceURI, 
    		   targetNamespaceURI, 
               stream)); 
   	
       // ok we dynamically create a new SDO namespace
       // but now what about linkages to DAS specific
       // namespace configs. Can/should these go away
       // somehow ??
       if (supplierUri == null) {
           PlasmaConfig.getInstance().addDynamicSDONamespace(
    		   targetNamespaceURI, null);
       }
       else {
           PlasmaConfig.getInstance().addDynamicSDONamespace(
        		   targetNamespaceURI, supplierUri);
       }
       
       List<Class> entities = stagingAssembler.getModel().getClazzs();
	   List<Type> result = new ArrayList<Type>(entities.size());
       for (Class cls : entities) {
    	   result.add(PlasmaTypeHelper.INSTANCE.getType(
    			   cls.getUri(), cls.getName()));
       }
       
       return result;
    	
    }

    private void writeStagingModel(Model stagingModel, 
    		String location, String fileName) {
		   try {
			    BindingValidationEventHandler debugHandler = new BindingValidationEventHandler() {
					public int getErrorCount() {
						return 0;
					}
					public boolean handleEvent(ValidationEvent ve) {
				        ValidationEventLocator vel = ve.getLocator();
				        
				        String message = "Line:Col:Offset[" + vel.getLineNumber() + ":" + vel.getColumnNumber() + ":" 
				            + String.valueOf(vel.getOffset())
				            + "] - " + ve.getMessage();
				        
				        switch (ve.getSeverity()) {
				        default:
				            log.debug(message);
				        }
				        return true;
					}			    	
			    };
			    ProvisioningModelDataBinding binding = 
				   new ProvisioningModelDataBinding(debugHandler);
			    String xml = binding.marshal(stagingModel);
			    binding.validate(xml);
			    
			    File provDebugFile = null;
			    if (location != null)
			    	provDebugFile = new File(location, 
			    			fileName);
			    else
			    	provDebugFile = File.createTempFile(fileName, "");
			    FileOutputStream provDebugos = new FileOutputStream(provDebugFile);
				log.debug("Writing provisioning model to: " + provDebugFile.getAbsolutePath());
			    binding.marshal(stagingModel, provDebugos);
			} catch (JAXBException e) {
				log.debug(e.getMessage(), e);
			} catch (SAXException e) {
				log.debug(e.getMessage(), e);
			} catch (IOException e) {
				log.debug(e.getMessage(), e);
			}
   	
    }

    private void marshalQuery(Query schema, OutputStream stream) {
        try {
        	PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                    new DefaultValidationEventHandler());
            
            binding.marshal(schema, stream);
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }
 
    private Query unmarshalQuery(InputStream stream) {
        try {
        	PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                    new DefaultValidationEventHandler());
            return (Query)binding.unmarshal(stream);
                        
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }
    
    private Query unmarshalQuery(String xml) {
        try {
        	PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                    new DefaultValidationEventHandler());
            return (Query)binding.unmarshal(xml);
                        
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }
    
    private Query unmarshalQuery(Reader reader) {
        try {
        	PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                    new DefaultValidationEventHandler());
            return (Query)binding.unmarshal(reader);
                        
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }
    
    private Query unmarshalQuery(Source source) {
        try {
        	PlasmaQueryDataBinding binding = new PlasmaQueryDataBinding(
                    new DefaultValidationEventHandler());
            return (Query)binding.unmarshal(source);
                        
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }

}
