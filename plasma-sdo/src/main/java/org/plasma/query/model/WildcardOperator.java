/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: WildcardOperator.java,v 1.2 2007/12/15 04:14:18 grays Exp $
 */

package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.visitor.QueryVisitor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WildcardOperator", propOrder = {
    "value"
})
@XmlRootElement(name = "WildcardOperator")
public class WildcardOperator implements org.plasma.query.Operator {

    @XmlValue
    protected WildcardOperatorValues value;

    public WildcardOperator() {
        super();
        value = WildcardOperatorValues.LIKE;
    } //-- org.plasma.mda.query.WildcardOperator()

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link WildcardOperatorValues }
     *     
     */
    public WildcardOperatorValues getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link WildcardOperatorValues }
     *     
     */
    public void setValue(WildcardOperatorValues value) {
        this.value = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }

}
