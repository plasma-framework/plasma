package org.plasma.query.collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.plasma.query.QueryException;
import org.plasma.query.model.AbstractPathElement;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;
import org.plasma.query.model.PathElement;
import org.plasma.query.model.Property;
import org.plasma.query.model.WildcardPathElement;
import org.plasma.query.model.WildcardProperty;

import commonj.sdo.Type;

/**
 * Package level superclass for common collection
 * functionality.
 */
abstract class CollectorSupport {

    protected Type rootType;

    /** 
     * Whether to collect only singular reference 
     * properties and follow paths composed of only 
     * singular properties. 
     */
    protected boolean onlySingularProperties;
    
    /**
     * Whether to collect only declared properties for
     * a type, otherwise indicates to collect all properties
     * for a given type and all its base types  
     */
    protected boolean onlyDeclaredProperties = true;
    
    public CollectorSupport(Type rootType,
    		boolean onlySingularProperties) {
    	this(rootType);
    	this.onlySingularProperties = onlySingularProperties;
    }

    public CollectorSupport(Type rootType) {
    	this.rootType = rootType;
	}
    
	/** 
     * Returns whether to collect only singular properties and 
     * follow paths composed of only singular properties. 
     */
    public boolean isOnlySingularProperties() {
		return onlySingularProperties;
	}

    /** 
     * Sets whether to collect only singular properties and 
     * follow paths composed of only singular properties. 
     */
	public void setOnlySingularProperties(boolean onlySingularProperties) {
		this.onlySingularProperties = onlySingularProperties;
	}

    /**
     * Returns whether to collect only declared properties for
     * a type. 
     */
	public boolean isOnlyDeclaredProperties() {
		return onlyDeclaredProperties;
	}

    /**
     * Sets whether to collect only declared properties for
     * a type.
     */
	public void setOnlyDeclaredProperties(boolean onlyDeclaredProperties) {
		this.onlyDeclaredProperties = onlyDeclaredProperties;
	}

	public void mapProperty(Type type, commonj.sdo.Property property, 
    		Map<Type, List<String>> map)
    {
        List<String> list = map.get(type);
        if (list == null) {
            list = new ArrayList<String>();
            map.put(type, list);
            list.add(property.getName()); 
        }
        else {
            if (!list.contains(property.getName()))
                list.add(property.getName()); 
        }
    }
    
	public void mapPropertyNames(Type type, String[] names,
    		Map<Type, List<String>> map)
    {
        List<String> list = map.get(type);
        if (list == null) {
            list = new ArrayList<String>(names.length);
            map.put(type, list);
            for (String name : names) {
                list.add(name);        
            }
        }
        else {
            for (String name : names)
                if (!list.contains(name)) {
                    list.add(name); 
                }
        }
    }
    
	/**
	 * A convenience method returning an array of names for 
	 * the given property whether a wildcard
	 * property or not. For wildcard properties determines the type of
	 * wildcard and returns the appropriate property set.  
	 * @param type the type
	 * @param abstractProperty the property
	 * @return the property names as an array
	 */
	public String[] findPropertyNames(Type type, AbstractProperty abstractProperty)
    {
        String[] result = null;       
        
        if (abstractProperty instanceof Property) {
            result = new String[1];
            String name = ((Property)abstractProperty).getName();
            type.getProperty(name); // just validates name
            result[0] = name;
        }
        else if (abstractProperty instanceof WildcardProperty) {
            WildcardProperty wildcardProperty = (WildcardProperty)abstractProperty;
            List<String> list = new ArrayList<String>();
            List<commonj.sdo.Property> props = null;
            if (this.onlyDeclaredProperties)
                props = type.getDeclaredProperties();
            else
            	props = type.getProperties();
            switch (wildcardProperty.getType())
            {
                default:
                case ALL:
                    for (commonj.sdo.Property prop : props) {
                        if (prop.isMany() && !prop.getType().isDataType() && this.onlySingularProperties)
                        	continue;
                    	list.add(prop.getName());
                    }
                    break;
                case REFERENCE:
                    for (commonj.sdo.Property prop : props) {
                        if (prop.isMany() && this.onlySingularProperties)
                        	continue;
                    	if (!prop.getType().isDataType())
                            list.add(prop.getName());
                    }
                    break;
                case DATA:
                    for (commonj.sdo.Property prop : props)
                    	if (prop.getType().isDataType())
                            list.add(prop.getName());
                    break;
            }
            result = new String[list.size()];
            list.toArray(result);            
        }
        else
            throw new IllegalArgumentException("unknown property class, "
                + abstractProperty.getClass().getName());
        
        return result;
    }
	
    /**
     * Recursively traverses the given path checking every
     * path element and determines if the entire path is composed of
     * singular properties.
     * @param path the path
     * @param rootType the root type
     * @param abstractProperty
     * @return whether the given path is composed entirely of singular \
     * properties
     */
    public boolean isSingularPath(Path path, Type rootType,
    		AbstractProperty abstractProperty) {
    	return isSingularPath(path, rootType,
    	    path.getPathNodes().get(0).getPathElement(), 0, abstractProperty);
    }
    
    /**
     * Recursively traverses the given path checking every
     * path element and determines if the entire path is composed of
     * singular properties.
     * @param path the path
     * @param currType the current type
     * @param currPathElement the current path element
     * @param curPathElementIndex the current path element index
     * @param abstractProperty the property
     * @return whether the given path is composed entirely of singular \
     * properties
     */
    public boolean isSingularPath(Path path, Type currType, 
            AbstractPathElement currPathElement, 
            int curPathElementIndex, AbstractProperty abstractProperty) {
        if (currPathElement instanceof PathElement) {
            PathElement pathElement = (PathElement)currPathElement;
            commonj.sdo.Property prop = currType.getProperty(pathElement.getValue());
            
            if (prop.getType().isDataType())
                if (abstractProperty instanceof Property)
                    throw new QueryException("traversal path for property '" + ((Property)abstractProperty).getName() 
                        + "' from root '" + rootType.getName() + "' contains a non-reference property '" 
                        + prop.getName() + "'");
                else
                    throw new QueryException("traversal path for wildcard property " 
                        + "' from root '" + rootType.getName() + "' contains a non-reference property '" 
                        + prop.getName() + "'");
            
            if (prop.isMany())
            	return false; 

            Type nextType = prop.getType(); // traverse
            
            if (path.getPathNodes().size() > curPathElementIndex + 1) { // more nodes
                int nextPathElementIndex = curPathElementIndex + 1;
                AbstractPathElement nextPathElement = path.getPathNodes().get(nextPathElementIndex).getPathElement();
                if (!isSingularPath(path, nextType, nextPathElement, nextPathElementIndex, abstractProperty))
                	return false;
            }
        }
        else if (currPathElement instanceof WildcardPathElement) {
            List<commonj.sdo.Property> properties = null;
            if (this.onlyDeclaredProperties)
            	properties = currType.getDeclaredProperties();
            else
            	properties = currType.getProperties();
            
            for (commonj.sdo.Property prop : properties) {
            	if (prop.getType().isDataType())
            		continue; // 
            	
            	Type nextType = prop.getOpposite().getContainingType();
                
                if (path.getPathNodes().size() > curPathElementIndex + 1) { // more nodes
                    int nextPathElementIndex = curPathElementIndex + 1;
                    AbstractPathElement nextPathElement = path.getPathNodes().get(nextPathElementIndex).getPathElement();
                    if (!isSingularPath(path, nextType, nextPathElement, nextPathElementIndex, abstractProperty))
                    	return false;
                }
            }
        }
        else
            throw new IllegalArgumentException("unknown path element class, "
                + currPathElement.getClass().getName());
        return true;
    }
}
