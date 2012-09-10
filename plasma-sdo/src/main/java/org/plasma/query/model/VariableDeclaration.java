/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: VariableDeclaration.java,v 1.2 2007/12/15 04:14:18 grays Exp $
 */

package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VariableDeclaration", propOrder = {
    "value"
})
@XmlRootElement(name = "VariableDeclaration")
public class VariableDeclaration {

    @XmlValue
    protected String value;

    public VariableDeclaration() {
        super();
        setValue("");
    } //-- org.plasma.mda.query.VariableDeclaration()

    public VariableDeclaration(String declaration) {
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
