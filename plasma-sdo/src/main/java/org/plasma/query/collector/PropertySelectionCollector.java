package org.plasma.query.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.PlasmaType;

import commonj.sdo.Type;


/**
 * Traverses a given {@link Select) clause collecting the specified property 
 * names, as well as any path-reference or key properties required
 * for graph structure, into a simple list of logical property name
 * strings mapped to the respective {@link Type} definition.  
 * @see org.plasma.query.model.Select 
 * @see commonj.sdo.Type
 */
public class PropertySelectionCollector extends CollectorSupport 
{
    private Select select;
    // FIXME: what to do with repeated/multiple predicates
    // 
    private Map<commonj.sdo.Property, Where> predicateMap; 
    private Map<Type, List<String>> propertyMap;
    private Map<Type, List<String>> singularPropertyMap;
    
    public PropertySelectionCollector(Select select, Type rootType) {
        super(rootType);
        this.select = select;
    }
    
    public PropertySelectionCollector(Select select, Type rootType,
    		boolean onlySingularProperties) {
    	super(rootType, onlySingularProperties);
        this.select = select;
    }
        
	private void collect()
	{
		if (this.propertyMap == null) {
			this.propertyMap = new HashMap<Type, List<String>>();
			this.singularPropertyMap = new HashMap<Type, List<String>>();
	        this.predicateMap = new HashMap<commonj.sdo.Property, Where>();
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
	        this.select.accept(visitor);
		}
	}	
    
    public Map<commonj.sdo.Property, Where> getPredicateMap() {
        collect();
		return predicateMap;
	}
    
    public Map<Type, List<String>> getSingularPropertyMap() {
        collect();
		return singularPropertyMap;
	}

	/**
	 * Return the specified property 
     * names, as well as any path-reference or key properties required
     * for graph structure, as a simple list of logical property name
     * strings mapped to the respective {@link Type} definition
	 * @return the {@link Type} to logical property name map
	 */
	public Map<Type, List<String>> getResult() {
        collect();
        return this.propertyMap;
    }	
    
	/**
	 * Returns all selected types. 
	 * @return all selected types.
	 */
	public List<Type> getTypes() {
        collect();
        List<Type> result = new ArrayList<Type>();
        result.addAll(this.propertyMap.keySet());
        return result;
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
            String[] names = this.findPropertyNames(rootType, abstractProperty);
            
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
        	if (!this.isOnlySingularProperties()) {
                collect(path, rootType, 
                    path.getPathNodes().get(0), 0, 
                    abstractProperty, this.propertyMap);
        	}
        	else {
        		if (this.isSingularPath(path, rootType, abstractProperty)) 
                    collect(path, rootType, 
                            path.getPathNodes().get(0), 0, 
                            abstractProperty, this.propertyMap);
        	}
        }
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
            
            if (prop.isMany() && this.isOnlySingularProperties())
            	return;

            
            Type nextType = prop.getType(); // traverse
            
            if (path.getPathNodes().size() > curPathElementIndex + 1) { // more nodes
            	this.mapProperty(currType, prop, map);

                int nextPathElementIndex = curPathElementIndex + 1;
                PathNode nextPathNode = path.getPathNodes().get(nextPathElementIndex);
                collect(path, nextType, nextPathNode, nextPathElementIndex, abstractProperty, map);
            }
            else {
            	this.mapProperty(currType, prop, map);                              
                String[] names = this.findPropertyNames(nextType, abstractProperty);
                this.mapPropertyNames(nextType, names, map);
            }
        }
        else if (currPathElement instanceof WildcardPathElement) {
            List<commonj.sdo.Property> properties = null;
            if (this.onlyDeclaredProperties)
            	properties = currType.getDeclaredProperties();
            else
            	properties = currType.getProperties();
            
            if (currPathode.getWhere() != null)
                throw new InvalidPathPredicateException("path predicate found on wildcard path element");

            for (commonj.sdo.Property prop : properties) {
            	if (prop.getType().isDataType())
            		continue;
                if (prop.isMany() && this.isOnlySingularProperties())
                	return;
            	
                Type nextType = prop.getType();
                
                if (path.getPathNodes().size() > curPathElementIndex + 1) {
                	this.mapProperty(currType, prop, map);

                    int nextPathElementIndex = curPathElementIndex + 1;
                    PathNode nextPathNode = path.getPathNodes().get(nextPathElementIndex);
                    collect(path, nextType, nextPathNode, nextPathElementIndex, abstractProperty, map);
                }
                else {
                	this.mapProperty(currType, prop, map);
                    String[] names = this.findPropertyNames(nextType, abstractProperty);
                    this.mapPropertyNames(nextType, names, map);
                }
            }
        }
        else
            throw new IllegalArgumentException("unknown path element class, "
                + currPathElement.getClass().getName());
    }
    
	public String dumpProperties() {
        StringBuilder buf = new StringBuilder();
		Iterator<Type> typeIter = propertyMap.keySet().iterator();
        while (typeIter.hasNext()) {
        	PlasmaType type = (PlasmaType)typeIter.next();
        	buf.append("\n" + type.getURI() + "#" + type.getName());
        	List<String> names = propertyMap.get(type);
            for (String name : names) {
    			PlasmaProperty prop = (PlasmaProperty)type.getProperty(name);
            	buf.append("\n\t" + name);
    		}        
        }
        return buf.toString();
	}
    
}
