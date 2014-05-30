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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Stereotype;
import org.modeldriven.fuml.repository.model.InMemoryRepository;
import org.plasma.config.Namespace;
import org.plasma.config.PlasmaConfig;
import org.plasma.sdo.PlasmaDataObjectConstants;
import org.plasma.sdo.PlasmaDataObjectException;
import org.plasma.sdo.PlasmaType;
import org.plasma.sdo.core.CoreType;
import org.plasma.sdo.repository.InvalidClassifierNameException;
import org.plasma.sdo.repository.PlasmaRepository;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.TypeHelper;

/**
 * Look up a Type given the uri and typeName or interfaceClass.
 * SDO Types are available through the
 *   getType(uri, typeName) method.
 * Defines Types from DataObjects.
 */
public class PlasmaTypeHelper implements TypeHelper {

    private static Log log = LogFactory.getLog(PlasmaTypeHelper.class);
    static public PlasmaTypeHelper INSTANCE = initializeInstance();
    
    private Map<String, Type> namespaceQualifiedNameToTypeMap = new HashMap<String, Type>();
    private Map<String, List<Type>> namespaceToTypesMap = new HashMap<String, List<Type>>();

    private PlasmaTypeHelper() {       
    }
     
    private static synchronized PlasmaTypeHelper initializeInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new PlasmaTypeHelper();
        return INSTANCE;
    }
    
    /**
     * Define the DataObject as a Type.
     * The Type is available through TypeHelper and DataGraph getType() methods.
     * @param type the DataObject representing the Type.
     * @return the defined Type.
     * @throws IllegalArgumentException if the Type could not be defined.
     */
    public Type define(DataObject type) {
        throw new RuntimeException("not yet supported");
    }

    /**
     * Define the list of DataObjects as Types.
     * The Types are available through TypeHelper and DataGraph getType() methods.
     * @param types a List of DataObjects representing the Types.
     * @return the defined Types.
     * @throws IllegalArgumentException if the Types could not be defined.
     */
    public List define(List types) {
        // TODO Auto-generated method stub
        throw new RuntimeException("not yet supported");
    }

    /**
     * Define the DataObject as a Property for setting open content.
     * The containing Type of the open content property is not specified by SDO.
     * If the specified uri is not null the defined property is accessible through
     * TypeHelper.getOpenContentProperty(uri, propertyName).
     * If a null uri is specified, the location and management of the open content property
     * is not specified by SDO.
     * @param uri the namespace URI of the open content Property or null.
     * @return the defined open content Property.
     * @throws IllegalArgumentException if the Property could not be defined.
     */
    public Property defineOpenContentProperty(String uri, DataObject property) {
        // TODO Auto-generated method stub
        throw new RuntimeException("not yet supported");
    }

    /**
     * Get the open content (global) Property with the specified uri and name, or null
     * if not found.
     * @param uri the namespace URI of the open content Property.
     * @param propertyName the name of the open content Property.
     * @return the global Property.
     */
    public Property getOpenContentProperty(String uri, String propertyName) {
        // TODO Auto-generated method stub
        throw new RuntimeException("not yet supported");
    }

    /**
     * Return the Type specified by typeName with the given uri,
     *   or null if not found.
     * @param uri The uri of the Type - type.getURI();
     * @param typeName The name of the Type - type.getName();
     * @return the Type specified by typeName with the given uri,
     *   or null if not found.
     */
    public Type getType(String uri, String typeName) {
        String qualifiedName = uri + "#" + typeName;
        Type result = namespaceQualifiedNameToTypeMap.get(qualifiedName);
        if (result == null)
        {
        	try {
                result = new CoreType(uri, typeName);
        	}
        	catch (InvalidClassifierNameException e) {
        		log.warn(e.getMessage(), e);
        		return null;
        	}
            namespaceQualifiedNameToTypeMap.put(qualifiedName, result);
            // New type could have a name based on config type-binding
            // also map by new logical name if exists
            if (!result.getName().equals(typeName)) {
            	qualifiedName = uri + "#" + result.getName();
                namespaceQualifiedNameToTypeMap.put(qualifiedName, result);
            }
            
            List<Type> namespaceTypes = namespaceToTypesMap.get(uri);
            if (namespaceTypes == null)
            	namespaceTypes = new ArrayList<Type>();
            namespaceTypes.add(result);
        }
        return result;
    }
    
    /**
     * Return the Type for this interfaceClass or null if not found. The interfaceClass 
     * is the Java interface that follows the SDO code generation pattern.
     * @param interfaceClass is the interface for the DataObject's Type -  
     * type.getInstanceClass(); 
     * @return the Type for this interfaceClass or null if not found.
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public Type getType(Class interfaceClass) 
    {    
        String uri = null;
        try {
            Field uriField = interfaceClass.getDeclaredField(
                    PlasmaDataObjectConstants.NAMESPACE_URI_FIELD_NAME);
            uri = (String)uriField.get(null);
        } catch (SecurityException e) {
            throw new PlasmaDataObjectException(e);
        } catch (NoSuchFieldException e) {
            throw new PlasmaDataObjectException(e);
        } catch (IllegalArgumentException e) {
            throw new PlasmaDataObjectException(e);
        } catch (IllegalAccessException e) {
            throw new PlasmaDataObjectException(e);
        }
                
        String qualifiedName = uri + "#" + interfaceClass.getSimpleName();
        Type result = namespaceQualifiedNameToTypeMap.get(qualifiedName);
        if (result == null)
        {
        	try {
                result = new CoreType(uri, interfaceClass.getSimpleName());
        	}
        	catch (InvalidClassifierNameException e) {
        		log.warn(e.getMessage(), e);
        		return null;
        	}
            namespaceQualifiedNameToTypeMap.put(qualifiedName, result);
            List<Type> namespaceTypes = namespaceToTypesMap.get(uri);
            if (namespaceTypes == null)
            	namespaceTypes = new ArrayList<Type>();
            namespaceTypes.add(result);
        }
        return result;
    }
    
    /**
     * Return the Type instances specified by the given uri.
     * @param uri The namespace uri;
     * @return the Type instances specified by the given uri.
     */
    public List<Type> getTypes(String uri) {
    	List<Type> result = new ArrayList<Type>();
    	List<Classifier> list = PlasmaRepository.getInstance().getClassifiers(uri);
    	for (Classifier classifier : list) {
    		if (classifier instanceof Stereotype) {
    			log.warn("ignoring stereotype: " + classifier.getName());
    			continue;
    		}
    			
    		Type type = getType(uri, classifier.getName());	
    		result.add(type);
    	}
    	return result;
    }

}
