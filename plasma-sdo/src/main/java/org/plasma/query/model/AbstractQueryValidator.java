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
package org.plasma.query.model;

import org.plasma.query.From;
import org.plasma.query.OrderBy;
import org.plasma.query.Query;
import org.plasma.query.Where;
import org.plasma.query.visitor.DefaultQueryVisitor;

/**
 */
public abstract class AbstractQueryValidator extends DefaultQueryVisitor 
{
    public AbstractQueryValidator() 
    {
        super();
    }

    public void start(Query query)     
    {
        super.start(query); 
    }

    public void start(Clause clause)    
    {
        super.start(clause); 
    }

    public void start(Select select)    
    {
        super.start(select); 
    }
    
    public void start(Update update)    
    {
        super.start(update); 
    }
    
    public void start(Delete delete)    
    {
        super.start(delete); 
    }

    public void start(From from)    
    {
        super.start(from); 
    }

    public void start(Where where)    
    {
        super.start(where); 
    }

    public void start(OrderBy orderBy)    
    {
        super.start(orderBy); 
    }

    public void start(Expression expression)    
    {
        super.start(expression); 
    }

    public void start(Term term)    
    {
        super.start(term); 
    }

    public void start(Property property)    
    {
        super.start(property); 
    }

    public void start(Entity entity)    
    {
        super.start(entity); 
    }

    public void start(RelationalOperator operator)    
    {
        super.start(operator); 
    }
    
    public void start(LogicalOperator operator)    
    {
        super.start(operator); 
    }

    public void start(ArithmeticOperator operator)    
    {
        super.start(operator); 
    }

    public void start(WildcardOperator operator)    
    {
        super.start(operator); 
    }

    public void start(Literal literal)    
    {
        super.start(literal); 
    }

}

