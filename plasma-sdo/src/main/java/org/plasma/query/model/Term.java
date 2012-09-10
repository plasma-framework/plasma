package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Term", propOrder = {
    "wildcardProperty",
    "subqueryOperator",
    "wildcardOperator",
    "entity",
    "expression",
    "variable",
    "nullLiteral",
    "literal",
    "groupOperator",
    "arithmeticOperator",
    "relationalOperator",
    "logicalOperator",
    "property",
    "query"
})
@XmlRootElement(name = "Term")
public class Term {

    @XmlElement(name = "WildcardProperty")
    protected WildcardProperty wildcardProperty;
    @XmlElement(name = "SubqueryOperator")
    protected SubqueryOperator subqueryOperator;
    @XmlElement(name = "WildcardOperator")
    protected WildcardOperator wildcardOperator;
    @XmlElement(name = "Entity")
    protected Entity entity;
    @XmlElement(name = "Expression")
    protected Expression expression;
    @XmlElement(name = "Variable")
    protected Variable variable;
    @XmlElement(name = "NullLiteral")
    protected NullLiteral nullLiteral;
    @XmlElement(name = "Literal")
    protected Literal literal;
    @XmlElement(name = "GroupOperator")
    protected GroupOperator groupOperator;
    @XmlElement(name = "ArithmeticOperator")
    protected ArithmeticOperator arithmeticOperator;
    @XmlElement(name = "RelationalOperator")
    protected RelationalOperator relationalOperator;
    
    @XmlElement(name = "LogicalOperator")
    protected LogicalOperator logicalOperator;
    
    @XmlElement(name = "Property")
    protected Property property;
    
    @XmlElement(name = "Query")
    protected Query query;


      //----------------/
     //- Constructors -/
    //----------------/

    public Term() {
        super();
    } //-- org.plasma.mda.query.Term()

    public Term(Property property) {
        this();
        this.setProperty(property);
    } //-- org.plasma.mda.query.Term()

    public Term(WildcardProperty wildcardProperty) {
        this();
        this.setWildcardProperty(wildcardProperty);
    } 

    public Term(Entity entity) {
        this();
        this.setEntity(entity);
    } 

    public Term(RelationalOperator oper) {
        this();
        this.setRelationalOperator(oper);
    } 

    public Term(LogicalOperator oper) {
        this();
        this.setLogicalOperator(oper);
    } 

    public Term(ArithmeticOperator oper) {
        this();
        this.setArithmeticOperator(oper);
    }

    public Term(GroupOperator oper) {
        this();
        this.setGroupOperator(oper);
    }

    public Term(WildcardOperator oper) {
        this();
        this.setWildcardOperator(oper);
    } 

    public Term(SubqueryOperator oper) {
        this();
        this.setSubqueryOperator(oper);
    } 

    public Term(Literal literal) {
        this();
        this.setLiteral(literal);
    }

    public Term(NullLiteral literal) {
        this();
        this.setNullLiteral(literal);
    }

    public Term(Variable var) {
        this();
        this.setVariable(var);
    } 

    public Term(Expression expression) {
        this();
        this.setExpression(expression);
    } 

    public Term(Query query) {
        this();
        this.setQuery(query);
    } 

    /**
     * Gets the value of the wildcardProperty property.
     * 
     * @return
     *     possible object is
     *     {@link WildcardProperty }
     *     
     */
    public WildcardProperty getWildcardProperty() {
        return wildcardProperty;
    }

    /**
     * Sets the value of the wildcardProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link WildcardProperty }
     *     
     */
    public void setWildcardProperty(WildcardProperty value) {
        this.wildcardProperty = value;
    }

    /**
     * Gets the value of the subqueryOperator property.
     * 
     * @return
     *     possible object is
     *     {@link SubqueryOperator }
     *     
     */
    public SubqueryOperator getSubqueryOperator() {
        return subqueryOperator;
    }

    /**
     * Sets the value of the subqueryOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubqueryOperator }
     *     
     */
    public void setSubqueryOperator(SubqueryOperator value) {
        this.subqueryOperator = value;
    }

    /**
     * Gets the value of the wildcardOperator property.
     * 
     * @return
     *     possible object is
     *     {@link WildcardOperator }
     *     
     */
    public WildcardOperator getWildcardOperator() {
        return wildcardOperator;
    }

    /**
     * Sets the value of the wildcardOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link WildcardOperator }
     *     
     */
    public void setWildcardOperator(WildcardOperator value) {
        this.wildcardOperator = value;
    }

    /**
     * Gets the value of the entity property.
     * 
     * @return
     *     possible object is
     *     {@link Entity }
     *     
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets the value of the entity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Entity }
     *     
     */
    public void setEntity(Entity value) {
        this.entity = value;
    }

    /**
     * Gets the value of the expression property.
     * 
     * @return
     *     possible object is
     *     {@link Expression }
     *     
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * Sets the value of the expression property.
     * 
     * @param value
     *     allowed object is
     *     {@link Expression }
     *     
     */
    public void setExpression(Expression value) {
        this.expression = value;
    }

    /**
     * Gets the value of the variable property.
     * 
     * @return
     *     possible object is
     *     {@link Variable }
     *     
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * Sets the value of the variable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Variable }
     *     
     */
    public void setVariable(Variable value) {
        this.variable = value;
    }

    /**
     * Gets the value of the nullLiteral property.
     * 
     * @return
     *     possible object is
     *     {@link NullLiteral }
     *     
     */
    public NullLiteral getNullLiteral() {
        return nullLiteral;
    }

    /**
     * Sets the value of the nullLiteral property.
     * 
     * @param value
     *     allowed object is
     *     {@link NullLiteral }
     *     
     */
    public void setNullLiteral(NullLiteral value) {
        this.nullLiteral = value;
    }

    /**
     * Gets the value of the literal property.
     * 
     * @return
     *     possible object is
     *     {@link Literal }
     *     
     */
    public Literal getLiteral() {
        return literal;
    }

    /**
     * Sets the value of the literal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Literal }
     *     
     */
    public void setLiteral(Literal value) {
        this.literal = value;
    }

    /**
     * Gets the value of the groupOperator property.
     * 
     * @return
     *     possible object is
     *     {@link GroupOperator }
     *     
     */
    public GroupOperator getGroupOperator() {
        return groupOperator;
    }

    /**
     * Sets the value of the groupOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupOperator }
     *     
     */
    public void setGroupOperator(GroupOperator value) {
        this.groupOperator = value;
    }

    /**
     * Gets the value of the relationalOperator property.
     * 
     * @return
     *     possible object is
     *     {@link RelationalOperator }
     *     
     */
    public RelationalOperator getRelationalOperator() {
        return relationalOperator;
    }

    /**
     * Sets the value of the relationalOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationalOperator }
     *     
     */
    public void setRelationalOperator(RelationalOperator value) {
        this.relationalOperator = value;
    }
    
    /**
     * Gets the value of the logicalOperator property.
     * 
     * @return
     *     possible object is
     *     {@link LogicalOperator }
     *     
     */
    public LogicalOperator getLogicalOperator() {
        return logicalOperator;
    }

    /**
     * Sets the value of the logicalOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicalOperator }
     *     
     */
    public void setLogicalOperator(LogicalOperator value) {
        this.logicalOperator = value;
    }

    /**
     * Gets the value of the arithmeticOperator property.
     * 
     * @return
     *     possible object is
     *     {@link ArithmeticOperator }
     *     
     */
    public ArithmeticOperator getArithmeticOperator() {
        return arithmeticOperator;
    }

    /**
     * Sets the value of the arithmeticOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArithmeticOperator }
     *     
     */
    public void setArithmeticOperator(ArithmeticOperator value) {
        this.arithmeticOperator = value;
    }

    /**
     * Gets the value of the property property.
     * 
     * @return
     *     possible object is
     *     {@link Property }
     *     
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Sets the value of the property property.
     * 
     * @param value
     *     allowed object is
     *     {@link Property }
     *     
     */
    public void setProperty(Property value) {
        this.property = value;
    }

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link Query }
     *     
     */
    public Query getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link Query }
     *     
     */
    public void setQuery(Query value) {
        this.query = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
        {
            if (property != null)                              
                property.accept(visitor);                      
            if (wildcardProperty != null)                              
                wildcardProperty.accept(visitor);                      
            if (entity != null)                                
                entity.accept(visitor);                        
            if (relationalOperator != null)                       
            	relationalOperator.accept(visitor);               
            if (logicalOperator != null)                       
            	logicalOperator.accept(visitor);               
            if (arithmeticOperator != null)                       
            	arithmeticOperator.accept(visitor);               
            if (groupOperator != null)                    
                groupOperator.accept(visitor);            
            if (wildcardOperator != null)                      
                wildcardOperator.accept(visitor);              
            if (subqueryOperator != null)                      
                subqueryOperator.accept(visitor);              
            if (literal != null)                               
                literal.accept(visitor);                       
            if (nullLiteral != null)                               
                nullLiteral.accept(visitor);                       
            if (variable != null)                               
                variable.accept(visitor);                       
            if (expression != null)                            
                expression.accept(visitor);                    
            if (query != null)                            
                query.accept(visitor);                    
        }
    	visitor.end(this);
    }

}
