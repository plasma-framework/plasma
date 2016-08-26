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
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.io.ResourceArtifact;
import org.plasma.common.bind.BindingValidationEventHandler;
import org.plasma.common.bind.DefaultValidationEventHandler;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.config.PlasmaConfig;
import org.plasma.metamodel.Class;
import org.plasma.metamodel.Model;
import org.plasma.metamodel.ModelAppInfo;
import org.plasma.provisioning.MetamodelAssembler;
import org.plasma.provisioning.MetamodelDataBinding;
import org.plasma.provisioning.SchemaMetamodelAssembler;
import org.plasma.provisioning.adapter.ModelAdapter;
import org.plasma.provisioning.adapter.ProvisioningModel;
import org.plasma.query.Query;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;
import org.plasma.xml.schema.Annotation;
import org.plasma.xml.schema.Appinfo;
import org.plasma.xml.schema.OpenAttrs;
import org.plasma.xml.schema.Schema;
import org.plasma.xml.schema.SchemaDataBinding;
import org.plasma.xml.schema.SchemaModelAssembler;
import org.plasma.xml.uml.MDModelAssembler;
import org.plasma.xml.uml.UMLModelAssembler;
import org.xml.sax.SAXException;

import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.XSDHelper;

public class PlasmaXSDHelper implements XSDHelper {
    private static Log log = LogFactory.getLog(
    		PlasmaXSDHelper.class); 

    static public volatile PlasmaXSDHelper INSTANCE = initializeInstance();

    private PlasmaXSDHelper() {       
    }
     
    private static synchronized PlasmaXSDHelper initializeInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new PlasmaXSDHelper();
        return INSTANCE;
    }
    
    /**
     * Define the XML Schema as Types.
     * The Types are available through TypeHelper and DataGraph getType() methods.
     * Same as define(new StringReader(xsd), null)
     * @param xsd the XML Schema.
     * @return the defined Types.
     * @throws IllegalArgumentException if the Types could not be defined.
     */
    public List<Type> define(String xsd) {
 	   if (log.isDebugEnabled())
		   log.debug("unmarshaling schema");
	   Schema schema = unmarshalSchema(xsd);
	   
       return define(schema, null);
    }

    /**
     * Define XML Schema as Types.
     * The Types are available through TypeHelper and DataGraph getType() methods.
     * @param xsdReader reader to an XML Schema.
     * @param schemaLocation the URI of the location of the schema, used 
     *   for processing relative imports and includes.  May be null if not used.
     * @return the defined Types.
     * @throws IllegalArgumentException if the Types could not be defined.
     */
    public List<Type> define(Reader xsdReader, String schemaLocation) {
  	   if (log.isDebugEnabled())
		   log.debug("unmarshaling schema");
	   Schema schema = unmarshalSchema(xsdReader);
	   
       return define(schema, schemaLocation);
    }

    /**
     * Define XML Schema as Types.
     * The Types are available through TypeHelper and DataGraph getType() methods.
     * @param xsdInputStream input stream to an XML Schema.
     * @param schemaLocation the URI of the location of the schema, used 
     *   for processing relative imports and includes.  May be null if not used.
     * @return the defined Types.
     * @throws IllegalArgumentException if the Types could not be defined.
     */
    public List<Type> define(InputStream xsdInputStream, String schemaLocation) {
	   
	   if (log.isDebugEnabled())
		   log.debug("unmarshaling schema");
	   Schema schema = unmarshalSchema(xsdInputStream);
	   
       return define(schema, schemaLocation);
    }

    private List<Type> define(Schema schema, String schemaLocation) {

       if (log.isDebugEnabled())
		   log.debug("provisioning UML/XMI model");
 	   SchemaMetamodelAssembler stagingAssembler = 
		   new SchemaMetamodelAssembler(schema, 
			   schema.getTargetNamespace(), "tns");
	   Model stagingModel = stagingAssembler.getModel();
	   if (log.isDebugEnabled())
		   writeSchemaStagingModel(stagingModel,  
		       schemaLocation, 
		    		this.getClass().getSimpleName()
	    			+ "-" + schema.getId() + "-model.xml");
	   	   
	   ProvisioningModel helper = 
		   new ModelAdapter(stagingModel);
	   
	   UMLModelAssembler assembler = new MDModelAssembler(stagingModel, 
			   schema.getTargetNamespace(), "tns");
	   String xmi = assembler.getContent();
	   if (log.isDebugEnabled()) {
		   File xmiDebugFile = null;
		   try {
			     if (schemaLocation != null)
			    	 xmiDebugFile = new File(schemaLocation, 
			    			 this.getClass().getSimpleName()+"-" + schema.getId() + "-model.mdxml");
			     else
			    	 xmiDebugFile =File.createTempFile(schema.getId(), "mdxml");
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
    		   schema.getTargetNamespace(), 
    		   schema.getTargetNamespace(), 
               stream)); 
   	
       // ok we dynamically create a new SDO namespace
       // but now what about linkages to DAS specific
       // namespace configs. Can/should these go away
       // somehow ??
       ModelAppInfo modelAppInfo = getModelAppInfo(schema);
       if (modelAppInfo != null) {
    	   if (log.isDebugEnabled())
    	       log.debug("supplier: " + modelAppInfo.getDerivation().getPackageSupplier().getUri());
           PlasmaConfig.getInstance().addDynamicSDONamespace(
        		   schema.getTargetNamespace(),
        		   modelAppInfo.getDerivation().getPackageSupplier().getUri());
       }
       else
           PlasmaConfig.getInstance().addDynamicSDONamespace(
        		   schema.getTargetNamespace(), null);
       
       List<Class> entities = stagingAssembler.getModel().getClazzs();
	   List<Type> result = new ArrayList<Type>(entities.size());
       for (Class cls : entities) {
    	   result.add(PlasmaTypeHelper.INSTANCE.getType(
    			   cls.getUri(), cls.getName()));
       }
       
       return result;
    	
    }
    
    private ModelAppInfo getModelAppInfo(Schema schema) {
        ModelAppInfo modelAppInfo = null;
        for (OpenAttrs attr : schema.getIncludesAndImportsAndRedefines()) {
     	   if (attr instanceof Annotation) {
     		   Annotation annot = (Annotation)attr;
     		   for (Object obj : annot.getAppinfosAndDocumentations()) {
     			   if (obj instanceof Appinfo) {
     				   for (Object obj2 : ((Appinfo)obj).getContent()) {
     					   if (obj2 instanceof ModelAppInfo) {
     						  modelAppInfo = (ModelAppInfo)obj2; // whew!
     						  break;
     					   }
     				   }
     			   }
     		   }
     	   }
        }
    	return modelAppInfo;
    }
   
    /**
     * Generate an XML Schema Declaration (XSD) from Types.
     * Same as generate(types, null);
     * @param types a List containing the Types
     * @return a String containing the generated XSD. 
     * @throws IllegalArgumentException if the XSD could not be generated.
     */
    public String generate(List<Type> types) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Generate an XML Schema Declaration (XSD) from Types.
     * Round trip from SDO to XSD to SDO is supported.
     * Round trip from XSD to SDO to XSD is not supported.
     *  Use the original schema if one exists instead of generating a new one, as
     *  the generated XSD validates a different set of documents than the original XSD.
     * Generating an XSD does not affect the XSDHelper or the Types.
     * The Types must all have the same URI.
     * The result is a String containing the generated XSD. 
     * All Types referenced with the same URI will be generated in the XSD
     *  and the list will be expanded to include all types generated.
     * Any Types referenced with other URIs will cause 
     *  imports to be produced as appropriate.
     * Imports will include a schemaLocation if a Map is provided with an entry
     *  of the form key=import target namespace, value=schemaLocation
     * @param types a List containing the Types
     * @param namespaceToSchemaLocation map of target namespace to schema locations or null
     * @return a String containing the generated XSD. 
     * @throws IllegalArgumentException if the XSD could not be generated.
     */
    public String generate(List<Type> types, Map<String, String> namespaceToSchemaLocation) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * Generate an XML Schema Declaration (XSD) with dynamically determined 
     * containment references as embodied within the given, 
     * PlasmaQuery<sup>TM</sup>, query. 
     * Round trip from SDO to XSD to SDO is supported.
     * Round trip from XSD to SDO to XSD is not supported.
     * Generating an XSD does not affect the XSDHelper or the Types.
     * The Types may have different URIs.
     * The result is a String containing the generated XSD. 
     * Imports will include a schemaLocation if a Map is provided with an entry
     *  of the form key=import target namespace, value=schemaLocation
     * @param query the Query used to determine selected types and properties, their 
     * ordering, local names and other information
     * @param targetNamespaceURI the target namespace for the resulting XSD
     * @param namespaceToSchemaLocation map of target namespace to schema locations or null
     * @return a String containing the generated XSD. 
     * @throws IllegalArgumentException if the XSD could not be generated.
     * @see org.plasma.query.PathNode
     * @see org.plasma.query.PathNode#getSelectClause
     * @see org.plasma.query.Select
     */
    public String generate(Query query,
    		String targetNamespaceURI, String targetNamespacePrefix,
    		Map<String, String> namespaceToSchemaLocation) {
        if (targetNamespaceURI == null || targetNamespaceURI.trim().length() == 0)
        	throw new IllegalArgumentException("expected argument 'targetNamespaceURI'");
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	generate(query, targetNamespaceURI, targetNamespacePrefix,
    			namespaceToSchemaLocation,
    			stream);
    	
        return new String(stream.toByteArray());
    }    

    public void generate(Query query,
    		String targetNamespaceURI, String targetNamespacePrefix,
    		Map<String, String> namespaceToSchemaLocation,
    		OutputStream xsdOutputStream) {
        if (targetNamespaceURI == null || targetNamespaceURI.trim().length() == 0)
        	throw new IllegalArgumentException("expected argument 'targetNamespaceURI'");
    	
        MetamodelAssembler assembler = new MetamodelAssembler(query,
        		targetNamespaceURI, targetNamespacePrefix);
        Model model = assembler.getModel();
 	    if (log.isDebugEnabled())
		   writeSchemaStagingModel(model, ".", 
		    	this.getClass().getSimpleName()
	    		+ "-" + query.getName() + "-model.xml");
        SchemaModelAssembler builder = new SchemaModelAssembler(model, 
    			targetNamespaceURI, targetNamespacePrefix);
    	Schema schema = builder.getSchema();
            	
    	this.marshalSchema(schema, xsdOutputStream);    	
    }    
    
    
    @Override
    public String getAppinfo(Type type, String source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAppinfo(Property property, String source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Property getGlobalProperty(String uri, String propertyName, boolean isElement) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getLocalName(Type type) {
        return ((PlasmaType)type).getLocalName();
    }
    
    public String getLocalName(Property property) {
        return ((PlasmaProperty)property).getLocalName();
    }

    public String getNamespaceURI(Property property) {
        return property.getContainingType().getURI();
    }

    public boolean isAttribute(Property property) {
        return ((PlasmaProperty)property).isXMLAttribute();
    }

    public boolean isElement(Property property) {
        return !((PlasmaProperty)property).isXMLAttribute();
    }

    @Override
    public boolean isMixed(Type type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isXSD(Type type) {
        // TODO Auto-generated method stub
        return false;
    }

    
    
    private void marshalSchema(Schema schema, OutputStream stream) {
        try {
            SchemaDataBinding binding = new SchemaDataBinding(
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
 
    private Schema unmarshalSchema(InputStream stream) {
        try {
            SchemaDataBinding binding = new SchemaDataBinding(
                    new DefaultValidationEventHandler());
            return (Schema)binding.unmarshal(stream);
                        
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }
    
    private Schema unmarshalSchema(String xml) {
        try {
            SchemaDataBinding binding = new SchemaDataBinding(
                    new DefaultValidationEventHandler());
            return (Schema)binding.unmarshal(xml);
                        
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }
    
    private Schema unmarshalSchema(Reader reader) {
        try {
            SchemaDataBinding binding = new SchemaDataBinding(
                    new DefaultValidationEventHandler());
            return (Schema)binding.unmarshal(reader);
                        
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }
    
    private Schema unmarshalSchema(Source source) {
        try {
            SchemaDataBinding binding = new SchemaDataBinding(
                    new DefaultValidationEventHandler());
            return (Schema)binding.unmarshal(source);
                        
        } catch (JAXBException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        } catch (SAXException e) {
            log.error(e.getMessage(), e);
            throw new PlasmaRuntimeException(e);
        }                     	
    }
    
    private void writeSchemaStagingModel(Model stagingModel, 
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
			    MetamodelDataBinding binding = 
				   new MetamodelDataBinding(debugHandler);
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
    
}
