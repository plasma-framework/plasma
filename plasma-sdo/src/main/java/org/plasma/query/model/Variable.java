/*
 * This class was automatically generated with 
 * <a href="http://castor.exolab.org">Castor 0.9.4</a>, using an
 * XML Schema.
 * $Id: Variable.java,v 1.2 2007/12/15 04:14:18 grays Exp $
 */

package org.plasma.query.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.DataProperty;
import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Variable", propOrder = {
    "property"
})
@XmlRootElement(name = "Variable")
public class Variable {

    @XmlElement(name = "Property", required = true)
    protected Property property;


      //----------------/
     //- Constructors -/
    //----------------/

    public Variable() {
        super();
    } //-- org.plasma.mda.query.Variable()

    public Variable(Property property) {
        this();
        this.property = property;
    } 


    /**
     * Gets the value of the property property.
     * 
     * @return
     *     possible object is
     *     {@link Property }
     *     
     */
    public DataProperty getProperty() {
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


    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
        {
            if (property != null)                              
                property.accept(visitor);       
        }               
    	visitor.end(this);
    }

}
