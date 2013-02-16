/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: GroupOperator.java,v 1.5 2007/12/15 04:14:19 grays Exp $
 */

package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupOperator", propOrder = {
    "value"
})
@XmlRootElement(name = "GroupOperator")
public class GroupOperator implements org.plasma.query.Operator {

    @XmlValue
    protected GroupOperatorValues value;


      //----------------/
     //- Constructors -/
    //----------------/

    public GroupOperator() {
        super();
    } 

    public GroupOperator(String oper) {
        super();
        value = GroupOperatorValues.valueOf(oper);
    } 

    public GroupOperator(GroupOperatorValues oper) {
        super();
        value = oper;
    } 

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link GroupOperatorValues }
     *     
     */
    public GroupOperatorValues getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupOperatorValues }
     *     
     */
    public void setValue(GroupOperatorValues value) {
        this.value = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }

}
