package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterDeclaration", propOrder = {
    "value"
})
@XmlRootElement(name = "ParameterDeclaration")
public class ParameterDeclaration {

    @XmlValue
    protected String value;

    public ParameterDeclaration() {
        super();
        setValue("");
    } 

    public ParameterDeclaration(String declaration) {
        super();
        setValue(declaration);
    } 

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
