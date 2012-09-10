/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: StringParameter.java,v 1.2 2007/12/15 04:14:19 grays Exp $
 */

package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StringParameter", propOrder = {
    "value"
})
@XmlRootElement(name = "StringParameter")
public class StringParameter {

    @XmlValue
    protected String value;


      //----------------/
     //- Constructors -/
    //----------------/

    public StringParameter() {
        super();
        setValue("");
    } //-- org.plasma.mda.query.StringParameter()

    public StringParameter(String value) {
        super();
        setValue(value);
    } //-- org.plasma.mda.query.StringParameter()

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
