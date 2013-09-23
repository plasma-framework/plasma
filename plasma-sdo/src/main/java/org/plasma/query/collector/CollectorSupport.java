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
package org.plasma.query.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.plasma.query.QueryException;
import org.plasma.query.model.AbstractPathElement;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;
import org.plasma.query.model.PathElement;
import org.plasma.query.model.Property;
import org.plasma.query.model.WildcardPathElement;
import org.plasma.query.model.WildcardProperty;
import org.plasma.sdo.PlasmaType;

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
    protected boolean onlyDeclaredProperties = false;
    
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

	protected void mapProperty(Type type, commonj.sdo.Property property, 
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
	
	protected void addProperty(Type type, commonj.sdo.Property property, 
    		Map<Type, Set<commonj.sdo.Property>> map)
    {
        Set<commonj.sdo.Property> set = map.get(type);
        if (set == null) {
        	set = new HashSet<commonj.sdo.Property>();
            map.put(type, set);
            set.add(property); 
        }
        else {
            if (!set.contains(property))
            	set.add(property); 
        }
    }
	
	protected void addProperty(Type type, Integer level, commonj.sdo.Property property, 
    		Map<Type, Map<Integer, Set<commonj.sdo.Property>>> map)
    {
		Map<Integer, Set<commonj.sdo.Property>> levelMap = map.get(type);
		if (levelMap == null) {
			levelMap = new HashMap<Integer, Set<commonj.sdo.Property>>();
			map.put(type, levelMap);
		}
		
        Set<commonj.sdo.Property> set = levelMap.get(level);
        if (set == null) {
        	set = new HashSet<commonj.sdo.Property>();
        	levelMap.put(level, set);
            set.add(property); 
        }
        else {
            if (!set.contains(property))
            	set.add(property); 
        }
    }
	
	protected void addProperty(Type type, commonj.sdo.Property edge, commonj.sdo.Property property, 
    		Map<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>> map)
    {
		Map<commonj.sdo.Property, Set<commonj.sdo.Property>> edgeMap = map.get(type);
		if (edgeMap == null) {
			edgeMap = new HashMap<commonj.sdo.Property, Set<commonj.sdo.Property>>();
			map.put(type, edgeMap);
		}
		
        Set<commonj.sdo.Property> set = edgeMap.get(edge);
        if (set == null) {
        	set = new HashSet<commonj.sdo.Property>();
        	edgeMap.put(edge, set);
            set.add(property); 
        }
        else {
            if (!set.contains(property))
            	set.add(property); 
        }
    }
	

	protected void mapInheritedProperty(Type type, commonj.sdo.Property property, 
    		Map<Type, List<String>> map) {
		mapProperty(type, property, map);
		
		PlasmaType plasmaType = (PlasmaType)type; 
		for (Type subType : plasmaType.getSubTypes()) 
			mapInheritedProperty(subType, property, map);
	}	
	
	protected void addInheritedProperty(Type type, commonj.sdo.Property property, 
    		Map<Type, Set<commonj.sdo.Property>> map) {
		addProperty(type, property, map);
		
		PlasmaType plasmaType = (PlasmaType)type; 
		for (Type subType : plasmaType.getSubTypes()) 
			addInheritedProperty(subType, property, map);
	}	
    
	protected void addInheritedProperty(Type type, Integer level, commonj.sdo.Property property, 
			Map<Type, Map<Integer, Set<commonj.sdo.Property>>> map) {
		addProperty(type, level, property, map);
		
		PlasmaType plasmaType = (PlasmaType)type; 
		for (Type subType : plasmaType.getSubTypes()) 
			addInheritedProperty(subType, level, property, map);
	}	

	protected void addInheritedProperty(Type type, commonj.sdo.Property edge, commonj.sdo.Property property, 
			Map<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>> map) {
		addProperty(type, edge, property, map);
		
		PlasmaType plasmaType = (PlasmaType)type; 
		for (Type subType : plasmaType.getSubTypes()) 
			addInheritedProperty(subType, edge, property, map);
	}	

	protected void mapPropertyNames(Type type, String[] names,
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
	
	protected void mapProperties(Type type, commonj.sdo.Property[] props,
    		Map<Type, Set<commonj.sdo.Property>> map)
    {
        Set<commonj.sdo.Property> set = map.get(type);
        if (set == null) {
        	set = new HashSet<commonj.sdo.Property>(props.length);
            map.put(type, set);
            for (commonj.sdo.Property prop : props) {
            	set.add(prop);        
            }
        }
        else {
            for (commonj.sdo.Property prop : props)
                if (!set.contains(prop)) {
                	set.add(prop); 
                }
        }
    }

	protected void mapProperties(Type type, Integer level, commonj.sdo.Property[] props,
    		Map<Type, Map<Integer, Set<commonj.sdo.Property>>> map)
    {
		Map<Integer, Set<commonj.sdo.Property>> levelMap = map.get(type);
		if (levelMap == null) {
			levelMap = new HashMap<Integer, Set<commonj.sdo.Property>>();
			map.put(type, levelMap);
		}
		
        Set<commonj.sdo.Property> set = levelMap.get(level);
        if (set == null) {
        	set = new HashSet<commonj.sdo.Property>(props.length);
        	levelMap.put(level, set);
            for (commonj.sdo.Property prop : props) {
            	set.add(prop);        
            }
        }
        else {
            for (commonj.sdo.Property prop : props)
                if (!set.contains(prop)) {
                	set.add(prop); 
                }
        }
    }
	
	protected void mapProperties(Type type, commonj.sdo.Property edge, commonj.sdo.Property[] props,
    		Map<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>> map)
    {
		Map<commonj.sdo.Property, Set<commonj.sdo.Property>> edgeMap = map.get(type);
		if (edgeMap == null) {
			edgeMap = new HashMap<commonj.sdo.Property, Set<commonj.sdo.Property>>();
			map.put(type, edgeMap);
		}
		
        Set<commonj.sdo.Property> set = edgeMap.get(edge);
        if (set == null) {
        	set = new HashSet<commonj.sdo.Property>(props.length);
        	edgeMap.put(edge, set);
            for (commonj.sdo.Property prop : props) {
            	set.add(prop);        
            }
        }
        else {
            for (commonj.sdo.Property prop : props)
                if (!set.contains(prop)) {
                	set.add(prop); 
                }
        }
    }
	
	
	protected void mapInheritedPropertyNames(Type type, String[] names, 
    		Map<Type, List<String>> map) {
		mapPropertyNames(type, names, map);
		
		PlasmaType plasmaType = (PlasmaType)type; 
		for (Type subType : plasmaType.getSubTypes()) 
			mapInheritedPropertyNames(subType, names, map);
	}	
	
	protected void mapInheritedProperties(Type type, commonj.sdo.Property[] props, 
			Map<Type, Set<commonj.sdo.Property>> map) {
		mapProperties(type, props, map);
		
		PlasmaType plasmaType = (PlasmaType)type; 
		for (Type subType : plasmaType.getSubTypes()) 
			mapInheritedProperties(subType, props, map);
	}	

	protected void mapInheritedProperties(Type type, Integer level, commonj.sdo.Property[] props, 
			Map<Type, Map<Integer, Set<commonj.sdo.Property>>> map) {
		mapProperties(type, level, props, map);
		
		PlasmaType plasmaType = (PlasmaType)type; 
		for (Type subType : plasmaType.getSubTypes()) 
			mapInheritedProperties(subType, level, props, map);
	}	
    
	protected void mapInheritedProperties(Type type, commonj.sdo.Property edge, commonj.sdo.Property[] props, 
			Map<Type, Map<commonj.sdo.Property, Set<commonj.sdo.Property>>> map) {
		mapProperties(type, edge, props, map);
		
		PlasmaType plasmaType = (PlasmaType)type; 
		for (Type subType : plasmaType.getSubTypes()) 
			mapInheritedProperties(subType, edge, props, map);
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
	protected String[] findPropertyNames(Type type, AbstractProperty abstractProperty)
    {
        String[] result = null;       
        
        if (abstractProperty instanceof Property) {
            String name = ((Property)abstractProperty).getName();
            type.getProperty(name); // just validates name
            result = new String[1];
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
	 * A convenience method returning an array of names for 
	 * the given property whether a wildcard
	 * property or not. For wildcard properties determines the type of
	 * wildcard and returns the appropriate property set.  
	 * @param type the type
	 * @param abstractProperty the property
	 * @return the property names as an array
	 */
	protected commonj.sdo.Property[] findProperties(Type type, AbstractProperty abstractProperty)
    {
		commonj.sdo.Property[] result = null;       
        
        if (abstractProperty instanceof Property) {
            String name = ((Property)abstractProperty).getName();
            result = new commonj.sdo.Property[1];
            result[0] = type.getProperty(name);
        }
        else if (abstractProperty instanceof WildcardProperty) {
            WildcardProperty wildcardProperty = (WildcardProperty)abstractProperty;
            List<commonj.sdo.Property> list = new ArrayList<commonj.sdo.Property>();
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
                    	list.add(prop);
                    }
                    break;
                case REFERENCE:
                    for (commonj.sdo.Property prop : props) {
                        if (prop.isMany() && this.onlySingularProperties)
                        	continue;
                    	if (!prop.getType().isDataType())
                            list.add(prop);
                    }
                    break;
                case DATA:
                    for (commonj.sdo.Property prop : props)
                    	if (prop.getType().isDataType())
                            list.add(prop);
                    break;
            }
            result = new commonj.sdo.Property[list.size()];
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
	protected boolean isSingularPath(Path path, Type rootType,
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
	protected boolean isSingularPath(Path path, Type currType, 
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
