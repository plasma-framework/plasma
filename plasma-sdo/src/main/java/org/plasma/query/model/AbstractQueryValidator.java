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

