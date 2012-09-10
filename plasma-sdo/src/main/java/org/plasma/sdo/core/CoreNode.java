package org.plasma.sdo.core;

// java imports
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.plasma.sdo.PlasmaEdge;
import org.plasma.sdo.PlasmaNode;
import org.plasma.sdo.PlasmaProperty;


public abstract class CoreNode 
    implements PlasmaNode, CoreObjectHolder, NamedEntity 
{
    private static Log log = LogFactory.getFactory().getInstance(CoreNode.class); 

    protected CoreObject valueObject;
    protected static final List <PlasmaEdge> EMPTY_EDGE_LIST = new ArrayList<PlasmaEdge>(); 
    
    /**
     * Default No-arg constructor required for serialization operations. This method 
     * is NOT intended to be used within application source code.  
     */
    protected CoreNode() {}

    public CoreNode(CoreObject values)
    {
        this.valueObject = values;
    }
    
    public abstract String getUUIDAsString();

    public Object getValue(String property) 
    { 
        Object results = valueObject.get(property);
        if (!(results instanceof NullValue))
            return results; 
        else
            return null;
    }

    public void setValue(String property, Object value) 
    { 
        if (value != null)
            valueObject.put(property, value);
        else
            valueObject.put(property, new NullValue());
    }
    
    public void removeValue(String property) 
    { 
        valueObject.remove(property);
    }
    
    public String getEntityName() { return valueObject.getEntityName(); };

    public CoreObject getValueObject() { return valueObject; };
    public void setValueObject(CoreObject v) { valueObject = v; };

	@SuppressWarnings("unchecked")
	public List <PlasmaEdge> getEdges(PlasmaProperty property) {
        List<PlasmaEdge> result = EMPTY_EDGE_LIST;
		if (!property.isMany()) 
        {
            PlasmaEdge edge = (PlasmaEdge)this.getValue(property.getName());
            if (edge != null) {
            	result = new ArrayList<PlasmaEdge>();
            	result.add(edge);
            }
        }
        else
        {
            List<PlasmaEdge> edgeList = (List<PlasmaEdge>) this.getValue(property.getName());  
            if (edgeList != null)
            	result = edgeList;
        }
		return result;
    }
	
	
}