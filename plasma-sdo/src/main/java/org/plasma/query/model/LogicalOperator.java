/*
 * This class was automatically generated with
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: BooleanOperator.java,v 1.3 2007/12/15 04:14:18 grays Exp $
 */

package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.QueryException;
import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogicalOperator", propOrder = {
    "value"
})
@XmlRootElement(name = "LogicalOperator")
public class LogicalOperator implements org.plasma.query.Operator {

    @XmlValue
    protected LogicalOperatorValues value;

    public LogicalOperator() {
        super();
    } 

    public LogicalOperator(String content) {
        this();
        setValue(LogicalOperatorValues.valueOf(content));
    } 

    public LogicalOperator(LogicalOperatorValues content) {
        this();
        setValue(content);
    } 
    
    public static LogicalOperator valueOf(String value) {
    	if ("=".equals(value))
    		return new LogicalOperator(LogicalOperatorValues.AND);
    	else if ("!=".equals(value))
    		return new LogicalOperator(LogicalOperatorValues.OR);
    	else
    	    throw new QueryException("invalid operator '" 
    	    		+ value + "'");
    }
    
    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link LogicalOperatorValues }
     *     
     */
    public LogicalOperatorValues getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogicalOperatorValues }
     *     
     */
    public void setValue(LogicalOperatorValues value) {
        this.value = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }
}
