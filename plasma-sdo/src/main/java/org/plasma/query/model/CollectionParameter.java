/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: CollectionParameter.java,v 1.2 2007/12/15 04:14:19 grays Exp $
 */

package org.plasma.query.model;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CollectionParameter", propOrder = {
    "value"
})
@XmlRootElement(name = "CollectionParameter")
public class CollectionParameter {

    @XmlValue
    protected String value;

    public CollectionParameter() {
        super();
    } 

    public CollectionParameter(Collection value) {
        this();
        StringBuilder buf = new StringBuilder();
        for (Object obj : value) {
            buf.append(String.valueOf(obj));
            buf.append(" ");
        }
        setValue(buf.toString());        
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
