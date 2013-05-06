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
    implements PropertySelection 
{
    private Select select;
    // FIXME: what to do with repeated/multiple predicates
    // 
    private Map<commonj.sdo.Property, Where> predicateMap; 
    private Map<Type, List<String>> propertyMap;
    private Map<Type, List<String>> singularPropertyMap;
    private Map<Type, List<String>> inheritedPropertyMap;
    
    public PropertySelectionCollector(Select select, Type rootType) {
        super(rootType);
        this.select = select;
    }
    
    public PropertySelectionCollector(Select select, Type rootType,
    		boolean onlySingularProperties) {
    	super(rootType, onlySingularProperties);
        this.select = select;
    }
    
	@Override
	public void collect(Where predicate) {
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
        predicate.accept(visitor);
	}
        
	private void collect()
	{
		if (this.propertyMap == null) {
			this.propertyMap = new HashMap<Type, List<String>>();
			this.singularPropertyMap = new HashMap<Type, List<String>>();
			this.inheritedPropertyMap = new HashMap<Type, List<String>>();
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
    
	@Deprecated
    public Map<commonj.sdo.Property, Where> getPredicateMap() {
        collect();
		return predicateMap;
	}
	
	/* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#getPredicate(commonj.sdo.Property)
	 */
	@Override
	public Where getPredicate(commonj.sdo.Property property) {
		if (this.predicateMap != null)
			return this.predicateMap.get(property);
		return null;
	}
    
	@Deprecated
    public Map<Type, List<String>> getSingularPropertyMap() {
        collect();
		return this.singularPropertyMap;
	}

	/* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#getSingularProperties(commonj.sdo.Type)
	 */
	@Override
	public List<String> getSingularProperties(Type type) {
        collect();
        return this.singularPropertyMap.get(type);		
	}
	
	/**
	 * Return the specified property 
     * names, as well as any path-reference or key properties required
     * for graph structure, as a simple list of logical property name
     * strings mapped to the respective {@link Type} definition
	 * @return the {@link Type} to logical property name map
	 */
	@Deprecated
	public Map<Type, List<String>> getResult() {
        collect();
        return this.propertyMap;
    }
	
	/* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#getProperties(commonj.sdo.Type)
	 */
	@Override
	public List<String> getProperties(Type type) {
        collect();
        return propertyMap.get(type);		
	}	

	/* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#getInheritedProperties(commonj.sdo.Type)
	 */
	@Override
	public List<String> getInheritedProperties(Type type) {
        collect();
        return inheritedPropertyMap.get(type);		
	}	
	
	/* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#getTypes()
	 */
	@Override
	public List<Type> getTypes() {
        collect();
        List<Type> result = new ArrayList<Type>();
        result.addAll(this.propertyMap.keySet());
        return result;
    }	

	/* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#hasType(commonj.sdo.Type)
	 */
	@Override
	public boolean hasType(Type type) {
    	return this.propertyMap.get(type) != null;
    }
	
	/* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#getInheritedTypes(commonj.sdo.Type)
	 */
	@Override
	public List<Type> getInheritedTypes() {
        collect();
        List<Type> result = new ArrayList<Type>();
        result.addAll(this.inheritedPropertyMap.keySet());
        return result;
	}

	/* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#hasInheritedType(commonj.sdo.Type)
	 */
	@Override
	public boolean hasInheritedType(Type type) {
    	return this.inheritedPropertyMap.get(type) != null;
	}
   
    /* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#hasProperty(commonj.sdo.Type, commonj.sdo.Property)
	 */
    @Override
	public boolean hasProperty(Type type,  commonj.sdo.Property property) {
    	List<String> props = this.propertyMap.get(type);
    	if (props != null && props.size() > 0) {
    	    for (String prop : props)	
    	    	if (prop.equals(property.getName()))
    	    		return true;
    	}
    	return false;
    }
    
    /* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#addProperty(commonj.sdo.Type, commonj.sdo.Property)
	 */
    @Override
	public List<Type> addProperty(Type rootType, String path) {
    	List<Type> result = new ArrayList<Type>();
    	Type contextType = rootType;
		StringBuilder buf = new StringBuilder();
		String[] tokens = path.split("/");
		for (int i = 0; i < tokens.length; i++) {
			if (i > 0)
				buf.append("/");
			String token = tokens[i];
			int right = token.indexOf("[");
			if (right >= 0) // remove predicate - were just after the path 
				token = token.substring(0, right);	
			int attr = token.indexOf("@");
			if (attr == 0)
				token = token.substring(1);
			commonj.sdo.Property prop = contextType.getProperty(token);
        	this.mapProperty(contextType, prop, this.propertyMap);
        	this.mapInheritedProperty(contextType, prop, this.inheritedPropertyMap);
			if (!prop.getType().isDataType())  {				
				contextType = prop.getType(); // traverse
				result.add(contextType);
			}
			buf.append(prop.getName());
		}
		return result;
	}
   
    /* (non-Javadoc)
	 * @see org.plasma.query.collector.PropertySelection#hasInheritedProperty(commonj.sdo.Type, commonj.sdo.Property)
	 */
    @Override	
    public boolean hasInheritedProperty(Type type, commonj.sdo.Property property) {
    	List<String> props = this.inheritedPropertyMap.get(type);
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
            this.mapPropertyNames(this.rootType, names, this.propertyMap);
            this.mapInheritedPropertyNames(this.rootType, names, this.inheritedPropertyMap);
        }
        else {
        	if (!this.isOnlySingularProperties()) {
                collect(path, rootType, 
                    path.getPathNodes().get(0), 0, 
                    abstractProperty);
        	}
        	else {
        		if (this.isSingularPath(path, rootType, abstractProperty)) 
                    collect(path, rootType, 
                            path.getPathNodes().get(0), 0, 
                            abstractProperty);
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
            int curPathElementIndex, AbstractProperty abstractProperty) {
        
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
            	this.mapProperty(currType, prop, this.propertyMap);
            	this.mapInheritedProperty(currType, prop, this.inheritedPropertyMap);

                int nextPathElementIndex = curPathElementIndex + 1;
                PathNode nextPathNode = path.getPathNodes().get(nextPathElementIndex);
                collect(path, nextType, nextPathNode, nextPathElementIndex, abstractProperty);
            }
            else {
            	this.mapProperty(currType, prop, this.propertyMap);                              
            	this.mapInheritedProperty(currType, prop, this.inheritedPropertyMap);                              
                String[] names = this.findPropertyNames(nextType, abstractProperty);
                this.mapPropertyNames(nextType, names, this.propertyMap);
                this.mapInheritedPropertyNames(nextType, names, this.inheritedPropertyMap);
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
                	this.mapProperty(currType, prop, this.propertyMap);                              
                	this.mapInheritedProperty(currType, prop, this.inheritedPropertyMap);                              

                    int nextPathElementIndex = curPathElementIndex + 1;
                    PathNode nextPathNode = path.getPathNodes().get(nextPathElementIndex);
                    collect(path, nextType, nextPathNode, nextPathElementIndex, abstractProperty);
                }
                else {
                	this.mapProperty(currType, prop, this.propertyMap);                              
                	this.mapInheritedProperty(currType, prop, this.inheritedPropertyMap);                              
                    String[] names = this.findPropertyNames(nextType, abstractProperty);
                	this.mapPropertyNames(nextType, names, this.propertyMap);                              
                	this.mapInheritedPropertyNames(nextType, names, this.inheritedPropertyMap);                              
                }
            }
        }
        else
            throw new IllegalArgumentException("unknown path element class, "
                + currPathElement.getClass().getName());
    }
    
	public String dumpProperties() {
        StringBuilder buf = new StringBuilder();
		Iterator<Type> typeIter = this.propertyMap.keySet().iterator();
        while (typeIter.hasNext()) {
        	PlasmaType type = (PlasmaType)typeIter.next();
        	buf.append("\n" + type.getURI() + "#" + type.getName());
        	List<String> names = this.propertyMap.get(type);
            for (String name : names) {
            	buf.append("\n\t" + name);
    		}        
        }
        return buf.toString();
	}
    
	public String dumpInheritedProperties() {
        StringBuilder buf = new StringBuilder();
		Iterator<Type> typeIter = this.inheritedPropertyMap.keySet().iterator();
        while (typeIter.hasNext()) {
        	PlasmaType type = (PlasmaType)typeIter.next();
        	buf.append("\n" + type.getURI() + "#" + type.getName());
        	List<String> names = this.inheritedPropertyMap.get(type);
            for (String name : names) {
            	buf.append("\n\t" + name);
    		}        
        }
        return buf.toString();
	}

}
