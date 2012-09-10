package org.plasma.query.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Property;
import org.plasma.query.model.Where;
import org.plasma.query.model.WildcardProperty;

import commonj.sdo.Type;

/**
 * Package level delegate for common collection
 * functionality.
 */
class CollectorSupport {

	 /** 
     * Whether to collect only singular properties and 
     * follow paths composed of only singular properties. 
     */
    private boolean onlySingularProperties;
    
    public CollectorSupport(boolean onlySingularProperties) {
    	this();
    	this.onlySingularProperties = onlySingularProperties;
    }

    public CollectorSupport() {
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
            for (String name : names)
                list.add(name);        
        }
        else {
            for (String name : names)
                if (!list.contains(name))
                    list.add(name); 
        }
    }
    
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
            List<commonj.sdo.Property> props = type.getDeclaredProperties();
            switch (wildcardProperty.getType())
            {
                default:
                case ALL:
                    for (commonj.sdo.Property prop : props) {
                        if (prop.isMany() && this.onlySingularProperties)
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
}
