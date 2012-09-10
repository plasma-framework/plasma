/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: SubqueryOperator.java,v 1.3 2007/12/15 04:14:18 grays Exp $
 */

package org.plasma.query.model;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubqueryOperator", propOrder = {
    "value"
})
@XmlRootElement(name = "SubqueryOperator")
public class SubqueryOperator {

    public SubqueryOperator() {}
    
    public SubqueryOperator(SubqueryOperatorValues value) {
        this.value = value;
    }
    
    
    @XmlValue
    protected SubqueryOperatorValues value;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link SubqueryOperatorValues }
     *     
     */
    public SubqueryOperatorValues getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubqueryOperatorValues }
     *     
     */
    public void setValue(SubqueryOperatorValues value) {
        this.value = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }
}
