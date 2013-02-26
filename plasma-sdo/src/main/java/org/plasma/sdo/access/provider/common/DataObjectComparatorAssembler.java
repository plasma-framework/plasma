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
package org.plasma.sdo.access.provider.common;

// java imports
import java.util.Comparator;

import org.plasma.query.model.AbstractPathElement;
import org.plasma.query.model.OrderBy;
import org.plasma.query.model.Path;
import org.plasma.query.model.PathElement;
import org.plasma.query.model.Property;
import org.plasma.query.model.SortDirectionValues;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.Traversal;
import org.plasma.sdo.access.DataAccessException;

import commonj.sdo.Type;

public class DataObjectComparatorAssembler extends DefaultQueryVisitor
{
    private OrderBy orderby;    
    private Type rootType;  
    private DataObjectComparator comparator;  

    @SuppressWarnings("unused")
	private DataObjectComparatorAssembler() {}

    public DataObjectComparatorAssembler(OrderBy orderby, Type rootType)
    {
        this.orderby = orderby;
        this.rootType = rootType;
        this.comparator = new DataObjectComparator();
        orderby.accept(this);
    }
    
    public Comparator getComparator()
    {
        return comparator;
    }

    public void start(Property property)                                                                            
    {                   
        Type contextType = this.rootType; // init
        
        String[] names = null;
        if (property.getPath() == null)
        {
            names = new String[1]; 
        }
        else                                                                             
        {                     
            Path path = property.getPath();
            names = new String[path.getPathNodes().size() + 1];                                                                                      
            for (int i = 0 ; i < path.getPathNodes().size(); i++)
            {
                AbstractPathElement elem = path.getPathNodes().get(i).getPathElement();
                if (elem instanceof PathElement) {
                    commonj.sdo.Property prop = contextType.getProperty(((PathElement)elem).getValue());
                    if (prop.isMany())
                        throw new DataAccessException("ordering not supported across collection properties - '" +
                            contextType.getName() + "." + prop.getName() + "' is a collection property");                                    
                    
                    contextType = prop.getOpposite().getContainingType();
                    names[i] = prop.getName(); 
                }
                else
                    throw new IllegalArgumentException("wildcard paths not supported for this query");
            }                                                                                                       
        }                                                                                                           
        commonj.sdo.Property endpointPDef = contextType.getProperty(property.getName());
        names[names.length-1] = endpointPDef.getName();
        if (property.getDirection() == null || property.getDirection().ordinal() == SortDirectionValues.ASC.ordinal())
            comparator.addAscending(names);
        else  
            comparator.addDescending(names);
        
        this.getContext().setTraversal(Traversal.ABORT);
    }                                                                                                                                                                                                                                                                                                                                                           
}