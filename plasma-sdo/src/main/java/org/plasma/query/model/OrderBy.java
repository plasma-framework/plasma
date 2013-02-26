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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.plasma.query.visitor.QueryVisitor;
import org.plasma.query.visitor.Traversal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderBy", propOrder = {
    "properties",
    "textContent"
})
@XmlRootElement(name = "OrderBy")
public class OrderBy implements org.plasma.query.OrderBy {

    @XmlElement(name = "Property")
    protected List<Property> properties;
    @XmlElement(name = "TextContent")
    protected TextContent textContent;

    public OrderBy() {
        super();
    } //-- org.plasma.mda.query.OrderBy()


    public OrderBy(Property p1) {
        this();
        getProperties().add(p1);
    }

    public OrderBy(Property p1, Property p2) {
        this();
        getProperties().add(p1);
        getProperties().add(p2);
    }

    public OrderBy(Property p1, Property p2, Property p3) {
        this();
        getProperties().add(p1);
        getProperties().add(p2);
        getProperties().add(p3);
    }

    public OrderBy(Property p1, Property p2, Property p3, Property p4) {
        this();
        getProperties().add(p1);
        getProperties().add(p2);
        getProperties().add(p3);
        getProperties().add(p4);
    }

    public OrderBy(Property[] properties) {
        this();
        for (int i = 0; i < properties.length; i++)
            getProperties().add(properties[i]);
    }

    public OrderBy(String content) {
        this();
        textContent = new TextContent();
        textContent.setValue(content);
    } 

    public List<Property> getProperties() {
        if (properties == null) {
            properties = new ArrayList<Property>();
        }
        return this.properties;
    }
    
    public void addProperty(Property property) {
    	this.getProperties().add(property);
    }

    /**
     * Gets the value of the textContent property.
     * 
     * @return
     *     possible object is
     *     {@link TextContent }
     *     
     */
    public TextContent getTextContent() {
        return textContent;
    }

    /**
     * Sets the value of the textContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextContent }
     *     
     */
    public void setTextContent(TextContent value) {
        this.textContent = value;
    }



    public void accept(QueryVisitor visitor)
    {
        visitor.start(this);
        if (visitor.getContext().getTraversal().ordinal() == Traversal.CONTINUE.ordinal())
        {
            for (int i = 0; i < this.getProperties().size(); i++)
               this.getProperties().get(i).accept(visitor);  
        }
    	visitor.end(this);
    }
}
