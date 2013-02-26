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