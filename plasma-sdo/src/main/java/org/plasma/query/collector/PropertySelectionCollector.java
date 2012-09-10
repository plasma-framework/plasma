package org.plasma.query.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.plasma.query.InvalidPathPredicateException;
import org.plasma.query.QueryException;
import org.plasma.query.model.AbstractPathElement;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.Path;
import org.plasma.query.model.PathElement;
import org.plasma.query.model.PathNode;
import org.plasma.query.model.Property;
import org.plasma.query.model.Select;
import org.plasma.query.model.Where;
import org.plasma.query.model.WildcardPathElement;
import org.plasma.query.model.WildcardProperty;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.QueryVisitor;

import commonj.sdo.Type;


/**
 * Traverses a given {@link Select) clause collecting the specified property 
 * names, as well as any path-reference or key properties required
 * for graph structure, into a simple list of logical property name
 * strings mapped to the respective {@link Type} definition.  
 * @see Select 
 * @see Type
 */
public class PropertySelectionCollector extends DefaultQueryVisitor 
{
    private Map<Type, List<String>> propertyMap;
    private Type rootType;
    private CollectorSupport collectorSupport;
    private Select select;
    // FIXME: what to do with repeated/multiple predicates
    private Map<commonj.sdo.Property, Where> predicateMap; 
    
    public PropertySelectionCollector(Select select, Type rootType) {
        this.rootType = rootType;
        this.select = select;
        this.collectorSupport = new CollectorSupport();
        this.predicateMap = new HashMap<commonj.sdo.Property, Where>();
    }
    
    public PropertySelectionCollector(Select select, Type rootType,
    		boolean onlySingularProperties) {
        this.rootType = rootType;
        this.select = select;
        this.collectorSupport = new CollectorSupport(onlySingularProperties);
        this.predicateMap = new HashMap<commonj.sdo.Property, Where>();
    }
    
    public Map<commonj.sdo.Property, Where> getPredicateMap() {
		return predicateMap;
	}

	/** 
     * Whether to collect only singular properties and 
     * follow paths composed of only singular properties. 
     */
    public boolean isOnlySingularProperties() {
		return this.collectorSupport.isOnlySingularProperties();
	}

	public void setOnlySingularProperties(boolean onlySingularProperties) {
		this.collectorSupport.setOnlySingularProperties(onlySingularProperties);
	}

	/**
	 * Return the specified property 
     * names, as well as any path-reference or key properties required
     * for graph structure, as a simple list of logical property name
     * strings mapped to the respective {@link Type} definition
	 * @return the {@link Type} to logical property name map
	 */
	public Map<Type, List<String>> getResult() {
		
		if (this.propertyMap == null)
		{
		    propertyMap = new HashMap<Type, List<String>>();
	        QueryVisitor visitor = new DefaultQueryVisitor() {
	            public void start(Property property)                                                                            
	            {    
                    collect(property);  
                    super.start(property);                         
	            }                                                                                                                                                                                                                                                                                                                                                               
	            public void start(WildcardProperty wildcardProperty)                                                                            
	            {     
                    collect(wildcardProperty);  
                    super.start(wildcardProperty);                         
	            }                                                                                                                                                                                                                                                                                                                                                               
	        };
	        select.accept(visitor);
		}
		
        return this.propertyMap;
    }
    
    public boolean hasType(Type type) {
    	return this.propertyMap.get(type) != null;
    }
    
    public boolean hasProperty(Type type,  commonj.sdo.Property property) {
    	List<String> props = this.propertyMap.get(type);
    	if (props != null && props.size() > 0) {
    	    for (String prop : props)	
    	    	if (prop.equals(property.getName()))
    	    		return true;
    	}
    	return false;
    }
    
    private void collect(AbstractProperty abstractProperty) {
        Path path = null;
        if (abstractProperty instanceof Property) {
            path = ((Property)abstractProperty).getPath();
        }
        else if (abstractProperty instanceof WildcardProperty) {
            path = ((WildcardProperty)abstractProperty).getPath();
        }
        else
            throw new IllegalArgumentException("unknown property class, "
                    + abstractProperty.getClass().getName());
        if (path == null) {
            String[] names = this.collectorSupport.findPropertyNames(rootType, abstractProperty);
            
            List<String> list = this.propertyMap.get(this.rootType);
            if (list == null) {
                list = new ArrayList<String>(names.length);
                this.propertyMap.put(this.rootType, list);
            }
            for (String name : names)
                if (!list.contains(name))
                    list.add(name);
        }
        else {
        	if (!this.collectorSupport.isOnlySingularProperties()) {
                collect(path, rootType, 
                    path.getPathNodes().get(0), 0, 
                    abstractProperty, this.propertyMap);
        	}
        	else {
        		if (isSingularPath(path, rootType, abstractProperty)) 
                    collect(path, rootType, 
                            path.getPathNodes().get(0), 0, 
                            abstractProperty, this.propertyMap);
        	}
        }
    }
    
    private boolean isSingularPath(Path path, Type rootType,
    		AbstractProperty abstractProperty) {
    	return isSingularPath(path, rootType,
    	    path.getPathNodes().get(0).getPathElement(), 0, abstractProperty);
    }
    
    private boolean isSingularPath(Path path, Type currType, 
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
            List<commonj.sdo.Property> properties = currType.getDeclaredProperties(); 
            
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
    
    /**
     * Recursively collects properties from the given path into 
     * the given property map including both wildcard paths and
     * properties
     * @param path the path
     * @param currType the current type
     * @param currPathElement the current path element/node
     * @param curPathElementIndex the current path element/node index
     * @param abstractProperty the property
     * @param map the property map
     */
    private void collect(Path path, Type currType, 
            PathNode currPathode, 
            int curPathElementIndex, AbstractProperty abstractProperty,
            Map<Type, List<String>> map) {
        
    	AbstractPathElement currPathElement = currPathode.getPathElement();
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
            
            if (currPathode.getWhere() != null)
            	this.predicateMap.put(prop, currPathode.getWhere());
            
            if (prop.isMany() && this.collectorSupport.isOnlySingularProperties())
            	return;

            
            Type nextType = prop.getType(); // traverse
            
            if (path.getPathNodes().size() > curPathElementIndex + 1) { // more nodes
            	this.collectorSupport.mapProperty(currType, prop, map);

                int nextPathElementIndex = curPathElementIndex + 1;
                PathNode nextPathNode = path.getPathNodes().get(nextPathElementIndex);
                collect(path, nextType, nextPathNode, nextPathElementIndex, abstractProperty, map);
            }
            else {
            	this.collectorSupport.mapProperty(currType, prop, map);                              
                String[] names = this.collectorSupport.findPropertyNames(nextType, abstractProperty);
                this.collectorSupport.mapPropertyNames(nextType, names, map);
            }
        }
        else if (currPathElement instanceof WildcardPathElement) {
            List<commonj.sdo.Property> properties = currType.getDeclaredProperties(); 
            
            if (currPathode.getWhere() != null)
                throw new InvalidPathPredicateException("path predicate found on wildcard path element");

            for (commonj.sdo.Property prop : properties) {
            	if (prop.getType().isDataType())
            		continue;
                if (prop.isMany() && this.collectorSupport.isOnlySingularProperties())
                	return;
            	
                Type nextType = prop.getType();
                
                if (path.getPathNodes().size() > curPathElementIndex + 1) {
                	this.collectorSupport.mapProperty(currType, prop, map);

                    int nextPathElementIndex = curPathElementIndex + 1;
                    PathNode nextPathNode = path.getPathNodes().get(nextPathElementIndex);
                    collect(path, nextType, nextPathNode, nextPathElementIndex, abstractProperty, map);
                }
                else {
                	this.collectorSupport.mapProperty(currType, prop, map);
                    String[] names = this.collectorSupport.findPropertyNames(nextType, abstractProperty);
                    this.collectorSupport.mapPropertyNames(nextType, names, map);
                }
            }
        }
        else
            throw new IllegalArgumentException("unknown path element class, "
                + currPathElement.getClass().getName());
    }
    
}
