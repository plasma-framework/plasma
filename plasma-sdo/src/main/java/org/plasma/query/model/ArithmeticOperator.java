package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.plasma.query.visitor.QueryVisitor;


/**
 * <p>Java class for ArithmeticOperator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArithmeticOperator">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.servicelabs.org/plasma/query>ArithmeticOperatorValues">
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArithmeticOperator", propOrder = {
    "value"
})
@XmlRootElement(name = "ArithmeticOperator")
public class ArithmeticOperator {

    @XmlValue
    protected ArithmeticOperatorValues value;

    public ArithmeticOperator() {
        super();
    } 
    public ArithmeticOperator(String content) {
        this();
        setValue(ArithmeticOperatorValues.valueOf(content));
    } 

    public ArithmeticOperator(ArithmeticOperatorValues content) {
        this();
        setValue(content);
    } 
    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link ArithmeticOperatorValues }
     *     
     */
    public ArithmeticOperatorValues getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArithmeticOperatorValues }
     *     
     */
    public void setValue(ArithmeticOperatorValues value) {
        this.value = value;
    }

    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
    	visitor.end(this);
    }
}
