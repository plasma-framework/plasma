package org.plasma.sdo.access.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaDataObjectConstants;
import org.plasma.sdo.PlasmaProperty;
import org.plasma.sdo.access.DataAccessException;
import org.plasma.sdo.access.provider.common.TraversalMap;
import org.plasma.sdo.helper.PlasmaTypeHelper;

import commonj.sdo.Property;
import commonj.sdo.Type;


/**
 */
public abstract class Entity implements DataEntity
{
    private static Log log = LogFactory.getFactory().getInstance(
        Entity.class); 
    
    private static Object lock = new Object();
    private static Map<String, Map<String, Method>[]> methodMap = new HashMap<String, Map<String, Method>[]>(); 
    private static Object[] emptyArgs = new Object[] {};
    private static int GET_METHODS = 0;
    private static int SET_METHODS = 1;
    private static int ADD_METHODS = 2;
    private String hashKey;

    private Type type;
    private String namespaceURI;

    public Entity() 
    {         
        synchronized (lock) 
        {
            // check static method-map-array hash for class-specific info
            Map<String, Method>[] maps = methodMap.get(this.getClass().getName());
            if (maps == null)
                methodMap.put(this.getClass().getName(), createMethodMaps());
        }
    }

    /**
     * Finds and maps an, albeit incomplete, set of specific methods
     * to/for 'this' class-name for use in generic property-oriented
     * get() and set() methods below.  
     */
    @SuppressWarnings("unchecked")
	private Map<String, Method>[] createMethodMaps()
    {
        Map<String, Method>[] maps = new Map[3]; 
        maps[GET_METHODS] = new HashMap<String, Method>();
        maps[SET_METHODS] = new HashMap<String, Method>();
        maps[ADD_METHODS] = new HashMap<String, Method>();

        Method[] methods = this.getClass().getMethods();                  
        for (int i = 0; i < methods.length; i++) 
        {
            if (methods[i].getName().startsWith("get"))               
            {                                                         
                Class[] argTypes = methods[i].getParameterTypes();    
                if (argTypes.length == 0) // zero arg getters
                {   
                    String key = 
                        String.valueOf(Character.toLowerCase(methods[i].getName().charAt(3)))
                            + methods[i].getName().substring(4);
                    maps[GET_METHODS].put(key, methods[i]); 

                    key = methods[i].getName().substring(3).toLowerCase();
                    maps[GET_METHODS].put(key, methods[i]); 
                } 
                // more getters??
            }
            else if (methods[i].getName().startsWith("set"))
            {
                Class[] argTypes = methods[i].getParameterTypes();    
                if (argTypes.length == 1) // single arg setters
                {   
                    String key = 
                        String.valueOf(Character.toLowerCase(methods[i].getName().charAt(3)))
                            + methods[i].getName().substring(4);                          
                    maps[SET_METHODS].put(key, methods[i]); 

                    key = methods[i].getName().substring(3).toLowerCase();
                    maps[SET_METHODS].put(key, methods[i]); 
                } 
                // more setters??
            }
            else if (methods[i].getName().startsWith("add"))
            {
                Class[] argTypes = methods[i].getParameterTypes();    
                if (argTypes.length == 1) // single arg adders
                {   
                    String key = 
                        String.valueOf(Character.toLowerCase(methods[i].getName().charAt(3)))
                            + methods[i].getName().substring(4);                          
                    maps[ADD_METHODS].put(key, methods[i]); 
                    
                    key = methods[i].getName().substring(3).toLowerCase();
                    maps[ADD_METHODS].put(key, methods[i]); 
                } 
                // more adders??
            }
        }
        return maps;                                                        
    }

    /**
     * Generic reflection-based 'getter'. Finds the
     * specific getter method based on the given property
     * name and 'this' class and invokes it.
     * @param - the property name
     * @return - the value
     */
    public Object get(String name)
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {     
        Map<String, Method>[] maps = methodMap.get(this.getClass().getName());  
        Method method = (Method)maps[GET_METHODS].get(name);
        if (method != null) {
            return method.invoke(this, emptyArgs); 
        }
        else {
        	String lowerName = name.toLowerCase();
        	method = (Method)maps[GET_METHODS].get(lowerName);
            if (method != null)
                return method.invoke(this, emptyArgs); 
            else
                throw new EntityException("no "+this.getClass().getName()
                		+ " 'get' method found for property '" + name + "'");
        }
    }

    /**
     * Generic reflection-based 'setter'. Finds the
     * specific setter method based on the given property
     * name and 'this' class and invokes it using the
     * given value.
     * @param - the property name
     * @param - the value to set
     * @return - the old value
     */
    public Object set(String name, Object o)
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {
        Object old = get(name);
        Map<String, Method>[] maps = methodMap.get(this.getClass().getName());  
        Method method = (Method)maps[SET_METHODS].get(name);
        if (method != null)
        {
            Object[] args = new Object[] {o};
            method.invoke(this, args);
        } 
        else {
        	String lowerName = name.toLowerCase();
        	method = (Method)maps[SET_METHODS].get(lowerName);
        	if (method != null) {
                Object[] args = new Object[] {o};
                method.invoke(this, args);
        	}
        	else
                throw new EntityException("no "+this.getClass().getName()
                		+ " 'set' method found for property '" + name + "'");
        }
        return old;
    }

    /**
     * Generic reflection-based 'adder'. Finds the
     * specific adder method based on the given property
     * name and 'this' class and invokes it using the
     * given value.
     * @param - the property name
     * @param - the value to add
     */
    public void add(String name, Object o)
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {
        Map<String, Method>[] maps = methodMap.get(this.getClass().getName());  
        Method method = (Method)maps[ADD_METHODS].get(name);
        if (method != null)
        {
            Object[] args = new Object[] {o};
            method.invoke(this, args);
        } 
        else {
        	String lowerName = name.toLowerCase();
        	method = (Method)maps[ADD_METHODS].get(lowerName);
        	if (method != null) {
                Object[] args = new Object[] {o};
                method.invoke(this, args);
        	}
        	else
                throw new EntityException("no "+this.getClass().getName()
                		+ " 'add' method found for property '" + name + "'");
        }
    }

    /**
     * Returns the value for the id property, often
     * the property associated with the DB primary key.
     */
    public Object getEntityId()
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {
        return get(getIdProperty().getName());
    }

    /**
     * Sets the value for the id property, often
     * the property associated with the DB primary key.
     * @param - the id value
     */
    public Object setEntityId(Object id)
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {
        return set(getIdProperty().getName(), id);
    }

    /**
     * This recursive traversal algorithm is based on the Visitor Pattern and is capable of 
     * both breadth-first and depth-first traversals. The entry point is found in the 
     * Entity.accept(IPOMVisitor visitor) method. It assumes a strict hierarchy of POM 
     * objects linked through reference properties. Within contexts where other than strict 
     * hierarchical structures are possible, the associated graph traversal algorithm should be 
     * used instead Though this algorithm will traverse any structure including a 
     * hierarchy, graph or network without errors, the traversal path could be undesirable 
     * except for strict hierarchies. 
     * @param - the visitor
     */
    public void accept(EntityVisitor visitor) 
    {
        try {
            accept(visitor, this, null, null, new HashMap<Entity, Entity>(), false);
        } catch (IllegalAccessException e) {
            throw new EntityException(e);
        } catch (IllegalArgumentException e) {
            throw new EntityException(e);
        } catch (InvocationTargetException e) {
            throw new EntityException(e);
        }
    }

    
    /**
     * Variant of above.
     */
    public void accept(EntityVisitor visitor, boolean depthFirst) 
    {
        try {
            accept(visitor, this, null, null, new HashMap<Entity, Entity>(), depthFirst);
        } catch (IllegalAccessException e) {
            throw new EntityException(e);
        } catch (IllegalArgumentException e) {
            throw new EntityException(e);
        } catch (InvocationTargetException e) {
            throw new EntityException(e);
        }
    }

    /**
     * This recursive traversal algorithm is based on the Visitor Pattern and 
     * is capable of only breadth-first traversals. It assumes a complex POM graph linked through 
     * reference properties. Within contexts where a strict hierarchical structure can be 
     * guaranteed, the associated hierarchy traversal algorithm should be used instead. This algorithm 
     * relies on a "traversal map" which is a structure which maps property name 
     * path strings, in the format '/shipment/orders', to property name string arrays.
     * @param - the graph visitor
     * @param - the traversal map     
     */
    public void accept(EntityGraphVisitor visitor, TraversalMap traversalMap) 
    {
        try {
            accept(visitor, this, traversalMap);
        } catch (IllegalAccessException e) {
            throw new EntityException(e);
        } catch (InvocationTargetException e) {
            throw new EntityException(e);
        }
    }

    private boolean accept(EntityVisitor visitor, Entity entity,  
        Entity source, String sourceKey, 
        Map<Entity, Entity> visited, boolean depthFirst) 
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {
        Entity[] entitys = null;
        visited.put(entity, entity);
 
        if (!depthFirst)
            if (!visitor.visit(entity, source, sourceKey))
            	return false; // abort

        List<Property> properties = this.getType().getDeclaredProperties();
        for (Property property : properties)
        {                                                                    
            if (property.getName().equals(this.getIdProperty().getName()))                                            
                continue;                                                    
            if (property.getType().isDataType())                                      
                continue;                                                    
            entitys = getNodes(entity, property);                            
                                                                             
            for (int j = 0; j < entitys.length; j++)                         
            {                                                                
                if (visited.get(entitys[j]) == null)                         
                    if (!accept(visitor, entitys[j], entity, property.getName(), visited, depthFirst))
                        return false; // abort        
            }                                                                
        }                                                                    

        if (depthFirst)
            if (!visitor.visit(entity, source, sourceKey))
            	return false; // abort
            	
        return true;    	
    }

    private void accept(EntityGraphVisitor visitor, Entity entity, 
        TraversalMap traversalMap) 
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {
        Entity target = entity;
        Iterator iter = traversalMap.iterator();
        while (iter.hasNext())
        {
            String pathStr = (String)iter.next(); 
            if (!pathStr.startsWith(TraversalMap.DELIM_PATH))
                continue; // not path-oriented format
            //log.info(pathStr);
            String[] path = pathStr.substring(1).split(TraversalMap.DELIM_PATH);
            if (path.length == 1 && path[0].length() == 0)
                path = null; // it's a root
                
            accept(visitor, entity, null, null, path, -1, pathStr, TraversalMap.DELIM_PATH);    
        }
    }

    private void accept(EntityGraphVisitor visitor, Entity entity, 
        Entity source, String sourceKey, String[] path, int item, String fullPath, String currPath) 
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {
        Entity[] entities = null;
        item += 1;                                                                                  

        if (path != null && item < path.length)
        {
            //log.info("path: " + fullPath + " item: " + path[item] + " curr: " + String.valueOf(currPath));
            visitor.visit(entity, source, sourceKey, null); // no path-key yet - haven't reached end
            currPath += path[item] + TraversalMap.DELIM_PATH;  
            
            Property property = entity.getType().getProperty(path[item]);
            
            entities = getNodes(entity, property);                                                                                                                                                                  
            for (int i = 0; i < entities.length; i++)                                                   
                accept(visitor, entities[i], entity, property.getName(), path, item, fullPath, currPath);                              
        }
        else
        {                                                                                             
            //log.info("path: " + fullPath + " curr: " + String.valueOf(currPath));
            visitor.visit(entity, source, sourceKey, currPath); // last or only one
        }
    }

    private Entity[] getNodes(Entity entity, Property property)
        throws IllegalAccessException,
               IllegalArgumentException,
               InvocationTargetException
    {
        Entity[] entitys = null;

        if (property.getType().isDataType())                            
            throw new EntityException("Expected reference property");                                          
        if (property.isMany())                             
        {                                                      
            entitys = (Entity[])entity.get(property.getName());   
        }                                                      
        else                                                   
        {                                                      
            Entity temp = (Entity)entity.get(property.getName()); 
            if (temp != null)                                  
            {                                                  
                entitys = new Entity[1];                         
                entitys[0] = temp;                              
            }                                                  
            else                                               
                entitys = new Entity[0];                         
        }
        return entitys;                                                      
    }
    
    public String getNamespaceURI() {
        if (this.namespaceURI == null) {
            try {
                Field uriField = this.getClass().getDeclaredField(
                        PlasmaDataObjectConstants.NAMESPACE_URI_FIELD_NAME);
                this.namespaceURI = (String)uriField.get(null);
            } catch (SecurityException e) {
                throw new EntityException(e);
            } catch (NoSuchFieldException e) {
                throw new EntityException(e);
            } catch (IllegalArgumentException e) {
                throw new EntityException(e);
            } catch (IllegalAccessException e) {
                throw new EntityException(e);
            }
        }
        return this.namespaceURI;
    }

    public Type getType()
    {
        if (this.type == null)
        {
            String className = this.getClass().getSimpleName();
            this.type = PlasmaTypeHelper.INSTANCE.getType(this.getNamespaceURI(), className);
        }
        return this.type;
    }

    public Property getIdProperty()
    {
        List<Property> pkList = (List<Property>)this.getType().get(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_PRIKEY_PROPERTIES);
        if (pkList == null || pkList.size() == 0)
            throw new DataAccessException("no pri-key properties found for type '" 
                    + this.getType().getName() + "'");
        if (pkList.size() > 1)
            throw new DataAccessException("multiple pri-key properties found for type '" 
                    + this.getType().getName() + "' - not yet supported");
        return pkList.get(0);
    }
    
    public String getHashKey() 
        throws IllegalArgumentException, IllegalAccessException, InvocationTargetException 
    {
        if (this.hashKey == null) {
            StringBuilder buf = new StringBuilder();
            buf.append(this.getType().getName());
            List<Property> pkPropertyList = (List<Property>)this.getType().get(PlasmaProperty.INSTANCE_PROPERTY_OBJECT_PRIKEY_PROPERTIES);
            if (pkPropertyList == null)
                throw new DataAccessException("found no pri-key properties found for type '" 
                        + this.getType().getName() + "'");
            
            for (Property property : pkPropertyList)
            {
                Object id = this.get(property.getName());
                if (id != null) {
                    buf.append(id.toString());
                }
                else {
                    // May not always want pri-keys in results sets, say when
                    // using aggregate functions. 
                    this.hashKey = java.util.UUID.randomUUID().toString();
                    if (log.isDebugEnabled())
                        log.debug("null pri-key value found for '"
                            + this.getType().getName() + "." + property.getName()
                            + "' when constructing hash-key - using UUID");
                    break;
                }
            }
            this.hashKey = buf.toString();
        }
        return hashKey;        
    }
    
    public String toString() {
        try {
            return String.valueOf(getHashKey());
        } catch (IllegalArgumentException e) {
            throw new EntityException(e);
        } catch (IllegalAccessException e) {
            throw new EntityException(e);
        } catch (InvocationTargetException e) {
            throw new EntityException(e);
        }
    }

/*    
    public int hashCode() {
        return hash;
    }

    private void resetHash()
    {
        try {
            String s = this.getClass().getName() + getId().toString();
            hash = Hash.intHash(s.getBytes());
        }
        catch (IllegalAccessException e) {
            throw new POMException(e);
        }
        catch (IllegalArgumentException e) {
            throw new POMException(e);
        }
        catch (InvocationTargetException e) {
            throw new POMException(e);
        }
    }
*/
}
