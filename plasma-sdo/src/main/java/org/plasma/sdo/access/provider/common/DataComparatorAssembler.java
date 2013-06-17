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

import org.plasma.query.model.OrderBy;
import org.plasma.query.model.Property;
import org.plasma.query.model.SortDirectionValues;
import org.plasma.query.visitor.DefaultQueryVisitor;
import org.plasma.query.visitor.Traversal;

import commonj.sdo.Type;

public abstract class DataComparatorAssembler extends DefaultQueryVisitor {

	protected OrderBy orderby;    
	protected Type rootType;  
    protected DataComparator dataComparator;
	protected DataComparatorAssembler(OrderBy orderby, Type rootType, 
			DataComparator dataComparator) {
        this.orderby = orderby;
        this.rootType = rootType;
		this.dataComparator = dataComparator;
        this.orderby.accept(this);
	}
	
    public void start(Property property)                                                                            
    {                   
        if (property.getDirection() == null || property.getDirection().ordinal() == SortDirectionValues.ASC.ordinal())
        	dataComparator.addAscending(property);
        else  
        	dataComparator.addDescending(property);
        
        this.getContext().setTraversal(Traversal.ABORT);
    }                                                                                                                                                                                                                                                                                                                                                           
}
