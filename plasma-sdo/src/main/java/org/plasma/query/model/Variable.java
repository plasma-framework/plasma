/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
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
